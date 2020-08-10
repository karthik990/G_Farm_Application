package com.mobiroller.adapters.ecommerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.fragments.ecommerce.ProductGalleryBottomSheetFragment;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductImage;
import java.util.ArrayList;
import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    AppCompatActivity context;
    /* access modifiers changed from: private */
    public ArrayList<ProductImage> images;
    LayoutInflater inflater;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ProductImageAdapter(AppCompatActivity appCompatActivity, List<ProductImage> list) {
        this.inflater = (LayoutInflater) appCompatActivity.getSystemService("layout_inflater");
        this.images = new ArrayList<>(list);
        this.context = appCompatActivity;
    }

    public int getCount() {
        return this.images.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.inflater.inflate(R.layout.layout_e_commerce_image_view_pager_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment = new ProductGalleryBottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ECommerceConstant.PRODUCT_IMAGE_LIST, ProductImageAdapter.this.images);
                productGalleryBottomSheetFragment.setArguments(bundle);
                productGalleryBottomSheetFragment.show(ProductImageAdapter.this.context.getSupportFragmentManager(), productGalleryBottomSheetFragment.getTag());
            }
        });
        Glide.with((FragmentActivity) this.context).load(((ProductImage) this.images.get(i)).f2181n).into(imageView);
        viewGroup.addView(inflate, -1, -1);
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
