package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveShortTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveShortTypeSerializer INSTANCE = new PrimitiveShortTypeSerializer();
	
	private PrimitiveShortTypeSerializer() {}
	
	public Class<?> getClazz() {
		return short.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putShort((Short) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getShort();
	}
}