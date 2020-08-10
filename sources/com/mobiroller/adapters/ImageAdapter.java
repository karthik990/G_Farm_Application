package com.mobiroller.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.GalleryModel;
import com.mobiroller.views.twowayview.StaggeredGridLayoutManager.LayoutParams;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;
import com.mobiroller.views.twowayview.TwoWayView;
import java.util.List;

public class ImageAdapter extends Adapter<ImageViewHolder> {
    private static final int TYPE_FULL = 0;
    private static final int TYPE_HALF = 1;
    private static final int TYPE_QUARTER = 2;
    MobiRollerApplication app = new MobiRollerApplication();
    private Context context;
    private List<GalleryModel> galleryModels;
    private final int mLayoutId;
    private final TwoWayView mRecyclerView;

    public class ImageViewHolder extends ViewHolder {
        TextView captionTextview;
        RelativeLayout gradientLayout;
        public ImageView imageView;

        ImageViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.gradientLayout = (RelativeLayout) view.findViewById(R.id.gallery_gradient);
            this.captionTextview = (TextView) view.findViewById(R.id.gallery_caption);
        }
    }

    public ImageAdapter(Context context2, List<GalleryModel> list, TwoWayView twoWayView, int i, LocalizationHelper localizationHelper) {
        this.context = context2;
        this.mRecyclerView = twoWayView;
        this.galleryModels = list;
        this.mLayoutId = i;
    }

    public int getItemViewType(int i) {
        int i2 = i % 8;
        if (i2 != 0) {
            if (!(i2 == 1 || i2 == 2 || i2 == 3)) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        return 0;
                    }
                }
            }
            return 2;
        }
        return 1;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (this.mLayoutId == 5) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_gallery_list_staggered_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_gallery_list_item, viewGroup, false);
        }
        return new ImageViewHolder(view);
    }

    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        int i2 = 0;
        boolean z = this.mRecyclerView.getOrientation() == Orientation.VERTICAL;
        View view = imageViewHolder.itemView;
        int i3 = this.mLayoutId;
        if (i3 == 5) {
            int itemViewType = getItemViewType(i);
            if (itemViewType == 0) {
                i2 = 3;
            } else if (itemViewType == 1) {
                i2 = 4;
            } else if (itemViewType == 2) {
                i2 = 2;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = imageViewHolder.imageView.getLayoutParams();
            if (!z) {
                layoutParams.span = i2;
                view.setLayoutParams(layoutParams);
            } else {
                layoutParams.span = i2;
                if (getItemViewType(i) == 2) {
                    int applyDimension = (int) TypedValue.applyDimension(1, 95.0f, this.context.getResources().getDisplayMetrics());
                    layoutParams.height = applyDimension;
                    layoutParams2.height = applyDimension;
                }
                view.setLayoutParams(layoutParams);
                imageViewHolder.imageView.setLayoutParams(layoutParams2);
            }
        } else if (i3 == 1) {
            RecyclerView.LayoutParams layoutParams3 = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams3.height = (int) TypedValue.applyDimension(1, 153.0f, this.context.getResources().getDisplayMetrics());
            view.setLayoutParams(layoutParams3);
        } else if (i3 == 2) {
            RecyclerView.LayoutParams layoutParams4 = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams4.height = (int) TypedValue.applyDimension(1, 107.0f, this.context.getResources().getDisplayMetrics());
            double d = (double) this.context.getResources().getDisplayMetrics().density;
            Double.isNaN(d);
            int i4 = (int) ((d * 1.35d) + 0.5d);
            view.setPadding(i4, i4, i4, i4);
            view.setLayoutParams(layoutParams4);
        } else if (i3 == 3) {
            imageViewHolder.gradientLayout.setVisibility(0);
        } else if (i3 == 4) {
            imageViewHolder.gradientLayout.setVisibility(0);
            if (((GalleryModel) this.galleryModels.get(i)).getCaption() != null) {
                imageViewHolder.captionTextview.setText(UtilManager.localizationHelper().getLocalizedTitle(((GalleryModel) this.galleryModels.get(i)).getCaption()));
            }
        }
        Glide.with(this.context).load(((GalleryModel) this.galleryModels.get(i)).getURL()).apply(RequestOptions.centerCropTransform().placeholder((int) R.drawable.no_image)).into(imageViewHolder.imageView);
    }

    public int getItemCount() {
        return this.galleryModels.size();
    }
}
