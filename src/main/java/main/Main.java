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
		/*CountDownLatch countDownLatch = new CountDownLatch(2);
			DemoThread firstStream = new DemoThread(countDownLatch);
			DemoThread secondStream = new DemoThread(countDownLatch);
			Thread firstThread = new Thread(firstStream);
			Thread secondThread = new Thread(secondStream);
			firstThread.start();
			secondThread.start();*/


		Formatter formatter = new HTML();
		//Formatter formatter = new TXT(onError);
		//Formatter formatter = new XML();
		ThreadReader threadReader = new ThreadReader(onError, "test.txt", formatter);
		threadReader.run();
			/*DefaultReader defaultReader = new DefaultReader("test.txt",
					counters,
					onError, formatter, getData);
			try {
				defaultReader.tryWithResources(onError);
			}catch (Exception ex)
			{
				onError.onError(ex);
			}*/

	}
}

/*class DemoThread implements Runnable
{
CountDownLatch countDownLatch;
	DemoThread(CountDownLatch countDownLatch)
	{
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			countDownLatch.countDown();
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<100000000;i++)
		{
			System.out.println(i + ", ");
		}

	}
}*/

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