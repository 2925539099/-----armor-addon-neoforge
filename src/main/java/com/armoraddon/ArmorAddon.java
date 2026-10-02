package com.armoraddon;

import com.armoraddon.item.ModArmorMaterials;
import com.armoraddon.item.ModCraftingEvents;
import com.armoraddon.item.ModItems;

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
 * <p>注册三套盔甲材料（附加盔甲、羊毛盔甲、铁质内衬甲）与对应的十二个盔甲部件，
 * 并把它们加入创造模式「战斗」物品栏。</p>
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

		LOGGER.info("[盔甲附加] NeoForge 版模组已加载，附加盔甲、羊毛盔甲与铁质内衬甲已注册。");
	}

	private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(ModItems.ADDON_HELMET);
			event.accept(ModItems.ADDON_CHESTPLATE);
			event.accept(ModItems.ADDON_LEGGINGS);
			event.accept(ModItems.ADDON_BOOTS);

			event.accept(ModItems.WOOL_HELMET);
			event.accept(ModItems.WOOL_CHESTPLATE);
			event.accept(ModItems.WOOL_LEGGINGS);
			event.accept(ModItems.WOOL_BOOTS);

			event.accept(ModItems.IRON_WOOL_HELMET);
			event.accept(ModItems.IRON_WOOL_CHESTPLATE);
			event.accept(ModItems.IRON_WOOL_LEGGINGS);
			event.accept(ModItems.IRON_WOOL_BOOTS);
		}
	}
}
