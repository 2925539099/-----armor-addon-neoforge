package com.armoraddon.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * 内衬盔甲的合成后处理。
 *
 * <p>内衬盔甲（铁质 / 金质）用普通无序合成配方（JEI / 配方书可见），
 * 而「继承原盔甲的附魔与名称、同步已损失的耐久」无法用原版配方表达，
 * 因此在这里监听 {@link PlayerEvent.ItemCraftedEvent}，
 * 在玩家取走产物时把材料中那件原版盔甲的数据搬到产物上。</p>
 *
 * <p>事件触发时机：{@code ResultSlot#checkTakeAchievements} 先 fire 本事件、
 * 之后才扣减合成格里的材料，所以事件里网格中仍能找到原盔甲，
 * 且 {@code event.getCrafting()} 就是玩家最终拿到手的那个物品栈。</p>
 */
public final class ModCraftingEvents {
	private ModCraftingEvents() {
	}

	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		ItemStack result = event.getCrafting();

		if (!isLiningArmor(result.getItem())) {
			return;
		}

		ItemStack base = findBaseArmor(event.getInventory());
		if (base == null) {
			return;
		}

		// 继承原盔甲的附魔。
		var enchantments = base.get(DataComponents.ENCHANTMENTS);
		if (enchantments != null) {
			result.set(DataComponents.ENCHANTMENTS, enchantments);
		}

		// 继承原盔甲的自定义名称。
		var customName = base.get(DataComponents.CUSTOM_NAME);
		if (customName != null) {
			result.set(DataComponents.CUSTOM_NAME, customName);
		}

		// 把原盔甲已损失的耐久同步到产物上。
		// 注意只搬 DAMAGE 一个组件，不能整体 applyComponents ——
		// 否则会把原盔甲的 MAX_DAMAGE 一起带过来，耐久上限就退回原盔甲了。
		int lostDurability = base.getDamageValue();
		if (lostDurability > 0) {
			result.setDamageValue(Math.min(lostDurability, result.getMaxDamage() - 1));
		}
	}

	/** 产物是否为内衬盔甲（铁质 / 金质 / 钻石）。 */
	private static boolean isLiningArmor(Item item) {
		return item == ModItems.IRON_WOOL_HELMET.get()
				|| item == ModItems.IRON_WOOL_CHESTPLATE.get()
				|| item == ModItems.IRON_WOOL_LEGGINGS.get()
				|| item == ModItems.IRON_WOOL_BOOTS.get()
				|| item == ModItems.GOLD_WOOL_HELMET.get()
				|| item == ModItems.GOLD_WOOL_CHESTPLATE.get()
				|| item == ModItems.GOLD_WOOL_LEGGINGS.get()
				|| item == ModItems.GOLD_WOOL_BOOTS.get()
				|| item == ModItems.DIAMOND_WOOL_HELMET.get()
				|| item == ModItems.DIAMOND_WOOL_CHESTPLATE.get()
				|| item == ModItems.DIAMOND_WOOL_LEGGINGS.get()
				|| item == ModItems.DIAMOND_WOOL_BOOTS.get();
	}

	/** 在合成网格里找出作为材料的原版铁 / 金 / 钻石盔甲。 */
	private static ItemStack findBaseArmor(Container grid) {
		for (int i = 0; i < grid.getContainerSize(); i++) {
			ItemStack stack = grid.getItem(i);
			Item item = stack.getItem();
			if (item == Items.IRON_HELMET
					|| item == Items.IRON_CHESTPLATE
					|| item == Items.IRON_LEGGINGS
					|| item == Items.IRON_BOOTS
					|| item == Items.GOLDEN_HELMET
					|| item == Items.GOLDEN_CHESTPLATE
					|| item == Items.GOLDEN_LEGGINGS
					|| item == Items.GOLDEN_BOOTS
					|| item == Items.DIAMOND_HELMET
					|| item == Items.DIAMOND_CHESTPLATE
					|| item == Items.DIAMOND_LEGGINGS
					|| item == Items.DIAMOND_BOOTS) {
				return stack;
			}
		}
		return null;
	}
}
