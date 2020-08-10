package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.flurry.sdk.cn */
public final class C1669cn extends C1671cp {

    /* renamed from: i */
    private final Context f839i;

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16375b() {
    }

    C1669cn(Context context, String str) {
        this.f839i = context;
        this.f840a = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final InputStream mo16373a() throws IOException {
        String str = "LocalAssetsTransport";
        if (this.f839i != null && !TextUtils.isEmpty(this.f840a)) {
            try {
                return this.f839i.getAssets().open(this.f840a);
            } catch (FileNotFoundException unused) {
                StringBuilder sb = new StringBuilder("File Not Found when opening ");
                sb.append(this.f840a);
                C1685cy.m762b(str, sb.toString());
            } catch (IOException unused2) {
                StringBuilder sb2 = new StringBuilder("IO Exception when opening ");
                sb2.append(this.f840a);
                C1685cy.m762b(str, sb2.toString());
            }
        }
        return null;
    }
}
