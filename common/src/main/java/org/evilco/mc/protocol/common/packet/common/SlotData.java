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

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.stream.NbtOutputStream;
import com.evilco.mc.nbt.tag.TagCompound;
import com.evilco.mc.nbt.tag.TagType;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Represents a slot.
 */
public class SlotData {

	/**
	 * Stores the amount of items in the slot.
	 */
	@Getter
	@Setter
	private byte count;

	/**
	 * Stores the item damage.
	 */
	@Getter
	@Setter
	private short damage;

	/**
	 * Stores the item NBT (if any).
	 */
	@Getter
	@Setter
	private TagCompound item;

	@Getter
	@Setter
	private short itemID;

	/**
	 * Constructs a new SlotData instance.
	 * @param itemID The item/block ID.
	 * @param count The item count.
	 * @param damage The item damage.
	 * @param item The item NBT.
	 */
	public SlotData (short itemID, byte count, short damage, TagCompound item) {
		this.itemID = itemID;
		this.count = count;
		this.damage = damage;
		this.item = item;
	}

	/**
	 * Constructs a new SlotData instance.
	 * @param buffer The buffer to read from.
	 * @throws IOException
	 */
	public SlotData (ByteBuf buffer) throws IOException {
		this.itemID = buffer.readShort ();

		// check whether further data is expected
		if (this.itemID == -1) return;

		// read count and damage
		this.count = buffer.readByte ();
		this.damage = buffer.readShort ();

		// read amount of data (if any)
		short dataLength = buffer.readShort ();

		// check whether further data is expected
		if (dataLength <= 0) return;

		// load data
		byte[] data = new byte[dataLength];
		buffer.readBytes (data);

		// decode NBT
		InputStream inputStream = new GZIPInputStream (new ByteArrayInputStream (data));
		NbtInputStream nbtInputStream = new NbtInputStream (inputStream);

		try {
			this.item = ((TagCompound) nbtInputStream.readTag ());
		} finally {
			nbtInputStream.close ();
			inputStream.close ();
		}
	}

	/**
	 * Writes the slot data.
	 * @param out The output buffer.
	 * @throws IOException
	 */
	public void write (ByteBuf out) throws IOException {
		// write itemID
		out.writeShort (this.itemID);

		// skip further data if slot is empty
		if (this.itemID == -1) return;

		// write count and damage
		out.writeByte (this.count);
		out.writeShort (this.damage);

		// skip data if empty
		if (this.item == null) {
			out.writeShort (0);
			return;
		}

		// encode data
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
		NbtOutputStream nbtOutputStream = new NbtOutputStream (new GZIPOutputStream (outputStream));

		try {
			nbtOutputStream.write (this.item);
		} finally {
			nbtOutputStream.flush ();
			nbtOutputStream.close ();
			outputStream.flush ();
			outputStream.close ();
		}

		// write size
		byte[] data = outputStream.toByteArray ();
		out.writeShort (data.length);
		out.writeBytes (data);
	}
}