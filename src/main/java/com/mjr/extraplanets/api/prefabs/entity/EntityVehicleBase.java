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
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityVehicleBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityVehicleBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ROCK_DIRECTION = EntityDataManager.createKey(EntityVehicleBase.class, DataSerializers.VARINT);

	public final int tankCapacity = 1000;
	public FluidTank roverFuelTank = new FluidTank(this.tankCapacity);
	protected long ticks = 0;
	public int roverType;
	private double speed;
	public float wheelRotationZ;
	public float wheelRotationX;
	private float maxSpeed = 0.5F;
	private float accel = 0.2F;
	private float turnFactor = 3.0F;
	public String texture;
	protected NonNullList<ItemStack> stacks = NonNullList.withSize(60, ItemStack.EMPTY);
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
		this.dataManager.register(CURRENT_DAMAGE, 0);
		this.dataManager.register(TIME_SINCE_HIT, 0);
		this.dataManager.register(ROCK_DIRECTION, 1);
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
		this.stacks = NonNullList.withSize(this.roverType * 18, ItemStack.EMPTY);
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
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger)) {
			final double offsetX = Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			final double offsetZ = Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			passenger.setPosition(this.posX + offsetX, this.posY + 0.4F + passenger.getYOffset(), this.posZ + offsetZ);
		}
	}

	@Override
	public void setPositionRotationAndMotion(double x, double y, double z, float yaw, float pitch, double motX, double motY, double motZ, boolean onGround) {
		if (this.world.isRemote) {
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
		if (this.isDead || var1.equals(DamageSource.CACTUS)) {
			return true;
		} else {
			Entity e = var1.getTrueSource();
			boolean flag = var1.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) var1.getTrueSource()).capabilities.isCreativeMode;

			if (this.isEntityInvulnerable(var1) || (e instanceof EntityLivingBase && !(e instanceof EntityPlayer))) {
				return false;
			} else {
				this.dataManager.set(ROCK_DIRECTION, -this.dataManager.get(ROCK_DIRECTION));
				this.dataManager.set(TIME_SINCE_HIT, 10);
				this.dataManager.set(CURRENT_DAMAGE, (int) ((this.dataManager.get(CURRENT_DAMAGE) + var2 * 10)));
				this.markVelocityChanged();

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
						if (!this.world.isRemote) {
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
				EntityItem.getItem().setTagCompound(item.getTagCompound().copy());
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean b) {
		if (!this.getPassengers().isEmpty()) {
			if (this.getPassengers().contains(MCUtilities.getClient().player)) {
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

		super.onUpdate();

		if (this.world.isRemote) {
			this.wheelRotationX += Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 150.0F * (this.speed < 0 ? 1 : -1);
			this.wheelRotationX %= 360;
			this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ * 0.9F));
		}

		if (this.world.isRemote && !MCUtilities.getClient().player.equals(this.world.getClosestPlayerToEntity(this, -1))) {
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
			this.world.playSound(null, (float) this.posX, (float) this.posY, (float) this.posZ, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.NEUTRAL, 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F);
		}

		this.speed *= 0.98D;

		if (this.speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		}

		if (this.collidedHorizontally && this.shouldClimb) {
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

		if (this.world.isRemote && this.roverFuelTank.getFluid() != null && this.roverFuelTank.getFluid().amount > 0) {
			this.motionX = -(this.speed * Math.cos((this.rotationYaw - 90F) * Math.PI / 180.0D));
			this.motionZ = -(this.speed * Math.sin((this.rotationYaw - 90F) * Math.PI / 180.0D));
		}

		if (this.world.isRemote) {
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		}

		if (!this.world.isRemote && Math.abs(this.motionX * this.motionZ) > 0.000001) {
			double d = this.motionX * this.motionX + this.motionZ * this.motionZ;

			if (d != 0 && this.ticks % (MathHelper.floor(2 / d) + 1) == 0) {
				this.removeFuel(1);
			}
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.world.isRemote) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketEntityUpdate(this));
		} else if (this.ticks % 5 == 0) {
			GalacticraftCore.packetPipeline.sendToAllAround(new PacketEntityUpdate(this), new TargetPoint(GCCoreUtil.getDimensionID(this.world), this.posX, this.posY, this.posZ, 50.0D));
			GalacticraftCore.packetPipeline.sendToAllAround(new PacketDynamic(this), new TargetPoint(GCCoreUtil.getDimensionID(this.world), this.posX, this.posY, this.posZ, 50.0D));
		}
	}

	@Override
	public void getNetworkedData(ArrayList<Object> sendData) {
		if (this.world.isRemote) {
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
		ItemStackHelper.loadAllItems(var1, this.stacks);
		if (var1.hasKey("fuelTank")) {
			this.roverFuelTank.readFromNBT(var1.getCompoundTag("fuelTank"));
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("roverType", this.roverType);
		if (this.roverFuelTank.getFluid() != null) {
			var1.setTag("fuelTank", this.roverFuelTank.writeToNBT(new NBTTagCompound()));
		}
		ItemStackHelper.saveAllItems(var1, stacks);
	}

	@Override
	public int getSizeInventory() {
		return this.roverType * 18;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.stacks.get(var1);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemStack = ItemStackHelper.getAndSplit(this.stacks, index, count);

		if (!itemStack.isEmpty()) {
			this.markDirty();
		}

		return itemStack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer var1) {
		return !this.isDead && var1.getDistanceSq(this) <= 64.0D;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (this.world.isRemote) {
			if (this.getPassengers() == null) {
				PlayerUtilties.sendMessage(player,
						GameSettings.getKeyDisplayString(KeyHandlerClient.leftKey.getKeyCode()) + " / " + GameSettings.getKeyDisplayString(KeyHandlerClient.rightKey.getKeyCode()) + "  - " + TranslateUtilities.translate("gui.buggy.turn.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.accelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.accel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.decelerateKey.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.decel.name"));
				PlayerUtilties.sendMessage(player, GameSettings.getKeyDisplayString(KeyHandlerClient.openFuelGui.getKeyCode()) + "       - " + TranslateUtilities.translate("gui.buggy.inv.name"));
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
		if (this.world.isRemote && (key == 6 || key == 8 || key == 9)) {
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(PacketSimple.EnumSimplePacket.S_CONTROL_ENTITY, GCCoreUtil.getDimensionID(this.world), new Object[] { key }));
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
	public EnumCargoLoadingState addCargo(ItemStack stack, boolean doAdd) {
		if (this.roverType == 0) {
			return EnumCargoLoadingState.NOINVENTORY;
		}

		int count = 0;

		for (count = 0; count < this.stacks.size(); count++) {
			ItemStack stackAt = this.stacks.get(count);

			if (stackAt != null && stackAt.getItem() == stack.getItem() && stackAt.getItemDamage() == stack.getItemDamage() && stackAt.getCount() < stackAt.getMaxStackSize()) {
				if (stackAt.getCount() + stack.getCount() <= stackAt.getMaxStackSize()) {
					if (doAdd) {
						stackAt.grow(stack.getCount());
						this.markDirty();
					}

					return EnumCargoLoadingState.SUCCESS;
				} else {
					// Part of the stack can fill this slot but there will be some left over
					int origSize = stackAt.getCount();
					int surplus = origSize + stack.getCount() - stackAt.getMaxStackSize();

					if (doAdd) {
						stackAt.setCount(stackAt.getMaxStackSize());
						this.markDirty();
					}

					stack.setCount(surplus);
					if (this.addCargo(stack, doAdd) == EnumCargoLoadingState.SUCCESS) {
						return EnumCargoLoadingState.SUCCESS;
					}

					stackAt.setCount(origSize);
					return EnumCargoLoadingState.FULL;
				}
			}
		}

		for (count = 0; count < this.stacks.size(); count++) {
			ItemStack stackAt = this.stacks.get(count);

			if (stackAt == null) {
				if (doAdd) {
					this.stacks.set(count, stack);
					this.markDirty();
				}

				return EnumCargoLoadingState.SUCCESS;
			}
		}

		return EnumCargoLoadingState.FULL;
	}

	@Override
	public RemovalResult removeCargo(boolean doRemove) {
		for (int i = 0; i < this.stacks.size(); i++) {
			ItemStack stackAt = this.getStackInSlot(i);

			if (stackAt != null) {
				ItemStack resultStack = stackAt.copy();
				resultStack.setCount(1);

				if (doRemove) {
					stackAt.shrink(1);
					this.markDirty();
				}

				return new RemovalResult(EnumCargoLoadingState.SUCCESS, resultStack);
			}
		}

		return new RemovalResult(EnumCargoLoadingState.EMPTY, ItemStack.EMPTY);
	}

	@Override
	public boolean hasCustomName() {
		return true;
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

	public abstract String getInventoryName();

	public abstract List<ItemStack> getItemsDropped();

	@Override
	public abstract ItemStack getPickedResult(RayTraceResult target);

	@Override
	public abstract void setPad(IFuelDock pad);

	@Override
	public abstract IFuelDock getLandingPad();

	@Override
	public abstract boolean isDockValid(IFuelDock dock);
}