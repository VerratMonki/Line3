package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class TXT implements Formatter {
    private String startNameFile = "statistic";
    private String endNameFile= ".txt";
    private String fileName;
    private String date;
    private int lines;
    private int words;
    private Map letters;
    private long ms;

    //Block #!
    @Override
    public void createNewFile(OnError onError) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(nameNewFile()))
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            fileOutputStream.write(date.getBytes());
            writeTab(fileOutputStream);
            String startMessage = "There are in file '";
            String endMessage = "' :";
            fileOutputStream.write(startMessage.getBytes());
            fileOutputStream.write(fileName.getBytes());
            fileOutputStream.write(endMessage.getBytes());
            writeTab(fileOutputStream);
            endMessage = " lines";
            fileOutputStream.write(lines);
            fileOutputStream.write(endMessage.getBytes());
            writeTab(fileOutputStream);
            endMessage = " words";
            fileOutputStream.write(words);
            fileOutputStream.write(endMessage.getBytes());
            writeTab(fileOutputStream);
            startMessage = "There are letters : ";
            fileOutputStream.write(startMessage.getBytes());
            objectOutputStream.writeObject(letters);
            startMessage = "The programme worked in ";
            endMessage = " ms.";
            writeTab(fileOutputStream);
            fileOutputStream.write(startMessage.getBytes());
            fileOutputStream.write((int) ms);
            fileOutputStream.write(endMessage.getBytes());
        }catch (Exception ex)
        {
            onError.onError(ex,"TXT","Block #1");
        }
    }

    String nameNewFile()
    {
        String fileName = startNameFile + endNameFile;
        return fileName;
    }

    @Override
    public void setName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setLines(int lines) {
        this.lines = lines;
    }

    @Override
    public void setWords(int words) {
        this.words = words;
    }

    @Override
    public void setLetters(Map map) {
        letters = map;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setTime(long ms) {
        this.ms = ms;
    }

    @Override
    public void changeName(int number) {
        startNameFile += Integer.toString(number);
    }

    //    @Override
//    public void writeDate(String date) throws IOException {
//        fileOutputStream.write(date.getBytes());
//        writeTab();
//    }
//
//    @Override
//    public void nameFile(String fileName) throws IOException {
//        String startMessage = "There are in file '";
//        String endMessage = "' :";
//        fileOutputStream.write(startMessage.getBytes());
//        fileOutputStream.write(fileName.getBytes());
//        fileOutputStream.write(endMessage.getBytes());
//        writeTab();
//    }
//
//    @Override
//    public void writeLines(int lines) throws IOException {
//        String endMessage = " lines;";
//        fileOutputStream.write(lines);
//        fileOutputStream.write(endMessage.getBytes());
//        writeTab();
//
//    }
//
//    @Override
//    public void writeWords(int words) throws IOException {
//        String endMessage = " words;";
//        fileOutputStream.write(words);
//        fileOutputStream.write(endMessage.getBytes());
//        writeTab();
//    }
//
//    @Override
//    public void writeLetters(Map map) throws IOException {
//        String startMessage = "There are letters : ";
//        fileOutputStream.write(startMessage.getBytes());
//        objectOutputStream.writeObject(map);
//
//    }
//
    private void writeTab(FileOutputStream fileOutputStream) throws IOException {
        String tab = "\t";
        fileOutputStream.write(tab.getBytes());
    }


}
