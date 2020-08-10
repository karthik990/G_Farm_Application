package org.jdom2.input.sax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

public class AbstractReaderXSDFactory extends AbstractReaderSchemaFactory {
    private static final ThreadLocal<SchemaFactory> schemafactl = new ThreadLocal<>();

    protected interface SchemaFactoryProvider {
        SchemaFactory getSchemaFactory();
    }

    private static final Schema getSchemaFromString(SchemaFactoryProvider schemaFactoryProvider, String... strArr) throws JDOMException {
        if (strArr == null) {
            throw new NullPointerException("Cannot specify a null input array");
        } else if (strArr.length != 0) {
            Source[] sourceArr = new Source[strArr.length];
            int i = 0;
            while (i < strArr.length) {
                if (strArr[i] != null) {
                    sourceArr[i] = new StreamSource(strArr[i]);
                    i++;
                } else {
                    throw new NullPointerException("Cannot specify a null SystemID");
                }
            }
            return getSchemaFromSource(schemaFactoryProvider, sourceArr);
        } else {
            throw new IllegalArgumentException("You need at least one XSD source for an XML Schema validator");
        }
    }

    private static final Schema getSchemaFromFile(SchemaFactoryProvider schemaFactoryProvider, File... fileArr) throws JDOMException {
        if (fileArr == null) {
            throw new NullPointerException("Cannot specify a null input array");
        } else if (fileArr.length != 0) {
            Source[] sourceArr = new Source[fileArr.length];
            int i = 0;
            while (i < fileArr.length) {
                if (fileArr[i] != null) {
                    sourceArr[i] = new StreamSource(fileArr[i]);
                    i++;
                } else {
                    throw new NullPointerException("Cannot specify a null SystemID");
                }
            }
            return getSchemaFromSource(schemaFactoryProvider, sourceArr);
        } else {
            throw new IllegalArgumentException("You need at least one XSD source for an XML Schema validator");
        }
    }

    private static final Schema getSchemaFromURL(SchemaFactoryProvider schemaFactoryProvider, URL... urlArr) throws JDOMException {
        int i;
        if (urlArr == null) {
            throw new NullPointerException("Cannot specify a null input array");
        } else if (urlArr.length != 0) {
            InputStream[] inputStreamArr = new InputStream[urlArr.length];
            int i2 = 0;
            try {
                Source[] sourceArr = new Source[urlArr.length];
                i = 0;
                while (i < urlArr.length) {
                    if (urlArr[i] != null) {
                        InputStream openStream = urlArr[i].openStream();
                        inputStreamArr[i] = openStream;
                        sourceArr[i] = new StreamSource(openStream, urlArr[i].toString());
                        i++;
                    } else {
                        throw new NullPointerException("Cannot specify a null SystemID");
                    }
                }
                Schema schemaFromSource = getSchemaFromSource(schemaFactoryProvider, sourceArr);
                int length = inputStreamArr.length;
                while (i2 < length) {
                    InputStream inputStream = inputStreamArr[i2];
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    i2++;
                }
                return schemaFromSource;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to read Schema URL ");
                sb.append(urlArr[i].toString());
                throw new JDOMException(sb.toString(), e);
            } catch (Throwable th) {
                int length2 = inputStreamArr.length;
                while (i2 < length2) {
                    InputStream inputStream2 = inputStreamArr[i2];
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    i2++;
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("You need at least one XSD source for an XML Schema validator");
        }
    }

    private static final Schema getSchemaFromSource(SchemaFactoryProvider schemaFactoryProvider, Source... sourceArr) throws JDOMException {
        if (sourceArr == null) {
            throw new NullPointerException("Cannot specify a null input array");
        } else if (sourceArr.length != 0) {
            try {
                SchemaFactory schemaFactory = (SchemaFactory) schemafactl.get();
                if (schemaFactory == null) {
                    schemaFactory = schemaFactoryProvider.getSchemaFactory();
                    schemafactl.set(schemaFactory);
                }
                if (schemaFactory != null) {
                    return schemaFactory.newSchema(sourceArr);
                }
                throw new JDOMException("Unable to create XSDSchema validator.");
            } catch (SAXException e) {
                String arrays = Arrays.toString(sourceArr);
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to create a Schema for Sources ");
                sb.append(arrays);
                throw new JDOMException(sb.toString(), e);
            }
        } else {
            throw new IllegalArgumentException("You need at least one XSD Source for an XML Schema validator");
        }
    }

    public AbstractReaderXSDFactory(SAXParserFactory sAXParserFactory, SchemaFactoryProvider schemaFactoryProvider, String... strArr) throws JDOMException {
        super(sAXParserFactory, getSchemaFromString(schemaFactoryProvider, strArr));
    }

    public AbstractReaderXSDFactory(SAXParserFactory sAXParserFactory, SchemaFactoryProvider schemaFactoryProvider, URL... urlArr) throws JDOMException {
        super(sAXParserFactory, getSchemaFromURL(schemaFactoryProvider, urlArr));
    }

    public AbstractReaderXSDFactory(SAXParserFactory sAXParserFactory, SchemaFactoryProvider schemaFactoryProvider, File... fileArr) throws JDOMException {
        super(sAXParserFactory, getSchemaFromFile(schemaFactoryProvider, fileArr));
    }

    public AbstractReaderXSDFactory(SAXParserFactory sAXParserFactory, SchemaFactoryProvider schemaFactoryProvider, Source... sourceArr) throws JDOMException {
        super(sAXParserFactory, getSchemaFromSource(schemaFactoryProvider, sourceArr));
    }
}
