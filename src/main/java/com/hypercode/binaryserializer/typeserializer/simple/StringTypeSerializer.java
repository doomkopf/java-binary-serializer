package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class StringTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final StringTypeSerializer INSTANCE = new StringTypeSerializer();
	
	private StringTypeSerializer() {}
	
	public Class<?> getClazz() {
		return String.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		String str = (String) obj;
		byte[] bytes = str.getBytes();
		buffer.putInt(bytes.length);
		buffer.put(bytes);
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		byte[] bytes = new byte[buffer.getInt()];
		buffer.get(bytes);
		return new String(bytes);
	}
}