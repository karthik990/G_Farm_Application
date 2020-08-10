package com.google.android.gms.internal.measurement;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import java.io.IOException;
import java.util.List;

final class zztt implements zzwk {
    private int tag;
    private final zztq zzbuk;
    private int zzbul;
    private int zzbum = 0;

    public static zztt zza(zztq zztq) {
        if (zztq.zzbud != null) {
            return zztq.zzbud;
        }
        return new zztt(zztq);
    }

    private zztt(zztq zztq) {
        this.zzbuk = (zztq) zzuq.zza(zztq, Keys.INPUT);
        this.zzbuk.zzbud = this;
    }

    public final int zzvh() throws IOException {
        int i = this.zzbum;
        if (i != 0) {
            this.tag = i;
            this.zzbum = 0;
        } else {
            this.tag = this.zzbuk.zzuj();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.zzbul) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final int getTag() {
        return this.tag;
    }

    public final boolean zzvi() throws IOException {
        if (!this.zzbuk.zzuz()) {
            int i = this.tag;
            if (i != this.zzbul) {
                return this.zzbuk.zzaq(i);
            }
        }
        return false;
    }

    private final void zzav(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzuv.zzwu();
        }
    }

    public final double readDouble() throws IOException {
        zzav(1);
        return this.zzbuk.readDouble();
    }

    public final float readFloat() throws IOException {
        zzav(5);
        return this.zzbuk.readFloat();
    }

    public final long zzuk() throws IOException {
        zzav(0);
        return this.zzbuk.zzuk();
    }

    public final long zzul() throws IOException {
        zzav(0);
        return this.zzbuk.zzul();
    }

    public final int zzum() throws IOException {
        zzav(0);
        return this.zzbuk.zzum();
    }

    public final long zzun() throws IOException {
        zzav(1);
        return this.zzbuk.zzun();
    }

    public final int zzuo() throws IOException {
        zzav(5);
        return this.zzbuk.zzuo();
    }

    public final boolean zzup() throws IOException {
        zzav(0);
        return this.zzbuk.zzup();
    }

    public final String readString() throws IOException {
        zzav(2);
        return this.zzbuk.readString();
    }

    public final String zzuq() throws IOException {
        zzav(2);
        return this.zzbuk.zzuq();
    }

    public final <T> T zza(zzwl<T> zzwl, zzub zzub) throws IOException {
        zzav(2);
        return zzc(zzwl, zzub);
    }

    public final <T> T zzb(zzwl<T> zzwl, zzub zzub) throws IOException {
        zzav(3);
        return zzd(zzwl, zzub);
    }

    private final <T> T zzc(zzwl<T> zzwl, zzub zzub) throws IOException {
        int zzus = this.zzbuk.zzus();
        if (this.zzbuk.zzbua < this.zzbuk.zzbub) {
            int zzas = this.zzbuk.zzas(zzus);
            T newInstance = zzwl.newInstance();
            this.zzbuk.zzbua++;
            zzwl.zza(newInstance, this, zzub);
            zzwl.zzy(newInstance);
            this.zzbuk.zzap(0);
            this.zzbuk.zzbua--;
            this.zzbuk.zzat(zzas);
            return newInstance;
        }
        throw zzuv.zzwv();
    }

    private final <T> T zzd(zzwl<T> zzwl, zzub zzub) throws IOException {
        int i = this.zzbul;
        this.zzbul = ((this.tag >>> 3) << 3) | 4;
        try {
            T newInstance = zzwl.newInstance();
            zzwl.zza(newInstance, this, zzub);
            zzwl.zzy(newInstance);
            if (this.tag == this.zzbul) {
                return newInstance;
            }
            throw zzuv.zzww();
        } finally {
            this.zzbul = i;
        }
    }

    public final zzte zzur() throws IOException {
        zzav(2);
        return this.zzbuk.zzur();
    }

    public final int zzus() throws IOException {
        zzav(0);
        return this.zzbuk.zzus();
    }

    public final int zzut() throws IOException {
        zzav(0);
        return this.zzbuk.zzut();
    }

    public final int zzuu() throws IOException {
        zzav(5);
        return this.zzbuk.zzuu();
    }

    public final long zzuv() throws IOException {
        zzav(1);
        return this.zzbuk.zzuv();
    }

    public final int zzuw() throws IOException {
        zzav(0);
        return this.zzbuk.zzuw();
    }

    public final long zzux() throws IOException {
        zzav(0);
        return this.zzbuk.zzux();
    }

    public final void zzi(List<Double> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzty) {
            zzty zzty = (zzty) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzty.zzd(this.zzbuk.readDouble());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzaw(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzty.zzd(this.zzbuk.readDouble());
                } while (this.zzbuk.zzva() < zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Double.valueOf(this.zzbuk.readDouble()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzaw(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Double.valueOf(this.zzbuk.readDouble()));
                } while (this.zzbuk.zzva() < zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzj(List<Float> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzul) {
            zzul zzul = (zzul) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzax(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzul.zzc(this.zzbuk.readFloat());
                } while (this.zzbuk.zzva() < zzva);
            } else if (i == 5) {
                do {
                    zzul.zzc(this.zzbuk.readFloat());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzax(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Float.valueOf(this.zzbuk.readFloat()));
                } while (this.zzbuk.zzva() < zzva2);
            } else if (i2 == 5) {
                do {
                    list.add(Float.valueOf(this.zzbuk.readFloat()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzk(List<Long> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzvj) {
            zzvj zzvj = (zzvj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzvj.zzbe(this.zzbuk.zzuk());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzvj.zzbe(this.zzbuk.zzuk());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzbuk.zzuk()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Long.valueOf(this.zzbuk.zzuk()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzl(List<Long> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzvj) {
            zzvj zzvj = (zzvj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzvj.zzbe(this.zzbuk.zzul());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzvj.zzbe(this.zzbuk.zzul());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzbuk.zzul()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Long.valueOf(this.zzbuk.zzul()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzm(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzup.zzbo(this.zzbuk.zzum());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzup.zzbo(this.zzbuk.zzum());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzum()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzum()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzn(List<Long> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzvj) {
            zzvj zzvj = (zzvj) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzvj.zzbe(this.zzbuk.zzun());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzaw(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzvj.zzbe(this.zzbuk.zzun());
                } while (this.zzbuk.zzva() < zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzbuk.zzun()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzaw(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Long.valueOf(this.zzbuk.zzun()));
                } while (this.zzbuk.zzva() < zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzo(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzax(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzup.zzbo(this.zzbuk.zzuo());
                } while (this.zzbuk.zzva() < zzva);
            } else if (i == 5) {
                do {
                    zzup.zzbo(this.zzbuk.zzuo());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzax(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuo()));
                } while (this.zzbuk.zzva() < zzva2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuo()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzp(List<Boolean> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zztc) {
            zztc zztc = (zztc) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zztc.addBoolean(this.zzbuk.zzup());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zztc.addBoolean(this.zzbuk.zzup());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Boolean.valueOf(this.zzbuk.zzup()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Boolean.valueOf(this.zzbuk.zzup()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void readStringList(List<String> list) throws IOException {
        zza(list, false);
    }

    public final void zzq(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int zzuj;
        int zzuj2;
        if ((this.tag & 7) != 2) {
            throw zzuv.zzwu();
        } else if (!(list instanceof zzve) || z) {
            do {
                list.add(z ? zzuq() : readString());
                if (!this.zzbuk.zzuz()) {
                    zzuj = this.zzbuk.zzuj();
                } else {
                    return;
                }
            } while (zzuj == this.tag);
            this.zzbum = zzuj;
        } else {
            zzve zzve = (zzve) list;
            do {
                zzve.zzc(zzur());
                if (!this.zzbuk.zzuz()) {
                    zzuj2 = this.zzbuk.zzuj();
                } else {
                    return;
                }
            } while (zzuj2 == this.tag);
            this.zzbum = zzuj2;
        }
    }

    public final <T> void zza(List<T> list, zzwl<T> zzwl, zzub zzub) throws IOException {
        int zzuj;
        int i = this.tag;
        if ((i & 7) == 2) {
            do {
                list.add(zzc(zzwl, zzub));
                if (!this.zzbuk.zzuz() && this.zzbum == 0) {
                    zzuj = this.zzbuk.zzuj();
                } else {
                    return;
                }
            } while (zzuj == i);
            this.zzbum = zzuj;
            return;
        }
        throw zzuv.zzwu();
    }

    public final <T> void zzb(List<T> list, zzwl<T> zzwl, zzub zzub) throws IOException {
        int zzuj;
        int i = this.tag;
        if ((i & 7) == 3) {
            do {
                list.add(zzd(zzwl, zzub));
                if (!this.zzbuk.zzuz() && this.zzbum == 0) {
                    zzuj = this.zzbuk.zzuj();
                } else {
                    return;
                }
            } while (zzuj == i);
            this.zzbum = zzuj;
            return;
        }
        throw zzuv.zzwu();
    }

    public final void zzr(List<zzte> list) throws IOException {
        int zzuj;
        if ((this.tag & 7) == 2) {
            do {
                list.add(zzur());
                if (!this.zzbuk.zzuz()) {
                    zzuj = this.zzbuk.zzuj();
                } else {
                    return;
                }
            } while (zzuj == this.tag);
            this.zzbum = zzuj;
            return;
        }
        throw zzuv.zzwu();
    }

    public final void zzs(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzup.zzbo(this.zzbuk.zzus());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzup.zzbo(this.zzbuk.zzus());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzus()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzus()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzt(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzup.zzbo(this.zzbuk.zzut());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzup.zzbo(this.zzbuk.zzut());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzut()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzut()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzu(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzax(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzup.zzbo(this.zzbuk.zzuu());
                } while (this.zzbuk.zzva() < zzva);
            } else if (i == 5) {
                do {
                    zzup.zzbo(this.zzbuk.zzuu());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzax(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuu()));
                } while (this.zzbuk.zzva() < zzva2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuu()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzv(List<Long> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzvj) {
            zzvj zzvj = (zzvj) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzvj.zzbe(this.zzbuk.zzuv());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzus = this.zzbuk.zzus();
                zzaw(zzus);
                int zzva = this.zzbuk.zzva() + zzus;
                do {
                    zzvj.zzbe(this.zzbuk.zzuv());
                } while (this.zzbuk.zzva() < zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zzbuk.zzuv()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzus2 = this.zzbuk.zzus();
                zzaw(zzus2);
                int zzva2 = this.zzbuk.zzva() + zzus2;
                do {
                    list.add(Long.valueOf(this.zzbuk.zzuv()));
                } while (this.zzbuk.zzva() < zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzw(List<Integer> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzup) {
            zzup zzup = (zzup) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzup.zzbo(this.zzbuk.zzuw());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzup.zzbo(this.zzbuk.zzuw());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuw()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Integer.valueOf(this.zzbuk.zzuw()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    public final void zzx(List<Long> list) throws IOException {
        int zzuj;
        int zzuj2;
        if (list instanceof zzvj) {
            zzvj zzvj = (zzvj) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzvj.zzbe(this.zzbuk.zzux());
                    if (!this.zzbuk.zzuz()) {
                        zzuj2 = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj2 == this.tag);
                this.zzbum = zzuj2;
            } else if (i == 2) {
                int zzva = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    zzvj.zzbe(this.zzbuk.zzux());
                } while (this.zzbuk.zzva() < zzva);
                zzay(zzva);
            } else {
                throw zzuv.zzwu();
            }
        } else {
            int i2 = this.tag & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zzbuk.zzux()));
                    if (!this.zzbuk.zzuz()) {
                        zzuj = this.zzbuk.zzuj();
                    } else {
                        return;
                    }
                } while (zzuj == this.tag);
                this.zzbum = zzuj;
            } else if (i2 == 2) {
                int zzva2 = this.zzbuk.zzva() + this.zzbuk.zzus();
                do {
                    list.add(Long.valueOf(this.zzbuk.zzux()));
                } while (this.zzbuk.zzva() < zzva2);
                zzay(zzva2);
            } else {
                throw zzuv.zzwu();
            }
        }
    }

    private static void zzaw(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzuv.zzww();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        if (zzvi() != false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        throw new com.google.android.gms.internal.measurement.zzuv(r6);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.measurement.zzvo<K, V> r9, com.google.android.gms.internal.measurement.zzub r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzav(r0)
            com.google.android.gms.internal.measurement.zztq r1 = r7.zzbuk
            int r1 = r1.zzus()
            com.google.android.gms.internal.measurement.zztq r2 = r7.zzbuk
            int r1 = r2.zzas(r1)
            K r2 = r9.zzcal
            V r3 = r9.zzbrr
        L_0x0014:
            int r4 = r7.zzvh()     // Catch:{ all -> 0x0064 }
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L_0x005b
            com.google.android.gms.internal.measurement.zztq r5 = r7.zzbuk     // Catch:{ all -> 0x0064 }
            boolean r5 = r5.zzuz()     // Catch:{ all -> 0x0064 }
            if (r5 != 0) goto L_0x005b
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L_0x0046
            if (r4 == r0) goto L_0x0039
            boolean r4 = r7.zzvi()     // Catch:{ zzuw -> 0x004e }
            if (r4 == 0) goto L_0x0033
            goto L_0x0014
        L_0x0033:
            com.google.android.gms.internal.measurement.zzuv r4 = new com.google.android.gms.internal.measurement.zzuv     // Catch:{ zzuw -> 0x004e }
            r4.<init>(r6)     // Catch:{ zzuw -> 0x004e }
            throw r4     // Catch:{ zzuw -> 0x004e }
        L_0x0039:
            com.google.android.gms.internal.measurement.zzxs r4 = r9.zzcam     // Catch:{ zzuw -> 0x004e }
            V r5 = r9.zzbrr     // Catch:{ zzuw -> 0x004e }
            java.lang.Class r5 = r5.getClass()     // Catch:{ zzuw -> 0x004e }
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch:{ zzuw -> 0x004e }
            goto L_0x0014
        L_0x0046:
            com.google.android.gms.internal.measurement.zzxs r4 = r9.zzcak     // Catch:{ zzuw -> 0x004e }
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch:{ zzuw -> 0x004e }
            goto L_0x0014
        L_0x004e:
            boolean r4 = r7.zzvi()     // Catch:{ all -> 0x0064 }
            if (r4 == 0) goto L_0x0055
            goto L_0x0014
        L_0x0055:
            com.google.android.gms.internal.measurement.zzuv r8 = new com.google.android.gms.internal.measurement.zzuv     // Catch:{ all -> 0x0064 }
            r8.<init>(r6)     // Catch:{ all -> 0x0064 }
            throw r8     // Catch:{ all -> 0x0064 }
        L_0x005b:
            r8.put(r2, r3)     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.measurement.zztq r8 = r7.zzbuk
            r8.zzat(r1)
            return
        L_0x0064:
            r8 = move-exception
            com.google.android.gms.internal.measurement.zztq r9 = r7.zzbuk
            r9.zzat(r1)
            goto L_0x006c
        L_0x006b:
            throw r8
        L_0x006c:
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zztt.zza(java.util.Map, com.google.android.gms.internal.measurement.zzvo, com.google.android.gms.internal.measurement.zzub):void");
    }

    private final Object zza(zzxs zzxs, Class<?> cls, zzub zzub) throws IOException {
        switch (zztu.zzbun[zzxs.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzup());
            case 2:
                return zzur();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzut());
            case 5:
                return Integer.valueOf(zzuo());
            case 6:
                return Long.valueOf(zzun());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzum());
            case 9:
                return Long.valueOf(zzul());
            case 10:
                zzav(2);
                return zzc(zzwh.zzxt().zzi(cls), zzub);
            case 11:
                return Integer.valueOf(zzuu());
            case 12:
                return Long.valueOf(zzuv());
            case 13:
                return Integer.valueOf(zzuw());
            case 14:
                return Long.valueOf(zzux());
            case 15:
                return zzuq();
            case 16:
                return Integer.valueOf(zzus());
            case 17:
                return Long.valueOf(zzuk());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzax(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzuv.zzww();
        }
    }

    private final void zzay(int i) throws IOException {
        if (this.zzbuk.zzva() != i) {
            throw zzuv.zzwq();
        }
    }
}
