package com.mjr.extraplanets.blocks.fluid;

import com.mjr.extraplanets.util.DamageSourceEP;
import com.mjr.mjrlegendslib.block.FluidBasicBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockEP extends FluidBasicBlock {

	public FluidBlockEP(Fluid fluid, String name, Material material) {
		super(fluid, name, material);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (state.getBlock() == ExtraPlanets_Fluids.MAGMA) {
			if ((entity instanceof EntityLivingBase)) {
				if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
					((EntityLivingBase) entity).attackEntityFrom(DamageSourceEP.magma, 4.0F);
				}
			}
		} else if (state.getBlock() == ExtraPlanets_Fluids.FROZEN_WATER || state == ExtraPlanets_Fluids.NITROGEN || state == ExtraPlanets_Fluids.NITROGEN_ICE) {
			if ((entity instanceof EntityLivingBase)) {
				if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
					((EntityLivingBase) entity).attackEntityFrom(DamageSourceEP.hypothermia, 2.5F);
				}
			}
		} else if (state.getBlock() == ExtraPlanets_Fluids.RADIO_ACTIVE_WATER) {
			if ((entity instanceof EntityLivingBase)) {
				if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
					((EntityLivingBase) entity).attackEntityFrom(DamageSourceEP.radiationLiquid, 3.5F);
				}
			}
		} else if (state.getBlock() == ExtraPlanets_Fluids.INFECTED_WATER || state.getBlock() == ExtraPlanets_Fluids.METHANE) {
			if ((entity instanceof EntityLivingBase)) {
				if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
					((EntityLivingBase) entity).attackEntityFrom(DamageSourceEP.infection, 1.0F);
				}
			}
		}
	}

	// @Override TODO Fix
	// @SideOnly(Side.CLIENT)
	// public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
	// super.randomDisplayTick(world, pos, state, rand);
	//
	// if (rand.nextInt(1200) == 0) {
	// world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, "liquid.lava", rand.nextFloat() * 0.25F + 0.75F, 0.00001F + rand.nextFloat() * 0.5F, false);
	// }
	// if (rand.nextInt(10) == 0) {
	// if (World.doesBlockHaveSolidTopSurface(world, pos.down()) && !world.getBlockState(pos.down(2)).getBlock().getMaterial().blocksMovement()) {
	// GalacticraftCore.proxy.spawnParticle("", new Vector3(pos.getX() + rand.nextFloat(), pos.getY() - 1.05D, pos.getZ() + rand.nextFloat()), new Vector3(0, 0, 0), new Object[] {});
	// }
	// }
	// }
}