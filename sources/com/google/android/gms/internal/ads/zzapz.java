package com.google.android.gms.internal.ads;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import com.google.android.exoplayer2.util.MimeTypes;

@zzadh
public final class zzapz implements OnAudioFocusChangeListener {
    private final AudioManager mAudioManager;
    private boolean zzcxs;
    private final zzaqa zzdaq;
    private boolean zzdar;
    private boolean zzdas;
    private float zzdat = 1.0f;

    public zzapz(Context context, zzaqa zzaqa) {
        this.mAudioManager = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.zzdaq = zzaqa;
    }

    private final void zztw() {
        boolean z = false;
        boolean z2 = this.zzcxs && !this.zzdas && this.zzdat > 0.0f;
        if (z2) {
            boolean z3 = this.zzdar;
            if (!z3) {
                AudioManager audioManager = this.mAudioManager;
                if (audioManager != null && !z3) {
                    if (audioManager.requestAudioFocus(this, 3, 2) == 1) {
                        z = true;
                    }
                    this.zzdar = z;
                }
                this.zzdaq.zzst();
                return;
            }
        }
        if (!z2) {
            boolean z4 = this.zzdar;
            if (z4) {
                AudioManager audioManager2 = this.mAudioManager;
                if (audioManager2 != null && z4) {
                    if (audioManager2.abandonAudioFocus(this) == 0) {
                        z = true;
                    }
                    this.zzdar = z;
                }
                this.zzdaq.zzst();
            }
        }
    }

    public final float getVolume() {
        float f = this.zzdas ? 0.0f : this.zzdat;
        if (this.zzdar) {
            return f;
        }
        return 0.0f;
    }

    public final void onAudioFocusChange(int i) {
        this.zzdar = i > 0;
        this.zzdaq.zzst();
    }

    public final void setMuted(boolean z) {
        this.zzdas = z;
        zztw();
    }

    public final void setVolume(float f) {
        this.zzdat = f;
        zztw();
    }

    public final void zztt() {
        this.zzcxs = true;
        zztw();
    }

    public final void zztu() {
        this.zzcxs = false;
        zztw();
    }
}
