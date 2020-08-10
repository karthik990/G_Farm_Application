package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.internal.C2774ab;

public class YouTubePlayerFragment extends Fragment implements Provider {

    /* renamed from: a */
    private final C2765a f1623a = new C2765a(this, 0);

    /* renamed from: b */
    private Bundle f1624b;

    /* renamed from: c */
    private YouTubePlayerView f1625c;

    /* renamed from: d */
    private String f1626d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public OnInitializedListener f1627e;

    /* renamed from: f */
    private boolean f1628f;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerFragment$a */
    private final class C2765a implements C2770b {
        private C2765a() {
        }

        /* synthetic */ C2765a(YouTubePlayerFragment youTubePlayerFragment, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo19253a(YouTubePlayerView youTubePlayerView) {
        }

        /* renamed from: a */
        public final void mo19254a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener) {
            YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.this;
            youTubePlayerFragment.initialize(str, youTubePlayerFragment.f1627e);
        }
    }

    /* renamed from: a */
    private void m1434a() {
        YouTubePlayerView youTubePlayerView = this.f1625c;
        if (youTubePlayerView != null && this.f1627e != null) {
            youTubePlayerView.mo37997a(this.f1628f);
            this.f1625c.mo37996a(getActivity(), this, this.f1626d, this.f1627e, this.f1624b);
            this.f1624b = null;
            this.f1627e = null;
        }
    }

    public static YouTubePlayerFragment newInstance() {
        return new YouTubePlayerFragment();
    }

    public void initialize(String str, OnInitializedListener onInitializedListener) {
        this.f1626d = C2774ab.m1497a(str, (Object) "Developer key cannot be null or empty");
        this.f1627e = onInitializedListener;
        m1434a();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1624b = bundle != null ? bundle.getBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE") : null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f1625c = new YouTubePlayerView(getActivity(), null, 0, this.f1623a);
        m1434a();
        return this.f1625c;
    }

    public void onDestroy() {
        if (this.f1625c != null) {
            Activity activity = getActivity();
            this.f1625c.mo38006b(activity == null || activity.isFinishing());
        }
        super.onDestroy();
    }

    public void onDestroyView() {
        this.f1625c.mo38008c(getActivity().isFinishing());
        this.f1625c = null;
        super.onDestroyView();
    }

    public void onPause() {
        this.f1625c.mo38007c();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.f1625c.mo38005b();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f1625c;
        bundle.putBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo38012e() : this.f1624b);
    }

    public void onStart() {
        super.onStart();
        this.f1625c.mo37995a();
    }

    public void onStop() {
        this.f1625c.mo38010d();
        super.onStop();
    }
}
