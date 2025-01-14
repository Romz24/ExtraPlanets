package com.mjr.extraplanets.network;

import java.util.EnumMap;

import com.mjr.extraplanets.Constants;
import com.mjr.mjrlegendslib.network.IPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import micdoodle8.mods.galacticraft.core.util.GCLog;

public class ExtraPlanetsChannelHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
	private EnumMap<Side, FMLEmbeddedChannel> channels;

	private ExtraPlanetsChannelHandler() {
		this.addDiscriminator(0, PacketSimpleEP.class);
	}

	public static ExtraPlanetsChannelHandler init() {
		ExtraPlanetsChannelHandler channelHandler = new ExtraPlanetsChannelHandler();
		channelHandler.channels = NetworkRegistry.INSTANCE.newChannel(Constants.modID, channelHandler, new ExtraPlanetsPacketHandler());
		return channelHandler;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
		msg.encodeInto(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IPacket msg) {
		msg.decodeInto(source);
	}

	/**
	 * Send this message to everyone.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message
	 *            The message to send
	 */
	public void sendToAll(IPacket message) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeOutbound(message);
	}

	/**
	 * Send this message to the specified player.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message
	 *            The message to send
	 * @param player
	 *            The player to send it to
	 */
	public void sendTo(IPacket message, EntityPlayerMP player) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		this.channels.get(Side.SERVER).writeOutbound(message);
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message
	 *            The message to send
	 * @param point
	 *            The {@link net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint} around which to send
	 */
	public void sendToAllAround(IPacket message, NetworkRegistry.TargetPoint point) {
		try {
			this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			this.channels.get(Side.SERVER).writeOutbound(message);
		} catch (Exception e) {
			GCLog.severe("Forge error when sending network packet to nearby players - this is not a Galacticraft bug, does another mod make fake players?");
			e.printStackTrace();
		}
	}

	/**
	 * Send this message to everyone within the supplied dimension.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message
	 *            The message to send
	 * @param dimensionID
	 *            The dimension id to target
	 */
	public void sendToDimension(IPacket message, int dimensionID) {
		try {
			this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
			this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionID);
			this.channels.get(Side.SERVER).writeOutbound(message);
		} catch (Exception e) {
			GCLog.severe("Forge error when sending network packet to all players in dimension - this is not a Galacticraft bug, does another mod make fake players?");
			e.printStackTrace();
		}
	}

	/**
	 * Send this message to the server.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message
	 *            The message to send
	 */
	public void sendToServer(IPacket message) {
		if (FMLCommonHandler.instance().getSide() != Side.CLIENT) {
			return;
		}
		this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channels.get(Side.CLIENT).writeOutbound(message);
	}
}
