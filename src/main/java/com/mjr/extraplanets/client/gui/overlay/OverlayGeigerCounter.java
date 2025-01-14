package com.mjr.extraplanets.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.client.handlers.capabilities.CapabilityStatsClientHandler;
import com.mjr.extraplanets.client.handlers.capabilities.IStatsClientCapability;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;

public class OverlayGeigerCounter extends Overlay {
	private static Minecraft minecraft = MCUtilities.getClient();

	public static void render() {
		GlStateManager.disableLighting();

		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(OverlayGeigerCounter.minecraft, OverlayGeigerCounter.minecraft.displayWidth, OverlayGeigerCounter.minecraft.displayHeight);
		final int width = scaledresolution.getScaledWidth();
		OverlayGeigerCounter.minecraft.entityRenderer.setupOverlayRendering();

		GL11.glPushMatrix();

		IStatsClientCapability stats = null;
		if (minecraft.player != null) {
			EntityPlayerSP playerBaseClient = minecraft.player;
			stats = playerBaseClient.getCapability(CapabilityStatsClientHandler.EP_STATS_CLIENT_CAPABILITY, null);
		}
		String text = TranslateUtilities.translate("gui.radiation.current.message") + ": " + (int) stats.getRadiationLevel() + "%";

		GL11.glScalef(1.0F, 1.0F, 1.0F);
		OverlayGeigerCounter.minecraft.fontRenderer.drawString(text, OverlayGeigerCounter.minecraft.fontRenderer.getStringWidth(text) / 2 - 80, 5, ColorUtil.to32BitColor(255, 60, 60, 128));

		GL11.glPopMatrix();
	}

}
