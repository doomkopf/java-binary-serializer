package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveIntegerTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveIntegerTypeSerializer INSTANCE = new PrimitiveIntegerTypeSerializer();
	
	private PrimitiveIntegerTypeSerializer() {}
	
	public Class<?> getClazz() {
		return int.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putInt((Integer) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getInt();
	}
}