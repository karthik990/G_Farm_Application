package com.bumptech.glide.request.target;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import com.bumptech.glide.C1156R;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public abstract class ViewTarget<T extends View, Z> extends BaseTarget<Z> {
    private static final String TAG = "ViewTarget";
    private static boolean isTagUsedAtLeastOnce;
    private static int tagId = C1156R.C1159id.glide_custom_view_target_tag;
    private OnAttachStateChangeListener attachStateListener;
    private boolean isAttachStateListenerAdded;
    private boolean isClearedByUs;
    private final SizeDeterminer sizeDeterminer;
    protected final T view;

    static final class SizeDeterminer {
        private static final int PENDING_SIZE = 0;
        static Integer maxDisplayLength;
        private final List<SizeReadyCallback> cbs = new ArrayList();
        private SizeDeterminerLayoutListener layoutListener;
        private final View view;
        boolean waitForLayout;

        private static final class SizeDeterminerLayoutListener implements OnPreDrawListener {
            private final WeakReference<SizeDeterminer> sizeDeterminerRef;

            SizeDeterminerLayoutListener(SizeDeterminer sizeDeterminer) {
                this.sizeDeterminerRef = new WeakReference<>(sizeDeterminer);
            }

            public boolean onPreDraw() {
                String str = ViewTarget.TAG;
                if (Log.isLoggable(str, 2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("OnGlobalLayoutListener called attachStateListener=");
                    sb.append(this);
                    Log.v(str, sb.toString());
                }
                SizeDeterminer sizeDeterminer = (SizeDeterminer) this.sizeDeterminerRef.get();
                if (sizeDeterminer != null) {
                    sizeDeterminer.checkCurrentDimens();
                }
                return true;
            }
        }

        private boolean isDimensionValid(int i) {
            return i > 0 || i == Integer.MIN_VALUE;
        }

        SizeDeterminer(View view2) {
            this.view = view2;
        }

        private static int getMaxDisplayLength(Context context) {
            if (maxDisplayLength == null) {
                Display defaultDisplay = ((WindowManager) Preconditions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                maxDisplayLength = Integer.valueOf(Math.max(point.x, point.y));
            }
            return maxDisplayLength.intValue();
        }

        private void notifyCbs(int i, int i2) {
            Iterator it = new ArrayList(this.cbs).iterator();
            while (it.hasNext()) {
                ((SizeReadyCallback) it.next()).onSizeReady(i, i2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void checkCurrentDimens() {
            if (!this.cbs.isEmpty()) {
                int targetWidth = getTargetWidth();
                int targetHeight = getTargetHeight();
                if (isViewStateAndSizeValid(targetWidth, targetHeight)) {
                    notifyCbs(targetWidth, targetHeight);
                    clearCallbacksAndListener();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void getSize(SizeReadyCallback sizeReadyCallback) {
            int targetWidth = getTargetWidth();
            int targetHeight = getTargetHeight();
            if (isViewStateAndSizeValid(targetWidth, targetHeight)) {
                sizeReadyCallback.onSizeReady(targetWidth, targetHeight);
                return;
            }
            if (!this.cbs.contains(sizeReadyCallback)) {
                this.cbs.add(sizeReadyCallback);
            }
            if (this.layoutListener == null) {
                ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
                this.layoutListener = new SizeDeterminerLayoutListener(this);
                viewTreeObserver.addOnPreDrawListener(this.layoutListener);
            }
        }

        /* access modifiers changed from: 0000 */
        public void removeCallback(SizeReadyCallback sizeReadyCallback) {
            this.cbs.remove(sizeReadyCallback);
        }

        /* access modifiers changed from: 0000 */
        public void clearCallbacksAndListener() {
            ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this.layoutListener);
            }
            this.layoutListener = null;
            this.cbs.clear();
        }

        private boolean isViewStateAndSizeValid(int i, int i2) {
            return isDimensionValid(i) && isDimensionValid(i2);
        }

        private int getTargetHeight() {
            int paddingTop = this.view.getPaddingTop() + this.view.getPaddingBottom();
            LayoutParams layoutParams = this.view.getLayoutParams();
            return getTargetDimen(this.view.getHeight(), layoutParams != null ? layoutParams.height : 0, paddingTop);
        }

        private int getTargetWidth() {
            int paddingLeft = this.view.getPaddingLeft() + this.view.getPaddingRight();
            LayoutParams layoutParams = this.view.getLayoutParams();
            return getTargetDimen(this.view.getWidth(), layoutParams != null ? layoutParams.width : 0, paddingLeft);
        }

        private int getTargetDimen(int i, int i2, int i3) {
            int i4 = i2 - i3;
            if (i4 > 0) {
                return i4;
            }
            if (this.waitForLayout && this.view.isLayoutRequested()) {
                return 0;
            }
            int i5 = i - i3;
            if (i5 > 0) {
                return i5;
            }
            if (this.view.isLayoutRequested() || i2 != -2) {
                return 0;
            }
            String str = ViewTarget.TAG;
            if (Log.isLoggable(str, 4)) {
                Log.i(str, "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return getMaxDisplayLength(this.view.getContext());
        }
    }

    public ViewTarget(T t) {
        this.view = (View) Preconditions.checkNotNull(t);
        this.sizeDeterminer = new SizeDeterminer(t);
    }

    @Deprecated
    public ViewTarget(T t, boolean z) {
        this(t);
        if (z) {
            waitForLayout();
        }
    }

    public final ViewTarget<T, Z> clearOnDetach() {
        if (this.attachStateListener != null) {
            return this;
        }
        this.attachStateListener = new OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View view) {
                ViewTarget.this.resumeMyRequest();
            }

            public void onViewDetachedFromWindow(View view) {
                ViewTarget.this.pauseMyRequest();
            }
        };
        maybeAddAttachStateListener();
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void resumeMyRequest() {
        Request request = getRequest();
        if (request != null && request.isCleared()) {
            request.begin();
        }
    }

    /* access modifiers changed from: 0000 */
    public void pauseMyRequest() {
        Request request = getRequest();
        if (request != null) {
            this.isClearedByUs = true;
            request.clear();
            this.isClearedByUs = false;
        }
    }

    public final ViewTarget<T, Z> waitForLayout() {
        this.sizeDeterminer.waitForLayout = true;
        return this;
    }

    public void onLoadStarted(Drawable drawable) {
        super.onLoadStarted(drawable);
        maybeAddAttachStateListener();
    }

    private void maybeAddAttachStateListener() {
        OnAttachStateChangeListener onAttachStateChangeListener = this.attachStateListener;
        if (onAttachStateChangeListener != null && !this.isAttachStateListenerAdded) {
            this.view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            this.isAttachStateListenerAdded = true;
        }
    }

    private void maybeRemoveAttachStateListener() {
        OnAttachStateChangeListener onAttachStateChangeListener = this.attachStateListener;
        if (onAttachStateChangeListener != null && this.isAttachStateListenerAdded) {
            this.view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            this.isAttachStateListenerAdded = false;
        }
    }

    public T getView() {
        return this.view;
    }

    public void getSize(SizeReadyCallback sizeReadyCallback) {
        this.sizeDeterminer.getSize(sizeReadyCallback);
    }

    public void removeCallback(SizeReadyCallback sizeReadyCallback) {
        this.sizeDeterminer.removeCallback(sizeReadyCallback);
    }

    public void onLoadCleared(Drawable drawable) {
        super.onLoadCleared(drawable);
        this.sizeDeterminer.clearCallbacksAndListener();
        if (!this.isClearedByUs) {
            maybeRemoveAttachStateListener();
        }
    }

    public void setRequest(Request request) {
        setTag(request);
    }

    public Request getRequest() {
        Object tag = getTag();
        if (tag == null) {
            return null;
        }
        if (tag instanceof Request) {
            return (Request) tag;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Target for: ");
        sb.append(this.view);
        return sb.toString();
    }

    private void setTag(Object obj) {
        isTagUsedAtLeastOnce = true;
        this.view.setTag(tagId, obj);
    }

    private Object getTag() {
        return this.view.getTag(tagId);
    }

    @Deprecated
    public static void setTagId(int i) {
        if (!isTagUsedAtLeastOnce) {
            tagId = i;
            return;
        }
        throw new IllegalArgumentException("You cannot set the tag id more than once or change the tag id after the first request has been made");
    }
}
