package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import androidx.collection.SimpleArrayMap;
import com.google.ads.AdRequest;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.ads.zzaaw;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzacm;
import com.google.android.gms.internal.ads.zzacq;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaki;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzanz;
import com.google.android.gms.internal.ads.zzaoj;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarg;
import com.google.android.gms.internal.ads.zzev;
import com.google.android.gms.internal.ads.zzgd;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zzlr;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzod;
import com.google.android.gms.internal.ads.zzoo;
import com.google.android.gms.internal.ads.zzoq;
import com.google.android.gms.internal.ads.zzos;
import com.google.android.gms.internal.ads.zzov;
import com.google.android.gms.internal.ads.zzox;
import com.google.android.gms.internal.ads.zzoy;
import com.google.android.gms.internal.ads.zzoz;
import com.google.android.gms.internal.ads.zzpa;
import com.google.android.gms.internal.ads.zzpb;
import com.google.android.gms.internal.ads.zzpw;
import com.google.android.gms.internal.ads.zzqs;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzrf;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxn;
import com.google.android.gms.internal.ads.zzxq;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import com.google.android.gms.internal.ads.zzyf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzbc extends zzd implements zzpa {
    private final Object mLock;
    private zzaqw zzaaa;
    private zzaqw zzaab;
    private int zzaac;
    private zzacm zzaad;
    private final String zzaae;
    private boolean zzwl;
    private boolean zzzy;
    private zzaoj<zzpb> zzzz;

    public zzbc(Context context, zzw zzw, zzjn zzjn, String str, zzxn zzxn, zzang zzang) {
        this(context, zzw, zzjn, str, zzxn, zzang, false);
    }

    public zzbc(Context context, zzw zzw, zzjn zzjn, String str, zzxn zzxn, zzang zzang, boolean z) {
        super(context, zzjn, str, zzxn, zzang, zzw);
        this.mLock = new Object();
        this.zzzz = new zzaoj<>();
        this.zzaac = 1;
        this.zzaae = UUID.randomUUID().toString();
        this.zzzy = z;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.ads.zzov zza(com.google.android.gms.internal.ads.zzpb r20) {
        /*
            r0 = r20
            boolean r1 = r0 instanceof com.google.android.gms.internal.ads.zzoq
            r2 = 0
            if (r1 == 0) goto L_0x0053
            com.google.android.gms.internal.ads.zzoq r0 = (com.google.android.gms.internal.ads.zzoq) r0
            com.google.android.gms.internal.ads.zzov r1 = new com.google.android.gms.internal.ads.zzov
            r3 = r1
            java.lang.String r4 = r0.getHeadline()
            java.util.List r5 = r0.getImages()
            java.lang.String r6 = r0.getBody()
            com.google.android.gms.internal.ads.zzpw r7 = r0.zzkg()
            java.lang.String r8 = r0.getCallToAction()
            java.lang.String r9 = r0.getAdvertiser()
            r10 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r12 = 0
            r13 = 0
            com.google.android.gms.internal.ads.zzoj r14 = r0.zzkc()
            com.google.android.gms.internal.ads.zzlo r15 = r0.getVideoController()
            android.view.View r16 = r0.zzkd()
            com.google.android.gms.dynamic.IObjectWrapper r17 = r0.zzke()
            java.lang.String r18 = r0.getMediationAdapterClassName()
            android.os.Bundle r19 = r0.getExtras()
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r12, r13, r14, r15, r16, r17, r18, r19)
            com.google.android.gms.dynamic.IObjectWrapper r3 = r0.zzka()
            if (r3 == 0) goto L_0x00a4
            com.google.android.gms.dynamic.IObjectWrapper r0 = r0.zzka()
        L_0x004d:
            java.lang.Object r0 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r0)
            r2 = r0
            goto L_0x00a4
        L_0x0053:
            boolean r1 = r0 instanceof com.google.android.gms.internal.ads.zzoo
            if (r1 == 0) goto L_0x00a3
            com.google.android.gms.internal.ads.zzoo r0 = (com.google.android.gms.internal.ads.zzoo) r0
            com.google.android.gms.internal.ads.zzov r1 = new com.google.android.gms.internal.ads.zzov
            r3 = r1
            java.lang.String r4 = r0.getHeadline()
            java.util.List r5 = r0.getImages()
            java.lang.String r6 = r0.getBody()
            com.google.android.gms.internal.ads.zzpw r7 = r0.zzjz()
            java.lang.String r8 = r0.getCallToAction()
            r9 = 0
            double r10 = r0.getStarRating()
            java.lang.String r12 = r0.getStore()
            java.lang.String r13 = r0.getPrice()
            com.google.android.gms.internal.ads.zzoj r14 = r0.zzkc()
            com.google.android.gms.internal.ads.zzlo r15 = r0.getVideoController()
            android.view.View r16 = r0.zzkd()
            com.google.android.gms.dynamic.IObjectWrapper r17 = r0.zzke()
            java.lang.String r18 = r0.getMediationAdapterClassName()
            android.os.Bundle r19 = r0.getExtras()
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r12, r13, r14, r15, r16, r17, r18, r19)
            com.google.android.gms.dynamic.IObjectWrapper r3 = r0.zzka()
            if (r3 == 0) goto L_0x00a4
            com.google.android.gms.dynamic.IObjectWrapper r0 = r0.zzka()
            goto L_0x004d
        L_0x00a3:
            r1 = r2
        L_0x00a4:
            boolean r0 = r2 instanceof com.google.android.gms.internal.ads.zzpd
            if (r0 == 0) goto L_0x00ad
            com.google.android.gms.internal.ads.zzpd r2 = (com.google.android.gms.internal.ads.zzpd) r2
            r1.zzb(r2)
        L_0x00ad:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.zzbc.zza(com.google.android.gms.internal.ads.zzpb):com.google.android.gms.internal.ads.zzov");
    }

    /* access modifiers changed from: private */
    public static void zza(zzbw zzbw, zzbw zzbw2) {
        if (zzbw2.zzade == null) {
            zzbw2.zzade = zzbw.zzade;
        }
        if (zzbw2.zzadf == null) {
            zzbw2.zzadf = zzbw.zzadf;
        }
        if (zzbw2.zzadh == null) {
            zzbw2.zzadh = zzbw.zzadh;
        }
        if (zzbw2.zzadi == null) {
            zzbw2.zzadi = zzbw.zzadi;
        }
        if (zzbw2.zzadk == null) {
            zzbw2.zzadk = zzbw.zzadk;
        }
        if (zzbw2.zzadj == null) {
            zzbw2.zzadj = zzbw.zzadj;
        }
        if (zzbw2.zzads == null) {
            zzbw2.zzads = zzbw.zzads;
        }
        if (zzbw2.zzacy == null) {
            zzbw2.zzacy = zzbw.zzacy;
        }
        if (zzbw2.zzadt == null) {
            zzbw2.zzadt = zzbw.zzadt;
        }
        if (zzbw2.zzacz == null) {
            zzbw2.zzacz = zzbw.zzacz;
        }
        if (zzbw2.zzada == null) {
            zzbw2.zzada = zzbw.zzada;
        }
        if (zzbw2.zzacv == null) {
            zzbw2.zzacv = zzbw.zzacv;
        }
        if (zzbw2.zzacw == null) {
            zzbw2.zzacw = zzbw.zzacw;
        }
        if (zzbw2.zzacx == null) {
            zzbw2.zzacx = zzbw.zzacx;
        }
    }

    private final void zza(zzoo zzoo) {
        zzakk.zzcrm.post(new zzbg(this, zzoo));
    }

    private final void zza(zzoq zzoq) {
        zzakk.zzcrm.post(new zzbi(this, zzoq));
    }

    private final void zza(zzov zzov) {
        zzakk.zzcrm.post(new zzbh(this, zzov));
    }

    private final boolean zzcp() {
        return this.zzvw.zzacw != null && this.zzvw.zzacw.zzcfp;
    }

    private final zzwy zzcw() {
        if (this.zzvw.zzacw == null || !this.zzvw.zzacw.zzceq) {
            return null;
        }
        return this.zzvw.zzacw.zzcod;
    }

    private final void zzdx() {
        zzacm zzdr = zzdr();
        if (zzdr != null) {
            zzdr.zzmc();
        }
    }

    public final String getAdUnitId() {
        return this.zzvw.zzacp;
    }

    public final String getUuid() {
        return this.zzaae;
    }

    public final void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    public final void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    public final void zza(zzaaw zzaaw) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    public final void zza(zzaji zzaji, zznx zznx) {
        String str = "";
        if (zzaji.zzacv != null) {
            this.zzvw.zzacv = zzaji.zzacv;
        }
        if (zzaji.errorCode != -2) {
            zzakk.zzcrm.post(new zzbd(this, zzaji));
            return;
        }
        int i = zzaji.zzcgs.zzceg;
        if (i == 1) {
            this.zzvw.zzadv = 0;
            zzbw zzbw = this.zzvw;
            zzbv.zzej();
            zzbw.zzacu = zzabl.zza(this.zzvw.zzrt, this, zzaji, this.zzvw.zzacq, null, this.zzwh, this, zznx);
            String str2 = "AdRenderer: ";
            String valueOf = String.valueOf(this.zzvw.zzacu.getClass().getName());
            zzakb.zzck(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            JSONArray jSONArray2 = new JSONObject(zzaji.zzcos.zzceo).getJSONArray("slots");
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONArray jSONArray3 = jSONArray2.getJSONObject(i2).getJSONArray("ads");
                for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                    jSONArray.put(jSONArray3.get(i3));
                }
            }
            zzdx();
            ArrayList arrayList = new ArrayList();
            for (int i4 = 0; i4 < i; i4++) {
                zzbe zzbe = new zzbe(this, i4, jSONArray, i, zzaji);
                arrayList.add(zzaki.zza(zzbe));
            }
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                try {
                    zzakk.zzcrm.post(new zzbf(this, (zzpb) ((zzanz) arrayList.get(i5)).get(((Long) zzkb.zzik().zzd(zznk.zzbao)).longValue(), TimeUnit.MILLISECONDS), i5, arrayList));
                } catch (InterruptedException e) {
                    zzane.zzc(str, e);
                    Thread.currentThread().interrupt();
                } catch (CancellationException | ExecutionException | TimeoutException e2) {
                    zzane.zzc(str, e2);
                }
            }
        } catch (JSONException e3) {
            zzakb.zzc("Malformed native ad response", e3);
            zzi(0);
        }
    }

    public final void zza(zzod zzod) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    public final void zza(zzox zzox) {
        zzaqw zzaqw = this.zzaaa;
        if (zzaqw != null) {
            zzaqw.zzb(zzox);
        }
    }

    public final void zza(zzoz zzoz) {
        if (this.zzvw.zzacw.zzcob != null) {
            zzbv.zzeo().zzqd().zza(this.zzvw.zzacv, this.zzvw.zzacw, (zzgd) new zzev(zzoz), (zzaqw) null);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzajh zzajh, zzajh zzajh2) {
        zzov zzov;
        zzajh zzajh3 = zzajh2;
        View view = null;
        zzd(null);
        if (this.zzvw.zzfo()) {
            if (zzajh3.zzceq) {
                zzdx();
                try {
                    zzyf zzmu = zzajh3.zzbtx != null ? zzajh3.zzbtx.zzmu() : null;
                    zzxz zzmo = zzajh3.zzbtx != null ? zzajh3.zzbtx.zzmo() : null;
                    zzyc zzmp = zzajh3.zzbtx != null ? zzajh3.zzbtx.zzmp() : null;
                    zzqs zzmt = zzajh3.zzbtx != null ? zzajh3.zzbtx.zzmt() : null;
                    String zzc = zzc(zzajh2);
                    if (zzmu != null && this.zzvw.zzadg != null) {
                        String headline = zzmu.getHeadline();
                        List images = zzmu.getImages();
                        String body = zzmu.getBody();
                        zzpw zzjz = zzmu.zzjz() != null ? zzmu.zzjz() : null;
                        String callToAction = zzmu.getCallToAction();
                        String advertiser = zzmu.getAdvertiser();
                        double starRating = zzmu.getStarRating();
                        String store = zzmu.getStore();
                        String price = zzmu.getPrice();
                        zzlo videoController = zzmu.getVideoController();
                        if (zzmu.zzmw() != null) {
                            view = (View) ObjectWrapper.unwrap(zzmu.zzmw());
                        }
                        zzov = new zzov(headline, images, body, zzjz, callToAction, advertiser, starRating, store, price, null, videoController, view, zzmu.zzke(), zzc, zzmu.getExtras());
                        zzoy zzoy = new zzoy(this.zzvw.zzrt, (zzpa) this, this.zzvw.zzacq, zzmu, (zzpb) zzov);
                        zzov.zzb((zzoz) zzoy);
                    } else if (zzmo != null && this.zzvw.zzadg != null) {
                        String headline2 = zzmo.getHeadline();
                        List images2 = zzmo.getImages();
                        String body2 = zzmo.getBody();
                        zzpw zzjz2 = zzmo.zzjz() != null ? zzmo.zzjz() : null;
                        String callToAction2 = zzmo.getCallToAction();
                        double starRating2 = zzmo.getStarRating();
                        String store2 = zzmo.getStore();
                        String price2 = zzmo.getPrice();
                        zzlo videoController2 = zzmo.getVideoController();
                        if (zzmo.zzmw() != null) {
                            view = (View) ObjectWrapper.unwrap(zzmo.zzmw());
                        }
                        zzov = new zzov(headline2, images2, body2, zzjz2, callToAction2, null, starRating2, store2, price2, null, videoController2, view, zzmo.zzke(), zzc, zzmo.getExtras());
                        zzoy zzoy2 = new zzoy(this.zzvw.zzrt, (zzpa) this, this.zzvw.zzacq, zzmo, (zzpb) zzov);
                        zzov.zzb((zzoz) zzoy2);
                    } else if (zzmo != null && this.zzvw.zzade != null) {
                        String headline3 = zzmo.getHeadline();
                        List images3 = zzmo.getImages();
                        String body3 = zzmo.getBody();
                        zzpw zzjz3 = zzmo.zzjz() != null ? zzmo.zzjz() : null;
                        String callToAction3 = zzmo.getCallToAction();
                        double starRating3 = zzmo.getStarRating();
                        String store3 = zzmo.getStore();
                        String price3 = zzmo.getPrice();
                        Bundle extras = zzmo.getExtras();
                        zzlo videoController3 = zzmo.getVideoController();
                        if (zzmo.zzmw() != null) {
                            view = (View) ObjectWrapper.unwrap(zzmo.zzmw());
                        }
                        zzoo zzoo = new zzoo(headline3, images3, body3, zzjz3, callToAction3, starRating3, store3, price3, null, extras, videoController3, view, zzmo.zzke(), zzc);
                        zzoy zzoy3 = new zzoy(this.zzvw.zzrt, (zzpa) this, this.zzvw.zzacq, zzmo, (zzpb) zzoo);
                        zzoo.zzb((zzoz) zzoy3);
                        zza(zzoo);
                    } else if (zzmp != null && this.zzvw.zzadg != null) {
                        String headline4 = zzmp.getHeadline();
                        List images4 = zzmp.getImages();
                        String body4 = zzmp.getBody();
                        zzpw zzkg = zzmp.zzkg() != null ? zzmp.zzkg() : null;
                        String callToAction4 = zzmp.getCallToAction();
                        String advertiser2 = zzmp.getAdvertiser();
                        zzlo videoController4 = zzmp.getVideoController();
                        if (zzmp.zzmw() != null) {
                            view = (View) ObjectWrapper.unwrap(zzmp.zzmw());
                        }
                        zzov zzov2 = new zzov(headline4, images4, body4, zzkg, callToAction4, advertiser2, -1.0d, null, null, null, videoController4, view, zzmp.zzke(), zzc, zzmp.getExtras());
                        zzyc zzyc = zzmp;
                        zzov = zzov2;
                        zzoy zzoy4 = new zzoy(this.zzvw.zzrt, (zzpa) this, this.zzvw.zzacq, zzyc, (zzpb) zzov2);
                        zzov.zzb((zzoz) zzoy4);
                    } else if (zzmp != null && this.zzvw.zzadf != null) {
                        String headline5 = zzmp.getHeadline();
                        List images5 = zzmp.getImages();
                        String body5 = zzmp.getBody();
                        zzpw zzkg2 = zzmp.zzkg() != null ? zzmp.zzkg() : null;
                        String callToAction5 = zzmp.getCallToAction();
                        String advertiser3 = zzmp.getAdvertiser();
                        Bundle extras2 = zzmp.getExtras();
                        zzlo videoController5 = zzmp.getVideoController();
                        if (zzmp.zzmw() != null) {
                            view = (View) ObjectWrapper.unwrap(zzmp.zzmw());
                        }
                        zzoq zzoq = new zzoq(headline5, images5, body5, zzkg2, callToAction5, advertiser3, null, extras2, videoController5, view, zzmp.zzke(), zzc);
                        zzyc zzyc2 = zzmp;
                        zzoq zzoq2 = zzoq;
                        zzoy zzoy5 = new zzoy(this.zzvw.zzrt, (zzpa) this, this.zzvw.zzacq, zzyc2, (zzpb) zzoq);
                        zzoq2.zzb((zzoz) zzoy5);
                        zza(zzoq2);
                    } else if (zzmt == null || this.zzvw.zzadi == null || this.zzvw.zzadi.get(zzmt.getCustomTemplateId()) == null) {
                        zzakb.zzdk("No matching mapper/listener for retrieved native ad template.");
                        zzi(0);
                        return false;
                    } else {
                        zzakk.zzcrm.post(new zzbk(this, zzmt));
                    }
                    zza(zzov);
                } catch (RemoteException e) {
                    zzakb.zzd("#007 Could not call remote method.", e);
                }
            } else {
                zzpb zzpb = zzajh3.zzcoj;
                if (this.zzzy) {
                    this.zzzz.set(zzpb);
                } else {
                    boolean z = zzpb instanceof zzoq;
                    if (!z || this.zzvw.zzadg == null) {
                        if (!z || this.zzvw.zzadf == null) {
                            boolean z2 = zzpb instanceof zzoo;
                            if (!z2 || this.zzvw.zzadg == null) {
                                if (!z2 || this.zzvw.zzade == null) {
                                    if ((zzpb instanceof zzos) && this.zzvw.zzadi != null) {
                                        zzos zzos = (zzos) zzpb;
                                        if (this.zzvw.zzadi.get(zzos.getCustomTemplateId()) != null) {
                                            zzakk.zzcrm.post(new zzbj(this, zzos.getCustomTemplateId(), zzajh3));
                                        }
                                    }
                                    zzakb.zzdk("No matching listener for retrieved native ad template.");
                                    zzi(0);
                                    return false;
                                }
                                zza((zzoo) zzajh3.zzcoj);
                            }
                        } else {
                            zza((zzoq) zzajh3.zzcoj);
                        }
                    }
                    zza(zza(zzajh3.zzcoj));
                }
            }
            return super.zza(zzajh, zzajh2);
        }
        throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzjj zzjj, zzajh zzajh, boolean z) {
        return this.zzvv.zzdz();
    }

    public final boolean zza(zzjj zzjj, zznx zznx) {
        try {
            zzdq();
            return super.zza(zzjj, zznx, this.zzaac);
        } catch (Exception e) {
            if (zzane.isLoggable(4)) {
                Log.i(AdRequest.LOGTAG, "Error initializing webview.", e);
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzb(IObjectWrapper iObjectWrapper) {
        Object unwrap = iObjectWrapper != null ? ObjectWrapper.unwrap(iObjectWrapper) : null;
        if (unwrap instanceof zzoz) {
            ((zzoz) unwrap).zzkl();
        }
        super.zzb(this.zzvw.zzacw, false);
    }

    /* access modifiers changed from: protected */
    public final void zzb(boolean z) {
        String str;
        super.zzb(z);
        if (this.zzwl) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbcb)).booleanValue()) {
                zzdt();
            }
        }
        if (!zzcp()) {
            return;
        }
        if (this.zzaab != null || this.zzaaa != null) {
            zzaqw zzaqw = this.zzaab;
            String str2 = null;
            if (zzaqw == null) {
                zzaqw = this.zzaaa;
                if (zzaqw != null) {
                    str2 = "javascript";
                } else {
                    zzaqw = null;
                    str = null;
                    if (zzaqw.getWebView() != null && zzbv.zzfa().zzi(this.zzvw.zzrt)) {
                        int i = this.zzvw.zzacr.zzcve;
                        int i2 = this.zzvw.zzacr.zzcvf;
                        StringBuilder sb = new StringBuilder(23);
                        sb.append(i);
                        sb.append(".");
                        sb.append(i2);
                        this.zzwb = zzbv.zzfa().zza(sb.toString(), zzaqw.getWebView(), "", "javascript", str);
                        if (this.zzwb != null) {
                            zzbv.zzfa().zzm(this.zzwb);
                            return;
                        }
                        return;
                    }
                }
            }
            str = str2;
            if (zzaqw.getWebView() != null) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzbq() {
        zzb(false);
    }

    /* access modifiers changed from: protected */
    public final void zzc(int i, boolean z) {
        zzdx();
        super.zzc(i, z);
    }

    public final void zzcd() {
        zzajh zzajh = this.zzvw.zzacw;
        if (zzajh.zzbtx == null) {
            super.zzcd();
            return;
        }
        try {
            zzxq zzxq = zzajh.zzbtx;
            zzlo zzlo = null;
            zzxz zzmo = zzxq.zzmo();
            if (zzmo != null) {
                zzlo = zzmo.getVideoController();
            } else {
                zzyc zzmp = zzxq.zzmp();
                if (zzmp != null) {
                    zzlo = zzmp.getVideoController();
                } else {
                    zzqs zzmt = zzxq.zzmt();
                    if (zzmt != null) {
                        zzlo = zzmt.getVideoController();
                    }
                }
            }
            if (zzlo != null) {
                zzlr zzio = zzlo.zzio();
                if (zzio != null) {
                    zzio.onVideoEnd();
                }
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    public final void zzce() {
        if (this.zzvw.zzacw != null) {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty)) {
                zzbs();
                return;
            }
        }
        super.zzce();
    }

    public final void zzcj() {
        if (this.zzvw.zzacw != null) {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty)) {
                zzbr();
                return;
            }
        }
        super.zzcj();
    }

    public final void zzcr() {
        if (zzcp() && this.zzwb != null) {
            zzaqw zzaqw = this.zzaab;
            if (zzaqw == null) {
                zzaqw = this.zzaaa;
                if (zzaqw == null) {
                    zzaqw = null;
                }
            }
            if (zzaqw != null) {
                zzaqw.zza("onSdkImpression", (Map<String, ?>) new HashMap<String,Object>());
            }
        }
    }

    public final void zzcs() {
        super.zzby();
        zzaqw zzaqw = this.zzaab;
        if (zzaqw != null) {
            zzaqw.destroy();
            this.zzaab = null;
        }
    }

    public final void zzct() {
        zzaqw zzaqw = this.zzaaa;
        if (zzaqw != null) {
            zzaqw.destroy();
            this.zzaaa = null;
        }
    }

    public final boolean zzcu() {
        if (zzcw() != null) {
            return zzcw().zzbta;
        }
        return false;
    }

    public final boolean zzcv() {
        if (zzcw() != null) {
            return zzcw().zzbtb;
        }
        return false;
    }

    public final void zzd(List<String> list) {
        Preconditions.checkMainThread("setNativeTemplates must be called on the main UI thread.");
        this.zzvw.zzads = list;
    }

    /* access modifiers changed from: 0000 */
    public final void zzdq() throws zzarg {
        synchronized (this.mLock) {
            zzakb.m1419v("Initializing webview native ads utills");
            zzacq zzacq = new zzacq(this.zzvw.zzrt, this, this.zzaae, this.zzvw.zzacq, this.zzvw.zzacr);
            this.zzaad = zzacq;
        }
    }

    public final zzacm zzdr() {
        zzacm zzacm;
        synchronized (this.mLock) {
            zzacm = this.zzaad;
        }
        return zzacm;
    }

    /* access modifiers changed from: protected */
    public final Future<zzpb> zzds() {
        return this.zzzz;
    }

    public final void zzdt() {
        if (this.zzvw.zzacw == null || this.zzaaa == null) {
            this.zzwl = true;
            zzakb.zzdk("Request to enable ActiveView before adState is available.");
            return;
        }
        zzbv.zzeo().zzqd().zza(this.zzvw.zzacv, this.zzvw.zzacw, this.zzaaa.getView(), this.zzaaa);
        this.zzwl = false;
    }

    public final void zzdu() {
        this.zzwl = false;
        if (this.zzvw.zzacw == null || this.zzaaa == null) {
            zzakb.zzdk("Request to enable ActiveView before adState is available.");
        } else {
            zzbv.zzeo().zzqd().zzh(this.zzvw.zzacw);
        }
    }

    public final SimpleArrayMap<String, zzrf> zzdv() {
        Preconditions.checkMainThread("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzvw.zzadi;
    }

    public final void zzdw() {
        zzaqw zzaqw = this.zzaaa;
        if (zzaqw != null && zzaqw.zztm() != null && this.zzvw.zzadj != null && this.zzvw.zzadj.zzbjr != null) {
            this.zzaaa.zztm().zzb(this.zzvw.zzadj.zzbjr);
        }
    }

    public final void zzf(zzaqw zzaqw) {
        this.zzaaa = zzaqw;
    }

    public final void zzg(zzaqw zzaqw) {
        this.zzaab = zzaqw;
    }

    /* access modifiers changed from: protected */
    public final void zzi(int i) {
        zzc(i, false);
    }

    public final void zzi(View view) {
        if (this.zzwb != null) {
            zzbv.zzfa().zza(this.zzwb, view);
        }
    }

    public final void zzj(int i) {
        Preconditions.checkMainThread("setMaxNumberOfAds must be called on the main UI thread.");
        this.zzaac = i;
    }

    public final zzrc zzr(String str) {
        Preconditions.checkMainThread("getOnCustomClickListener must be called on the main UI thread.");
        if (this.zzvw.zzadh == null) {
            return null;
        }
        return (zzrc) this.zzvw.zzadh.get(str);
    }
}
