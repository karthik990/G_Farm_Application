package p043io.netty.handler.ssl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.CipherSuiteConverter */
final class CipherSuiteConverter {
    private static final Pattern JAVA_AES_CBC_PATTERN = Pattern.compile("^(AES)_([0-9]+)_CBC$");
    private static final Pattern JAVA_AES_PATTERN = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
    private static final Pattern JAVA_CIPHERSUITE_PATTERN = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
    private static final Pattern OPENSSL_AES_CBC_PATTERN = Pattern.compile("^(AES)([0-9]+)$");
    private static final Pattern OPENSSL_AES_PATTERN = Pattern.compile("^(AES)([0-9]+)-(.*)$");
    private static final Pattern OPENSSL_CIPHERSUITE_PATTERN = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP)-(?:DSS|RSA|ECDSA)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
    private static final ConcurrentMap<String, String> j2o = PlatformDependent.newConcurrentHashMap();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CipherSuiteConverter.class);
    private static final ConcurrentMap<String, Map<String, String>> o2j = PlatformDependent.newConcurrentHashMap();

    private static String toJavaHmacAlgo(String str) {
        return str;
    }

    private static String toOpenSslHmacAlgo(String str) {
        return str;
    }

    static void clearCache() {
        j2o.clear();
        o2j.clear();
    }

    static boolean isJ2OCached(String str, String str2) {
        return str2.equals(j2o.get(str));
    }

    static boolean isO2JCached(String str, String str2, String str3) {
        Map map = (Map) o2j.get(str);
        if (map == null) {
            return false;
        }
        return str3.equals(map.get(str2));
    }

    static String toOpenSsl(Iterable<String> iterable) {
        StringBuilder sb = new StringBuilder();
        for (String str : iterable) {
            if (str == null) {
                break;
            }
            String openSsl = toOpenSsl(str);
            if (openSsl != null) {
                str = openSsl;
            }
            sb.append(str);
            sb.append(':');
        }
        if (sb.length() <= 0) {
            return "";
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    static String toOpenSsl(String str) {
        String str2 = (String) j2o.get(str);
        if (str2 != null) {
            return str2;
        }
        return cacheFromJava(str);
    }

    private static String cacheFromJava(String str) {
        String openSslUncached = toOpenSslUncached(str);
        if (openSslUncached == null) {
            return null;
        }
        j2o.putIfAbsent(str, openSslUncached);
        String substring = str.substring(4);
        HashMap hashMap = new HashMap(4);
        hashMap.put("", substring);
        StringBuilder sb = new StringBuilder();
        sb.append("SSL_");
        sb.append(substring);
        hashMap.put("SSL", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("TLS_");
        sb2.append(substring);
        hashMap.put("TLS", sb2.toString());
        o2j.put(openSslUncached, hashMap);
        logger.debug("Cipher suite mapping: {} => {}", str, openSslUncached);
        return openSslUncached;
    }

    static String toOpenSslUncached(String str) {
        Matcher matcher = JAVA_CIPHERSUITE_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        String openSslHandshakeAlgo = toOpenSslHandshakeAlgo(matcher.group(1));
        String openSslBulkCipher = toOpenSslBulkCipher(matcher.group(2));
        String openSslHmacAlgo = toOpenSslHmacAlgo(matcher.group(3));
        if (openSslHandshakeAlgo.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(openSslBulkCipher);
            sb.append('-');
            sb.append(openSslHmacAlgo);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(openSslHandshakeAlgo);
        sb2.append('-');
        sb2.append(openSslBulkCipher);
        sb2.append('-');
        sb2.append(openSslHmacAlgo);
        return sb2.toString();
    }

    private static String toOpenSslHandshakeAlgo(String str) {
        boolean endsWith = str.endsWith("_EXPORT");
        if (endsWith) {
            str = str.substring(0, str.length() - 7);
        }
        if ("RSA".equals(str)) {
            str = "";
        } else if (str.endsWith("_anon")) {
            StringBuilder sb = new StringBuilder();
            sb.append('A');
            sb.append(str.substring(0, str.length() - 5));
            str = sb.toString();
        }
        if (endsWith) {
            if (str.isEmpty()) {
                str = "EXP";
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("EXP-");
                sb2.append(str);
                str = sb2.toString();
            }
        }
        return str.replace('_', '-');
    }

    private static String toOpenSslBulkCipher(String str) {
        if (str.startsWith("AES_")) {
            Matcher matcher = JAVA_AES_CBC_PATTERN.matcher(str);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1$2");
            }
            Matcher matcher2 = JAVA_AES_PATTERN.matcher(str);
            if (matcher2.matches()) {
                return matcher2.replaceFirst("$1$2-$3");
            }
        }
        if ("3DES_EDE_CBC".equals(str)) {
            return "DES-CBC3";
        }
        if ("RC4_128".equals(str) || "RC4_40".equals(str)) {
            return "RC4";
        }
        if ("DES40_CBC".equals(str) || "DES_CBC_40".equals(str)) {
            return "DES-CBC";
        }
        if ("RC2_CBC_40".equals(str)) {
            return "RC2-CBC";
        }
        return str.replace('_', '-');
    }

    static String toJava(String str, String str2) {
        Map map = (Map) o2j.get(str);
        if (map == null) {
            map = cacheFromOpenSsl(str);
            if (map == null) {
                return null;
            }
        }
        String str3 = (String) map.get(str2);
        if (str3 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append('_');
            sb.append((String) map.get(""));
            str3 = sb.toString();
        }
        return str3;
    }

    private static Map<String, String> cacheFromOpenSsl(String str) {
        String javaUncached = toJavaUncached(str);
        if (javaUncached == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SSL_");
        sb.append(javaUncached);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("TLS_");
        sb3.append(javaUncached);
        String sb4 = sb3.toString();
        HashMap hashMap = new HashMap(4);
        hashMap.put("", javaUncached);
        hashMap.put("SSL", sb2);
        hashMap.put("TLS", sb4);
        o2j.putIfAbsent(str, hashMap);
        j2o.putIfAbsent(sb4, str);
        j2o.putIfAbsent(sb2, str);
        String str2 = "Cipher suite mapping: {} => {}";
        logger.debug(str2, sb4, str);
        logger.debug(str2, sb2, str);
        return hashMap;
    }

    static String toJavaUncached(String str) {
        Matcher matcher = OPENSSL_CIPHERSUITE_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        boolean z = true;
        String group = matcher.group(1);
        String str2 = "";
        if (group == null) {
            group = str2;
        } else {
            if (group.startsWith("EXP-")) {
                group = group.substring(4);
            } else if ("EXP".equals(group)) {
                group = str2;
            }
            String javaHandshakeAlgo = toJavaHandshakeAlgo(group, z);
            String javaBulkCipher = toJavaBulkCipher(matcher.group(2), z);
            String javaHmacAlgo = toJavaHmacAlgo(matcher.group(3));
            StringBuilder sb = new StringBuilder();
            sb.append(javaHandshakeAlgo);
            sb.append("_WITH_");
            sb.append(javaBulkCipher);
            sb.append('_');
            sb.append(javaHmacAlgo);
            return sb.toString();
        }
        z = false;
        String javaHandshakeAlgo2 = toJavaHandshakeAlgo(group, z);
        String javaBulkCipher2 = toJavaBulkCipher(matcher.group(2), z);
        String javaHmacAlgo2 = toJavaHmacAlgo(matcher.group(3));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(javaHandshakeAlgo2);
        sb2.append("_WITH_");
        sb2.append(javaBulkCipher2);
        sb2.append('_');
        sb2.append(javaHmacAlgo2);
        return sb2.toString();
    }

    private static String toJavaHandshakeAlgo(String str, boolean z) {
        if (str.isEmpty()) {
            str = "RSA";
        } else if ("ADH".equals(str)) {
            str = "DH_anon";
        } else if ("AECDH".equals(str)) {
            str = "ECDH_anon";
        }
        String replace = str.replace('-', '_');
        if (!z) {
            return replace;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(replace);
        sb.append("_EXPORT");
        return sb.toString();
    }

    private static String toJavaBulkCipher(String str, boolean z) {
        if (str.startsWith("AES")) {
            Matcher matcher = OPENSSL_AES_CBC_PATTERN.matcher(str);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1_$2_CBC");
            }
            Matcher matcher2 = OPENSSL_AES_PATTERN.matcher(str);
            if (matcher2.matches()) {
                return matcher2.replaceFirst("$1_$2_$3");
            }
        }
        if ("DES-CBC3".equals(str)) {
            return "3DES_EDE_CBC";
        }
        if ("RC4".equals(str)) {
            return z ? "RC4_40" : "RC4_128";
        }
        if ("DES-CBC".equals(str)) {
            return z ? "DES_CBC_40" : "DES_CBC";
        }
        if ("RC2-CBC".equals(str)) {
            return z ? "RC2_CBC_40" : "RC2_CBC";
        }
        return str.replace('-', '_');
    }

    private CipherSuiteConverter() {
    }
}
