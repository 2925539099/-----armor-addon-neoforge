package com.armoraddon.item;

import com.armoraddon.ArmorAddon;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 「盔甲附加」的物品注册。
 */
public final class ModItems {
	/** 羊毛盔甲的耐久度倍率：皮革为 5，这里取两倍。 */
	public static final int WOOL_DURABILITY_MULTIPLIER = 10;

	/**
	 * 铁质内衬盔甲的耐久度倍率：原版铁盔甲为 15，此处 18 正好是 +20%
	 * （头盔 198 / 胸甲 288 / 护腿 270 / 靴子 234）。
	 */
	public static final int IRON_LINING_DURABILITY_MULTIPLIER = 18;

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArmorAddon.MOD_ID);

	// ---- 木制盔甲 ----
	// 耐久为铁盔甲的一半。铁盔甲是 11/16/15/13 × 15 = 165/240/225/195，
	// 除以 2 得 82.5/120/112.5/97.5，截断取整后逐件写具体值
	// （15 ÷ 2 = 7.5 不是整数，无法用倍率表达）。
	public static final DeferredItem<Item> WOOD_HELMET =
			registerArmorWithDurability("wood_helmet", ModArmorMaterials.WOOD,
					ArmorItem.Type.HELMET, 82);
	public static final DeferredItem<Item> WOOD_CHESTPLATE =
			registerArmorWithDurability("wood_chestplate", ModArmorMaterials.WOOD,
					ArmorItem.Type.CHESTPLATE, 120);
	public static final DeferredItem<Item> WOOD_LEGGINGS =
			registerArmorWithDurability("wood_leggings", ModArmorMaterials.WOOD,
					ArmorItem.Type.LEGGINGS, 112);
	public static final DeferredItem<Item> WOOD_BOOTS =
			registerArmorWithDurability("wood_boots", ModArmorMaterials.WOOD,
					ArmorItem.Type.BOOTS, 97);

	// ---- 羊毛盔甲 ----
	public static final DeferredItem<Item> WOOL_HELMET =
			registerArmor("wool_helmet", ModArmorMaterials.WOOL, ArmorItem.Type.HELMET, WOOL_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> WOOL_CHESTPLATE =
			registerArmor("wool_chestplate", ModArmorMaterials.WOOL, ArmorItem.Type.CHESTPLATE, WOOL_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> WOOL_LEGGINGS =
			registerArmor("wool_leggings", ModArmorMaterials.WOOL, ArmorItem.Type.LEGGINGS, WOOL_DURABILITY_MULTIPLIER);
	/**
	 * 羊毛靴子使用自定义物品类：可在细雪上行走，并且移动时不会被
	 * 幽匿感测体 / 坚守者探测到（见 {@link WoolBootsItem}、{@link WoolBootsEvents}）。
	 */
	public static final DeferredItem<Item> WOOL_BOOTS =
			ITEMS.register("wool_boots", () -> new WoolBootsItem(
					ModArmorMaterials.WOOL,
					new Item.Properties().durability(
							ArmorItem.Type.BOOTS.getDurability(WOOL_DURABILITY_MULTIPLIER))));

	// ---- 铁质内衬盔甲（羊毛盔甲 + 铁盔甲的升级版本）----
	public static final DeferredItem<Item> IRON_LINING_HELMET =
			registerArmor("iron_lining_helmet", ModArmorMaterials.IRON_LINING, ArmorItem.Type.HELMET, IRON_LINING_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_LINING_CHESTPLATE =
			registerArmor("iron_lining_chestplate", ModArmorMaterials.IRON_LINING, ArmorItem.Type.CHESTPLATE, IRON_LINING_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_LINING_LEGGINGS =
			registerArmor("iron_lining_leggings", ModArmorMaterials.IRON_LINING, ArmorItem.Type.LEGGINGS, IRON_LINING_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_LINING_BOOTS =
			registerArmor("iron_lining_boots", ModArmorMaterials.IRON_LINING, ArmorItem.Type.BOOTS, IRON_LINING_DURABILITY_MULTIPLIER);

	// ---- 金质内衬甲（羊毛盔甲 + 金盔甲的升级版本）----
	// 金盔甲的耐久倍率是 7，×1.2 = 8.4 不是整数，所以这里逐件直接写
	// 「金盔甲耐久 +20%」的具体值：
	//   头盔 11×7=77  → 92
	//   胸甲 16×7=112 → 134
	//   护腿 15×7=105 → 126
	//   靴子 13×7=91  → 109
	public static final DeferredItem<Item> GOLD_LINING_HELMET =
			registerArmorWithDurability("gold_lining_helmet", ModArmorMaterials.GOLD_LINING,
					ArmorItem.Type.HELMET, 92);
	public static final DeferredItem<Item> GOLD_LINING_CHESTPLATE =
			registerArmorWithDurability("gold_lining_chestplate", ModArmorMaterials.GOLD_LINING,
					ArmorItem.Type.CHESTPLATE, 134);
	public static final DeferredItem<Item> GOLD_LINING_LEGGINGS =
			registerArmorWithDurability("gold_lining_leggings", ModArmorMaterials.GOLD_LINING,
					ArmorItem.Type.LEGGINGS, 126);
	public static final DeferredItem<Item> GOLD_LINING_BOOTS =
			registerArmorWithDurability("gold_lining_boots", ModArmorMaterials.GOLD_LINING,
					ArmorItem.Type.BOOTS, 109);

	// ---- 钻石内衬甲（羊毛盔甲 + 钻石盔甲的升级版本）----
	// 钻石盔甲的耐久倍率是 33，×1.2 = 39.6 不是整数，同样逐件写具体值：
	//   头盔 11×33=363 → 435
	//   胸甲 16×33=528 → 633
	//   护腿 15×33=495 → 594
	//   靴子 13×33=429 → 514
	public static final DeferredItem<Item> DIAMOND_LINING_HELMET =
			registerArmorWithDurability("diamond_lining_helmet", ModArmorMaterials.DIAMOND_LINING,
					ArmorItem.Type.HELMET, 435);
	public static final DeferredItem<Item> DIAMOND_LINING_CHESTPLATE =
			registerArmorWithDurability("diamond_lining_chestplate", ModArmorMaterials.DIAMOND_LINING,
					ArmorItem.Type.CHESTPLATE, 633);
	public static final DeferredItem<Item> DIAMOND_LINING_LEGGINGS =
			registerArmorWithDurability("diamond_lining_leggings", ModArmorMaterials.DIAMOND_LINING,
					ArmorItem.Type.LEGGINGS, 594);
	public static final DeferredItem<Item> DIAMOND_LINING_BOOTS =
			registerArmorWithDurability("diamond_lining_boots", ModArmorMaterials.DIAMOND_LINING,
					ArmorItem.Type.BOOTS, 514);

	// ---- 合金内衬甲（羊毛盔甲 + 下界合金盔甲的升级版本）----
	// 下界合金盔甲的耐久倍率是 37，×1.2 = 44.4 不是整数，同样逐件写具体值：
	//   头盔 11×37=407 → 488
	//   胸甲 16×37=592 → 710
	//   护腿 15×37=555 → 666
	//   靴子 13×37=481 → 577
	// 另外与原版下界合金盔甲一致带抗火（物品不会被火烧毁）。
	public static final DeferredItem<Item> ALLOY_LINING_HELMET =
			registerFireResistantArmor("alloy_lining_helmet", ModArmorMaterials.ALLOY_LINING,
					ArmorItem.Type.HELMET, 488);
	public static final DeferredItem<Item> ALLOY_LINING_CHESTPLATE =
			registerFireResistantArmor("alloy_lining_chestplate", ModArmorMaterials.ALLOY_LINING,
					ArmorItem.Type.CHESTPLATE, 710);
	public static final DeferredItem<Item> ALLOY_LINING_LEGGINGS =
			registerFireResistantArmor("alloy_lining_leggings", ModArmorMaterials.ALLOY_LINING,
					ArmorItem.Type.LEGGINGS, 666);
	public static final DeferredItem<Item> ALLOY_LINING_BOOTS =
			registerFireResistantArmor("alloy_lining_boots", ModArmorMaterials.ALLOY_LINING,
					ArmorItem.Type.BOOTS, 577);

	private ModItems() {
	}

	public static void register(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
	}

	private static DeferredItem<Item> registerArmor(String name, Holder<ArmorMaterial> material,
													ArmorItem.Type type, int durabilityMultiplier) {
		return ITEMS.register(name, () -> new ArmorItem(
				material,
				type,
				new Item.Properties().durability(type.getDurability(durabilityMultiplier))));
	}

	/** 直接指定耐久数值（用于原版倍率 ×1.2 不是整数的情况）。 */
	private static DeferredItem<Item> registerArmorWithDurability(String name, Holder<ArmorMaterial> material,
																  ArmorItem.Type type, int durability) {
		return ITEMS.register(name, () -> new ArmorItem(
				material,
				type,
				new Item.Properties().durability(durability)));
	}

	/** 同上，但额外带抗火 —— 与原版下界合金盔甲一致。 */
	private static DeferredItem<Item> registerFireResistantArmor(String name, Holder<ArmorMaterial> material,
																 ArmorItem.Type type, int durability) {
		return ITEMS.register(name, () -> new ArmorItem(
				material,
				type,
				new Item.Properties().fireResistant().durability(durability)));
	}
}
