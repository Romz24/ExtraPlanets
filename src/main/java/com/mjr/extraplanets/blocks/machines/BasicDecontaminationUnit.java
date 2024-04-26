package com.mjr.extraplanets.blocks.machines;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.tileEntities.machines.TileEntityBasicDecontaminationUnit;
import com.mjr.mjrlegendslib.util.PlayerUtilties;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;

public class BasicDecontaminationUnit extends BlockTileGC implements ISortableBlock {
	protected static final AxisAlignedBB AABB_UNIT = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.45F, 1.0F);

	protected BasicDecontaminationUnit(String name) {
		super(Material.IRON);
		this.blockHardness = 3.0F;
		this.setUnlocalizedName(name);
		this.setSoundType(SoundType.METAL);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return ExtraPlanets.BlocksTab;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBasicDecontaminationUnit();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB_UNIT;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);

		TileEntity tile = world.getTileEntity(pos);

		boolean validSpot = true;

		for (int x = -2; x <= 2; x++) {
			for (int y = 0; y < 4; y++) {
				for (int z = -1; z <= 2; z++) {
					if (!(x == 0 && y == 0 && z == 0)) {
						IBlockState stateAt = world.getBlockState(pos.add(x, y, z));

						if (!stateAt.getMaterial().isReplaceable()) {
							validSpot = false;
						}
					}
				}
			}
		}

		if (!validSpot) {
			world.setBlockToAir(pos);

			if (placer instanceof EntityPlayer) {
				if (!world.isRemote) {
					PlayerUtilties.sendMessage((EntityPlayer) placer, "" + EnumColor.RED + TranslateUtilities.translate("gui.warning.noroom"));
				}
				((EntityPlayer) placer).inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(this), 1, 0));
			}

			return;
		}

		if (tile instanceof TileEntityBasicDecontaminationUnit) {
			((TileEntityBasicDecontaminationUnit) tile).onCreate(world, pos);
		}
	}

	@Override
	public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		return ((IMultiBlock) world.getTileEntity(pos)).onActivated(playerIn);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntity tileAt = world.getTileEntity(pos);

		int fakeBlockCount = 0;

		for (int x = -1; x <= 1; x++) {
			for (int y = 0; y < 3; y += 2) {
				for (int z = -1; z <= 1; z++) {
					if (!(x == 0 && y == 0 && z == 0)) {
						if (world.getBlockState(pos.add(x, y, z)).getBlock() == ExtraPlanets_Blocks.FAKE_BLOCK_DECONTAMINATION_UNIT) {
							fakeBlockCount++;
						}
					}
				}
			}
		}

		if (tileAt instanceof TileEntityBasicDecontaminationUnit) {
			if (fakeBlockCount > 0) {
				((TileEntityBasicDecontaminationUnit) tileAt).onDestroy(tileAt);
			}
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.MACHINE;
	}
}
