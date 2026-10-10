package com.armoraddon.item;

import com.armoraddon.ArmorAddon;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 羊毛盔甲、内衬甲与重型甲的「音波抗性」。
 *
 * <p>坚守者的远程攻击（音波）伤害类型是 {@code minecraft:sonic_boom}，
 * 原版设定里<strong>护甲、盾牌、保护魔咒都无法吸收</strong>，只有抗性提升有效。
 * 本模组让下面这几套盔甲对这种伤害产生额外减免：</p>
 *
 * <ul>
 *   <li>羊毛盔甲；</li>
 *   <li>铁质 / 金质 / 钻石 / 合金内衬甲；</li>
 *   <li>铁质 / 金制 / 钻石 / 合金重型甲。</li>
 * </ul>
 *
 * <p><strong>木制盔甲不在此列。</strong></p>
 *
 * <p>减免规则：<strong>每穿一件减 1 点伤害</strong>。穿满四件即减 4 点，
 * 例如普通难度下坚守者音波的 6 点伤害会变成 2 点。最低减到 0，不会变成治疗。</p>
 *
 * <p>判定依据是伤害类型标签 {@code armor_addon:sonic_like}，默认只含
 * {@code minecraft:sonic_boom}。整合包或数据包只要往这个标签里追加条目，
 * 其他模组注册的同类「穿透护甲的音波伤害」也会一并被减免，不需要改代码。</p>
 *
 * <p>钩在 {@link LivingIncomingDamageEvent} 上，它在无敌帧检查之后、
 * <strong>所有伤害减免计算之前</strong>触发，因此：</p>
 * <ul>
 *   <li>音波伤害本来就不经过护甲，这里减掉的点数就是最终减掉的点数；</li>
 *   <li>抗性提升等原版减免仍在之后照常生效，与原版行为一致。</li>
 * </ul>
 */
public final class SonicResistEvents {
	/** 参与音波抗性的九个盔甲材料。木制盔甲不在其中。 */
	private static final List<Holder<ArmorMaterial>> PROTECTED_MATERIALS = List.of(
			ModArmorMaterials.WOOL,
			ModArmorMaterials.IRON_LINING,
			ModArmorMaterials.GOLD_LINING,
			ModArmorMaterials.DIAMOND_LINING,
			ModArmorMaterials.ALLOY_LINING,
			ModArmorMaterials.IRON_HEAVY,
			ModArmorMaterials.GOLD_HEAVY,
			ModArmorMaterials.DIAMOND_HEAVY,
			ModArmorMaterials.ALLOY_HEAVY);

	/** 参与判定的四个盔甲槽位。 */
	private static final List<EquipmentSlot> ARMOR_SLOTS = List.of(
			EquipmentSlot.HEAD,
			EquipmentSlot.CHEST,
			EquipmentSlot.LEGS,
			EquipmentSlot.FEET);

	/** 「音波类」伤害类型标签，默认含 {@code minecraft:sonic_boom}。 */
	public static final TagKey<DamageType> SONIC_LIKE = TagKey.create(
			Registries.DAMAGE_TYPE,
			ResourceLocation.fromNamespaceAndPath(ArmorAddon.MOD_ID, "sonic_like"));

	private SonicResistEvents() {
	}

	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (!(event.getEntity() instanceof Player player)) {
			return;
		}

		// 只处理音波类伤害。
		if (!event.getSource().is(SONIC_LIKE)) {
			return;
		}

		int pieces = countProtectedPieces(player);

		if (pieces <= 0) {
			return;
		}

		// 每件减 1 点，最低减到 0。
		event.setAmount(Math.max(0.0F, event.getAmount() - pieces));
	}

	/** 数出玩家身上穿着几件本模组参与音波抗性的盔甲。 */
	private static int countProtectedPieces(Player player) {
		int count = 0;

		for (EquipmentSlot slot : ARMOR_SLOTS) {
			if (isProtectedArmor(player.getItemBySlot(slot))) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 判断这件盔甲是否属于参与音波抗性的套装。
	 *
	 * <p>用 {@code ==} 比较 {@link Holder} 引用即可：{@link ArmorItem} 在构造时
	 * 收到的就是我们传进去的那个 {@code DeferredHolder} 实例，注册前后都是同一个对象。
	 * 其他模组的盔甲材质与这些都不相等，因此不会受影响。</p>
	 */
	private static boolean isProtectedArmor(ItemStack stack) {
		if (!(stack.getItem() instanceof ArmorItem armor)) {
			return false;
		}

		Holder<ArmorMaterial> material = armor.getMaterial();

		for (Holder<ArmorMaterial> protectedMaterial : PROTECTED_MATERIALS) {
			if (material == protectedMaterial) {
				return true;
			}
		}

		return false;
	}
}
