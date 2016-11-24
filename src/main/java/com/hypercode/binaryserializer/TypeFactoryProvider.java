package com.hypercode.binaryserializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeFactoryProvider {
	
	private final Map<Class<?>, TypeFactory> map = new HashMap<Class<?>, TypeFactory>();
	
	public TypeFactoryProvider(Collection<Mapping<Class<?>, TypeFactory>> typeFactoryMappings) {
		for (Mapping<Class<?>, TypeFactory> mapping : typeFactoryMappings) {
			map.put(mapping.key, mapping.value);
		}
	}
	
	public TypeFactory getTypeFactory(Class<?> clazz) {
		return map.get(clazz);
	}
}