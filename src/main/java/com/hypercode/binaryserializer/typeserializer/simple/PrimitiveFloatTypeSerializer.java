package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveFloatTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveFloatTypeSerializer INSTANCE = new PrimitiveFloatTypeSerializer();
	
	private PrimitiveFloatTypeSerializer() {}
	
	public Class<?> getClazz() {
		return float.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putFloat((Float) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getFloat();
	}
}