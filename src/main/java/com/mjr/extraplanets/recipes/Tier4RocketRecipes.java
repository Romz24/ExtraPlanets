package com.mjr.extraplanets.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mjr.extraplanets.inventory.rockets.InventorySchematicTier4Rocket;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;

public class Tier4RocketRecipes {
	private static List<INasaWorkbenchRecipe> tier4RocketRecipes = new ArrayList<INasaWorkbenchRecipe>();

	public static ItemStack findMatchingTier4RocketRecipe(InventorySchematicTier4Rocket inventoryRocketBench) {
		for (INasaWorkbenchRecipe recipe : tier4RocketRecipes) {
			if (recipe.matches(inventoryRocketBench)) {
				return recipe.getRecipeOutput();
			}
		}
		return ItemStack.EMPTY;
	}

	public static void addTier4RocketRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
		addTier4RocketRecipe(new NasaWorkbenchRecipe(result, input));
	}

	public static void addTier4RocketRecipe(INasaWorkbenchRecipe recipe) {
		tier4RocketRecipes.add(recipe);
	}

	public static List<INasaWorkbenchRecipe> getTier4RocketRecipes() {
		return tier4RocketRecipes;
	}

	public static void removeTier4RocketRecipe(INasaWorkbenchRecipe recipe) {
		tier4RocketRecipes.remove(recipe);
	}

	public static void removeAllTier4RocketRecipes() {
		tier4RocketRecipes.clear();
	}

	public static void registerRocketCraftingRecipe() {
		HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
		input.put(1, new ItemStack(ExtraPlanets_Items.TIER_4_NOSE_CONE)); // Cone
		// Body
		input.put(2, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(3, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(4, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(5, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(6, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(7, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(8, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(9, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(10, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
		input.put(11, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));

		input.put(12, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 1)); // Booster
		input.put(13, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 2)); // Fin
		input.put(14, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 2)); // Fin
		input.put(15, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 0)); // Engine
		input.put(16, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 1)); // Booster
		input.put(17, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 2)); // Fin
		input.put(18, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 2)); // Fin
		input.put(19, ItemStack.EMPTY);
		input.put(20, ItemStack.EMPTY);
		input.put(21, ItemStack.EMPTY);
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 0), input));

		HashMap<Integer, ItemStack> input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, new ItemStack(Blocks.CHEST));
		input2.put(20, ItemStack.EMPTY);
		input2.put(21, ItemStack.EMPTY);
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, ItemStack.EMPTY);
		input2.put(20, new ItemStack(Blocks.CHEST));
		input2.put(21, ItemStack.EMPTY);
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, ItemStack.EMPTY);
		input2.put(20, ItemStack.EMPTY);
		input2.put(21, new ItemStack(Blocks.CHEST));
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 1), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, new ItemStack(Blocks.CHEST));
		input2.put(20, new ItemStack(Blocks.CHEST));
		input2.put(21, ItemStack.EMPTY);
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, new ItemStack(Blocks.CHEST));
		input2.put(20, ItemStack.EMPTY);
		input2.put(21, new ItemStack(Blocks.CHEST));
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, ItemStack.EMPTY);
		input2.put(20, new ItemStack(Blocks.CHEST));
		input2.put(21, new ItemStack(Blocks.CHEST));
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 2), input2));

		input2 = new HashMap<Integer, ItemStack>(input);
		input2.put(19, new ItemStack(Blocks.CHEST));
		input2.put(20, new ItemStack(Blocks.CHEST));
		input2.put(21, new ItemStack(Blocks.CHEST));
		Tier4RocketRecipes.addTier4RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET, 1, 3), input2));
	}
}
