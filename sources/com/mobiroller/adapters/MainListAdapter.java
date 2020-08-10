package com.mobiroller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.NavDrawerItem;
import java.util.ArrayList;

public class MainListAdapter extends Adapter<ContentViewHolder> {
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private int selectedPos = 0;
    private String selectedPosColor = "#92CCCCCC";

    class ContentViewHolder extends ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        View view;

        ContentViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.imgIcon = (ImageView) view2.findViewById(R.id.slide_icon);
            this.txtTitle = (TextView) view2.findViewById(R.id.slide_title);
        }
    }

    public MainListAdapter(Context context2, ArrayList<NavDrawerItem> arrayList) {
        this.context = context2;
        this.navDrawerItems = arrayList;
    }

    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ContentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, viewGroup, false));
    }

    public void onBindViewHolder(ContentViewHolder contentViewHolder, int i) {
        NavDrawerItem navDrawerItem = (NavDrawerItem) this.navDrawerItems.get(i);
        if (navDrawerItem.getImageUrl() != null && !navDrawerItem.getImageUrl().equalsIgnoreCase("null")) {
            ImageManager.loadImageView(this.context, navDrawerItem.getImageUrl(), contentViewHolder.imgIcon);
        }
        contentViewHolder.txtTitle.setTextColor(navDrawerItem.textColor);
        contentViewHolder.txtTitle.setText(navDrawerItem.getTitle());
        if (this.selectedPos == i) {
            contentViewHolder.view.setBackgroundColor(Color.parseColor(this.selectedPosColor));
        } else {
            contentViewHolder.view.setBackgroundColor(0);
        }
    }

    public int getItemCount() {
        return this.navDrawerItems.size();
    }

    public void setSelectedPos(int i) {
        int i2 = this.selectedPos;
        this.selectedPos = i;
        notifyItemChanged(this.selectedPos);
        notifyItemChanged(i2);
    }
}
