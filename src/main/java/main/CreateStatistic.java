package main;

import java.util.Date;

public class CreateStatistic {
    private String fileName;
    private long timeWork;
    Date date1;
    String date;
    private int sumLines;
    private int sumWords;
    private String sumLetters;

    public CreateStatistic(String fileName, long timeWork, int sumLines, int sumWords, String sumLetters)
    {
        this.fileName = fileName;
        this.timeWork = timeWork;
        this.sumLines = sumLines;
        this.sumWords = sumWords;
        this.sumLetters = sumLetters;
        date1 = new Date();
        date = date1.toString();
    }

    public String getDate() {
        return date;
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

    public String getSumLetters() {
        return sumLetters;
    }
}
