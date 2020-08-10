package org.jdom2.output;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.http.protocol.HTTP;
import org.jdom2.IllegalDataException;
import org.jdom2.Verifier;

public class Format implements Cloneable {
    private static final EscapeStrategy Bits7EscapeStrategy = new EscapeStrategy7Bits();
    private static final EscapeStrategy Bits8EscapeStrategy = new EscapeStrategy8Bits();
    private static final EscapeStrategy DefaultEscapeStrategy = new EscapeStrategy() {
        public boolean shouldEscape(char c) {
            return Verifier.isHighSurrogate(c);
        }
    };
    private static final String STANDARD_ENCODING = "UTF-8";
    private static final String STANDARD_INDENT = "  ";
    private static final String STANDARD_LINE_SEPARATOR = LineSeparator.DEFAULT.value();
    private static final EscapeStrategy UTFEscapeStrategy = new EscapeStrategyUTF();
    String encoding;
    EscapeStrategy escapeStrategy;
    boolean expandEmptyElements;
    boolean ignoreTrAXEscapingPIs;
    String indent = null;
    String lineSeparator = STANDARD_LINE_SEPARATOR;
    TextMode mode;
    boolean omitDeclaration;
    boolean omitEncoding;
    boolean specifiedAttributesOnly;

    private static final class DefaultCharsetEscapeStrategy implements EscapeStrategy {
        private final CharsetEncoder encoder;

        public DefaultCharsetEscapeStrategy(CharsetEncoder charsetEncoder) {
            this.encoder = charsetEncoder;
        }

        public boolean shouldEscape(char c) {
            if (Verifier.isHighSurrogate(c)) {
                return true;
            }
            return !this.encoder.canEncode(c);
        }
    }

    private static final class EscapeStrategy7Bits implements EscapeStrategy {
        public boolean shouldEscape(char c) {
            return (c >>> 7) != 0;
        }

        private EscapeStrategy7Bits() {
        }
    }

    private static final class EscapeStrategy8Bits implements EscapeStrategy {
        public boolean shouldEscape(char c) {
            return (c >>> 8) != 0;
        }

        private EscapeStrategy8Bits() {
        }
    }

    private static final class EscapeStrategyUTF implements EscapeStrategy {
        private EscapeStrategyUTF() {
        }

        public final boolean shouldEscape(char c) {
            return Verifier.isHighSurrogate(c);
        }
    }

    public enum TextMode {
        PRESERVE,
        TRIM,
        NORMALIZE,
        TRIM_FULL_WHITE
    }

    public static Format getRawFormat() {
        return new Format();
    }

    public static Format getPrettyFormat() {
        Format format = new Format();
        format.setIndent(STANDARD_INDENT);
        format.setTextMode(TextMode.TRIM);
        return format;
    }

    public static Format getCompactFormat() {
        Format format = new Format();
        format.setTextMode(TextMode.NORMALIZE);
        return format;
    }

    public static final String compact(String str) {
        int length = str.length() - 1;
        int i = 0;
        while (i <= length && Verifier.isXMLWhitespace(str.charAt(i))) {
            i++;
        }
        while (length > i && Verifier.isXMLWhitespace(str.charAt(length))) {
            length--;
        }
        if (i > length) {
            return "";
        }
        StringBuilder sb = new StringBuilder((length - i) + 1);
        boolean z = true;
        while (i <= length) {
            char charAt = str.charAt(i);
            if (!Verifier.isXMLWhitespace(charAt)) {
                sb.append(charAt);
                z = true;
            } else if (z) {
                sb.append(' ');
                z = false;
            }
            i++;
        }
        return sb.toString();
    }

    public static final String trimRight(String str) {
        int length = str.length() - 1;
        while (length >= 0 && Verifier.isXMLWhitespace(str.charAt(length))) {
            length--;
        }
        if (length < 0) {
            return "";
        }
        return str.substring(0, length + 1);
    }

    public static final String trimLeft(String str) {
        int length = str.length();
        int i = 0;
        while (i < length && Verifier.isXMLWhitespace(str.charAt(i))) {
            i++;
        }
        if (i >= length) {
            return "";
        }
        return str.substring(i);
    }

    public static final String trimBoth(String str) {
        int length = str.length() - 1;
        while (length > 0 && Verifier.isXMLWhitespace(str.charAt(length))) {
            length--;
        }
        int i = 0;
        while (i <= length && Verifier.isXMLWhitespace(str.charAt(i))) {
            i++;
        }
        if (i > length) {
            return "";
        }
        return str.substring(i, length + 1);
    }

    public static final String escapeAttribute(EscapeStrategy escapeStrategy2, String str) {
        EscapeStrategy escapeStrategy3 = escapeStrategy2;
        String str2 = str;
        int length = str.length();
        int i = 0;
        while (i < length) {
            char charAt = str2.charAt(i);
            if (charAt == '<' || charAt == '>' || charAt == '&' || charAt == 13 || charAt == 10 || charAt == '\"' || charAt == 9 || escapeStrategy3.shouldEscape(charAt)) {
                break;
            }
            i++;
        }
        if (i == length) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(length + 5);
        sb.append(str2, 0, i);
        while (true) {
            char c = 0;
            while (i < length) {
                int i2 = i + 1;
                char charAt2 = str2.charAt(i);
                String str3 = "&#x";
                if (c <= 0) {
                    if (charAt2 == 9) {
                        sb.append("&#x9;");
                    } else if (charAt2 == 10) {
                        sb.append("&#xA;");
                    } else if (charAt2 == 13) {
                        sb.append("&#xD;");
                    } else if (charAt2 == '\"') {
                        sb.append("&quot;");
                    } else if (charAt2 == '&') {
                        sb.append("&amp;");
                    } else if (charAt2 == '<') {
                        sb.append("&lt;");
                    } else if (charAt2 == '>') {
                        sb.append("&gt;");
                    } else if (!escapeStrategy3.shouldEscape(charAt2)) {
                        sb.append(charAt2);
                    } else if (Verifier.isHighSurrogate(charAt2)) {
                        c = charAt2;
                    } else {
                        sb.append(str3);
                        sb.append(Integer.toHexString(charAt2));
                        sb.append(';');
                    }
                    i = i2;
                } else if (Verifier.isLowSurrogate(charAt2)) {
                    int decodeSurrogatePair = Verifier.decodeSurrogatePair(c, charAt2);
                    sb.append(str3);
                    sb.append(Integer.toHexString(decodeSurrogatePair));
                    sb.append(';');
                    i = i2;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Could not decode surrogate pair 0x");
                    sb2.append(Integer.toHexString(c));
                    sb2.append(" / 0x");
                    sb2.append(Integer.toHexString(charAt2));
                    throw new IllegalDataException(sb2.toString());
                }
            }
            if (c <= 0) {
                return sb.toString();
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Surrogate pair 0x");
            sb3.append(Integer.toHexString(c));
            sb3.append("truncated");
            throw new IllegalDataException(sb3.toString());
        }
    }

    public static final String escapeText(EscapeStrategy escapeStrategy2, String str, String str2) {
        EscapeStrategy escapeStrategy3 = escapeStrategy2;
        String str3 = str;
        String str4 = str2;
        int length = str2.length();
        int i = 0;
        while (i < length) {
            char charAt = str4.charAt(i);
            if (charAt == '<' || charAt == '>' || charAt == '&' || charAt == 13 || charAt == 10 || escapeStrategy3.shouldEscape(charAt)) {
                break;
            }
            i++;
        }
        if (i == length) {
            return str4;
        }
        StringBuilder sb = new StringBuilder();
        if (i > 0) {
            sb.append(str4, 0, i);
        }
        while (true) {
            char c = 0;
            while (i < length) {
                int i2 = i + 1;
                char charAt2 = str4.charAt(i);
                String str5 = ";";
                String str6 = "&#x";
                if (c <= 0) {
                    if (charAt2 != 10) {
                        if (charAt2 == 13) {
                            sb.append("&#xD;");
                        } else if (charAt2 == '&') {
                            sb.append("&amp;");
                        } else if (charAt2 == '<') {
                            sb.append("&lt;");
                        } else if (charAt2 == '>') {
                            sb.append("&gt;");
                        } else if (!escapeStrategy3.shouldEscape(charAt2)) {
                            sb.append(charAt2);
                        } else if (Verifier.isHighSurrogate(charAt2)) {
                            c = charAt2;
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str6);
                            sb2.append(Integer.toHexString(charAt2));
                            sb2.append(str5);
                            sb.append(sb2.toString());
                        }
                    } else if (str3 != null) {
                        sb.append(str3);
                    } else {
                        sb.append(10);
                    }
                    i = i2;
                } else if (Verifier.isLowSurrogate(charAt2)) {
                    int decodeSurrogatePair = Verifier.decodeSurrogatePair(c, charAt2);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str6);
                    sb3.append(Integer.toHexString(decodeSurrogatePair));
                    sb3.append(str5);
                    sb.append(sb3.toString());
                    i = i2;
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Could not decode surrogate pair 0x");
                    sb4.append(Integer.toHexString(c));
                    sb4.append(" / 0x");
                    sb4.append(Integer.toHexString(charAt2));
                    throw new IllegalDataException(sb4.toString());
                }
            }
            if (c <= 0) {
                return sb.toString();
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Surrogate pair 0x");
            sb5.append(Integer.toHexString(c));
            sb5.append("truncated");
            throw new IllegalDataException(sb5.toString());
        }
    }

    private static final EscapeStrategy chooseStrategy(String str) {
        if ("UTF-8".equalsIgnoreCase(str) || "UTF-16".equalsIgnoreCase(str)) {
            return UTFEscapeStrategy;
        }
        if (str.toUpperCase().startsWith("ISO-8859-") || "Latin1".equalsIgnoreCase(str)) {
            return Bits8EscapeStrategy;
        }
        if ("US-ASCII".equalsIgnoreCase(str) || HTTP.ASCII.equalsIgnoreCase(str)) {
            return Bits7EscapeStrategy;
        }
        try {
            return new DefaultCharsetEscapeStrategy(Charset.forName(str).newEncoder());
        } catch (Exception unused) {
            return DefaultEscapeStrategy;
        }
    }

    private Format() {
        String str = "UTF-8";
        this.encoding = str;
        this.omitDeclaration = false;
        this.omitEncoding = false;
        this.specifiedAttributesOnly = false;
        this.expandEmptyElements = false;
        this.ignoreTrAXEscapingPIs = false;
        this.mode = TextMode.PRESERVE;
        this.escapeStrategy = DefaultEscapeStrategy;
        setEncoding(str);
    }

    public Format setEscapeStrategy(EscapeStrategy escapeStrategy2) {
        this.escapeStrategy = escapeStrategy2;
        return this;
    }

    public EscapeStrategy getEscapeStrategy() {
        return this.escapeStrategy;
    }

    public Format setLineSeparator(String str) {
        if ("".equals(str)) {
            str = null;
        }
        this.lineSeparator = str;
        return this;
    }

    public Format setLineSeparator(LineSeparator lineSeparator2) {
        return setLineSeparator(lineSeparator2 == null ? STANDARD_LINE_SEPARATOR : lineSeparator2.value());
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public Format setOmitEncoding(boolean z) {
        this.omitEncoding = z;
        return this;
    }

    public boolean getOmitEncoding() {
        return this.omitEncoding;
    }

    public Format setOmitDeclaration(boolean z) {
        this.omitDeclaration = z;
        return this;
    }

    public boolean getOmitDeclaration() {
        return this.omitDeclaration;
    }

    public Format setExpandEmptyElements(boolean z) {
        this.expandEmptyElements = z;
        return this;
    }

    public boolean getExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    public void setIgnoreTrAXEscapingPIs(boolean z) {
        this.ignoreTrAXEscapingPIs = z;
    }

    public boolean getIgnoreTrAXEscapingPIs() {
        return this.ignoreTrAXEscapingPIs;
    }

    public Format setTextMode(TextMode textMode) {
        this.mode = textMode;
        return this;
    }

    public TextMode getTextMode() {
        return this.mode;
    }

    public Format setIndent(String str) {
        this.indent = str;
        return this;
    }

    public String getIndent() {
        return this.indent;
    }

    public Format setEncoding(String str) {
        this.encoding = str;
        this.escapeStrategy = chooseStrategy(str);
        return this;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean isSpecifiedAttributesOnly() {
        return this.specifiedAttributesOnly;
    }

    public void setSpecifiedAttributesOnly(boolean z) {
        this.specifiedAttributesOnly = z;
    }

    public Format clone() {
        try {
            return (Format) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
