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
 * Represents the update health packet.
 */
public class UpdateHealthPacket implements IPacket {

	/**
	 * Stores the player health.
	 */
	@Getter
	@Setter
	private float health;

	/**
	 * Stores the player food level.
	 */
	@Getter
	@Setter
	protected short food;

	/**
	 * Stores the player saturation.
	 */
	@Getter
	@Setter
	protected float saturation;

	/**
	 * Constructs a new UpdateHealthPacket instance.
	 * @param health The health.
	 * @param food The food level.
	 * @param saturation The saturation level.
	 */
	public UpdateHealthPacket (float health, short food, float saturation) {
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	/**
	 * Constructs a new UpdateHealthPacket instance.
	 * @param buffer The packet buffer.
	 */
	public UpdateHealthPacket (ByteBuf buffer) {
		this.health = buffer.readFloat ();
		this.food = buffer.readShort ();
		this.saturation = buffer.readFloat ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeFloat (this.health);
		out.writeShort (this.food);
		out.writeFloat (this.saturation);
	}
}