package com.flurry.sdk;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* renamed from: com.flurry.sdk.dq */
public final class C1720dq<ObjectType> extends C1719dp<ObjectType> {
    public C1720dq(C1724ds<ObjectType> dsVar) {
        super(dsVar);
    }

    /* renamed from: a */
    public final void mo16251a(OutputStream outputStream, ObjectType objecttype) throws IOException {
        if (outputStream != null) {
            GZIPOutputStream gZIPOutputStream = null;
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(outputStream);
                try {
                    super.mo16251a(gZIPOutputStream2, objecttype);
                    C1734dz.m871a((Closeable) gZIPOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    gZIPOutputStream = gZIPOutputStream2;
                    C1734dz.m871a((Closeable) gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                C1734dz.m871a((Closeable) gZIPOutputStream);
                throw th;
            }
        }
    }

    /* renamed from: a */
    public final ObjectType mo16250a(InputStream inputStream) throws IOException {
        GZIPInputStream gZIPInputStream = null;
        if (inputStream == null) {
            return null;
        }
        try {
            GZIPInputStream gZIPInputStream2 = new GZIPInputStream(inputStream);
            try {
                ObjectType a = super.mo16250a(gZIPInputStream2);
                C1734dz.m871a((Closeable) gZIPInputStream2);
                return a;
            } catch (Throwable th) {
                th = th;
                gZIPInputStream = gZIPInputStream2;
                C1734dz.m871a((Closeable) gZIPInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            C1734dz.m871a((Closeable) gZIPInputStream);
            throw th;
        }
    }
}
