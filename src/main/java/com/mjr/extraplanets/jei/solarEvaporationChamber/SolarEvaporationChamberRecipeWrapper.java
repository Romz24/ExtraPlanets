package com.mjr.extraplanets.jei.solarEvaporationChamber;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SolarEvaporationChamberRecipeWrapper implements IRecipeWrapper {
	@Nonnull
	private final ItemStack input;
	@Nonnull
	private final ItemStack output;

	public SolarEvaporationChamberRecipeWrapper(@Nonnull ItemStack input, @Nonnull ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
