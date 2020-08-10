package org.jdom2.input.sax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

public interface SAXEngine {
    Document build(File file) throws JDOMException, IOException;

    Document build(InputStream inputStream) throws JDOMException, IOException;

    Document build(InputStream inputStream, String str) throws JDOMException, IOException;

    Document build(Reader reader) throws JDOMException, IOException;

    Document build(Reader reader, String str) throws JDOMException, IOException;

    Document build(String str) throws JDOMException, IOException;

    Document build(URL url) throws JDOMException, IOException;

    Document build(InputSource inputSource) throws JDOMException, IOException;

    DTDHandler getDTDHandler();

    EntityResolver getEntityResolver();

    ErrorHandler getErrorHandler();

    boolean getExpandEntities();

    boolean getIgnoringBoundaryWhitespace();

    boolean getIgnoringElementContentWhitespace();

    JDOMFactory getJDOMFactory();

    boolean isValidating();
}
