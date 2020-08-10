package com.google.android.gms.internal.firebase_auth;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import java.io.IOException;
import java.util.List;

final class zzgy implements zzjp {
    private int tag;
    private final zzgr zzwt;
    private int zzwu;
    private int zzwv = 0;

    public static zzgy zza(zzgr zzgr) {
        if (zzgr.zzwh != null) {
            return zzgr.zzwh;
        }
        return new zzgy(zzgr);
    }

    private zzgy(zzgr zzgr) {
        this.zzwt = (zzgr) zzht.zza(zzgr, Keys.INPUT);
        this.zzwt.zzwh = this;
    }

    public final int zzhg() throws IOException {
        int i = this.zzwv;
        if (i != 0) {
            this.tag = i;
            this.zzwv = 0;
        } else {
            this.tag = this.zzwt.zzgi();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.zzwu) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final int getTag() {
        return this.tag;
    }

    public final boolean zzhh() throws IOException {
        if (!this.zzwt.zzgy()) {
            int i = this.tag;
            if (i != this.zzwu) {
                return this.zzwt.zzt(i);
            }
        }
        return false;
    }

    private final void zzac(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzic.zziw();
        }
    }

    public final double readDouble() throws IOException {
        zzac(1);
        return this.zzwt.readDouble();
    }

    public final float readFloat() throws IOException {
        zzac(5);
        return this.zzwt.readFloat();
    }

    public final long zzgj() throws IOException {
        zzac(0);
        return this.zzwt.zzgj();
    }

    public final long zzgk() throws IOException {
        zzac(0);
        return this.zzwt.zzgk();
    }

    public final int zzgl() throws IOException {
        zzac(0);
        return this.zzwt.zzgl();
    }

    public final long zzgm() throws IOException {
        zzac(1);
        return this.zzwt.zzgm();
    }

    public final int zzgn() throws IOException {
        zzac(5);
        return this.zzwt.zzgn();
    }

    public final boolean zzgo() throws IOException {
        zzac(0);
        return this.zzwt.zzgo();
    }

    public final String readString() throws IOException {
        zzac(2);
        return this.zzwt.readString();
    }

    public final String zzgp() throws IOException {
        zzac(2);
        return this.zzwt.zzgp();
    }

    public final <T> T zza(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        zzac(2);
        return zzc(zzjs, zzhf);
    }

    public final <T> T zzb(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        zzac(3);
        return zzd(zzjs, zzhf);
    }

    private final <T> T zzc(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int zzgr = this.zzwt.zzgr();
        if (this.zzwt.zzwe < this.zzwt.zzwf) {
            int zzu = this.zzwt.zzu(zzgr);
            T newInstance = zzjs.newInstance();
            this.zzwt.zzwe++;
            zzjs.zza(newInstance, this, zzhf);
            zzjs.zzf(newInstance);
            this.zzwt.zzs(0);
            this.zzwt.zzwe--;
            this.zzwt.zzv(zzu);
            return newInstance;
        }
        throw new zzic("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    private final <T> T zzd(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int i = this.zzwu;
        this.zzwu = ((this.tag >>> 3) << 3) | 4;
        try {
            T newInstance = zzjs.newInstance();
            zzjs.zza(newInstance, this, zzhf);
            zzjs.zzf(newInstance);
            if (this.tag == this.zzwu) {
                return newInstance;
            }
            throw zzic.zziy();
        } finally {
            this.zzwu = i;
        }
    }

    public final zzgf zzgq() throws IOException {
        zzac(2);
        return this.zzwt.zzgq();
    }

    public final int zzgr() throws IOException {
        zzac(0);
        return this.zzwt.zzgr();
    }

    public final int zzgs() throws IOException {
        zzac(0);
        return this.zzwt.zzgs();
    }

    public final int zzgt() throws IOException {
        zzac(5);
        return this.zzwt.zzgt();
    }

    public final long zzgu() throws IOException {
        zzac(1);
        return this.zzwt.zzgu();
    }

    public final int zzgv() throws IOException {
        zzac(0);
        return this.zzwt.zzgv();
    }

    public final long zzgw() throws IOException {
        zzac(0);
        return this.zzwt.zzgw();
    }

    public final void zzh(List<Double> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhb) {
            zzhb zzhb = (zzhb) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzhb.zzc(this.zzwt.readDouble());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzad(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zzhb.zzc(this.zzwt.readDouble());
                } while (this.zzwt.zzgz() < zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Double.valueOf(this.zzwt.readDouble()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzad(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Double.valueOf(this.zzwt.readDouble()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzi(List<Float> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzho) {
            zzho zzho = (zzho) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzae(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zzho.zzc(this.zzwt.readFloat());
                } while (this.zzwt.zzgz() < zzgz);
            } else if (i == 5) {
                do {
                    zzho.zzc(this.zzwt.readFloat());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzae(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Float.valueOf(this.zzwt.readFloat()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else if (i2 == 5) {
                do {
                    list.add(Float.valueOf(this.zzwt.readFloat()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzj(List<Long> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgj());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgj());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzwt.zzgj()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Long.valueOf(this.zzwt.zzgj()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzk(List<Long> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgk());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgk());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzwt.zzgk()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Long.valueOf(this.zzwt.zzgk()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzl(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgl());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgl());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgl()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgl()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzm(List<Long> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zziq.zzl(this.zzwt.zzgm());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzad(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zziq.zzl(this.zzwt.zzgm());
                } while (this.zzwt.zzgz() < zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzwt.zzgm()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzad(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Long.valueOf(this.zzwt.zzgm()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzn(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzae(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zzhu.zzaw(this.zzwt.zzgn());
                } while (this.zzwt.zzgz() < zzgz);
            } else if (i == 5) {
                do {
                    zzhu.zzaw(this.zzwt.zzgn());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzae(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgn()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgn()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzo(List<Boolean> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzgd) {
            zzgd zzgd = (zzgd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzgd.addBoolean(this.zzwt.zzgo());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzgd.addBoolean(this.zzwt.zzgo());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Boolean.valueOf(this.zzwt.zzgo()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Boolean.valueOf(this.zzwt.zzgo()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void readStringList(List<String> list) throws IOException {
        zza(list, false);
    }

    public final void zzp(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int zzgi;
        int zzgi2;
        if ((this.tag & 7) != 2) {
            throw zzic.zziw();
        } else if (!(list instanceof zzij) || z) {
            do {
                list.add(z ? zzgp() : readString());
                if (!this.zzwt.zzgy()) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == this.tag);
            this.zzwv = zzgi;
        } else {
            zzij zzij = (zzij) list;
            do {
                zzij.zzc(zzgq());
                if (!this.zzwt.zzgy()) {
                    zzgi2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi2 == this.tag);
            this.zzwv = zzgi2;
        }
    }

    public final <T> void zza(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int zzgi;
        int i = this.tag;
        if ((i & 7) == 2) {
            do {
                list.add(zzc(zzjs, zzhf));
                if (!this.zzwt.zzgy() && this.zzwv == 0) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == i);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final <T> void zzb(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int zzgi;
        int i = this.tag;
        if ((i & 7) == 3) {
            do {
                list.add(zzd(zzjs, zzhf));
                if (!this.zzwt.zzgy() && this.zzwv == 0) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == i);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final void zzq(List<zzgf> list) throws IOException {
        int zzgi;
        if ((this.tag & 7) == 2) {
            do {
                list.add(zzgq());
                if (!this.zzwt.zzgy()) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == this.tag);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final void zzr(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgr());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgr());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgr()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgr()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzs(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgs());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgs());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgs()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgs()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzt(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzae(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zzhu.zzaw(this.zzwt.zzgt());
                } while (this.zzwt.zzgz() < zzgz);
            } else if (i == 5) {
                do {
                    zzhu.zzaw(this.zzwt.zzgt());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzae(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgt()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgt()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzu(List<Long> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zziq.zzl(this.zzwt.zzgu());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgr = this.zzwt.zzgr();
                zzad(zzgr);
                int zzgz = this.zzwt.zzgz() + zzgr;
                do {
                    zziq.zzl(this.zzwt.zzgu());
                } while (this.zzwt.zzgz() < zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzwt.zzgu()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgr2 = this.zzwt.zzgr();
                zzad(zzgr2);
                int zzgz2 = this.zzwt.zzgz() + zzgr2;
                do {
                    list.add(Long.valueOf(this.zzwt.zzgu()));
                } while (this.zzwt.zzgz() < zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzv(List<Integer> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgv());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgv());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgv()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Integer.valueOf(this.zzwt.zzgv()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    public final void zzw(List<Long> list) throws IOException {
        int zzgi;
        int zzgi2;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgw());
                    if (!this.zzwt.zzgy()) {
                        zzgi2 = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi2 == this.tag);
                this.zzwv = zzgi2;
            } else if (i == 2) {
                int zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgw());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
            } else {
                throw zzic.zziw();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzwt.zzgw()));
                    if (!this.zzwt.zzgy()) {
                        zzgi = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (zzgi == this.tag);
                this.zzwv = zzgi;
            } else if (i2 == 2) {
                int zzgz2 = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    list.add(Long.valueOf(this.zzwt.zzgw()));
                } while (this.zzwt.zzgz() < zzgz2);
                zzaf(zzgz2);
            } else {
                throw zzic.zziw();
            }
        }
    }

    private static void zzad(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzic.zziy();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        if (zzhh() != false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        throw new com.google.android.gms.internal.firebase_auth.zzic(r6);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.firebase_auth.zzit<K, V> r9, com.google.android.gms.internal.firebase_auth.zzhf r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzac(r0)
            com.google.android.gms.internal.firebase_auth.zzgr r1 = r7.zzwt
            int r1 = r1.zzgr()
            com.google.android.gms.internal.firebase_auth.zzgr r2 = r7.zzwt
            int r1 = r2.zzu(r1)
            K r2 = r9.zzacl
            V r3 = r9.zzacn
        L_0x0014:
            int r4 = r7.zzhg()     // Catch:{ all -> 0x0064 }
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L_0x005b
            com.google.android.gms.internal.firebase_auth.zzgr r5 = r7.zzwt     // Catch:{ all -> 0x0064 }
            boolean r5 = r5.zzgy()     // Catch:{ all -> 0x0064 }
            if (r5 != 0) goto L_0x005b
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L_0x0046
            if (r4 == r0) goto L_0x0039
            boolean r4 = r7.zzhh()     // Catch:{ zzib -> 0x004e }
            if (r4 == 0) goto L_0x0033
            goto L_0x0014
        L_0x0033:
            com.google.android.gms.internal.firebase_auth.zzic r4 = new com.google.android.gms.internal.firebase_auth.zzic     // Catch:{ zzib -> 0x004e }
            r4.<init>(r6)     // Catch:{ zzib -> 0x004e }
            throw r4     // Catch:{ zzib -> 0x004e }
        L_0x0039:
            com.google.android.gms.internal.firebase_auth.zzlb r4 = r9.zzacm     // Catch:{ zzib -> 0x004e }
            V r5 = r9.zzacn     // Catch:{ zzib -> 0x004e }
            java.lang.Class r5 = r5.getClass()     // Catch:{ zzib -> 0x004e }
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch:{ zzib -> 0x004e }
            goto L_0x0014
        L_0x0046:
            com.google.android.gms.internal.firebase_auth.zzlb r4 = r9.zzack     // Catch:{ zzib -> 0x004e }
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch:{ zzib -> 0x004e }
            goto L_0x0014
        L_0x004e:
            boolean r4 = r7.zzhh()     // Catch:{ all -> 0x0064 }
            if (r4 == 0) goto L_0x0055
            goto L_0x0014
        L_0x0055:
            com.google.android.gms.internal.firebase_auth.zzic r8 = new com.google.android.gms.internal.firebase_auth.zzic     // Catch:{ all -> 0x0064 }
            r8.<init>(r6)     // Catch:{ all -> 0x0064 }
            throw r8     // Catch:{ all -> 0x0064 }
        L_0x005b:
            r8.put(r2, r3)     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.firebase_auth.zzgr r8 = r7.zzwt
            r8.zzv(r1)
            return
        L_0x0064:
            r8 = move-exception
            com.google.android.gms.internal.firebase_auth.zzgr r9 = r7.zzwt
            r9.zzv(r1)
            goto L_0x006c
        L_0x006b:
            throw r8
        L_0x006c:
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgy.zza(java.util.Map, com.google.android.gms.internal.firebase_auth.zzit, com.google.android.gms.internal.firebase_auth.zzhf):void");
    }

    private final Object zza(zzlb zzlb, Class<?> cls, zzhf zzhf) throws IOException {
        switch (zzlb) {
            case BOOL:
                return Boolean.valueOf(zzgo());
            case BYTES:
                return zzgq();
            case DOUBLE:
                return Double.valueOf(readDouble());
            case ENUM:
                return Integer.valueOf(zzgs());
            case FIXED32:
                return Integer.valueOf(zzgn());
            case FIXED64:
                return Long.valueOf(zzgm());
            case FLOAT:
                return Float.valueOf(readFloat());
            case INT32:
                return Integer.valueOf(zzgl());
            case INT64:
                return Long.valueOf(zzgk());
            case MESSAGE:
                zzac(2);
                return zzc(zzjo.zzjv().zzf(cls), zzhf);
            case SFIXED32:
                return Integer.valueOf(zzgt());
            case SFIXED64:
                return Long.valueOf(zzgu());
            case SINT32:
                return Integer.valueOf(zzgv());
            case SINT64:
                return Long.valueOf(zzgw());
            case STRING:
                return zzgp();
            case UINT32:
                return Integer.valueOf(zzgr());
            case UINT64:
                return Long.valueOf(zzgj());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzae(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzic.zziy();
        }
    }

    private final void zzaf(int i) throws IOException {
        if (this.zzwt.zzgz() != i) {
            throw zzic.zzir();
        }
    }
}
