package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.message.TokenParser;

final class zzcx extends zzbq {

    /* renamed from: ID */
    private static final String f1560ID = zza.JOINER.toString();
    private static final String zzags = zzb.ARG0.toString();
    private static final String zzahl = zzb.ITEM_SEPARATOR.toString();
    private static final String zzahm = zzb.KEY_VALUE_SEPARATOR.toString();
    private static final String zzahn = zzb.ESCAPE.toString();

    public zzcx() {
        super(f1560ID, zzags);
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        zzl zzl = (zzl) map.get(zzags);
        if (zzl == null) {
            return zzgj.zzkc();
        }
        zzl zzl2 = (zzl) map.get(zzahl);
        String zzc = zzl2 != null ? zzgj.zzc(zzl2) : "";
        zzl zzl3 = (zzl) map.get(zzahm);
        String zzc2 = zzl3 != null ? zzgj.zzc(zzl3) : "=";
        int i = zzcz.zzahp;
        zzl zzl4 = (zzl) map.get(zzahn);
        HashSet hashSet = null;
        if (zzl4 != null) {
            String zzc3 = zzgj.zzc(zzl4);
            if ("url".equals(zzc3)) {
                i = zzcz.zzahq;
            } else if ("backslash".equals(zzc3)) {
                i = zzcz.zzahr;
                hashSet = new HashSet();
                zza(hashSet, zzc);
                zza(hashSet, zzc2);
                hashSet.remove(Character.valueOf(TokenParser.ESCAPE));
            } else {
                String str = "Joiner: unsupported escape type: ";
                String valueOf = String.valueOf(zzc3);
                zzdi.zzav(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                return zzgj.zzkc();
            }
        }
        StringBuilder sb = new StringBuilder();
        int i2 = zzl.type;
        if (i2 == 2) {
            zzl[] zzlArr = zzl.zzqn;
            int length = zzlArr.length;
            int i3 = 0;
            boolean z = true;
            while (i3 < length) {
                zzl zzl5 = zzlArr[i3];
                if (!z) {
                    sb.append(zzc);
                }
                zza(sb, zzgj.zzc(zzl5), i, hashSet);
                i3++;
                z = false;
            }
        } else if (i2 != 3) {
            zza(sb, zzgj.zzc(zzl), i, hashSet);
        } else {
            for (int i4 = 0; i4 < zzl.zzqo.length; i4++) {
                if (i4 > 0) {
                    sb.append(zzc);
                }
                String zzc4 = zzgj.zzc(zzl.zzqo[i4]);
                String zzc5 = zzgj.zzc(zzl.zzqp[i4]);
                zza(sb, zzc4, i, hashSet);
                sb.append(zzc2);
                zza(sb, zzc5, i, hashSet);
            }
        }
        return zzgj.zzi(sb.toString());
    }

    private static void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Integer, code=null, for r2v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(java.lang.StringBuilder r0, java.lang.String r1, java.lang.Integer r2, java.util.Set<java.lang.Character> r3) {
        /*
            java.lang.String r1 = zza(r1, r2, r3)
            r0.append(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcx.zza(java.lang.StringBuilder, java.lang.String, int, java.util.Set):void");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Integer, code=null, for r4v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zza(java.lang.String r3, java.lang.Integer r4, java.util.Set<java.lang.Character> r5) {
        /*
            int[] r0 = com.google.android.gms.tagmanager.zzcy.zzaho
            r1 = 1
            int r4 = r4 - r1
            r4 = r0[r4]
            if (r4 == r1) goto L_0x0042
            r0 = 2
            if (r4 == r0) goto L_0x000c
            return r3
        L_0x000c:
            java.lang.String r4 = "\\"
            java.lang.String r0 = "\\\\"
            java.lang.String r3 = r3.replace(r4, r0)
            java.util.Iterator r5 = r5.iterator()
        L_0x0018:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0041
            java.lang.Object r0 = r5.next()
            java.lang.Character r0 = (java.lang.Character) r0
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = java.lang.String.valueOf(r0)
            int r2 = r1.length()
            if (r2 == 0) goto L_0x0037
            java.lang.String r1 = r4.concat(r1)
            goto L_0x003c
        L_0x0037:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r4)
        L_0x003c:
            java.lang.String r3 = r3.replace(r0, r1)
            goto L_0x0018
        L_0x0041:
            return r3
        L_0x0042:
            java.lang.String r3 = com.google.android.gms.tagmanager.zzgn.zzbs(r3)     // Catch:{ UnsupportedEncodingException -> 0x0047 }
            return r3
        L_0x0047:
            r4 = move-exception
            java.lang.String r5 = "Joiner: unsupported encoding"
            com.google.android.gms.tagmanager.zzdi.zza(r5, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcx.zza(java.lang.String, int, java.util.Set):java.lang.String");
    }
}
