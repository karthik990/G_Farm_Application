package com.pierfrancescosoffritti.androidyoutubeplayer.player;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackQuality;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackRate;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerError;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;
import java.util.Collection;
import org.apache.http.client.config.CookieSpecs;

public class YouTubePlayerBridge {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final YouTubePlayerBridgeCallbacks f2262a;

    /* renamed from: b */
    private final Handler f2263b = new Handler(Looper.getMainLooper());

    public interface YouTubePlayerBridgeCallbacks {
        Collection<YouTubePlayerListener> getListeners();

        void onYouTubeIframeAPIReady();
    }

    public YouTubePlayerBridge(YouTubePlayerBridgeCallbacks youTubePlayerBridgeCallbacks) {
        this.f2262a = youTubePlayerBridgeCallbacks;
    }

    /* renamed from: a */
    private PlayerState m2047a(String str) {
        return str.equalsIgnoreCase("UNSTARTED") ? PlayerState.UNSTARTED : str.equalsIgnoreCase("ENDED") ? PlayerState.ENDED : str.equalsIgnoreCase("PLAYING") ? PlayerState.PLAYING : str.equalsIgnoreCase("PAUSED") ? PlayerState.PAUSED : str.equalsIgnoreCase("BUFFERING") ? PlayerState.BUFFERING : str.equalsIgnoreCase("CUED") ? PlayerState.VIDEO_CUED : PlayerState.UNKNOWN;
    }

    /* renamed from: b */
    private PlaybackQuality m2049b(String str) {
        return str.equalsIgnoreCase("small") ? PlaybackQuality.SMALL : str.equalsIgnoreCase(Param.MEDIUM) ? PlaybackQuality.MEDIUM : str.equalsIgnoreCase("large") ? PlaybackQuality.LARGE : str.equalsIgnoreCase("hd720") ? PlaybackQuality.HD720 : str.equalsIgnoreCase("hd1080") ? PlaybackQuality.HD1080 : str.equalsIgnoreCase("highres") ? PlaybackQuality.HIGH_RES : str.equalsIgnoreCase(CookieSpecs.DEFAULT) ? PlaybackQuality.DEFAULT : PlaybackQuality.UNKNOWN;
    }

    /* renamed from: c */
    private PlaybackRate m2050c(String str) {
        return str.equalsIgnoreCase("0.25") ? PlaybackRate.RATE_0_25 : str.equalsIgnoreCase("0.5") ? PlaybackRate.RATE_0_5 : str.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) ? PlaybackRate.RATE_1 : str.equalsIgnoreCase("1.5") ? PlaybackRate.RATE_1_5 : str.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) ? PlaybackRate.RATE_2 : PlaybackRate.UNKNOWN;
    }

    /* renamed from: d */
    private PlayerError m2051d(String str) {
        return str.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) ? PlayerError.INVALID_PARAMETER_IN_REQUEST : str.equalsIgnoreCase("5") ? PlayerError.HTML_5_PLAYER : str.equalsIgnoreCase("100") ? PlayerError.VIDEO_NOT_FOUND : (!str.equalsIgnoreCase("101") && !str.equalsIgnoreCase("150")) ? PlayerError.UNKNOWN : PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER;
    }

    @JavascriptInterface
    public void sendApiChange() {
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onApiChange : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onApiChange.onApiChange();
                }
            }
        });
    }

    @JavascriptInterface
    public void sendError(String str) {
        final PlayerError d = m2051d(str);
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onError : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onError.onError(d);
                }
            }
        });
    }

    @JavascriptInterface
    public void sendPlaybackQualityChange(String str) {
        final PlaybackQuality b = m2049b(str);
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onPlaybackQualityChange : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onPlaybackQualityChange.onPlaybackQualityChange(b);
                }
            }
        });
    }

    @JavascriptInterface
    public void sendPlaybackRateChange(String str) {
        final PlaybackRate c = m2050c(str);
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onPlaybackRateChange : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onPlaybackRateChange.onPlaybackRateChange(c);
                }
            }
        });
    }

    @JavascriptInterface
    public void sendReady() {
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onReady : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onReady.onReady();
                }
            }
        });
    }

    @JavascriptInterface
    public void sendStateChange(String str) {
        final PlayerState a = m2047a(str);
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onStateChange : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onStateChange.onStateChange(a);
                }
            }
        });
    }

    @JavascriptInterface
    public void sendVideoCurrentTime(String str) {
        try {
            final float parseFloat = Float.parseFloat(str);
            this.f2263b.post(new Runnable() {
                public void run() {
                    for (YouTubePlayerListener onCurrentSecond : YouTubePlayerBridge.this.f2262a.getListeners()) {
                        onCurrentSecond.onCurrentSecond(parseFloat);
                    }
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void sendVideoDuration(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            final float parseFloat = Float.parseFloat(str);
            this.f2263b.post(new Runnable() {
                public void run() {
                    for (YouTubePlayerListener onVideoDuration : YouTubePlayerBridge.this.f2262a.getListeners()) {
                        onVideoDuration.onVideoDuration(parseFloat);
                    }
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void sendVideoId(final String str) {
        this.f2263b.post(new Runnable() {
            public void run() {
                for (YouTubePlayerListener onVideoId : YouTubePlayerBridge.this.f2262a.getListeners()) {
                    onVideoId.onVideoId(str);
                }
            }
        });
    }

    @JavascriptInterface
    public void sendVideoLoadedFraction(String str) {
        try {
            final float parseFloat = Float.parseFloat(str);
            this.f2263b.post(new Runnable() {
                public void run() {
                    for (YouTubePlayerListener onVideoLoadedFraction : YouTubePlayerBridge.this.f2262a.getListeners()) {
                        onVideoLoadedFraction.onVideoLoadedFraction(parseFloat);
                    }
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void sendYouTubeIframeAPIReady() {
        this.f2262a.onYouTubeIframeAPIReady();
    }
}
