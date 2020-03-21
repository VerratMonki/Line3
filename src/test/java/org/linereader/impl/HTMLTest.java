package org.linereader.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linereader.interfaces.OnError;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.mockito.Mockito.*;

class HTMLTest {

    @Test
    void writeDate() throws FileNotFoundException, UnsupportedEncodingException {
        HTML html = spy(new HTML());
        OnError onError = mock(OnError.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        Date date = mock(Date.class);
        doReturn(printWriter).when(html).setPrintWriter();
        doNothing().when(html).writeDate(printWriter);

        html.setDate(date.toString());
        html.createNewFile(onError);

        //verify(printWriter).print(anyString());
        verify(printWriter).print(date.toString());
    }

    @Test
    void setName() throws FileNotFoundException, UnsupportedEncodingException {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            HTML html = new HTML();
            html.setName(null);
        });
    }
}