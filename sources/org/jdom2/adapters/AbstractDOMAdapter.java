package org.jdom2.adapters;

import java.lang.reflect.InvocationTargetException;
import org.jdom2.DocType;
import org.jdom2.JDOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public abstract class AbstractDOMAdapter implements DOMAdapter {
    public Document createDocument(DocType docType) throws JDOMException {
        if (docType == null) {
            return createDocument();
        }
        DOMImplementation implementation = createDocument().getImplementation();
        DocumentType createDocumentType = implementation.createDocumentType(docType.getElementName(), docType.getPublicID(), docType.getSystemID());
        setInternalSubset(createDocumentType, docType.getInternalSubset());
        Document createDocument = implementation.createDocument("http://temporary", docType.getElementName(), createDocumentType);
        Element documentElement = createDocument.getDocumentElement();
        if (documentElement != null) {
            createDocument.removeChild(documentElement);
        }
        return createDocument;
    }

    /* access modifiers changed from: protected */
    public void setInternalSubset(DocumentType documentType, String str) {
        if (documentType != null && str != null) {
            try {
                documentType.getClass().getMethod("setInternalSubset", new Class[]{String.class}).invoke(documentType, new Object[]{str});
            } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
            }
        }
    }
}
