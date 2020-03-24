package org.linereader.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SumStatisticFromThreads {
    ArrayList<ThreadCounter> threads;
    int sumLines;
    int sumWords;
    int sumNumber;
    HashMap<Character, AtomicInteger> sumMaps;

    public SumStatisticFromThreads(ArrayList threads) {
        this.threads=threads;
        sumMaps = new HashMap();
    }

    public void sumStatistic()
    {
        for(int i = 0; i<threads.size();i++)
        {
            sumLines(threads.get(i));
            sumWords(threads.get(i));
            sumMaps(threads.get(i));
        }
    }

    private void sumLines(ThreadCounter threadCounter)
    {
        sumLines += threadCounter.getLines();
    }

    private  void sumWords(ThreadCounter threadCounter)
    {
        sumWords += threadCounter.getWords();
    }

    private void sumMaps(ThreadCounter threadCounter)
    {
        Map<Character, AtomicInteger> currentMap = threadCounter.getMap();
        char currentLetter;
        int currentNumber;
        for (Map.Entry<Character, AtomicInteger> output : currentMap.entrySet())
        {
            currentLetter = output.getKey();
            /*currentNumber = output.getValue();
            sumNumber = sumMaps.getOrDefault(currentLetter,0);
            sumNumber += currentNumber;
            sumMaps.put(currentLetter,sumNumber);*/
            sumMaps.get(currentLetter).addAndGet(output.getValue().get());
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
