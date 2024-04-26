package com.mjr.extraplanets.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.entities.landers.EntityMercuryLander;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;

@SideOnly(Side.CLIENT)
public class OverlayMercuryLander extends Overlay {
	private static Minecraft minecraft = MCUtilities.getClient();

	private static long screenTicks;

	public static void renderLanderOverlay() {
		OverlayMercuryLander.screenTicks++;
		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(minecraft, OverlayMercuryLander.minecraft.displayWidth, OverlayMercuryLander.minecraft.displayHeight);
		final int width = scaledresolution.getScaledWidth();
		final int height = scaledresolution.getScaledHeight();
		OverlayMercuryLander.minecraft.entityRenderer.setupOverlayRendering();

		GL11.glPushMatrix();

		GL11.glScalef(2.0F, 2.0F, 0.0F);

		if (OverlayMercuryLander.minecraft.player.getRidingEntity().motionY < -2.0) {
			OverlayMercuryLander.minecraft.fontRenderer.drawString(TranslateUtilities.translate("gui.warning"), width / 4 - OverlayMercuryLander.minecraft.fontRenderer.getStringWidth(TranslateUtilities.translate("gui.warning")) / 2, height / 8 - 20,
					ColorUtil.to32BitColor(255, 255, 0, 0));
			final int alpha = (int) (255 * Math.sin(OverlayMercuryLander.screenTicks / 20.0F));
			final String press1 = TranslateUtilities.translate("gui.lander.warning2");
			final String press2 = TranslateUtilities.translate("gui.lander.warning3");
			OverlayMercuryLander.minecraft.fontRenderer.drawString(press1 + GameSettings.getKeyDisplayString(KeyHandlerClient.spaceKey.getKeyCode()) + press2,
					width / 4 - OverlayMercuryLander.minecraft.fontRenderer.getStringWidth(press1 + GameSettings.getKeyDisplayString(KeyHandlerClient.spaceKey.getKeyCode()) + press2) / 2, height / 8,
					ColorUtil.to32BitColor(alpha, alpha, alpha, alpha));
		}

		GL11.glPopMatrix();

		if (OverlayMercuryLander.minecraft.player.getRidingEntity().motionY != 0.0D) {
			String string = TranslateUtilities.translate("gui.lander.velocity") + ": " + Math.round(((EntityMercuryLander) OverlayMercuryLander.minecraft.player.getRidingEntity()).motionY * 1000) / 100.0D + " "
					+ TranslateUtilities.translate("gui.lander.velocityu");
			int color = ColorUtil.to32BitColor(255, (int) Math.floor(Math.abs(OverlayMercuryLander.minecraft.player.getRidingEntity().motionY) * 51.0D),
					255 - (int) Math.floor(Math.abs(OverlayMercuryLander.minecraft.player.getRidingEntity().motionY) * 51.0D), 0);
			OverlayMercuryLander.minecraft.fontRenderer.drawString(string, width / 2 - OverlayMercuryLander.minecraft.fontRenderer.getStringWidth(string) / 2, height / 3, color);
		}
	}
}
