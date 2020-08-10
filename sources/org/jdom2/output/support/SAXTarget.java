package org.jdom2.output.support;

import org.jdom2.output.JDOMLocator;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public final class SAXTarget {
    private final ContentHandler contentHandler;
    private final DeclHandler declHandler;
    private final boolean declareNamespaces;
    private final DTDHandler dtdHandler;
    private final EntityResolver entityResolver;
    private final ErrorHandler errorHandler;
    private final LexicalHandler lexicalHandler;
    private final SAXLocator locator;
    private final boolean reportDtdEvents;

    public static final class SAXLocator implements JDOMLocator {
        private Object node = null;
        private final String publicid;
        private final String systemid;

        public int getColumnNumber() {
            return -1;
        }

        public int getLineNumber() {
            return -1;
        }

        public SAXLocator(String str, String str2) {
            this.publicid = str;
            this.systemid = str2;
        }

        public String getPublicId() {
            return this.publicid;
        }

        public String getSystemId() {
            return this.systemid;
        }

        public Object getNode() {
            return this.node;
        }

        public void setNode(Object obj) {
            this.node = obj;
        }
    }

    public SAXTarget(ContentHandler contentHandler2, ErrorHandler errorHandler2, DTDHandler dTDHandler, EntityResolver entityResolver2, LexicalHandler lexicalHandler2, DeclHandler declHandler2, boolean z, boolean z2, String str, String str2) {
        this.contentHandler = contentHandler2;
        this.errorHandler = errorHandler2;
        this.dtdHandler = dTDHandler;
        this.entityResolver = entityResolver2;
        this.lexicalHandler = lexicalHandler2;
        this.declHandler = declHandler2;
        this.declareNamespaces = z;
        this.reportDtdEvents = z2;
        this.locator = new SAXLocator(str, str2);
    }

    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }

    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    public LexicalHandler getLexicalHandler() {
        return this.lexicalHandler;
    }

    public DeclHandler getDeclHandler() {
        return this.declHandler;
    }

    public boolean isDeclareNamespaces() {
        return this.declareNamespaces;
    }

    public boolean isReportDTDEvents() {
        return this.reportDtdEvents;
    }

    public SAXLocator getLocator() {
        return this.locator;
    }
}
