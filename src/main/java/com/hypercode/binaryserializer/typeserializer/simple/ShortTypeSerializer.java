package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class ShortTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final ShortTypeSerializer INSTANCE = new ShortTypeSerializer();
	
	private ShortTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Short.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		buffer.putShort(((Short) obj).shortValue());
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return buffer.getShort();
	}
}