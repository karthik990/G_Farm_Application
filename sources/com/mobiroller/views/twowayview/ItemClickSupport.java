package com.mobiroller.views.twowayview;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.mobiroller.mobi942763453128.R;

public class ItemClickSupport {
    /* access modifiers changed from: private */
    public OnItemClickListener mItemClickListener;
    /* access modifiers changed from: private */
    public OnItemLongClickListener mItemLongClickListener;
    private final RecyclerView mRecyclerView;
    private final TouchListener mTouchListener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerView recyclerView, View view, int i, long j);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView recyclerView, View view, int i, long j);
    }

    private class TouchListener extends ClickItemTouchListener {
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        TouchListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        /* access modifiers changed from: 0000 */
        public boolean performItemClick(RecyclerView recyclerView, View view, int i, long j) {
            if (ItemClickSupport.this.mItemClickListener == null) {
                return false;
            }
            view.playSoundEffect(0);
            ItemClickSupport.this.mItemClickListener.onItemClick(recyclerView, view, i, j);
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean performItemLongClick(RecyclerView recyclerView, View view, int i, long j) {
            if (ItemClickSupport.this.mItemLongClickListener == null) {
                return false;
            }
            view.performHapticFeedback(0);
            return ItemClickSupport.this.mItemLongClickListener.onItemLongClick(recyclerView, view, i, j);
        }
    }

    private ItemClickSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mTouchListener = new TouchListener(recyclerView);
        recyclerView.addOnItemTouchListener(this.mTouchListener);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (!this.mRecyclerView.isLongClickable()) {
            this.mRecyclerView.setLongClickable(true);
        }
        this.mItemLongClickListener = onItemLongClickListener;
    }

    public static ItemClickSupport addTo(RecyclerView recyclerView) {
        ItemClickSupport from = from(recyclerView);
        if (from != null) {
            return from;
        }
        ItemClickSupport itemClickSupport = new ItemClickSupport(recyclerView);
        recyclerView.setTag(R.id.twowayview_item_click_support, itemClickSupport);
        return itemClickSupport;
    }

    public static void removeFrom(RecyclerView recyclerView) {
        ItemClickSupport from = from(recyclerView);
        if (from != null) {
            recyclerView.removeOnItemTouchListener(from.mTouchListener);
            recyclerView.setTag(R.id.twowayview_item_click_support, null);
        }
    }

    public static ItemClickSupport from(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return null;
        }
        return (ItemClickSupport) recyclerView.getTag(R.id.twowayview_item_click_support);
    }
}
