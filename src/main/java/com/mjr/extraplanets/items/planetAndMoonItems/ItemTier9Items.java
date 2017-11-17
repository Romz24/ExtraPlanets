package com.mjr.extraplanets.items.planetAndMoonItems;

import java.util.List;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.ExtraPlanets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTier9Items extends Item {
	public static final String[] names = { "tier9engine", "tier9booster", "tier9Fin", "tier9HeavyDutyPlate", "compressedTungsten", "ingotTungsten" };
	public static final String[] textureNames = { "tier9.tier9engine", "tier9.tier9booster", "tier9.tier9_fin", "tier9.tier9_heavy_duty_plate", "tier9.compressed_tungsten", "tier9.ingot_tungsten" };

	protected IIcon[] icons = new IIcon[ItemTier9Items.names.length];

	public ItemTier9Items(String assetName) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(assetName);
		this.setCreativeTab(ExtraPlanets.ItemsTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		int i = 0;

		for (final String name : ItemTier9Items.textureNames) {
			this.icons[i++] = iconRegister.registerIcon(Constants.TEXTURE_PREFIX + name);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return this.getUnlocalizedName() + "." + ItemTier9Items.names[itemStack.getItemDamage()];
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		if (this.icons.length > damage) {
			return this.icons[damage];
		}

		return super.getIconFromDamage(damage);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < ItemTier9Items.names.length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack != null && itemStack.getItemDamage() == 3) {
			if (player.worldObj.isRemote) {
				list.add(EnumChatFormatting.GRAY + GCCoreUtil.translate("tier9.heavyDutyPlate.name"));
			}
		}
	}

}
