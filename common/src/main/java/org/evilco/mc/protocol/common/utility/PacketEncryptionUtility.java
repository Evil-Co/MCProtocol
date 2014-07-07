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

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Provides utility methods for encrypting and decrypting Minecraft traffic.
 */
public class PacketEncryptionUtility {

	/**
	 * Defines the protocol encryption algorithm.
	 */
	public static final String ALGORITHM_ENCRYPTION = "AES";

	/**
	 * Defines the protocol encryption padding.
	 */
	public static final String ALGORITHM_ENCRYPTION_PADDING = "/CFB8/NoPadding";

	/**
	 * Defines the key exchange algorithm.
	 */
	public static final String ALGORITHM_KEY_EXCHANGE = "RSA";

	/**
	 * Returns the encryption cipher.
	 * @return The cipher.
	 */
	@SneakyThrows ({NoSuchAlgorithmException.class, NoSuchPaddingException.class })
	public static Cipher getProtocolCipher () {
		return Cipher.getInstance (ALGORITHM_ENCRYPTION + ALGORITHM_ENCRYPTION_PADDING);
	}

	/**
	 * Returns the protocol encryption cipher.
	 * @param secretKey The secret key.
	 * @return The cipher.
	 * @throws InvalidKeyException
	 */
	public static Cipher getProtocolEncryptionCipher (SecretKey secretKey) throws InvalidKeyException {
		// get cipher
		Cipher cipher = getProtocolCipher ();

		// initialize
		cipher.init (Cipher.ENCRYPT_MODE, secretKey);
		return cipher;
	}

	/**
	 * Returns the protocol decryption cipher.
	 * @param secretKey The secret key.
	 * @return The cipher.
	 * @throws InvalidKeyException
	 */
	public static Cipher getProtocolDecryptionCipher (SecretKey secretKey) throws InvalidKeyException {
		// get cipher
		Cipher cipher = getProtocolCipher ();

		// initialize
		cipher.init (Cipher.DECRYPT_MODE, secretKey);
		return cipher;
	}

	/**
	 * Returns the key exchange cipher.
	 * @return The cipher.
	 */
	@SneakyThrows ({ NoSuchAlgorithmException.class, NoSuchPaddingException.class })
	public static Cipher getKeyExchangeCipher () {
		return Cipher.getInstance (ALGORITHM_KEY_EXCHANGE);
	}

	/**
	 * Returns the key exchange encryption cipher.
	 * @param publicKey The public key.
	 * @return The cipher.
	 * @throws InvalidKeyException
	 */
	public static Cipher getKeyExchangeEncryptionCipher (PublicKey publicKey) throws InvalidKeyException {
		// get cipher
		Cipher cipher = getKeyExchangeCipher ();

		// initialize
		cipher.init (Cipher.ENCRYPT_MODE, publicKey);
		return cipher;
	}

	/**
	 * Returns the key exchange decryption cipher.
	 * @param privateKey The private key.
	 * @return The cipher.
	 * @throws InvalidKeyException
	 */
	public static Cipher getKeyExchangeDecryptionCipher (PrivateKey privateKey) throws InvalidKeyException {
		// get cipher
		Cipher cipher = getKeyExchangeCipher ();

		// initialize
		cipher.init (Cipher.DECRYPT_MODE, privateKey);
		return cipher;
	}
}