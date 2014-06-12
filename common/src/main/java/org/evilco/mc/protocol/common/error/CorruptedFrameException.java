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

package org.evilco.mc.protocol.common.error;

/**
 * Represents errors occurring due to corrupted network frames.
 */
public class CorruptedFrameException extends ProtocolException {

	/**
	 * Constructs a new CorruptedFrameException instance.
	 */
	public CorruptedFrameException () {
		super ();
	}

	/**
	 * Constructs a new CorruptedFrameException instance.
	 * @param message The error message.
	 */
	public CorruptedFrameException (String message) {
		super (message);
	}

	/**
	 * Constructs a new CorruptedFrameException instance.
	 * @param message The error message.
	 * @param cause The error cause.
	 */
	public CorruptedFrameException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new CorruptedFrameException instance.
	 * @param cause The error cause.
	 */
	public CorruptedFrameException (Throwable cause) {
		super (cause);
	}
}