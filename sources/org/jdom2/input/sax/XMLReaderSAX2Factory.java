package org.jdom2.input.sax;

import org.jdom2.JDOMConstants;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLReaderSAX2Factory implements XMLReaderJDOMFactory {
    private final String saxdriver;
    private final boolean validate;

    public XMLReaderSAX2Factory(boolean z) {
        this(z, null);
    }

    public XMLReaderSAX2Factory(boolean z, String str) {
        this.validate = z;
        this.saxdriver = str;
    }

    public XMLReader createXMLReader() throws JDOMException {
        try {
            XMLReader createXMLReader = this.saxdriver == null ? XMLReaderFactory.createXMLReader() : XMLReaderFactory.createXMLReader(this.saxdriver);
            createXMLReader.setFeature(JDOMConstants.SAX_FEATURE_VALIDATION, this.validate);
            createXMLReader.setFeature(JDOMConstants.SAX_FEATURE_NAMESPACES, true);
            createXMLReader.setFeature(JDOMConstants.SAX_FEATURE_NAMESPACE_PREFIXES, true);
            return createXMLReader;
        } catch (SAXException e) {
            throw new JDOMException("Unable to create SAX2 XMLReader.", e);
        }
    }

    public String getDriverClassName() {
        return this.saxdriver;
    }

    public boolean isValidating() {
        return this.validate;
    }
}
