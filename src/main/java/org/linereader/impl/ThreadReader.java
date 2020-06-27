package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadReader implements Runnable {
    OnError onError;
    String fileName;
    MultipartFile multipartFile;
    private String currentLine;
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    int numberThreads;
    SumStatisticFromThreads sumStatisticFromThreads;
    InputStreamReader inputStreamReader;
    AtomicBoolean endDocument = new AtomicBoolean();

    //Block #1
    public ThreadReader(OnError onError, String fileName, int numberThreads) {
        try {
            this.onError = onError;
            this.fileName = fileName;
            this.numberThreads = numberThreads;
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        }catch (Exception ex){
            onError.onError(ex, "ThreadReader", "Block #1");
        }
    }

    public ThreadReader(OnError onError, MultipartFile multipartFile, int numberThreads) {
        try {
            this.onError = onError;
            this.multipartFile=multipartFile;
            this.numberThreads = numberThreads;
            inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        }catch (Exception ex){
            onError.onError(ex, "ThreadReader", "Block #1");
        }
    }

    //Block #2
    @Override
    public void run() {

        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            endDocument.set(false);
            arrayBlockingQueue = new ArrayBlockingQueue<String>(100);
            sumStatisticFromThreads = new SumStatisticFromThreads(onError);
            createThreads();
            addLinesInArray(bufferedReader);
            sumStatisticFromThreads.sumStatistic();
            while(!sumStatisticFromThreads.checkEnd()){

            }
        } catch(Exception e){
            e.printStackTrace();
            //onError.onError(e, "ThreadReader", "Block #2");
        }
    }

    private void createThreads()
    {
        for (int i = 0; i < numberThreads; i++) {
            CounterLines counterLines = new CounterLines();
            sumStatisticFromThreads.addElementInMap("counterLines",counterLines);
            CounterLetter counterLetter = new CounterLetter(onError);
            BreakLineToCharArray breakLineToCharArray = new BreakLineToCharArray(counterLetter, onError);
            sumStatisticFromThreads.addElementInMap("counterLetters",breakLineToCharArray);
            CounterWords counterWords = new CounterWords(onError);
            sumStatisticFromThreads.addElementInMap("counterWords",counterWords);
            ThreadCounter threadCounter = new ThreadCounter(arrayBlockingQueue, onError, counterLines, breakLineToCharArray, counterWords, endDocument);
            Thread thread = new Thread(threadCounter);
            thread.start();
        }
    }

    //Block #3
    private void addLinesInArray(BufferedReader bufferedReader) throws IOException {
        try {
            while ((currentLine = bufferedReader.readLine()) != null) {
                while (!arrayBlockingQueue.offer(currentLine)) ;
            }
            endDocument.set(true);
            do {
                if (arrayBlockingQueue.isEmpty()) break;
            } while (true);
        }catch (Exception ex){
            onError.onError(ex, "ThreadReader", "Block #3");
        }
    }

    public int getSumLines()
    {
        return sumStatisticFromThreads.getSumLines();
    }

    public int getSumWords()
    {
        return sumStatisticFromThreads.getSumWords();
    }

    public Map<Character, AtomicInteger> getSumLetters()
    {
        return sumStatisticFromThreads.getSumLetters();
    }
}
