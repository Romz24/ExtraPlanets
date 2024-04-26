package com.mjr.extraplanets.jei.purifier;

import javax.annotation.Nonnull;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.jei.RecipeCategories;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PurifierRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {
	private static final ResourceLocation guiTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/purifier.png");

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;
	@Nonnull
	private final IDrawableAnimated saltBar;
	@Nonnull
	private final IDrawableAnimated saltBar2;
	@Nonnull
	private final IDrawableAnimated saltBar3;

	@Nonnull
	public PurifierRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(guiTexture, 3, 4, 168, 80);
		this.localizedName = TranslateUtilities.translate("container.basic.purifier.name");
		IDrawableStatic progressBarDrawableSalt = guiHelper.createDrawable(guiTexture, 192, 0, 16, 38);
		this.saltBar = guiHelper.createAnimatedDrawable(progressBarDrawableSalt, 70, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic progressBarDrawableSalt2 = guiHelper.createDrawable(guiTexture, 192, 0, 16, 38);
		this.saltBar2 = guiHelper.createAnimatedDrawable(progressBarDrawableSalt2, 70, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic progressBarDrawableSalt3 = guiHelper.createDrawable(guiTexture, 192, 0, 16, 38);
		this.saltBar3 = guiHelper.createAnimatedDrawable(progressBarDrawableSalt3, 70, IDrawableAnimated.StartDirection.TOP, false);
	}

	@Nonnull
	@Override
	public String getUid() {
		return RecipeCategories.PURIFIER_ID;
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
	public void drawExtras(@Nonnull Minecraft minecraft) {
		// this.saltBar.draw(minecraft, 4, 24);
		// this.saltBar2.draw(minecraft, 29, 24);
		// this.saltBar3.draw(minecraft, 129, 24);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

		itemstacks.init(0, true, 3, 2);
		itemstacks.init(1, true, 28, 2);
		itemstacks.init(2, true, 69, 31);
		itemstacks.init(3, false, 28, 2);
		itemstacks.init(4, false, 118, 2);
		itemstacks.set(ingredients);
	}

	@Override
	public String getModName() {
		return Constants.modName;
	}
}
