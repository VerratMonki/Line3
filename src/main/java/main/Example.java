package main;

import org.linereader.impl.ErrorAttention;
import org.linereader.impl.ThreadReader;
import org.linereader.interfaces.File;
import org.linereader.interfaces.OnError;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ControllerAdvice
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/home")
    String home() {
        return "Hello World - v3!";
    }

    /*@RequestMapping("/filestatistic")
    String statistic()
     {
        long start = System.currentTimeMillis();
        Date date = new Date();
        OnError onError = new ErrorAttention();
        //String fileName = "C:/file.txt";
        String fileName = "C:/test.fb2";
        ThreadReader reader = new ThreadReader(onError, fileName,7);
        reader.run();
        String letters = "{<tr>";
        int dual = 0;
        for (Map.Entry<Character, AtomicInteger> output : reader.getSumLetters().entrySet())
        {
            letters = letters +"<td>'" + output.getKey() + "'</td><td>" + output.getValue() + "</td>";
            dual++;
            if(dual%2==0) letters += "</tr><tr>";
            else letters += "<td></td>";
        }
        letters += "}";
        long end = System.currentTimeMillis();
        return "<html><body><table><tr><td>"+date.toString()+"</td><td>"+(end-start)+"</td><td>"+fileName+"</td></tr><tr><td>"+reader.getSumLines()+" lines</td><td>"+reader.getSumWords()+" words</td></tr></table><table><tr><td>Letters : "+letters+"</td></tr></table></body></html>";
    }*/

    @RequestMapping(value = "/filestatistic",method = RequestMethod.GET)
    String getFile(Model model)
    {
        model.addAttribute("getFile", new GetFile());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("startPage");
        return "startPage";
    }

    @RequestMapping(value = "filestatistic", method = RequestMethod.POST)
    String sendStatistic(@ModelAttribute GetFile getFile, Model model)
    {
        String fileName = getFile.getFileName();

        long start = System.currentTimeMillis();
        OnError onError = new ErrorAttention();
        ThreadReader reader = new ThreadReader(onError, fileName,7);
        reader.run();
        String letters = "{";
        for (Map.Entry<Character, AtomicInteger> output : reader.getSumLetters().entrySet())
        {
            letters = letters +"'" + output.getKey() + "' -> " + output.getValue() + ", ";
        }
        letters += "}";
        long end = System.currentTimeMillis();

        CreateStatistic createStatistic = new CreateStatistic(fileName, (end-start), reader.getSumLines(), reader.getSumWords(), letters);
        model.addAttribute("createStatistic", createStatistic);
        return "statisticPage";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

}
