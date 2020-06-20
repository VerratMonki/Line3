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
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadReader implements Runnable {
    OnError onError;
    String fileName;
    MultipartFile multipartFile;
    private String currentLine;
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    int numberThreads;
    private boolean way;
    SumStatisticFromThreads sumStatisticFromThreads;
    private boolean endDocument = false;
    ArrayList<ThreadCounter> threads = new ArrayList<>();

    //Block #1
    public ThreadReader(OnError onError, String fileName, int numberThreads) {
        try {
            this.onError = onError;
            this.fileName = fileName;
            this.numberThreads = numberThreads;
            way = true;
        }catch (Exception ex){
            onError.onError(ex, "ThreadReader", "Block #1");
        }
    }

    public ThreadReader(OnError onError, MultipartFile multipartFile, int numberThreads) {
        try {
            this.onError = onError;
            this.multipartFile=multipartFile;
            this.numberThreads = numberThreads;
            way = false;
        }catch (Exception ex){
            onError.onError(ex, "ThreadReader", "Block #1");
        }
    }

    private InputStreamReader getInputStreamReader() throws IOException {
        if(way)return new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        else return new InputStreamReader(multipartFile.getInputStream());
    }


    //Block #2
    @Override
    public void run() {

        try {
            BufferedReader bufferedReader = new BufferedReader(getInputStreamReader());
            arrayBlockingQueue = new ArrayBlockingQueue<String>(100);
            addLinesInArray(bufferedReader);
            sumStatisticFromThreads = new SumStatisticFromThreads(threads,onError);
            sumStatisticFromThreads.sumStatistic();
            while(!sumStatisticFromThreads.checkEnd()){

            }

        } catch(Exception e){
            onError.onError(e, "ThreadReader", "Block #2");
        }
    }

    //Block #3
    private void addLinesInArray(BufferedReader bufferedReader) throws IOException {
        try {
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
                //if (currentLine.trim().length() != 0)
                arrayBlockingQueue.add(currentLine);
            }
            for(int i=0;i<threads.size();i++) threads.get(i).changeBoolean();
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
