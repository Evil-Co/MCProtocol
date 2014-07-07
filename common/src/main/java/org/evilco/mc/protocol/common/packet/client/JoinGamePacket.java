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
 * Represents a join game packet.
 */
public class JoinGamePacket implements IPacket {

	/**
	 * Stores the dimension ID (-1 - nether, 0 - overworld, 1 - end).
	 */
	@Getter
	@Setter
	private byte dimension;

	/**
	 * Stores the difficulty (0 - Peaceful, 1 - Easy, 2 - Normal, 3 - Hard).
	 */
	@Getter
	@Setter
	private short difficulty;

	/**
	 * Stores the player entity ID.
	 */
	@Getter
	@Setter
	private int entityID;

	/**
	 * Stores the player gamemode.
	 */
	@Getter
	@Setter
	private short gamemode;

	/**
	 * Stores the level type (default, flat, largeBiomes, amplified, default_1_1).
	 */
	@Getter
	@Setter
	@NonNull
	private String levelType;

	/**
	 * Stores the maximum amount of players.
	 */
	@Getter
	@Setter
	private short maxPlayers;

	/**
	 * Constructs a new JoinGamePacket instance.
	 * @param entityID The entitiyID of the player entity.
	 * @param gamemode The player gamemode.
	 * @param dimension The dimension to spawn in.
	 * @param difficulty The world difficulty.
	 * @param maxPlayers The maximum amount of players.
	 * @param levelType The level type.
	 */
	public JoinGamePacket (int entityID, short gamemode, byte dimension, short difficulty, short maxPlayers, @NonNull String levelType) {
		this.entityID = entityID;
		this.gamemode = gamemode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.maxPlayers = maxPlayers;
		this.levelType = levelType;
	}

	/**
	 * Constructs a new JoinGamePacket instance.
	 * @param buffer The packet buffer.
	 */
	public JoinGamePacket (ByteBuf buffer) {
		this.entityID = buffer.readInt ();
		this.gamemode = buffer.readUnsignedByte ();
		this.dimension = buffer.readByte ();
		this.difficulty = buffer.readUnsignedByte ();
		this.maxPlayers = buffer.readUnsignedByte ();
		this.levelType = PacketDataUtility.readString (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) {
		out.writeInt (this.entityID);
		out.writeByte (this.gamemode);
		out.writeByte (this.dimension);
		out.writeByte (this.difficulty);
		out.writeByte (this.maxPlayers);
		PacketDataUtility.writeString (out, this.levelType);
	}
}