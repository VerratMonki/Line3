package main;

import org.linereader.impl.ErrorAttention;
import org.linereader.impl.ThreadReader;
import org.linereader.interfaces.OnError;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ControllerAdvice
@EnableAutoConfiguration
public class UploadingFileController {

    @RequestMapping("/home")
    String home() {
        return "Hello World - v3!";
    }

    /*@RequestMapping("/filestatistic")
    String statistic() throws FileNotFoundException, UnsupportedEncodingException {
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

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100_000_000);
        return multipartResolver;
    }

    @RequestMapping(value = "/filestatistic",method = RequestMethod.GET)
    String getFile(Model model)
    {
        model.addAttribute("getFile", new GetFile());
        return "startPage";
    }

    @RequestMapping(value = "filestatistic", method = RequestMethod.POST)
    String sendStatistic(@ModelAttribute GetFile getFile, @RequestParam("file") MultipartFile file, Model model)
    {
        String fileName = file.getName();

        long start = System.currentTimeMillis();
        OnError onError = new ErrorAttention();
        ThreadReader reader = new ThreadReader(onError, file,7);
        reader.run();
        int number_letters=0;
        List<Map> SumLetters = new ArrayList<>();
        Map<Character,AtomicInteger> letters = new HashMap<>();
        for (Map.Entry<Character, AtomicInteger> output : reader.getSumLetters().entrySet())
        {
            letters.put(output.getKey(), new AtomicInteger(output.getValue().get()));
            number_letters++;
            if(number_letters>=25)
            {
                number_letters=0;
                SumLetters.add(letters);
                letters = new HashMap<>();
            }
        }

        long end = System.currentTimeMillis();

        CreateStatistic createStatistic = new CreateStatistic(fileName, (end-start), reader.getSumLines(), reader.getSumWords());
        createStatistic.getMaps(SumLetters);
        model.addAttribute("createStatistic", createStatistic);
        return "statisticPage";
    }

}
