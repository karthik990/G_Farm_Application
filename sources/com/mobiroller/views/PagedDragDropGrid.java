package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import com.crashlytics.android.Crashlytics;
import com.mobiroller.adapters.PagedDragDropGridAdapter;
import com.mobiroller.containers.OnPageChangedListener;
import com.mobiroller.containers.PagedContainer;

public class PagedDragDropGrid extends HorizontalScrollView implements PagedContainer, OnGestureListener {
    private static final int FLING_VELOCITY = 500;
    private int activePage = 0;
    private boolean activePageRestored = false;
    private PagedDragDropGridAdapter adapter;
    /* access modifiers changed from: private */
    public GestureDetector gestureScanner;
    private DragDropGrid grid;
    private OnPageChangedListener pageChangedListener;

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public PagedDragDropGrid(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPagedScroll();
        initGrid();
    }

    public PagedDragDropGrid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initPagedScroll();
        initGrid();
    }

    public PagedDragDropGrid(Context context) {
        super(context);
        initPagedScroll();
        initGrid();
    }

    private void initGrid() {
        this.grid = new DragDropGrid(getContext());
        addView(this.grid);
    }

    public void initPagedScroll() {
        setScrollBarStyle(0);
        if (!isInEditMode()) {
            this.gestureScanner = new GestureDetector(getContext(), this);
        }
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean onTouchEvent = PagedDragDropGrid.this.gestureScanner.onTouchEvent(motionEvent);
                if (onTouchEvent || (motionEvent.getAction() != 1 && motionEvent.getAction() != 3)) {
                    return onTouchEvent;
                }
                int scrollX = PagedDragDropGrid.this.getScrollX();
                int measuredWidth = view.getMeasuredWidth();
                PagedDragDropGrid.this.scrollToPage((scrollX + (measuredWidth / 2)) / measuredWidth);
                return true;
            }
        });
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.pageChangedListener = onPageChangedListener;
    }

    public void setAdapter(PagedDragDropGridAdapter pagedDragDropGridAdapter) {
        this.adapter = pagedDragDropGridAdapter;
        this.grid.setAdapter(pagedDragDropGridAdapter);
        this.grid.setContainer(this);
    }

    public boolean onLongClick(View view) {
        return this.grid.onLongClick(view);
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        initGrid();
        this.grid.setAdapter(this.adapter);
        this.grid.setContainer(this);
    }

    public void scrollToPage(int i) {
        try {
            this.activePage = i;
            smoothScrollTo(getMeasuredWidth() * i, 0);
            this.pageChangedListener.onPageChanged(this, i);
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.log("PagedDragDropGrid scrollToPage crash!");
        }
    }

    public void scrollLeft() {
        this.adapter.moveItemToPreviousPage(this.activePage, 0);
        int i = this.activePage - 1;
        if (canScrollToPreviousPage()) {
            scrollToPage(i);
        }
    }

    public void scrollRight() {
        try {
            this.adapter.moveItemToNextPage(this.activePage, 0);
            int i = this.activePage + 1;
            if (canScrollToNextPage()) {
                scrollToPage(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.log("PagedDragDropGrid scrollRight crash!");
        }
    }

    public int currentPage() {
        return this.activePage;
    }

    public void enableScroll() {
        requestDisallowInterceptTouchEvent(false);
    }

    public void disableScroll() {
        requestDisallowInterceptTouchEvent(true);
    }

    public boolean canScrollToNextPage() {
        if (this.activePage + 1 < this.adapter.pageCount()) {
            return true;
        }
        return false;
    }

    public boolean canScrollToPreviousPage() {
        return this.activePage - 1 >= 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.activePageRestored) {
            this.activePageRestored = false;
            scrollToRestoredPage();
        }
    }

    private void scrollToRestoredPage() {
        scrollToPage(this.activePage);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (f < -500.0f) {
            scrollRight();
            return true;
        } else if (f <= 500.0f) {
            return false;
        } else {
            scrollLeft();
            return true;
        }
    }
}
