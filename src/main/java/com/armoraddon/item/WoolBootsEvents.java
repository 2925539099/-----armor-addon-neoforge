package com.armoraddon.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;

import net.neoforged.neoforge.event.VanillaGameEvent;

/**
 * 羊毛靴子的移动静音。
 *
 * <p>幽匿感测体与坚守者通过 {@link GameEvent} 振动感知玩家。原版里<strong>蹲行</strong>会屏蔽
 * 脚步声等振动（{@code GameEventTags.IGNORE_VIBRATIONS_SNEAKING} 标签里的那一组），
 * 这里让穿羊毛靴子的玩家获得同样的「移动静音」效果。</p>
 *
 * <p>被屏蔽的三个事件与原版蹲行一致：</p>
 * <ul>
 *   <li>{@code STEP} —— 行走 / 奔跑的脚步；</li>
 *   <li>{@code SWIM} —— 游泳；</li>
 *   <li>{@code HIT_GROUND} —— 跳跃落地。</li>
 * </ul>
 *
 * <p>之所以放在 {@link VanillaGameEvent} 上而不是覆盖实体的 {@code dampensVibrations()}：
 * 后者需要 Mixin 修改玩家类，而且会让玩家的<strong>所有</strong>振动
 * （放置方块、攻击等）都静音，范围超出「移动时不被探测」的需求。</p>
 */
public final class WoolBootsEvents {
	private WoolBootsEvents() {
	}

	public static void onVanillaGameEvent(VanillaGameEvent event) {
		var gameEvent = event.getVanillaEvent();

		// 只处理「移动」类振动，与原版蹲行屏蔽的那组一致。
		// 注意用 Holder#is(ResourceKey) 重载：Holder#is(Holder) 已被弃用。
		if (!gameEvent.is(GameEvent.STEP.key())
				&& !gameEvent.is(GameEvent.SWIM.key())
				&& !gameEvent.is(GameEvent.HIT_GROUND.key())) {
			return;
		}

		// 找出振动源；脚部穿着羊毛靴子时取消该振动。
		// VanillaGameEvent 被取消后，振动不会再派发给附近的监听者
		// （钩子在 ServerLevel#gameEvent，幽匿感测体与坚守者都收不到）。
		Entity cause = event.getCause();

		if (cause instanceof LivingEntity living
				&& living.getItemBySlot(EquipmentSlot.FEET).is(ModItems.WOOL_BOOTS.get())) {
			event.setCanceled(true);
		}
	}
}
