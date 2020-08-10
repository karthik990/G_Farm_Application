package com.mobiroller.views;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener;
import com.mobiroller.mobi942763453128.R;

public class ItemClickSupport {
    private OnChildAttachStateChangeListener mAttachListener = new OnChildAttachStateChangeListener() {
        public void onChildViewDetachedFromWindow(View view) {
        }

        public void onChildViewAttachedToWindow(View view) {
            if (ItemClickSupport.this.mOnItemClickListener != null) {
                view.setOnClickListener(ItemClickSupport.this.mOnClickListener);
            }
            if (ItemClickSupport.this.mOnItemLongClickListener != null) {
                view.setOnLongClickListener(ItemClickSupport.this.mOnLongClickListener);
            }
        }
    };
    /* access modifiers changed from: private */
    public OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (ItemClickSupport.this.mOnItemClickListener != null) {
                ItemClickSupport.this.mOnItemClickListener.onItemClicked(ItemClickSupport.this.mRecyclerView, ItemClickSupport.this.mRecyclerView.getChildViewHolder(view).getAdapterPosition(), view);
            }
        }
    };
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    /* access modifiers changed from: private */
    public OnItemLongClickListener mOnItemLongClickListener;
    /* access modifiers changed from: private */
    public OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        public boolean onLongClick(View view) {
            if (ItemClickSupport.this.mOnItemLongClickListener == null) {
                return false;
            }
            return ItemClickSupport.this.mOnItemLongClickListener.onItemLongClicked(ItemClickSupport.this.mRecyclerView, ItemClickSupport.this.mRecyclerView.getChildViewHolder(view).getAdapterPosition(), view);
        }
    };
    /* access modifiers changed from: private */
    public final RecyclerView mRecyclerView;

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int i, View view);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int i, View view);
    }

    private ItemClickSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.setTag(R.id.item_click_support, this);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView recyclerView) {
        ItemClickSupport itemClickSupport = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        return itemClickSupport == null ? new ItemClickSupport(recyclerView) : itemClickSupport;
    }

    public static ItemClickSupport removeFrom(RecyclerView recyclerView) {
        ItemClickSupport itemClickSupport = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (itemClickSupport != null) {
            itemClickSupport.detach(recyclerView);
        }
        return itemClickSupport;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
        return this;
    }

    private void detach(RecyclerView recyclerView) {
        recyclerView.removeOnChildAttachStateChangeListener(this.mAttachListener);
        recyclerView.setTag(R.id.item_click_support, null);
    }
}
