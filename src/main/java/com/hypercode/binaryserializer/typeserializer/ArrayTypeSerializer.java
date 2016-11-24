package com.hypercode.binaryserializer.typeserializer;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.TypeSerializer;

public class ArrayTypeSerializer extends NullRefAwareTypeSerializer {
	
	private final TypeSerializer typeSerializer;
	private final Class<?> clazz;
	
	public ArrayTypeSerializer(TypeSerializer typeSerializer, Class<?> clazz) {
		this.typeSerializer = typeSerializer;
		this.clazz = clazz;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		int length = Array.getLength(obj);
		buffer.putInt(length);
		for (int i = 0; i < length; i++) {
			Object elem = Array.get(obj, i);
			typeSerializer.writeObjectToBuffer(elem, buffer);
		}
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		int length = buffer.getInt();
		Object array = Array.newInstance(clazz, length);
		for (int i = 0; i < length; i++) {
			Array.set(array, i, typeSerializer.readObjectFromBuffer(buffer));
		}
		
		return array;
	}
}