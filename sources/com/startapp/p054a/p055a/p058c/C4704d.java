package com.startapp.p054a.p055a.p058c;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/* renamed from: com.startapp.a.a.c.d */
/* compiled from: StartAppSDK */
public class C4704d {

    /* renamed from: a */
    public static final char f2407a = File.separatorChar;

    /* renamed from: b */
    public static final String f2408b;

    static {
        C4705e eVar = new C4705e(4);
        PrintWriter printWriter = new PrintWriter(eVar);
        printWriter.println();
        f2408b = eVar.toString();
        printWriter.close();
    }

    /* renamed from: a */
    public static void m2108a(OutputStream outputStream) {
        m2107a((Closeable) outputStream);
    }

    /* renamed from: a */
    public static void m2107a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
