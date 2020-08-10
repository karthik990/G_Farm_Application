package com.rometools.rome.p052io.impl;

import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS091NetscapeParser */
public class RSS091NetscapeParser extends RSS091UserlandParser {
    static final String ELEMENT_NAME = "rss";
    static final String PUBLIC_ID = "-//Netscape Communications//DTD RSS 0.91//EN";
    static final String SYSTEM_ID = "http://my.netscape.com/publish/formats/rss-0.91.dtd";

    /* access modifiers changed from: protected */
    public String getTextInputLabel() {
        return "textinput";
    }

    /* access modifiers changed from: protected */
    public boolean isHourFormat24(Element element) {
        return false;
    }

    public RSS091NetscapeParser() {
        this("rss_0.91N");
    }

    protected RSS091NetscapeParser(String str) {
        super(str);
    }

    public boolean isMyType(Document document) {
        Element rootElement = document.getRootElement();
        String name = rootElement.getName();
        Attribute attribute = rootElement.getAttribute(ClientCookie.VERSION_ATTR);
        DocType docType = document.getDocType();
        String str = ELEMENT_NAME;
        if (name.equals(str) && attribute != null && attribute.getValue().equals(getRSSVersion()) && docType != null && str.equals(docType.getElementName())) {
            if (PUBLIC_ID.equals(docType.getPublicID())) {
                if (SYSTEM_ID.equals(docType.getSystemID())) {
                    return true;
                }
            }
        }
        return false;
    }
}
