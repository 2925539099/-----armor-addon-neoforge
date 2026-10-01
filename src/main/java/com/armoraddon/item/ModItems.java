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
	public static final DeferredItem<Item> WOOL_BOOTS =
			registerArmor("wool_boots", ModArmorMaterials.WOOL, ArmorItem.Type.BOOTS, WOOL_DURABILITY_MULTIPLIER);

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
}
