package com.mjr.extraplanets.api.prefabs.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.api.block.IPowerDock;
import com.mjr.extraplanets.api.enitity.IPoweredDockable;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.TransformerHooks;
import micdoodle8.mods.galacticraft.core.entities.IControllableEntity;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.network.PacketDynamic;
import micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate;
import micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate.IEntityFullSync;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public abstract class EntityPoweredVehicleBase extends Entity implements IInventoryDefaults, IPacketReceiver, IPoweredDockable, IControllableEntity, IEntityFullSync {
	private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityPoweredVehicleBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityPoweredVehicleBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ROCK_DIRECTION = EntityDataManager.createKey(EntityPoweredVehicleBase.class, DataSerializers.VARINT);

	protected long ticks = 0;
	public int roverType;
	private double speed;
	public float wheelRotationZ;
	public float wheelRotationX;
	private float maxSpeed = 0.6F;
	private float accel = 0.5F;
	private float turnFactor = 3.0F;
	public String texture;
	protected ItemStack[] cargoItems = new ItemStack[60];
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	private int boatPosRotationIncrements;
	protected IPowerDock landingPad;
	private int timeClimbing;
	private boolean shouldClimb;
	protected boolean invertControls = false;

	// Power System
	private float currentPowerCapacity;
	private float powerMaxCapacity;

	public EntityPoweredVehicleBase(World var1) {
		super(var1);
		this.setSize(2.8F, 1F);
		this.speed = 0.0D;
		this.preventEntitySpawning = true;
		this.dataManager.register(CURRENT_DAMAGE, 0);
		this.dataManager.register(TIME_SINCE_HIT, 0);
		this.dataManager.register(ROCK_DIRECTION, 1);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;

		// Power System
		this.currentPowerCapacity = 0;
		this.powerMaxCapacity = 10000;

		if (var1 != null && var1.isRemote) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketDynamic(this));
		}
	}

	public EntityPoweredVehicleBase(World var1, double var2, double var4, double var6, int type) {
		this(var1);
		this.setPosition(var2, var4, var6);
		this.setBuggyType(type);
		this.cargoItems = new ItemStack[this.roverType * 18];
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
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger)) {
			final double offsetX = Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			final double offsetZ = Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			passenger.setPosition(this.posX + offsetX - 0.1F, this.posY + 0.4F + passenger.getYOffset(), this.posZ + offsetZ);
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
		this.dataManager.set(ROCK_DIRECTION, -this.dataManager.get(ROCK_DIRECTION));
		this.dataManager.set(TIME_SINCE_HIT, 10);
		this.dataManager.set(CURRENT_DAMAGE, this.dataManager.get(CURRENT_DAMAGE) * 5);
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
				this.dataManager.set(ROCK_DIRECTION, -this.dataManager.get(ROCK_DIRECTION));
				this.dataManager.set(TIME_SINCE_HIT, 10);
				this.dataManager.set(CURRENT_DAMAGE, (int) (this.dataManager.get(CURRENT_DAMAGE) + var2 * 10));
				this.setBeenAttacked();

				if (e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isCreativeMode) {
					this.dataManager.set(CURRENT_DAMAGE, 100);
				}

				if (flag || this.dataManager.get(CURRENT_DAMAGE) > 2) {
					if (!this.getPassengers().isEmpty()) {
						this.removePassengers();
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
				EntityItem.getEntityItem().setTagCompound(item.getTagCompound().copy());
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean b) {
		if (!this.getPassengers().isEmpty()) {
			if (this.getPassengers().contains(MCUtilities.getClient().thePlayer)) {
			} else {
				this.boatPosRotationIncrements = posRotationIncrements + 5;
				this.boatX = x;
				this.boatY = y;
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
		this.featureUpdate();
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
				var12 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + var12 / this.boatPosRotationIncrements);
				this.rotationPitch = (float) (this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(x, y, z);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				x = this.posX + this.motionX;
				y = this.posY + this.motionY;
				z = this.posZ + this.motionZ;
				if (this.getPassengers() != null) {
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

		if (this.dataManager.get(TIME_SINCE_HIT) > 0) {
			this.dataManager.set(TIME_SINCE_HIT, this.dataManager.get(TIME_SINCE_HIT) - 1);
		}

		if (this.dataManager.get(CURRENT_DAMAGE) > 0) {
			this.dataManager.set(CURRENT_DAMAGE, this.dataManager.get(CURRENT_DAMAGE) - 1);
		}

		if (!this.onGround) {
			this.motionY -= TransformerHooks.getGravityForEntity(this) * 0.5D;
		}

		if (this.inWater && this.speed > 0.2D) {
			this.worldObj.playSound(null, (float) this.posX, (float) this.posY, (float) this.posZ, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.NEUTRAL, 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
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

		if (this.worldObj.isRemote && this.currentPowerCapacity > 0) {
			this.motionX = -(this.speed * Math.cos((this.rotationYaw - 90F) * Math.PI / 180.0D));
			this.motionZ = -(this.speed * Math.sin((this.rotationYaw - 90F) * Math.PI / 180.0D));
		}

		if (this.worldObj.isRemote) {
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		}

		if (!this.worldObj.isRemote && Math.abs(this.motionX * this.motionZ) > 0.0) {
			double d = this.motionX * this.motionX + this.motionZ * this.motionZ;

			if (d != 0 && this.ticks % (MathHelper.floor_double(2 / d) + 1) == 0) {
				this.removePower(10);
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
		sendData.add(this.currentPowerCapacity);
	}

	@Override
	public void decodePacketdata(ByteBuf buffer) {
		this.roverType = buffer.readInt();
		this.currentPowerCapacity = buffer.readFloat();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.roverType = var1.getInteger("roverType");
		final NBTTagList var2 = var1.getTagList("Items", 10);
		this.cargoItems = new ItemStack[this.getSizeInventory()];
		this.currentPowerCapacity = var1.getFloat("currentPowerCapacity");

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
		var1.setFloat("currentPowerCapacity", this.currentPowerCapacity);
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
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if (this.worldObj.isRemote) {
			if (this.getPassengers() == null) {
				PlayerUtilties.sendMessage(player,
						GameSettings.getKeyDisplayString(KeyHandlerClient.leftKey.getKeyCode()) + " / " + GameSettings.getKeyDisplayString(KeyHandlerClient.rightKey.getKeyCode()) + "  - " + TranslateUtilities.translate("gui.buggy.turn.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.accelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.accel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.decelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.decel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(com.mjr.extraplanets.client.handlers.KeyHandlerClient.openPowerGUI.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.powered.inv.name"));
			}

			return true;
		} else {
			if (this.getPassengers().contains(player)) {
				this.removePassenger(player);

				return true;
			} else {
				player.startRiding(this);
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
			case 0: // Deccelerate
				if (this.currentPowerCapacity < 10)
					return false;
				this.speed -= this.accel / 20D;
				this.shouldClimb = true;
				return true;
			case 1: // Accelerate
				if (this.currentPowerCapacity < 10)
					return false;
				this.speed += this.accel / 20D;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public EnumCargoLoadingState addCargo(ItemStack stack, boolean doAdd) {
		if (this.roverType == 0) {
			return EnumCargoLoadingState.NOINVENTORY;
		}

		int count = 0;

		for (count = 0; count < this.cargoItems.length; count++) {
			ItemStack stackAt = this.cargoItems[count];

			if (stackAt != null && stackAt.getItem() == stack.getItem() && stackAt.getItemDamage() == stack.getItemDamage() && stackAt.stackSize < stackAt.getMaxStackSize()) {
				if (stackAt.stackSize + stack.stackSize <= stackAt.getMaxStackSize()) {
					if (doAdd) {
						this.cargoItems[count].stackSize += stack.stackSize;
						this.markDirty();
					}

					return EnumCargoLoadingState.SUCCESS;
				} else {
					// Part of the stack can fill this slot but there will be some left over
					int origSize = stackAt.stackSize;
					int surplus = origSize + stack.stackSize - stackAt.getMaxStackSize();

					if (doAdd) {
						this.cargoItems[count].stackSize = stackAt.getMaxStackSize();
						this.markDirty();
					}

					stack.stackSize = surplus;
					if (this.addCargo(stack, doAdd) == EnumCargoLoadingState.SUCCESS) {
						return EnumCargoLoadingState.SUCCESS;
					}

					this.cargoItems[count].stackSize = origSize;
					return EnumCargoLoadingState.FULL;
				}
			}
		}

		for (count = 0; count < this.cargoItems.length; count++) {
			ItemStack stackAt = this.cargoItems[count];

			if (stackAt == null) {
				if (doAdd) {
					this.cargoItems[count] = stack;
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
			ItemStack stackAt = this.cargoItems[i];

			if (stackAt != null) {
				ItemStack resultStack = stackAt.copy();
				resultStack.stackSize = 1;

				if (doRemove && --stackAt.stackSize <= 0) {
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
		return false;
	}

	@Override
	public UUID getOwnerUUID() {
		if (!this.getPassengers().isEmpty() && !(this.getPassengers().get(0) instanceof EntityPlayer)) {
			return null;
		}

		return !this.getPassengers().isEmpty() ? this.getPassengers().get(0).getPersistentID() : null;
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

	/*
	 * Power System Methods ------------------------------------------------------------------------------------------------------
	 */
	public float getCurrentPowerCapacity() {
		return currentPowerCapacity;
	}

	public void setCurrentPowerCapacity(float currentPowerCapacity) {
		this.currentPowerCapacity = currentPowerCapacity;
	}

	public float getPowerMaxCapacity() {
		return powerMaxCapacity;
	}

	public void setPowerMaxCapacity(float powerMaxCapacity) {
		this.powerMaxCapacity = powerMaxCapacity;
	}

	@Override
	public float addPower(float amount, boolean doDrain) {
		float beforePower = this.getCurrentPowerCapacity();
		if (this.getCurrentPowerCapacity() >= this.getPowerMaxCapacity())
			this.setCurrentPowerCapacity(this.getPowerMaxCapacity());
		else
			this.setCurrentPowerCapacity(this.getCurrentPowerCapacity() + amount);
		return this.getCurrentPowerCapacity() - beforePower;
	}

	@Override
	public float removePower(float amount) {
		float beforePower = this.getCurrentPowerCapacity();
		if ((this.getCurrentPowerCapacity() - amount) <= 0)
			this.setCurrentPowerCapacity(0);
		else
			this.setCurrentPowerCapacity(this.getCurrentPowerCapacity() - amount);
		return beforePower - this.getCurrentPowerCapacity();
	}

	@Override
	public boolean inFlight() {
		return false;
	}

	// ------------------------------------------------------------------------------------------------------

	public abstract String getInventoryName();

	public abstract List<ItemStack> getItemsDropped();

	@Override
	public abstract ItemStack getPickedResult(RayTraceResult target);

	@Override
	public abstract void setPad(IPowerDock pad);

	@Override
	public abstract IPowerDock getLandingPad();

	@Override
	public abstract boolean isDockValid(IPowerDock dock);

	public abstract void featureUpdate();
}