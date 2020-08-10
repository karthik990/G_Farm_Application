package com.rometools.rome.p052io.impl;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS091NetscapeGenerator */
public class RSS091NetscapeGenerator extends RSS091UserlandGenerator {
    /* access modifiers changed from: protected */
    public String getTextInputLabel() {
        return "textinput";
    }

    /* access modifiers changed from: protected */
    public boolean isHourFormat24() {
        return false;
    }

    public RSS091NetscapeGenerator() {
        this("rss_0.91N", "0.91");
    }

    protected RSS091NetscapeGenerator(String str, String str2) {
        super(str, str2);
    }

    /* access modifiers changed from: protected */
    public Document createDocument(Element element) {
        Document document = new Document(element);
        document.setDocType(new DocType("rss", "-//Netscape Communications//DTD RSS 0.91//EN", "http://my.netscape.com/publish/formats/rss-0.91.dtd"));
        return document;
    }
}
