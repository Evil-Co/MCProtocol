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
import lombok.NonNull;
import org.evilco.mc.protocol.common.error.PacketException;
import org.evilco.mc.protocol.common.packet.AbstractPacket;
import org.evilco.mc.protocol.common.packet.IPacket;

/**
 * Provides a more automated protocol registry.
 */
public class AutomatedProtocolRegistry extends SimpleProtocolRegistry {

	/**
	 * Constructs a new empty AutomatedProtocolRegistry instance.
	 * @param state The protocol state.
	 */
	public AutomatedProtocolRegistry (@NonNull ProtocolState state) {
		super (state);
	}

	/**
	 * Copies an AutomatedProtocolRegistry instance.
	 * @param state The protocol state.
	 * @param registry The original protocol registry.
	 */
	public AutomatedProtocolRegistry (@NonNull ProtocolState state, @NonNull SimpleProtocolRegistry registry) {
		super (state, registry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPacket createPacket (@NonNull Class<? extends IPacket> packetClass, ByteBuf buffer) throws PacketException {
		// get packet instance
		IPacket packet = super.createPacket (packetClass, buffer);

		// read data
		if (packet instanceof AbstractPacket) ((AbstractPacket) packet).readPacketData ();

		// return packet
		return packet;
	}
}