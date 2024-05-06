package com.mjr.extraplanets.entities.rockets;

import java.util.List;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import com.mjr.extraplanets.tileEntities.blocks.TileEntityTier3LandingPad;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;

public class EntityTier10Rocket extends EntityTieredRocket {
	public EntityTier10Rocket(World par1World) {
		super(par1World);
		this.setSize(1.8F, 6F);
	}

	public EntityTier10Rocket(World par1World, double x, double y, double z, EnumRocketType rocketType) {
		super(par1World, x, y, z);
		this.rocketType = rocketType;
		this.cargoItems = new ItemStack[this.getSizeInventory()];
	}

	public EntityTier10Rocket(World par1World, double x, double y, double z, boolean reversed, EnumRocketType rocketType, ItemStack[] inv) {
		this(par1World, x, y, z, rocketType);
		this.cargoItems = inv;
	}

	@Override
	public double getYOffset() {
		return 1.5F;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(ExtraPlanets_Items.TIER_10_ROCKET, 1, this.rocketType.getIndex());
	}

	@Override
	public double getMountedYOffset() {
		return 6.75D;
	}

	@Override
	public float getRotateOffset() {
		return 5.8F;
	}

	@Override
	public double getOnPadYOffset() {
		return 0.0D;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		int i;

		if (this.timeUntilLaunch >= 100) {
			i = Math.abs(this.timeUntilLaunch / 100);
		} else {
			i = 1;
		}

		if ((this.getLaunched() || this.launchPhase == EnumLaunchPhase.IGNITED.ordinal() && this.rand.nextInt(i) == 0) && !ConfigManagerCore.disableSpaceshipParticles && this.hasValidFuel()) {
			if (this.worldObj.isRemote) {
				this.spawnParticles(this.getLaunched());
			}
		}

		if (this.launchPhase >= EnumLaunchPhase.LAUNCHED.ordinal() && this.hasValidFuel()) {
			if (this.launchPhase == EnumLaunchPhase.LAUNCHED.ordinal()) {
				double d = this.timeSinceLaunch / 150;

				d = Math.min(d, 1);

				if (d != 0.0) {
					this.motionY = -d * 2.5D * Math.cos((this.rotationPitch - 180) / Constants.RADIANS_TO_DEGREES);
				}
			} else {
				this.motionY -= 0.008D;
			}

			double multiplier = 1.0D;

			if (this.worldObj.provider instanceof IGalacticraftWorldProvider) {
				multiplier = ((IGalacticraftWorldProvider) this.worldObj.provider).getFuelUsageMultiplier();

				if (multiplier <= 0) {
					multiplier = 1;
				}
			}

			if (this.timeSinceLaunch % MathHelper.floor_double(2 * (1 / multiplier)) == 0) {
				this.removeFuel(1);
				if (!this.hasValidFuel()) {
					this.stopRocketSound();
				}
			}
		} else if (!this.hasValidFuel() && this.getLaunched() && !this.worldObj.isRemote) {
			if (Math.abs(Math.sin(this.timeSinceLaunch / 1000)) / 10 != 0.0) {
				this.motionY -= Math.abs(Math.sin(this.timeSinceLaunch / 1000)) / 20;
			}
		}
	}

	@Override
	public void onTeleport(EntityPlayerMP player) {
		EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

		if (playerBase != null) {
			GCPlayerStats stats = GCPlayerStats.get(playerBase);

			if (this.cargoItems == null || this.cargoItems.length == 0) {
				stats.setRocketStacks(new ItemStack[2]);
			} else {
				stats.setRocketStacks(this.cargoItems);
			}

			stats.setRocketType(this.rocketType.getIndex());
			stats.setRocketItem(ExtraPlanets_Items.TIER_10_ROCKET);
			stats.setFuelLevel(this.fuelTank.getFluidAmount());
		}
	}

	protected void spawnParticles(boolean launched) {
		if (!this.isDead) {
			double sinPitch = Math.sin(this.rotationPitch / Constants.RADIANS_TO_DEGREES_D);
			double x1 = 3.2 * Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
			double z1 = 3.2 * Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
			double y1 = 3.2 * Math.cos((this.rotationPitch - 180) / Constants.RADIANS_TO_DEGREES_D);
			if (this.launchPhase == EnumLaunchPhase.LANDING.ordinal() && this.targetVec != null) {
				double modifier = this.posY - this.targetVec.getY();
				modifier = Math.max(modifier, 180.0);
				x1 *= modifier / 200.0D;
				y1 *= Math.min(modifier / 200.0D, 2.5D);
				z1 *= modifier / 200.0D;
			}

			final double y2 = this.prevPosY + (this.posY - this.prevPosY) + y1 - 0.75 * this.motionY - 0.3 + 1.2D;

			final double x2 = this.posX + x1 + this.motionX;
			final double z2 = this.posZ + z1 + this.motionZ;
			Vector3 motionVec = new Vector3(x1 + this.motionX, y1 + this.motionY, z1 + this.motionZ);
			Vector3 d1 = new Vector3(y1 * 0.1D, -x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
			Vector3 d2 = new Vector3(x1 * 0.1D, -z1 * 0.1D, y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
			Vector3 d3 = new Vector3(-y1 * 0.1D, x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
			Vector3 d4 = new Vector3(x1 * 0.1D, z1 * 0.1D, -y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
			Vector3 mv1 = motionVec.clone().translate(d1);
			Vector3 mv2 = motionVec.clone().translate(d2);
			Vector3 mv3 = motionVec.clone().translate(d3);
			Vector3 mv4 = motionVec.clone().translate(d4);
			// T3 - Four flameballs which spread
			Object[] rider = new Object[] { riddenByEntity };
			makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, this.getLaunched(), rider);
			makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, this.getLaunched(), rider);
			makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, this.getLaunched(), rider);
			makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, this.getLaunched(), rider);
		}
	}

	private void makeFlame(double x2, double y2, double z2, Vector3 motionVec, boolean getLaunched, Object[] rider) {
		if (getLaunched) {
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4, y2, z2), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4, y2, z2), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 + 0.4D), motionVec, rider);
			GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 - 0.4D), motionVec, rider);
			return;
		}

		if (this.ticksExisted % 2 == 0)
			return;

		y2 += 1.2D;
		double x1 = motionVec.x;
		double y1 = motionVec.y;
		double z1 = motionVec.z;
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 + 0.5D, y1 - 0.3D, z1 + 0.5D), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 - 0.5D, y1 - 0.3D, z1 + 0.5D), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 - 0.5D, y1 - 0.3D, z1 - 0.5D), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 + 0.5D, y1 - 0.3D, z1 - 0.5D), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4, y2, z2), new Vector3(x1 + 0.8D, y1 - 0.3D, z1), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4, y2, z2), new Vector3(x1 - 0.8D, y1 - 0.3D, z1), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 + 0.4D), new Vector3(x1, y1 - 0.3D, z1 + 0.8D), rider);
		GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 - 0.4D), new Vector3(x1, y1 - 0.3D, z1 - 0.8D), rider);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return !this.isDead && entityPlayer.getDistanceSqToEntity(this) <= 64.0D;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	public void onPadDestroyed() {
		if (!this.isDead && this.launchPhase != EnumLaunchPhase.LAUNCHED.ordinal()) {
			this.dropShipAsItem();
			this.setDead();
		}
	}

	@Override
	public int getRocketTier() {
		return 10;
	}

	@Override
	public int getFuelTankCapacity() {
		return 1500;
	}

	@Override
	public int getPreLaunchWait() {
		return 400;
	}

	@Override
	public float getCameraZoom() {
		return 15.0F;
	}

	@Override
	public boolean defaultThirdPerson() {
		return true;
	}

	@Override
	public List<ItemStack> getItemsDropped(List<ItemStack> droppedItems) {
		super.getItemsDropped(droppedItems);
		ItemStack rocket = new ItemStack(ExtraPlanets_Items.TIER_10_ROCKET, 1, this.rocketType.getIndex());
		rocket.setTagCompound(new NBTTagCompound());
		rocket.getTagCompound().setInteger("RocketFuel", this.fuelTank.getFluidAmount());
		droppedItems.add(rocket);
		return droppedItems;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public float getRenderOffsetY() {
		return 1.1F;
	}

	@Override
	public boolean isDockValid(IFuelDock dock) {
		return dock instanceof TileEntityTier3LandingPad;
	}

	@Override
	public String getName() {
		return TranslateUtilities.translate("entity.extraplanets.EntityTier10Rocket.name");
	}
}
