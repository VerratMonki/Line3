package org.linereader.impl;

import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.OnError;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class XML implements Formatter {
    private String startNameFile = "statistic";
    private String endNameFile= ".xml";
    private String fileName;
    private String date;
    private int lines;
    private int words;
    private long ms;
    private Map<Character, Integer> letters;

    //Block #1
    @Override
    public void createNewFile(OnError onError) throws ParserConfigurationException {
        File file1 = new File(nameNewFile());
        try(FileWriter fileWriter = new FileWriter(file1)) {
            fileWriter.write(date);
            fileWriter.write("\t");
            fileWriter.write("There are in file '");
            fileWriter.write(fileName);
            fileWriter.write("' : \t");
            fileWriter.write("" + lines);
            fileWriter.write(" lines, \t");
            fileWriter.write("" + words);
            fileWriter.write(" words, \tletters : ");
            for (Map.Entry<Character, Integer> output : letters.entrySet())
            {
                fileWriter.write("{" + output.getKey() + "} -> " + output.getValue() + ", ");
            }
            fileWriter.write("\tThe programme worked in " + ms + " ms.");
//        {
//            System.out.println("{" + output.getKey() + "} -> " + output.getValue());
//        }
        } catch (IOException e) {
            onError.onError(e, "XML", "Block #1");
        }



        /*DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("root");
        Element font = document.createElement("font");
        Text text = document.createTextNode("TimesNewRoman");
        Text date = document.createTextNode(date);
        Element fileName = document.createElement("fileName");
        Element lines = document.createElement("lines");
        Element words = document.createElement("words");
        Element letters = document.createElement("letters");
        document.appendChild(root);
        root.appendChild(font);
        font.appendChild(text);
        font.setAttribute("size", "14");
        Element table = document.createElement("table");
        Element tr = document.createElement("tr");
        Element td = document.createElement("td");
        document.appendChild(table);
        table.setAttribute("border", "1");
        table.appendChild(tr);
        tr.setAttribute("bgcolor", "#CCCCCC");
        tr.appendChild(td);
        td.setAttribute("align", "center");
        Text textDate = document.createTextNode("Date");
        Text textFileName = document.createTextNode("Name of file");
        Text textLines = document.createTextNode("Number of lines");
        Text textWords = document.createTextNode("Number of words");
        Text textLetters = document.createTextNode("Letters in file");
        td.appendChild(textDate);
        td.setAttribute("align", "center");
        tr.appendChild(td);
        td.appendChild(textFileName);
        td.setAttribute("align", "center");
        tr.appendChild(td);
        td.appendChild(textLines);
        td.setAttribute("align", "center");
        tr.appendChild(td);
        td.appendChild(textWords);
        td.setAttribute("align", "center");
        tr.appendChild(td);
        td.appendChild(textLetters);
        td.setAttribute("align", "center");
        table.appendChild(tr);
        tr.appendChild(td);
        tr.setAttribute("bgcolor", "#CCCCCC");
        td.appendChild(date);*/
    }

    String nameNewFile() {
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
//    public void writeDate(String date) {
//
//    }
//
//    @Override
//    public void nameFile(String fileName) {
//
//    }
//
//    @Override
//    public void writeLines(int lines) {
//
//    }
//
//    @Override
//    public void writeWords(int words) {
//
//    }
//
//    @Override
//    public void writeLetters(Map map) {
//
//    }

}
