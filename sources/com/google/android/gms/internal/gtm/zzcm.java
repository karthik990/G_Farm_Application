package com.google.android.gms.internal.gtm;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzk;

public final class zzcm extends zzan {
    /* access modifiers changed from: private */
    public SharedPreferences zzabv;
    private long zzabw;
    private long zzabx = -1;
    private final zzco zzaby;

    protected zzcm(zzap zzap) {
        super(zzap);
        zzco zzco = new zzco(this, "monitoring", ((Long) zzby.zzaao.get()).longValue());
        this.zzaby = zzco;
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
        this.zzabv = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public final long zzfv() {
        zzk.zzav();
        zzdb();
        if (this.zzabw == 0) {
            String str = "first_run";
            long j = this.zzabv.getLong(str, 0);
            if (j != 0) {
                this.zzabw = j;
            } else {
                long currentTimeMillis = zzcn().currentTimeMillis();
                Editor edit = this.zzabv.edit();
                edit.putLong(str, currentTimeMillis);
                if (!edit.commit()) {
                    zzt("Failed to commit first run time");
                }
                this.zzabw = currentTimeMillis;
            }
        }
        return this.zzabw;
    }

    public final zzcv zzfw() {
        return new zzcv(zzcn(), zzfv());
    }

    public final long zzfx() {
        zzk.zzav();
        zzdb();
        if (this.zzabx == -1) {
            this.zzabx = this.zzabv.getLong("last_dispatch", 0);
        }
        return this.zzabx;
    }

    public final void zzfy() {
        zzk.zzav();
        zzdb();
        long currentTimeMillis = zzcn().currentTimeMillis();
        Editor edit = this.zzabv.edit();
        edit.putLong("last_dispatch", currentTimeMillis);
        edit.apply();
        this.zzabx = currentTimeMillis;
    }

    public final String zzfz() {
        zzk.zzav();
        zzdb();
        String string = this.zzabv.getString("installation_campaign", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public final void zzad(String str) {
        zzk.zzav();
        zzdb();
        Editor edit = this.zzabv.edit();
        String str2 = "installation_campaign";
        if (TextUtils.isEmpty(str)) {
            edit.remove(str2);
        } else {
            edit.putString(str2, str);
        }
        if (!edit.commit()) {
            zzt("Failed to commit campaign data");
        }
    }

    public final zzco zzga() {
        return this.zzaby;
    }
}
