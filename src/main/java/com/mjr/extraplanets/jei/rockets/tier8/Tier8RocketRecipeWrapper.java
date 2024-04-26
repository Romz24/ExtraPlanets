package com.mjr.extraplanets.jei.rockets.tier8;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;

public class Tier8RocketRecipeWrapper extends BlankRecipeWrapper implements IRecipeWrapper {
	@Nonnull
	private final INasaWorkbenchRecipe recipe;

	public Tier8RocketRecipeWrapper(@Nonnull INasaWorkbenchRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Lists.newArrayList(recipe.getRecipeInput().values()));
		ingredients.setOutput(ItemStack.class, this.recipe.getRecipeOutput());
	}
}