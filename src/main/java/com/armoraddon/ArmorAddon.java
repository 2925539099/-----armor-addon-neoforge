package com.armoraddon;

import com.armoraddon.item.ModArmorMaterials;
import com.armoraddon.item.ModCraftingEvents;
import com.armoraddon.item.ModItems;
import com.armoraddon.item.WoolBootsEvents;

import net.minecraft.world.item.CreativeModeTabs;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 盔甲附加（Armor Addon）—— NeoForge 版主入口。
 *
 * <p>注册六套盔甲材料（木制盔甲、羊毛盔甲、铁质内衬甲、金质内衬甲、钻石内衬甲、合金内衬甲）
 * 与对应的二十四个盔甲部件，并把它们加入创造模式「战斗」物品栏。</p>
 */
@Mod(ArmorAddon.MOD_ID)
public final class ArmorAddon {
	public static final String MOD_ID = "armor_addon";
	public static final Logger LOGGER = LoggerFactory.getLogger("Armor Addon");

	public ArmorAddon(IEventBus modEventBus, ModContainer modContainer) {
		// 盔甲材料必须先于使用它的盔甲物品注册。
		ModArmorMaterials.register(modEventBus);
		ModItems.register(modEventBus);

		modEventBus.addListener(this::addCreativeTabItems);
		// 铁质内衬甲合成时继承铁盔甲附魔 / 名称并同步损耗耐久。
		NeoForge.EVENT_BUS.addListener(ModCraftingEvents::onItemCrafted);
		// 羊毛靴子：移动时不被幽匿感测体 / 坚守者探测到。
		NeoForge.EVENT_BUS.addListener(WoolBootsEvents::onVanillaGameEvent);

		LOGGER.info("[盔甲附加] NeoForge 版模组已加载，木制 / 羊毛盔甲与铁质 / 金质 / 钻石 / 合金内衬甲已注册。");
	}

	private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			// 锻造模板放在最前，它是升级配方的入口。
			event.accept(ModItems.ARMOR_UPGRADE_TEMPLATE);

			event.accept(ModItems.WOOD_HELMET);
			event.accept(ModItems.WOOD_CHESTPLATE);
			event.accept(ModItems.WOOD_LEGGINGS);
			event.accept(ModItems.WOOD_BOOTS);

			event.accept(ModItems.WOOL_HELMET);
			event.accept(ModItems.WOOL_CHESTPLATE);
			event.accept(ModItems.WOOL_LEGGINGS);
			event.accept(ModItems.WOOL_BOOTS);

			event.accept(ModItems.IRON_LINING_HELMET);
			event.accept(ModItems.IRON_LINING_CHESTPLATE);
			event.accept(ModItems.IRON_LINING_LEGGINGS);
			event.accept(ModItems.IRON_LINING_BOOTS);

			event.accept(ModItems.GOLD_LINING_HELMET);
			event.accept(ModItems.GOLD_LINING_CHESTPLATE);
			event.accept(ModItems.GOLD_LINING_LEGGINGS);
			event.accept(ModItems.GOLD_LINING_BOOTS);

			event.accept(ModItems.DIAMOND_LINING_HELMET);
			event.accept(ModItems.DIAMOND_LINING_CHESTPLATE);
			event.accept(ModItems.DIAMOND_LINING_LEGGINGS);
			event.accept(ModItems.DIAMOND_LINING_BOOTS);

			event.accept(ModItems.ALLOY_LINING_HELMET);
			event.accept(ModItems.ALLOY_LINING_CHESTPLATE);
			event.accept(ModItems.ALLOY_LINING_LEGGINGS);
			event.accept(ModItems.ALLOY_LINING_BOOTS);

			// ---- 重型甲（由对应内衬甲在锻造台升级而来）----
			event.accept(ModItems.IRON_HEAVY_HELMET);
			event.accept(ModItems.IRON_HEAVY_CHESTPLATE);
			event.accept(ModItems.IRON_HEAVY_LEGGINGS);
			event.accept(ModItems.IRON_HEAVY_BOOTS);

			event.accept(ModItems.GOLD_HEAVY_HELMET);
			event.accept(ModItems.GOLD_HEAVY_CHESTPLATE);
			event.accept(ModItems.GOLD_HEAVY_LEGGINGS);
			event.accept(ModItems.GOLD_HEAVY_BOOTS);

			event.accept(ModItems.DIAMOND_HEAVY_HELMET);
			event.accept(ModItems.DIAMOND_HEAVY_CHESTPLATE);
			event.accept(ModItems.DIAMOND_HEAVY_LEGGINGS);
			event.accept(ModItems.DIAMOND_HEAVY_BOOTS);

			event.accept(ModItems.ALLOY_HEAVY_HELMET);
			event.accept(ModItems.ALLOY_HEAVY_CHESTPLATE);
			event.accept(ModItems.ALLOY_HEAVY_LEGGINGS);
			event.accept(ModItems.ALLOY_HEAVY_BOOTS);
		}
	}
}
