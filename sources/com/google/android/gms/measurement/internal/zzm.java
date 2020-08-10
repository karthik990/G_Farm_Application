package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfj;
import com.google.android.gms.internal.measurement.zzfk;
import com.google.android.gms.internal.measurement.zzfl;
import com.google.android.gms.internal.measurement.zzfm;
import com.google.android.gms.internal.measurement.zzfn;
import com.google.android.gms.internal.measurement.zzfs;
import com.google.android.gms.internal.measurement.zzfu;
import com.google.android.gms.internal.measurement.zzfz;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzm extends zzfm {
    zzm(zzfn zzfn) {
        super(zzfn);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x03b7  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x040c  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x045f  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0480  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x02ce  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x02eb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.measurement.zzfr[] zza(java.lang.String r68, com.google.android.gms.internal.measurement.zzft[] r69, com.google.android.gms.internal.measurement.zzfz[] r70) {
        /*
            r67 = this;
            r7 = r67
            r15 = r68
            r13 = r69
            r14 = r70
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r68)
            java.util.HashSet r11 = new java.util.HashSet
            r11.<init>()
            androidx.collection.ArrayMap r12 = new androidx.collection.ArrayMap
            r12.<init>()
            androidx.collection.ArrayMap r10 = new androidx.collection.ArrayMap
            r10.<init>()
            androidx.collection.ArrayMap r9 = new androidx.collection.ArrayMap
            r9.<init>()
            androidx.collection.ArrayMap r8 = new androidx.collection.ArrayMap
            r8.<init>()
            androidx.collection.ArrayMap r6 = new androidx.collection.ArrayMap
            r6.<init>()
            com.google.android.gms.measurement.internal.zzq r0 = r67.zzgv()
            boolean r23 = r0.zzbb(r15)
            com.google.android.gms.measurement.internal.zzt r0 = r67.zzjt()
            java.util.Map r0 = r0.zzbp(r15)
            r5 = 1
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r5)
            if (r0 == 0) goto L_0x0187
            java.util.Set r1 = r0.keySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0048:
            boolean r16 = r1.hasNext()
            if (r16 == 0) goto L_0x0187
            java.lang.Object r16 = r1.next()
            java.lang.Integer r16 = (java.lang.Integer) r16
            int r16 = r16.intValue()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r16)
            java.lang.Object r4 = r0.get(r4)
            com.google.android.gms.internal.measurement.zzfx r4 = (com.google.android.gms.internal.measurement.zzfx) r4
            java.lang.Integer r5 = java.lang.Integer.valueOf(r16)
            java.lang.Object r5 = r10.get(r5)
            java.util.BitSet r5 = (java.util.BitSet) r5
            java.lang.Integer r2 = java.lang.Integer.valueOf(r16)
            java.lang.Object r2 = r9.get(r2)
            java.util.BitSet r2 = (java.util.BitSet) r2
            r20 = r0
            if (r23 == 0) goto L_0x00b6
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            r21 = r1
            if (r4 == 0) goto L_0x00aa
            com.google.android.gms.internal.measurement.zzfs[] r1 = r4.zzayr
            if (r1 != 0) goto L_0x0088
            goto L_0x00aa
        L_0x0088:
            com.google.android.gms.internal.measurement.zzfs[] r1 = r4.zzayr
            r22 = r2
            int r2 = r1.length
            r24 = r3
            r3 = 0
        L_0x0090:
            if (r3 >= r2) goto L_0x00ae
            r25 = r2
            r2 = r1[r3]
            r26 = r1
            java.lang.Integer r1 = r2.zzawz
            if (r1 == 0) goto L_0x00a3
            java.lang.Integer r1 = r2.zzawz
            java.lang.Long r2 = r2.zzaxa
            r0.put(r1, r2)
        L_0x00a3:
            int r3 = r3 + 1
            r2 = r25
            r1 = r26
            goto L_0x0090
        L_0x00aa:
            r22 = r2
            r24 = r3
        L_0x00ae:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r16)
            r8.put(r1, r0)
            goto L_0x00bd
        L_0x00b6:
            r21 = r1
            r22 = r2
            r24 = r3
            r0 = 0
        L_0x00bd:
            if (r5 != 0) goto L_0x00d8
            java.util.BitSet r5 = new java.util.BitSet
            r5.<init>()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r16)
            r10.put(r1, r5)
            java.util.BitSet r2 = new java.util.BitSet
            r2.<init>()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r16)
            r9.put(r1, r2)
            goto L_0x00da
        L_0x00d8:
            r2 = r22
        L_0x00da:
            r1 = 0
        L_0x00db:
            long[] r3 = r4.zzayp
            int r3 = r3.length
            int r3 = r3 << 6
            if (r1 >= r3) goto L_0x0130
            long[] r3 = r4.zzayp
            boolean r3 = com.google.android.gms.measurement.internal.zzft.zza(r3, r1)
            if (r3 == 0) goto L_0x0115
            com.google.android.gms.measurement.internal.zzas r3 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjo()
            r22 = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r16)
            r25 = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r1)
            r26 = r10
            java.lang.String r10 = "Filter already evaluated. audience ID, filter ID"
            r3.zze(r10, r8, r9)
            r2.set(r1)
            long[] r3 = r4.zzayq
            boolean r3 = com.google.android.gms.measurement.internal.zzft.zza(r3, r1)
            if (r3 == 0) goto L_0x011b
            r5.set(r1)
            r3 = 1
            goto L_0x011c
        L_0x0115:
            r22 = r8
            r25 = r9
            r26 = r10
        L_0x011b:
            r3 = 0
        L_0x011c:
            if (r0 == 0) goto L_0x0127
            if (r3 != 0) goto L_0x0127
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r0.remove(r3)
        L_0x0127:
            int r1 = r1 + 1
            r8 = r22
            r9 = r25
            r10 = r26
            goto L_0x00db
        L_0x0130:
            r22 = r8
            r25 = r9
            r26 = r10
            com.google.android.gms.internal.measurement.zzfr r1 = new com.google.android.gms.internal.measurement.zzfr
            r1.<init>()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r16)
            r12.put(r3, r1)
            r3 = 0
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r3)
            r1.zzawx = r8
            r1.zzaww = r4
            com.google.android.gms.internal.measurement.zzfx r3 = new com.google.android.gms.internal.measurement.zzfx
            r3.<init>()
            r1.zzawv = r3
            com.google.android.gms.internal.measurement.zzfx r3 = r1.zzawv
            long[] r4 = com.google.android.gms.measurement.internal.zzft.zza(r5)
            r3.zzayq = r4
            com.google.android.gms.internal.measurement.zzfx r3 = r1.zzawv
            long[] r2 = com.google.android.gms.measurement.internal.zzft.zza(r2)
            r3.zzayp = r2
            if (r23 == 0) goto L_0x0178
            com.google.android.gms.internal.measurement.zzfx r1 = r1.zzawv
            com.google.android.gms.internal.measurement.zzfs[] r0 = zzb(r0)
            r1.zzayr = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r16)
            androidx.collection.ArrayMap r1 = new androidx.collection.ArrayMap
            r1.<init>()
            r6.put(r0, r1)
        L_0x0178:
            r0 = r20
            r1 = r21
            r8 = r22
            r3 = r24
            r9 = r25
            r10 = r26
            r5 = 1
            goto L_0x0048
        L_0x0187:
            r24 = r3
            r22 = r8
            r25 = r9
            r26 = r10
            java.lang.String r9 = "Filter definition"
            java.lang.String r4 = "Skipping failed audience ID"
            java.lang.String r27 = "null"
            if (r13 == 0) goto L_0x07e0
            androidx.collection.ArrayMap r5 = new androidx.collection.ArrayMap
            r5.<init>()
            int r3 = r13.length
            r28 = 0
            r20 = r28
            r0 = 0
            r1 = 0
            r2 = 0
        L_0x01a4:
            if (r2 >= r3) goto L_0x07e0
            r14 = r13[r2]
            java.lang.String r8 = r14.name
            com.google.android.gms.internal.measurement.zzfu[] r10 = r14.zzaxc
            r30 = r2
            com.google.android.gms.measurement.internal.zzq r2 = r67.zzgv()
            r31 = r3
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r3 = com.google.android.gms.measurement.internal.zzai.zzaki
            boolean r2 = r2.zzd(r15, r3)
            r32 = 1
            if (r2 == 0) goto L_0x0396
            r67.zzjr()
            java.lang.String r2 = "_eid"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzft.zzb(r14, r2)
            java.lang.Long r3 = (java.lang.Long) r3
            if (r3 == 0) goto L_0x01ce
            r34 = 1
            goto L_0x01d0
        L_0x01ce:
            r34 = 0
        L_0x01d0:
            r35 = r4
            if (r34 == 0) goto L_0x01de
            java.lang.String r4 = "_ep"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x01de
            r4 = 1
            goto L_0x01df
        L_0x01de:
            r4 = 0
        L_0x01df:
            if (r4 == 0) goto L_0x0345
            r67.zzjr()
            java.lang.String r4 = "_en"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzft.zzb(r14, r4)
            r8 = r4
            java.lang.String r8 = (java.lang.String) r8
            boolean r4 = android.text.TextUtils.isEmpty(r8)
            if (r4 == 0) goto L_0x0210
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()
            java.lang.String r4 = "Extra parameter without an event name. eventId"
            r2.zzg(r4, r3)
            r36 = r0
            r41 = r5
            r43 = r6
            r40 = r24
            r24 = r31
            r42 = r35
            r31 = 1
            goto L_0x0334
        L_0x0210:
            if (r0 == 0) goto L_0x0225
            if (r1 == 0) goto L_0x0225
            long r36 = r3.longValue()
            long r38 = r1.longValue()
            int r4 = (r36 > r38 ? 1 : (r36 == r38 ? 0 : -1))
            if (r4 == 0) goto L_0x0221
            goto L_0x0225
        L_0x0221:
            r4 = r0
            r34 = r1
            goto L_0x024d
        L_0x0225:
            com.google.android.gms.measurement.internal.zzt r4 = r67.zzjt()
            android.util.Pair r4 = r4.zza(r15, r3)
            r36 = r0
            if (r4 == 0) goto L_0x031b
            java.lang.Object r0 = r4.first
            if (r0 != 0) goto L_0x0237
            goto L_0x031b
        L_0x0237:
            java.lang.Object r0 = r4.first
            com.google.android.gms.internal.measurement.zzft r0 = (com.google.android.gms.internal.measurement.zzft) r0
            java.lang.Object r1 = r4.second
            java.lang.Long r1 = (java.lang.Long) r1
            long r20 = r1.longValue()
            r67.zzjr()
            java.lang.Object r1 = com.google.android.gms.measurement.internal.zzft.zzb(r0, r2)
            java.lang.Long r1 = (java.lang.Long) r1
            goto L_0x0221
        L_0x024d:
            long r20 = r20 - r32
            int r0 = (r20 > r28 ? 1 : (r20 == r28 ? 0 : -1))
            if (r0 > 0) goto L_0x02a0
            com.google.android.gms.measurement.internal.zzt r1 = r67.zzjt()
            r1.zzaf()
            com.google.android.gms.measurement.internal.zzas r0 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            java.lang.String r2 = "Clearing complex main event info. appId"
            r0.zzg(r2, r15)
            android.database.sqlite.SQLiteDatabase r0 = r1.getWritableDatabase()     // Catch:{ SQLiteException -> 0x027e }
            java.lang.String r2 = "delete from main_event_params where app_id=?"
            r18 = r4
            r3 = 1
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x027c }
            r19 = 0
            r4[r19] = r15     // Catch:{ SQLiteException -> 0x027a }
            r0.execSQL(r2, r4)     // Catch:{ SQLiteException -> 0x027a }
            goto L_0x0291
        L_0x027a:
            r0 = move-exception
            goto L_0x0284
        L_0x027c:
            r0 = move-exception
            goto L_0x0282
        L_0x027e:
            r0 = move-exception
            r18 = r4
            r3 = 1
        L_0x0282:
            r19 = 0
        L_0x0284:
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()
            java.lang.String r2 = "Error clearing complex main event"
            r1.zzg(r2, r0)
        L_0x0291:
            r41 = r5
            r43 = r6
            r1 = r18
            r40 = r24
            r24 = r31
            r42 = r35
            r31 = 1
            goto L_0x02c0
        L_0x02a0:
            r18 = r4
            r4 = 1
            r19 = 0
            com.google.android.gms.measurement.internal.zzt r1 = r67.zzjt()
            r2 = r68
            r40 = r24
            r24 = r31
            r41 = r5
            r42 = r35
            r31 = 1
            r4 = r20
            r43 = r6
            r6 = r18
            r1.zza(r2, r3, r4, r6)
            r1 = r18
        L_0x02c0:
            com.google.android.gms.internal.measurement.zzfu[] r0 = r1.zzaxc
            int r0 = r0.length
            int r2 = r10.length
            int r0 = r0 + r2
            com.google.android.gms.internal.measurement.zzfu[] r0 = new com.google.android.gms.internal.measurement.zzfu[r0]
            com.google.android.gms.internal.measurement.zzfu[] r2 = r1.zzaxc
            int r3 = r2.length
            r4 = 0
            r5 = 0
        L_0x02cc:
            if (r4 >= r3) goto L_0x02e7
            r6 = r2[r4]
            r67.zzjr()
            r18 = r1
            java.lang.String r1 = r6.name
            com.google.android.gms.internal.measurement.zzfu r1 = com.google.android.gms.measurement.internal.zzft.zza(r14, r1)
            if (r1 != 0) goto L_0x02e2
            int r1 = r5 + 1
            r0[r5] = r6
            r5 = r1
        L_0x02e2:
            int r4 = r4 + 1
            r1 = r18
            goto L_0x02cc
        L_0x02e7:
            r18 = r1
            if (r5 <= 0) goto L_0x0307
            int r1 = r10.length
            r2 = 0
        L_0x02ed:
            if (r2 >= r1) goto L_0x02f9
            r3 = r10[r2]
            int r4 = r5 + 1
            r0[r5] = r3
            int r2 = r2 + 1
            r5 = r4
            goto L_0x02ed
        L_0x02f9:
            int r1 = r0.length
            if (r5 != r1) goto L_0x02fd
            goto L_0x0303
        L_0x02fd:
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r5)
            com.google.android.gms.internal.measurement.zzfu[] r0 = (com.google.android.gms.internal.measurement.zzfu[]) r0
        L_0x0303:
            r35 = r0
            r0 = r8
            goto L_0x0317
        L_0x0307:
            com.google.android.gms.measurement.internal.zzas r0 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r1 = "No unique parameters in main event. eventName"
            r0.zzg(r1, r8)
            r0 = r8
            r35 = r10
        L_0x0317:
            r36 = r18
            goto L_0x03a9
        L_0x031b:
            r41 = r5
            r43 = r6
            r40 = r24
            r24 = r31
            r42 = r35
            r31 = 1
            com.google.android.gms.measurement.internal.zzas r0 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()
            java.lang.String r2 = "Extra parameter without existing main event. eventName, eventId"
            r0.zze(r2, r8, r3)
        L_0x0334:
            r46 = r9
            r14 = r11
            r66 = r12
            r44 = r22
            r45 = r25
            r47 = r26
            r0 = r36
            r65 = r40
            goto L_0x07bf
        L_0x0345:
            r36 = r0
            r41 = r5
            r43 = r6
            r40 = r24
            r24 = r31
            r42 = r35
            r31 = 1
            if (r34 == 0) goto L_0x03a4
            r67.zzjr()
            java.lang.Long r0 = java.lang.Long.valueOf(r28)
            java.lang.String r1 = "_epc"
            java.lang.Object r1 = com.google.android.gms.measurement.internal.zzft.zzb(r14, r1)
            if (r1 != 0) goto L_0x0365
            goto L_0x0366
        L_0x0365:
            r0 = r1
        L_0x0366:
            java.lang.Long r0 = (java.lang.Long) r0
            long r17 = r0.longValue()
            int r0 = (r17 > r28 ? 1 : (r17 == r28 ? 0 : -1))
            if (r0 > 0) goto L_0x037f
            com.google.android.gms.measurement.internal.zzas r0 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r1 = "Complex event with zero extra param count. eventName"
            r0.zzg(r1, r8)
            r0 = r3
            goto L_0x038c
        L_0x037f:
            com.google.android.gms.measurement.internal.zzt r1 = r67.zzjt()
            r2 = r68
            r0 = r3
            r4 = r17
            r6 = r14
            r1.zza(r2, r3, r4, r6)
        L_0x038c:
            r34 = r0
            r0 = r8
            r35 = r10
            r36 = r14
            r37 = r17
            goto L_0x03ab
        L_0x0396:
            r36 = r0
            r42 = r4
            r41 = r5
            r43 = r6
            r40 = r24
            r24 = r31
            r31 = 1
        L_0x03a4:
            r34 = r1
            r0 = r8
            r35 = r10
        L_0x03a9:
            r37 = r20
        L_0x03ab:
            com.google.android.gms.measurement.internal.zzt r1 = r67.zzjt()
            java.lang.String r2 = r14.name
            com.google.android.gms.measurement.internal.zzac r1 = r1.zzg(r15, r2)
            if (r1 != 0) goto L_0x040c
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjj()
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)
            com.google.android.gms.measurement.internal.zzaq r3 = r67.zzgq()
            java.lang.String r3 = r3.zzbt(r0)
            java.lang.String r4 = "Event aggregate wasn't created during raw event logging. appId, event"
            r1.zze(r4, r2, r3)
            com.google.android.gms.measurement.internal.zzac r1 = new com.google.android.gms.measurement.internal.zzac
            java.lang.String r10 = r14.name
            r2 = 1
            r4 = 1
            java.lang.Long r6 = r14.zzaxd
            long r17 = r6.longValue()
            r19 = 0
            r6 = 0
            r21 = 0
            r32 = 0
            r33 = 0
            r44 = r22
            r8 = r1
            r46 = r9
            r45 = r25
            r9 = r68
            r47 = r26
            r48 = r11
            r49 = r12
            r11 = r2
            r3 = r70
            r2 = r14
            r13 = r4
            r5 = r15
            r15 = r17
            r17 = r19
            r19 = r6
            r20 = r21
            r21 = r32
            r22 = r33
            r8.<init>(r9, r10, r11, r13, r15, r17, r19, r20, r21, r22)
            goto L_0x044c
        L_0x040c:
            r3 = r70
            r46 = r9
            r48 = r11
            r49 = r12
            r2 = r14
            r5 = r15
            r44 = r22
            r45 = r25
            r47 = r26
            com.google.android.gms.measurement.internal.zzac r4 = new com.google.android.gms.measurement.internal.zzac
            java.lang.String r6 = r1.zztt
            java.lang.String r8 = r1.name
            long r9 = r1.zzahv
            long r53 = r9 + r32
            long r9 = r1.zzahw
            long r55 = r9 + r32
            long r9 = r1.zzahx
            long r11 = r1.zzahy
            java.lang.Long r13 = r1.zzahz
            java.lang.Long r14 = r1.zzaia
            java.lang.Long r15 = r1.zzaib
            java.lang.Boolean r1 = r1.zzaic
            r50 = r4
            r51 = r6
            r52 = r8
            r57 = r9
            r59 = r11
            r61 = r13
            r62 = r14
            r63 = r15
            r64 = r1
            r50.<init>(r51, r52, r53, r55, r57, r59, r61, r62, r63, r64)
            r1 = r4
        L_0x044c:
            com.google.android.gms.measurement.internal.zzt r4 = r67.zzjt()
            r4.zza(r1)
            long r8 = r1.zzahv
            r10 = r41
            java.lang.Object r1 = r10.get(r0)
            java.util.Map r1 = (java.util.Map) r1
            if (r1 != 0) goto L_0x0471
            com.google.android.gms.measurement.internal.zzt r1 = r67.zzjt()
            java.util.Map r1 = r1.zzl(r5, r0)
            if (r1 != 0) goto L_0x046e
            androidx.collection.ArrayMap r1 = new androidx.collection.ArrayMap
            r1.<init>()
        L_0x046e:
            r10.put(r0, r1)
        L_0x0471:
            r11 = r1
            java.util.Set r1 = r11.keySet()
            java.util.Iterator r12 = r1.iterator()
        L_0x047a:
            boolean r1 = r12.hasNext()
            if (r1 == 0) goto L_0x07b1
            java.lang.Object r1 = r12.next()
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r13 = r1.intValue()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r14 = r48
            boolean r1 = r14.contains(r1)
            if (r1 == 0) goto L_0x04aa
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            r15 = r42
            r1.zzg(r15, r4)
            r48 = r14
            goto L_0x047a
        L_0x04aa:
            r15 = r42
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r6 = r49
            java.lang.Object r1 = r6.get(r1)
            com.google.android.gms.internal.measurement.zzfr r1 = (com.google.android.gms.internal.measurement.zzfr) r1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            r41 = r10
            r10 = r47
            java.lang.Object r4 = r10.get(r4)
            java.util.BitSet r4 = (java.util.BitSet) r4
            r16 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            r17 = r12
            r12 = r45
            java.lang.Object r2 = r12.get(r2)
            java.util.BitSet r2 = (java.util.BitSet) r2
            r18 = r2
            if (r23 == 0) goto L_0x04f7
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            r7 = r44
            java.lang.Object r2 = r7.get(r2)
            java.util.Map r2 = (java.util.Map) r2
            r19 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            r42 = r15
            r15 = r43
            java.lang.Object r2 = r15.get(r2)
            java.util.Map r2 = (java.util.Map) r2
            goto L_0x0500
        L_0x04f7:
            r42 = r15
            r15 = r43
            r7 = r44
            r2 = 0
            r19 = 0
        L_0x0500:
            if (r1 != 0) goto L_0x0567
            com.google.android.gms.internal.measurement.zzfr r1 = new com.google.android.gms.internal.measurement.zzfr
            r1.<init>()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            r6.put(r4, r1)
            r4 = r40
            r1.zzawx = r4
            java.util.BitSet r1 = new java.util.BitSet
            r1.<init>()
            r20 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            r10.put(r2, r1)
            java.util.BitSet r2 = new java.util.BitSet
            r2.<init>()
            r18 = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r12.put(r1, r2)
            if (r23 == 0) goto L_0x0557
            androidx.collection.ArrayMap r1 = new androidx.collection.ArrayMap
            r1.<init>()
            r21 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            r7.put(r2, r1)
            androidx.collection.ArrayMap r2 = new androidx.collection.ArrayMap
            r2.<init>()
            r19 = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r15.put(r1, r2)
            r44 = r7
            r43 = r15
            r15 = r19
            r7 = r2
            r2 = r4
            r4 = r18
            goto L_0x0564
        L_0x0557:
            r21 = r2
            r2 = r4
            r44 = r7
            r43 = r15
            r4 = r18
            r15 = r19
            r7 = r20
        L_0x0564:
            r18 = r21
            goto L_0x0573
        L_0x0567:
            r20 = r2
            r2 = r40
            r44 = r7
            r43 = r15
            r15 = r19
            r7 = r20
        L_0x0573:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            java.lang.Object r1 = r11.get(r1)
            java.util.List r1 = (java.util.List) r1
            java.util.Iterator r19 = r1.iterator()
        L_0x0581:
            boolean r1 = r19.hasNext()
            if (r1 == 0) goto L_0x0794
            java.lang.Object r1 = r19.next()
            com.google.android.gms.internal.measurement.zzfj r1 = (com.google.android.gms.internal.measurement.zzfj) r1
            r40 = r2
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            r20 = r11
            r11 = 2
            boolean r2 = r2.isLoggable(r11)
            if (r2 == 0) goto L_0x05d1
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjo()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r13)
            java.lang.Integer r3 = r1.zzavm
            com.google.android.gms.measurement.internal.zzaq r5 = r67.zzgq()
            r49 = r6
            java.lang.String r6 = r1.zzavn
            java.lang.String r5 = r5.zzbt(r6)
            java.lang.String r6 = "Evaluating filter. audience, filter, event"
            r2.zzd(r6, r11, r3, r5)
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjo()
            com.google.android.gms.measurement.internal.zzft r3 = r67.zzjr()
            java.lang.String r3 = r3.zza(r1)
            r11 = r46
            r2.zzg(r11, r3)
            goto L_0x05d5
        L_0x05d1:
            r49 = r6
            r11 = r46
        L_0x05d5:
            java.lang.Integer r2 = r1.zzavm
            if (r2 == 0) goto L_0x0750
            java.lang.Integer r2 = r1.zzavm
            int r2 = r2.intValue()
            r5 = 256(0x100, float:3.59E-43)
            if (r2 <= r5) goto L_0x05e5
            goto L_0x0750
        L_0x05e5:
            java.lang.String r6 = "Event filter result"
            if (r23 == 0) goto L_0x06c3
            if (r1 == 0) goto L_0x05fa
            java.lang.Boolean r2 = r1.zzavj
            if (r2 == 0) goto L_0x05fa
            java.lang.Boolean r2 = r1.zzavj
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x05fa
            r21 = 1
            goto L_0x05fc
        L_0x05fa:
            r21 = 0
        L_0x05fc:
            if (r1 == 0) goto L_0x060d
            java.lang.Boolean r2 = r1.zzavk
            if (r2 == 0) goto L_0x060d
            java.lang.Boolean r2 = r1.zzavk
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x060d
            r22 = 1
            goto L_0x060f
        L_0x060d:
            r22 = 0
        L_0x060f:
            java.lang.Integer r2 = r1.zzavm
            int r2 = r2.intValue()
            boolean r2 = r4.get(r2)
            if (r2 == 0) goto L_0x0640
            if (r21 != 0) goto L_0x0640
            if (r22 != 0) goto L_0x0640
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjo()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r13)
            java.lang.Integer r1 = r1.zzavm
            java.lang.String r6 = "Event filter already evaluated true and it is not associated with a dynamic audience. audience ID, filter ID"
            r2.zze(r6, r3, r1)
            r5 = r68
            r3 = r70
            r46 = r11
            r11 = r20
            r2 = r40
            r6 = r49
            goto L_0x0581
        L_0x0640:
            r3 = r1
            r2 = r18
            r1 = r67
            r46 = r11
            r45 = r12
            r11 = r16
            r65 = r40
            r12 = r2
            r2 = r3
            r47 = r10
            r10 = r3
            r3 = r0
            r16 = r0
            r0 = r4
            r4 = r35
            r18 = r15
            r66 = r49
            r15 = r6
            r5 = r8
            java.lang.Boolean r1 = r1.zza(r2, r3, r4, r5)
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjo()
            if (r1 != 0) goto L_0x066f
            r3 = r27
            goto L_0x0670
        L_0x066f:
            r3 = r1
        L_0x0670:
            r2.zzg(r15, r3)
            if (r1 != 0) goto L_0x067e
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r14.add(r1)
            goto L_0x077b
        L_0x067e:
            java.lang.Integer r2 = r10.zzavm
            int r2 = r2.intValue()
            r12.set(r2)
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x077b
            java.lang.Integer r1 = r10.zzavm
            int r1 = r1.intValue()
            r0.set(r1)
            if (r21 != 0) goto L_0x069a
            if (r22 == 0) goto L_0x077b
        L_0x069a:
            java.lang.Long r1 = r11.zzaxd
            if (r1 == 0) goto L_0x077b
            if (r22 == 0) goto L_0x06b1
            java.lang.Integer r1 = r10.zzavm
            int r1 = r1.intValue()
            java.lang.Long r2 = r11.zzaxd
            long r2 = r2.longValue()
            zzb(r7, r1, r2)
            goto L_0x077b
        L_0x06b1:
            java.lang.Integer r1 = r10.zzavm
            int r1 = r1.intValue()
            java.lang.Long r2 = r11.zzaxd
            long r2 = r2.longValue()
            r5 = r18
            zza(r5, r1, r2)
            goto L_0x06f6
        L_0x06c3:
            r47 = r10
            r46 = r11
            r45 = r12
            r5 = r15
            r11 = r16
            r12 = r18
            r65 = r40
            r66 = r49
            r16 = r0
            r10 = r1
            r0 = r4
            r15 = r6
            java.lang.Integer r1 = r10.zzavm
            int r1 = r1.intValue()
            boolean r1 = r0.get(r1)
            if (r1 == 0) goto L_0x070e
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            java.lang.Integer r3 = r10.zzavm
            java.lang.String r4 = "Event filter already evaluated true. audience ID, filter ID"
            r1.zze(r4, r2, r3)
        L_0x06f6:
            r3 = r70
            r4 = r0
            r15 = r5
            r18 = r12
            r0 = r16
            r12 = r45
            r10 = r47
            r2 = r65
            r6 = r66
            r5 = r68
            r16 = r11
            r11 = r20
            goto L_0x0581
        L_0x070e:
            r1 = r67
            r2 = r10
            r3 = r16
            r4 = r35
            r18 = r5
            r5 = r8
            java.lang.Boolean r1 = r1.zza(r2, r3, r4, r5)
            com.google.android.gms.measurement.internal.zzas r2 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjo()
            if (r1 != 0) goto L_0x0729
            r3 = r27
            goto L_0x072a
        L_0x0729:
            r3 = r1
        L_0x072a:
            r2.zzg(r15, r3)
            if (r1 != 0) goto L_0x0737
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r14.add(r1)
            goto L_0x077b
        L_0x0737:
            java.lang.Integer r2 = r10.zzavm
            int r2 = r2.intValue()
            r12.set(r2)
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x077b
            java.lang.Integer r1 = r10.zzavm
            int r1 = r1.intValue()
            r0.set(r1)
            goto L_0x077b
        L_0x0750:
            r47 = r10
            r46 = r11
            r45 = r12
            r11 = r16
            r12 = r18
            r65 = r40
            r66 = r49
            r16 = r0
            r10 = r1
            r0 = r4
            r18 = r15
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjj()
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)
            java.lang.Integer r3 = r10.zzavm
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = "Invalid event filter ID. appId, id"
            r1.zze(r4, r2, r3)
        L_0x077b:
            r5 = r68
            r3 = r70
            r4 = r0
            r0 = r16
            r15 = r18
            r10 = r47
            r2 = r65
            r6 = r66
            r16 = r11
            r18 = r12
            r11 = r20
            r12 = r45
            goto L_0x0581
        L_0x0794:
            r20 = r11
            r11 = r16
            r7 = r67
            r5 = r68
            r3 = r70
            r40 = r2
            r49 = r6
            r47 = r10
            r2 = r11
            r45 = r12
            r48 = r14
            r12 = r17
            r11 = r20
            r10 = r41
            goto L_0x047a
        L_0x07b1:
            r41 = r10
            r65 = r40
            r14 = r48
            r66 = r49
            r1 = r34
            r0 = r36
            r20 = r37
        L_0x07bf:
            int r2 = r30 + 1
            r7 = r67
            r15 = r68
            r13 = r69
            r11 = r14
            r3 = r24
            r5 = r41
            r4 = r42
            r6 = r43
            r22 = r44
            r25 = r45
            r9 = r46
            r26 = r47
            r24 = r65
            r12 = r66
            r14 = r70
            goto L_0x01a4
        L_0x07e0:
            r42 = r4
            r43 = r6
            r46 = r9
            r14 = r11
            r66 = r12
            r44 = r22
            r65 = r24
            r45 = r25
            r47 = r26
            r31 = 1
            r1 = r70
            if (r1 == 0) goto L_0x0b2c
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            int r2 = r1.length
            r3 = 0
        L_0x07fe:
            if (r3 >= r2) goto L_0x0b2c
            r4 = r1[r3]
            java.lang.String r5 = r4.name
            java.lang.Object r5 = r0.get(r5)
            java.util.Map r5 = (java.util.Map) r5
            if (r5 != 0) goto L_0x0825
            com.google.android.gms.measurement.internal.zzt r5 = r67.zzjt()
            java.lang.String r6 = r4.name
            r7 = r68
            java.util.Map r5 = r5.zzm(r7, r6)
            if (r5 != 0) goto L_0x081f
            androidx.collection.ArrayMap r5 = new androidx.collection.ArrayMap
            r5.<init>()
        L_0x081f:
            java.lang.String r6 = r4.name
            r0.put(r6, r5)
            goto L_0x0827
        L_0x0825:
            r7 = r68
        L_0x0827:
            java.util.Set r6 = r5.keySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x082f:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x0b1a
            java.lang.Object r8 = r6.next()
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            boolean r9 = r14.contains(r9)
            if (r9 == 0) goto L_0x085b
            com.google.android.gms.measurement.internal.zzas r9 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjo()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r10 = r42
            r9.zzg(r10, r8)
            goto L_0x082f
        L_0x085b:
            r10 = r42
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            r11 = r66
            java.lang.Object r9 = r11.get(r9)
            com.google.android.gms.internal.measurement.zzfr r9 = (com.google.android.gms.internal.measurement.zzfr) r9
            java.lang.Integer r12 = java.lang.Integer.valueOf(r8)
            r13 = r47
            java.lang.Object r12 = r13.get(r12)
            java.util.BitSet r12 = (java.util.BitSet) r12
            java.lang.Integer r15 = java.lang.Integer.valueOf(r8)
            r1 = r45
            java.lang.Object r15 = r1.get(r15)
            java.util.BitSet r15 = (java.util.BitSet) r15
            r69 = r0
            if (r23 == 0) goto L_0x08a4
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)
            r16 = r2
            r2 = r44
            java.lang.Object r0 = r2.get(r0)
            java.util.Map r0 = (java.util.Map) r0
            r17 = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)
            r18 = r6
            r6 = r43
            java.lang.Object r0 = r6.get(r0)
            java.util.Map r0 = (java.util.Map) r0
            goto L_0x08af
        L_0x08a4:
            r16 = r2
            r18 = r6
            r6 = r43
            r2 = r44
            r0 = 0
            r17 = 0
        L_0x08af:
            if (r9 != 0) goto L_0x090e
            com.google.android.gms.internal.measurement.zzfr r9 = new com.google.android.gms.internal.measurement.zzfr
            r9.<init>()
            java.lang.Integer r12 = java.lang.Integer.valueOf(r8)
            r11.put(r12, r9)
            r12 = r65
            r9.zzawx = r12
            java.util.BitSet r9 = new java.util.BitSet
            r9.<init>()
            java.lang.Integer r15 = java.lang.Integer.valueOf(r8)
            r13.put(r15, r9)
            java.util.BitSet r15 = new java.util.BitSet
            r15.<init>()
            r19 = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)
            r1.put(r0, r15)
            if (r23 == 0) goto L_0x0901
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            r20 = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            r2.put(r9, r0)
            androidx.collection.ArrayMap r9 = new androidx.collection.ArrayMap
            r9.<init>()
            r17 = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)
            r6.put(r0, r9)
            r44 = r2
            r2 = r9
            r40 = r12
            r0 = r17
            goto L_0x090b
        L_0x0901:
            r20 = r9
            r44 = r2
            r40 = r12
            r0 = r17
            r2 = r19
        L_0x090b:
            r12 = r20
            goto L_0x0918
        L_0x090e:
            r19 = r0
            r44 = r2
            r0 = r17
            r2 = r19
            r40 = r65
        L_0x0918:
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            java.lang.Object r9 = r5.get(r9)
            java.util.List r9 = (java.util.List) r9
            java.util.Iterator r9 = r9.iterator()
        L_0x0926:
            boolean r17 = r9.hasNext()
            if (r17 == 0) goto L_0x0b00
            java.lang.Object r17 = r9.next()
            r19 = r5
            r5 = r17
            com.google.android.gms.internal.measurement.zzfm r5 = (com.google.android.gms.internal.measurement.zzfm) r5
            r17 = r9
            com.google.android.gms.measurement.internal.zzas r9 = r67.zzgt()
            r42 = r10
            r10 = 2
            boolean r9 = r9.isLoggable(r10)
            if (r9 == 0) goto L_0x097c
            com.google.android.gms.measurement.internal.zzas r9 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjo()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r7 = r5.zzavm
            r43 = r6
            com.google.android.gms.measurement.internal.zzaq r6 = r67.zzgq()
            r45 = r1
            java.lang.String r1 = r5.zzawc
            java.lang.String r1 = r6.zzbv(r1)
            java.lang.String r6 = "Evaluating filter. audience, filter, property"
            r9.zzd(r6, r10, r7, r1)
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            com.google.android.gms.measurement.internal.zzft r6 = r67.zzjr()
            java.lang.String r6 = r6.zza(r5)
            r7 = r46
            r1.zzg(r7, r6)
            goto L_0x0982
        L_0x097c:
            r45 = r1
            r43 = r6
            r7 = r46
        L_0x0982:
            java.lang.Integer r1 = r5.zzavm
            if (r1 == 0) goto L_0x0ac8
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            r6 = 256(0x100, float:3.59E-43)
            if (r1 <= r6) goto L_0x0992
            goto L_0x0ac8
        L_0x0992:
            java.lang.String r1 = "Property filter result"
            if (r23 == 0) goto L_0x0a5b
            if (r5 == 0) goto L_0x09a6
            java.lang.Boolean r9 = r5.zzavj
            if (r9 == 0) goto L_0x09a6
            java.lang.Boolean r9 = r5.zzavj
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x09a6
            r9 = 1
            goto L_0x09a7
        L_0x09a6:
            r9 = 0
        L_0x09a7:
            if (r5 == 0) goto L_0x09b7
            java.lang.Boolean r10 = r5.zzavk
            if (r10 == 0) goto L_0x09b7
            java.lang.Boolean r10 = r5.zzavk
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x09b7
            r10 = 1
            goto L_0x09b8
        L_0x09b7:
            r10 = 0
        L_0x09b8:
            java.lang.Integer r6 = r5.zzavm
            int r6 = r6.intValue()
            boolean r6 = r12.get(r6)
            if (r6 == 0) goto L_0x09eb
            if (r9 != 0) goto L_0x09eb
            if (r10 != 0) goto L_0x09eb
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r5 = r5.zzavm
            java.lang.String r9 = "Property filter already evaluated true and it is not associated with a dynamic audience. audience ID, filter ID"
            r1.zze(r9, r6, r5)
            r46 = r7
            r9 = r17
            r5 = r19
            r10 = r42
            r6 = r43
            r1 = r45
            r7 = r68
            goto L_0x0926
        L_0x09eb:
            r6 = r67
            r46 = r7
            r7 = r44
            java.lang.Boolean r20 = r6.zza(r5, r4)
            com.google.android.gms.measurement.internal.zzas r21 = r67.zzgt()
            r44 = r7
            com.google.android.gms.measurement.internal.zzau r7 = r21.zzjo()
            r49 = r11
            if (r20 != 0) goto L_0x0a06
            r11 = r27
            goto L_0x0a08
        L_0x0a06:
            r11 = r20
        L_0x0a08:
            r7.zzg(r1, r11)
            if (r20 != 0) goto L_0x0a15
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            r14.add(r1)
            goto L_0x0a80
        L_0x0a15:
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            r15.set(r1)
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            boolean r7 = r20.booleanValue()
            r12.set(r1, r7)
            boolean r1 = r20.booleanValue()
            if (r1 == 0) goto L_0x0a80
            if (r9 != 0) goto L_0x0a35
            if (r10 == 0) goto L_0x0a80
        L_0x0a35:
            java.lang.Long r1 = r4.zzayw
            if (r1 == 0) goto L_0x0a80
            if (r10 == 0) goto L_0x0a4b
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            java.lang.Long r5 = r4.zzayw
            long r9 = r5.longValue()
            zzb(r2, r1, r9)
            goto L_0x0a80
        L_0x0a4b:
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            java.lang.Long r5 = r4.zzayw
            long r9 = r5.longValue()
            zza(r0, r1, r9)
            goto L_0x0a80
        L_0x0a5b:
            r6 = r67
            r46 = r7
            r49 = r11
            java.lang.Integer r7 = r5.zzavm
            int r7 = r7.intValue()
            boolean r7 = r12.get(r7)
            if (r7 == 0) goto L_0x0a90
            com.google.android.gms.measurement.internal.zzas r1 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r5 = r5.zzavm
            java.lang.String r9 = "Property filter already evaluated true. audience ID, filter ID"
            r1.zze(r9, r7, r5)
        L_0x0a80:
            r7 = r68
            r9 = r17
            r5 = r19
            r10 = r42
            r6 = r43
            r1 = r45
            r11 = r49
            goto L_0x0926
        L_0x0a90:
            java.lang.Boolean r7 = r6.zza(r5, r4)
            com.google.android.gms.measurement.internal.zzas r9 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjo()
            if (r7 != 0) goto L_0x0aa1
            r10 = r27
            goto L_0x0aa2
        L_0x0aa1:
            r10 = r7
        L_0x0aa2:
            r9.zzg(r1, r10)
            if (r7 != 0) goto L_0x0aaf
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            r14.add(r1)
            goto L_0x0a80
        L_0x0aaf:
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            r15.set(r1)
            boolean r1 = r7.booleanValue()
            if (r1 == 0) goto L_0x0a80
            java.lang.Integer r1 = r5.zzavm
            int r1 = r1.intValue()
            r12.set(r1)
            goto L_0x0a80
        L_0x0ac8:
            r6 = r67
            r46 = r7
            r49 = r11
            com.google.android.gms.measurement.internal.zzas r0 = r67.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.Object r1 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)
            java.lang.Integer r2 = r5.zzavm
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r5 = "Invalid property filter ID. appId, id"
            r0.zze(r5, r1, r2)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)
            r14.add(r0)
            r7 = r68
            r0 = r69
            r1 = r70
            r47 = r13
            r2 = r16
            r6 = r18
            r5 = r19
            r65 = r40
            r66 = r49
            goto L_0x082f
        L_0x0b00:
            r43 = r6
            r6 = r67
            r7 = r68
            r0 = r69
            r45 = r1
            r42 = r10
            r66 = r11
            r47 = r13
            r2 = r16
            r6 = r18
            r65 = r40
            r1 = r70
            goto L_0x082f
        L_0x0b1a:
            r6 = r67
            r69 = r0
            r16 = r2
            r13 = r47
            r40 = r65
            r49 = r66
            int r3 = r3 + 1
            r1 = r70
            goto L_0x07fe
        L_0x0b2c:
            r6 = r67
            r13 = r47
            r49 = r66
            int r0 = r13.size()
            com.google.android.gms.internal.measurement.zzfr[] r1 = new com.google.android.gms.internal.measurement.zzfr[r0]
            java.util.Set r0 = r13.keySet()
            java.util.Iterator r2 = r0.iterator()
            r0 = 0
        L_0x0b41:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0cee
            java.lang.Object r3 = r2.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r14.contains(r4)
            if (r4 != 0) goto L_0x0cea
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            r5 = r49
            java.lang.Object r4 = r5.get(r4)
            com.google.android.gms.internal.measurement.zzfr r4 = (com.google.android.gms.internal.measurement.zzfr) r4
            if (r4 != 0) goto L_0x0b6e
            com.google.android.gms.internal.measurement.zzfr r4 = new com.google.android.gms.internal.measurement.zzfr
            r4.<init>()
        L_0x0b6e:
            int r7 = r0 + 1
            r1[r0] = r4
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            r4.zzavg = r0
            com.google.android.gms.internal.measurement.zzfx r0 = new com.google.android.gms.internal.measurement.zzfx
            r0.<init>()
            r4.zzawv = r0
            com.google.android.gms.internal.measurement.zzfx r0 = r4.zzawv
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            java.lang.Object r8 = r13.get(r8)
            java.util.BitSet r8 = (java.util.BitSet) r8
            long[] r8 = com.google.android.gms.measurement.internal.zzft.zza(r8)
            r0.zzayq = r8
            com.google.android.gms.internal.measurement.zzfx r0 = r4.zzawv
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            r9 = r45
            java.lang.Object r8 = r9.get(r8)
            java.util.BitSet r8 = (java.util.BitSet) r8
            long[] r8 = com.google.android.gms.measurement.internal.zzft.zza(r8)
            r0.zzayp = r8
            if (r23 == 0) goto L_0x0c45
            com.google.android.gms.internal.measurement.zzfx r0 = r4.zzawv
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            r10 = r44
            java.lang.Object r8 = r10.get(r8)
            java.util.Map r8 = (java.util.Map) r8
            com.google.android.gms.internal.measurement.zzfs[] r8 = zzb(r8)
            r0.zzayr = r8
            com.google.android.gms.internal.measurement.zzfx r0 = r4.zzawv
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            r11 = r43
            java.lang.Object r8 = r11.get(r8)
            java.util.Map r8 = (java.util.Map) r8
            if (r8 != 0) goto L_0x0bd5
            r12 = 0
            com.google.android.gms.internal.measurement.zzfy[] r8 = new com.google.android.gms.internal.measurement.zzfy[r12]
            r69 = r2
            r49 = r5
            r15 = r8
            goto L_0x0c42
        L_0x0bd5:
            r12 = 0
            int r15 = r8.size()
            com.google.android.gms.internal.measurement.zzfy[] r15 = new com.google.android.gms.internal.measurement.zzfy[r15]
            java.util.Set r16 = r8.keySet()
            java.util.Iterator r16 = r16.iterator()
            r17 = 0
        L_0x0be6:
            boolean r18 = r16.hasNext()
            if (r18 == 0) goto L_0x0c3e
            java.lang.Object r18 = r16.next()
            r12 = r18
            java.lang.Integer r12 = (java.lang.Integer) r12
            r69 = r2
            com.google.android.gms.internal.measurement.zzfy r2 = new com.google.android.gms.internal.measurement.zzfy
            r2.<init>()
            r2.zzawz = r12
            java.lang.Object r12 = r8.get(r12)
            java.util.List r12 = (java.util.List) r12
            if (r12 == 0) goto L_0x0c30
            java.util.Collections.sort(r12)
            r49 = r5
            int r5 = r12.size()
            long[] r5 = new long[r5]
            java.util.Iterator r12 = r12.iterator()
            r18 = 0
        L_0x0c16:
            boolean r19 = r12.hasNext()
            if (r19 == 0) goto L_0x0c2d
            java.lang.Object r19 = r12.next()
            java.lang.Long r19 = (java.lang.Long) r19
            int r20 = r18 + 1
            long r21 = r19.longValue()
            r5[r18] = r21
            r18 = r20
            goto L_0x0c16
        L_0x0c2d:
            r2.zzayu = r5
            goto L_0x0c32
        L_0x0c30:
            r49 = r5
        L_0x0c32:
            int r5 = r17 + 1
            r15[r17] = r2
            r2 = r69
            r17 = r5
            r5 = r49
            r12 = 0
            goto L_0x0be6
        L_0x0c3e:
            r69 = r2
            r49 = r5
        L_0x0c42:
            r0.zzays = r15
            goto L_0x0c4d
        L_0x0c45:
            r69 = r2
            r49 = r5
            r11 = r43
            r10 = r44
        L_0x0c4d:
            com.google.android.gms.measurement.internal.zzt r2 = r67.zzjt()
            com.google.android.gms.internal.measurement.zzfx r0 = r4.zzawv
            r2.zzcl()
            r2.zzaf()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r68)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            int r4 = r0.zzvx()     // Catch:{ IOException -> 0x0cc9 }
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x0cc9 }
            int r5 = r4.length     // Catch:{ IOException -> 0x0cc9 }
            r8 = 0
            com.google.android.gms.internal.measurement.zzya r5 = com.google.android.gms.internal.measurement.zzya.zzk(r4, r8, r5)     // Catch:{ IOException -> 0x0cc5 }
            r0.zza(r5)     // Catch:{ IOException -> 0x0cc5 }
            r5.zzza()     // Catch:{ IOException -> 0x0cc5 }
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            java.lang.String r5 = "app_id"
            r12 = r68
            r0.put(r5, r12)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.String r5 = "audience_id"
            r0.put(r5, r3)
            java.lang.String r3 = "current_results"
            r0.put(r3, r4)
            android.database.sqlite.SQLiteDatabase r3 = r2.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0cb1 }
            java.lang.String r4 = "audience_filter_values"
            r5 = 5
            r15 = 0
            long r3 = r3.insertWithOnConflict(r4, r15, r0, r5)     // Catch:{ SQLiteException -> 0x0caf }
            r16 = -1
            int r0 = (r3 > r16 ? 1 : (r3 == r16 ? 0 : -1))
            if (r0 != 0) goto L_0x0cdf
            com.google.android.gms.measurement.internal.zzas r0 = r2.zzgt()     // Catch:{ SQLiteException -> 0x0caf }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ SQLiteException -> 0x0caf }
            java.lang.String r3 = "Failed to insert filter results (got -1). appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)     // Catch:{ SQLiteException -> 0x0caf }
            r0.zzg(r3, r4)     // Catch:{ SQLiteException -> 0x0caf }
            goto L_0x0cdf
        L_0x0caf:
            r0 = move-exception
            goto L_0x0cb3
        L_0x0cb1:
            r0 = move-exception
            r15 = 0
        L_0x0cb3:
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)
            java.lang.String r4 = "Error storing filter results. appId"
            r2.zze(r4, r3, r0)
            goto L_0x0cdf
        L_0x0cc5:
            r0 = move-exception
            r12 = r68
            goto L_0x0ccd
        L_0x0cc9:
            r0 = move-exception
            r12 = r68
            r8 = 0
        L_0x0ccd:
            r15 = 0
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r68)
            java.lang.String r4 = "Configuration loss. Failed to serialize filter results. appId"
            r2.zze(r4, r3, r0)
        L_0x0cdf:
            r2 = r69
            r0 = r7
            r45 = r9
            r44 = r10
            r43 = r11
            goto L_0x0b41
        L_0x0cea:
            r12 = r68
            goto L_0x0b41
        L_0x0cee:
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r1, r0)
            com.google.android.gms.internal.measurement.zzfr[] r0 = (com.google.android.gms.internal.measurement.zzfr[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzm.zza(java.lang.String, com.google.android.gms.internal.measurement.zzft[], com.google.android.gms.internal.measurement.zzfz[]):com.google.android.gms.internal.measurement.zzfr[]");
    }

    private final Boolean zza(zzfj zzfj, String str, zzfu[] zzfuArr, long j) {
        zzfk[] zzfkArr;
        zzfk[] zzfkArr2;
        Boolean bool;
        zzfl zzfl = zzfj.zzavq;
        Boolean valueOf = Boolean.valueOf(false);
        if (zzfl != null) {
            Boolean zza = zza(j, zzfj.zzavq);
            if (zza == null) {
                return null;
            }
            if (!zza.booleanValue()) {
                return valueOf;
            }
        }
        HashSet hashSet = new HashSet();
        for (zzfk zzfk : zzfj.zzavo) {
            if (TextUtils.isEmpty(zzfk.zzavv)) {
                zzgt().zzjj().zzg("null or empty param name in filter. event", zzgq().zzbt(str));
                return null;
            }
            hashSet.add(zzfk.zzavv);
        }
        ArrayMap arrayMap = new ArrayMap();
        for (zzfu zzfu : zzfuArr) {
            if (hashSet.contains(zzfu.name)) {
                if (zzfu.zzaxg != null) {
                    arrayMap.put(zzfu.name, zzfu.zzaxg);
                } else if (zzfu.zzaup != null) {
                    arrayMap.put(zzfu.name, zzfu.zzaup);
                } else if (zzfu.zzamn != null) {
                    arrayMap.put(zzfu.name, zzfu.zzamn);
                } else {
                    zzgt().zzjj().zze("Unknown value for param. event, param", zzgq().zzbt(str), zzgq().zzbu(zzfu.name));
                    return null;
                }
            }
        }
        for (zzfk zzfk2 : zzfj.zzavo) {
            boolean equals = Boolean.TRUE.equals(zzfk2.zzavu);
            String str2 = zzfk2.zzavv;
            if (TextUtils.isEmpty(str2)) {
                zzgt().zzjj().zzg("Event has empty param name. event", zzgq().zzbt(str));
                return null;
            }
            Object obj = arrayMap.get(str2);
            if (obj instanceof Long) {
                if (zzfk2.zzavt == null) {
                    zzgt().zzjj().zze("No number filter for long param. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                    return null;
                }
                Boolean zza2 = zza(((Long) obj).longValue(), zzfk2.zzavt);
                if (zza2 == null) {
                    return null;
                }
                if ((true ^ zza2.booleanValue()) ^ equals) {
                    return valueOf;
                }
            } else if (obj instanceof Double) {
                if (zzfk2.zzavt == null) {
                    zzgt().zzjj().zze("No number filter for double param. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                    return null;
                }
                Boolean zza3 = zza(((Double) obj).doubleValue(), zzfk2.zzavt);
                if (zza3 == null) {
                    return null;
                }
                if ((true ^ zza3.booleanValue()) ^ equals) {
                    return valueOf;
                }
            } else if (obj instanceof String) {
                if (zzfk2.zzavs != null) {
                    bool = zza((String) obj, zzfk2.zzavs);
                } else if (zzfk2.zzavt != null) {
                    String str3 = (String) obj;
                    if (zzft.zzcs(str3)) {
                        bool = zza(str3, zzfk2.zzavt);
                    } else {
                        zzgt().zzjj().zze("Invalid param value for number filter. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                        return null;
                    }
                } else {
                    zzgt().zzjj().zze("No filter for String param. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                    return null;
                }
                if (bool == null) {
                    return null;
                }
                if ((true ^ bool.booleanValue()) ^ equals) {
                    return valueOf;
                }
            } else if (obj == null) {
                zzgt().zzjo().zze("Missing param for filter. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                return valueOf;
            } else {
                zzgt().zzjj().zze("Unknown param type. event, param", zzgq().zzbt(str), zzgq().zzbu(str2));
                return null;
            }
        }
        return Boolean.valueOf(true);
    }

    private final Boolean zza(zzfm zzfm, zzfz zzfz) {
        zzfk zzfk = zzfm.zzawd;
        if (zzfk == null) {
            zzgt().zzjj().zzg("Missing property filter. property", zzgq().zzbv(zzfz.name));
            return null;
        }
        boolean equals = Boolean.TRUE.equals(zzfk.zzavu);
        if (zzfz.zzaxg != null) {
            if (zzfk.zzavt != null) {
                return zza(zza(zzfz.zzaxg.longValue(), zzfk.zzavt), equals);
            }
            zzgt().zzjj().zzg("No number filter for long property. property", zzgq().zzbv(zzfz.name));
            return null;
        } else if (zzfz.zzaup != null) {
            if (zzfk.zzavt != null) {
                return zza(zza(zzfz.zzaup.doubleValue(), zzfk.zzavt), equals);
            }
            zzgt().zzjj().zzg("No number filter for double property. property", zzgq().zzbv(zzfz.name));
            return null;
        } else if (zzfz.zzamn == null) {
            zzgt().zzjj().zzg("User property has no value, property", zzgq().zzbv(zzfz.name));
            return null;
        } else if (zzfk.zzavs != null) {
            return zza(zza(zzfz.zzamn, zzfk.zzavs), equals);
        } else {
            if (zzfk.zzavt == null) {
                zzgt().zzjj().zzg("No string or number filter defined. property", zzgq().zzbv(zzfz.name));
            } else if (zzft.zzcs(zzfz.zzamn)) {
                return zza(zza(zzfz.zzamn, zzfk.zzavt), equals);
            } else {
                zzgt().zzjj().zze("Invalid user property value for Numeric number filter. property, value", zzgq().zzbv(zzfz.name), zzfz.zzamn);
            }
            return null;
        }
    }

    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, zzfn zzfn) {
        String str2;
        List list;
        Preconditions.checkNotNull(zzfn);
        if (str == null || zzfn.zzawe == null || zzfn.zzawe.intValue() == 0) {
            return null;
        }
        if (zzfn.zzawe.intValue() == 6) {
            if (zzfn.zzawh == null || zzfn.zzawh.length == 0) {
                return null;
            }
        } else if (zzfn.zzawf == null) {
            return null;
        }
        int intValue = zzfn.zzawe.intValue();
        boolean z = zzfn.zzawg != null && zzfn.zzawg.booleanValue();
        if (z || intValue == 1 || intValue == 6) {
            str2 = zzfn.zzawf;
        } else {
            str2 = zzfn.zzawf.toUpperCase(Locale.ENGLISH);
        }
        String str3 = str2;
        if (zzfn.zzawh == null) {
            list = null;
        } else {
            String[] strArr = zzfn.zzawh;
            if (z) {
                list = Arrays.asList(strArr);
            } else {
                ArrayList arrayList = new ArrayList();
                for (String upperCase : strArr) {
                    arrayList.add(upperCase.toUpperCase(Locale.ENGLISH));
                }
                list = arrayList;
            }
        }
        return zza(str, intValue, z, str3, list, intValue == 1 ? str3 : null);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && i != 1) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException unused) {
                    zzgt().zzjj().zzg("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    private final Boolean zza(long j, zzfl zzfl) {
        try {
            return zza(new BigDecimal(j), zzfl, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(double d, zzfl zzfl) {
        try {
            return zza(new BigDecimal(d), zzfl, Math.ulp(d));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(String str, zzfl zzfl) {
        if (!zzft.zzcs(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzfl, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0071, code lost:
        if (r3 != null) goto L_0x0073;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean zza(java.math.BigDecimal r10, com.google.android.gms.internal.measurement.zzfl r11, double r12) {
        /*
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r11)
            java.lang.Integer r0 = r11.zzavw
            r1 = 0
            if (r0 == 0) goto L_0x00f3
            java.lang.Integer r0 = r11.zzavw
            int r0 = r0.intValue()
            if (r0 != 0) goto L_0x0012
            goto L_0x00f3
        L_0x0012:
            java.lang.Integer r0 = r11.zzavw
            int r0 = r0.intValue()
            r2 = 4
            if (r0 != r2) goto L_0x0024
            java.lang.String r0 = r11.zzavz
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = r11.zzawa
            if (r0 != 0) goto L_0x0029
        L_0x0023:
            return r1
        L_0x0024:
            java.lang.String r0 = r11.zzavy
            if (r0 != 0) goto L_0x0029
            return r1
        L_0x0029:
            java.lang.Integer r0 = r11.zzavw
            int r0 = r0.intValue()
            java.lang.Integer r3 = r11.zzavw
            int r3 = r3.intValue()
            if (r3 != r2) goto L_0x005a
            java.lang.String r3 = r11.zzavz
            boolean r3 = com.google.android.gms.measurement.internal.zzft.zzcs(r3)
            if (r3 == 0) goto L_0x0059
            java.lang.String r3 = r11.zzawa
            boolean r3 = com.google.android.gms.measurement.internal.zzft.zzcs(r3)
            if (r3 != 0) goto L_0x0048
            goto L_0x0059
        L_0x0048:
            java.math.BigDecimal r3 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0059 }
            java.lang.String r4 = r11.zzavz     // Catch:{ NumberFormatException -> 0x0059 }
            r3.<init>(r4)     // Catch:{ NumberFormatException -> 0x0059 }
            java.math.BigDecimal r4 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0059 }
            java.lang.String r11 = r11.zzawa     // Catch:{ NumberFormatException -> 0x0059 }
            r4.<init>(r11)     // Catch:{ NumberFormatException -> 0x0059 }
            r11 = r3
            r3 = r1
            goto L_0x006c
        L_0x0059:
            return r1
        L_0x005a:
            java.lang.String r3 = r11.zzavy
            boolean r3 = com.google.android.gms.measurement.internal.zzft.zzcs(r3)
            if (r3 != 0) goto L_0x0063
            return r1
        L_0x0063:
            java.math.BigDecimal r3 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x00f3 }
            java.lang.String r11 = r11.zzavy     // Catch:{ NumberFormatException -> 0x00f3 }
            r3.<init>(r11)     // Catch:{ NumberFormatException -> 0x00f3 }
            r11 = r1
            r4 = r11
        L_0x006c:
            if (r0 != r2) goto L_0x0071
            if (r11 != 0) goto L_0x0073
            return r1
        L_0x0071:
            if (r3 == 0) goto L_0x00f3
        L_0x0073:
            r5 = -1
            r6 = 0
            r7 = 1
            if (r0 == r7) goto L_0x00e7
            r8 = 2
            if (r0 == r8) goto L_0x00db
            r9 = 3
            if (r0 == r9) goto L_0x0093
            if (r0 == r2) goto L_0x0081
            goto L_0x00f3
        L_0x0081:
            int r11 = r10.compareTo(r11)
            if (r11 == r5) goto L_0x008e
            int r10 = r10.compareTo(r4)
            if (r10 == r7) goto L_0x008e
            r6 = 1
        L_0x008e:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r6)
            return r10
        L_0x0093:
            r0 = 0
            int r11 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r11 == 0) goto L_0x00cf
            java.math.BigDecimal r11 = new java.math.BigDecimal
            r11.<init>(r12)
            java.math.BigDecimal r0 = new java.math.BigDecimal
            r0.<init>(r8)
            java.math.BigDecimal r11 = r11.multiply(r0)
            java.math.BigDecimal r11 = r3.subtract(r11)
            int r11 = r10.compareTo(r11)
            if (r11 != r7) goto L_0x00ca
            java.math.BigDecimal r11 = new java.math.BigDecimal
            r11.<init>(r12)
            java.math.BigDecimal r12 = new java.math.BigDecimal
            r12.<init>(r8)
            java.math.BigDecimal r11 = r11.multiply(r12)
            java.math.BigDecimal r11 = r3.add(r11)
            int r10 = r10.compareTo(r11)
            if (r10 != r5) goto L_0x00ca
            r6 = 1
        L_0x00ca:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r6)
            return r10
        L_0x00cf:
            int r10 = r10.compareTo(r3)
            if (r10 != 0) goto L_0x00d6
            r6 = 1
        L_0x00d6:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r6)
            return r10
        L_0x00db:
            int r10 = r10.compareTo(r3)
            if (r10 != r7) goto L_0x00e2
            r6 = 1
        L_0x00e2:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r6)
            return r10
        L_0x00e7:
            int r10 = r10.compareTo(r3)
            if (r10 != r5) goto L_0x00ee
            r6 = 1
        L_0x00ee:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r6)
            return r10
        L_0x00f3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzm.zza(java.math.BigDecimal, com.google.android.gms.internal.measurement.zzfl, double):java.lang.Boolean");
    }

    private static zzfs[] zzb(Map<Integer, Long> map) {
        if (map == null) {
            return null;
        }
        int i = 0;
        zzfs[] zzfsArr = new zzfs[map.size()];
        for (Integer num : map.keySet()) {
            zzfs zzfs = new zzfs();
            zzfs.zzawz = num;
            zzfs.zzaxa = (Long) map.get(num);
            int i2 = i + 1;
            zzfsArr[i] = zzfs;
            i = i2;
        }
        return zzfsArr;
    }

    private static void zza(Map<Integer, Long> map, int i, long j) {
        Long l = (Long) map.get(Integer.valueOf(i));
        long j2 = j / 1000;
        if (l == null || j2 > l.longValue()) {
            map.put(Integer.valueOf(i), Long.valueOf(j2));
        }
    }

    private static void zzb(Map<Integer, List<Long>> map, int i, long j) {
        List list = (List) map.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList();
            map.put(Integer.valueOf(i), list);
        }
        list.add(Long.valueOf(j / 1000));
    }
}
