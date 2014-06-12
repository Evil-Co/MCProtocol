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

package org.evilco.mc.protocol.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.evilco.mc.protocol.common.error.CorruptedFrameException;
import org.evilco.mc.protocol.common.utility.PacketDataUtility;

import java.util.List;

/**
 * Provides a simple frame en- and de-coder based upon variable integers.
 */
public class VariableIntegerFrameCodec extends ByteToMessageCodec<ByteBuf> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		// calculate body and header length
		int bodyLength = msg.readableBytes ();
		int headerLength = PacketDataUtility.getVariableIntegerSize (bodyLength);

		// ensure we did not hit the buffer bounds
		out.ensureWritable ((bodyLength + headerLength));

		// write header
		PacketDataUtility.writeVariableInteger (out, bodyLength);

		// write body
		out.writeBytes (msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// mark current position
		in.markReaderIndex ();

		// initialize buffer
		final byte[] buffer = new byte[3];

		for (int i = 0; i < buffer.length; i++) {
			// check whether enough data is available
			if (!in.isReadable ()) {
				// reset input buffer
				in.resetReaderIndex ();

				// skip further handling (wait for more data)
				return;
			}

			// read a single bite
			buffer[i] = in.readByte ();

			// check data
			if (buffer[i] < 0) continue;

			// get length
			int length = PacketDataUtility.readVariableInteger (in);

			// verify amount of data found
			if (length > in.readableBytes ()) {
				// reset input buffer
				in.resetReaderIndex ();

				// skip further handling (wait for more data)
				return;
			}

			// add data to stack
			out.add (in.readBytes (length));

			// skip further data
			return;
		}

		// handle corrupted frames
		throw new CorruptedFrameException ("Length wider than 21-bit.");
	}
}