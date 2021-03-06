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
 * Represents the keep alive (ping) packet.
 */
public class KeepAlivePacket implements IPacket {

	/**
	 * The keep alive ID.
	 */
	@Getter
	@Setter
	private int keepAliveID;

	/**
	 * Constructs a new KeepAlivePacket.
	 * @param keepAliveID The data.
	 */
	public KeepAlivePacket (int keepAliveID) {
		this.keepAliveID = keepAliveID;
	}

	/**
	 * Constructs a new KeepAlivePacket.
	 * @param buffer The packet buffer.
	 */
	public KeepAlivePacket (ByteBuf buffer) {
		this.keepAliveID = buffer.readInt ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) {
		out.writeInt (this.keepAliveID);
	}
}