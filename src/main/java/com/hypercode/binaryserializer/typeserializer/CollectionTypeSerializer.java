package com.hypercode.binaryserializer.typeserializer;

import java.nio.ByteBuffer;
import java.util.Collection;

import com.hypercode.binaryserializer.TypeFactoryProvider;

@SuppressWarnings("rawtypes")
public class CollectionTypeSerializer<T extends Collection> extends NullRefAwareTypeSerializer {
	
	private final UnknownTypeSerializer unknownTypeSerializer;
	private final TypeFactoryProvider typeFactoryProvider;
	
	public CollectionTypeSerializer(UnknownTypeSerializer unknownTypeSerializer, TypeFactoryProvider typeFactoryProvider) {
		this.unknownTypeSerializer = unknownTypeSerializer;
		this.typeFactoryProvider = typeFactoryProvider;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		byte[] classNameBytes = obj.getClass().getName().getBytes();
		buffer.putInt(classNameBytes.length);
		buffer.put(classNameBytes);
		
		T collection = (T) obj;
		buffer.putInt(collection.size());
		for (Object elem : collection) {
			unknownTypeSerializer.writeObjectToBuffer(elem, buffer);
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		byte[] classNameBytes = new byte[buffer.getInt()];
		buffer.get(classNameBytes);
		Class<?> clazz = Class.forName(new String(classNameBytes));
		
		T collection = null;
		try {
			collection = (T) clazz.newInstance();
		} catch (InstantiationException e) {
			// expected exception used for control flow
		} catch (IllegalAccessException e) {
			// expected exception used for control flow
		}
		
		if (collection == null) {
			collection = (T) typeFactoryProvider.getTypeFactory(clazz).create();
		}
		
		int elems = buffer.getInt();
		for (int i = 0; i < elems; i++) {
			collection.add(unknownTypeSerializer.readObjectFromBuffer(buffer));
		}
		
		return collection;
	}
}