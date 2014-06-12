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

package org.evilco.mc.protocol.common.utility;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import lombok.NonNull;

/**
 * Provides utility methods for interacting with Minecraft data types.
 */
public class PacketDataUtility {

	/**
	 * Reads a string with a dynamic length.
	 * @param in The input buffer.
	 * @return The string.
	 */
	public static String readString (@NonNull ByteBuf in) {
		// read length
		int length = readVariableInteger (in);

		// verify packet length
		Preconditions.checkArgument (length <= Short.MAX_VALUE, "The string exceeds the maximum of %s bytes (found %s bytes).", Short.MAX_VALUE, length);

		// create buffer
		byte[] buffer = new byte[length];
		in.readBytes (buffer);

		// decode string
		return (new String (buffer, Charsets.UTF_8));
	}

	/**
	 * Reads a variable integer.
	 * @param in The input buffer.
	 * @return The variable integer.
	 */
	public static int readVariableInteger (@NonNull ByteBuf in) {
		// create buffers
		int out = 0;
		int bytes = 0;
		byte buffer;

		// read all parts
		while (true) {
			// read one more byte
			buffer = in.readByte ();

			// let's have some fun
			out |= (buffer & 0x7F) << (bytes++ * 7);

			// verify size
			if (bytes > 5) throw new IllegalArgumentException ("The VariableInteger exceeds the maximum size.");

			// check end
			if ((buffer & 0x80) != 0x80) break;
		}

		return out;
	}

	/**
	 * Writes a string with a dynamic length to a buffer.
	 * @param out The output buffer.
	 * @param value The value.
	 */
	public static void writeString (@NonNull ByteBuf out, @NonNull String value) {
		// verify length
		Preconditions.checkArgument (value.length () <= Short.MAX_VALUE, "The string exceeds the maximum of %s bytes (found %s bytes).", Short.MAX_VALUE, value.length ());

		// get bytes
		byte[] data = value.getBytes (Charsets.UTF_8);

		// write length
		writeVariableInteger (out, data.length);

		// write data
		out.writeBytes (data);
	}

	/**
	 * Writes a variable integer.
	 * @param out The output buffer.
	 * @param value The value.
	 */
	public static void writeVariableInteger (@NonNull ByteBuf out, int value) {
		int part;

		// write all parts
		while (true) {
			// get current part
			part = value & 0x7F;

			// remove part from stack
			value >>>= 7;

			// fill
			if (value != 0) part |= 0x80;

			// write output
			out.writeByte (part);

			// check whether the stack is empty
			if (value == 0) break;
		}
	}
}