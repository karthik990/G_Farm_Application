package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.List;

public final class zzoy {
    private final List<zzot> zzatu;
    private final List<zzot> zzatv;
    private final List<zzot> zzatw;
    private final List<zzot> zzatx;
    private final List<zzot> zzava;
    private final List<zzot> zzavb;
    private final List<String> zzavc;
    private final List<String> zzavd;
    private final List<String> zzave;
    private final List<String> zzavf;

    private zzoy() {
        this.zzatu = new ArrayList();
        this.zzatv = new ArrayList();
        this.zzatw = new ArrayList();
        this.zzatx = new ArrayList();
        this.zzava = new ArrayList();
        this.zzavb = new ArrayList();
        this.zzavc = new ArrayList();
        this.zzavd = new ArrayList();
        this.zzave = new ArrayList();
        this.zzavf = new ArrayList();
    }

    public final zzoy zzd(zzot zzot) {
        this.zzatu.add(zzot);
        return this;
    }

    public final zzoy zze(zzot zzot) {
        this.zzatv.add(zzot);
        return this;
    }

    public final zzoy zzf(zzot zzot) {
        this.zzatw.add(zzot);
        return this;
    }

    public final zzoy zzct(String str) {
        this.zzave.add(str);
        return this;
    }

    public final zzoy zzg(zzot zzot) {
        this.zzatx.add(zzot);
        return this;
    }

    public final zzoy zzcu(String str) {
        this.zzavf.add(str);
        return this;
    }

    public final zzoy zzh(zzot zzot) {
        this.zzava.add(zzot);
        return this;
    }

    public final zzoy zzcv(String str) {
        this.zzavc.add(str);
        return this;
    }

    public final zzoy zzi(zzot zzot) {
        this.zzavb.add(zzot);
        return this;
    }

    public final zzoy zzcw(String str) {
        this.zzavd.add(str);
        return this;
    }

    public final zzox zzms() {
        zzox zzox = new zzox(this.zzatu, this.zzatv, this.zzatw, this.zzatx, this.zzava, this.zzavb, this.zzavc, this.zzavd, this.zzave, this.zzavf);
        return zzox;
    }
}
