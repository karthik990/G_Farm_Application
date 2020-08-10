package com.rometools.rome.p052io;

import org.jdom2.JDOMException;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaders;
import org.xml.sax.XMLReader;

/* renamed from: com.rometools.rome.io.SAXBuilder */
public class SAXBuilder extends org.jdom2.input.SAXBuilder {
    public SAXBuilder(XMLReaderJDOMFactory xMLReaderJDOMFactory) {
        super(xMLReaderJDOMFactory);
    }

    @Deprecated
    public SAXBuilder(boolean z) {
        super((XMLReaderJDOMFactory) z ? XMLReaders.DTDVALIDATING : XMLReaders.NONVALIDATING);
    }

    public XMLReader createParser() throws JDOMException {
        return super.createParser();
    }
}
