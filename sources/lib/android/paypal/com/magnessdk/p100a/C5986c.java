package lib.android.paypal.com.magnessdk.p100a;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import lib.android.paypal.com.magnessdk.C5989c;
import lib.android.paypal.com.magnessdk.p046b.C5988a;

/* renamed from: lib.android.paypal.com.magnessdk.a.c */
public final class C5986c {
    private C5986c() {
    }

    /* renamed from: a */
    public static String m4026a(File file) {
        Class<C5986c> cls = C5986c.class;
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
            try {
                byte[] bArr = new byte[((int) randomAccessFile2.length())];
                randomAccessFile2.readFully(bArr);
                String str = new String(bArr, "UTF-8");
                C5989c.m4036a(cls, (Closeable) randomAccessFile2);
                return str;
            } catch (Exception e) {
                e = e;
                randomAccessFile = randomAccessFile2;
                try {
                    C5988a.m4032a(cls, 3, (Throwable) e);
                    C5989c.m4036a(cls, (Closeable) randomAccessFile);
                    return "";
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile2 = randomAccessFile;
                    C5989c.m4036a(cls, (Closeable) randomAccessFile2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                C5989c.m4036a(cls, (Closeable) randomAccessFile2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            C5988a.m4032a(cls, 3, (Throwable) e);
            C5989c.m4036a(cls, (Closeable) randomAccessFile);
            return "";
        }
    }

    /* renamed from: a */
    public static boolean m4027a(File file, String str) {
        Class<C5986c> cls = C5986c.class;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(str.getBytes("UTF-8"));
                C5989c.m4036a(cls, (Closeable) fileOutputStream2);
                return true;
            } catch (Exception e) {
                e = e;
                fileOutputStream = fileOutputStream2;
                try {
                    C5988a.m4032a(cls, 3, (Throwable) e);
                    C5989c.m4036a(cls, (Closeable) fileOutputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    C5989c.m4036a(cls, (Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                C5989c.m4036a(cls, (Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            C5988a.m4032a(cls, 3, (Throwable) e);
            C5989c.m4036a(cls, (Closeable) fileOutputStream);
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m4028b(File file) {
        Class<C5986c> cls = C5986c.class;
        try {
            if (file.exists()) {
                C5988a.m4031a(cls, 0, "deleting CachedConfigDataFromDisk");
                return file.delete();
            }
        } catch (Exception e) {
            C5988a.m4032a(cls, 3, (Throwable) e);
        }
        return false;
    }
}
