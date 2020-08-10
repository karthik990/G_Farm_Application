package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class BasicLineParser implements LineParser {
    @Deprecated
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    public static final BasicLineParser INSTANCE = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) {
            protocolVersion = HttpVersion.HTTP_1_1;
        }
        this.protocol = protocolVersion;
    }

    public BasicLineParser() {
        this(null);
    }

    public static ProtocolVersion parseProtocolVersion(String str, LineParser lineParser) throws ParseException {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseProtocolVersion(charArrayBuffer, parserCursor);
    }

    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        String protocol2 = this.protocol.getProtocol();
        int length = protocol2.length();
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        skipWhitespace(charArrayBuffer, parserCursor);
        int pos2 = parserCursor.getPos();
        int i = pos2 + length;
        String str = "Not a valid protocol version: ";
        if (i + 4 <= upperBound) {
            boolean z = true;
            int i2 = 0;
            while (z && i2 < length) {
                z = charArrayBuffer.charAt(pos2 + i2) == protocol2.charAt(i2);
                i2++;
            }
            if (z) {
                z = charArrayBuffer.charAt(i) == '/';
            }
            if (z) {
                int i3 = pos2 + length + 1;
                int indexOf = charArrayBuffer.indexOf(46, i3, upperBound);
                if (indexOf != -1) {
                    try {
                        int parseInt = Integer.parseInt(charArrayBuffer.substringTrimmed(i3, indexOf));
                        int i4 = indexOf + 1;
                        int indexOf2 = charArrayBuffer.indexOf(32, i4, upperBound);
                        if (indexOf2 == -1) {
                            indexOf2 = upperBound;
                        }
                        try {
                            int parseInt2 = Integer.parseInt(charArrayBuffer.substringTrimmed(i4, indexOf2));
                            parserCursor.updatePos(indexOf2);
                            return createProtocolVersion(parseInt, parseInt2);
                        } catch (NumberFormatException unused) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Invalid protocol minor version number: ");
                            sb.append(charArrayBuffer.substring(pos, upperBound));
                            throw new ParseException(sb.toString());
                        }
                    } catch (NumberFormatException unused2) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Invalid protocol major version number: ");
                        sb2.append(charArrayBuffer.substring(pos, upperBound));
                        throw new ParseException(sb2.toString());
                    }
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Invalid protocol version number: ");
                    sb3.append(charArrayBuffer.substring(pos, upperBound));
                    throw new ParseException(sb3.toString());
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(sb4.toString());
            }
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb5.toString());
        }
    }

    /* access modifiers changed from: protected */
    public ProtocolVersion createProtocolVersion(int i, int i2) {
        return this.protocol.forVersion(i, i2);
    }

    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        String protocol2 = this.protocol.getProtocol();
        int length = protocol2.length();
        if (charArrayBuffer.length() < length + 4) {
            return false;
        }
        if (pos < 0) {
            pos = (charArrayBuffer.length() - 4) - length;
        } else if (pos == 0) {
            while (pos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
                pos++;
            }
        }
        int i = pos + length;
        if (i + 4 > charArrayBuffer.length()) {
            return false;
        }
        boolean z = true;
        int i2 = 0;
        while (z && i2 < length) {
            z = charArrayBuffer.charAt(pos + i2) == protocol2.charAt(i2);
            i2++;
        }
        if (z) {
            z = charArrayBuffer.charAt(i) == '/';
        }
        return z;
    }

    public static RequestLine parseRequestLine(String str, LineParser lineParser) throws ParseException {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseRequestLine(charArrayBuffer, parserCursor);
    }

    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        String str = "Invalid request line: ";
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        try {
            skipWhitespace(charArrayBuffer, parserCursor);
            int pos2 = parserCursor.getPos();
            int indexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
            if (indexOf >= 0) {
                String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, indexOf);
                parserCursor.updatePos(indexOf);
                skipWhitespace(charArrayBuffer, parserCursor);
                int pos3 = parserCursor.getPos();
                int indexOf2 = charArrayBuffer.indexOf(32, pos3, upperBound);
                if (indexOf2 >= 0) {
                    String substringTrimmed2 = charArrayBuffer.substringTrimmed(pos3, indexOf2);
                    parserCursor.updatePos(indexOf2);
                    ProtocolVersion parseProtocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
                    skipWhitespace(charArrayBuffer, parserCursor);
                    if (parserCursor.atEnd()) {
                        return createRequestLine(substringTrimmed, substringTrimmed2, parseProtocolVersion);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(charArrayBuffer.substring(pos, upperBound));
                    throw new ParseException(sb.toString());
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(sb2.toString());
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb3.toString());
        } catch (IndexOutOfBoundsException unused) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb4.toString());
        }
    }

    /* access modifiers changed from: protected */
    public RequestLine createRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(str, str2, protocolVersion);
    }

    public static StatusLine parseStatusLine(String str, LineParser lineParser) throws ParseException {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseStatusLine(charArrayBuffer, parserCursor);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r9 = java.lang.Integer.parseInt(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        if (r3 >= r1) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r3 = r8.substringTrimmed(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0064, code lost:
        r3 = "";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006a, code lost:
        return createStatusLine(r2, r9, r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.StatusLine parseStatusLine(org.apache.http.util.CharArrayBuffer r8, org.apache.http.message.ParserCursor r9) throws org.apache.http.ParseException {
        /*
            r7 = this;
            java.lang.String r0 = "Char array buffer"
            org.apache.http.util.Args.notNull(r8, r0)
            java.lang.String r0 = "Parser cursor"
            org.apache.http.util.Args.notNull(r9, r0)
            int r0 = r9.getPos()
            int r1 = r9.getUpperBound()
            org.apache.http.ProtocolVersion r2 = r7.parseProtocolVersion(r8, r9)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r7.skipWhitespace(r8, r9)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            int r9 = r9.getPos()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r3 = 32
            int r3 = r8.indexOf(r3, r9, r1)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            if (r3 >= 0) goto L_0x0026
            r3 = r1
        L_0x0026:
            java.lang.String r9 = r8.substringTrimmed(r9, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r4 = 0
        L_0x002b:
            int r5 = r9.length()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.String r6 = "Status line contains invalid status code: "
            if (r4 >= r5) goto L_0x0059
            char r5 = r9.charAt(r4)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            boolean r5 = java.lang.Character.isDigit(r5)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            if (r5 == 0) goto L_0x0040
            int r4 = r4 + 1
            goto L_0x002b
        L_0x0040:
            org.apache.http.ParseException r9 = new org.apache.http.ParseException     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.append(r6)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.String r3 = r8.substring(r0, r1)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.append(r3)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.String r2 = r2.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r9.<init>(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            throw r9     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
        L_0x0059:
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ NumberFormatException -> 0x006b }
            if (r3 >= r1) goto L_0x0064
            java.lang.String r3 = r8.substringTrimmed(r3, r1)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            goto L_0x0066
        L_0x0064:
            java.lang.String r3 = ""
        L_0x0066:
            org.apache.http.StatusLine r8 = r7.createStatusLine(r2, r9, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            return r8
        L_0x006b:
            org.apache.http.ParseException r9 = new org.apache.http.ParseException     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.append(r6)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.String r3 = r8.substring(r0, r1)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r2.append(r3)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            java.lang.String r2 = r2.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            r9.<init>(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
            throw r9     // Catch:{ IndexOutOfBoundsException -> 0x0084 }
        L_0x0084:
            org.apache.http.ParseException r9 = new org.apache.http.ParseException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Invalid status line: "
            r2.append(r3)
            java.lang.String r8 = r8.substring(r0, r1)
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            r9.<init>(r8)
            goto L_0x00a0
        L_0x009f:
            throw r9
        L_0x00a0:
            goto L_0x009f
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.BasicLineParser.parseStatusLine(org.apache.http.util.CharArrayBuffer, org.apache.http.message.ParserCursor):org.apache.http.StatusLine");
    }

    /* access modifiers changed from: protected */
    public StatusLine createStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        return new BasicStatusLine(protocolVersion, i, str);
    }

    public static Header parseHeader(String str, LineParser lineParser) throws ParseException {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseHeader(charArrayBuffer);
    }

    public Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        while (pos < upperBound && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
            pos++;
        }
        parserCursor.updatePos(pos);
    }
}
