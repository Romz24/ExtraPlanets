package com.mjr.extraplanets.itemBlocks.planetAndMoons;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockKepler22b extends ItemBlock {
	public ItemBlockKepler22b(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";

		switch (itemStack.getItemDamage()) {
			case 0: {
				name = "dirt";
				break;
			}
			case 1: {
				name = "stone";
				break;
			}
			case 2: {
				name = "ore_iron";
				break;
			}
			case 3: {
				name = "ore_tin";
				break;
			}
			case 4: {
				name = "ore_copper";
				break;
			}
			case 5: {
				name = "ore_dense_coal";
				break;
			}
			case 6: {
				name = "ore_blue_diamonds";
				break;
			}
			case 7: {
				name = "ore_red_diamonds";
				break;
			}
			case 8: {
				name = "ore_purple_diamonds";
				break;
			}
			case 9: {
				name = "ore_yellow_diamonds";
				break;
			}
			case 10: {
				name = "ore_green_diamonds";
				break;
			}
			case 11: {
				name = "stone_bricks";
				break;
			}
			case 12: {
				name = "cobblestone";
				break;
			}
			case 13: {
				name = "ore_platinum";
				break;
			}
			case 14: {
				name = "platinum_block";
				break;
			}
			default:
				name = "null";
		}
		return this.block.getUnlocalizedName() + "." + name;
	}

	@Override
	public String getUnlocalizedName() {
		return this.block.getUnlocalizedName() + ".0";
	}
}