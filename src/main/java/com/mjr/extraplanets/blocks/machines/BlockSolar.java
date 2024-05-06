package com.mjr.extraplanets.blocks.machines;

import java.util.List;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.tileEntities.machines.TileEntitySolar;
import com.mjr.mjrlegendslib.util.TranslateUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;

public class BlockSolar extends BlockTileGC implements IShiftDescription, IPartialSealableBlock, ISortableBlock {
	public static final int HYBRID_METADATA = 0;
	public static final int ULTIMATE_METADATA = 4;

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<EnumSolarType> TYPE = PropertyEnum.create("type", EnumSolarType.class);

	public enum EnumSolarType implements IStringSerializable {
		HYBRID_SOLAR(0, "hybrid_solar"), ULTIMATE_SOLAR(1, "ultimate_solar"); // 3 for backwards compatibility

		private final int meta;
		private final String name;

		EnumSolarType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		public static EnumSolarType byMetadata(int meta) {
			return values()[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	public BlockSolar(String name) {
		super(Material.iron);
		this.setHardness(1.0F);
		this.setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, BlockSolar.HYBRID_METADATA));
		list.add(new ItemStack(item, 1, BlockSolar.ULTIMATE_METADATA));
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return ExtraPlanets.BlocksTab;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		for (int y = 1; y <= 2; y++) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos posAt = pos.add(y == 2 ? x : 0, y, y == 2 ? z : 0);
					Block block = world.getBlockState(posAt).getBlock();

					if (block.getMaterial() != Material.air && !block.isReplaceable(world, posAt)) {
						return false;
					}
				}
			}
		}

		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				BlockPos posAt = pos.add(x, 0, z);
				Block block = world.getBlockState(posAt).getBlock();

				if (block == this) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemStack) {
		final int angle = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int change = EnumFacing.getHorizontal(angle).getOpposite().getHorizontalIndex();

		if (itemStack.getItemDamage() >= ULTIMATE_METADATA) {
			change += ULTIMATE_METADATA;
		} else if (itemStack.getItemDamage() >= HYBRID_METADATA) {
			change += HYBRID_METADATA;
		}

		world.setBlockState(pos, getStateFromMeta(change), 3);

		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof TileEntitySolar) {
			((TileEntitySolar) tile).onCreate(world, pos);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntity var9 = world.getTileEntity(pos);

		if (var9 instanceof TileEntitySolar) {
			((TileEntitySolar) var9).onDestroy(var9);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onUseWrench(World world, BlockPos pos, EntityPlayer entityPlayer, EnumFacing side, float hitX, float hitY, float hitZ) {
		int metadata = getMetaFromState(world.getBlockState(pos));
		int change = world.getBlockState(pos).getValue(FACING).rotateY().getHorizontalIndex();

		world.setBlockState(pos, this.getStateFromMeta(metadata - (metadata % 4) + change), 3);

		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileBaseUniversalElectrical) {
			((TileBaseUniversalElectrical) te).updateFacing();
		}

		return true;
	}

	@Override
	public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(ExtraPlanets.instance, -1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public int damageDropped(IBlockState state) {
		if (getMetaFromState(state) >= BlockSolar.ULTIMATE_METADATA) {
			return BlockSolar.ULTIMATE_METADATA;
		} else {
			return BlockSolar.HYBRID_METADATA;
		}
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		if (getMetaFromState(state) >= BlockSolar.ULTIMATE_METADATA) {
			return new TileEntitySolar(2);
		} else {
			return new TileEntitySolar(1);
		}
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public String getShiftDescription(int meta) {
		switch (meta) {
			case HYBRID_METADATA:
				return TranslateUtilities.translate("tile.solar_hybrid.description");
			case ULTIMATE_METADATA:
				return TranslateUtilities.translate("tile.solar_ultimate.description");
		}
		return "";
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSealed(World world, BlockPos pos, EnumFacing direction) {
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta % 4);
		EnumSolarType type = EnumSolarType.byMetadata((int) Math.floor(meta / 4.0));
		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(TYPE, type);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() + state.getValue(TYPE).getMeta() * 4;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, FACING, TYPE);
	}

	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.MACHINE;
	}
}
