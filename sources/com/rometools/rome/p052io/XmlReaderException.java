package com.rometools.rome.p052io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.rometools.rome.io.XmlReaderException */
public class XmlReaderException extends IOException {
    private static final long serialVersionUID = 1;
    private final String bomEncoding;
    private final String contentTypeEncoding;
    private final String contentTypeMime;

    /* renamed from: is */
    private final InputStream f2361is;
    private final String xmlEncoding;
    private final String xmlGuessEncoding;

    public XmlReaderException(String str, String str2, String str3, String str4, InputStream inputStream) {
        this(str, null, null, str2, str3, str4, inputStream);
    }

    public XmlReaderException(String str, String str2, String str3, String str4, String str5, String str6, InputStream inputStream) {
        super(str);
        this.contentTypeMime = str2;
        this.contentTypeEncoding = str3;
        this.bomEncoding = str4;
        this.xmlGuessEncoding = str5;
        this.xmlEncoding = str6;
        this.f2361is = inputStream;
    }

    public String getBomEncoding() {
        return this.bomEncoding;
    }

    public String getXmlGuessEncoding() {
        return this.xmlGuessEncoding;
    }

    public String getXmlEncoding() {
        return this.xmlEncoding;
    }

    public String getContentTypeMime() {
        return this.contentTypeMime;
    }

    public String getContentTypeEncoding() {
        return this.contentTypeEncoding;
    }

    public InputStream getInputStream() {
        return this.f2361is;
    }
}
