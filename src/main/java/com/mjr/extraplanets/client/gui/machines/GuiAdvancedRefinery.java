package com.mjr.extraplanets.client.gui.machines;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.inventory.machines.ContainerAdvancedRefinery;
import com.mjr.extraplanets.tileEntities.machines.TileEntityAdvancedRefinery;
import com.mjr.mjrlegendslib.util.MCUtilities;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

@SideOnly(Side.CLIENT)
public class GuiAdvancedRefinery extends GuiContainerGC {
	private static final ResourceLocation refineryTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/refinery.png");

	private final TileEntityAdvancedRefinery tileEntity;

	private GuiButton buttonDisable;

	private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
	private GuiElementInfoRegion oilTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 62, (this.height - this.ySize) / 2 + 16, 56, 9, new ArrayList<String>(), this.width, this.height, this);

	public GuiAdvancedRefinery(InventoryPlayer inventoryPlayer, TileEntityAdvancedRefinery tileEntity) {
		super(new ContainerAdvancedRefinery(inventoryPlayer, tileEntity, MCUtilities.getClient().player));
		this.tileEntity = tileEntity;
		this.ySize = 168;
	}

	@Override
	public void initGui() {
		super.initGui();
		List<String> oilTankDesc = new ArrayList<String>();
		oilTankDesc.add(TranslateUtilities.translate("gui.oil_tank.desc.0"));
		oilTankDesc.add(TranslateUtilities.translate("gui.oil_tank.desc.1"));
		int oilLevel = this.tileEntity.oilTank != null && this.tileEntity.oilTank.getFluid() != null ? this.tileEntity.oilTank.getFluid().amount : 0;
		int oilCapacity = this.tileEntity.oilTank != null ? this.tileEntity.oilTank.getCapacity() : 0;
		oilTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.oil.name") + ": " + oilLevel + " / " + oilCapacity);
		this.oilTankRegion.tooltipStrings = oilTankDesc;
		this.oilTankRegion.xPosition = (this.width - this.xSize) / 2 + 7;
		this.oilTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
		this.oilTankRegion.parentWidth = this.width;
		this.oilTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.oilTankRegion);
		List<String> batterySlotDesc = new ArrayList<String>();
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.0"));
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 49, (this.height - this.ySize) / 2 + 50, 18, 18, batterySlotDesc, this.width, this.height, this));
		List<String> fuelTankDesc = new ArrayList<String>();
		fuelTankDesc.add(TranslateUtilities.translate("gui.fuel_tank.desc.4"));
		int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
		fuelTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
		this.fuelTankRegion.tooltipStrings = fuelTankDesc;
		this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 153;
		this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
		this.fuelTankRegion.parentWidth = this.width;
		this.fuelTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.fuelTankRegion);
		List<String> fuelSlotDesc = new ArrayList<String>();
		fuelSlotDesc.add(TranslateUtilities.translate("gui.fuel_output.desc.0"));
		fuelSlotDesc.add(TranslateUtilities.translate("gui.fuel_output.desc.1"));
		fuelSlotDesc.add(TranslateUtilities.translate("gui.fuel_output.desc.2"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 152, (this.height - this.ySize) / 2 + 6, 18, 18, fuelSlotDesc, this.width, this.height, this));
		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		electricityDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 62;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 16;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
		this.buttonList.add(this.buttonDisable = new GuiButton(0, this.width / 2 - 39, this.height / 2 - 56, 76, 20, TranslateUtilities.translate("gui.button.refine.name")));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, this.mc.world.provider.getDimensionType().getId(), new Object[] { this.tileEntity.getPos(), 0 }));
			break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GCCoreUtil.drawStringCentered(this.tileEntity.getName(), this.xSize / 2, 5, 4210752, this.fontRenderer);
		String displayText = "";
		int yOffset = -18;

		String missingInput = null;
		if (this.tileEntity.oilTank.getFluid() == null || this.tileEntity.oilTank.getFluidAmount() == 0) {
			missingInput = EnumColor.RED + TranslateUtilities.translate("gui.status.nooil.name");
		}
		String activeString = this.tileEntity.canProcess() ? EnumColor.BRIGHT_GREEN + TranslateUtilities.translate("gui.status.refining.name") : null;
		displayText = this.tileEntity.getGUIstatus(missingInput, activeString, false);

		this.buttonDisable.enabled = this.tileEntity.disableCooldown == 0;
		this.buttonDisable.displayString = this.tileEntity.processTicks == 0 ? TranslateUtilities.translate("gui.button.refine.name") : TranslateUtilities.translate("gui.button.stoprefine.name");
		this.fontRenderer.drawString(TranslateUtilities.translate("gui.message.status.name") + ": ", 60, 45 + 23 + yOffset, 4210752);
		this.fontRenderer.drawString(displayText, 60, 45 + 34 + yOffset, 4210752);
		// this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.tileEntity.ueWattsPerTick * 20, ElectricUnit.WATT), 72, 56 + 23 + yOffset, 4210752);
		// this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE), 72, 68 + 23 + yOffset, 4210752);
		this.fontRenderer.drawString(TranslateUtilities.translate("container.inventory"), 8, this.ySize - 118 + 2 + 23, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(GuiAdvancedRefinery.refineryTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		int displayInt = this.tileEntity.getScaledOilLevel(38);
		this.drawTexturedModalRect((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 17 + 49 - displayInt, 176, 38 - displayInt, 16, displayInt);

		displayInt = this.tileEntity.getScaledFuelLevel(38);
		this.drawTexturedModalRect((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 17 + 49 - displayInt, 176 + 16, 38 - displayInt, 16, displayInt);

		List<String> oilTankDesc = new ArrayList<String>();
		oilTankDesc.add(TranslateUtilities.translate("gui.oil_tank.desc.0"));
		oilTankDesc.add(TranslateUtilities.translate("gui.oil_tank.desc.1"));
		int oilLevel = this.tileEntity.oilTank != null && this.tileEntity.oilTank.getFluid() != null ? this.tileEntity.oilTank.getFluid().amount : 0;
		int oilCapacity = this.tileEntity.oilTank != null ? this.tileEntity.oilTank.getCapacity() : 0;
		oilTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.oil.name") + ": " + oilLevel + " / " + oilCapacity);
		this.oilTankRegion.tooltipStrings = oilTankDesc;

		List<String> fuelTankDesc = new ArrayList<String>();
		fuelTankDesc.add(TranslateUtilities.translate("gui.fuel_tank.desc.4"));
		int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
		fuelTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
		this.fuelTankRegion.tooltipStrings = fuelTankDesc;

		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		// electricityDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int)
		// Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if (this.tileEntity.getEnergyStoredGC() > 0) {
			this.drawTexturedModalRect(containerWidth + 49, containerHeight + 16, 208, 0, 11, 10);
		}

		this.drawTexturedModalRect(containerWidth + 63, containerHeight + 17, 176, 38, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
	}
}
