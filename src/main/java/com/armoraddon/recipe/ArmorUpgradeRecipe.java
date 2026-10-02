package com.armoraddon.recipe;

import com.armoraddon.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

/**
 * 铁羊毛盔甲的升级配方。
 *
 * <p>在工作台里放入「对应的铁盔甲 + 对应的羊毛盔甲 + 1 个锁链」即可合成，
 * 例如铁头盔 + 羊毛头盔 + 锁链 → 铁羊毛头盔。</p>
 *
 * <p>结果会继承铁盔甲的附魔与自定义名称，并把铁盔甲<strong>已经损失的耐久</strong>
 * 原样同步到新盔甲上。由于铁羊毛盔甲的耐久上限是铁盔甲的 1.2 倍，
 * 这部分损耗是照搬的绝对值，不是按比例换算。</p>
 *
 * <p>该配方是「特殊配方」（{@link CustomRecipe#isSpecial()}），结果取决于输入物品，
 * 因此不会出现在配方书里 —— 与原版的皮革盔甲染色一致。</p>
 */
public class ArmorUpgradeRecipe extends CustomRecipe {
	public ArmorUpgradeRecipe(CraftingBookCategory category) {
		super(category);
	}

	/** 铁盔甲 -> 对应的羊毛盔甲。 */
	private static Item woolCounterpart(Item iron) {
		if (iron == Items.IRON_HELMET) {
			return ModItems.WOOL_HELMET.get();
		}
		if (iron == Items.IRON_CHESTPLATE) {
			return ModItems.WOOL_CHESTPLATE.get();
		}
		if (iron == Items.IRON_LEGGINGS) {
			return ModItems.WOOL_LEGGINGS.get();
		}
		if (iron == Items.IRON_BOOTS) {
			return ModItems.WOOL_BOOTS.get();
		}
		return null;
	}

	/** 铁盔甲 -> 合成产出的铁羊毛盔甲。 */
	private static Item upgradeResult(Item iron) {
		if (iron == Items.IRON_HELMET) {
			return ModItems.IRON_WOOL_HELMET.get();
		}
		if (iron == Items.IRON_CHESTPLATE) {
			return ModItems.IRON_WOOL_CHESTPLATE.get();
		}
		if (iron == Items.IRON_LEGGINGS) {
			return ModItems.IRON_WOOL_LEGGINGS.get();
		}
		if (iron == Items.IRON_BOOTS) {
			return ModItems.IRON_WOOL_BOOTS.get();
		}
		return null;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return findIronArmor(input) != null;
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
		ItemStack iron = findIronArmor(input);
		return iron == null ? ItemStack.EMPTY : createResult(iron);
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 3;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.ARMOR_UPGRADE.get();
	}

	/**
	 * 检查网格并返回其中的铁盔甲；网格必须正好是「铁盔甲 + 对应羊毛盔甲 + 锁链」，
	 * 出现任何多余物品都返回 null。
	 */
	private static ItemStack findIronArmor(CraftingInput input) {
		ItemStack iron = null;
		ItemStack wool = null;
		boolean hasChain = false;

		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			Item item = stack.getItem();
			Item counterpart = woolCounterpart(item);

			if (!hasChain && item == Items.CHAIN) {
				hasChain = true;
			} else if (iron == null && counterpart != null) {
				iron = stack;
			} else if (wool == null && isWoolArmor(item)) {
				wool = stack;
			} else {
				// 多余或重复的物品
				return null;
			}
		}

		if (iron == null || wool == null || !hasChain) {
			return null;
		}

		// 羊毛盔甲必须是铁盔甲对应的那个部位。
		return wool.getItem() == woolCounterpart(iron.getItem()) ? iron : null;
	}

	private static boolean isWoolArmor(Item item) {
		return item == ModItems.WOOL_HELMET.get()
				|| item == ModItems.WOOL_CHESTPLATE.get()
				|| item == ModItems.WOOL_LEGGINGS.get()
				|| item == ModItems.WOOL_BOOTS.get();
	}

	private static ItemStack createResult(ItemStack iron) {
		Item resultItem = upgradeResult(iron.getItem());
		if (resultItem == null) {
			return ItemStack.EMPTY;
		}

		ItemStack result = new ItemStack(resultItem);

		// 继承铁盔甲的附魔。
		var enchantments = iron.get(DataComponents.ENCHANTMENTS);
		if (enchantments != null) {
			result.set(DataComponents.ENCHANTMENTS, enchantments);
		}

		// 继承铁盔甲的自定义名称。
		var customName = iron.get(DataComponents.CUSTOM_NAME);
		if (customName != null) {
			result.set(DataComponents.CUSTOM_NAME, customName);
		}

		// 把铁盔甲已损失的耐久同步过来。
		// 注意：这里只搬 DAMAGE 一个组件，不能整体 applyComponents —— 那会把铁盔甲的
		// MAX_DAMAGE 一起覆盖掉，新盔甲的耐久上限就退回成铁盔甲的数值了。
		int lostDurability = iron.getDamageValue();
		if (lostDurability > 0) {
			result.setDamageValue(Math.min(lostDurability, result.getMaxDamage() - 1));
		}

		return result;
	}
}
