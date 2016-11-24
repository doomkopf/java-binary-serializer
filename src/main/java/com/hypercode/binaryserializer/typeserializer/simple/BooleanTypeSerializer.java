package com.hypercode.binaryserializer.typeserializer.simple;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.typeserializer.NullRefAwareTypeSerializer;

public class BooleanTypeSerializer extends NullRefAwareTypeSerializer implements SimpleTypeSerializer {
	
	public static final BooleanTypeSerializer INSTANCE = new BooleanTypeSerializer();
	
	private BooleanTypeSerializer() {}
	
	public Class<?> getClazz() {
		return Boolean.class;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		PrimitiveBooleanTypeSerializer.INSTANCE.writeObjectToBuffer(((Boolean) obj).booleanValue(), buffer);
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		return PrimitiveBooleanTypeSerializer.INSTANCE.readObjectFromBuffer(buffer);
	}
}