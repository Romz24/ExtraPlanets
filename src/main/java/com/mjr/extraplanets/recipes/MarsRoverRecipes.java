package com.mjr.extraplanets.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mjr.extraplanets.inventory.vehicles.InventoryMarsRover;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;

public class MarsRoverRecipes {
	private static List<INasaWorkbenchRecipe> marsRoverRecipes = new ArrayList<INasaWorkbenchRecipe>();

	public static ItemStack findMatchingMarsRoverRecipe(InventoryMarsRover craftMatrix) {
		for (INasaWorkbenchRecipe recipe : marsRoverRecipes) {
			if (recipe.matches(craftMatrix)) {
				return recipe.getRecipeOutput();
			}
		}
		return ItemStack.EMPTY;
	}

	public static void addMarsRoverRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
		addMarsRoverRecipe(new NasaWorkbenchRecipe(result, input));
	}

	public static void addMarsRoverRecipe(INasaWorkbenchRecipe recipe) {
		marsRoverRecipes.add(recipe);
	}

	public static List<INasaWorkbenchRecipe> getMarsRoverRecipes() {
		return marsRoverRecipes;
	}

	public static void removeMarsRoverRecipe(INasaWorkbenchRecipe recipe) {
		marsRoverRecipes.remove(recipe);
	}

	public static void removeAllMarsRoverRecipes() {
		marsRoverRecipes.clear();
	}

	public static void registerRoverCraftingRecipe() {
		HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
		input.put(1, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(2, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(3, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(4, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(5, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(6, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(7, new ItemStack(GCItems.partBuggy, 1, 1));
		input.put(8, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(9, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(10, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(11, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(12, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 0));
		input.put(13, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(14, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(15, new ItemStack(MarsItems.marsItemBasic, 1, 3));
		input.put(16, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(17, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(18, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(19, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(20, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(21, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 2));
		input.put(22, ItemStack.EMPTY);
		input.put(23, ItemStack.EMPTY);
		input.put(24, ItemStack.EMPTY);
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 0), input));

		HashMap<Integer, ItemStack> input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, new ItemStack(Blocks.CHEST));
		input.put(23, ItemStack.EMPTY);
		input.put(24, ItemStack.EMPTY);
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, ItemStack.EMPTY);
		input2.put(23, new ItemStack(Blocks.CHEST));
		input2.put(24, ItemStack.EMPTY);
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, ItemStack.EMPTY);
		input2.put(23, ItemStack.EMPTY);
		input2.put(24, new ItemStack(Blocks.CHEST));
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, new ItemStack(Blocks.CHEST));
		input2.put(23, new ItemStack(Blocks.CHEST));
		input2.put(24, ItemStack.EMPTY);
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, new ItemStack(Blocks.CHEST));
		input2.put(23, ItemStack.EMPTY);
		input2.put(24, new ItemStack(Blocks.CHEST));
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, ItemStack.EMPTY);
		input2.put(23, new ItemStack(Blocks.CHEST));
		input2.put(24, new ItemStack(Blocks.CHEST));
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(22, new ItemStack(Blocks.CHEST));
		input2.put(23, new ItemStack(Blocks.CHEST));
		input2.put(24, new ItemStack(Blocks.CHEST));
		MarsRoverRecipes.addMarsRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.MARS_ROVER, 1, 3), input2));
	}
}
