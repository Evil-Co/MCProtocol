/*
 * Copyright (C) 2014 Johannes Donath <johannesd@evil-co.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.evilco.mc.protocol.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.evilco.mc.protocol.common.IProtocolRegistry;
import org.evilco.mc.protocol.common.ProtocolState;
import org.evilco.mc.protocol.common.packet.AbstractPacket;
import org.evilco.mc.protocol.common.utility.PacketDataUtility;

import java.util.List;

/**
 * Provides a netty codec which allows de- and en-coding via POJOs.
 */
public class MinecraftCodec extends ByteToMessageCodec<AbstractPacket> {

	/**
	 * Defines the protocol registry channel attribute.
	 */
	public static final AttributeKey<ProtocolState> CHANNEL_ATTRIBUTE_REGISTRY = AttributeKey.valueOf ("ProtocolState");

	/**
	 * Stores the active codec mode.
	 */
	protected final CodecMode mode;

	/**
	 * Constructs a new MinecraftCodec instance.
	 * @param mode The codec mode.
	 */
	public MinecraftCodec (CodecMode mode) {
		this.mode = mode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
		// write packetID
		PacketDataUtility.writeVariableInteger (out, this.getOutboundRegistry (ctx.channel ()).getPacketID (msg));

		// write data
		msg.write (out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// read packetID
		int packetID = PacketDataUtility.readVariableInteger (in);

		// construct a new packet
		out.add (this.getInboundRegistry (ctx.channel ()).createPacket (this.getInboundRegistry (ctx.channel ()).getPacketClass (packetID), in));
	}

	/**
	 * Returns the inbound registry.
	 * @param channel The channel.
	 * @return The registry.
	 */
	public IProtocolRegistry getInboundRegistry (Channel channel) {
		return (this.mode == CodecMode.SERVER ? getProtocolState (channel).getInbound () : getProtocolState (channel).getOutbound ());
	}

	/**
	 * Returns the outbound registry.
	 * @param channel The channel.
	 * @return The registry.
	 */
	public IProtocolRegistry getOutboundRegistry (Channel channel) {
		return (this.mode == CodecMode.SERVER ? getProtocolState (channel).getOutbound () : getProtocolState (channel).getInbound ());
	}

	/**
	 * Returns the channel state.
	 * @param channel The channel.
	 * @return The active channel state.
	 */
	public static ProtocolState getProtocolState (Channel channel) {
		return getProtocolStateAttribute (channel).get ();
	}

	/**
	 * Returns the protocol state attribute.
	 * @param channel The channel.
	 * @return The state attribute.
	 */
	public static Attribute<ProtocolState> getProtocolStateAttribute (Channel channel) {
		return channel.attr (CHANNEL_ATTRIBUTE_REGISTRY);
	}

	/**
	 * Sets a new protocol state.
	 * @param channel The channel.
	 * @param state The state.
	 */
	public static void setChannelAttribute (Channel channel, ProtocolState state) {
		getProtocolStateAttribute (channel).set (state);
	}
}