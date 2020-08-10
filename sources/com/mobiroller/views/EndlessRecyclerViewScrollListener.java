package com.mobiroller.views;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends OnScrollListener {
    private int currentPage = 0;
    private boolean loading = true;
    LayoutManager mLayoutManager;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 0;
    private int visibleThreshold = 5;

    public abstract void onLoadMore(int i, int i2);

    public EndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager) {
        this.mLayoutManager = gridLayoutManager;
        this.visibleThreshold *= gridLayoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.mLayoutManager = staggeredGridLayoutManager;
        this.visibleThreshold *= staggeredGridLayoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 == 0) {
                i = iArr[i2];
            } else if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        int itemCount = this.mLayoutManager.getItemCount();
        LayoutManager layoutManager = this.mLayoutManager;
        int i3 = layoutManager instanceof StaggeredGridLayoutManager ? getLastVisibleItem(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null)) : layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() : layoutManager instanceof GridLayoutManager ? ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() : 0;
        if (itemCount < this.previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = itemCount;
            if (itemCount == 0) {
                this.loading = true;
            }
        }
        if (this.loading && itemCount > this.previousTotalItemCount) {
            this.loading = false;
            this.previousTotalItemCount = itemCount;
        }
        if (!this.loading && i3 + this.visibleThreshold > itemCount) {
            this.currentPage++;
            onLoadMore(this.currentPage, itemCount);
            this.loading = true;
        }
    }
}
