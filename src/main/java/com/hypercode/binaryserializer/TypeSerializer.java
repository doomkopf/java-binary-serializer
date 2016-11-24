package com.hypercode.binaryserializer;

import java.nio.ByteBuffer;

public interface TypeSerializer {
	void writeObjectToBuffer(Object obj, ByteBuffer buffer) throws Exception;
	Object readObjectFromBuffer(ByteBuffer buffer) throws Exception;
}