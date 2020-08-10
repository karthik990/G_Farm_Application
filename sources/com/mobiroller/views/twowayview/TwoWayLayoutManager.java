package com.mobiroller.views.twowayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import androidx.recyclerview.widget.RecyclerView.Recycler;
import androidx.recyclerview.widget.RecyclerView.State;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.preview.C4290R;
import java.util.List;

public abstract class TwoWayLayoutManager extends LayoutManager {
    private static final String LOGTAG = "TwoWayLayoutManager";
    /* access modifiers changed from: private */
    public boolean mIsVertical;
    private int mLayoutEnd;
    private int mLayoutStart;
    private SavedState mPendingSavedState;
    private int mPendingScrollOffset;
    private int mPendingScrollPosition;
    private RecyclerView mRecyclerView;

    public enum Direction {
        START,
        END
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    protected static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        protected static final SavedState EMPTY_STATE = new SavedState();
        /* access modifiers changed from: private */
        public int anchorItemPosition;
        /* access modifiers changed from: private */
        public Bundle itemSelectionState;
        private final Parcelable superState;

        public int describeContents() {
            return 0;
        }

        private SavedState() {
            this.superState = null;
        }

        protected SavedState(Parcelable parcelable) {
            if (parcelable != null) {
                if (parcelable == EMPTY_STATE) {
                    parcelable = null;
                }
                this.superState = parcelable;
                return;
            }
            throw new IllegalArgumentException("superState must not be null");
        }

        protected SavedState(Parcel parcel) {
            this.superState = EMPTY_STATE;
            this.anchorItemPosition = parcel.readInt();
            this.itemSelectionState = (Bundle) parcel.readParcelable(getClass().getClassLoader());
        }

        public Parcelable getSuperState() {
            return this.superState;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.anchorItemPosition);
            parcel.writeParcelable(this.itemSelectionState, i);
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean canAddMoreViews(Direction direction, int i);

    /* access modifiers changed from: protected */
    public void detachChild(View view, Direction direction) {
    }

    /* access modifiers changed from: protected */
    public abstract void layoutChild(View view, Direction direction);

    /* access modifiers changed from: protected */
    public abstract void measureChild(View view, Direction direction);

    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    public TwoWayLayoutManager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoWayLayoutManager(Context context, AttributeSet attributeSet, int i) {
        this.mIsVertical = true;
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollOffset = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.twowayview_TwoWayLayoutManager, i, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            if (index == 0) {
                int i3 = obtainStyledAttributes.getInt(index, -1);
                if (i3 >= 0) {
                    setOrientation(Orientation.values()[i3]);
                }
            }
        }
        obtainStyledAttributes.recycle();
    }

    public TwoWayLayoutManager(Orientation orientation) {
        boolean z = true;
        this.mIsVertical = true;
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollOffset = 0;
        if (orientation != Orientation.VERTICAL) {
            z = false;
        }
        this.mIsVertical = z;
    }

    private int getTotalSpace() {
        int width;
        int paddingLeft;
        if (this.mIsVertical) {
            width = getHeight() - getPaddingBottom();
            paddingLeft = getPaddingTop();
        } else {
            width = getWidth() - getPaddingRight();
            paddingLeft = getPaddingLeft();
        }
        return width - paddingLeft;
    }

    /* access modifiers changed from: protected */
    public int getStartWithPadding() {
        return this.mIsVertical ? getPaddingTop() : getPaddingLeft();
    }

    /* access modifiers changed from: protected */
    public int getEndWithPadding() {
        int width;
        int paddingRight;
        if (this.mIsVertical) {
            width = getHeight();
            paddingRight = getPaddingBottom();
        } else {
            width = getWidth();
            paddingRight = getPaddingRight();
        }
        return width - paddingRight;
    }

    /* access modifiers changed from: protected */
    public int getChildStart(View view) {
        return this.mIsVertical ? getDecoratedTop(view) : getDecoratedLeft(view);
    }

    /* access modifiers changed from: protected */
    public int getChildEnd(View view) {
        return this.mIsVertical ? getDecoratedBottom(view) : getDecoratedRight(view);
    }

    /* access modifiers changed from: protected */
    public Adapter getAdapter() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            return recyclerView.getAdapter();
        }
        return null;
    }

    private void offsetChildren(int i) {
        if (this.mIsVertical) {
            offsetChildrenVertical(i);
        } else {
            offsetChildrenHorizontal(i);
        }
        this.mLayoutStart += i;
        this.mLayoutEnd += i;
    }

    private void recycleChildrenOutOfBounds(Direction direction, Recycler recycler) {
        if (direction == Direction.END) {
            recycleChildrenFromStart(direction, recycler);
        } else {
            recycleChildrenFromEnd(direction, recycler);
        }
    }

    private void recycleChildrenFromStart(Direction direction, Recycler recycler) {
        int childCount = getChildCount();
        int startWithPadding = getStartWithPadding();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (getChildEnd(childAt) >= startWithPadding) {
                break;
            }
            i++;
            detachChild(childAt, direction);
        }
        while (true) {
            i--;
            if (i >= 0) {
                View childAt2 = getChildAt(0);
                removeAndRecycleView(childAt2, recycler);
                updateLayoutEdgesFromRemovedChild(childAt2, direction);
            } else {
                return;
            }
        }
    }

    private void recycleChildrenFromEnd(Direction direction, Recycler recycler) {
        int endWithPadding = getEndWithPadding();
        int i = 0;
        int i2 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (getChildStart(childAt) <= endWithPadding) {
                break;
            }
            i++;
            detachChild(childAt, direction);
            i2 = childCount;
        }
        while (true) {
            i--;
            if (i >= 0) {
                View childAt2 = getChildAt(i2);
                removeAndRecycleViewAt(i2, recycler);
                updateLayoutEdgesFromRemovedChild(childAt2, direction);
            } else {
                return;
            }
        }
    }

    private int scrollBy(int i, Recycler recycler, State state) {
        int i2;
        int childCount = getChildCount();
        if (!(childCount == 0 || i == 0)) {
            int startWithPadding = getStartWithPadding();
            int endWithPadding = getEndWithPadding();
            int firstVisiblePosition = getFirstVisiblePosition();
            int totalSpace = getTotalSpace();
            boolean z = true;
            if (i < 0) {
                i2 = Math.max(-(totalSpace - 1), i);
            } else {
                i2 = Math.min(totalSpace - 1, i);
            }
            boolean z2 = firstVisiblePosition == 0 && this.mLayoutStart >= startWithPadding && i2 <= 0;
            if (firstVisiblePosition + childCount != state.getItemCount() || this.mLayoutEnd > endWithPadding || i2 < 0) {
                z = false;
            }
            if (!z && !z2) {
                offsetChildren(-i2);
                Direction direction = i2 > 0 ? Direction.END : Direction.START;
                recycleChildrenOutOfBounds(direction, recycler);
                int abs = Math.abs(i2);
                if (canAddMoreViews(Direction.START, startWithPadding - abs) || canAddMoreViews(Direction.END, endWithPadding + abs)) {
                    fillGap(direction, recycler, state);
                }
                return i2;
            }
        }
        return 0;
    }

    private void fillGap(Direction direction, Recycler recycler, State state) {
        int childCount = getChildCount();
        int extraLayoutSpace = getExtraLayoutSpace(state);
        int firstVisiblePosition = getFirstVisiblePosition();
        if (direction == Direction.END) {
            fillAfter(firstVisiblePosition + childCount, recycler, state, extraLayoutSpace);
            correctTooHigh(childCount, recycler, state);
            return;
        }
        fillBefore(firstVisiblePosition - 1, recycler, extraLayoutSpace);
        correctTooLow(childCount, recycler, state);
    }

    private void fillBefore(int i, Recycler recycler) {
        fillBefore(i, recycler, 0);
    }

    private void fillBefore(int i, Recycler recycler, int i2) {
        int startWithPadding = getStartWithPadding() - i2;
        while (canAddMoreViews(Direction.START, startWithPadding) && i >= 0) {
            makeAndAddView(i, Direction.START, recycler);
            i--;
        }
    }

    private void fillAfter(int i, Recycler recycler, State state) {
        fillAfter(i, recycler, state, 0);
    }

    private void fillAfter(int i, Recycler recycler, State state, int i2) {
        int endWithPadding = getEndWithPadding() + i2;
        int itemCount = state.getItemCount();
        while (canAddMoreViews(Direction.END, endWithPadding) && i < itemCount) {
            makeAndAddView(i, Direction.END, recycler);
            i++;
        }
    }

    private void fillSpecific(int i, Recycler recycler, State state) {
        if (state.getItemCount() != 0) {
            makeAndAddView(i, Direction.END, recycler);
            int extraLayoutSpace = getExtraLayoutSpace(state);
            int i2 = 0;
            if (state.getTargetScrollPosition() >= i) {
                i2 = extraLayoutSpace;
                extraLayoutSpace = 0;
            }
            fillBefore(i - 1, recycler, extraLayoutSpace);
            adjustViewsStartOrEnd();
            fillAfter(i + 1, recycler, state, i2);
            correctTooHigh(getChildCount(), recycler, state);
        }
    }

    private void correctTooHigh(int i, Recycler recycler, State state) {
        if (getLastVisiblePosition() == state.getItemCount() - 1 && i != 0) {
            int startWithPadding = getStartWithPadding();
            int endWithPadding = getEndWithPadding();
            int firstVisiblePosition = getFirstVisiblePosition();
            int i2 = endWithPadding - this.mLayoutEnd;
            if (i2 <= 0) {
                return;
            }
            if (firstVisiblePosition > 0 || this.mLayoutStart < startWithPadding) {
                if (firstVisiblePosition == 0) {
                    i2 = Math.min(i2, startWithPadding - this.mLayoutStart);
                }
                offsetChildren(i2);
                if (firstVisiblePosition > 0) {
                    fillBefore(firstVisiblePosition - 1, recycler);
                    adjustViewsStartOrEnd();
                }
            }
        }
    }

    private void correctTooLow(int i, Recycler recycler, State state) {
        if (getFirstVisiblePosition() == 0 && i != 0) {
            int startWithPadding = getStartWithPadding();
            int endWithPadding = getEndWithPadding();
            int itemCount = state.getItemCount();
            int lastVisiblePosition = getLastVisiblePosition();
            int i2 = this.mLayoutStart - startWithPadding;
            if (i2 > 0) {
                int i3 = itemCount - 1;
                if (lastVisiblePosition < i3 || this.mLayoutEnd > endWithPadding) {
                    if (lastVisiblePosition == i3) {
                        i2 = Math.min(i2, this.mLayoutEnd - endWithPadding);
                    }
                    offsetChildren(-i2);
                    if (lastVisiblePosition < i3) {
                        fillAfter(lastVisiblePosition + 1, recycler, state);
                        adjustViewsStartOrEnd();
                    }
                } else if (lastVisiblePosition == i3) {
                    adjustViewsStartOrEnd();
                }
            }
        }
    }

    private void adjustViewsStartOrEnd() {
        if (getChildCount() != 0) {
            int startWithPadding = this.mLayoutStart - getStartWithPadding();
            if (startWithPadding < 0) {
                startWithPadding = 0;
            }
            if (startWithPadding != 0) {
                offsetChildren(-startWithPadding);
            }
        }
    }

    private static View findNextScrapView(List<ViewHolder> list, Direction direction, int i) {
        int size = list.size();
        int i2 = Integer.MAX_VALUE;
        ViewHolder viewHolder = null;
        for (int i3 = 0; i3 < size; i3++) {
            ViewHolder viewHolder2 = (ViewHolder) list.get(i3);
            int position = viewHolder2.getPosition() - i;
            if ((position >= 0 || direction != Direction.END) && (position <= 0 || direction != Direction.START)) {
                int abs = Math.abs(position);
                if (abs < i2) {
                    viewHolder = viewHolder2;
                    if (position == 0) {
                        break;
                    }
                    i2 = abs;
                } else {
                    continue;
                }
            }
        }
        if (viewHolder != null) {
            return viewHolder.itemView;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void fillFromScrapList(java.util.List<androidx.recyclerview.widget.RecyclerView.ViewHolder> r4, com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction r5) {
        /*
            r3 = this;
            int r0 = r3.getFirstVisiblePosition()
            com.mobiroller.views.twowayview.TwoWayLayoutManager$Direction r1 = com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction.END
            r2 = 1
            if (r5 != r1) goto L_0x000f
            int r1 = r3.getChildCount()
        L_0x000d:
            int r0 = r0 + r1
            goto L_0x0010
        L_0x000f:
            int r0 = r0 - r2
        L_0x0010:
            android.view.View r1 = findNextScrapView(r4, r5, r0)
            if (r1 == 0) goto L_0x0021
            r3.setupChild(r1, r5)
            com.mobiroller.views.twowayview.TwoWayLayoutManager$Direction r1 = com.mobiroller.views.twowayview.TwoWayLayoutManager.Direction.END
            if (r5 != r1) goto L_0x001f
            r1 = 1
            goto L_0x000d
        L_0x001f:
            r1 = -1
            goto L_0x000d
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.views.twowayview.TwoWayLayoutManager.fillFromScrapList(java.util.List, com.mobiroller.views.twowayview.TwoWayLayoutManager$Direction):void");
    }

    private void setupChild(View view, Direction direction) {
        ItemSelectionSupport from = ItemSelectionSupport.from(this.mRecyclerView);
        if (from != null) {
            from.setViewChecked(view, from.isItemChecked(getPosition(view)));
        }
        measureChild(view, direction);
        layoutChild(view, direction);
    }

    private View makeAndAddView(int i, Direction direction, Recycler recycler) {
        View viewForPosition = recycler.getViewForPosition(i);
        boolean isItemRemoved = ((LayoutParams) viewForPosition.getLayoutParams()).isItemRemoved();
        if (!isItemRemoved) {
            addView(viewForPosition, direction == Direction.END ? -1 : 0);
        }
        setupChild(viewForPosition, direction);
        if (!isItemRemoved) {
            updateLayoutEdgesFromNewChild(viewForPosition);
        }
        return viewForPosition;
    }

    private void handleUpdate() {
        int firstVisiblePosition = getFirstVisiblePosition();
        View findViewByPosition = findViewByPosition(firstVisiblePosition);
        if (findViewByPosition != null) {
            setPendingScrollPositionWithOffset(firstVisiblePosition, getChildStart(findViewByPosition));
        } else {
            setPendingScrollPositionWithOffset(-1, 0);
        }
    }

    private void updateLayoutEdgesFromNewChild(View view) {
        int childStart = getChildStart(view);
        if (childStart < this.mLayoutStart) {
            this.mLayoutStart = childStart;
        }
        int childEnd = getChildEnd(view);
        if (childEnd > this.mLayoutEnd) {
            this.mLayoutEnd = childEnd;
        }
    }

    private void updateLayoutEdgesFromRemovedChild(View view, Direction direction) {
        int i;
        int i2;
        int childCount = getChildCount();
        if (childCount == 0) {
            resetLayoutEdges();
            return;
        }
        int childStart = getChildStart(view);
        int childEnd = getChildEnd(view);
        if (childStart <= this.mLayoutStart || childEnd >= this.mLayoutEnd) {
            if (direction == Direction.END) {
                this.mLayoutStart = Integer.MAX_VALUE;
                childStart = childEnd;
                i = 0;
            } else {
                this.mLayoutEnd = Integer.MIN_VALUE;
                i = childCount - 1;
            }
            while (i >= 0 && i <= childCount - 1) {
                View childAt = getChildAt(i);
                if (direction == Direction.END) {
                    int childStart2 = getChildStart(childAt);
                    if (childStart2 < this.mLayoutStart) {
                        this.mLayoutStart = childStart2;
                    }
                    if (childStart2 >= childStart) {
                        break;
                    }
                    i2 = i + 1;
                } else {
                    int childEnd2 = getChildEnd(childAt);
                    if (childEnd2 > this.mLayoutEnd) {
                        this.mLayoutEnd = childEnd2;
                    }
                    if (childEnd2 <= childStart) {
                        break;
                    }
                    i2 = i - 1;
                }
            }
        }
    }

    private void resetLayoutEdges() {
        this.mLayoutStart = getStartWithPadding();
        this.mLayoutEnd = this.mLayoutStart;
    }

    /* access modifiers changed from: protected */
    public int getExtraLayoutSpace(State state) {
        if (state.hasTargetScrollPosition()) {
            return getTotalSpace();
        }
        return 0;
    }

    private Bundle getPendingItemSelectionState() {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return savedState.itemSelectionState;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void setPendingScrollPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollOffset = i2;
    }

    /* access modifiers changed from: protected */
    public int getPendingScrollPosition() {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return savedState.anchorItemPosition;
        }
        return this.mPendingScrollPosition;
    }

    /* access modifiers changed from: protected */
    public int getPendingScrollOffset() {
        if (this.mPendingSavedState != null) {
            return 0;
        }
        return this.mPendingScrollOffset;
    }

    /* access modifiers changed from: protected */
    public int getAnchorItemPosition(State state) {
        int itemCount = state.getItemCount();
        int pendingScrollPosition = getPendingScrollPosition();
        if (pendingScrollPosition != -1 && (pendingScrollPosition < 0 || pendingScrollPosition >= itemCount)) {
            pendingScrollPosition = -1;
        }
        if (pendingScrollPosition != -1) {
            return pendingScrollPosition;
        }
        if (getChildCount() > 0) {
            return findFirstValidChildPosition(itemCount);
        }
        return 0;
    }

    private int findFirstValidChildPosition(int i) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            int position = getPosition(getChildAt(i2));
            if (position >= 0 && position < i) {
                return position;
            }
        }
        return 0;
    }

    public int getDecoratedMeasuredWidth(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return super.getDecoratedMeasuredWidth(view) + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    public int getDecoratedMeasuredHeight(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return super.getDecoratedMeasuredHeight(view) + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public int getDecoratedLeft(View view) {
        return super.getDecoratedLeft(view) - ((MarginLayoutParams) view.getLayoutParams()).leftMargin;
    }

    public int getDecoratedTop(View view) {
        return super.getDecoratedTop(view) - ((MarginLayoutParams) view.getLayoutParams()).topMargin;
    }

    public int getDecoratedRight(View view) {
        return super.getDecoratedRight(view) + ((MarginLayoutParams) view.getLayoutParams()).rightMargin;
    }

    public int getDecoratedBottom(View view) {
        return super.getDecoratedBottom(view) + ((MarginLayoutParams) view.getLayoutParams()).bottomMargin;
    }

    public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        super.layoutDecorated(view, i + marginLayoutParams.leftMargin, i2 + marginLayoutParams.topMargin, i3 - marginLayoutParams.rightMargin, i4 - marginLayoutParams.bottomMargin);
    }

    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.mRecyclerView = null;
    }

    public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        super.onAdapterChanged(adapter, adapter2);
        ItemSelectionSupport from = ItemSelectionSupport.from(this.mRecyclerView);
        if (adapter != null && from != null) {
            from.clearChoices();
        }
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        ItemSelectionSupport from = ItemSelectionSupport.from(this.mRecyclerView);
        if (from != null) {
            Bundle pendingItemSelectionState = getPendingItemSelectionState();
            if (pendingItemSelectionState != null) {
                from.onRestoreInstanceState(pendingItemSelectionState);
            }
            if (state.didStructureChange()) {
                from.onAdapterDataChanged();
            }
        }
        int anchorItemPosition = getAnchorItemPosition(state);
        detachAndScrapAttachedViews(recycler);
        fillSpecific(anchorItemPosition, recycler, state);
        onLayoutScrapList(recycler, state);
        setPendingScrollPositionWithOffset(-1, 0);
        this.mPendingSavedState = null;
    }

    /* access modifiers changed from: protected */
    public void onLayoutScrapList(Recycler recycler, State state) {
        if (getChildCount() != 0 && !state.isPreLayout() && supportsPredictiveItemAnimations()) {
            List scrapList = recycler.getScrapList();
            fillFromScrapList(scrapList, Direction.START);
            fillFromScrapList(scrapList, Direction.END);
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        handleUpdate();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        handleUpdate();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        handleUpdate();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        handleUpdate();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        handleUpdate();
    }

    public LayoutParams generateDefaultLayoutParams() {
        if (this.mIsVertical) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        if (this.mIsVertical) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        if (!this.mIsVertical) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public boolean canScrollHorizontally() {
        return !this.mIsVertical;
    }

    public boolean canScrollVertically() {
        return this.mIsVertical;
    }

    public void scrollToPosition(int i) {
        scrollToPositionWithOffset(i, 0);
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        setPendingScrollPositionWithOffset(i, i2);
        requestLayout();
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        C44821 r2 = new LinearSmoothScroller(recyclerView.getContext()) {
            /* access modifiers changed from: protected */
            public int getHorizontalSnapPreference() {
                return -1;
            }

            /* access modifiers changed from: protected */
            public int getVerticalSnapPreference() {
                return -1;
            }

            public PointF computeScrollVectorForPosition(int i) {
                if (getChildCount() == 0) {
                    return null;
                }
                int i2 = i < TwoWayLayoutManager.this.getFirstVisiblePosition() ? -1 : 1;
                if (TwoWayLayoutManager.this.mIsVertical) {
                    return new PointF(0.0f, (float) i2);
                }
                return new PointF((float) i2, 0.0f);
            }
        };
        r2.setTargetPosition(i);
        startSmoothScroll(r2);
    }

    public int computeHorizontalScrollOffset(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return getFirstVisiblePosition();
    }

    public int computeVerticalScrollOffset(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return getFirstVisiblePosition();
    }

    public int computeHorizontalScrollExtent(State state) {
        return getChildCount();
    }

    public int computeVerticalScrollExtent(State state) {
        return getChildCount();
    }

    public int computeHorizontalScrollRange(State state) {
        return state.getItemCount();
    }

    public int computeVerticalScrollRange(State state) {
        return state.getItemCount();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState((Parcelable) SavedState.EMPTY_STATE);
        int pendingScrollPosition = getPendingScrollPosition();
        if (pendingScrollPosition == -1) {
            pendingScrollPosition = getFirstVisiblePosition();
        }
        savedState.anchorItemPosition = pendingScrollPosition;
        ItemSelectionSupport from = ItemSelectionSupport.from(this.mRecyclerView);
        if (from != null) {
            savedState.itemSelectionState = from.onSaveInstanceState();
        } else {
            savedState.itemSelectionState = Bundle.EMPTY;
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mPendingSavedState = (SavedState) parcelable;
        requestLayout();
    }

    public Orientation getOrientation() {
        return this.mIsVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    public void setOrientation(Orientation orientation) {
        boolean z = orientation == Orientation.VERTICAL;
        if (this.mIsVertical != z) {
            this.mIsVertical = z;
            requestLayout();
        }
    }

    public int getFirstVisiblePosition() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    public int getLastVisiblePosition() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }
}
