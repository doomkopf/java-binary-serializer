package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class IntegerTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final IntegerTypeSerializer INSTANCE = new IntegerTypeSerializer();
	
	private IntegerTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Integer.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putInt(((Integer) obj).intValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getInt();
	}
}