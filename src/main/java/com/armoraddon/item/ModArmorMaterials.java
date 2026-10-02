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

	/**
	 * 铁羊毛盔甲材料：羊毛盔甲与原版铁盔甲的升级版本。
	 *
	 * <ul>
	 *   <li>护甲值 = 原版铁盔甲 + 1（头盔 3 / 胸甲 7 / 护腿 6 / 靴子 3）；</li>
	 *   <li>韧性继承羊毛盔甲，为 2.0；</li>
	 *   <li>耐久倍率 18，恰好是铁盔甲（15）的 +20%。</li>
	 * </ul>
	 *
	 * <p>其余属性沿用铁盔甲：附魔亲和度 9、铁砧修复材料为铁锭、铁甲穿戴音效。</p>
	 */
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> IRON_WOOL = ARMOR_MATERIALS.register(
			"iron_wool",
			() -> new ArmorMaterial(
					// 原版铁盔甲（2 / 6 / 5 / 2）各 +1。
					Map.of(
							ArmorItem.Type.HELMET, 3,
							ArmorItem.Type.CHESTPLATE, 7,
							ArmorItem.Type.LEGGINGS, 6,
							ArmorItem.Type.BOOTS, 3),
					// 附魔亲和度沿用铁盔甲。
					9,
					// 穿戴音效沿用铁盔甲。
					SoundEvents.ARMOR_EQUIP_IRON,
					// 铁砧修复材料：铁锭。
					() -> Ingredient.of(Items.IRON_INGOT),
					List.of(new ArmorMaterial.Layer(
							ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "iron_wool"), "", false)),
					// 韧性继承羊毛盔甲。
					2.0F,
					// 击退抗性沿用铁盔甲。
					0.0F));

	/**
	 * 金质内衬甲材料：羊毛盔甲与原版金盔甲的升级版本。
	 *
	 * <ul>
	 *   <li>护甲值 = 原版金盔甲 + 1（头盔 3 / 胸甲 6 / 护腿 4 / 靴子 2）；</li>
	 *   <li>韧性继承羊毛盔甲，为 2.0；</li>
	 *   <li>附魔亲和度、音效、修复材料与击退抗性均沿用金盔甲。</li>
	 * </ul>
	 *
	 * <p>耐久不在这里设置：金盔甲的耐久倍率是 7，×1.2 不是整数，
	 * 因此改为在注册物品时逐件写入「金盔甲耐久 +20%」的具体数值。</p>
	 */
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOLD_WOOL = ARMOR_MATERIALS.register(
			"gold_wool",
			() -> new ArmorMaterial(
					// 原版金盔甲（2 / 5 / 3 / 1）各 +1。
					Map.of(
							ArmorItem.Type.HELMET, 3,
							ArmorItem.Type.CHESTPLATE, 6,
							ArmorItem.Type.LEGGINGS, 4,
							ArmorItem.Type.BOOTS, 2),
					// 附魔亲和度沿用金盔甲。
					25,
					// 穿戴音效沿用金盔甲。
					SoundEvents.ARMOR_EQUIP_GOLD,
					// 铁砧修复材料：金锭。
					() -> Ingredient.of(Items.GOLD_INGOT),
					List.of(new ArmorMaterial.Layer(
							ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "gold_wool"), "", false)),
					// 韧性继承羊毛盔甲。
					2.0F,
					// 击退抗性沿用金盔甲。
					0.0F));

	/**
	 * 钻石内衬甲材料：羊毛盔甲与原版钻石盔甲的升级版本。
	 *
	 * <p><strong>与前两套的唯一区别是韧性</strong>：原版钻石盔甲本身就带 2.0 韧性，
	 * 这里按「钻石甲的韧性 + 羊毛盔甲的韧性」叠加，得到 <strong>4.0</strong>。</p>
	 *
	 * <ul>
	 *   <li>护甲值 = 原版钻石盔甲 + 1（头盔 4 / 胸甲 9 / 护腿 7 / 靴子 4）；</li>
	 *   <li>韧性 = 2.0（钻石甲）+ 2.0（羊毛盔甲）= 4.0；</li>
	 *   <li>附魔亲和度、音效、修复材料与击退抗性均沿用钻石盔甲。</li>
	 * </ul>
	 *
	 * <p>耐久同样不按倍率换算：钻石甲倍率 33，×1.2 = 39.6 不是整数，
	 * 因此与金质内衬甲一样在注册物品时逐件写入具体数值。</p>
	 */
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DIAMOND_WOOL = ARMOR_MATERIALS.register(
			"diamond_wool",
			() -> new ArmorMaterial(
					// 原版钻石盔甲（3 / 8 / 6 / 3）各 +1。
					Map.of(
							ArmorItem.Type.HELMET, 4,
							ArmorItem.Type.CHESTPLATE, 9,
							ArmorItem.Type.LEGGINGS, 7,
							ArmorItem.Type.BOOTS, 4),
					// 附魔亲和度沿用钻石盔甲。
					10,
					// 穿戴音效沿用钻石盔甲。
					SoundEvents.ARMOR_EQUIP_DIAMOND,
					// 铁砧修复材料：钻石。
					() -> Ingredient.of(Items.DIAMOND),
					List.of(new ArmorMaterial.Layer(
							ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "diamond_wool"), "", false)),
					// 韧性 = 钻石甲 2.0 + 羊毛盔甲 2.0（本套与前两套的唯一区别）。
					4.0F,
					// 击退抗性沿用钻石盔甲。
					0.0F));

	private ModArmorMaterials() {
	}

	public static void register(IEventBus modEventBus) {
		ARMOR_MATERIALS.register(modEventBus);
	}
}
