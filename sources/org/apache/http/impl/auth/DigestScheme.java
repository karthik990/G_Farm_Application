package org.apache.http.impl.auth;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class DigestScheme extends RFC2617Scheme {
    private static final char[] HEXADECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;
    private static final long serialVersionUID = 3883908186234566916L;

    /* renamed from: a1 */
    private String f4528a1;

    /* renamed from: a2 */
    private String f4529a2;
    private String cnonce;
    private boolean complete;
    private String lastNonce;
    private long nounceCount;

    public String getSchemeName() {
        return "digest";
    }

    public boolean isConnectionBased() {
        return false;
    }

    public DigestScheme(Charset charset) {
        super(charset);
        this.complete = false;
    }

    @Deprecated
    public DigestScheme(ChallengeState challengeState) {
        super(challengeState);
    }

    public DigestScheme() {
        this(Consts.ASCII);
    }

    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
        if (getParameters().isEmpty()) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
    }

    public boolean isComplete() {
        if ("true".equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    public void overrideParamter(String str, String str2) {
        getParameters().put(str, str2);
    }

    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return authenticate(credentials, httpRequest, new BasicHttpContext());
    }

    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        if (getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        } else if (getParameter("nonce") != null) {
            getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
            getParameters().put("uri", httpRequest.getRequestLine().getUri());
            String str = p043io.fabric.sdk.android.services.network.HttpRequest.PARAM_CHARSET;
            if (getParameter(str) == null) {
                getParameters().put(str, getCredentialsCharset(httpRequest));
            }
            return createDigestHeader(credentials, httpRequest);
        } else {
            throw new AuthenticationException("missing nonce in challenge");
        }
    }

    private static MessageDigest createMessageDigest(String str) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(str);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported algorithm in HTTP Digest authentication: ");
            sb.append(str);
            throw new UnsupportedDigestAlgorithmException(sb.toString());
        }
    }

    private Header createDigestHeader(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        String str;
        char c;
        String str2;
        String str3;
        String str4;
        String str5;
        HttpRequest httpRequest2 = httpRequest;
        String str6 = "uri";
        String parameter = getParameter(str6);
        String str7 = "realm";
        String parameter2 = getParameter(str7);
        String str8 = "nonce";
        String parameter3 = getParameter(str8);
        String str9 = "opaque";
        String parameter4 = getParameter(str9);
        String parameter5 = getParameter("methodname");
        String str10 = "algorithm";
        String parameter6 = getParameter(str10);
        String str11 = MessageDigestAlgorithms.MD5;
        if (parameter6 == null) {
            parameter6 = str11;
        }
        HashSet hashSet = new HashSet(8);
        String str12 = "qop";
        String str13 = str11;
        String parameter7 = getParameter(str12);
        String str14 = str9;
        String str15 = "auth-int";
        String str16 = parameter4;
        String str17 = "auth";
        String str18 = str10;
        if (parameter7 != null) {
            str = str12;
            StringTokenizer stringTokenizer = new StringTokenizer(parameter7, ",");
            while (stringTokenizer.hasMoreTokens()) {
                StringTokenizer stringTokenizer2 = stringTokenizer;
                hashSet.add(stringTokenizer.nextToken().trim().toLowerCase(Locale.ROOT));
                stringTokenizer = stringTokenizer2;
            }
            c = (!(httpRequest2 instanceof HttpEntityEnclosingRequest) || !hashSet.contains(str15)) ? hashSet.contains(str17) ? (char) 2 : 65535 : 1;
        } else {
            str = str12;
            c = 0;
        }
        if (c != 65535) {
            String parameter8 = getParameter(p043io.fabric.sdk.android.services.network.HttpRequest.PARAM_CHARSET);
            if (parameter8 == null) {
                parameter8 = "ISO-8859-1";
            }
            String str19 = parameter6.equalsIgnoreCase("MD5-sess") ? str13 : parameter6;
            try {
                MessageDigest createMessageDigest = createMessageDigest(str19);
                String str20 = str15;
                String name = credentials.getUserPrincipal().getName();
                String str21 = str6;
                String password = credentials.getPassword();
                String str22 = str8;
                String str23 = str7;
                if (parameter3.equals(this.lastNonce)) {
                    str2 = parameter2;
                    this.nounceCount++;
                } else {
                    str2 = parameter2;
                    this.nounceCount = 1;
                    this.cnonce = null;
                    this.lastNonce = parameter3;
                }
                StringBuilder sb = new StringBuilder(256);
                String str24 = str17;
                Formatter formatter = new Formatter(sb, Locale.US);
                HashSet hashSet2 = hashSet;
                String str25 = parameter5;
                formatter.format("%08x", new Object[]{Long.valueOf(this.nounceCount)});
                formatter.close();
                String sb2 = sb.toString();
                if (this.cnonce == null) {
                    this.cnonce = createCnonce();
                }
                this.f4528a1 = null;
                this.f4529a2 = null;
                if (parameter6.equalsIgnoreCase("MD5-sess")) {
                    sb.setLength(0);
                    sb.append(name);
                    sb.append(':');
                    sb.append(str2);
                    sb.append(':');
                    sb.append(password);
                    String encode = encode(createMessageDigest.digest(EncodingUtils.getBytes(sb.toString(), parameter8)));
                    sb.setLength(0);
                    sb.append(encode);
                    sb.append(':');
                    sb.append(parameter3);
                    sb.append(':');
                    sb.append(this.cnonce);
                    this.f4528a1 = sb.toString();
                } else {
                    sb.setLength(0);
                    sb.append(name);
                    sb.append(':');
                    sb.append(str2);
                    sb.append(':');
                    sb.append(password);
                    this.f4528a1 = sb.toString();
                }
                String encode2 = encode(createMessageDigest.digest(EncodingUtils.getBytes(this.f4528a1, parameter8)));
                if (c == 2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str25);
                    sb3.append(':');
                    sb3.append(parameter);
                    this.f4529a2 = sb3.toString();
                    str3 = str24;
                } else {
                    String str26 = str25;
                    if (c == 1) {
                        HttpEntity entity = httpRequest2 instanceof HttpEntityEnclosingRequest ? ((HttpEntityEnclosingRequest) httpRequest2).getEntity() : null;
                        if (entity == null || entity.isRepeatable()) {
                            str3 = str24;
                            HttpEntityDigester httpEntityDigester = new HttpEntityDigester(createMessageDigest);
                            if (entity != null) {
                                try {
                                    entity.writeTo(httpEntityDigester);
                                } catch (IOException e) {
                                    throw new AuthenticationException("I/O error reading entity content", e);
                                }
                            }
                            httpEntityDigester.close();
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str26);
                            sb4.append(':');
                            sb4.append(parameter);
                            sb4.append(':');
                            sb4.append(encode(httpEntityDigester.getDigest()));
                            this.f4529a2 = sb4.toString();
                        } else {
                            str3 = str24;
                            if (hashSet2.contains(str3)) {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(str26);
                                sb5.append(':');
                                sb5.append(parameter);
                                this.f4529a2 = sb5.toString();
                                c = 2;
                            } else {
                                throw new AuthenticationException("Qop auth-int cannot be used with a non-repeatable entity");
                            }
                        }
                    } else {
                        str3 = str24;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str26);
                        sb6.append(':');
                        sb6.append(parameter);
                        this.f4529a2 = sb6.toString();
                    }
                }
                String encode3 = encode(createMessageDigest.digest(EncodingUtils.getBytes(this.f4529a2, parameter8)));
                if (c == 0) {
                    sb.setLength(0);
                    sb.append(encode2);
                    sb.append(':');
                    sb.append(parameter3);
                    sb.append(':');
                    sb.append(encode3);
                    str4 = sb.toString();
                } else {
                    sb.setLength(0);
                    sb.append(encode2);
                    sb.append(':');
                    sb.append(parameter3);
                    sb.append(':');
                    sb.append(sb2);
                    sb.append(':');
                    sb.append(this.cnonce);
                    sb.append(':');
                    sb.append(c == 1 ? str20 : str3);
                    sb.append(':');
                    sb.append(encode3);
                    str4 = sb.toString();
                }
                String encode4 = encode(createMessageDigest.digest(EncodingUtils.getAsciiBytes(str4)));
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
                if (isProxy()) {
                    charArrayBuffer.append("Proxy-Authorization");
                } else {
                    charArrayBuffer.append("Authorization");
                }
                charArrayBuffer.append(": Digest ");
                ArrayList arrayList = new ArrayList(20);
                arrayList.add(new BasicNameValuePair("username", name));
                arrayList.add(new BasicNameValuePair(str23, str2));
                arrayList.add(new BasicNameValuePair(str22, parameter3));
                arrayList.add(new BasicNameValuePair(str21, parameter));
                arrayList.add(new BasicNameValuePair("response", encode4));
                if (c != 0) {
                    if (c == 1) {
                        str3 = str20;
                    }
                    str5 = str;
                    arrayList.add(new BasicNameValuePair(str5, str3));
                    arrayList.add(new BasicNameValuePair("nc", sb2));
                    arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
                } else {
                    str5 = str;
                }
                String str27 = str18;
                arrayList.add(new BasicNameValuePair(str27, parameter6));
                if (str16 != null) {
                    arrayList.add(new BasicNameValuePair(str14, str16));
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    BasicNameValuePair basicNameValuePair = (BasicNameValuePair) arrayList.get(i);
                    if (i > 0) {
                        charArrayBuffer.append(", ");
                    }
                    String name2 = basicNameValuePair.getName();
                    BasicHeaderValueFormatter.INSTANCE.formatNameValuePair(charArrayBuffer, (NameValuePair) basicNameValuePair, !("nc".equals(name2) || str5.equals(name2) || str27.equals(name2)));
                }
                return new BufferedHeader(charArrayBuffer);
            } catch (UnsupportedDigestAlgorithmException unused) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Unsuppported digest algorithm: ");
                sb7.append(str19);
                throw new AuthenticationException(sb7.toString());
            }
        } else {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("None of the qop methods is supported: ");
            sb8.append(parameter7);
            throw new AuthenticationException(sb8.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public String getCnonce() {
        return this.cnonce;
    }

    /* access modifiers changed from: 0000 */
    public String getA1() {
        return this.f4528a1;
    }

    /* access modifiers changed from: 0000 */
    public String getA2() {
        return this.f4529a2;
    }

    static String encode(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i] & Ascii.f1875SI;
            int i2 = (bArr[i] & 240) >> 4;
            int i3 = i * 2;
            char[] cArr2 = HEXADECIMAL;
            cArr[i3] = cArr2[i2];
            cArr[i3 + 1] = cArr2[b];
        }
        return new String(cArr);
    }

    public static String createCnonce() {
        byte[] bArr = new byte[8];
        new SecureRandom().nextBytes(bArr);
        return encode(bArr);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DIGEST [complete=");
        sb.append(this.complete);
        sb.append(", nonce=");
        sb.append(this.lastNonce);
        sb.append(", nc=");
        sb.append(this.nounceCount);
        sb.append("]");
        return sb.toString();
    }
}
