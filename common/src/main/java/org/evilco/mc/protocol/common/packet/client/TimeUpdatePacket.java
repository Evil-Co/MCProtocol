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
 * Represents the time update packet.
 */
public class TimeUpdatePacket implements IPacket {

	/**
	 * Stores the world age.
	 */
	@Getter
	@Setter
	protected long age;

	/**
	 * The current region time.
	 */
	@Getter
	@Setter
	protected long time;

	/**
	 * Constructs a new TimeUpdatePacket instance.
	 * @param age The world age.
	 * @param time The region time.
	 */
	public TimeUpdatePacket (long age, long time) {
		this.age = age;
		this.time = time;
	}

	/**
	 * Constructs a new TimeUpdatePacket instance.
	 * @param buffer The packet buffer.
	 */
	public TimeUpdatePacket (ByteBuf buffer) {
		this.age = buffer.readLong ();
		this.time = buffer.readLong ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) {
		out.writeLong (this.age);
		out.writeLong (this.time);
	}
}