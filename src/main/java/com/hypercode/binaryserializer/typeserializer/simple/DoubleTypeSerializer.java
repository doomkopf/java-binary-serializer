package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class DoubleTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final DoubleTypeSerializer INSTANCE = new DoubleTypeSerializer();
	
	private DoubleTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Double.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putDouble(((Double) obj).doubleValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getDouble();
	}
}