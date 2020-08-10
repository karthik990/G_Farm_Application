package com.google.android.gms.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzeh {
    private static zzeh zzaip;
    private volatile String zzaec = null;
    private volatile zza zzaiq = zza.NONE;
    private volatile String zzair = null;
    private volatile String zzais = null;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzeh() {
    }

    static zzeh zziy() {
        zzeh zzeh;
        synchronized (zzeh.class) {
            if (zzaip == null) {
                zzaip = new zzeh();
            }
            zzeh = zzaip;
        }
        return zzeh;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean zza(Uri uri) {
        try {
            String decode = URLDecoder.decode(uri.toString(), "UTF-8");
            if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                String str = "Container preview url: ";
                String valueOf = String.valueOf(decode);
                zzdi.zzab(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                if (decode.matches(".*?&gtm_debug=x$")) {
                    this.zzaiq = zza.CONTAINER_DEBUG;
                } else {
                    this.zzaiq = zza.CONTAINER;
                }
                this.zzais = uri.getQuery().replace("&gtm_debug=x", "");
                if (this.zzaiq == zza.CONTAINER || this.zzaiq == zza.CONTAINER_DEBUG) {
                    String str2 = "/r?";
                    String valueOf2 = String.valueOf(this.zzais);
                    this.zzair = valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2);
                }
                this.zzaec = zzbh(this.zzais);
                return true;
            } else if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                String str3 = "Invalid preview uri: ";
                String valueOf3 = String.valueOf(decode);
                zzdi.zzac(valueOf3.length() != 0 ? str3.concat(valueOf3) : new String(str3));
                return false;
            } else if (!zzbh(uri.getQuery()).equals(this.zzaec)) {
                return false;
            } else {
                String str4 = "Exit preview mode for container: ";
                String valueOf4 = String.valueOf(this.zzaec);
                zzdi.zzab(valueOf4.length() != 0 ? str4.concat(valueOf4) : new String(str4));
                this.zzaiq = zza.NONE;
                this.zzair = null;
                return true;
            }
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final zza zziz() {
        return this.zzaiq;
    }

    /* access modifiers changed from: 0000 */
    public final String zzja() {
        return this.zzair;
    }

    /* access modifiers changed from: 0000 */
    public final String getContainerId() {
        return this.zzaec;
    }

    private static String zzbh(String str) {
        return str.split("&")[0].split("=")[1];
    }
}
