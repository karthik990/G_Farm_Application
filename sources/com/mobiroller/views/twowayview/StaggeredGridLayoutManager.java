package com.mobiroller.views.twowayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.RecyclerView.Recycler;
import androidx.recyclerview.widget.RecyclerView.State;
import com.mobiroller.preview.C4290R;
import com.mobiroller.views.twowayview.Lanes.LaneInfo;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;

public class StaggeredGridLayoutManager extends GridLayoutManager {
    private static final int DEFAULT_NUM_COLS = 2;
    private static final int DEFAULT_NUM_ROWS = 2;
    private static final String LOGTAG = "StaggeredGridLayoutManager";

    public static class LayoutParams extends androidx.recyclerview.widget.RecyclerView.LayoutParams {
        private static final int DEFAULT_SPAN = 1;
        public int span;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.span = 1;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.twowayview_StaggeredGridViewChild);
            this.span = Math.max(1, obtainStyledAttributes.getInt(0, -1));
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            init(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            init(marginLayoutParams);
        }

        private void init(android.view.ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                this.span = ((LayoutParams) layoutParams).span;
            } else {
                this.span = 1;
            }
        }
    }

    protected static class StaggeredItemEntry extends ItemEntry {
        public static final Creator<StaggeredItemEntry> CREATOR = new Creator<StaggeredItemEntry>() {
            public StaggeredItemEntry createFromParcel(Parcel parcel) {
                return new StaggeredItemEntry(parcel);
            }

            public StaggeredItemEntry[] newArray(int i) {
                return new StaggeredItemEntry[i];
            }
        };
        /* access modifiers changed from: private */
        public int height;
        /* access modifiers changed from: private */
        public final int span;
        /* access modifiers changed from: private */
        public int width;

        public StaggeredItemEntry(int i, int i2, int i3) {
            super(i, i2);
            this.span = i3;
        }

        public StaggeredItemEntry(Parcel parcel) {
            super(parcel);
            this.span = parcel.readInt();
            this.width = parcel.readInt();
            this.height = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.span);
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
        }
    }

    public StaggeredGridLayoutManager(Context context) {
        this(context, null);
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 2, 2);
    }

    public StaggeredGridLayoutManager(Orientation orientation, int i, int i2) {
        super(orientation, i, i2);
    }

    /* access modifiers changed from: 0000 */
    public int getLaneSpanForChild(View view) {
        return ((LayoutParams) view.getLayoutParams()).span;
    }

    /* access modifiers changed from: 0000 */
    public int getLaneSpanForPosition(int i) {
        StaggeredItemEntry staggeredItemEntry = (StaggeredItemEntry) getItemEntryForPosition(i);
        if (staggeredItemEntry != null) {
            return staggeredItemEntry.span;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Could not find span for position ");
        sb.append(i);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void getLaneForPosition(LaneInfo laneInfo, int i, Direction direction) {
        StaggeredItemEntry staggeredItemEntry = (StaggeredItemEntry) getItemEntryForPosition(i);
        if (staggeredItemEntry != null) {
            laneInfo.set(staggeredItemEntry.startLane, staggeredItemEntry.anchorLane);
        } else {
            laneInfo.setUndefined();
        }
    }

    /* access modifiers changed from: 0000 */
    public void getLaneForChild(LaneInfo laneInfo, View view, Direction direction) {
        super.getLaneForChild(laneInfo, view, direction);
        if (laneInfo.isUndefined()) {
            getLanes().findLane(laneInfo, getLaneSpanForChild(view), direction);
        }
    }

    /* access modifiers changed from: 0000 */
    public void moveLayoutToPosition(int i, int i2, Recycler recycler, State state) {
        StaggeredItemEntry staggeredItemEntry;
        int i3 = i;
        boolean isVertical = isVertical();
        Lanes lanes = getLanes();
        lanes.reset(0);
        for (int i4 = 0; i4 <= i3; i4++) {
            StaggeredItemEntry staggeredItemEntry2 = (StaggeredItemEntry) getItemEntryForPosition(i4);
            if (staggeredItemEntry2 != null) {
                this.mTempLaneInfo.set(staggeredItemEntry2.startLane, staggeredItemEntry2.anchorLane);
                if (this.mTempLaneInfo.isUndefined()) {
                    lanes.findLane(this.mTempLaneInfo, getLaneSpanForPosition(i4), Direction.END);
                    staggeredItemEntry2.setLane(this.mTempLaneInfo);
                }
                lanes.getChildFrame(this.mTempRect, staggeredItemEntry2.width, staggeredItemEntry2.height, this.mTempLaneInfo, Direction.END);
                staggeredItemEntry = staggeredItemEntry2;
                Recycler recycler2 = recycler;
            } else {
                View viewForPosition = recycler.getViewForPosition(i4);
                measureChild(viewForPosition, Direction.END);
                StaggeredItemEntry staggeredItemEntry3 = (StaggeredItemEntry) getItemEntryForPosition(i4);
                this.mTempLaneInfo.set(staggeredItemEntry3.startLane, staggeredItemEntry3.anchorLane);
                lanes.getChildFrame(this.mTempRect, getDecoratedMeasuredWidth(viewForPosition), getDecoratedMeasuredHeight(viewForPosition), this.mTempLaneInfo, Direction.END);
                cacheItemFrame(staggeredItemEntry3, this.mTempRect);
                staggeredItemEntry = staggeredItemEntry3;
            }
            if (i4 != i3) {
                pushChildFrame(staggeredItemEntry, this.mTempRect, staggeredItemEntry.startLane, staggeredItemEntry.span, Direction.END);
            }
        }
        lanes.getLane(this.mTempLaneInfo.startLane, this.mTempRect);
        lanes.reset(Direction.END);
        Rect rect = this.mTempRect;
        lanes.offset(i2 - (isVertical ? rect.bottom : rect.right));
    }

    /* access modifiers changed from: 0000 */
    public ItemEntry cacheChildLaneAndSpan(View view, Direction direction) {
        int position = getPosition(view);
        this.mTempLaneInfo.setUndefined();
        StaggeredItemEntry staggeredItemEntry = (StaggeredItemEntry) getItemEntryForPosition(position);
        if (staggeredItemEntry != null) {
            this.mTempLaneInfo.set(staggeredItemEntry.startLane, staggeredItemEntry.anchorLane);
        }
        if (this.mTempLaneInfo.isUndefined()) {
            getLaneForChild(this.mTempLaneInfo, view, direction);
        }
        if (staggeredItemEntry == null) {
            StaggeredItemEntry staggeredItemEntry2 = new StaggeredItemEntry(this.mTempLaneInfo.startLane, this.mTempLaneInfo.anchorLane, getLaneSpanForChild(view));
            setItemEntryForPosition(position, staggeredItemEntry2);
            return staggeredItemEntry2;
        }
        staggeredItemEntry.setLane(this.mTempLaneInfo);
        return staggeredItemEntry;
    }

    /* access modifiers changed from: 0000 */
    public void cacheItemFrame(StaggeredItemEntry staggeredItemEntry, Rect rect) {
        staggeredItemEntry.width = rect.right - rect.left;
        staggeredItemEntry.height = rect.bottom - rect.top;
    }

    /* access modifiers changed from: 0000 */
    public ItemEntry cacheChildFrame(View view, Rect rect) {
        StaggeredItemEntry staggeredItemEntry = (StaggeredItemEntry) getItemEntryForPosition(getPosition(view));
        if (staggeredItemEntry != null) {
            cacheItemFrame(staggeredItemEntry, rect);
            return staggeredItemEntry;
        }
        throw new IllegalStateException("Tried to cache frame on undefined item");
    }

    public boolean checkLayoutParams(androidx.recyclerview.widget.RecyclerView.LayoutParams layoutParams) {
        boolean checkLayoutParams = super.checkLayoutParams(layoutParams);
        if (!(layoutParams instanceof LayoutParams)) {
            return checkLayoutParams;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        boolean z = true;
        if (layoutParams2.span < 1 || layoutParams2.span > getLaneCount()) {
            z = false;
        }
        return checkLayoutParams & z;
    }

    public LayoutParams generateDefaultLayoutParams() {
        if (isVertical()) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2 = new LayoutParams((MarginLayoutParams) layoutParams);
        if (isVertical()) {
            layoutParams2.width = -1;
            layoutParams2.height = layoutParams.height;
        } else {
            layoutParams2.width = layoutParams.width;
            layoutParams2.height = -1;
        }
        if (layoutParams instanceof LayoutParams) {
            layoutParams2.span = Math.max(1, Math.min(((LayoutParams) layoutParams).span, getLaneCount()));
        }
        return layoutParams2;
    }

    public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }
}
