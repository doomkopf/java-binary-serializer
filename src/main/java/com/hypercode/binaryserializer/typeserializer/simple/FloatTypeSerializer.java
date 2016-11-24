package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class FloatTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final FloatTypeSerializer INSTANCE = new FloatTypeSerializer();
	
	private FloatTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Float.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putFloat(((Float) obj).floatValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getFloat();
	}
}