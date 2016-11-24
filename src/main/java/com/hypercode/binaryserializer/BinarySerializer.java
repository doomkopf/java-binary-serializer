package com.hypercode.binaryserializer;

import java.io.Externalizable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hypercode.binaryserializer.typeserializer.ArrayTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.CollectionTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.ComplexTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.ComplexTypeSerializer.InternalField;
import com.hypercode.binaryserializer.typeserializer.EnumTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.MapTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.SerializableTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.UnknownTypeSerializer;
import com.hypercode.binaryserializer.typeserializer.simple.SimpleTypes;

public class BinarySerializer {
	
	private static final int RESIZE_BYTES = 1024;
	
	private final Map<Class<?>, TypeSerializer> classToTypeMap = new ConcurrentHashMap<Class<?>, TypeSerializer>();
	private final ThreadLocal<ByteBuffer> threadLocalByteBuffer = new ThreadLocal<ByteBuffer>();
	private final TypeFactoryProvider typeFactoryProvider;
	private final UnknownTypeSerializer unknownTypeSerializer = new UnknownTypeSerializer(this);
	private final CollectionTypeSerializer<?> collectionTypeSerializer;
	private final MapTypeSerializer mapTypeSerializer;
	
	private int bufferSize = RESIZE_BYTES;
	
	@SuppressWarnings("rawtypes")
	public BinarySerializer(Collection<Mapping<Class<?>, TypeFactory>> typeFactoryMappings) {
		typeFactoryProvider = new TypeFactoryProvider(typeFactoryMappings);
		collectionTypeSerializer = new CollectionTypeSerializer(unknownTypeSerializer, typeFactoryProvider);
		mapTypeSerializer = new MapTypeSerializer(unknownTypeSerializer, typeFactoryProvider);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BinarySerializer() {
		this(new ArrayList(0));
	}
	
	private ByteBuffer getByteBuffer() {
		
		ByteBuffer byteBuffer = threadLocalByteBuffer.get();
		if (byteBuffer == null || byteBuffer.capacity() < bufferSize) {
			byteBuffer = ByteBuffer.allocate(bufferSize);
			threadLocalByteBuffer.set(byteBuffer);
		} else {
			byteBuffer.clear();
		}
		
		return byteBuffer;
	}
	
	public TypeSerializer getOrCreateType(Class<?> clazz) {
		
		TypeSerializer typeSerializer = SimpleTypes.findType(clazz);
		if (typeSerializer == null) {
			if (Externalizable.class.isAssignableFrom(clazz)) {
				return SerializableTypeSerializer.INSTANCE;
			}
			
			if (Collection.class.isAssignableFrom(clazz)) {
				return collectionTypeSerializer;
			}
			
			if (Map.class.isAssignableFrom(clazz)) {
				return mapTypeSerializer;
			}
			
			int mod = clazz.getModifiers();
			if (!clazz.isArray() && (Modifier.isInterface(mod) || Modifier.isAbstract(mod) || clazz == Object.class)) {
				return unknownTypeSerializer;
			}
			
			typeSerializer = classToTypeMap.get(clazz);
			if (typeSerializer == null) {
				typeSerializer = createType(clazz);
				TypeSerializer existing = classToTypeMap.putIfAbsent(clazz, typeSerializer);
				if (existing != null) {
					typeSerializer = existing;
				}
			}
		}
		
		return typeSerializer;
	}
	
	private TypeSerializer createType(Class<?> clazz) {
		
		if (clazz.isArray()) {
			clazz = clazz.getComponentType();
			return new ArrayTypeSerializer(getOrCreateType(clazz), clazz);
		}
		
		if (clazz.isEnum()) {
			return new EnumTypeSerializer(clazz);
		}
		
		return createComplexType(clazz);
	}
	
	private TypeSerializer createComplexType(Class<?> clazz) {
		
		List<InternalField> fields = new LinkedList<InternalField>();
		List<Field> recFields = new LinkedList<Field>();
		for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
			int mod = field.getModifiers();
			if (Modifier.isTransient(mod) || Modifier.isStatic(mod)) {
				continue;
			}
			if (Modifier.isFinal(mod)) {
				return SerializableTypeSerializer.INSTANCE;
			}
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			if (field.getType() == clazz) {
				recFields.add(field);
				continue;
			}
			
			fields.add(new InternalField(field, getOrCreateType(field.getType())));
		}
		
		Constructor<?> localConstructor = null;
		try {
			localConstructor = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			// expected exception used for control flow
		} catch (SecurityException e) {
			// expected exception used for control flow
		}
		
		TypeFactory typeFactory;
		if (localConstructor == null) {
			typeFactory = typeFactoryProvider.getTypeFactory(clazz);
		} else {
			if (!localConstructor.isAccessible()) {
				localConstructor.setAccessible(true);
			}
			final Constructor<?> constructor = localConstructor;
			typeFactory = new TypeFactory() {
				public Object create() throws Exception {
					return constructor.newInstance();
				}
			};
		}
		
		ComplexTypeSerializer type = new ComplexTypeSerializer(typeFactory, fields);
		for (java.lang.reflect.Field field : recFields) {
			fields.add(new InternalField(field, type));
		}
		
		return type;
	}
	
	public byte[] serialize(Object obj) throws Exception {
		
		ByteBuffer byteBuffer;
		TypeSerializer typeSerializer = getOrCreateType(obj.getClass());
		while (true) {
			byteBuffer = getByteBuffer();
			try {
				typeSerializer.writeObjectToBuffer(obj, byteBuffer);
				break;
			} catch (BufferOverflowException e) {
				bufferSize += RESIZE_BYTES;
			}
		}
		
		byte[] bytes = new byte[byteBuffer.position()];
		byteBuffer.position(0);
		byteBuffer.get(bytes);
		return bytes;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
		return (T) getOrCreateType(clazz).readObjectFromBuffer(ByteBuffer.wrap(bytes));
	}
}