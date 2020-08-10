package com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerError;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;

public class PlaybackResumer extends AbstractYouTubePlayerListener {

    /* renamed from: a */
    private boolean f2300a = false;

    /* renamed from: b */
    private PlayerError f2301b = null;

    /* renamed from: c */
    private String f2302c;

    /* renamed from: d */
    private float f2303d;

    /* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.PlaybackResumer$1 */
    static /* synthetic */ class C45951 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2304a = new int[PlayerState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState[] r0 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2304a = r0
                int[] r0 = f2304a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.ENDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2304a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.PAUSED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2304a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.PLAYING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.PlaybackResumer.C45951.<clinit>():void");
        }
    }

    public void onCurrentSecond(float f) {
        this.f2303d = f;
    }

    public void onError(PlayerError playerError) {
        if (playerError == PlayerError.HTML_5_PLAYER) {
            this.f2301b = playerError;
        }
    }

    public void onStateChange(PlayerState playerState) {
        int i = C45951.f2304a[playerState.ordinal()];
        if (i == 1) {
            this.f2300a = false;
        } else if (i == 2) {
            this.f2300a = false;
        } else if (i == 3) {
            this.f2300a = true;
        }
    }

    public void onVideoId(String str) {
        this.f2302c = str;
    }

    public void resume(YouTubePlayer youTubePlayer) {
        if (this.f2300a && this.f2301b == PlayerError.HTML_5_PLAYER) {
            youTubePlayer.loadVideo(this.f2302c, this.f2303d);
        } else if (!this.f2300a && this.f2301b == PlayerError.HTML_5_PLAYER) {
            youTubePlayer.cueVideo(this.f2302c, this.f2303d);
        }
        this.f2301b = null;
    }
}
