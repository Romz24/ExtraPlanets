package com.mjr.extraplanets.client.gui.rockets;

import org.lwjgl.opengl.GL11;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.inventory.rockets.ContainerSchematicTier5Rocket;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiPositionedContainer;

public class GuiSchematicTier5Rocket extends GuiPositionedContainer implements ISchematicResultPage {
	private static final ResourceLocation rocketBenchTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/rocketbench_tier5.png");

	private int pageIndex;

	public GuiSchematicTier5Rocket(InventoryPlayer inventoryPlayer, BlockPos pos) {
		super(new ContainerSchematicTier5Rocket(inventoryPlayer, pos), pos);
		this.ySize = 238;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 130, this.height / 2 - 110, 40, 20, TranslateUtilities.translate("gui.button.back.name")));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 130, this.height / 2 - 110 + 25, 40, 20, TranslateUtilities.translate("gui.button.next.name")));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			switch (par1GuiButton.id) {
			case 0:
				SchematicRegistry.flipToLastPage(this, this.pageIndex);
				break;
			case 1:
				SchematicRegistry.flipToNextPage(this, this.pageIndex);
				break;
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(TranslateUtilities.translate("schematic.rocket.t5.name"), 7, -20 + 27, 4210752);
		this.fontRenderer.drawString(TranslateUtilities.translate("container.inventory"), 8, 220 - 104 + 2 + 27, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GuiSchematicTier5Rocket.rocketBenchTexture);
		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void setPageIndex(int index) {
		this.pageIndex = index;
	}
}