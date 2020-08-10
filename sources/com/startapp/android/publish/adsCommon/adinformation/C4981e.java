package com.startapp.android.publish.adsCommon.adinformation;

import android.content.Context;
import android.graphics.Bitmap;
import com.startapp.android.publish.adsCommon.Utils.C4934a;
import com.startapp.common.C5134a;
import com.startapp.common.C5134a.C5137a;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.adinformation.e */
/* compiled from: StartAppSDK */
public class C4981e implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: a */
    private transient Bitmap f3144a;

    /* renamed from: b */
    private transient Bitmap f3145b;

    /* renamed from: c */
    private transient Bitmap f3146c = null;
    private int height = 1;
    private String imageFallbackUrl;
    private String imageUrlSecured;
    private String name;
    private int width = 1;

    private C4981e() {
        String str = "";
        this.imageUrlSecured = str;
        this.imageFallbackUrl = str;
    }

    /* renamed from: a */
    public String mo62134a() {
        return this.name;
    }

    /* renamed from: a */
    public Bitmap mo62133a(Context context) {
        if (this.f3146c == null) {
            this.f3146c = mo62145f();
            if (this.f3146c == null) {
                this.f3146c = mo62139b(context);
            }
        }
        return this.f3146c;
    }

    /* renamed from: b */
    public int mo62138b() {
        return this.width;
    }

    /* renamed from: c */
    public int mo62142c() {
        return this.height;
    }

    /* renamed from: a */
    public void mo62135a(int i) {
        this.width = i;
    }

    /* renamed from: b */
    public void mo62140b(int i) {
        this.height = i;
    }

    /* renamed from: d */
    public String mo62143d() {
        String str = this.imageUrlSecured;
        return str != null ? str : "";
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo62144e() {
        mo62136a((Bitmap) null);
        new C5134a(mo62143d(), new C5137a() {
            /* renamed from: a */
            public void mo61329a(Bitmap bitmap, int i) {
                C4981e.this.mo62136a(bitmap);
            }
        }, 0).mo62837a();
    }

    /* renamed from: a */
    public void mo62137a(String str) {
        this.imageFallbackUrl = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62141b(String str) {
        this.name = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62136a(Bitmap bitmap) {
        this.f3144a = bitmap;
        if (bitmap != null) {
            this.f3146c = bitmap;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public Bitmap mo62145f() {
        return this.f3144a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public String mo62146g() {
        return this.imageFallbackUrl;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public Bitmap mo62139b(Context context) {
        if (this.f3145b == null) {
            this.f3145b = C4934a.m2858a(context, mo62146g());
        }
        return this.f3145b;
    }

    /* renamed from: c */
    public static C4981e m3014c(String str) {
        C4981e eVar = new C4981e();
        eVar.mo62141b(str);
        return eVar;
    }
}
