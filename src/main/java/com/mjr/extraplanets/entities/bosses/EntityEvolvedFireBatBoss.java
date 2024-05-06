package com.mjr.extraplanets.entities.bosses;

import java.util.List;
import java.util.Random;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.core.entities.EntityBossBase;

public class EntityEvolvedFireBatBoss extends EntityBossBase implements IMob, IEntityBreathable {
	private int explosionStrength = 2;

	/** Coordinates of where the bat spawned. */
	private BlockPos spawnPosition;

	public EntityEvolvedFireBatBoss(World world) {
		super(world);
		this.setSize(6.5F, 6.9F);
		this.isImmuneToFire = true;
		this.tasks.addTask(7, new EntityEvolvedFireBatBoss.AIFireballAttack(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false, true));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.1F;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * 0.95F;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.bat.idle";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.bat.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.bat.death";
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entity) {
	}

	@Override
	protected void collideWithNearbyEntities() {
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(800.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
	}

	@SideOnly(Side.CLIENT)
	public boolean isAttacking() {
		return this.dataWatcher.getWatchableObjectByte(16) != 0;
	}

	public void setAttacking(boolean p_175454_1_) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) (p_175454_1_ ? 1 : 0)));
	}

	public int getFireballStrength() {
		return this.explosionStrength;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
		this.motionY *= 0.6000000238418579D;
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq(((int) this.posX), ((int) this.posY), ((int) this.posZ)) < 4.0D) {
			this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
		double d1 = this.spawnPosition.getY() + 1.5D - this.posY;
		double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
		float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
		float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;
	}

	static class AIFireballAttack extends EntityAIBase {
		private EntityEvolvedFireBatBoss parentEntity;
		public int attackTimer;

		public AIFireballAttack(EntityEvolvedFireBatBoss entityEvolvedFireBatBoss) {
			this.parentEntity = entityEvolvedFireBatBoss;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			return this.parentEntity.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			this.attackTimer = 0;
		}

		/**
		 * Resets the task
		 */
		@Override
		public void resetTask() {
			this.parentEntity.setAttacking(false);
		}

		/**
		 * Updates the task
		 */
		@Override
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
			double d0 = 64.0D;

			if (entitylivingbase.getDistanceSqToEntity(this.parentEntity) < d0 * d0 && this.parentEntity.canEntityBeSeen(entitylivingbase)) {
				World world = this.parentEntity.worldObj;
				++this.attackTimer;

				if (this.attackTimer == 10) {
					world.playAuxSFXAtEntity((EntityPlayer) null, 1007, new BlockPos(this.parentEntity), 0);
				}

				if (this.attackTimer == 20) {
					double d1 = 4.0D;
					Vec3 vec3 = this.parentEntity.getLook(1.0F);
					double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3.xCoord * d1);
					double d3 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - (0.5D + this.parentEntity.posY + this.parentEntity.height / 2.0F);
					double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3.zCoord * d1);
					world.playAuxSFXAtEntity((EntityPlayer) null, 1008, new BlockPos(this.parentEntity), 0);
					EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.parentEntity, d2, d3, d4);
					entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
					entitylargefireball.posX = this.parentEntity.posX + vec3.xCoord * d1;
					entitylargefireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0F + 0.5D;
					entitylargefireball.posZ = this.parentEntity.posZ + vec3.zCoord * d1;
					world.spawnEntityInWorld(entitylargefireball);
					this.attackTimer = -40;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}

			this.parentEntity.setAttacking(this.attackTimer > 10);
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	/**
	 * Return whether this entity should NOT trigger a pressure plate or a tripwire.
	 */
	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			return super.attackEntityFrom(source, amount);
		}
	}

	/**
	 * /** (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("ExplosionPower", this.explosionStrength);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		if (nbt.hasKey("ExplosionPower", 99)) {
			this.explosionStrength = nbt.getInteger("ExplosionPower");
		}
	}

	@Override
	public float getEyeHeight() {
		return this.height / 2.0F;
	}

	@Override
	public EntityItem entityDropItem(ItemStack itemStack, float par2) {
		final EntityItem EntityItem = new EntityItem(this.worldObj, this.posX, this.posY + par2, this.posZ, itemStack);
		EntityItem.motionY = -2.0D;
		EntityItem.setDefaultPickupDelay();
		if (this.captureDrops) {
			this.capturedDrops.add(EntityItem);
		} else {
			this.worldObj.spawnEntityInWorld(EntityItem);
		}
		return EntityItem;
	}

	@Override
	public boolean canBreath() {
		return true;
	}

	@Override
	public int getChestTier() {
		return 5;
	}

	@Override
	public void dropKey() {
		this.entityDropItem(new ItemStack(ExtraPlanets_Items.TIER_5_KEY, 1, 0), 0.5F);
	}

	@Override
	public ItemStack getGuaranteedLoot(Random rand) {
		List<ItemStack> itemStackList;
		if (Config.MORE_PLANETS_COMPATIBILITY)
			itemStackList = GalacticraftRegistry.getDungeonLoot(4);
		else
			itemStackList = GalacticraftRegistry.getDungeonLoot(5);
		return itemStackList.get(rand.nextInt(itemStackList.size())).copy();
	}
}