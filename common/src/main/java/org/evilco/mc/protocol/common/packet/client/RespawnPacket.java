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
import lombok.NonNull;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;
import org.evilco.mc.protocol.common.utility.PacketDataUtility;

/**
 * Represents the respawn packet.
 */
public class RespawnPacket implements IPacket {

	/**
	 * Stores the difficulty (0 - peaceful, 1 - easy, 2 - normal, 3 - hard).
	 */
	@Getter
	@Setter
	private short difficulty;

	/**
	 * Stores the dimension to respawn in (-1 - nether, 0 - overworld, 1 - end).
	 */
	@Getter
	@Setter
	private int dimension;

	/**
	 * Stores the gamemode (0 - survival, 1 - creative, 2 - adventure).
	 */
	@Getter
	@Setter
	private short gamemode;

	/**
	 * Stores the level type.
	 */
	@Getter
	@Setter
	private String levelType;

	/**
	 * Constructs a new RespawnPacket instance.
	 * @param dimension The dimension.
	 * @param difficulty The difficulty.
	 * @param gamemode The gamemode.
	 * @param levelType The level type.
	 */
	public RespawnPacket (int dimension, short difficulty, short gamemode, @NonNull String levelType) {
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.gamemode = gamemode;
		this.levelType = levelType;
	}

	/**
	 * Constructs a new RespawnPacket instance.
	 * @param buffer The packet buffer.
	 */
	public RespawnPacket (ByteBuf buffer) {
		this.dimension = buffer.readInt ();
		this.difficulty = buffer.readUnsignedByte ();
		this.gamemode = buffer.readUnsignedByte ();
		this.levelType = PacketDataUtility.readString (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeInt (this.dimension);
		out.writeByte (this.difficulty);
		out.writeByte (this.gamemode);
		PacketDataUtility.writeString (out, this.levelType);
	}
}