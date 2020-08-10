package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public abstract class zzai {
    private String zzbs;
    private String zzbt;
    private long zzcb = -1;
    private zzcl zzcc;

    public abstract void addHeader(String str, String str2) throws IOException;

    public void zza(int i, int i2) throws IOException {
    }

    public abstract zzaj zzai() throws IOException;

    public final void setContentLength(long j) throws IOException {
        this.zzcb = j;
    }

    public final long getContentLength() {
        return this.zzcb;
    }

    public final void setContentEncoding(String str) throws IOException {
        this.zzbs = str;
    }

    public final String getContentEncoding() {
        return this.zzbs;
    }

    public final void setContentType(String str) throws IOException {
        this.zzbt = str;
    }

    public final String getContentType() {
        return this.zzbt;
    }

    public final void zza(zzcl zzcl) throws IOException {
        this.zzcc = zzcl;
    }

    public final zzcl zzah() {
        return this.zzcc;
    }
}
