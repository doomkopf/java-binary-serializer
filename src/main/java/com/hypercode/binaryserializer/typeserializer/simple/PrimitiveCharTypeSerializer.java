package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveCharTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveCharTypeSerializer INSTANCE = new PrimitiveCharTypeSerializer();
	
	private PrimitiveCharTypeSerializer() {}
	
	public Class<?> getClazz() {
		return char.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putChar((Character) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getChar();
	}
}