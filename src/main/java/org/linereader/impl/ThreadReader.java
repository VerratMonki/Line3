package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadReader implements Runnable {
    OnError onError;
    String fileName;
    private String currentLine;
    private Formatter formatter;
    private Date date;
    private ArrayBlockingQueue<String> arrayBlockingQueue;

    public ThreadReader(OnError onError, String fileName, Formatter formatter) {
        this.onError = onError;
        this.fileName = fileName;
        this.formatter = formatter;
        date = new Date();
    }

    @Override
    public void run() {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)))
        {
            formatter.setDate(date.toString());
            formatter.setName(fileName);
            arrayBlockingQueue = new ArrayBlockingQueue<String>(100);
            ThreadCounter firstCounter = new ThreadCounter(arrayBlockingQueue,onError);
            ThreadCounter secondCounter = new ThreadCounter(arrayBlockingQueue,onError);
            ThreadCounter thirdCounter = new ThreadCounter(arrayBlockingQueue,onError);
            ThreadCounter fourthCounter = new ThreadCounter(arrayBlockingQueue,onError);
            ThreadCounter fifthCounter = new ThreadCounter(arrayBlockingQueue,onError);
            firstCounter.run();
            secondCounter.run();
            thirdCounter.run();
            fourthCounter.run();
            fifthCounter.run();
            while ((currentLine = bufferedReader.readLine())!=null)
            {
                arrayBlockingQueue.add(currentLine);
            }
            SumStatisticFromThreads sumStatisticFromThreads = new SumStatisticFromThreads(firstCounter,secondCounter,thirdCounter,fourthCounter,fifthCounter);
            sumStatisticFromThreads.sumStatistic();
            transportData(sumStatisticFromThreads);
        } catch (Exception e) {
            onError.onError(e);
        }
    }

    private void transportData(SumStatisticFromThreads sumStatisticFromThreads) throws ParserConfigurationException {
        formatter.setLines(sumStatisticFromThreads.getSumLines());
        formatter.setWords(sumStatisticFromThreads.getSumWords());
        formatter.setLetters(sumStatisticFromThreads.getSumLetters());
        formatter.createNewFile(onError);
    }
}
