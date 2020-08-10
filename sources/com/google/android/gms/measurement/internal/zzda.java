package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import p043io.netty.handler.traffic.AbstractTrafficShapingHandler;

public final class zzda extends zzf {
    protected zzdu zzara;
    private zzcx zzarb;
    private final Set<zzcy> zzarc = new CopyOnWriteArraySet();
    private boolean zzard;
    private final AtomicReference<String> zzare = new AtomicReference<>();
    protected boolean zzarf = true;

    protected zzda(zzbw zzbw) {
        super(zzbw);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    public final void zzkw() {
        if (getContext().getApplicationContext() instanceof Application) {
            ((Application) getContext().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zzara);
        }
    }

    public final Boolean zzkx() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzgs().zza(atomicReference, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME, "boolean test flag value", new zzdb(this, atomicReference));
    }

    public final String zzky() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzgs().zza(atomicReference, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME, "String test flag value", new zzdl(this, atomicReference));
    }

    public final Long zzkz() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzgs().zza(atomicReference, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME, "long test flag value", new zzdn(this, atomicReference));
    }

    public final Integer zzla() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzgs().zza(atomicReference, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME, "int test flag value", new zzdo(this, atomicReference));
    }

    public final Double zzlb() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzgs().zza(atomicReference, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME, "double test flag value", new zzdp(this, atomicReference));
    }

    public final void setMeasurementEnabled(boolean z) {
        zzcl();
        zzgg();
        zzgs().zzc((Runnable) new zzdq(this, z));
    }

    public final void zzd(boolean z) {
        zzcl();
        zzgg();
        zzgs().zzc((Runnable) new zzdr(this, z));
    }

    /* access modifiers changed from: private */
    public final void zzj(boolean z) {
        zzaf();
        zzgg();
        zzcl();
        zzgt().zzjn().zzg("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzgu().setMeasurementEnabled(z);
        zzlc();
    }

    /* access modifiers changed from: private */
    public final void zzlc() {
        if (!zzgv().zzba(zzgk().zzal()) || !this.zzada.isEnabled() || !this.zzarf) {
            zzgt().zzjn().zzby("Updating Scion state (FE)");
            zzgl().zzlg();
            return;
        }
        zzgt().zzjn().zzby("Recording app launch after enabling measurement for the first time (FE)");
        zzld();
    }

    public final void setMinimumSessionDuration(long j) {
        zzgg();
        zzgs().zzc((Runnable) new zzds(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzgg();
        zzgs().zzc((Runnable) new zzdt(this, j));
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        logEvent(str, str2, bundle, false, true, zzbx().currentTimeMillis());
    }

    public final void logEvent(String str, String str2, Bundle bundle) {
        logEvent(str, str2, bundle, true, true, zzbx().currentTimeMillis());
    }

    /* access modifiers changed from: 0000 */
    public final void zza(String str, String str2, Bundle bundle) {
        zzgg();
        zzaf();
        zza(str, str2, zzbx().currentTimeMillis(), bundle);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(String str, String str2, long j, Bundle bundle) {
        zzgg();
        zzaf();
        zza(str, str2, j, bundle, true, this.zzarb == null || zzfx.zzcy(str2), false, null);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.String r28, java.lang.String r29, long r30, android.os.Bundle r32, boolean r33, boolean r34, boolean r35, java.lang.String r36) {
        /*
            r27 = this;
            r7 = r27
            r8 = r28
            r5 = r29
            r14 = r30
            r6 = r32
            r4 = r36
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r28)
            com.google.android.gms.measurement.internal.zzq r0 = r27.zzgv()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r1 = com.google.android.gms.measurement.internal.zzai.zzali
            boolean r0 = r0.zze(r4, r1)
            if (r0 != 0) goto L_0x001e
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r29)
        L_0x001e:
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r32)
            r27.zzaf()
            r27.zzcl()
            com.google.android.gms.measurement.internal.zzbw r0 = r7.zzada
            boolean r0 = r0.isEnabled()
            if (r0 != 0) goto L_0x003d
            com.google.android.gms.measurement.internal.zzas r0 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjn()
            java.lang.String r1 = "Event not sent since app measurement is disabled"
            r0.zzby(r1)
            return
        L_0x003d:
            boolean r0 = r7.zzard
            r1 = 0
            r16 = 0
            r3 = 1
            if (r0 != 0) goto L_0x0081
            r7.zzard = r3
            java.lang.String r0 = "com.google.android.gms.tagmanager.TagManagerService"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0074 }
            java.lang.String r2 = "initialize"
            java.lang.Class[] r9 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0065 }
            java.lang.Class<android.content.Context> r10 = android.content.Context.class
            r9[r16] = r10     // Catch:{ Exception -> 0x0065 }
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r2, r9)     // Catch:{ Exception -> 0x0065 }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0065 }
            android.content.Context r9 = r27.getContext()     // Catch:{ Exception -> 0x0065 }
            r2[r16] = r9     // Catch:{ Exception -> 0x0065 }
            r0.invoke(r1, r2)     // Catch:{ Exception -> 0x0065 }
            goto L_0x0081
        L_0x0065:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzas r2 = r27.zzgt()     // Catch:{ ClassNotFoundException -> 0x0074 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjj()     // Catch:{ ClassNotFoundException -> 0x0074 }
            java.lang.String r9 = "Failed to invoke Tag Manager's initialize() method"
            r2.zzg(r9, r0)     // Catch:{ ClassNotFoundException -> 0x0074 }
            goto L_0x0081
        L_0x0074:
            com.google.android.gms.measurement.internal.zzas r0 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjm()
            java.lang.String r2 = "Tag Manager is not found and thus will not be used"
            r0.zzby(r2)
        L_0x0081:
            r0 = 40
            r2 = 2
            if (r35 == 0) goto L_0x00ea
            r27.zzgw()
            java.lang.String r9 = "_iap"
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L_0x00ea
            com.google.android.gms.measurement.internal.zzbw r9 = r7.zzada
            com.google.android.gms.measurement.internal.zzfx r9 = r9.zzgr()
            java.lang.String r10 = "event"
            boolean r11 = r9.zzs(r10, r5)
            if (r11 != 0) goto L_0x00a1
        L_0x009f:
            r9 = 2
            goto L_0x00b4
        L_0x00a1:
            java.lang.String[] r11 = com.google.android.gms.measurement.internal.zzcu.zzaqt
            boolean r11 = r9.zza(r10, r11, r5)
            if (r11 != 0) goto L_0x00ac
            r9 = 13
            goto L_0x00b4
        L_0x00ac:
            boolean r9 = r9.zza(r10, r0, r5)
            if (r9 != 0) goto L_0x00b3
            goto L_0x009f
        L_0x00b3:
            r9 = 0
        L_0x00b4:
            if (r9 == 0) goto L_0x00ea
            com.google.android.gms.measurement.internal.zzas r1 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzji()
            com.google.android.gms.measurement.internal.zzaq r2 = r27.zzgq()
            java.lang.String r2 = r2.zzbt(r5)
            java.lang.String r4 = "Invalid public event name. Event will not be logged (FE)"
            r1.zzg(r4, r2)
            com.google.android.gms.measurement.internal.zzbw r1 = r7.zzada
            r1.zzgr()
            java.lang.String r0 = com.google.android.gms.measurement.internal.zzfx.zza(r5, r0, r3)
            if (r5 == 0) goto L_0x00dd
            int r16 = r29.length()
            r1 = r16
            goto L_0x00de
        L_0x00dd:
            r1 = 0
        L_0x00de:
            com.google.android.gms.measurement.internal.zzbw r2 = r7.zzada
            com.google.android.gms.measurement.internal.zzfx r2 = r2.zzgr()
            java.lang.String r3 = "_ev"
            r2.zza(r9, r3, r0, r1)
            return
        L_0x00ea:
            r27.zzgw()
            com.google.android.gms.measurement.internal.zzdy r9 = r27.zzgm()
            com.google.android.gms.measurement.internal.zzdx r13 = r9.zzle()
            java.lang.String r12 = "_sc"
            if (r13 == 0) goto L_0x0101
            boolean r9 = r6.containsKey(r12)
            if (r9 != 0) goto L_0x0101
            r13.zzars = r3
        L_0x0101:
            if (r33 == 0) goto L_0x0107
            if (r35 == 0) goto L_0x0107
            r9 = 1
            goto L_0x0108
        L_0x0107:
            r9 = 0
        L_0x0108:
            com.google.android.gms.measurement.internal.zzdy.zza(r13, r6, r9)
            java.lang.String r9 = "am"
            boolean r17 = r9.equals(r8)
            boolean r9 = com.google.android.gms.measurement.internal.zzfx.zzcy(r29)
            if (r33 == 0) goto L_0x014a
            com.google.android.gms.measurement.internal.zzcx r10 = r7.zzarb
            if (r10 == 0) goto L_0x014a
            if (r9 != 0) goto L_0x014a
            if (r17 != 0) goto L_0x014a
            com.google.android.gms.measurement.internal.zzas r0 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjn()
            com.google.android.gms.measurement.internal.zzaq r1 = r27.zzgq()
            java.lang.String r1 = r1.zzbt(r5)
            com.google.android.gms.measurement.internal.zzaq r2 = r27.zzgq()
            java.lang.String r2 = r2.zzd(r6)
            java.lang.String r3 = "Passing event to registered event handler (FE)"
            r0.zze(r3, r1, r2)
            com.google.android.gms.measurement.internal.zzcx r1 = r7.zzarb
            r2 = r28
            r3 = r29
            r4 = r32
            r5 = r30
            r1.interceptEvent(r2, r3, r4, r5)
            return
        L_0x014a:
            com.google.android.gms.measurement.internal.zzbw r9 = r7.zzada
            boolean r9 = r9.zzkv()
            if (r9 != 0) goto L_0x0153
            return
        L_0x0153:
            com.google.android.gms.measurement.internal.zzfx r9 = r27.zzgr()
            int r9 = r9.zzcu(r5)
            if (r9 == 0) goto L_0x0199
            com.google.android.gms.measurement.internal.zzas r1 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzji()
            com.google.android.gms.measurement.internal.zzaq r2 = r27.zzgq()
            java.lang.String r2 = r2.zzbt(r5)
            java.lang.String r6 = "Invalid event name. Event will not be logged (FE)"
            r1.zzg(r6, r2)
            r27.zzgr()
            java.lang.String r0 = com.google.android.gms.measurement.internal.zzfx.zza(r5, r0, r3)
            if (r5 == 0) goto L_0x0181
            int r1 = r29.length()
            r16 = r1
        L_0x0181:
            com.google.android.gms.measurement.internal.zzbw r1 = r7.zzada
            com.google.android.gms.measurement.internal.zzfx r1 = r1.zzgr()
            java.lang.String r2 = "_ev"
            r28 = r1
            r29 = r36
            r30 = r9
            r31 = r2
            r32 = r0
            r33 = r16
            r28.zza(r29, r30, r31, r32, r33)
            return
        L_0x0199:
            r0 = 4
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r11 = "_o"
            r0[r16] = r11
            java.lang.String r10 = "_sn"
            r0[r3] = r10
            r0[r2] = r12
            r2 = 3
            java.lang.String r9 = "_si"
            r0[r2] = r9
            java.util.List r0 = com.google.android.gms.common.util.CollectionUtils.listOf((T[]) r0)
            com.google.android.gms.measurement.internal.zzfx r2 = r27.zzgr()
            r18 = 1
            r1 = r9
            r9 = r2
            r2 = r10
            r10 = r36
            r20 = r11
            r11 = r29
            r3 = r12
            r12 = r32
            r21 = r13
            r13 = r0
            r7 = r14
            r14 = r35
            r15 = r18
            android.os.Bundle r15 = r9.zza(r10, r11, r12, r13, r14, r15)
            if (r15 == 0) goto L_0x01f7
            boolean r9 = r15.containsKey(r3)
            if (r9 == 0) goto L_0x01f7
            boolean r9 = r15.containsKey(r1)
            if (r9 != 0) goto L_0x01dc
            goto L_0x01f7
        L_0x01dc:
            java.lang.String r2 = r15.getString(r2)
            java.lang.String r3 = r15.getString(r3)
            long r9 = r15.getLong(r1)
            java.lang.Long r1 = java.lang.Long.valueOf(r9)
            com.google.android.gms.measurement.internal.zzdx r9 = new com.google.android.gms.measurement.internal.zzdx
            long r10 = r1.longValue()
            r9.<init>(r2, r3, r10)
            r13 = r9
            goto L_0x01f8
        L_0x01f7:
            r13 = 0
        L_0x01f8:
            if (r13 != 0) goto L_0x01fd
            r14 = r21
            goto L_0x01fe
        L_0x01fd:
            r14 = r13
        L_0x01fe:
            com.google.android.gms.measurement.internal.zzq r1 = r27.zzgv()
            boolean r1 = r1.zzbk(r4)
            java.lang.String r13 = "_ae"
            r9 = 0
            if (r1 == 0) goto L_0x0232
            r27.zzgw()
            com.google.android.gms.measurement.internal.zzdy r1 = r27.zzgm()
            com.google.android.gms.measurement.internal.zzdx r1 = r1.zzle()
            if (r1 == 0) goto L_0x0232
            boolean r1 = r13.equals(r5)
            if (r1 == 0) goto L_0x0232
            com.google.android.gms.measurement.internal.zzfd r1 = r27.zzgo()
            long r1 = r1.zzlp()
            int r3 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r3 <= 0) goto L_0x0232
            com.google.android.gms.measurement.internal.zzfx r3 = r27.zzgr()
            r3.zza(r15, r1)
        L_0x0232:
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            r12.add(r15)
            com.google.android.gms.measurement.internal.zzfx r1 = r27.zzgr()
            java.security.SecureRandom r1 = r1.zzmk()
            long r2 = r1.nextLong()
            com.google.android.gms.measurement.internal.zzq r1 = r27.zzgv()
            com.google.android.gms.measurement.internal.zzam r11 = r27.zzgk()
            java.lang.String r11 = r11.zzal()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzai.zzaky
            boolean r1 = r1.zze(r11, r9)
            if (r1 == 0) goto L_0x02eb
            com.google.android.gms.measurement.internal.zzbd r1 = r27.zzgu()
            com.google.android.gms.measurement.internal.zzbg r1 = r1.zzanq
            long r9 = r1.get()
            r18 = 0
            int r1 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            if (r1 <= 0) goto L_0x02eb
            com.google.android.gms.measurement.internal.zzbd r1 = r27.zzgu()
            boolean r1 = r1.zzaf(r7)
            if (r1 == 0) goto L_0x02eb
            com.google.android.gms.measurement.internal.zzbd r1 = r27.zzgu()
            com.google.android.gms.measurement.internal.zzbf r1 = r1.zzant
            boolean r1 = r1.get()
            if (r1 == 0) goto L_0x02eb
            com.google.android.gms.measurement.internal.zzas r1 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.String r9 = "Current session is expired, remove the session number and Id"
            r1.zzby(r9)
            com.google.android.gms.measurement.internal.zzq r1 = r27.zzgv()
            com.google.android.gms.measurement.internal.zzam r9 = r27.zzgk()
            java.lang.String r9 = r9.zzal()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r10 = com.google.android.gms.measurement.internal.zzai.zzaku
            boolean r1 = r1.zze(r9, r10)
            if (r1 == 0) goto L_0x02bf
            r9 = 0
            com.google.android.gms.common.util.Clock r1 = r27.zzbx()
            long r10 = r1.currentTimeMillis()
            java.lang.String r21 = "auto"
            java.lang.String r22 = "_sid"
            r1 = r27
            r23 = r2
            r2 = r21
            r7 = 1
            r3 = r22
            r8 = r4
            r4 = r9
            r9 = r5
            r5 = r10
            r1.zza(r2, r3, r4, r5)
            goto L_0x02c4
        L_0x02bf:
            r23 = r2
            r8 = r4
            r9 = r5
            r7 = 1
        L_0x02c4:
            com.google.android.gms.measurement.internal.zzq r1 = r27.zzgv()
            com.google.android.gms.measurement.internal.zzam r2 = r27.zzgk()
            java.lang.String r2 = r2.zzal()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r3 = com.google.android.gms.measurement.internal.zzai.zzakv
            boolean r1 = r1.zze(r2, r3)
            if (r1 == 0) goto L_0x02f0
            r4 = 0
            com.google.android.gms.common.util.Clock r1 = r27.zzbx()
            long r5 = r1.currentTimeMillis()
            java.lang.String r2 = "auto"
            java.lang.String r3 = "_sno"
            r1 = r27
            r1.zza(r2, r3, r4, r5)
            goto L_0x02f0
        L_0x02eb:
            r23 = r2
            r8 = r4
            r9 = r5
            r7 = 1
        L_0x02f0:
            com.google.android.gms.measurement.internal.zzq r1 = r27.zzgv()
            com.google.android.gms.measurement.internal.zzam r2 = r27.zzgk()
            java.lang.String r2 = r2.zzal()
            boolean r1 = r1.zzbj(r2)
            if (r1 == 0) goto L_0x032b
            java.lang.String r1 = "extend_session"
            r2 = 0
            long r1 = r15.getLong(r1, r2)
            r3 = 1
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x032b
            com.google.android.gms.measurement.internal.zzas r1 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.String r2 = "EXTEND_SESSION param attached: initiate a new session or extend the current active session"
            r1.zzby(r2)
            r5 = r27
            r3 = r30
            com.google.android.gms.measurement.internal.zzbw r1 = r5.zzada
            com.google.android.gms.measurement.internal.zzfd r1 = r1.zzgo()
            r1.zza(r3, r7)
            goto L_0x032f
        L_0x032b:
            r5 = r27
            r3 = r30
        L_0x032f:
            java.util.Set r1 = r15.keySet()
            int r2 = r32.size()
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.Object[] r1 = r1.toArray(r2)
            java.lang.String[] r1 = (java.lang.String[]) r1
            java.util.Arrays.sort(r1)
            int r2 = r1.length
            r6 = 0
            r11 = 0
        L_0x0345:
            java.lang.String r10 = "_eid"
            if (r6 >= r2) goto L_0x03f0
            r7 = r1[r6]
            java.lang.Object r18 = r15.get(r7)
            r27.zzgr()
            r32 = r1
            android.os.Bundle[] r1 = com.google.android.gms.measurement.internal.zzfx.zzf(r18)
            if (r1 == 0) goto L_0x03cc
            r18 = r2
            int r2 = r1.length
            r15.putInt(r7, r2)
            r2 = 0
        L_0x0361:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x03bb
            r3 = r1[r2]
            r4 = 1
            com.google.android.gms.measurement.internal.zzdy.zza(r14, r3, r4)
            com.google.android.gms.measurement.internal.zzfx r4 = r27.zzgr()
            r19 = 0
            java.lang.String r21 = "_ep"
            r9 = r4
            r4 = r10
            r10 = r36
            r25 = r11
            r11 = r21
            r26 = r12
            r12 = r3
            r3 = r13
            r13 = r0
            r21 = r14
            r14 = r35
            r33 = r0
            r0 = r15
            r15 = r19
            android.os.Bundle r9 = r9.zza(r10, r11, r12, r13, r14, r15)
            java.lang.String r10 = "_en"
            r11 = r29
            r9.putString(r10, r11)
            r12 = r23
            r9.putLong(r4, r12)
            java.lang.String r10 = "_gn"
            r9.putString(r10, r7)
            int r10 = r1.length
            java.lang.String r14 = "_ll"
            r9.putInt(r14, r10)
            java.lang.String r10 = "_i"
            r9.putInt(r10, r2)
            r10 = r26
            r10.add(r9)
            int r2 = r2 + 1
            r15 = r0
            r9 = r11
            r14 = r21
            r11 = r25
            r0 = r33
            r13 = r3
            r12 = r10
            r10 = r4
            goto L_0x0361
        L_0x03bb:
            r33 = r0
            r25 = r11
            r10 = r12
            r3 = r13
            r21 = r14
            r0 = r15
            r12 = r23
            r11 = r9
            int r1 = r1.length
            r2 = r25
            int r1 = r1 + r2
            goto L_0x03da
        L_0x03cc:
            r33 = r0
            r18 = r2
            r2 = r11
            r10 = r12
            r3 = r13
            r21 = r14
            r0 = r15
            r12 = r23
            r11 = r9
            r1 = r2
        L_0x03da:
            int r6 = r6 + 1
            r15 = r0
            r9 = r11
            r23 = r12
            r2 = r18
            r14 = r21
            r7 = 1
            r0 = r33
            r11 = r1
            r13 = r3
            r12 = r10
            r3 = r30
            r1 = r32
            goto L_0x0345
        L_0x03f0:
            r4 = r10
            r2 = r11
            r10 = r12
            r3 = r13
            r0 = r15
            r12 = r23
            r11 = r9
            if (r2 == 0) goto L_0x0402
            r0.putLong(r4, r12)
            java.lang.String r1 = "_epc"
            r0.putInt(r1, r2)
        L_0x0402:
            r0 = 0
        L_0x0403:
            int r1 = r10.size()
            if (r0 >= r1) goto L_0x048f
            java.lang.Object r1 = r10.get(r0)
            android.os.Bundle r1 = (android.os.Bundle) r1
            if (r0 == 0) goto L_0x0413
            r2 = 1
            goto L_0x0414
        L_0x0413:
            r2 = 0
        L_0x0414:
            if (r2 == 0) goto L_0x041b
            java.lang.String r2 = "_ep"
            r7 = r28
            goto L_0x041e
        L_0x041b:
            r7 = r28
            r2 = r11
        L_0x041e:
            r9 = r20
            r1.putString(r9, r7)
            if (r34 == 0) goto L_0x042d
            com.google.android.gms.measurement.internal.zzfx r4 = r27.zzgr()
            android.os.Bundle r1 = r4.zze(r1)
        L_0x042d:
            r12 = r1
            com.google.android.gms.measurement.internal.zzas r1 = r27.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjn()
            com.google.android.gms.measurement.internal.zzaq r4 = r27.zzgq()
            java.lang.String r4 = r4.zzbt(r11)
            com.google.android.gms.measurement.internal.zzaq r6 = r27.zzgq()
            java.lang.String r6 = r6.zzd(r12)
            java.lang.String r13 = "Logging event (FE)"
            r1.zze(r13, r4, r6)
            com.google.android.gms.measurement.internal.zzag r13 = new com.google.android.gms.measurement.internal.zzag
            com.google.android.gms.measurement.internal.zzad r4 = new com.google.android.gms.measurement.internal.zzad
            r4.<init>(r12)
            r1 = r13
            r14 = r3
            r3 = r4
            r4 = r28
            r15 = r5
            r5 = r30
            r1.<init>(r2, r3, r4, r5)
            com.google.android.gms.measurement.internal.zzeb r1 = r27.zzgl()
            r1.zzc(r13, r8)
            if (r17 != 0) goto L_0x0487
            java.util.Set<com.google.android.gms.measurement.internal.zzcy> r1 = r15.zzarc
            java.util.Iterator r13 = r1.iterator()
        L_0x046c:
            boolean r1 = r13.hasNext()
            if (r1 == 0) goto L_0x0487
            java.lang.Object r1 = r13.next()
            com.google.android.gms.measurement.internal.zzcy r1 = (com.google.android.gms.measurement.internal.zzcy) r1
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>(r12)
            r2 = r28
            r3 = r29
            r5 = r30
            r1.onEvent(r2, r3, r4, r5)
            goto L_0x046c
        L_0x0487:
            int r0 = r0 + 1
            r20 = r9
            r3 = r14
            r5 = r15
            goto L_0x0403
        L_0x048f:
            r14 = r3
            r15 = r5
            r27.zzgw()
            com.google.android.gms.measurement.internal.zzdy r0 = r27.zzgm()
            com.google.android.gms.measurement.internal.zzdx r0 = r0.zzle()
            if (r0 == 0) goto L_0x04ac
            boolean r0 = r14.equals(r11)
            if (r0 == 0) goto L_0x04ac
            com.google.android.gms.measurement.internal.zzfd r0 = r27.zzgo()
            r1 = 1
            r0.zza(r1, r1)
        L_0x04ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzda.zza(java.lang.String, java.lang.String, long, android.os.Bundle, boolean, boolean, boolean, java.lang.String):void");
    }

    public final void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        zzgg();
        zzb(str == null ? SettingsJsonConstants.APP_KEY : str, str2, j, bundle == null ? new Bundle() : bundle, z2, !z2 || this.zzarb == null || zzfx.zzcy(str2), !z, null);
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle zzf = zzfx.zzf(bundle);
        zzbr zzgs = zzgs();
        zzdc zzdc = new zzdc(this, str, str2, j, zzf, z, z2, z3, str3);
        zzgs.zzc((Runnable) zzdc);
    }

    public final void zzb(String str, String str2, Object obj, boolean z) {
        zza(str, str2, obj, z, zzbx().currentTimeMillis());
    }

    public final void zza(String str, String str2, Object obj, boolean z, long j) {
        if (str == null) {
            str = SettingsJsonConstants.APP_KEY;
        }
        String str3 = str;
        int i = 6;
        int i2 = 0;
        if (z) {
            i = zzgr().zzcv(str2);
        } else {
            zzfx zzgr = zzgr();
            String str4 = "user property";
            if (zzgr.zzs(str4, str2)) {
                if (!zzgr.zza(str4, zzcw.zzaqx, str2)) {
                    i = 15;
                } else if (zzgr.zza(str4, 24, str2)) {
                    i = 0;
                }
            }
        }
        String str5 = "_ev";
        if (i != 0) {
            zzgr();
            String zza = zzfx.zza(str2, 24, true);
            if (str2 != null) {
                i2 = str2.length();
            }
            this.zzada.zzgr().zza(i, str5, zza, i2);
        } else if (obj != null) {
            int zzi = zzgr().zzi(str2, obj);
            if (zzi != 0) {
                zzgr();
                String zza2 = zzfx.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i2 = String.valueOf(obj).length();
                }
                this.zzada.zzgr().zza(zzi, str5, zza2, i2);
                return;
            }
            Object zzj = zzgr().zzj(str2, obj);
            if (zzj != null) {
                zza(str3, str2, j, zzj);
            }
        } else {
            zza(str3, str2, j, (Object) null);
        }
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzbr zzgs = zzgs();
        zzdd zzdd = new zzdd(this, str, str2, obj, j);
        zzgs.zzc((Runnable) zzdd);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(String str, String str2, Object obj, long j) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzgg();
        zzcl();
        if (!this.zzada.isEnabled()) {
            zzgt().zzjn().zzby("User property not set since app measurement is disabled");
        } else if (this.zzada.zzkv()) {
            zzgt().zzjn().zze("Setting user property (FE)", zzgq().zzbt(str2), obj);
            zzfu zzfu = new zzfu(str2, j, obj, str);
            zzgl().zzb(zzfu);
        }
    }

    public final List<zzfu> zzk(boolean z) {
        zzgg();
        zzcl();
        zzgt().zzjn().zzby("Fetching user attributes (FE)");
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzby("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzn.isMainThread()) {
            zzgt().zzjg().zzby("Cannot get all user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzada.zzgs().zzc((Runnable) new zzde(this, atomicReference, z));
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgt().zzjj().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzfu> list = (List) atomicReference.get();
            if (list == null) {
                zzgt().zzjj().zzby("Timed out waiting for get user properties");
                list = Collections.emptyList();
            }
            return list;
        }
    }

    public final String zzgc() {
        zzgg();
        return (String) this.zzare.get();
    }

    public final String zzag(long j) {
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzby("Cannot retrieve app instance id from analytics worker thread");
            return null;
        } else if (zzn.isMainThread()) {
            zzgt().zzjg().zzby("Cannot retrieve app instance id from main thread");
            return null;
        } else {
            long elapsedRealtime = zzbx().elapsedRealtime();
            String zzah = zzah(120000);
            long elapsedRealtime2 = zzbx().elapsedRealtime() - elapsedRealtime;
            if (zzah == null && elapsedRealtime2 < 120000) {
                zzah = zzah(120000 - elapsedRealtime2);
            }
            return zzah;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzcp(String str) {
        this.zzare.set(str);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:10|11|12|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        zzgt().zzjj().zzby("Interrupted waiting for app instance id");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        return null;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x001d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String zzah(long r4) {
        /*
            r3 = this;
            java.util.concurrent.atomic.AtomicReference r0 = new java.util.concurrent.atomic.AtomicReference
            r0.<init>()
            monitor-enter(r0)
            com.google.android.gms.measurement.internal.zzbr r1 = r3.zzgs()     // Catch:{ all -> 0x002d }
            com.google.android.gms.measurement.internal.zzdf r2 = new com.google.android.gms.measurement.internal.zzdf     // Catch:{ all -> 0x002d }
            r2.<init>(r3, r0)     // Catch:{ all -> 0x002d }
            r1.zzc(r2)     // Catch:{ all -> 0x002d }
            r0.wait(r4)     // Catch:{ InterruptedException -> 0x001d }
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            java.lang.Object r4 = r0.get()
            java.lang.String r4 = (java.lang.String) r4
            return r4
        L_0x001d:
            com.google.android.gms.measurement.internal.zzas r4 = r3.zzgt()     // Catch:{ all -> 0x002d }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjj()     // Catch:{ all -> 0x002d }
            java.lang.String r5 = "Interrupted waiting for app instance id"
            r4.zzby(r5)     // Catch:{ all -> 0x002d }
            r4 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return r4
        L_0x002d:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzda.zzah(long):java.lang.String");
    }

    public final void resetAnalyticsData(long j) {
        if (zzgv().zza(zzai.zzald)) {
            zzcp(null);
        }
        zzgs().zzc((Runnable) new zzdg(this, j));
    }

    public final void zzld() {
        zzaf();
        zzgg();
        zzcl();
        if (this.zzada.zzkv()) {
            zzgl().zzld();
            this.zzarf = false;
            String zzka = zzgu().zzka();
            if (!TextUtils.isEmpty(zzka)) {
                zzgp().zzcl();
                if (!zzka.equals(VERSION.RELEASE)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("_po", zzka);
                    logEvent("auto", "_ou", bundle);
                }
            }
        }
    }

    public final void zza(zzcx zzcx) {
        zzaf();
        zzgg();
        zzcl();
        if (zzcx != null) {
            zzcx zzcx2 = this.zzarb;
            if (zzcx != zzcx2) {
                Preconditions.checkState(zzcx2 == null, "EventInterceptor already set.");
            }
        }
        this.zzarb = zzcx;
    }

    public final void zza(zzcy zzcy) {
        zzgg();
        zzcl();
        Preconditions.checkNotNull(zzcy);
        if (!this.zzarc.add(zzcy)) {
            zzgt().zzjj().zzby("OnEventListener already registered");
        }
    }

    public final void zzb(zzcy zzcy) {
        zzgg();
        zzcl();
        Preconditions.checkNotNull(zzcy);
        if (!this.zzarc.remove(zzcy)) {
            zzgt().zzjj().zzby("OnEventListener had not been registered");
        }
    }

    public final void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzgg();
        ConditionalUserProperty conditionalUserProperty2 = new ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            zzgt().zzjj().zzby("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mAppId);
        zzgf();
        zza(new ConditionalUserProperty(conditionalUserProperty));
    }

    private final void zza(ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzbx().currentTimeMillis();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzgr().zzcv(str) != 0) {
            zzgt().zzjg().zzg("Invalid conditional user property name", zzgq().zzbv(str));
        } else if (zzgr().zzi(str, obj) != 0) {
            zzgt().zzjg().zze("Invalid conditional user property value", zzgq().zzbv(str), obj);
        } else {
            Object zzj = zzgr().zzj(str, obj);
            if (zzj == null) {
                zzgt().zzjg().zze("Unable to normalize conditional user property value", zzgq().zzbv(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzj;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                long j2 = conditionalUserProperty.mTimeToLive;
                if (j2 > 15552000000L || j2 < 1) {
                    zzgt().zzjg().zze("Invalid conditional user property time to live", zzgq().zzbv(str), Long.valueOf(j2));
                    return;
                }
                zzgs().zzc((Runnable) new zzdi(this, conditionalUserProperty));
                return;
            }
            zzgt().zzjg().zze("Invalid conditional user property timeout", zzgq().zzbv(str), Long.valueOf(j));
        }
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zzgg();
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        zza(str, str2, str3, bundle);
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzbx().currentTimeMillis();
        Preconditions.checkNotEmpty(str2);
        ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzgs().zzc((Runnable) new zzdj(this, conditionalUserProperty));
    }

    /* access modifiers changed from: private */
    public final void zzb(ConditionalUserProperty conditionalUserProperty) {
        ConditionalUserProperty conditionalUserProperty2 = conditionalUserProperty;
        zzaf();
        zzcl();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty2.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty2.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty2.mValue);
        if (!this.zzada.isEnabled()) {
            zzgt().zzjn().zzby("Conditional property not sent since collection is disabled");
            return;
        }
        zzfu zzfu = new zzfu(conditionalUserProperty2.mName, conditionalUserProperty2.mTriggeredTimestamp, conditionalUserProperty2.mValue, conditionalUserProperty2.mOrigin);
        try {
            zzag zza = zzgr().zza(conditionalUserProperty2.mAppId, conditionalUserProperty2.mTriggeredEventName, conditionalUserProperty2.mTriggeredEventParams, conditionalUserProperty2.mOrigin, 0, true, false);
            zzag zza2 = zzgr().zza(conditionalUserProperty2.mAppId, conditionalUserProperty2.mTimedOutEventName, conditionalUserProperty2.mTimedOutEventParams, conditionalUserProperty2.mOrigin, 0, true, false);
            zzag zza3 = zzgr().zza(conditionalUserProperty2.mAppId, conditionalUserProperty2.mExpiredEventName, conditionalUserProperty2.mExpiredEventParams, conditionalUserProperty2.mOrigin, 0, true, false);
            String str = conditionalUserProperty2.mAppId;
            String str2 = conditionalUserProperty2.mOrigin;
            long j = conditionalUserProperty2.mCreationTimestamp;
            String str3 = conditionalUserProperty2.mTriggerEventName;
            long j2 = conditionalUserProperty2.mTriggerTimeout;
            zzo zzo = r3;
            zzo zzo2 = new zzo(str, str2, zzfu, j, false, str3, zza2, j2, zza, conditionalUserProperty2.mTimeToLive, zza3);
            zzgl().zzd(zzo);
        } catch (IllegalArgumentException unused) {
        }
    }

    /* access modifiers changed from: private */
    public final void zzc(ConditionalUserProperty conditionalUserProperty) {
        ConditionalUserProperty conditionalUserProperty2 = conditionalUserProperty;
        zzaf();
        zzcl();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty2.mName);
        if (!this.zzada.isEnabled()) {
            zzgt().zzjn().zzby("Conditional property not cleared since collection is disabled");
            return;
        }
        zzfu zzfu = new zzfu(conditionalUserProperty2.mName, 0, null, null);
        try {
            zzag zza = zzgr().zza(conditionalUserProperty2.mAppId, conditionalUserProperty2.mExpiredEventName, conditionalUserProperty2.mExpiredEventParams, conditionalUserProperty2.mOrigin, conditionalUserProperty2.mCreationTimestamp, true, false);
            String str = conditionalUserProperty2.mAppId;
            String str2 = conditionalUserProperty2.mOrigin;
            long j = conditionalUserProperty2.mCreationTimestamp;
            boolean z = conditionalUserProperty2.mActive;
            String str3 = conditionalUserProperty2.mTriggerEventName;
            long j2 = conditionalUserProperty2.mTriggerTimeout;
            zzo zzo = r3;
            zzo zzo2 = new zzo(str, str2, zzfu, j, z, str3, null, j2, null, conditionalUserProperty2.mTimeToLive, zza);
            zzgl().zzd(zzo);
        } catch (IllegalArgumentException unused) {
        }
    }

    public final List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        zzgg();
        return zzf(null, str, str2);
    }

    public final List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        return zzf(str, str2, str3);
    }

    private final List<ConditionalUserProperty> zzf(String str, String str2, String str3) {
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzby("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzn.isMainThread()) {
            zzgt().zzjg().zzby("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                zzbr zzgs = this.zzada.zzgs();
                zzdk zzdk = new zzdk(this, atomicReference, str, str2, str3);
                zzgs.zzc((Runnable) zzdk);
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgt().zzjj().zze("Interrupted waiting for get conditional user properties", str, e);
                }
            }
            List<zzo> list = (List) atomicReference.get();
            if (list == null) {
                zzgt().zzjj().zzg("Timed out waiting for get conditional user properties", str);
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList(list.size());
            for (zzo zzo : list) {
                ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
                conditionalUserProperty.mAppId = zzo.packageName;
                conditionalUserProperty.mOrigin = zzo.origin;
                conditionalUserProperty.mCreationTimestamp = zzo.creationTimestamp;
                conditionalUserProperty.mName = zzo.zzags.name;
                conditionalUserProperty.mValue = zzo.zzags.getValue();
                conditionalUserProperty.mActive = zzo.active;
                conditionalUserProperty.mTriggerEventName = zzo.triggerEventName;
                if (zzo.zzagt != null) {
                    conditionalUserProperty.mTimedOutEventName = zzo.zzagt.name;
                    if (zzo.zzagt.zzahu != null) {
                        conditionalUserProperty.mTimedOutEventParams = zzo.zzagt.zzahu.zziy();
                    }
                }
                conditionalUserProperty.mTriggerTimeout = zzo.triggerTimeout;
                if (zzo.zzagu != null) {
                    conditionalUserProperty.mTriggeredEventName = zzo.zzagu.name;
                    if (zzo.zzagu.zzahu != null) {
                        conditionalUserProperty.mTriggeredEventParams = zzo.zzagu.zzahu.zziy();
                    }
                }
                conditionalUserProperty.mTriggeredTimestamp = zzo.zzags.zzaum;
                conditionalUserProperty.mTimeToLive = zzo.timeToLive;
                if (zzo.zzagv != null) {
                    conditionalUserProperty.mExpiredEventName = zzo.zzagv.name;
                    if (zzo.zzagv.zzahu != null) {
                        conditionalUserProperty.mExpiredEventParams = zzo.zzagv.zzahu.zziy();
                    }
                }
                arrayList.add(conditionalUserProperty);
            }
            return arrayList;
        }
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzgg();
        return zzb((String) null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        return zzb(str, str2, str3, z);
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzby("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        } else if (zzn.isMainThread()) {
            zzgt().zzjg().zzby("Cannot get user properties from main thread");
            return Collections.emptyMap();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                zzbr zzgs = this.zzada.zzgs();
                zzdm zzdm = new zzdm(this, atomicReference, str, str2, str3, z);
                zzgs.zzc((Runnable) zzdm);
                try {
                    atomicReference.wait(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                } catch (InterruptedException e) {
                    zzgt().zzjj().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzfu> list = (List) atomicReference.get();
            if (list == null) {
                zzgt().zzjj().zzby("Timed out waiting for get user properties");
                return Collections.emptyMap();
            }
            ArrayMap arrayMap = new ArrayMap(list.size());
            for (zzfu zzfu : list) {
                arrayMap.put(zzfu.name, zzfu.getValue());
            }
            return arrayMap;
        }
    }

    public final String getCurrentScreenName() {
        zzdx zzlf = this.zzada.zzgm().zzlf();
        if (zzlf != null) {
            return zzlf.zzuw;
        }
        return null;
    }

    public final String getCurrentScreenClass() {
        zzdx zzlf = this.zzada.zzgm().zzlf();
        if (zzlf != null) {
            return zzlf.zzarq;
        }
        return null;
    }

    public final String getGmpAppId() {
        if (this.zzada.zzko() != null) {
            return this.zzada.zzko();
        }
        try {
            return GoogleServices.getGoogleAppId();
        } catch (IllegalStateException e) {
            this.zzada.zzgt().zzjg().zzg("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zza zzgi() {
        return super.zzgi();
    }

    public final /* bridge */ /* synthetic */ zzda zzgj() {
        return super.zzgj();
    }

    public final /* bridge */ /* synthetic */ zzam zzgk() {
        return super.zzgk();
    }

    public final /* bridge */ /* synthetic */ zzeb zzgl() {
        return super.zzgl();
    }

    public final /* bridge */ /* synthetic */ zzdy zzgm() {
        return super.zzgm();
    }

    public final /* bridge */ /* synthetic */ zzao zzgn() {
        return super.zzgn();
    }

    public final /* bridge */ /* synthetic */ zzfd zzgo() {
        return super.zzgo();
    }

    public final /* bridge */ /* synthetic */ zzaa zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfx zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbr zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzas zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbd zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzq zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzn zzgw() {
        return super.zzgw();
    }
}
