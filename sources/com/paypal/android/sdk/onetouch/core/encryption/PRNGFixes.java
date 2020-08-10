package com.paypal.android.sdk.onetouch.core.encryption;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.security.SecureRandomSpi;

final class PRNGFixes {
    private static final byte[] BUILD_FINGERPRINT_AND_DEVICE_SERIAL = getBuildFingerprintAndDeviceSerial();
    private static final int VERSION_CODE_JELLY_BEAN = 16;
    private static final int VERSION_CODE_JELLY_BEAN_MR2 = 18;

    public static class LinuxPRNGSecureRandom extends SecureRandomSpi {
        private static final File URANDOM_FILE = new File("/dev/urandom");
        private static final Object sLock = new Object();
        private static DataInputStream sUrandomIn;
        private static OutputStream sUrandomOut;
        private boolean mSeeded;

        /* access modifiers changed from: protected */
        public void engineSetSeed(byte[] bArr) {
            OutputStream urandomOutputStream;
            try {
                synchronized (sLock) {
                    urandomOutputStream = getUrandomOutputStream();
                }
                urandomOutputStream.write(bArr);
                urandomOutputStream.flush();
            } catch (IOException unused) {
                try {
                    String simpleName = PRNGFixes.class.getSimpleName();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to mix seed into ");
                    sb.append(URANDOM_FILE);
                    Log.w(simpleName, sb.toString());
                } catch (Throwable th) {
                    this.mSeeded = true;
                    throw th;
                }
            }
            this.mSeeded = true;
        }

        /* access modifiers changed from: protected */
        public void engineNextBytes(byte[] bArr) {
            DataInputStream urandomInputStream;
            if (!this.mSeeded) {
                engineSetSeed(PRNGFixes.generateSeed());
            }
            try {
                synchronized (sLock) {
                    urandomInputStream = getUrandomInputStream();
                }
                synchronized (urandomInputStream) {
                    urandomInputStream.readFully(bArr);
                }
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to read from ");
                sb.append(URANDOM_FILE);
                throw new SecurityException(sb.toString(), e);
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGenerateSeed(int i) {
            byte[] bArr = new byte[i];
            engineNextBytes(bArr);
            return bArr;
        }

        private DataInputStream getUrandomInputStream() {
            DataInputStream dataInputStream;
            synchronized (sLock) {
                if (sUrandomIn == null) {
                    try {
                        sUrandomIn = new DataInputStream(new FileInputStream(URANDOM_FILE));
                    } catch (IOException e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Failed to open ");
                        sb.append(URANDOM_FILE);
                        sb.append(" for reading");
                        throw new SecurityException(sb.toString(), e);
                    }
                }
                dataInputStream = sUrandomIn;
            }
            return dataInputStream;
        }

        private OutputStream getUrandomOutputStream() throws IOException {
            OutputStream outputStream;
            synchronized (sLock) {
                if (sUrandomOut == null) {
                    sUrandomOut = new FileOutputStream(URANDOM_FILE);
                }
                outputStream = sUrandomOut;
            }
            return outputStream;
        }
    }

    private static class LinuxPRNGSecureRandomProvider extends Provider {
        public LinuxPRNGSecureRandomProvider() {
            super("LinuxPRNG", 1.0d, "A Linux-specific random number provider that uses /dev/urandom");
            put("SecureRandom.SHA1PRNG", LinuxPRNGSecureRandom.class.getName());
            put("SecureRandom.SHA1PRNG ImplementedIn", ExifInterface.TAG_SOFTWARE);
        }
    }

    private PRNGFixes() {
    }

    public static void apply() {
        applyOpenSSLFix();
        installLinuxPRNGSecureRandom();
    }

    private static void applyOpenSSLFix() throws SecurityException {
        String str = "org.apache.harmony.xnet.provider.jsse.NativeCrypto";
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
            try {
                Class.forName(str).getMethod("RAND_seed", new Class[]{byte[].class}).invoke(null, new Object[]{generateSeed()});
                int intValue = ((Integer) Class.forName(str).getMethod("RAND_load_file", new Class[]{String.class, Long.TYPE}).invoke(null, new Object[]{"/dev/urandom", Integer.valueOf(1024)})).intValue();
                if (intValue != 1024) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unexpected number of bytes read from Linux PRNG: ");
                    sb.append(intValue);
                    throw new IOException(sb.toString());
                }
            } catch (Exception e) {
                throw new SecurityException("Failed to seed OpenSSL PRNG", e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        if (r0[0].getClass().getSimpleName().equals("LinuxPRNGSecureRandomProvider") != false) goto L_0x0031;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void installLinuxPRNGSecureRandom() throws java.lang.SecurityException {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 18
            if (r0 <= r1) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r0 = "SecureRandom.SHA1PRNG"
            java.security.Provider[] r0 = java.security.Security.getProviders(r0)
            java.lang.Class<java.security.Security> r1 = java.security.Security.class
            monitor-enter(r1)
            r2 = 1
            if (r0 == 0) goto L_0x0029
            int r3 = r0.length     // Catch:{ all -> 0x00ad }
            if (r3 < r2) goto L_0x0029
            r3 = 0
            r0 = r0[r3]     // Catch:{ all -> 0x00ad }
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x00ad }
            java.lang.String r0 = r0.getSimpleName()     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = "LinuxPRNGSecureRandomProvider"
            boolean r0 = r0.equals(r3)     // Catch:{ all -> 0x00ad }
            if (r0 != 0) goto L_0x0031
        L_0x0029:
            com.paypal.android.sdk.onetouch.core.encryption.PRNGFixes$LinuxPRNGSecureRandomProvider r0 = new com.paypal.android.sdk.onetouch.core.encryption.PRNGFixes$LinuxPRNGSecureRandomProvider     // Catch:{ all -> 0x00ad }
            r0.<init>()     // Catch:{ all -> 0x00ad }
            java.security.Security.insertProviderAt(r0, r2)     // Catch:{ all -> 0x00ad }
        L_0x0031:
            java.security.SecureRandom r0 = new java.security.SecureRandom     // Catch:{ all -> 0x00ad }
            r0.<init>()     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "LinuxPRNGSecureRandomProvider"
            java.security.Provider r3 = r0.getProvider()     // Catch:{ all -> 0x00ad }
            java.lang.Class r3 = r3.getClass()     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = r3.getSimpleName()     // Catch:{ all -> 0x00ad }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x00ad }
            if (r2 == 0) goto L_0x008e
            java.lang.String r0 = "SHA1PRNG"
            java.security.SecureRandom r0 = java.security.SecureRandom.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0085 }
            java.lang.String r2 = "LinuxPRNGSecureRandomProvider"
            java.security.Provider r3 = r0.getProvider()     // Catch:{ all -> 0x00ad }
            java.lang.Class r3 = r3.getClass()     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = r3.getSimpleName()     // Catch:{ all -> 0x00ad }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x00ad }
            if (r2 == 0) goto L_0x0066
            monitor-exit(r1)     // Catch:{ all -> 0x00ad }
            return
        L_0x0066:
            java.lang.SecurityException r2 = new java.lang.SecurityException     // Catch:{ all -> 0x00ad }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ad }
            r3.<init>()     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "SecureRandom.getInstance(\"SHA1PRNG\") backed by wrong Provider: "
            r3.append(r4)     // Catch:{ all -> 0x00ad }
            java.security.Provider r0 = r0.getProvider()     // Catch:{ all -> 0x00ad }
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x00ad }
            r3.append(r0)     // Catch:{ all -> 0x00ad }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00ad }
            r2.<init>(r0)     // Catch:{ all -> 0x00ad }
            throw r2     // Catch:{ all -> 0x00ad }
        L_0x0085:
            r0 = move-exception
            java.lang.SecurityException r2 = new java.lang.SecurityException     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = "SHA1PRNG not available"
            r2.<init>(r3, r0)     // Catch:{ all -> 0x00ad }
            throw r2     // Catch:{ all -> 0x00ad }
        L_0x008e:
            java.lang.SecurityException r2 = new java.lang.SecurityException     // Catch:{ all -> 0x00ad }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ad }
            r3.<init>()     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "new SecureRandom() backed by wrong Provider: "
            r3.append(r4)     // Catch:{ all -> 0x00ad }
            java.security.Provider r0 = r0.getProvider()     // Catch:{ all -> 0x00ad }
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x00ad }
            r3.append(r0)     // Catch:{ all -> 0x00ad }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00ad }
            r2.<init>(r0)     // Catch:{ all -> 0x00ad }
            throw r2     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ad }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.paypal.android.sdk.onetouch.core.encryption.PRNGFixes.installLinuxPRNGSecureRandom():void");
    }

    /* access modifiers changed from: private */
    public static byte[] generateSeed() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeLong(System.nanoTime());
            dataOutputStream.writeInt(Process.myPid());
            dataOutputStream.writeInt(Process.myUid());
            dataOutputStream.write(BUILD_FINGERPRINT_AND_DEVICE_SERIAL);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SecurityException("Failed to generate seed", e);
        }
    }

    private static String getDeviceSerialNumber() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] getBuildFingerprintAndDeviceSerial() {
        StringBuilder sb = new StringBuilder();
        String str = Build.FINGERPRINT;
        if (str != null) {
            sb.append(str);
        }
        String deviceSerialNumber = getDeviceSerialNumber();
        if (deviceSerialNumber != null) {
            sb.append(deviceSerialNumber);
        }
        try {
            return sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 encoding not supported");
        }
    }
}
