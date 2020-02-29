package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HTML implements Formatter {
    private final String nameFile = "statistic";
    private String typeFile = ".html";
    private String nameFile2 = nameFile + typeFile;
    private String startHtmlCode = "<html><body>";
    private String endHtmlCode = "</body></html>";
    private int indexFile = 0;
    PrintWriter printWriter = new PrintWriter(nameFile2, "UTF-8");
    
    public HTML() throws FileNotFoundException, UnsupportedEncodingException {
    }
    
    void setPrintWriter(PrintWriter printWriter)
    {
        this.printWriter = printWriter;
    }
    
    void createTable()
    {
        printWriter.print("<table><tr><td>");
    }
    
    void newCell()
    {
        printWriter.print("</td><td>");
    }
    
    void newLine()
    {
        printWriter.print("</td></tr><tr><td>");
    }
    
    void closeTable()
    {
        printWriter.print("</td></tr></table>");
    }

    @Override
    public void createNewFile(OnError onError, PrintWriter printWriter) {
        printWriter.print(15);
      /*  if(indexFile>0) nameFile2 += Integer.toString(indexFile);
        nameFile2 += typeFile;
        try(PrintWriter printWriter = new PrintWriter(nameFile2, "UTF-8"))
        {
            //nameFile2 = nameFile;
            printWriter.print(startHtmlCode);
            setPrintWriter(printWriter);
            indexFile++;
        }catch (Exception ex)
        {
            onError.onError(ex);
        }*/
    }

    
    @Override
    public void writeDate(String date){
        createTable();
        printWriter.print(date);
    }

    @Override
    public void nameFile(String fileName) {
        newCell();
        printWriter.print("There are in file '" + fileName + "' :");
    }

    @Override
    public void writeLines(int lines) {
        newLine();
        printWriter.print(lines + " lines;");
    }

    @Override
    public void writeWords(int words) {
        newCell();
        printWriter.print(words + " words;");
    }

    @Override
    public void writeLetters(Map map) {
        newCell();
        printWriter.print(map);
        printWriter.print(endHtmlCode);
        closeTable();
    }
}
