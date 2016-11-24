package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class CharTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final CharTypeSerializer INSTANCE = new CharTypeSerializer();
	
	private CharTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Character.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putChar(((Character) obj).charValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getChar();
	}
}