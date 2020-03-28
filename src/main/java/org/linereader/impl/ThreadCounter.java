package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounter implements Runnable {
    OnError onError;
    CounterLines counterLines;
    Counter counter;
    CounterWords counterWords;
    CounterLetters counterLetters;
    ArrayBlockingQueue<String> arrayBlockingQueue;

    //Block #1
    public ThreadCounter(ArrayBlockingQueue arrayBlockingQueue, OnError onError) {
        try {
            this.onError = onError;
            this.arrayBlockingQueue = arrayBlockingQueue;
            counterLines = new CounterLines();
            counter = new Counter(onError);
            counterLetters = new CounterLetters(counter, onError);
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
                currentLine = arrayBlockingQueue.take();
                counterLines.nextLine(currentLine);
                counterWords.nextLine(currentLine);
                counterLetters.nextLine(currentLine);
            }while (currentLine!=null);
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
        return counterLetters.getMap();
    }
}
