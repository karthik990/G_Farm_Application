package org.jdom2.input.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public abstract class AbstractReaderSchemaFactory implements XMLReaderJDOMFactory {
    private final SAXParserFactory saxfac;

    public boolean isValidating() {
        return true;
    }

    public AbstractReaderSchemaFactory(SAXParserFactory sAXParserFactory, Schema schema) {
        if (schema != null) {
            this.saxfac = sAXParserFactory;
            SAXParserFactory sAXParserFactory2 = this.saxfac;
            if (sAXParserFactory2 != null) {
                sAXParserFactory2.setNamespaceAware(true);
                this.saxfac.setValidating(false);
                this.saxfac.setSchema(schema);
                return;
            }
            return;
        }
        throw new NullPointerException("Cannot create a SchemaXMLReaderFactory with a null schema");
    }

    public XMLReader createXMLReader() throws JDOMException {
        String str = "Could not create a new Schema-Validating XMLReader.";
        SAXParserFactory sAXParserFactory = this.saxfac;
        if (sAXParserFactory != null) {
            try {
                return sAXParserFactory.newSAXParser().getXMLReader();
            } catch (SAXException e) {
                throw new JDOMException(str, e);
            } catch (ParserConfigurationException e2) {
                throw new JDOMException(str, e2);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("It was not possible to configure a suitable XMLReader to support ");
            sb.append(this);
            throw new JDOMException(sb.toString());
        }
    }
}
