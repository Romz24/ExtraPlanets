package com.mjr.extraplanets.planets.Mercury.worldgen.dungeon;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.blocks.BlockTier1TreasureChest;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;

public class RoomChestMercury extends RoomEmptyMercury
{
    public RoomChestMercury()
    {
    }

    public RoomChestMercury(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random rand, StructureBoundingBox boundingBox)
    {
        if (super.addComponentParts(worldIn, rand, boundingBox))
        {
            int chestX = this.sizeX / 2;
            int chestY = 1;
            int chestZ = this.sizeZ / 2;
            this.setBlockState(worldIn, Blocks.CHEST.getDefaultState().withProperty(BlockTier1TreasureChest.FACING, this.getDirection().getOpposite()), chestX, chestY, chestZ, boundingBox);

            BlockPos blockpos = new BlockPos(this.getXWithOffset(chestX, chestZ), this.getYWithOffset(chestY), this.getZWithOffset(chestX, chestZ));
            TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(blockpos);

            if (chest != null)
            {
                ResourceLocation chesttype = RoomTreasureMercuryBase.MOONCHEST;
                if (worldIn.provider instanceof IGalacticraftWorldProvider)
                {
                    chesttype = ((IGalacticraftWorldProvider)worldIn.provider).getDungeonChestType();
                }
                chest.setLootTable(chesttype, rand.nextLong());
            }

            return true;
        }

        return false;
    }
}