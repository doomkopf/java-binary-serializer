package com.hypercode.binaryserializer.typeserializer.simple;

import com.hypercode.binaryserializer.TypeSerializer;

/**
 * Not only primitives, but all types that can be determined by simply comparing the class object.
 */
public class SimpleTypes {
	
	private static final SimpleTypeSerializer[] SIMPLE_TYPES = new SimpleTypeSerializer[] {
			PrimitiveIntegerTypeSerializer.INSTANCE,
			PrimitiveLongTypeSerializer.INSTANCE,
			PrimitiveShortTypeSerializer.INSTANCE,
			PrimitiveBooleanTypeSerializer.INSTANCE,
			PrimitiveByteTypeSerializer.INSTANCE,
			PrimitiveCharTypeSerializer.INSTANCE,
			PrimitiveDoubleTypeSerializer.INSTANCE,
			PrimitiveFloatTypeSerializer.INSTANCE,
			IntegerTypeSerializer.INSTANCE,
			LongTypeSerializer.INSTANCE,
			ShortTypeSerializer.INSTANCE,
			BooleanTypeSerializer.INSTANCE,
			ByteTypeSerializer.INSTANCE,
			CharTypeSerializer.INSTANCE,
			DoubleTypeSerializer.INSTANCE,
			FloatTypeSerializer.INSTANCE,
			StringTypeSerializer.INSTANCE,
			UuidTypeSerializer.INSTANCE
	};
	
	public static TypeSerializer findType(Class<?> clazz) {
		
		for (int i = 0; i < SIMPLE_TYPES.length; i++) {
			SimpleTypeSerializer type = SIMPLE_TYPES[i];
			if (type.getClazz() == clazz) {
				return type;
			}
		}
		
		return null;
	}
	
	private SimpleTypes() {}
}