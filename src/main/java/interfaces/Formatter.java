package interfaces;

import java.io.IOException;
import java.util.Map;

public interface Formatter {
	void createNewFile(OnError onError);
	void writeDate(String date) throws IOException;
	void nameFile(String fileName) throws IOException;
	void writeLines(int lines) throws IOException;
	void writeWords(int words) throws IOException;
	void writeLetters(Map map) throws IOException;
}
