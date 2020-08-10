package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

final class zzy implements Runnable {
    private final String zztj;
    final /* synthetic */ zzv zzub;

    zzy(zzv zzv, String str) {
        this.zzub = zzv;
        this.zztj = Preconditions.checkNotEmpty(str);
    }

    public final void run() {
        boolean z;
        FirebaseApp instance = FirebaseApp.getInstance(this.zztj);
        FirebaseAuth instance2 = FirebaseAuth.getInstance(instance);
        zzal.initialize(instance.getApplicationContext());
        try {
            z = ((Boolean) zzal.zzup.get()).booleanValue();
        } catch (SecurityException unused) {
            z = true;
        }
        if (instance2.getCurrentUser() != null && z) {
            Task accessToken = instance2.getAccessToken(true);
            zzv.zzjt.mo26595v("Token refreshing started", new Object[0]);
            accessToken.addOnFailureListener(new zzx(this));
        }
    }
}
