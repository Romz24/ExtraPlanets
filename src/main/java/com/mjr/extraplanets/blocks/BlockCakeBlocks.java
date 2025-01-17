package com.mjr.extraplanets.blocks;

import java.util.Random;

import com.mjr.extraplanets.ExtraPlanets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;

public class BlockCakeBlocks extends Block implements IDetectableResource, ISortableBlock {
	public static final PropertyEnum<EnumBlockBasic> BASIC_TYPE = PropertyEnum.create("basictypecakeblocks", EnumBlockBasic.class);

	public enum EnumBlockBasic implements IStringSerializable {
		CAKE_BLOCK(0, "cake_block"), CAKE_BLOCK_RED_VERVET(1, "cake_block_red_velvet"), CAKE_BLOCK_CHOCOLATE(2, "cake_block_chocolate"), WHITE_ICING_RED_DOTS(3, "white_icing_red_dots"), WHITE_ICING_GREEN_DOTS(4,
				"white_icing_green_dots"), WHITE_ICING_PINK_DOTS(5, "white_icing_pink_dots"), WHITE_ICING_ORANGE_DOTS(6, "white_icing_orange_dots"), COOKIE_ROCKS(7, "cookie_rocks");

		private final int meta;
		private final String name;

		private EnumBlockBasic(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		public static EnumBlockBasic byMetadata(int meta) {
			return values()[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	public BlockCakeBlocks(String name) {
		super(Material.ROCK);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ExtraPlanets.BlocksTab);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		return 1.5F;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBasic.COOKIE_ROCKS))
			return Items.COOKIE;
		return Item.getItemFromBlock(this);
	}

	@Override
	public int damageDropped(IBlockState state) {
		if (state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBasic.COOKIE_ROCKS))
			return 0;
		int meta = state.getBlock().getMetaFromState(state);
		return meta;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		if (state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBasic.COOKIE_ROCKS))
			return 6;
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (EnumBlockBasic blockBasic : EnumBlockBasic.values()) {
			list.add(new ItemStack(this, 1, blockBasic.getMeta()));
		}
	}

	@Override
	public boolean isValueable(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBasic.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BASIC_TYPE).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC_TYPE);
	}

	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.DECORATION;
	}
}
