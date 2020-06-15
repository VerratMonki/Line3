package main;

import org.linereader.impl.ErrorAttention;
import org.linereader.impl.ThreadReader;
import org.linereader.interfaces.OnError;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@ControllerAdvice
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/home")
    String home() {
        return "Hello World - v3!";
    }

    @RequestMapping("/filestatistic")
    String statistic()
    {
        long start = System.currentTimeMillis();
        Date date = new Date();
        OnError onError = new ErrorAttention();
        //String fileName = "C:/file.txt";
        String fileName = "C:/test.fb2";
        ThreadReader reader = new ThreadReader(onError, fileName,7);
        reader.run();
        String letters = "{";
        for (Map.Entry<Character, AtomicInteger> output : reader.getSumLetters().entrySet())
        {
            letters = letters +"'" + output.getKey() + "' -> " + output.getValue() + ", ";
        }
        letters += "}";
        long end = System.currentTimeMillis();
        return "<html><body><table><tr><td>"+date.toString()+"</td><td>"+(end-start)+"</td><td>"+fileName+"</td></tr><tr><td>"+reader.getSumLines()+" letters</td><td>"+reader.getSumWords()+" words</td></tr><tr><td>Letters : "+letters+"</body></html>";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

}
