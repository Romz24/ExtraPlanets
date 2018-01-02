package com.mjr.extraplanets.jei.vehicles.venusRover;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.util.ResourceLocation;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.jei.RecipeCategories;

public class VenusRoverRecipeCategory extends BlankRecipeCategory {
	private static final ResourceLocation buggyGuiTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/venus_roverbench.png");

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public VenusRoverRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(buggyGuiTexture, 3, 4, 168, 130);
		this.localizedName = GCCoreUtil.translate("tile.rocket_workbench.name");

	}

	@Nonnull
	@Override
	public String getUid() {
		return RecipeCategories.VENUS_ROVER_ID;
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
		IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

		itemstacks.init(0, true, 35, 28);
		itemstacks.init(1, true, 35, 46);
		itemstacks.init(2, true, 35, 64);
		itemstacks.init(3, true, 35, 82);
		itemstacks.init(4, true, 35, 100);

		itemstacks.init(5, true, 53, 28);
		itemstacks.init(6, true, 53, 46);
		itemstacks.init(7, true, 53, 64);
		itemstacks.init(8, true, 53, 82);
		itemstacks.init(9, true, 53, 100);

		itemstacks.init(10, true, 71, 28);
		itemstacks.init(11, true, 71, 46);
		itemstacks.init(12, true, 71, 64);
		itemstacks.init(13, true, 71, 82);
		itemstacks.init(14, true, 71, 100);

		itemstacks.init(15, true, 17, 28);
		itemstacks.init(16, true, 17, 64);
		itemstacks.init(17, true, 17, 100);

		itemstacks.init(18, true, 89, 28);
		itemstacks.init(19, true, 89, 64);
		itemstacks.init(20, true, 89, 100);

		itemstacks.init(21, true, 89, 7);
		itemstacks.init(22, true, 115, 7);
		itemstacks.init(23, true, 141, 7);
		itemstacks.init(24, false, 138, 100);

		if (recipeWrapper instanceof VenusRoverRecipeWrapper) {
			VenusRoverRecipeWrapper buggyRecipeWrapper = (VenusRoverRecipeWrapper) recipeWrapper;
			List inputs = buggyRecipeWrapper.getInputs();

			for (int i = 0; i < inputs.size(); ++i) {
				Object o = inputs.get(i);
				if (o != null) {
					itemstacks.setFromRecipe(i, o);
				}
			}
			itemstacks.setFromRecipe(24, buggyRecipeWrapper.getOutputs());
		}
	}
}