package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C2798j.C2799a;

/* renamed from: com.google.android.youtube.player.internal.p */
public final class C2810p extends C2772a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final Handler f1689a = new Handler(Looper.getMainLooper());

    /* renamed from: b */
    private C2776b f1690b;

    /* renamed from: c */
    private C2801k f1691c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f1692d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public boolean f1693e;

    /* renamed from: com.google.android.youtube.player.internal.p$a */
    private final class C2811a extends C2799a {
        private C2811a() {
        }

        /* synthetic */ C2811a(C2810p pVar, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo38140a(Bitmap bitmap, String str, boolean z, boolean z2) {
            Handler a = C2810p.this.f1689a;
            final boolean z3 = z;
            final boolean z4 = z2;
            final Bitmap bitmap2 = bitmap;
            final String str2 = str;
            C28121 r1 = new Runnable() {
                public final void run() {
                    C2810p.this.f1692d = z3;
                    C2810p.this.f1693e = z4;
                    C2810p.this.mo38043a(bitmap2, str2);
                }
            };
            a.post(r1);
        }

        /* renamed from: a */
        public final void mo38141a(final String str, final boolean z, final boolean z2) {
            C2810p.this.f1689a.post(new Runnable() {
                public final void run() {
                    C2810p.this.f1692d = z;
                    C2810p.this.f1693e = z2;
                    C2810p.this.mo38048b(str);
                }
            });
        }
    }

    public C2810p(C2776b bVar, YouTubeThumbnailView youTubeThumbnailView) {
        super(youTubeThumbnailView);
        this.f1690b = (C2776b) C2774ab.m1496a(bVar, (Object) "connectionClient cannot be null");
        this.f1691c = bVar.mo38059a((C2798j) new C2811a(this, 0));
    }

    /* renamed from: a */
    public final void mo38044a(String str) {
        try {
            this.f1691c.mo38146a(str);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: a */
    public final void mo38045a(String str, int i) {
        try {
            this.f1691c.mo38147a(str, i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo38046a() {
        return super.mo38046a() && this.f1691c != null;
    }

    /* renamed from: c */
    public final void mo38049c() {
        try {
            this.f1691c.mo38145a();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: d */
    public final void mo38050d() {
        try {
            this.f1691c.mo38148b();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: e */
    public final void mo38051e() {
        try {
            this.f1691c.mo38149c();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: f */
    public final boolean mo38052f() {
        return this.f1693e;
    }

    /* renamed from: g */
    public final boolean mo38053g() {
        return this.f1692d;
    }

    /* renamed from: h */
    public final void mo38054h() {
        try {
            this.f1691c.mo38150d();
        } catch (RemoteException unused) {
        }
        this.f1690b.mo38166d();
        this.f1691c = null;
        this.f1690b = null;
    }
}
