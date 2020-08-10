package com.startapp.p054a.p055a.p059d;

import com.startapp.p054a.p055a.p058c.C4700a;
import com.startapp.p054a.p055a.p058c.C4704d;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/* renamed from: com.startapp.a.a.d.a */
/* compiled from: StartAppSDK */
public class C4707a implements C4711e {

    /* renamed from: a */
    private final C4709c f2410a;

    public C4707a(C4709c cVar) {
        this.f2410a = cVar;
    }

    /* renamed from: a */
    public String mo61116a(String str) {
        GZIPOutputStream gZIPOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream2.write(str.getBytes());
                C4704d.m2108a((OutputStream) gZIPOutputStream2);
                String a = this.f2410a.mo61117a(C4700a.m2091a(byteArrayOutputStream.toByteArray()));
                C4704d.m2108a((OutputStream) gZIPOutputStream2);
                return a;
            } catch (Exception unused) {
                gZIPOutputStream = gZIPOutputStream2;
                String str2 = "";
                C4704d.m2108a((OutputStream) gZIPOutputStream);
                return str2;
            } catch (Throwable th) {
                C4704d.m2108a((OutputStream) gZIPOutputStream2);
                throw th;
            }
        } catch (Exception unused2) {
            String str22 = "";
            C4704d.m2108a((OutputStream) gZIPOutputStream);
            return str22;
        }
    }
}
