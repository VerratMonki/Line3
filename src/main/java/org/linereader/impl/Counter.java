package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private Map<Character, AtomicInteger>counter = new HashMap();
    //private int CounterNumber;
    OnError onError;

    public Counter(OnError onError) {
        this.onError = onError;
    }

    //Block #1
    void count(char letter)
    {
        try {
            /*CounterNumber = counter.getOrDefault(letter,0);
            CounterNumber++;
            counter.put(letter,CounterNumber);*/
            if (counter.containsKey(letter)) counter.get(letter).getAndIncrement();
            else counter.put(letter, new AtomicInteger(1));
        }catch (Exception ex)
        {
            onError.onError(ex, "Counter", "Block #1");
        }
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
