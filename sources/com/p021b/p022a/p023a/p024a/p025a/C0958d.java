package com.p021b.p022a.p023a.p024a.p025a;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings.System;
import com.google.android.exoplayer2.util.MimeTypes;

/* renamed from: com.b.a.a.a.a.d */
public final class C0958d extends ContentObserver {

    /* renamed from: a */
    private final Context f94a;

    /* renamed from: b */
    private final AudioManager f95b;

    /* renamed from: c */
    private final C0955a f96c;

    /* renamed from: d */
    private final C0957c f97d;

    /* renamed from: e */
    private float f98e;

    public C0958d(Handler handler, Context context, C0955a aVar, C0957c cVar) {
        super(handler);
        this.f94a = context;
        this.f95b = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.f96c = aVar;
        this.f97d = cVar;
    }

    /* renamed from: a */
    private boolean m96a(float f) {
        return f != this.f98e;
    }

    /* renamed from: c */
    private float m97c() {
        return this.f96c.mo11457a(this.f95b.getStreamVolume(3), this.f95b.getStreamMaxVolume(3));
    }

    /* renamed from: d */
    private void m98d() {
        this.f97d.mo11459a(this.f98e);
    }

    /* renamed from: a */
    public void mo11460a() {
        this.f98e = m97c();
        m98d();
        this.f94a.getContentResolver().registerContentObserver(System.CONTENT_URI, true, this);
    }

    /* renamed from: b */
    public void mo11461b() {
        this.f94a.getContentResolver().unregisterContentObserver(this);
    }

    public void onChange(boolean z) {
        super.onChange(z);
        float c = m97c();
        if (m96a(c)) {
            this.f98e = c;
            m98d();
        }
    }
}
