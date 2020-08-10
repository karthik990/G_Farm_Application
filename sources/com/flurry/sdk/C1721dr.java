package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.flurry.sdk.dr */
public final class C1721dr<T> implements C1724ds<List<T>> {

    /* renamed from: a */
    C1724ds<T> f979a;

    /* renamed from: a */
    public final /* synthetic */ void mo16251a(OutputStream outputStream, Object obj) throws IOException {
        List list = (List) obj;
        if (outputStream != null) {
            C17221 r0 = new DataOutputStream(outputStream) {
                public final void close() {
                }
            };
            int size = list != null ? list.size() : 0;
            r0.writeInt(size);
            for (int i = 0; i < size; i++) {
                this.f979a.mo16251a(outputStream, list.get(i));
            }
            r0.flush();
        }
    }

    public C1721dr(C1724ds<T> dsVar) {
        this.f979a = dsVar;
    }

    /* renamed from: a */
    public final /* synthetic */ Object mo16250a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int readInt = new DataInputStream(inputStream) {
            public final void close() {
            }
        }.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        int i = 0;
        while (i < readInt) {
            Object a = this.f979a.mo16250a(inputStream);
            if (a != null) {
                arrayList.add(a);
                i++;
            } else {
                throw new IOException("Missing record.");
            }
        }
        return arrayList;
    }
}
