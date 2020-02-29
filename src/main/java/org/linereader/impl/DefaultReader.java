package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.GetData;
import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class DefaultReader {
    private LineConsumer lineConsumer;
    private OnError error;
    private String fileName;
    private Formatter formatter;
    private GetData getData;
    Date date;
    
    public DefaultReader(LineConsumer lineConsumer, OnError error) {
        this.lineConsumer = lineConsumer;
        this.error = error;
    }

    public DefaultReader(String fileName, LineConsumer lineConsumer, OnError error, Formatter formatter, GetData getData) {
        this.fileName = fileName;
        this.lineConsumer = lineConsumer;
        this.error = error;
        this.formatter = formatter;
        this.getData = getData;
        date = new Date();
    }

    public void tryWithResources(OnError onError) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(createInputStreamByFilename(), StandardCharsets.UTF_8)))
        {
            createFile("statistic.html");
            formatter.writeDate(date.toString());
            formatter.nameFile(fileName);
            readFile(bufferedReader, lineConsumer);
        } catch (Exception ex) {
            error.onError(ex);
        }
    }
    
    void createFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter printWriter = new PrintWriter(fileName, "UTF-8");
    }
    
    FileInputStream createInputStreamByFilename() throws FileNotFoundException {
        return new FileInputStream(fileName);
    }
    
    void readFile(BufferedReader bufferedReader, LineConsumer lineConsumer) throws IOException {
            readingLines(lineConsumer, bufferedReader);
            lineConsumer.afterReadFile(fileName);
            transportData();
        }

        //ok
         void readingLines(LineConsumer lineConsumer, BufferedReader bufferedReader) throws IllegalArgumentException, IOException {
            if(bufferedReader==null) {
                throw new IllegalArgumentException();
            }
             String currentLine;
            while ((currentLine = bufferedReader.readLine())!=null) {
                lineConsumer.nextLine(currentLine);
            }
        }
        void transportData() throws IOException {
            formatter.writeLines(getData.getLines());
            formatter.writeWords(getData.getWords());
            formatter.writeLetters(getData.getMap());
        }
    
     LineConsumer getLineConsumer() {
        return lineConsumer;
    }
    
     OnError getError() {
        return error;
    }
    
     String getFileName() {
        return fileName;
    }
    
    }
