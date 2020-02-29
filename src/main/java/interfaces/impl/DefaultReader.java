package interfaces.impl;

import interfaces.Formatter;
import interfaces.GetData;
import interfaces.LineConsumer;
import interfaces.OnError;

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

    public void tryWithResources() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(createInputStreamByFilename(), StandardCharsets.UTF_8)))
        {
            formatter.writeDate(date.toString());
            formatter.nameFile(fileName);
            readFile(bufferedReader, lineConsumer);
        } catch (Exception ex) {
            error.onError(ex);
        }
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
        void transportData()
        {
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
