package com.google.android.youtube.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import androidx.core.view.ViewCompat;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.internal.C2773aa;
import com.google.android.youtube.player.internal.C2774ab;
import com.google.android.youtube.player.internal.C2776b;
import com.google.android.youtube.player.internal.C2808n;
import com.google.android.youtube.player.internal.C2822s;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;
import com.google.android.youtube.player.internal.C2834w.C2835a;
import com.google.android.youtube.player.internal.C2837y;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class YouTubePlayerView extends ViewGroup implements Provider {

    /* renamed from: a */
    private final C2769a f1637a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Set<View> f1638b;

    /* renamed from: c */
    private final C2770b f1639c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public C2776b f1640d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C2822s f1641e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public View f1642f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public C2808n f1643g;

    /* renamed from: h */
    private Provider f1644h;

    /* renamed from: i */
    private Bundle f1645i;

    /* renamed from: j */
    private OnInitializedListener f1646j;

    /* renamed from: k */
    private boolean f1647k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean f1648l;

    /* renamed from: com.google.android.youtube.player.YouTubePlayerView$a */
    private final class C2769a implements OnGlobalFocusChangeListener {
        private C2769a() {
        }

        /* synthetic */ C2769a(YouTubePlayerView youTubePlayerView, byte b) {
            this();
        }

        public final void onGlobalFocusChanged(View view, View view2) {
            if (YouTubePlayerView.this.f1641e != null && YouTubePlayerView.this.f1638b.contains(view2) && !YouTubePlayerView.this.f1638b.contains(view)) {
                YouTubePlayerView.this.f1641e.mo38195g();
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.YouTubePlayerView$b */
    interface C2770b {
        /* renamed from: a */
        void mo19253a(YouTubePlayerView youTubePlayerView);

        /* renamed from: a */
        void mo19254a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener);
    }

    public YouTubePlayerView(Context context) {
        this(context, null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet, int i) {
        if (context instanceof YouTubeBaseActivity) {
            this(context, attributeSet, i, ((YouTubeBaseActivity) context).mo19245a());
            return;
        }
        throw new IllegalStateException("A YouTubePlayerView can only be created with an Activity  which extends YouTubeBaseActivity as its context.");
    }

    YouTubePlayerView(Context context, AttributeSet attributeSet, int i, C2770b bVar) {
        super((Context) C2774ab.m1496a(context, (Object) "context cannot be null"), attributeSet, i);
        this.f1639c = (C2770b) C2774ab.m1496a(bVar, (Object) "listener cannot be null");
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        setClipToPadding(false);
        this.f1643g = new C2808n(context);
        requestTransparentRegion(this.f1643g);
        addView(this.f1643g);
        this.f1638b = new HashSet();
        this.f1637a = new C2769a(this, 0);
    }

    /* renamed from: a */
    private void m1442a(View view) {
        if (!(view == this.f1643g || (this.f1641e != null && view == this.f1642f))) {
            throw new UnsupportedOperationException("No views can be added on top of the player");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1443a(YouTubeInitializationResult youTubeInitializationResult) {
        this.f1641e = null;
        this.f1643g.mo38160c();
        OnInitializedListener onInitializedListener = this.f1646j;
        if (onInitializedListener != null) {
            onInitializedListener.onInitializationFailure(this.f1644h, youTubeInitializationResult);
            this.f1646j = null;
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m1444a(YouTubePlayerView youTubePlayerView, Activity activity) {
        try {
            youTubePlayerView.f1641e = new C2822s(youTubePlayerView.f1640d, C2773aa.m1490a().mo38057a(activity, youTubePlayerView.f1640d, youTubePlayerView.f1647k));
            youTubePlayerView.f1642f = youTubePlayerView.f1641e.mo38183a();
            youTubePlayerView.addView(youTubePlayerView.f1642f);
            youTubePlayerView.removeView(youTubePlayerView.f1643g);
            youTubePlayerView.f1639c.mo19253a(youTubePlayerView);
            if (youTubePlayerView.f1646j != null) {
                boolean z = false;
                Bundle bundle = youTubePlayerView.f1645i;
                if (bundle != null) {
                    z = youTubePlayerView.f1641e.mo38187a(bundle);
                    youTubePlayerView.f1645i = null;
                }
                youTubePlayerView.f1646j.onInitializationSuccess(youTubePlayerView.f1644h, youTubePlayerView.f1641e, z);
                youTubePlayerView.f1646j = null;
            }
        } catch (C2835a e) {
            C2837y.m1746a("Error creating YouTubePlayerView", (Throwable) e);
            youTubePlayerView.m1443a(YouTubeInitializationResult.INTERNAL_ERROR);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo37995a() {
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38188b();
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo37996a(final Activity activity, Provider provider, String str, OnInitializedListener onInitializedListener, Bundle bundle) {
        if (this.f1641e == null && this.f1646j == null) {
            C2774ab.m1496a(activity, (Object) "activity cannot be null");
            this.f1644h = (Provider) C2774ab.m1496a(provider, (Object) "provider cannot be null");
            this.f1646j = (OnInitializedListener) C2774ab.m1496a(onInitializedListener, (Object) "listener cannot be null");
            this.f1645i = bundle;
            this.f1643g.mo38159b();
            this.f1640d = C2773aa.m1490a().mo38056a(getContext(), str, new C2828a() {
                /* renamed from: a */
                public final void mo38023a() {
                    if (YouTubePlayerView.this.f1640d != null) {
                        YouTubePlayerView.m1444a(YouTubePlayerView.this, activity);
                    }
                    YouTubePlayerView.this.f1640d = null;
                }

                /* renamed from: b */
                public final void mo38024b() {
                    if (!YouTubePlayerView.this.f1648l && YouTubePlayerView.this.f1641e != null) {
                        YouTubePlayerView.this.f1641e.mo38194f();
                    }
                    YouTubePlayerView.this.f1643g.mo38158a();
                    YouTubePlayerView youTubePlayerView = YouTubePlayerView.this;
                    if (youTubePlayerView.indexOfChild(youTubePlayerView.f1643g) < 0) {
                        YouTubePlayerView youTubePlayerView2 = YouTubePlayerView.this;
                        youTubePlayerView2.addView(youTubePlayerView2.f1643g);
                        YouTubePlayerView youTubePlayerView3 = YouTubePlayerView.this;
                        youTubePlayerView3.removeView(youTubePlayerView3.f1642f);
                    }
                    YouTubePlayerView.this.f1642f = null;
                    YouTubePlayerView.this.f1641e = null;
                    YouTubePlayerView.this.f1640d = null;
                }
            }, new C2829b() {
                /* renamed from: a */
                public final void mo38025a(YouTubeInitializationResult youTubeInitializationResult) {
                    YouTubePlayerView.this.m1443a(youTubeInitializationResult);
                    YouTubePlayerView.this.f1640d = null;
                }
            });
            this.f1640d.mo38171e();
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo37997a(boolean z) {
        if (!z || VERSION.SDK_INT >= 14) {
            this.f1647k = z;
            return;
        }
        C2837y.m1747a("Could not enable TextureView because API level is lower than 14", new Object[0]);
        this.f1647k = false;
    }

    public final void addFocusables(ArrayList<View> arrayList, int i) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i);
        arrayList.addAll(arrayList2);
        this.f1638b.clear();
        this.f1638b.addAll(arrayList2);
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i, i2);
        arrayList.addAll(arrayList2);
        this.f1638b.clear();
        this.f1638b.addAll(arrayList2);
    }

    public final void addView(View view) {
        m1442a(view);
        super.addView(view);
    }

    public final void addView(View view, int i) {
        m1442a(view);
        super.addView(view, i);
    }

    public final void addView(View view, int i, int i2) {
        m1442a(view);
        super.addView(view, i, i2);
    }

    public final void addView(View view, int i, LayoutParams layoutParams) {
        m1442a(view);
        super.addView(view, i, layoutParams);
    }

    public final void addView(View view, LayoutParams layoutParams) {
        m1442a(view);
        super.addView(view, layoutParams);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo38005b() {
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38191c();
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo38006b(boolean z) {
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38189b(z);
            mo38008c(z);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final void mo38007c() {
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38192d();
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final void mo38008c(boolean z) {
        this.f1648l = true;
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38185a(z);
        }
    }

    public final void clearChildFocus(View view) {
        if (hasFocusable()) {
            requestFocus();
        } else {
            super.clearChildFocus(view);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public final void mo38010d() {
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38193e();
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.f1641e != null) {
            if (keyEvent.getAction() == 0) {
                return this.f1641e.mo38186a(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
            if (keyEvent.getAction() == 1) {
                return this.f1641e.mo38190b(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public final Bundle mo38012e() {
        C2822s sVar = this.f1641e;
        return sVar == null ? this.f1645i : sVar.mo38196h();
    }

    public final void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
        this.f1638b.add(view);
    }

    public final void initialize(String str, OnInitializedListener onInitializedListener) {
        C2774ab.m1497a(str, (Object) "Developer key cannot be null or empty");
        this.f1639c.mo19254a(this, str, onInitializedListener);
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.f1637a);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C2822s sVar = this.f1641e;
        if (sVar != null) {
            sVar.mo38184a(configuration);
        }
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.f1637a);
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() > 0) {
            getChildAt(0).layout(0, 0, i3 - i, i4 - i2);
        }
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            childAt.measure(i, i2);
            setMeasuredDimension(childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            return;
        }
        setMeasuredDimension(0, 0);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        this.f1638b.add(view2);
    }

    public final void setClipToPadding(boolean z) {
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
    }
}
