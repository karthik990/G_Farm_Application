package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.do */
public final class C1718do implements C1724ds<byte[]> {
    /* renamed from: a */
    public final /* synthetic */ void mo16251a(OutputStream outputStream, Object obj) throws IOException {
        byte[] bArr = (byte[]) obj;
        if (outputStream != null && bArr != null) {
            outputStream.write(bArr, 0, bArr.length);
        }
    }

    /* renamed from: a */
    public final /* synthetic */ Object mo16250a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        C1734dz.m866a(inputStream, (OutputStream) byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
