package com.mobiroller.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseProduct;
import com.mobiroller.models.InAppPurchaseProductImageModel;
import com.mobiroller.util.ImageManager;

public class InAppPurchaseViewHolder extends ViewHolder {
    @BindView(2131362531)
    ImageView image;
    @BindView(2131363258)
    TextView title;

    public InAppPurchaseViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(Context context, InAppPurchaseProduct inAppPurchaseProduct) {
        this.title.setText(inAppPurchaseProduct.title);
        if (inAppPurchaseProduct.productImages.size() > 0) {
            ImageManager.loadImageViewInAppPurchase(context, ((InAppPurchaseProductImageModel) inAppPurchaseProduct.productImages.get(0)).imageUrl, this.image);
        } else {
            Glide.with(context).load(Integer.valueOf(R.drawable.no_image)).into(this.image);
        }
    }
}
