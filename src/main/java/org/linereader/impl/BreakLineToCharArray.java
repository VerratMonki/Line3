package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.util.Map;

public class BreakLineToCharArray implements LineConsumer {
    CounterLetter counterLetter;
    char[] lineToArray;
    OnError onError;
    

    public BreakLineToCharArray(CounterLetter counterLetter, OnError onError)
    {
        this.counterLetter = counterLetter;
        this.onError = onError;
    }

//Block #1
    @Override
    public void nextLine(String line) {
        try {
            lineToArray = line.toCharArray();
            for (int i = 0; i < lineToArray.length; i++) {
                counterLetter.count(lineToArray[i]);
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
        return counterLetter.getMapCounter();
    }

}
