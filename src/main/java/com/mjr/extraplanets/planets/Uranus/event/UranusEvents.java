package com.mjr.extraplanets.planets.Uranus.event;

import com.mjr.extraplanets.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import micdoodle8.mods.galacticraft.api.event.oxygen.GCCoreOxygenSuffocationEvent;
import micdoodle8.mods.galacticraft.core.event.EventWakePlayer;

public class UranusEvents {
	@SubscribeEvent
	public void GCCoreOxygenSuffocationEvent(GCCoreOxygenSuffocationEvent.Pre event) {
		if (event.entityLiving.worldObj.provider.getDimensionId() == Config.URANUS_ID) {
			if (event.entity instanceof EntityPlayer) {
				event.setCanceled(false);
			} else {
				if (Config.MOB_SUFFOCATION)
					event.setCanceled(false);
				else
					event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void GCCoreEventWakePlayer(EventWakePlayer event) {
		if (event.entityLiving.worldObj.provider.getDimensionId() == Config.URANUS_ID) {
			event.entityPlayer.heal(5.0F);
		}
	}
}
