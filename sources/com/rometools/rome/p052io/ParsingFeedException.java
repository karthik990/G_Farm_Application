package com.rometools.rome.p052io;

import org.jdom2.input.JDOMParseException;

/* renamed from: com.rometools.rome.io.ParsingFeedException */
public class ParsingFeedException extends FeedException {
    private static final long serialVersionUID = 1;

    public ParsingFeedException(String str) {
        super(str);
    }

    public ParsingFeedException(String str, Throwable th) {
        super(str, th);
    }

    public int getLineNumber() {
        if (getCause() instanceof JDOMParseException) {
            return ((JDOMParseException) getCause()).getLineNumber();
        }
        return -1;
    }

    public int getColumnNumber() {
        if (getCause() instanceof JDOMParseException) {
            return ((JDOMParseException) getCause()).getColumnNumber();
        }
        return -1;
    }
}
