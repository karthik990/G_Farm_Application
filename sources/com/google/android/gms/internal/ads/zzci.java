package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public final class zzci {
    private static final String[] zzrc = {"/aclk", "/pcs/click"};
    private String zzqy = "googleads.g.doubleclick.net";
    private String zzqz = "/pagead/ads";
    private String zzra = "ad.doubleclick.net";
    private String[] zzrb = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private zzce zzrd;

    public zzci(zzce zzce) {
        this.zzrd = zzce;
    }

    private final Uri zza(Uri uri, Context context, String str, boolean z, View view, Activity activity) throws zzcj {
        try {
            boolean zza = zza(uri);
            String str2 = "ms";
            if (zza) {
                if (uri.toString().contains("dc_ms=")) {
                    throw new zzcj("Parameter already exists: dc_ms");
                }
            } else if (uri.getQueryParameter(str2) != null) {
                throw new zzcj("Query parameter already exists: ms");
            }
            String zza2 = z ? this.zzrd.zza(context, str, view, activity) : this.zzrd.zza(context);
            String str3 = "=";
            if (zza) {
                String str4 = "dc_ms";
                String uri2 = uri.toString();
                int indexOf = uri2.indexOf(";adurl");
                String str5 = ";";
                if (indexOf != -1) {
                    int i = indexOf + 1;
                    StringBuilder sb = new StringBuilder(uri2.substring(0, i));
                    sb.append(str4);
                    sb.append(str3);
                    sb.append(zza2);
                    sb.append(str5);
                    sb.append(uri2.substring(i));
                    return Uri.parse(sb.toString());
                }
                String encodedPath = uri.getEncodedPath();
                int indexOf2 = uri2.indexOf(encodedPath);
                StringBuilder sb2 = new StringBuilder(uri2.substring(0, encodedPath.length() + indexOf2));
                sb2.append(str5);
                sb2.append(str4);
                sb2.append(str3);
                sb2.append(zza2);
                sb2.append(str5);
                sb2.append(uri2.substring(indexOf2 + encodedPath.length()));
                return Uri.parse(sb2.toString());
            }
            String uri3 = uri.toString();
            int indexOf3 = uri3.indexOf("&adurl");
            if (indexOf3 == -1) {
                indexOf3 = uri3.indexOf("?adurl");
            }
            if (indexOf3 == -1) {
                return uri.buildUpon().appendQueryParameter(str2, zza2).build();
            }
            int i2 = indexOf3 + 1;
            StringBuilder sb3 = new StringBuilder(uri3.substring(0, i2));
            sb3.append(str2);
            sb3.append(str3);
            sb3.append(zza2);
            sb3.append("&");
            sb3.append(uri3.substring(i2));
            return Uri.parse(sb3.toString());
        } catch (UnsupportedOperationException unused) {
            throw new zzcj("Provided Uri is not in a valid state");
        }
    }

    private final boolean zza(Uri uri) {
        if (uri != null) {
            try {
                return uri.getHost().equals(this.zzra);
            } catch (NullPointerException unused) {
                return false;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public final Uri zza(Uri uri, Context context) throws zzcj {
        return zza(uri, context, null, false, null, null);
    }

    public final Uri zza(Uri uri, Context context, View view, Activity activity) throws zzcj {
        try {
            return zza(uri, context, uri.getQueryParameter("ai"), true, view, activity);
        } catch (UnsupportedOperationException unused) {
            throw new zzcj("Provided Uri is not in a valid state");
        }
    }

    public final void zza(MotionEvent motionEvent) {
        this.zzrd.zza(motionEvent);
    }

    public final zzce zzaa() {
        return this.zzrd;
    }

    public final boolean zzb(Uri uri) {
        if (uri != null) {
            try {
                String host = uri.getHost();
                for (String endsWith : this.zzrb) {
                    if (host.endsWith(endsWith)) {
                        return true;
                    }
                }
            } catch (NullPointerException unused) {
            }
            return false;
        }
        throw new NullPointerException();
    }

    public final boolean zzc(Uri uri) {
        if (zzb(uri)) {
            for (String endsWith : zzrc) {
                if (uri.getPath().endsWith(endsWith)) {
                    return true;
                }
            }
        }
        return false;
    }
}
