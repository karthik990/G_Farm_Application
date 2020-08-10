package com.google.android.youtube.player;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;

public class YouTubeBaseActivity extends Activity {

    /* renamed from: a */
    private C2097a f1497a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public YouTubePlayerView f1498b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public int f1499c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public Bundle f1500d;

    /* renamed from: com.google.android.youtube.player.YouTubeBaseActivity$a */
    private final class C2097a implements C2770b {
        private C2097a() {
        }

        /* synthetic */ C2097a(YouTubeBaseActivity youTubeBaseActivity, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo19253a(YouTubePlayerView youTubePlayerView) {
            if (!(YouTubeBaseActivity.this.f1498b == null || YouTubeBaseActivity.this.f1498b == youTubePlayerView)) {
                YouTubeBaseActivity.this.f1498b.mo38008c(true);
            }
            YouTubeBaseActivity.this.f1498b = youTubePlayerView;
            if (YouTubeBaseActivity.this.f1499c > 0) {
                youTubePlayerView.mo37995a();
            }
            if (YouTubeBaseActivity.this.f1499c >= 2) {
                youTubePlayerView.mo38005b();
            }
        }

        /* renamed from: a */
        public final void mo19254a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener) {
            YouTubeBaseActivity youTubeBaseActivity = YouTubeBaseActivity.this;
            youTubePlayerView.mo37996a(youTubeBaseActivity, youTubePlayerView, str, onInitializedListener, youTubeBaseActivity.f1500d);
            YouTubeBaseActivity.this.f1500d = null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final C2770b mo19245a() {
        return this.f1497a;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1497a = new C2097a(this, 0);
        this.f1500d = bundle != null ? bundle.getBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE") : null;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        YouTubePlayerView youTubePlayerView = this.f1498b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo38006b(isFinishing());
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.f1499c = 1;
        YouTubePlayerView youTubePlayerView = this.f1498b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo38007c();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.f1499c = 2;
        YouTubePlayerView youTubePlayerView = this.f1498b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo38005b();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f1498b;
        bundle.putBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo38012e() : this.f1500d);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.f1499c = 1;
        YouTubePlayerView youTubePlayerView = this.f1498b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo37995a();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.f1499c = 0;
        YouTubePlayerView youTubePlayerView = this.f1498b;
        if (youTubePlayerView != null) {
            youTubePlayerView.mo38010d();
        }
        super.onStop();
    }
}
