#!/usr/bin/env python3
"""把用户手绘的铁质内衬甲贴图重新上色，生成其余套装。

为什么这样做：用户手绘的贴图使用的是 24 级灰度的明暗阶梯（有立体感），
而脚本原本生成的贴图只有 4 个平面色。直接复用用户的手绘母版重新上色，
可以完整保留轮廓、描边与明暗层次，只替换色相，风格必然与手绘版一致。

原理：源图是离散灰阶，逐像素取亮度 L，在目标色相的「明暗阶梯」上按
相对亮度插值取色。目标阶梯用若干锚点（RGBA）定义，锚点的相对亮度位置
与源图的关键灰阶对应。

用法：
    python tools/recolor_handdrawn.py            # 输出到 tools/recolor_out/
    python tools/recolor_handdrawn.py --apply    # 直接覆盖项目的贴图
"""

from __future__ import annotations

import argparse
import shutil
from pathlib import Path

from PIL import Image

PROJ = Path(__file__).resolve().parent.parent
ASSETS = PROJ / "src" / "main" / "resources" / "assets" / "armor_addon"
ITEM_DIR = ASSETS / "textures" / "item"
ARMOR_DIR = ASSETS / "textures" / "models" / "armor"
OUT_DIR = PROJ / "tools" / "recolor_out"

# 结构母版：用户手绘的铁质内衬甲
SOURCE_SET = "iron_wool"

# 目标色相的明暗阶梯：从最暗的描边到最亮的高光。
# 每项为 (锚点亮度占比, (R, G, B))，占比 0=最暗、1=最亮。
RAMPS = {
    # 附加盔甲：冰钢蓝
    "addon": [
        (0.00, (24, 34, 50)),
        (0.12, (36, 54, 80)),
        (0.30, (56, 96, 142)),
        (0.55, (94, 148, 202)),
        (0.80, (156, 206, 246)),
        (1.00, (214, 238, 253)),
    ],
    # 羊毛盔甲：暖白毛线（描边比金属套装浅一些，保持「柔软」的感觉）
    "wool": [
        (0.00, (74, 68, 60)),
        (0.12, (118, 114, 106)),
        (0.30, (162, 158, 148)),
        (0.55, (206, 202, 192)),
        (0.80, (235, 233, 226)),
        (1.00, (253, 252, 249)),
    ],
    # 内衬甲系列：沿用各自的调色板作为锚点
    "iron_wool": [
        (0.00, (25, 25, 25)),
        (0.12, (45, 45, 45)),
        (0.30, (107, 107, 107)),
        (0.55, (150, 150, 150)),
        (0.80, (216, 216, 216)),
        (1.00, (255, 255, 255)),
    ],
    "gold_wool": [
        (0.00, (74, 48, 8)),
        (0.12, (118, 78, 14)),
        (0.30, (188, 134, 30)),
        (0.55, (236, 189, 63)),
        (0.80, (247, 214, 108)),
        (1.00, (253, 235, 150)),
    ],
    "diamond_wool": [
        (0.00, (14, 64, 68)),
        (0.12, (24, 102, 108)),
        (0.30, (52, 164, 162)),
        (0.55, (92, 224, 216)),
        (0.80, (140, 236, 230)),
        (1.00, (188, 248, 243)),
    ],
    "netherite_wool": [
        (0.00, (26, 22, 26)),
        (0.12, (46, 38, 44)),
        (0.30, (68, 60, 66)),
        (0.55, (102, 92, 100)),
        (0.80, (132, 122, 130)),
        (1.00, (162, 152, 160)),
    ],
}

# 每套要生成哪些贴图。
#   "icons"  = 16x16 物品图标
#   "layers" = 64x32 穿戴贴图
# 注意：黄金 / 钻石 / 合金三套的穿戴贴图是用户手绘的，**不能被本工具覆盖**，
# 因此只生成它们的图标（这三套的图标用户还没做）。
GENERATE = {
    "addon":           ("icons", "layers"),
    "wool":            ("icons", "layers"),
    "gold_wool":       ("icons",),
    "diamond_wool":    ("icons",),
    "netherite_wool":  ("icons",),
}

PIECES = ["helmet", "chestplate", "leggings", "boots"]
LAYERS = ["layer_1", "layer_2"]


def luminance(c) -> float:
    return 0.2126 * c[0] + 0.7152 * c[1] + 0.0722 * c[2]


def build_lut(ramp, lo: float, hi: float):
    """按源图亮度区间 [lo, hi] 建 256 项查找表。"""
    anchors = [(t, c) for t, c in ramp]
    lut = []
    span = max(hi - lo, 1e-6)
    for v in range(256):
        u = (v - lo) / span
        u = 0.0 if u < 0 else (1.0 if u > 1 else u)
        # 找到 u 落在哪个锚点区间
        for i in range(len(anchors) - 1):
            t0, c0 = anchors[i]
            t1, c1 = anchors[i + 1]
            if t0 <= u <= t1:
                f = 0.0 if t1 == t0 else (u - t0) / (t1 - t0)
                lut.append(tuple(round(c0[k] + (c1[k] - c0[k]) * f) for k in range(3)) + (255,))
                break
        else:
            lut.append(anchors[-1][1] + (255,))
    return lut


def recolor(src: Image.Image, ramp) -> Image.Image:
    """把一张源图重新上色；保留 alpha（含全透明像素）。"""
    src = src.convert("RGBA")
    pixels = list(src.getdata())
    op = [p for p in pixels if p[3] > 0]
    if not op:
        return src.copy()
    lums = [luminance(p) for p in op]
    lo, hi = min(lums), max(lums)
    lut = build_lut(ramp, lo, hi)

    out = Image.new("RGBA", src.size, (0, 0, 0, 0))
    out.putdata([
        (0, 0, 0, 0) if p[3] == 0 else lut[max(0, min(255, round(luminance(p))))]
        for p in pixels
    ])
    return out


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("--apply", action="store_true", help="直接覆盖项目的贴图")
    args = ap.parse_args()

    jobs = []
    for set_name, kinds in GENERATE.items():
        if "icons" in kinds:
            for piece in PIECES:
                jobs.append((set_name, ITEM_DIR / f"{SOURCE_SET}_{piece}.png",
                             ITEM_DIR / f"{set_name}_{piece}.png"))
        if "layers" in kinds:
            for layer in LAYERS:
                jobs.append((set_name, ARMOR_DIR / f"{SOURCE_SET}_{layer}.png",
                             ARMOR_DIR / f"{set_name}_{layer}.png"))

    if args.apply:
        print("=== 直接覆盖项目贴图 ===")
    else:
        print(f"=== 输出到 {OUT_DIR.relative_to(PROJ)}（不修改项目）===")
        OUT_DIR.mkdir(parents=True, exist_ok=True)

    for set_name, src_path, dst_path in jobs:
        if not src_path.exists():
            print(f"  跳过（母版缺失）: {src_path.name}")
            continue
        img = recolor(Image.open(src_path), RAMPS[set_name])
        if args.apply:
            img.save(dst_path)
            print(f"  覆盖 {dst_path.relative_to(PROJ)}")
        else:
            sub = "item" if dst_path.parent == ITEM_DIR else "armor"
            (OUT_DIR / sub).mkdir(parents=True, exist_ok=True)
            img.save(OUT_DIR / sub / dst_path.name)
            print(f"  生成 {sub}/{dst_path.name}")

    print("\n完成。")


if __name__ == "__main__":
    main()
