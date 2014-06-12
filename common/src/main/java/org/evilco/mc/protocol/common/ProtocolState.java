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

package org.evilco.mc.protocol.common;

/**
 * Defines a list of valid protocol states.
 */
public enum ProtocolState {
	/**
	 * Initial state to ensure server and client can talk to each other.
	 */
	HANDSHAKE {
		{

		}
	},

	/**
	 * Status state to transmit information for the server listing.
	 */
	STATUS {
		{

		}
	},

	/**
	 * Login state which is used to exchange authentication and encryption parameters.
	 */
	LOGIN {
		{

		}
	},

	/**
	 * Game state which handles all in-game packets used to synchronize client and server.
	 */
	GAME {
		{

		}
	};

	/**
	 * Stores the inbound packet registry.
	 */
	public final IProtocolRegistry INBOUND;

	/**
	 * Stores the outbound packet registry.
	 */
	public final IProtocolRegistry OUTBOUND;

	/**
	 * Constructs a new ProtocolState instance.
	 */
	private ProtocolState () {
		INBOUND = new SimpleProtocolRegistry (this);
		OUTBOUND = new SimpleProtocolRegistry (this);
	}
}