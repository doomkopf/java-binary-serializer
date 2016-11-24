package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.Constants;

public class PrimitiveBooleanTypeSerializer implements SimpleTypeSerializer {
	
	public static final PrimitiveBooleanTypeSerializer INSTANCE = new PrimitiveBooleanTypeSerializer();
	
	private PrimitiveBooleanTypeSerializer() {}
	
	public Class<?> getClazz() {
		return boolean.class;
	}
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.put(((Boolean) obj) ? Constants.BYTE_TRUE : Constants.BYTE_FALSE);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.get() == Constants.BYTE_TRUE;
	}
}