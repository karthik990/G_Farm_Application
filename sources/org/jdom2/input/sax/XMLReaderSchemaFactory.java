package org.jdom2.input.sax;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;

public class XMLReaderSchemaFactory extends AbstractReaderSchemaFactory {
    public XMLReaderSchemaFactory(Schema schema) {
        super(SAXParserFactory.newInstance(), schema);
    }

    public XMLReaderSchemaFactory(String str, ClassLoader classLoader, Schema schema) {
        super(SAXParserFactory.newInstance(str, classLoader), schema);
    }
}
