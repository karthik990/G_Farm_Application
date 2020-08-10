package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.ah */
public final class C1536ah {

    /* renamed from: a */
    public final boolean f494a;

    /* renamed from: b */
    public final byte[] f495b;

    /* renamed from: c */
    public final byte[] f496c;

    /* renamed from: d */
    public final int f497d;

    /* renamed from: com.flurry.sdk.ah$a */
    static class C1537a implements C1724ds<C1536ah> {
        C1537a() {
        }

        /* renamed from: a */
        public final /* synthetic */ void mo16251a(OutputStream outputStream, Object obj) throws IOException {
            C1536ah ahVar = (C1536ah) obj;
            if (outputStream != null && ahVar != null) {
                C15381 r0 = new DataOutputStream(outputStream) {
                    public final void close() {
                    }
                };
                r0.writeBoolean(ahVar.f494a);
                if (ahVar.f495b == null) {
                    r0.writeInt(0);
                } else {
                    r0.writeInt(ahVar.f495b.length);
                    r0.write(ahVar.f495b);
                }
                if (ahVar.f496c == null) {
                    r0.writeInt(0);
                } else {
                    r0.writeInt(ahVar.f496c.length);
                    r0.write(ahVar.f496c);
                }
                r0.writeInt(ahVar.f497d);
                r0.flush();
            }
        }

        /* renamed from: a */
        public final /* synthetic */ Object mo16250a(InputStream inputStream) throws IOException {
            byte[] bArr;
            byte[] bArr2 = null;
            if (inputStream == null) {
                return null;
            }
            C15392 r1 = new DataInputStream(inputStream) {
                public final void close() {
                }
            };
            boolean readBoolean = r1.readBoolean();
            int readInt = r1.readInt();
            if (readInt > 0) {
                bArr = new byte[readInt];
                r1.read(bArr, 0, readInt);
            } else {
                bArr = null;
            }
            int readInt2 = r1.readInt();
            if (readInt2 > 0) {
                bArr2 = new byte[readInt2];
                r1.read(bArr2, 0, readInt2);
            }
            return new C1536ah(bArr2, bArr, readBoolean, r1.readInt());
        }
    }

    C1536ah(byte[] bArr, byte[] bArr2, boolean z, int i) {
        this.f495b = bArr2;
        this.f496c = bArr;
        this.f494a = z;
        this.f497d = i;
    }
}
