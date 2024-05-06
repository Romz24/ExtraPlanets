package com.mjr.extraplanets.client.model.rockets;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.Constants;
import com.mjr.mjrlegendslib.client.model.ModelTransformWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.model.TRSRTransformation;

import micdoodle8.mods.galacticraft.core.util.ClientUtil;

@SuppressWarnings("deprecation")
public class ItemModelRocketT9New extends ModelTransformWrapper {
	public ItemModelRocketT9New(IBakedModel modelToWrap) {
		super(modelToWrap);
	}

	@Override
	protected Matrix4f getTransformForPerspective(TransformType cameraTransformType) {
		if (cameraTransformType == TransformType.GUI) {
			//

			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
			RenderHelper.enableStandardItemLighting();
			Vector3f trans = new Vector3f(-0.12F, 0.0F, -0.12F);
			Matrix4f ret = new Matrix4f();
			ret.setIdentity();
			Matrix4f mul = new Matrix4f();
			mul.setIdentity();
			Quat4f rot = TRSRTransformation.quatFromYXZDegrees(new Vector3f(30, 225, 0));
			mul.setRotation(rot);
			ret.mul(mul);
			mul.setIdentity();
			mul.setScale(0.46F);
			ret.mul(mul);
			mul.setIdentity();
			mul.setTranslation(new Vector3f(-0.65F, -0.85F, 0.0F));
			ret.mul(mul);
			mul.setIdentity();
			mul.rotY(Constants.halfPI);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotX((float) (Math.PI / 4.0F));
			ret.mul(mul);
			mul.setIdentity();
			mul.setTranslation(trans);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotY(ClientUtil.getMilliseconds() / 1000.0F);
			ret.mul(mul);
			mul.setIdentity();
			trans.scale(-1.0F);
			mul.setTranslation(trans);
			ret.mul(mul);
			mul.setIdentity();
			mul.setScale(0.25F);
			ret.mul(mul);
			return ret;
		}

		if (cameraTransformType == TransformType.FIRST_PERSON) {
			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
			Vector3f trans = new Vector3f(0.5F, -1.9F, -1.8F);
			Matrix4f ret = new Matrix4f();
			ret.setIdentity();
			Matrix4f mul = new Matrix4f();
			mul.setIdentity();
			mul.setScale(4.0F);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotX(Constants.halfPI);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotZ(-0.65F);
			ret.mul(mul);
			mul.setIdentity();
			mul.setTranslation(trans);
			ret.mul(mul);
			return ret;
		}

		if (cameraTransformType == TransformType.THIRD_PERSON) {

			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
			Vector3f trans = new Vector3f(0.0F, -4.9F, -0.8F);
			Matrix4f ret = new Matrix4f();
			ret.setIdentity();
			Matrix4f mul = new Matrix4f();
			mul.setIdentity();
			Quat4f rot = TRSRTransformation.quatFromYXZDegrees(new Vector3f(0, 10, 0));
			mul.setRotation(rot);
			ret.mul(mul);
			mul.setIdentity();
			mul.setScale(0.6F);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotX((float) (Math.PI / 3.0F));
			ret.mul(mul);
			mul.setIdentity();
			mul.rotZ((float) (-Math.PI / 2.0F));
			ret.mul(mul);
			mul.setIdentity();
			mul.rotX(0.3F);
			ret.mul(mul);
			mul.setIdentity();
			mul.setTranslation(trans);
			ret.mul(mul);
			return ret;
		}

		if (cameraTransformType == TransformType.GROUND) {

			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
			Matrix4f ret = new Matrix4f();
			ret.setIdentity();
			Matrix4f mul = new Matrix4f();
			mul.setIdentity();
			mul.setScale(0.1F);
			ret.mul(mul);
			return ret;
		}

		if (cameraTransformType == TransformType.FIXED) {
			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
			Matrix4f ret = new Matrix4f();
			ret.setIdentity();
			Matrix4f mul = new Matrix4f();
			mul.setIdentity();
			mul.setScale(0.055F);
			ret.mul(mul);
			mul.setIdentity();
			mul.rotZ(7F);
			ret.mul(mul);
			mul.setIdentity();
			mul.setTranslation(new Vector3f(0.1F, -10F, 0.05F));
			ret.mul(mul);
			return ret;
		}

		return null;
	}
}
