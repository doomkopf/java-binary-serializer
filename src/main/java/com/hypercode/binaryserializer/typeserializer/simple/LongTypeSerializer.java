package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class LongTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final LongTypeSerializer INSTANCE = new LongTypeSerializer();
	
	private LongTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Long.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putLong(((Long) obj).longValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getLong();
	}
}