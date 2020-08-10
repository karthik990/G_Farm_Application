package org.jdom2.input.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public enum XMLReaders implements XMLReaderJDOMFactory {
    NONVALIDATING(0),
    DTDVALIDATING(1),
    XSDVALIDATING(2);
    
    private final int singletonID;

    private enum DTDSingleton implements FactorySupplier {
        private static final /* synthetic */ DTDSingleton[] $VALUES = null;
        public static final DTDSingleton INSTANCE = null;
        private final SAXParserFactory factory;

        public boolean validates() {
            return true;
        }

        public static DTDSingleton valueOf(String str) {
            return (DTDSingleton) Enum.valueOf(DTDSingleton.class, str);
        }

        public static DTDSingleton[] values() {
            return (DTDSingleton[]) $VALUES.clone();
        }

        static {
            INSTANCE = new DTDSingleton("INSTANCE", 0);
            $VALUES = new DTDSingleton[]{INSTANCE};
        }

        private DTDSingleton(String str, int i) {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            newInstance.setValidating(true);
            this.factory = newInstance;
        }

        public SAXParserFactory supply() throws Exception {
            return this.factory;
        }
    }

    private interface FactorySupplier {
        SAXParserFactory supply() throws Exception;

        boolean validates();
    }

    private enum NONSingleton implements FactorySupplier {
        private static final /* synthetic */ NONSingleton[] $VALUES = null;
        public static final NONSingleton INSTANCE = null;
        private final SAXParserFactory factory;

        public boolean validates() {
            return false;
        }

        public static NONSingleton valueOf(String str) {
            return (NONSingleton) Enum.valueOf(NONSingleton.class, str);
        }

        public static NONSingleton[] values() {
            return (NONSingleton[]) $VALUES.clone();
        }

        static {
            INSTANCE = new NONSingleton("INSTANCE", 0);
            $VALUES = new NONSingleton[]{INSTANCE};
        }

        private NONSingleton(String str, int i) {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            newInstance.setValidating(false);
            this.factory = newInstance;
        }

        public SAXParserFactory supply() throws Exception {
            return this.factory;
        }
    }

    private enum XSDSingleton implements FactorySupplier {
        private static final /* synthetic */ XSDSingleton[] $VALUES = null;
        public static final XSDSingleton INSTANCE = null;
        private final SAXParserFactory factory;
        private final Exception failcause;

        public boolean validates() {
            return true;
        }

        public static XSDSingleton valueOf(String str) {
            return (XSDSingleton) Enum.valueOf(XSDSingleton.class, str);
        }

        public static XSDSingleton[] values() {
            return (XSDSingleton[]) $VALUES.clone();
        }

        static {
            INSTANCE = new XSDSingleton("INSTANCE", 0);
            $VALUES = new XSDSingleton[]{INSTANCE};
        }

        private XSDSingleton(String str, int i) {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            newInstance.setValidating(false);
            SAXParserFactory sAXParserFactory = null;
            try {
                newInstance.setSchema(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema());
                sAXParserFactory = newInstance;
                e = null;
            } catch (IllegalArgumentException | UnsupportedOperationException | SAXException e) {
                e = e;
            }
            this.factory = sAXParserFactory;
            this.failcause = e;
        }

        public SAXParserFactory supply() throws Exception {
            SAXParserFactory sAXParserFactory = this.factory;
            if (sAXParserFactory != null) {
                return sAXParserFactory;
            }
            throw this.failcause;
        }
    }

    private XMLReaders(int i) {
        this.singletonID = i;
    }

    private FactorySupplier getSupplier() {
        int i = this.singletonID;
        if (i == 0) {
            return NONSingleton.INSTANCE;
        }
        if (i == 1) {
            return DTDSingleton.INSTANCE;
        }
        if (i == 2) {
            return XSDSingleton.INSTANCE;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown singletonID: ");
        sb.append(this.singletonID);
        throw new IllegalStateException(sb.toString());
    }

    public XMLReader createXMLReader() throws JDOMException {
        String str = "Unable to create a new XMLReader instance";
        try {
            return getSupplier().supply().newSAXParser().getXMLReader();
        } catch (SAXException e) {
            throw new JDOMException(str, e);
        } catch (ParserConfigurationException e2) {
            throw new JDOMException(str, e2);
        } catch (Exception e3) {
            StringBuilder sb = new StringBuilder();
            sb.append("It was not possible to configure a suitable XMLReader to support ");
            sb.append(this);
            throw new JDOMException(sb.toString(), e3);
        }
    }

    public boolean isValidating() {
        return getSupplier().validates();
    }
}
