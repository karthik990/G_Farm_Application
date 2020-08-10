package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.List;
import java.util.Map;

interface zzjp {
    int getTag();

    double readDouble() throws IOException;

    float readFloat() throws IOException;

    String readString() throws IOException;

    void readStringList(List<String> list) throws IOException;

    <T> T zza(zzjs<T> zzjs, zzhf zzhf) throws IOException;

    <T> void zza(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException;

    <K, V> void zza(Map<K, V> map, zzit<K, V> zzit, zzhf zzhf) throws IOException;

    @Deprecated
    <T> T zzb(zzjs<T> zzjs, zzhf zzhf) throws IOException;

    @Deprecated
    <T> void zzb(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException;

    long zzgj() throws IOException;

    long zzgk() throws IOException;

    int zzgl() throws IOException;

    long zzgm() throws IOException;

    int zzgn() throws IOException;

    boolean zzgo() throws IOException;

    String zzgp() throws IOException;

    zzgf zzgq() throws IOException;

    int zzgr() throws IOException;

    int zzgs() throws IOException;

    int zzgt() throws IOException;

    long zzgu() throws IOException;

    int zzgv() throws IOException;

    long zzgw() throws IOException;

    void zzh(List<Double> list) throws IOException;

    int zzhg() throws IOException;

    boolean zzhh() throws IOException;

    void zzi(List<Float> list) throws IOException;

    void zzj(List<Long> list) throws IOException;

    void zzk(List<Long> list) throws IOException;

    void zzl(List<Integer> list) throws IOException;

    void zzm(List<Long> list) throws IOException;

    void zzn(List<Integer> list) throws IOException;

    void zzo(List<Boolean> list) throws IOException;

    void zzp(List<String> list) throws IOException;

    void zzq(List<zzgf> list) throws IOException;

    void zzr(List<Integer> list) throws IOException;

    void zzs(List<Integer> list) throws IOException;

    void zzt(List<Integer> list) throws IOException;

    void zzu(List<Long> list) throws IOException;

    void zzv(List<Integer> list) throws IOException;

    void zzw(List<Long> list) throws IOException;
}
