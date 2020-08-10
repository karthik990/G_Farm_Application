package org.jdom2.input.sax;

import org.jdom2.JDOMException;
import org.xml.sax.XMLReader;

public interface XMLReaderJDOMFactory {
    XMLReader createXMLReader() throws JDOMException;

    boolean isValidating();
}
