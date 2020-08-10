package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.mobiroller.activities.ImagePagerActivity;
import com.mobiroller.activities.aveRssContentViewPager;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.FavoriteHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FavoriteModel;
import java.util.ArrayList;
import java.util.Iterator;

public class FavoriteAdapter extends Adapter<ViewHolder> implements Filterable {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public ArrayList<FavoriteModel> dataList = this.mFavoriteHelper.getDb();
    /* access modifiers changed from: private */
    public ArrayList<FavoriteModel> dataListFiltered = this.dataList;
    private FavoriteHelper mFavoriteHelper;
    /* access modifiers changed from: private */
    public MenuHelper menuHelper;

    private class FavoriteViewHolder extends ViewHolder {
        ImageView image;
        CardView mainLayout;
        TextView title;

        FavoriteViewHolder(View view) {
            super(view);
            this.mainLayout = (CardView) view.findViewById(R.id.favorite_item_main_layout);
            this.title = (TextView) view.findViewById(R.id.favorite_item_title);
            this.image = (ImageView) view.findViewById(R.id.favorite_item_image);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public FavoriteAdapter(Activity activity2, MenuHelper menuHelper2) {
        this.activity = activity2;
        this.menuHelper = menuHelper2;
        this.mFavoriteHelper = new FavoriteHelper(activity2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FavoriteViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_favorite_item, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final FavoriteModel favoriteModel = (FavoriteModel) this.dataListFiltered.get(i);
        if (favoriteModel.IsScreen()) {
            FavoriteViewHolder favoriteViewHolder = (FavoriteViewHolder) viewHolder;
            favoriteViewHolder.title.setText(UtilManager.localizationHelper().getLocalizedTitle(favoriteModel.getScreenTitle()));
            if (favoriteModel.getScreenImage() != null) {
                Glide.with(this.activity).load(favoriteModel.getScreenImage()).into(favoriteViewHolder.image);
            }
            favoriteViewHolder.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FavoriteAdapter.this.menuHelper.startActivityFromScreenModel(favoriteModel.getScreenModel(), favoriteModel.getScreenType(), favoriteModel.getmSubScreenType(), favoriteModel.getScreenId(), true);
                }
            });
        } else if (favoriteModel.getContentType() == 1) {
            FavoriteViewHolder favoriteViewHolder2 = (FavoriteViewHolder) viewHolder;
            favoriteViewHolder2.title.setText(favoriteModel.getRssModel().getTitle());
            if (favoriteModel.getRssModel().getImage() != null) {
                Glide.with(this.activity).load(favoriteModel.getRssModel().getImage()).into(favoriteViewHolder2.image);
            }
            favoriteViewHolder2.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(FavoriteAdapter.this.activity, aveRssContentViewPager.class);
                    intent.putExtra(Constants.KEY_RSS_TITLE, favoriteModel.getRssModel().getTitle());
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(favoriteModel.getRssModel());
                    FavoriteAdapter.this.activity.startActivity(intent);
                    aveRssContentViewPager.setRssModelList(arrayList);
                    aveRssContentViewPager.notifyAdapter();
                }
            });
        } else if (favoriteModel.getContentType() == 2) {
            FavoriteViewHolder favoriteViewHolder3 = (FavoriteViewHolder) viewHolder;
            favoriteViewHolder3.title.setText(UtilManager.localizationHelper().getLocalizedTitle(favoriteModel.getGalleryModel().getCaption()));
            if (favoriteViewHolder3.title.getText().toString().isEmpty()) {
                favoriteViewHolder3.title.setVisibility(8);
            }
            if (favoriteModel.getGalleryModel().getURL() != null) {
                Glide.with(this.activity).load(favoriteModel.getGalleryModel().getURL()).into(favoriteViewHolder3.image);
            }
            favoriteViewHolder3.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(FavoriteAdapter.this.activity, ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.TOOLBAR_TITLE, "title");
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(favoriteModel.getGalleryModel());
                    intent.putExtra("imageList", arrayList);
                    intent.putExtra(Constants.KEY_RSS_POSITION, 0);
                    intent.putExtra("isDownloadable", false);
                    intent.putExtra("fromGalleryView", true);
                    if (VERSION.SDK_INT >= 21) {
                        FavoriteAdapter.this.activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(FavoriteAdapter.this.activity, ((FavoriteViewHolder) viewHolder).image, "galleryImage").toBundle());
                        return;
                    }
                    FavoriteAdapter.this.activity.startActivity(intent);
                }
            });
        }
    }

    public int getItemCount() {
        return this.dataListFiltered.size();
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.toString().isEmpty()) {
                    FavoriteAdapter favoriteAdapter = FavoriteAdapter.this;
                    favoriteAdapter.dataListFiltered = favoriteAdapter.dataList;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = FavoriteAdapter.this.dataList.iterator();
                    while (it.hasNext()) {
                        FavoriteModel favoriteModel = (FavoriteModel) it.next();
                        if (favoriteModel.IsScreen() && UtilManager.localizationHelper().getLocalizedTitle(favoriteModel.getScreenModel().getTitle()).toLowerCase().contains(charSequence)) {
                            arrayList.add(favoriteModel);
                        } else if (favoriteModel.getContentType() == 1 && favoriteModel.getRssModel().getTitle().toLowerCase().contains(charSequence)) {
                            arrayList.add(favoriteModel);
                        } else if (favoriteModel.getContentType() == 2 && UtilManager.localizationHelper().getLocalizedTitle(favoriteModel.getGalleryModel().getCaption()).toLowerCase().contains(charSequence)) {
                            arrayList.add(favoriteModel);
                        }
                    }
                    FavoriteAdapter.this.dataListFiltered = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = FavoriteAdapter.this.dataListFiltered;
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FavoriteAdapter.this.dataListFiltered = (ArrayList) filterResults.values;
                FavoriteAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
