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
	/** 附加盔甲的耐久度倍率（钻石为 33）。 */
	public static final int ADDON_DURABILITY_MULTIPLIER = 33;

	/** 羊毛盔甲的耐久度倍率：皮革为 5，这里取两倍。 */
	public static final int WOOL_DURABILITY_MULTIPLIER = 10;

	/**
	 * 铁羊毛盔甲的耐久度倍率：原版铁盔甲为 15，此处 18 正好是 +20%
	 * （头盔 198 / 胸甲 288 / 护腿 270 / 靴子 234）。
	 */
	public static final int IRON_WOOL_DURABILITY_MULTIPLIER = 18;

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArmorAddon.MOD_ID);

	// ---- 附加盔甲 ----
	public static final DeferredItem<Item> ADDON_HELMET =
			registerArmor("addon_helmet", ModArmorMaterials.ADDON, ArmorItem.Type.HELMET, ADDON_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> ADDON_CHESTPLATE =
			registerArmor("addon_chestplate", ModArmorMaterials.ADDON, ArmorItem.Type.CHESTPLATE, ADDON_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> ADDON_LEGGINGS =
			registerArmor("addon_leggings", ModArmorMaterials.ADDON, ArmorItem.Type.LEGGINGS, ADDON_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> ADDON_BOOTS =
			registerArmor("addon_boots", ModArmorMaterials.ADDON, ArmorItem.Type.BOOTS, ADDON_DURABILITY_MULTIPLIER);

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

	// ---- 铁羊毛盔甲（羊毛盔甲 + 铁盔甲的升级版本）----
	public static final DeferredItem<Item> IRON_WOOL_HELMET =
			registerArmor("iron_wool_helmet", ModArmorMaterials.IRON_WOOL, ArmorItem.Type.HELMET, IRON_WOOL_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_WOOL_CHESTPLATE =
			registerArmor("iron_wool_chestplate", ModArmorMaterials.IRON_WOOL, ArmorItem.Type.CHESTPLATE, IRON_WOOL_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_WOOL_LEGGINGS =
			registerArmor("iron_wool_leggings", ModArmorMaterials.IRON_WOOL, ArmorItem.Type.LEGGINGS, IRON_WOOL_DURABILITY_MULTIPLIER);
	public static final DeferredItem<Item> IRON_WOOL_BOOTS =
			registerArmor("iron_wool_boots", ModArmorMaterials.IRON_WOOL, ArmorItem.Type.BOOTS, IRON_WOOL_DURABILITY_MULTIPLIER);

	// ---- 金质内衬甲（羊毛盔甲 + 金盔甲的升级版本）----
	// 金盔甲的耐久倍率是 7，×1.2 = 8.4 不是整数，所以这里逐件直接写
	// 「金盔甲耐久 +20%」的具体值：
	//   头盔 11×7=77  → 92
	//   胸甲 16×7=112 → 134
	//   护腿 15×7=105 → 126
	//   靴子 13×7=91  → 109
	public static final DeferredItem<Item> GOLD_WOOL_HELMET =
			registerArmorWithDurability("gold_wool_helmet", ModArmorMaterials.GOLD_WOOL,
					ArmorItem.Type.HELMET, 92);
	public static final DeferredItem<Item> GOLD_WOOL_CHESTPLATE =
			registerArmorWithDurability("gold_wool_chestplate", ModArmorMaterials.GOLD_WOOL,
					ArmorItem.Type.CHESTPLATE, 134);
	public static final DeferredItem<Item> GOLD_WOOL_LEGGINGS =
			registerArmorWithDurability("gold_wool_leggings", ModArmorMaterials.GOLD_WOOL,
					ArmorItem.Type.LEGGINGS, 126);
	public static final DeferredItem<Item> GOLD_WOOL_BOOTS =
			registerArmorWithDurability("gold_wool_boots", ModArmorMaterials.GOLD_WOOL,
					ArmorItem.Type.BOOTS, 109);

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
}
