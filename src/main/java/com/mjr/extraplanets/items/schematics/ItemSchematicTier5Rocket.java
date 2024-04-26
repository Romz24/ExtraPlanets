package com.mjr.extraplanets.items.schematics;

import java.util.List;

import javax.annotation.Nullable;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.recipe.ISchematicItem;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.entities.EntityHangingSchematic;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;

public class ItemSchematicTier5Rocket extends ItemHangingEntity implements ISchematicItem, ISortableItem {
	private static int indexOffset = 0;

	public ItemSchematicTier5Rocket(String name) {
		super(EntityHangingSchematic.class);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ExtraPlanets.ItemsTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumColor.GREY + TranslateUtilities.translate("schematic.tier5.rocket.name"));
	}

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.SCHEMATIC;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		BlockPos blockpos = pos.offset(facing);

		if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && playerIn.canPlayerEdit(blockpos, facing, stack)) {
			EntityHangingSchematic entityhanging = this.createEntity(world, blockpos, facing, this.getIndex(stack.getItemDamage()));

			if (entityhanging != null && entityhanging.onValidSurface()) {
				if (!world.isRemote) {
					entityhanging.playPlaceSound();
					world.spawnEntity(entityhanging);
					entityhanging.sendToClient(world, blockpos);
				}

				stack.shrink(1);
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	private EntityHangingSchematic createEntity(World world, BlockPos pos, EnumFacing clickedSide, int index) {
		return new EntityHangingSchematic(world, pos, clickedSide, index);
	}

	/**
	 * Higher tiers should use this form and make sure they have set up the indexOffset correctly in registerSchematicItems()
	 */
	protected int getIndex(int damage) {
		return damage + indexOffset;
	}

	/**
	 * Make sure the number of these will match the index values
	 */
	public static void registerSchematicItems() {
		indexOffset = SchematicRegistry.registerSchematicItem(new ItemStack(ExtraPlanets_Items.TIER_5_SCHEMATIC));
	}

	/**
	 * Make sure the order of these will match the index values
	 */
	@SideOnly(value = Side.CLIENT)
	public static void registerTextures() {
		SchematicRegistry.registerTexture(new ResourceLocation(Constants.ASSET_PREFIX, "textures/items/tier5_schematic_rocket.png"));
	}
}
