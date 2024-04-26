package com.mjr.extraplanets.planets.Jupiter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class CloudRenderJupiter extends IRenderHandler {
	private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
	public static int cloudTickCounter = 0;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		GlStateManager.disableCull();
		Entity entity = mc.getRenderViewEntity();
		float f = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		double d0 = CloudRenderJupiter.cloudTickCounter + partialTicks;
		double d1 = (entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks + d0 * 0.029999999329447746D) / 12.0D;
		double d2 = (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks) / 12.0D + 0.33000001311302185D;
		float f3 = world.provider.getCloudHeight() - 25 - f + 0.33F;
		mc.renderEngine.bindTexture(CLOUDS_TEXTURES);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		Vec3d vec3d = new Vec3d(0, 0, 0);
		float f4 = (float) vec3d.xCoord + 71;
		float f5 = (float) vec3d.yCoord + 140;
		float f6 = (float) vec3d.zCoord + 170;

		float f26 = f4 * 0.9F;
		float f27 = f5 * 0.9F;
		float f28 = f6 * 0.9F;
		float f10 = f4 * 0.7F;
		float f11 = f5 * 0.7F;
		float f12 = f6 * 0.7F;
		float f13 = f4 * 0.8F;
		float f14 = f5 * 0.8F;
		float f15 = f6 * 0.8F;
		float f17 = MathHelper.floor(d1) * 0.00390625F;
		float f18 = MathHelper.floor(d2) * 0.00390625F;
		float f19 = (float) (d1 - MathHelper.floor(d1));
		float f20 = (float) (d2 - MathHelper.floor(d2));
		GlStateManager.scale(12.0F, 1.0F, 12.0F);

		for (int i1 = 0; i1 < 2; ++i1) {
			if (i1 == 0) {
				GlStateManager.colorMask(false, false, false, false);
			} else {
				switch (2) {
					case 0:
						GlStateManager.colorMask(false, true, true, true);
						break;
					case 1:
						GlStateManager.colorMask(true, false, false, true);
						break;
					case 2:
						GlStateManager.colorMask(true, true, true, true);
				}
			}

			for (int j1 = -3; j1 <= 4; ++j1) {
				for (int k1 = -3; k1 <= 4; ++k1) {
					vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
					float f22 = j1 * 8;
					float f23 = k1 * 8;
					float f24 = f22 - f19;
					float f25 = f23 - f20;

					if (f3 > -5.0F) {
						vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + 8.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f10, f11, f12, 0.555F).normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + 8.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f10, f11, f12, 0.555F).normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + 0.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f10, f11, f12, 0.555F).normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + 0.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f10, f11, f12, 0.555F).normal(0.0F, -1.0F, 0.0F).endVertex();
					}

					if (f3 <= 5.0F) {
						vertexbuffer.pos(f24 + 0.0F, f3 + 7.0F - 9.765625E-4F, f25 + 8.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f4, f5, f6, 0.555F).normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 7.0F - 9.765625E-4F, f25 + 8.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f4, f5, f6, 0.555F).normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 7.0F - 9.765625E-4F, f25 + 0.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f4, f5, f6, 0.555F).normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 0.0F, f3 + 7.0F - 9.765625E-4F, f25 + 0.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f4, f5, f6, 0.555F).normal(0.0F, 1.0F, 0.0F).endVertex();
					}

					if (j1 > -1) {
						for (int l1 = 0; l1 < 8; ++l1) {
							vertexbuffer.pos(f24 + l1 + 0.0F, f3 + 0.0F, f25 + 8.0F).tex((f22 + l1 + 0.5F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + l1 + 0.0F, f3 + 7.0F, f25 + 8.0F).tex((f22 + l1 + 0.5F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + l1 + 0.0F, f3 + 7.0F, f25 + 0.0F).tex((f22 + l1 + 0.5F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + l1 + 0.0F, f3 + 0.0F, f25 + 0.0F).tex((f22 + l1 + 0.5F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(-1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (j1 <= 1) {
						for (int i2 = 0; i2 < 8; ++i2) {
							vertexbuffer.pos(f24 + i2 + 1.0F - 9.765625E-4F, f3 + 0.0F, f25 + 8.0F).tex((f22 + i2 + 0.5F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + i2 + 1.0F - 9.765625E-4F, f3 + 7.0F, f25 + 8.0F).tex((f22 + i2 + 0.5F) * 0.00390625F + f17, (f23 + 8.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + i2 + 1.0F - 9.765625E-4F, f3 + 7.0F, f25 + 0.0F).tex((f22 + i2 + 0.5F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + i2 + 1.0F - 9.765625E-4F, f3 + 0.0F, f25 + 0.0F).tex((f22 + i2 + 0.5F) * 0.00390625F + f17, (f23 + 0.0F) * 0.00390625F + f18).color(f26, f27, f28, 0.555F).normal(1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (k1 > -1) {
						for (int j2 = 0; j2 < 8; ++j2) {
							vertexbuffer.pos(f24 + 0.0F, f3 + 7.0F, f25 + j2 + 0.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + j2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 7.0F, f25 + j2 + 0.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + j2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + j2 + 0.0F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + j2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + j2 + 0.0F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + j2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, -1.0F).endVertex();
						}
					}

					if (k1 <= 1) {
						for (int k2 = 0; k2 < 8; ++k2) {
							vertexbuffer.pos(f24 + 0.0F, f3 + 7.0F, f25 + k2 + 1.0F - 9.765625E-4F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + k2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 7.0F, f25 + k2 + 1.0F - 9.765625E-4F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + k2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + k2 + 1.0F - 9.765625E-4F).tex((f22 + 8.0F) * 0.00390625F + f17, (f23 + k2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + k2 + 1.0F - 9.765625E-4F).tex((f22 + 0.0F) * 0.00390625F + f17, (f23 + k2 + 0.5F) * 0.00390625F + f18).color(f13, f14, f15, 0.555F).normal(0.0F, 0.0F, 1.0F).endVertex();
						}
					}

					tessellator.draw();
				}
			}
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
	}
}
