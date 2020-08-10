package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzkb;
import javax.annotation.Nullable;

@zzadh
public final class zzo extends FrameLayout implements OnClickListener {
    private final ImageButton zzbyy;
    private final zzw zzbyz;

    public zzo(Context context, zzp zzp, @Nullable zzw zzw) {
        super(context);
        this.zzbyz = zzw;
        setOnClickListener(this);
        this.zzbyy = new ImageButton(context);
        this.zzbyy.setImageResource(17301527);
        this.zzbyy.setBackgroundColor(0);
        this.zzbyy.setOnClickListener(this);
        ImageButton imageButton = this.zzbyy;
        zzkb.zzif();
        int zza = zzamu.zza(context, zzp.paddingLeft);
        zzkb.zzif();
        int zza2 = zzamu.zza(context, 0);
        zzkb.zzif();
        int zza3 = zzamu.zza(context, zzp.paddingRight);
        zzkb.zzif();
        imageButton.setPadding(zza, zza2, zza3, zzamu.zza(context, zzp.paddingBottom));
        this.zzbyy.setContentDescription("Interstitial close button");
        zzkb.zzif();
        zzamu.zza(context, zzp.size);
        ImageButton imageButton2 = this.zzbyy;
        zzkb.zzif();
        int zza4 = zzamu.zza(context, zzp.size + zzp.paddingLeft + zzp.paddingRight);
        zzkb.zzif();
        addView(imageButton2, new LayoutParams(zza4, zzamu.zza(context, zzp.size + zzp.paddingBottom), 17));
    }

    public final void onClick(View view) {
        zzw zzw = this.zzbyz;
        if (zzw != null) {
            zzw.zzni();
        }
    }

    public final void zzu(boolean z) {
        ImageButton imageButton;
        int i;
        if (z) {
            imageButton = this.zzbyy;
            i = 8;
        } else {
            imageButton = this.zzbyy;
            i = 0;
        }
        imageButton.setVisibility(i);
    }
}
