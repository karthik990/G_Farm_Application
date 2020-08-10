package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/* renamed from: com.flurry.sdk.bo */
public final class C1611bo {

    /* renamed from: a */
    String f698a;

    /* renamed from: b */
    byte[] f699b;

    /* renamed from: com.flurry.sdk.bo$a */
    public static class C1613a implements C1724ds<C1611bo> {

        /* renamed from: a */
        private int f700a = 1;

        /* renamed from: a */
        public final /* synthetic */ void mo16251a(OutputStream outputStream, Object obj) throws IOException {
            C1611bo boVar = (C1611bo) obj;
            if (outputStream != null && boVar != null) {
                C16141 r0 = new DataOutputStream(outputStream) {
                    public final void close() {
                    }
                };
                int length = boVar.f699b.length;
                if (this.f700a == 1) {
                    r0.writeShort(length);
                } else {
                    r0.writeInt(length);
                }
                r0.write(boVar.f699b);
                r0.writeShort(0);
                r0.flush();
            }
        }

        public C1613a(int i) {
            this.f700a = i;
        }

        /* renamed from: a */
        public final /* synthetic */ Object mo16250a(InputStream inputStream) throws IOException {
            int i;
            if (inputStream == null) {
                return null;
            }
            C16152 r1 = new DataInputStream(inputStream) {
                public final void close() {
                }
            };
            C1611bo boVar = new C1611bo(0);
            if (this.f700a == 1) {
                i = r1.readShort();
            } else {
                i = r1.readInt();
            }
            if (i == 0) {
                return null;
            }
            boVar.f699b = new byte[i];
            r1.readFully(boVar.f699b);
            r1.readUnsignedShort();
            return boVar;
        }
    }

    /* synthetic */ C1611bo(byte b) {
        this();
    }

    private C1611bo() {
        this.f698a = null;
        this.f699b = null;
    }

    public C1611bo(byte[] bArr) {
        this.f698a = null;
        this.f699b = null;
        this.f698a = UUID.randomUUID().toString();
        this.f699b = bArr;
    }

    /* renamed from: a */
    public static String m563a(String str) {
        return ".yflurrydatasenderblock.".concat(String.valueOf(str));
    }

    /* renamed from: b */
    public static C1941l<C1611bo> m564b(String str) {
        return new C1941l<>(C1576b.m502a().getFileStreamPath(m563a(str)), ".yflurrydatasenderblock.", 2, new C1729dv<C1611bo>() {
            /* renamed from: a */
            public final C1724ds<C1611bo> mo16258a(int i) {
                return new C1613a(i);
            }
        });
    }
}
