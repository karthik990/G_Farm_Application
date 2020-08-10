package com.mobiroller.views.twowayview;

import android.os.Build.VERSION;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;

abstract class ClickItemTouchListener implements OnItemTouchListener {
    private static final String LOGTAG = "ClickItemTouchListener";

    private class ItemClickGestureListener extends SimpleOnGestureListener {
        private final RecyclerView mHostView;
        private View mTargetChild;

        public ItemClickGestureListener(RecyclerView recyclerView) {
            this.mHostView = recyclerView;
        }

        public void dispatchSingleTapUpIfNeeded(MotionEvent motionEvent) {
            if (this.mTargetChild != null) {
                onSingleTapUp(motionEvent);
            }
        }

        public boolean onDown(MotionEvent motionEvent) {
            this.mTargetChild = this.mHostView.findChildViewUnder((float) ((int) motionEvent.getX()), (float) ((int) motionEvent.getY()));
            return this.mTargetChild != null;
        }

        public void onShowPress(MotionEvent motionEvent) {
            View view = this.mTargetChild;
            if (view != null) {
                view.setPressed(true);
            }
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            View view = this.mTargetChild;
            if (view == null) {
                return false;
            }
            view.setPressed(false);
            int childPosition = this.mHostView.getChildPosition(this.mTargetChild);
            boolean performItemClick = ClickItemTouchListener.this.performItemClick(this.mHostView, this.mTargetChild, childPosition, this.mHostView.getAdapter().getItemId(childPosition));
            this.mTargetChild = null;
            return performItemClick;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            View view = this.mTargetChild;
            if (view == null) {
                return false;
            }
            view.setPressed(false);
            this.mTargetChild = null;
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            View view = this.mTargetChild;
            if (view != null) {
                int childPosition = this.mHostView.getChildPosition(view);
                if (ClickItemTouchListener.this.performItemLongClick(this.mHostView, this.mTargetChild, childPosition, this.mHostView.getAdapter().getItemId(childPosition))) {
                    this.mTargetChild.setPressed(false);
                    this.mTargetChild = null;
                }
            }
        }
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean performItemClick(RecyclerView recyclerView, View view, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract boolean performItemLongClick(RecyclerView recyclerView, View view, int i, long j);

    ClickItemTouchListener(RecyclerView recyclerView) {
    }

    private boolean isAttachedToWindow(RecyclerView recyclerView) {
        if (VERSION.SDK_INT >= 19) {
            return recyclerView.isAttachedToWindow();
        }
        return recyclerView.getHandler() != null;
    }

    private boolean hasAdapter(RecyclerView recyclerView) {
        return recyclerView.getAdapter() != null;
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (!isAttachedToWindow(recyclerView) || !hasAdapter(recyclerView)) {
        }
        return false;
    }
}
