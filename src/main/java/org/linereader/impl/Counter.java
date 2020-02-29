package org.linereader.impl;

import java.util.HashMap;
import java.util.Map;

public class Counter {
    private Map<Character, Integer>counter;
    private int CounterNumber;
    
    void count(char letter) throws NullPointerException
    {
        counter = createMapCounter();
        CounterNumber = counter.getOrDefault(letter,0);
        CounterNumber++;
        counter.put(letter,CounterNumber);
    }
    
    Map createMapCounter()
    {
        return new HashMap();
    }
//    public void print()
//    {
//        for (Map.Entry<Character, Integer> output : counter.entrySet())
//        {
//            System.out.println("{" + output.getKey() + "} -> " + output.getValue());
//        }
//    }
    
    public Map getMapCounter()
    {
        return counter;
    }
    

}
