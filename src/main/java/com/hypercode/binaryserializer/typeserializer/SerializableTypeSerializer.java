package com.hypercode.binaryserializer.typeserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class SerializableTypeSerializer extends NullRefAwareTypeSerializer {
	
	public static final SerializableTypeSerializer INSTANCE = new SerializableTypeSerializer();
	
	private SerializableTypeSerializer() {}
	
	@Override
	public void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		
		oo.writeObject(obj);
		byte[] bytes = bo.toByteArray();
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		
		oo.close();
	}
	
	@Override
	public Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		byte[] bytes = new byte[buffer.getInt()];
		buffer.get(bytes);
		ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(bytes));
		Object retObject = oi.readObject();
		
		oi.close();
		
		return retObject;
	}
}