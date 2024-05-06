package com.mjr.extraplanets.items.vehicles;

import java.util.List;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.entities.vehicles.EntityMarsRover;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.item.IHoldableItem;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class ItemMarsRover extends Item implements IHoldableItem {
	public ItemMarsRover(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return ExtraPlanets.ItemsTab;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
		for (int i = 0; i < 4; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		final float var4 = 1.0F;
		final float var5 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * var4;
		final float var6 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * var4;
		final double var7 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * var4;
		final double var9 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * var4 + 1.62D - entityPlayer.getYOffset();
		final double var11 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * var4;
		final Vec3 var13 = new Vec3(var7, var9, var11);
		final float var14 = MathHelper.cos(-var6 / Constants.RADIANS_TO_DEGREES - (float) Math.PI);
		final float var15 = MathHelper.sin(-var6 / Constants.RADIANS_TO_DEGREES - (float) Math.PI);
		final float var16 = -MathHelper.cos(-var5 / Constants.RADIANS_TO_DEGREES);
		final float var17 = MathHelper.sin(-var5 / Constants.RADIANS_TO_DEGREES);
		final float var18 = var15 * var16;
		final float var20 = var14 * var16;
		final double var21 = 5.0D;
		final Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
		final MovingObjectPosition var24 = world.rayTraceBlocks(var13, var23, true);

		if (var24 == null) {
			return itemStack;
		} else {
			final Vec3 var25 = entityPlayer.getLook(var4);
			boolean var26 = false;
			final float var27 = 1.0F;
			final List<?> var28 = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.getEntityBoundingBox().addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand(var27, var27, var27));
			int var29;

			for (var29 = 0; var29 < var28.size(); ++var29) {
				final Entity var30 = (Entity) var28.get(var29);

				if (var30.canBeCollidedWith()) {
					final float var31 = var30.getCollisionBorderSize();
					final AxisAlignedBB var32 = var30.getEntityBoundingBox().expand(var31, var31, var31);

					if (var32.isVecInside(var13)) {
						var26 = true;
					}
				}
			}

			if (var26) {
				return itemStack;
			} else {
				if (var24.typeOfHit == MovingObjectType.BLOCK) {
					var29 = var24.getBlockPos().getX();
					int var33 = var24.getBlockPos().getY();
					final int var34 = var24.getBlockPos().getZ();

					if (world.getBlockState(new BlockPos(var29, var33, var34)) == Blocks.snow) {
						--var33;
					}

					final EntityMarsRover var35 = new EntityMarsRover(world, var29 + 0.5F, var33 + 1.0F, var34 + 0.5F, itemStack.getItemDamage());

					if (!world.getCollidingBoundingBoxes(var35, var35.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
						return itemStack;
					}

					if (!world.isRemote) {
						world.spawnEntityInWorld(var35);
					}

					if (!entityPlayer.capabilities.isCreativeMode) {
						--itemStack.stackSize;
					}
				}

				return itemStack;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> par2List, boolean b) {
		if (itemStack.getItemDamage() != 0) {
			par2List.add(TranslateUtilities.translate("gui.buggy.storage_space") + ": " + itemStack.getItemDamage() * 18);
		}
		par2List.add(EnumColor.AQUA + TranslateUtilities.translate("gui.rover.information"));
		par2List.add(EnumColor.BRIGHT_GREEN + TranslateUtilities.translate("gui.rover.information.2"));
	}

	@Override
	public boolean shouldHoldLeftHandUp(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean shouldHoldRightHandUp(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean shouldCrouch(EntityPlayer player) {
		return true;
	}
}