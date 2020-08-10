package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.dp */
public class C1719dp<ObjectType> implements C1724ds<ObjectType> {

    /* renamed from: a */
    protected final C1724ds<ObjectType> f978a;

    public C1719dp(C1724ds<ObjectType> dsVar) {
        this.f978a = dsVar;
    }

    /* renamed from: a */
    public void mo16251a(OutputStream outputStream, ObjectType objecttype) throws IOException {
        C1724ds<ObjectType> dsVar = this.f978a;
        if (dsVar != null && outputStream != null && objecttype != null) {
            dsVar.mo16251a(outputStream, objecttype);
        }
    }

    /* renamed from: a */
    public ObjectType mo16250a(InputStream inputStream) throws IOException {
        C1724ds<ObjectType> dsVar = this.f978a;
        if (dsVar == null || inputStream == null) {
            return null;
        }
        return dsVar.mo16250a(inputStream);
    }
}
