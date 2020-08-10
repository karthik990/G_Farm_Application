package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.security.DigestOutputStream;

/* renamed from: com.flurry.sdk.gb */
public final class C1823gb {
    /* renamed from: a */
    public static byte[] m1054a(C1930jp jpVar) {
        DataOutputStream dataOutputStream;
        String str = "FrameSerializer";
        byte[] bArr = null;
        try {
            C1913j jVar = new C1913j();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, jVar);
            dataOutputStream = new DataOutputStream(digestOutputStream);
            try {
                StringBuilder sb = new StringBuilder("Adding frame ");
                sb.append(jpVar.mo16501a());
                sb.append(" payload ");
                sb.append(jpVar.mo16518e());
                C1685cy.m754a(3, str, sb.toString());
                dataOutputStream.writeByte(jpVar.mo16520g());
                int i = jpVar.mo16501a().f1386N;
                byte[] bArr2 = new byte[4];
                bArr2[0] = (byte) (i >> 16);
                bArr2[1] = (byte) (i >> 8);
                bArr2[2] = (byte) (i >> 0);
                for (int i2 = 0; i2 < 3; i2++) {
                    dataOutputStream.write(bArr2[i2]);
                }
                dataOutputStream.writeLong(jpVar.mo16516c());
                dataOutputStream.writeLong(jpVar.mo16517d());
                byte[] bytes = jpVar.mo16518e().getBytes("UTF-8");
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.write(bytes);
                if (jpVar.mo16521h()) {
                    digestOutputStream.on(false);
                    dataOutputStream.writeInt(jVar.mo16511a());
                }
                dataOutputStream.close();
                bArr = byteArrayOutputStream.toByteArray();
            } catch (Throwable th) {
                th = th;
                try {
                    C1685cy.m755a(3, str, "Error when generating report", th);
                    C1734dz.m871a((Closeable) dataOutputStream);
                    return bArr;
                } catch (Throwable th2) {
                    C1734dz.m871a((Closeable) dataOutputStream);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            C1685cy.m755a(3, str, "Error when generating report", th);
            C1734dz.m871a((Closeable) dataOutputStream);
            return bArr;
        }
        C1734dz.m871a((Closeable) dataOutputStream);
        return bArr;
    }
}
