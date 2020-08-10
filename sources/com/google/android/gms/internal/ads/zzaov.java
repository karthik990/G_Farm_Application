package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import com.google.android.gms.ads.internal.zzbv;
import com.google.logging.type.LogSeverity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@zzadh
public final class zzaov extends zzapg implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceTextureListener {
    private static final Map<Integer, String> zzcwo = new HashMap();
    private final zzapx zzcwp;
    private final boolean zzcwq;
    private int zzcwr = 0;
    private int zzcws = 0;
    private MediaPlayer zzcwt;
    private Uri zzcwu;
    private int zzcwv;
    private int zzcww;
    private int zzcwx;
    private int zzcwy;
    private int zzcwz;
    private zzapu zzcxa;
    private boolean zzcxb;
    private int zzcxc;
    /* access modifiers changed from: private */
    public zzapf zzcxd;

    static {
        if (VERSION.SDK_INT >= 17) {
            zzcwo.put(Integer.valueOf(-1004), "MEDIA_ERROR_IO");
            zzcwo.put(Integer.valueOf(-1007), "MEDIA_ERROR_MALFORMED");
            zzcwo.put(Integer.valueOf(-1010), "MEDIA_ERROR_UNSUPPORTED");
            zzcwo.put(Integer.valueOf(-110), "MEDIA_ERROR_TIMED_OUT");
            zzcwo.put(Integer.valueOf(3), "MEDIA_INFO_VIDEO_RENDERING_START");
        }
        zzcwo.put(Integer.valueOf(100), "MEDIA_ERROR_SERVER_DIED");
        zzcwo.put(Integer.valueOf(1), "MEDIA_ERROR_UNKNOWN");
        zzcwo.put(Integer.valueOf(1), "MEDIA_INFO_UNKNOWN");
        zzcwo.put(Integer.valueOf(LogSeverity.ALERT_VALUE), "MEDIA_INFO_VIDEO_TRACK_LAGGING");
        zzcwo.put(Integer.valueOf(701), "MEDIA_INFO_BUFFERING_START");
        zzcwo.put(Integer.valueOf(702), "MEDIA_INFO_BUFFERING_END");
        zzcwo.put(Integer.valueOf(LogSeverity.EMERGENCY_VALUE), "MEDIA_INFO_BAD_INTERLEAVING");
        zzcwo.put(Integer.valueOf(801), "MEDIA_INFO_NOT_SEEKABLE");
        zzcwo.put(Integer.valueOf(802), "MEDIA_INFO_METADATA_UPDATE");
        if (VERSION.SDK_INT >= 19) {
            zzcwo.put(Integer.valueOf(901), "MEDIA_INFO_UNSUPPORTED_SUBTITLE");
            zzcwo.put(Integer.valueOf(902), "MEDIA_INFO_SUBTITLE_TIMED_OUT");
        }
    }

    public zzaov(Context context, boolean z, boolean z2, zzapv zzapv, zzapx zzapx) {
        super(context);
        setSurfaceTextureListener(this);
        this.zzcwp = zzapx;
        this.zzcxb = z;
        this.zzcwq = z2;
        this.zzcwp.zzb(this);
    }

    private final void zza(float f) {
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.setVolume(f, f);
            } catch (IllegalStateException unused) {
            }
        } else {
            zzakb.zzdk("AdMediaPlayerView setMediaPlayerVolume() called before onPrepared().");
        }
    }

    private final void zzag(int i) {
        if (i == 3) {
            this.zzcwp.zztt();
            this.zzcxl.zztt();
        } else if (this.zzcwr == 3) {
            this.zzcwp.zztu();
            this.zzcxl.zztu();
        }
        this.zzcwr = i;
    }

    private final void zzag(boolean z) {
        zzakb.m1419v("AdMediaPlayerView release");
        zzapu zzapu = this.zzcxa;
        if (zzapu != null) {
            zzapu.zzti();
            this.zzcxa = null;
        }
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.zzcwt.release();
            this.zzcwt = null;
            zzag(0);
            if (z) {
                this.zzcws = 0;
                this.zzcws = 0;
            }
        }
    }

    private final void zzsq() {
        zzakb.m1419v("AdMediaPlayerView init MediaPlayer");
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (!(this.zzcwu == null || surfaceTexture == null)) {
            zzag(false);
            try {
                zzbv.zzfb();
                this.zzcwt = new MediaPlayer();
                this.zzcwt.setOnBufferingUpdateListener(this);
                this.zzcwt.setOnCompletionListener(this);
                this.zzcwt.setOnErrorListener(this);
                this.zzcwt.setOnInfoListener(this);
                this.zzcwt.setOnPreparedListener(this);
                this.zzcwt.setOnVideoSizeChangedListener(this);
                this.zzcwx = 0;
                if (this.zzcxb) {
                    this.zzcxa = new zzapu(getContext());
                    this.zzcxa.zza(surfaceTexture, getWidth(), getHeight());
                    this.zzcxa.start();
                    SurfaceTexture zztj = this.zzcxa.zztj();
                    if (zztj != null) {
                        surfaceTexture = zztj;
                    } else {
                        this.zzcxa.zzti();
                        this.zzcxa = null;
                    }
                }
                this.zzcwt.setDataSource(getContext(), this.zzcwu);
                zzbv.zzfc();
                this.zzcwt.setSurface(new Surface(surfaceTexture));
                this.zzcwt.setAudioStreamType(3);
                this.zzcwt.setScreenOnWhilePlaying(true);
                this.zzcwt.prepareAsync();
                zzag(1);
            } catch (IOException | IllegalArgumentException | IllegalStateException e) {
                String valueOf = String.valueOf(this.zzcwu);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
                sb.append("Failed to initialize MediaPlayer at ");
                sb.append(valueOf);
                zzakb.zzc(sb.toString(), e);
                onError(this.zzcwt, 1, 0);
            }
        }
    }

    private final void zzsr() {
        if (this.zzcwq && zzss() && this.zzcwt.getCurrentPosition() > 0 && this.zzcws != 3) {
            zzakb.m1419v("AdMediaPlayerView nudging MediaPlayer");
            zza(0.0f);
            this.zzcwt.start();
            int currentPosition = this.zzcwt.getCurrentPosition();
            long currentTimeMillis = zzbv.zzer().currentTimeMillis();
            while (zzss() && this.zzcwt.getCurrentPosition() == currentPosition) {
                if (zzbv.zzer().currentTimeMillis() - currentTimeMillis > 250) {
                    break;
                }
            }
            this.zzcwt.pause();
            zzst();
        }
    }

    private final boolean zzss() {
        if (this.zzcwt != null) {
            int i = this.zzcwr;
            if (!(i == -1 || i == 0 || i == 1)) {
                return true;
            }
        }
        return false;
    }

    public final int getCurrentPosition() {
        if (zzss()) {
            return this.zzcwt.getCurrentPosition();
        }
        return 0;
    }

    public final int getDuration() {
        if (zzss()) {
            return this.zzcwt.getDuration();
        }
        return -1;
    }

    public final int getVideoHeight() {
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoHeight();
        }
        return 0;
    }

    public final int getVideoWidth() {
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoWidth();
        }
        return 0;
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        this.zzcwx = i;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        zzakb.m1419v("AdMediaPlayerView completion");
        zzag(5);
        this.zzcws = 5;
        zzakk.zzcrm.post(new zzaoy(this));
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str = (String) zzcwo.get(Integer.valueOf(i));
        String str2 = (String) zzcwo.get(Integer.valueOf(i2));
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 38 + String.valueOf(str2).length());
        sb.append("AdMediaPlayerView MediaPlayer error: ");
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        zzakb.zzdk(sb.toString());
        zzag(-1);
        this.zzcws = -1;
        zzakk.zzcrm.post(new zzaoz(this, str, str2));
        return true;
    }

    public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        String str = (String) zzcwo.get(Integer.valueOf(i));
        String str2 = (String) zzcwo.get(Integer.valueOf(i2));
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 37 + String.valueOf(str2).length());
        sb.append("AdMediaPlayerView MediaPlayer info: ");
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        zzakb.m1419v(sb.toString());
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a3, code lost:
        if (r7 != r1) goto L_0x00a5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.zzcwv
            int r0 = getDefaultSize(r0, r6)
            int r1 = r5.zzcww
            int r1 = getDefaultSize(r1, r7)
            int r2 = r5.zzcwv
            if (r2 <= 0) goto L_0x0088
            int r2 = r5.zzcww
            if (r2 <= 0) goto L_0x0088
            com.google.android.gms.internal.ads.zzapu r2 = r5.zzcxa
            if (r2 != 0) goto L_0x0088
            int r0 = android.view.View.MeasureSpec.getMode(r6)
            int r6 = android.view.View.MeasureSpec.getSize(r6)
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            int r7 = android.view.View.MeasureSpec.getSize(r7)
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 != r2) goto L_0x0048
            if (r1 != r2) goto L_0x0048
            int r0 = r5.zzcwv
            int r1 = r0 * r7
            int r2 = r5.zzcww
            int r3 = r6 * r2
            if (r1 >= r3) goto L_0x003d
            int r0 = r0 * r7
            int r0 = r0 / r2
            r1 = r7
            goto L_0x0088
        L_0x003d:
            int r1 = r0 * r7
            int r3 = r6 * r2
            if (r1 <= r3) goto L_0x0069
            int r2 = r2 * r6
            int r1 = r2 / r0
            goto L_0x0089
        L_0x0048:
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 != r2) goto L_0x005a
            int r0 = r5.zzcww
            int r0 = r0 * r6
            int r2 = r5.zzcwv
            int r0 = r0 / r2
            if (r1 != r3) goto L_0x0058
            if (r0 <= r7) goto L_0x0058
            goto L_0x0067
        L_0x0058:
            r1 = r0
            goto L_0x0089
        L_0x005a:
            if (r1 != r2) goto L_0x006b
            int r1 = r5.zzcwv
            int r1 = r1 * r7
            int r2 = r5.zzcww
            int r1 = r1 / r2
            if (r0 != r3) goto L_0x0068
            if (r1 <= r6) goto L_0x0068
        L_0x0067:
            goto L_0x0069
        L_0x0068:
            r6 = r1
        L_0x0069:
            r1 = r7
            goto L_0x0089
        L_0x006b:
            int r2 = r5.zzcwv
            int r4 = r5.zzcww
            if (r1 != r3) goto L_0x0078
            if (r4 <= r7) goto L_0x0078
            int r2 = r2 * r7
            int r2 = r2 / r4
            r1 = r7
            goto L_0x0079
        L_0x0078:
            r1 = r4
        L_0x0079:
            if (r0 != r3) goto L_0x0086
            if (r2 <= r6) goto L_0x0086
            int r7 = r5.zzcww
            int r7 = r7 * r6
            int r0 = r5.zzcwv
            int r1 = r7 / r0
            goto L_0x0089
        L_0x0086:
            r6 = r2
            goto L_0x0089
        L_0x0088:
            r6 = r0
        L_0x0089:
            r5.setMeasuredDimension(r6, r1)
            com.google.android.gms.internal.ads.zzapu r7 = r5.zzcxa
            if (r7 == 0) goto L_0x0093
            r7.zzh(r6, r1)
        L_0x0093:
            int r7 = android.os.Build.VERSION.SDK_INT
            r0 = 16
            if (r7 != r0) goto L_0x00ac
            int r7 = r5.zzcwy
            if (r7 <= 0) goto L_0x009f
            if (r7 != r6) goto L_0x00a5
        L_0x009f:
            int r7 = r5.zzcwz
            if (r7 <= 0) goto L_0x00a8
            if (r7 == r1) goto L_0x00a8
        L_0x00a5:
            r5.zzsr()
        L_0x00a8:
            r5.zzcwy = r6
            r5.zzcwz = r1
        L_0x00ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaov.onMeasure(int, int):void");
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        zzakb.m1419v("AdMediaPlayerView prepared");
        zzag(2);
        this.zzcwp.zzsv();
        zzakk.zzcrm.post(new zzaox(this));
        this.zzcwv = mediaPlayer.getVideoWidth();
        this.zzcww = mediaPlayer.getVideoHeight();
        int i = this.zzcxc;
        if (i != 0) {
            seekTo(i);
        }
        zzsr();
        int i2 = this.zzcwv;
        int i3 = this.zzcww;
        StringBuilder sb = new StringBuilder(62);
        sb.append("AdMediaPlayerView stream dimensions: ");
        sb.append(i2);
        sb.append(" x ");
        sb.append(i3);
        zzakb.zzdj(sb.toString());
        if (this.zzcws == 3) {
            play();
        }
        zzst();
    }

    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        zzakb.m1419v("AdMediaPlayerView surface created");
        zzsq();
        zzakk.zzcrm.post(new zzapa(this));
    }

    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        zzakb.m1419v("AdMediaPlayerView surface destroyed");
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null && this.zzcxc == 0) {
            this.zzcxc = mediaPlayer.getCurrentPosition();
        }
        zzapu zzapu = this.zzcxa;
        if (zzapu != null) {
            zzapu.zzti();
        }
        zzakk.zzcrm.post(new zzapc(this));
        zzag(true);
        return true;
    }

    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        zzakb.m1419v("AdMediaPlayerView surface changed");
        boolean z = true;
        boolean z2 = this.zzcws == 3;
        if (!(this.zzcwv == i && this.zzcww == i2)) {
            z = false;
        }
        if (this.zzcwt != null && z2 && z) {
            int i3 = this.zzcxc;
            if (i3 != 0) {
                seekTo(i3);
            }
            play();
        }
        zzapu zzapu = this.zzcxa;
        if (zzapu != null) {
            zzapu.zzh(i, i2);
        }
        zzakk.zzcrm.post(new zzapb(this, i, i2));
    }

    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.zzcwp.zzc(this);
        this.zzcxk.zza(surfaceTexture, this.zzcxd);
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        StringBuilder sb = new StringBuilder(57);
        sb.append("AdMediaPlayerView size changed: ");
        sb.append(i);
        sb.append(" x ");
        sb.append(i2);
        zzakb.m1419v(sb.toString());
        this.zzcwv = mediaPlayer.getVideoWidth();
        this.zzcww = mediaPlayer.getVideoHeight();
        if (this.zzcwv != 0 && this.zzcww != 0) {
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public final void onWindowVisibilityChanged(int i) {
        StringBuilder sb = new StringBuilder(58);
        sb.append("AdMediaPlayerView window visibility changed to ");
        sb.append(i);
        zzakb.m1419v(sb.toString());
        zzakk.zzcrm.post(new zzaow(this, i));
        super.onWindowVisibilityChanged(i);
    }

    public final void pause() {
        zzakb.m1419v("AdMediaPlayerView pause");
        if (zzss() && this.zzcwt.isPlaying()) {
            this.zzcwt.pause();
            zzag(4);
            zzakk.zzcrm.post(new zzape(this));
        }
        this.zzcws = 4;
    }

    public final void play() {
        zzakb.m1419v("AdMediaPlayerView play");
        if (zzss()) {
            this.zzcwt.start();
            zzag(3);
            this.zzcxk.zzsw();
            zzakk.zzcrm.post(new zzapd(this));
        }
        this.zzcws = 3;
    }

    public final void seekTo(int i) {
        StringBuilder sb = new StringBuilder(34);
        sb.append("AdMediaPlayerView seek ");
        sb.append(i);
        zzakb.m1419v(sb.toString());
        if (zzss()) {
            this.zzcwt.seekTo(i);
            this.zzcxc = 0;
            return;
        }
        this.zzcxc = i;
    }

    public final void setVideoPath(String str) {
        Uri parse = Uri.parse(str);
        zzhl zzd = zzhl.zzd(parse);
        if (zzd != null) {
            parse = Uri.parse(zzd.url);
        }
        this.zzcwu = parse;
        this.zzcxc = 0;
        zzsq();
        requestLayout();
        invalidate();
    }

    public final void stop() {
        zzakb.m1419v("AdMediaPlayerView stop");
        MediaPlayer mediaPlayer = this.zzcwt;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.zzcwt.release();
            this.zzcwt = null;
            zzag(0);
            this.zzcws = 0;
        }
        this.zzcwp.onStop();
    }

    public final String toString() {
        String name = getClass().getName();
        String hexString = Integer.toHexString(hashCode());
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 1 + String.valueOf(hexString).length());
        sb.append(name);
        sb.append("@");
        sb.append(hexString);
        return sb.toString();
    }

    public final void zza(float f, float f2) {
        zzapu zzapu = this.zzcxa;
        if (zzapu != null) {
            zzapu.zzb(f, f2);
        }
    }

    public final void zza(zzapf zzapf) {
        this.zzcxd = zzapf;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzah(int i) {
        zzapf zzapf = this.zzcxd;
        if (zzapf != null) {
            zzapf.onWindowVisibilityChanged(i);
        }
    }

    public final String zzsp() {
        String str = this.zzcxb ? " spherical" : "";
        String str2 = "MediaPlayer";
        return str.length() != 0 ? str2.concat(str) : new String(str2);
    }

    public final void zzst() {
        zza(this.zzcxl.getVolume());
    }
}
