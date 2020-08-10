package com.google.android.youtube.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.youtube.player.internal.C2772a;
import com.google.android.youtube.player.internal.C2773aa;
import com.google.android.youtube.player.internal.C2774ab;
import com.google.android.youtube.player.internal.C2776b;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;

public final class YouTubeThumbnailView extends ImageView {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public C2776b f1654a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C2772a f1655b;

    public interface OnInitializedListener {
        void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult);

        void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader);
    }

    /* renamed from: com.google.android.youtube.player.YouTubeThumbnailView$a */
    private static final class C2771a implements C2828a, C2829b {

        /* renamed from: a */
        private YouTubeThumbnailView f1656a;

        /* renamed from: b */
        private OnInitializedListener f1657b;

        public C2771a(YouTubeThumbnailView youTubeThumbnailView, OnInitializedListener onInitializedListener) {
            this.f1656a = (YouTubeThumbnailView) C2774ab.m1496a(youTubeThumbnailView, (Object) "thumbnailView cannot be null");
            this.f1657b = (OnInitializedListener) C2774ab.m1496a(onInitializedListener, (Object) "onInitializedlistener cannot be null");
        }

        /* renamed from: c */
        private void m1473c() {
            YouTubeThumbnailView youTubeThumbnailView = this.f1656a;
            if (youTubeThumbnailView != null) {
                youTubeThumbnailView.f1654a = null;
                this.f1656a = null;
                this.f1657b = null;
            }
        }

        /* renamed from: a */
        public final void mo38023a() {
            YouTubeThumbnailView youTubeThumbnailView = this.f1656a;
            if (youTubeThumbnailView != null && youTubeThumbnailView.f1654a != null) {
                this.f1656a.f1655b = C2773aa.m1490a().mo38055a(this.f1656a.f1654a, this.f1656a);
                OnInitializedListener onInitializedListener = this.f1657b;
                YouTubeThumbnailView youTubeThumbnailView2 = this.f1656a;
                onInitializedListener.onInitializationSuccess(youTubeThumbnailView2, youTubeThumbnailView2.f1655b);
                m1473c();
            }
        }

        /* renamed from: a */
        public final void mo38025a(YouTubeInitializationResult youTubeInitializationResult) {
            this.f1657b.onInitializationFailure(this.f1656a, youTubeInitializationResult);
            m1473c();
        }

        /* renamed from: b */
        public final void mo38024b() {
            m1473c();
        }
    }

    public YouTubeThumbnailView(Context context) {
        this(context, null);
    }

    public YouTubeThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubeThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        C2772a aVar = this.f1655b;
        if (aVar != null) {
            aVar.mo38047b();
            this.f1655b = null;
        }
        super.finalize();
    }

    public final void initialize(String str, OnInitializedListener onInitializedListener) {
        C2771a aVar = new C2771a(this, onInitializedListener);
        this.f1654a = C2773aa.m1490a().mo38056a(getContext(), str, aVar, aVar);
        this.f1654a.mo38171e();
    }
}
