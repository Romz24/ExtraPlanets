package com.mjr.extraplanets.moons.Callisto.event;

import com.mjr.extraplanets.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import micdoodle8.mods.galacticraft.api.event.oxygen.GCCoreOxygenSuffocationEvent;
import micdoodle8.mods.galacticraft.core.event.EventWakePlayer;

public class CallistoEvents {
	@SubscribeEvent
	public void GCCoreOxygenSuffocationEvent(GCCoreOxygenSuffocationEvent.Pre event) {
		if (event.getEntityLiving().worldObj.provider.getDimension() == Config.CALLISTO_ID) {
			if (event.getEntity() instanceof EntityPlayer) {
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
		if (event.getEntityLiving().worldObj.provider.getDimension() == Config.CALLISTO_ID) {
			event.getEntityPlayer().heal(5.0F);
		}
	}
}
