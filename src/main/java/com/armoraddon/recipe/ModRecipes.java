package com.armoraddon.recipe;

import com.armoraddon.ArmorAddon;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 「盔甲附加」的自定义配方序列化器。
 *
 * <p>{@link ArmorUpgradeRecipe} 继承自 {@code CustomRecipe}，其
 * {@code getType()} 已默认返回 {@code RecipeType.CRAFTING}，
 * 因此只需要注册序列化器，不需要额外的配方类型。</p>
 */
public final class ModRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
			DeferredRegister.create(Registries.RECIPE_SERIALIZER, ArmorAddon.MOD_ID);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ArmorUpgradeRecipe>> ARMOR_UPGRADE =
			RECIPE_SERIALIZERS.register("armor_upgrade",
					() -> new SimpleCraftingRecipeSerializer<>(ArmorUpgradeRecipe::new));

	private ModRecipes() {
	}

	public static void register(IEventBus modEventBus) {
		RECIPE_SERIALIZERS.register(modEventBus);
	}
}
