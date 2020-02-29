package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import java.io.PrintWriter;
import java.util.Map;

public class HTML implements Formatter {
    private final String nameFile = "statistic";
    private String nameFile2 = nameFile;
    private String typeFile = ".html";
    private String startHtmlCode = "<html><body>";
    private String endHtmlCode = "</body></html>";
    private int indexFile = 0;
    PrintWriter printWriter;

    HTML(OnError onError)
    {
        if(indexFile>0) nameFile2 += Integer.toString(indexFile);
        nameFile2 += typeFile;
        try(PrintWriter printWriter = new PrintWriter(nameFile2, "UTF-8"))
        {
            nameFile2 = nameFile;
            printWriter.println(startHtmlCode);
            setPrintWriter(printWriter);
            indexFile++;
        }catch (Exception ex)
        {
            onError.onError(ex);
        }
    }

    void setPrintWriter(PrintWriter printWriter)
    {
        this.printWriter = printWriter;
    }

    @Override
    public void createNewFile(OnError onError) {
        new HTML(onError);
    }

    @Override
    public void writeDate(String date){
        printWriter.print(date);
        writeTab();
    }

    @Override
    public void nameFile(String fileName) {
        printWriter.print("There are in file '" + fileName + "' :");
        writeTab();
    }

    @Override
    public void writeLines(int lines) {
        printWriter.print(lines + " lines;");
        writeTab();
    }

    @Override
    public void writeWords(int words) {
        printWriter.print(words + " words;");
        writeTab();
    }

    @Override
    public void writeLetters(Map map) {
        printWriter.print(map);
        printWriter.print(endHtmlCode);
    }

    private void writeTab(){
        String tab = "\t";
        printWriter.print(tab);
    }
}
