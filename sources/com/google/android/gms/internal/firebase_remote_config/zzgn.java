package com.google.android.gms.internal.firebase_remote_config;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import java.io.IOException;
import java.util.List;

final class zzgn implements zzje {
    private int tag;
    private final zzgi zzpl;
    private int zzpm;
    private int zzpn = 0;

    public static zzgn zza(zzgi zzgi) {
        if (zzgi.zzpa != null) {
            return zzgi.zzpa;
        }
        return new zzgn(zzgi);
    }

    private zzgn(zzgi zzgi) {
        this.zzpl = (zzgi) zzhk.zza(zzgi, Keys.INPUT);
        this.zzpl.zzpa = this;
    }

    public final int zzgb() throws IOException {
        int i = this.zzpn;
        if (i != 0) {
            this.tag = i;
            this.zzpn = 0;
        } else {
            this.tag = this.zzpl.zzfd();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.zzpm) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final int getTag() {
        return this.tag;
    }

    public final boolean zzgc() throws IOException {
        if (!this.zzpl.zzft()) {
            int i = this.tag;
            if (i != this.zzpm) {
                return this.zzpl.zzz(i);
            }
        }
        return false;
    }

    private final void zzaj(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzho.zzhl();
        }
    }

    public final double readDouble() throws IOException {
        zzaj(1);
        return this.zzpl.readDouble();
    }

    public final float readFloat() throws IOException {
        zzaj(5);
        return this.zzpl.readFloat();
    }

    public final long zzfe() throws IOException {
        zzaj(0);
        return this.zzpl.zzfe();
    }

    public final long zzff() throws IOException {
        zzaj(0);
        return this.zzpl.zzff();
    }

    public final int zzfg() throws IOException {
        zzaj(0);
        return this.zzpl.zzfg();
    }

    public final long zzfh() throws IOException {
        zzaj(1);
        return this.zzpl.zzfh();
    }

    public final int zzfi() throws IOException {
        zzaj(5);
        return this.zzpl.zzfi();
    }

    public final boolean zzfj() throws IOException {
        zzaj(0);
        return this.zzpl.zzfj();
    }

    public final String readString() throws IOException {
        zzaj(2);
        return this.zzpl.readString();
    }

    public final String zzfk() throws IOException {
        zzaj(2);
        return this.zzpl.zzfk();
    }

    public final <T> T zza(zzjj<T> zzjj, zzgv zzgv) throws IOException {
        zzaj(2);
        return zzc(zzjj, zzgv);
    }

    public final <T> T zzb(zzjj<T> zzjj, zzgv zzgv) throws IOException {
        zzaj(3);
        return zzd(zzjj, zzgv);
    }

    private final <T> T zzc(zzjj<T> zzjj, zzgv zzgv) throws IOException {
        int zzfm = this.zzpl.zzfm();
        if (this.zzpl.zzox < this.zzpl.zzoy) {
            int zzaa = this.zzpl.zzaa(zzfm);
            T newInstance = zzjj.newInstance();
            this.zzpl.zzox++;
            zzjj.zza(newInstance, this, zzgv);
            zzjj.zzm(newInstance);
            this.zzpl.zzy(0);
            this.zzpl.zzox--;
            this.zzpl.zzab(zzaa);
            return newInstance;
        }
        throw new zzho("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    private final <T> T zzd(zzjj<T> zzjj, zzgv zzgv) throws IOException {
        int i = this.zzpm;
        this.zzpm = ((this.tag >>> 3) << 3) | 4;
        try {
            T newInstance = zzjj.newInstance();
            zzjj.zza(newInstance, this, zzgv);
            zzjj.zzm(newInstance);
            if (this.tag == this.zzpm) {
                return newInstance;
            }
            throw zzho.zzhn();
        } finally {
            this.zzpm = i;
        }
    }

    public final zzfw zzfl() throws IOException {
        zzaj(2);
        return this.zzpl.zzfl();
    }

    public final int zzfm() throws IOException {
        zzaj(0);
        return this.zzpl.zzfm();
    }

    public final int zzfn() throws IOException {
        zzaj(0);
        return this.zzpl.zzfn();
    }

    public final int zzfo() throws IOException {
        zzaj(5);
        return this.zzpl.zzfo();
    }

    public final long zzfp() throws IOException {
        zzaj(1);
        return this.zzpl.zzfp();
    }

    public final int zzfq() throws IOException {
        zzaj(0);
        return this.zzpl.zzfq();
    }

    public final long zzfr() throws IOException {
        zzaj(0);
        return this.zzpl.zzfr();
    }

    public final void zzc(List<Double> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzgs) {
            zzgs zzgs = (zzgs) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzgs.zze(this.zzpl.readDouble());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzak(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzgs.zze(this.zzpl.readDouble());
                } while (this.zzpl.zzfu() < zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Double.valueOf(this.zzpl.readDouble()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzak(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Double.valueOf(this.zzpl.readDouble()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzd(List<Float> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhg) {
            zzhg zzhg = (zzhg) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzal(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzhg.zzd(this.zzpl.readFloat());
                } while (this.zzpl.zzfu() < zzfu);
            } else if (i == 5) {
                do {
                    zzhg.zzd(this.zzpl.readFloat());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzal(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Float.valueOf(this.zzpl.readFloat()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else if (i2 == 5) {
                do {
                    list.add(Float.valueOf(this.zzpl.readFloat()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zze(List<Long> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzic.zzp(this.zzpl.zzfe());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzic.zzp(this.zzpl.zzfe());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzpl.zzfe()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Long.valueOf(this.zzpl.zzfe()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzf(List<Long> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzic.zzp(this.zzpl.zzff());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzic.zzp(this.zzpl.zzff());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzpl.zzff()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Long.valueOf(this.zzpl.zzff()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzg(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhj.zzbc(this.zzpl.zzfg());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzhj.zzbc(this.zzpl.zzfg());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfg()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfg()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzh(List<Long> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzic.zzp(this.zzpl.zzfh());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzak(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzic.zzp(this.zzpl.zzfh());
                } while (this.zzpl.zzfu() < zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzpl.zzfh()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzak(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Long.valueOf(this.zzpl.zzfh()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzi(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzal(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzhj.zzbc(this.zzpl.zzfi());
                } while (this.zzpl.zzfu() < zzfu);
            } else if (i == 5) {
                do {
                    zzhj.zzbc(this.zzpl.zzfi());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzal(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfi()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfi()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzj(List<Boolean> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzfu) {
            zzfu zzfu = (zzfu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzfu.addBoolean(this.zzpl.zzfj());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzfu.addBoolean(this.zzpl.zzfj());
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Boolean.valueOf(this.zzpl.zzfj()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu3 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Boolean.valueOf(this.zzpl.zzfj()));
                } while (this.zzpl.zzfu() < zzfu3);
                zzam(zzfu3);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void readStringList(List<String> list) throws IOException {
        zza(list, false);
    }

    public final void zzk(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int zzfd;
        int zzfd2;
        if ((this.tag & 7) != 2) {
            throw zzho.zzhl();
        } else if (!(list instanceof zzhx) || z) {
            do {
                list.add(z ? zzfk() : readString());
                if (!this.zzpl.zzft()) {
                    zzfd = this.zzpl.zzfd();
                } else {
                    return;
                }
            } while (zzfd == this.tag);
            this.zzpn = zzfd;
        } else {
            zzhx zzhx = (zzhx) list;
            do {
                zzhx.zzd(zzfl());
                if (!this.zzpl.zzft()) {
                    zzfd2 = this.zzpl.zzfd();
                } else {
                    return;
                }
            } while (zzfd2 == this.tag);
            this.zzpn = zzfd2;
        }
    }

    public final <T> void zza(List<T> list, zzjj<T> zzjj, zzgv zzgv) throws IOException {
        int zzfd;
        int i = this.tag;
        if ((i & 7) == 2) {
            do {
                list.add(zzc(zzjj, zzgv));
                if (!this.zzpl.zzft() && this.zzpn == 0) {
                    zzfd = this.zzpl.zzfd();
                } else {
                    return;
                }
            } while (zzfd == i);
            this.zzpn = zzfd;
            return;
        }
        throw zzho.zzhl();
    }

    public final <T> void zzb(List<T> list, zzjj<T> zzjj, zzgv zzgv) throws IOException {
        int zzfd;
        int i = this.tag;
        if ((i & 7) == 3) {
            do {
                list.add(zzd(zzjj, zzgv));
                if (!this.zzpl.zzft() && this.zzpn == 0) {
                    zzfd = this.zzpl.zzfd();
                } else {
                    return;
                }
            } while (zzfd == i);
            this.zzpn = zzfd;
            return;
        }
        throw zzho.zzhl();
    }

    public final void zzl(List<zzfw> list) throws IOException {
        int zzfd;
        if ((this.tag & 7) == 2) {
            do {
                list.add(zzfl());
                if (!this.zzpl.zzft()) {
                    zzfd = this.zzpl.zzfd();
                } else {
                    return;
                }
            } while (zzfd == this.tag);
            this.zzpn = zzfd;
            return;
        }
        throw zzho.zzhl();
    }

    public final void zzm(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhj.zzbc(this.zzpl.zzfm());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzhj.zzbc(this.zzpl.zzfm());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfm()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfm()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzn(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhj.zzbc(this.zzpl.zzfn());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzhj.zzbc(this.zzpl.zzfn());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfn()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfn()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzo(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzal(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzhj.zzbc(this.zzpl.zzfo());
                } while (this.zzpl.zzfu() < zzfu);
            } else if (i == 5) {
                do {
                    zzhj.zzbc(this.zzpl.zzfo());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzal(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfo()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfo()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzp(List<Long> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzic.zzp(this.zzpl.zzfp());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfm = this.zzpl.zzfm();
                zzak(zzfm);
                int zzfu = this.zzpl.zzfu() + zzfm;
                do {
                    zzic.zzp(this.zzpl.zzfp());
                } while (this.zzpl.zzfu() < zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzpl.zzfp()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfm2 = this.zzpl.zzfm();
                zzak(zzfm2);
                int zzfu2 = this.zzpl.zzfu() + zzfm2;
                do {
                    list.add(Long.valueOf(this.zzpl.zzfp()));
                } while (this.zzpl.zzfu() < zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzq(List<Integer> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhj.zzbc(this.zzpl.zzfq());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzhj.zzbc(this.zzpl.zzfq());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfq()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Integer.valueOf(this.zzpl.zzfq()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    public final void zzr(List<Long> list) throws IOException {
        int zzfd;
        int zzfd2;
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzic.zzp(this.zzpl.zzfr());
                    if (!this.zzpl.zzft()) {
                        zzfd2 = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd2 == this.tag);
                this.zzpn = zzfd2;
            } else if (i == 2) {
                int zzfu = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    zzic.zzp(this.zzpl.zzfr());
                } while (this.zzpl.zzfu() < zzfu);
                zzam(zzfu);
            } else {
                throw zzho.zzhl();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzpl.zzfr()));
                    if (!this.zzpl.zzft()) {
                        zzfd = this.zzpl.zzfd();
                    } else {
                        return;
                    }
                } while (zzfd == this.tag);
                this.zzpn = zzfd;
            } else if (i2 == 2) {
                int zzfu2 = this.zzpl.zzfu() + this.zzpl.zzfm();
                do {
                    list.add(Long.valueOf(this.zzpl.zzfr()));
                } while (this.zzpl.zzfu() < zzfu2);
                zzam(zzfu2);
            } else {
                throw zzho.zzhl();
            }
        }
    }

    private static void zzak(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzho.zzhn();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        if (zzgc() != false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        throw new com.google.android.gms.internal.firebase_remote_config.zzho(r6);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.firebase_remote_config.zzih<K, V> r9, com.google.android.gms.internal.firebase_remote_config.zzgv r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzaj(r0)
            com.google.android.gms.internal.firebase_remote_config.zzgi r1 = r7.zzpl
            int r1 = r1.zzfm()
            com.google.android.gms.internal.firebase_remote_config.zzgi r2 = r7.zzpl
            int r1 = r2.zzaa(r1)
            K r2 = r9.zzvc
            V r3 = r9.zzve
        L_0x0014:
            int r4 = r7.zzgb()     // Catch:{ all -> 0x0064 }
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L_0x005b
            com.google.android.gms.internal.firebase_remote_config.zzgi r5 = r7.zzpl     // Catch:{ all -> 0x0064 }
            boolean r5 = r5.zzft()     // Catch:{ all -> 0x0064 }
            if (r5 != 0) goto L_0x005b
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L_0x0046
            if (r4 == r0) goto L_0x0039
            boolean r4 = r7.zzgc()     // Catch:{ zzhp -> 0x004e }
            if (r4 == 0) goto L_0x0033
            goto L_0x0014
        L_0x0033:
            com.google.android.gms.internal.firebase_remote_config.zzho r4 = new com.google.android.gms.internal.firebase_remote_config.zzho     // Catch:{ zzhp -> 0x004e }
            r4.<init>(r6)     // Catch:{ zzhp -> 0x004e }
            throw r4     // Catch:{ zzhp -> 0x004e }
        L_0x0039:
            com.google.android.gms.internal.firebase_remote_config.zzkq r4 = r9.zzvd     // Catch:{ zzhp -> 0x004e }
            V r5 = r9.zzve     // Catch:{ zzhp -> 0x004e }
            java.lang.Class r5 = r5.getClass()     // Catch:{ zzhp -> 0x004e }
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch:{ zzhp -> 0x004e }
            goto L_0x0014
        L_0x0046:
            com.google.android.gms.internal.firebase_remote_config.zzkq r4 = r9.zzvb     // Catch:{ zzhp -> 0x004e }
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch:{ zzhp -> 0x004e }
            goto L_0x0014
        L_0x004e:
            boolean r4 = r7.zzgc()     // Catch:{ all -> 0x0064 }
            if (r4 == 0) goto L_0x0055
            goto L_0x0014
        L_0x0055:
            com.google.android.gms.internal.firebase_remote_config.zzho r8 = new com.google.android.gms.internal.firebase_remote_config.zzho     // Catch:{ all -> 0x0064 }
            r8.<init>(r6)     // Catch:{ all -> 0x0064 }
            throw r8     // Catch:{ all -> 0x0064 }
        L_0x005b:
            r8.put(r2, r3)     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.firebase_remote_config.zzgi r8 = r7.zzpl
            r8.zzab(r1)
            return
        L_0x0064:
            r8 = move-exception
            com.google.android.gms.internal.firebase_remote_config.zzgi r9 = r7.zzpl
            r9.zzab(r1)
            goto L_0x006c
        L_0x006b:
            throw r8
        L_0x006c:
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzgn.zza(java.util.Map, com.google.android.gms.internal.firebase_remote_config.zzih, com.google.android.gms.internal.firebase_remote_config.zzgv):void");
    }

    private final Object zza(zzkq zzkq, Class<?> cls, zzgv zzgv) throws IOException {
        switch (zzgo.zzpo[zzkq.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzfj());
            case 2:
                return zzfl();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzfn());
            case 5:
                return Integer.valueOf(zzfi());
            case 6:
                return Long.valueOf(zzfh());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzfg());
            case 9:
                return Long.valueOf(zzff());
            case 10:
                zzaj(2);
                return zzc(zzjb.zzik().zzk(cls), zzgv);
            case 11:
                return Integer.valueOf(zzfo());
            case 12:
                return Long.valueOf(zzfp());
            case 13:
                return Integer.valueOf(zzfq());
            case 14:
                return Long.valueOf(zzfr());
            case 15:
                return zzfk();
            case 16:
                return Integer.valueOf(zzfm());
            case 17:
                return Long.valueOf(zzfe());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzal(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzho.zzhn();
        }
    }

    private final void zzam(int i) throws IOException {
        if (this.zzpl.zzfu() != i) {
            throw zzho.zzhg();
        }
    }
}
