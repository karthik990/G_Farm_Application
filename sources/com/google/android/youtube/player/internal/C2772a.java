package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* renamed from: com.google.android.youtube.player.internal.a */
public abstract class C2772a implements YouTubeThumbnailLoader {

    /* renamed from: a */
    private final WeakReference<YouTubeThumbnailView> f1658a;

    /* renamed from: b */
    private OnThumbnailLoadedListener f1659b;

    /* renamed from: c */
    private boolean f1660c;

    /* renamed from: d */
    private boolean f1661d;

    public C2772a(YouTubeThumbnailView youTubeThumbnailView) {
        this.f1658a = new WeakReference<>(C2774ab.m1495a(youTubeThumbnailView));
    }

    /* renamed from: i */
    private void m1477i() {
        if (!mo38046a()) {
            throw new IllegalStateException("This YouTubeThumbnailLoader has been released");
        }
    }

    /* renamed from: a */
    public final void mo38043a(Bitmap bitmap, String str) {
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) this.f1658a.get();
        if (mo38046a() && youTubeThumbnailView != null) {
            youTubeThumbnailView.setImageBitmap(bitmap);
            OnThumbnailLoadedListener onThumbnailLoadedListener = this.f1659b;
            if (onThumbnailLoadedListener != null) {
                onThumbnailLoadedListener.onThumbnailLoaded(youTubeThumbnailView, str);
            }
        }
    }

    /* renamed from: a */
    public abstract void mo38044a(String str);

    /* renamed from: a */
    public abstract void mo38045a(String str, int i);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo38046a() {
        return !this.f1661d;
    }

    /* renamed from: b */
    public final void mo38047b() {
        if (mo38046a()) {
            C2837y.m1747a("The finalize() method for a YouTubeThumbnailLoader has work to do. You should have called release().", new Object[0]);
            release();
        }
    }

    /* renamed from: b */
    public final void mo38048b(String str) {
        ErrorReason errorReason;
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) this.f1658a.get();
        if (mo38046a() && this.f1659b != null && youTubeThumbnailView != null) {
            try {
                errorReason = ErrorReason.valueOf(str);
            } catch (IllegalArgumentException | NullPointerException unused) {
                errorReason = ErrorReason.UNKNOWN;
            }
            this.f1659b.onThumbnailError(youTubeThumbnailView, errorReason);
        }
    }

    /* renamed from: c */
    public abstract void mo38049c();

    /* renamed from: d */
    public abstract void mo38050d();

    /* renamed from: e */
    public abstract void mo38051e();

    /* renamed from: f */
    public abstract boolean mo38052f();

    public final void first() {
        m1477i();
        if (this.f1660c) {
            mo38051e();
            return;
        }
        throw new IllegalStateException("Must call setPlaylist first");
    }

    /* renamed from: g */
    public abstract boolean mo38053g();

    /* renamed from: h */
    public abstract void mo38054h();

    public final boolean hasNext() {
        m1477i();
        return mo38052f();
    }

    public final boolean hasPrevious() {
        m1477i();
        return mo38053g();
    }

    public final void next() {
        m1477i();
        if (!this.f1660c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (mo38052f()) {
            mo38049c();
        } else {
            throw new NoSuchElementException("Called next at end of playlist");
        }
    }

    public final void previous() {
        m1477i();
        if (!this.f1660c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (mo38053g()) {
            mo38050d();
        } else {
            throw new NoSuchElementException("Called previous at start of playlist");
        }
    }

    public final void release() {
        if (mo38046a()) {
            this.f1661d = true;
            this.f1659b = null;
            mo38054h();
        }
    }

    public final void setOnThumbnailLoadedListener(OnThumbnailLoadedListener onThumbnailLoadedListener) {
        m1477i();
        this.f1659b = onThumbnailLoadedListener;
    }

    public final void setPlaylist(String str) {
        setPlaylist(str, 0);
    }

    public final void setPlaylist(String str, int i) {
        m1477i();
        this.f1660c = true;
        mo38045a(str, i);
    }

    public final void setVideo(String str) {
        m1477i();
        this.f1660c = false;
        mo38044a(str);
    }
}
