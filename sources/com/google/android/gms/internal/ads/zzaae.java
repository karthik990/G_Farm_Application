package com.google.android.gms.internal.ads;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.google.android.gms.ads.impl.C2522R;
import com.google.android.gms.ads.internal.zzbv;
import java.util.Map;

@zzadh
public final class zzaae extends zzaal {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Map<String, String> zzbgp;

    public zzaae(zzaqw zzaqw, Map<String, String> map) {
        super(zzaqw, "storePicture");
        this.zzbgp = map;
        this.mContext = zzaqw.zzto();
    }

    public final void execute() {
        if (this.mContext == null) {
            zzbw("Activity context is not available");
            return;
        }
        zzbv.zzek();
        if (!zzakk.zzao(this.mContext).zziy()) {
            zzbw("Feature is not supported by the device.");
            return;
        }
        String str = (String) this.zzbgp.get("iurl");
        if (TextUtils.isEmpty(str)) {
            zzbw("Image url cannot be empty.");
        } else if (!URLUtil.isValidUrl(str)) {
            String str2 = "Invalid image url: ";
            String valueOf = String.valueOf(str);
            zzbw(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else {
            String lastPathSegment = Uri.parse(str).getLastPathSegment();
            zzbv.zzek();
            if (!zzakk.zzcw(lastPathSegment)) {
                String str3 = "Image type not recognized: ";
                String valueOf2 = String.valueOf(lastPathSegment);
                zzbw(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
                return;
            }
            Resources resources = zzbv.zzeo().getResources();
            zzbv.zzek();
            Builder zzan = zzakk.zzan(this.mContext);
            zzan.setTitle(resources != null ? resources.getString(C2522R.C2523string.f1531s1) : "Save image");
            zzan.setMessage(resources != null ? resources.getString(C2522R.C2523string.f1532s2) : "Allow Ad to store image in Picture gallery?");
            zzan.setPositiveButton(resources != null ? resources.getString(C2522R.C2523string.f1533s3) : "Accept", new zzaaf(this, str, lastPathSegment));
            zzan.setNegativeButton(resources != null ? resources.getString(C2522R.C2523string.f1534s4) : "Decline", new zzaag(this));
            zzan.create().show();
        }
    }
}
