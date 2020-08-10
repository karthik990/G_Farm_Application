package com.mobiroller.views.twowayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView.Recycler;
import androidx.recyclerview.widget.RecyclerView.State;
import com.mobiroller.preview.C4290R;
import com.mobiroller.views.twowayview.Lanes.LaneInfo;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;

public class GridLayoutManager extends BaseLayoutManager {
    private static final int DEFAULT_NUM_COLS = 2;
    private static final int DEFAULT_NUM_ROWS = 2;
    private static final String LOGTAG = "GridLayoutManager";
    private int mNumColumns;
    private int mNumRows;

    public GridLayoutManager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 2, 2);
    }

    protected GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2, int i3) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.twowayview_GridLayoutManager, i, 0);
        this.mNumColumns = Math.max(1, obtainStyledAttributes.getInt(0, i2));
        this.mNumRows = Math.max(1, obtainStyledAttributes.getInt(1, i3));
        obtainStyledAttributes.recycle();
    }

    public GridLayoutManager(Orientation orientation, int i, int i2) {
        super(orientation);
        this.mNumColumns = i;
        this.mNumRows = i2;
        if (this.mNumColumns < 1) {
            throw new IllegalArgumentException("GridLayoutManager must have at least 1 column");
        } else if (this.mNumRows < 1) {
            throw new IllegalArgumentException("GridLayoutManager must have at least 1 row");
        }
    }

    /* access modifiers changed from: 0000 */
    public int getLaneCount() {
        return isVertical() ? this.mNumColumns : this.mNumRows;
    }

    /* access modifiers changed from: 0000 */
    public void getLaneForPosition(LaneInfo laneInfo, int i, Direction direction) {
        int laneCount = i % getLaneCount();
        laneInfo.set(laneCount, laneCount);
    }

    /* access modifiers changed from: 0000 */
    public void moveLayoutToPosition(int i, int i2, Recycler recycler, State state) {
        Lanes lanes = getLanes();
        lanes.reset(i2);
        getLaneForPosition(this.mTempLaneInfo, i, Direction.END);
        int i3 = this.mTempLaneInfo.startLane;
        if (i3 != 0) {
            View viewForPosition = recycler.getViewForPosition(i);
            measureChild(viewForPosition, Direction.END);
            int decoratedMeasuredHeight = isVertical() ? getDecoratedMeasuredHeight(viewForPosition) : getDecoratedMeasuredWidth(viewForPosition);
            for (int i4 = i3 - 1; i4 >= 0; i4--) {
                lanes.offset(i4, decoratedMeasuredHeight);
            }
        }
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int i) {
        if (this.mNumColumns != i) {
            this.mNumColumns = i;
            if (isVertical()) {
                requestLayout();
            }
        }
    }

    public int getNumRows() {
        return this.mNumRows;
    }

    public void setNumRows(int i) {
        if (this.mNumRows != i) {
            this.mNumRows = i;
            if (!isVertical()) {
                requestLayout();
            }
        }
    }
}
