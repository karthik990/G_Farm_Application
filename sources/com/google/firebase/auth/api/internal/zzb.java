package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdz;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.internal.firebase_auth.zzed;
import com.google.android.gms.internal.firebase_auth.zzeg;
import com.google.android.gms.internal.firebase_auth.zzeh;
import com.google.android.gms.internal.firebase_auth.zzei;
import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzel;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzeq;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfa;
import com.google.android.gms.internal.firebase_auth.zzfd;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.android.gms.internal.firebase_auth.zzfi;
import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.android.gms.internal.firebase_auth.zzfl;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.android.gms.internal.firebase_auth.zzfo;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.android.gms.internal.firebase_auth.zzfr;
import com.google.android.gms.internal.firebase_auth.zzfs;
import com.google.android.gms.internal.firebase_auth.zzfu;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzt;
import com.google.firebase.auth.zzf;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;

public final class zzb {
    /* access modifiers changed from: private */
    public final zzex zzlb;
    /* access modifiers changed from: private */
    public final zzdt zzlc;
    /* access modifiers changed from: private */
    public final zzeg zzld;

    public zzb(zzex zzex, zzdt zzdt, zzeg zzeg) {
        this.zzlb = (zzex) Preconditions.checkNotNull(zzex);
        this.zzlc = (zzdt) Preconditions.checkNotNull(zzdt);
        this.zzld = (zzeg) Preconditions.checkNotNull(zzeg);
    }

    public final void zza(String str, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzei(str), (zzez<zzes>) new zza<zzes>(this, zzdm));
    }

    public final void zza(zzfr zzfr, zzdm zzdm) {
        Preconditions.checkNotNull(zzfr);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(zzfr, (zzez<zzfq>) new zzn<zzfq>(this, zzdm));
    }

    public final void zza(zzfm zzfm, zzdm zzdm) {
        Preconditions.checkNotNull(zzfm);
        Preconditions.checkNotNull(zzdm);
        if (this.zzlc.zzec().booleanValue() && this.zzld.zzej()) {
            zzfm.zzr(this.zzlc.zzec().booleanValue());
        }
        this.zzlb.zza(zzfm, (zzez<zzfo>) new zzv<zzfo>(this, zzdm));
    }

    public final void zzb(String str, zzdm zzdm) {
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzfi(str), (zzez<zzfl>) new zzaa<zzfl>(this, zzdm));
    }

    public final void zza(String str, UserProfileChangeRequest userProfileChangeRequest, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(userProfileChangeRequest);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzad<zzes>(this, userProfileChangeRequest, zzdm));
    }

    public final void zza(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzac<zzes>(this, str2, zzdm));
    }

    public final void zzb(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzaf<zzes>(this, str2, zzdm));
    }

    public final void zzc(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zzfg zzfg = new zzfg();
        zzfg.zzcw(str);
        zzfg.zzcx(str2);
        this.zzlb.zza(zzfg, (zzez<zzfj>) new zzae<zzfj>(this, zzdm));
    }

    private final void zza(String str, zzez<zzes> zzez) {
        Preconditions.checkNotNull(zzez);
        Preconditions.checkNotEmpty(str);
        zzes zzcn = zzes.zzcn(str);
        if (zzcn.isValid()) {
            zzez.onSuccess(zzcn);
            return;
        }
        this.zzlb.zza(new zzei(zzcn.zzs()), (zzez<zzes>) new zzah<zzes>(this, zzez));
    }

    public final void zza(String str, String str2, String str3, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzfi(str, str2, null, str3), (zzez<zzfl>) new zzd<zzfl>(this, zzdm));
    }

    public final void zzb(String str, String str2, String str3, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzfs(str, str2, str3), (zzez<zzfu>) new zzc<zzfu>(this, zzdm));
    }

    public final void zza(EmailAuthCredential emailAuthCredential, zzdm zzdm) {
        Preconditions.checkNotNull(emailAuthCredential);
        Preconditions.checkNotNull(zzdm);
        if (emailAuthCredential.zzcq()) {
            zza(emailAuthCredential.zzcp(), (zzez<zzes>) new zzf<zzes>(this, emailAuthCredential, zzdm));
        } else {
            zza(new zzeh(emailAuthCredential, null), zzdm);
        }
    }

    /* access modifiers changed from: private */
    public final void zza(zzeh zzeh, zzdm zzdm) {
        Preconditions.checkNotNull(zzeh);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(zzeh, (zzez<zzeg>) new zze<zzeg>(this, zzdm));
    }

    /* access modifiers changed from: private */
    public final void zza(zzdm zzdm, zzes zzes, zzfg zzfg, zzew zzew) {
        Preconditions.checkNotNull(zzdm);
        Preconditions.checkNotNull(zzes);
        Preconditions.checkNotNull(zzfg);
        Preconditions.checkNotNull(zzew);
        zzel zzel = new zzel(zzes.getAccessToken());
        zzex zzex = this.zzlb;
        zzh zzh = new zzh(this, zzew, zzdm, zzes, zzfg);
        zzex.zza(zzel, (zzez<zzek>) zzh);
    }

    /* access modifiers changed from: private */
    public final void zza(zzdm zzdm, zzes zzes, zzem zzem, zzfg zzfg, zzew zzew) {
        Preconditions.checkNotNull(zzdm);
        Preconditions.checkNotNull(zzes);
        Preconditions.checkNotNull(zzem);
        Preconditions.checkNotNull(zzfg);
        Preconditions.checkNotNull(zzew);
        zzex zzex = this.zzlb;
        zzg zzg = new zzg(this, zzfg, zzem, zzdm, zzes, zzew);
        zzex.zza(zzfg, (zzez<zzfj>) zzg);
    }

    /* access modifiers changed from: private */
    public static zzes zza(zzes zzes, zzfj zzfj) {
        Preconditions.checkNotNull(zzes);
        Preconditions.checkNotNull(zzfj);
        String idToken = zzfj.getIdToken();
        String zzs = zzfj.zzs();
        return (TextUtils.isEmpty(idToken) || TextUtils.isEmpty(zzs)) ? zzes : new zzes(zzs, idToken, Long.valueOf(zzfj.zzt()), zzes.zzeu());
    }

    /* access modifiers changed from: private */
    public final void zza(zzes zzes, String str, String str2, Boolean bool, zzf zzf, zzdm zzdm, zzew zzew) {
        Preconditions.checkNotNull(zzes);
        Preconditions.checkNotNull(zzew);
        Preconditions.checkNotNull(zzdm);
        zzel zzel = new zzel(zzes.getAccessToken());
        zzex zzex = this.zzlb;
        zzj zzj = new zzj(this, zzew, str2, str, bool, zzf, zzdm, zzes);
        zzex.zza(zzel, (zzez<zzek>) zzj);
    }

    public final void zzd(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzed(str, str2), (zzez<zzec>) new zzi<zzec>(this, zzdm));
    }

    public final void zza(String str, ActionCodeSettings actionCodeSettings, String str2, zzdm zzdm) {
        zzeq zzeq;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zzfw zzk = zzfw.zzk(actionCodeSettings.getRequestType());
        if (zzk != null) {
            zzeq = new zzeq(zzk);
        } else {
            zzeq = new zzeq(zzfw.OOB_REQ_TYPE_UNSPECIFIED);
        }
        zzeq.zzcj(str);
        zzeq.zza(actionCodeSettings);
        zzeq.zzcl(str2);
        this.zzlb.zza(zzeq, (zzez<Object>) new zzl<Object>(this, zzdm));
    }

    public final void zza(String str, ActionCodeSettings actionCodeSettings, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zzeq zzeq = new zzeq(zzfw.VERIFY_EMAIL);
        zzeq.zzck(str);
        if (actionCodeSettings != null) {
            zzeq.zza(actionCodeSettings);
        }
        zzb(zzeq, zzdm);
    }

    public final void zze(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzfa(str, null, str2), (zzez<zzfd>) new zzk<zzfd>(this, zzdm));
    }

    public final void zzc(String str, String str2, String str3, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(new zzfa(str, str2, str3), (zzez<zzfd>) new zzm<zzfd>(this, zzdm));
    }

    public final void zzd(String str, String str2, String str3, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzdm);
        zza(str3, (zzez<zzes>) new zzp<zzes>(this, str, str2, zzdm));
    }

    public final void zza(String str, zzfm zzfm, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzfm);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzo<zzes>(this, zzfm, zzdm));
    }

    public final void zzc(String str, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzq<zzes>(this, zzdm));
    }

    public final void zzf(String str, String str2, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(zzdm);
        zza(str2, (zzez<zzes>) new zzt<zzes>(this, str, zzdm));
    }

    public final void zza(zzeq zzeq, zzdm zzdm) {
        zzb(zzeq, zzdm);
    }

    public final void zzd(String str, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzu<zzes>(this, zzdm));
    }

    public final void zze(String str, zzdm zzdm) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzdm);
        zza(str, (zzez<zzes>) new zzw<zzes>(this, zzdm));
    }

    public final void zzf(String str, zzdm zzdm) {
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zzb(str, new zzy(this, zzdm));
    }

    private final void zzb(zzeq zzeq, zzdm zzdm) {
        Preconditions.checkNotNull(zzeq);
        Preconditions.checkNotNull(zzdm);
        this.zzlb.zza(zzeq, (zzez<Object>) new zzab<Object>(this, zzdm));
    }

    /* access modifiers changed from: private */
    public final void zza(zzfo zzfo, zzdm zzdm, zzew zzew) {
        Status status;
        if (zzfo.zzfc()) {
            zzf zzdo = zzfo.zzdo();
            String email = zzfo.getEmail();
            String zzba = zzfo.zzba();
            if (zzfo.zzfb()) {
                status = new Status(FirebaseError.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL);
            } else {
                status = zzt.zzdc(zzfo.getErrorMessage());
            }
            if (!this.zzlc.zzec().booleanValue() || !this.zzld.zzej()) {
                zzdm.onFailure(status);
            } else {
                zzdm.zza(new zzdz(status, zzdo, email, zzba));
            }
        } else {
            zza(new zzes(zzfo.zzs(), zzfo.getIdToken(), Long.valueOf(zzfo.zzt()), OAuthConstants.AUTHORIZATION_BEARER), zzfo.getRawUserInfo(), zzfo.getProviderId(), Boolean.valueOf(zzfo.isNewUser()), zzfo.zzdo(), zzdm, zzew);
        }
    }
}
