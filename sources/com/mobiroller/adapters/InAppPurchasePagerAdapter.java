package com.mobiroller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseProduct;
import com.mobiroller.models.InAppPurchaseProductImageModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import java.util.List;

public class InAppPurchasePagerAdapter extends PagerAdapter {
    /* access modifiers changed from: private */
    public Activity mContext;
    private List<InAppPurchaseProduct> mDataList;
    /* access modifiers changed from: private */
    public boolean mIsFromActivity;
    /* access modifiers changed from: private */
    public String mScreenId;
    /* access modifiers changed from: private */
    public String screenType;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public InAppPurchasePagerAdapter(Activity activity, List<InAppPurchaseProduct> list, String str, String str2, boolean z) {
        this.mContext = activity;
        this.mScreenId = str;
        this.screenType = str2;
        this.mDataList = list;
        this.mIsFromActivity = z;
    }

    public int getCount() {
        return this.mDataList.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_in_app_purchase_view_pager_item, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.description);
        Button button = (Button) inflate.findViewById(R.id.button_more);
        InAppPurchaseProduct inAppPurchaseProduct = (InAppPurchaseProduct) this.mDataList.get(i);
        ImageManager.loadImageViewInAppPurchase(this.mContext, ((InAppPurchaseProductImageModel) inAppPurchaseProduct.productImages.get(0)).imageUrl, (ImageView) inflate.findViewById(R.id.image_view));
        textView.setText(UtilManager.localizationHelper().getLocalizedTitle(inAppPurchaseProduct.title));
        textView2.setText(UtilManager.localizationHelper().getLocalizedTitle(inAppPurchaseProduct.description));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MobirollerIntent.startInAppPurchaseDetailActivity(InAppPurchasePagerAdapter.this.mContext, (InAppPurchaseProduct) InAppPurchaseHelper.getScreenProductList(InAppPurchasePagerAdapter.this.mScreenId).get(i), InAppPurchasePagerAdapter.this.mScreenId, InAppPurchasePagerAdapter.this.screenType, InAppPurchasePagerAdapter.this.mIsFromActivity);
            }
        });
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MobirollerIntent.startInAppPurchaseDetailActivity(InAppPurchasePagerAdapter.this.mContext, (InAppPurchaseProduct) InAppPurchaseHelper.getScreenProductList(InAppPurchasePagerAdapter.this.mScreenId).get(i), InAppPurchasePagerAdapter.this.mScreenId, InAppPurchasePagerAdapter.this.screenType, InAppPurchasePagerAdapter.this.mIsFromActivity);
            }
        });
        viewGroup.addView(inflate);
        StringBuilder sb = new StringBuilder();
        sb.append("viewpager");
        sb.append(i);
        inflate.setTag(sb.toString());
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
