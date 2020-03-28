package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.util.Map;

public class Counters implements LineConsumer {
    CounterWords counterWords;
    CounterLines counterLines;
    CounterLetters counterLetters;
    OnError onError;

    //Block #1
    public Counters(CounterLetters counterLetters, CounterLines counterLines, CounterWords counterWords, OnError onError)
    {
        try {
            this.counterLetters = counterLetters;
            this.counterLines = counterLines;
            this.counterWords = counterWords;
            this.onError = onError;
        }catch (Exception ex)
        {
            onError.onError(ex, "Counters", "Block #1");
        }
    }
    
    public Counters() {
    
    }

    //Block #2
    @Override
    public void nextLine(String line) {
        try {
            counterLines.nextLine(line);
            counterWords.nextLine(line);
            counterLetters.nextLine(line);
        }catch (Exception ex)
        {
            onError.onError(ex,"Counters", "Block #2");
        }
    }

    @Override
    public void afterReadFile(String fileName) {

    }
    
    int getCounterLines()
    {
        return counterLines.getCounterLines();
    }
    
    int getCounterWords()
    {
        return counterWords.getCounterWords();
    }
    
    Map getMapCounter()
    {
        return counterLetters.getMap();
    }
}
