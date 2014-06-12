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
import org.evilco.mc.protocol.common.error.PacketException;

/**
 * Provides a base implementation for packets.
 */
public abstract class AbstractPacket implements IPacket {

	/**
	 * Stores the packet buffer.
	 */
	protected ByteBuf buffer = null;

	/**
	 * Defines whether the package has been parsed.
	 */
	@Getter
	protected boolean parsed = false;

	/**
	 * Constructs a new empty AbstractPacket instance.
	 */
	protected AbstractPacket () { }

	/**
	 * Constructs a new AbstractPacket instance from a packet buffer.
	 * @param buffer The buffer.
	 */
	public AbstractPacket (ByteBuf buffer) throws PacketException {
		this.buffer = buffer;
	}

	/**
	 * Reads the packet data.
	 * @param buffer The buffer.
	 */
	public void readPacketData (ByteBuf buffer) throws PacketException {
		this.parsed = true;
	}
}