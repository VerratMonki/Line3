package main;

import org.linereader.impl.*;
import org.linereader.interfaces.Formatter;
import org.linereader.interfaces.GetData;
import org.linereader.interfaces.LineConsumer;
import org.linereader.interfaces.OnError;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Counter counter = new Counter();
		CounterLines counterLines = new CounterLines();
		CounterWords counterWords = new CounterWords();
		CounterLetters counterLetters = new CounterLetters(counter);
		LineConsumer lineConsumer = new Counters(counterLetters,counterLines,counterWords);
		Counters counters = new Counters(counterLetters,counterLines,counterWords);
		GetData getData = new GetInformation(counters);
			OnError onError = ex -> {
				System.err.println(ex);
			};
		//Formatter formatter = new HTML();
		//Formatter formatter = new TXT(onError);
		Formatter formatter = new XML();
			DefaultReader defaultReader = new DefaultReader("test.txt",
					counters,
					onError, formatter, getData);
			try {
				defaultReader.tryWithResources(onError);
			}catch (Exception ex)
			{
				onError.onError(ex);
			}

	}
}

enum OutputType
{
XML ("XML"), HTML ("HTML"), TXT ("TXT");
String type;
	OutputType(String type)
	{
		this.type=type;
	}
	String getType()
	{
		return type;
	}
}