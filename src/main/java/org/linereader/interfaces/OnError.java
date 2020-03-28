package org.linereader.interfaces;

public interface OnError {
    void onError (Exception ex, String className, String numberBlock);
}
