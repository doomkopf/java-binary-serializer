package com.hypercode.binaryserializer;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class BinarySerializerTest {
	
	public static class TestExternalizable implements Externalizable {
		
		int value;
		String text;
		
		public TestExternalizable() {}
		
		public TestExternalizable(int value, String text) {
			this.value = value;
			this.text = text;
		}
		
		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeInt(value);
			out.writeUTF(text);
		}
		
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			value = in.readInt();
			text = in.readUTF();
		}
	}
	
	public static class TestSimpleClass {
		
		int value;
		
		@SuppressWarnings("unused")
		private TestSimpleClass() {}
		
		public TestSimpleClass(int value) {
			this.value = value;
		}
	}
	
	public static class RecursiveClass {
		
		int value;
		RecursiveClass recursiveClass;
		
		@SuppressWarnings("unused")
		private RecursiveClass() {}
		
		public RecursiveClass(int value, RecursiveClass recursiveClass) {
			this.value = value;
			this.recursiveClass = recursiveClass;
		}
	}
	
	public static class SerializationSubject {
		
		int intValue;
		String stringValue;
		short shortValue;
		long longValue;
		float floatValue;
		double doubleValue;
		byte byteValue;
		char charValue;
		boolean booleanValue;
		Integer intClass;
		Short shortClass;
		Long longClass;
		Float floatClass;
		Double doubleClass;
		Byte byteClass;
		Character charClass;
		Boolean booleanClass;
		UUID uuid;
		TestExternalizable testExternalizable;
		String nullValue;
		List<TestSimpleClass> arrayListWithObjects;
		TestSimpleClass[] arrayWithObjects;
		Map<String, TestSimpleClass> hashMapWithObjects;
		RecursiveClass recursive;
		
		@SuppressWarnings("unused")
		private SerializationSubject() {}
		
		public SerializationSubject(
				int intValue,
				String stringValue,
				short shortValue,
				long longValue,
				float floatValue,
				double doubleValue,
				byte byteValue,
				char charValue,
				boolean booleanValue,
				Integer intClass,
				Short shortClass,
				Long longClass,
				Float floatClass,
				Double doubleClass,
				Byte byteClass,
				Character charClass,
				Boolean booleanClass,
				UUID uuid,
				TestExternalizable testExternalizable,
				List<TestSimpleClass> arrayListWithObjects,
				TestSimpleClass[] arrayWithObjects,
				Map<String, TestSimpleClass> hashMapWithObjects,
				RecursiveClass recursive
				) {
			
			this.intValue = intValue;
			this.stringValue = stringValue;
			this.shortValue = shortValue;
			this.longValue = longValue;
			this.floatValue = floatValue;
			this.doubleValue = doubleValue;
			this.byteValue = byteValue;
			this.charValue = charValue;
			this.booleanValue = booleanValue;
			this.intClass = intClass;
			this.shortClass = shortClass;
			this.longClass = longClass;
			this.floatClass = floatClass;
			this.doubleClass = doubleClass;
			this.byteClass = byteClass;
			this.charClass = charClass;
			this.booleanClass = booleanClass;
			this.uuid = uuid;
			this.testExternalizable = testExternalizable;
			nullValue = null;
			this.arrayListWithObjects = arrayListWithObjects;
			this.arrayWithObjects = arrayWithObjects;
			this.hashMapWithObjects = hashMapWithObjects;
			this.recursive = recursive;
		}
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testSerialization() throws Exception {
		
		BinarySerializer binarySerializer = new BinarySerializer();
		
		List<TestSimpleClass> arrayListWithObjects = new ArrayList<TestSimpleClass>();
		arrayListWithObjects.add(new TestSimpleClass(100));
		arrayListWithObjects.add(new TestSimpleClass(200));
		
		Map<String, TestSimpleClass> hashMapWithObjects = new HashMap<String, TestSimpleClass>();
		hashMapWithObjects.put("test1", new TestSimpleClass(1000));
		hashMapWithObjects.put("test3", new TestSimpleClass(8000));
		hashMapWithObjects.put("test4", new TestSimpleClass(10000));
		hashMapWithObjects.put("test1234", new TestSimpleClass(20000));
		
		SerializationSubject test = new SerializationSubject(
				10,
				"Hello world",
				Short.MAX_VALUE,
				Long.MAX_VALUE,
				Float.MAX_VALUE,
				Double.MAX_VALUE,
				(byte) 66,
				'$',
				true,
				10,
				Short.MAX_VALUE,
				Long.MAX_VALUE,
				Float.MAX_VALUE,
				Double.MAX_VALUE,
				(byte) 66,
				'$',
				true,
				new UUID(1234, 9876),
				new TestExternalizable(1234, "987654321"),
				arrayListWithObjects,
				new TestSimpleClass[] {new TestSimpleClass(50), new TestSimpleClass(1000), new TestSimpleClass(1235)},
				hashMapWithObjects,
				new RecursiveClass(10, new RecursiveClass(20, new RecursiveClass(30, null)))
				);
		
		byte[] bytes = binarySerializer.serialize(test);
		SerializationSubject testResult = binarySerializer.deserialize(bytes, SerializationSubject.class);
		
		Assert.assertNotSame(test, testResult);
		Assert.assertEquals(test.intValue, testResult.intValue);
		Assert.assertNotSame(test.stringValue, testResult.stringValue);
		Assert.assertEquals(test.stringValue, testResult.stringValue);
		Assert.assertEquals(test.shortValue, testResult.shortValue);
		Assert.assertEquals(test.longValue, testResult.longValue);
		Assert.assertEquals(test.floatValue, testResult.floatValue, 0);
		Assert.assertEquals(test.doubleValue, testResult.doubleValue, 0);
		Assert.assertEquals(test.byteValue, testResult.byteValue);
		Assert.assertEquals(test.charValue, testResult.charValue);
		Assert.assertEquals(test.booleanValue, testResult.booleanValue);
		Assert.assertEquals(test.intClass.intValue(), testResult.intClass.intValue());
		Assert.assertNotSame(test.shortClass, testResult.shortClass);
		Assert.assertEquals(test.shortClass.shortValue(), testResult.shortClass.shortValue());
		Assert.assertNotSame(test.longClass, testResult.longClass);
		Assert.assertEquals(test.longClass.longValue(), testResult.longClass.longValue());
		Assert.assertNotSame(test.floatClass, testResult.floatClass);
		Assert.assertEquals(test.floatClass, testResult.floatClass, 0);
		Assert.assertNotSame(test.doubleClass, testResult.doubleClass);
		Assert.assertEquals(test.doubleClass, testResult.doubleClass, 0);
		Assert.assertEquals(test.byteClass.byteValue(), testResult.byteClass.byteValue());
		Assert.assertEquals(test.charClass.charValue(), testResult.charClass.charValue());
		Assert.assertEquals(test.booleanClass.booleanValue(), testResult.booleanClass.booleanValue());
		Assert.assertNotSame(test.uuid, testResult.uuid);
		Assert.assertEquals(test.uuid.getMostSignificantBits(), testResult.uuid.getMostSignificantBits());
		Assert.assertEquals(test.uuid.getLeastSignificantBits(), testResult.uuid.getLeastSignificantBits());
		Assert.assertEquals(test.testExternalizable.value, testResult.testExternalizable.value);
		Assert.assertEquals(test.testExternalizable.text, testResult.testExternalizable.text);
		Assert.assertNull(testResult.nullValue);
		
		Assert.assertNotSame(test.arrayListWithObjects, testResult.arrayListWithObjects);
		Assert.assertEquals(test.arrayListWithObjects.size(), testResult.arrayListWithObjects.size());
		for (int i = 0; i < test.arrayListWithObjects.size(); i++) {
			Assert.assertEquals(test.arrayListWithObjects.get(i).value, testResult.arrayListWithObjects.get(i).value);
		}
		
		Assert.assertNotSame(test.arrayWithObjects, testResult.arrayWithObjects);
		Assert.assertEquals(test.arrayWithObjects.length, testResult.arrayWithObjects.length);
		for (int i = 0; i < test.arrayWithObjects.length; i++) {
			Assert.assertEquals(test.arrayWithObjects[i].value, testResult.arrayWithObjects[i].value);
		}
		
		Assert.assertNotSame(test.hashMapWithObjects, testResult.hashMapWithObjects);
		Assert.assertEquals(test.hashMapWithObjects.size(), testResult.hashMapWithObjects.size());
		for (Entry<String, TestSimpleClass> entry : test.hashMapWithObjects.entrySet()) {
			Assert.assertEquals(entry.getValue().value, testResult.hashMapWithObjects.get(entry.getKey()).value);
		}
		
		Assert.assertNotSame(test.recursive, testResult.recursive);
		Assert.assertEquals(test.recursive.value, testResult.recursive.value);
		Assert.assertEquals(test.recursive.recursiveClass.value, testResult.recursive.recursiveClass.value);
		Assert.assertEquals(test.recursive.recursiveClass.recursiveClass.value, testResult.recursive.recursiveClass.recursiveClass.value);
		Assert.assertNull(testResult.recursive.recursiveClass.recursiveClass.recursiveClass);
	}
}