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

package org.evilco.mc.protocol.common.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;
import org.evilco.mc.protocol.common.utility.PacketDataUtility;

/**
 * Represents the handshake packet.
 */
public class HandshakePacket implements IPacket {

	/**
	 * Stores the protocol version.
	 */
	@Getter
	@Setter
	private int protocolVersion = 4;

	/**
	 * Stores the server address (the hostname or IP address).
	 */
	@Getter
	@Setter
	@NonNull
	private String serverAddress;

	/**
	 * Stores the server port.
	 */
	@Getter
	@Setter
	private int serverPort;

	/**
	 * Stores the next requested state.
	 */
	@Getter
	@Setter
	private int nextState;

	/**
	 * Constructs a new HandshakePacket.
	 * @param serverAddress The server address.
	 * @param serverPort The server port.
	 * @param nextState The next state.
	 */
	public HandshakePacket (@NonNull String serverAddress, int serverPort, int nextState) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.nextState = nextState;
	}

	/**
	 * Constructs a new HandshakePacket.
	 * @param protocolVersion The protocol version.
	 * @param serverAddress The server address.
	 * @param serverPort The server port.
	 * @param nextState The next state.
	 */
	public HandshakePacket (int protocolVersion, @NonNull String serverAddress, int serverPort, int nextState) {
		this (serverAddress, serverPort, nextState);
		this.protocolVersion = protocolVersion;
	}

	/**
	 * Constructs a new HandshakePacket.
	 * @param buffer The packet buffer.
	 */
	public HandshakePacket (ByteBuf buffer) {
		this.protocolVersion = PacketDataUtility.readVariableInteger (buffer);
		this.serverAddress = PacketDataUtility.readString (buffer);
		this.serverPort = buffer.readUnsignedShort ();
		this.nextState = PacketDataUtility.readVariableInteger (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) {
		PacketDataUtility.writeVariableInteger (out, this.protocolVersion);
		PacketDataUtility.writeString (out, this.serverAddress);
		out.writeShort (this.serverPort);
		PacketDataUtility.writeVariableInteger (out, this.nextState);
	}
}