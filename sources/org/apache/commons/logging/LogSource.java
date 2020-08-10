package org.apache.commons.logging;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import org.apache.commons.logging.impl.NoOpLog;

public class LogSource {
    protected static boolean jdk14IsAvailable;
    protected static boolean log4jIsAvailable;
    protected static Constructor logImplctor = null;
    protected static Hashtable logs = new Hashtable();

    /* JADX WARNING: Can't wrap try/catch for region: R(3:40|41|43) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:28|29|30|31|48) */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        setLogImplementation(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x004f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x0069 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:32:0x0053=Splitter:B:32:0x0053, B:30:0x004f=Splitter:B:30:0x004f} */
    static {
        /*
            java.lang.String r0 = "org.apache.commons.logging.impl.Jdk14Logger"
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            logs = r1
            r1 = 0
            log4jIsAvailable = r1
            jdk14IsAvailable = r1
            r2 = 0
            logImplctor = r2
            r3 = 1
            java.lang.String r4 = "org.apache.log4j.Logger"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ all -> 0x0020 }
            if (r4 == 0) goto L_0x001c
            r4 = 1
            goto L_0x001d
        L_0x001c:
            r4 = 0
        L_0x001d:
            log4jIsAvailable = r4     // Catch:{ all -> 0x0020 }
            goto L_0x0022
        L_0x0020:
            log4jIsAvailable = r1
        L_0x0022:
            java.lang.String r4 = "java.util.logging.Logger"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ all -> 0x0035 }
            if (r4 == 0) goto L_0x0031
            java.lang.Class r4 = java.lang.Class.forName(r0)     // Catch:{ all -> 0x0035 }
            if (r4 == 0) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r3 = 0
        L_0x0032:
            jdk14IsAvailable = r3     // Catch:{ all -> 0x0035 }
            goto L_0x0037
        L_0x0035:
            jdk14IsAvailable = r1
        L_0x0037:
            java.lang.String r1 = "org.apache.commons.logging.log"
            java.lang.String r2 = java.lang.System.getProperty(r1)     // Catch:{ all -> 0x0046 }
            if (r2 != 0) goto L_0x0047
            java.lang.String r1 = "org.apache.commons.logging.Log"
            java.lang.String r2 = java.lang.System.getProperty(r1)     // Catch:{ all -> 0x0046 }
            goto L_0x0047
        L_0x0046:
        L_0x0047:
            java.lang.String r1 = "org.apache.commons.logging.impl.NoOpLog"
            if (r2 == 0) goto L_0x0053
            setLogImplementation(r2)     // Catch:{ all -> 0x004f }
            goto L_0x006c
        L_0x004f:
            setLogImplementation(r1)     // Catch:{ all -> 0x006c }
            goto L_0x006c
        L_0x0053:
            boolean r2 = log4jIsAvailable     // Catch:{ all -> 0x0069 }
            if (r2 == 0) goto L_0x005d
            java.lang.String r0 = "org.apache.commons.logging.impl.Log4JLogger"
            setLogImplementation(r0)     // Catch:{ all -> 0x0069 }
            goto L_0x006c
        L_0x005d:
            boolean r2 = jdk14IsAvailable     // Catch:{ all -> 0x0069 }
            if (r2 == 0) goto L_0x0065
            setLogImplementation(r0)     // Catch:{ all -> 0x0069 }
            goto L_0x006c
        L_0x0065:
            setLogImplementation(r1)     // Catch:{ all -> 0x0069 }
            goto L_0x006c
        L_0x0069:
            setLogImplementation(r1)     // Catch:{ all -> 0x006c }
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogSource.<clinit>():void");
    }

    private LogSource() {
    }

    public static void setLogImplementation(String str) throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException {
        try {
            logImplctor = Class.forName(str).getConstructor(new Class[]{"".getClass()});
        } catch (Throwable unused) {
            logImplctor = null;
        }
    }

    public static void setLogImplementation(Class cls) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
        logImplctor = cls.getConstructor(new Class[]{"".getClass()});
    }

    public static Log getInstance(String str) {
        Log log = (Log) logs.get(str);
        if (log != null) {
            return log;
        }
        Log makeNewLogInstance = makeNewLogInstance(str);
        logs.put(str, makeNewLogInstance);
        return makeNewLogInstance;
    }

    public static Log getInstance(Class cls) {
        return getInstance(cls.getName());
    }

    public static Log makeNewLogInstance(String str) {
        Log log;
        try {
            log = (Log) logImplctor.newInstance(new Object[]{str});
        } catch (Throwable unused) {
            log = null;
        }
        return log == null ? new NoOpLog(str) : log;
    }

    public static String[] getLogNames() {
        return (String[]) logs.keySet().toArray(new String[logs.size()]);
    }
}
