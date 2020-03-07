package org.linereader.impl;

import java.util.HashMap;
import java.util.Map;

public class Counter {
    private Map<Character, Integer>counter = new HashMap();
    private int CounterNumber;
    
    void count(char letter) throws NullPointerException
    {
        CounterNumber = counter.getOrDefault(letter,0);
        CounterNumber++;
        counter.put(letter,CounterNumber);
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
