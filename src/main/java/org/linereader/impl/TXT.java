package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class TXT implements Formatter {
    private final String nameFile = "statistic";
    private String nameFile2 = nameFile;
    private String typeFile = ".txt";
    private int indexFile = 0;
    FileOutputStream fileOutputStream;
    ObjectOutputStream objectOutputStream;

    TXT(OnError onError)
    {
        if(indexFile>0) nameFile2 += Integer.toString(indexFile);
        nameFile2 += typeFile;
        try(FileOutputStream fileOutputStream = new FileOutputStream(nameFile2))
        {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            nameFile2 = nameFile;
            setFileOutputStream(fileOutputStream);
            indexFile++;
        }catch (Exception ex)
        {
            onError.onError(ex);
        }
    }

    void setFileOutputStream(FileOutputStream fileOutputStream)
    {
        this.fileOutputStream = fileOutputStream;
    }

    @Override
    public void createNewFile(OnError onError) {
        new TXT(onError);
    }

    @Override
    public void writeDate(String date) throws IOException {
        fileOutputStream.write(date.getBytes());
        writeTab();
    }

    @Override
    public void nameFile(String fileName) throws IOException {
        String startMessage = "There are in file '";
        String endMessage = "' :";
        fileOutputStream.write(startMessage.getBytes());
        fileOutputStream.write(fileName.getBytes());
        fileOutputStream.write(endMessage.getBytes());
        writeTab();
    }

    @Override
    public void writeLines(int lines) throws IOException {
        String endMessage = " lines;";
        fileOutputStream.write(lines);
        fileOutputStream.write(endMessage.getBytes());
        writeTab();

    }

    @Override
    public void writeWords(int words) throws IOException {
        String endMessage = " words;";
        fileOutputStream.write(words);
        fileOutputStream.write(endMessage.getBytes());
        writeTab();
    }

    @Override
    public void writeLetters(Map map) throws IOException {
        String startMessage = "There are letters : ";
        fileOutputStream.write(startMessage.getBytes());
        objectOutputStream.writeObject(map);

    }

    private void writeTab() throws IOException {
        String tab = "\t";
        fileOutputStream.write(tab.getBytes());
    }


}
