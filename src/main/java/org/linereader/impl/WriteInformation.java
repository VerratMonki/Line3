package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.Map;

public class WriteInformation {
	OnError onError;

	public WriteInformation(OnError onError) {
		this.onError = onError;
	}

	//Block #!
	public void write(Counters counters)
	{
		try {
			System.out.println("There are in file : ");
			System.out.println(counters.getCounterLines() + " lines.");
			System.out.println(counters.getCounterWords() + " words.");
		}catch (Exception ex)
		{
			onError.onError(ex,"WriteInformation","Block #1");
		}
	}

	//Block #2
	void writeMapCounter(Counters counters)
	{
		try {
			Map<Character, Integer> counter = counters.getMapCounter();
			System.out.println("There are letters : ");
			for (Map.Entry<Character, Integer> output : counter.entrySet()) {
				System.out.println("{" + output.getKey() + "} -> " + output.getValue());
			}
		}catch (Exception ex)
		{
			onError.onError(ex,"WriteInformation","Block #2");
		}
	}
}
