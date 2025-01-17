package com.mjr.extraplanets.jei.rockets.tier7;

import javax.annotation.Nonnull;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.jei.RecipeCategories;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;

public class Tier7RocketRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
	private static final ResourceLocation rocketGuiTexture = new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "textures/gui/schematic_rocket_t3_recipe.png");

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public Tier7RocketRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(rocketGuiTexture, 0, 0, 168, 126);
		this.localizedName = TranslateUtilities.translate("tile.rocket_workbench.name");

	}

	@Nonnull
	@Override
	public String getUid() {
		return RecipeCategories.ROCKET_T7_ID;
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
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(0, true, 44, 0);
		itemStacks.init(1, true, 35, 18);
		itemStacks.init(2, true, 35, 36);
		itemStacks.init(3, true, 35, 54);
		itemStacks.init(4, true, 35, 72);
		itemStacks.init(5, true, 35, 90);
		itemStacks.init(6, true, 53, 18);
		itemStacks.init(7, true, 53, 36);
		itemStacks.init(8, true, 53, 54);
		itemStacks.init(9, true, 53, 72);
		itemStacks.init(10, true, 53, 90);
		itemStacks.init(11, true, 17, 72); // Booster left
		itemStacks.init(12, true, 17, 90);
		itemStacks.init(13, true, 71, 90);
		itemStacks.init(14, true, 44, 108); // Rocket
		itemStacks.init(15, true, 71, 72); // Booster right
		itemStacks.init(16, true, 17, 108);
		itemStacks.init(17, true, 71, 108);
		itemStacks.init(18, true, 89, 7);
		itemStacks.init(19, true, 115, 7);
		itemStacks.init(20, true, 141, 7);
		itemStacks.init(21, false, 138, 95);

		itemStacks.set(ingredients);
	}

	@Override
	public String getModName() {
		return Constants.modName;
	}
}