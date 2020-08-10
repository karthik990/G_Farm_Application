package org.apache.commons.logging;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public abstract class LogFactory {
    public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
    public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
    public static final String FACTORY_PROPERTIES = "commons-logging.properties";
    public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
    public static final String PRIORITY_KEY = "priority";
    protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
    public static final String TCCL_KEY = "use_tccl";
    private static final String WEAK_HASHTABLE_CLASSNAME = "org.apache.commons.logging.impl.WeakHashtable";
    static /* synthetic */ Class class$org$apache$commons$logging$LogFactory;
    private static final String diagnosticPrefix;
    private static PrintStream diagnosticsStream = initDiagnostics();
    protected static Hashtable factories = createFactoryStore();
    protected static volatile LogFactory nullClassLoaderFactory;
    private static final ClassLoader thisClassLoader;

    public abstract Object getAttribute(String str);

    public abstract String[] getAttributeNames();

    public abstract Log getInstance(Class cls) throws LogConfigurationException;

    public abstract Log getInstance(String str) throws LogConfigurationException;

    public abstract void release();

    public abstract void removeAttribute(String str);

    public abstract void setAttribute(String str, Object obj);

    protected LogFactory() {
    }

    private static final Hashtable createFactoryStore() {
        String str;
        Hashtable hashtable = null;
        try {
            str = getSystemProperty(HASHTABLE_IMPLEMENTATION_PROPERTY, null);
        } catch (SecurityException unused) {
            str = null;
        }
        String str2 = WEAK_HASHTABLE_CLASSNAME;
        if (str == null) {
            str = str2;
        }
        try {
            hashtable = (Hashtable) Class.forName(str).newInstance();
        } catch (Throwable th) {
            handleThrowable(th);
            if (!str2.equals(str)) {
                String str3 = "[ERROR] LogFactory: Load of custom hashtable failed";
                if (isDiagnosticsEnabled()) {
                    logDiagnostic(str3);
                } else {
                    System.err.println(str3);
                }
            }
        }
        return hashtable == null ? new Hashtable() : hashtable;
    }

    private static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    protected static void handleThrowable(Throwable th) {
        if (th instanceof ThreadDeath) {
            throw ((ThreadDeath) th);
        } else if (th instanceof VirtualMachineError) {
            throw ((VirtualMachineError) th);
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x0115 */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073 A[Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009a A[Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x018f  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01f5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.commons.logging.LogFactory getFactory() throws org.apache.commons.logging.LogConfigurationException {
        /*
            java.lang.String r0 = "META-INF/services/org.apache.commons.logging.LogFactory"
            java.lang.String r1 = "]. Trying alternative implementations..."
            java.lang.String r2 = "[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: ["
            java.lang.String r3 = "org.apache.commons.logging.LogFactory"
            java.lang.ClassLoader r4 = getContextClassLoaderInternal()
            if (r4 != 0) goto L_0x0019
            boolean r5 = isDiagnosticsEnabled()
            if (r5 == 0) goto L_0x0019
            java.lang.String r5 = "Context classloader is null."
            logDiagnostic(r5)
        L_0x0019:
            org.apache.commons.logging.LogFactory r5 = getCachedFactory(r4)
            if (r5 == 0) goto L_0x0020
            return r5
        L_0x0020:
            boolean r6 = isDiagnosticsEnabled()
            if (r6 == 0) goto L_0x0043
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            r6.<init>()
            java.lang.String r7 = "[LOOKUP] LogFactory implementation requested for the first time for context classloader "
            r6.append(r7)
            java.lang.String r7 = objectId(r4)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            logDiagnostic(r6)
            java.lang.String r6 = "[LOOKUP] "
            logHierarchy(r6, r4)
        L_0x0043:
            java.lang.String r6 = "commons-logging.properties"
            java.util.Properties r6 = getConfigurationFile(r4, r6)
            if (r6 == 0) goto L_0x0060
            java.lang.String r7 = "use_tccl"
            java.lang.String r7 = r6.getProperty(r7)
            if (r7 == 0) goto L_0x0060
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x0060
            java.lang.ClassLoader r7 = thisClassLoader
            goto L_0x0061
        L_0x0060:
            r7 = r4
        L_0x0061:
            boolean r8 = isDiagnosticsEnabled()
            if (r8 == 0) goto L_0x006c
            java.lang.String r8 = "[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use..."
            logDiagnostic(r8)
        L_0x006c:
            r8 = 0
            java.lang.String r8 = getSystemProperty(r3, r8)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            if (r8 == 0) goto L_0x009a
            boolean r9 = isDiagnosticsEnabled()     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            if (r9 == 0) goto L_0x0095
            java.lang.StringBuffer r9 = new java.lang.StringBuffer     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            r9.<init>()     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            java.lang.String r10 = "[LOOKUP] Creating an instance of LogFactory class '"
            r9.append(r10)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            r9.append(r8)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            java.lang.String r10 = "' as specified by system property "
            r9.append(r10)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            r9.append(r3)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            java.lang.String r9 = r9.toString()     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            logDiagnostic(r9)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
        L_0x0095:
            org.apache.commons.logging.LogFactory r5 = newFactory(r8, r7, r4)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            goto L_0x00f5
        L_0x009a:
            boolean r8 = isDiagnosticsEnabled()     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            if (r8 == 0) goto L_0x00f5
            java.lang.String r8 = "[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined."
            logDiagnostic(r8)     // Catch:{ SecurityException -> 0x00a8, RuntimeException -> 0x00a6 }
            goto L_0x00f5
        L_0x00a6:
            r0 = move-exception
            goto L_0x00aa
        L_0x00a8:
            r8 = move-exception
            goto L_0x00d2
        L_0x00aa:
            boolean r1 = isDiagnosticsEnabled()
            if (r1 == 0) goto L_0x00d1
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = "[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: ["
            r1.append(r2)
            java.lang.String r2 = r0.getMessage()
            java.lang.String r2 = trim(r2)
            r1.append(r2)
            java.lang.String r2 = "] as specified by a system property."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            logDiagnostic(r1)
        L_0x00d1:
            throw r0
        L_0x00d2:
            boolean r9 = isDiagnosticsEnabled()
            if (r9 == 0) goto L_0x00f5
            java.lang.StringBuffer r9 = new java.lang.StringBuffer
            r9.<init>()
            r9.append(r2)
            java.lang.String r8 = r8.getMessage()
            java.lang.String r8 = trim(r8)
            r9.append(r8)
            r9.append(r1)
            java.lang.String r8 = r9.toString()
            logDiagnostic(r8)
        L_0x00f5:
            if (r5 != 0) goto L_0x018d
            boolean r8 = isDiagnosticsEnabled()
            if (r8 == 0) goto L_0x0102
            java.lang.String r8 = "[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use..."
            logDiagnostic(r8)
        L_0x0102:
            java.io.InputStream r8 = getResourceAsStream(r4, r0)     // Catch:{ Exception -> 0x0169 }
            if (r8 == 0) goto L_0x015d
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ UnsupportedEncodingException -> 0x0115 }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ UnsupportedEncodingException -> 0x0115 }
            java.lang.String r11 = "UTF-8"
            r10.<init>(r8, r11)     // Catch:{ UnsupportedEncodingException -> 0x0115 }
            r9.<init>(r10)     // Catch:{ UnsupportedEncodingException -> 0x0115 }
            goto L_0x011f
        L_0x0115:
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0169 }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0169 }
            r10.<init>(r8)     // Catch:{ Exception -> 0x0169 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x0169 }
        L_0x011f:
            java.lang.String r8 = r9.readLine()     // Catch:{ Exception -> 0x0169 }
            r9.close()     // Catch:{ Exception -> 0x0169 }
            if (r8 == 0) goto L_0x018d
            java.lang.String r9 = ""
            boolean r9 = r9.equals(r8)     // Catch:{ Exception -> 0x0169 }
            if (r9 != 0) goto L_0x018d
            boolean r9 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x0169 }
            if (r9 == 0) goto L_0x0157
            java.lang.StringBuffer r9 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0169 }
            r9.<init>()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r10 = "[LOOKUP]  Creating an instance of LogFactory class "
            r9.append(r10)     // Catch:{ Exception -> 0x0169 }
            r9.append(r8)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r10 = " as specified by file '"
            r9.append(r10)     // Catch:{ Exception -> 0x0169 }
            r9.append(r0)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r0 = "' which was present in the path of the context classloader."
            r9.append(r0)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r0 = r9.toString()     // Catch:{ Exception -> 0x0169 }
            logDiagnostic(r0)     // Catch:{ Exception -> 0x0169 }
        L_0x0157:
            org.apache.commons.logging.LogFactory r0 = newFactory(r8, r7, r4)     // Catch:{ Exception -> 0x0169 }
            r5 = r0
            goto L_0x018d
        L_0x015d:
            boolean r0 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x0169 }
            if (r0 == 0) goto L_0x018d
            java.lang.String r0 = "[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found."
            logDiagnostic(r0)     // Catch:{ Exception -> 0x0169 }
            goto L_0x018d
        L_0x0169:
            r0 = move-exception
            boolean r8 = isDiagnosticsEnabled()
            if (r8 == 0) goto L_0x018d
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            r8.append(r2)
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = trim(r0)
            r8.append(r0)
            r8.append(r1)
            java.lang.String r0 = r8.toString()
            logDiagnostic(r0)
        L_0x018d:
            if (r5 != 0) goto L_0x01de
            if (r6 == 0) goto L_0x01d3
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x019c
            java.lang.String r0 = "[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use..."
            logDiagnostic(r0)
        L_0x019c:
            java.lang.String r0 = r6.getProperty(r3)
            if (r0 == 0) goto L_0x01c7
            boolean r1 = isDiagnosticsEnabled()
            if (r1 == 0) goto L_0x01c1
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = "[LOOKUP] Properties file specifies LogFactory subclass '"
            r1.append(r2)
            r1.append(r0)
            java.lang.String r2 = "'"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            logDiagnostic(r1)
        L_0x01c1:
            org.apache.commons.logging.LogFactory r0 = newFactory(r0, r7, r4)
            r5 = r0
            goto L_0x01de
        L_0x01c7:
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x01de
            java.lang.String r0 = "[LOOKUP] Properties file has no entry specifying LogFactory subclass."
            logDiagnostic(r0)
            goto L_0x01de
        L_0x01d3:
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x01de
            java.lang.String r0 = "[LOOKUP] No properties file available to determine LogFactory subclass from.."
            logDiagnostic(r0)
        L_0x01de:
            if (r5 != 0) goto L_0x01f3
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x01eb
            java.lang.String r0 = "[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader)."
            logDiagnostic(r0)
        L_0x01eb:
            java.lang.ClassLoader r0 = thisClassLoader
            java.lang.String r1 = "org.apache.commons.logging.impl.LogFactoryImpl"
            org.apache.commons.logging.LogFactory r5 = newFactory(r1, r0, r4)
        L_0x01f3:
            if (r5 == 0) goto L_0x0212
            cacheFactory(r4, r5)
            if (r6 == 0) goto L_0x0212
            java.util.Enumeration r0 = r6.propertyNames()
        L_0x01fe:
            boolean r1 = r0.hasMoreElements()
            if (r1 == 0) goto L_0x0212
            java.lang.Object r1 = r0.nextElement()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = r6.getProperty(r1)
            r5.setAttribute(r1, r2)
            goto L_0x01fe
        L_0x0212:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.getFactory():org.apache.commons.logging.LogFactory");
    }

    public static Log getLog(Class cls) throws LogConfigurationException {
        return getFactory().getInstance(cls);
    }

    public static Log getLog(String str) throws LogConfigurationException {
        return getFactory().getInstance(str);
    }

    public static void release(ClassLoader classLoader) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Releasing factory for classloader ");
            stringBuffer.append(objectId(classLoader));
            logDiagnostic(stringBuffer.toString());
        }
        Hashtable hashtable = factories;
        synchronized (hashtable) {
            if (classLoader != null) {
                LogFactory logFactory = (LogFactory) hashtable.get(classLoader);
                if (logFactory != null) {
                    logFactory.release();
                    hashtable.remove(classLoader);
                }
            } else if (nullClassLoaderFactory != null) {
                nullClassLoaderFactory.release();
                nullClassLoaderFactory = null;
            }
        }
    }

    public static void releaseAll() {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Releasing factory for all classloaders.");
        }
        Hashtable hashtable = factories;
        synchronized (hashtable) {
            Enumeration elements = hashtable.elements();
            while (elements.hasMoreElements()) {
                ((LogFactory) elements.nextElement()).release();
            }
            hashtable.clear();
            if (nullClassLoaderFactory != null) {
                nullClassLoaderFactory.release();
                nullClassLoaderFactory = null;
            }
        }
    }

    protected static ClassLoader getClassLoader(Class cls) {
        try {
            return cls.getClassLoader();
        } catch (SecurityException e) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to get classloader for class '");
                stringBuffer.append(cls);
                stringBuffer.append("' due to security restrictions - ");
                stringBuffer.append(e.getMessage());
                logDiagnostic(stringBuffer.toString());
            }
            throw e;
        }
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return directGetContextClassLoader();
    }

    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return LogFactory.directGetContextClassLoader();
            }
        });
    }

    protected static ClassLoader directGetContextClassLoader() throws LogConfigurationException {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (SecurityException unused) {
            return null;
        }
    }

    private static LogFactory getCachedFactory(ClassLoader classLoader) {
        if (classLoader == null) {
            return nullClassLoaderFactory;
        }
        return (LogFactory) factories.get(classLoader);
    }

    private static void cacheFactory(ClassLoader classLoader, LogFactory logFactory) {
        if (logFactory == null) {
            return;
        }
        if (classLoader == null) {
            nullClassLoaderFactory = logFactory;
        } else {
            factories.put(classLoader, logFactory);
        }
    }

    protected static LogFactory newFactory(final String str, final ClassLoader classLoader, ClassLoader classLoader2) throws LogConfigurationException {
        Object doPrivileged = AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return LogFactory.createFactory(str, classLoader);
            }
        });
        if (doPrivileged instanceof LogConfigurationException) {
            LogConfigurationException logConfigurationException = (LogConfigurationException) doPrivileged;
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("An error occurred while loading the factory class:");
                stringBuffer.append(logConfigurationException.getMessage());
                logDiagnostic(stringBuffer.toString());
            }
            throw logConfigurationException;
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Created object ");
            stringBuffer2.append(objectId(doPrivileged));
            stringBuffer2.append(" to manage classloader ");
            stringBuffer2.append(objectId(classLoader2));
            logDiagnostic(stringBuffer2.toString());
        }
        return (LogFactory) doPrivileged;
    }

    protected static LogFactory newFactory(String str, ClassLoader classLoader) {
        return newFactory(str, classLoader, null);
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x009f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.Object createFactory(java.lang.String r5, java.lang.ClassLoader r6) {
        /*
            java.lang.String r0 = "org.apache.commons.logging.LogFactory"
            r1 = 0
            if (r6 == 0) goto L_0x0180
            java.lang.Class r1 = r6.loadClass(r5)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.Class r2 = class$org$apache$commons$logging$LogFactory     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            if (r2 != 0) goto L_0x0014
            java.lang.Class r2 = class$(r0)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            class$org$apache$commons$logging$LogFactory = r2     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            goto L_0x0016
        L_0x0014:
            java.lang.Class r2 = class$org$apache$commons$logging$LogFactory     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
        L_0x0016:
            boolean r2 = r2.isAssignableFrom(r1)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            if (r2 == 0) goto L_0x0047
            boolean r2 = isDiagnosticsEnabled()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            if (r2 == 0) goto L_0x0098
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.<init>()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = "Loaded class "
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = r1.getName()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = " from classloader "
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = objectId(r6)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r2 = r2.toString()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            logDiagnostic(r2)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            goto L_0x0098
        L_0x0047:
            boolean r2 = isDiagnosticsEnabled()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            if (r2 == 0) goto L_0x0098
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.<init>()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = "Factory class "
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = r1.getName()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = " loaded from classloader "
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.ClassLoader r3 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = objectId(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = " does not extend '"
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.Class r3 = class$org$apache$commons$logging$LogFactory     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            if (r3 != 0) goto L_0x007e
            java.lang.Class r3 = class$(r0)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            class$org$apache$commons$logging$LogFactory = r3     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            goto L_0x0080
        L_0x007e:
            java.lang.Class r3 = class$org$apache$commons$logging$LogFactory     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
        L_0x0080:
            java.lang.String r3 = r3.getName()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r3 = "' as loaded by this classloader."
            r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r2 = r2.toString()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            logDiagnostic(r2)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            java.lang.String r2 = "[BAD CL TREE] "
            logHierarchy(r2, r6)     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
        L_0x0098:
            java.lang.Object r2 = r1.newInstance()     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            org.apache.commons.logging.LogFactory r2 = (org.apache.commons.logging.LogFactory) r2     // Catch:{ ClassNotFoundException -> 0x0154, NoClassDefFoundError -> 0x011e, ClassCastException -> 0x009f }
            return r2
        L_0x009f:
            java.lang.ClassLoader r2 = thisClassLoader     // Catch:{ Exception -> 0x01ae }
            if (r6 != r2) goto L_0x0180
            boolean r6 = implementsLogFactory(r1)     // Catch:{ Exception -> 0x01ae }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01ae }
            r2.<init>()     // Catch:{ Exception -> 0x01ae }
            java.lang.String r3 = "The application has specified that a custom LogFactory implementation "
            r2.append(r3)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r3 = "should be used but Class '"
            r2.append(r3)     // Catch:{ Exception -> 0x01ae }
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "' cannot be converted to '"
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.Class r5 = class$org$apache$commons$logging$LogFactory     // Catch:{ Exception -> 0x01ae }
            if (r5 != 0) goto L_0x00c9
            java.lang.Class r5 = class$(r0)     // Catch:{ Exception -> 0x01ae }
            class$org$apache$commons$logging$LogFactory = r5     // Catch:{ Exception -> 0x01ae }
            goto L_0x00cb
        L_0x00c9:
            java.lang.Class r5 = class$org$apache$commons$logging$LogFactory     // Catch:{ Exception -> 0x01ae }
        L_0x00cb:
            java.lang.String r5 = r5.getName()     // Catch:{ Exception -> 0x01ae }
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "'. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            if (r6 == 0) goto L_0x00fd
            java.lang.String r5 = "The conflict is caused by the presence of multiple LogFactory classes "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "in incompatible classloaders. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "Background can be found in http://commons.apache.org/logging/tech.html. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "If you have not explicitly specified a custom LogFactory then it is likely "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "that the container has set one without your knowledge. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "In this case, consider using the commons-logging-adapters.jar file or "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "specifying the standard LogFactory from the command line. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            goto L_0x0102
        L_0x00fd:
            java.lang.String r5 = "Please check the custom implementation. "
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
        L_0x0102:
            java.lang.String r5 = "Help can be found @http://commons.apache.org/logging/troubleshooting.html."
            r2.append(r5)     // Catch:{ Exception -> 0x01ae }
            boolean r5 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x01ae }
            if (r5 == 0) goto L_0x0114
            java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x01ae }
            logDiagnostic(r5)     // Catch:{ Exception -> 0x01ae }
        L_0x0114:
            java.lang.ClassCastException r5 = new java.lang.ClassCastException     // Catch:{ Exception -> 0x01ae }
            java.lang.String r6 = r2.toString()     // Catch:{ Exception -> 0x01ae }
            r5.<init>(r6)     // Catch:{ Exception -> 0x01ae }
            throw r5     // Catch:{ Exception -> 0x01ae }
        L_0x011e:
            r2 = move-exception
            java.lang.ClassLoader r3 = thisClassLoader     // Catch:{ Exception -> 0x01ae }
            if (r6 != r3) goto L_0x0180
            boolean r3 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x01ae }
            if (r3 == 0) goto L_0x0153
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01ae }
            r3.<init>()     // Catch:{ Exception -> 0x01ae }
            java.lang.String r4 = "Class '"
            r3.append(r4)     // Catch:{ Exception -> 0x01ae }
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "' cannot be loaded"
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = " via classloader "
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = objectId(r6)     // Catch:{ Exception -> 0x01ae }
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = " - it depends on some other class that cannot be found."
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = r3.toString()     // Catch:{ Exception -> 0x01ae }
            logDiagnostic(r5)     // Catch:{ Exception -> 0x01ae }
        L_0x0153:
            throw r2     // Catch:{ Exception -> 0x01ae }
        L_0x0154:
            r2 = move-exception
            java.lang.ClassLoader r3 = thisClassLoader     // Catch:{ Exception -> 0x01ae }
            if (r6 != r3) goto L_0x0180
            boolean r3 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x01ae }
            if (r3 == 0) goto L_0x017f
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01ae }
            r3.<init>()     // Catch:{ Exception -> 0x01ae }
            java.lang.String r4 = "Unable to locate any class called '"
            r3.append(r4)     // Catch:{ Exception -> 0x01ae }
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = "' via classloader "
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = objectId(r6)     // Catch:{ Exception -> 0x01ae }
            r3.append(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r5 = r3.toString()     // Catch:{ Exception -> 0x01ae }
            logDiagnostic(r5)     // Catch:{ Exception -> 0x01ae }
        L_0x017f:
            throw r2     // Catch:{ Exception -> 0x01ae }
        L_0x0180:
            boolean r2 = isDiagnosticsEnabled()     // Catch:{ Exception -> 0x01ae }
            if (r2 == 0) goto L_0x01a3
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01ae }
            r2.<init>()     // Catch:{ Exception -> 0x01ae }
            java.lang.String r3 = "Unable to load factory class via classloader "
            r2.append(r3)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r6 = objectId(r6)     // Catch:{ Exception -> 0x01ae }
            r2.append(r6)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r6 = " - trying the classloader associated with this LogFactory."
            r2.append(r6)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r6 = r2.toString()     // Catch:{ Exception -> 0x01ae }
            logDiagnostic(r6)     // Catch:{ Exception -> 0x01ae }
        L_0x01a3:
            java.lang.Class r1 = java.lang.Class.forName(r5)     // Catch:{ Exception -> 0x01ae }
            java.lang.Object r5 = r1.newInstance()     // Catch:{ Exception -> 0x01ae }
            org.apache.commons.logging.LogFactory r5 = (org.apache.commons.logging.LogFactory) r5     // Catch:{ Exception -> 0x01ae }
            return r5
        L_0x01ae:
            r5 = move-exception
            boolean r6 = isDiagnosticsEnabled()
            if (r6 == 0) goto L_0x01ba
            java.lang.String r6 = "Unable to create LogFactory instance."
            logDiagnostic(r6)
        L_0x01ba:
            if (r1 == 0) goto L_0x01d4
            java.lang.Class r6 = class$org$apache$commons$logging$LogFactory
            if (r6 != 0) goto L_0x01c6
            java.lang.Class r6 = class$(r0)
            class$org$apache$commons$logging$LogFactory = r6
        L_0x01c6:
            boolean r6 = r6.isAssignableFrom(r1)
            if (r6 != 0) goto L_0x01d4
            org.apache.commons.logging.LogConfigurationException r6 = new org.apache.commons.logging.LogConfigurationException
            java.lang.String r0 = "The chosen LogFactory implementation does not extend LogFactory. Please check your configuration."
            r6.<init>(r0, r5)
            return r6
        L_0x01d4:
            org.apache.commons.logging.LogConfigurationException r6 = new org.apache.commons.logging.LogConfigurationException
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.createFactory(java.lang.String, java.lang.ClassLoader):java.lang.Object");
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private static boolean implementsLogFactory(Class cls) {
        String str = "[CUSTOM LOG FACTORY] ";
        boolean z = false;
        if (cls != null) {
            try {
                ClassLoader classLoader = cls.getClassLoader();
                if (classLoader == null) {
                    logDiagnostic("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
                } else {
                    logHierarchy(str, classLoader);
                    z = Class.forName(FACTORY_PROPERTY, false, classLoader).isAssignableFrom(cls);
                    if (z) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(str);
                        stringBuffer.append(cls.getName());
                        stringBuffer.append(" implements LogFactory but was loaded by an incompatible classloader.");
                        logDiagnostic(stringBuffer.toString());
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append(str);
                        stringBuffer2.append(cls.getName());
                        stringBuffer2.append(" does not implement LogFactory.");
                        logDiagnostic(stringBuffer2.toString());
                    }
                }
            } catch (SecurityException e) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer3.append(e.getMessage());
                logDiagnostic(stringBuffer3.toString());
            } catch (LinkageError e2) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer4.append(e2.getMessage());
                logDiagnostic(stringBuffer4.toString());
            } catch (ClassNotFoundException unused) {
                logDiagnostic("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
            }
        }
        return z;
    }

    private static InputStream getResourceAsStream(final ClassLoader classLoader, final String str) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                ClassLoader classLoader = classLoader;
                if (classLoader != null) {
                    return classLoader.getResourceAsStream(str);
                }
                return ClassLoader.getSystemResourceAsStream(str);
            }
        });
    }

    private static Enumeration getResources(final ClassLoader classLoader, final String str) {
        return (Enumeration) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                Enumeration enumeration = null;
                try {
                    if (classLoader != null) {
                        return classLoader.getResources(str);
                    }
                    enumeration = ClassLoader.getSystemResources(str);
                    return enumeration;
                } catch (IOException e) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Exception while trying to find configuration file ");
                        stringBuffer.append(str);
                        stringBuffer.append(":");
                        stringBuffer.append(e.getMessage());
                        LogFactory.logDiagnostic(stringBuffer.toString());
                    }
                    return null;
                } catch (NoSuchMethodError unused) {
                }
            }
        });
    }

    private static Properties getProperties(final URL url) {
        return (Properties) AccessController.doPrivileged(new PrivilegedAction() {
            /* JADX WARNING: Can't wrap try/catch for region: R(4:19|20|(1:22)|(2:24|25)) */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x004a, code lost:
                if (org.apache.commons.logging.LogFactory.isDiagnosticsEnabled() != false) goto L_0x004c;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
                r3 = new java.lang.StringBuffer();
                r3.append("Unable to read URL ");
                r3.append(r1);
                org.apache.commons.logging.LogFactory.access$000(r3.toString());
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
                if (r2 != null) goto L_0x0064;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
                r2.close();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:28:0x006c, code lost:
                if (org.apache.commons.logging.LogFactory.isDiagnosticsEnabled() != false) goto L_0x006e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x006e, code lost:
                r2 = new java.lang.StringBuffer();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:0x0075, code lost:
                r1 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
                r2.close();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:37:0x0080, code lost:
                if (org.apache.commons.logging.LogFactory.isDiagnosticsEnabled() != false) goto L_0x0082;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:38:0x0082, code lost:
                r2 = new java.lang.StringBuffer();
                r2.append(r0);
                r2.append(r1);
                org.apache.commons.logging.LogFactory.access$000(r2.toString());
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0046 */
            /* JADX WARNING: Removed duplicated region for block: B:33:0x0078 A[SYNTHETIC, Splitter:B:33:0x0078] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object run() {
                /*
                    r6 = this;
                    java.lang.String r0 = "Unable to close stream for URL "
                    r1 = 0
                    java.net.URL r2 = r1     // Catch:{ IOException -> 0x0045, all -> 0x0040 }
                    java.net.URLConnection r2 = r2.openConnection()     // Catch:{ IOException -> 0x0045, all -> 0x0040 }
                    r3 = 0
                    r2.setUseCaches(r3)     // Catch:{ IOException -> 0x0045, all -> 0x0040 }
                    java.io.InputStream r2 = r2.getInputStream()     // Catch:{ IOException -> 0x0045, all -> 0x0040 }
                    if (r2 == 0) goto L_0x001f
                    java.util.Properties r3 = new java.util.Properties     // Catch:{ IOException -> 0x0046 }
                    r3.<init>()     // Catch:{ IOException -> 0x0046 }
                    r3.load(r2)     // Catch:{ IOException -> 0x0046 }
                    r2.close()     // Catch:{ IOException -> 0x0046 }
                    return r3
                L_0x001f:
                    if (r2 == 0) goto L_0x0074
                    r2.close()     // Catch:{ IOException -> 0x0025 }
                    goto L_0x0074
                L_0x0025:
                    boolean r2 = org.apache.commons.logging.LogFactory.isDiagnosticsEnabled()
                    if (r2 == 0) goto L_0x0074
                    java.lang.StringBuffer r2 = new java.lang.StringBuffer
                    r2.<init>()
                L_0x0030:
                    r2.append(r0)
                    java.net.URL r0 = r1
                    r2.append(r0)
                    java.lang.String r0 = r2.toString()
                    org.apache.commons.logging.LogFactory.logDiagnostic(r0)
                    goto L_0x0074
                L_0x0040:
                    r2 = move-exception
                    r5 = r2
                    r2 = r1
                    r1 = r5
                    goto L_0x0076
                L_0x0045:
                    r2 = r1
                L_0x0046:
                    boolean r3 = org.apache.commons.logging.LogFactory.isDiagnosticsEnabled()     // Catch:{ all -> 0x0075 }
                    if (r3 == 0) goto L_0x0062
                    java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ all -> 0x0075 }
                    r3.<init>()     // Catch:{ all -> 0x0075 }
                    java.lang.String r4 = "Unable to read URL "
                    r3.append(r4)     // Catch:{ all -> 0x0075 }
                    java.net.URL r4 = r1     // Catch:{ all -> 0x0075 }
                    r3.append(r4)     // Catch:{ all -> 0x0075 }
                    java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0075 }
                    org.apache.commons.logging.LogFactory.logDiagnostic(r3)     // Catch:{ all -> 0x0075 }
                L_0x0062:
                    if (r2 == 0) goto L_0x0074
                    r2.close()     // Catch:{ IOException -> 0x0068 }
                    goto L_0x0074
                L_0x0068:
                    boolean r2 = org.apache.commons.logging.LogFactory.isDiagnosticsEnabled()
                    if (r2 == 0) goto L_0x0074
                    java.lang.StringBuffer r2 = new java.lang.StringBuffer
                    r2.<init>()
                    goto L_0x0030
                L_0x0074:
                    return r1
                L_0x0075:
                    r1 = move-exception
                L_0x0076:
                    if (r2 == 0) goto L_0x0096
                    r2.close()     // Catch:{ IOException -> 0x007c }
                    goto L_0x0096
                L_0x007c:
                    boolean r2 = org.apache.commons.logging.LogFactory.isDiagnosticsEnabled()
                    if (r2 == 0) goto L_0x0096
                    java.lang.StringBuffer r2 = new java.lang.StringBuffer
                    r2.<init>()
                    r2.append(r0)
                    java.net.URL r0 = r1
                    r2.append(r0)
                    java.lang.String r0 = r2.toString()
                    org.apache.commons.logging.LogFactory.logDiagnostic(r0)
                L_0x0096:
                    goto L_0x0098
                L_0x0097:
                    throw r1
                L_0x0098:
                    goto L_0x0097
                */
                throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.C60705.run():java.lang.Object");
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.util.Properties getConfigurationFile(java.lang.ClassLoader r14, java.lang.String r15) {
        /*
            r0 = 0
            java.util.Enumeration r14 = getResources(r14, r15)     // Catch:{ SecurityException -> 0x00dc }
            if (r14 != 0) goto L_0x0008
            return r0
        L_0x0008:
            r1 = 0
            r3 = r0
            r4 = r1
        L_0x000c:
            boolean r6 = r14.hasMoreElements()     // Catch:{ SecurityException -> 0x00da }
            if (r6 == 0) goto L_0x00e8
            java.lang.Object r6 = r14.nextElement()     // Catch:{ SecurityException -> 0x00da }
            java.net.URL r6 = (java.net.URL) r6     // Catch:{ SecurityException -> 0x00da }
            java.util.Properties r7 = getProperties(r6)     // Catch:{ SecurityException -> 0x00da }
            if (r7 == 0) goto L_0x000c
            java.lang.String r8 = "priority"
            java.lang.String r9 = " with priority "
            java.lang.String r10 = "'"
            if (r0 != 0) goto L_0x005d
            java.lang.String r0 = r7.getProperty(r8)     // Catch:{ SecurityException -> 0x0059 }
            if (r0 == 0) goto L_0x0031
            double r3 = java.lang.Double.parseDouble(r0)     // Catch:{ SecurityException -> 0x0059 }
            goto L_0x0032
        L_0x0031:
            r3 = r1
        L_0x0032:
            boolean r0 = isDiagnosticsEnabled()     // Catch:{ SecurityException -> 0x0059 }
            if (r0 == 0) goto L_0x0055
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ SecurityException -> 0x0059 }
            r0.<init>()     // Catch:{ SecurityException -> 0x0059 }
            java.lang.String r5 = "[LOOKUP] Properties file found at '"
            r0.append(r5)     // Catch:{ SecurityException -> 0x0059 }
            r0.append(r6)     // Catch:{ SecurityException -> 0x0059 }
            r0.append(r10)     // Catch:{ SecurityException -> 0x0059 }
            r0.append(r9)     // Catch:{ SecurityException -> 0x0059 }
            r0.append(r3)     // Catch:{ SecurityException -> 0x0059 }
            java.lang.String r0 = r0.toString()     // Catch:{ SecurityException -> 0x0059 }
            logDiagnostic(r0)     // Catch:{ SecurityException -> 0x0059 }
        L_0x0055:
            r4 = r3
            r3 = r6
            r0 = r7
            goto L_0x000c
        L_0x0059:
            r3 = r6
            r0 = r7
            goto L_0x00dd
        L_0x005d:
            java.lang.String r8 = r7.getProperty(r8)     // Catch:{ SecurityException -> 0x00da }
            if (r8 == 0) goto L_0x0068
            double r11 = java.lang.Double.parseDouble(r8)     // Catch:{ SecurityException -> 0x00da }
            goto L_0x0069
        L_0x0068:
            r11 = r1
        L_0x0069:
            java.lang.String r8 = "[LOOKUP] Properties file at '"
            int r13 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r13 <= 0) goto L_0x00a6
            boolean r13 = isDiagnosticsEnabled()     // Catch:{ SecurityException -> 0x00da }
            if (r13 == 0) goto L_0x00a1
            java.lang.StringBuffer r13 = new java.lang.StringBuffer     // Catch:{ SecurityException -> 0x00da }
            r13.<init>()     // Catch:{ SecurityException -> 0x00da }
            r13.append(r8)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r6)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r10)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r9)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r11)     // Catch:{ SecurityException -> 0x00da }
            java.lang.String r8 = " overrides file at '"
            r13.append(r8)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r3)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r10)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r9)     // Catch:{ SecurityException -> 0x00da }
            r13.append(r4)     // Catch:{ SecurityException -> 0x00da }
            java.lang.String r4 = r13.toString()     // Catch:{ SecurityException -> 0x00da }
            logDiagnostic(r4)     // Catch:{ SecurityException -> 0x00da }
        L_0x00a1:
            r3 = r6
            r0 = r7
            r4 = r11
            goto L_0x000c
        L_0x00a6:
            boolean r7 = isDiagnosticsEnabled()     // Catch:{ SecurityException -> 0x00da }
            if (r7 == 0) goto L_0x000c
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ SecurityException -> 0x00da }
            r7.<init>()     // Catch:{ SecurityException -> 0x00da }
            r7.append(r8)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r6)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r10)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r9)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r11)     // Catch:{ SecurityException -> 0x00da }
            java.lang.String r6 = " does not override file at '"
            r7.append(r6)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r3)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r10)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r9)     // Catch:{ SecurityException -> 0x00da }
            r7.append(r4)     // Catch:{ SecurityException -> 0x00da }
            java.lang.String r6 = r7.toString()     // Catch:{ SecurityException -> 0x00da }
            logDiagnostic(r6)     // Catch:{ SecurityException -> 0x00da }
            goto L_0x000c
        L_0x00da:
            goto L_0x00dd
        L_0x00dc:
            r3 = r0
        L_0x00dd:
            boolean r14 = isDiagnosticsEnabled()
            if (r14 == 0) goto L_0x00e8
            java.lang.String r14 = "SecurityException thrown while trying to find/read config files."
            logDiagnostic(r14)
        L_0x00e8:
            boolean r14 = isDiagnosticsEnabled()
            if (r14 == 0) goto L_0x012b
            if (r0 != 0) goto L_0x010a
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r14.<init>()
            java.lang.String r1 = "[LOOKUP] No properties file of name '"
            r14.append(r1)
            r14.append(r15)
            java.lang.String r15 = "' found."
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            logDiagnostic(r14)
            goto L_0x012b
        L_0x010a:
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r14.<init>()
            java.lang.String r1 = "[LOOKUP] Properties file of name '"
            r14.append(r1)
            r14.append(r15)
            java.lang.String r15 = "' found at '"
            r14.append(r15)
            r14.append(r3)
            r15 = 34
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            logDiagnostic(r14)
        L_0x012b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.getConfigurationFile(java.lang.ClassLoader, java.lang.String):java.util.Properties");
    }

    private static String getSystemProperty(final String str, final String str2) throws SecurityException {
        return (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty(str, str2);
            }
        });
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.io.PrintStream initDiagnostics() {
        /*
            r0 = 0
            java.lang.String r1 = "org.apache.commons.logging.diagnostics.dest"
            java.lang.String r1 = getSystemProperty(r1, r0)     // Catch:{ SecurityException -> 0x002c }
            if (r1 != 0) goto L_0x000a
            return r0
        L_0x000a:
            java.lang.String r2 = "STDOUT"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0015
            java.io.PrintStream r0 = java.lang.System.out
            return r0
        L_0x0015:
            java.lang.String r2 = "STDERR"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0020
            java.io.PrintStream r0 = java.lang.System.err
            return r0
        L_0x0020:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{  }
            r3 = 1
            r2.<init>(r1, r3)     // Catch:{  }
            java.io.PrintStream r1 = new java.io.PrintStream     // Catch:{  }
            r1.<init>(r2)     // Catch:{  }
            return r1
        L_0x002c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.initDiagnostics():java.io.PrintStream");
    }

    protected static boolean isDiagnosticsEnabled() {
        return diagnosticsStream != null;
    }

    /* access modifiers changed from: private */
    public static final void logDiagnostic(String str) {
        PrintStream printStream = diagnosticsStream;
        if (printStream != null) {
            printStream.print(diagnosticPrefix);
            diagnosticsStream.println(str);
            diagnosticsStream.flush();
        }
    }

    protected static final void logRawDiagnostic(String str) {
        PrintStream printStream = diagnosticsStream;
        if (printStream != null) {
            printStream.println(str);
            diagnosticsStream.flush();
        }
    }

    private static void logClassLoaderEnvironment(Class cls) {
        if (isDiagnosticsEnabled()) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("[ENV] Extension directories (java.ext.dir): ");
                stringBuffer.append(System.getProperty("java.ext.dir"));
                logDiagnostic(stringBuffer.toString());
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("[ENV] Application classpath (java.class.path): ");
                stringBuffer2.append(System.getProperty("java.class.path"));
                logDiagnostic(stringBuffer2.toString());
            } catch (SecurityException unused) {
                logDiagnostic("[ENV] Security setting prevent interrogation of system classpaths.");
            }
            String name = cls.getName();
            try {
                ClassLoader classLoader = getClassLoader(cls);
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[ENV] Class ");
                stringBuffer3.append(name);
                stringBuffer3.append(" was loaded via classloader ");
                stringBuffer3.append(objectId(classLoader));
                logDiagnostic(stringBuffer3.toString());
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[ENV] Ancestry of classloader which loaded ");
                stringBuffer4.append(name);
                stringBuffer4.append(" is ");
                logHierarchy(stringBuffer4.toString(), classLoader);
            } catch (SecurityException unused2) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("[ENV] Security forbids determining the classloader for ");
                stringBuffer5.append(name);
                logDiagnostic(stringBuffer5.toString());
            }
        }
    }

    private static void logHierarchy(String str, ClassLoader classLoader) {
        if (isDiagnosticsEnabled()) {
            if (classLoader != null) {
                String obj = classLoader.toString();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str);
                stringBuffer.append(objectId(classLoader));
                stringBuffer.append(" == '");
                stringBuffer.append(obj);
                stringBuffer.append("'");
                logDiagnostic(stringBuffer.toString());
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (classLoader != null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(str);
                    stringBuffer2.append("ClassLoader tree:");
                    StringBuffer stringBuffer3 = new StringBuffer(stringBuffer2.toString());
                    do {
                        stringBuffer3.append(objectId(classLoader));
                        if (classLoader == systemClassLoader) {
                            stringBuffer3.append(" (SYSTEM) ");
                        }
                        try {
                            classLoader = classLoader.getParent();
                            stringBuffer3.append(" --> ");
                        } catch (SecurityException unused) {
                            stringBuffer3.append(" --> SECRET");
                        }
                    } while (classLoader != null);
                    stringBuffer3.append("BOOT");
                    logDiagnostic(stringBuffer3.toString());
                }
            } catch (SecurityException unused2) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append(str);
                stringBuffer4.append("Security forbids determining the system classloader.");
                logDiagnostic(stringBuffer4.toString());
            }
        }
    }

    public static String objectId(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(obj.getClass().getName());
        stringBuffer.append("@");
        stringBuffer.append(System.identityHashCode(obj));
        return stringBuffer.toString();
    }

    static {
        String str;
        Class cls = class$org$apache$commons$logging$LogFactory;
        String str2 = FACTORY_PROPERTY;
        if (cls == null) {
            cls = class$(str2);
            class$org$apache$commons$logging$LogFactory = cls;
        }
        thisClassLoader = getClassLoader(cls);
        try {
            str = thisClassLoader == null ? "BOOTLOADER" : objectId(thisClassLoader);
        } catch (SecurityException unused) {
            str = "UNKNOWN";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LogFactory from ");
        stringBuffer.append(str);
        stringBuffer.append("] ");
        diagnosticPrefix = stringBuffer.toString();
        Class cls2 = class$org$apache$commons$logging$LogFactory;
        if (cls2 == null) {
            cls2 = class$(str2);
            class$org$apache$commons$logging$LogFactory = cls2;
        }
        logClassLoaderEnvironment(cls2);
        if (isDiagnosticsEnabled()) {
            logDiagnostic("BOOTSTRAP COMPLETED");
        }
    }
}
