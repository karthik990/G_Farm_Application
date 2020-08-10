package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.util.JsonWriter;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

@zzadh
public final class zzamy {
    private static Object sLock = new Object();
    private static boolean zzcuv = false;
    private static boolean zzcuw = false;
    private static Clock zzcux = DefaultClock.getInstance();
    private static final Set<String> zzcuy = new HashSet(Arrays.asList(new String[0]));
    private final List<String> zzcuz;

    public zzamy() {
        this(null);
    }

    public zzamy(String str) {
        List<String> list;
        if (!isEnabled()) {
            list = new ArrayList<>();
        } else {
            String uuid = UUID.randomUUID().toString();
            String str2 = "network_request_";
            if (str == null) {
                String[] strArr = new String[1];
                String valueOf = String.valueOf(uuid);
                strArr[0] = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
                list = Arrays.asList(strArr);
            } else {
                String[] strArr2 = new String[2];
                String str3 = "ad_request_";
                String valueOf2 = String.valueOf(str);
                strArr2[0] = valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3);
                String valueOf3 = String.valueOf(uuid);
                strArr2[1] = valueOf3.length() != 0 ? str2.concat(valueOf3) : new String(str2);
                list = Arrays.asList(strArr2);
            }
        }
        this.zzcuz = list;
    }

    public static boolean isEnabled() {
        boolean z;
        synchronized (sLock) {
            z = zzcuv && zzcuw;
        }
        return z;
    }

    static final /* synthetic */ void zza(int i, Map map, JsonWriter jsonWriter) throws IOException {
        jsonWriter.name("params").beginObject();
        jsonWriter.name("firstline").beginObject();
        jsonWriter.name("code").value((long) i);
        jsonWriter.endObject();
        zza(jsonWriter, map);
        jsonWriter.endObject();
    }

    private static void zza(JsonWriter jsonWriter, Map<String, ?> map) throws IOException {
        if (map != null) {
            jsonWriter.name("headers").beginArray();
            Iterator it = map.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Entry entry = (Entry) it.next();
                String str = (String) entry.getKey();
                if (!zzcuy.contains(str)) {
                    boolean z = entry.getValue() instanceof List;
                    String str2 = Param.VALUE;
                    String str3 = PostalAddressParser.USER_ADDRESS_NAME_KEY;
                    if (!z) {
                        if (!(entry.getValue() instanceof String)) {
                            zzane.m1272e("Connection headers should be either Map<String, String> or Map<String, List<String>>");
                            break;
                        }
                        jsonWriter.beginObject();
                        jsonWriter.name(str3).value(str);
                        jsonWriter.name(str2).value((String) entry.getValue());
                        jsonWriter.endObject();
                    } else {
                        for (String str4 : (List) entry.getValue()) {
                            jsonWriter.beginObject();
                            jsonWriter.name(str3).value(str);
                            jsonWriter.name(str2).value(str4);
                            jsonWriter.endObject();
                        }
                    }
                }
            }
            jsonWriter.endArray();
        }
    }

    static final /* synthetic */ void zza(String str, JsonWriter jsonWriter) throws IOException {
        jsonWriter.name("params").beginObject();
        if (str != null) {
            jsonWriter.name("error_description").value(str);
        }
        jsonWriter.endObject();
    }

    private final void zza(String str, zzand zzand) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        try {
            jsonWriter.beginObject();
            jsonWriter.name(AppMeasurement.Param.TIMESTAMP).value(zzcux.currentTimeMillis());
            jsonWriter.name("event").value(str);
            jsonWriter.name("components").beginArray();
            for (String value : this.zzcuz) {
                jsonWriter.value(value);
            }
            jsonWriter.endArray();
            zzand.zza(jsonWriter);
            jsonWriter.endObject();
            jsonWriter.flush();
            jsonWriter.close();
        } catch (IOException e) {
            zzane.zzb("unable to log", e);
        }
        zzdi(stringWriter.toString());
    }

    static final /* synthetic */ void zza(String str, String str2, Map map, byte[] bArr, JsonWriter jsonWriter) throws IOException {
        jsonWriter.name("params").beginObject();
        jsonWriter.name("firstline").beginObject();
        jsonWriter.name("uri").value(str);
        jsonWriter.name("verb").value(str2);
        jsonWriter.endObject();
        zza(jsonWriter, map);
        if (bArr != null) {
            jsonWriter.name(TtmlNode.TAG_BODY).value(Base64Utils.encode(bArr));
        }
        jsonWriter.endObject();
    }

    static final /* synthetic */ void zza(byte[] bArr, JsonWriter jsonWriter) throws IOException {
        String str;
        jsonWriter.name("params").beginObject();
        int length = bArr.length;
        String encode = Base64Utils.encode(bArr);
        if (length < 10000) {
            str = TtmlNode.TAG_BODY;
        } else {
            encode = zzamu.zzde(encode);
            if (encode != null) {
                str = "bodydigest";
            }
            jsonWriter.name("bodylength").value((long) length);
            jsonWriter.endObject();
        }
        jsonWriter.name(str).value(encode);
        jsonWriter.name("bodylength").value((long) length);
        jsonWriter.endObject();
    }

    public static void zzaf(boolean z) {
        synchronized (sLock) {
            zzcuv = true;
            zzcuw = z;
        }
    }

    private final void zzb(String str, String str2, Map<String, ?> map, byte[] bArr) {
        zza("onNetworkRequest", (zzand) new zzamz(str, str2, map, bArr));
    }

    private final void zzb(Map<String, ?> map, int i) {
        zza("onNetworkResponse", (zzand) new zzana(i, map));
    }

    public static boolean zzbl(Context context) {
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzazm)).booleanValue()) {
            return false;
        }
        try {
            return Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) != 0;
        } catch (Exception e) {
            zzane.zzc("Fail to determine debug setting.", e);
            return false;
        }
    }

    private final void zzdh(String str) {
        zza("onNetworkRequestError", (zzand) new zzanc(str));
    }

    private static synchronized void zzdi(String str) {
        synchronized (zzamy.class) {
            zzane.zzdj("GMA Debug BEGIN");
            int i = 0;
            while (i < str.length()) {
                int i2 = i + 4000;
                String str2 = "GMA Debug CONTENT ";
                String valueOf = String.valueOf(str.substring(i, Math.min(i2, str.length())));
                zzane.zzdj(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                i = i2;
            }
            zzane.zzdj("GMA Debug FINISH");
        }
    }

    public static void zzsj() {
        synchronized (sLock) {
            zzcuv = false;
            zzcuw = false;
            zzane.zzdk("Ad debug logging enablement is out of date.");
        }
    }

    public static boolean zzsk() {
        boolean z;
        synchronized (sLock) {
            z = zzcuv;
        }
        return z;
    }

    public final void zza(String str, String str2, Map<String, ?> map, byte[] bArr) {
        if (isEnabled()) {
            zzb(str, str2, map, bArr);
        }
    }

    public final void zza(HttpURLConnection httpURLConnection, int i) {
        if (isEnabled()) {
            String str = null;
            zzb(httpURLConnection.getHeaderFields() == null ? null : new HashMap(httpURLConnection.getHeaderFields()), i);
            if (i < 200 || i >= 300) {
                try {
                    str = httpURLConnection.getResponseMessage();
                } catch (IOException e) {
                    String str2 = "Can not get error message from error HttpURLConnection\n";
                    String valueOf = String.valueOf(e.getMessage());
                    zzane.zzdk(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                zzdh(str);
            }
        }
    }

    public final void zza(HttpURLConnection httpURLConnection, byte[] bArr) {
        if (isEnabled()) {
            zzb(new String(httpURLConnection.getURL().toString()), new String(httpURLConnection.getRequestMethod()), httpURLConnection.getRequestProperties() == null ? null : new HashMap(httpURLConnection.getRequestProperties()), bArr);
        }
    }

    public final void zza(Map<String, ?> map, int i) {
        if (isEnabled()) {
            zzb(map, i);
            if (i < 200 || i >= 300) {
                zzdh(null);
            }
        }
    }

    public final void zzdg(String str) {
        if (isEnabled() && str != null) {
            zzf(str.getBytes());
        }
    }

    public final void zzf(byte[] bArr) {
        zza("onNetworkResponseBody", (zzand) new zzanb(bArr));
    }
}
