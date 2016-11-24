package com.hypercode.binaryserializer.typeserializer;

import java.nio.ByteBuffer;

public class EnumTypeSerializer extends NullRefAwareTypeSerializer {
	
	@SuppressWarnings("rawtypes")
	private final Class enumType;
	
	public EnumTypeSerializer(Class<?> enumType) {
		this.enumType = enumType;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		Enum<?> e = (Enum<?>) obj;
		byte[] nameBytes = e.name().getBytes();
		buffer.putInt(nameBytes.length);
		buffer.put(nameBytes);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		byte[] nameBytes = new byte[buffer.getInt()];
		buffer.get(nameBytes);
		String name = new String(nameBytes);
		return Enum.valueOf(enumType, name);
	}
}