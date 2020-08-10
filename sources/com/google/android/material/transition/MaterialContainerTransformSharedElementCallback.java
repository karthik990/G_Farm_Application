package com.google.android.material.transition;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.Shapeable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class MaterialContainerTransformSharedElementCallback extends SharedElementCallback {
    /* access modifiers changed from: private */
    public static WeakReference<View> capturedSharedElement;
    private boolean entering = true;
    private Rect returnEndBounds;
    private boolean transparentWindowBackgroundEnabled = true;

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        capturedSharedElement = new WeakReference<>(view);
        return super.onCaptureSharedElementSnapshot(view, matrix, rectF);
    }

    public View onCreateSnapshotView(Context context, Parcelable parcelable) {
        View onCreateSnapshotView = super.onCreateSnapshotView(context, parcelable);
        WeakReference<View> weakReference = capturedSharedElement;
        if (weakReference != null && (weakReference.get() instanceof Shapeable)) {
            onCreateSnapshotView.setTag(((Shapeable) capturedSharedElement.get()).getShapeAppearanceModel());
        }
        return onCreateSnapshotView;
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
        if (!list.isEmpty() && !map.isEmpty()) {
            View view = (View) map.get(list.get(0));
            if (view != null) {
                Activity activity = ContextUtils.getActivity(view.getContext());
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (this.entering) {
                        setUpEnterTransform(window);
                    } else {
                        setUpReturnTransform(activity, window);
                    }
                }
            }
        }
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
        if (!list2.isEmpty() && !list3.isEmpty()) {
            ((View) list2.get(0)).setTag(list3.get(0));
        }
        if (!this.entering && !list2.isEmpty() && this.returnEndBounds != null) {
            View view = (View) list2.get(0);
            view.measure(MeasureSpec.makeMeasureSpec(this.returnEndBounds.width(), 1073741824), MeasureSpec.makeMeasureSpec(this.returnEndBounds.height(), 1073741824));
            view.layout(this.returnEndBounds.left, this.returnEndBounds.top, this.returnEndBounds.right, this.returnEndBounds.bottom);
        }
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
        if (!list2.isEmpty() && (((View) list2.get(0)).getTag() instanceof View)) {
            ((View) list2.get(0)).setTag(null);
        }
        if (!this.entering && !list2.isEmpty()) {
            this.returnEndBounds = TransitionUtils.getRelativeBoundsRect((View) list2.get(0));
        }
        this.entering = false;
    }

    public boolean isTransparentWindowBackgroundEnabled() {
        return this.transparentWindowBackgroundEnabled;
    }

    public void setTransparentWindowBackgroundEnabled(boolean z) {
        this.transparentWindowBackgroundEnabled = z;
    }

    private void setUpEnterTransform(final Window window) {
        Transition sharedElementEnterTransition = window.getSharedElementEnterTransition();
        if (sharedElementEnterTransition instanceof MaterialContainerTransform) {
            MaterialContainerTransform materialContainerTransform = (MaterialContainerTransform) sharedElementEnterTransition;
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, materialContainerTransform);
                materialContainerTransform.addListener(new TransitionListenerAdapter() {
                    public void onTransitionStart(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
                    }

                    public void onTransitionEnd(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.restoreWindowBackground(window);
                    }
                });
            }
        }
    }

    private void setUpReturnTransform(final Activity activity, final Window window) {
        Transition sharedElementReturnTransition = window.getSharedElementReturnTransition();
        if (sharedElementReturnTransition instanceof MaterialContainerTransform) {
            MaterialContainerTransform materialContainerTransform = (MaterialContainerTransform) sharedElementReturnTransition;
            materialContainerTransform.setHoldAtEndEnabled(true);
            materialContainerTransform.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(Transition transition) {
                    if (!(MaterialContainerTransformSharedElementCallback.capturedSharedElement == null || MaterialContainerTransformSharedElementCallback.capturedSharedElement.get() == null)) {
                        ((View) MaterialContainerTransformSharedElementCallback.capturedSharedElement.get()).setAlpha(1.0f);
                        MaterialContainerTransformSharedElementCallback.capturedSharedElement = null;
                    }
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }
            });
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, materialContainerTransform);
                materialContainerTransform.addListener(new TransitionListenerAdapter() {
                    public void onTransitionStart(Transition transition) {
                        MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public static void removeWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
    }

    /* access modifiers changed from: private */
    public static void restoreWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().clearColorFilter();
    }

    private static void updateBackgroundFadeDuration(Window window, MaterialContainerTransform materialContainerTransform) {
        window.setTransitionBackgroundFadeDuration(materialContainerTransform.getDuration() * 2);
    }
}
