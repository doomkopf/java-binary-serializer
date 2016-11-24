package com.hypercode.binaryserializer.typeserializer;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;

import com.hypercode.binaryserializer.TypeFactoryProvider;

import java.util.Set;

public class MapTypeSerializer extends NullRefAwareTypeSerializer {
	
	private final UnknownTypeSerializer unknownTypeSerializer;
	private final TypeFactoryProvider typeFactoryProvider;
	
	public MapTypeSerializer(UnknownTypeSerializer unknownTypeSerializer, TypeFactoryProvider typeFactoryProvider) {
		this.unknownTypeSerializer = unknownTypeSerializer;
		this.typeFactoryProvider = typeFactoryProvider;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		byte[] classNameBytes = obj.getClass().getName().getBytes();
		buffer.putInt(classNameBytes.length);
		buffer.put(classNameBytes);
		
		Map map = (Map) obj;
		Set<Entry> entrySet = map.entrySet();
		buffer.putInt(entrySet.size());
		for (Entry entry : entrySet) {
			unknownTypeSerializer.writeObjectToBuffer(entry.getKey(), buffer);
			unknownTypeSerializer.writeObjectToBuffer(entry.getValue(), buffer);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		byte[] classNameBytes = new byte[buffer.getInt()];
		buffer.get(classNameBytes);
		Class<?> clazz = Class.forName(new String(classNameBytes));
		
		Map map = null;
		try {
			map = (Map) clazz.newInstance();
		} catch (InstantiationException e) {
			// expected exception used for control flow
		} catch (IllegalAccessException e) {
			// expected exception used for control flow
		}
		
		if (map == null) {
			map = (Map) typeFactoryProvider.getTypeFactory(clazz).create();
		}
		
		int elems = buffer.getInt();
		for (int i = 0; i < elems; i++) {
			map.put(
					unknownTypeSerializer.readObjectFromBuffer(buffer),
					unknownTypeSerializer.readObjectFromBuffer(buffer));
		}
		
		return map;
	}
}