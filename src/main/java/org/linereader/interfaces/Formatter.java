package org.linereader.interfaces;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;

public interface Formatter {
	void createNewFile(OnError onError) throws ParserConfigurationException;
	void setName(String fileName);
	void setLines(int lines);
	void setWords(int words);
	void setLetters(Map map);
	void setDate(String date);
	void setTime(long ms);
	void changeName(int number);
}
