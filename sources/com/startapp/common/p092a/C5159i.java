package com.startapp.common.p092a;

import android.content.Context;
import android.widget.Toast;

/* renamed from: com.startapp.common.a.i */
/* compiled from: StartAppSDK */
public class C5159i {

    /* renamed from: a */
    private static C5159i f3569a = new C5159i();

    /* renamed from: b */
    private Toast f3570b;

    private C5159i() {
    }

    /* renamed from: a */
    public void mo62876a(Context context, String str) {
        Toast toast = this.f3570b;
        if (toast == null) {
            this.f3570b = Toast.makeText(context, str, 0);
        } else {
            toast.setText(str);
            this.f3570b.setDuration(0);
        }
        this.f3570b.show();
    }

    /* renamed from: a */
    public static C5159i m3829a() {
        return f3569a;
    }
}
