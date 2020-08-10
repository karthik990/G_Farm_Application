package com.mobiroller.views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

public abstract class EndlessRecyclerViewScrollListenerRecyclerView extends OnScrollListener {
    private int currentPage = 0;
    private boolean loading = true;
    private LinearLayoutManager mLinearLayoutManager;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 0;
    private int visibleThreshold = 5;

    public abstract void onLoadMore(int i, int i2);

    public EndlessRecyclerViewScrollListenerRecyclerView(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        int findFirstVisibleItemPosition = this.mLinearLayoutManager.findFirstVisibleItemPosition();
        int childCount = recyclerView.getChildCount();
        int itemCount = this.mLinearLayoutManager.getItemCount();
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
        if (!this.loading && itemCount - childCount <= findFirstVisibleItemPosition + this.visibleThreshold) {
            this.currentPage++;
            onLoadMore(this.currentPage, itemCount);
            this.loading = true;
        }
    }
}
