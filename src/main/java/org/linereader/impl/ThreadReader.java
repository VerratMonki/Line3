package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadReader implements Runnable {
    OnError onError;
    String fileName;
    private String currentLine;
    private Formatter formatter;
    private Date date;
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    private long start;
    int numberThreads;
    ArrayList<ThreadCounter> threads = new ArrayList<>();

    public ThreadReader(OnError onError, String fileName, Formatter formatter, long start, int numberThreads) {
        this.onError = onError;
        this.fileName = fileName;
        this.formatter = formatter;
        date = new Date();
        this.start=start;
        this.numberThreads = numberThreads;
    }

    @Override
    public void run() {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            formatter.setDate(date.toString());
            formatter.setName(fileName);
            arrayBlockingQueue = new ArrayBlockingQueue<String>(100);
            for (int i = 0; i < numberThreads; i++) {
                ThreadCounter threadCounter = new ThreadCounter(arrayBlockingQueue, onError);
                threads.add(threadCounter);
                Thread thread = new Thread(threadCounter);
                thread.start();
            }
                while ((currentLine = bufferedReader.readLine()) != null) {
                    do {
                        if (arrayBlockingQueue.size() < 100) break;
                    } while (true);
                    if (currentLine.trim().length() != 0) arrayBlockingQueue.add(currentLine);
                }
                do {
                    if (arrayBlockingQueue.isEmpty()) break;
                } while (true);
                SumStatisticFromThreads sumStatisticFromThreads = new SumStatisticFromThreads(threads);
                sumStatisticFromThreads.sumStatistic();
                transportData(sumStatisticFromThreads);
            } catch(Exception e){
                onError.onError(e);
            }
    }

    private void transportData(SumStatisticFromThreads sumStatisticFromThreads) throws ParserConfigurationException {
        formatter.setLines(sumStatisticFromThreads.getSumLines());
        formatter.setWords(sumStatisticFromThreads.getSumWords());
        formatter.setLetters(sumStatisticFromThreads.getSumLetters());
        //formatter.changeName(numberThreads);
        long end = System.currentTimeMillis();
        long ms = end - start;
        System.out.println(numberThreads + " - " + ms);
        formatter.setTime(ms);
        formatter.createNewFile(onError);
    }
}
