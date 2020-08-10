package com.google.firebase.auth.api.internal;

import android.app.Activity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzeb;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfd;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.auth.internal.zzz;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

abstract class zzen<ResultT, CallbackT> implements zzan<zzdp, ResultT> {
    protected FirebaseApp zzik;
    protected String zzjl;
    protected final int zzpp;
    final zzep zzpq = new zzep(this);
    protected FirebaseUser zzpr;
    protected CallbackT zzps;
    protected zzz zzpt;
    protected zzel<ResultT> zzpu;
    protected final List<OnVerificationStateChangedCallbacks> zzpv = new ArrayList();
    private Activity zzpw;
    protected Executor zzpx;
    protected zzes zzpy;
    protected zzem zzpz;
    protected zzec zzqa;
    protected zzfd zzqb;
    protected String zzqc;
    protected AuthCredential zzqd;
    protected String zzqe;
    protected String zzqf;
    protected zzeb zzqg;
    protected boolean zzqh;
    /* access modifiers changed from: private */
    public boolean zzqi;
    boolean zzqj;
    private ResultT zzqk;
    private Status zzql;

    static class zza extends LifecycleCallback {
        private final List<OnVerificationStateChangedCallbacks> zzpo;

        public static void zza(Activity activity, List<OnVerificationStateChangedCallbacks> list) {
            LifecycleFragment fragment = getFragment(activity);
            if (((zza) fragment.getCallbackOrNull("PhoneAuthActivityStopCallback", zza.class)) == null) {
                new zza(fragment, list);
            }
        }

        private zza(LifecycleFragment lifecycleFragment, List<OnVerificationStateChangedCallbacks> list) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("PhoneAuthActivityStopCallback", this);
            this.zzpo = list;
        }

        public void onStop() {
            synchronized (this.zzpo) {
                this.zzpo.clear();
            }
        }
    }

    public zzen(int i) {
        this.zzpp = i;
    }

    public abstract void zzdx();

    public final zzen<ResultT, CallbackT> zza(FirebaseApp firebaseApp) {
        this.zzik = (FirebaseApp) Preconditions.checkNotNull(firebaseApp, "firebaseApp cannot be null");
        return this;
    }

    public final zzen<ResultT, CallbackT> zzf(FirebaseUser firebaseUser) {
        this.zzpr = (FirebaseUser) Preconditions.checkNotNull(firebaseUser, "firebaseUser cannot be null");
        return this;
    }

    public final zzen<ResultT, CallbackT> zzb(CallbackT callbackt) {
        this.zzps = Preconditions.checkNotNull(callbackt, "external callback cannot be null");
        return this;
    }

    public final zzen<ResultT, CallbackT> zza(zzz zzz) {
        this.zzpt = (zzz) Preconditions.checkNotNull(zzz, "external failure callback cannot be null");
        return this;
    }

    public final zzen<ResultT, CallbackT> zza(OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Activity activity, Executor executor) {
        synchronized (this.zzpv) {
            this.zzpv.add((OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks));
        }
        this.zzpw = activity;
        if (this.zzpw != null) {
            zza.zza(activity, this.zzpv);
        }
        this.zzpx = (Executor) Preconditions.checkNotNull(executor);
        return this;
    }

    public final zzan<zzdp, ResultT> zzdw() {
        this.zzqh = true;
        return this;
    }

    public final void zzc(ResultT resultt) {
        this.zzqi = true;
        this.zzqj = true;
        this.zzqk = resultt;
        this.zzpu.zza(resultt, null);
    }

    public final void zzc(Status status) {
        this.zzqi = true;
        this.zzqj = false;
        this.zzql = status;
        this.zzpu.zza(null, status);
    }

    /* access modifiers changed from: private */
    public final void zzen() {
        zzdx();
        Preconditions.checkState(this.zzqi, "no success or failure set on method implementation");
    }

    /* access modifiers changed from: private */
    public final void zzd(Status status) {
        zzz zzz = this.zzpt;
        if (zzz != null) {
            zzz.zza(status);
        }
    }
}
