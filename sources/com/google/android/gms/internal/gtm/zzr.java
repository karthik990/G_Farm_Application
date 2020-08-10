package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.analytics.zzi;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.HashMap;

public final class zzr extends zzi<zzr> {
    private String name;
    private String zzno;
    private String zztz;
    private String zzua;
    private String zzub;
    private String zzuc;
    private String zzud;
    private String zzue;
    private String zzuf;
    private String zzug;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, this.name);
        hashMap.put(Param.SOURCE, this.zztz);
        hashMap.put(Param.MEDIUM, this.zzua);
        hashMap.put("keyword", this.zzub);
        hashMap.put(Param.CONTENT, this.zzuc);
        hashMap.put(TtmlNode.ATTR_ID, this.zzno);
        hashMap.put("adNetworkId", this.zzud);
        hashMap.put("gclid", this.zzue);
        hashMap.put("dclid", this.zzuf);
        hashMap.put(Param.ACLID, this.zzug);
        return zza((Object) hashMap);
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getSource() {
        return this.zztz;
    }

    public final void zzc(String str) {
        this.zztz = str;
    }

    public final String zzbd() {
        return this.zzua;
    }

    public final void zzd(String str) {
        this.zzua = str;
    }

    public final String zzbe() {
        return this.zzub;
    }

    public final void zze(String str) {
        this.zzub = str;
    }

    public final String zzbf() {
        return this.zzuc;
    }

    public final void zzf(String str) {
        this.zzuc = str;
    }

    public final String getId() {
        return this.zzno;
    }

    public final void zzg(String str) {
        this.zzno = str;
    }

    public final String zzbg() {
        return this.zzud;
    }

    public final void zzh(String str) {
        this.zzud = str;
    }

    public final String zzbh() {
        return this.zzue;
    }

    public final void zzi(String str) {
        this.zzue = str;
    }

    public final String zzbi() {
        return this.zzuf;
    }

    public final void zzj(String str) {
        this.zzuf = str;
    }

    public final String zzbj() {
        return this.zzug;
    }

    public final void zzk(String str) {
        this.zzug = str;
    }

    public final /* synthetic */ void zzb(zzi zzi) {
        zzr zzr = (zzr) zzi;
        if (!TextUtils.isEmpty(this.name)) {
            zzr.name = this.name;
        }
        if (!TextUtils.isEmpty(this.zztz)) {
            zzr.zztz = this.zztz;
        }
        if (!TextUtils.isEmpty(this.zzua)) {
            zzr.zzua = this.zzua;
        }
        if (!TextUtils.isEmpty(this.zzub)) {
            zzr.zzub = this.zzub;
        }
        if (!TextUtils.isEmpty(this.zzuc)) {
            zzr.zzuc = this.zzuc;
        }
        if (!TextUtils.isEmpty(this.zzno)) {
            zzr.zzno = this.zzno;
        }
        if (!TextUtils.isEmpty(this.zzud)) {
            zzr.zzud = this.zzud;
        }
        if (!TextUtils.isEmpty(this.zzue)) {
            zzr.zzue = this.zzue;
        }
        if (!TextUtils.isEmpty(this.zzuf)) {
            zzr.zzuf = this.zzuf;
        }
        if (!TextUtils.isEmpty(this.zzug)) {
            zzr.zzug = this.zzug;
        }
    }
}
