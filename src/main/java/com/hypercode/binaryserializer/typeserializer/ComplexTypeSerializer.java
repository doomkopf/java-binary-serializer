package com.hypercode.binaryserializer.typeserializer;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

import com.hypercode.binaryserializer.TypeFactory;
import com.hypercode.binaryserializer.TypeSerializer;

public class ComplexTypeSerializer extends NullRefAwareTypeSerializer {
	
	public static class InternalField {
		public final Field field;
		public final TypeSerializer typeSerializer;
		
		public InternalField(Field field, TypeSerializer typeSerializer) {
			this.field = field;
			this.typeSerializer = typeSerializer;
		}
	}
	
	private final TypeFactory typeFactory;
	private final List<InternalField> fields;
	
	public ComplexTypeSerializer(TypeFactory typeFactory, List<InternalField> fields) {
		this.typeFactory = typeFactory;
		this.fields = fields;
	}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		for (InternalField field : fields) {
			Object value = field.field.get(obj);
			field.typeSerializer.writeObjectToBuffer(value, buffer);
		}
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		Object obj = typeFactory.create();
		
		for (InternalField field : fields) {
			Object value = field.typeSerializer.readObjectFromBuffer(buffer);
			field.field.set(obj, value);
		}
		
		return obj;
	}
}