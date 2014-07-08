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
 * Represents the animation packet.
 */
public class AnimationPacket implements IPacket {

	/**
	 * Stores the entity ID.
	 */
	@Getter
	@Setter
	private int entityID;

	/**
	 * Stores the animation ID.
	 */
	@Getter
	@Setter
	private short animation;

	/**
	 * Constructs a new AnimationPacket instance.
	 * @param entityID The entity ID.
	 * @param animation The animation ID.
	 */
	public AnimationPacket (int entityID, short animation) {
		this.entityID = entityID;
		this.animation = animation;
	}

	/**
	 * Constructs a new AnimationPacket instance.
	 * @param buffer The packet buffer.
	 */
	public AnimationPacket (ByteBuf buffer) {
		this.entityID = buffer.readInt ();
		this.animation = buffer.readUnsignedByte ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws Exception {
		out.writeInt (this.entityID);
		out.writeByte (this.animation);
	}
}