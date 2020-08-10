package com.rometools.rome.p052io;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.impl.ConfigurableClassLoader;
import com.rometools.rome.p052io.impl.FeedParsers;
import com.rometools.rome.p052io.impl.XmlFixerReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import org.jdom2.JDOMConstants;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaders;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/* renamed from: com.rometools.rome.io.WireFeedInput */
public class WireFeedInput {
    /* access modifiers changed from: private */
    public static final InputSource EMPTY_INPUTSOURCE = new InputSource(new ByteArrayInputStream(new byte[0]));
    private static final EntityResolver RESOLVER = new EmptyEntityResolver();
    private static Map<ClassLoader, FeedParsers> clMap = new WeakHashMap();
    private boolean allowDoctypes;
    private final Locale locale;
    private final boolean validate;
    private boolean xmlHealerOn;

    /* renamed from: com.rometools.rome.io.WireFeedInput$EmptyEntityResolver */
    private static class EmptyEntityResolver implements EntityResolver {
        private EmptyEntityResolver() {
        }

        public InputSource resolveEntity(String str, String str2) {
            if (str2 == null || !str2.endsWith(".dtd")) {
                return null;
            }
            return WireFeedInput.EMPTY_INPUTSOURCE;
        }
    }

    private static FeedParsers getFeedParsers() {
        FeedParsers feedParsers;
        synchronized (WireFeedInput.class) {
            ClassLoader classLoader = ConfigurableClassLoader.INSTANCE.getClassLoader();
            feedParsers = (FeedParsers) clMap.get(classLoader);
            if (feedParsers == null) {
                feedParsers = new FeedParsers();
                clMap.put(classLoader, feedParsers);
            }
        }
        return feedParsers;
    }

    public static List<String> getSupportedFeedTypes() {
        return getFeedParsers().getSupportedFeedTypes();
    }

    public WireFeedInput() {
        this(false, Locale.US);
    }

    public WireFeedInput(boolean z, Locale locale2) {
        this.allowDoctypes = false;
        this.validate = false;
        this.xmlHealerOn = true;
        this.locale = locale2;
    }

    public void setXmlHealerOn(boolean z) {
        this.xmlHealerOn = z;
    }

    public boolean getXmlHealerOn() {
        return this.xmlHealerOn;
    }

    public boolean isAllowDoctypes() {
        return this.allowDoctypes;
    }

    public void setAllowDoctypes(boolean z) {
        this.allowDoctypes = z;
    }

    public WireFeed build(File file) throws FileNotFoundException, IOException, IllegalArgumentException, FeedException {
        Reader fileReader = new FileReader(file);
        try {
            if (this.xmlHealerOn) {
                fileReader = new XmlFixerReader(fileReader);
            }
            return build(fileReader);
        } finally {
            fileReader.close();
        }
    }

    public WireFeed build(Reader reader) throws IllegalArgumentException, FeedException {
        SAXBuilder createSAXBuilder = createSAXBuilder();
        try {
            if (this.xmlHealerOn) {
                reader = new XmlFixerReader(reader);
            }
            return build(createSAXBuilder.build(reader));
        } catch (JDOMParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid XML: ");
            sb.append(e.getMessage());
            throw new ParsingFeedException(sb.toString(), e);
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ParsingFeedException("Invalid XML", e3);
        }
    }

    public WireFeed build(InputSource inputSource) throws IllegalArgumentException, FeedException {
        try {
            return build(createSAXBuilder().build(inputSource));
        } catch (JDOMParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid XML: ");
            sb.append(e.getMessage());
            throw new ParsingFeedException(sb.toString(), e);
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ParsingFeedException("Invalid XML", e3);
        }
    }

    public WireFeed build(Document document) throws IllegalArgumentException, FeedException {
        try {
            return build(new DOMBuilder().build(document));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e2) {
            throw new ParsingFeedException("Invalid XML", e2);
        }
    }

    public WireFeed build(org.jdom2.Document document) throws IllegalArgumentException, FeedException {
        WireFeedParser parserFor = getFeedParsers().getParserFor(document);
        if (parserFor != null) {
            return parserFor.parse(document, this.validate, this.locale);
        }
        throw new IllegalArgumentException("Invalid document");
    }

    /* access modifiers changed from: protected */
    public SAXBuilder createSAXBuilder() {
        SAXBuilder sAXBuilder;
        if (this.validate) {
            sAXBuilder = new SAXBuilder((XMLReaderJDOMFactory) XMLReaders.DTDVALIDATING);
        } else {
            sAXBuilder = new SAXBuilder((XMLReaderJDOMFactory) XMLReaders.NONVALIDATING);
        }
        sAXBuilder.setEntityResolver(RESOLVER);
        try {
            XMLReader createParser = sAXBuilder.createParser();
            setFeature(sAXBuilder, createParser, JDOMConstants.SAX_FEATURE_EXTERNAL_ENT, false);
            setFeature(sAXBuilder, createParser, "http://xml.org/sax/features/external-parameter-entities", false);
            setFeature(sAXBuilder, createParser, "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            if (!this.allowDoctypes) {
                setFeature(sAXBuilder, createParser, "http://apache.org/xml/features/disallow-doctype-decl", true);
            }
            sAXBuilder.setExpandEntities(false);
            return sAXBuilder;
        } catch (JDOMException e) {
            throw new IllegalStateException("JDOM could not create a SAX parser", e);
        }
    }

    private void setFeature(SAXBuilder sAXBuilder, XMLReader xMLReader, String str, boolean z) {
        if (isFeatureSupported(xMLReader, str, z)) {
            sAXBuilder.setFeature(str, z);
        }
    }

    private boolean isFeatureSupported(XMLReader xMLReader, String str, boolean z) {
        try {
            xMLReader.setFeature(str, z);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            return false;
        }
    }
}
