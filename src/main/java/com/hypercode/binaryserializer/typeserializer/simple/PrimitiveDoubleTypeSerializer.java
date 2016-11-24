package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveDoubleTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveDoubleTypeSerializer INSTANCE = new PrimitiveDoubleTypeSerializer();
	
	private PrimitiveDoubleTypeSerializer() {}
	
	public Class<?> getClazz() {
		return double.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putDouble((Double) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getDouble();
	}
}