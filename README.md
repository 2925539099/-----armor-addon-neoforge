# 盔甲附加 (Armor Addon) — NeoForge 版

Minecraft **1.21.1** / **NeoForge** 模组，新增六套共 24 件可合成的盔甲。

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

#### 羊毛靴子的额外特性

羊毛靴子（`armor_addon:wool_boots`）在羊毛护甲属性之外还有两个特性，**仅此一件**：

- **可以在细雪上行走** —— 与原版皮革靴子一样不会陷进细雪
  （覆盖 NeoForge 在 `Item` 上的 `canWalkOnPowderedSnow` 扩展方法，
  其默认实现只对 `minecraft:leather_boots` 返回 true）。
- **移动时不会被幽匿感测体 / 坚守者探测到** —— 屏蔽下列三种振动，
  与原版**蹲行**屏蔽的那组完全一致（`GameEventTags.IGNORE_VIBRATIONS_SNEAKING`）：

  | 振动 | 对应行为 |
  | --- | --- |
  | `STEP` | 行走 / 奔跑的脚步 |
  | `SWIM` | 游泳 |
  | `HIT_GROUND` | 跳跃落地 |

  实现方式是监听 NeoForge 的 `VanillaGameEvent` 并取消以上事件
  （钩子在 `ServerLevel#gameEvent`，取消后振动不会派发给附近监听者）。
  放置方块、攻击、交互等**其它**动作仍会正常发出振动。

### 铁质内衬甲（材料 `armor_addon:iron_wool`）

羊毛盔甲与原版铁盔甲的升级版本：在工作台里用**对应的铁盔甲 + 羊毛盔甲 + 1 个锁链**合成，
继承铁盔甲的附魔与自定义名称，并把铁盔甲已损失的耐久原样同步到新盔甲上。
本套盔甲**不参与染色**，使用单一固定材质（外壳为铁、内衬为羊毛）。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 铁质内衬头盔 | `armor_addon:iron_wool_helmet` | 3 | 198 |
| 铁质内衬胸甲 | `armor_addon:iron_wool_chestplate` | 7 | 288 |
| 铁质内衬护腿 | `armor_addon:iron_wool_leggings` | 6 | 270 |
| 铁质内衬靴子 | `armor_addon:iron_wool_boots` | 3 | 234 |

与原版铁盔甲的对照：

| 属性 | 铁盔甲 | 铁质内衬甲 |
| --- | --- | --- |
| 防御点 | 2 / 6 / 5 / 2 | **3 / 7 / 6 / 3（各 +1）** |
| 护甲韧性 | 0.0 | **2.0（继承羊毛盔甲）** |
| 耐久 | 165 / 240 / 225 / 195 | **198 / 288 / 270 / 234（+20%）** |
| 附魔亲和度 | 9 | 9（相同） |
| 击退抗性 | 0.0 | 0.0（相同） |
| 修复材料 | 铁锭 | 铁锭（相同） |
| 耐久倍率 | 15 | **18** |

### 金质内衬甲（材料 `armor_addon:gold_wool`）

金盔甲版的「内衬甲」，规则与铁质内衬甲完全一致：工作台里用**对应的金盔甲 + 羊毛盔甲 +
1 个锁链**合成，继承金盔甲的附魔与自定义名称并同步已损失的耐久，同样**不可染色**。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 金质内衬头盔 | `armor_addon:gold_wool_helmet` | 3 | 92 |
| 金质内衬胸甲 | `armor_addon:gold_wool_chestplate` | 6 | 134 |
| 金质内衬护腿 | `armor_addon:gold_wool_leggings` | 4 | 126 |
| 金质内衬靴子 | `armor_addon:gold_wool_boots` | 2 | 109 |

与原版金盔甲的对照：

| 属性 | 金盔甲 | 金质内衬甲 |
| --- | --- | --- |
| 防御点 | 2 / 5 / 3 / 1 | **3 / 6 / 4 / 2（各 +1）** |
| 护甲韧性 | 0.0 | **2.0（继承羊毛盔甲）** |
| 耐久 | 77 / 112 / 105 / 91 | **92 / 134 / 126 / 109（+20%）** |
| 附魔亲和度 | 25 | 25（相同） |
| 击退抗性 | 0.0 | 0.0（相同） |
| 修复材料 | 金锭 | 金锭（相同） |

> 金盔甲的耐久倍率是 7，×1.2 = 8.4 不是整数，所以金质内衬甲不按倍率换算，
> 而是逐件直接写入「金盔甲耐久 +20%」的具体数值。

### 钻石内衬甲（材料 `armor_addon:diamond_wool`）

钻石盔甲版的「内衬甲」，规则与铁 / 金质内衬甲相同，**唯一区别是韧性**：

> 原版钻石盔甲本身就带 2.0 韧性，所以本套按「钻石甲的韧性 + 羊毛盔甲的韧性」
> 叠加，得到 **4.0**。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 钻石内衬头盔 | `armor_addon:diamond_wool_helmet` | 4 | 435 |
| 钻石内衬胸甲 | `armor_addon:diamond_wool_chestplate` | 9 | 633 |
| 钻石内衬护腿 | `armor_addon:diamond_wool_leggings` | 7 | 594 |
| 钻石内衬靴子 | `armor_addon:diamond_wool_boots` | 4 | 514 |

与原版钻石盔甲的对照：

| 属性 | 钻石盔甲 | 钻石内衬甲 |
| --- | --- | --- |
| 防御点 | 3 / 8 / 6 / 3 | **4 / 9 / 7 / 4（各 +1）** |
| 护甲韧性 | 2.0 | **4.0（钻石 2.0 + 羊毛 2.0）** |
| 耐久 | 363 / 528 / 495 / 429 | **435 / 633 / 594 / 514（+20%）** |
| 附魔亲和度 | 10 | 10（相同） |
| 击退抗性 | 0.0 | 0.0（相同） |
| 修复材料 | 钻石 | 钻石（相同） |

> 钻石甲的耐久倍率是 33，×1.2 = 39.6 不是整数，因此与金质内衬甲一样逐件写入具体值。

### 合金内衬甲（材料 `armor_addon:netherite_wool`）

下界合金盔甲版的「内衬甲」，沿用钻石内衬甲的逻辑（韧性 = 基甲韧性 + 羊毛韧性），
所以韧性为 **3.0 + 2.0 = 5.0**。这也是目前唯一带击退抗性的一套（沿用下界合金的 0.1）。

| 物品 | ID | 防御 | 耐久 |
| --- | --- | --- | --- |
| 合金内衬头盔 | `armor_addon:netherite_wool_helmet` | 4 | 488 |
| 合金内衬胸甲 | `armor_addon:netherite_wool_chestplate` | 9 | 710 |
| 合金内衬护腿 | `armor_addon:netherite_wool_leggings` | 7 | 666 |
| 合金内衬靴子 | `armor_addon:netherite_wool_boots` | 4 | 577 |

与原版下界合金盔甲的对照：

| 属性 | 下界合金 | 合金内衬甲 |
| --- | --- | --- |
| 防御点 | 3 / 8 / 6 / 3 | **4 / 9 / 7 / 4（各 +1）** |
| 护甲韧性 | 3.0 | **5.0（下界合金 3.0 + 羊毛 2.0）** |
| 击退抗性 | 0.1 | **0.1（相同）** |
| 耐久 | 407 / 592 / 555 / 481 | **488 / 710 / 666 / 577（+20%）** |
| 附魔亲和度 | 15 | 15（相同） |
| 修复材料 | 下界合金锭 | 下界合金锭（相同） |

另外与原版下界合金盔甲一致**带抗火**（物品不会被火烧毁）——
下界合金倍率 37，×1.2 = 44.4 不是整数，所以耐久同样逐件写入具体值。

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

**铁质内衬甲** —— 在**工作台**里（无序摆放）放入**对应的铁盔甲 + 对应的羊毛盔甲 + 1 个锁链**：

| 铁盔甲 | 羊毛盔甲 | 材料 | 产物 |
| --- | --- | --- | --- |
| 铁头盔 | 羊毛头盔 | 锁链 | 铁质内衬头盔 |
| 铁胸甲 | 羊毛胸甲 | 锁链 | 铁质内衬胸甲 |
| 铁护腿 | 羊毛护腿 | 锁链 | 铁质内衬护腿 |
| 铁靴子 | 羊毛靴子 | 锁链 | 铁质内衬靴子 |

这 4 条是普通无序配方，会显示在 **JEI** 与配方书中。合成时继承铁盔甲的附魔与
自定义名称，并把铁盔甲**已损失的耐久**原样同步到产物上
（例如损失了 100 点耐久的铁胸甲，产出的铁质内衬胸甲同样损失 100 点耐久）。

**金质内衬甲** —— 同理，在工作台里无序放入**对应的金盔甲 + 对应的羊毛盔甲 + 1 个锁链**：

| 金盔甲 | 羊毛盔甲 | 材料 | 产物 |
| --- | --- | --- | --- |
| 金头盔 | 羊毛头盔 | 锁链 | 金质内衬头盔 |
| 金胸甲 | 羊毛胸甲 | 锁链 | 金质内衬胸甲 |
| 金护腿 | 羊毛护腿 | 锁链 | 金质内衬护腿 |
| 金靴子 | 羊毛靴子 | 锁链 | 金质内衬靴子 |

同样显示在 JEI 与配方书中，并继承金盔甲的附魔 / 名称与已损失的耐久。

**钻石内衬甲** —— 同理，在工作台里无序放入**对应的钻石盔甲 + 对应的羊毛盔甲 + 1 个锁链**：

| 钻石盔甲 | 羊毛盔甲 | 材料 | 产物 |
| --- | --- | --- | --- |
| 钻石头盔 | 羊毛头盔 | 锁链 | 钻石内衬头盔 |
| 钻石胸甲 | 羊毛胸甲 | 锁链 | 钻石内衬胸甲 |
| 钻石护腿 | 羊毛护腿 | 锁链 | 钻石内衬护腿 |
| 钻石靴子 | 羊毛靴子 | 锁链 | 钻石内衬靴子 |

同样显示在 JEI 与配方书中，并继承钻石盔甲的附魔 / 名称与已损失的耐久。

**合金内衬甲** —— 同理，在工作台里无序放入**对应的下界合金盔甲 + 对应的羊毛盔甲 + 1 个锁链**：

| 下界合金盔甲 | 羊毛盔甲 | 材料 | 产物 |
| --- | --- | --- | --- |
| 下界合金头盔 | 羊毛头盔 | 锁链 | 合金内衬头盔 |
| 下界合金胸甲 | 羊毛胸甲 | 锁链 | 合金内衬胸甲 |
| 下界合金护腿 | 羊毛护腿 | 锁链 | 合金内衬护腿 |
| 下界合金靴子 | 羊毛靴子 | 锁链 | 合金内衬靴子 |

同样显示在 JEI 与配方书中，并继承下界合金盔甲的附魔 / 名称与已损失的耐久。

六套盔甲都会出现在创造模式「战斗」物品栏。

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
Loaded 1314 recipes        # 原版 1290 + 本模组 24 条（六套盔甲各 4 条）
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
    │   └── item/
    │       ├── ModArmorMaterials.java               盔甲材料 DeferredRegister
    │       ├── ModItems.java                        盔甲物品 DeferredRegister
    │       ├── WoolBootsItem.java                   羊毛靴子（可在细雪上行走）
    │       ├── WoolBootsEvents.java                 羊毛靴子移动静音
    │       └── ModCraftingEvents.java               内衬甲合成继承附魔/名称/损耗耐久
    └── resources/
        ├── META-INF/neoforge.mods.toml
        ├── assets/armor_addon/                      贴图、模型、语言文件
        └── data/armor_addon/recipe/                 合成配方
```

## 重新生成贴图

```bash
python tools/generate_textures.py
```

## 与 MCreator 协作（本地开发用）

本机装了 MCreator，工作区在 `<MCreator-workspace>
为了能用 MCreator 的贴图编辑器直接改本模组的贴图，该工作区的两个贴图目录被做成了
指向本项目的 **目录联接（junction）**：

| MCreator 侧 | 指向 |
| --- | --- |
| `...\assets\armor_attachment\textures\item` | `src/main/resources/assets/armor_addon/textures/item` |
| `...\assets\armor_attachment\textures\models\armor` | `src/main/resources/assets/armor_addon/textures/models/armor` |

因为是联接而不是复制，**两边是同一份文件**：在 MCreator 里画完保存，项目里的 PNG
立刻就变了，不需要任何同步操作；反过来在这里新增贴图，MCreator 也马上能看到。

MCreator 的盔甲元素按 `<registry_name>_layer_1.png` / `<registry_name>_layer_2.png`
找穿戴贴图、按 `<name>.png` 找物品图标，所以只要把元素的 registry name 取成与套装名
一致（`addon` / `wool` / `iron_wool` / `gold_wool` / `diamond_wool` / `netherite_wool`），
它就会直接命中本模组已有的贴图。该元素还支持自定义 3D 模型
（`helmetModelName` / `bodyModelName` / `leggingsModelName` / `bootsModelName`
配合 `*ModelPart` 映射）。

### 注意：贴图目录里混有 MCreator 的旧贴图

建立联接时，MCreator 工作区原有的那些贴图（`ymkj_*`、`ce_shi__*`、`ym.png`、
`tokui.png`，共 14 个）被移进了本项目的贴图目录——否则它们会随目录一起消失，
MCreator 里那两个旧盔甲元素就会失效。

这些文件**不属于本模组**，因此：

- 已写入 `.gitignore`，不纳入版本控制；
- 已在 `build.gradle` 的 `processResources` 中排除，不会混进成品 jar。

### 配套脚本（在工作区根目录，即本项目的上一级）

| 脚本 | 用途 |
| --- | --- |
| `sync-from-mcreator.bat` | 把 MCreator 侧改过的贴图同步回项目（有了联接后其实用不上，留作保险）；加 `-Build` 可顺带重新构建 |
| `restore-build-config.bat` | 万一 MCreator 覆盖了 `build.gradle` / `gradle.properties`，一键还原镜像构建配置并校验 |

> `.ps1` 需要 UTF-8 BOM 才能被 Windows PowerShell 5.1 正确解析，`.bat` 里已用
> `-ExecutionPolicy Bypass` 绕过脚本执行策略，双击即可。

### 如何解除联接

```powershell
# 先删联接（不会影响项目里的真实文件），再把旧贴图从项目里清掉
Remove-Item "<MCreator工作区>\src\main\resources\assets\armor_attachment\textures\item" -Force
Remove-Item "<MCreator工作区>\src\main\resources\assets\armor_attachment\textures\models\armor" -Force
```

改完记得把 `.gitignore` 与 `build.gradle` 里的排除规则一并去掉。

## 许可

MIT
