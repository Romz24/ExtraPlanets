package com.mjr.extraplanets.itemBlocks.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;

public class ItemBlockBasicDecontaminationUnit extends ItemBlockDesc {
	public ItemBlockBasicDecontaminationUnit(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean par4) {
		super.addInformation(itemStack, entityPlayer, list, par4);
	}
}