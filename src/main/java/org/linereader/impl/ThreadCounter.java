package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounter implements Runnable {
    OnError onError;
    CounterLines counterLines;
    CounterWords counterWords;
    BreakLineToCharArray breakLineToCharArray;
    ArrayBlockingQueue<String> arrayBlockingQueue;
    private AtomicBoolean endDocument;

    //Block #1
    public ThreadCounter(ArrayBlockingQueue arrayBlockingQueue, OnError onError, CounterLines counterLines, BreakLineToCharArray breakLineToCharArray, CounterWords counterWords, AtomicBoolean endDocument) {
        try {
            this.onError = onError;
            this.arrayBlockingQueue = arrayBlockingQueue;
            this.counterLines = counterLines;
            this.breakLineToCharArray = breakLineToCharArray;
            this.counterWords = counterWords;
            this.endDocument = endDocument;
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
                currentLine = null;
                if(endDocument.get() && arrayBlockingQueue.isEmpty()) {
                    break;
                }
                while (true) {
                    if(!arrayBlockingQueue.isEmpty()) currentLine = arrayBlockingQueue.take();
                    if(currentLine!=null)break;
                }
                counterLines.nextLine(currentLine);
                if (currentLine.trim().length() != 0) {
                    counterWords.nextLine(currentLine);
                    breakLineToCharArray.nextLine(currentLine);
                }
            } while (true);

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
}
