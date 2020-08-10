package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzl;
import com.google.android.gms.internal.firebase_auth.zzp.zzl.zza;
import com.google.firebase.auth.api.internal.zzfd;
import java.util.Arrays;
import java.util.List;

public final class zzfg implements zzfd<zzl> {
    private String zzhy;
    private String zzib;
    private String zzif;
    private String zzig;
    private String zzjv;
    private String zzkc;
    private zzfk zzsh = new zzfk();
    private zzfk zzsi = new zzfk();
    private boolean zzsj = true;
    private String zzsk;

    public final boolean zzcp(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzsi.zzez().contains(str);
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final String getDisplayName() {
        return this.zzjv;
    }

    public final String zzam() {
        return this.zzkc;
    }

    public final zzfg zzcq(String str) {
        this.zzib = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfg zzcr(String str) {
        if (str == null) {
            this.zzsi.zzez().add("EMAIL");
        } else {
            this.zzif = str;
        }
        return this;
    }

    public final zzfg zzcs(String str) {
        if (str == null) {
            this.zzsi.zzez().add("PASSWORD");
        } else {
            this.zzig = str;
        }
        return this;
    }

    public final zzfg zzct(String str) {
        if (str == null) {
            this.zzsi.zzez().add("DISPLAY_NAME");
        } else {
            this.zzjv = str;
        }
        return this;
    }

    public final zzfg zzcu(String str) {
        if (str == null) {
            this.zzsi.zzez().add("PHOTO_URL");
        } else {
            this.zzkc = str;
        }
        return this;
    }

    public final zzfg zzcv(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzsh.zzez().add(str);
        return this;
    }

    public final zzfg zzcw(String str) {
        this.zzsk = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfg zzcx(String str) {
        this.zzhy = str;
        return this;
    }

    public final /* synthetic */ zzjc zzeq() {
        zzv zzv;
        zza zzd = zzl.zzaj().zzf(this.zzsj).zzd(this.zzsh.zzez());
        List zzez = this.zzsi.zzez();
        zzv[] zzvArr = new zzv[zzez.size()];
        for (int i = 0; i < zzez.size(); i++) {
            String str = (String) zzez.get(i);
            char c = 65535;
            switch (str.hashCode()) {
                case -333046776:
                    if (str.equals("DISPLAY_NAME")) {
                        c = 1;
                        break;
                    }
                    break;
                case 66081660:
                    if (str.equals("EMAIL")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1939891618:
                    if (str.equals("PHOTO_URL")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1999612571:
                    if (str.equals("PASSWORD")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                zzv = zzv.EMAIL;
            } else if (c == 1) {
                zzv = zzv.DISPLAY_NAME;
            } else if (c == 2) {
                zzv = zzv.PASSWORD;
            } else if (c != 3) {
                zzv = zzv.USER_ATTRIBUTE_NAME_UNSPECIFIED;
            } else {
                zzv = zzv.PHOTO_URL;
            }
            zzvArr[i] = zzv;
        }
        zza zzc = zzd.zzc(Arrays.asList(zzvArr));
        String str2 = this.zzib;
        if (str2 != null) {
            zzc.zzap(str2);
        }
        String str3 = this.zzif;
        if (str3 != null) {
            zzc.zzar(str3);
        }
        String str4 = this.zzig;
        if (str4 != null) {
            zzc.zzas(str4);
        }
        String str5 = this.zzjv;
        if (str5 != null) {
            zzc.zzaq(str5);
        }
        String str6 = this.zzkc;
        if (str6 != null) {
            zzc.zzau(str6);
        }
        String str7 = this.zzsk;
        if (str7 != null) {
            zzc.zzat(str7);
        }
        String str8 = this.zzhy;
        if (str8 != null) {
            zzc.zzav(str8);
        }
        return (zzl) ((zzhs) zzc.zzih());
    }
}
