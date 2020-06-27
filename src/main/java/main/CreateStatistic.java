package main;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateStatistic {
    private String fileName;
    private long timeWork;
    Date date;
    private int sumLines;
    private int number_Maps;
    private int sumWords;
    private List sumLetters;

    public CreateStatistic(String fileName, long timeWork, int sumLines, int sumWords)
    {
        this.fileName = fileName;
        this.timeWork = timeWork;
        this.sumLines = sumLines;
        this.sumWords = sumWords;
        date = new Date();
    }

    public void getMaps(List sumLetters)
    {
        setNumber_Maps(sumLetters.size());
        this.sumLetters=sumLetters;
    }

    public void setNumber_Maps(int number_Maps) {
        this.number_Maps = number_Maps;
    }

    public String getDate() {
        return date.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public long getTimeWork() {
        return timeWork;
    }

    public int getSumLines() {
        return sumLines;
    }

    public int getSumWords() {
        return sumWords;
    }

    public List getSumLetters() {
        return sumLetters;
    }
}
