package com.flurry.sdk;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.af */
public final class C1527af {

    /* renamed from: a */
    String f475a;

    /* renamed from: b */
    final Set<String> f476b;

    C1527af() {
        HashSet hashSet = new HashSet();
        String str = "";
        hashSet.add(str);
        hashSet.add(null);
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        this.f476b = Collections.unmodifiableSet(hashSet);
        this.f475a = str;
    }

    /* renamed from: a */
    static void m416a(String str) {
        if (!TextUtils.isEmpty(str)) {
            File fileStreamPath = C1576b.m502a().getFileStreamPath(".flurryb.");
            if (C1732dy.m861a(fileStreamPath)) {
                DataOutputStream dataOutputStream = null;
                try {
                    DataOutputStream dataOutputStream2 = new DataOutputStream(new FileOutputStream(fileStreamPath));
                    try {
                        dataOutputStream2.writeInt(1);
                        dataOutputStream2.writeUTF(str);
                        C1734dz.m871a((Closeable) dataOutputStream2);
                    } catch (Throwable th) {
                        th = th;
                        dataOutputStream = dataOutputStream2;
                        try {
                            C1685cy.m755a(6, "DeviceIdProvider", "Error when saving deviceId", th);
                        } finally {
                            C1734dz.m871a((Closeable) dataOutputStream);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    C1685cy.m755a(6, "DeviceIdProvider", "Error when saving deviceId", th);
                }
            }
        }
    }

    /* renamed from: a */
    static String m415a() {
        DataInputStream dataInputStream;
        File fileStreamPath = C1576b.m502a().getFileStreamPath(".flurryb.");
        String str = null;
        if (fileStreamPath == null || !fileStreamPath.exists()) {
            return null;
        }
        try {
            dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
            try {
                if (1 == dataInputStream.readInt()) {
                    str = dataInputStream.readUTF();
                }
            } catch (Throwable th) {
                th = th;
                try {
                    C1685cy.m755a(6, "DeviceIdProvider", "Error when loading deviceId", th);
                    C1734dz.m871a((Closeable) dataInputStream);
                    return str;
                } catch (Throwable th2) {
                    C1734dz.m871a((Closeable) dataInputStream);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            dataInputStream = null;
            C1685cy.m755a(6, "DeviceIdProvider", "Error when loading deviceId", th);
            C1734dz.m871a((Closeable) dataInputStream);
            return str;
        }
        C1734dz.m871a((Closeable) dataInputStream);
        return str;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final String mo16244b() {
        DataInputStream dataInputStream;
        File filesDir = C1576b.m502a().getFilesDir();
        String str = null;
        if (filesDir == null) {
            return null;
        }
        String[] list = filesDir.list(new FilenameFilter() {
            public final boolean accept(File file, String str) {
                return str.startsWith(".flurryagent.");
            }
        });
        if (!(list == null || list.length == 0)) {
            File fileStreamPath = C1576b.m502a().getFileStreamPath(list[0]);
            if (fileStreamPath != null && fileStreamPath.exists()) {
                try {
                    dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                    try {
                        if (46586 == dataInputStream.readUnsignedShort() && 2 == dataInputStream.readUnsignedShort()) {
                            dataInputStream.readUTF();
                            str = dataInputStream.readUTF();
                        }
                    } catch (Throwable th) {
                        th = th;
                        try {
                            C1685cy.m755a(6, "DeviceIdProvider", "Error when loading deviceId", th);
                            C1734dz.m871a((Closeable) dataInputStream);
                            return str;
                        } catch (Throwable th2) {
                            C1734dz.m871a((Closeable) dataInputStream);
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    dataInputStream = null;
                    C1685cy.m755a(6, "DeviceIdProvider", "Error when loading deviceId", th);
                    C1734dz.m871a((Closeable) dataInputStream);
                    return str;
                }
                C1734dz.m871a((Closeable) dataInputStream);
                return str;
            }
        }
        return null;
    }
}
