package com.google.android.youtube.player.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.youtube.player.internal.C2804l.C2805a;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;

/* renamed from: com.google.android.youtube.player.internal.o */
public final class C2809o extends C2815r<C2804l> implements C2776b {

    /* renamed from: b */
    private final String f1685b;

    /* renamed from: c */
    private final String f1686c;

    /* renamed from: d */
    private final String f1687d;

    /* renamed from: e */
    private boolean f1688e;

    public C2809o(Context context, String str, String str2, String str3, C2828a aVar, C2829b bVar) {
        super(context, aVar, bVar);
        this.f1685b = (String) C2774ab.m1495a(str);
        this.f1686c = C2774ab.m1497a(str2, (Object) "callingPackage cannot be null or empty");
        this.f1687d = C2774ab.m1497a(str3, (Object) "callingAppVersion cannot be null or empty");
    }

    /* renamed from: k */
    private final void m1654k() {
        mo38175i();
        if (this.f1688e) {
            throw new IllegalStateException("Connection client has been released");
        }
    }

    /* renamed from: a */
    public final IBinder mo38058a() {
        m1654k();
        try {
            return ((C2804l) mo38176j()).mo38153a();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final /* synthetic */ IInterface mo38162a(IBinder iBinder) {
        return C2805a.m1647a(iBinder);
    }

    /* renamed from: a */
    public final C2801k mo38059a(C2798j jVar) {
        m1654k();
        try {
            return ((C2804l) mo38176j()).mo38154a(jVar);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo38163a(C2795i iVar, C2820d dVar) throws RemoteException {
        iVar.mo38137a(dVar, 1202, this.f1686c, this.f1687d, this.f1685b, null);
    }

    /* renamed from: a */
    public final void mo38060a(boolean z) {
        if (mo38172f()) {
            try {
                ((C2804l) mo38176j()).mo38155a(z);
            } catch (RemoteException unused) {
            }
            this.f1688e = true;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final String mo38164b() {
        return "com.google.android.youtube.player.internal.IYouTubeService";
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public final String mo38165c() {
        return "com.google.android.youtube.api.service.START";
    }

    /* renamed from: d */
    public final void mo38166d() {
        if (!this.f1688e) {
            mo38060a(true);
        }
        super.mo38166d();
    }
}
