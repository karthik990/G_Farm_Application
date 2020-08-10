package com.google.android.youtube.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.internal.C2774ab;

public class YouTubePlayerSupportFragment extends Fragment implements Provider {

    /* renamed from: a */
    private final C2766a f1630a = new C2766a(this, 0);

    /* renamed from: b */
    private Bundle f1631b;

    /* renamed from: c */
    private YouTubePlayerView f1632c;

    /* renamed from: d */
    private String f1633d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public OnInitializedListener f1634e;

    /* renamed from: f */
    private boolean f1635f;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerSupportFragment$a */
    private final class C2766a implements C2770b {
        private C2766a() {
        }

        /* synthetic */ C2766a(YouTubePlayerSupportFragment youTubePlayerSupportFragment, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo19253a(YouTubePlayerView youTubePlayerView) {
        }

        /* renamed from: a */
        public final void mo19254a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener) {
            YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.this;
            youTubePlayerSupportFragment.initialize(str, youTubePlayerSupportFragment.f1634e);
        }
    }

    /* renamed from: a */
    private void m1438a() {
        YouTubePlayerView youTubePlayerView = this.f1632c;
        if (youTubePlayerView != null && this.f1634e != null) {
            youTubePlayerView.mo37997a(this.f1635f);
            this.f1632c.mo37996a(getActivity(), this, this.f1633d, this.f1634e, this.f1631b);
            this.f1631b = null;
            this.f1634e = null;
        }
    }

    public static YouTubePlayerSupportFragment newInstance() {
        return new YouTubePlayerSupportFragment();
    }

    public void initialize(String str, OnInitializedListener onInitializedListener) {
        this.f1633d = C2774ab.m1497a(str, (Object) "Developer key cannot be null or empty");
        this.f1634e = onInitializedListener;
        m1438a();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1631b = bundle != null ? bundle.getBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE") : null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f1632c = new YouTubePlayerView(getActivity(), null, 0, this.f1630a);
        m1438a();
        return this.f1632c;
    }

    public void onDestroy() {
        if (this.f1632c != null) {
            FragmentActivity activity = getActivity();
            this.f1632c.mo38006b(activity == null || activity.isFinishing());
        }
        super.onDestroy();
    }

    public void onDestroyView() {
        this.f1632c.mo38008c(getActivity().isFinishing());
        this.f1632c = null;
        super.onDestroyView();
    }

    public void onPause() {
        this.f1632c.mo38007c();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.f1632c.mo38005b();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.f1632c;
        bundle.putBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.mo38012e() : this.f1631b);
    }

    public void onStart() {
        super.onStart();
        this.f1632c.mo37995a();
    }

    public void onStop() {
        this.f1632c.mo38010d();
        super.onStop();
    }
}
