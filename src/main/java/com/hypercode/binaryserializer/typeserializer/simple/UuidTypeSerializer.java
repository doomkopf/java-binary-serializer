package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class UuidTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final UuidTypeSerializer INSTANCE = new UuidTypeSerializer();
	
	private UuidTypeSerializer() {}
	
	public Class<?> getClazz() {
		return UUID.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		UUID uuid = (UUID) obj;
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return new UUID(buffer.getLong(), buffer.getLong());
	}
}