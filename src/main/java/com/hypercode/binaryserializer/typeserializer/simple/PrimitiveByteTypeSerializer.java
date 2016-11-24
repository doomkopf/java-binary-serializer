package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveByteTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveByteTypeSerializer INSTANCE = new PrimitiveByteTypeSerializer();
	
	private PrimitiveByteTypeSerializer() {}
	
	public Class<?> getClazz() {
		return byte.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.put((Byte) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.get();
	}
}