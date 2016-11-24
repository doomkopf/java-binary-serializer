package com.hypercode.binaryserializer;

public class Mapping<K, V> {
	
	public final K key;
	public final V value;
	
	public Mapping(K key, V value) {
		this.key = key;
		this.value = value;
	}
}