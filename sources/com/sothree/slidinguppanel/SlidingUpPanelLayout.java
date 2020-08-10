package com.sothree.slidinguppanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.sothree.slidinguppanel.ViewDragHelper.Callback;
import java.util.List;

public class SlidingUpPanelLayout extends ViewGroup {
    private static final float DEFAULT_ANCHOR_POINT = 1.0f;
    private static final int[] DEFAULT_ATTRS = {16842927};
    private static final boolean DEFAULT_CLIP_PANEL_FLAG = true;
    private static final int DEFAULT_FADE_COLOR = -1728053248;
    private static final int DEFAULT_MIN_FLING_VELOCITY = 400;
    private static final boolean DEFAULT_OVERLAY_FLAG = false;
    private static final int DEFAULT_PANEL_HEIGHT = 68;
    private static final int DEFAULT_PARALLAX_OFFSET = 0;
    private static final int DEFAULT_SHADOW_HEIGHT = 4;
    private static PanelState DEFAULT_SLIDE_STATE = PanelState.COLLAPSED;
    public static final String SLIDING_STATE = "sliding_state";
    private static final String TAG = SlidingUpPanelLayout.class.getSimpleName();
    /* access modifiers changed from: private */
    public float mAnchorPoint;
    private boolean mClipPanel;
    private int mCoveredFadeColor;
    private final Paint mCoveredFadePaint;
    /* access modifiers changed from: private */
    public final ViewDragHelper mDragHelper;
    private View mDragView;
    private int mDragViewResId;
    private OnClickListener mFadeOnClickListener;
    private boolean mFirstLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsScrollableViewHandlingTouch;
    /* access modifiers changed from: private */
    public boolean mIsSlidingUp;
    private boolean mIsTouchEnabled;
    /* access modifiers changed from: private */
    public boolean mIsUnableToDrag;
    private PanelState mLastNotDraggingSlideState;
    private View mMainView;
    private int mMinFlingVelocity;
    private boolean mOverlayContent;
    private int mPanelHeight;
    private final List<PanelSlideListener> mPanelSlideListeners;
    private int mParallaxOffset;
    private float mPrevMotionX;
    private float mPrevMotionY;
    private View mScrollableView;
    private ScrollableViewHelper mScrollableViewHelper;
    private int mScrollableViewResId;
    private final Drawable mShadowDrawable;
    private int mShadowHeight;
    /* access modifiers changed from: private */
    public float mSlideOffset;
    /* access modifiers changed from: private */
    public int mSlideRange;
    /* access modifiers changed from: private */
    public PanelState mSlideState;
    /* access modifiers changed from: private */
    public View mSlideableView;
    private final Rect mTmpRect;

    /* renamed from: com.sothree.slidinguppanel.SlidingUpPanelLayout$2 */
    static /* synthetic */ class C46232 {

        /* renamed from: $SwitchMap$com$sothree$slidinguppanel$SlidingUpPanelLayout$PanelState */
        static final /* synthetic */ int[] f2363x1974508 = new int[PanelState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState[] r0 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2363x1974508 = r0
                int[] r0 = f2363x1974508     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r1 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2363x1974508     // Catch:{ NoSuchFieldError -> 0x001f }
                com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r1 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.ANCHORED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2363x1974508     // Catch:{ NoSuchFieldError -> 0x002a }
                com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r1 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2363x1974508     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r1 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.C46232.<clinit>():void");
        }
    }

    private class DragHelperCallback extends Callback {
        private DragHelperCallback() {
        }

        public boolean tryCaptureView(View view, int i) {
            return !SlidingUpPanelLayout.this.mIsUnableToDrag && view == SlidingUpPanelLayout.this.mSlideableView;
        }

        public void onViewDragStateChanged(int i) {
            if (SlidingUpPanelLayout.this.mDragHelper != null && SlidingUpPanelLayout.this.mDragHelper.getViewDragState() == 0) {
                SlidingUpPanelLayout slidingUpPanelLayout = SlidingUpPanelLayout.this;
                slidingUpPanelLayout.mSlideOffset = slidingUpPanelLayout.computeSlideOffset(slidingUpPanelLayout.mSlideableView.getTop());
                SlidingUpPanelLayout.this.applyParallaxForCurrentSlideOffset();
                if (SlidingUpPanelLayout.this.mSlideOffset == 1.0f) {
                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.EXPANDED);
                } else if (SlidingUpPanelLayout.this.mSlideOffset == 0.0f) {
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.COLLAPSED);
                } else if (SlidingUpPanelLayout.this.mSlideOffset < 0.0f) {
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.HIDDEN);
                    SlidingUpPanelLayout.this.mSlideableView.setVisibility(4);
                } else {
                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.ANCHORED);
                }
            }
        }

        public void onViewCaptured(View view, int i) {
            SlidingUpPanelLayout.this.setAllChildrenVisible();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            SlidingUpPanelLayout.this.onPanelDragged(i2);
            SlidingUpPanelLayout.this.invalidate();
        }

        public void onViewReleased(View view, float f, float f2) {
            int i;
            if (SlidingUpPanelLayout.this.mIsSlidingUp) {
                f2 = -f2;
            }
            if (f2 > 0.0f && SlidingUpPanelLayout.this.mSlideOffset <= SlidingUpPanelLayout.this.mAnchorPoint) {
                SlidingUpPanelLayout slidingUpPanelLayout = SlidingUpPanelLayout.this;
                i = slidingUpPanelLayout.computePanelTopPosition(slidingUpPanelLayout.mAnchorPoint);
            } else if (f2 > 0.0f && SlidingUpPanelLayout.this.mSlideOffset > SlidingUpPanelLayout.this.mAnchorPoint) {
                i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
            } else if (f2 < 0.0f && SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint) {
                SlidingUpPanelLayout slidingUpPanelLayout2 = SlidingUpPanelLayout.this;
                i = slidingUpPanelLayout2.computePanelTopPosition(slidingUpPanelLayout2.mAnchorPoint);
            } else if (f2 < 0.0f && SlidingUpPanelLayout.this.mSlideOffset < SlidingUpPanelLayout.this.mAnchorPoint) {
                i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0f);
            } else if (SlidingUpPanelLayout.this.mSlideOffset >= (SlidingUpPanelLayout.this.mAnchorPoint + 1.0f) / 2.0f) {
                i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
            } else if (SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint / 2.0f) {
                SlidingUpPanelLayout slidingUpPanelLayout3 = SlidingUpPanelLayout.this;
                i = slidingUpPanelLayout3.computePanelTopPosition(slidingUpPanelLayout3.mAnchorPoint);
            } else {
                i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0f);
            }
            if (SlidingUpPanelLayout.this.mDragHelper != null) {
                SlidingUpPanelLayout.this.mDragHelper.settleCapturedViewAt(view.getLeft(), i);
            }
            SlidingUpPanelLayout.this.invalidate();
        }

        public int getViewVerticalDragRange(View view) {
            return SlidingUpPanelLayout.this.mSlideRange;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            int access$1200 = SlidingUpPanelLayout.this.computePanelTopPosition(0.0f);
            int access$12002 = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
            if (SlidingUpPanelLayout.this.mIsSlidingUp) {
                return Math.min(Math.max(i, access$12002), access$1200);
            }
            return Math.min(Math.max(i, access$1200), access$12002);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] ATTRS = {16843137};
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.weight = f;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
            if (obtainStyledAttributes != null) {
                this.weight = obtainStyledAttributes.getFloat(0, 0.0f);
                obtainStyledAttributes.recycle();
            }
        }
    }

    public interface PanelSlideListener {
        void onPanelSlide(View view, float f);

        void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2);
    }

    public enum PanelState {
        EXPANDED,
        COLLAPSED,
        ANCHORED,
        HIDDEN,
        DRAGGING
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        public void onPanelSlide(View view, float f) {
        }

        public void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
        }
    }

    public SlidingUpPanelLayout(Context context) {
        this(context, null);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0133  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SlidingUpPanelLayout(android.content.Context r9, android.util.AttributeSet r10, int r11) {
        /*
            r8 = this;
            r8.<init>(r9, r10, r11)
            r11 = 400(0x190, float:5.6E-43)
            r8.mMinFlingVelocity = r11
            r0 = -1728053248(0xffffffff99000000, float:-6.617445E-24)
            r8.mCoveredFadeColor = r0
            android.graphics.Paint r1 = new android.graphics.Paint
            r1.<init>()
            r8.mCoveredFadePaint = r1
            r1 = -1
            r8.mPanelHeight = r1
            r8.mShadowHeight = r1
            r8.mParallaxOffset = r1
            r2 = 0
            r8.mOverlayContent = r2
            r3 = 1
            r8.mClipPanel = r3
            r8.mDragViewResId = r1
            com.sothree.slidinguppanel.ScrollableViewHelper r4 = new com.sothree.slidinguppanel.ScrollableViewHelper
            r4.<init>()
            r8.mScrollableViewHelper = r4
            com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r4 = DEFAULT_SLIDE_STATE
            r8.mSlideState = r4
            r8.mLastNotDraggingSlideState = r4
            r4 = 1065353216(0x3f800000, float:1.0)
            r8.mAnchorPoint = r4
            r8.mIsScrollableViewHandlingTouch = r2
            java.util.concurrent.CopyOnWriteArrayList r5 = new java.util.concurrent.CopyOnWriteArrayList
            r5.<init>()
            r8.mPanelSlideListeners = r5
            r8.mFirstLayout = r3
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>()
            r8.mTmpRect = r5
            boolean r5 = r8.isInEditMode()
            r6 = 0
            if (r5 == 0) goto L_0x0050
            r8.mShadowDrawable = r6
            r8.mDragHelper = r6
            return
        L_0x0050:
            if (r10 == 0) goto L_0x00e2
            int[] r5 = DEFAULT_ATTRS
            android.content.res.TypedArray r5 = r9.obtainStyledAttributes(r10, r5)
            if (r5 == 0) goto L_0x0064
            int r7 = r5.getInt(r2, r2)
            r8.setGravity(r7)
            r5.recycle()
        L_0x0064:
            int[] r5 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout
            android.content.res.TypedArray r10 = r9.obtainStyledAttributes(r10, r5)
            if (r10 == 0) goto L_0x00e2
            int r5 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoPanelHeight
            int r5 = r10.getDimensionPixelSize(r5, r1)
            r8.mPanelHeight = r5
            int r5 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoShadowHeight
            int r5 = r10.getDimensionPixelSize(r5, r1)
            r8.mShadowHeight = r5
            int r5 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoParallaxOffset
            int r5 = r10.getDimensionPixelSize(r5, r1)
            r8.mParallaxOffset = r5
            int r5 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoFlingVelocity
            int r11 = r10.getInt(r5, r11)
            r8.mMinFlingVelocity = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoFadeColor
            int r11 = r10.getColor(r11, r0)
            r8.mCoveredFadeColor = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoDragView
            int r11 = r10.getResourceId(r11, r1)
            r8.mDragViewResId = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoScrollableView
            int r11 = r10.getResourceId(r11, r1)
            r8.mScrollableViewResId = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoOverlay
            boolean r11 = r10.getBoolean(r11, r2)
            r8.mOverlayContent = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoClipPanel
            boolean r11 = r10.getBoolean(r11, r3)
            r8.mClipPanel = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoAnchorPoint
            float r11 = r10.getFloat(r11, r4)
            r8.mAnchorPoint = r11
            com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState[] r11 = com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.values()
            int r0 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoInitialState
            com.sothree.slidinguppanel.SlidingUpPanelLayout$PanelState r4 = DEFAULT_SLIDE_STATE
            int r4 = r4.ordinal()
            int r0 = r10.getInt(r0, r4)
            r11 = r11[r0]
            r8.mSlideState = r11
            int r11 = com.sothree.slidinguppanel.library.C4626R.styleable.SlidingUpPanelLayout_umanoScrollInterpolator
            int r11 = r10.getResourceId(r11, r1)
            if (r11 == r1) goto L_0x00dd
            android.view.animation.Interpolator r11 = android.view.animation.AnimationUtils.loadInterpolator(r9, r11)
            goto L_0x00de
        L_0x00dd:
            r11 = r6
        L_0x00de:
            r10.recycle()
            goto L_0x00e3
        L_0x00e2:
            r11 = r6
        L_0x00e3:
            android.content.res.Resources r9 = r9.getResources()
            android.util.DisplayMetrics r9 = r9.getDisplayMetrics()
            float r9 = r9.density
            int r10 = r8.mPanelHeight
            r0 = 1056964608(0x3f000000, float:0.5)
            if (r10 != r1) goto L_0x00fb
            r10 = 1116209152(0x42880000, float:68.0)
            float r10 = r10 * r9
            float r10 = r10 + r0
            int r10 = (int) r10
            r8.mPanelHeight = r10
        L_0x00fb:
            int r10 = r8.mShadowHeight
            if (r10 != r1) goto L_0x0107
            r10 = 1082130432(0x40800000, float:4.0)
            float r10 = r10 * r9
            float r10 = r10 + r0
            int r10 = (int) r10
            r8.mShadowHeight = r10
        L_0x0107:
            int r10 = r8.mParallaxOffset
            if (r10 != r1) goto L_0x0111
            r10 = 0
            float r10 = r10 * r9
            int r10 = (int) r10
            r8.mParallaxOffset = r10
        L_0x0111:
            int r10 = r8.mShadowHeight
            if (r10 <= 0) goto L_0x0133
            boolean r10 = r8.mIsSlidingUp
            if (r10 == 0) goto L_0x0126
            android.content.res.Resources r10 = r8.getResources()
            int r1 = com.sothree.slidinguppanel.library.C4626R.C4628drawable.above_shadow
            android.graphics.drawable.Drawable r10 = r10.getDrawable(r1)
            r8.mShadowDrawable = r10
            goto L_0x0135
        L_0x0126:
            android.content.res.Resources r10 = r8.getResources()
            int r1 = com.sothree.slidinguppanel.library.C4626R.C4628drawable.below_shadow
            android.graphics.drawable.Drawable r10 = r10.getDrawable(r1)
            r8.mShadowDrawable = r10
            goto L_0x0135
        L_0x0133:
            r8.mShadowDrawable = r6
        L_0x0135:
            r8.setWillNotDraw(r2)
            com.sothree.slidinguppanel.SlidingUpPanelLayout$DragHelperCallback r10 = new com.sothree.slidinguppanel.SlidingUpPanelLayout$DragHelperCallback
            r10.<init>()
            com.sothree.slidinguppanel.ViewDragHelper r10 = com.sothree.slidinguppanel.ViewDragHelper.create(r8, r0, r11, r10)
            r8.mDragHelper = r10
            com.sothree.slidinguppanel.ViewDragHelper r10 = r8.mDragHelper
            int r11 = r8.mMinFlingVelocity
            float r11 = (float) r11
            float r11 = r11 * r9
            r10.setMinVelocity(r11)
            r8.mIsTouchEnabled = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mDragViewResId;
        if (i != -1) {
            setDragView(findViewById(i));
        }
        int i2 = this.mScrollableViewResId;
        if (i2 != -1) {
            setScrollableView(findViewById(i2));
        }
    }

    public void setGravity(int i) {
        if (i == 48 || i == 80) {
            this.mIsSlidingUp = i == 80;
            if (!this.mFirstLayout) {
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("gravity must be set to either top or bottom");
    }

    public void setCoveredFadeColor(int i) {
        this.mCoveredFadeColor = i;
        requestLayout();
    }

    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public void setTouchEnabled(boolean z) {
        this.mIsTouchEnabled = z;
    }

    public boolean isTouchEnabled() {
        return (!this.mIsTouchEnabled || this.mSlideableView == null || this.mSlideState == PanelState.HIDDEN) ? false : true;
    }

    public void setPanelHeight(int i) {
        if (getPanelHeight() != i) {
            this.mPanelHeight = i;
            if (!this.mFirstLayout) {
                requestLayout();
            }
            if (getPanelState() == PanelState.COLLAPSED) {
                smoothToBottom();
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void smoothToBottom() {
        smoothSlideTo(0.0f, 0);
    }

    public int getShadowHeight() {
        return this.mShadowHeight;
    }

    public void setShadowHeight(int i) {
        this.mShadowHeight = i;
        if (!this.mFirstLayout) {
            invalidate();
        }
    }

    public int getPanelHeight() {
        return this.mPanelHeight;
    }

    public int getCurrentParallaxOffset() {
        int max = (int) (((float) this.mParallaxOffset) * Math.max(this.mSlideOffset, 0.0f));
        return this.mIsSlidingUp ? -max : max;
    }

    public void setParallaxOffset(int i) {
        this.mParallaxOffset = i;
        if (!this.mFirstLayout) {
            requestLayout();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public void setMinFlingVelocity(int i) {
        this.mMinFlingVelocity = i;
    }

    public void addPanelSlideListener(PanelSlideListener panelSlideListener) {
        synchronized (this.mPanelSlideListeners) {
            this.mPanelSlideListeners.add(panelSlideListener);
        }
    }

    public void removePanelSlideListener(PanelSlideListener panelSlideListener) {
        synchronized (this.mPanelSlideListeners) {
            this.mPanelSlideListeners.remove(panelSlideListener);
        }
    }

    public void setFadeOnClickListener(OnClickListener onClickListener) {
        this.mFadeOnClickListener = onClickListener;
    }

    public void setDragView(View view) {
        View view2 = this.mDragView;
        if (view2 != null) {
            view2.setOnClickListener(null);
        }
        this.mDragView = view;
        View view3 = this.mDragView;
        if (view3 != null) {
            view3.setClickable(true);
            this.mDragView.setFocusable(false);
            this.mDragView.setFocusableInTouchMode(false);
            this.mDragView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SlidingUpPanelLayout.this.isEnabled() && SlidingUpPanelLayout.this.isTouchEnabled()) {
                        if (SlidingUpPanelLayout.this.mSlideState == PanelState.EXPANDED || SlidingUpPanelLayout.this.mSlideState == PanelState.ANCHORED) {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.COLLAPSED);
                        } else if (SlidingUpPanelLayout.this.mAnchorPoint < 1.0f) {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.ANCHORED);
                        } else {
                            SlidingUpPanelLayout.this.setPanelState(PanelState.EXPANDED);
                        }
                    }
                }
            });
        }
    }

    public void setDragView(int i) {
        this.mDragViewResId = i;
        setDragView(findViewById(i));
    }

    public void setScrollableView(View view) {
        this.mScrollableView = view;
    }

    public void setScrollableViewHelper(ScrollableViewHelper scrollableViewHelper) {
        this.mScrollableViewHelper = scrollableViewHelper;
    }

    public void setAnchorPoint(float f) {
        if (f > 0.0f && f <= 1.0f) {
            this.mAnchorPoint = f;
            this.mFirstLayout = true;
            requestLayout();
        }
    }

    public float getAnchorPoint() {
        return this.mAnchorPoint;
    }

    public void setOverlayed(boolean z) {
        this.mOverlayContent = z;
    }

    public boolean isOverlayed() {
        return this.mOverlayContent;
    }

    public void setClipPanel(boolean z) {
        this.mClipPanel = z;
    }

    public boolean isClipPanel() {
        return this.mClipPanel;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelSlide(View view) {
        synchronized (this.mPanelSlideListeners) {
            for (PanelSlideListener onPanelSlide : this.mPanelSlideListeners) {
                onPanelSlide.onPanelSlide(view, this.mSlideOffset);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
        synchronized (this.mPanelSlideListeners) {
            for (PanelSlideListener onPanelStateChanged : this.mPanelSlideListeners) {
                onPanelStateChanged.onPanelStateChanged(view, panelState, panelState2);
            }
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void updateObscuredViewVisibility() {
        int i;
        int i2;
        int i3;
        int i4;
        if (getChildCount() != 0) {
            int paddingLeft = getPaddingLeft();
            int width = getWidth() - getPaddingRight();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            View view = this.mSlideableView;
            int i5 = 0;
            if (view == null || !hasOpaqueBackground(view)) {
                i4 = 0;
                i3 = 0;
                i2 = 0;
                i = 0;
            } else {
                i4 = this.mSlideableView.getLeft();
                i3 = this.mSlideableView.getRight();
                i2 = this.mSlideableView.getTop();
                i = this.mSlideableView.getBottom();
            }
            View childAt = getChildAt(0);
            int max = Math.max(paddingLeft, childAt.getLeft());
            int max2 = Math.max(paddingTop, childAt.getTop());
            int min = Math.min(width, childAt.getRight());
            int min2 = Math.min(height, childAt.getBottom());
            if (max >= i4 && max2 >= i2 && min <= i3 && min2 <= i) {
                i5 = 4;
            }
            childAt.setVisibility(i5);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    private static boolean hasOpaqueBackground(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824 && mode != Integer.MIN_VALUE) {
            throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
        } else if (mode2 == 1073741824 || mode2 == Integer.MIN_VALUE) {
            int childCount = getChildCount();
            if (childCount == 2) {
                this.mMainView = getChildAt(0);
                this.mSlideableView = getChildAt(1);
                if (this.mDragView == null) {
                    setDragView(this.mSlideableView);
                }
                if (this.mSlideableView.getVisibility() != 0) {
                    this.mSlideState = PanelState.HIDDEN;
                }
                int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
                int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
                for (int i7 = 0; i7 < childCount; i7++) {
                    View childAt = getChildAt(i7);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (childAt.getVisibility() != 8 || i7 != 0) {
                        if (childAt == this.mMainView) {
                            i4 = (this.mOverlayContent || this.mSlideState == PanelState.HIDDEN) ? paddingTop : paddingTop - this.mPanelHeight;
                            i3 = paddingLeft - (layoutParams.leftMargin + layoutParams.rightMargin);
                        } else {
                            i4 = childAt == this.mSlideableView ? paddingTop - layoutParams.topMargin : paddingTop;
                            i3 = paddingLeft;
                        }
                        if (layoutParams.width == -2) {
                            i5 = MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
                        } else if (layoutParams.width == -1) {
                            i5 = MeasureSpec.makeMeasureSpec(i3, 1073741824);
                        } else {
                            i5 = MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                        }
                        if (layoutParams.height == -2) {
                            i6 = MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
                        } else {
                            if (layoutParams.weight > 0.0f && layoutParams.weight < 1.0f) {
                                i4 = (int) (((float) i4) * layoutParams.weight);
                            } else if (layoutParams.height != -1) {
                                i4 = layoutParams.height;
                            }
                            i6 = MeasureSpec.makeMeasureSpec(i4, 1073741824);
                        }
                        childAt.measure(i5, i6);
                        View view = this.mSlideableView;
                        if (childAt == view) {
                            this.mSlideRange = view.getMeasuredHeight() - this.mPanelHeight;
                        }
                    }
                }
                setMeasuredDimension(size, size2);
                return;
            }
            throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
        } else {
            throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.mFirstLayout) {
            int i5 = C46232.f2363x1974508[this.mSlideState.ordinal()];
            if (i5 == 1) {
                this.mSlideOffset = 1.0f;
            } else if (i5 == 2) {
                this.mSlideOffset = this.mAnchorPoint;
            } else if (i5 != 3) {
                this.mSlideOffset = 0.0f;
            } else {
                this.mSlideOffset = computeSlideOffset(computePanelTopPosition(0.0f) + (this.mIsSlidingUp ? this.mPanelHeight : -this.mPanelHeight));
            }
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8 || (i6 != 0 && !this.mFirstLayout)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int computePanelTopPosition = childAt == this.mSlideableView ? computePanelTopPosition(this.mSlideOffset) : paddingTop;
                if (!this.mIsSlidingUp && childAt == this.mMainView && !this.mOverlayContent) {
                    computePanelTopPosition = computePanelTopPosition(this.mSlideOffset) + this.mSlideableView.getMeasuredHeight();
                }
                int i7 = layoutParams.leftMargin + paddingLeft;
                childAt.layout(i7, computePanelTopPosition, childAt.getMeasuredWidth() + i7, measuredHeight + computePanelTopPosition);
            }
        }
        if (this.mFirstLayout) {
            updateObscuredViewVisibility();
        }
        applyParallaxForCurrentSlideOffset();
        this.mFirstLayout = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i2 != i4) {
            this.mFirstLayout = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0038, code lost:
        if (r0 != 3) goto L_0x009d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            boolean r0 = r8.mIsScrollableViewHandlingTouch
            r1 = 0
            if (r0 != 0) goto L_0x00a4
            boolean r0 = r8.isTouchEnabled()
            if (r0 != 0) goto L_0x000d
            goto L_0x00a4
        L_0x000d:
            int r0 = androidx.core.view.MotionEventCompat.getActionMasked(r9)
            float r2 = r9.getX()
            float r3 = r9.getY()
            float r4 = r8.mInitialMotionX
            float r4 = r2 - r4
            float r4 = java.lang.Math.abs(r4)
            float r5 = r8.mInitialMotionY
            float r5 = r3 - r5
            float r5 = java.lang.Math.abs(r5)
            com.sothree.slidinguppanel.ViewDragHelper r6 = r8.mDragHelper
            int r6 = r6.getTouchSlop()
            r7 = 1
            if (r0 == 0) goto L_0x0085
            if (r0 == r7) goto L_0x004c
            r2 = 2
            if (r0 == r2) goto L_0x003b
            r2 = 3
            if (r0 == r2) goto L_0x004c
            goto L_0x009d
        L_0x003b:
            float r0 = (float) r6
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x009d
            int r0 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x009d
            com.sothree.slidinguppanel.ViewDragHelper r9 = r8.mDragHelper
            r9.cancel()
            r8.mIsUnableToDrag = r7
            return r1
        L_0x004c:
            com.sothree.slidinguppanel.ViewDragHelper r0 = r8.mDragHelper
            boolean r0 = r0.isDragging()
            if (r0 == 0) goto L_0x005a
            com.sothree.slidinguppanel.ViewDragHelper r0 = r8.mDragHelper
            r0.processTouchEvent(r9)
            return r7
        L_0x005a:
            float r0 = (float) r6
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x009d
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x009d
            float r0 = r8.mSlideOffset
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x009d
            android.view.View r0 = r8.mSlideableView
            float r2 = r8.mInitialMotionX
            int r2 = (int) r2
            float r3 = r8.mInitialMotionY
            int r3 = (int) r3
            boolean r0 = r8.isViewUnder(r0, r2, r3)
            if (r0 != 0) goto L_0x009d
            android.view.View$OnClickListener r0 = r8.mFadeOnClickListener
            if (r0 == 0) goto L_0x009d
            r8.playSoundEffect(r1)
            android.view.View$OnClickListener r9 = r8.mFadeOnClickListener
            r9.onClick(r8)
            return r7
        L_0x0085:
            r8.mIsUnableToDrag = r1
            r8.mInitialMotionX = r2
            r8.mInitialMotionY = r3
            android.view.View r0 = r8.mDragView
            int r2 = (int) r2
            int r3 = (int) r3
            boolean r0 = r8.isViewUnder(r0, r2, r3)
            if (r0 != 0) goto L_0x009d
            com.sothree.slidinguppanel.ViewDragHelper r9 = r8.mDragHelper
            r9.cancel()
            r8.mIsUnableToDrag = r7
            return r1
        L_0x009d:
            com.sothree.slidinguppanel.ViewDragHelper r0 = r8.mDragHelper
            boolean r9 = r0.shouldInterceptTouchEvent(r9)
            return r9
        L_0x00a4:
            com.sothree.slidinguppanel.ViewDragHelper r9 = r8.mDragHelper
            r9.abort()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isTouchEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.mDragHelper.processTouchEvent(motionEvent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (!isEnabled() || !isTouchEnabled() || (this.mIsUnableToDrag && actionMasked != 0)) {
            this.mDragHelper.abort();
            return super.dispatchTouchEvent(motionEvent);
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (actionMasked == 0) {
            this.mIsScrollableViewHandlingTouch = false;
            this.mPrevMotionX = x;
            this.mPrevMotionY = y;
        } else if (actionMasked == 2) {
            float f = x - this.mPrevMotionX;
            float f2 = y - this.mPrevMotionY;
            this.mPrevMotionX = x;
            this.mPrevMotionY = y;
            if (Math.abs(f) > Math.abs(f2)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            if (!isViewUnder(this.mScrollableView, (int) this.mInitialMotionX, (int) this.mInitialMotionY)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            int i = -1;
            if (((float) (this.mIsSlidingUp ? 1 : -1)) * f2 <= 0.0f) {
                if (this.mIsSlidingUp) {
                    i = 1;
                }
                if (f2 * ((float) i) < 0.0f) {
                    if (this.mSlideOffset < 1.0f) {
                        this.mIsScrollableViewHandlingTouch = false;
                        return onTouchEvent(motionEvent);
                    }
                    if (!this.mIsScrollableViewHandlingTouch && this.mDragHelper.isDragging()) {
                        this.mDragHelper.cancel();
                        motionEvent.setAction(0);
                    }
                    this.mIsScrollableViewHandlingTouch = true;
                    return super.dispatchTouchEvent(motionEvent);
                }
            } else if (this.mScrollableViewHelper.getScrollableViewScrollPosition(this.mScrollableView, this.mIsSlidingUp) > 0) {
                this.mIsScrollableViewHandlingTouch = true;
                return super.dispatchTouchEvent(motionEvent);
            } else {
                if (this.mIsScrollableViewHandlingTouch) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction(3);
                    super.dispatchTouchEvent(obtain);
                    obtain.recycle();
                    motionEvent.setAction(0);
                }
                this.mIsScrollableViewHandlingTouch = false;
                return onTouchEvent(motionEvent);
            }
        } else if (actionMasked == 1 && this.mIsScrollableViewHandlingTouch) {
            this.mDragHelper.setDragState(0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean isViewUnder(View view, int i, int i2) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i3 = iArr2[0] + i;
        boolean z = true;
        int i4 = iArr2[1] + i2;
        if (i3 < iArr[0] || i3 >= iArr[0] + view.getWidth() || i4 < iArr[1] || i4 >= iArr[1] + view.getHeight()) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public int computePanelTopPosition(float f) {
        View view = this.mSlideableView;
        int measuredHeight = view != null ? view.getMeasuredHeight() : 0;
        int i = (int) (f * ((float) this.mSlideRange));
        if (this.mIsSlidingUp) {
            return ((getMeasuredHeight() - getPaddingBottom()) - this.mPanelHeight) - i;
        }
        return (getPaddingTop() - measuredHeight) + this.mPanelHeight + i;
    }

    /* access modifiers changed from: private */
    public float computeSlideOffset(int i) {
        int computePanelTopPosition = computePanelTopPosition(0.0f);
        return (this.mIsSlidingUp ? (float) (computePanelTopPosition - i) : (float) (i - computePanelTopPosition)) / ((float) this.mSlideRange);
    }

    public PanelState getPanelState() {
        return this.mSlideState;
    }

    public void setPanelState(PanelState panelState) {
        if (this.mDragHelper.getViewDragState() == 2) {
            Log.d(TAG, "View is settling. Aborting animation.");
            this.mDragHelper.abort();
        }
        if (panelState == null || panelState == PanelState.DRAGGING) {
            throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
        } else if (!isEnabled()) {
        } else {
            if (this.mFirstLayout || this.mSlideableView != null) {
                PanelState panelState2 = this.mSlideState;
                if (panelState != panelState2 && panelState2 != PanelState.DRAGGING) {
                    if (this.mFirstLayout) {
                        setPanelStateInternal(panelState);
                        return;
                    }
                    if (this.mSlideState == PanelState.HIDDEN) {
                        this.mSlideableView.setVisibility(0);
                        requestLayout();
                    }
                    int i = C46232.f2363x1974508[panelState.ordinal()];
                    if (i == 1) {
                        smoothSlideTo(1.0f, 0);
                    } else if (i == 2) {
                        smoothSlideTo(this.mAnchorPoint, 0);
                    } else if (i == 3) {
                        smoothSlideTo(computeSlideOffset(computePanelTopPosition(0.0f) + (this.mIsSlidingUp ? this.mPanelHeight : -this.mPanelHeight)), 0);
                    } else if (i == 4) {
                        smoothSlideTo(0.0f, 0);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPanelStateInternal(PanelState panelState) {
        PanelState panelState2 = this.mSlideState;
        if (panelState2 != panelState) {
            this.mSlideState = panelState;
            dispatchOnPanelStateChanged(this, panelState2, panelState);
        }
    }

    /* access modifiers changed from: private */
    public void applyParallaxForCurrentSlideOffset() {
        if (this.mParallaxOffset > 0) {
            ViewCompat.setTranslationY(this.mMainView, (float) getCurrentParallaxOffset());
        }
    }

    /* access modifiers changed from: private */
    public void onPanelDragged(int i) {
        if (this.mSlideState != PanelState.DRAGGING) {
            this.mLastNotDraggingSlideState = this.mSlideState;
        }
        setPanelStateInternal(PanelState.DRAGGING);
        this.mSlideOffset = computeSlideOffset(i);
        applyParallaxForCurrentSlideOffset();
        dispatchOnPanelSlide(this.mSlideableView);
        LayoutParams layoutParams = (LayoutParams) this.mMainView.getLayoutParams();
        int height = ((getHeight() - getPaddingBottom()) - getPaddingTop()) - this.mPanelHeight;
        if (this.mSlideOffset <= 0.0f && !this.mOverlayContent) {
            layoutParams.height = this.mIsSlidingUp ? i - getPaddingBottom() : ((getHeight() - getPaddingBottom()) - this.mSlideableView.getMeasuredHeight()) - i;
            if (layoutParams.height == height) {
                layoutParams.height = -1;
            }
            this.mMainView.requestLayout();
        } else if (layoutParams.height != -1 && !this.mOverlayContent) {
            layoutParams.height = -1;
            this.mMainView.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        int save = canvas.save(2);
        View view2 = this.mSlideableView;
        if (view2 == null || view2 == view) {
            z = super.drawChild(canvas, view, j);
        } else {
            canvas.getClipBounds(this.mTmpRect);
            if (!this.mOverlayContent) {
                if (this.mIsSlidingUp) {
                    Rect rect = this.mTmpRect;
                    rect.bottom = Math.min(rect.bottom, this.mSlideableView.getTop());
                } else {
                    Rect rect2 = this.mTmpRect;
                    rect2.top = Math.max(rect2.top, this.mSlideableView.getBottom());
                }
            }
            if (this.mClipPanel) {
                canvas.clipRect(this.mTmpRect);
            }
            z = super.drawChild(canvas, view, j);
            int i = this.mCoveredFadeColor;
            if (i != 0) {
                float f = this.mSlideOffset;
                if (f > 0.0f) {
                    this.mCoveredFadePaint.setColor((i & ViewCompat.MEASURED_SIZE_MASK) | (((int) (((float) ((-16777216 & i) >>> 24)) * f)) << 24));
                    canvas.drawRect(this.mTmpRect, this.mCoveredFadePaint);
                }
            }
        }
        canvas.restoreToCount(save);
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean smoothSlideTo(float f, int i) {
        if (isEnabled() && this.mSlideableView != null) {
            int computePanelTopPosition = computePanelTopPosition(f);
            ViewDragHelper viewDragHelper = this.mDragHelper;
            View view = this.mSlideableView;
            if (viewDragHelper.smoothSlideViewTo(view, view.getLeft(), computePanelTopPosition)) {
                setAllChildrenVisible();
                ViewCompat.postInvalidateOnAnimation(this);
                return true;
            }
        }
        return false;
    }

    public void computeScroll() {
        ViewDragHelper viewDragHelper = this.mDragHelper;
        if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
            if (!isEnabled()) {
                this.mDragHelper.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void draw(Canvas canvas) {
        int i;
        int i2;
        super.draw(canvas);
        if (this.mShadowDrawable != null) {
            View view = this.mSlideableView;
            if (view != null) {
                int right = view.getRight();
                if (this.mIsSlidingUp) {
                    i2 = this.mSlideableView.getTop() - this.mShadowHeight;
                    i = this.mSlideableView.getTop();
                } else {
                    i2 = this.mSlideableView.getBottom();
                    i = this.mSlideableView.getBottom() + this.mShadowHeight;
                }
                this.mShadowDrawable.setBounds(this.mSlideableView.getLeft(), i2, right, i);
                this.mShadowDrawable.draw(canvas);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        View view2 = view;
        boolean z2 = true;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i4 = i2 + scrollX;
                if (i4 >= childAt.getLeft() && i4 < childAt.getRight()) {
                    int i5 = i3 + scrollY;
                    if (i5 >= childAt.getTop() && i5 < childAt.getBottom()) {
                        if (canScroll(childAt, true, i, i4 - childAt.getLeft(), i5 - childAt.getTop())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (!z || !ViewCompat.canScrollHorizontally(view, -i)) {
            z2 = false;
        }
        return z2;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putSerializable(SLIDING_STATE, this.mSlideState != PanelState.DRAGGING ? this.mSlideState : this.mLastNotDraggingSlideState);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mSlideState = (PanelState) bundle.getSerializable(SLIDING_STATE);
            PanelState panelState = this.mSlideState;
            if (panelState == null) {
                panelState = DEFAULT_SLIDE_STATE;
            }
            this.mSlideState = panelState;
            parcelable = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(parcelable);
    }
}
