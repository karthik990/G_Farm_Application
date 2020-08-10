package com.mobiroller.adapters.ecommerce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.activities.ecommerce.ProductDetailActivity;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.viewholders.ecommerce.ProductViewHolder;
import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends Adapter<ViewHolder> {
    private static final long CLICK_TIME_INTERVAL = 1000;
    Activity context;
    List<ProductDetailModel> data;
    ECommerceRequestHelper eCommerceRequestHelper;
    /* access modifiers changed from: private */
    public long mLastClickTime = 0;
    ProgressViewHelper progressViewHelper;

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public ProductListAdapter(Activity activity, List<ProductDetailModel> list) {
        this.context = activity;
        this.data = list;
        this.progressViewHelper = new ProgressViewHelper(activity);
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_ecommerce_list_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final ProductViewHolder productViewHolder = (ProductViewHolder) viewHolder;
        productViewHolder.bind((ProductDetailModel) this.data.get(i));
        productViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                long currentTimeMillis = System.currentTimeMillis();
                if (ProductListAdapter.this.mLastClickTime == 0 || currentTimeMillis - ProductListAdapter.this.mLastClickTime >= 1000) {
                    ProductListAdapter.this.mLastClickTime = System.currentTimeMillis();
                    String str = "featuredImage";
                    ProductDetailActivity.startActivity(productViewHolder.image.getContext(), (ProductDetailModel) ProductListAdapter.this.data.get(i), (String) null, str, ActivityOptionsCompat.makeSceneTransitionAnimation(ProductListAdapter.this.context, productViewHolder.image, str));
                }
            }
        });
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void addItems(List<ProductDetailModel> list) {
        int size = this.data.size();
        this.data.addAll(list);
        notifyItemRangeChanged(size, this.data.size() - size);
    }

    public void deleteAll() {
        this.data = new ArrayList();
        notifyDataSetChanged();
    }
}
