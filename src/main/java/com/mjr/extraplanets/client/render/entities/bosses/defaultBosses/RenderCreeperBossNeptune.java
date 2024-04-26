package com.mjr.extraplanets.client.render.entities.bosses.defaultBosses;

import com.mjr.extraplanets.client.model.bosses.defaultBosses.ModelCreeperBossNeptune;
import com.mjr.extraplanets.entities.bosses.defaultBosses.EntityCreeperBossNeptune;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;

public class RenderCreeperBossNeptune extends RenderLiving<EntityCreeperBossNeptune> {
	private static final ResourceLocation creeperTexture = new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "textures/model/creeper.png");

	public RenderCreeperBossNeptune(RenderManager renderManager) {
		super(renderManager, new ModelCreeperBossNeptune(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCreeperBossNeptune entity) {
		return RenderCreeperBossNeptune.creeperTexture;
	}

	@Override
	public void doRender(EntityCreeperBossNeptune entityLiving, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entityLiving, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected void preRenderCallback(EntityCreeperBossNeptune entityLiving, float partialTickTime) {
		GlStateManager.scale(4.0F, 4.0F, 4.0F);
		GlStateManager.rotate((float) (Math.pow(entityLiving.deathTicks, 2) / 5.0F + (Math.pow(entityLiving.deathTicks, 2) / 5.0F - Math.pow(entityLiving.deathTicks - 1, 2) / 5.0F) * partialTickTime), 0.0F, 1.0F, 0.0F);
	}

	@Override
	protected int getColorMultiplier(EntityCreeperBossNeptune entityLivingBase, float lightBrightness, float partialTickTime) {
		return super.getColorMultiplier(entityLivingBase, lightBrightness, partialTickTime);
	}
}