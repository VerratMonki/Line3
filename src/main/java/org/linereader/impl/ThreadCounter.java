package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadCounter implements Runnable {
    OnError onError;
    CounterLines counterLines;
    Counter counter;
    CounterWords counterWords;
    CounterLetters counterLetters;
    ArrayBlockingQueue<String> arrayBlockingQueue;

    public ThreadCounter(ArrayBlockingQueue arrayBlockingQueue, OnError onError) {
        this.onError = onError;
        this.arrayBlockingQueue = arrayBlockingQueue;
        counterLines = new CounterLines();
        counter = new Counter();
        counterLetters = new CounterLetters(counter);
        counterWords = new CounterWords();
    }

    @Override
    public void run() {

        String currentLine;
        try {
            do {
                currentLine = getCurrentLine();
                counterLines.nextLine(currentLine);
                counterWords.nextLine(currentLine);
                counterLetters.nextLine(currentLine);
            }while (currentLine!=null);
        }catch (Exception ex)
        {
            onError.onError(ex);
        }
    }

    private String getCurrentLine() throws InterruptedException {
        return arrayBlockingQueue.take();
    }

    public int getLines()
    {
        return counterLines.getCounterLines();
    }

    public int getWords()
    {
        return counterWords.getCounterWords();
    }

    public Map<Character, Integer> getMap()
    {
        return counterLetters.getMap();
    }
}
