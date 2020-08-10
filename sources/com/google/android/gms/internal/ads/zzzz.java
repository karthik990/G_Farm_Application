package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.google.android.gms.ads.internal.zzbv;

final class zzzz implements OnClickListener {
    private final /* synthetic */ zzzy zzbvx;

    zzzz(zzzy zzzy) {
        this.zzbvx = zzzy;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent createIntent = this.zzbvx.createIntent();
        zzbv.zzek();
        zzakk.zza(this.zzbvx.mContext, createIntent);
    }
}
