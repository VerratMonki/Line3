package org.linereader.impl;

import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

public class CounterWords implements LineConsumer {
    private String[] subLine;
    private String spliter = " ";
    private int counterWords = 0;
    OnError onError;

    public CounterWords(OnError onError) {
        this.onError = onError;
    }

    //Block #1
    @Override
    public void nextLine(String line) {
        try {
            subLine = line.split(spliter);
            for (int i = 0; i < subLine.length; i++) {
                if (subLine[i] == spliter || line.trim().length() == 0) continue;
                else counterWords++;
            }
        }catch (Exception ex) {
            onError.onError(ex, "CounterWords", "Block #1");
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
