package com.rometools.rome.p052io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.rometools.rome.io.XmlReader */
public class XmlReader extends Reader {
    private static final int BUFFER_SIZE = 4096;
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([.[^; ]]*)");
    private static final String CP1047 = "CP1047";
    private static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
    private static final MessageFormat HTTP_EX_1 = new MessageFormat("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL");
    private static final MessageFormat HTTP_EX_2 = new MessageFormat("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch");
    private static final MessageFormat HTTP_EX_3 = new MessageFormat("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME");
    private static final MessageFormat RAW_EX_1 = new MessageFormat("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch");
    private static final MessageFormat RAW_EX_2 = new MessageFormat("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM");
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_16 = "UTF-16";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String UTF_16LE = "UTF-16LE";
    private static final String UTF_8 = "UTF-8";
    private static String staticDefaultEncoding = null;
    private final String defaultEncoding;
    private String encoding;
    private Reader reader;

    public XmlReader(File file) throws IOException {
        this((InputStream) new FileInputStream(file));
    }

    public XmlReader(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public XmlReader(InputStream inputStream, boolean z, String str) throws IOException, XmlReaderException {
        if (str == null) {
            this.defaultEncoding = staticDefaultEncoding;
        } else {
            this.defaultEncoding = str;
        }
        try {
            doRawStream(inputStream, z);
        } catch (XmlReaderException e) {
            if (z) {
                doLenientDetection(null, e);
                return;
            }
            throw e;
        }
    }

    public XmlReader(InputStream inputStream, boolean z) throws IOException, XmlReaderException {
        this(inputStream, z, (String) null);
    }

    public XmlReader(URL url) throws IOException {
        this(url.openConnection());
    }

    public XmlReader(URLConnection uRLConnection) throws IOException {
        this.defaultEncoding = staticDefaultEncoding;
        if (uRLConnection instanceof HttpURLConnection) {
            Package packageR = getClass().getPackage();
            String str = "User-Agent";
            if (packageR.getImplementationTitle() == null || packageR.getImplementationVersion() == null) {
                uRLConnection.setRequestProperty(str, "ROME");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(packageR.getImplementationTitle());
                sb.append("/");
                sb.append(packageR.getImplementationVersion());
                uRLConnection.setRequestProperty(str, sb.toString());
            }
            try {
                doHttpStream(uRLConnection.getInputStream(), uRLConnection.getContentType(), true);
            } catch (XmlReaderException e) {
                doLenientDetection(uRLConnection.getContentType(), e);
            }
        } else if (uRLConnection.getContentType() != null) {
            try {
                doHttpStream(uRLConnection.getInputStream(), uRLConnection.getContentType(), true);
            } catch (XmlReaderException e2) {
                doLenientDetection(uRLConnection.getContentType(), e2);
            }
        } else {
            try {
                doRawStream(uRLConnection.getInputStream(), true);
            } catch (XmlReaderException e3) {
                doLenientDetection(null, e3);
            }
        }
    }

    public XmlReader(InputStream inputStream, String str) throws IOException {
        this(inputStream, str, true);
    }

    public XmlReader(InputStream inputStream, String str, boolean z, String str2) throws IOException, XmlReaderException {
        if (str2 == null) {
            this.defaultEncoding = staticDefaultEncoding;
        } else {
            this.defaultEncoding = str2;
        }
        try {
            doHttpStream(inputStream, str, z);
        } catch (XmlReaderException e) {
            if (z) {
                doLenientDetection(str, e);
                return;
            }
            throw e;
        }
    }

    public XmlReader(InputStream inputStream, String str, boolean z) throws IOException, XmlReaderException {
        this(inputStream, str, z, null);
    }

    public static String getDefaultEncoding() {
        return staticDefaultEncoding;
    }

    public static void setDefaultEncoding(String str) {
        staticDefaultEncoding = str;
    }

    public String getEncoding() {
        return this.encoding;
    }

    private void doLenientDetection(String str, XmlReaderException xmlReaderException) throws IOException {
        if (str != null && str.startsWith("text/html")) {
            String substring = str.substring(9);
            StringBuilder sb = new StringBuilder();
            sb.append("text/xml");
            sb.append(substring);
            try {
                doHttpStream(xmlReaderException.getInputStream(), sb.toString(), true);
                xmlReaderException = null;
            } catch (XmlReaderException e) {
                xmlReaderException = e;
            }
        }
        if (xmlReaderException != null) {
            String xmlEncoding = xmlReaderException.getXmlEncoding();
            if (xmlEncoding == null) {
                xmlEncoding = xmlReaderException.getContentTypeEncoding();
            }
            if (xmlEncoding == null) {
                xmlEncoding = this.defaultEncoding;
                if (xmlEncoding == null) {
                    xmlEncoding = "UTF-8";
                }
            }
            prepareReader(xmlReaderException.getInputStream(), xmlEncoding);
        }
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        return this.reader.read(cArr, i, i2);
    }

    public void close() throws IOException {
        this.reader.close();
    }

    private void doRawStream(InputStream inputStream, boolean z) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
        String bOMEncoding = getBOMEncoding(bufferedInputStream);
        String xMLGuessEncoding = getXMLGuessEncoding(bufferedInputStream);
        prepareReader(bufferedInputStream, calculateRawEncoding(bOMEncoding, xMLGuessEncoding, getXmlProlog(bufferedInputStream, xMLGuessEncoding), bufferedInputStream));
    }

    private void doHttpStream(InputStream inputStream, String str, boolean z) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
        String contentTypeMime = getContentTypeMime(str);
        String contentTypeEncoding = getContentTypeEncoding(str);
        String bOMEncoding = getBOMEncoding(bufferedInputStream);
        String xMLGuessEncoding = getXMLGuessEncoding(bufferedInputStream);
        prepareReader(bufferedInputStream, calculateHttpEncoding(contentTypeMime, contentTypeEncoding, bOMEncoding, xMLGuessEncoding, getXmlProlog(bufferedInputStream, xMLGuessEncoding), bufferedInputStream, z));
    }

    private void prepareReader(InputStream inputStream, String str) throws IOException {
        this.reader = new InputStreamReader(inputStream, str);
        this.encoding = str;
    }

    private String calculateRawEncoding(String str, String str2, String str3, InputStream inputStream) throws IOException {
        String str4 = str;
        String str5 = str2;
        String str6 = str3;
        String str7 = "UTF-16";
        String str8 = "UTF-16LE";
        String str9 = "UTF-16BE";
        String str10 = "UTF-8";
        if (str4 != null) {
            if (str.equals(str10)) {
                if (str5 != null && !str2.equals(str10)) {
                    XmlReaderException xmlReaderException = new XmlReaderException(RAW_EX_1.format(new Object[]{str4, str5, str6}), str, str2, str3, inputStream);
                    throw xmlReaderException;
                } else if (str6 == null || str6.equals(str10)) {
                    return str10;
                } else {
                    XmlReaderException xmlReaderException2 = new XmlReaderException(RAW_EX_1.format(new Object[]{str4, str5, str6}), str, str2, str3, inputStream);
                    throw xmlReaderException2;
                }
            } else if (!str.equals(str9) && !str.equals(str8)) {
                XmlReaderException xmlReaderException3 = new XmlReaderException(RAW_EX_2.format(new Object[]{str4, str5, str6}), str, str2, str3, inputStream);
                throw xmlReaderException3;
            } else if (str5 != null && !str2.equals(str)) {
                throw new IOException(RAW_EX_1.format(new Object[]{str4, str5, str6}));
            } else if (str6 == null || str6.equals(str7) || str6.equals(str)) {
                return str4;
            } else {
                XmlReaderException xmlReaderException4 = new XmlReaderException(RAW_EX_1.format(new Object[]{str4, str5, str6}), str, str2, str3, inputStream);
                throw xmlReaderException4;
            }
        } else if (str5 == null || str6 == null) {
            String str11 = this.defaultEncoding;
            if (str11 == null) {
                return str10;
            }
            return str11;
        } else if (!str6.equals(str7) || (!str2.equals(str9) && !str2.equals(str8))) {
            return str6;
        } else {
            return str5;
        }
    }

    private String calculateHttpEncoding(String str, String str2, String str3, String str4, String str5, InputStream inputStream, boolean z) throws IOException {
        String str6 = str2;
        String str7 = str3;
        String str8 = str4;
        String str9 = str5;
        if (z && str9 != null) {
            return str9;
        }
        boolean isAppXml = isAppXml(str);
        boolean isTextXml = isTextXml(str);
        if (!isAppXml && !isTextXml) {
            XmlReaderException xmlReaderException = new XmlReaderException(HTTP_EX_3.format(new Object[]{str, str6, str7, str8, str9}), str, str2, str3, str4, str5, inputStream);
            throw xmlReaderException;
        } else if (str6 != null) {
            InputStream inputStream2 = inputStream;
            if (str7 == null || (!str6.equals("UTF-16BE") && !str6.equals("UTF-16LE"))) {
                String str10 = "UTF-16";
                if (!str6.equals(str10)) {
                    return str6;
                }
                if (str7 != null && str7.startsWith(str10)) {
                    return str7;
                }
                XmlReaderException xmlReaderException2 = new XmlReaderException(HTTP_EX_2.format(new Object[]{str, str6, str7, str8, str9}), str, str2, str3, str4, str5, inputStream);
                throw xmlReaderException2;
            }
            XmlReaderException xmlReaderException3 = new XmlReaderException(HTTP_EX_1.format(new Object[]{str, str6, str7, str8, str9}), str, str2, str3, str4, str5, inputStream);
            throw xmlReaderException3;
        } else if (isAppXml) {
            return calculateRawEncoding(str7, str8, str9, inputStream);
        } else {
            String str11 = this.defaultEncoding;
            if (str11 == null) {
                return "US-ASCII";
            }
            return str11;
        }
    }

    private static String getContentTypeMime(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(";");
        if (indexOf == -1) {
            return str.trim();
        }
        return str.substring(0, indexOf).trim();
    }

    private static String getContentTypeEncoding(String str) {
        String str2 = null;
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(";");
        if (indexOf > -1) {
            Matcher matcher = CHARSET_PATTERN.matcher(str.substring(indexOf + 1));
            if (matcher.find()) {
                str2 = matcher.group(1);
            }
            if (str2 != null) {
                str2 = str2.toUpperCase(Locale.ENGLISH);
            }
        }
        if (str2 == null) {
            return str2;
        }
        String str3 = "\"";
        if (!str2.startsWith(str3) || !str2.endsWith(str3)) {
            String str4 = "'";
            if (!str2.startsWith(str4) || !str2.endsWith(str4)) {
                return str2;
            }
        }
        return str2.substring(1, str2.length() - 1);
    }

    private static String getBOMEncoding(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(3);
        int[] iArr = {bufferedInputStream.read(), bufferedInputStream.read(), bufferedInputStream.read()};
        if (iArr[0] == 254 && iArr[1] == 255) {
            bufferedInputStream.reset();
            bufferedInputStream.read();
            bufferedInputStream.read();
            return "UTF-16BE";
        } else if (iArr[0] == 255 && iArr[1] == 254) {
            bufferedInputStream.reset();
            bufferedInputStream.read();
            bufferedInputStream.read();
            return "UTF-16LE";
        } else if (iArr[0] == 239 && iArr[1] == 187 && iArr[2] == 191) {
            return "UTF-8";
        } else {
            bufferedInputStream.reset();
            return null;
        }
    }

    private static String getXMLGuessEncoding(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(4);
        int[] iArr = {bufferedInputStream.read(), bufferedInputStream.read(), bufferedInputStream.read(), bufferedInputStream.read()};
        bufferedInputStream.reset();
        if (iArr[0] == 0 && iArr[1] == 60 && iArr[2] == 0 && iArr[3] == 63) {
            return "UTF-16BE";
        }
        if (iArr[0] == 60 && iArr[1] == 0 && iArr[2] == 63 && iArr[3] == 0) {
            return "UTF-16LE";
        }
        if (iArr[0] == 60 && iArr[1] == 63 && iArr[2] == 120 && iArr[3] == 109) {
            return "UTF-8";
        }
        if (iArr[0] == 76 && iArr[1] == 111 && iArr[2] == 167 && iArr[3] == 148) {
            return CP1047;
        }
        return null;
    }

    static String getXmlProlog(InputStream inputStream, String str) throws IOException {
        if (str != null) {
            byte[] bArr = new byte[4096];
            inputStream.mark(4096);
            int read = inputStream.read(bArr, 0, 4096);
            int i = -1;
            int i2 = 0;
            int i3 = 4096;
            while (read != -1 && i == -1 && i2 < 4096) {
                i2 += read;
                i3 -= read;
                read = inputStream.read(bArr, i2, i3);
                i = new String(bArr, 0, i2, str).indexOf(">");
            }
            if (i == -1) {
                if (read == -1) {
                    throw new IOException("Unexpected end of XML stream");
                }
                StringBuilder sb = new StringBuilder();
                sb.append("XML prolog or ROOT element not found on first ");
                sb.append(i2);
                sb.append(" bytes");
                throw new IOException(sb.toString());
            } else if (i2 > 0) {
                inputStream.reset();
                Matcher matcher = ENCODING_PATTERN.matcher(new String(bArr, str).substring(0, i));
                if (matcher.find()) {
                    String upperCase = matcher.group(1).toUpperCase(Locale.ENGLISH);
                    return upperCase.substring(1, upperCase.length() - 1);
                }
            }
        }
        return null;
    }

    private static boolean isAppXml(String str) {
        return str != null && (str.equals("application/xml") || str.equals("application/xml-dtd") || str.equals("application/xml-external-parsed-entity") || (str.startsWith("application/") && str.endsWith("+xml")));
    }

    private static boolean isTextXml(String str) {
        return str != null && (str.equals("text/xml") || str.equals("text/xml-external-parsed-entity") || (str.startsWith("text/") && str.endsWith("+xml")));
    }
}
