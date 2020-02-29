package interfaces.impl;

import interfaces.LineConsumer;

import java.util.HashMap;
import java.util.Map;

public class Counters implements LineConsumer {
    CounterWords counterWords;
    CounterLines counterLines;
    CounterLetters counterLetters;
    int words;
    int lines;
    Map<Character,Integer> characters = new HashMap();

    public Counters(CounterLetters counterLetters, CounterLines counterLines, CounterWords counterWords)
    {
        this.counterLetters = counterLetters;
        this.counterLines = counterLines;
        this.counterWords = counterWords;
    }
    
    public Counters() {
    
    }
    
    @Override
    public void nextLine(String line) {
            counterLines.nextLine(line);
            counterWords.nextLine(line);
            counterLetters.nextLine(line);
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
