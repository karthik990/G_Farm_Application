package com.startapp.common.p092a;

import android.content.Context;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.common.a.e */
/* compiled from: StartAppSDK */
public class C5152e {
    /* renamed from: a */
    public static <T> T m3786a(Context context, String str, Class<T> cls) {
        return m3787a(context, (String) null, str, cls);
    }

    /* renamed from: a */
    public static void m3790a(Context context, String str, Serializable serializable) {
        m3791a(context, (String) null, str, serializable);
    }

    /* renamed from: b */
    public static void m3799b(final Context context, final String str, final Serializable serializable) {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                C5152e.m3791a(context, (String) null, str, serializable);
            }
        });
    }

    /* renamed from: a */
    public static void m3791a(Context context, String str, String str2, Serializable serializable) {
        String str3 = "FileUtils";
        if (str2 == null) {
            C5155g.m3807a(str3, 3, "writeToDisk: fileName is null");
            return;
        }
        try {
            m3794a(m3797b(context, str), str2, serializable);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed writing to disk: ");
            sb.append(e.getLocalizedMessage());
            C5155g.m3807a(str3, 3, sb.toString());
        }
    }

    /* renamed from: b */
    public static void m3800b(Context context, String str, String str2, Serializable serializable) {
        String str3 = "FileUtils";
        if (str2 == null) {
            C5155g.m3807a(str3, 3, "writeToDisk: fileName is null");
            return;
        }
        try {
            m3794a(m3801c(context, str), str2, serializable);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed writing to disk: ");
            sb.append(e.getLocalizedMessage());
            C5155g.m3807a(str3, 3, sb.toString());
        }
    }

    /* renamed from: a */
    public static <T> T m3787a(Context context, String str, String str2, Class<T> cls) {
        String str3 = "Failed to read from disk: ";
        String str4 = "FileUtils";
        T t = null;
        if (str2 == null) {
            C5155g.m3805a(3, "readFromDisk::fileName is null");
            return null;
        }
        try {
            t = m3788a(m3797b(context, str), str2);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(e.getLocalizedMessage());
            C5155g.m3807a(str4, 6, sb.toString());
        } catch (Error e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(e2.getLocalizedMessage());
            C5155g.m3807a(str4, 6, sb2.toString());
        }
        return t;
    }

    /* renamed from: b */
    public static <T> T m3795b(Context context, String str, String str2, Class<T> cls) {
        String str3 = "Failed to read from disk: ";
        String str4 = "FileUtils";
        T t = null;
        if (str2 == null) {
            C5155g.m3805a(3, "readFromDisk::fileName is null");
            return null;
        }
        try {
            t = m3788a(m3801c(context, str), str2);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(e.getLocalizedMessage());
            C5155g.m3807a(str4, 6, sb.toString());
        } catch (Error e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(e2.getLocalizedMessage());
            C5155g.m3807a(str4, 6, sb2.toString());
        }
        return t;
    }

    /* renamed from: b */
    public static <T> List<T> m3798b(Context context, String str, Class<T> cls) {
        String str2 = "FileUtils";
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(m3801c(context, str));
            if (file.exists()) {
                if (file.isDirectory()) {
                    String[] list = file.list();
                    if (list == null) {
                        C5155g.m3807a(str2, 3, "Files directory is empty");
                        return null;
                    }
                    for (String file2 : list) {
                        File file3 = new File(file, file2);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Reading file: ");
                        sb.append(file3.getPath());
                        C5155g.m3807a(str2, 4, sb.toString());
                        arrayList.add(m3796b(file3));
                    }
                    return arrayList;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Files directory does not exist or not a directory: ");
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
            return null;
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Failed to read from disk: ");
            sb3.append(e.getLocalizedMessage());
            C5155g.m3807a(str2, 6, sb3.toString());
        }
    }

    /* renamed from: a */
    public static void m3789a(Context context, String str) {
        if (str == null) {
            C5155g.m3805a(3, "deleteDirectory::dirPath == null");
            return;
        }
        m3792a(new File(m3797b(context, str)));
        m3792a(new File(m3801c(context, str)));
    }

    /* renamed from: b */
    private static String m3797b(Context context, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir().toString());
        if (str != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(File.separator);
            sb2.append(str);
            str2 = sb2.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: c */
    private static String m3801c(Context context, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getCacheDir().toString());
        if (str != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(File.separator);
            sb2.append(str);
            str2 = sb2.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: a */
    private static void m3794a(String str, String str2, Serializable serializable) {
        File file = new File(str);
        String str3 = "FileUtils";
        if (file.exists() || file.mkdirs()) {
            File file2 = new File(file, str2);
            StringBuilder sb = new StringBuilder();
            sb.append("Writing file: ");
            sb.append(file2.getPath());
            C5155g.m3807a(str3, 4, sb.toString());
            m3793a(serializable, file2);
            return;
        }
        C5155g.m3807a(str3, 3, "Unable to create directories");
    }

    /* renamed from: a */
    private static <T> T m3788a(String str, String str2) {
        File file = new File(str);
        T t = null;
        String str3 = "FileUtils";
        if (!file.exists() || !file.isDirectory()) {
            C5155g.m3807a(str3, 3, "Files directory does not exist or not a directory");
            return null;
        }
        File file2 = new File(file, str2);
        if (file2.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Reading file: ");
            sb.append(file2.getPath());
            C5155g.m3807a(str3, 4, sb.toString());
            t = m3796b(file2);
        }
        return t;
    }

    /* renamed from: a */
    private static void m3792a(File file) {
        if (file.isDirectory()) {
            for (File a : file.listFiles()) {
                m3792a(a);
            }
        }
        file.delete();
    }

    /* renamed from: a */
    private static void m3793a(Serializable serializable, File file) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    /* renamed from: b */
    private static <T> T m3796b(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        T readObject = objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return readObject;
    }
}
