package com.pierfrancescosoffritti.androidyoutubeplayer.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.mobiroller.constants.Constants;
import com.pierfrancescosoffritti.androidyoutubeplayer.C4571R;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerBridge.YouTubePlayerBridgeCallbacks;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.player.a */
class C4587a extends WebView implements YouTubePlayer, YouTubePlayerBridgeCallbacks {

    /* renamed from: a */
    private final Set<YouTubePlayerListener> f2282a;

    /* renamed from: b */
    private final Handler f2283b;

    /* renamed from: c */
    private YouTubePlayerInitListener f2284c;

    protected C4587a(Context context) {
        this(context, null);
    }

    protected C4587a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    protected C4587a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f2283b = new Handler(Looper.getMainLooper());
        this.f2282a = new HashSet();
    }

    /* renamed from: a */
    private void m2052a() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(2);
        settings.setMediaPlaybackRequiresUserGesture(false);
        addJavascriptInterface(new YouTubePlayerBridge(this), "YouTubePlayerBridge");
        loadDataWithBaseURL("https://www.youtube.com", m2053b(), "text/html", "utf-8", null);
        setWebChromeClient(new WebChromeClient() {
            public Bitmap getDefaultVideoPoster() {
                Bitmap defaultVideoPoster = super.getDefaultVideoPoster();
                return defaultVideoPoster == null ? Bitmap.createBitmap(1, 1, Config.RGB_565) : defaultVideoPoster;
            }
        });
    }

    /* renamed from: b */
    private String m2053b() {
        try {
            InputStream openRawResource = getResources().openRawResource(C4571R.raw.youtube_player);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openRawResource, "utf-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append(Constants.NEW_LINE);
                } else {
                    openRawResource.close();
                    return sb.toString();
                }
            }
        } catch (Exception unused) {
            throw new RuntimeException("Can't parse HTML file containing the player.");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo58863a(YouTubePlayerInitListener youTubePlayerInitListener) {
        this.f2284c = youTubePlayerInitListener;
        m2052a();
    }

    public boolean addListener(YouTubePlayerListener youTubePlayerListener) {
        if (youTubePlayerListener != null) {
            return this.f2282a.add(youTubePlayerListener);
        }
        Log.e("YouTubePlayer", "null YouTubePlayerListener not allowed.");
        return false;
    }

    public void cueVideo(final String str, final float f) {
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a aVar = C4587a.this;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:cueVideo('");
                sb.append(str);
                sb.append("', ");
                sb.append(f);
                sb.append(")");
                aVar.loadUrl(sb.toString());
            }
        });
    }

    public void destroy() {
        this.f2282a.clear();
        this.f2283b.removeCallbacksAndMessages(null);
        super.destroy();
    }

    public Collection<YouTubePlayerListener> getListeners() {
        return Collections.unmodifiableCollection(new HashSet(this.f2282a));
    }

    public void loadVideo(final String str, final float f) {
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a aVar = C4587a.this;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:loadVideo('");
                sb.append(str);
                sb.append("', ");
                sb.append(f);
                sb.append(")");
                aVar.loadUrl(sb.toString());
            }
        });
    }

    public void onYouTubeIframeAPIReady() {
        this.f2284c.onInitSuccess(this);
    }

    public void pause() {
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a.this.loadUrl("javascript:pauseVideo()");
            }
        });
    }

    public void play() {
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a.this.loadUrl("javascript:playVideo()");
            }
        });
    }

    public boolean removeListener(YouTubePlayerListener youTubePlayerListener) {
        return this.f2282a.remove(youTubePlayerListener);
    }

    public void seekTo(final float f) {
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a aVar = C4587a.this;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:seekTo(");
                sb.append(f);
                sb.append(")");
                aVar.loadUrl(sb.toString());
            }
        });
    }

    public void setVolume(final int i) {
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.f2283b.post(new Runnable() {
            public void run() {
                C4587a aVar = C4587a.this;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:setVolume(");
                sb.append(i);
                sb.append(")");
                aVar.loadUrl(sb.toString());
            }
        });
    }
}
