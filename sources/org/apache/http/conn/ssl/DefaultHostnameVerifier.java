package org.apache.http.conn.ssl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.conn.util.PublicSuffixMatcher;

public final class DefaultHostnameVerifier implements HostnameVerifier {
    private final Log log;
    private final PublicSuffixMatcher publicSuffixMatcher;

    /* renamed from: org.apache.http.conn.ssl.DefaultHostnameVerifier$1 */
    static /* synthetic */ class C60861 {

        /* renamed from: $SwitchMap$org$apache$http$conn$ssl$DefaultHostnameVerifier$HostNameType */
        static final /* synthetic */ int[] f4525xdc99fdb1 = new int[HostNameType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                org.apache.http.conn.ssl.DefaultHostnameVerifier$HostNameType[] r0 = org.apache.http.conn.ssl.DefaultHostnameVerifier.HostNameType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f4525xdc99fdb1 = r0
                int[] r0 = f4525xdc99fdb1     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.apache.http.conn.ssl.DefaultHostnameVerifier$HostNameType r1 = org.apache.http.conn.ssl.DefaultHostnameVerifier.HostNameType.IPv4     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f4525xdc99fdb1     // Catch:{ NoSuchFieldError -> 0x001f }
                org.apache.http.conn.ssl.DefaultHostnameVerifier$HostNameType r1 = org.apache.http.conn.ssl.DefaultHostnameVerifier.HostNameType.IPv6     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.conn.ssl.DefaultHostnameVerifier.C60861.<clinit>():void");
        }
    }

    enum HostNameType {
        IPv4(7),
        IPv6(7),
        DNS(2);
        
        final int subjectType;

        private HostNameType(int i) {
            this.subjectType = i;
        }
    }

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher2) {
        this.log = LogFactory.getLog(getClass());
        this.publicSuffixMatcher = publicSuffixMatcher2;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    public boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException e) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(e.getMessage(), e);
            }
            return false;
        }
    }

    public void verify(String str, X509Certificate x509Certificate) throws SSLException {
        HostNameType determineHostFormat = determineHostFormat(str);
        List subjectAltNames = getSubjectAltNames(x509Certificate);
        if (subjectAltNames == null || subjectAltNames.isEmpty()) {
            String extractCN = extractCN(x509Certificate.getSubjectX500Principal().getName("RFC2253"));
            if (extractCN != null) {
                matchCN(str, extractCN, this.publicSuffixMatcher);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate subject for <");
            sb.append(str);
            sb.append("> doesn't contain ");
            sb.append("a common name and does not have alternative names");
            throw new SSLException(sb.toString());
        }
        int i = C60861.f4525xdc99fdb1[determineHostFormat.ordinal()];
        if (i == 1) {
            matchIPAddress(str, subjectAltNames);
        } else if (i != 2) {
            matchDNSName(str, subjectAltNames, this.publicSuffixMatcher);
        } else {
            matchIPv6Address(str, subjectAltNames);
        }
    }

    static void matchIPAddress(String str, List<SubjectName> list) throws SSLException {
        int i = 0;
        while (i < list.size()) {
            SubjectName subjectName = (SubjectName) list.get(i);
            if (subjectName.getType() != 7 || !str.equals(subjectName.getValue())) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    static void matchIPv6Address(String str, List<SubjectName> list) throws SSLException {
        String normaliseAddress = normaliseAddress(str);
        int i = 0;
        while (i < list.size()) {
            SubjectName subjectName = (SubjectName) list.get(i);
            if (subjectName.getType() != 7 || !normaliseAddress.equals(normaliseAddress(subjectName.getValue()))) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    static void matchDNSName(String str, List<SubjectName> list, PublicSuffixMatcher publicSuffixMatcher2) throws SSLException {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < list.size()) {
            SubjectName subjectName = (SubjectName) list.get(i);
            if (subjectName.getType() != 2 || !matchIdentityStrict(lowerCase, subjectName.getValue().toLowerCase(Locale.ROOT), publicSuffixMatcher2)) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    static void matchCN(String str, String str2, PublicSuffixMatcher publicSuffixMatcher2) throws SSLException {
        if (!matchIdentityStrict(str.toLowerCase(Locale.ROOT), str2.toLowerCase(Locale.ROOT), publicSuffixMatcher2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate for <");
            sb.append(str);
            sb.append("> doesn't match ");
            sb.append("common name of the certificate subject: ");
            sb.append(str2);
            throw new SSLPeerUnverifiedException(sb.toString());
        }
    }

    static boolean matchDomainRoot(String str, String str2) {
        boolean z = false;
        if (str2 == null) {
            return false;
        }
        if (str.endsWith(str2) && (str.length() == str2.length() || str.charAt((str.length() - str2.length()) - 1) == '.')) {
            z = true;
        }
        return z;
    }

    private static boolean matchIdentity(String str, String str2, PublicSuffixMatcher publicSuffixMatcher2, boolean z) {
        String str3 = ".";
        if (publicSuffixMatcher2 != null && str.contains(str3) && !matchDomainRoot(str, publicSuffixMatcher2.getDomainRoot(str2, DomainType.ICANN))) {
            return false;
        }
        int indexOf = str2.indexOf(42);
        if (indexOf == -1) {
            return str.equalsIgnoreCase(str2);
        }
        String substring = str2.substring(0, indexOf);
        String substring2 = str2.substring(indexOf + 1);
        if (!substring.isEmpty() && !str.startsWith(substring)) {
            return false;
        }
        if (!substring2.isEmpty() && !str.endsWith(substring2)) {
            return false;
        }
        if (!z || !str.substring(substring.length(), str.length() - substring2.length()).contains(str3)) {
            return true;
        }
        return false;
    }

    static boolean matchIdentity(String str, String str2, PublicSuffixMatcher publicSuffixMatcher2) {
        return matchIdentity(str, str2, publicSuffixMatcher2, false);
    }

    static boolean matchIdentity(String str, String str2) {
        return matchIdentity(str, str2, null, false);
    }

    static boolean matchIdentityStrict(String str, String str2, PublicSuffixMatcher publicSuffixMatcher2) {
        return matchIdentity(str, str2, publicSuffixMatcher2, true);
    }

    static boolean matchIdentityStrict(String str, String str2) {
        return matchIdentity(str, str2, null, true);
    }

    static String extractCN(String str) throws SSLException {
        if (str == null) {
            return null;
        }
        try {
            List rdns = new LdapName(str).getRdns();
            for (int size = rdns.size() - 1; size >= 0; size--) {
                Attribute attribute = ((Rdn) rdns.get(size)).toAttributes().get("cn");
                if (attribute != null) {
                    try {
                        Object obj = attribute.get();
                        if (obj != null) {
                            return obj.toString();
                        }
                    } catch (NoSuchElementException | NamingException unused) {
                        continue;
                    }
                }
            }
            return null;
        } catch (InvalidNameException unused2) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" is not a valid X500 distinguished name");
            throw new SSLException(sb.toString());
        }
    }

    static HostNameType determineHostFormat(String str) {
        if (InetAddressUtils.isIPv4Address(str)) {
            return HostNameType.IPv4;
        }
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
        }
        if (InetAddressUtils.isIPv6Address(str)) {
            return HostNameType.IPv6;
        }
        return HostNameType.DNS;
    }

    static List<SubjectName> getSubjectAltNames(X509Certificate x509Certificate) {
        try {
            Collection<List> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (List list : subjectAlternativeNames) {
                Integer num = list.size() >= 2 ? (Integer) list.get(0) : null;
                if (num != null && (num.intValue() == 2 || num.intValue() == 7)) {
                    Object obj = list.get(1);
                    if (obj instanceof String) {
                        arrayList.add(new SubjectName((String) obj, num.intValue()));
                    } else {
                        boolean z = obj instanceof byte[];
                    }
                }
            }
            return arrayList;
        } catch (CertificateParsingException unused) {
            return Collections.emptyList();
        }
    }

    static String normaliseAddress(String str) {
        if (str == null) {
            return str;
        }
        try {
            str = InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
        }
        return str;
    }
}
