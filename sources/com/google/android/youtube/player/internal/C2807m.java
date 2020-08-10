package com.google.android.youtube.player.internal;

import android.content.Context;
import android.content.res.Resources;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.google.android.youtube.player.internal.m */
public final class C2807m {

    /* renamed from: a */
    public final String f1673a;

    /* renamed from: b */
    public final String f1674b;

    /* renamed from: c */
    public final String f1675c;

    /* renamed from: d */
    public final String f1676d;

    /* renamed from: e */
    public final String f1677e;

    /* renamed from: f */
    public final String f1678f;

    /* renamed from: g */
    public final String f1679g;

    /* renamed from: h */
    public final String f1680h;

    /* renamed from: i */
    public final String f1681i;

    /* renamed from: j */
    public final String f1682j;

    public C2807m(Context context) {
        Resources resources = context.getResources();
        Map a = C2836x.m1744a((resources == null || resources.getConfiguration() == null || resources.getConfiguration().locale == null) ? Locale.getDefault() : resources.getConfiguration().locale);
        this.f1673a = (String) a.get("error_initializing_player");
        this.f1674b = (String) a.get("get_youtube_app_title");
        this.f1675c = (String) a.get("get_youtube_app_text");
        this.f1676d = (String) a.get("get_youtube_app_action");
        this.f1677e = (String) a.get("enable_youtube_app_title");
        this.f1678f = (String) a.get("enable_youtube_app_text");
        this.f1679g = (String) a.get("enable_youtube_app_action");
        this.f1680h = (String) a.get("update_youtube_app_title");
        this.f1681i = (String) a.get("update_youtube_app_text");
        this.f1682j = (String) a.get("update_youtube_app_action");
    }
}
