#!/usr/bin/env python3
"""生成「盔甲附加」模组的贴图资源。

产物（两套盔甲 + 模组图标）：
  - textures/item/{set}_{helmet,chestplate,leggings,boots}.png   物品图标 (16x16)
  - textures/models/armor/{set}_layer_1.png                      盔甲层 1 (头盔/胸甲/靴子, 64x32)
  - textures/models/armor/{set}_layer_2.png                      盔甲层 2 (护腿, 64x32)
  - icon.png                                                     模组图标 (128x128)

set 取值：addon（附加盔甲，冰钢蓝）、wool（羊毛盔甲，暖白毛线）、
iron_wool（铁羊毛盔甲，钢灰毛线，羊毛盔甲与铁盔甲的升级版本）。

用法: python generate_textures.py
"""

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

# ---- 附加盔甲：冰钢蓝 ----
ADDON_PALETTE = {
    "D": (36, 54, 80, 255),  # 描边
    "M": (94, 148, 202, 255),  # 主体
    "L": (156, 206, 246, 255),  # 高光
    "S": (56, 96, 142, 255),  # 阴影
}

# ---- 羊毛盔甲：暖白毛线 ----
WOOL_PALETTE = {
    "D": (118, 114, 106, 255),
    "M": (233, 231, 223, 255),
    "L": (253, 252, 249, 255),
    "S": (198, 195, 185, 255),
}

# ---- 铁羊毛盔甲：羊毛盔甲的编织质感 + 铁甲的钢灰配色 ----
IRON_WOOL_PALETTE = {
    "D": (62, 66, 74, 255),
    "M": (176, 180, 188, 255),
    "L": (226, 229, 234, 255),
    "S": (128, 132, 140, 255),
}

# ---- 金羊毛盔甲：羊毛盔甲的编织质感 + 金甲的暖金配色 ----
GOLD_WOOL_PALETTE = {
    "D": (118, 78, 14, 255),
    "M": (236, 189, 63, 255),
    "L": (253, 235, 150, 255),
    "S": (188, 134, 30, 255),
}

# ---- 钻石羊毛盔甲：羊毛盔甲的编织质感 + 钻石甲的青蓝配色 ----
DIAMOND_WOOL_PALETTE = {
    "D": (24, 102, 108, 255),
    "M": (92, 224, 216, 255),
    "L": (188, 248, 243, 255),
    "S": (52, 164, 162, 255),
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
    "addon": (ADDON_PALETTE, False),
    "wool": (WOOL_PALETTE, True),
    "iron_wool": (IRON_WOOL_PALETTE, True),
    "gold_wool": (GOLD_WOOL_PALETTE, True),
    "diamond_wool": (DIAMOND_WOOL_PALETTE, True),
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
    item_dir = ASSETS / "textures" / "item"
    armor_dir = ASSETS / "textures" / "models" / "armor"
    item_dir.mkdir(parents=True, exist_ok=True)
    armor_dir.mkdir(parents=True, exist_ok=True)

    first_helmet = None

    for set_name, (palette, textured) in SETS.items():
        for piece, rows in SHAPES.items():
            img = art_to_image(rows, palette)
            if textured:
                weave(img, palette)
            img.save(item_dir / f"{set_name}_{piece}.png")
            if first_helmet is None and piece == "helmet":
                first_helmet = img
        print(f"  [{set_name}] 物品图标 4 个")

        layer_1 = build_layer_1(palette)
        layer_2 = build_layer_2(palette)
        if textured:
            weave(layer_1, palette)
            weave(layer_2, palette)
        layer_1.save(armor_dir / f"{set_name}_layer_1.png")
        layer_2.save(armor_dir / f"{set_name}_layer_2.png")
        print(f"  [{set_name}] 盔甲层 1 / 2")

    assert first_helmet is not None
    first_helmet.resize((128, 128), Image.NEAREST).save(ASSETS / "icon.png")
    print("  模组图标 icon.png")
    print(f"\n贴图已输出到: {ASSETS}")


if __name__ == "__main__":
    main()
