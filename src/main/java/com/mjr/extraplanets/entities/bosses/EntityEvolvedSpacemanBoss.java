package com.mjr.extraplanets.entities.bosses;

import java.util.List;
import java.util.Random;

import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityBossBase;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class EntityEvolvedSpacemanBoss extends EntityBossBase implements IMob, IEntityBreathable {

	public EntityEvolvedSpacemanBoss(World world) {
		super(world);
		this.setSize(0.6F * 4, 1.95F * 4);
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1300.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	protected void onDeathUpdate() {
		super.onDeathUpdate();

		if (!this.worldObj.isRemote) {
			if (this.deathTicks == 100) {
				GalacticraftCore.packetPipeline.sendToAllAround(new PacketSimple(PacketSimple.EnumSimplePacket.C_PLAY_SOUND_BOSS_DEATH, GCCoreUtil.getDimensionID(this.worldObj), new Object[] { 1.5F }),
						new NetworkRegistry.TargetPoint(GCCoreUtil.getDimensionID(this.worldObj), this.posX, this.posY, this.posZ, 40.0D));
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (super.attackEntityFrom(source, amount)) {
			EntityLivingBase entitylivingbase = this.getAttackTarget();

			if (entitylivingbase == null && source.getEntity() instanceof EntityLivingBase) {
				entitylivingbase = (EntityLivingBase) source.getEntity();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);

		if (flag) {
			float f = this.worldObj.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

			if (this.getHeldItem() == null) {
				if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
					entity.setFire(2 * (int) f);
				}
			}
		}

		return flag;
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		this.playSound(Constants.TEXTURE_PREFIX + "entity.bossliving", this.getSoundVolume(), this.getSoundPitch() + 6.0F);
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	}

	@Override
	public float getEyeHeight() {
		float f = 1.74F;

		if (this.isChild()) {
			f = (float) (f - 0.81D);
		}

		return f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		super.handleStatusUpdate(id);
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
		return 9;
	}

	@Override
	public void dropKey() {
		this.entityDropItem(new ItemStack(ExtraPlanets_Items.TIER_9_KEY, 1, 0), 0.5F);
	}

	@Override
	public ItemStack getGuaranteedLoot(Random rand) {
		List<ItemStack> itemStackList;
		itemStackList = GalacticraftRegistry.getDungeonLoot(9);
		return itemStackList.get(rand.nextInt(itemStackList.size())).copy();
	}
}
