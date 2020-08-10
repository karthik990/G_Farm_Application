package org.apache.commons.logging.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

public class LogFactoryImpl extends LogFactory {
    public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
    public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
    public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
    private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
    private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
    private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
    private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
    public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
    protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
    private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
    private static final int PKG_LEN = 32;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$org$apache$commons$logging$Log;
    static /* synthetic */ Class class$org$apache$commons$logging$LogFactory;
    static /* synthetic */ Class class$org$apache$commons$logging$impl$LogFactoryImpl;
    private static final String[] classesToDiscover = {LOGGING_IMPL_LOG4J_LOGGER, LOGGING_IMPL_JDK14_LOGGER, LOGGING_IMPL_LUMBERJACK_LOGGER, LOGGING_IMPL_SIMPLE_LOGGER};
    private boolean allowFlawedContext;
    private boolean allowFlawedDiscovery;
    private boolean allowFlawedHierarchy;
    protected Hashtable attributes = new Hashtable();
    private String diagnosticPrefix;
    protected Hashtable instances = new Hashtable();
    private String logClassName;
    protected Constructor logConstructor = null;
    protected Class[] logConstructorSignature;
    protected Method logMethod;
    protected Class[] logMethodSignature;
    private boolean useTCCL = true;

    public LogFactoryImpl() {
        Class[] clsArr = new Class[1];
        Class cls = class$java$lang$String;
        if (cls == null) {
            cls = class$("java.lang.String");
            class$java$lang$String = cls;
        }
        clsArr[0] = cls;
        this.logConstructorSignature = clsArr;
        this.logMethod = null;
        Class[] clsArr2 = new Class[1];
        Class cls2 = class$org$apache$commons$logging$LogFactory;
        if (cls2 == null) {
            cls2 = class$(LogFactory.FACTORY_PROPERTY);
            class$org$apache$commons$logging$LogFactory = cls2;
        }
        clsArr2[0] = cls2;
        this.logMethodSignature = clsArr2;
        initDiagnostics();
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Instance created.");
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public Object getAttribute(String str) {
        return this.attributes.get(str);
    }

    public String[] getAttributeNames() {
        return (String[]) this.attributes.keySet().toArray(new String[this.attributes.size()]);
    }

    public Log getInstance(Class cls) throws LogConfigurationException {
        return getInstance(cls.getName());
    }

    public Log getInstance(String str) throws LogConfigurationException {
        Log log = (Log) this.instances.get(str);
        if (log != null) {
            return log;
        }
        Log newInstance = newInstance(str);
        this.instances.put(str, newInstance);
        return newInstance;
    }

    public void release() {
        logDiagnostic("Releasing all known loggers");
        this.instances.clear();
    }

    public void removeAttribute(String str) {
        this.attributes.remove(str);
    }

    public void setAttribute(String str, Object obj) {
        if (this.logConstructor != null) {
            logDiagnostic("setAttribute: call too late; configuration already performed.");
        }
        if (obj == null) {
            this.attributes.remove(str);
        } else {
            this.attributes.put(str, obj);
        }
        if (str.equals(LogFactory.TCCL_KEY)) {
            this.useTCCL = obj != null && Boolean.valueOf(obj.toString()).booleanValue();
        }
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.getContextClassLoader();
    }

    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.isDiagnosticsEnabled();
    }

    protected static ClassLoader getClassLoader(Class cls) {
        return LogFactory.getClassLoader(cls);
    }

    private void initDiagnostics() {
        String str;
        ClassLoader classLoader = getClassLoader(getClass());
        if (classLoader == null) {
            str = "BOOTLOADER";
        } else {
            try {
                str = objectId(classLoader);
            } catch (SecurityException unused) {
                str = "UNKNOWN";
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LogFactoryImpl@");
        stringBuffer.append(System.identityHashCode(this));
        stringBuffer.append(" from ");
        stringBuffer.append(str);
        stringBuffer.append("] ");
        this.diagnosticPrefix = stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public void logDiagnostic(String str) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.diagnosticPrefix);
            stringBuffer.append(str);
            logRawDiagnostic(stringBuffer.toString());
        }
    }

    /* access modifiers changed from: protected */
    public String getLogClassName() {
        if (this.logClassName == null) {
            discoverLogImplementation(getClass().getName());
        }
        return this.logClassName;
    }

    /* access modifiers changed from: protected */
    public Constructor getLogConstructor() throws LogConfigurationException {
        if (this.logConstructor == null) {
            discoverLogImplementation(getClass().getName());
        }
        return this.logConstructor;
    }

    /* access modifiers changed from: protected */
    public boolean isJdk13LumberjackAvailable() {
        return isLogLibraryAvailable("Jdk13Lumberjack", LOGGING_IMPL_LUMBERJACK_LOGGER);
    }

    /* access modifiers changed from: protected */
    public boolean isJdk14Available() {
        return isLogLibraryAvailable("Jdk14", LOGGING_IMPL_JDK14_LOGGER);
    }

    /* access modifiers changed from: protected */
    public boolean isLog4JAvailable() {
        return isLogLibraryAvailable("Log4J", LOGGING_IMPL_LOG4J_LOGGER);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r4v3, types: [java.lang.Throwable] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.commons.logging.Log newInstance(java.lang.String r4) throws org.apache.commons.logging.LogConfigurationException {
        /*
            r3 = this;
            java.lang.reflect.Constructor r0 = r3.logConstructor     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x000b
            org.apache.commons.logging.Log r4 = r3.discoverLogImplementation(r4)     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            goto L_0x0017
        L_0x000b:
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            r0[r1] = r4     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            java.lang.reflect.Constructor r4 = r3.logConstructor     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            java.lang.Object r4 = r4.newInstance(r0)     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            org.apache.commons.logging.Log r4 = (org.apache.commons.logging.Log) r4     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
        L_0x0017:
            java.lang.reflect.Method r0 = r3.logMethod     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            if (r0 == 0) goto L_0x0024
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            r0[r1] = r3     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            java.lang.reflect.Method r1 = r3.logMethod     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
            r1.invoke(r4, r0)     // Catch:{ LogConfigurationException -> 0x003e, InvocationTargetException -> 0x002f, all -> 0x0025 }
        L_0x0024:
            return r4
        L_0x0025:
            r4 = move-exception
            handleThrowable(r4)
            org.apache.commons.logging.LogConfigurationException r0 = new org.apache.commons.logging.LogConfigurationException
            r0.<init>(r4)
            throw r0
        L_0x002f:
            r4 = move-exception
            java.lang.Throwable r0 = r4.getTargetException()
            org.apache.commons.logging.LogConfigurationException r1 = new org.apache.commons.logging.LogConfigurationException
            if (r0 != 0) goto L_0x0039
            goto L_0x003a
        L_0x0039:
            r4 = r0
        L_0x003a:
            r1.<init>(r4)
            throw r1
        L_0x003e:
            r4 = move-exception
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.impl.LogFactoryImpl.newInstance(java.lang.String):org.apache.commons.logging.Log");
    }

    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return LogFactoryImpl.directGetContextClassLoader();
            }
        });
    }

    private static String getSystemProperty(final String str, final String str2) throws SecurityException {
        return (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty(str, str2);
            }
        });
    }

    private ClassLoader getParentClassLoader(final ClassLoader classLoader) {
        try {
            return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    return classLoader.getParent();
                }
            });
        } catch (SecurityException unused) {
            logDiagnostic("[SECURITY] Unable to obtain parent classloader");
            return null;
        }
    }

    private boolean isLogLibraryAvailable(String str, String str2) {
        String str3 = "'.";
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Checking for '");
            stringBuffer.append(str);
            stringBuffer.append(str3);
            logDiagnostic(stringBuffer.toString());
        }
        try {
            if (createLogFromClass(str2, getClass().getName(), false) == null) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Did not find '");
                    stringBuffer2.append(str);
                    stringBuffer2.append(str3);
                    logDiagnostic(stringBuffer2.toString());
                }
                return false;
            }
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Found '");
                stringBuffer3.append(str);
                stringBuffer3.append(str3);
                logDiagnostic(stringBuffer3.toString());
            }
            return true;
        } catch (LogConfigurationException unused) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Logging system '");
                stringBuffer4.append(str);
                stringBuffer4.append("' is available but not useable.");
                logDiagnostic(stringBuffer4.toString());
            }
            return false;
        }
    }

    private String getConfigurationValue(String str) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[ENV] Trying to get configuration for item ");
            stringBuffer.append(str);
            logDiagnostic(stringBuffer.toString());
        }
        Object attribute = getAttribute(str);
        String str2 = "] for ";
        if (attribute != null) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("[ENV] Found LogFactory attribute [");
                stringBuffer2.append(attribute);
                stringBuffer2.append(str2);
                stringBuffer2.append(str);
                logDiagnostic(stringBuffer2.toString());
            }
            return attribute.toString();
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("[ENV] No LogFactory attribute found for ");
            stringBuffer3.append(str);
            logDiagnostic(stringBuffer3.toString());
        }
        try {
            String systemProperty = getSystemProperty(str, null);
            if (systemProperty != null) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append("[ENV] Found system property [");
                    stringBuffer4.append(systemProperty);
                    stringBuffer4.append(str2);
                    stringBuffer4.append(str);
                    logDiagnostic(stringBuffer4.toString());
                }
                return systemProperty;
            }
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("[ENV] No system property found for property ");
                stringBuffer5.append(str);
                logDiagnostic(stringBuffer5.toString());
            }
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("[ENV] No configuration defined for item ");
                stringBuffer6.append(str);
                logDiagnostic(stringBuffer6.toString());
            }
            return null;
        } catch (SecurityException unused) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer7 = new StringBuffer();
                stringBuffer7.append("[ENV] Security prevented reading system property ");
                stringBuffer7.append(str);
                logDiagnostic(stringBuffer7.toString());
            }
        }
    }

    private boolean getBooleanConfiguration(String str, boolean z) {
        String configurationValue = getConfigurationValue(str);
        if (configurationValue == null) {
            return z;
        }
        return Boolean.valueOf(configurationValue).booleanValue();
    }

    private void initConfiguration() {
        this.allowFlawedContext = getBooleanConfiguration(ALLOW_FLAWED_CONTEXT_PROPERTY, true);
        this.allowFlawedDiscovery = getBooleanConfiguration(ALLOW_FLAWED_DISCOVERY_PROPERTY, true);
        this.allowFlawedHierarchy = getBooleanConfiguration(ALLOW_FLAWED_HIERARCHY_PROPERTY, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0087 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.commons.logging.Log discoverLogImplementation(java.lang.String r6) throws org.apache.commons.logging.LogConfigurationException {
        /*
            r5 = this;
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "Discovering a Log implementation..."
            r5.logDiagnostic(r0)
        L_0x000b:
            r5.initConfiguration()
            r0 = 0
            java.lang.String r1 = r5.findUserSpecifiedLogClassName()
            r2 = 1
            if (r1 == 0) goto L_0x0069
            boolean r0 = isDiagnosticsEnabled()
            if (r0 == 0) goto L_0x0035
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r3 = "Attempting to load user-specified log class '"
            r0.append(r3)
            r0.append(r1)
            java.lang.String r3 = "'..."
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            r5.logDiagnostic(r0)
        L_0x0035:
            org.apache.commons.logging.Log r6 = r5.createLogFromClass(r1, r6, r2)
            if (r6 == 0) goto L_0x003c
            return r6
        L_0x003c:
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            java.lang.String r0 = "User-specified log class '"
            r6.<init>(r0)
            r6.append(r1)
            java.lang.String r0 = "' cannot be found or is not useable."
            r6.append(r0)
            java.lang.String r0 = "org.apache.commons.logging.impl.Log4JLogger"
            r5.informUponSimilarName(r6, r1, r0)
            java.lang.String r0 = "org.apache.commons.logging.impl.Jdk14Logger"
            r5.informUponSimilarName(r6, r1, r0)
            java.lang.String r0 = "org.apache.commons.logging.impl.Jdk13LumberjackLogger"
            r5.informUponSimilarName(r6, r1, r0)
            java.lang.String r0 = "org.apache.commons.logging.impl.SimpleLog"
            r5.informUponSimilarName(r6, r1, r0)
            org.apache.commons.logging.LogConfigurationException r0 = new org.apache.commons.logging.LogConfigurationException
            java.lang.String r6 = r6.toString()
            r0.<init>(r6)
            throw r0
        L_0x0069:
            boolean r1 = isDiagnosticsEnabled()
            if (r1 == 0) goto L_0x0074
            java.lang.String r1 = "No user-specified Log implementation; performing discovery using the standard supported logging implementations..."
            r5.logDiagnostic(r1)
        L_0x0074:
            r1 = 0
        L_0x0075:
            java.lang.String[] r3 = classesToDiscover
            int r4 = r3.length
            if (r1 >= r4) goto L_0x0085
            if (r0 != 0) goto L_0x0085
            r0 = r3[r1]
            org.apache.commons.logging.Log r0 = r5.createLogFromClass(r0, r6, r2)
            int r1 = r1 + 1
            goto L_0x0075
        L_0x0085:
            if (r0 == 0) goto L_0x0088
            return r0
        L_0x0088:
            org.apache.commons.logging.LogConfigurationException r6 = new org.apache.commons.logging.LogConfigurationException
            java.lang.String r0 = "No suitable Log implementation"
            r6.<init>(r0)
            goto L_0x0091
        L_0x0090:
            throw r6
        L_0x0091:
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.impl.LogFactoryImpl.discoverLogImplementation(java.lang.String):org.apache.commons.logging.Log");
    }

    private void informUponSimilarName(StringBuffer stringBuffer, String str, String str2) {
        if (!str.equals(str2)) {
            if (str.regionMatches(true, 0, str2, 0, PKG_LEN + 5)) {
                stringBuffer.append(" Did you mean '");
                stringBuffer.append(str2);
                stringBuffer.append("'?");
            }
        }
    }

    private String findUserSpecifiedLogClassName() {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
        }
        String str = LOG_PROPERTY;
        String str2 = (String) getAttribute(str);
        String str3 = LOG_PROPERTY_OLD;
        if (str2 == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
            }
            str2 = (String) getAttribute(str3);
        }
        if (str2 == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
            }
            try {
                str2 = getSystemProperty(str, null);
            } catch (SecurityException e) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No access allowed to system property 'org.apache.commons.logging.Log' - ");
                    stringBuffer.append(e.getMessage());
                    logDiagnostic(stringBuffer.toString());
                }
            }
        }
        if (str2 == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
            }
            try {
                str2 = getSystemProperty(str3, null);
            } catch (SecurityException e2) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("No access allowed to system property 'org.apache.commons.logging.log' - ");
                    stringBuffer2.append(e2.getMessage());
                    logDiagnostic(stringBuffer2.toString());
                }
            }
        }
        return str2 != null ? str2.trim() : str2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x011c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x011d, code lost:
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x011f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0120, code lost:
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0122, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0123, code lost:
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x015e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x015f, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0158 A[LOOP:0: B:4:0x0037->B:45:0x0158, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x015e A[ExcHandler: LogConfigurationException (r0v24 'e' org.apache.commons.logging.LogConfigurationException A[CUSTOM_DECLARE]), Splitter:B:5:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01bb A[EDGE_INSN: B:63:0x01bb->B:52:0x01bb ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.commons.logging.Log createLogFromClass(java.lang.String r17, java.lang.String r18, boolean r19) throws org.apache.commons.logging.LogConfigurationException {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            java.lang.String r3 = ".class"
            java.lang.String r4 = ": "
            java.lang.String r5 = "The log adapter '"
            boolean r0 = isDiagnosticsEnabled()
            java.lang.String r6 = "'"
            if (r0 == 0) goto L_0x0029
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r7 = "Attempting to instantiate '"
            r0.append(r7)
            r0.append(r2)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            r1.logDiagnostic(r0)
        L_0x0029:
            r7 = 1
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r0 = 0
            r8[r0] = r18
            java.lang.ClassLoader r0 = r16.getBaseClassLoader()
            r9 = 0
            r10 = r0
            r11 = r9
            r12 = r11
        L_0x0037:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r13 = "Trying to load '"
            r0.append(r13)
            r0.append(r2)
            java.lang.String r13 = "' from classloader "
            r0.append(r13)
            java.lang.String r14 = objectId(r10)
            r0.append(r14)
            java.lang.String r0 = r0.toString()
            r1.logDiagnostic(r0)
            boolean r0 = isDiagnosticsEnabled()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            if (r0 == 0) goto L_0x00cf
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14 = 46
            r15 = 47
            java.lang.String r14 = r2.replace(r14, r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r14)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r3)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r0.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            if (r10 == 0) goto L_0x007b
            java.net.URL r14 = r10.getResource(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            goto L_0x008e
        L_0x007b:
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r3)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r14 = r14.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.net.URL r14 = java.lang.ClassLoader.getSystemResource(r14)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
        L_0x008e:
            java.lang.String r15 = "Class '"
            if (r14 != 0) goto L_0x00b2
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r2)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r15 = "' ["
            r14.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = "] cannot be found."
            r14.append(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r14.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r1.logDiagnostic(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            goto L_0x00cf
        L_0x00b2:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r2)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r15 = "' was found at '"
            r0.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r14)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r0.append(r6)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r0.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r1.logDiagnostic(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
        L_0x00cf:
            java.lang.Class r0 = java.lang.Class.forName(r2, r7, r10)     // Catch:{ ClassNotFoundException -> 0x00d5 }
        L_0x00d3:
            r14 = r0
            goto L_0x0108
        L_0x00d5:
            r0 = move-exception
            r14 = r0
            java.lang.String r0 = r14.getMessage()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r5)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r2)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r15 = "' is not available via classloader "
            r14.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r15 = objectId(r10)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r4)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r0.trim()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r14.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r1.logDiagnostic(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.Class r0 = java.lang.Class.forName(r17)     // Catch:{ ClassNotFoundException -> 0x0129 }
            goto L_0x00d3
        L_0x0108:
            java.lang.Class[] r0 = r1.logConstructorSignature     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.reflect.Constructor r11 = r14.getConstructor(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.Object r0 = r11.newInstance(r8)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            boolean r15 = r0 instanceof org.apache.commons.logging.Log     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            if (r15 == 0) goto L_0x0125
            org.apache.commons.logging.Log r0 = (org.apache.commons.logging.Log) r0     // Catch:{ NoClassDefFoundError -> 0x0122, ExceptionInInitializerError -> 0x011f, LogConfigurationException -> 0x015e, all -> 0x011c }
            r3 = r0
            r12 = r14
            goto L_0x01bc
        L_0x011c:
            r0 = move-exception
            r12 = r14
            goto L_0x014f
        L_0x011f:
            r0 = move-exception
            r12 = r14
            goto L_0x0161
        L_0x0122:
            r0 = move-exception
            r12 = r14
            goto L_0x018f
        L_0x0125:
            r1.handleFlawedHierarchy(r10, r14)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            goto L_0x0155
        L_0x0129:
            r0 = move-exception
            r14 = r0
            java.lang.String r0 = r14.getMessage()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.<init>()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r5)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r2)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r15 = "' is not available via the LogFactoryImpl class classloader: "
            r14.append(r15)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r0.trim()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r14.append(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            java.lang.String r0 = r14.toString()     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            r1.logDiagnostic(r0)     // Catch:{ NoClassDefFoundError -> 0x018e, ExceptionInInitializerError -> 0x0160, LogConfigurationException -> 0x015e, all -> 0x014e }
            goto L_0x01bb
        L_0x014e:
            r0 = move-exception
        L_0x014f:
            handleThrowable(r0)
            r1.handleFlawedDiscovery(r2, r10, r0)
        L_0x0155:
            if (r10 != 0) goto L_0x0158
            goto L_0x01bb
        L_0x0158:
            java.lang.ClassLoader r10 = r1.getParentClassLoader(r10)
            goto L_0x0037
        L_0x015e:
            r0 = move-exception
            throw r0
        L_0x0160:
            r0 = move-exception
        L_0x0161:
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            r3.append(r5)
            r3.append(r2)
            java.lang.String r5 = "' is unable to initialize itself when loaded via classloader "
            r3.append(r5)
            java.lang.String r5 = objectId(r10)
            r3.append(r5)
            r3.append(r4)
            java.lang.String r0 = r0.trim()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r1.logDiagnostic(r0)
            goto L_0x01bb
        L_0x018e:
            r0 = move-exception
        L_0x018f:
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            r3.append(r5)
            r3.append(r2)
            java.lang.String r5 = "' is missing dependencies when loaded via classloader "
            r3.append(r5)
            java.lang.String r5 = objectId(r10)
            r3.append(r5)
            r3.append(r4)
            java.lang.String r0 = r0.trim()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r1.logDiagnostic(r0)
        L_0x01bb:
            r3 = r9
        L_0x01bc:
            if (r12 == 0) goto L_0x023b
            if (r19 == 0) goto L_0x023b
            r1.logClassName = r2
            r1.logConstructor = r11
            java.lang.String r0 = "setLogFactory"
            java.lang.Class[] r4 = r1.logMethodSignature     // Catch:{ all -> 0x01e6 }
            java.lang.reflect.Method r0 = r12.getMethod(r0, r4)     // Catch:{ all -> 0x01e6 }
            r1.logMethod = r0     // Catch:{ all -> 0x01e6 }
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ all -> 0x01e6 }
            r0.<init>()     // Catch:{ all -> 0x01e6 }
            java.lang.String r4 = "Found method setLogFactory(LogFactory) in '"
            r0.append(r4)     // Catch:{ all -> 0x01e6 }
            r0.append(r2)     // Catch:{ all -> 0x01e6 }
            r0.append(r6)     // Catch:{ all -> 0x01e6 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01e6 }
            r1.logDiagnostic(r0)     // Catch:{ all -> 0x01e6 }
            goto L_0x0214
        L_0x01e6:
            r0 = move-exception
            handleThrowable(r0)
            r1.logMethod = r9
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r4 = "[INFO] '"
            r0.append(r4)
            r0.append(r2)
            r0.append(r13)
            java.lang.String r4 = objectId(r10)
            r0.append(r4)
            java.lang.String r4 = " does not declare optional method "
            r0.append(r4)
            java.lang.String r4 = "setLogFactory(LogFactory)"
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            r1.logDiagnostic(r0)
        L_0x0214:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r4 = "Log adapter '"
            r0.append(r4)
            r0.append(r2)
            r0.append(r13)
            java.lang.ClassLoader r2 = r12.getClassLoader()
            java.lang.String r2 = objectId(r2)
            r0.append(r2)
            java.lang.String r2 = " has been selected for use."
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.logDiagnostic(r0)
        L_0x023b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.impl.LogFactoryImpl.createLogFromClass(java.lang.String, java.lang.String, boolean):org.apache.commons.logging.Log");
    }

    private ClassLoader getBaseClassLoader() throws LogConfigurationException {
        Class cls = class$org$apache$commons$logging$impl$LogFactoryImpl;
        if (cls == null) {
            cls = class$(LogFactory.FACTORY_DEFAULT);
            class$org$apache$commons$logging$impl$LogFactoryImpl = cls;
        }
        ClassLoader classLoader = getClassLoader(cls);
        if (!this.useTCCL) {
            return classLoader;
        }
        ClassLoader contextClassLoaderInternal = getContextClassLoaderInternal();
        ClassLoader lowestClassLoader = getLowestClassLoader(contextClassLoaderInternal, classLoader);
        String str = "Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.";
        if (lowestClassLoader != null) {
            if (lowestClassLoader != contextClassLoaderInternal) {
                if (!this.allowFlawedContext) {
                    throw new LogConfigurationException(str);
                } else if (isDiagnosticsEnabled()) {
                    logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
                }
            }
            return lowestClassLoader;
        } else if (this.allowFlawedContext) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
            }
            return contextClassLoaderInternal;
        } else {
            throw new LogConfigurationException(str);
        }
    }

    private ClassLoader getLowestClassLoader(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader == null) {
            return classLoader2;
        }
        if (classLoader2 == null) {
            return classLoader;
        }
        ClassLoader classLoader3 = classLoader;
        while (classLoader3 != null) {
            if (classLoader3 == classLoader2) {
                return classLoader;
            }
            classLoader3 = getParentClassLoader(classLoader3);
        }
        ClassLoader classLoader4 = classLoader2;
        while (classLoader4 != null) {
            if (classLoader4 == classLoader) {
                return classLoader2;
            }
            classLoader4 = getParentClassLoader(classLoader4);
        }
        return null;
    }

    private void handleFlawedDiscovery(String str, ClassLoader classLoader, Throwable th) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not instantiate Log '");
            stringBuffer.append(str);
            stringBuffer.append("' -- ");
            stringBuffer.append(th.getClass().getName());
            String str2 = ": ";
            stringBuffer.append(str2);
            stringBuffer.append(th.getLocalizedMessage());
            logDiagnostic(stringBuffer.toString());
            if (th instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) th).getTargetException();
                if (targetException != null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("... InvocationTargetException: ");
                    stringBuffer2.append(targetException.getClass().getName());
                    stringBuffer2.append(str2);
                    stringBuffer2.append(targetException.getLocalizedMessage());
                    logDiagnostic(stringBuffer2.toString());
                    if (targetException instanceof ExceptionInInitializerError) {
                        Throwable exception = ((ExceptionInInitializerError) targetException).getException();
                        if (exception != null) {
                            StringWriter stringWriter = new StringWriter();
                            exception.printStackTrace(new PrintWriter(stringWriter, true));
                            StringBuffer stringBuffer3 = new StringBuffer();
                            stringBuffer3.append("... ExceptionInInitializerError: ");
                            stringBuffer3.append(stringWriter.toString());
                            logDiagnostic(stringBuffer3.toString());
                        }
                    }
                }
            }
        }
        if (!this.allowFlawedDiscovery) {
            throw new LogConfigurationException(th);
        }
    }

    private void handleFlawedHierarchy(ClassLoader classLoader, Class cls) throws LogConfigurationException {
        Class cls2;
        Class cls3 = class$org$apache$commons$logging$Log;
        String str = LOG_PROPERTY;
        if (cls3 == null) {
            cls3 = class$(str);
            class$org$apache$commons$logging$Log = cls3;
        }
        String name = cls3.getName();
        Class[] interfaces = cls.getInterfaces();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= interfaces.length) {
                break;
            } else if (name.equals(interfaces[i].getName())) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            if (isDiagnosticsEnabled()) {
                try {
                    if (class$org$apache$commons$logging$Log == null) {
                        cls2 = class$(str);
                        class$org$apache$commons$logging$Log = cls2;
                    } else {
                        cls2 = class$org$apache$commons$logging$Log;
                    }
                    ClassLoader classLoader2 = getClassLoader(cls2);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Class '");
                    stringBuffer.append(cls.getName());
                    stringBuffer.append("' was found in classloader ");
                    stringBuffer.append(objectId(classLoader));
                    stringBuffer.append(". It is bound to a Log interface which is not");
                    stringBuffer.append(" the one loaded from classloader ");
                    stringBuffer.append(objectId(classLoader2));
                    logDiagnostic(stringBuffer.toString());
                } catch (Throwable th) {
                    handleThrowable(th);
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Error while trying to output diagnostics about bad class '");
                    stringBuffer2.append(cls);
                    stringBuffer2.append("'");
                    logDiagnostic(stringBuffer2.toString());
                }
            }
            String str2 = "' visible.";
            String str3 = "You have more than one version of '";
            if (!this.allowFlawedHierarchy) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Terminating logging for this context ");
                stringBuffer3.append("due to bad log hierarchy. ");
                stringBuffer3.append(str3);
                Class cls4 = class$org$apache$commons$logging$Log;
                if (cls4 == null) {
                    cls4 = class$(str);
                    class$org$apache$commons$logging$Log = cls4;
                }
                stringBuffer3.append(cls4.getName());
                stringBuffer3.append(str2);
                if (isDiagnosticsEnabled()) {
                    logDiagnostic(stringBuffer3.toString());
                }
                throw new LogConfigurationException(stringBuffer3.toString());
            } else if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Warning: bad log hierarchy. ");
                stringBuffer4.append(str3);
                Class cls5 = class$org$apache$commons$logging$Log;
                if (cls5 == null) {
                    cls5 = class$(str);
                    class$org$apache$commons$logging$Log = cls5;
                }
                stringBuffer4.append(cls5.getName());
                stringBuffer4.append(str2);
                logDiagnostic(stringBuffer4.toString());
            }
        } else {
            String str4 = "' does not implement the Log interface.";
            if (!this.allowFlawedDiscovery) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("Terminating logging for this context. ");
                stringBuffer5.append("Log class '");
                stringBuffer5.append(cls.getName());
                stringBuffer5.append(str4);
                if (isDiagnosticsEnabled()) {
                    logDiagnostic(stringBuffer5.toString());
                }
                throw new LogConfigurationException(stringBuffer5.toString());
            } else if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("[WARNING] Log class '");
                stringBuffer6.append(cls.getName());
                stringBuffer6.append(str4);
                logDiagnostic(stringBuffer6.toString());
            }
        }
    }
}
