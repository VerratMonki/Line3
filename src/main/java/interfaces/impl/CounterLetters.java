package interfaces.impl;

import interfaces.LineConsumer;

import java.util.Map;

public class CounterLetters implements LineConsumer {
    Counter counter;
    Map<Character,Integer> linetoMap;
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
