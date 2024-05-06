package com.mjr.extraplanets.command;

import java.util.ArrayList;
import java.util.List;

import com.mjr.mjrlegendslib.util.PlayerUtilties;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import micdoodle8.mods.galacticraft.api.recipe.ISchematicPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.dimension.SpaceRaceManager;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;

public class CommandUnlockSchematic extends CommandBase {

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/" + this.getCommandName() + " <player> <schematic>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public String getCommandName() {
		return "epUnlockSchematic";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(sender.getName(), true);
		if (playerBase == null) {
			return;
		}
		if (args.length == 2) {
			String username = args[0];
			EntityPlayerMP playerToAddFor;

			if (args[0].startsWith("@") || args[0].contains("-"))
				playerToAddFor = getPlayer(sender, args[0]);
			else
				playerToAddFor = PlayerUtilties.getPlayerFromUUID(MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(username).getId());

			try {
				for (ItemStack stack : SchematicRegistry.schematicItems) {
					if ((stack.getItem().getRegistryName().toString() + ":" + stack.getItemDamage()).equalsIgnoreCase(args[1])) {
						final ISchematicPage page = SchematicRegistry.getMatchingRecipeForItemStack(stack);
						SchematicRegistry.unlockNewPage(playerBase, stack);
						SpaceRaceManager.teamUnlockSchematic(playerBase, stack);
						GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_ADD_NEW_SCHEMATIC, playerToAddFor.worldObj.provider.getDimensionId(), new Object[] { page.getPageID() }), playerBase);
						String name = stack.getUnlocalizedName() + ":" + stack.getItemDamage();
						List<String> tooltips = stack.getTooltip(playerToAddFor, false);
						if (tooltips.size() >= 2)
							name = tooltips.get(1);
						playerBase.addChatMessage(new ChatComponentText(EnumColor.AQUA + "Unlocked Schematic: " + name + EnumColor.AQUA + " for " + playerToAddFor.getName()));
						playerToAddFor.addChatMessage(new ChatComponentText(EnumColor.AQUA + playerBase.getName() + " has given you Schematic: " + name));
					}
				}
			} catch (final Exception var6) {
				throw new CommandException(var6.getMessage(), new Object[0]);
			}
		}

	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> schematics = new ArrayList<>(100);

		for (ItemStack stack : SchematicRegistry.schematicItems)
			schematics.add(stack.getItem().getRegistryName().toString() + ":" + stack.getItemDamage());

		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : (args.length == 2 ? schematics : null);
	}

	@Override
	public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
		return par2 == 0;
	}
}
