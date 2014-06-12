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
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.evilco.mc.protocol.common.error.UnknownPacketException;
import org.evilco.mc.protocol.common.packet.IPacket;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a simple protocol registry.
 */
public class SimpleProtocolRegistry implements IProtocolRegistry {

	/**
	 * Stores a registry of packets.
	 */
	@Getter
	protected Map<Integer, Class<? extends IPacket>> packetRegistry = new HashMap<Integer, Class<? extends IPacket>> ();

	/**
	 * Stores a registry of packets (reversed).
	 */
	@Getter
	protected Map<Class<? extends IPacket>, Integer> reversePacketRegistry = new HashMap<Class<? extends IPacket>, Integer> ();

	/**
	 * Stores the corresponding protocol state.
	 */
	@Getter
	protected final ProtocolState state;

	/**
	 * Constructs a new SimpleProtocolRegistry instance.
	 * @param state The protocol state.
	 */
	public SimpleProtocolRegistry (@NonNull ProtocolState state) {
		this.state = state;
	}

	/**
	 * Copies a SimpleProtocolRegistry instance.
	 * @param state The protocol state.
	 * @param registry The original registry.
	 */
	public SimpleProtocolRegistry (@NonNull ProtocolState state, @NonNull SimpleProtocolRegistry registry) {
		this.state = state;

		this.packetRegistry = registry.getPacketRegistry ();
		this.reversePacketRegistry = registry.getReversePacketRegistry ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SneakyThrows
	public IPacket createPacket (@NonNull Class<? extends IPacket> packetClass, ByteBuf buffer) {
		// find constructor
		Constructor<? extends IPacket> constructor = packetClass.getDeclaredConstructor (ByteBuf.class);

		// create a new instance
		return constructor.newInstance (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends IPacket> getPacketClass (int packetID) throws UnknownPacketException {
		if (!this.packetRegistry.containsKey (packetID)) throw new UnknownPacketException ("Could not find packet with packet identifier 0x" + Integer.toHexString (packetID) + ".");
		return this.packetRegistry.get (packetID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPacketID (@NonNull Class<? extends IPacket> packetClass) throws UnknownPacketException {
		if (!this.reversePacketRegistry.containsKey (packetClass)) throw new UnknownPacketException ("Could not find packet registration for type " + packetClass.getName () + ".");
		return this.reversePacketRegistry.get (packetClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPacketID (@NonNull IPacket packet) throws UnknownPacketException {
		return this.getPacketID (packet.getClass ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register (int packetID, Class<? extends IPacket> packetClass) {
		this.register (packetID, packetClass, true);
	}

	/**
	 * Registers a new packet.
	 * @param packetID The packet identifier.
	 * @param packetClass The packet class.
	 * @param preventOverwrite True if overwriting shall be prevented.
	 */
	@SneakyThrows
	public void register (int packetID, @NonNull Class<? extends IPacket> packetClass, boolean preventOverwrite) {
		// prevent overwriting
		if (preventOverwrite && this.packetRegistry.containsKey (packetID)) throw new UnsupportedOperationException ("The packet identifier 0x" + Integer.toHexString (packetID) + " has already been registered and may not be overwritten.");
		if (preventOverwrite && this.reversePacketRegistry.containsKey (packetClass)) throw new UnsupportedOperationException ("The packet " + (packetClass.getName ()) + " has already been registered and may not be overwritten.");

		// overwrite
		if (this.packetRegistry.containsKey (packetID)) this.unregisterPacket (packetID);
		if (this.reversePacketRegistry.containsKey (packetClass)) this.unregisterPacket (packetClass);

		// register packet
		this.packetRegistry.put (packetID, packetClass);
		this.reversePacketRegistry.put (packetClass, packetID);
	}

	/**
	 * Un-Registers a packet.
	 * @param packetID The packet identifier.
	 * @throws UnknownPacketException
	 */
	public void unregisterPacket (int packetID) throws UnknownPacketException {
		// skip execution
		if (!this.packetRegistry.containsKey (packetID)) throw new UnknownPacketException ("Could not find packet with packet identifier " + Integer.toHexString (packetID) + ".");

		// remove entries
		this.reversePacketRegistry.remove (this.getPacketClass (packetID));
		this.packetRegistry.remove (packetID);
	}

	/**
	 * Un-Registers a packet.
	 * @param packetClass The packet class.
	 * @throws UnknownPacketException
	 */
	public void unregisterPacket (Class<? extends IPacket> packetClass) throws UnknownPacketException {
		// skip execution
		if (!this.reversePacketRegistry.containsKey (packetClass)) throw new UnknownPacketException ("Could not find packet with packet type " + packetClass.getName () + ".");

		// remove entries
		this.packetRegistry.remove (this.getPacketID (packetClass));
		this.reversePacketRegistry.remove (packetClass);
	}
}