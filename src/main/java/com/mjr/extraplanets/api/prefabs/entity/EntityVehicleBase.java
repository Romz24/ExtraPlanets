package com.mjr.extraplanets.api.prefabs.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mjr.extraplanets.Constants;
import com.mjr.mjrlegendslib.inventory.IInventoryDefaults;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.PlayerUtilties;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import micdoodle8.mods.galacticraft.api.entity.IDockable;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.TransformerHooks;
import micdoodle8.mods.galacticraft.core.entities.IControllableEntity;
import micdoodle8.mods.galacticraft.core.network.*;
import micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate.IEntityFullSync;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public abstract class EntityVehicleBase extends Entity implements IInventoryDefaults, IPacketReceiver, IDockable, IControllableEntity, IEntityFullSync {
	public final int tankCapacity = 1000;
	public FluidTank roverFuelTank = new FluidTank(this.tankCapacity);
	protected long ticks = 0;
	public int roverType;
	public int currentDamage;
	public int timeSinceHit;
	public int rockDirection;
	private double speed;
	public float wheelRotationZ;
	public float wheelRotationX;
	private float maxSpeed = 0.5F;
	private float accel = 0.2F;
	private float turnFactor = 3.0F;
	public String texture;
	ItemStack[] cargoItems = new ItemStack[60];
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	private int boatPosRotationIncrements;
	protected IFuelDock landingPad;
	private int timeClimbing;
	private boolean shouldClimb;

	public EntityVehicleBase(World var1) {
		super(var1);
		this.setSize(0.98F, 1F);
		this.speed = 0.0D;
		this.preventEntitySpawning = true;
		this.dataWatcher.addObject(this.currentDamage, new Integer(0));
		this.dataWatcher.addObject(this.timeSinceHit, new Integer(0));
		this.dataWatcher.addObject(this.rockDirection, new Integer(1));
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;

		if (var1 != null && var1.isRemote) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketDynamic(this));
		}
	}

	public EntityVehicleBase(World var1, double var2, double var4, double var6, int type) {
		this(var1);
		this.setPosition(var2, var4, var6);
		this.setBuggyType(type);
		this.cargoItems = new ItemStack[this.roverType * 18];
	}

	public int getScaledFuelLevel(int i) {
		final double fuelLevel = this.roverFuelTank.getFluid() == null ? 0 : this.roverFuelTank.getFluid().amount;

		return (int) (fuelLevel * i / this.tankCapacity);
	}

	public ModelBase getModel() {
		return null;
	}

	public int getType() {
		return this.roverType;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public double getMountedYOffset() {
		return this.height - 3.0D;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void setBuggyType(int par1) {
		this.roverType = par1;
	}

	@Override
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			final double var1 = Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			final double var3 = Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			this.riddenByEntity.setPosition(this.posX + var1, this.posY + 0.4F + this.riddenByEntity.getYOffset(), this.posZ + var3);
		}
	}

	@Override
	public void setPositionRotationAndMotion(double x, double y, double z, float yaw, float pitch, double motX, double motY, double motZ, boolean onGround) {
		if (this.worldObj.isRemote) {
			this.boatX = x;
			this.boatY = y;
			this.boatZ = z;
			this.boatYaw = yaw;
			this.boatPitch = pitch;
			this.motionX = motX;
			this.motionY = motY;
			this.motionZ = motZ;
			this.boatPosRotationIncrements = 5;
		} else {
			this.setPosition(x, y, z);
			this.setRotation(yaw, pitch);
			this.motionX = motX;
			this.motionY = motY;
			this.motionZ = motZ;
		}
	}

	@Override
	public void performHurtAnimation() {
		this.dataWatcher.updateObject(this.rockDirection, Integer.valueOf(-this.dataWatcher.getWatchableObjectInt(this.rockDirection)));
		this.dataWatcher.updateObject(this.timeSinceHit, Integer.valueOf(10));
		this.dataWatcher.updateObject(this.currentDamage, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(this.currentDamage) * 5));
	}

	@Override
	public boolean attackEntityFrom(DamageSource var1, float var2) {
		if (this.isDead || var1.equals(DamageSource.cactus)) {
			return true;
		} else {
			Entity e = var1.getEntity();
			boolean flag = var1.getEntity() instanceof EntityPlayer && ((EntityPlayer) var1.getEntity()).capabilities.isCreativeMode;

			if (this.isEntityInvulnerable(var1) || (e instanceof EntityLivingBase && !(e instanceof EntityPlayer))) {
				return false;
			} else {
				this.dataWatcher.updateObject(this.rockDirection, Integer.valueOf(-this.dataWatcher.getWatchableObjectInt(this.rockDirection)));
				this.dataWatcher.updateObject(this.timeSinceHit, Integer.valueOf(10));
				this.dataWatcher.updateObject(this.currentDamage, Integer.valueOf((int) (this.dataWatcher.getWatchableObjectInt(this.currentDamage) + var2 * 10)));
				this.setBeenAttacked();

				if (e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isCreativeMode) {
					this.dataWatcher.updateObject(this.currentDamage, 100);
				}

				if (flag || this.dataWatcher.getWatchableObjectInt(this.currentDamage) > 2) {
					if (this.riddenByEntity != null) {
						this.riddenByEntity.mountEntity(this);
					}

					if (!this.worldObj.isRemote) {
						if (this.riddenByEntity != null) {
							this.riddenByEntity.mountEntity(this);
						}
					}
					if (flag) {
						this.setDead();
					} else {
						this.setDead();
						if (!this.worldObj.isRemote) {
							this.dropBuggyAsItem();
						}
					}
					this.setDead();
				}

				return true;
			}
		}
	}

	public void dropBuggyAsItem() {
		List<ItemStack> dropped = this.getItemsDropped();

		if (dropped == null) {
			return;
		}

		for (final ItemStack item : dropped) {
			EntityItem EntityItem = this.entityDropItem(item, 0);

			if (item.hasTagCompound()) {
				EntityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
			}
		}
	}

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean b) {
		if (this.riddenByEntity != null) {
			if (this.riddenByEntity instanceof EntityPlayer && MCUtilities.getClient().thePlayer.equals(this.riddenByEntity)) {
			} else {
				this.boatPosRotationIncrements = posRotationIncrements + 5;
				this.boatX = x;
				this.boatY = y + (this.riddenByEntity == null ? 1 : 0);
				this.boatZ = z;
				this.boatYaw = yaw;
				this.boatPitch = pitch;
			}
		}
	}

	@Override
	public void onUpdate() {
		if (this.ticks >= Long.MAX_VALUE) {
			this.ticks = 1;
		}

		this.ticks++;

		super.onUpdate();

		if (this.worldObj.isRemote) {
			this.wheelRotationX += Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 150.0F * (this.speed < 0 ? 1 : -1);
			this.wheelRotationX %= 360;
			this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ * 0.9F));
		}

		if (this.worldObj.isRemote && !MCUtilities.getClient().thePlayer.equals(this.worldObj.getClosestPlayerToEntity(this, -1))) {
			double x;
			double y;
			double var12;
			double z;
			if (this.boatPosRotationIncrements > 0) {
				x = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
				y = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
				z = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
				var12 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + var12 / this.boatPosRotationIncrements);
				this.rotationPitch = (float) (this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(x, y, z);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				x = this.posX + this.motionX;
				y = this.posY + this.motionY;
				z = this.posZ + this.motionZ;
				if (this.riddenByEntity != null) {
					this.setPosition(x, y, z);
				}

				if (this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}
			return;
		}

		if (this.dataWatcher.getWatchableObjectInt(this.timeSinceHit) > 0) {
			this.dataWatcher.updateObject(this.timeSinceHit, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(this.timeSinceHit) - 1));
		}

		if (this.dataWatcher.getWatchableObjectInt(this.currentDamage) > 0) {
			this.dataWatcher.updateObject(this.currentDamage, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(this.currentDamage) - 1));
		}

		if (!this.onGround) {
			this.motionY -= TransformerHooks.getGravityForEntity(this) * 0.5D;
		}

		if (this.inWater && this.speed > 0.2D) {
			this.worldObj.playSoundEffect((float) this.posX, (float) this.posY, (float) this.posZ, "rand.fizz", 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
		}

		this.speed *= 0.98D;

		if (this.speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		}

		if (this.isCollidedHorizontally && this.shouldClimb) {
			this.speed *= 0.9;
			this.motionY = 0.15D * ((-Math.pow((this.timeClimbing) - 1, 2)) / 250.0F) + 0.15F;
			this.motionY = Math.max(-0.15, this.motionY);
			this.shouldClimb = false;
		}

		if ((this.motionX == 0 || this.motionZ == 0) && !this.onGround) {
			this.timeClimbing++;
		} else {
			this.timeClimbing = 0;
		}

		if (this.worldObj.isRemote && this.roverFuelTank.getFluid() != null && this.roverFuelTank.getFluid().amount > 0) {
			this.motionX = -(this.speed * Math.cos((this.rotationYaw - 90F) * Math.PI / 180.0D));
			this.motionZ = -(this.speed * Math.sin((this.rotationYaw - 90F) * Math.PI / 180.0D));
		}

		if (this.worldObj.isRemote) {
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		}

		if (!this.worldObj.isRemote && Math.abs(this.motionX * this.motionZ) > 0.000001) {
			double d = this.motionX * this.motionX + this.motionZ * this.motionZ;

			if (d != 0 && this.ticks % (MathHelper.floor_double(2 / d) + 1) == 0) {
				this.removeFuel(1);
			}
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.worldObj.isRemote) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketEntityUpdate(this));
		} else if (this.ticks % 5 == 0) {
			GalacticraftCore.packetPipeline.sendToAllAround(new PacketEntityUpdate(this), new TargetPoint(GCCoreUtil.getDimensionID(this.worldObj), this.posX, this.posY, this.posZ, 50.0D));
			GalacticraftCore.packetPipeline.sendToAllAround(new PacketDynamic(this), new TargetPoint(GCCoreUtil.getDimensionID(this.worldObj), this.posX, this.posY, this.posZ, 50.0D));
		}
	}

	@Override
	public void getNetworkedData(ArrayList<Object> sendData) {
		if (this.worldObj.isRemote) {
			return;
		}
		sendData.add(this.roverType);
		sendData.add(this.roverFuelTank);
	}

	@Override
	public void decodePacketdata(ByteBuf buffer) {
		this.roverType = buffer.readInt();

		try {
			this.roverFuelTank = NetworkUtil.readFluidTank(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.roverType = var1.getInteger("roverType");
		final NBTTagList var2 = var1.getTagList("Items", 10);
		this.cargoItems = new ItemStack[this.getSizeInventory()];

		if (var1.hasKey("fuelTank")) {
			this.roverFuelTank.readFromNBT(var1.getCompoundTag("fuelTank"));
		}

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			final int var5 = var4.getByte("Slot") & 255;

			if (var5 < this.cargoItems.length) {
				this.cargoItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("roverType", this.roverType);
		final NBTTagList var2 = new NBTTagList();

		if (this.roverFuelTank.getFluid() != null) {
			var1.setTag("fuelTank", this.roverFuelTank.writeToNBT(new NBTTagCompound()));
		}

		for (int var3 = 0; var3 < this.cargoItems.length; ++var3) {
			if (this.cargoItems[var3] != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.cargoItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		var1.setTag("Items", var2);
	}

	@Override
	public int getSizeInventory() {
		return this.roverType * 18;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.cargoItems[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if (this.cargoItems[var1] != null) {
			ItemStack var3;

			if (this.cargoItems[var1].stackSize <= var2) {
				var3 = this.cargoItems[var1];
				this.cargoItems[var1] = null;
				return var3;
			} else {
				var3 = this.cargoItems[var1].splitStack(var2);

				if (this.cargoItems[var1].stackSize == 0) {
					this.cargoItems[var1] = null;
				}

				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int var1) {
		if (this.cargoItems[var1] != null) {
			final ItemStack var2 = this.cargoItems[var1];
			this.cargoItems[var1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.cargoItems[var1] = var2;

		if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
			var2.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return !this.isDead && var1.getDistanceSqToEntity(this) <= 64.0D;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if (this.worldObj.isRemote) {
			if (this.riddenByEntity == null) {
				PlayerUtilties.sendMessage(player,
						GameSettings.getKeyDisplayString(KeyHandlerClient.leftKey.getKeyCode()) + " / " + GameSettings.getKeyDisplayString(KeyHandlerClient.rightKey.getKeyCode()) + "  - " + TranslateUtilities.translate("gui.buggy.turn.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.accelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.accel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.decelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.decel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.openFuelGui.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.inv.name"));
			}

			return true;
		} else {
			if (this.riddenByEntity != null) {
				if (this.riddenByEntity == player)
					player.mountEntity(null);
				return true;
			} else {
				player.mountEntity(this);
				return true;
			}
		}
	}

	@Override
	public boolean pressKey(int key) {
		if (this.worldObj.isRemote && (key == 6 || key == 8 || key == 9)) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(PacketSimple.EnumSimplePacket.S_CONTROL_ENTITY, GCCoreUtil.getDimensionID(this.worldObj), new Object[] { key }));
			return true;
		}

		switch (key) {
			case 0: // Accelerate
				this.speed += this.accel / 20D;
				this.shouldClimb = true;
				return true;
			case 1: // Deccelerate
				this.speed -= this.accel / 20D;
				this.shouldClimb = true;
				return true;
			case 2: // Left
				this.rotationYaw -= 0.5F * this.turnFactor;
				this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ + 0.5F));
				return true;
			case 3: // Right
				this.rotationYaw += 0.5F * this.turnFactor;
				this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ - 0.5F));
				return true;
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return false;
	}

	@Override
	public int addFuel(FluidStack liquid, boolean doDrain) {
		if (this.landingPad != null)
			return FluidUtil.fillWithGCFuel(this.roverFuelTank, liquid, doDrain);

		return 0;
	}

	@Override
	public FluidStack removeFuel(int amount) {
		return this.roverFuelTank.drain(amount, true);
	}

	@Override
	public EnumCargoLoadingState addCargo(ItemStack itemStack, boolean doAdd) {
		if (this.roverType == 0) {
			return EnumCargoLoadingState.NOINVENTORY;
		}

		int count = 0;

		for (count = 0; count < this.cargoItems.length; count++) {
			ItemStack itemStackAt = this.cargoItems[count];

			if (itemStackAt != null && itemStackAt.getItem() == itemStack.getItem() && itemStackAt.getItemDamage() == itemStack.getItemDamage() && itemStackAt.stackSize < itemStackAt.getMaxStackSize()) {
				if (itemStackAt.stackSize + itemStack.stackSize <= itemStackAt.getMaxStackSize()) {
					if (doAdd) {
						this.cargoItems[count].stackSize += itemStack.stackSize;
						this.markDirty();
					}

					return EnumCargoLoadingState.SUCCESS;
				} else {
					// Part of the itemStack can fill this slot but there will be some left over
					int origSize = itemStackAt.stackSize;
					int surplus = origSize + itemStack.stackSize - itemStackAt.getMaxStackSize();

					if (doAdd) {
						this.cargoItems[count].stackSize = itemStackAt.getMaxStackSize();
						this.markDirty();
					}

					itemStack.stackSize = surplus;
					if (this.addCargo(itemStack, doAdd) == EnumCargoLoadingState.SUCCESS) {
						return EnumCargoLoadingState.SUCCESS;
					}

					this.cargoItems[count].stackSize = origSize;
					return EnumCargoLoadingState.FULL;
				}
			}
		}

		for (count = 0; count < this.cargoItems.length; count++) {
			ItemStack itemStackAt = this.cargoItems[count];

			if (itemStackAt == null) {
				if (doAdd) {
					this.cargoItems[count] = itemStack;
					this.markDirty();
				}

				return EnumCargoLoadingState.SUCCESS;
			}
		}

		return EnumCargoLoadingState.FULL;
	}

	@Override
	public RemovalResult removeCargo(boolean doRemove) {
		for (int i = 0; i < this.cargoItems.length; i++) {
			ItemStack itemStackAt = this.cargoItems[i];

			if (itemStackAt != null) {
				ItemStack resultStack = itemStackAt.copy();
				resultStack.stackSize = 1;

				if (doRemove && --itemStackAt.stackSize <= 0) {
					this.cargoItems[i] = null;
				}

				if (doRemove) {
					this.markDirty();
				}
				return new RemovalResult(EnumCargoLoadingState.SUCCESS, resultStack);
			}
		}

		return new RemovalResult(EnumCargoLoadingState.EMPTY, null);
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public UUID getOwnerUUID() {
		if (this.riddenByEntity != null && !(this.riddenByEntity instanceof EntityPlayer)) {
			return null;
		}

		return this.riddenByEntity != null ? ((EntityPlayer) this.riddenByEntity).getPersistentID() : null;
	}

	@Override
	public void onPadDestroyed() {

	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getAccel() {
		return accel;
	}

	public void setAccel(float accel) {
		this.accel = accel;
	}

	public abstract String getInventoryName();

	public abstract List<ItemStack> getItemsDropped();

	@Override
	public abstract ItemStack getPickedResult(MovingObjectPosition target);

	@Override
	public abstract void setPad(IFuelDock pad);

	@Override
	public abstract IFuelDock getLandingPad();

	@Override
	public abstract boolean isDockValid(IFuelDock dock);
}