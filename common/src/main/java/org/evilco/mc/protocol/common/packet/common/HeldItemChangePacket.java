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

package org.evilco.mc.protocol.common.packet.common;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;

/**
 * Represents the held item change packet.
 */
public class HeldItemChangePacket implements IPacket {

	/**
	 * Stores the selected slot number.
	 */
	@Getter
	@Setter
	protected byte slot;

	/**
	 * Constructs a new HeldItemChangePacket instance.
	 * @param slot The slot number.
	 */
	public HeldItemChangePacket (byte slot) {
		this.slot = slot;
	}

	/**
	 * Constructs a new HeldItemChangePacket instance.
	 * @param buffer The buffer.
	 */
	public HeldItemChangePacket (ByteBuf buffer) {
		this.slot = buffer.readByte ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeByte (this.slot);
	}
}