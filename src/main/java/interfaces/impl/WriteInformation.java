package interfaces.impl;

import java.util.Map;

public class WriteInformation {
	public void write(Counters counters)
	{
		System.out.println("There are in file : ");
		System.out.println(counters.getCounterLines() + " lines.");
		System.out.println(counters.getCounterWords() + " words.");
		
	}
	
	void writeMapCounter(Counters counters)
	{
		Map<Character, Integer> counter = counters.getMapCounter();
		System.out.println("There are letters : ");
		for (Map.Entry<Character, Integer> output : counter.entrySet())
		{
			System.out.println("{" + output.getKey() + "} -> " + output.getValue());
		}
	}
}
