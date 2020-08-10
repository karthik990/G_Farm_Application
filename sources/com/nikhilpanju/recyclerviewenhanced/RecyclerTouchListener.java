package com.nikhilpanju.recyclerviewenhanced;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import com.google.logging.type.LogSeverity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RecyclerTouchListener implements OnItemTouchListener, OnActivityTouchListener {
    private static final String TAG = "RecyclerTouchListener";
    private long ANIMATION_CLOSE = 150;
    private long ANIMATION_STANDARD = 300;
    private int LONG_CLICK_DELAY = LogSeverity.EMERGENCY_VALUE;
    Activity act;
    private View bgView;
    private int bgViewID;
    private int bgViewIDLeft;
    /* access modifiers changed from: private */
    public boolean bgVisible;
    private int bgVisiblePosition;
    private View bgVisibleView;
    private int bgWidth = 1;
    private int bgWidthLeft = 1;
    private boolean clickable = false;
    private ArrayList<Integer> fadeViews;
    private boolean fgPartialViewClicked;
    private View fgView;
    private int fgViewID;
    final Handler handler = new Handler();
    private int heightOutsideRView;
    List<Integer> independentViews;
    private boolean isFgSwiping;
    /* access modifiers changed from: private */
    public boolean isRViewScrolling;
    /* access modifiers changed from: private */
    public boolean longClickVibrate;
    /* access modifiers changed from: private */
    public boolean longClickable = false;
    /* access modifiers changed from: private */
    public OnSwipeOptionsClickListener mBgClickListener;
    private OnSwipeOptionsClickListener mBgClickListenerLeft;
    private int mDismissAnimationRefCount = 0;
    /* access modifiers changed from: private */
    public boolean mLongClickPerformed;
    Runnable mLongPressed = new Runnable() {
        public void run() {
            if (RecyclerTouchListener.this.longClickable) {
                RecyclerTouchListener.this.mLongClickPerformed = true;
                if (!RecyclerTouchListener.this.bgVisible && RecyclerTouchListener.this.touchedPosition >= 0 && !RecyclerTouchListener.this.unClickableRows.contains(Integer.valueOf(RecyclerTouchListener.this.touchedPosition)) && !RecyclerTouchListener.this.isRViewScrolling) {
                    if (RecyclerTouchListener.this.longClickVibrate) {
                        ((Vibrator) RecyclerTouchListener.this.act.getSystemService("vibrator")).vibrate(100);
                    }
                    RecyclerTouchListener.this.mRowLongClickListener.onRowLongClicked(RecyclerTouchListener.this.touchedPosition);
                }
            }
        }
    };
    private boolean mPaused;
    private OnRowClickListener mRowClickListener;
    /* access modifiers changed from: private */
    public OnRowLongClickListener mRowLongClickListener;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private int maxFlingVel;
    private int minFlingVel;
    List<Integer> optionViews;
    private RecyclerView rView;
    private int screenHeight;
    private boolean swipeable = false;
    private boolean swipeableLeftOptions = false;
    private int touchSlop;
    /* access modifiers changed from: private */
    public int touchedPosition;
    private View touchedView;
    private float touchedX;
    private float touchedY;
    List<Integer> unClickableRows;
    List<Integer> unSwipeableRows;

    /* renamed from: com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation */
    private enum C4531Animation {
        OPEN,
        CLOSE
    }

    public interface OnRowClickListener {
        void onIndependentViewClicked(int i, int i2);

        void onRowClicked(int i);
    }

    public interface OnRowLongClickListener {
        void onRowLongClicked(int i);
    }

    public interface OnSwipeListener {
        void onSwipeOptionsClosed();

        void onSwipeOptionsOpened();
    }

    public interface OnSwipeOptionsClickListener {
        void onSwipeOptionClicked(int i, int i2);
    }

    public interface RecyclerTouchListenerHelper {
        void setOnActivityTouchListener(OnActivityTouchListener onActivityTouchListener);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    private RecyclerTouchListener() {
    }

    public RecyclerTouchListener(Activity activity, RecyclerView recyclerView) {
        this.act = activity;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        this.minFlingVel = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.maxFlingVel = viewConfiguration.getScaledMaximumFlingVelocity();
        this.rView = recyclerView;
        this.bgVisible = false;
        this.bgVisiblePosition = -1;
        this.bgVisibleView = null;
        this.fgPartialViewClicked = false;
        this.unSwipeableRows = new ArrayList();
        this.unClickableRows = new ArrayList();
        this.independentViews = new ArrayList();
        this.optionViews = new ArrayList();
        this.fadeViews = new ArrayList<>();
        this.isRViewScrolling = false;
        this.rView.addOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            }

            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                boolean z = false;
                RecyclerTouchListener.this.setEnabled(i != 1);
                RecyclerTouchListener recyclerTouchListener = RecyclerTouchListener.this;
                if (i != 0) {
                    z = true;
                }
                recyclerTouchListener.isRViewScrolling = z;
            }
        });
    }

    public void setEnabled(boolean z) {
        this.mPaused = !z;
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        return handleTouchEvent(motionEvent);
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        handleTouchEvent(motionEvent);
    }

    public RecyclerTouchListener setClickable(OnRowClickListener onRowClickListener) {
        this.clickable = true;
        this.mRowClickListener = onRowClickListener;
        return this;
    }

    public RecyclerTouchListener setClickable(boolean z) {
        this.clickable = z;
        return this;
    }

    public RecyclerTouchListener setLongClickable(boolean z, OnRowLongClickListener onRowLongClickListener) {
        this.longClickable = true;
        this.mRowLongClickListener = onRowLongClickListener;
        this.longClickVibrate = z;
        return this;
    }

    public RecyclerTouchListener setLongClickable(boolean z) {
        this.longClickable = z;
        return this;
    }

    public RecyclerTouchListener setIndependentViews(Integer... numArr) {
        this.independentViews = new ArrayList(Arrays.asList(numArr));
        return this;
    }

    public RecyclerTouchListener setUnClickableRows(Integer... numArr) {
        this.unClickableRows = new ArrayList(Arrays.asList(numArr));
        return this;
    }

    public RecyclerTouchListener setSwipeable(int i, int i2, OnSwipeOptionsClickListener onSwipeOptionsClickListener) {
        this.swipeable = true;
        int i3 = this.fgViewID;
        if (i3 == 0 || i == i3) {
            this.fgViewID = i;
            this.bgViewID = i2;
            this.mBgClickListener = onSwipeOptionsClickListener;
            Activity activity = this.act;
            if (activity instanceof RecyclerTouchListenerHelper) {
                ((RecyclerTouchListenerHelper) activity).setOnActivityTouchListener(this);
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.screenHeight = displayMetrics.heightPixels;
            return this;
        }
        throw new IllegalArgumentException("foregroundID does not match previously set ID");
    }

    public RecyclerTouchListener setSwipeable(boolean z) {
        this.swipeable = z;
        if (!z) {
            invalidateSwipeOptions();
        }
        return this;
    }

    public RecyclerTouchListener setSwipeOptionViews(Integer... numArr) {
        this.optionViews = new ArrayList(Arrays.asList(numArr));
        return this;
    }

    public RecyclerTouchListener setUnSwipeableRows(Integer... numArr) {
        this.unSwipeableRows = new ArrayList(Arrays.asList(numArr));
        return this;
    }

    public RecyclerTouchListener setViewsToFade(Integer... numArr) {
        this.fadeViews = new ArrayList<>(Arrays.asList(numArr));
        return this;
    }

    public RecyclerTouchListener setFgFade() {
        if (!this.fadeViews.contains(Integer.valueOf(this.fgViewID))) {
            this.fadeViews.add(Integer.valueOf(this.fgViewID));
        }
        return this;
    }

    private boolean isIndependentViewClicked(MotionEvent motionEvent) {
        for (int i = 0; i < this.independentViews.size(); i++) {
            if (this.touchedView != null) {
                Rect rect = new Rect();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                this.touchedView.findViewById(((Integer) this.independentViews.get(i)).intValue()).getGlobalVisibleRect(rect);
                if (rect.contains(rawX, rawY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getOptionViewID(MotionEvent motionEvent) {
        for (int i = 0; i < this.optionViews.size(); i++) {
            if (this.touchedView != null) {
                Rect rect = new Rect();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                this.touchedView.findViewById(((Integer) this.optionViews.get(i)).intValue()).getGlobalVisibleRect(rect);
                if (rect.contains(rawX, rawY)) {
                    return ((Integer) this.optionViews.get(i)).intValue();
                }
            }
        }
        return -1;
    }

    private int getIndependentViewID(MotionEvent motionEvent) {
        for (int i = 0; i < this.independentViews.size(); i++) {
            if (this.touchedView != null) {
                Rect rect = new Rect();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                this.touchedView.findViewById(((Integer) this.independentViews.get(i)).intValue()).getGlobalVisibleRect(rect);
                if (rect.contains(rawX, rawY)) {
                    return ((Integer) this.independentViews.get(i)).intValue();
                }
            }
        }
        return -1;
    }

    public void invalidateSwipeOptions() {
        this.bgWidth = 1;
    }

    public void openSwipeOptions(int i) {
        if (this.swipeable && this.rView.getChildAt(i) != null && !this.unSwipeableRows.contains(Integer.valueOf(i))) {
            if (this.bgWidth < 2) {
                if (this.act.findViewById(this.bgViewID) != null) {
                    this.bgWidth = this.act.findViewById(this.bgViewID).getWidth();
                }
                this.heightOutsideRView = this.screenHeight - this.rView.getHeight();
            }
            this.touchedPosition = i;
            this.touchedView = this.rView.getChildAt(i);
            this.fgView = this.touchedView.findViewById(this.fgViewID);
            this.bgView = this.touchedView.findViewById(this.bgViewID);
            this.bgView.setMinimumHeight(this.fgView.getHeight());
            closeVisibleBG(null);
            animateFG(this.touchedView, C4531Animation.OPEN, this.ANIMATION_STANDARD);
            this.bgVisible = true;
            this.bgVisibleView = this.fgView;
            this.bgVisiblePosition = this.touchedPosition;
        }
    }

    @Deprecated
    public void closeVisibleBG() {
        View view = this.bgVisibleView;
        if (view == null) {
            Log.e(TAG, "No rows found for which background options are visible");
            return;
        }
        view.animate().translationX(0.0f).setDuration(this.ANIMATION_CLOSE).setListener(null);
        animateFadeViews(this.bgVisibleView, 1.0f, this.ANIMATION_CLOSE);
        this.bgVisible = false;
        this.bgVisibleView = null;
        this.bgVisiblePosition = -1;
    }

    public void closeVisibleBG(final OnSwipeListener onSwipeListener) {
        View view = this.bgVisibleView;
        if (view == null) {
            Log.e(TAG, "No rows found for which background options are visible");
            return;
        }
        final ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, new float[]{0.0f});
        ofFloat.setDuration(this.ANIMATION_CLOSE);
        ofFloat.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                OnSwipeListener onSwipeListener = onSwipeListener;
                if (onSwipeListener != null) {
                    onSwipeListener.onSwipeOptionsClosed();
                }
                ofFloat.removeAllListeners();
            }
        });
        ofFloat.start();
        animateFadeViews(this.bgVisibleView, 1.0f, this.ANIMATION_CLOSE);
        this.bgVisible = false;
        this.bgVisibleView = null;
        this.bgVisiblePosition = -1;
    }

    private void animateFadeViews(View view, float f, long j) {
        ArrayList<Integer> arrayList = this.fadeViews;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                view.findViewById(((Integer) it.next()).intValue()).animate().alpha(f).setDuration(j);
            }
        }
    }

    private void animateFG(View view, C4531Animation animation, long j) {
        if (animation == C4531Animation.OPEN) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.fgView, View.TRANSLATION_X, new float[]{(float) (-this.bgWidth)});
            ofFloat.setDuration(j);
            ofFloat.setInterpolator(new DecelerateInterpolator(1.5f));
            ofFloat.start();
            animateFadeViews(view, 0.0f, j);
        } else if (animation == C4531Animation.CLOSE) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.fgView, View.TRANSLATION_X, new float[]{0.0f});
            ofFloat2.setDuration(j);
            ofFloat2.setInterpolator(new DecelerateInterpolator(1.5f));
            ofFloat2.start();
            animateFadeViews(view, 1.0f, j);
        }
    }

    private void animateFG(View view, final C4531Animation animation, long j, final OnSwipeListener onSwipeListener) {
        final ObjectAnimator objectAnimator;
        if (animation == C4531Animation.OPEN) {
            objectAnimator = ObjectAnimator.ofFloat(this.fgView, View.TRANSLATION_X, new float[]{(float) (-this.bgWidth)});
            objectAnimator.setDuration(j);
            objectAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
            objectAnimator.start();
            animateFadeViews(view, 0.0f, j);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(this.fgView, View.TRANSLATION_X, new float[]{0.0f});
            objectAnimator.setDuration(j);
            objectAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
            objectAnimator.start();
            animateFadeViews(view, 1.0f, j);
        }
        objectAnimator.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (onSwipeListener != null) {
                    if (animation == C4531Animation.OPEN) {
                        onSwipeListener.onSwipeOptionsOpened();
                    } else if (animation == C4531Animation.CLOSE) {
                        onSwipeListener.onSwipeOptionsClosed();
                    }
                }
                objectAnimator.removeAllListeners();
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:150:0x0297  */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x0476  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleTouchEvent(android.view.MotionEvent r14) {
        /*
            r13 = this;
            boolean r0 = r13.swipeable
            r1 = 2
            if (r0 == 0) goto L_0x002c
            int r0 = r13.bgWidth
            if (r0 >= r1) goto L_0x002c
            android.app.Activity r0 = r13.act
            int r2 = r13.bgViewID
            android.view.View r0 = r0.findViewById(r2)
            if (r0 == 0) goto L_0x0021
            android.app.Activity r0 = r13.act
            int r2 = r13.bgViewID
            android.view.View r0 = r0.findViewById(r2)
            int r0 = r0.getWidth()
            r13.bgWidth = r0
        L_0x0021:
            int r0 = r13.screenHeight
            androidx.recyclerview.widget.RecyclerView r2 = r13.rView
            int r2 = r2.getHeight()
            int r0 = r0 - r2
            r13.heightOutsideRView = r0
        L_0x002c:
            int r0 = r14.getActionMasked()
            r2 = 0
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L_0x048b
            r5 = -1
            r6 = 0
            if (r0 == r3) goto L_0x023b
            if (r0 == r1) goto L_0x007b
            r14 = 3
            if (r0 == r14) goto L_0x0040
            goto L_0x056d
        L_0x0040:
            android.os.Handler r14 = r13.handler
            java.lang.Runnable r0 = r13.mLongPressed
            r14.removeCallbacks(r0)
            boolean r14 = r13.mLongClickPerformed
            if (r14 == 0) goto L_0x004d
            goto L_0x056d
        L_0x004d:
            android.view.VelocityTracker r14 = r13.mVelocityTracker
            if (r14 != 0) goto L_0x0053
            goto L_0x056d
        L_0x0053:
            boolean r14 = r13.swipeable
            if (r14 == 0) goto L_0x0071
            android.view.View r14 = r13.touchedView
            if (r14 == 0) goto L_0x0066
            boolean r0 = r13.isFgSwiping
            if (r0 == 0) goto L_0x0066
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.CLOSE
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
        L_0x0066:
            android.view.VelocityTracker r14 = r13.mVelocityTracker
            r14.recycle()
            r13.mVelocityTracker = r2
            r13.isFgSwiping = r4
            r13.bgView = r2
        L_0x0071:
            r13.touchedX = r6
            r13.touchedY = r6
            r13.touchedView = r2
            r13.touchedPosition = r5
            goto L_0x056d
        L_0x007b:
            boolean r0 = r13.mLongClickPerformed
            if (r0 == 0) goto L_0x0081
            goto L_0x056d
        L_0x0081:
            android.view.VelocityTracker r0 = r13.mVelocityTracker
            if (r0 == 0) goto L_0x056d
            boolean r1 = r13.mPaused
            if (r1 != 0) goto L_0x056d
            boolean r1 = r13.swipeable
            if (r1 != 0) goto L_0x008f
            goto L_0x056d
        L_0x008f:
            r0.addMovement(r14)
            float r0 = r14.getRawX()
            float r1 = r13.touchedX
            float r0 = r0 - r1
            float r14 = r14.getRawY()
            float r1 = r13.touchedY
            float r14 = r14 - r1
            boolean r1 = r13.isFgSwiping
            if (r1 != 0) goto L_0x00d3
            float r1 = java.lang.Math.abs(r0)
            int r2 = r13.touchSlop
            float r2 = (float) r2
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x00d3
            float r14 = java.lang.Math.abs(r14)
            float r1 = java.lang.Math.abs(r0)
            r2 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r2
            int r14 = (r14 > r1 ? 1 : (r14 == r1 ? 0 : -1))
            if (r14 >= 0) goto L_0x00d3
            android.os.Handler r14 = r13.handler
            java.lang.Runnable r1 = r13.mLongPressed
            r14.removeCallbacks(r1)
            r13.isFgSwiping = r3
            int r14 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r14 <= 0) goto L_0x00ce
            int r14 = r13.touchSlop
            goto L_0x00d1
        L_0x00ce:
            int r14 = r13.touchSlop
            int r14 = -r14
        L_0x00d1:
            r13.mSwipingSlop = r14
        L_0x00d3:
            boolean r14 = r13.swipeable
            if (r14 == 0) goto L_0x01e7
            boolean r14 = r13.isFgSwiping
            if (r14 == 0) goto L_0x01e7
            java.util.List<java.lang.Integer> r14 = r13.unSwipeableRows
            int r1 = r13.touchedPosition
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            boolean r14 = r14.contains(r1)
            if (r14 != 0) goto L_0x01e7
            android.view.View r14 = r13.bgView
            if (r14 != 0) goto L_0x00fc
            android.view.View r14 = r13.touchedView
            int r1 = r13.bgViewID
            android.view.View r14 = r14.findViewById(r1)
            r13.bgView = r14
            android.view.View r14 = r13.bgView
            r14.setVisibility(r4)
        L_0x00fc:
            int r14 = r13.touchSlop
            float r14 = (float) r14
            r1 = 1065353216(0x3f800000, float:1.0)
            int r14 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1))
            if (r14 >= 0) goto L_0x015c
            boolean r14 = r13.bgVisible
            if (r14 != 0) goto L_0x015c
            int r14 = r13.mSwipingSlop
            float r14 = (float) r14
            float r0 = r0 - r14
            android.view.View r14 = r13.fgView
            float r2 = java.lang.Math.abs(r0)
            int r4 = r13.bgWidth
            float r5 = (float) r4
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x011d
            int r2 = -r4
            float r2 = (float) r2
            goto L_0x011e
        L_0x011d:
            r2 = r0
        L_0x011e:
            r14.setTranslationX(r2)
            android.view.View r14 = r13.fgView
            float r14 = r14.getTranslationX()
            int r14 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r14 <= 0) goto L_0x0130
            android.view.View r14 = r13.fgView
            r14.setTranslationX(r6)
        L_0x0130:
            java.util.ArrayList<java.lang.Integer> r14 = r13.fadeViews
            if (r14 == 0) goto L_0x01e6
            java.util.Iterator r14 = r14.iterator()
        L_0x0138:
            boolean r2 = r14.hasNext()
            if (r2 == 0) goto L_0x01e6
            java.lang.Object r2 = r14.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            android.view.View r4 = r13.touchedView
            android.view.View r2 = r4.findViewById(r2)
            float r4 = java.lang.Math.abs(r0)
            int r5 = r13.bgWidth
            float r5 = (float) r5
            float r4 = r4 / r5
            float r4 = r1 - r4
            r2.setAlpha(r4)
            goto L_0x0138
        L_0x015c:
            int r14 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r14 <= 0) goto L_0x01e6
            boolean r14 = r13.bgVisible
            if (r14 == 0) goto L_0x01e6
            if (r14 == 0) goto L_0x01a6
            int r14 = r13.mSwipingSlop
            float r14 = (float) r14
            float r0 = r0 - r14
            int r14 = r13.bgWidth
            float r14 = (float) r14
            float r14 = r0 - r14
            android.view.View r0 = r13.fgView
            int r2 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x0176
            goto L_0x0177
        L_0x0176:
            r6 = r14
        L_0x0177:
            r0.setTranslationX(r6)
            java.util.ArrayList<java.lang.Integer> r0 = r13.fadeViews
            if (r0 == 0) goto L_0x01e6
            java.util.Iterator r0 = r0.iterator()
        L_0x0182:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x01e6
            java.lang.Object r2 = r0.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            android.view.View r4 = r13.touchedView
            android.view.View r2 = r4.findViewById(r2)
            float r4 = java.lang.Math.abs(r14)
            int r5 = r13.bgWidth
            float r5 = (float) r5
            float r4 = r4 / r5
            float r4 = r1 - r4
            r2.setAlpha(r4)
            goto L_0x0182
        L_0x01a6:
            int r14 = r13.mSwipingSlop
            float r14 = (float) r14
            float r0 = r0 - r14
            int r14 = r13.bgWidth
            float r14 = (float) r14
            float r14 = r0 - r14
            android.view.View r0 = r13.fgView
            int r2 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x01b6
            goto L_0x01b7
        L_0x01b6:
            r6 = r14
        L_0x01b7:
            r0.setTranslationX(r6)
            java.util.ArrayList<java.lang.Integer> r0 = r13.fadeViews
            if (r0 == 0) goto L_0x01e6
            java.util.Iterator r0 = r0.iterator()
        L_0x01c2:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x01e6
            java.lang.Object r2 = r0.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            android.view.View r4 = r13.touchedView
            android.view.View r2 = r4.findViewById(r2)
            float r4 = java.lang.Math.abs(r14)
            int r5 = r13.bgWidth
            float r5 = (float) r5
            float r4 = r4 / r5
            float r4 = r1 - r4
            r2.setAlpha(r4)
            goto L_0x01c2
        L_0x01e6:
            return r3
        L_0x01e7:
            boolean r14 = r13.swipeable
            if (r14 == 0) goto L_0x056d
            boolean r14 = r13.isFgSwiping
            if (r14 == 0) goto L_0x056d
            java.util.List<java.lang.Integer> r14 = r13.unSwipeableRows
            int r1 = r13.touchedPosition
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            boolean r14 = r14.contains(r1)
            if (r14 == 0) goto L_0x056d
            int r14 = r13.touchSlop
            float r14 = (float) r14
            int r14 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1))
            if (r14 >= 0) goto L_0x023a
            boolean r14 = r13.bgVisible
            if (r14 != 0) goto L_0x023a
            int r14 = r13.mSwipingSlop
            float r14 = (float) r14
            float r0 = r0 - r14
            android.view.View r14 = r13.bgView
            if (r14 != 0) goto L_0x021a
            android.view.View r14 = r13.touchedView
            int r1 = r13.bgViewID
            android.view.View r14 = r14.findViewById(r1)
            r13.bgView = r14
        L_0x021a:
            android.view.View r14 = r13.bgView
            if (r14 == 0) goto L_0x0223
            r1 = 8
            r14.setVisibility(r1)
        L_0x0223:
            android.view.View r14 = r13.fgView
            r1 = 1084227584(0x40a00000, float:5.0)
            float r0 = r0 / r1
            r14.setTranslationX(r0)
            android.view.View r14 = r13.fgView
            float r14 = r14.getTranslationX()
            int r14 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r14 <= 0) goto L_0x023a
            android.view.View r14 = r13.fgView
            r14.setTranslationX(r6)
        L_0x023a:
            return r3
        L_0x023b:
            android.os.Handler r0 = r13.handler
            java.lang.Runnable r7 = r13.mLongPressed
            r0.removeCallbacks(r7)
            boolean r0 = r13.mLongClickPerformed
            if (r0 == 0) goto L_0x0248
            goto L_0x056d
        L_0x0248:
            android.view.VelocityTracker r0 = r13.mVelocityTracker
            if (r0 != 0) goto L_0x0252
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x0252
            goto L_0x056d
        L_0x0252:
            int r0 = r13.touchedPosition
            if (r0 >= 0) goto L_0x0258
            goto L_0x056d
        L_0x0258:
            float r0 = r14.getRawX()
            float r7 = r13.touchedX
            float r0 = r0 - r7
            boolean r7 = r13.isFgSwiping
            if (r7 == 0) goto L_0x0270
            int r7 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0269
            r7 = 1
            goto L_0x026a
        L_0x0269:
            r7 = 0
        L_0x026a:
            int r8 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0271
            r8 = 1
            goto L_0x0272
        L_0x0270:
            r7 = 0
        L_0x0271:
            r8 = 0
        L_0x0272:
            float r9 = java.lang.Math.abs(r0)
            int r10 = r13.bgWidth
            int r10 = r10 / r1
            float r1 = (float) r10
            int r1 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0293
            boolean r1 = r13.isFgSwiping
            if (r1 == 0) goto L_0x0293
            int r1 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x0288
            r1 = 1
            goto L_0x0289
        L_0x0288:
            r1 = 0
        L_0x0289:
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x0290
            r0 = 1
            goto L_0x02f7
        L_0x0290:
            r0 = 0
            goto L_0x02f7
        L_0x0293:
            boolean r1 = r13.swipeable
            if (r1 == 0) goto L_0x02f5
            android.view.VelocityTracker r1 = r13.mVelocityTracker
            r1.addMovement(r14)
            android.view.VelocityTracker r1 = r13.mVelocityTracker
            r9 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r9)
            android.view.VelocityTracker r1 = r13.mVelocityTracker
            float r1 = r1.getXVelocity()
            float r9 = java.lang.Math.abs(r1)
            android.view.VelocityTracker r10 = r13.mVelocityTracker
            float r10 = r10.getYVelocity()
            float r10 = java.lang.Math.abs(r10)
            int r11 = r13.minFlingVel
            float r11 = (float) r11
            int r11 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r11 > 0) goto L_0x02f5
            int r11 = r13.maxFlingVel
            float r11 = (float) r11
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 > 0) goto L_0x02f5
            int r9 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x02f5
            boolean r9 = r13.isFgSwiping
            if (r9 == 0) goto L_0x02f5
            int r9 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r9 >= 0) goto L_0x02d3
            r9 = 1
            goto L_0x02d4
        L_0x02d3:
            r9 = 0
        L_0x02d4:
            int r10 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r10 >= 0) goto L_0x02da
            r10 = 1
            goto L_0x02db
        L_0x02da:
            r10 = 0
        L_0x02db:
            if (r9 != r10) goto L_0x02df
            r9 = 1
            goto L_0x02e0
        L_0x02df:
            r9 = 0
        L_0x02e0:
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x02e6
            r1 = 1
            goto L_0x02e7
        L_0x02e6:
            r1 = 0
        L_0x02e7:
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x02ed
            r0 = 1
            goto L_0x02ee
        L_0x02ed:
            r0 = 0
        L_0x02ee:
            if (r1 != r0) goto L_0x02f2
            r0 = 1
            goto L_0x02f3
        L_0x02f2:
            r0 = 0
        L_0x02f3:
            r1 = r9
            goto L_0x02f7
        L_0x02f5:
            r0 = 0
            r1 = 0
        L_0x02f7:
            boolean r9 = r13.swipeable
            if (r9 == 0) goto L_0x032d
            if (r8 != 0) goto L_0x032d
            if (r1 == 0) goto L_0x032d
            int r1 = r13.touchedPosition
            if (r1 == r5) goto L_0x032d
            java.util.List<java.lang.Integer> r9 = r13.unSwipeableRows
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            boolean r1 = r9.contains(r1)
            if (r1 != 0) goto L_0x032d
            boolean r1 = r13.bgVisible
            if (r1 != 0) goto L_0x032d
            android.view.View r14 = r13.touchedView
            int r0 = r13.touchedPosition
            int r1 = r13.mDismissAnimationRefCount
            int r1 = r1 + r3
            r13.mDismissAnimationRefCount = r1
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r1 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.OPEN
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r1, r7)
            r13.bgVisible = r3
            android.view.View r14 = r13.fgView
            r13.bgVisibleView = r14
            r13.bgVisiblePosition = r0
            goto L_0x0472
        L_0x032d:
            boolean r1 = r13.swipeable
            if (r1 == 0) goto L_0x0361
            if (r7 != 0) goto L_0x0361
            if (r0 == 0) goto L_0x0361
            int r0 = r13.touchedPosition
            if (r0 == r5) goto L_0x0361
            java.util.List<java.lang.Integer> r1 = r13.unSwipeableRows
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            boolean r0 = r1.contains(r0)
            if (r0 != 0) goto L_0x0361
            boolean r0 = r13.bgVisible
            if (r0 == 0) goto L_0x0361
            android.view.View r14 = r13.touchedView
            int r0 = r13.touchedPosition
            int r0 = r13.mDismissAnimationRefCount
            int r0 = r0 + r3
            r13.mDismissAnimationRefCount = r0
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.CLOSE
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
            r13.bgVisible = r4
            r13.bgVisibleView = r2
            r13.bgVisiblePosition = r5
            goto L_0x0472
        L_0x0361:
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x0384
            if (r7 == 0) goto L_0x0384
            boolean r0 = r13.bgVisible
            if (r0 != 0) goto L_0x0384
            android.view.View r14 = r13.bgView
            android.view.View r8 = r13.touchedView
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r9 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.CLOSE
            long r10 = r13.ANIMATION_STANDARD
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$5 r12 = new com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$5
            r12.<init>(r14)
            r7 = r13
            r7.animateFG(r8, r9, r10, r12)
            r13.bgVisible = r4
            r13.bgVisibleView = r2
            r13.bgVisiblePosition = r5
            goto L_0x0472
        L_0x0384:
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x03a3
            if (r8 == 0) goto L_0x03a3
            boolean r0 = r13.bgVisible
            if (r0 == 0) goto L_0x03a3
            android.view.View r14 = r13.touchedView
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.OPEN
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
            r13.bgVisible = r3
            android.view.View r14 = r13.fgView
            r13.bgVisibleView = r14
            int r14 = r13.touchedPosition
            r13.bgVisiblePosition = r14
            goto L_0x0472
        L_0x03a3:
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x03be
            if (r8 == 0) goto L_0x03be
            boolean r0 = r13.bgVisible
            if (r0 != 0) goto L_0x03be
            android.view.View r14 = r13.touchedView
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.CLOSE
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
            r13.bgVisible = r4
            r13.bgVisibleView = r2
            r13.bgVisiblePosition = r5
            goto L_0x0472
        L_0x03be:
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x03dd
            if (r7 == 0) goto L_0x03dd
            boolean r0 = r13.bgVisible
            if (r0 == 0) goto L_0x03dd
            android.view.View r14 = r13.touchedView
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.OPEN
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
            r13.bgVisible = r3
            android.view.View r14 = r13.fgView
            r13.bgVisibleView = r14
            int r14 = r13.touchedPosition
            r13.bgVisiblePosition = r14
            goto L_0x0472
        L_0x03dd:
            if (r8 != 0) goto L_0x0472
            if (r7 != 0) goto L_0x0472
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x03fa
            boolean r0 = r13.fgPartialViewClicked
            if (r0 == 0) goto L_0x03fa
            android.view.View r14 = r13.touchedView
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$Animation r0 = com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.C4531Animation.CLOSE
            long r7 = r13.ANIMATION_STANDARD
            r13.animateFG(r14, r0, r7)
            r13.bgVisible = r4
            r13.bgVisibleView = r2
            r13.bgVisiblePosition = r5
            goto L_0x0472
        L_0x03fa:
            boolean r0 = r13.clickable
            if (r0 == 0) goto L_0x0424
            boolean r0 = r13.bgVisible
            if (r0 != 0) goto L_0x0424
            int r0 = r13.touchedPosition
            if (r0 < 0) goto L_0x0424
            java.util.List<java.lang.Integer> r1 = r13.unClickableRows
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            boolean r0 = r1.contains(r0)
            if (r0 != 0) goto L_0x0424
            boolean r0 = r13.isIndependentViewClicked(r14)
            if (r0 == 0) goto L_0x0424
            boolean r0 = r13.isRViewScrolling
            if (r0 != 0) goto L_0x0424
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$OnRowClickListener r14 = r13.mRowClickListener
            int r0 = r13.touchedPosition
            r14.onRowClicked(r0)
            goto L_0x0472
        L_0x0424:
            boolean r0 = r13.clickable
            if (r0 == 0) goto L_0x0454
            boolean r0 = r13.bgVisible
            if (r0 != 0) goto L_0x0454
            int r0 = r13.touchedPosition
            if (r0 < 0) goto L_0x0454
            java.util.List<java.lang.Integer> r1 = r13.unClickableRows
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            boolean r0 = r1.contains(r0)
            if (r0 != 0) goto L_0x0454
            boolean r0 = r13.isIndependentViewClicked(r14)
            if (r0 != 0) goto L_0x0454
            boolean r0 = r13.isRViewScrolling
            if (r0 != 0) goto L_0x0454
            int r14 = r13.getIndependentViewID(r14)
            if (r14 < 0) goto L_0x0472
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$OnRowClickListener r0 = r13.mRowClickListener
            int r1 = r13.touchedPosition
            r0.onIndependentViewClicked(r14, r1)
            goto L_0x0472
        L_0x0454:
            boolean r0 = r13.swipeable
            if (r0 == 0) goto L_0x0472
            boolean r0 = r13.bgVisible
            if (r0 == 0) goto L_0x0472
            boolean r0 = r13.fgPartialViewClicked
            if (r0 != 0) goto L_0x0472
            int r14 = r13.getOptionViewID(r14)
            if (r14 < 0) goto L_0x0472
            int r0 = r13.touchedPosition
            if (r0 < 0) goto L_0x0472
            com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$6 r1 = new com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener$6
            r1.<init>(r14, r0)
            r13.closeVisibleBG(r1)
        L_0x0472:
            boolean r14 = r13.swipeable
            if (r14 == 0) goto L_0x047d
            android.view.VelocityTracker r14 = r13.mVelocityTracker
            r14.recycle()
            r13.mVelocityTracker = r2
        L_0x047d:
            r13.touchedX = r6
            r13.touchedY = r6
            r13.touchedView = r2
            r13.touchedPosition = r5
            r13.isFgSwiping = r4
            r13.bgView = r2
            goto L_0x056d
        L_0x048b:
            boolean r0 = r13.mPaused
            if (r0 == 0) goto L_0x0491
            goto L_0x056d
        L_0x0491:
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            androidx.recyclerview.widget.RecyclerView r5 = r13.rView
            int r5 = r5.getChildCount()
            int[] r1 = new int[r1]
            androidx.recyclerview.widget.RecyclerView r6 = r13.rView
            r6.getLocationOnScreen(r1)
            float r6 = r14.getRawX()
            int r6 = (int) r6
            r7 = r1[r4]
            int r6 = r6 - r7
            float r7 = r14.getRawY()
            int r7 = (int) r7
            r1 = r1[r3]
            int r7 = r7 - r1
            r1 = 0
        L_0x04b4:
            if (r1 >= r5) goto L_0x04cb
            androidx.recyclerview.widget.RecyclerView r3 = r13.rView
            android.view.View r3 = r3.getChildAt(r1)
            r3.getHitRect(r0)
            boolean r8 = r0.contains(r6, r7)
            if (r8 == 0) goto L_0x04c8
            r13.touchedView = r3
            goto L_0x04cb
        L_0x04c8:
            int r1 = r1 + 1
            goto L_0x04b4
        L_0x04cb:
            android.view.View r1 = r13.touchedView
            if (r1 == 0) goto L_0x054a
            float r1 = r14.getRawX()
            r13.touchedX = r1
            float r1 = r14.getRawY()
            r13.touchedY = r1
            androidx.recyclerview.widget.RecyclerView r1 = r13.rView
            android.view.View r3 = r13.touchedView
            int r1 = r1.getChildAdapterPosition(r3)
            r13.touchedPosition = r1
            boolean r1 = r13.longClickable
            if (r1 == 0) goto L_0x04f5
            r13.mLongClickPerformed = r4
            android.os.Handler r1 = r13.handler
            java.lang.Runnable r3 = r13.mLongPressed
            int r5 = r13.LONG_CLICK_DELAY
            long r5 = (long) r5
            r1.postDelayed(r3, r5)
        L_0x04f5:
            boolean r1 = r13.swipeable
            if (r1 == 0) goto L_0x054a
            android.view.VelocityTracker r1 = android.view.VelocityTracker.obtain()
            r13.mVelocityTracker = r1
            android.view.VelocityTracker r1 = r13.mVelocityTracker
            r1.addMovement(r14)
            android.view.View r1 = r13.touchedView
            int r3 = r13.fgViewID
            android.view.View r1 = r1.findViewById(r3)
            r13.fgView = r1
            android.view.View r1 = r13.touchedView
            int r3 = r13.bgViewID
            android.view.View r1 = r1.findViewById(r3)
            r13.bgView = r1
            android.view.View r1 = r13.bgView
            android.view.View r3 = r13.fgView
            int r3 = r3.getHeight()
            r1.setMinimumHeight(r3)
            boolean r1 = r13.bgVisible
            if (r1 == 0) goto L_0x0548
            android.view.View r1 = r13.fgView
            if (r1 == 0) goto L_0x0548
            android.os.Handler r1 = r13.handler
            java.lang.Runnable r3 = r13.mLongPressed
            r1.removeCallbacks(r3)
            float r1 = r14.getRawX()
            int r1 = (int) r1
            float r3 = r14.getRawY()
            int r3 = (int) r3
            android.view.View r5 = r13.fgView
            r5.getGlobalVisibleRect(r0)
            boolean r1 = r0.contains(r1, r3)
            r13.fgPartialViewClicked = r1
            goto L_0x054a
        L_0x0548:
            r13.fgPartialViewClicked = r4
        L_0x054a:
            r14.getRawX()
            r14.getRawY()
            androidx.recyclerview.widget.RecyclerView r14 = r13.rView
            r14.getHitRect(r0)
            boolean r14 = r13.swipeable
            if (r14 == 0) goto L_0x056d
            boolean r14 = r13.bgVisible
            if (r14 == 0) goto L_0x056d
            int r14 = r13.touchedPosition
            int r0 = r13.bgVisiblePosition
            if (r14 == r0) goto L_0x056d
            android.os.Handler r14 = r13.handler
            java.lang.Runnable r0 = r13.mLongPressed
            r14.removeCallbacks(r0)
            r13.closeVisibleBG(r2)
        L_0x056d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.handleTouchEvent(android.view.MotionEvent):boolean");
    }

    public void getTouchCoordinates(MotionEvent motionEvent) {
        int rawY = (int) motionEvent.getRawY();
        if (this.swipeable && this.bgVisible && motionEvent.getActionMasked() == 0 && rawY < this.heightOutsideRView) {
            closeVisibleBG(null);
        }
    }
}
