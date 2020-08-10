package org.jdom2.adapters;

import org.jdom2.DocType;
import org.jdom2.JDOMException;
import org.w3c.dom.Document;

public interface DOMAdapter {
    Document createDocument() throws JDOMException;

    Document createDocument(DocType docType) throws JDOMException;
}
