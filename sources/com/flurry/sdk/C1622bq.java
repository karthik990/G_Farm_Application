package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.bq */
public final class C1622bq {

    /* renamed from: a */
    String f711a;

    /* renamed from: com.flurry.sdk.bq$a */
    public static class C1623a implements C1724ds<C1622bq> {
        /* renamed from: a */
        public final /* synthetic */ void mo16251a(OutputStream outputStream, Object obj) throws IOException {
            C1622bq bqVar = (C1622bq) obj;
            if (outputStream != null && bqVar != null) {
                C16241 r0 = new DataOutputStream(outputStream) {
                    public final void close() {
                    }
                };
                r0.writeUTF(bqVar.f711a);
                r0.flush();
            }
        }

        /* renamed from: a */
        public final /* synthetic */ Object mo16250a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            C16252 r0 = new DataInputStream(inputStream) {
                public final void close() {
                }
            };
            C1622bq bqVar = new C1622bq(0);
            bqVar.f711a = r0.readUTF();
            return bqVar;
        }
    }

    /* synthetic */ C1622bq(byte b) {
        this();
    }

    private C1622bq() {
    }

    public C1622bq(String str) {
        this.f711a = str;
    }
}
