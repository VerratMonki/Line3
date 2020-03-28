package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.util.Map;

public class CounterLetters implements LineConsumer {
    Counter counter;
    char[] lineToArray;
    OnError onError;
    

    public CounterLetters(Counter counter, OnError onError)
    {
        this.counter = counter;
        this.onError = onError;
    }

//Block #1
    @Override
    public void nextLine(String line) {
        try {
            lineToArray = line.toCharArray();
            for (int i = 0; i < lineToArray.length; i++) {
                counter.count(lineToArray[i]);
            }
        }catch (Exception ex)
        {
            onError.onError(ex, "CounterLetters", "Block #1");
        }
    }

    @Override
    public void afterReadFile(String fileName) {
        //counter.print();
    }

    public Map getMap()
    {
        return counter.getMapCounter();
    }

}
