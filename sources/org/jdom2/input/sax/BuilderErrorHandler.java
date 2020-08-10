package org.jdom2.input.sax;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class BuilderErrorHandler implements ErrorHandler {
    public void warning(SAXParseException sAXParseException) throws SAXException {
    }

    public void error(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }
}
