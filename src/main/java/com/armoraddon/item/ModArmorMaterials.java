package com.armoraddon.item;

import com.armoraddon.ArmorAddon;

import java.util.List;
import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 「盔甲附加」的自定义盔甲材料。
 */
public final class ModArmorMaterials {
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
			DeferredRegister.create(Registries.ARMOR_MATERIAL, ArmorAddon.MOD_ID);

	/**
	 * 附加盔甲材料：防御与钻石相当，附魔亲和度更高，并带有少量韧性与击退抗性。
	 */
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ADDON = ARMOR_MATERIALS.register(
			"addon",
			() -> new ArmorMaterial(
					Map.of(
							ArmorItem.Type.HELMET, 3,
							ArmorItem.Type.CHESTPLATE, 8,
							ArmorItem.Type.LEGGINGS, 6,
							ArmorItem.Type.BOOTS, 3),
					// 附魔亲和度：皮革 15、铁 9、钻石 10。
					15,
					// 穿戴音效。
					SoundEvents.ARMOR_EQUIP_IRON,
					// 铁砧修复材料。
					() -> Ingredient.of(Items.IRON_INGOT),
					// 贴图名、后缀、是否可染色。
					// 贴图路径为 assets/armor_addon/textures/models/armor/addon_layer_1.png
					List.of(new ArmorMaterial.Layer(
							ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "addon"), "", false)),
					// 韧性。
					2.0F,
					// 击退抗性。
					0.1F));

	/**
	 * 羊毛盔甲材料。
	 *
	 * <p>防御点与附魔亲和度同原版皮革盔甲，修复材料为羊毛（任意颜色），
	 * 但每件都附带 2.0 的护甲韧性。</p>
	 *
	 * <p>耐久度倍率不在这里设置，而是在注册物品时传给
	 * {@code ArmorItem.Type#getDurability}。</p>
	 */
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WOOL = ARMOR_MATERIALS.register(
			"wool",
			() -> new ArmorMaterial(
					// 与皮革相同的防御点。
					Map.of(
							ArmorItem.Type.HELMET, 1,
							ArmorItem.Type.CHESTPLATE, 3,
							ArmorItem.Type.LEGGINGS, 2,
							ArmorItem.Type.BOOTS, 1),
					// 与皮革相同的附魔亲和度。
					15,
					// 布甲音效。
					SoundEvents.ARMOR_EQUIP_LEATHER,
					// 修复材料：任意颜色的羊毛（minecraft:wool 标签）。
					() -> Ingredient.of(ItemTags.WOOL),
					List.of(new ArmorMaterial.Layer(
							ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "wool"), "", false)),
					// 韧性 2.0，全套每一件都生效。
					2.0F,
					// 击退抗性同皮革。
					0.0F));

	private ModArmorMaterials() {
	}

	public static void register(IEventBus modEventBus) {
		ARMOR_MATERIALS.register(modEventBus);
	}
}
