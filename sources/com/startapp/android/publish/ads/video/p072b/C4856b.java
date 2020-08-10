package com.startapp.android.publish.ads.video.p072b;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.VideoView;
import com.startapp.android.publish.ads.video.C4869c;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4863c;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4867g;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4868h;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.ads.video.b.b */
/* compiled from: StartAppSDK */
public class C4856b extends C4855a implements OnCompletionListener, OnErrorListener, OnPreparedListener {

    /* renamed from: h */
    private MediaPlayer f2762h;

    /* renamed from: i */
    private VideoView f2763i;

    /* renamed from: com.startapp.android.publish.ads.video.b.b$a */
    /* compiled from: StartAppSDK */
    public enum C4858a {
        MEDIA_ERROR_IO,
        MEDIA_ERROR_MALFORMED,
        MEDIA_ERROR_UNSUPPORTED,
        MEDIA_ERROR_TIMED_OUT;

        /* renamed from: a */
        public static C4858a m2601a(int i) {
            if (i == -1010) {
                return MEDIA_ERROR_UNSUPPORTED;
            }
            if (i == -1007) {
                return MEDIA_ERROR_MALFORMED;
            }
            if (i == -1004) {
                return MEDIA_ERROR_IO;
            }
            if (i != -110) {
                return MEDIA_ERROR_IO;
            }
            return MEDIA_ERROR_TIMED_OUT;
        }
    }

    /* renamed from: com.startapp.android.publish.ads.video.b.b$b */
    /* compiled from: StartAppSDK */
    public enum C4859b {
        MEDIA_ERROR_UNKNOWN,
        MEDIA_ERROR_SERVER_DIED;

        /* renamed from: a */
        public static C4859b m2602a(int i) {
            if (i == 100) {
                return MEDIA_ERROR_SERVER_DIED;
            }
            return MEDIA_ERROR_UNKNOWN;
        }
    }

    public C4856b(VideoView videoView) {
        C5155g.m3807a("NativeVideoPlayer", 4, "Ctor");
        this.f2763i = videoView;
        this.f2763i.setOnPreparedListener(this);
        this.f2763i.setOnCompletionListener(this);
        this.f2763i.setOnErrorListener(this);
    }

    /* renamed from: a */
    public void mo61671a() {
        C5155g.m3807a("NativeVideoPlayer", 4, TtmlNode.START);
        this.f2763i.start();
    }

    /* renamed from: a */
    public void mo61672a(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("seekTo(");
        sb.append(i);
        sb.append(")");
        C5155g.m3807a("NativeVideoPlayer", 4, sb.toString());
        this.f2763i.seekTo(i);
    }

    /* renamed from: b */
    public void mo61674b() {
        C5155g.m3807a("NativeVideoPlayer", 4, "pause");
        this.f2763i.pause();
    }

    /* renamed from: c */
    public void mo61675c() {
        C5155g.m3807a("NativeVideoPlayer", 4, "stop");
        this.f2763i.stopPlayback();
    }

    /* renamed from: a */
    public void mo61673a(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("setMute(");
        sb.append(z);
        sb.append(")");
        C5155g.m3807a("NativeVideoPlayer", 4, sb.toString());
        MediaPlayer mediaPlayer = this.f2762h;
        if (mediaPlayer == null) {
            return;
        }
        if (z) {
            mediaPlayer.setVolume(0.0f, 0.0f);
        } else {
            mediaPlayer.setVolume(1.0f, 1.0f);
        }
    }

    /* renamed from: d */
    public int mo61676d() {
        return this.f2763i.getCurrentPosition();
    }

    /* renamed from: e */
    public int mo61677e() {
        return this.f2763i.getDuration();
    }

    /* renamed from: f */
    public boolean mo61678f() {
        return this.f2762h != null;
    }

    /* renamed from: a */
    public void mo61670a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("setVideoLocation(");
        sb.append(str);
        sb.append(")");
        C5155g.m3807a("NativeVideoPlayer", 4, sb.toString());
        super.mo61670a(str);
        this.f2763i.setVideoPath(this.f2755a);
    }

    /* renamed from: g */
    public void mo61679g() {
        if (this.f2762h != null) {
            this.f2762h = null;
        }
        C4869c.m2627a().mo61692a((C4863c) null);
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        String str = "NativeVideoPlayer";
        C5155g.m3807a(str, 4, "onPrepared");
        this.f2762h = mediaPlayer;
        if (this.f2756b != null) {
            C5155g.m3807a(str, 3, "Dispatching onPrepared");
            this.f2756b.mo61687a();
        }
        if (C4988c.m3132d(this.f2755a)) {
            MediaPlayer mediaPlayer2 = this.f2762h;
            if (mediaPlayer2 != null) {
                mediaPlayer2.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
                    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                        if (C4856b.this.f2760f != null) {
                            C5155g.m3807a("NativeVideoPlayer", 4, "onBufferingUpdate");
                            C4856b.this.f2760f.mo61684a(i);
                        }
                    }
                });
                return;
            }
        }
        if (!C4988c.m3132d(this.f2755a)) {
            C4869c.m2627a().mo61692a(this.f2760f);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        String str = "NativeVideoPlayer";
        C5155g.m3807a(str, 4, "onCompletion");
        if (this.f2758d != null) {
            C5155g.m3807a(str, 3, "Dispatching onCompletion");
            this.f2758d.mo61685a();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("onError(");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(")");
        String str = "NativeVideoPlayer";
        C5155g.m3807a(str, 6, sb.toString());
        if (this.f2757c == null) {
            return false;
        }
        C5155g.m3807a(str, 3, "Dispatching onError");
        return this.f2757c.mo61686a(m2590a(i, i2, mediaPlayer != null ? mediaPlayer.getCurrentPosition() : -1));
    }

    /* renamed from: a */
    private C4867g m2590a(int i, int i2, int i3) {
        return new C4867g(C4859b.m2602a(i) == C4859b.MEDIA_ERROR_SERVER_DIED ? C4868h.SERVER_DIED : C4868h.UNKNOWN, C4858a.m2601a(i2).toString(), i3);
    }
}
