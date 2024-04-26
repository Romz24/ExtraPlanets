package com.mjr.extraplanets.jei.solarEvaporationChamber;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;

@SuppressWarnings("deprecation")
public class SolarEvaporationChamberRecipeWrapper extends BlankRecipeWrapper implements ICraftingRecipeWrapper {
	@Nonnull
	private final ItemStack input;
	@Nonnull
	private final ItemStack output;

	public SolarEvaporationChamberRecipeWrapper(@Nonnull ItemStack input, @Nonnull ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		return Collections.singletonList(this.input);

	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(this.output);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
