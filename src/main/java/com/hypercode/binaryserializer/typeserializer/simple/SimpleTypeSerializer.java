package com.hypercode.binaryserializer.typeserializer.simple;

import com.hypercode.binaryserializer.TypeSerializer;

public interface SimpleTypeSerializer extends TypeSerializer {
	Class<?> getClazz();
}