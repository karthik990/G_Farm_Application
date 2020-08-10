package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

interface zzje {
    int getTag();

    double readDouble() throws IOException;

    float readFloat() throws IOException;

    String readString() throws IOException;

    void readStringList(List<String> list) throws IOException;

    <T> T zza(zzjj<T> zzjj, zzgv zzgv) throws IOException;

    <T> void zza(List<T> list, zzjj<T> zzjj, zzgv zzgv) throws IOException;

    <K, V> void zza(Map<K, V> map, zzih<K, V> zzih, zzgv zzgv) throws IOException;

    @Deprecated
    <T> T zzb(zzjj<T> zzjj, zzgv zzgv) throws IOException;

    @Deprecated
    <T> void zzb(List<T> list, zzjj<T> zzjj, zzgv zzgv) throws IOException;

    void zzc(List<Double> list) throws IOException;

    void zzd(List<Float> list) throws IOException;

    void zze(List<Long> list) throws IOException;

    void zzf(List<Long> list) throws IOException;

    long zzfe() throws IOException;

    long zzff() throws IOException;

    int zzfg() throws IOException;

    long zzfh() throws IOException;

    int zzfi() throws IOException;

    boolean zzfj() throws IOException;

    String zzfk() throws IOException;

    zzfw zzfl() throws IOException;

    int zzfm() throws IOException;

    int zzfn() throws IOException;

    int zzfo() throws IOException;

    long zzfp() throws IOException;

    int zzfq() throws IOException;

    long zzfr() throws IOException;

    void zzg(List<Integer> list) throws IOException;

    int zzgb() throws IOException;

    boolean zzgc() throws IOException;

    void zzh(List<Long> list) throws IOException;

    void zzi(List<Integer> list) throws IOException;

    void zzj(List<Boolean> list) throws IOException;

    void zzk(List<String> list) throws IOException;

    void zzl(List<zzfw> list) throws IOException;

    void zzm(List<Integer> list) throws IOException;

    void zzn(List<Integer> list) throws IOException;

    void zzo(List<Integer> list) throws IOException;

    void zzp(List<Long> list) throws IOException;

    void zzq(List<Integer> list) throws IOException;

    void zzr(List<Long> list) throws IOException;
}
