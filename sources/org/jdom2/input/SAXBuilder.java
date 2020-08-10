package org.jdom2.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.Document;
import org.jdom2.JDOMConstants;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.Verifier;
import org.jdom2.input.sax.BuilderErrorHandler;
import org.jdom2.input.sax.DefaultSAXHandlerFactory;
import org.jdom2.input.sax.SAXBuilderEngine;
import org.jdom2.input.sax.SAXEngine;
import org.jdom2.input.sax.SAXHandler;
import org.jdom2.input.sax.SAXHandlerFactory;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderSAX2Factory;
import org.jdom2.input.sax.XMLReaders;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class SAXBuilder implements SAXEngine {
    private static final JDOMFactory DEFAULTJDOMFAC = new DefaultJDOMFactory();
    private static final SAXHandlerFactory DEFAULTSAXHANDLERFAC = new DefaultSAXHandlerFactory();
    private SAXEngine engine;
    private boolean expand;
    private final HashMap<String, Boolean> features;
    private SAXHandlerFactory handlerfac;
    private boolean ignoringBoundaryWhite;
    private boolean ignoringWhite;
    private JDOMFactory jdomfac;
    private final HashMap<String, Object> properties;
    private XMLReaderJDOMFactory readerfac;
    private boolean reuseParser;
    private DTDHandler saxDTDHandler;
    private EntityResolver saxEntityResolver;
    private ErrorHandler saxErrorHandler;
    private XMLFilter saxXMLFilter;

    @Deprecated
    public void setFastReconfigure(boolean z) {
    }

    public SAXBuilder() {
        this(null, null, null);
    }

    @Deprecated
    public SAXBuilder(boolean z) {
        this(z ? XMLReaders.DTDVALIDATING : XMLReaders.NONVALIDATING, null, null);
    }

    @Deprecated
    public SAXBuilder(String str) {
        this(str, false);
    }

    @Deprecated
    public SAXBuilder(String str, boolean z) {
        this(new XMLReaderSAX2Factory(z, str), null, null);
    }

    public SAXBuilder(XMLReaderJDOMFactory xMLReaderJDOMFactory) {
        this(xMLReaderJDOMFactory, null, null);
    }

    public SAXBuilder(XMLReaderJDOMFactory xMLReaderJDOMFactory, SAXHandlerFactory sAXHandlerFactory, JDOMFactory jDOMFactory) {
        this.readerfac = null;
        this.handlerfac = null;
        this.jdomfac = null;
        this.features = new HashMap<>(5);
        this.properties = new HashMap<>(5);
        this.saxErrorHandler = null;
        this.saxEntityResolver = null;
        this.saxDTDHandler = null;
        this.saxXMLFilter = null;
        this.expand = true;
        this.ignoringWhite = false;
        this.ignoringBoundaryWhite = false;
        this.reuseParser = true;
        this.engine = null;
        if (xMLReaderJDOMFactory == null) {
            xMLReaderJDOMFactory = XMLReaders.NONVALIDATING;
        }
        this.readerfac = xMLReaderJDOMFactory;
        if (sAXHandlerFactory == null) {
            sAXHandlerFactory = DEFAULTSAXHANDLERFAC;
        }
        this.handlerfac = sAXHandlerFactory;
        if (jDOMFactory == null) {
            jDOMFactory = DEFAULTJDOMFAC;
        }
        this.jdomfac = jDOMFactory;
    }

    @Deprecated
    public String getDriverClass() {
        XMLReaderJDOMFactory xMLReaderJDOMFactory = this.readerfac;
        if (xMLReaderJDOMFactory instanceof XMLReaderSAX2Factory) {
            return ((XMLReaderSAX2Factory) xMLReaderJDOMFactory).getDriverClassName();
        }
        return null;
    }

    @Deprecated
    public JDOMFactory getFactory() {
        return getJDOMFactory();
    }

    public JDOMFactory getJDOMFactory() {
        return this.jdomfac;
    }

    @Deprecated
    public void setFactory(JDOMFactory jDOMFactory) {
        setJDOMFactory(jDOMFactory);
    }

    public void setJDOMFactory(JDOMFactory jDOMFactory) {
        this.jdomfac = jDOMFactory;
        this.engine = null;
    }

    public XMLReaderJDOMFactory getXMLReaderFactory() {
        return this.readerfac;
    }

    public void setXMLReaderFactory(XMLReaderJDOMFactory xMLReaderJDOMFactory) {
        if (xMLReaderJDOMFactory == null) {
            xMLReaderJDOMFactory = XMLReaders.NONVALIDATING;
        }
        this.readerfac = xMLReaderJDOMFactory;
        this.engine = null;
    }

    public SAXHandlerFactory getSAXHandlerFactory() {
        return this.handlerfac;
    }

    public void setSAXHandlerFactory(SAXHandlerFactory sAXHandlerFactory) {
        if (sAXHandlerFactory == null) {
            sAXHandlerFactory = DEFAULTSAXHANDLERFAC;
        }
        this.handlerfac = sAXHandlerFactory;
        this.engine = null;
    }

    @Deprecated
    public boolean getValidation() {
        return isValidating();
    }

    public boolean isValidating() {
        return this.readerfac.isValidating();
    }

    @Deprecated
    public void setValidation(boolean z) {
        setXMLReaderFactory(z ? XMLReaders.DTDVALIDATING : XMLReaders.NONVALIDATING);
    }

    public ErrorHandler getErrorHandler() {
        return this.saxErrorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.saxErrorHandler = errorHandler;
        this.engine = null;
    }

    public EntityResolver getEntityResolver() {
        return this.saxEntityResolver;
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.saxEntityResolver = entityResolver;
        this.engine = null;
    }

    public DTDHandler getDTDHandler() {
        return this.saxDTDHandler;
    }

    public void setDTDHandler(DTDHandler dTDHandler) {
        this.saxDTDHandler = dTDHandler;
        this.engine = null;
    }

    public XMLFilter getXMLFilter() {
        return this.saxXMLFilter;
    }

    public void setXMLFilter(XMLFilter xMLFilter) {
        this.saxXMLFilter = xMLFilter;
        this.engine = null;
    }

    public boolean getIgnoringElementContentWhitespace() {
        return this.ignoringWhite;
    }

    public void setIgnoringElementContentWhitespace(boolean z) {
        this.ignoringWhite = z;
        this.engine = null;
    }

    public boolean getIgnoringBoundaryWhitespace() {
        return this.ignoringBoundaryWhite;
    }

    public void setIgnoringBoundaryWhitespace(boolean z) {
        this.ignoringBoundaryWhite = z;
        this.engine = null;
    }

    public boolean getExpandEntities() {
        return this.expand;
    }

    public void setExpandEntities(boolean z) {
        this.expand = z;
        this.engine = null;
    }

    public boolean getReuseParser() {
        return this.reuseParser;
    }

    public void setReuseParser(boolean z) {
        this.reuseParser = z;
        if (!z) {
            this.engine = null;
        }
    }

    public void setFeature(String str, boolean z) {
        this.features.put(str, z ? Boolean.TRUE : Boolean.FALSE);
        this.engine = null;
    }

    public void setProperty(String str, Object obj) {
        this.properties.put(str, obj);
        this.engine = null;
    }

    public SAXEngine buildEngine() throws JDOMException {
        SAXHandler createSAXHandler = this.handlerfac.createSAXHandler(this.jdomfac);
        createSAXHandler.setExpandEntities(this.expand);
        createSAXHandler.setIgnoringElementContentWhitespace(this.ignoringWhite);
        createSAXHandler.setIgnoringBoundaryWhitespace(this.ignoringBoundaryWhite);
        XMLReader createParser = createParser();
        configureParser(createParser, createSAXHandler);
        return new SAXBuilderEngine(createParser, createSAXHandler, this.readerfac.isValidating());
    }

    /* access modifiers changed from: protected */
    public XMLReader createParser() throws JDOMException {
        XMLReader createXMLReader = this.readerfac.createXMLReader();
        XMLFilter xMLFilter = this.saxXMLFilter;
        if (xMLFilter == null) {
            return createXMLReader;
        }
        while (xMLFilter.getParent() instanceof XMLFilter) {
            xMLFilter = (XMLFilter) xMLFilter.getParent();
        }
        xMLFilter.setParent(createXMLReader);
        return this.saxXMLFilter;
    }

    private SAXEngine getEngine() throws JDOMException {
        SAXEngine sAXEngine = this.engine;
        if (sAXEngine != null) {
            return sAXEngine;
        }
        this.engine = buildEngine();
        return this.engine;
    }

    /* access modifiers changed from: protected */
    public void configureParser(XMLReader xMLReader, SAXHandler sAXHandler) throws JDOMException {
        String str = JDOMConstants.SAX_FEATURE_EXTERNAL_ENT;
        xMLReader.setContentHandler(sAXHandler);
        EntityResolver entityResolver = this.saxEntityResolver;
        if (entityResolver != null) {
            xMLReader.setEntityResolver(entityResolver);
        }
        DTDHandler dTDHandler = this.saxDTDHandler;
        if (dTDHandler != null) {
            xMLReader.setDTDHandler(dTDHandler);
        } else {
            xMLReader.setDTDHandler(sAXHandler);
        }
        ErrorHandler errorHandler = this.saxErrorHandler;
        if (errorHandler != null) {
            xMLReader.setErrorHandler(errorHandler);
        } else {
            xMLReader.setErrorHandler(new BuilderErrorHandler());
        }
        boolean z = false;
        try {
            xMLReader.setProperty(JDOMConstants.SAX_PROPERTY_LEXICAL_HANDLER, sAXHandler);
            z = true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
        }
        if (!z) {
            try {
                xMLReader.setProperty(JDOMConstants.SAX_PROPERTY_LEXICAL_HANDLER_ALT, sAXHandler);
            } catch (SAXNotRecognizedException | SAXNotSupportedException unused2) {
            }
        }
        for (Entry entry : this.features.entrySet()) {
            internalSetFeature(xMLReader, (String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue(), (String) entry.getKey());
        }
        for (Entry entry2 : this.properties.entrySet()) {
            internalSetProperty(xMLReader, (String) entry2.getKey(), entry2.getValue(), (String) entry2.getKey());
        }
        try {
            if (xMLReader.getFeature(str) != this.expand) {
                xMLReader.setFeature(str, this.expand);
            }
        } catch (SAXException unused3) {
        }
        if (!this.expand) {
            try {
                xMLReader.setProperty(JDOMConstants.SAX_PROPERTY_DECLARATION_HANDLER, sAXHandler);
            } catch (SAXNotRecognizedException | SAXNotSupportedException unused4) {
            }
        }
    }

    private void internalSetFeature(XMLReader xMLReader, String str, boolean z, String str2) throws JDOMException {
        try {
            xMLReader.setFeature(str, z);
        } catch (SAXNotSupportedException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(" feature not supported for SAX driver ");
            sb.append(xMLReader.getClass().getName());
            throw new JDOMException(sb.toString());
        } catch (SAXNotRecognizedException unused2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(" feature not recognized for SAX driver ");
            sb2.append(xMLReader.getClass().getName());
            throw new JDOMException(sb2.toString());
        }
    }

    private void internalSetProperty(XMLReader xMLReader, String str, Object obj, String str2) throws JDOMException {
        try {
            xMLReader.setProperty(str, obj);
        } catch (SAXNotSupportedException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(" property not supported for SAX driver ");
            sb.append(xMLReader.getClass().getName());
            throw new JDOMException(sb.toString());
        } catch (SAXNotRecognizedException unused2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(" property not recognized for SAX driver ");
            sb2.append(xMLReader.getClass().getName());
            throw new JDOMException(sb2.toString());
        }
    }

    public Document build(InputSource inputSource) throws JDOMException, IOException {
        try {
            return getEngine().build(inputSource);
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(InputStream inputStream) throws JDOMException, IOException {
        try {
            return getEngine().build(inputStream);
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(File file) throws JDOMException, IOException {
        try {
            return getEngine().build(file);
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(URL url) throws JDOMException, IOException {
        try {
            return getEngine().build(url);
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(InputStream inputStream, String str) throws JDOMException, IOException {
        try {
            Document build = getEngine().build(inputStream, str);
            return build;
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(Reader reader) throws JDOMException, IOException {
        try {
            return getEngine().build(reader);
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(Reader reader, String str) throws JDOMException, IOException {
        try {
            Document build = getEngine().build(reader, str);
            return build;
        } finally {
            if (!this.reuseParser) {
                this.engine = null;
            }
        }
    }

    public Document build(String str) throws JDOMException, IOException {
        if (str != null) {
            try {
                Document build = getEngine().build(str);
                if (!this.reuseParser) {
                    this.engine = null;
                }
                return build;
            } catch (IOException e) {
                int length = str.length();
                int i = 0;
                while (i < length && Verifier.isXMLWhitespace(str.charAt(i))) {
                    i++;
                }
                if (i >= length || '<' != str.charAt(i)) {
                    throw e;
                }
                MalformedURLException malformedURLException = new MalformedURLException("SAXBuilder.build(String) expects the String to be a systemID, but in this instance it appears to be actual XML data.");
                malformedURLException.initCause(e);
                throw malformedURLException;
            } catch (Throwable th) {
                if (!this.reuseParser) {
                    this.engine = null;
                }
                throw th;
            }
        } else {
            throw new NullPointerException("Unable to build a URI from a null systemID.");
        }
    }
}
