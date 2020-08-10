package com.google.android.gms.internal.ads;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

@zzadh
public final class zzhl extends AbstractSafeParcelable {
    public static final Creator<zzhl> CREATOR = new zzhm();
    public final String url;
    private final long zzajv;
    private final String zzajw;
    private final String zzajx;
    private final String zzajy;
    private final Bundle zzajz;
    private final boolean zzaka;
    private long zzakb;

    zzhl(String str, long j, String str2, String str3, String str4, Bundle bundle, boolean z, long j2) {
        this.url = str;
        this.zzajv = j;
        String str5 = "";
        if (str2 == null) {
            str2 = str5;
        }
        this.zzajw = str2;
        if (str3 == null) {
            str3 = str5;
        }
        this.zzajx = str3;
        if (str4 != null) {
            str5 = str4;
        }
        this.zzajy = str5;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzajz = bundle;
        this.zzaka = z;
        this.zzakb = j2;
    }

    public static zzhl zzaa(String str) {
        return zzd(Uri.parse(str));
    }

    public static zzhl zzd(Uri uri) {
        try {
            if (!"gcache".equals(uri.getScheme())) {
                return null;
            }
            List pathSegments = uri.getPathSegments();
            if (pathSegments.size() != 2) {
                int size = pathSegments.size();
                StringBuilder sb = new StringBuilder(62);
                sb.append("Expected 2 path parts for namespace and id, found :");
                sb.append(size);
                zzakb.zzdk(sb.toString());
                return null;
            }
            String str = (String) pathSegments.get(0);
            String str2 = (String) pathSegments.get(1);
            String host = uri.getHost();
            String queryParameter = uri.getQueryParameter("url");
            boolean equals = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(uri.getQueryParameter("read_only"));
            String queryParameter2 = uri.getQueryParameter("expiration");
            long parseLong = queryParameter2 == null ? 0 : Long.parseLong(queryParameter2);
            Bundle bundle = new Bundle();
            for (String str3 : zzbv.zzem().zzh(uri)) {
                if (str3.startsWith("tag.")) {
                    bundle.putString(str3.substring(4), uri.getQueryParameter(str3));
                }
            }
            zzhl zzhl = new zzhl(queryParameter, parseLong, host, str, str2, bundle, equals, 0);
            return zzhl;
        } catch (NullPointerException | NumberFormatException e) {
            zzakb.zzc("Unable to parse Uri into cache offering.", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.url, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzajv);
        SafeParcelWriter.writeString(parcel, 4, this.zzajw, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzajx, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzajy, false);
        SafeParcelWriter.writeBundle(parcel, 7, this.zzajz, false);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzaka);
        SafeParcelWriter.writeLong(parcel, 9, this.zzakb);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
