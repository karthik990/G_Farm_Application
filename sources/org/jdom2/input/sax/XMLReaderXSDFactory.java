package org.jdom2.input.sax;

import java.io.File;
import java.net.URL;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.validation.SchemaFactory;
import org.jdom2.JDOMException;

public class XMLReaderXSDFactory extends AbstractReaderXSDFactory {
    private static final SchemaFactoryProvider xsdschemas = new SchemaFactoryProvider() {
        public SchemaFactory getSchemaFactory() {
            return SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        }
    };

    public XMLReaderXSDFactory(String... strArr) throws JDOMException {
        super(SAXParserFactory.newInstance(), xsdschemas, strArr);
    }

    public XMLReaderXSDFactory(String str, ClassLoader classLoader, String... strArr) throws JDOMException {
        super(SAXParserFactory.newInstance(str, classLoader), xsdschemas, strArr);
    }

    public XMLReaderXSDFactory(URL... urlArr) throws JDOMException {
        super(SAXParserFactory.newInstance(), xsdschemas, urlArr);
    }

    public XMLReaderXSDFactory(String str, ClassLoader classLoader, URL... urlArr) throws JDOMException {
        super(SAXParserFactory.newInstance(str, classLoader), xsdschemas, urlArr);
    }

    public XMLReaderXSDFactory(File... fileArr) throws JDOMException {
        super(SAXParserFactory.newInstance(), xsdschemas, fileArr);
    }

    public XMLReaderXSDFactory(String str, ClassLoader classLoader, File... fileArr) throws JDOMException {
        super(SAXParserFactory.newInstance(str, classLoader), xsdschemas, fileArr);
    }

    public XMLReaderXSDFactory(Source... sourceArr) throws JDOMException {
        super(SAXParserFactory.newInstance(), xsdschemas, sourceArr);
    }

    public XMLReaderXSDFactory(String str, ClassLoader classLoader, Source... sourceArr) throws JDOMException {
        super(SAXParserFactory.newInstance(str, classLoader), xsdschemas, sourceArr);
    }
}
