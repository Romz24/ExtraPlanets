package com.mjr.extraplanets.client.gui.machines;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.inventory.machines.ContainerSolar;
import com.mjr.extraplanets.tileEntities.machines.TileEntitySolar;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;

public class GuiSolar extends GuiContainerGC {
	private static final ResourceLocation solarGuiTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/solar.png");

	private final TileEntitySolar tileEntity;

	private GuiButton buttonEnableSolar;
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 107, (this.height - this.ySize) / 2 + 101, 56, 9, new ArrayList<String>(), this.width, this.height, this);

	public GuiSolar(InventoryPlayer inventoryPlayer, TileEntitySolar tileEntity) {
		super(new ContainerSolar(inventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
		this.ySize = 201;
		this.xSize = 176;
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
			case 0:
				GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, this.mc.world.provider.getDimensionType().getId(), new Object[] { this.tileEntity.getPos(), 0 }));
				break;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		electricityDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 96;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
		List<String> batterySlotDesc = new ArrayList<String>();
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.0"));
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 151, (this.height - this.ySize) / 2 + 82, 18, 18, batterySlotDesc, this.width, this.height, this));
		List<String> sunGenDesc = new ArrayList<String>();
		float sunVisible = Math.round(this.tileEntity.solarStrength / 9.0F * 1000) / 10.0F;
		sunGenDesc.add(this.tileEntity.solarStrength > 0 ? TranslateUtilities.translate("gui.status.sun_visible.name") + ": " + sunVisible + "%" : TranslateUtilities.translate("gui.status.blockedfully.name"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 47, (this.height - this.ySize) / 2 + 20, 18, 18, sunGenDesc, this.width, this.height, this));
		this.buttonList.add(this.buttonEnableSolar = new GuiButton(0, this.width / 2 - 36, this.height / 2 - 19, 72, 20, TranslateUtilities.translate("gui.button.enable.name")));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int offsetY = 35;
		this.buttonEnableSolar.enabled = this.tileEntity.disableCooldown == 0;
		this.buttonEnableSolar.displayString = !this.tileEntity.getDisabled(0) ? TranslateUtilities.translate("gui.button.disable.name") : TranslateUtilities.translate("gui.button.enable.name");
		String displayString = this.tileEntity.getName();
		this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 7, 4210752);
		displayString = TranslateUtilities.translate("gui.message.status.name") + ": " + this.getStatus();
		this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 45 + 23 - 46 + offsetY, 4210752);
		displayString = TranslateUtilities.translate("gui.message.generating.name") + ": "
				+ (this.tileEntity.generateWatts > 0 ? EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.generateWatts) + "/t" : TranslateUtilities.translate("gui.status.not_generating.name"));
		this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 34 + 23 - 46 + offsetY, 4210752);
		float boost = Math.round((this.tileEntity.getSolarBoost() - 1) * 1000) / 10.0F;
		displayString = TranslateUtilities.translate("gui.message.environment.name") + ": " + boost + "%";
		this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 56 + 23 - 46 + offsetY, 4210752);
		// displayString = ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE);
		// this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 68 + 23 - 46 + offsetY, 4210752);
		this.fontRenderer.drawString(TranslateUtilities.translate("container.inventory"), 8, this.ySize - 94, 4210752);
	}

	private String getStatus() {
		if (this.tileEntity.getDisabled(0)) {
			return EnumColor.ORANGE + TranslateUtilities.translate("gui.status.disabled.name");
		}

		if (!this.tileEntity.getWorld().isDaytime()) {
			return EnumColor.DARK_RED + TranslateUtilities.translate("gui.status.blockedfully.name");
		}

		if (this.tileEntity.getWorld().isRaining() || this.tileEntity.getWorld().isThundering()) {
			return EnumColor.DARK_RED + TranslateUtilities.translate("gui.status.raining.name");
		}

		if (this.tileEntity.solarStrength == 0) {
			return EnumColor.DARK_RED + TranslateUtilities.translate("gui.status.blockedfully.name");
		}

		if (this.tileEntity.solarStrength < 9) {
			return EnumColor.DARK_RED + TranslateUtilities.translate("gui.status.blockedpartial.name");
		}

		if (this.tileEntity.generateWatts > 0) {
			return EnumColor.DARK_GREEN + TranslateUtilities.translate("gui.status.collectingenergy.name");
		}

		return EnumColor.ORANGE + TranslateUtilities.translate("gui.status.unknown.name");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiSolar.solarGuiTexture);
		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		List<String> electricityDesc = new ArrayList<String>();
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
		// electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		// electricityDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int)
		// Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if (this.tileEntity.getEnergyStoredGC() > 0) {
			this.drawTexturedModalRect(var5 + 83, var6 + 24, 176, 0, 11, 10);
		}

		if (this.tileEntity.isDaylight) {
			this.drawTexturedModalRect(var5 + 48, var6 + 21, 176, 10, 16, 16);
		}

		this.drawTexturedModalRect(var5 + 97, var6 + 25, 187, 0, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
	}
}
