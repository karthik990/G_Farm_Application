package com.google.android.gms.internal.ads;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.CalendarContract.Events;
import android.text.TextUtils;
import com.anjlab.android.iab.p020v3.Constants;
import com.google.android.gms.ads.impl.C2522R;
import com.google.android.gms.ads.internal.zzbv;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Map;

@zzadh
public final class zzzy extends zzaal {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Map<String, String> zzbgp;
    private String zzbvs = zzbu(Constants.RESPONSE_DESCRIPTION);
    private long zzbvt = zzbv("start_ticks");
    private long zzbvu = zzbv("end_ticks");
    private String zzbvv = zzbu("summary");
    private String zzbvw = zzbu(Param.LOCATION);

    public zzzy(zzaqw zzaqw, Map<String, String> map) {
        super(zzaqw, "createCalendarEvent");
        this.zzbgp = map;
        this.mContext = zzaqw.zzto();
    }

    private final String zzbu(String str) {
        return TextUtils.isEmpty((CharSequence) this.zzbgp.get(str)) ? "" : (String) this.zzbgp.get(str);
    }

    private final long zzbv(String str) {
        String str2 = (String) this.zzbgp.get(str);
        long j = -1;
        if (str2 == null) {
            return -1;
        }
        try {
            j = Long.parseLong(str2);
        } catch (NumberFormatException unused) {
        }
        return j;
    }

    /* access modifiers changed from: 0000 */
    public final Intent createIntent() {
        Intent data = new Intent("android.intent.action.EDIT").setData(Events.CONTENT_URI);
        data.putExtra("title", this.zzbvs);
        data.putExtra("eventLocation", this.zzbvw);
        data.putExtra(Constants.RESPONSE_DESCRIPTION, this.zzbvv);
        long j = this.zzbvt;
        if (j > -1) {
            data.putExtra("beginTime", j);
        }
        long j2 = this.zzbvu;
        if (j2 > -1) {
            data.putExtra("endTime", j2);
        }
        data.setFlags(268435456);
        return data;
    }

    public final void execute() {
        if (this.mContext == null) {
            zzbw("Activity context is not available.");
            return;
        }
        zzbv.zzek();
        if (!zzakk.zzao(this.mContext).zziz()) {
            zzbw("This feature is not available on the device.");
            return;
        }
        zzbv.zzek();
        Builder zzan = zzakk.zzan(this.mContext);
        Resources resources = zzbv.zzeo().getResources();
        zzan.setTitle(resources != null ? resources.getString(C2522R.C2523string.f1535s5) : "Create calendar event");
        zzan.setMessage(resources != null ? resources.getString(C2522R.C2523string.f1536s6) : "Allow Ad to create a calendar event?");
        zzan.setPositiveButton(resources != null ? resources.getString(C2522R.C2523string.f1533s3) : "Accept", new zzzz(this));
        zzan.setNegativeButton(resources != null ? resources.getString(C2522R.C2523string.f1534s4) : "Decline", new zzaaa(this));
        zzan.create().show();
    }
}
