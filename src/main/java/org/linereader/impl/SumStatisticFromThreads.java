package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SumStatisticFromThreads {
    int sumLines;
    int sumWords;
    OnError onError;
    boolean end = false;
    Map<String, List> counters;
    HashMap<Character, AtomicInteger> sumMaps;

    public SumStatisticFromThreads( OnError onError) {
        sumMaps = new HashMap();
        counters = new HashMap<String, List>();
        this.onError = onError;
    }

    public void addElementInMap(String key, LineConsumer lineConsumer)
    {
        if(counters.containsKey(key))
        {
            counters.get(key).add(lineConsumer);
        }
        else
        {
            counters.put(key, new ArrayList());
            counters.get(key).add(lineConsumer);
        }
    }

    //Block #1
    void sumStatistic()
    {
        try {
            for (Map.Entry<String,List> output : counters.entrySet())
            {
                if(output.getKey()=="counterLines")sumLines(output.getValue());
                if(output.getKey()=="counterWords")sumWords(output.getValue());
                if(output.getKey()=="counterLetters")sumMaps(output.getValue());
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

    private void sumLines(List<CounterLines> counterLines)
    {
        for (int i=0; i<counterLines.size();i++)sumLines += counterLines.get(i).getCounterLines();
    }

    private  void sumWords(List<CounterWords> counterWords)
    {
        for (int i=0; i<counterWords.size();i++)sumWords += counterWords.get(i).getCounterWords();
    }

    //Block #2
    private void sumMaps(List<BreakLineToCharArray> counterLetters)
    {
        try {
            for(int i = 0; i<counterLetters.size();i++)
            {
                Map<Character, AtomicInteger> currentMap = counterLetters.get(i).getMap();
                char currentLetter;
                for (Map.Entry<Character, AtomicInteger> output : currentMap.entrySet()) {
                    currentLetter = output.getKey();
                    if (sumMaps.containsKey(currentLetter))
                        sumMaps.get(currentLetter).addAndGet(output.getValue().get());
                    else sumMaps.put(currentLetter, new AtomicInteger(output.getValue().get()));
                }
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
