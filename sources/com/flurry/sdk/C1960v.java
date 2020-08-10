package com.flurry.sdk;

import com.mobiroller.constants.Constants;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* renamed from: com.flurry.sdk.v */
public final class C1960v {

    /* renamed from: c */
    private static SimpleDateFormat f1452c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    /* renamed from: a */
    public String f1453a;

    /* renamed from: b */
    public long f1454b;

    public C1960v(String str, long j) {
        this.f1453a = str;
        this.f1454b = j;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(f1452c.format(Long.valueOf(this.f1454b)));
        sb.append(": ");
        sb.append(this.f1453a);
        sb.append(Constants.NEW_LINE);
        return sb.toString();
    }
}
