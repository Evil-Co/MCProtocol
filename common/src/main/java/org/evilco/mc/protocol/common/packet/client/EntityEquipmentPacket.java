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

package org.evilco.mc.protocol.common.packet.client;

import com.evilco.mc.nbt.stream.NbtOutputStream;
import com.evilco.mc.nbt.tag.TagCompound;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.evilco.mc.protocol.common.packet.IPacket;
import org.evilco.mc.protocol.common.packet.common.SlotData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Represents the entity equipment packet.
 */
public class EntityEquipmentPacket implements IPacket {

	/**
	 * The entity ID.
	 */
	@Getter
	@Setter
	protected int entityID;

	/**
	 * Stores the item data.
	 */
	@Getter
	@Setter
	@NonNull
	protected SlotData item;

	/**
	 * The slot (0=held, 1-4=armor slot (1 - boots, 2 - leggings, 3 - chestplate, 4 - helmet)).
	 */
	protected short slot;

	/**
	 * Constructs a new EntityEquipmentPacket.
	 * @param entityID The entityID.
	 * @param slot The slot.
	 * @param item The slot contents.
	 */
	public EntityEquipmentPacket (int entityID, short slot, @NonNull SlotData item) {
		this.entityID = entityID;
		this.slot = slot;
		this.item = item;
	}

	/**
	 * Constructs a new EntityEquipmentPacket.
	 * @param buffer The packet buffer.
	 * @throws IOException
	 */
	public EntityEquipmentPacket (ByteBuf buffer) throws IOException {
		this.entityID = buffer.readInt ();
		this.slot = buffer.readShort ();
		this.item = new SlotData (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf out) throws IOException {
		out.writeInt (this.entityID);
		out.writeShort (this.slot);
		this.item.write (out);
	}
}