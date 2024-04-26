package com.mjr.extraplanets.jei.chemicalInjector;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ChemicalInjectorRecipeWrapper extends BlankRecipeWrapper implements IRecipeWrapper {
	@Nonnull
	private final List<ItemStack> input;
	@Nonnull
	private final ItemStack output;

	public ChemicalInjectorRecipeWrapper(@Nonnull List<ItemStack> input, @Nonnull ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
