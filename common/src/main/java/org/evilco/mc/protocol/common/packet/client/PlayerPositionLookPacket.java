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
 * Represents the player position and look packet.
 */
public class PlayerPositionLookPacket implements IPacket {

	/**
	 * Indicates whether the player is on ground.
	 */
	@Getter
	@Setter
	private boolean onGround;

	/**
	 * Stores the player pitch.
	 */
	@Getter
	@Setter
	private float pitch;

	/**
	 * Stores the X-coordinate.
	 */
	@Getter
	@Setter
	private double x;

	/**
	 * Stores the Y-coordinate.
	 */
	@Getter
	@Setter
	private double y;

	/**
	 * Stores the player yaw.
	 */
	@Getter
	@Setter
	private float yaw;

	/**
	 * Stores the Z-coordinate.
	 */
	@Getter
	@Setter
	private double z;

	/**
	 * Constructs a new PlayerPositionLookPacket instance.
	 * @param x The X-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate.
	 * @param yaw The player yaw.
	 * @param pitch The player pitch.
	 * @param onGround Indicates whether the player is standing on the ground.
	 */
	public PlayerPositionLookPacket (double x, double y, double z, float yaw, float pitch, boolean onGround) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.yaw = yaw;
		this.pitch = pitch;

		this.onGround = onGround;
	}

	/**
	 * Constructs a new PlayerPositionLookPacket instance.
	 * @param buffer The packet buffer.
	 */
	public PlayerPositionLookPacket (ByteBuf buffer) {
		this.x = buffer.readDouble ();
		this.y = buffer.readDouble ();
		this.z = buffer.readDouble ();

		this.yaw = buffer.readFloat ();
		this.pitch = buffer.readFloat ();

		this.onGround = buffer.readBoolean ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeDouble (this.x);
		out.writeDouble (this.y);
		out.writeDouble (this.z);

		out.writeFloat (this.yaw);
		out.writeFloat (this.pitch);

		out.writeBoolean (this.onGround);
	}
}