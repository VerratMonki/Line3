package interfaces.impl;

import interfaces.LineConsumer;
import interfaces.OnError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultReaderTest {
    OnError error;
    Counter counter = new Counter();
    CounterLetters counterLetters = new CounterLetters(counter);
    CounterLines counterLines = new CounterLines();
    CounterWords counterWords = new CounterWords();
    LineConsumer reader;
    
    @BeforeEach
    void setUp() {
        
        //reader = new Counters(counterLetters, counterLines, counterWords);
        
    }
    
    @AfterEach
    void tearDown() {
    }
    
    
    @Test
    void callCounter() throws IOException {
        BufferedReader bufferedReader = mock(BufferedReader.class);
        LineConsumer lineConsumer = spy(new Counters(counterLetters,counterLines,counterWords));
        DefaultReader defaultReader = spy(new DefaultReader(lineConsumer,null));
        defaultReader.readingLines(mock(LineConsumer.class), bufferedReader);
        when(bufferedReader.readLine()).thenReturn("123").thenReturn(null);
        
        defaultReader.readingLines(lineConsumer, bufferedReader);
        
        verify(lineConsumer).nextLine("123");
    }
    
    @Test
    void callReadingLinesTest() throws IOException {
        BufferedReader bufferedReader = mock(BufferedReader.class);
        LineConsumer lineConsumer = spy(new Counters(counterLetters,counterLines,counterWords));
        DefaultReader defaultReader = spy(new DefaultReader(lineConsumer,null));
        
        defaultReader.readFile(bufferedReader, lineConsumer);
        
        verify(defaultReader).readingLines(lineConsumer,bufferedReader);
    }
    
    @Test
    void callAfterReadFile() throws IOException {
        BufferedReader bufferedReader = mock(BufferedReader.class);
        LineConsumer lineConsumer = spy(new Counters(counterLetters,counterLines,counterWords));
        DefaultReader defaultReader = spy(new DefaultReader(lineConsumer,null));
    
        defaultReader.readFile(bufferedReader,lineConsumer);
        
        verify(lineConsumer).afterReadFile(any());
    }
    
    @Test
    void tryWithResourcesTest() throws Exception
    {
        DefaultReader defaultReader = spy(new DefaultReader(null,null));
//        LineConsumer lineConsumer = spy(new Counters(counterLetters,counterLines,counterWords));
        FileInputStream fileInputStream = mock(FileInputStream.class);
        doReturn(fileInputStream).when(defaultReader).createInputStreamByFilename();
        doNothing().when(defaultReader).readFile(any(BufferedReader.class), any(LineConsumer.class));
        
        defaultReader.tryWithResources();
        
        verify(defaultReader).readFile(any(BufferedReader.class), any(LineConsumer.class));
    }
    
    @Test
    void tryWithResourcesException() throws Exception
    {
        OnError error = mock(OnError.class);
        LineConsumer lineConsumer = spy(new Counters(counterLetters,counterLines,counterWords));
        DefaultReader defaultReader = spy(new DefaultReader(lineConsumer, error));
        FileInputStream fileInputStream = mock(FileInputStream.class);
        doReturn(fileInputStream).when(defaultReader).createInputStreamByFilename();
        IOException ioException = new IOException();
        doThrow(ioException).when(defaultReader).readFile(any(BufferedReader.class), eq(lineConsumer));
        
        defaultReader.tryWithResources();
        
        verify(error).onError(ioException);
    }
    
}
    
    
    
//    @Test
//    void setReader() throws IOException, IllegalArgumentException {
//        try {
//            DefaultReader defaultReader = spy(new DefaultReader());
//            BufferedReader bufferedReader = mock(BufferedReader.class);
//            defaultReader.setReader(bufferedReader, reader);
//            File file = mock(File.class);
//            defaultReader.setFile(file);
//
//
//
//
//
//
//
//            verify(defaultReader).getResult();
//        }catch (Exception e){
//            error.onError(e);
//        }