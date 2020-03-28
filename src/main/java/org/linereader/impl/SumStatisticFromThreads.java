package org.linereader.impl;

import org.linereader.interfaces.OnError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SumStatisticFromThreads {
    ArrayList<ThreadCounter> threads;
    int sumLines;
    int sumWords;
    //int sumNumber;
    OnError onError;
    boolean end = false;
    HashMap<Character, AtomicInteger> sumMaps;

    public SumStatisticFromThreads(ArrayList threads, OnError onError) {
        this.threads=threads;
        sumMaps = new HashMap();
        this.onError = onError;
    }

    //Block #1
    void sumStatistic()
    {
        try {
            for (int i = 0; i < threads.size(); i++) {
                sumLines(threads.get(i));
                sumWords(threads.get(i));
                sumMaps(threads.get(i));
            }
            changeEnd();
        }catch (Exception ex){
            onError.onError(ex,"SumStatisticFromThreads","Block #1");
        }
    }

    private void changeEnd()
    {
        end = true;
    }

    boolean checkEnd()
    {
        return end;
    }

    private void sumLines(ThreadCounter threadCounter)
    {
        sumLines += threadCounter.getLines();
    }

    private  void sumWords(ThreadCounter threadCounter)
    {
        sumWords += threadCounter.getWords();
    }

    //Block #2
    private void sumMaps(ThreadCounter threadCounter)
    {
        try {
            Map<Character, AtomicInteger> currentMap = threadCounter.getMap();
            char currentLetter;
            int currentNumber;
            for (Map.Entry<Character, AtomicInteger> output : currentMap.entrySet()) {
                currentLetter = output.getKey();
            /*currentNumber = output.getValue();
            sumNumber = sumMaps.getOrDefault(currentLetter,0);
            sumNumber += currentNumber;
            sumMaps.put(currentLetter,sumNumber);*/
                sumMaps.get(currentLetter).addAndGet(output.getValue().get());
            }
        }catch (Exception ex){
            onError.onError(ex,"SumStatisticFromThreads","Block #2");
        }
    }

    public int getSumLines()
    {
        return sumLines;
    }

    public int getSumWords()
    {
        return sumWords;
    }

    public Map getSumLetters()
    {
        return sumMaps;
    }

}
