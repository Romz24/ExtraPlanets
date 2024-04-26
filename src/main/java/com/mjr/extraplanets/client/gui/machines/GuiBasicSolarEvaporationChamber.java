package com.mjr.extraplanets.client.gui.machines;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.inventory.machines.ContainerBasicSolarEvaporationChamber;
import com.mjr.extraplanets.tileEntities.machines.TileEntityBasicSolarEvaporationChamber;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

@SideOnly(Side.CLIENT)
public class GuiBasicSolarEvaporationChamber extends GuiContainerGC {
	private static final ResourceLocation solarEvaporationChamberTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/solar_evaporation_chamber.png");

	private final TileEntityBasicSolarEvaporationChamber tileEntity;

	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 62, (this.height - this.ySize) / 2 + 16, 56, 9, new ArrayList<String>(), this.width, this.height, this);

	public GuiBasicSolarEvaporationChamber(InventoryPlayer inventoryPlayer, TileEntityBasicSolarEvaporationChamber tileEntity) {
		super(new ContainerBasicSolarEvaporationChamber(inventoryPlayer, tileEntity, MCUtilities.getClient().player));
		this.tileEntity = tileEntity;
		this.ySize = 168;
	}

	@Override
	public void initGui() {
		super.initGui();
		List<String> batterySlotDesc = new ArrayList<String>();
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.0"));
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 152, (this.height - this.ySize) / 2 + 6 + 12, 18, 18, batterySlotDesc, this.width, this.height, this));
		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		electricityDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 62;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 16;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GCCoreUtil.drawStringCentered(this.tileEntity.getName(), this.xSize / 2, 5, 4210752, this.fontRenderer);
		String displayText = "";
		int yOffset = -10;

		if (!this.tileEntity.hasInputs()) {
			displayText = EnumColor.RED + TranslateUtilities.translate("gui.status.missing.inputs.name");
		} else if (!this.tileEntity.hasEnoughEnergyToRun) {
			displayText = EnumColor.RED + TranslateUtilities.translate("gui.status.missing.power.name");
		} else if (this.tileEntity.canProcess()) {
			int progress;
			if (this.tileEntity.canProcess() && this.tileEntity.canOutput()) {
				progress = 100 - this.tileEntity.processTicks;
			} else
				progress = 0;
			displayText = EnumColor.BRIGHT_GREEN + TranslateUtilities.translate("gui.status.solarEvaporationChamber.name") + " " + progress + "%";
		} else {
			displayText = EnumColor.AQUA + TranslateUtilities.translate("gui.status.idle.name");
		}

		this.fontRenderer.drawString(TranslateUtilities.translate("gui.message.status.name") + ": " + displayText, 48 - (displayText.length() * 2), 45 + 24 + yOffset, 4210752);
		this.fontRenderer.drawString(TranslateUtilities.translate("container.inventory"), 8, this.ySize - 118 + 2 + 23, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(GuiBasicSolarEvaporationChamber.solarEvaporationChamberTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if (this.tileEntity.getEnergyStoredGC() > 0) {
			this.drawTexturedModalRect(containerWidth + 49, containerHeight + 16, 208, 0, 11, 10);
		}

		if (this.tileEntity.isDaylight) {
			this.drawTexturedModalRect(containerWidth + 81 + 62, containerHeight + 57, 176, 45, 16, 16);
		}

		this.drawTexturedModalRect(containerWidth + 63, containerHeight + 17, 176, 38, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
	}
}