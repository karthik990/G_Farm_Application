package com.google.firebase.auth.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzay;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.zzf;
import com.google.firebase.auth.zzv;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import p043io.reactivex.annotations.SchedulerSupport;

public class zzm extends FirebaseUser {
    public static final Creator<zzm> CREATOR = new zzl();
    private zzf zzkw;
    private boolean zzrg;
    private zzes zzth;
    private zzi zzti;
    private String zztj;
    private String zztk;
    private List<zzi> zztl;
    private List<String> zztm;
    private String zztn;
    private Boolean zzto;
    private zzo zztp;
    private zzao zztq;

    zzm(zzes zzes, zzi zzi, String str, String str2, List<zzi> list, List<String> list2, String str3, Boolean bool, zzo zzo, boolean z, zzf zzf, zzao zzao) {
        this.zzth = zzes;
        this.zzti = zzi;
        this.zztj = str;
        this.zztk = str2;
        this.zztl = list;
        this.zztm = list2;
        this.zztn = str3;
        this.zzto = bool;
        this.zztp = zzo;
        this.zzrg = z;
        this.zzkw = zzf;
        this.zztq = zzao;
    }

    public zzm(FirebaseApp firebaseApp, List<? extends UserInfo> list) {
        Preconditions.checkNotNull(firebaseApp);
        this.zztj = firebaseApp.getName();
        this.zztk = "com.google.firebase.auth.internal.DefaultFirebaseUser";
        this.zztn = ExifInterface.GPS_MEASUREMENT_2D;
        zza(list);
    }

    public String getDisplayName() {
        return this.zzti.getDisplayName();
    }

    public Uri getPhotoUrl() {
        return this.zzti.getPhotoUrl();
    }

    public String getEmail() {
        return this.zzti.getEmail();
    }

    public String getPhoneNumber() {
        return this.zzti.getPhoneNumber();
    }

    public final String zzba() {
        zzes zzes = this.zzth;
        if (zzes == null || zzes.getAccessToken() == null) {
            return null;
        }
        Map map = (Map) zzan.zzdf(this.zzth.getAccessToken()).getClaims().get(FirebaseAuthProvider.PROVIDER_ID);
        if (map != null) {
            return (String) map.get("tenant");
        }
        return null;
    }

    public final zzm zzdb(String str) {
        this.zztn = str;
        return this;
    }

    public String getProviderId() {
        return this.zzti.getProviderId();
    }

    public final FirebaseApp zzcu() {
        return FirebaseApp.getInstance(this.zztj);
    }

    public final List<zzi> zzff() {
        return this.zztl;
    }

    public String getUid() {
        return this.zzti.getUid();
    }

    public boolean isAnonymous() {
        Boolean bool = this.zzto;
        if (bool == null || bool.booleanValue()) {
            zzes zzes = this.zzth;
            String str = "";
            if (zzes != null) {
                GetTokenResult zzdf = zzan.zzdf(zzes.getAccessToken());
                if (zzdf != null) {
                    str = zzdf.getSignInProvider();
                }
            }
            boolean z = true;
            if (getProviderData().size() > 1 || (str != null && str.equals(SchedulerSupport.CUSTOM))) {
                z = false;
            }
            this.zzto = Boolean.valueOf(z);
        }
        return this.zzto.booleanValue();
    }

    public final List<String> zzcw() {
        return this.zztm;
    }

    public final FirebaseUser zza(List<? extends UserInfo> list) {
        Preconditions.checkNotNull(list);
        this.zztl = new ArrayList(list.size());
        this.zztm = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            UserInfo userInfo = (UserInfo) list.get(i);
            if (userInfo.getProviderId().equals(FirebaseAuthProvider.PROVIDER_ID)) {
                this.zzti = (zzi) userInfo;
            } else {
                this.zztm.add(userInfo.getProviderId());
            }
            this.zztl.add((zzi) userInfo);
        }
        if (this.zzti == null) {
            this.zzti = (zzi) this.zztl.get(0);
        }
        return this;
    }

    public List<? extends UserInfo> getProviderData() {
        return this.zztl;
    }

    public final zzes zzcy() {
        return this.zzth;
    }

    public final String zzda() {
        return zzcy().getAccessToken();
    }

    public final String zzcz() {
        return this.zzth.zzew();
    }

    public final void zza(zzes zzes) {
        this.zzth = (zzes) Preconditions.checkNotNull(zzes);
    }

    public boolean isEmailVerified() {
        return this.zzti.isEmailVerified();
    }

    public final void zza(zzo zzo) {
        this.zztp = zzo;
    }

    public FirebaseUserMetadata getMetadata() {
        return this.zztp;
    }

    public final void zzs(boolean z) {
        this.zzrg = z;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final void zzb(zzf zzf) {
        this.zzkw = zzf;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final void zzb(List<zzx> list) {
        this.zztq = zzao.zzf(list);
    }

    public final List<zzx> zzdc() {
        zzao zzao = this.zztq;
        if (zzao != null) {
            return zzao.zzdp();
        }
        return zzay.zzce();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, zzcy(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzti, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zztj, false);
        SafeParcelWriter.writeString(parcel, 4, this.zztk, false);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zztl, false);
        SafeParcelWriter.writeStringList(parcel, 6, zzcw(), false);
        SafeParcelWriter.writeString(parcel, 7, this.zztn, false);
        SafeParcelWriter.writeBooleanObject(parcel, 8, Boolean.valueOf(isAnonymous()), false);
        SafeParcelWriter.writeParcelable(parcel, 9, getMetadata(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzrg);
        SafeParcelWriter.writeParcelable(parcel, 11, this.zzkw, i, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zztq, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public static FirebaseUser zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser) {
        zzm zzm = new zzm(firebaseApp, firebaseUser.getProviderData());
        if (firebaseUser instanceof zzm) {
            zzm zzm2 = (zzm) firebaseUser;
            zzm.zztn = zzm2.zztn;
            zzm.zztk = zzm2.zztk;
            zzm.zztp = (zzo) zzm2.getMetadata();
        } else {
            zzm.zztp = null;
        }
        if (firebaseUser.zzcy() != null) {
            zzm.zza(firebaseUser.zzcy());
        }
        if (!firebaseUser.isAnonymous()) {
            zzm.zzcx();
        }
        return zzm;
    }

    public final /* synthetic */ zzv zzdb() {
        return new zzq(this);
    }

    public final /* synthetic */ FirebaseUser zzcx() {
        this.zzto = Boolean.valueOf(false);
        return this;
    }
}
