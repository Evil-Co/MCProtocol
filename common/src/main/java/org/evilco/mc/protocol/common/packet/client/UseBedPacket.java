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

package org.evilco.mc.protocol.common.packet.client;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;

/**
 * Represents the use bed packet.
 */
public class UseBedPacket implements IPacket {

	/**
	 * The entity ID.
	 */
	@Getter
	@Setter
	private int entityID;

	/**
	 * The X-coordinate.
	 */
	@Getter
	@Setter
	private int x;

	/**
	 * The Y-coordinate.
	 */
	@Getter
	@Setter
	private short y;

	/**
	 * The Z-coordinate.
	 */
	@Getter
	@Setter
	private int z;

	/**
	 * Constructs a new UseBedPacket instance.
	 * @param entityID The entityID.
	 * @param x The X-coordinate.
	 * @param y The Y-coordinate.
	 * @param z The Z-coordinate.
	 */
	public UseBedPacket (int entityID, int x, short y, int z) {
		this.entityID = entityID;

		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a new UseBadPacket instance.
	 * @param buffer The packet buffer.
	 */
	public UseBedPacket (ByteBuf buffer) {
		this.entityID = buffer.readInt ();

		this.x = buffer.readInt ();
		this.y = buffer.readUnsignedByte ();
		this.z = buffer.readInt ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeInt (this.entityID);

		out.writeInt (this.x);
		out.writeByte (this.y);
		out.writeInt (this.z);
	}
}