package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HTML implements Formatter {
    private final String nameFile = "statistic.html";
    private String startHtmlCode = "<html><body>";
    private String endHtmlCode = "</body></html>";
    private String fileName;
    private String date;
    private int lines;
    private int words;
    private Map letters;
    
    public HTML() throws FileNotFoundException, UnsupportedEncodingException {
    }
    
    void createTable(PrintWriter printWriter)
    {
        printWriter.print("<table><tr><td>");
    }
    
    void newCell(PrintWriter printWriter)
    {
        printWriter.print("</td><td>");
    }
    
    void newLine(PrintWriter printWriter)
    {
        printWriter.print("</td></tr><tr><td>");
    }
    
    void closeTable(PrintWriter printWriter)
    {
        printWriter.print("</td></tr></table>");
    }

    PrintWriter setPrintWriter() throws FileNotFoundException, UnsupportedEncodingException {
        return new PrintWriter(nameFile, "UTF-8");
    }

    @Override
    public void createNewFile(OnError onError) {
        try(PrintWriter printWriter = setPrintWriter())
        {
            printWriter.print(startHtmlCode);
            createTable(printWriter);
            writeDate(printWriter);
            writeFileName(printWriter);
            writeLines(printWriter);
            writeWords(printWriter);
            writeLetters(printWriter);
            closeTable(printWriter);
            printWriter.print(endHtmlCode);
        }catch (Exception ex)
        {
            onError.onError(ex);
        }
    }

    void writeDate(PrintWriter printWriter)
    {
        printWriter.print(date);
        newCell(printWriter);
        newCell(printWriter);
    }

    void writeFileName(PrintWriter printWriter)
    {
        printWriter.print("There are in file '");
        printWriter.print(fileName);
        printWriter.print("'");
        newLine(printWriter);
    }

    void writeLines(PrintWriter printWriter)
    {
        printWriter.print(lines);
        printWriter.print(" lines");
        newCell(printWriter);
    }

    void writeWords(PrintWriter printWriter)
    {
        printWriter.print(words);
        printWriter.print(" words");
        newCell(printWriter);
    }

    void writeLetters(PrintWriter printWriter)
    {
        printWriter.print("There are letters : ");
        printWriter.print(letters);
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


//    @Override
//    public void nameFile(PrintWriter printWriter) {
//        newCell(printWriter);
//        printWriter.print("There are in file '" + fileName + "' :");
//    }
//
//    @Override
//    public void writeLines(PrintWriter printWriter) {
//        newLine(printWriter);
//        printWriter.print(lines + " lines;");
//    }
//
//    @Override
//    public void writeWords(PrintWriter printWriter) {
//        newCell(printWriter);
//        printWriter.print(words + " words;");
//    }
//
//    @Override
//    public void writeLetters(PrintWriter printWriter) {
//        newCell(printWriter);
//        printWriter.print(map);
//        printWriter.print(endHtmlCode);
//        closeTable(printWriter);
//    }
}
