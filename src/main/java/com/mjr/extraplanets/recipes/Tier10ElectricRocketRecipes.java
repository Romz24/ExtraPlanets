package com.mjr.extraplanets.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mjr.extraplanets.inventory.rockets.InventorySchematicTier10ElectricRocket;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;

public class Tier10ElectricRocketRecipes {
	private static List<INasaWorkbenchRecipe> tier10ElectricRocketRecipes = new ArrayList<INasaWorkbenchRecipe>();

	public static ItemStack findMatchingTier10ElectricRocketRecipe(InventorySchematicTier10ElectricRocket inventoryRocketBench) {
		for (INasaWorkbenchRecipe recipe : tier10ElectricRocketRecipes) {
			if (recipe.matches(inventoryRocketBench)) {
				return recipe.getRecipeOutput();
			}
		}
		return ItemStack.EMPTY;
	}

	public static void addTier10ElectricRocketRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
		addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(result, input));
	}

	public static void addTier10ElectricRocketRecipe(INasaWorkbenchRecipe recipe) {
		tier10ElectricRocketRecipes.add(recipe);
	}

	public static List<INasaWorkbenchRecipe> getTier10ElectricRocketRecipes() {
		return tier10ElectricRocketRecipes;
	}

	public static void removeTier10ElectricRocketRecipe(INasaWorkbenchRecipe recipe) {
		tier10ElectricRocketRecipes.remove(recipe);
	}

	public static void removeAllTier10ElectricRocketRecipes() {
		tier10ElectricRocketRecipes.clear();
	}

	public static void registerRocketCraftingRecipe() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
				input.put(1, new ItemStack(ExtraPlanets_Items.TIER_10_NOSE_CONE)); // Cone
				// Body
				input.put(2, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(3, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(4, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(5, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(6, new ItemStack(ExtraPlanets_Items.TIER_10_ROCKET, 1, i));
				input.put(7, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(8, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(9, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(10, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
				input.put(11, new ItemStack(ExtraPlanets_Items.TIER_10_ROCKET, 1, j));
		
				input.put(12, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 1)); // Booster
				input.put(13, new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 2)); // Fin
				input.put(14, new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 2)); // Fin
				input.put(15, new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 0)); // Engine
				input.put(16, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 1)); // Booster
				input.put(17, new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 2)); // Fin
				input.put(18, new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 2)); // Fin
				input.put(19, ItemStack.EMPTY);
				input.put(20, ItemStack.EMPTY);
				input.put(21, ItemStack.EMPTY);
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 0), input));
		
				HashMap<Integer, ItemStack> input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, new ItemStack(Blocks.CHEST));
				input2.put(20, ItemStack.EMPTY);
				input2.put(21, ItemStack.EMPTY);
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 1), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, ItemStack.EMPTY);
				input2.put(20, new ItemStack(Blocks.CHEST));
				input2.put(21, ItemStack.EMPTY);
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 1), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, ItemStack.EMPTY);
				input2.put(20, ItemStack.EMPTY);
				input2.put(21, new ItemStack(Blocks.CHEST));
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 1), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, new ItemStack(Blocks.CHEST));
				input2.put(20, new ItemStack(Blocks.CHEST));
				input2.put(21, ItemStack.EMPTY);
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 2), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, new ItemStack(Blocks.CHEST));
				input2.put(20, ItemStack.EMPTY);
				input2.put(21, new ItemStack(Blocks.CHEST));
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 2), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, ItemStack.EMPTY);
				input2.put(20, new ItemStack(Blocks.CHEST));
				input2.put(21, new ItemStack(Blocks.CHEST));
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 2), input2));
		
				input2 = new HashMap<Integer, ItemStack>(input);
				input2.put(19, new ItemStack(Blocks.CHEST));
				input2.put(20, new ItemStack(Blocks.CHEST));
				input2.put(21, new ItemStack(Blocks.CHEST));
				Tier10ElectricRocketRecipes.addTier10ElectricRocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET, 1, 3), input2));
			}
		}
	}
}
