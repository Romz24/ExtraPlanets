package com.mjr.extraplanets.jei.rockets.tier10Electric;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;

public class Tier10ElectricRocketRecipeWrapper implements IRecipeWrapper {
	@Nonnull
	private final INasaWorkbenchRecipe recipe;

	public Tier10ElectricRocketRecipeWrapper(@Nonnull INasaWorkbenchRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Lists.newArrayList(recipe.getRecipeInput().values()));
		ingredients.setOutput(ItemStack.class, this.recipe.getRecipeOutput());
	}
}