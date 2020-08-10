package com.stfalcon.chatkit.messages;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

class RecyclerScrollMoreListener extends OnScrollListener {
    private int currentPage = 0;
    private OnLoadMoreListener loadMoreListener;
    private boolean loading = true;
    private LayoutManager mLayoutManager;
    private int previousTotalItemCount = 0;

    interface OnLoadMoreListener {
        void onLoadMore(int i, int i2);
    }

    RecyclerScrollMoreListener(LinearLayoutManager linearLayoutManager, OnLoadMoreListener onLoadMoreListener) {
        this.mLayoutManager = linearLayoutManager;
        this.loadMoreListener = onLoadMoreListener;
    }

    private int getLastVisibleItem(int[] iArr) {
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
        if (this.loadMoreListener != null) {
            int itemCount = this.mLayoutManager.getItemCount();
            LayoutManager layoutManager = this.mLayoutManager;
            int i3 = layoutManager instanceof StaggeredGridLayoutManager ? getLastVisibleItem(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null)) : layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() : layoutManager instanceof GridLayoutManager ? ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() : 0;
            if (itemCount < this.previousTotalItemCount) {
                this.currentPage = 0;
                this.previousTotalItemCount = itemCount;
                if (itemCount == 0) {
                    this.loading = true;
                }
            }
            if (this.loading && itemCount > this.previousTotalItemCount) {
                this.loading = false;
                this.previousTotalItemCount = itemCount;
            }
            if (!this.loading && i3 + 5 > itemCount) {
                this.currentPage++;
                this.loadMoreListener.onLoadMore(this.currentPage, itemCount);
                this.loading = true;
            }
        }
    }
}
