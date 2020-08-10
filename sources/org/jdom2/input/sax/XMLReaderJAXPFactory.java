package org.jdom2.input.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLReaderJAXPFactory implements XMLReaderJDOMFactory {
    private final SAXParserFactory instance;
    private final boolean validating;

    public XMLReaderJAXPFactory(String str, ClassLoader classLoader, boolean z) {
        this.instance = SAXParserFactory.newInstance(str, classLoader);
        this.instance.setNamespaceAware(true);
        this.instance.setValidating(z);
        this.validating = z;
    }

    public XMLReader createXMLReader() throws JDOMException {
        String str = "Unable to create a new XMLReader instance";
        try {
            return this.instance.newSAXParser().getXMLReader();
        } catch (SAXException e) {
            throw new JDOMException(str, e);
        } catch (ParserConfigurationException e2) {
            throw new JDOMException(str, e2);
        }
    }

    public boolean isValidating() {
        return this.validating;
    }
}
