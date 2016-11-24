package com.hypercode.binaryserializer.typeserializer;

import java.nio.ByteBuffer;

import com.hypercode.binaryserializer.Constants;
import com.hypercode.binaryserializer.TypeSerializer;

public abstract class NullRefAwareTypeSerializer implements TypeSerializer {
	
	public void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception {
		
		if (obj == null) {
			buffer.put(Constants.BYTE_NULL);
			return;
		}
		
		buffer.put(Constants.BYTE_NOT_NULL);
		
		writeNotNullObjectToBuffer(obj, buffer);
	}
	
	public Object readObjectFromBuffer(ByteBuffer buffer) throws Exception {
		
		if (buffer.get() == Constants.BYTE_NULL) {
			return null;
		}
		
		return readNotNullObjectFromBuffer(buffer);
	}
	
	public abstract void writeNotNullObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception;
	public abstract Object readNotNullObjectFromBuffer(ByteBuffer buffer) throws Exception;
}