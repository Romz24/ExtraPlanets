package com.mjr.extraplanets.entities.mobs;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;

public class EntityEvolvedAncientMagmaCube extends EntitySlime implements IEntityBreathable {
	public EntityEvolvedAncientMagmaCube(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
	}

	public static void registerFixesMagmaCube(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntityEvolvedAncientMagmaCube.class);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D * 2);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

	public void setSlimeSizePublic(int size, boolean resetHealth) {
		this.setSlimeSize(size, resetHealth);
	}

	@Override
	protected void setSlimeSize(int size, boolean resetHealth) {
		super.setSlimeSize(size, resetHealth);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(size * 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	protected EnumParticleTypes getParticleType() {
		return EnumParticleTypes.FLAME;
	}

	@Override
	protected EntitySlime createInstance() {
		return new EntityEvolvedAncientMagmaCube(this.world);
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return this.isSmallSlime() ? LootTableList.EMPTY : LootTableList.ENTITIES_MAGMA_CUBE;
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return false;
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	@Override
	public void setInWeb() {
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	@Override
	protected int getJumpDelay() {
		return super.getJumpDelay() * 2;
	}

	@Override
	protected void alterSquishAmount() {
		this.squishAmount *= 0.9F;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jump() {
		this.motionY = 0.42F + this.getSlimeSize() * 0.1F;
		this.isAirBorne = true;
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
	}

	@Override
	protected void handleJumpLava() {
		this.motionY = 0.22F + this.getSlimeSize() * 0.05F;
		this.isAirBorne = true;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the slime's size)
	 */
	@Override
	protected boolean canDamagePlayer() {
		return true;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the slime.
	 */
	@Override
	protected int getAttackStrength() {
		return super.getAttackStrength() + 7;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT : SoundEvents.ENTITY_MAGMACUBE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH : SoundEvents.ENTITY_MAGMACUBE_DEATH;
	}

	@Override
	protected SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_SQUISH;
	}

	@Override
	protected SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_MAGMACUBE_JUMP;
	}

	@Override
	public boolean canBreath() {
		return true;
	}
}