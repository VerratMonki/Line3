package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;

public class CounterWords implements LineConsumer {
    private String[] subLine;
    private String spliter = " ";
    private int counterWords = 0;
    @Override
    public void nextLine(String line) {
        subLine = line.split(spliter);
        for (int i = 0; i < subLine.length; i++)
        {
            if(subLine[i]==spliter || line.trim().length() == 0) continue;
            else counterWords++;
        }
    }

    @Override
    public void afterReadFile(String fileName) {
        System.out.println(counterWords + " words.");
    }

    int getCounterWords()
    {
        return counterWords;
    }

}
