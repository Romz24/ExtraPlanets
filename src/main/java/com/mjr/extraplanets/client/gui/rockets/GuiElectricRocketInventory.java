package com.mjr.extraplanets.client.gui.rockets;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.api.prefabs.entity.EntityElectricRocketBase;
import com.mjr.extraplanets.inventory.rockets.ContainerElectricRocketInventory;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.entity.IRocketType.EnumRocketType;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

@SideOnly(Side.CLIENT)
public class GuiElectricRocketInventory extends GuiContainerGC {
	private static ResourceLocation[] rocketTextures = new ResourceLocation[4];

	static {
		for (int i = 0; i < 4; i++) {
			GuiElectricRocketInventory.rocketTextures[i] = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/powered_vehicle_" + i * 18 + ".png");
		}
	}

	private final IInventory upperChestInventory;
	private final EnumRocketType rocketType;

	public GuiElectricRocketInventory(IInventory par1IInventory, IInventory par2IInventory, EnumRocketType rocketType) {
		super(new ContainerElectricRocketInventory(par1IInventory, par2IInventory, rocketType, MCUtilities.getClient().thePlayer));
		this.upperChestInventory = par1IInventory;
		this.allowUserInput = false;
		this.ySize = rocketType.getInventorySpace() <= 3 ? 145 : 142 + rocketType.getInventorySpace() * 2;
		this.rocketType = rocketType;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(TranslateUtilities.translate("gui.message.power.rocket.name"), 8, 2 + 3, 4210752);
		this.fontRendererObj.drawString(TranslateUtilities.translate(this.upperChestInventory.getName()), 8, 34 + 2 + 3, 4210752);

		if (this.mc.thePlayer != null && this.mc.thePlayer.ridingEntity != null && this.mc.thePlayer.ridingEntity instanceof EntityElectricRocketBase) {
			this.fontRendererObj.drawString(TranslateUtilities.translate("gui.message.power.message.name") + ":", 130, 15 + 3, 4210752);
			final int percentage = (int) (((EntityElectricRocketBase) this.mc.thePlayer.ridingEntity).getCurrentPowerCapacity() / 100);
			final String color = percentage > 80.0D ? EnumColor.BRIGHT_GREEN.getCode() : percentage > 40.0D ? EnumColor.ORANGE.getCode() : EnumColor.RED.getCode();
			final String str = percentage + "% " + TranslateUtilities.translate("gui.message.full.name");
			this.fontRendererObj.drawString(color + str, 130 - str.length() / 2, 20 + 8, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.getTextureManager().bindTexture(GuiElectricRocketInventory.rocketTextures[(this.rocketType.getInventorySpace() - 2) / 18]);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, 176, this.ySize);

		if (this.mc.thePlayer != null && this.mc.thePlayer.ridingEntity != null && this.mc.thePlayer.ridingEntity instanceof EntityElectricRocketBase) {
			final int percentage = (int) ((int) ((((EntityElectricRocketBase) this.mc.thePlayer.ridingEntity).getCurrentPowerCapacity() / 100) / 2) / 2.6);
			this.drawTexturedModalRect((this.width - this.xSize) / 2 + 95, (this.height - this.ySize) / 2 + 45 - percentage, 176, 38 - percentage, 25, percentage);
		}
	}
}
