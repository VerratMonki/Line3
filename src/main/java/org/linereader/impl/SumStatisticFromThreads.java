package org.linereader.impl;

import java.util.HashMap;
import java.util.Map;

public class SumStatisticFromThreads {
    ThreadCounter firstCounter;
    ThreadCounter secondCounter;
    ThreadCounter thirdCounter;
    ThreadCounter fourthCounter;
    ThreadCounter fifthCounter;
    int sumLines;
    int sumWords;
    int sumNumber;
    HashMap<Character, Integer> sumMaps;

    public SumStatisticFromThreads(ThreadCounter firstCounter, ThreadCounter secondCounter, ThreadCounter thirdCounter, ThreadCounter fourthCounter, ThreadCounter fifthCounter) {
        this.firstCounter = firstCounter;
        this.secondCounter = secondCounter;
        this.thirdCounter = thirdCounter;
        this.fourthCounter = fourthCounter;
        this.fifthCounter = fifthCounter;
        sumMaps = new HashMap();
    }

    public void sumStatistic()
    {
        sumLines();
        sumWords();
        sumMaps(firstCounter);
        sumMaps(secondCounter);
        sumMaps(thirdCounter);
        sumMaps(fourthCounter);
        sumMaps(fifthCounter);
    }

    private void sumLines()
    {
        sumLines += firstCounter.getLines();
        sumLines += secondCounter.getLines();
        sumLines += thirdCounter.getLines();
        sumLines += fourthCounter.getLines();
        sumLines += fifthCounter.getLines();
    }

    private  void sumWords()
    {
        sumWords += firstCounter.getWords();
        sumWords += secondCounter.getWords();
        sumWords += thirdCounter.getWords();
        sumWords += fourthCounter.getWords();
        sumWords += fifthCounter.getWords();
    }

    private void sumMaps(ThreadCounter threadCounter)
    {
        Map<Character, Integer> currentMap = threadCounter.getMap();
        char currentLetter;
        int currentNumber;
        for (Map.Entry<Character, Integer> output : currentMap.entrySet())
        {
            currentLetter = output.getKey();
            currentNumber = output.getValue();
            sumNumber = sumMaps.getOrDefault(currentLetter,0);
            sumNumber += currentNumber;
            sumMaps.put(currentLetter,sumNumber);
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
