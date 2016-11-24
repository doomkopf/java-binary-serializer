package com.hypercode.binaryserializer.typeserializer;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.BinarySerializer;

public class UnknownTypeSerializer extends NullRefAwareTypeSerializer {
	
	private final BinarySerializer binarySerializer;
	
	public UnknownTypeSerializer(BinarySerializer binarySerializer) {
		this.binarySerializer = binarySerializer;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		Class<?> clazz = obj.getClass();
		String className = clazz.getName();
		byte[] classNameBytes = className.getBytes();
		buffer.putInt(classNameBytes.length);
		buffer.put(classNameBytes);
		
		binarySerializer.getOrCreateType(clazz).writeObjectToBuffer(obj, buffer);
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		byte[] classNameBytes = new byte[buffer.getInt()];
		buffer.get(classNameBytes);
		String className = new String(classNameBytes);
		Class<?> clazz = Class.forName(className);
		
		return binarySerializer.getOrCreateType(clazz).readObjectFromBuffer(buffer);
	}
}