package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.Statistics;

public class CounterLines implements LineConsumer, Statistics {
    private int counterLines;
    @Override
    public void nextLine(String line) {
        counterLines++;
    }

    @Override
    public void afterReadFile(String fileName) {
        //System.out.println(counterLines + " lines.");
    }

    int getCounterLines()
    {
        return counterLines;
    }
    
    @Override
    public void statisticLines() {
    
    }
}
