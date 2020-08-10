package com.rometools.rome.p052io.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.rometools.rome.io.impl.XmlFixerReader */
public class XmlFixerReader extends Reader {
    private static Map<String, String> CODED_ENTITIES = new HashMap();
    private static Pattern ENTITIES_PATTERN = Pattern.compile("&[A-Za-z^#]+;");
    private final StringBuffer buffer;
    private int bufferPos;
    private boolean cdata = false;

    /* renamed from: in */
    protected Reader f2362in;
    private int state = 0;
    private boolean trimmed;

    public boolean markSupported() {
        return false;
    }

    public XmlFixerReader(Reader reader) {
        super(reader);
        this.f2362in = reader;
        this.buffer = new StringBuffer();
        this.state = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x011c, code lost:
        r4 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011d, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x011e, code lost:
        if (r2 != false) goto L_0x0121;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0120, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean trimStream() throws java.io.IOException {
        /*
            r9 = this;
            r0 = 0
            r1 = 1
            r2 = 0
            r3 = 1
        L_0x0004:
            r4 = 4
            r5 = 45
            r6 = -1
            r7 = 3
            switch(r2) {
                case 0: goto L_0x00db;
                case 1: goto L_0x00b4;
                case 2: goto L_0x0094;
                case 3: goto L_0x0076;
                case 4: goto L_0x0056;
                case 5: goto L_0x0035;
                case 6: goto L_0x0014;
                default: goto L_0x000c;
            }
        L_0x000c:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "It shouldn't happen"
            r0.<init>(r1)
            throw r0
        L_0x0014:
            java.io.Reader r5 = r9.f2362in
            int r5 = r5.read()
            if (r5 != r6) goto L_0x0020
            r9.state = r7
            goto L_0x00be
        L_0x0020:
            r2 = 62
            if (r5 == r2) goto L_0x002c
            java.lang.StringBuffer r2 = r9.buffer
            char r5 = (char) r5
            r2.append(r5)
            goto L_0x011d
        L_0x002c:
            java.lang.StringBuffer r2 = r9.buffer
            r2.setLength(r0)
            r2 = 1
            r4 = 0
            goto L_0x011e
        L_0x0035:
            java.io.Reader r8 = r9.f2362in
            int r8 = r8.read()
            if (r8 != r6) goto L_0x0041
            r9.state = r7
            goto L_0x00be
        L_0x0041:
            if (r8 == r5) goto L_0x004b
            java.lang.StringBuffer r2 = r9.buffer
            char r5 = (char) r8
            r2.append(r5)
            goto L_0x011d
        L_0x004b:
            java.lang.StringBuffer r2 = r9.buffer
            char r4 = (char) r8
            r2.append(r4)
            r2 = 6
            r2 = 1
            r4 = 6
            goto L_0x011e
        L_0x0056:
            java.io.Reader r4 = r9.f2362in
            int r4 = r4.read()
            if (r4 != r6) goto L_0x0061
            r9.state = r7
            goto L_0x00be
        L_0x0061:
            if (r4 == r5) goto L_0x006b
            java.lang.StringBuffer r5 = r9.buffer
            char r4 = (char) r4
            r5.append(r4)
            goto L_0x011c
        L_0x006b:
            java.lang.StringBuffer r2 = r9.buffer
            char r4 = (char) r4
            r2.append(r4)
            r2 = 5
            r2 = 1
            r4 = 5
            goto L_0x011e
        L_0x0076:
            java.io.Reader r8 = r9.f2362in
            int r8 = r8.read()
            if (r8 != r6) goto L_0x0081
            r9.state = r7
            goto L_0x00be
        L_0x0081:
            if (r8 != r5) goto L_0x008b
            java.lang.StringBuffer r2 = r9.buffer
            char r5 = (char) r8
            r2.append(r5)
            goto L_0x011d
        L_0x008b:
            java.lang.StringBuffer r3 = r9.buffer
            char r4 = (char) r8
            r3.append(r4)
            r9.state = r7
            goto L_0x00be
        L_0x0094:
            java.io.Reader r4 = r9.f2362in
            int r4 = r4.read()
            if (r4 != r6) goto L_0x009f
            r9.state = r7
            goto L_0x00be
        L_0x009f:
            if (r4 != r5) goto L_0x00ab
            java.lang.StringBuffer r2 = r9.buffer
            char r4 = (char) r4
            r2.append(r4)
            r2 = 1
            r4 = 3
            goto L_0x011e
        L_0x00ab:
            java.lang.StringBuffer r3 = r9.buffer
            char r4 = (char) r4
            r3.append(r4)
            r9.state = r7
            goto L_0x00be
        L_0x00b4:
            java.io.Reader r4 = r9.f2362in
            int r4 = r4.read()
            if (r4 != r6) goto L_0x00c2
            r9.state = r7
        L_0x00be:
            r4 = r2
            r2 = 0
            r3 = 1
            goto L_0x011e
        L_0x00c2:
            r5 = 33
            if (r4 == r5) goto L_0x00d1
            java.lang.StringBuffer r3 = r9.buffer
            char r4 = (char) r4
            r3.append(r4)
            r9.state = r7
            r9.state = r7
            goto L_0x00be
        L_0x00d1:
            java.lang.StringBuffer r2 = r9.buffer
            char r4 = (char) r4
            r2.append(r4)
            r2 = 2
            r2 = 1
            r4 = 2
            goto L_0x011e
        L_0x00db:
            java.io.Reader r4 = r9.f2362in
            int r4 = r4.read()
            if (r4 != r6) goto L_0x00e7
            r4 = r2
            r2 = 0
            r3 = 0
            goto L_0x011e
        L_0x00e7:
            r5 = 32
            if (r4 == r5) goto L_0x011c
            r5 = 10
            if (r4 == r5) goto L_0x011c
            r5 = 13
            if (r4 == r5) goto L_0x011c
            r5 = 9
            if (r4 != r5) goto L_0x00f8
            goto L_0x011c
        L_0x00f8:
            r5 = 60
            if (r4 != r5) goto L_0x010c
            java.lang.StringBuffer r2 = r9.buffer
            r2.setLength(r0)
            r9.bufferPos = r0
            java.lang.StringBuffer r2 = r9.buffer
            char r4 = (char) r4
            r2.append(r4)
            r2 = 1
            r4 = 1
            goto L_0x011e
        L_0x010c:
            java.lang.StringBuffer r3 = r9.buffer
            r3.setLength(r0)
            r9.bufferPos = r0
            java.lang.StringBuffer r3 = r9.buffer
            char r4 = (char) r4
            r3.append(r4)
            r9.state = r7
            goto L_0x00be
        L_0x011c:
            r4 = r2
        L_0x011d:
            r2 = 1
        L_0x011e:
            if (r2 != 0) goto L_0x0121
            return r3
        L_0x0121:
            r2 = r4
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rometools.rome.p052io.impl.XmlFixerReader.trimStream():boolean");
    }

    public int read() throws IOException {
        boolean z;
        int i;
        int i2;
        if (!this.trimmed) {
            this.trimmed = true;
            if (!trimStream()) {
                return -1;
            }
        }
        do {
            int i3 = this.state;
            z = false;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 == 2) {
                        String str = (String) CODED_ENTITIES.get(this.buffer.toString());
                        if (str != null) {
                            this.buffer.setLength(0);
                            this.buffer.append(str);
                        }
                        this.state = 3;
                    } else if (i3 != 3) {
                        if (i3 == 4) {
                            i2 = this.f2362in.read();
                            this.state = 3;
                            if (i2 != -1) {
                                if (i2 == 32 || i2 == 47 || i2 == 62) {
                                    this.buffer.append((char) i2);
                                } else if (i2 != 91) {
                                    this.state = 4;
                                    this.buffer.append((char) i2);
                                } else {
                                    this.buffer.append((char) i2);
                                    if ("<![CDATA[".equals(this.buffer.toString())) {
                                        this.cdata = true;
                                    } else {
                                        this.state = 4;
                                    }
                                }
                            }
                        } else if (i3 == 5) {
                            i = this.f2362in.read();
                            this.state = 3;
                            if (i != -1) {
                                if (i == 62) {
                                    this.buffer.append((char) i);
                                    if ("]]>".equals(this.buffer.toString())) {
                                        this.cdata = false;
                                    }
                                } else if (i != 93) {
                                    this.buffer.append((char) i);
                                } else {
                                    this.buffer.append((char) i);
                                    this.state = 5;
                                }
                            }
                        } else {
                            throw new IOException("It shouldn't happen");
                        }
                    } else if (this.bufferPos < this.buffer.length()) {
                        StringBuffer stringBuffer = this.buffer;
                        int i4 = this.bufferPos;
                        this.bufferPos = i4 + 1;
                        i = stringBuffer.charAt(i4);
                        continue;
                    } else {
                        this.state = 0;
                    }
                    i = 0;
                } else {
                    i2 = this.f2362in.read();
                    String str2 = "amp;";
                    if (i2 <= -1) {
                        if (!this.cdata) {
                            this.buffer.insert(1, str2);
                        }
                        this.state = 3;
                    } else if (i2 == 59) {
                        this.buffer.append((char) i2);
                        this.state = 2;
                    } else if ((i2 < 97 || i2 > 122) && ((i2 < 65 || i2 > 90) && i2 != 35 && (i2 < 48 || i2 > 57))) {
                        if (!this.cdata) {
                            this.buffer.insert(1, str2);
                        }
                        this.buffer.append((char) i2);
                        this.state = 3;
                    } else {
                        this.buffer.append((char) i2);
                    }
                }
                i = i2;
            } else {
                i = this.f2362in.read();
                if (i <= -1) {
                    continue;
                } else if (i == 38) {
                    this.state = 1;
                    this.buffer.setLength(0);
                    this.bufferPos = 0;
                    this.buffer.append((char) i);
                } else if (i == 60) {
                    this.state = 4;
                    this.buffer.setLength(0);
                    this.bufferPos = 0;
                    this.buffer.append((char) i);
                } else if (i == 93 && this.cdata) {
                    this.state = 5;
                    this.buffer.setLength(0);
                    this.bufferPos = 0;
                    this.buffer.append((char) i);
                }
            }
            z = true;
            continue;
        } while (z);
        return i;
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        int read = read();
        if (read == -1) {
            return -1;
        }
        cArr[i + 0] = (char) read;
        int i3 = 1;
        while (i3 < i2) {
            int read2 = read();
            if (read2 <= -1) {
                break;
            }
            int i4 = i3 + 1;
            cArr[i3 + i] = (char) read2;
            i3 = i4;
        }
        return i3;
    }

    public long skip(long j) throws IOException {
        if (j == 0) {
            return 0;
        }
        if (j >= 0) {
            int read = read();
            long j2 = 1;
            while (read > -1 && j2 < j) {
                read = read();
                j2++;
            }
            return j2;
        }
        throw new IllegalArgumentException("'n' cannot be negative");
    }

    public boolean ready() throws IOException {
        return this.state != 0 || this.f2362in.ready();
    }

    public void mark(int i) throws IOException {
        throw new IOException("Stream does not support mark");
    }

    public void reset() throws IOException {
        throw new IOException("Stream does not support mark");
    }

    public void close() throws IOException {
        this.f2362in.close();
    }

    static {
        CODED_ENTITIES.put("&nbsp;", "&#160;");
        CODED_ENTITIES.put("&iexcl;", "&#161;");
        CODED_ENTITIES.put("&cent;", "&#162;");
        CODED_ENTITIES.put("&pound;", "&#163;");
        CODED_ENTITIES.put("&curren;", "&#164;");
        CODED_ENTITIES.put("&yen;", "&#165;");
        CODED_ENTITIES.put("&brvbar;", "&#166;");
        CODED_ENTITIES.put("&sect;", "&#167;");
        CODED_ENTITIES.put("&uml;", "&#168;");
        CODED_ENTITIES.put("&copy;", "&#169;");
        CODED_ENTITIES.put("&ordf;", "&#170;");
        CODED_ENTITIES.put("&laquo;", "&#171;");
        CODED_ENTITIES.put("&not;", "&#172;");
        CODED_ENTITIES.put("&shy;", "&#173;");
        CODED_ENTITIES.put("&reg;", "&#174;");
        CODED_ENTITIES.put("&macr;", "&#175;");
        CODED_ENTITIES.put("&deg;", "&#176;");
        CODED_ENTITIES.put("&plusmn;", "&#177;");
        CODED_ENTITIES.put("&sup2;", "&#178;");
        CODED_ENTITIES.put("&sup3;", "&#179;");
        CODED_ENTITIES.put("&acute;", "&#180;");
        CODED_ENTITIES.put("&micro;", "&#181;");
        CODED_ENTITIES.put("&para;", "&#182;");
        CODED_ENTITIES.put("&middot;", "&#183;");
        CODED_ENTITIES.put("&cedil;", "&#184;");
        CODED_ENTITIES.put("&sup1;", "&#185;");
        CODED_ENTITIES.put("&ordm;", "&#186;");
        CODED_ENTITIES.put("&raquo;", "&#187;");
        CODED_ENTITIES.put("&frac14;", "&#188;");
        CODED_ENTITIES.put("&frac12;", "&#189;");
        CODED_ENTITIES.put("&frac34;", "&#190;");
        CODED_ENTITIES.put("&iquest;", "&#191;");
        CODED_ENTITIES.put("&Agrave;", "&#192;");
        CODED_ENTITIES.put("&Aacute;", "&#193;");
        CODED_ENTITIES.put("&Acirc;", "&#194;");
        CODED_ENTITIES.put("&Atilde;", "&#195;");
        CODED_ENTITIES.put("&Auml;", "&#196;");
        CODED_ENTITIES.put("&Aring;", "&#197;");
        CODED_ENTITIES.put("&AElig;", "&#198;");
        CODED_ENTITIES.put("&Ccedil;", "&#199;");
        CODED_ENTITIES.put("&Egrave;", "&#200;");
        CODED_ENTITIES.put("&Eacute;", "&#201;");
        CODED_ENTITIES.put("&Ecirc;", "&#202;");
        CODED_ENTITIES.put("&Euml;", "&#203;");
        CODED_ENTITIES.put("&Igrave;", "&#204;");
        CODED_ENTITIES.put("&Iacute;", "&#205;");
        CODED_ENTITIES.put("&Icirc;", "&#206;");
        CODED_ENTITIES.put("&Iuml;", "&#207;");
        CODED_ENTITIES.put("&ETH;", "&#208;");
        CODED_ENTITIES.put("&Ntilde;", "&#209;");
        CODED_ENTITIES.put("&Ograve;", "&#210;");
        CODED_ENTITIES.put("&Oacute;", "&#211;");
        CODED_ENTITIES.put("&Ocirc;", "&#212;");
        CODED_ENTITIES.put("&Otilde;", "&#213;");
        CODED_ENTITIES.put("&Ouml;", "&#214;");
        CODED_ENTITIES.put("&times;", "&#215;");
        CODED_ENTITIES.put("&Oslash;", "&#216;");
        CODED_ENTITIES.put("&Ugrave;", "&#217;");
        CODED_ENTITIES.put("&Uacute;", "&#218;");
        CODED_ENTITIES.put("&Ucirc;", "&#219;");
        CODED_ENTITIES.put("&Uuml;", "&#220;");
        CODED_ENTITIES.put("&Yacute;", "&#221;");
        CODED_ENTITIES.put("&THORN;", "&#222;");
        CODED_ENTITIES.put("&szlig;", "&#223;");
        CODED_ENTITIES.put("&agrave;", "&#224;");
        CODED_ENTITIES.put("&aacute;", "&#225;");
        CODED_ENTITIES.put("&acirc;", "&#226;");
        CODED_ENTITIES.put("&atilde;", "&#227;");
        CODED_ENTITIES.put("&auml;", "&#228;");
        CODED_ENTITIES.put("&aring;", "&#229;");
        CODED_ENTITIES.put("&aelig;", "&#230;");
        CODED_ENTITIES.put("&ccedil;", "&#231;");
        CODED_ENTITIES.put("&egrave;", "&#232;");
        CODED_ENTITIES.put("&eacute;", "&#233;");
        CODED_ENTITIES.put("&ecirc;", "&#234;");
        CODED_ENTITIES.put("&euml;", "&#235;");
        CODED_ENTITIES.put("&igrave;", "&#236;");
        CODED_ENTITIES.put("&iacute;", "&#237;");
        CODED_ENTITIES.put("&icirc;", "&#238;");
        CODED_ENTITIES.put("&iuml;", "&#239;");
        CODED_ENTITIES.put("&eth;", "&#240;");
        CODED_ENTITIES.put("&ntilde;", "&#241;");
        CODED_ENTITIES.put("&ograve;", "&#242;");
        CODED_ENTITIES.put("&oacute;", "&#243;");
        CODED_ENTITIES.put("&ocirc;", "&#244;");
        CODED_ENTITIES.put("&otilde;", "&#245;");
        CODED_ENTITIES.put("&ouml;", "&#246;");
        CODED_ENTITIES.put("&divide;", "&#247;");
        CODED_ENTITIES.put("&oslash;", "&#248;");
        CODED_ENTITIES.put("&ugrave;", "&#249;");
        CODED_ENTITIES.put("&uacute;", "&#250;");
        CODED_ENTITIES.put("&ucirc;", "&#251;");
        CODED_ENTITIES.put("&uuml;", "&#252;");
        CODED_ENTITIES.put("&yacute;", "&#253;");
        CODED_ENTITIES.put("&thorn;", "&#254;");
        CODED_ENTITIES.put("&yuml;", "&#255;");
        CODED_ENTITIES.put("&fnof;", "&#402;");
        CODED_ENTITIES.put("&Alpha;", "&#913;");
        CODED_ENTITIES.put("&Beta;", "&#914;");
        CODED_ENTITIES.put("&Gamma;", "&#915;");
        CODED_ENTITIES.put("&Delta;", "&#916;");
        CODED_ENTITIES.put("&Epsilon;", "&#917;");
        CODED_ENTITIES.put("&Zeta;", "&#918;");
        CODED_ENTITIES.put("&Eta;", "&#919;");
        CODED_ENTITIES.put("&Theta;", "&#920;");
        CODED_ENTITIES.put("&Iota;", "&#921;");
        CODED_ENTITIES.put("&Kappa;", "&#922;");
        CODED_ENTITIES.put("&Lambda;", "&#923;");
        CODED_ENTITIES.put("&Mu;", "&#924;");
        CODED_ENTITIES.put("&Nu;", "&#925;");
        CODED_ENTITIES.put("&Xi;", "&#926;");
        CODED_ENTITIES.put("&Omicron;", "&#927;");
        CODED_ENTITIES.put("&Pi;", "&#928;");
        CODED_ENTITIES.put("&Rho;", "&#929;");
        CODED_ENTITIES.put("&Sigma;", "&#931;");
        CODED_ENTITIES.put("&Tau;", "&#932;");
        CODED_ENTITIES.put("&Upsilon;", "&#933;");
        CODED_ENTITIES.put("&Phi;", "&#934;");
        CODED_ENTITIES.put("&Chi;", "&#935;");
        CODED_ENTITIES.put("&Psi;", "&#936;");
        CODED_ENTITIES.put("&Omega;", "&#937;");
        CODED_ENTITIES.put("&alpha;", "&#945;");
        CODED_ENTITIES.put("&beta;", "&#946;");
        CODED_ENTITIES.put("&gamma;", "&#947;");
        CODED_ENTITIES.put("&delta;", "&#948;");
        CODED_ENTITIES.put("&epsilon;", "&#949;");
        CODED_ENTITIES.put("&zeta;", "&#950;");
        CODED_ENTITIES.put("&eta;", "&#951;");
        CODED_ENTITIES.put("&theta;", "&#952;");
        CODED_ENTITIES.put("&iota;", "&#953;");
        CODED_ENTITIES.put("&kappa;", "&#954;");
        CODED_ENTITIES.put("&lambda;", "&#955;");
        CODED_ENTITIES.put("&mu;", "&#956;");
        CODED_ENTITIES.put("&nu;", "&#957;");
        CODED_ENTITIES.put("&xi;", "&#958;");
        CODED_ENTITIES.put("&omicron;", "&#959;");
        CODED_ENTITIES.put("&pi;", "&#960;");
        CODED_ENTITIES.put("&rho;", "&#961;");
        CODED_ENTITIES.put("&sigmaf;", "&#962;");
        CODED_ENTITIES.put("&sigma;", "&#963;");
        CODED_ENTITIES.put("&tau;", "&#964;");
        CODED_ENTITIES.put("&upsilon;", "&#965;");
        CODED_ENTITIES.put("&phi;", "&#966;");
        CODED_ENTITIES.put("&chi;", "&#967;");
        CODED_ENTITIES.put("&psi;", "&#968;");
        CODED_ENTITIES.put("&omega;", "&#969;");
        CODED_ENTITIES.put("&thetasym;", "&#977;");
        CODED_ENTITIES.put("&upsih;", "&#978;");
        CODED_ENTITIES.put("&piv;", "&#982;");
        CODED_ENTITIES.put("&bull;", "&#8226;");
        CODED_ENTITIES.put("&hellip;", "&#8230;");
        CODED_ENTITIES.put("&prime;", "&#8242;");
        CODED_ENTITIES.put("&Prime;", "&#8243;");
        CODED_ENTITIES.put("&oline;", "&#8254;");
        CODED_ENTITIES.put("&frasl;", "&#8260;");
        CODED_ENTITIES.put("&weierp;", "&#8472;");
        CODED_ENTITIES.put("&image;", "&#8465;");
        CODED_ENTITIES.put("&real;", "&#8476;");
        CODED_ENTITIES.put("&trade;", "&#8482;");
        CODED_ENTITIES.put("&alefsym;", "&#8501;");
        CODED_ENTITIES.put("&larr;", "&#8592;");
        CODED_ENTITIES.put("&uarr;", "&#8593;");
        CODED_ENTITIES.put("&rarr;", "&#8594;");
        CODED_ENTITIES.put("&darr;", "&#8595;");
        CODED_ENTITIES.put("&harr;", "&#8596;");
        CODED_ENTITIES.put("&crarr;", "&#8629;");
        CODED_ENTITIES.put("&lArr;", "&#8656;");
        CODED_ENTITIES.put("&uArr;", "&#8657;");
        CODED_ENTITIES.put("&rArr;", "&#8658;");
        CODED_ENTITIES.put("&dArr;", "&#8659;");
        CODED_ENTITIES.put("&hArr;", "&#8660;");
        CODED_ENTITIES.put("&forall;", "&#8704;");
        CODED_ENTITIES.put("&part;", "&#8706;");
        CODED_ENTITIES.put("&exist;", "&#8707;");
        CODED_ENTITIES.put("&empty;", "&#8709;");
        CODED_ENTITIES.put("&nabla;", "&#8711;");
        CODED_ENTITIES.put("&isin;", "&#8712;");
        CODED_ENTITIES.put("&notin;", "&#8713;");
        CODED_ENTITIES.put("&ni;", "&#8715;");
        CODED_ENTITIES.put("&prod;", "&#8719;");
        CODED_ENTITIES.put("&sum;", "&#8721;");
        CODED_ENTITIES.put("&minus;", "&#8722;");
        CODED_ENTITIES.put("&lowast;", "&#8727;");
        CODED_ENTITIES.put("&radic;", "&#8730;");
        CODED_ENTITIES.put("&prop;", "&#8733;");
        CODED_ENTITIES.put("&infin;", "&#8734;");
        CODED_ENTITIES.put("&ang;", "&#8736;");
        CODED_ENTITIES.put("&and;", "&#8743;");
        CODED_ENTITIES.put("&or;", "&#8744;");
        CODED_ENTITIES.put("&cap;", "&#8745;");
        CODED_ENTITIES.put("&cup;", "&#8746;");
        CODED_ENTITIES.put("&int;", "&#8747;");
        CODED_ENTITIES.put("&there4;", "&#8756;");
        CODED_ENTITIES.put("&sim;", "&#8764;");
        CODED_ENTITIES.put("&cong;", "&#8773;");
        CODED_ENTITIES.put("&asymp;", "&#8776;");
        CODED_ENTITIES.put("&ne;", "&#8800;");
        CODED_ENTITIES.put("&equiv;", "&#8801;");
        CODED_ENTITIES.put("&le;", "&#8804;");
        CODED_ENTITIES.put("&ge;", "&#8805;");
        CODED_ENTITIES.put("&sub;", "&#8834;");
        CODED_ENTITIES.put("&sup;", "&#8835;");
        CODED_ENTITIES.put("&nsub;", "&#8836;");
        CODED_ENTITIES.put("&sube;", "&#8838;");
        CODED_ENTITIES.put("&supe;", "&#8839;");
        CODED_ENTITIES.put("&oplus;", "&#8853;");
        CODED_ENTITIES.put("&otimes;", "&#8855;");
        CODED_ENTITIES.put("&perp;", "&#8869;");
        CODED_ENTITIES.put("&sdot;", "&#8901;");
        CODED_ENTITIES.put("&lceil;", "&#8968;");
        CODED_ENTITIES.put("&rceil;", "&#8969;");
        CODED_ENTITIES.put("&lfloor;", "&#8970;");
        CODED_ENTITIES.put("&rfloor;", "&#8971;");
        CODED_ENTITIES.put("&lang;", "&#9001;");
        CODED_ENTITIES.put("&rang;", "&#9002;");
        CODED_ENTITIES.put("&loz;", "&#9674;");
        CODED_ENTITIES.put("&spades;", "&#9824;");
        CODED_ENTITIES.put("&clubs;", "&#9827;");
        CODED_ENTITIES.put("&hearts;", "&#9829;");
        CODED_ENTITIES.put("&diams;", "&#9830;");
        CODED_ENTITIES.put("&quot;", "&#34;");
        CODED_ENTITIES.put("&amp;", "&#38;");
        CODED_ENTITIES.put("&lt;", "&#60;");
        CODED_ENTITIES.put("&gt;", "&#62;");
        CODED_ENTITIES.put("&OElig;", "&#338;");
        CODED_ENTITIES.put("&oelig;", "&#339;");
        CODED_ENTITIES.put("&Scaron;", "&#352;");
        CODED_ENTITIES.put("&scaron;", "&#353;");
        CODED_ENTITIES.put("&Yuml;", "&#376;");
        CODED_ENTITIES.put("&circ;", "&#710;");
        CODED_ENTITIES.put("&tilde;", "&#732;");
        CODED_ENTITIES.put("&ensp;", "&#8194;");
        CODED_ENTITIES.put("&emsp;", "&#8195;");
        CODED_ENTITIES.put("&thinsp;", "&#8201;");
        CODED_ENTITIES.put("&zwnj;", "&#8204;");
        CODED_ENTITIES.put("&zwj;", "&#8205;");
        CODED_ENTITIES.put("&lrm;", "&#8206;");
        CODED_ENTITIES.put("&rlm;", "&#8207;");
        CODED_ENTITIES.put("&ndash;", "&#8211;");
        CODED_ENTITIES.put("&mdash;", "&#8212;");
        CODED_ENTITIES.put("&lsquo;", "&#8216;");
        CODED_ENTITIES.put("&rsquo;", "&#8217;");
        CODED_ENTITIES.put("&sbquo;", "&#8218;");
        CODED_ENTITIES.put("&ldquo;", "&#8220;");
        CODED_ENTITIES.put("&rdquo;", "&#8221;");
        CODED_ENTITIES.put("&bdquo;", "&#8222;");
        CODED_ENTITIES.put("&dagger;", "&#8224;");
        CODED_ENTITIES.put("&Dagger;", "&#8225;");
        CODED_ENTITIES.put("&permil;", "&#8240;");
        CODED_ENTITIES.put("&lsaquo;", "&#8249;");
        CODED_ENTITIES.put("&rsaquo;", "&#8250;");
        CODED_ENTITIES.put("&euro;", "&#8364;");
    }

    public String processHtmlEntities(String str) {
        if (str.indexOf(38) == -1) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int i = 0;
        while (i < str.length()) {
            String substring = str.substring(i);
            Matcher matcher = ENTITIES_PATTERN.matcher(substring);
            if (matcher.find()) {
                int start = matcher.start() + i;
                int end = matcher.end() + i;
                if (start > i) {
                    stringBuffer.append(str.substring(i, start));
                    i = start;
                }
                String substring2 = str.substring(i, end);
                String str2 = (String) CODED_ENTITIES.get(substring2);
                if (str2 != null) {
                    substring2 = str2;
                }
                stringBuffer.append(substring2);
                i = end;
            } else {
                stringBuffer.append(substring);
                i += substring.length();
            }
        }
        return stringBuffer.toString();
    }
}
