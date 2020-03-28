package org.linereader.impl;

import org.linereader.interfaces.OnError;

public class ErrorAttention implements OnError {
    @Override
    public void onError(Exception ex, String className, String numberBlock) {
        System.err.println(className + ":" + numberBlock + " - " + ex );
    }
}
