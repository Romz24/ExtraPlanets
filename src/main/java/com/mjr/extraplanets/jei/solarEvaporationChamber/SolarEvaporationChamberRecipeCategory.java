package com.mjr.extraplanets.jei.solarEvaporationChamber;

import javax.annotation.Nonnull;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.jei.RecipeCategories;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SolarEvaporationChamberRecipeCategory extends BlankRecipeCategory {
	private static final ResourceLocation guiTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/solar_evaporation_chamber.png");

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	@Nonnull
	public SolarEvaporationChamberRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(guiTexture, 3, 4, 168, 80);
		this.localizedName = TranslateUtilities.translate("container.basic.solar.evaporation.chamber.name").substring(6);
	}

	@Nonnull
	@Override
	public String getUid() {
		return RecipeCategories.SOLAR_EVAPORTATION_CHAMBER_ID;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return this.localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(0, true, 46, 30);
		itemStacks.init(1, false, 108, 30);

		if (recipeWrapper instanceof SolarEvaporationChamberRecipeWrapper) {
			SolarEvaporationChamberRecipeWrapper circuitFabricatorRecipeWrapper = (SolarEvaporationChamberRecipeWrapper) recipeWrapper;
			itemStacks.setFromRecipe(0, circuitFabricatorRecipeWrapper.getInputs());
			itemStacks.setFromRecipe(1, circuitFabricatorRecipeWrapper.getOutputs());
		}
	}
}
