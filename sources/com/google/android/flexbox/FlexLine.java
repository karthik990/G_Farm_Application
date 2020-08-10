package com.google.android.flexbox;

import java.util.ArrayList;
import java.util.List;

public class FlexLine {
    int mBottom = Integer.MIN_VALUE;
    int mCrossSize;
    int mDividerLengthInMainSize;
    List<Integer> mIndicesAlignSelfStretch = new ArrayList();
    int mItemCount;
    int mLeft = Integer.MAX_VALUE;
    int mMainSize;
    int mMaxBaseline;
    int mRight = Integer.MIN_VALUE;
    int mTop = Integer.MAX_VALUE;
    float mTotalFlexGrow;
    float mTotalFlexShrink;

    FlexLine() {
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getTop() {
        return this.mTop;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getBottom() {
        return this.mBottom;
    }

    public int getMainSize() {
        return this.mMainSize;
    }

    public int getCrossSize() {
        return this.mCrossSize;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public float getTotalFlexGrow() {
        return this.mTotalFlexGrow;
    }

    public float getTotalFlexShrink() {
        return this.mTotalFlexShrink;
    }
}
