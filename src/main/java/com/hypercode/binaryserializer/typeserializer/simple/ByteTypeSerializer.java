package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class ByteTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final ByteTypeSerializer INSTANCE = new ByteTypeSerializer();
	
	private ByteTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Byte.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.put(((Byte) obj).byteValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.get();
	}
}