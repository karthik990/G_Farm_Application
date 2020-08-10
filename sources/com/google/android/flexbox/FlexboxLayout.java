package com.google.android.flexbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FlexboxLayout extends ViewGroup {
    public static final int ALIGN_CONTENT_CENTER = 2;
    public static final int ALIGN_CONTENT_FLEX_END = 1;
    public static final int ALIGN_CONTENT_FLEX_START = 0;
    public static final int ALIGN_CONTENT_SPACE_AROUND = 4;
    public static final int ALIGN_CONTENT_SPACE_BETWEEN = 3;
    public static final int ALIGN_CONTENT_STRETCH = 5;
    public static final int ALIGN_ITEMS_BASELINE = 3;
    public static final int ALIGN_ITEMS_CENTER = 2;
    public static final int ALIGN_ITEMS_FLEX_END = 1;
    public static final int ALIGN_ITEMS_FLEX_START = 0;
    public static final int ALIGN_ITEMS_STRETCH = 4;
    public static final int FLEX_DIRECTION_COLUMN = 2;
    public static final int FLEX_DIRECTION_COLUMN_REVERSE = 3;
    public static final int FLEX_DIRECTION_ROW = 0;
    public static final int FLEX_DIRECTION_ROW_REVERSE = 1;
    public static final int FLEX_WRAP_NOWRAP = 0;
    public static final int FLEX_WRAP_WRAP = 1;
    public static final int FLEX_WRAP_WRAP_REVERSE = 2;
    public static final int JUSTIFY_CONTENT_CENTER = 2;
    public static final int JUSTIFY_CONTENT_FLEX_END = 1;
    public static final int JUSTIFY_CONTENT_FLEX_START = 0;
    public static final int JUSTIFY_CONTENT_SPACE_AROUND = 4;
    public static final int JUSTIFY_CONTENT_SPACE_BETWEEN = 3;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    private int mAlignContent;
    private int mAlignItems;
    private boolean[] mChildrenFrozen;
    private Drawable mDividerDrawableHorizontal;
    private Drawable mDividerDrawableVertical;
    private int mDividerHorizontalHeight;
    private int mDividerVerticalWidth;
    private int mFlexDirection;
    private List<FlexLine> mFlexLines;
    private int mFlexWrap;
    private int mJustifyContent;
    private SparseIntArray mOrderCache;
    private int[] mReorderedIndices;
    private int mShowDividerHorizontal;
    private int mShowDividerVertical;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignContent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignItems {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlexDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlexWrap {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface JustifyContent {
    }

    public static class LayoutParams extends MarginLayoutParams {
        public static final int ALIGN_SELF_AUTO = -1;
        public static final int ALIGN_SELF_BASELINE = 3;
        public static final int ALIGN_SELF_CENTER = 2;
        public static final int ALIGN_SELF_FLEX_END = 1;
        public static final int ALIGN_SELF_FLEX_START = 0;
        public static final int ALIGN_SELF_STRETCH = 4;
        public static final float FLEX_BASIS_PERCENT_DEFAULT = -1.0f;
        private static final float FLEX_GROW_DEFAULT = 0.0f;
        private static final float FLEX_SHRINK_DEFAULT = 1.0f;
        private static final int MAX_SIZE = 16777215;
        private static final int ORDER_DEFAULT = 1;
        public int alignSelf = -1;
        public float flexBasisPercent = -1.0f;
        public float flexGrow = 0.0f;
        public float flexShrink = 1.0f;
        public int maxHeight = 16777215;
        public int maxWidth = 16777215;
        public int minHeight;
        public int minWidth;
        public int order = 1;
        public boolean wrapBefore;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2514R.styleable.FlexboxLayout_Layout);
            this.order = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_Layout_layout_order, 1);
            this.flexGrow = obtainStyledAttributes.getFloat(C2514R.styleable.FlexboxLayout_Layout_layout_flexGrow, 0.0f);
            this.flexShrink = obtainStyledAttributes.getFloat(C2514R.styleable.FlexboxLayout_Layout_layout_flexShrink, 1.0f);
            this.alignSelf = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_Layout_layout_alignSelf, -1);
            this.flexBasisPercent = obtainStyledAttributes.getFraction(C2514R.styleable.FlexboxLayout_Layout_layout_flexBasisPercent, 1, 1, -1.0f);
            this.minWidth = obtainStyledAttributes.getDimensionPixelSize(C2514R.styleable.FlexboxLayout_Layout_layout_minWidth, 0);
            this.minHeight = obtainStyledAttributes.getDimensionPixelSize(C2514R.styleable.FlexboxLayout_Layout_layout_minHeight, 0);
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(C2514R.styleable.FlexboxLayout_Layout_layout_maxWidth, 16777215);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(C2514R.styleable.FlexboxLayout_Layout_layout_maxHeight, 16777215);
            this.wrapBefore = obtainStyledAttributes.getBoolean(C2514R.styleable.FlexboxLayout_Layout_layout_wrapBefore, false);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.order = layoutParams.order;
            this.flexGrow = layoutParams.flexGrow;
            this.flexShrink = layoutParams.flexShrink;
            this.alignSelf = layoutParams.alignSelf;
            this.flexBasisPercent = layoutParams.flexBasisPercent;
            this.minWidth = layoutParams.minWidth;
            this.minHeight = layoutParams.minHeight;
            this.maxWidth = layoutParams.maxWidth;
            this.maxHeight = layoutParams.maxHeight;
            this.wrapBefore = layoutParams.wrapBefore;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(int i, int i2) {
            super(new android.view.ViewGroup.LayoutParams(i, i2));
        }
    }

    private static class Order implements Comparable<Order> {
        int index;
        int order;

        private Order() {
        }

        public int compareTo(Order order2) {
            int i = this.order;
            int i2 = order2.order;
            if (i != i2) {
                return i - i2;
            }
            return this.index - order2.index;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Order{order=");
            sb.append(this.order);
            sb.append(", index=");
            sb.append(this.index);
            sb.append('}');
            return sb.toString();
        }
    }

    private boolean isMainAxisDirectionHorizontal(int i) {
        return i == 0 || i == 1;
    }

    public FlexboxLayout(Context context) {
        this(context, null);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFlexLines = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2514R.styleable.FlexboxLayout, i, 0);
        this.mFlexDirection = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_flexDirection, 0);
        this.mFlexWrap = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_flexWrap, 0);
        this.mJustifyContent = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_justifyContent, 0);
        this.mAlignItems = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_alignItems, 4);
        this.mAlignContent = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_alignContent, 5);
        Drawable drawable = obtainStyledAttributes.getDrawable(C2514R.styleable.FlexboxLayout_dividerDrawable);
        if (drawable != null) {
            setDividerDrawableHorizontal(drawable);
            setDividerDrawableVertical(drawable);
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(C2514R.styleable.FlexboxLayout_dividerDrawableHorizontal);
        if (drawable2 != null) {
            setDividerDrawableHorizontal(drawable2);
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(C2514R.styleable.FlexboxLayout_dividerDrawableVertical);
        if (drawable3 != null) {
            setDividerDrawableVertical(drawable3);
        }
        int i2 = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_showDivider, 0);
        if (i2 != 0) {
            this.mShowDividerVertical = i2;
            this.mShowDividerHorizontal = i2;
        }
        int i3 = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_showDividerVertical, 0);
        if (i3 != 0) {
            this.mShowDividerVertical = i3;
        }
        int i4 = obtainStyledAttributes.getInt(C2514R.styleable.FlexboxLayout_showDividerHorizontal, 0);
        if (i4 != 0) {
            this.mShowDividerHorizontal = i4;
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (isOrderChangedFromLastMeasurement()) {
            this.mReorderedIndices = createReorderedIndices();
        }
        boolean[] zArr = this.mChildrenFrozen;
        if (zArr == null || zArr.length < getChildCount()) {
            this.mChildrenFrozen = new boolean[getChildCount()];
        }
        int i3 = this.mFlexDirection;
        if (i3 == 0 || i3 == 1) {
            measureHorizontal(i, i2);
        } else if (i3 == 2 || i3 == 3) {
            measureVertical(i, i2);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid value for the flex direction is set: ");
            sb.append(this.mFlexDirection);
            throw new IllegalStateException(sb.toString());
        }
        Arrays.fill(this.mChildrenFrozen, false);
    }

    public View getReorderedChildAt(int i) {
        if (i >= 0) {
            int[] iArr = this.mReorderedIndices;
            if (i < iArr.length) {
                return getChildAt(iArr[i]);
            }
        }
        return null;
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        this.mReorderedIndices = createReorderedIndices(view, i, layoutParams);
        super.addView(view, i, layoutParams);
    }

    private int[] createReorderedIndices(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        int childCount = getChildCount();
        List createOrders = createOrders(childCount);
        Order order = new Order();
        if (view == null || !(layoutParams instanceof LayoutParams)) {
            order.order = 1;
        } else {
            order.order = ((LayoutParams) layoutParams).order;
        }
        if (i == -1 || i == childCount) {
            order.index = childCount;
        } else if (i < getChildCount()) {
            order.index = i;
            while (i < childCount) {
                Order order2 = (Order) createOrders.get(i);
                order2.index++;
                i++;
            }
        } else {
            order.index = childCount;
        }
        createOrders.add(order);
        return sortOrdersIntoReorderedIndices(childCount + 1, createOrders);
    }

    private int[] createReorderedIndices() {
        int childCount = getChildCount();
        return sortOrdersIntoReorderedIndices(childCount, createOrders(childCount));
    }

    private int[] sortOrdersIntoReorderedIndices(int i, List<Order> list) {
        Collections.sort(list);
        if (this.mOrderCache == null) {
            this.mOrderCache = new SparseIntArray(i);
        }
        this.mOrderCache.clear();
        int[] iArr = new int[i];
        int i2 = 0;
        for (Order order : list) {
            iArr[i2] = order.index;
            this.mOrderCache.append(i2, order.order);
            i2++;
        }
        return iArr;
    }

    private List<Order> createOrders(int i) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
            Order order = new Order();
            order.order = layoutParams.order;
            order.index = i2;
            arrayList.add(order);
        }
        return arrayList;
    }

    private boolean isOrderChangedFromLastMeasurement() {
        int childCount = getChildCount();
        if (this.mOrderCache == null) {
            this.mOrderCache = new SparseIntArray(childCount);
        }
        if (this.mOrderCache.size() != childCount) {
            return true;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && ((LayoutParams) childAt.getLayoutParams()).order != this.mOrderCache.get(i)) {
                return true;
            }
        }
        return false;
    }

    private void measureHorizontal(int i, int i2) {
        int i3;
        int i4;
        int i5;
        LayoutParams layoutParams;
        int i6;
        int i7;
        int i8 = i;
        int i9 = i2;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        this.mFlexLines.clear();
        int childCount = getChildCount();
        int paddingStart = ViewCompat.getPaddingStart(this);
        int paddingEnd = ViewCompat.getPaddingEnd(this);
        FlexLine flexLine = new FlexLine();
        int i10 = paddingStart + paddingEnd;
        flexLine.mMainSize = i10;
        int i11 = 0;
        FlexLine flexLine2 = flexLine;
        int i12 = 0;
        int i13 = Integer.MIN_VALUE;
        int i14 = 0;
        int i15 = 0;
        while (i14 < childCount) {
            View reorderedChildAt = getReorderedChildAt(i14);
            if (reorderedChildAt == null) {
                addFlexLineIfLastFlexItem(i14, childCount, flexLine2);
            } else if (reorderedChildAt.getVisibility() == 8) {
                flexLine2.mItemCount++;
                addFlexLineIfLastFlexItem(i14, childCount, flexLine2);
            } else {
                LayoutParams layoutParams2 = (LayoutParams) reorderedChildAt.getLayoutParams();
                if (layoutParams2.alignSelf == 4) {
                    flexLine2.mIndicesAlignSelfStretch.add(Integer.valueOf(i14));
                }
                int i16 = layoutParams2.width;
                if (layoutParams2.flexBasisPercent != -1.0f && mode == 1073741824) {
                    i16 = Math.round(((float) size) * layoutParams2.flexBasisPercent);
                }
                reorderedChildAt.measure(getChildMeasureSpec(i8, getPaddingLeft() + getPaddingRight() + layoutParams2.leftMargin + layoutParams2.rightMargin, i16), getChildMeasureSpec(i9, getPaddingTop() + getPaddingBottom() + layoutParams2.topMargin + layoutParams2.bottomMargin, layoutParams2.height));
                checkSizeConstraints(reorderedChildAt);
                int combineMeasuredStates = ViewCompat.combineMeasuredStates(i12, ViewCompat.getMeasuredState(reorderedChildAt));
                int max = Math.max(i13, reorderedChildAt.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
                LayoutParams layoutParams3 = layoutParams2;
                View view = reorderedChildAt;
                i4 = mode;
                FlexLine flexLine3 = flexLine2;
                i3 = size;
                i5 = i14;
                if (isWrapRequired(mode, size, flexLine2.mMainSize, reorderedChildAt.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin, layoutParams3, i14, i15)) {
                    if (flexLine3.mItemCount > 0) {
                        addFlexLine(flexLine3);
                    }
                    flexLine2 = new FlexLine();
                    flexLine2.mItemCount = 1;
                    flexLine2.mMainSize = i10;
                    layoutParams = layoutParams3;
                    i7 = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    i6 = 0;
                } else {
                    layoutParams = layoutParams3;
                    flexLine3.mItemCount++;
                    i6 = i15 + 1;
                    flexLine2 = flexLine3;
                    i7 = max;
                }
                flexLine2.mMainSize += view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                flexLine2.mTotalFlexGrow += layoutParams.flexGrow;
                flexLine2.mTotalFlexShrink += layoutParams.flexShrink;
                flexLine2.mCrossSize = Math.max(flexLine2.mCrossSize, i7);
                if (hasDividerBeforeChildAtAlongMainAxis(i5, i6)) {
                    flexLine2.mMainSize += this.mDividerVerticalWidth;
                    flexLine2.mDividerLengthInMainSize += this.mDividerVerticalWidth;
                }
                if (this.mFlexWrap != 2) {
                    flexLine2.mMaxBaseline = Math.max(flexLine2.mMaxBaseline, view.getBaseline() + layoutParams.topMargin);
                } else {
                    flexLine2.mMaxBaseline = Math.max(flexLine2.mMaxBaseline, (view.getMeasuredHeight() - view.getBaseline()) + layoutParams.bottomMargin);
                }
                addFlexLineIfLastFlexItem(i5, childCount, flexLine2);
                i15 = i6;
                i13 = i7;
                i12 = combineMeasuredStates;
                i14 = i5 + 1;
                mode = i4;
                size = i3;
            }
            i4 = mode;
            i3 = size;
            i5 = i14;
            i14 = i5 + 1;
            mode = i4;
            size = i3;
        }
        determineMainSize(this.mFlexDirection, i8, i9);
        if (this.mAlignItems == 3) {
            for (FlexLine flexLine4 : this.mFlexLines) {
                int i17 = Integer.MIN_VALUE;
                for (int i18 = i11; i18 < i11 + flexLine4.mItemCount; i18++) {
                    View reorderedChildAt2 = getReorderedChildAt(i18);
                    LayoutParams layoutParams4 = (LayoutParams) reorderedChildAt2.getLayoutParams();
                    if (this.mFlexWrap != 2) {
                        i17 = Math.max(i17, reorderedChildAt2.getHeight() + Math.max(flexLine4.mMaxBaseline - reorderedChildAt2.getBaseline(), layoutParams4.topMargin) + layoutParams4.bottomMargin);
                    } else {
                        i17 = Math.max(i17, reorderedChildAt2.getHeight() + layoutParams4.topMargin + Math.max((flexLine4.mMaxBaseline - reorderedChildAt2.getMeasuredHeight()) + reorderedChildAt2.getBaseline(), layoutParams4.bottomMargin));
                    }
                }
                flexLine4.mCrossSize = i17;
                i11 += flexLine4.mItemCount;
            }
        }
        determineCrossSize(this.mFlexDirection, i8, i9, getPaddingTop() + getPaddingBottom());
        stretchViews(this.mFlexDirection, this.mAlignItems);
        setMeasuredDimensionForFlex(this.mFlexDirection, i8, i9, i12);
    }

    private void measureVertical(int i, int i2) {
        int i3;
        int i4;
        LayoutParams layoutParams;
        int i5;
        int i6 = i;
        int i7 = i2;
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        this.mFlexLines.clear();
        int childCount = getChildCount();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        FlexLine flexLine = new FlexLine();
        int i8 = paddingTop + paddingBottom;
        flexLine.mMainSize = i8;
        FlexLine flexLine2 = flexLine;
        int i9 = 0;
        int i10 = Integer.MIN_VALUE;
        int i11 = 0;
        int i12 = 0;
        while (i11 < childCount) {
            View reorderedChildAt = getReorderedChildAt(i11);
            if (reorderedChildAt == null) {
                addFlexLineIfLastFlexItem(i11, childCount, flexLine2);
            } else if (reorderedChildAt.getVisibility() == 8) {
                flexLine2.mItemCount++;
                addFlexLineIfLastFlexItem(i11, childCount, flexLine2);
            } else {
                LayoutParams layoutParams2 = (LayoutParams) reorderedChildAt.getLayoutParams();
                if (layoutParams2.alignSelf == 4) {
                    flexLine2.mIndicesAlignSelfStretch.add(Integer.valueOf(i11));
                }
                int i13 = layoutParams2.height;
                if (layoutParams2.flexBasisPercent != -1.0f && mode == 1073741824) {
                    i13 = Math.round(((float) size) * layoutParams2.flexBasisPercent);
                }
                reorderedChildAt.measure(getChildMeasureSpec(i6, getPaddingLeft() + getPaddingRight() + layoutParams2.leftMargin + layoutParams2.rightMargin, layoutParams2.width), getChildMeasureSpec(i7, getPaddingTop() + getPaddingBottom() + layoutParams2.topMargin + layoutParams2.bottomMargin, i13));
                checkSizeConstraints(reorderedChildAt);
                int combineMeasuredStates = ViewCompat.combineMeasuredStates(i9, ViewCompat.getMeasuredState(reorderedChildAt));
                int max = Math.max(i10, reorderedChildAt.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
                LayoutParams layoutParams3 = layoutParams2;
                int i14 = combineMeasuredStates;
                View view = reorderedChildAt;
                FlexLine flexLine3 = flexLine2;
                i3 = mode;
                i4 = i11;
                if (isWrapRequired(mode, size, flexLine2.mMainSize, reorderedChildAt.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin, layoutParams3, i11, i12)) {
                    if (flexLine3.mItemCount > 0) {
                        addFlexLine(flexLine3);
                    }
                    flexLine2 = new FlexLine();
                    flexLine2.mItemCount = 1;
                    flexLine2.mMainSize = i8;
                    layoutParams = layoutParams3;
                    max = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                    i5 = 0;
                } else {
                    layoutParams = layoutParams3;
                    flexLine3.mItemCount++;
                    i5 = i12 + 1;
                    flexLine2 = flexLine3;
                }
                flexLine2.mMainSize += view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                flexLine2.mTotalFlexGrow += layoutParams.flexGrow;
                flexLine2.mTotalFlexShrink += layoutParams.flexShrink;
                flexLine2.mCrossSize = Math.max(flexLine2.mCrossSize, max);
                if (hasDividerBeforeChildAtAlongMainAxis(i4, i5)) {
                    flexLine2.mMainSize += this.mDividerHorizontalHeight;
                }
                addFlexLineIfLastFlexItem(i4, childCount, flexLine2);
                i12 = i5;
                i10 = max;
                i9 = i14;
                i11 = i4 + 1;
                i6 = i;
                mode = i3;
            }
            i3 = mode;
            i4 = i11;
            i11 = i4 + 1;
            i6 = i;
            mode = i3;
        }
        int i15 = i;
        determineMainSize(this.mFlexDirection, i15, i7);
        determineCrossSize(this.mFlexDirection, i15, i7, getPaddingLeft() + getPaddingRight());
        stretchViews(this.mFlexDirection, this.mAlignItems);
        setMeasuredDimensionForFlex(this.mFlexDirection, i15, i7, i9);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkSizeConstraints(android.view.View r7) {
        /*
            r6 = this;
            android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
            com.google.android.flexbox.FlexboxLayout$LayoutParams r0 = (com.google.android.flexbox.FlexboxLayout.LayoutParams) r0
            int r1 = r7.getMeasuredWidth()
            int r2 = r7.getMeasuredHeight()
            int r3 = r7.getMeasuredWidth()
            int r4 = r0.minWidth
            r5 = 1
            if (r3 >= r4) goto L_0x001b
            int r1 = r0.minWidth
        L_0x0019:
            r3 = 1
            goto L_0x0027
        L_0x001b:
            int r3 = r7.getMeasuredWidth()
            int r4 = r0.maxWidth
            if (r3 <= r4) goto L_0x0026
            int r1 = r0.maxWidth
            goto L_0x0019
        L_0x0026:
            r3 = 0
        L_0x0027:
            int r4 = r0.minHeight
            if (r2 >= r4) goto L_0x002e
            int r2 = r0.minHeight
            goto L_0x0036
        L_0x002e:
            int r4 = r0.maxHeight
            if (r2 <= r4) goto L_0x0035
            int r2 = r0.maxHeight
            goto L_0x0036
        L_0x0035:
            r5 = r3
        L_0x0036:
            if (r5 == 0) goto L_0x0045
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r0)
            r7.measure(r1, r0)
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.flexbox.FlexboxLayout.checkSizeConstraints(android.view.View):void");
    }

    private void addFlexLineIfLastFlexItem(int i, int i2, FlexLine flexLine) {
        if (i == i2 - 1 && flexLine.mItemCount != 0) {
            addFlexLine(flexLine);
        }
    }

    private void addFlexLine(FlexLine flexLine) {
        if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
            if ((this.mShowDividerVertical & 4) > 0) {
                flexLine.mMainSize += this.mDividerVerticalWidth;
                flexLine.mDividerLengthInMainSize += this.mDividerVerticalWidth;
            }
        } else if ((this.mShowDividerHorizontal & 4) > 0) {
            flexLine.mMainSize += this.mDividerHorizontalHeight;
            flexLine.mDividerLengthInMainSize += this.mDividerHorizontalHeight;
        }
        this.mFlexLines.add(flexLine);
    }

    private void determineMainSize(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = i;
        if (i8 == 0 || i8 == 1) {
            int mode = MeasureSpec.getMode(i2);
            int size = MeasureSpec.getSize(i2);
            if (mode != 1073741824) {
                size = getLargestMainSize();
            }
            i6 = getPaddingLeft();
            i5 = getPaddingRight();
        } else if (i8 == 2 || i8 == 3) {
            int mode2 = MeasureSpec.getMode(i3);
            i4 = MeasureSpec.getSize(i3);
            if (mode2 != 1073741824) {
                i4 = getLargestMainSize();
            }
            i6 = getPaddingTop();
            i5 = getPaddingBottom();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid flex direction: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        int i9 = i6 + i5;
        int i10 = i4;
        int i11 = 0;
        for (FlexLine flexLine : this.mFlexLines) {
            if (flexLine.mMainSize < i10) {
                i7 = expandFlexItems(i2, i3, flexLine, i, i10, i9, i11, false);
            } else {
                i7 = shrinkFlexItems(i2, i3, flexLine, i, i10, i9, i11, false);
            }
            i11 = i7;
        }
    }

    private int expandFlexItems(int i, int i2, FlexLine flexLine, int i3, int i4, int i5, int i6, boolean z) {
        float f;
        int i7;
        double d;
        int i8;
        double d2;
        FlexLine flexLine2 = flexLine;
        int i9 = i4;
        if (flexLine2.mTotalFlexGrow <= 0.0f || i9 < flexLine2.mMainSize) {
            return i6 + flexLine2.mItemCount;
        }
        int i10 = flexLine2.mMainSize;
        float f2 = ((float) (i9 - flexLine2.mMainSize)) / flexLine2.mTotalFlexGrow;
        flexLine2.mMainSize = i5 + flexLine2.mDividerLengthInMainSize;
        if (!z) {
            flexLine2.mCrossSize = Integer.MIN_VALUE;
        }
        int i11 = 0;
        int i12 = i6;
        boolean z2 = false;
        float f3 = 0.0f;
        int i13 = 0;
        while (i11 < flexLine2.mItemCount) {
            View reorderedChildAt = getReorderedChildAt(i12);
            if (reorderedChildAt != null) {
                if (reorderedChildAt.getVisibility() == 8) {
                    i12++;
                } else {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (isMainAxisDirectionHorizontal(i3)) {
                        if (!this.mChildrenFrozen[i12]) {
                            float measuredWidth = ((float) reorderedChildAt.getMeasuredWidth()) + (layoutParams.flexGrow * f2);
                            if (i11 == flexLine2.mItemCount - 1) {
                                measuredWidth += f3;
                                f3 = 0.0f;
                            }
                            int round = Math.round(measuredWidth);
                            if (round > layoutParams.maxWidth) {
                                round = layoutParams.maxWidth;
                                this.mChildrenFrozen[i12] = true;
                                flexLine2.mTotalFlexGrow -= layoutParams.flexGrow;
                                i8 = i2;
                                z2 = true;
                            } else {
                                f3 += measuredWidth - ((float) round);
                                double d3 = (double) f3;
                                if (d3 > 1.0d) {
                                    round++;
                                    Double.isNaN(d3);
                                    d2 = d3 - 1.0d;
                                } else {
                                    if (d3 < -1.0d) {
                                        round--;
                                        Double.isNaN(d3);
                                        d2 = d3 + 1.0d;
                                    }
                                    i8 = i2;
                                }
                                f3 = (float) d2;
                                i8 = i2;
                            }
                            reorderedChildAt.measure(MeasureSpec.makeMeasureSpec(round, 1073741824), getChildHeightMeasureSpec(i8, layoutParams));
                            i13 = Math.max(i13, reorderedChildAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                        } else {
                            int i14 = i2;
                        }
                        flexLine2.mMainSize += reorderedChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                        f = f2;
                    } else {
                        int i15 = i2;
                        if (!this.mChildrenFrozen[i12]) {
                            float measuredHeight = ((float) reorderedChildAt.getMeasuredHeight()) + (layoutParams.flexGrow * f2);
                            if (i11 == flexLine2.mItemCount - 1) {
                                measuredHeight += f3;
                                f3 = 0.0f;
                            }
                            int round2 = Math.round(measuredHeight);
                            if (round2 > layoutParams.maxHeight) {
                                round2 = layoutParams.maxHeight;
                                this.mChildrenFrozen[i12] = true;
                                flexLine2.mTotalFlexGrow -= layoutParams.flexGrow;
                                i7 = i;
                                f = f2;
                                z2 = true;
                            } else {
                                float f4 = f3 + (measuredHeight - ((float) round2));
                                f = f2;
                                double d4 = (double) f4;
                                if (d4 > 1.0d) {
                                    round2++;
                                    Double.isNaN(d4);
                                    d = d4 - 1.0d;
                                } else {
                                    if (d4 < -1.0d) {
                                        round2--;
                                        Double.isNaN(d4);
                                        d = d4 + 1.0d;
                                    }
                                    i7 = i;
                                }
                                f4 = (float) d;
                                i7 = i;
                            }
                            reorderedChildAt.measure(getChildWidthMeasureSpec(i7, layoutParams), MeasureSpec.makeMeasureSpec(round2, 1073741824));
                            i13 = Math.max(i13, reorderedChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                        } else {
                            f = f2;
                        }
                        flexLine2.mMainSize += reorderedChildAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    }
                    flexLine2.mCrossSize = Math.max(flexLine2.mCrossSize, i13);
                    i12++;
                    i11++;
                    int i16 = i4;
                    f2 = f;
                }
            }
            int i17 = i2;
            int i18 = i3;
            f = f2;
            i11++;
            int i162 = i4;
            f2 = f;
        }
        int i19 = i2;
        int i20 = i3;
        if (z2 && i10 != flexLine2.mMainSize) {
            expandFlexItems(i, i2, flexLine, i3, i4, i5, i6, true);
        }
        return i12;
    }

    private int shrinkFlexItems(int i, int i2, FlexLine flexLine, int i3, int i4, int i5, int i6, boolean z) {
        float f;
        int i7;
        FlexLine flexLine2 = flexLine;
        int i8 = i4;
        int i9 = flexLine2.mMainSize;
        if (flexLine2.mTotalFlexShrink <= 0.0f || i8 > flexLine2.mMainSize) {
            return i6 + flexLine2.mItemCount;
        }
        float f2 = ((float) (flexLine2.mMainSize - i8)) / flexLine2.mTotalFlexShrink;
        flexLine2.mMainSize = i5 + flexLine2.mDividerLengthInMainSize;
        if (!z) {
            flexLine2.mCrossSize = Integer.MIN_VALUE;
        }
        int i10 = 0;
        int i11 = i6;
        boolean z2 = false;
        float f3 = 0.0f;
        int i12 = 0;
        while (i10 < flexLine2.mItemCount) {
            View reorderedChildAt = getReorderedChildAt(i11);
            if (reorderedChildAt != null) {
                if (reorderedChildAt.getVisibility() == 8) {
                    i11++;
                } else {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    boolean z3 = true;
                    if (isMainAxisDirectionHorizontal(i3)) {
                        if (!this.mChildrenFrozen[i11]) {
                            float measuredWidth = ((float) reorderedChildAt.getMeasuredWidth()) - (layoutParams.flexShrink * f2);
                            if (i10 == flexLine2.mItemCount - 1) {
                                measuredWidth += f3;
                                f3 = 0.0f;
                            }
                            int round = Math.round(measuredWidth);
                            if (round < layoutParams.minWidth) {
                                round = layoutParams.minWidth;
                                this.mChildrenFrozen[i11] = true;
                                flexLine2.mTotalFlexShrink -= layoutParams.flexShrink;
                            } else {
                                f3 += measuredWidth - ((float) round);
                                double d = (double) f3;
                                if (d > 1.0d) {
                                    round++;
                                    f3 -= 1.0f;
                                } else if (d < -1.0d) {
                                    round--;
                                    f3 += 1.0f;
                                }
                                z3 = z2;
                            }
                            int i13 = round;
                            reorderedChildAt.measure(MeasureSpec.makeMeasureSpec(i13, 1073741824), getChildHeightMeasureSpec(i2, layoutParams));
                            i12 = Math.max(i12, reorderedChildAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                        } else {
                            int i14 = i2;
                            z3 = z2;
                        }
                        flexLine2.mMainSize += reorderedChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                        f = f2;
                        int i15 = i;
                    } else {
                        int i16 = i2;
                        if (!this.mChildrenFrozen[i11]) {
                            float measuredHeight = ((float) reorderedChildAt.getMeasuredHeight()) - (layoutParams.flexShrink * f2);
                            if (i10 == flexLine2.mItemCount - 1) {
                                measuredHeight += f3;
                                f3 = 0.0f;
                            }
                            int round2 = Math.round(measuredHeight);
                            f = f2;
                            if (round2 < layoutParams.minHeight) {
                                round2 = layoutParams.minHeight;
                                this.mChildrenFrozen[i11] = true;
                                flexLine2.mTotalFlexShrink -= layoutParams.flexShrink;
                                i7 = i;
                                z2 = true;
                            } else {
                                float f4 = f3 + (measuredHeight - ((float) round2));
                                double d2 = (double) f4;
                                if (d2 > 1.0d) {
                                    round2++;
                                    f4 -= 1.0f;
                                } else if (d2 < -1.0d) {
                                    round2--;
                                    f4 += 1.0f;
                                }
                                i7 = i;
                            }
                            reorderedChildAt.measure(getChildWidthMeasureSpec(i7, layoutParams), MeasureSpec.makeMeasureSpec(round2, 1073741824));
                            i12 = Math.max(i12, reorderedChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                        } else {
                            f = f2;
                            int i17 = i;
                        }
                        z3 = z2;
                        flexLine2.mMainSize += reorderedChildAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    }
                    flexLine2.mCrossSize = Math.max(flexLine2.mCrossSize, i12);
                    i11++;
                    z2 = z3;
                    i10++;
                    f2 = f;
                }
            }
            int i18 = i3;
            f = f2;
            int i19 = i;
            i10++;
            f2 = f;
        }
        int i20 = i;
        int i21 = i3;
        if (z2 && i9 != flexLine2.mMainSize) {
            shrinkFlexItems(i, i2, flexLine, i3, i4, i5, i6, true);
        }
        return i11;
    }

    private int getChildWidthMeasureSpec(int i, LayoutParams layoutParams) {
        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width);
        int size = MeasureSpec.getSize(childMeasureSpec);
        if (size > layoutParams.maxWidth) {
            return MeasureSpec.makeMeasureSpec(layoutParams.maxWidth, MeasureSpec.getMode(childMeasureSpec));
        }
        return size < layoutParams.minWidth ? MeasureSpec.makeMeasureSpec(layoutParams.minWidth, MeasureSpec.getMode(childMeasureSpec)) : childMeasureSpec;
    }

    private int getChildHeightMeasureSpec(int i, LayoutParams layoutParams) {
        int childMeasureSpec = getChildMeasureSpec(i, getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height);
        int size = MeasureSpec.getSize(childMeasureSpec);
        if (size > layoutParams.maxHeight) {
            return MeasureSpec.makeMeasureSpec(layoutParams.maxHeight, MeasureSpec.getMode(childMeasureSpec));
        }
        return size < layoutParams.minHeight ? MeasureSpec.makeMeasureSpec(layoutParams.minHeight, MeasureSpec.getMode(childMeasureSpec)) : childMeasureSpec;
    }

    private void determineCrossSize(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i == 0 || i == 1) {
            i6 = MeasureSpec.getMode(i3);
            i5 = MeasureSpec.getSize(i3);
        } else if (i == 2 || i == 3) {
            i6 = MeasureSpec.getMode(i2);
            i5 = MeasureSpec.getSize(i2);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid flex direction: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i6 == 1073741824) {
            int sumOfCrossSize = getSumOfCrossSize() + i4;
            int i7 = 0;
            if (this.mFlexLines.size() == 1) {
                ((FlexLine) this.mFlexLines.get(0)).mCrossSize = i5 - i4;
            } else if (this.mFlexLines.size() >= 2 && sumOfCrossSize < i5) {
                int i8 = this.mAlignContent;
                if (i8 == 1) {
                    int i9 = i5 - sumOfCrossSize;
                    FlexLine flexLine = new FlexLine();
                    flexLine.mCrossSize = i9;
                    this.mFlexLines.add(0, flexLine);
                } else if (i8 == 2) {
                    int i10 = (i5 - sumOfCrossSize) / 2;
                    ArrayList arrayList = new ArrayList();
                    FlexLine flexLine2 = new FlexLine();
                    flexLine2.mCrossSize = i10;
                    int size = this.mFlexLines.size();
                    while (i7 < size) {
                        if (i7 == 0) {
                            arrayList.add(flexLine2);
                        }
                        arrayList.add((FlexLine) this.mFlexLines.get(i7));
                        if (i7 == this.mFlexLines.size() - 1) {
                            arrayList.add(flexLine2);
                        }
                        i7++;
                    }
                    this.mFlexLines = arrayList;
                } else if (i8 == 3) {
                    float size2 = ((float) (i5 - sumOfCrossSize)) / ((float) (this.mFlexLines.size() - 1));
                    ArrayList arrayList2 = new ArrayList();
                    int size3 = this.mFlexLines.size();
                    float f = 0.0f;
                    while (i7 < size3) {
                        arrayList2.add((FlexLine) this.mFlexLines.get(i7));
                        if (i7 != this.mFlexLines.size() - 1) {
                            FlexLine flexLine3 = new FlexLine();
                            if (i7 == this.mFlexLines.size() - 2) {
                                flexLine3.mCrossSize = Math.round(f + size2);
                                f = 0.0f;
                            } else {
                                flexLine3.mCrossSize = Math.round(size2);
                            }
                            f += size2 - ((float) flexLine3.mCrossSize);
                            if (f > 1.0f) {
                                flexLine3.mCrossSize++;
                                f -= 1.0f;
                            } else if (f < -1.0f) {
                                flexLine3.mCrossSize--;
                                f += 1.0f;
                            }
                            arrayList2.add(flexLine3);
                        }
                        i7++;
                    }
                    this.mFlexLines = arrayList2;
                } else if (i8 == 4) {
                    int size4 = (i5 - sumOfCrossSize) / (this.mFlexLines.size() * 2);
                    ArrayList arrayList3 = new ArrayList();
                    FlexLine flexLine4 = new FlexLine();
                    flexLine4.mCrossSize = size4;
                    for (FlexLine flexLine5 : this.mFlexLines) {
                        arrayList3.add(flexLine4);
                        arrayList3.add(flexLine5);
                        arrayList3.add(flexLine4);
                    }
                    this.mFlexLines = arrayList3;
                } else if (i8 == 5) {
                    float size5 = ((float) (i5 - sumOfCrossSize)) / ((float) this.mFlexLines.size());
                    int size6 = this.mFlexLines.size();
                    float f2 = 0.0f;
                    while (i7 < size6) {
                        FlexLine flexLine6 = (FlexLine) this.mFlexLines.get(i7);
                        float f3 = ((float) flexLine6.mCrossSize) + size5;
                        if (i7 == this.mFlexLines.size() - 1) {
                            f3 += f2;
                            f2 = 0.0f;
                        }
                        int round = Math.round(f3);
                        f2 += f3 - ((float) round);
                        if (f2 > 1.0f) {
                            round++;
                            f2 -= 1.0f;
                        } else if (f2 < -1.0f) {
                            round--;
                            f2 += 1.0f;
                        }
                        flexLine6.mCrossSize = round;
                        i7++;
                    }
                }
            }
        }
    }

    private void stretchViews(int i, int i2) {
        String str = "Invalid flex direction: ";
        if (i2 == 4) {
            int i3 = 0;
            for (FlexLine flexLine : this.mFlexLines) {
                int i4 = i3;
                int i5 = 0;
                while (i5 < flexLine.mItemCount) {
                    View reorderedChildAt = getReorderedChildAt(i4);
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (layoutParams.alignSelf == -1 || layoutParams.alignSelf == 4) {
                        if (i == 0 || i == 1) {
                            stretchViewVertically(reorderedChildAt, flexLine.mCrossSize);
                        } else if (i == 2 || i == 3) {
                            stretchViewHorizontally(reorderedChildAt, flexLine.mCrossSize);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(i);
                            throw new IllegalArgumentException(sb.toString());
                        }
                    }
                    i5++;
                    i4++;
                }
                i3 = i4;
            }
            return;
        }
        for (FlexLine flexLine2 : this.mFlexLines) {
            Iterator it = flexLine2.mIndicesAlignSelfStretch.iterator();
            while (true) {
                if (it.hasNext()) {
                    View reorderedChildAt2 = getReorderedChildAt(((Integer) it.next()).intValue());
                    if (i == 0 || i == 1) {
                        stretchViewVertically(reorderedChildAt2, flexLine2.mCrossSize);
                    } else if (i == 2 || i == 3) {
                        stretchViewHorizontally(reorderedChildAt2, flexLine2.mCrossSize);
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(i);
                        throw new IllegalArgumentException(sb2.toString());
                    }
                }
            }
        }
    }

    private void stretchViewVertically(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        view.measure(MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(Math.max((i - layoutParams.topMargin) - layoutParams.bottomMargin, 0), 1073741824));
    }

    private void stretchViewHorizontally(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        view.measure(MeasureSpec.makeMeasureSpec(Math.max((i - layoutParams.leftMargin) - layoutParams.rightMargin, 0), 1073741824), MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824));
    }

    private void setMeasuredDimensionForFlex(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        if (i == 0 || i == 1) {
            i5 = getSumOfCrossSize() + getPaddingTop() + getPaddingBottom();
            i6 = getLargestMainSize();
        } else if (i == 2 || i == 3) {
            i5 = getLargestMainSize();
            i6 = getSumOfCrossSize() + getPaddingLeft() + getPaddingRight();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid flex direction: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        if (mode == Integer.MIN_VALUE) {
            if (size < i6) {
                i4 = ViewCompat.combineMeasuredStates(i4, 16777216);
            } else {
                size = i6;
            }
            i7 = ViewCompat.resolveSizeAndState(size, i2, i4);
        } else if (mode == 0) {
            i7 = ViewCompat.resolveSizeAndState(i6, i2, i4);
        } else if (mode == 1073741824) {
            if (size < i6) {
                i4 = ViewCompat.combineMeasuredStates(i4, 16777216);
            }
            i7 = ViewCompat.resolveSizeAndState(size, i2, i4);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unknown width mode is set: ");
            sb2.append(mode);
            throw new IllegalStateException(sb2.toString());
        }
        if (mode2 == Integer.MIN_VALUE) {
            if (size2 < i5) {
                i4 = ViewCompat.combineMeasuredStates(i4, 256);
                i5 = size2;
            }
            i8 = ViewCompat.resolveSizeAndState(i5, i3, i4);
        } else if (mode2 == 0) {
            i8 = ViewCompat.resolveSizeAndState(i5, i3, i4);
        } else if (mode2 == 1073741824) {
            if (size2 < i5) {
                i4 = ViewCompat.combineMeasuredStates(i4, 256);
            }
            i8 = ViewCompat.resolveSizeAndState(size2, i3, i4);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unknown height mode is set: ");
            sb3.append(mode2);
            throw new IllegalStateException(sb3.toString());
        }
        setMeasuredDimension(i7, i8);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isWrapRequired(int r3, int r4, int r5, int r6, com.google.android.flexbox.FlexboxLayout.LayoutParams r7, int r8, int r9) {
        /*
            r2 = this;
            int r0 = r2.mFlexWrap
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r7 = r7.wrapBefore
            r0 = 1
            if (r7 == 0) goto L_0x000c
            return r0
        L_0x000c:
            if (r3 != 0) goto L_0x000f
            return r1
        L_0x000f:
            int r3 = r2.mFlexDirection
            boolean r3 = r2.isMainAxisDirectionHorizontal(r3)
            if (r3 == 0) goto L_0x0029
            boolean r3 = r2.hasDividerBeforeChildAtAlongMainAxis(r8, r9)
            if (r3 == 0) goto L_0x0020
            int r3 = r2.mDividerVerticalWidth
            int r6 = r6 + r3
        L_0x0020:
            int r3 = r2.mShowDividerVertical
            r3 = r3 & 4
            if (r3 <= 0) goto L_0x003b
            int r3 = r2.mDividerVerticalWidth
            goto L_0x003a
        L_0x0029:
            boolean r3 = r2.hasDividerBeforeChildAtAlongMainAxis(r8, r9)
            if (r3 == 0) goto L_0x0032
            int r3 = r2.mDividerHorizontalHeight
            int r6 = r6 + r3
        L_0x0032:
            int r3 = r2.mShowDividerHorizontal
            r3 = r3 & 4
            if (r3 <= 0) goto L_0x003b
            int r3 = r2.mDividerHorizontalHeight
        L_0x003a:
            int r6 = r6 + r3
        L_0x003b:
            int r5 = r5 + r6
            if (r4 >= r5) goto L_0x003f
            goto L_0x0040
        L_0x003f:
            r0 = 0
        L_0x0040:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.flexbox.FlexboxLayout.isWrapRequired(int, int, int, int, com.google.android.flexbox.FlexboxLayout$LayoutParams, int, int):boolean");
    }

    private int getLargestMainSize() {
        int i = Integer.MIN_VALUE;
        for (FlexLine flexLine : this.mFlexLines) {
            i = Math.max(i, flexLine.mMainSize);
        }
        return i;
    }

    private int getSumOfCrossSize() {
        int i;
        int i2;
        int size = this.mFlexLines.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i4);
            if (hasDividerBeforeFlexLine(i4)) {
                if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                    i2 = this.mDividerHorizontalHeight;
                } else {
                    i2 = this.mDividerVerticalWidth;
                }
                i3 += i2;
            }
            if (hasEndDividerAfterFlexLine(i4)) {
                if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                    i = this.mDividerHorizontalHeight;
                } else {
                    i = this.mDividerVerticalWidth;
                }
                i3 += i;
            }
            i3 += flexLine.mCrossSize;
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int i5 = this.mFlexDirection;
        boolean z2 = false;
        if (i5 == 0) {
            layoutHorizontal(layoutDirection == 1, i, i2, i3, i4);
        } else if (i5 == 1) {
            layoutHorizontal(layoutDirection != 1, i, i2, i3, i4);
        } else if (i5 == 2) {
            if (layoutDirection == 1) {
                z2 = true;
            }
            layoutVertical(this.mFlexWrap == 2 ? !z2 : z2, false, i, i2, i3, i4);
        } else if (i5 == 3) {
            if (layoutDirection == 1) {
                z2 = true;
            }
            layoutVertical(this.mFlexWrap == 2 ? !z2 : z2, true, i, i2, i3, i4);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid flex direction is set: ");
            sb.append(this.mFlexDirection);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void layoutHorizontal(boolean r28, int r29, int r30, int r31, int r32) {
        /*
            r27 = this;
            r9 = r27
            int r10 = r27.getPaddingLeft()
            int r11 = r27.getPaddingRight()
            int r0 = r32 - r30
            int r12 = r31 - r29
            int r1 = r27.getPaddingBottom()
            int r0 = r0 - r1
            int r1 = r27.getPaddingTop()
            java.util.List<com.google.android.flexbox.FlexLine> r2 = r9.mFlexLines
            int r13 = r2.size()
            r2 = 0
            r15 = 0
        L_0x001f:
            if (r15 >= r13) goto L_0x0228
            java.util.List<com.google.android.flexbox.FlexLine> r3 = r9.mFlexLines
            java.lang.Object r3 = r3.get(r15)
            r8 = r3
            com.google.android.flexbox.FlexLine r8 = (com.google.android.flexbox.FlexLine) r8
            boolean r3 = r9.hasDividerBeforeFlexLine(r15)
            if (r3 == 0) goto L_0x0034
            int r3 = r9.mDividerHorizontalHeight
            int r0 = r0 - r3
            int r1 = r1 + r3
        L_0x0034:
            r16 = r0
            r17 = r1
            int r0 = r9.mJustifyContent
            r7 = 2
            r1 = 0
            if (r0 == 0) goto L_0x00b6
            r3 = 1
            if (r0 == r3) goto L_0x00ac
            r4 = 1073741824(0x40000000, float:2.0)
            if (r0 == r7) goto L_0x0098
            r5 = 3
            if (r0 == r5) goto L_0x007c
            r3 = 4
            if (r0 != r3) goto L_0x0063
            int r0 = r8.mItemCount
            if (r0 == 0) goto L_0x0059
            int r0 = r8.mMainSize
            int r0 = r12 - r0
            float r0 = (float) r0
            int r3 = r8.mItemCount
            float r3 = (float) r3
            float r0 = r0 / r3
            goto L_0x005a
        L_0x0059:
            r0 = 0
        L_0x005a:
            float r3 = (float) r10
            float r4 = r0 / r4
            float r3 = r3 + r4
            int r5 = r12 - r11
            float r5 = (float) r5
            float r5 = r5 - r4
            goto L_0x00bb
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid justifyContent is set: "
            r1.append(r2)
            int r2 = r9.mJustifyContent
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x007c:
            float r0 = (float) r10
            int r4 = r8.mItemCount
            if (r4 == r3) goto L_0x0086
            int r4 = r8.mItemCount
            int r4 = r4 - r3
            float r3 = (float) r4
            goto L_0x0088
        L_0x0086:
            r3 = 1065353216(0x3f800000, float:1.0)
        L_0x0088:
            int r4 = r8.mMainSize
            int r4 = r12 - r4
            float r4 = (float) r4
            float r3 = r4 / r3
            int r4 = r12 - r11
            float r5 = (float) r4
            r26 = r3
            r3 = r0
            r0 = r26
            goto L_0x00bb
        L_0x0098:
            float r0 = (float) r10
            int r3 = r8.mMainSize
            int r3 = r12 - r3
            float r3 = (float) r3
            float r3 = r3 / r4
            float r3 = r3 + r0
            int r0 = r12 - r11
            float r0 = (float) r0
            int r5 = r8.mMainSize
            int r5 = r12 - r5
            float r5 = (float) r5
            float r5 = r5 / r4
            float r5 = r0 - r5
            goto L_0x00ba
        L_0x00ac:
            int r0 = r8.mMainSize
            int r0 = r12 - r0
            int r0 = r0 + r11
            float r3 = (float) r0
            int r0 = r8.mMainSize
            int r0 = r0 - r10
            goto L_0x00b9
        L_0x00b6:
            float r3 = (float) r10
            int r0 = r12 - r11
        L_0x00b9:
            float r5 = (float) r0
        L_0x00ba:
            r0 = 0
        L_0x00bb:
            float r18 = java.lang.Math.max(r0, r1)
            r0 = r5
            r6 = 0
            r5 = r2
        L_0x00c2:
            int r1 = r8.mItemCount
            if (r6 >= r1) goto L_0x0217
            android.view.View r19 = r9.getReorderedChildAt(r5)
            if (r19 != 0) goto L_0x00d5
        L_0x00cc:
            r20 = r3
            r22 = r6
            r3 = r8
            r23 = 2
            goto L_0x020f
        L_0x00d5:
            int r1 = r19.getVisibility()
            r2 = 8
            if (r1 != r2) goto L_0x00e0
            int r5 = r5 + 1
            goto L_0x00cc
        L_0x00e0:
            android.view.ViewGroup$LayoutParams r1 = r19.getLayoutParams()
            r4 = r1
            com.google.android.flexbox.FlexboxLayout$LayoutParams r4 = (com.google.android.flexbox.FlexboxLayout.LayoutParams) r4
            int r1 = r4.leftMargin
            float r1 = (float) r1
            float r3 = r3 + r1
            int r1 = r4.rightMargin
            float r1 = (float) r1
            float r0 = r0 - r1
            boolean r1 = r9.hasDividerBeforeChildAtAlongMainAxis(r5, r6)
            if (r1 == 0) goto L_0x00fb
            int r1 = r9.mDividerVerticalWidth
            float r2 = (float) r1
            float r3 = r3 + r2
            float r1 = (float) r1
            float r0 = r0 - r1
        L_0x00fb:
            r21 = r0
            r20 = r3
            int r3 = r9.mFlexWrap
            if (r3 != r7) goto L_0x0165
            if (r28 == 0) goto L_0x013a
            int r2 = r9.mAlignItems
            int r0 = java.lang.Math.round(r21)
            int r1 = r19.getMeasuredWidth()
            int r22 = r0 - r1
            int r0 = r19.getMeasuredHeight()
            int r23 = r16 - r0
            int r24 = java.lang.Math.round(r21)
            r0 = r27
            r1 = r19
            r25 = r2
            r2 = r8
            r14 = r4
            r4 = r25
            r25 = r5
            r5 = r22
            r22 = r6
            r6 = r23
            r23 = 2
            r7 = r24
            r30 = r8
            r8 = r16
            r0.layoutSingleChildHorizontal(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01b3
        L_0x013a:
            r14 = r4
            r25 = r5
            r22 = r6
            r30 = r8
            r23 = 2
            int r4 = r9.mAlignItems
            int r5 = java.lang.Math.round(r20)
            int r0 = r19.getMeasuredHeight()
            int r6 = r16 - r0
            int r0 = java.lang.Math.round(r20)
            int r1 = r19.getMeasuredWidth()
            int r7 = r0 + r1
            r0 = r27
            r1 = r19
            r2 = r30
            r8 = r16
            r0.layoutSingleChildHorizontal(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01b3
        L_0x0165:
            r14 = r4
            r25 = r5
            r22 = r6
            r30 = r8
            r23 = 2
            if (r28 == 0) goto L_0x0192
            int r4 = r9.mAlignItems
            int r0 = java.lang.Math.round(r21)
            int r1 = r19.getMeasuredWidth()
            int r5 = r0 - r1
            int r7 = java.lang.Math.round(r21)
            int r0 = r19.getMeasuredHeight()
            int r8 = r17 + r0
            r0 = r27
            r1 = r19
            r2 = r30
            r6 = r17
            r0.layoutSingleChildHorizontal(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01b3
        L_0x0192:
            int r4 = r9.mAlignItems
            int r5 = java.lang.Math.round(r20)
            int r0 = java.lang.Math.round(r20)
            int r1 = r19.getMeasuredWidth()
            int r7 = r0 + r1
            int r0 = r19.getMeasuredHeight()
            int r8 = r17 + r0
            r0 = r27
            r1 = r19
            r2 = r30
            r6 = r17
            r0.layoutSingleChildHorizontal(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x01b3:
            int r0 = r19.getMeasuredWidth()
            float r0 = (float) r0
            float r0 = r0 + r18
            int r1 = r14.rightMargin
            float r1 = (float) r1
            float r0 = r0 + r1
            float r20 = r20 + r0
            int r0 = r19.getMeasuredWidth()
            float r0 = (float) r0
            float r0 = r0 + r18
            int r1 = r14.leftMargin
            float r1 = (float) r1
            float r0 = r0 + r1
            float r21 = r21 - r0
            int r5 = r25 + 1
            r3 = r30
            int r0 = r3.mLeft
            int r1 = r19.getLeft()
            int r2 = r14.leftMargin
            int r1 = r1 - r2
            int r0 = java.lang.Math.min(r0, r1)
            r3.mLeft = r0
            int r0 = r3.mTop
            int r1 = r19.getTop()
            int r2 = r14.topMargin
            int r1 = r1 - r2
            int r0 = java.lang.Math.min(r0, r1)
            r3.mTop = r0
            int r0 = r3.mRight
            int r1 = r19.getRight()
            int r2 = r14.rightMargin
            int r1 = r1 + r2
            int r0 = java.lang.Math.max(r0, r1)
            r3.mRight = r0
            int r0 = r3.mBottom
            int r1 = r19.getBottom()
            int r2 = r14.bottomMargin
            int r1 = r1 + r2
            int r0 = java.lang.Math.max(r0, r1)
            r3.mBottom = r0
            r0 = r21
        L_0x020f:
            int r6 = r22 + 1
            r8 = r3
            r3 = r20
            r7 = 2
            goto L_0x00c2
        L_0x0217:
            r25 = r5
            r3 = r8
            int r0 = r3.mCrossSize
            int r1 = r17 + r0
            int r0 = r3.mCrossSize
            int r0 = r16 - r0
            int r15 = r15 + 1
            r2 = r25
            goto L_0x001f
        L_0x0228:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.flexbox.FlexboxLayout.layoutHorizontal(boolean, int, int, int, int):void");
    }

    private void layoutSingleChildHorizontal(View view, FlexLine flexLine, int i, int i2, int i3, int i4, int i5, int i6) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.alignSelf != -1) {
            i2 = layoutParams.alignSelf;
        }
        int i7 = flexLine.mCrossSize;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2) {
                    int measuredHeight = (((i7 - view.getMeasuredHeight()) + layoutParams.topMargin) - layoutParams.bottomMargin) / 2;
                    if (i != 2) {
                        int i8 = i4 + measuredHeight;
                        view.layout(i3, i8, i5, view.getMeasuredHeight() + i8);
                        return;
                    }
                    int i9 = i4 - measuredHeight;
                    view.layout(i3, i9, i5, view.getMeasuredHeight() + i9);
                    return;
                } else if (i2 != 3) {
                    if (i2 != 4) {
                        return;
                    }
                } else if (i != 2) {
                    int max = Math.max(flexLine.mMaxBaseline - view.getBaseline(), layoutParams.topMargin);
                    view.layout(i3, i4 + max, i5, i6 + max);
                    return;
                } else {
                    int max2 = Math.max((flexLine.mMaxBaseline - view.getMeasuredHeight()) + view.getBaseline(), layoutParams.bottomMargin);
                    view.layout(i3, i4 - max2, i5, i6 - max2);
                    return;
                }
            } else if (i != 2) {
                int i10 = i4 + i7;
                view.layout(i3, (i10 - view.getMeasuredHeight()) - layoutParams.bottomMargin, i5, i10 - layoutParams.bottomMargin);
                return;
            } else {
                view.layout(i3, (i4 - i7) + view.getMeasuredHeight() + layoutParams.topMargin, i5, (i6 - i7) + view.getMeasuredHeight() + layoutParams.topMargin);
                return;
            }
        }
        if (i != 2) {
            view.layout(i3, i4 + layoutParams.topMargin, i5, i6 + layoutParams.topMargin);
        } else {
            view.layout(i3, i4 - layoutParams.bottomMargin, i5, i6 - layoutParams.bottomMargin);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void layoutVertical(boolean r27, boolean r28, int r29, int r30, int r31, int r32) {
        /*
            r26 = this;
            r9 = r26
            int r10 = r26.getPaddingTop()
            int r11 = r26.getPaddingBottom()
            int r0 = r26.getPaddingRight()
            int r1 = r26.getPaddingLeft()
            int r2 = r31 - r29
            int r12 = r32 - r30
            int r2 = r2 - r0
            java.util.List<com.google.android.flexbox.FlexLine> r0 = r9.mFlexLines
            int r13 = r0.size()
            r0 = 0
            r15 = 0
        L_0x001f:
            if (r15 >= r13) goto L_0x021d
            java.util.List<com.google.android.flexbox.FlexLine> r3 = r9.mFlexLines
            java.lang.Object r3 = r3.get(r15)
            r8 = r3
            com.google.android.flexbox.FlexLine r8 = (com.google.android.flexbox.FlexLine) r8
            boolean r3 = r9.hasDividerBeforeFlexLine(r15)
            if (r3 == 0) goto L_0x0034
            int r3 = r9.mDividerVerticalWidth
            int r1 = r1 + r3
            int r2 = r2 - r3
        L_0x0034:
            r16 = r1
            r17 = r2
            int r1 = r9.mJustifyContent
            r2 = 0
            if (r1 == 0) goto L_0x00b6
            r3 = 1
            if (r1 == r3) goto L_0x00ac
            r4 = 2
            r5 = 1073741824(0x40000000, float:2.0)
            if (r1 == r4) goto L_0x0098
            r4 = 3
            if (r1 == r4) goto L_0x007c
            r3 = 4
            if (r1 != r3) goto L_0x0063
            int r1 = r8.mItemCount
            if (r1 == 0) goto L_0x0059
            int r1 = r8.mMainSize
            int r1 = r12 - r1
            float r1 = (float) r1
            int r3 = r8.mItemCount
            float r3 = (float) r3
            float r1 = r1 / r3
            goto L_0x005a
        L_0x0059:
            r1 = 0
        L_0x005a:
            float r3 = (float) r10
            float r4 = r1 / r5
            float r3 = r3 + r4
            int r5 = r12 - r11
            float r5 = (float) r5
            float r5 = r5 - r4
            goto L_0x00bb
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid justifyContent is set: "
            r1.append(r2)
            int r2 = r9.mJustifyContent
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x007c:
            float r1 = (float) r10
            int r4 = r8.mItemCount
            if (r4 == r3) goto L_0x0086
            int r4 = r8.mItemCount
            int r4 = r4 - r3
            float r3 = (float) r4
            goto L_0x0088
        L_0x0086:
            r3 = 1065353216(0x3f800000, float:1.0)
        L_0x0088:
            int r4 = r8.mMainSize
            int r4 = r12 - r4
            float r4 = (float) r4
            float r3 = r4 / r3
            int r4 = r12 - r11
            float r5 = (float) r4
            r25 = r3
            r3 = r1
            r1 = r25
            goto L_0x00bb
        L_0x0098:
            float r1 = (float) r10
            int r3 = r8.mMainSize
            int r3 = r12 - r3
            float r3 = (float) r3
            float r3 = r3 / r5
            float r3 = r3 + r1
            int r1 = r12 - r11
            float r1 = (float) r1
            int r4 = r8.mMainSize
            int r4 = r12 - r4
            float r4 = (float) r4
            float r4 = r4 / r5
            float r5 = r1 - r4
            goto L_0x00ba
        L_0x00ac:
            int r1 = r8.mMainSize
            int r1 = r12 - r1
            int r1 = r1 + r11
            float r3 = (float) r1
            int r1 = r8.mMainSize
            int r1 = r1 - r10
            goto L_0x00b9
        L_0x00b6:
            float r3 = (float) r10
            int r1 = r12 - r11
        L_0x00b9:
            float r5 = (float) r1
        L_0x00ba:
            r1 = 0
        L_0x00bb:
            float r18 = java.lang.Math.max(r1, r2)
            r6 = r0
            r7 = 0
        L_0x00c1:
            int r0 = r8.mItemCount
            if (r7 >= r0) goto L_0x020c
            android.view.View r19 = r9.getReorderedChildAt(r6)
            if (r19 != 0) goto L_0x00d2
        L_0x00cb:
            r20 = r3
            r23 = r7
            r3 = r8
            goto L_0x0205
        L_0x00d2:
            int r0 = r19.getVisibility()
            r1 = 8
            if (r0 != r1) goto L_0x00dd
            int r6 = r6 + 1
            goto L_0x00cb
        L_0x00dd:
            android.view.ViewGroup$LayoutParams r0 = r19.getLayoutParams()
            r4 = r0
            com.google.android.flexbox.FlexboxLayout$LayoutParams r4 = (com.google.android.flexbox.FlexboxLayout.LayoutParams) r4
            int r0 = r4.topMargin
            float r0 = (float) r0
            float r3 = r3 + r0
            int r0 = r4.bottomMargin
            float r0 = (float) r0
            float r5 = r5 - r0
            boolean r0 = r9.hasDividerBeforeChildAtAlongMainAxis(r6, r7)
            if (r0 == 0) goto L_0x00f8
            int r0 = r9.mDividerHorizontalHeight
            float r1 = (float) r0
            float r3 = r3 + r1
            float r0 = (float) r0
            float r5 = r5 - r0
        L_0x00f8:
            r20 = r3
            r21 = r5
            if (r27 == 0) goto L_0x015b
            if (r28 == 0) goto L_0x0131
            r3 = 1
            int r5 = r9.mAlignItems
            int r0 = r19.getMeasuredWidth()
            int r22 = r17 - r0
            int r0 = java.lang.Math.round(r21)
            int r1 = r19.getMeasuredHeight()
            int r23 = r0 - r1
            int r24 = java.lang.Math.round(r21)
            r0 = r26
            r1 = r19
            r2 = r8
            r14 = r4
            r4 = r5
            r5 = r22
            r22 = r6
            r6 = r23
            r23 = r7
            r7 = r17
            r30 = r8
            r8 = r24
            r0.layoutSingleChildVertical(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01a9
        L_0x0131:
            r14 = r4
            r22 = r6
            r23 = r7
            r30 = r8
            r3 = 1
            int r4 = r9.mAlignItems
            int r0 = r19.getMeasuredWidth()
            int r5 = r17 - r0
            int r6 = java.lang.Math.round(r20)
            int r0 = java.lang.Math.round(r20)
            int r1 = r19.getMeasuredHeight()
            int r8 = r0 + r1
            r0 = r26
            r1 = r19
            r2 = r30
            r7 = r17
            r0.layoutSingleChildVertical(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01a9
        L_0x015b:
            r14 = r4
            r22 = r6
            r23 = r7
            r30 = r8
            if (r28 == 0) goto L_0x0187
            r3 = 0
            int r4 = r9.mAlignItems
            int r0 = java.lang.Math.round(r21)
            int r1 = r19.getMeasuredHeight()
            int r6 = r0 - r1
            int r0 = r19.getMeasuredWidth()
            int r7 = r16 + r0
            int r8 = java.lang.Math.round(r21)
            r0 = r26
            r1 = r19
            r2 = r30
            r5 = r16
            r0.layoutSingleChildVertical(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x01a9
        L_0x0187:
            r3 = 0
            int r4 = r9.mAlignItems
            int r6 = java.lang.Math.round(r20)
            int r0 = r19.getMeasuredWidth()
            int r7 = r16 + r0
            int r0 = java.lang.Math.round(r20)
            int r1 = r19.getMeasuredHeight()
            int r8 = r0 + r1
            r0 = r26
            r1 = r19
            r2 = r30
            r5 = r16
            r0.layoutSingleChildVertical(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x01a9:
            int r0 = r19.getMeasuredHeight()
            float r0 = (float) r0
            float r0 = r0 + r18
            int r1 = r14.bottomMargin
            float r1 = (float) r1
            float r0 = r0 + r1
            float r20 = r20 + r0
            int r0 = r19.getMeasuredHeight()
            float r0 = (float) r0
            float r0 = r0 + r18
            int r1 = r14.topMargin
            float r1 = (float) r1
            float r0 = r0 + r1
            float r21 = r21 - r0
            int r6 = r22 + 1
            r3 = r30
            int r0 = r3.mLeft
            int r1 = r19.getLeft()
            int r2 = r14.leftMargin
            int r1 = r1 - r2
            int r0 = java.lang.Math.min(r0, r1)
            r3.mLeft = r0
            int r0 = r3.mTop
            int r1 = r19.getTop()
            int r2 = r14.topMargin
            int r1 = r1 - r2
            int r0 = java.lang.Math.min(r0, r1)
            r3.mTop = r0
            int r0 = r3.mRight
            int r1 = r19.getRight()
            int r2 = r14.rightMargin
            int r1 = r1 + r2
            int r0 = java.lang.Math.max(r0, r1)
            r3.mRight = r0
            int r0 = r3.mBottom
            int r1 = r19.getBottom()
            int r2 = r14.bottomMargin
            int r1 = r1 + r2
            int r0 = java.lang.Math.max(r0, r1)
            r3.mBottom = r0
            r5 = r21
        L_0x0205:
            int r7 = r23 + 1
            r8 = r3
            r3 = r20
            goto L_0x00c1
        L_0x020c:
            r22 = r6
            r3 = r8
            int r0 = r3.mCrossSize
            int r1 = r16 + r0
            int r0 = r3.mCrossSize
            int r2 = r17 - r0
            int r15 = r15 + 1
            r0 = r22
            goto L_0x001f
        L_0x021d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.flexbox.FlexboxLayout.layoutVertical(boolean, boolean, int, int, int, int):void");
    }

    private void layoutSingleChildVertical(View view, FlexLine flexLine, boolean z, int i, int i2, int i3, int i4, int i5) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.alignSelf != -1) {
            i = layoutParams.alignSelf;
        }
        int i6 = flexLine.mCrossSize;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    int measuredWidth = (((i6 - view.getMeasuredWidth()) + MarginLayoutParamsCompat.getMarginStart(layoutParams)) - MarginLayoutParamsCompat.getMarginEnd(layoutParams)) / 2;
                    if (!z) {
                        view.layout(i2 + measuredWidth, i3, i4 + measuredWidth, i5);
                        return;
                    } else {
                        view.layout(i2 - measuredWidth, i3, i4 - measuredWidth, i5);
                        return;
                    }
                } else if (!(i == 3 || i == 4)) {
                    return;
                }
            } else if (!z) {
                view.layout(((i2 + i6) - view.getMeasuredWidth()) - layoutParams.rightMargin, i3, ((i4 + i6) - view.getMeasuredWidth()) - layoutParams.rightMargin, i5);
                return;
            } else {
                view.layout((i2 - i6) + view.getMeasuredWidth() + layoutParams.leftMargin, i3, (i4 - i6) + view.getMeasuredWidth() + layoutParams.leftMargin, i5);
                return;
            }
        }
        if (!z) {
            view.layout(i2 + layoutParams.leftMargin, i3, i4 + layoutParams.leftMargin, i5);
        } else {
            view.layout(i2 - layoutParams.rightMargin, i3, i4 - layoutParams.rightMargin, i5);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDividerDrawableVertical != null || this.mDividerDrawableHorizontal != null) {
            if (this.mShowDividerHorizontal != 0 || this.mShowDividerVertical != 0) {
                int layoutDirection = ViewCompat.getLayoutDirection(this);
                int i = this.mFlexDirection;
                boolean z = false;
                boolean z2 = true;
                if (i == 0) {
                    boolean z3 = layoutDirection == 1;
                    if (this.mFlexWrap == 2) {
                        z = true;
                    }
                    drawDividersHorizontal(canvas, z3, z);
                } else if (i == 1) {
                    boolean z4 = layoutDirection != 1;
                    if (this.mFlexWrap == 2) {
                        z = true;
                    }
                    drawDividersHorizontal(canvas, z4, z);
                } else if (i == 2) {
                    if (layoutDirection != 1) {
                        z2 = false;
                    }
                    if (this.mFlexWrap == 2) {
                        z2 = !z2;
                    }
                    drawDividersVertical(canvas, z2, false);
                } else if (i == 3) {
                    if (layoutDirection == 1) {
                        z = true;
                    }
                    if (this.mFlexWrap == 2) {
                        z = !z;
                    }
                    drawDividersVertical(canvas, z, true);
                }
            }
        }
    }

    private void drawDividersHorizontal(Canvas canvas, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        int i4;
        Canvas canvas2 = canvas;
        int paddingLeft = getPaddingLeft();
        int max = Math.max(0, (getWidth() - getPaddingRight()) - paddingLeft);
        int size = this.mFlexLines.size();
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i5);
            int i7 = i6;
            for (int i8 = 0; i8 < flexLine.mItemCount; i8++) {
                View reorderedChildAt = getReorderedChildAt(i7);
                LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                if (hasDividerBeforeChildAtAlongMainAxis(i7, i8)) {
                    if (z) {
                        i4 = reorderedChildAt.getRight() + layoutParams.rightMargin;
                    } else {
                        i4 = (reorderedChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerVerticalWidth;
                    }
                    drawVerticalDivider(canvas2, i4, flexLine.mTop, flexLine.mCrossSize);
                }
                if (i8 == flexLine.mItemCount - 1 && (this.mShowDividerVertical & 4) > 0) {
                    if (z) {
                        i3 = (reorderedChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerVerticalWidth;
                    } else {
                        i3 = reorderedChildAt.getRight() + layoutParams.rightMargin;
                    }
                    drawVerticalDivider(canvas2, i3, flexLine.mTop, flexLine.mCrossSize);
                }
                i7++;
            }
            if (hasDividerBeforeFlexLine(i5)) {
                if (z2) {
                    i2 = flexLine.mBottom;
                } else {
                    i2 = flexLine.mTop - this.mDividerHorizontalHeight;
                }
                drawHorizontalDivider(canvas2, paddingLeft, i2, max);
            }
            if (hasEndDividerAfterFlexLine(i5) && (this.mShowDividerHorizontal & 4) > 0) {
                if (z2) {
                    i = flexLine.mTop - this.mDividerHorizontalHeight;
                } else {
                    i = flexLine.mBottom;
                }
                drawHorizontalDivider(canvas2, paddingLeft, i, max);
            }
            i5++;
            i6 = i7;
        }
    }

    private void drawDividersVertical(Canvas canvas, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        int i4;
        Canvas canvas2 = canvas;
        int paddingTop = getPaddingTop();
        int max = Math.max(0, (getHeight() - getPaddingBottom()) - paddingTop);
        int size = this.mFlexLines.size();
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i5);
            int i7 = i6;
            for (int i8 = 0; i8 < flexLine.mItemCount; i8++) {
                View reorderedChildAt = getReorderedChildAt(i7);
                LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                if (hasDividerBeforeChildAtAlongMainAxis(i7, i8)) {
                    if (z2) {
                        i4 = reorderedChildAt.getBottom() + layoutParams.bottomMargin;
                    } else {
                        i4 = (reorderedChildAt.getTop() - layoutParams.topMargin) - this.mDividerHorizontalHeight;
                    }
                    drawHorizontalDivider(canvas2, flexLine.mLeft, i4, flexLine.mCrossSize);
                }
                if (i8 == flexLine.mItemCount - 1 && (this.mShowDividerHorizontal & 4) > 0) {
                    if (z2) {
                        i3 = (reorderedChildAt.getTop() - layoutParams.topMargin) - this.mDividerHorizontalHeight;
                    } else {
                        i3 = reorderedChildAt.getBottom() + layoutParams.bottomMargin;
                    }
                    drawHorizontalDivider(canvas2, flexLine.mLeft, i3, flexLine.mCrossSize);
                }
                i7++;
            }
            if (hasDividerBeforeFlexLine(i5)) {
                if (z) {
                    i2 = flexLine.mRight;
                } else {
                    i2 = flexLine.mLeft - this.mDividerVerticalWidth;
                }
                drawVerticalDivider(canvas2, i2, paddingTop, max);
            }
            if (hasEndDividerAfterFlexLine(i5) && (this.mShowDividerVertical & 4) > 0) {
                if (z) {
                    i = flexLine.mLeft - this.mDividerVerticalWidth;
                } else {
                    i = flexLine.mRight;
                }
                drawVerticalDivider(canvas2, i, paddingTop, max);
            }
            i5++;
            i6 = i7;
        }
    }

    private void drawVerticalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableVertical;
        if (drawable != null) {
            drawable.setBounds(i, i2, this.mDividerVerticalWidth + i, i3 + i2);
            this.mDividerDrawableVertical.draw(canvas);
        }
    }

    private void drawHorizontalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableHorizontal;
        if (drawable != null) {
            drawable.setBounds(i, i2, i3 + i, this.mDividerHorizontalHeight + i2);
            this.mDividerDrawableHorizontal.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getFlexDirection() {
        return this.mFlexDirection;
    }

    public void setFlexDirection(int i) {
        if (this.mFlexDirection != i) {
            this.mFlexDirection = i;
            requestLayout();
        }
    }

    public int getFlexWrap() {
        return this.mFlexWrap;
    }

    public void setFlexWrap(int i) {
        if (this.mFlexWrap != i) {
            this.mFlexWrap = i;
            requestLayout();
        }
    }

    public int getJustifyContent() {
        return this.mJustifyContent;
    }

    public void setJustifyContent(int i) {
        if (this.mJustifyContent != i) {
            this.mJustifyContent = i;
            requestLayout();
        }
    }

    public int getAlignItems() {
        return this.mAlignItems;
    }

    public void setAlignItems(int i) {
        if (this.mAlignItems != i) {
            this.mAlignItems = i;
            requestLayout();
        }
    }

    public int getAlignContent() {
        return this.mAlignContent;
    }

    public void setAlignContent(int i) {
        if (this.mAlignContent != i) {
            this.mAlignContent = i;
            requestLayout();
        }
    }

    public List<FlexLine> getFlexLines() {
        ArrayList arrayList = new ArrayList(this.mFlexLines.size());
        for (FlexLine flexLine : this.mFlexLines) {
            if (flexLine.getItemCount() != 0) {
                arrayList.add(flexLine);
            }
        }
        return arrayList;
    }

    public Drawable getDividerDrawableHorizontal() {
        return this.mDividerDrawableHorizontal;
    }

    public Drawable getDividerDrawableVertical() {
        return this.mDividerDrawableVertical;
    }

    public void setDividerDrawable(Drawable drawable) {
        setDividerDrawableHorizontal(drawable);
        setDividerDrawableVertical(drawable);
    }

    public void setDividerDrawableHorizontal(Drawable drawable) {
        if (drawable != this.mDividerDrawableHorizontal) {
            this.mDividerDrawableHorizontal = drawable;
            if (drawable != null) {
                this.mDividerHorizontalHeight = drawable.getIntrinsicHeight();
            } else {
                this.mDividerHorizontalHeight = 0;
            }
            setWillNotDrawFlag();
            requestLayout();
        }
    }

    public void setDividerDrawableVertical(Drawable drawable) {
        if (drawable != this.mDividerDrawableVertical) {
            this.mDividerDrawableVertical = drawable;
            if (drawable != null) {
                this.mDividerVerticalWidth = drawable.getIntrinsicWidth();
            } else {
                this.mDividerVerticalWidth = 0;
            }
            setWillNotDrawFlag();
            requestLayout();
        }
    }

    public int getShowDividerVertical() {
        return this.mShowDividerVertical;
    }

    public int getShowDividerHorizontal() {
        return this.mShowDividerHorizontal;
    }

    public void setShowDivider(int i) {
        setShowDividerVertical(i);
        setShowDividerHorizontal(i);
    }

    public void setShowDividerVertical(int i) {
        if (i != this.mShowDividerVertical) {
            this.mShowDividerVertical = i;
            requestLayout();
        }
    }

    public void setShowDividerHorizontal(int i) {
        if (i != this.mShowDividerHorizontal) {
            this.mShowDividerHorizontal = i;
            requestLayout();
        }
    }

    private void setWillNotDrawFlag() {
        if (this.mDividerDrawableHorizontal == null && this.mDividerDrawableVertical == null) {
            setWillNotDraw(true);
        } else {
            setWillNotDraw(false);
        }
    }

    private boolean hasDividerBeforeChildAtAlongMainAxis(int i, int i2) {
        boolean allViewsAreGoneBefore = allViewsAreGoneBefore(i, i2);
        boolean z = false;
        if (allViewsAreGoneBefore) {
            if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                if ((this.mShowDividerVertical & 1) != 0) {
                    z = true;
                }
                return z;
            }
            if ((this.mShowDividerHorizontal & 1) != 0) {
                z = true;
            }
            return z;
        } else if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
            if ((this.mShowDividerVertical & 2) != 0) {
                z = true;
            }
            return z;
        } else {
            if ((this.mShowDividerHorizontal & 2) != 0) {
                z = true;
            }
            return z;
        }
    }

    private boolean allViewsAreGoneBefore(int i, int i2) {
        for (int i3 = 1; i3 <= i2; i3++) {
            View reorderedChildAt = getReorderedChildAt(i - i3);
            if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDividerBeforeFlexLine(int i) {
        boolean z = false;
        if (i >= 0 && i < this.mFlexLines.size()) {
            if (allFlexLinesAreDummyBefore(i)) {
                if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                    if ((this.mShowDividerHorizontal & 1) != 0) {
                        z = true;
                    }
                    return z;
                }
                if ((this.mShowDividerVertical & 1) != 0) {
                    z = true;
                }
                return z;
            } else if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                if ((this.mShowDividerHorizontal & 2) != 0) {
                    z = true;
                }
                return z;
            } else if ((this.mShowDividerVertical & 2) != 0) {
                z = true;
            }
        }
        return z;
    }

    private boolean allFlexLinesAreDummyBefore(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (((FlexLine) this.mFlexLines.get(i2)).mItemCount > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean hasEndDividerAfterFlexLine(int i) {
        boolean z = false;
        if (i >= 0 && i < this.mFlexLines.size()) {
            for (int i2 = i + 1; i2 < this.mFlexLines.size(); i2++) {
                if (((FlexLine) this.mFlexLines.get(i2)).mItemCount > 0) {
                    return false;
                }
            }
            if (isMainAxisDirectionHorizontal(this.mFlexDirection)) {
                if ((this.mShowDividerHorizontal & 4) != 0) {
                    z = true;
                }
                return z;
            } else if ((this.mShowDividerVertical & 4) != 0) {
                z = true;
            }
        }
        return z;
    }
}
