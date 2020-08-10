package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.PageTransformer;

public class VerticalViewPager extends ViewPager {

    private static final class VerticalPageTransformer implements PageTransformer {
        private VerticalPageTransformer() {
        }

        public void transformPage(View view, float f) {
            int width = view.getWidth();
            int height = view.getHeight();
            if (f < -1.0f) {
                view.setAlpha(0.0f);
            } else if (f <= 1.0f) {
                view.setAlpha(1.0f);
                view.setTranslationX(((float) width) * (-f));
                view.setTranslationY(f * ((float) height));
            } else {
                view.setAlpha(0.0f);
            }
        }
    }

    public boolean canScrollHorizontally(int i) {
        return false;
    }

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public boolean canScrollVertically(int i) {
        return super.canScrollHorizontally(i);
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(2);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            return super.onInterceptTouchEvent(flipXY(motionEvent));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(flipXY(motionEvent));
        flipXY(motionEvent);
        return onTouchEvent;
    }

    private MotionEvent flipXY(MotionEvent motionEvent) {
        float width = (float) getWidth();
        float height = (float) getHeight();
        motionEvent.setLocation((motionEvent.getY() / height) * width, (motionEvent.getX() / width) * height);
        return motionEvent;
    }
}
