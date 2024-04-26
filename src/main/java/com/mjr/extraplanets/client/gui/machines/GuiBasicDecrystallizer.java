package com.mjr.extraplanets.client.gui.machines;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.inventory.machines.ContainerBasicDecrystallizer;
import com.mjr.extraplanets.tileEntities.machines.TileEntityBasicDecrystallizer;
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
public class GuiBasicDecrystallizer extends GuiContainerGC {
	private static final ResourceLocation decrystallizerTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/decrystallizer.png");

	private final TileEntityBasicDecrystallizer tileEntity;

	private GuiElementInfoRegion outputTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 62, (this.height - this.ySize) / 2 + 16, 56, 9, new ArrayList<String>(), this.width, this.height, this);

	public GuiBasicDecrystallizer(InventoryPlayer inventoryPlayer, TileEntityBasicDecrystallizer tileEntity) {
		super(new ContainerBasicDecrystallizer(inventoryPlayer, tileEntity, MCUtilities.getClient().player));
		this.tileEntity = tileEntity;
		this.ySize = 168;
	}

	@Override
	public void initGui() {
		super.initGui();
		List<String> batterySlotDesc = new ArrayList<String>();
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.0"));
		batterySlotDesc.add(TranslateUtilities.translate("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 6, 18, 18, batterySlotDesc, this.width, this.height, this));
		List<String> outputTankDesc = new ArrayList<String>();
		outputTankDesc.add(TranslateUtilities.translate("gui.fuel_tank.desc.4"));
		int fuelLevel = this.tileEntity.outputTank != null && this.tileEntity.outputTank.getFluid() != null ? this.tileEntity.outputTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.outputTank != null ? this.tileEntity.outputTank.getCapacity() : 0;
		outputTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
		this.outputTankRegion.tooltipStrings = outputTankDesc;
		this.outputTankRegion.xPosition = (this.width - this.xSize) / 2 + 153;
		this.outputTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
		this.outputTankRegion.parentWidth = this.width;
		this.outputTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.outputTankRegion);
		List<String> fuelSlotDesc = new ArrayList<String>();
		fuelSlotDesc.add(TranslateUtilities.translate("gui.salt_water_output.desc.0"));
		fuelSlotDesc.add(TranslateUtilities.translate("gui.salt_water_output.desc.1"));
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
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GCCoreUtil.drawStringCentered(this.tileEntity.getName(), this.xSize / 2, 5, 4210752, this.fontRenderer);
		String displayText = "";
		int yOffset = -10;

		if (this.tileEntity.canProcess()) {
			displayText = EnumColor.BRIGHT_GREEN + TranslateUtilities.translate("gui.status.decrystallizing.name");
		} else {
			displayText = EnumColor.RED + TranslateUtilities.translate("gui.status.idle.name");
		}

		this.fontRenderer.drawString(TranslateUtilities.translate("gui.message.status.name") + ": " + displayText, 20, 45 + 23 + yOffset, 4210752);
		this.fontRenderer.drawString(TranslateUtilities.translate("container.inventory"), 8, this.ySize - 118 + 2 + 23, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(GuiBasicDecrystallizer.decrystallizerTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		int displayInt = this.tileEntity.getScaledFuelLevel(38);
		this.drawTexturedModalRect((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 17 + 49 - displayInt, 176 + 16, 38 - displayInt, 16, displayInt);

		List<String> outputTankDesc = new ArrayList<String>();
		outputTankDesc.add(TranslateUtilities.translate("gui.salt_water_tank.desc.4"));
		int fuelLevel = this.tileEntity.outputTank != null && this.tileEntity.outputTank.getFluid() != null ? this.tileEntity.outputTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.outputTank != null ? this.tileEntity.outputTank.getCapacity() : 0;
		outputTankDesc.add(EnumColor.YELLOW + TranslateUtilities.translate("gui.message.salt_water.name") + ": " + fuelLevel + " / " + fuelCapacity);
		this.outputTankRegion.tooltipStrings = outputTankDesc;

		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(TranslateUtilities.translate("gui.energy_storage.desc.0"));
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if (this.tileEntity.getEnergyStoredGC() > 0) {
			this.drawTexturedModalRect(containerWidth + 49, containerHeight + 16, 208, 0, 11, 10);
		}

		this.drawTexturedModalRect(containerWidth + 63, containerHeight + 17, 176, 38, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
	}
}