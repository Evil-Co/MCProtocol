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
import lombok.NonNull;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;
import org.evilco.mc.protocol.common.utility.PacketDataUtility;

/**
 * Represents the chat message packet.
 */
public class ChatMessagePacket implements IPacket {

	/**
	 * The chat message (json if sent to client, plaintext message if sent to server).
	 */
	@Getter
	@Setter
	@NonNull
	protected String message;

	/**
	 * Constructs a new ChatMessagePacket instance.
	 * @param message The message.
	 */
	public ChatMessagePacket (@NonNull String message) {
		this.message = message;
	}

	/**
	 * Constructs a new ChatMessagePacket instance.
	 * @param buffer The packet buffer.
	 */
	public ChatMessagePacket (ByteBuf buffer) {
		this.message = PacketDataUtility.readString (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) {
		PacketDataUtility.writeString (out, this.message);
	}
}