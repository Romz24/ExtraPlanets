package com.mjr.extraplanets.blocks.planetAndMoonBlocks.Kepler22b;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKepler22bMapleTreeLeaves2 extends BlockLeaves {
	public static final PropertyEnum<BlockKepler22bMapleTreeLeaves2.EnumType> VARIANT = PropertyEnum.<BlockKepler22bMapleTreeLeaves2.EnumType> create("variant", BlockKepler22bMapleTreeLeaves2.EnumType.class);

	public static enum EnumType implements IStringSerializable {
		MAPLE_GREEN(0, "maple_green_leaf", MapColor.GREEN), MAPLE_BROWN(1, "maple_brown_leaf", MapColor.BROWN);

		private static final BlockKepler22bMapleTreeLeaves2.EnumType[] META_LOOKUP = new BlockKepler22bMapleTreeLeaves2.EnumType[values().length];
		private final int meta;
		private final String name;
		private final MapColor map_color;

		private EnumType(int meta, String name, MapColor map_color) {
			this.meta = meta;
			this.name = name;
			this.map_color = map_color;
		}

		public int getMetadata() {
			return this.meta;
		}

		public MapColor func_181070_c() {
			return this.map_color;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static BlockKepler22bMapleTreeLeaves2.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

		static {
			for (BlockKepler22bMapleTreeLeaves2.EnumType blockleafs$enumtype : values()) {
				META_LOOKUP[blockleafs$enumtype.getMetadata()] = blockleafs$enumtype;
			}
		}
	}

	public BlockKepler22bMapleTreeLeaves2() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockKepler22bMapleTreeLeaves2.EnumType.MAPLE_GREEN).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.setCreativeTab(ExtraPlanets.BlocksTab);
	}

	@Override
	protected int getSaplingDropChance(IBlockState state) {
		return super.getSaplingDropChance(state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, BlockKepler22bMapleTreeLeaves2.EnumType.MAPLE_GREEN.getMetadata()));
		list.add(new ItemStack(this, 1, BlockKepler22bMapleTreeLeaves2.EnumType.MAPLE_BROWN.getMetadata()));
	}

	protected ItemStack createStackedBlock(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).getMetadata());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata((meta & 3) % 4)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(VARIANT).getMetadata();

		if (!state.getValue(DECAYABLE).booleanValue()) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY).booleanValue()) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT, CHECK_DECAY, DECAYABLE });
	}

	@Override
	public int damageDropped(IBlockState state) {
		int incomingMeta = state.getValue(VARIANT).getMetadata();
		switch (incomingMeta) {
			case 0:
			default:
				return BlockKepler22bMapleTreeSapling.EnumType.MAPLE_GREEN.getMetadata();
			case 1:
				return BlockKepler22bMapleTreeSapling.EnumType.MAPLE_BROWN.getMetadata();
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return Blocks.LEAVES.isOpaqueCube(state);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return Blocks.LEAVES.getBlockLayer();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !this.leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(state, blockAccess, pos, side);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		return new java.util.ArrayList<ItemStack>(java.util.Arrays.asList(new ItemStack(this, 1, state.getValue(VARIANT).getMetadata())));
	}

	@Override
	public net.minecraft.block.BlockPlanks.EnumType getWoodType(int meta) {
		return null;
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ExtraPlanets_Blocks.KEPLER22B_MAPLE_SAPLING);
	}
}