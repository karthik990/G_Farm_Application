package p043io.netty.util.internal;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.security.AccessController;
import java.security.PrivilegedAction;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.internal.SystemPropertyUtil */
public final class SystemPropertyUtil {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);

    public static boolean contains(String str) {
        return get(str) != null;
    }

    public static String get(String str) {
        return get(str, null);
    }

    public static String get(final String str, String str2) {
        if (str == null) {
            throw new NullPointerException("key");
        } else if (!str.isEmpty()) {
            String str3 = null;
            try {
                if (System.getSecurityManager() == null) {
                    str3 = System.getProperty(str);
                } else {
                    str3 = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                        public String run() {
                            return System.getProperty(str);
                        }
                    });
                }
            } catch (SecurityException e) {
                logger.warn("Unable to retrieve a system property '{}'; default values will be used.", str, e);
            }
            return str3 == null ? str2 : str3;
        } else {
            throw new IllegalArgumentException("key must not be empty.");
        }
    }

    public static boolean getBoolean(String str, boolean z) {
        String str2 = get(str);
        if (str2 == null) {
            return z;
        }
        String lowerCase = str2.trim().toLowerCase();
        if (lowerCase.isEmpty() || "true".equals(lowerCase) || "yes".equals(lowerCase) || IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(lowerCase)) {
            return true;
        }
        if ("false".equals(lowerCase) || "no".equals(lowerCase) || "0".equals(lowerCase)) {
            return false;
        }
        logger.warn("Unable to parse the boolean system property '{}':{} - using the default value: {}", str, lowerCase, Boolean.valueOf(z));
        return z;
    }

    public static int getInt(String str, int i) {
        String str2 = get(str);
        if (str2 == null) {
            return i;
        }
        String trim = str2.trim();
        try {
            return Integer.parseInt(trim);
        } catch (Exception unused) {
            logger.warn("Unable to parse the integer system property '{}':{} - using the default value: {}", str, trim, Integer.valueOf(i));
            return i;
        }
    }

    public static long getLong(String str, long j) {
        String str2 = get(str);
        if (str2 == null) {
            return j;
        }
        String trim = str2.trim();
        try {
            return Long.parseLong(trim);
        } catch (Exception unused) {
            logger.warn("Unable to parse the long integer system property '{}':{} - using the default value: {}", str, trim, Long.valueOf(j));
            return j;
        }
    }

    private SystemPropertyUtil() {
    }
}
