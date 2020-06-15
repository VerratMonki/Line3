package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounter implements Runnable {
    OnError onError;
    CounterLines counterLines;
    CounterLetter counter;
    CounterWords counterWords;
    BreakLineToCharArray breakLineToCharArray;
    ArrayBlockingQueue<String> arrayBlockingQueue;
    private boolean endDocument = false;

    //Block #1
    public ThreadCounter(ArrayBlockingQueue arrayBlockingQueue, OnError onError) {
        try {
            this.onError = onError;
            this.arrayBlockingQueue = arrayBlockingQueue;
            counterLines = new CounterLines();
            counter = new CounterLetter(onError);
            breakLineToCharArray = new BreakLineToCharArray(counter, onError);
            counterWords = new CounterWords(onError);
        }catch (Exception ex)
        {
            onError.onError(ex,"ThreadCounter","Block #1");
        }
    }

    //Block #2
    @Override
    public void run() {

        String currentLine;
        try {
            do {
                do {
                    currentLine = arrayBlockingQueue.take();
                    counterLines.nextLine(currentLine);
                    if (currentLine.trim().length() != 0) {
                        counterWords.nextLine(currentLine);
                        breakLineToCharArray.nextLine(currentLine);
                    }
                } while (!endDocument);
            }while (!arrayBlockingQueue.isEmpty());
        }catch (Exception ex)
        {
            onError.onError(ex,"ThreadCounter", "Block #2");
        }
    }

    public int getLines()
    {
        return counterLines.getCounterLines();
    }

    public int getWords()
    {
        return counterWords.getCounterWords();
    }

    public Map<Character, AtomicInteger> getMap()
    {
        return breakLineToCharArray.getMap();
    }

    public void changeBoolean()
    {
        endDocument = true;
    }
}
