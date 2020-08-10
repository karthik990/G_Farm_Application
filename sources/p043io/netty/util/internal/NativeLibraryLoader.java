package p043io.netty.util.internal;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.internal.NativeLibraryLoader */
public final class NativeLibraryLoader {
    private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final String OSNAME;
    private static final File WORKDIR;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);

    /* renamed from: io.netty.util.internal.NativeLibraryLoader$NoexecVolumeDetector */
    private static final class NoexecVolumeDetector {
        /* access modifiers changed from: private */
        public static boolean canExecuteExecutable(File file) throws IOException {
            if (PlatformDependent.javaVersion() < 7 || file.canExecute()) {
                return true;
            }
            Set posixFilePermissions = Files.getPosixFilePermissions(file.toPath(), new LinkOption[0]);
            EnumSet of = EnumSet.of(PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);
            if (posixFilePermissions.containsAll(of)) {
                return false;
            }
            EnumSet copyOf = EnumSet.copyOf(posixFilePermissions);
            copyOf.addAll(of);
            Files.setPosixFilePermissions(file.toPath(), copyOf);
            return file.canExecute();
        }

        private NoexecVolumeDetector() {
        }
    }

    static {
        String str = "";
        OSNAME = SystemPropertyUtil.get("os.name", str).toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", str);
        String str2 = SystemPropertyUtil.get("io.netty.native.workdir");
        String str3 = "-Dio.netty.native.workdir: ";
        if (str2 != null) {
            File file = new File(str2);
            file.mkdirs();
            try {
                file = file.getAbsoluteFile();
            } catch (Exception unused) {
            }
            WORKDIR = file;
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(WORKDIR);
            internalLogger.debug(sb.toString());
        } else {
            WORKDIR = tmpdir();
            InternalLogger internalLogger2 = logger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(WORKDIR);
            sb2.append(" (io.netty.tmpdir)");
            internalLogger2.debug(sb2.toString());
        }
    }

    private static File tmpdir() {
        File file;
        try {
            File directory = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
            String str = "-Dio.netty.tmpdir: ";
            if (directory != null) {
                InternalLogger internalLogger = logger;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(directory);
                internalLogger.debug(sb.toString());
                return directory;
            }
            File directory2 = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
            if (directory2 != null) {
                InternalLogger internalLogger2 = logger;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(directory2);
                sb2.append(" (java.io.tmpdir)");
                internalLogger2.debug(sb2.toString());
                return directory2;
            }
            if (isWindows()) {
                File directory3 = toDirectory(System.getenv("TEMP"));
                if (directory3 != null) {
                    InternalLogger internalLogger3 = logger;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(directory3);
                    sb3.append(" (%TEMP%)");
                    internalLogger3.debug(sb3.toString());
                    return directory3;
                }
                String str2 = System.getenv("USERPROFILE");
                if (str2 != null) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append("\\AppData\\Local\\Temp");
                    File directory4 = toDirectory(sb4.toString());
                    if (directory4 != null) {
                        InternalLogger internalLogger4 = logger;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(str);
                        sb5.append(directory4);
                        sb5.append(" (%USERPROFILE%\\AppData\\Local\\Temp)");
                        internalLogger4.debug(sb5.toString());
                        return directory4;
                    }
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str2);
                    sb6.append("\\Local Settings\\Temp");
                    File directory5 = toDirectory(sb6.toString());
                    if (directory5 != null) {
                        InternalLogger internalLogger5 = logger;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str);
                        sb7.append(directory5);
                        sb7.append(" (%USERPROFILE%\\Local Settings\\Temp)");
                        internalLogger5.debug(sb7.toString());
                        return directory5;
                    }
                }
            } else {
                File directory6 = toDirectory(System.getenv("TMPDIR"));
                if (directory6 != null) {
                    InternalLogger internalLogger6 = logger;
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(str);
                    sb8.append(directory6);
                    sb8.append(" ($TMPDIR)");
                    internalLogger6.debug(sb8.toString());
                    return directory6;
                }
            }
            if (isWindows()) {
                file = new File("C:\\Windows\\Temp");
            } else {
                file = new File("/tmp");
            }
            InternalLogger internalLogger7 = logger;
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Failed to get the temporary directory; falling back to: ");
            sb9.append(file);
            internalLogger7.warn(sb9.toString());
            return file;
        } catch (Exception unused) {
        }
    }

    private static File toDirectory(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        try {
            return file.getAbsoluteFile();
        } catch (Exception unused) {
            return file;
        }
    }

    private static boolean isWindows() {
        return OSNAME.startsWith("windows");
    }

    private static boolean isOSX() {
        return OSNAME.startsWith("macosx") || OSNAME.startsWith("osx");
    }

    public static void loadFirstAvailable(ClassLoader classLoader, String... strArr) {
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            try {
                load(str, classLoader);
                logger.debug("Successfully loaded the library: {}", (Object) str);
                return;
            } catch (Throwable th) {
                logger.debug("Unable to load the library '{}', trying next name...", str, th);
                i++;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to load any of the given libraries: ");
        sb.append(Arrays.toString(strArr));
        throw new IllegalArgumentException(sb.toString());
    }

    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.File] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.lang.Object, java.io.File] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r0v11, types: [java.io.File] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v4
      assigns: []
      uses: []
      mth insns count: 151
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x011b A[SYNTHETIC, Splitter:B:65:0x011b] */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void load(java.lang.String r7, java.lang.ClassLoader r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "io.netty.packagePrefix"
            java.lang.String r2 = ""
            java.lang.String r1 = p043io.netty.util.internal.SystemPropertyUtil.get(r1, r2)
            r2 = 46
            r3 = 45
            java.lang.String r1 = r1.replace(r2, r3)
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            java.lang.String r0 = java.lang.System.mapLibraryName(r7)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "META-INF/native/"
            r1.append(r3)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.net.URL r3 = r8.getResource(r1)
            if (r3 != 0) goto L_0x0079
            boolean r4 = isOSX()
            if (r4 == 0) goto L_0x0079
            java.lang.String r3 = ".jnilib"
            boolean r1 = r1.endsWith(r3)
            java.lang.String r4 = "META-INF/native/lib"
            if (r1 == 0) goto L_0x0063
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            r1.append(r7)
            java.lang.String r3 = ".dynlib"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.net.URL r3 = r8.getResource(r1)
            goto L_0x0079
        L_0x0063:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            r1.append(r7)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.net.URL r3 = r8.getResource(r1)
        L_0x0079:
            r1 = 0
            if (r3 != 0) goto L_0x0080
            loadLibrary(r8, r7, r1)
            return
        L_0x0080:
            int r2 = r0.lastIndexOf(r2)
            java.lang.String r4 = r0.substring(r1, r2)
            int r5 = r0.length()
            java.lang.String r0 = r0.substring(r2, r5)
            r2 = 0
            java.io.File r5 = WORKDIR     // Catch:{ UnsatisfiedLinkError -> 0x0116, Exception -> 0x00f4, all -> 0x00f0 }
            java.io.File r0 = java.io.File.createTempFile(r4, r0, r5)     // Catch:{ UnsatisfiedLinkError -> 0x0116, Exception -> 0x00f4, all -> 0x00f0 }
            java.io.InputStream r3 = r3.openStream()     // Catch:{ UnsatisfiedLinkError -> 0x00eb, Exception -> 0x00e8, all -> 0x00e4 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ UnsatisfiedLinkError -> 0x00e1, Exception -> 0x00dd, all -> 0x00d8 }
            r4.<init>(r0)     // Catch:{ UnsatisfiedLinkError -> 0x00e1, Exception -> 0x00dd, all -> 0x00d8 }
            r5 = 8192(0x2000, float:1.14794E-41)
            byte[] r5 = new byte[r5]     // Catch:{ UnsatisfiedLinkError -> 0x00d6, Exception -> 0x00d4, all -> 0x00d2 }
        L_0x00a4:
            int r6 = r3.read(r5)     // Catch:{ UnsatisfiedLinkError -> 0x00d6, Exception -> 0x00d4, all -> 0x00d2 }
            if (r6 <= 0) goto L_0x00ae
            r4.write(r5, r1, r6)     // Catch:{ UnsatisfiedLinkError -> 0x00d6, Exception -> 0x00d4, all -> 0x00d2 }
            goto L_0x00a4
        L_0x00ae:
            r4.flush()     // Catch:{ UnsatisfiedLinkError -> 0x00d6, Exception -> 0x00d4, all -> 0x00d2 }
            closeQuietly(r4)     // Catch:{ UnsatisfiedLinkError -> 0x00d6, Exception -> 0x00d4, all -> 0x00d2 }
            java.lang.String r1 = r0.getPath()     // Catch:{ UnsatisfiedLinkError -> 0x00e1, Exception -> 0x00dd, all -> 0x00d8 }
            r4 = 1
            loadLibrary(r8, r1, r4)     // Catch:{ UnsatisfiedLinkError -> 0x00e1, Exception -> 0x00dd, all -> 0x00d8 }
            closeQuietly(r3)
            closeQuietly(r2)
            if (r0 == 0) goto L_0x00d1
            boolean r7 = DELETE_NATIVE_LIB_AFTER_LOADING
            if (r7 == 0) goto L_0x00ce
            boolean r7 = r0.delete()
            if (r7 != 0) goto L_0x00d1
        L_0x00ce:
            r0.deleteOnExit()
        L_0x00d1:
            return
        L_0x00d2:
            r7 = move-exception
            goto L_0x00da
        L_0x00d4:
            r8 = move-exception
            goto L_0x00df
        L_0x00d6:
            r7 = move-exception
            goto L_0x00ee
        L_0x00d8:
            r7 = move-exception
            r4 = r2
        L_0x00da:
            r2 = r3
            goto L_0x0145
        L_0x00dd:
            r8 = move-exception
            r4 = r2
        L_0x00df:
            r2 = r3
            goto L_0x00f7
        L_0x00e1:
            r7 = move-exception
            r4 = r2
            goto L_0x00ee
        L_0x00e4:
            r7 = move-exception
            r4 = r2
            goto L_0x0145
        L_0x00e8:
            r8 = move-exception
            r4 = r2
            goto L_0x00f7
        L_0x00eb:
            r7 = move-exception
            r3 = r2
            r4 = r3
        L_0x00ee:
            r2 = r0
            goto L_0x0119
        L_0x00f0:
            r7 = move-exception
            r0 = r2
            r4 = r0
            goto L_0x0145
        L_0x00f4:
            r8 = move-exception
            r0 = r2
            r4 = r0
        L_0x00f7:
            java.lang.UnsatisfiedLinkError r1 = new java.lang.UnsatisfiedLinkError     // Catch:{ all -> 0x0114 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0114 }
            r3.<init>()     // Catch:{ all -> 0x0114 }
            java.lang.String r5 = "could not load a native library: "
            r3.append(r5)     // Catch:{ all -> 0x0114 }
            r3.append(r7)     // Catch:{ all -> 0x0114 }
            java.lang.String r7 = r3.toString()     // Catch:{ all -> 0x0114 }
            r1.<init>(r7)     // Catch:{ all -> 0x0114 }
            java.lang.Throwable r7 = r1.initCause(r8)     // Catch:{ all -> 0x0114 }
            java.lang.UnsatisfiedLinkError r7 = (java.lang.UnsatisfiedLinkError) r7     // Catch:{ all -> 0x0114 }
            throw r7     // Catch:{ all -> 0x0114 }
        L_0x0114:
            r7 = move-exception
            goto L_0x0145
        L_0x0116:
            r7 = move-exception
            r3 = r2
            r4 = r3
        L_0x0119:
            if (r2 == 0) goto L_0x0141
            boolean r8 = r2.isFile()     // Catch:{ all -> 0x0139 }
            if (r8 == 0) goto L_0x0141
            boolean r8 = r2.canRead()     // Catch:{ all -> 0x0139 }
            if (r8 == 0) goto L_0x0141
            boolean r8 = p043io.netty.util.internal.NativeLibraryLoader.NoexecVolumeDetector.canExecuteExecutable(r2)     // Catch:{ all -> 0x0139 }
            if (r8 != 0) goto L_0x0141
            io.netty.util.internal.logging.InternalLogger r8 = logger     // Catch:{ all -> 0x0139 }
            java.lang.String r0 = "{} exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -Dio.netty.native.workdir=[path] to set native working directory separately."
            java.lang.String r1 = r2.getPath()     // Catch:{ all -> 0x0139 }
            r8.info(r0, r1)     // Catch:{ all -> 0x0139 }
            goto L_0x0141
        L_0x0139:
            r8 = move-exception
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = "Error checking if {} is on a file store mounted with noexec"
            r0.debug(r1, r2, r8)     // Catch:{ all -> 0x0142 }
        L_0x0141:
            throw r7     // Catch:{ all -> 0x0142 }
        L_0x0142:
            r7 = move-exception
            r0 = r2
            goto L_0x00da
        L_0x0145:
            closeQuietly(r2)
            closeQuietly(r4)
            if (r0 == 0) goto L_0x015a
            boolean r8 = DELETE_NATIVE_LIB_AFTER_LOADING
            if (r8 == 0) goto L_0x0157
            boolean r8 = r0.delete()
            if (r8 != 0) goto L_0x015a
        L_0x0157:
            r0.deleteOnExit()
        L_0x015a:
            goto L_0x015c
        L_0x015b:
            throw r7
        L_0x015c:
            goto L_0x015b
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.NativeLibraryLoader.load(java.lang.String, java.lang.ClassLoader):void");
    }

    private static void loadLibrary(ClassLoader classLoader, String str, boolean z) {
        String str2 = "Unable to load the library '{}', trying other loading mechanism.";
        try {
            loadLibraryByHelper(tryToLoadClass(classLoader, NativeLibraryUtil.class), str, z);
        } catch (UnsatisfiedLinkError e) {
            logger.debug(str2, str, e);
            NativeLibraryUtil.loadLibrary(str, z);
        } catch (Exception e2) {
            logger.debug(str2, str, e2);
            NativeLibraryUtil.loadLibrary(str, z);
        }
    }

    private static void loadLibraryByHelper(final Class<?> cls, final String str, final boolean z) throws UnsatisfiedLinkError {
        Object doPrivileged = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = cls.getMethod("loadLibrary", new Class[]{String.class, Boolean.TYPE});
                    method.setAccessible(true);
                    return method.invoke(null, new Object[]{str, Boolean.valueOf(z)});
                } catch (Exception e) {
                    return e;
                }
            }
        });
        if (doPrivileged instanceof Throwable) {
            Throwable th = (Throwable) doPrivileged;
            Throwable cause = th.getCause();
            if (cause == null) {
                throw new UnsatisfiedLinkError(th.getMessage());
            } else if (cause instanceof UnsatisfiedLinkError) {
                throw ((UnsatisfiedLinkError) cause);
            } else {
                throw new UnsatisfiedLinkError(cause.getMessage());
            }
        }
    }

    private static Class<?> tryToLoadClass(final ClassLoader classLoader, final Class<?> cls) throws ClassNotFoundException {
        try {
            return classLoader.loadClass(cls.getName());
        } catch (ClassNotFoundException unused) {
            final byte[] classToByteArray = classToByteArray(cls);
            return (Class) AccessController.doPrivileged(new PrivilegedAction<Class<?>>() {
                public Class<?> run() {
                    try {
                        Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE});
                        declaredMethod.setAccessible(true);
                        return (Class) declaredMethod.invoke(classLoader, new Object[]{cls.getName(), classToByteArray, Integer.valueOf(0), Integer.valueOf(classToByteArray.length)});
                    } catch (Exception e) {
                        throw new IllegalStateException("Define class failed!", e);
                    }
                }
            });
        }
    }

    private static byte[] classToByteArray(Class<?> cls) throws ClassNotFoundException {
        String name = cls.getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf > 0) {
            name = name.substring(lastIndexOf + 1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(".class");
        URL resource = cls.getResource(sb.toString());
        if (resource != null) {
            byte[] bArr = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            try {
                InputStream openStream = resource.openStream();
                while (true) {
                    int read = openStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        closeQuietly(openStream);
                        closeQuietly(byteArrayOutputStream);
                        return byteArray;
                    }
                }
            } catch (IOException e) {
                throw new ClassNotFoundException(cls.getName(), e);
            } catch (Throwable th) {
                closeQuietly(null);
                closeQuietly(byteArrayOutputStream);
                throw th;
            }
        } else {
            throw new ClassNotFoundException(cls.getName());
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    private NativeLibraryLoader() {
    }
}
