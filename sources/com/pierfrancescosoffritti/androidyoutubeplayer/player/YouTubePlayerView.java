package com.pierfrancescosoffritti.androidyoutubeplayer.player;

import android.content.Context;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.DefaultPlayerUIController;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.PlayerUIController;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.FullScreenHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.PlaybackResumer;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.C4603Utils;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.Callable;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.NetworkReceiver;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.NetworkReceiver.NetworkListener;

public class YouTubePlayerView extends FrameLayout implements LifecycleObserver, NetworkListener {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final C4587a f1506a;

    /* renamed from: b */
    private DefaultPlayerUIController f1507b;

    /* renamed from: c */
    private final NetworkReceiver f1508c;

    /* renamed from: d */
    private final PlaybackResumer f1509d;

    /* renamed from: e */
    private final FullScreenHelper f1510e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public Callable f1511f;

    public YouTubePlayerView(Context context) {
        this(context, null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1506a = new C4587a(context);
        addView(this.f1506a, new LayoutParams(-1, -1));
        this.f1507b = new DefaultPlayerUIController(this, this.f1506a);
        this.f1509d = new PlaybackResumer();
        this.f1508c = new NetworkReceiver(this);
        this.f1510e = new FullScreenHelper();
        this.f1510e.addFullScreenListener(this.f1507b);
        m1283a((YouTubePlayer) this.f1506a);
    }

    /* renamed from: a */
    private void m1283a(YouTubePlayer youTubePlayer) {
        DefaultPlayerUIController defaultPlayerUIController = this.f1507b;
        if (defaultPlayerUIController != null) {
            youTubePlayer.addListener(defaultPlayerUIController);
        }
        youTubePlayer.addListener(this.f1509d);
        youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
            public void onReady() {
                YouTubePlayerView.this.f1511f = null;
            }
        });
    }

    public boolean addFullScreenListener(YouTubePlayerFullScreenListener youTubePlayerFullScreenListener) {
        return this.f1510e.addFullScreenListener(youTubePlayerFullScreenListener);
    }

    public void enterFullScreen() {
        this.f1510e.enterFullScreen(this);
    }

    public void exitFullScreen() {
        this.f1510e.exitFullScreen(this);
    }

    public PlayerUIController getPlayerUIController() {
        DefaultPlayerUIController defaultPlayerUIController = this.f1507b;
        if (defaultPlayerUIController != null) {
            return defaultPlayerUIController;
        }
        throw new RuntimeException("You have inflated a custom player UI. You must manage it with your own controller.");
    }

    public View inflateCustomPlayerUI(int i) {
        removeViews(1, getChildCount() - 1);
        DefaultPlayerUIController defaultPlayerUIController = this.f1507b;
        if (defaultPlayerUIController != null) {
            this.f1506a.removeListener(defaultPlayerUIController);
            this.f1510e.removeFullScreenListener(this.f1507b);
        }
        this.f1507b = null;
        return View.inflate(getContext(), i, this);
    }

    public void initialize(final YouTubePlayerInitListener youTubePlayerInitListener, boolean z) {
        if (z) {
            getContext().registerReceiver(this.f1508c, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        this.f1511f = new Callable() {
            public void call() {
                YouTubePlayerView.this.f1506a.mo58863a(new YouTubePlayerInitListener() {
                    public void onInitSuccess(YouTubePlayer youTubePlayer) {
                        youTubePlayerInitListener.onInitSuccess(youTubePlayer);
                    }
                });
            }
        };
        if (C4603Utils.isOnline(getContext())) {
            this.f1511f.call();
        }
    }

    public boolean isFullScreen() {
        return this.f1510e.isFullScreen();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (getLayoutParams().height == -2) {
            i2 = MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(i) * 9) / 16, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    public void onNetworkAvailable() {
        Callable callable = this.f1511f;
        if (callable != null) {
            callable.call();
        } else {
            this.f1509d.resume(this.f1506a);
        }
    }

    public void onNetworkUnavailable() {
    }

    /* access modifiers changed from: 0000 */
    @OnLifecycleEvent(Event.ON_STOP)
    public void onStop() {
        this.f1506a.pause();
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void release() {
        removeView(this.f1506a);
        this.f1506a.removeAllViews();
        this.f1506a.destroy();
        try {
            getContext().unregisterReceiver(this.f1508c);
        } catch (Exception unused) {
        }
    }

    public boolean removeFullScreenListener(YouTubePlayerFullScreenListener youTubePlayerFullScreenListener) {
        return this.f1510e.removeFullScreenListener(youTubePlayerFullScreenListener);
    }

    public void toggleFullScreen() {
        this.f1510e.toggleFullScreen(this);
    }
}
