# 盔甲附加 (Armor Addon) — NeoForge 版

Minecraft **1.21.1** / **NeoForge** 模组，新增三套共 12 件可合成的盔甲。

Fabric 版见同级目录 `armor-addon/`。

## 内容

新增三套共 12 件盔甲。

### 附加盔甲（材料 `armor_addon:addon`）

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 附加头盔 | `armor_addon:addon_helmet` | 3 | 363 |
| 附加胸甲 | `armor_addon:addon_chestplate` | 8 | 528 |
| 附加护腿 | `armor_addon:addon_leggings` | 6 | 495 |
| 附加靴子 | `armor_addon:addon_boots` | 3 | 429 |

材料属性（与钻石盔甲同级，但附魔亲和度更高）：

- 防御点：3 / 8 / 6 / 3
- 韧性：2.0
- 击退抗性：0.1
- 附魔亲和度：15（钻石为 10，皮革为 15）
- 修复材料：铁锭
- 耐久倍率：33（同钻石）

### 羊毛盔甲（材料 `armor_addon:wool`）

防御与附魔完全对标原版皮革盔甲，但耐久翻倍，并且每件都带 2.0 韧性。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 羊毛头盔 | `armor_addon:wool_helmet` | 1 | 110 |
| 羊毛胸甲 | `armor_addon:wool_chestplate` | 3 | 160 |
| 羊毛护腿 | `armor_addon:wool_leggings` | 2 | 150 |
| 羊毛靴子 | `armor_addon:wool_boots` | 1 | 130 |

与原版皮革盔甲的对照：

| 属性 | 皮革 | 羊毛盔甲 |
| --- | --- | --- |
| 防御点 | 1 / 3 / 2 / 1 | 1 / 3 / 2 / 1（相同） |
| 附魔亲和度 | 15 | 15（相同） |
| 耐久倍率 | 5 | **10（两倍）** |
| 韧性 | 0.0 | **2.0（每件）** |
| 击退抗性 | 0.0 | 0.0（相同） |
| 修复材料 | 皮革 | **羊毛（`minecraft:wool` 标签，任意颜色）** |

### 铁羊毛盔甲（材料 `armor_addon:iron_wool`）

羊毛盔甲与原版铁盔甲的升级版本：在工作台里用**对应的铁盔甲 + 羊毛盔甲 + 1 个锁链**合成，
继承铁盔甲的附魔与自定义名称，并把铁盔甲已损失的耐久原样同步到新盔甲上。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 铁羊毛头盔 | `armor_addon:iron_wool_helmet` | 3 | 198 |
| 铁羊毛胸甲 | `armor_addon:iron_wool_chestplate` | 7 | 288 |
| 铁羊毛护腿 | `armor_addon:iron_wool_leggings` | 6 | 270 |
| 铁羊毛靴子 | `armor_addon:iron_wool_boots` | 3 | 234 |

与原版铁盔甲的对照：

| 属性 | 铁盔甲 | 铁羊毛盔甲 |
| --- | --- | --- |
| 防御点 | 2 / 6 / 5 / 2 | **3 / 7 / 6 / 3（各 +1）** |
| 护甲韧性 | 0.0 | **2.0（继承羊毛盔甲）** |
| 耐久 | 165 / 240 / 225 / 195 | **198 / 288 / 270 / 234（+20%）** |
| 附魔亲和度 | 9 | 9（相同） |
| 击退抗性 | 0.0 | 0.0（相同） |
| 修复材料 | 铁锭 | 铁锭（相同） |
| 耐久倍率 | 15 | **18** |

## 合成配方

所有配方都在**工作台**上合成，形状与原版盔甲一致。

**附加盔甲** —— `X` 为铁锭，`E` 为绿宝石（放在护心位置）：

| 部位 | 配方 |
| --- | --- |
| 头盔 | `XEX` / `X X` |
| 胸甲 | `X X` / `XEX` / `XXX` |
| 护腿 | `XXX` / `XEX` / `X X` |
| 靴子 | `X X` / `XEX` |

**羊毛盔甲** —— 全部使用 `minecraft:wool` 标签，因此**任意颜色的羊毛**都可以：

| 部位 | 配方 |
| --- | --- |
| 头盔 | `XXX` / `X X` |
| 胸甲 | `X X` / `XXX` / `XXX` |
| 护腿 | `XXX` / `X X` / `X X` |
| 靴子 | `X X` / `X X` |

**铁羊毛盔甲** —— 在**工作台**里（无序摆放）放入**对应的铁盔甲 + 对应的羊毛盔甲 + 1 个锁链**：

| 铁盔甲 | 羊毛盔甲 | 材料 | 产物 |
| --- | --- | --- | --- |
| 铁头盔 | 羊毛头盔 | 锁链 | 铁羊毛头盔 |
| 铁胸甲 | 羊毛胸甲 | 锁链 | 铁羊毛胸甲 |
| 铁护腿 | 羊毛护腿 | 锁链 | 铁羊毛护腿 |
| 铁靴子 | 羊毛靴子 | 锁链 | 铁羊毛靴子 |

合成时继承铁盔甲的附魔与自定义名称，并把铁盔甲**已损失的耐久**原样同步到产物上
（例如损失了 100 点耐久的铁胸甲，产出的铁羊毛胸甲同样损失 100 点耐久）。
该配方结果取决于输入，因此不会出现在配方书中（与原版皮革盔甲染色一致）。

三套盔甲都会出现在创造模式「战斗」物品栏。

## 安装

1. 安装 Minecraft 1.21.1 的 **NeoForge**（21.1.244 或更高的 21.1.x）。
2. 将 `build/libs/armor-addon-neoforge-1.0.0.jar` 放入 `.minecraft/mods/`。

**不需要**额外的前置模组。

## 构建

需要 **JDK 21**。

```bash
# Windows
gradlew.bat build

# Linux / macOS
./gradlew build
```

产物位于 `build/libs/armor-addon-neoforge-1.0.0.jar`。

> 本次构建使用的是工作区内的 Gradle 缓存目录 `../.gradle-home`。想复用它以免重新下载：
>
> ```powershell
> $env:GRADLE_USER_HOME = "<工作区>\.gradle-home"
> .\gradlew.bat build
> ```
>
> 不设置也可以，Gradle 会改用默认的 `~/.gradle` 并重新下载依赖。

### 关于下载源（重要）

本机网络环境下，Mojang 官方下载源与 NeoForge 官方仓库**均不可达**：

| 原始地址 | 状态 | 本项目使用的镜像 |
| --- | --- | --- |
| `libraries.minecraft.net` | 证书握手失败 | `bmclapi2.bangbang93.com/maven/` |
| `piston-meta.mojang.com` / `piston-data.mojang.com` | 证书握手失败 | 本地重写代理 → BMCLAPI |
| `resources.download.minecraft.net` | 证书握手失败 | `bmclapi2.bangbang93.com/assets/` |
| `maven.neoforged.net/mojang-meta` | 连接超时 | `neoforged.forgecdn.net/mojang-meta/` |
| `maven.neoforged.net/releases` | 连接超时 | `neoforged.forgecdn.net/releases/` |

ModDevGradle 把前三个仓库**硬编码**在插件里，因此 `gradle/mojang-mirror.gradle`
在构建时按名字把它们替换成镜像。

而 NeoForm Runtime（NFRT）会从 Minecraft 版本清单与版本 JSON **内容里**读取
client / server jar 的下载地址，这些地址无法用 Gradle 属性替换。所以该脚本还会启动
一个本地 HTTP 重写代理：把 JSON 里所有 Mojang 域名改写成 `http://127.0.0.1:<端口>/`，
再转发到 BMCLAPI，并通过 `neoFormRuntime.launcherManifestUrl` 提供给 NFRT。

- 代理使用**固定端口** `mirror_port`（默认 47821）：NFRT 会缓存改写过 URL 的版本 JSON，
  端口若每次都变，缓存里的地址就会失效导致下载失败。
- 若你的网络能直连 Mojang 与 `maven.neoforged.net`，删掉 `build.gradle` 里的
  `apply from: 'gradle/mojang-mirror.gradle'` 一行即可还原为官方源。

Gradle 发行包通过华为云镜像下载（见 `gradle/wrapper/gradle-wrapper.properties`）。

### 验证模组能否正常加载

```bash
gradlew.bat runServer
```

首次运行需要先在 `run/eula.txt` 中同意 Mojang EULA（本仓库已提供）。
日志里出现下面两行即说明盔甲与配方均已正确注册：

```
盔甲附加 1.0.0 (armor_addon)
Loaded 1299 recipes        # 原版 1290 + 本模组 9 条（4 附加 + 4 羊毛 + 1 升级配方）
```

## 项目结构

```
armor-addon-neoforge/
├── build.gradle                                    构建脚本（NeoForge ModDevGradle）
├── gradle.properties                               版本号与镜像配置
├── gradle/mojang-mirror.gradle                     仓库镜像 + Mojang 下载重写代理
├── tools/generate_textures.py                      贴图生成脚本（Pillow）
└── src/main/
    ├── java/com/armoraddon/
    │   ├── ArmorAddon.java                         主入口（@Mod）
    │   ├── item/
    │   │   ├── ModArmorMaterials.java               盔甲材料 DeferredRegister
    │   │   └── ModItems.java                        盔甲物品 DeferredRegister
    │   └── recipe/
    │       ├── ModRecipes.java                      配方序列化器注册
    │       └── ArmorUpgradeRecipe.java              铁羊毛盔甲升级配方（继承附魔/损耗耐久）
    └── resources/
        ├── META-INF/neoforge.mods.toml
        ├── assets/armor_addon/                      贴图、模型、语言文件
        └── data/armor_addon/recipe/                 合成配方
```

## 重新生成贴图

```bash
python tools/generate_textures.py
```

## 许可

MIT
