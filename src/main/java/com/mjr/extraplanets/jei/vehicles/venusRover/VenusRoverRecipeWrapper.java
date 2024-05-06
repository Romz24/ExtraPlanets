package com.mjr.extraplanets.jei.vehicles.venusRover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;

public class VenusRoverRecipeWrapper extends BlankRecipeWrapper implements ICraftingRecipeWrapper {
	@Nonnull
	private final INasaWorkbenchRecipe recipe;

	public VenusRoverRecipeWrapper(@Nonnull INasaWorkbenchRecipe recipe) {
		this.recipe = recipe;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> list = new ArrayList<>();
		list.addAll(recipe.getRecipeInput().values());
		return list;
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(recipe.getRecipeOutput());
	}
}