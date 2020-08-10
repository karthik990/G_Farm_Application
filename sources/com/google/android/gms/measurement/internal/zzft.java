package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.text.TextUtils;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzfj;
import com.google.android.gms.internal.measurement.zzfk;
import com.google.android.gms.internal.measurement.zzfl;
import com.google.android.gms.internal.measurement.zzfm;
import com.google.android.gms.internal.measurement.zzfn;
import com.google.android.gms.internal.measurement.zzfr;
import com.google.android.gms.internal.measurement.zzfs;
import com.google.android.gms.internal.measurement.zzfu;
import com.google.android.gms.internal.measurement.zzfv;
import com.google.android.gms.internal.measurement.zzfw;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzfy;
import com.google.android.gms.internal.measurement.zzfz;
import com.google.android.gms.internal.measurement.zzya;
import com.mobiroller.constants.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import lib.android.paypal.com.magnessdk.p100a.C5985b;
import p101me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

public final class zzft extends zzfm {
    zzft(zzfn zzfn) {
        super(zzfn);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfz zzfz, Object obj) {
        Preconditions.checkNotNull(obj);
        zzfz.zzamn = null;
        zzfz.zzaxg = null;
        zzfz.zzaup = null;
        if (obj instanceof String) {
            zzfz.zzamn = (String) obj;
        } else if (obj instanceof Long) {
            zzfz.zzaxg = (Long) obj;
        } else if (obj instanceof Double) {
            zzfz.zzaup = (Double) obj;
        } else {
            zzgt().zzjg().zzg("Ignoring invalid (type) user attribute value", obj);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfu zzfu, Object obj) {
        Preconditions.checkNotNull(obj);
        zzfu.zzamn = null;
        zzfu.zzaxg = null;
        zzfu.zzaup = null;
        if (obj instanceof String) {
            zzfu.zzamn = (String) obj;
        } else if (obj instanceof Long) {
            zzfu.zzaxg = (Long) obj;
        } else if (obj instanceof Double) {
            zzfu.zzaup = (Double) obj;
        } else {
            zzgt().zzjg().zzg("Ignoring invalid (type) event param value", obj);
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] zza(zzfv zzfv) {
        try {
            byte[] bArr = new byte[zzfv.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzfv.zza(zzk);
            zzk.zzza();
            return bArr;
        } catch (IOException e) {
            zzgt().zzjg().zzg("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    static zzfu zza(com.google.android.gms.internal.measurement.zzft zzft, String str) {
        zzfu[] zzfuArr;
        for (zzfu zzfu : zzft.zzaxc) {
            if (zzfu.name.equals(str)) {
                return zzfu;
            }
        }
        return null;
    }

    static Object zzb(com.google.android.gms.internal.measurement.zzft zzft, String str) {
        zzfu zza = zza(zzft, str);
        if (zza != null) {
            if (zza.zzamn != null) {
                return zza.zzamn;
            }
            if (zza.zzaxg != null) {
                return zza.zzaxg;
            }
            if (zza.zzaup != null) {
                return zza.zzaup;
            }
        }
        return null;
    }

    static zzfu[] zza(zzfu[] zzfuArr, String str, Object obj) {
        for (zzfu zzfu : zzfuArr) {
            if (str.equals(zzfu.name)) {
                zzfu.zzaxg = null;
                zzfu.zzamn = null;
                zzfu.zzaup = null;
                if (obj instanceof Long) {
                    zzfu.zzaxg = (Long) obj;
                } else if (obj instanceof String) {
                    zzfu.zzamn = (String) obj;
                } else if (obj instanceof Double) {
                    zzfu.zzaup = (Double) obj;
                }
                return zzfuArr;
            }
        }
        zzfu[] zzfuArr2 = new zzfu[(zzfuArr.length + 1)];
        System.arraycopy(zzfuArr, 0, zzfuArr2, 0, zzfuArr.length);
        zzfu zzfu2 = new zzfu();
        zzfu2.name = str;
        if (obj instanceof Long) {
            zzfu2.zzaxg = (Long) obj;
        } else if (obj instanceof String) {
            zzfu2.zzamn = (String) obj;
        } else if (obj instanceof Double) {
            zzfu2.zzaup = (Double) obj;
        }
        zzfuArr2[zzfuArr.length] = zzfu2;
        return zzfuArr2;
    }

    /* access modifiers changed from: 0000 */
    public final String zzb(zzfv zzfv) {
        zzfw[] zzfwArr;
        com.google.android.gms.internal.measurement.zzft[] zzftArr;
        String str;
        com.google.android.gms.internal.measurement.zzft[] zzftArr2;
        String str2;
        int i;
        int i2;
        zzfw[] zzfwArr2;
        String str3;
        zzfz[] zzfzArr;
        zzfv zzfv2 = zzfv;
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        String str4 = "}\n";
        if (zzfv2.zzaxh != null) {
            zzfw[] zzfwArr3 = zzfv2.zzaxh;
            int length = zzfwArr3.length;
            int i3 = 0;
            while (i3 < length) {
                zzfw zzfw = zzfwArr3[i3];
                if (zzfw == null || zzfw == null) {
                    zzfwArr = zzfwArr3;
                } else {
                    zza(sb, 1);
                    sb.append("bundle {\n");
                    zza(sb, 1, "protocol_version", (Object) zzfw.zzaxj);
                    zza(sb, 1, "platform", (Object) zzfw.zzaxr);
                    zza(sb, 1, "gmp_version", (Object) zzfw.zzaxv);
                    zza(sb, 1, "uploading_gmp_version", (Object) zzfw.zzaxw);
                    zza(sb, 1, "config_version", (Object) zzfw.zzayh);
                    zza(sb, 1, "gmp_app_id", (Object) zzfw.zzafi);
                    zza(sb, 1, "admob_app_id", (Object) zzfw.zzawr);
                    zza(sb, 1, "app_id", (Object) zzfw.zztt);
                    zza(sb, 1, "app_version", (Object) zzfw.zzts);
                    zza(sb, 1, "app_version_major", (Object) zzfw.zzayd);
                    zza(sb, 1, "firebase_instance_id", (Object) zzfw.zzafk);
                    zza(sb, 1, "dev_cert_hash", (Object) zzfw.zzaxz);
                    zza(sb, 1, "app_store", (Object) zzfw.zzafp);
                    zza(sb, 1, "upload_timestamp_millis", (Object) zzfw.zzaxm);
                    zza(sb, 1, "start_timestamp_millis", (Object) zzfw.zzaxn);
                    zza(sb, 1, "end_timestamp_millis", (Object) zzfw.zzaxo);
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", (Object) zzfw.zzaxp);
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", (Object) zzfw.zzaxq);
                    zza(sb, 1, "app_instance_id", (Object) zzfw.zzafh);
                    zza(sb, 1, "resettable_device_id", (Object) zzfw.zzaxx);
                    zza(sb, 1, "device_id", (Object) zzfw.zzayg);
                    zza(sb, 1, "ds_id", (Object) zzfw.zzayj);
                    zza(sb, 1, "limited_ad_tracking", (Object) zzfw.zzaxy);
                    zza(sb, 1, "os_version", (Object) zzfw.zzaxs);
                    zza(sb, 1, "device_model", (Object) zzfw.zzaxt);
                    zza(sb, 1, "user_default_language", (Object) zzfw.zzahr);
                    zza(sb, 1, "time_zone_offset_minutes", (Object) zzfw.zzaxu);
                    zza(sb, 1, "bundle_sequential_index", (Object) zzfw.zzaya);
                    zza(sb, 1, "service_upload", (Object) zzfw.zzayb);
                    zza(sb, 1, "health_monitor", (Object) zzfw.zzagm);
                    if (!(zzfw.zzayi == null || zzfw.zzayi.longValue() == 0)) {
                        zza(sb, 1, C5985b.f4233f, (Object) zzfw.zzayi);
                    }
                    if (zzfw.zzayl != null) {
                        zza(sb, 1, "retry_counter", (Object) zzfw.zzayl);
                    }
                    zzfz[] zzfzArr2 = zzfw.zzaxl;
                    String str5 = "double_value";
                    String str6 = "int_value";
                    String str7 = "string_value";
                    String str8 = PostalAddressParser.USER_ADDRESS_NAME_KEY;
                    int i4 = 2;
                    if (zzfzArr2 != null) {
                        int length2 = zzfzArr2.length;
                        int i5 = 0;
                        while (i5 < length2) {
                            zzfz zzfz = zzfzArr2[i5];
                            if (zzfz != null) {
                                zza(sb, 2);
                                sb.append("user_property {\n");
                                zzfzArr = zzfzArr2;
                                zza(sb, 2, "set_timestamp_millis", (Object) zzfz.zzayw);
                                zza(sb, 2, str8, (Object) zzgq().zzbv(zzfz.name));
                                zza(sb, 2, str7, (Object) zzfz.zzamn);
                                zza(sb, 2, str6, (Object) zzfz.zzaxg);
                                zza(sb, 2, str5, (Object) zzfz.zzaup);
                                zza(sb, 2);
                                sb.append(str4);
                            } else {
                                zzfzArr = zzfzArr2;
                            }
                            i5++;
                            zzfzArr2 = zzfzArr;
                        }
                    }
                    zzfr[] zzfrArr = zzfw.zzayc;
                    String str9 = zzfw.zztt;
                    if (zzfrArr != null) {
                        int length3 = zzfrArr.length;
                        int i6 = 0;
                        while (i6 < length3) {
                            zzfr zzfr = zzfrArr[i6];
                            if (zzfr != null) {
                                zza(sb, i4);
                                sb.append("audience_membership {\n");
                                i2 = i6;
                                i = length3;
                                zza(sb, 2, "audience_id", (Object) zzfr.zzavg);
                                zza(sb, 2, "new_audience", (Object) zzfr.zzawx);
                                zzfr zzfr2 = zzfr;
                                zzfx zzfx = zzfr.zzawv;
                                StringBuilder sb2 = sb;
                                str2 = str8;
                                zzfwArr2 = zzfwArr3;
                                str3 = str7;
                                String str10 = str9;
                                zza(sb2, 2, "current_data", zzfx, str10);
                                zza(sb2, 2, "previous_data", zzfr2.zzaww, str10);
                                zza(sb, 2);
                                sb.append(str4);
                            } else {
                                i2 = i6;
                                i = length3;
                                str2 = str8;
                                zzfwArr2 = zzfwArr3;
                                str3 = str7;
                            }
                            i6 = i2 + 1;
                            str7 = str3;
                            zzfwArr3 = zzfwArr2;
                            length3 = i;
                            str8 = str2;
                            i4 = 2;
                        }
                    }
                    String str11 = str8;
                    zzfwArr = zzfwArr3;
                    int i7 = 2;
                    String str12 = str7;
                    com.google.android.gms.internal.measurement.zzft[] zzftArr3 = zzfw.zzaxk;
                    if (zzftArr3 != null) {
                        int length4 = zzftArr3.length;
                        int i8 = 0;
                        while (i8 < length4) {
                            com.google.android.gms.internal.measurement.zzft zzft = zzftArr3[i8];
                            if (zzft != null) {
                                zza(sb, i7);
                                sb.append("event {\n");
                                str = str11;
                                zza(sb, i7, str, (Object) zzgq().zzbt(zzft.name));
                                zza(sb, i7, "timestamp_millis", (Object) zzft.zzaxd);
                                zza(sb, i7, "previous_timestamp_millis", (Object) zzft.zzaxe);
                                zza(sb, i7, NewHtcHomeBadger.COUNT, (Object) zzft.count);
                                zzfu[] zzfuArr = zzft.zzaxc;
                                if (zzfuArr != null) {
                                    int length5 = zzfuArr.length;
                                    int i9 = 0;
                                    while (i9 < length5) {
                                        zzfu zzfu = zzfuArr[i9];
                                        if (zzfu != null) {
                                            zza(sb, 3);
                                            sb.append("param {\n");
                                            zzftArr2 = zzftArr3;
                                            zza(sb, 3, str, (Object) zzgq().zzbu(zzfu.name));
                                            zza(sb, 3, str12, (Object) zzfu.zzamn);
                                            zza(sb, 3, str6, (Object) zzfu.zzaxg);
                                            zza(sb, 3, str5, (Object) zzfu.zzaup);
                                            zza(sb, 3);
                                            sb.append(str4);
                                        } else {
                                            zzftArr2 = zzftArr3;
                                        }
                                        i9++;
                                        zzftArr3 = zzftArr2;
                                        i7 = 2;
                                    }
                                }
                                zzftArr = zzftArr3;
                                zza(sb, i7);
                                sb.append(str4);
                            } else {
                                zzftArr = zzftArr3;
                                str = str11;
                            }
                            i8++;
                            str11 = str;
                            zzftArr3 = zzftArr;
                        }
                    }
                    zza(sb, 1);
                    sb.append(str4);
                }
                i3++;
                zzfwArr3 = zzfwArr;
            }
        }
        sb.append(str4);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String zza(zzfj zzfj) {
        if (zzfj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        zza(sb, 0, "filter_id", (Object) zzfj.zzavm);
        zza(sb, 0, "event_name", (Object) zzgq().zzbt(zzfj.zzavn));
        zza(sb, 1, "event_count_filter", zzfj.zzavq);
        sb.append("  filters {\n");
        for (zzfk zza : zzfj.zzavo) {
            zza(sb, 2, zza);
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String zza(zzfm zzfm) {
        if (zzfm == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        zza(sb, 0, "filter_id", (Object) zzfm.zzavm);
        zza(sb, 0, "property_name", (Object) zzgq().zzbv(zzfm.zzawc));
        zza(sb, 1, zzfm.zzawd);
        sb.append("}\n");
        return sb.toString();
    }

    private final void zza(StringBuilder sb, int i, String str, zzfx zzfx, String str2) {
        StringBuilder sb2 = sb;
        zzfx zzfx2 = zzfx;
        if (zzfx2 != null) {
            zza(sb2, 3);
            sb2.append(str);
            sb2.append(" {\n");
            String str3 = ", ";
            if (zzfx2.zzayq != null) {
                zza(sb2, 4);
                sb2.append("results: ");
                long[] jArr = zzfx2.zzayq;
                int length = jArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    Long valueOf = Long.valueOf(jArr[i2]);
                    int i4 = i3 + 1;
                    if (i3 != 0) {
                        sb2.append(str3);
                    }
                    sb2.append(valueOf);
                    i2++;
                    i3 = i4;
                }
                sb2.append(10);
            }
            if (zzfx2.zzayp != null) {
                zza(sb2, 4);
                sb2.append("status: ");
                long[] jArr2 = zzfx2.zzayp;
                int length2 = jArr2.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < length2) {
                    Long valueOf2 = Long.valueOf(jArr2[i5]);
                    int i7 = i6 + 1;
                    if (i6 != 0) {
                        sb2.append(str3);
                    }
                    sb2.append(valueOf2);
                    i5++;
                    i6 = i7;
                }
                sb2.append(10);
            }
            String str4 = "}\n";
            if (zzgv().zzbb(str2)) {
                if (zzfx2.zzayr != null) {
                    zza(sb2, 4);
                    sb2.append("dynamic_filter_timestamps: {");
                    zzfs[] zzfsArr = zzfx2.zzayr;
                    int length3 = zzfsArr.length;
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 < length3) {
                        zzfs zzfs = zzfsArr[i8];
                        int i10 = i9 + 1;
                        if (i9 != 0) {
                            sb2.append(str3);
                        }
                        sb2.append(zzfs.zzawz);
                        sb2.append(":");
                        sb2.append(zzfs.zzaxa);
                        i8++;
                        i9 = i10;
                    }
                    sb2.append(str4);
                }
                if (zzfx2.zzays != null) {
                    zza(sb2, 4);
                    sb2.append("sequence_filter_timestamps: {");
                    zzfy[] zzfyArr = zzfx2.zzays;
                    int length4 = zzfyArr.length;
                    int i11 = 0;
                    int i12 = 0;
                    while (i11 < length4) {
                        zzfy zzfy = zzfyArr[i11];
                        int i13 = i12 + 1;
                        if (i12 != 0) {
                            sb2.append(str3);
                        }
                        sb2.append(zzfy.zzawz);
                        sb2.append(": [");
                        long[] jArr3 = zzfy.zzayu;
                        int length5 = jArr3.length;
                        int i14 = 0;
                        int i15 = 0;
                        while (i14 < length5) {
                            long j = jArr3[i14];
                            int i16 = i15 + 1;
                            if (i15 != 0) {
                                sb2.append(str3);
                            }
                            sb2.append(j);
                            i14++;
                            i15 = i16;
                        }
                        sb2.append("]");
                        i11++;
                        i12 = i13;
                    }
                    sb2.append(str4);
                }
            }
            zza(sb2, 3);
            sb2.append(str4);
        }
    }

    private final void zza(StringBuilder sb, int i, String str, zzfl zzfl) {
        if (zzfl != null) {
            zza(sb, i);
            sb.append(str);
            sb.append(" {\n");
            if (zzfl.zzavw != null) {
                int intValue = zzfl.zzavw.intValue();
                String str2 = intValue != 1 ? intValue != 2 ? intValue != 3 ? intValue != 4 ? "UNKNOWN_COMPARISON_TYPE" : "BETWEEN" : "EQUAL" : "GREATER_THAN" : "LESS_THAN";
                zza(sb, i, "comparison_type", (Object) str2);
            }
            zza(sb, i, "match_as_float", (Object) zzfl.zzavx);
            zza(sb, i, "comparison_value", (Object) zzfl.zzavy);
            zza(sb, i, "min_comparison_value", (Object) zzfl.zzavz);
            zza(sb, i, "max_comparison_value", (Object) zzfl.zzawa);
            zza(sb, i);
            sb.append("}\n");
        }
    }

    private final void zza(StringBuilder sb, int i, zzfk zzfk) {
        String[] strArr;
        String str;
        if (zzfk != null) {
            zza(sb, i);
            sb.append("filter {\n");
            zza(sb, i, "complement", (Object) zzfk.zzavu);
            zza(sb, i, "param_name", (Object) zzgq().zzbu(zzfk.zzavv));
            int i2 = i + 1;
            zzfn zzfn = zzfk.zzavs;
            String str2 = "}\n";
            if (zzfn != null) {
                zza(sb, i2);
                sb.append("string_filter");
                sb.append(" {\n");
                if (zzfn.zzawe != null) {
                    switch (zzfn.zzawe.intValue()) {
                        case 1:
                            str = "REGEXP";
                            break;
                        case 2:
                            str = "BEGINS_WITH";
                            break;
                        case 3:
                            str = "ENDS_WITH";
                            break;
                        case 4:
                            str = "PARTIAL";
                            break;
                        case 5:
                            str = "EXACT";
                            break;
                        case 6:
                            str = "IN_LIST";
                            break;
                        default:
                            str = "UNKNOWN_MATCH_TYPE";
                            break;
                    }
                    zza(sb, i2, "match_type", (Object) str);
                }
                zza(sb, i2, "expression", (Object) zzfn.zzawf);
                zza(sb, i2, "case_sensitive", (Object) zzfn.zzawg);
                if (zzfn.zzawh.length > 0) {
                    zza(sb, i2 + 1);
                    sb.append("expression_list {\n");
                    for (String str3 : zzfn.zzawh) {
                        zza(sb, i2 + 2);
                        sb.append(str3);
                        sb.append(Constants.NEW_LINE);
                    }
                    sb.append(str2);
                }
                zza(sb, i2);
                sb.append(str2);
            }
            zza(sb, i2, "number_filter", zzfk.zzavt);
            zza(sb, i);
            sb.append(str2);
        }
    }

    private static void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private static void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj != null) {
            zza(sb, i + 1);
            sb.append(str);
            sb.append(": ");
            sb.append(obj);
            sb.append(10);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        zzgt().zzjg().zzby("Failed to load parcelable from buffer");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        r1.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x001c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T extends android.os.Parcelable> T zza(byte[] r5, android.os.Parcelable.Creator<T> r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            android.os.Parcel r1 = android.os.Parcel.obtain()
            int r2 = r5.length     // Catch:{ ParseException -> 0x001c }
            r3 = 0
            r1.unmarshall(r5, r3, r2)     // Catch:{ ParseException -> 0x001c }
            r1.setDataPosition(r3)     // Catch:{ ParseException -> 0x001c }
            java.lang.Object r5 = r6.createFromParcel(r1)     // Catch:{ ParseException -> 0x001c }
            android.os.Parcelable r5 = (android.os.Parcelable) r5     // Catch:{ ParseException -> 0x001c }
            r1.recycle()
            return r5
        L_0x001a:
            r5 = move-exception
            goto L_0x002d
        L_0x001c:
            com.google.android.gms.measurement.internal.zzas r5 = r4.zzgt()     // Catch:{ all -> 0x001a }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ all -> 0x001a }
            java.lang.String r6 = "Failed to load parcelable from buffer"
            r5.zzby(r6)     // Catch:{ all -> 0x001a }
            r1.recycle()
            return r0
        L_0x002d:
            r1.recycle()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzft.zza(byte[], android.os.Parcelable$Creator):android.os.Parcelable");
    }

    /* access modifiers changed from: 0000 */
    public final boolean zze(zzag zzag, zzk zzk) {
        Preconditions.checkNotNull(zzag);
        Preconditions.checkNotNull(zzk);
        if (!TextUtils.isEmpty(zzk.zzafi) || !TextUtils.isEmpty(zzk.zzafv)) {
            return true;
        }
        zzgw();
        return false;
    }

    static boolean zzcs(String str) {
        return str != null && str.matches("([+-])?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    static boolean zza(long[] jArr, int i) {
        if (i >= (jArr.length << 6)) {
            return false;
        }
        if (((1 << (i % 64)) & jArr[i / 64]) != 0) {
            return true;
        }
        return false;
    }

    static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i << 6) + i2;
                if (i3 >= bitSet.length()) {
                    break;
                }
                if (bitSet.get(i3)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
            }
        }
        return jArr;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzb(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzbx().currentTimeMillis() - j) > j2;
    }

    /* access modifiers changed from: 0000 */
    public final byte[] zza(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            zzgt().zzjg().zzg("Failed to ungzip content", e);
            throw e;
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] zzb(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzgt().zzjg().zzg("Failed to gzip content", e);
            throw e;
        }
    }

    /* access modifiers changed from: 0000 */
    public final int[] zzmi() {
        Map zzm = zzai.zzm(this.zzamx.getContext());
        if (zzm == null || zzm.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int intValue = ((Integer) zzai.zzakg.get()).intValue();
        Iterator it = zzm.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Entry entry = (Entry) it.next();
            if (((String) entry.getKey()).startsWith("measurement.id.")) {
                try {
                    int parseInt = Integer.parseInt((String) entry.getValue());
                    if (parseInt != 0) {
                        arrayList.add(Integer.valueOf(parseInt));
                        if (arrayList.size() >= intValue) {
                            zzgt().zzjj().zzg("Too many experiment IDs. Number of IDs", Integer.valueOf(arrayList.size()));
                            break;
                        }
                    } else {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    zzgt().zzjj().zzg("Experiment ID NumberFormatException", e);
                }
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            int i3 = i2 + 1;
            iArr[i2] = ((Integer) obj).intValue();
            i2 = i3;
        }
        return iArr;
    }

    public final /* bridge */ /* synthetic */ zzft zzjr() {
        return super.zzjr();
    }

    public final /* bridge */ /* synthetic */ zzm zzjs() {
        return super.zzjs();
    }

    public final /* bridge */ /* synthetic */ zzt zzjt() {
        return super.zzjt();
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
