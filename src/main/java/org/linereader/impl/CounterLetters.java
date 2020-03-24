package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;

import java.util.Map;

public class CounterLetters implements LineConsumer {
    Counter counter;
    char[] lineToArray;
    

    public CounterLetters(Counter counter)
    {
        this.counter = counter;
    }


    @Override
    public void nextLine(String line) {
        lineToArray = line.toCharArray();
        for (int i=0; i<lineToArray.length; i++)
        {
            counter.count(lineToArray[i]);
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
