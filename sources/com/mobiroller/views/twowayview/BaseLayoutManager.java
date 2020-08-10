package com.mobiroller.views.twowayview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import androidx.recyclerview.widget.RecyclerView.Recycler;
import androidx.recyclerview.widget.RecyclerView.State;
import com.mobiroller.views.twowayview.Lanes.LaneInfo;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;

public abstract class BaseLayoutManager extends TwoWayLayoutManager {
    protected final Rect mChildFrame;
    private ItemEntries mItemEntries;
    private ItemEntries mItemEntriesToRestore;
    private Lanes mLanes;
    private Lanes mLanesToRestore;
    protected final LaneInfo mTempLaneInfo;
    protected final Rect mTempRect;

    /* renamed from: com.mobiroller.views.twowayview.BaseLayoutManager$1 */
    static /* synthetic */ class C44751 {

        /* renamed from: $SwitchMap$com$mobiroller$views$twowayview$BaseLayoutManager$UpdateOp */
        static final /* synthetic */ int[] f2233xf2a83c54 = new int[UpdateOp.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.mobiroller.views.twowayview.BaseLayoutManager$UpdateOp[] r0 = com.mobiroller.views.twowayview.BaseLayoutManager.UpdateOp.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2233xf2a83c54 = r0
                int[] r0 = f2233xf2a83c54     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.mobiroller.views.twowayview.BaseLayoutManager$UpdateOp r1 = com.mobiroller.views.twowayview.BaseLayoutManager.UpdateOp.ADD     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2233xf2a83c54     // Catch:{ NoSuchFieldError -> 0x001f }
                com.mobiroller.views.twowayview.BaseLayoutManager$UpdateOp r1 = com.mobiroller.views.twowayview.BaseLayoutManager.UpdateOp.REMOVE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2233xf2a83c54     // Catch:{ NoSuchFieldError -> 0x002a }
                com.mobiroller.views.twowayview.BaseLayoutManager$UpdateOp r1 = com.mobiroller.views.twowayview.BaseLayoutManager.UpdateOp.MOVE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.views.twowayview.BaseLayoutManager.C44751.<clinit>():void");
        }
    }

    protected static class ItemEntry implements Parcelable {
        public static final Creator<ItemEntry> CREATOR = new Creator<ItemEntry>() {
            public ItemEntry createFromParcel(Parcel parcel) {
                return new ItemEntry(parcel);
            }

            public ItemEntry[] newArray(int i) {
                return new ItemEntry[i];
            }
        };
        public int anchorLane;
        private int[] spanMargins;
        public int startLane;

        public int describeContents() {
            return 0;
        }

        public ItemEntry(int i, int i2) {
            this.startLane = i;
            this.anchorLane = i2;
        }

        public ItemEntry(Parcel parcel) {
            this.startLane = parcel.readInt();
            this.anchorLane = parcel.readInt();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.spanMargins = new int[readInt];
                for (int i = 0; i < readInt; i++) {
                    this.spanMargins[i] = parcel.readInt();
                }
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.startLane);
            parcel.writeInt(this.anchorLane);
            int[] iArr = this.spanMargins;
            int length = iArr != null ? iArr.length : 0;
            parcel.writeInt(length);
            for (int i2 = 0; i2 < length; i2++) {
                parcel.writeInt(this.spanMargins[i2]);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setLane(LaneInfo laneInfo) {
            this.startLane = laneInfo.startLane;
            this.anchorLane = laneInfo.anchorLane;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateLane() {
            this.startLane = -1;
            this.anchorLane = -1;
            this.spanMargins = null;
        }

        /* access modifiers changed from: private */
        public boolean hasSpanMargins() {
            return this.spanMargins != null;
        }

        /* access modifiers changed from: private */
        public int getSpanMargin(int i) {
            int[] iArr = this.spanMargins;
            if (iArr == null) {
                return 0;
            }
            return iArr[i];
        }

        /* access modifiers changed from: private */
        public void setSpanMargin(int i, int i2, int i3) {
            if (this.spanMargins == null) {
                this.spanMargins = new int[i3];
            }
            this.spanMargins[i] = i2;
        }
    }

    protected static class LanedSavedState extends SavedState {
        public static final Creator<LanedSavedState> CREATOR = new Creator<LanedSavedState>() {
            public LanedSavedState createFromParcel(Parcel parcel) {
                return new LanedSavedState(parcel, null);
            }

            public LanedSavedState[] newArray(int i) {
                return new LanedSavedState[i];
            }
        };
        /* access modifiers changed from: private */
        public ItemEntries itemEntries;
        /* access modifiers changed from: private */
        public int laneSize;
        /* access modifiers changed from: private */
        public Rect[] lanes;
        /* access modifiers changed from: private */
        public Orientation orientation;

        /* synthetic */ LanedSavedState(Parcel parcel, C44751 r2) {
            this(parcel);
        }

        protected LanedSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private LanedSavedState(Parcel parcel) {
            super(parcel);
            this.orientation = Orientation.values()[parcel.readInt()];
            this.laneSize = parcel.readInt();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.lanes = new Rect[readInt];
                for (int i = 0; i < readInt; i++) {
                    Rect rect = new Rect();
                    rect.readFromParcel(parcel);
                    this.lanes[i] = rect;
                }
            }
            int readInt2 = parcel.readInt();
            if (readInt2 > 0) {
                this.itemEntries = new ItemEntries();
                for (int i2 = 0; i2 < readInt2; i2++) {
                    this.itemEntries.putItemEntry(i2, (ItemEntry) parcel.readParcelable(getClass().getClassLoader()));
                }
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.orientation.ordinal());
            parcel.writeInt(this.laneSize);
            Rect[] rectArr = this.lanes;
            int length = rectArr != null ? rectArr.length : 0;
            parcel.writeInt(length);
            for (int i2 = 0; i2 < length; i2++) {
                this.lanes[i2].writeToParcel(parcel, 1);
            }
            ItemEntries itemEntries2 = this.itemEntries;
            int size = itemEntries2 != null ? itemEntries2.size() : 0;
            parcel.writeInt(size);
            for (int i3 = 0; i3 < size; i3++) {
                parcel.writeParcelable(this.itemEntries.getItemEntry(i3), i);
            }
        }
    }

    private enum UpdateOp {
        ADD,
        REMOVE,
        UPDATE,
        MOVE
    }

    /* access modifiers changed from: 0000 */
    public ItemEntry cacheChildFrame(View view, Rect rect) {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemEntry cacheChildLaneAndSpan(View view, Direction direction) {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public abstract int getLaneCount();

    /* access modifiers changed from: 0000 */
    public abstract void getLaneForPosition(LaneInfo laneInfo, int i, Direction direction);

    /* access modifiers changed from: 0000 */
    public int getLaneSpanForChild(View view) {
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public int getLaneSpanForPosition(int i) {
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public abstract void moveLayoutToPosition(int i, int i2, Recycler recycler, State state);

    public BaseLayoutManager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseLayoutManager(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildFrame = new Rect();
        this.mTempRect = new Rect();
        this.mTempLaneInfo = new LaneInfo();
    }

    public BaseLayoutManager(Orientation orientation) {
        super(orientation);
        this.mChildFrame = new Rect();
        this.mTempRect = new Rect();
        this.mTempLaneInfo = new LaneInfo();
    }

    /* access modifiers changed from: protected */
    public void pushChildFrame(ItemEntry itemEntry, Rect rect, int i, int i2, Direction direction) {
        boolean z = direction == Direction.END && itemEntry != null && !itemEntry.hasSpanMargins();
        for (int i3 = i; i3 < i + i2; i3++) {
            int pushChildFrame = this.mLanes.pushChildFrame(rect, i3, (itemEntry == null || direction == Direction.END) ? 0 : itemEntry.getSpanMargin(i3 - i), direction);
            if (i2 > 1 && z) {
                itemEntry.setSpanMargin(i3 - i, pushChildFrame, i2);
            }
        }
    }

    private void popChildFrame(ItemEntry itemEntry, Rect rect, int i, int i2, Direction direction) {
        for (int i3 = i; i3 < i + i2; i3++) {
            this.mLanes.popChildFrame(rect, i3, (itemEntry == null || direction == Direction.END) ? 0 : itemEntry.getSpanMargin(i3 - i), direction);
        }
    }

    /* access modifiers changed from: 0000 */
    public void getDecoratedChildFrame(View view, Rect rect) {
        rect.left = getDecoratedLeft(view);
        rect.top = getDecoratedTop(view);
        rect.right = getDecoratedRight(view);
        rect.bottom = getDecoratedBottom(view);
    }

    /* access modifiers changed from: 0000 */
    public boolean isVertical() {
        return getOrientation() == Orientation.VERTICAL;
    }

    /* access modifiers changed from: 0000 */
    public Lanes getLanes() {
        return this.mLanes;
    }

    /* access modifiers changed from: 0000 */
    public void setItemEntryForPosition(int i, ItemEntry itemEntry) {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            itemEntries.putItemEntry(i, itemEntry);
        }
    }

    /* access modifiers changed from: 0000 */
    public ItemEntry getItemEntryForPosition(int i) {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            return itemEntries.getItemEntry(i);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void clearItemEntries() {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            itemEntries.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public void invalidateItemLanesAfter(int i) {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            itemEntries.invalidateItemLanesAfter(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void offsetForAddition(int i, int i2) {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            itemEntries.offsetForAddition(i, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void offsetForRemoval(int i, int i2) {
        ItemEntries itemEntries = this.mItemEntries;
        if (itemEntries != null) {
            itemEntries.offsetForRemoval(i, i2);
        }
    }

    private void requestMoveLayout() {
        if (getPendingScrollPosition() == -1) {
            int firstVisiblePosition = getFirstVisiblePosition();
            View findViewByPosition = findViewByPosition(firstVisiblePosition);
            setPendingScrollPositionWithOffset(firstVisiblePosition, findViewByPosition != null ? getChildStart(findViewByPosition) : 0);
        }
    }

    private boolean canUseLanes(Lanes lanes) {
        boolean z = false;
        if (lanes == null) {
            return false;
        }
        int laneCount = getLaneCount();
        int calculateLaneSize = Lanes.calculateLaneSize(this, laneCount);
        if (lanes.getOrientation() == getOrientation() && lanes.getCount() == laneCount && lanes.getLaneSize() == calculateLaneSize) {
            z = true;
        }
        return z;
    }

    private boolean ensureLayoutState() {
        int laneCount = getLaneCount();
        if (laneCount == 0 || getWidth() == 0 || getHeight() == 0 || canUseLanes(this.mLanes)) {
            return false;
        }
        Lanes lanes = this.mLanes;
        this.mLanes = new Lanes(this, laneCount);
        requestMoveLayout();
        if (this.mItemEntries == null) {
            this.mItemEntries = new ItemEntries();
        }
        if (lanes != null && lanes.getOrientation() == this.mLanes.getOrientation() && lanes.getLaneSize() == this.mLanes.getLaneSize()) {
            invalidateItemLanesAfter(0);
        } else {
            this.mItemEntries.clear();
        }
        return true;
    }

    private void handleUpdate(int i, int i2, UpdateOp updateOp) {
        invalidateItemLanesAfter(i);
        int i3 = C44751.f2233xf2a83c54[updateOp.ordinal()];
        if (i3 == 1) {
            offsetForAddition(i, i2);
        } else if (i3 == 2) {
            offsetForRemoval(i, i2);
        } else if (i3 == 3) {
            offsetForRemoval(i, 1);
            offsetForAddition(i2, 1);
        }
        if (i2 + i > getFirstVisiblePosition() && i <= getLastVisiblePosition()) {
            requestLayout();
        }
    }

    public void offsetChildrenHorizontal(int i) {
        if (!isVertical()) {
            this.mLanes.offset(i);
        }
        super.offsetChildrenHorizontal(i);
    }

    public void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        if (isVertical()) {
            this.mLanes.offset(i);
        }
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        boolean z = this.mLanesToRestore != null;
        if (z) {
            this.mLanes = this.mLanesToRestore;
            this.mItemEntries = this.mItemEntriesToRestore;
            this.mLanesToRestore = null;
            this.mItemEntriesToRestore = null;
        }
        boolean ensureLayoutState = ensureLayoutState();
        if (this.mLanes != null) {
            this.mItemEntries.setAdapterSize(state.getItemCount());
            int anchorItemPosition = getAnchorItemPosition(state);
            if (anchorItemPosition > 0 && ensureLayoutState && !z) {
                moveLayoutToPosition(anchorItemPosition, getPendingScrollOffset(), recycler, state);
            }
            this.mLanes.reset(Direction.START);
            super.onLayoutChildren(recycler, state);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayoutScrapList(Recycler recycler, State state) {
        this.mLanes.save();
        super.onLayoutScrapList(recycler, state);
        this.mLanes.restore();
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, UpdateOp.ADD);
        super.onItemsAdded(recyclerView, i, i2);
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, UpdateOp.REMOVE);
        super.onItemsRemoved(recyclerView, i, i2);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, UpdateOp.UPDATE);
        super.onItemsUpdated(recyclerView, i, i2);
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        handleUpdate(i, i2, UpdateOp.MOVE);
        super.onItemsMoved(recyclerView, i, i2, i3);
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        clearItemEntries();
        super.onItemsChanged(recyclerView);
    }

    public Parcelable onSaveInstanceState() {
        LanedSavedState lanedSavedState = new LanedSavedState(super.onSaveInstanceState());
        Lanes lanes = this.mLanes;
        int i = 0;
        int count = lanes != null ? lanes.getCount() : 0;
        lanedSavedState.lanes = new Rect[count];
        for (int i2 = 0; i2 < count; i2++) {
            Rect rect = new Rect();
            this.mLanes.getLane(i2, rect);
            lanedSavedState.lanes[i2] = rect;
        }
        lanedSavedState.orientation = getOrientation();
        Lanes lanes2 = this.mLanes;
        if (lanes2 != null) {
            i = lanes2.getLaneSize();
        }
        lanedSavedState.laneSize = i;
        lanedSavedState.itemEntries = this.mItemEntries;
        return lanedSavedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        LanedSavedState lanedSavedState = (LanedSavedState) parcelable;
        if (lanedSavedState.lanes != null && lanedSavedState.laneSize > 0) {
            this.mLanesToRestore = new Lanes(this, lanedSavedState.orientation, lanedSavedState.lanes, lanedSavedState.laneSize);
            this.mItemEntriesToRestore = lanedSavedState.itemEntries;
        }
        super.onRestoreInstanceState(lanedSavedState.getSuperState());
    }

    /* access modifiers changed from: protected */
    public boolean canAddMoreViews(Direction direction, int i) {
        boolean z = true;
        if (direction == Direction.START) {
            if (this.mLanes.getInnerStart() <= i) {
                z = false;
            }
            return z;
        }
        if (this.mLanes.getInnerEnd() >= i) {
            z = false;
        }
        return z;
    }

    private int getWidthUsed(View view) {
        if (!isVertical()) {
            return 0;
        }
        return ((getWidth() - getPaddingLeft()) - getPaddingRight()) - (getLanes().getLaneSize() * getLaneSpanForChild(view));
    }

    private int getHeightUsed(View view) {
        if (isVertical()) {
            return 0;
        }
        return ((getHeight() - getPaddingTop()) - getPaddingBottom()) - (getLanes().getLaneSize() * getLaneSpanForChild(view));
    }

    /* access modifiers changed from: 0000 */
    public void measureChildWithMargins(View view) {
        measureChildWithMargins(view, getWidthUsed(view), getHeightUsed(view));
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, Direction direction) {
        cacheChildLaneAndSpan(view, direction);
        measureChildWithMargins(view);
    }

    /* access modifiers changed from: protected */
    public void layoutChild(View view, Direction direction) {
        getLaneForChild(this.mTempLaneInfo, view, direction);
        this.mLanes.getChildFrame(this.mChildFrame, getDecoratedMeasuredWidth(view), getDecoratedMeasuredHeight(view), this.mTempLaneInfo, direction);
        ItemEntry cacheChildFrame = cacheChildFrame(view, this.mChildFrame);
        layoutDecorated(view, this.mChildFrame.left, this.mChildFrame.top, this.mChildFrame.right, this.mChildFrame.bottom);
        if (!((LayoutParams) view.getLayoutParams()).isItemRemoved()) {
            pushChildFrame(cacheChildFrame, this.mChildFrame, this.mTempLaneInfo.startLane, getLaneSpanForChild(view), direction);
        }
    }

    /* access modifiers changed from: protected */
    public void detachChild(View view, Direction direction) {
        int position = getPosition(view);
        getLaneForPosition(this.mTempLaneInfo, position, direction);
        getDecoratedChildFrame(view, this.mChildFrame);
        popChildFrame(getItemEntryForPosition(position), this.mChildFrame, this.mTempLaneInfo.startLane, getLaneSpanForChild(view), direction);
    }

    /* access modifiers changed from: 0000 */
    public void getLaneForChild(LaneInfo laneInfo, View view, Direction direction) {
        getLaneForPosition(laneInfo, getPosition(view), direction);
    }

    public boolean checkLayoutParams(LayoutParams layoutParams) {
        boolean z = true;
        if (isVertical()) {
            if (layoutParams.width != -1) {
                z = false;
            }
            return z;
        }
        if (layoutParams.height != -1) {
            z = false;
        }
        return z;
    }

    public LayoutParams generateDefaultLayoutParams() {
        if (isVertical()) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2 = new LayoutParams((MarginLayoutParams) layoutParams);
        if (isVertical()) {
            layoutParams2.width = -1;
            layoutParams2.height = layoutParams.height;
        } else {
            layoutParams2.width = layoutParams.width;
            layoutParams2.height = -1;
        }
        return layoutParams2;
    }

    public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }
}
