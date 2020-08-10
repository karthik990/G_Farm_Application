package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.du */
public final class C1726du<T> implements C1724ds<T> {

    /* renamed from: a */
    private final String f982a;

    /* renamed from: b */
    private final int f983b;

    /* renamed from: c */
    private final C1729dv<T> f984c;

    public C1726du(String str, int i, C1729dv<T> dvVar) {
        this.f982a = str;
        this.f983b = i;
        this.f984c = dvVar;
    }

    /* renamed from: a */
    public final void mo16251a(OutputStream outputStream, T t) throws IOException {
        if (outputStream != null && this.f984c != null) {
            C17271 r0 = new DataOutputStream(outputStream) {
                public final void close() {
                }
            };
            r0.writeUTF(this.f982a);
            r0.writeInt(this.f983b);
            this.f984c.mo16258a(this.f983b).mo16251a(r0, t);
            r0.flush();
        }
    }

    /* renamed from: a */
    public final T mo16250a(InputStream inputStream) throws IOException {
        if (inputStream == null || this.f984c == null) {
            return null;
        }
        C17282 r0 = new DataInputStream(inputStream) {
            public final void close() {
            }
        };
        String readUTF = r0.readUTF();
        if (this.f982a.equals(readUTF)) {
            return this.f984c.mo16258a(r0.readInt()).mo16250a(r0);
        }
        StringBuilder sb = new StringBuilder("Signature: ");
        sb.append(readUTF);
        sb.append(" is invalid");
        throw new IOException(sb.toString());
    }
}
