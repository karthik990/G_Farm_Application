package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

final class zzaag implements OnClickListener {
    private final /* synthetic */ zzaae zzbwq;

    zzaag(zzaae zzaae) {
        this.zzbwq = zzaae;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzbwq.zzbw("User canceled the download.");
    }
}
