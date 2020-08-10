package com.mobiroller.containers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import androidx.viewpager.widget.ViewPager;

public class ClickableViewPager extends ViewPager {
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    private class TapGestureListener extends SimpleOnGestureListener {
        private TapGestureListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (ClickableViewPager.this.mOnItemClickListener != null) {
                ClickableViewPager.this.mOnItemClickListener.onItemClick(ClickableViewPager.this.getCurrentItem());
            }
            return true;
        }
    }

    public ClickableViewPager(Context context) {
        super(context);
        setup();
    }

    public ClickableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup();
    }

    private void setup() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new TapGestureListener());
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
