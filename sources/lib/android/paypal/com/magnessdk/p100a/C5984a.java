package lib.android.paypal.com.magnessdk.p100a;

import android.os.Environment;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import lib.android.paypal.com.magnessdk.C5989c;

/* renamed from: lib.android.paypal.com.magnessdk.a.a */
public class C5984a {

    /* renamed from: a */
    private static final int f4224a = 1024;

    /* renamed from: b */
    private boolean f4225b = false;

    /* renamed from: c */
    private boolean f4226c = false;

    /* renamed from: d */
    private File f4227d;

    public C5984a() {
        m4021a();
        this.f4227d = Environment.getExternalStorageDirectory();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m4021a() {
        /*
            r5 = this;
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            int r1 = r0.hashCode()
            r2 = 1242932856(0x4a15a678, float:2451870.0)
            r3 = 0
            r4 = 1
            if (r1 == r2) goto L_0x001f
            r2 = 1299749220(0x4d789964, float:2.60675136E8)
            if (r1 == r2) goto L_0x0015
            goto L_0x0029
        L_0x0015:
            java.lang.String r1 = "mounted_ro"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0029
            r0 = 1
            goto L_0x002a
        L_0x001f:
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0029
            r0 = 0
            goto L_0x002a
        L_0x0029:
            r0 = -1
        L_0x002a:
            if (r0 == 0) goto L_0x0038
            if (r0 == r4) goto L_0x0033
            r5.f4226c = r3
            r5.f4225b = r3
            goto L_0x003c
        L_0x0033:
            r5.f4225b = r4
            r5.f4226c = r3
            goto L_0x003c
        L_0x0038:
            r5.f4226c = r4
            r5.f4225b = r4
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: lib.android.paypal.com.magnessdk.p100a.C5984a.m4021a():void");
    }

    /* renamed from: a */
    public void mo72533a(String str) {
        this.f4227d = new File(str);
    }

    /* renamed from: a */
    public void mo72534a(String str, byte[] bArr) {
        FileOutputStream fileOutputStream;
        if (this.f4225b && this.f4226c) {
            FileOutputStream fileOutputStream2 = null;
            try {
                if (!this.f4227d.mkdirs()) {
                    if (!this.f4227d.isDirectory()) {
                        fileOutputStream = null;
                        C5989c.m4036a(getClass(), (Closeable) fileOutputStream);
                    }
                }
                fileOutputStream = new FileOutputStream(new File(this.f4227d, str));
                try {
                    fileOutputStream.write(bArr);
                    C5989c.m4036a(getClass(), (Closeable) fileOutputStream);
                } catch (Throwable th) {
                    fileOutputStream2 = fileOutputStream;
                    th = th;
                    C5989c.m4036a(getClass(), (Closeable) fileOutputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                C5989c.m4036a(getClass(), (Closeable) fileOutputStream2);
                throw th;
            }
        }
    }

    /* renamed from: b */
    public String mo72535b(String str) {
        FileInputStream fileInputStream;
        byte[] bArr = new byte[1024];
        String str2 = null;
        if (this.f4226c) {
            try {
                fileInputStream = new FileInputStream(new File(this.f4227d, str));
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int read = fileInputStream.read(bArr, 0, 1024); read != -1; read = fileInputStream.read(bArr, 0, 1024)) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    str2 = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                    C5989c.m4036a(getClass(), (Closeable) fileInputStream);
                } catch (Throwable th) {
                    th = th;
                    C5989c.m4036a(getClass(), (Closeable) fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
                C5989c.m4036a(getClass(), (Closeable) fileInputStream);
                throw th;
            }
        }
        return str2;
    }

    /* renamed from: c */
    public boolean mo72536c(String str) {
        return new File(this.f4227d, str).delete();
    }
}
