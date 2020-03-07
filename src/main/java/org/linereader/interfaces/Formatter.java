package org.linereader.interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public interface Formatter {
	void createNewFile(OnError onError, PrintWriter printWriter);
	void writeDate(String date) throws IOException;
	void nameFile(String fileName) throws IOException;
	void writeLines(int lines) throws IOException;
	void writeWords(int words) throws IOException;
	void writeLetters(Map map) throws IOException;
}
