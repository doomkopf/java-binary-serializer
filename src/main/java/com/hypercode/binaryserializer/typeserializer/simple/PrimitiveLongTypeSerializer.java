package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

public class PrimitiveLongTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveLongTypeSerializer INSTANCE = new PrimitiveLongTypeSerializer();
	
	private PrimitiveLongTypeSerializer() {}
	
	public Class<?> getClazz() {
		return long.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putLong((Long) obj);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getLong();
	}
}