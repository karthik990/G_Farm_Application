package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

final class zzfu implements zzbe {
    private final String zzabp;
    private final zzfx zzalb;
    private final zzfw zzalc;
    private final Context zzrm;

    private zzfu(zzfx zzfx, Context context, zzfw zzfw) {
        this.zzalb = zzfx;
        this.zzrm = context.getApplicationContext();
        this.zzalc = zzfw;
        String str = VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        String str2 = null;
        if (!(locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (!(locale.getCountry() == null || locale.getCountry().length() == 0)) {
                sb.append("-");
                sb.append(locale.getCountry().toLowerCase());
            }
            str2 = sb.toString();
        }
        this.zzabp = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleTagManager", "4.00", str, str2, Build.MODEL, Build.ID});
    }

    zzfu(Context context, zzfw zzfw) {
        this(new zzfv(), context, zzfw);
    }

    public final boolean zzhy() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzrm.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzdi.zzab("...no network connectivity");
        return false;
    }

    public final void zzd(List<zzbw> list) {
        HttpURLConnection zzc;
        int min = Math.min(list.size(), 40);
        boolean z = true;
        for (int i = 0; i < min; i++) {
            zzbw zzbw = (zzbw) list.get(i);
            URL zzd = zzd(zzbw);
            if (zzd == null) {
                zzdi.zzac("No destination: discarding hit.");
                this.zzalc.zzb(zzbw);
            } else {
                InputStream inputStream = null;
                try {
                    zzc = this.zzalb.zzc(zzd);
                    if (z) {
                        zzdn.zzn(this.zzrm);
                        z = false;
                    }
                    zzc.setRequestProperty("User-Agent", this.zzabp);
                    int responseCode = zzc.getResponseCode();
                    InputStream inputStream2 = zzc.getInputStream();
                    if (responseCode != 200) {
                        StringBuilder sb = new StringBuilder(25);
                        sb.append("Bad response: ");
                        sb.append(responseCode);
                        zzdi.zzac(sb.toString());
                        this.zzalc.zzc(zzbw);
                    } else {
                        this.zzalc.zza(zzbw);
                    }
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    zzc.disconnect();
                } catch (IOException e) {
                    String str = "Exception sending hit: ";
                    String valueOf = String.valueOf(e.getClass().getSimpleName());
                    zzdi.zzac(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    zzdi.zzac(e.getMessage());
                    this.zzalc.zzc(zzbw);
                } catch (Throwable th) {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    zzc.disconnect();
                    throw th;
                }
            }
        }
    }

    private static URL zzd(zzbw zzbw) {
        try {
            return new URL(zzbw.zzij());
        } catch (MalformedURLException unused) {
            zzdi.zzav("Error trying to parse the GTM url.");
            return null;
        }
    }
}
