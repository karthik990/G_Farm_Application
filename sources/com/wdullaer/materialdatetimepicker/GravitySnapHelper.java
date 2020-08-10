package com.wdullaer.materialdatetimepicker;

import android.os.Build.VERSION;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

public class GravitySnapHelper extends LinearSnapHelper {
    private int gravity;
    private OrientationHelper horizontalHelper;
    private boolean isRtlHorizontal;
    /* access modifiers changed from: private */
    public SnapListener listener;
    private OnScrollListener mScrollListener;
    /* access modifiers changed from: private */
    public boolean snapping;
    private OrientationHelper verticalHelper;

    public interface SnapListener {
        void onSnap(int i);
    }

    public GravitySnapHelper(int i) {
        this(i, null);
    }

    public GravitySnapHelper(int i, SnapListener snapListener) {
        this.mScrollListener = new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 2) {
                    GravitySnapHelper.this.snapping = false;
                }
                if (i == 0 && GravitySnapHelper.this.snapping && GravitySnapHelper.this.listener != null) {
                    int access$200 = GravitySnapHelper.this.getSnappedPosition(recyclerView);
                    if (access$200 != -1) {
                        GravitySnapHelper.this.listener.onSnap(access$200);
                    }
                    GravitySnapHelper.this.snapping = false;
                }
            }
        };
        if (i == 8388611 || i == 8388613 || i == 80 || i == 48) {
            this.gravity = i;
            this.listener = snapListener;
            return;
        }
        throw new IllegalArgumentException("Invalid gravity value. Use START | END | BOTTOM | TOP constants");
    }

    public void attachToRecyclerView(RecyclerView recyclerView) throws IllegalStateException {
        if (recyclerView != null) {
            int i = this.gravity;
            if ((i == 8388611 || i == 8388613) && VERSION.SDK_INT >= 17) {
                boolean z = true;
                if (recyclerView.getContext().getResources().getConfiguration().getLayoutDirection() != 1) {
                    z = false;
                }
                this.isRtlHorizontal = z;
            }
            if (this.listener != null) {
                recyclerView.addOnScrollListener(this.mScrollListener);
            }
        }
        super.attachToRecyclerView(recyclerView);
    }

    public int[] calculateDistanceToFinalSnap(LayoutManager layoutManager, View view) {
        int[] iArr = new int[2];
        if (!layoutManager.canScrollHorizontally()) {
            iArr[0] = 0;
        } else if (this.gravity == 8388611) {
            iArr[0] = distanceToStart(view, getHorizontalHelper(layoutManager), false);
        } else {
            iArr[0] = distanceToEnd(view, getHorizontalHelper(layoutManager), false);
        }
        if (!layoutManager.canScrollVertically()) {
            iArr[1] = 0;
        } else if (this.gravity == 48) {
            iArr[1] = distanceToStart(view, getVerticalHelper(layoutManager), false);
        } else {
            iArr[1] = distanceToEnd(view, getVerticalHelper(layoutManager), false);
        }
        return iArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View findSnapView(androidx.recyclerview.widget.RecyclerView.LayoutManager r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof androidx.recyclerview.widget.LinearLayoutManager
            if (r0 == 0) goto L_0x003d
            int r0 = r2.gravity
            r1 = 48
            if (r0 == r1) goto L_0x0034
            r1 = 80
            if (r0 == r1) goto L_0x002b
            r1 = 8388611(0x800003, float:1.1754948E-38)
            if (r0 == r1) goto L_0x0022
            r1 = 8388613(0x800005, float:1.175495E-38)
            if (r0 == r1) goto L_0x0019
            goto L_0x003d
        L_0x0019:
            androidx.recyclerview.widget.OrientationHelper r0 = r2.getHorizontalHelper(r3)
            android.view.View r3 = r2.findEndView(r3, r0)
            goto L_0x003e
        L_0x0022:
            androidx.recyclerview.widget.OrientationHelper r0 = r2.getHorizontalHelper(r3)
            android.view.View r3 = r2.findStartView(r3, r0)
            goto L_0x003e
        L_0x002b:
            androidx.recyclerview.widget.OrientationHelper r0 = r2.getVerticalHelper(r3)
            android.view.View r3 = r2.findEndView(r3, r0)
            goto L_0x003e
        L_0x0034:
            androidx.recyclerview.widget.OrientationHelper r0 = r2.getVerticalHelper(r3)
            android.view.View r3 = r2.findStartView(r3, r0)
            goto L_0x003e
        L_0x003d:
            r3 = 0
        L_0x003e:
            if (r3 == 0) goto L_0x0042
            r0 = 1
            goto L_0x0043
        L_0x0042:
            r0 = 0
        L_0x0043:
            r2.snapping = r0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wdullaer.materialdatetimepicker.GravitySnapHelper.findSnapView(androidx.recyclerview.widget.RecyclerView$LayoutManager):android.view.View");
    }

    private int distanceToStart(View view, OrientationHelper orientationHelper, boolean z) {
        if (!this.isRtlHorizontal || z) {
            return orientationHelper.getDecoratedStart(view) - orientationHelper.getStartAfterPadding();
        }
        return distanceToEnd(view, orientationHelper, true);
    }

    private int distanceToEnd(View view, OrientationHelper orientationHelper, boolean z) {
        if (!this.isRtlHorizontal || z) {
            return orientationHelper.getDecoratedEnd(view) - orientationHelper.getEndAfterPadding();
        }
        return distanceToStart(view, orientationHelper, true);
    }

    private View findStartView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int i;
        float f;
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (findFirstVisibleItemPosition == -1) {
            return null;
        }
        View findViewByPosition = layoutManager.findViewByPosition(findFirstVisibleItemPosition);
        if (this.isRtlHorizontal) {
            f = (float) (orientationHelper.getTotalSpace() - orientationHelper.getDecoratedStart(findViewByPosition));
            i = orientationHelper.getDecoratedMeasurement(findViewByPosition);
        } else {
            f = (float) orientationHelper.getDecoratedEnd(findViewByPosition);
            i = orientationHelper.getDecoratedMeasurement(findViewByPosition);
        }
        float f2 = f / ((float) i);
        boolean z = linearLayoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1;
        if (f2 > 0.5f && !z) {
            return findViewByPosition;
        }
        if (z) {
            return null;
        }
        return layoutManager.findViewByPosition(findFirstVisibleItemPosition + 1);
    }

    private View findEndView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int i;
        float f;
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        if (findLastVisibleItemPosition == -1) {
            return null;
        }
        View findViewByPosition = layoutManager.findViewByPosition(findLastVisibleItemPosition);
        if (this.isRtlHorizontal) {
            f = (float) orientationHelper.getDecoratedEnd(findViewByPosition);
            i = orientationHelper.getDecoratedMeasurement(findViewByPosition);
        } else {
            f = (float) (orientationHelper.getTotalSpace() - orientationHelper.getDecoratedStart(findViewByPosition));
            i = orientationHelper.getDecoratedMeasurement(findViewByPosition);
        }
        float f2 = f / ((float) i);
        boolean z = linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
        if (f2 > 0.5f && !z) {
            return findViewByPosition;
        }
        if (z) {
            return null;
        }
        return layoutManager.findViewByPosition(findLastVisibleItemPosition - 1);
    }

    /* access modifiers changed from: private */
    public int getSnappedPosition(RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int i = this.gravity;
            if (i == 8388611 || i == 48) {
                return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            }
            if (i == 8388613 || i == 80) {
                return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
        }
        return -1;
    }

    private OrientationHelper getVerticalHelper(LayoutManager layoutManager) {
        if (this.verticalHelper == null) {
            this.verticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.verticalHelper;
    }

    private OrientationHelper getHorizontalHelper(LayoutManager layoutManager) {
        if (this.horizontalHelper == null) {
            this.horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.horizontalHelper;
    }
}
