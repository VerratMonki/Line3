package org.linereader.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.spy;

class CounterTest {
	
	@BeforeEach
	void setUp() {
	}
	
	@AfterEach
	void tearDown() {
	}
	
	@Test
	void count() {
		Counter counter = spy(new Counter());
		
		counter.count('g');
		Map<Character, Integer> mapCounter=counter.getMapCounter();
		
		if(mapCounter.get('g')==1) Assertions.assertTrue(true);
		else Assertions.assertFalse(true);
	}
}