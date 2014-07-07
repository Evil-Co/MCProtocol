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

package org.evilco.mc.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.Getter;
import org.evilco.mc.protocol.common.utility.PacketEncryptionUtility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.List;

/**
 * Provides an encryption codec for netty based servers.
 */
public class EncryptionCodec extends ByteToMessageCodec<ByteBuf> {

	/**
	 * Stores the decryption cipher.
	 */
	@Getter
	private Cipher decryptionCipher = null;

	/**
	 * Stores the encryption cipher.
	 */
	@Getter
	private Cipher encryptionCipher = null;

	/**
	 * Stores the shared secret key.
	 */
	@Getter
	private SecretKey secretKey = null;

	/**
	 * Constructs a new EncryptionCodec instance.
	 * @param secretKey The secret key.
	 * @throws InvalidKeyException
	 */
	public EncryptionCodec (SecretKey secretKey) throws InvalidKeyException {
		// store key
		this.secretKey = secretKey;

		// create ciphers
		this.decryptionCipher = PacketEncryptionUtility.getProtocolDecryptionCipher (secretKey);
		this.encryptionCipher = PacketEncryptionUtility.getProtocolEncryptionCipher (secretKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int readableBytes = msg.readableBytes ();
		byte[] buffer = new byte[readableBytes];

		// read buffer
		msg.readBytes (buffer);

		// create output buffer
		byte[] heapOut = new byte[this.encryptionCipher.getOutputSize (readableBytes)];

		// write finished result
		out.writeBytes (heapOut, 0, this.encryptionCipher.update (buffer, 0, readableBytes, heapOut));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// create new buffer
		int readableBytes = in.readableBytes ();
		byte[] buffer = new byte[readableBytes];

		// read buffer
		in.readBytes (buffer);

		// create heap buffer
		ByteBuf heap = ctx.alloc ().heapBuffer (this.decryptionCipher.getOutputSize (readableBytes));

		// write data
		heap.writerIndex (this.decryptionCipher.update (buffer, 0, readableBytes, heap.array (), heap.arrayOffset ()));

		// return finished version
		out.add (heap);
	}
}