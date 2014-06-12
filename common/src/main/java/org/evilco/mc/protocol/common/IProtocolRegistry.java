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

package org.evilco.mc.protocol.common;

import io.netty.buffer.ByteBuf;
import org.evilco.mc.protocol.common.error.PacketException;
import org.evilco.mc.protocol.common.error.UnknownPacketException;
import org.evilco.mc.protocol.common.packet.IPacket;

/**
 * Provides a registry which manages a protocol state.
 */
public interface IProtocolRegistry {

	/**
	 * Creates a new packet instance.
	 * @param packetClass The packet class.
	 * @param buffer The packet data.
	 * @return The packet instance.
	 */
	public IPacket createPacket (Class<? extends IPacket> packetClass, ByteBuf buffer) throws PacketException;

	/**
	 * Returns the packet class based on the packet identifier.
	 * @param packetID The packet identifier.
	 * @return The packet representation.
	 * @throws UnknownPacketException
	 */
	public Class<? extends IPacket> getPacketClass (int packetID) throws UnknownPacketException;

	/**
	 * Returns the packet identifier assigned to a specific packet.
	 * @param packetClass The packet class.
	 * @return The packet identifier.
	 * @throws UnknownPacketException
	 */
	public int getPacketID (Class<? extends IPacket> packetClass) throws UnknownPacketException;

	/**
	 * Returns the packet identifier assigned to a specific packet.
	 * @param packet The packet object.
	 * @return The packet identifier.
	 * @throws UnknownPacketException
	 */
	public int getPacketID (IPacket packet) throws UnknownPacketException;

	/**
	 * Returns the protocol state.
	 * @return The state.
	 */
	public ProtocolState getState ();

	/**
	 * Registers a new packet.
	 * @param packetID The packet identifier.
	 * @param packetClass The packet class.
	 * @throws UnsupportedOperationException
	 */
	public void register (int packetID, Class<? extends IPacket> packetClass) throws UnsupportedOperationException;
}