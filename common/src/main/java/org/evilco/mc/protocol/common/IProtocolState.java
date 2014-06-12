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
 * Provides methods required for a protocol state.
 */
public interface IProtocolState {

	/**
	 * Returns the inbound packet registry.
	 * @return The inbound packet registry.
	 */
	public IProtocolRegistry getInbound ();

	/**
	 * Returns the outbound packet registry.
	 * @return The outbound packet registry.
	 */
	public IProtocolRegistry getOutbound ();
}