package org.jdom2.adapters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.JDOMException;
import org.w3c.dom.Document;

public class JAXPDOMAdapter extends AbstractDOMAdapter {
    private static final ThreadLocal<DocumentBuilder> localbuilder = new ThreadLocal<>();

    public Document createDocument() throws JDOMException {
        DocumentBuilder documentBuilder = (DocumentBuilder) localbuilder.get();
        if (documentBuilder == null) {
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                localbuilder.set(documentBuilder);
            } catch (ParserConfigurationException e) {
                throw new JDOMException("Unable to obtain a DOM parser. See cause:", e);
            }
        }
        return documentBuilder.newDocument();
    }
}
