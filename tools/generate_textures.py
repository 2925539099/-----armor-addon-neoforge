#!/usr/bin/env python3
"""补齐缺失的贴图，并刷新模组图标。

⚠️ 历史说明（重要）
-------------------
本脚本原本用 ASCII 像素画生成全部六套盔甲贴图。但现在六套贴图都以
**用户手绘的 iron_lining 为母版**，由 `recolor_handdrawn.py` 重新上色生成，
风格远好于本脚本的 4 色平面图。

因此本脚本默认**绝不覆盖已存在的贴图**：
  - 只补齐缺失的文件（正常情况下一个都不会生成）；
  - 总是用当前的 `iron_lining_helmet.png` 刷新 `icon.png`。

想强行用旧的 ASCII 模板重建全部贴图，需要显式加 `--force`
—— 那会覆盖掉所有手绘版本，只会用于把仓库恢复到早期状态。

用法:
    python generate_textures.py            # 安全：补齐缺失 + 刷新图标
    python generate_textures.py --force    # 危险：用旧模板重建全部贴图
"""

import argparse
from pathlib import Path

from PIL import Image

ASSETS = (
    Path(__file__).resolve().parent.parent
    / "src"
    / "main"
    / "resources"
    / "assets"
    / "armor_addon"
)

TRANSPARENT = (0, 0, 0, 0)

# ---- 羊毛盔甲：暖白毛线 ----
WOOL_PALETTE = {
    "D": (118, 114, 106, 255),
    "M": (233, 231, 223, 255),
    "L": (253, 252, 249, 255),
    "S": (198, 195, 185, 255),
}

# ---- 铁质内衬盔甲：羊毛盔甲的编织质感 + 铁甲的钢灰配色 ----
IRON_LINING_PALETTE = {
    "D": (62, 66, 74, 255),
    "M": (176, 180, 188, 255),
    "L": (226, 229, 234, 255),
    "S": (128, 132, 140, 255),
}

# ---- 金质内衬盔甲：羊毛盔甲的编织质感 + 金甲的暖金配色 ----
GOLD_LINING_PALETTE = {
    "D": (118, 78, 14, 255),
    "M": (236, 189, 63, 255),
    "L": (253, 235, 150, 255),
    "S": (188, 134, 30, 255),
}

# ---- 钻石内衬盔甲：羊毛盔甲的编织质感 + 钻石甲的青蓝配色 ----
DIAMOND_LINING_PALETTE = {
    "D": (24, 102, 108, 255),
    "M": (92, 224, 216, 255),
    "L": (188, 248, 243, 255),
    "S": (52, 164, 162, 255),
}

# ---- 合金内衬甲：羊毛盔甲的编织质感 + 下界合金的暗紫灰配色 ----
ALLOY_LINING_PALETTE = {
    "D": (46, 38, 44, 255),
    "M": (102, 92, 100, 255),
    "L": (162, 152, 160, 255),
    "S": (68, 60, 66, 255),
}

ICON_SIZE = 16

# ---- 16x16 物品图标底稿（'.' 透明）；两套盔甲共用轮廓，仅换色与质感 ----
SHAPES = {
    "helmet": [
        "................",
        "................",
        "....DDDDDDDD....",
        "...DMMMMMMMMD...",
        "..DMMLLLLLLMMD..",
        "..DMMLLLLLLMMD..",
        "..DMMMMMMMMMMD..",
        "..DMMDDDDDDMMD..",
        "..DMMD....DMMD..",
        "..DMMD....DMMD..",
        "..DMMDDDDDDMMD..",
        "..DMMMMMMMMMMD..",
        "..DDMMMMMMMMDD..",
        "...DDSSSSSSDD...",
        "....DDDDDDDD....",
        "................",
    ],
    "chestplate": [
        "................",
        "..DDD......DDD..",
        ".DMMMD....DMMMD.",
        ".DMMMD....DMMMD.",
        ".DMMMMMMMMMMMMD.",
        ".DMMMMMMMMMMMMD.",
        ".DMMLLLLLLLLMMD.",
        ".DMMLMMMMMMLMMD.",
        ".DMMMMMMMMMMMMD.",
        ".DMMMMMMMMMMMMD.",
        "..DMMMMMMMMMMD..",
        "..DMMMMMMMMMMD..",
        "...DMMMMMMMMD...",
        "....DDMMMMDD....",
        ".....DDDDDD.....",
        "................",
    ],
    "leggings": [
        "................",
        "..DDDDDDDDDDDD..",
        ".DMMMMMMMMMMMMD.",
        ".DMMMMMMMMMMMMD.",
        ".DMMDDDDDDDDMMD.",
        ".DMMD......DMMD.",
        ".DMMD......DMMD.",
        ".DMMD......DMMD.",
        ".DMMD......DMMD.",
        ".DMMD......DMMD.",
        ".DMMD......DMMD.",
        ".DDDD......DDDD.",
        "................",
        "................",
        "................",
        "................",
    ],
    "boots": [
        "................",
        "................",
        "................",
        "..DDDDD..DDDDD..",
        "..DMMMD..DMMMD..",
        "..DMMMD..DMMMD..",
        "..DMMMD..DMMMD..",
        "..DMMMD..DMMMD..",
        "..DMMMD..DMMMD..",
        "..DMMMD..DMMMD..",
        ".DMMMMD..DMMMMD.",
        ".DMMMMD..DMMMMD.",
        ".DDDDDD..DDDDDD.",
        "................",
        "................",
        "................",
    ],
}

# set 名 -> (调色板, 是否加毛线质感)
SETS = {
    "wool": (WOOL_PALETTE, True),
    "iron_lining": (IRON_LINING_PALETTE, True),
    "gold_lining": (GOLD_LINING_PALETTE, True),
    "diamond_lining": (DIAMOND_LINING_PALETTE, True),
    "alloy_lining": (ALLOY_LINING_PALETTE, True),
}


def art_to_image(rows, palette, size=ICON_SIZE):
    """把 ASCII 像素画转成 RGBA 图，行/列不足或超出的部分自动规整。"""
    normalized = list(rows)[:size]
    normalized += ["." * size] * (size - len(normalized))

    img = Image.new("RGBA", (size, size), TRANSPARENT)
    px = img.load()

    for y, row in enumerate(normalized):
        row = (row + "." * size)[:size]
        for x, ch in enumerate(row):
            px[x, y] = palette.get(ch, TRANSPARENT)

    return img


def weave(img, palette):
    """给主体像素点缀明暗，做出毛线编织的质感（只改动主体色像素）。"""
    main, light, shade = palette["M"], palette["L"], palette["S"]
    px = img.load()

    for y in range(img.height):
        for x in range(img.width):
            if px[x, y] == main and (x * 3 + y * 5) % 7 == 0:
                px[x, y] = shade if (x + y) % 2 == 0 else light

    return img


def rect(img, x, y, w, h, color):
    """在图上填充一个矩形（自动裁剪到边界内）。"""
    x0, y0 = max(x, 0), max(y, 0)
    x1, y1 = min(x + w, img.width), min(y + h, img.height)
    if x1 <= x0 or y1 <= y0:
        return
    for j in range(y0, y1):
        for i in range(x0, x1):
            img.putpixel((i, j), color)


def face(img, x, y, w, h, palette):
    """绘制一个带高光、阴影与描边的贴图面。"""
    rect(img, x, y, w, h, palette["M"])
    rect(img, x + 1, y + 1, w - 2, 1, palette["L"])  # 顶部高光
    rect(img, x + 1, y + h - 2, w - 2, 1, palette["S"])  # 底部阴影
    rect(img, x, y, w, 1, palette["D"])  # 上描边
    rect(img, x, y + h - 1, w, 1, palette["D"])  # 下描边
    rect(img, x, y, 1, h, palette["D"])  # 左描边
    rect(img, x + w - 1, y, 1, h, palette["D"])  # 右描边


def cube(img, u, v, w, h, d, palette):
    """在 (u, v) 处绘制 w x h x d 立方体的展开贴图。"""
    face(img, u + d, v, w, d, palette)  # 顶面
    face(img, u + d + w, v, w, d, palette)  # 底面
    face(img, u, v + d, d, h, palette)  # 右面
    face(img, u + d, v + d, w, h, palette)  # 正面
    face(img, u + d + w, v + d, d, h, palette)  # 左面
    face(img, u + d + w + d, v + d, w, h, palette)  # 背面


def build_layer_1(palette):
    """盔甲层 1：头盔 + 胸甲 + 护臂 + 靴子。"""
    img = Image.new("RGBA", (64, 32), TRANSPARENT)

    cube(img, 0, 0, 8, 8, 8, palette)  # 头盔（头部 8x8x8）
    cube(img, 16, 16, 8, 12, 4, palette)  # 胸甲（身体 8x12x4）
    cube(img, 40, 16, 4, 12, 4, palette)  # 护臂（手臂 4x12x4）

    # 靴子：只覆盖腿部贴图的下半部分（腿部展开在 (0,16)，4x12x4）
    for x in (0, 4, 8, 12):  # 右 / 前 / 左 / 后 四个侧面
        face(img, x, 27, 4, 5, palette)
    face(img, 8, 16, 4, 4, palette)  # 鞋底

    return img


def build_layer_2(palette):
    """盔甲层 2：护腿（整条腿 + 腰部）。"""
    img = Image.new("RGBA", (64, 32), TRANSPARENT)

    cube(img, 0, 16, 4, 12, 4, palette)  # 护腿（腿部 4x12x4）

    # 腰部：身体贴图的下半部分（身体展开在 (16,16)，8x12x4）
    for x, w in ((16, 4), (20, 8), (28, 4), (32, 8)):
        face(img, x, 26, w, 6, palette)

    return img


def main():
    ap = argparse.ArgumentParser(description="补齐缺失贴图并刷新模组图标")
    ap.add_argument("--force", action="store_true",
                    help="用旧的 ASCII 模板覆盖重建全部贴图（会毁掉手绘版本）")
    args = ap.parse_args()

    item_dir = ASSETS / "textures" / "item"
    armor_dir = ASSETS / "textures" / "models" / "armor"
    item_dir.mkdir(parents=True, exist_ok=True)
    armor_dir.mkdir(parents=True, exist_ok=True)

    if not args.force:
        print("安全模式：不覆盖已存在的贴图（加 --force 才会用旧模板重建）\n")

    made = 0
    skipped = 0

    for set_name, (palette, textured) in SETS.items():
        for piece, rows in SHAPES.items():
            dst = item_dir / f"{set_name}_{piece}.png"
            if dst.exists() and not args.force:
                skipped += 1
                continue
            img = art_to_image(rows, palette)
            if textured:
                weave(img, palette)
            img.save(dst)
            made += 1

        for layer_no, builder in ((1, build_layer_1), (2, build_layer_2)):
            dst = armor_dir / f"{set_name}_layer_{layer_no}.png"
            if dst.exists() and not args.force:
                skipped += 1
                continue
            layer = builder(palette)
            if textured:
                weave(layer, palette)
            layer.save(dst)
            made += 1

    print(f"新生成 {made} 个贴图，跳过已存在 {skipped} 个")

    # 模组图标始终用「当前」的 iron_lining_helmet 刷新，而不是本次新生成的
    helmet = item_dir / "iron_lining_helmet.png"
    if helmet.exists():
        Image.open(helmet).resize((128, 128), Image.NEAREST).save(ASSETS / "icon.png")
        print("模组图标 icon.png 已按当前 iron_lining_helmet.png 刷新")
    else:
        print("!! 找不到 iron_lining_helmet.png，未刷新模组图标")

    print(f"\n贴图目录: {ASSETS}")


if __name__ == "__main__":
    main()
