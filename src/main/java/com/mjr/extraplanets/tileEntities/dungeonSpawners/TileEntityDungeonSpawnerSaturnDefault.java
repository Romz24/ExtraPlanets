package com.mjr.extraplanets.tileEntities.dungeonSpawners;

import java.util.ArrayList;
import java.util.List;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.entities.bosses.defaultBosses.EntityCreeperBossSaturn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;

public class TileEntityDungeonSpawnerSaturnDefault extends TileEntityDungeonSpawner<EntityCreeperBossSaturn> {
	public TileEntityDungeonSpawnerSaturnDefault() {
		super(EntityCreeperBossSaturn.class);
	}

	@Override
	public List<Class<? extends EntityLiving>> getDisabledCreatures() {
		List<Class<? extends EntityLiving>> list = new ArrayList<Class<? extends EntityLiving>>();
		list.add(EntityEvolvedSkeleton.class);
		list.add(EntityEvolvedZombie.class);
		list.add(EntityEvolvedSpider.class);
		return list;
	}

	@Override
	public void playSpawnSound(Entity entity) {
		this.worldObj.playSoundAtEntity(entity, Constants.TEXTURE_PREFIX + "ambience.scaryscape", 9.0F, 1.4F);
	}
}
