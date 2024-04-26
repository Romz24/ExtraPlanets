package com.mjr.extraplanets.itemBlocks.planetAndMoons;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockJupiter extends ItemBlock {
	public ItemBlockJupiter(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";

		switch (itemstack.getItemDamage()) {
			case 0: {
				name = "surface";
				break;
			}
			case 1: {
				name = "sub_surface";
				break;
			}
			case 2: {
				name = "stone";
				break;
			}
			case 3: {
				name = "ore_iron";
				break;
			}
			case 4: {
				name = "ore_tin";
				break;
			}
			case 5: {
				name = "ore_copper";
				break;
			}
			case 6: {
				name = "ore_palladium";
				break;
			}
			case 7: {
				name = "ore_nickel";
				break;
			}
			case 8: {
				name = "palladium_block";
				break;
			}
			case 9: {
				name = "stone_bricks";
				break;
			}
			case 10: {
				name = "dungeon_brick";
				break;
			}
			case 11: {
				name = "ore_red_gem";
				break;
			}
			case 12: {
				name = "red_gem_block";
				break;
			}
			case 13: {
				name = "nickel_block";
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