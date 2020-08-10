package com.google.android.gms.tagmanager;

final class zzaw implements Runnable {
    private final /* synthetic */ zzat zzagd;
    private final /* synthetic */ String zzagf;

    zzaw(zzat zzat, String str) {
        this.zzagd = zzat;
        this.zzagf = str;
    }

    public final void run() {
        this.zzagd.zzat(this.zzagf);
    }
}
