package org.linereader.impl;

import org.linereader.interfaces.GetData;

import java.util.Map;

public class GetInformation implements GetData {
    Counters counters;
    public GetInformation(Counters counters)
    {
        this.counters = counters;
    }
    
    @Override
    public int getWords() {
        return counters.getCounterWords();
    }

    @Override
    public int getLines() {
        return counters.getCounterLines();
    }

    @Override
    public Map getMap() {
        return counters.getMapCounter();
    }
}

