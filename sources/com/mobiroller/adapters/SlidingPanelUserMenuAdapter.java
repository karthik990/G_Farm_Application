package com.mobiroller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.UserMenuModel;
import com.mobiroller.views.CircleImageView;
import java.util.List;

public class SlidingPanelUserMenuAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<UserMenuModel> mList;
    private int mSelectedColor;
    private int selectedPosition = -1;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public SlidingPanelUserMenuAdapter(Context context, List<UserMenuModel> list, int i) {
        super(context, R.layout.layout_sliding_panel_menu_item);
        this.mContext = context;
        this.mList = list;
        this.mSelectedColor = i;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.mList.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.layout_sliding_panel_menu_item, viewGroup, false);
        }
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.image);
        Glide.with(this.mContext).load(Integer.valueOf(((UserMenuModel) this.mList.get(i)).icon)).into((ImageView) view.findViewById(R.id.icon));
        if (i == this.selectedPosition) {
            circleImageView.setBorderColor(-1);
            circleImageView.setFillColor(this.mSelectedColor);
            circleImageView.setBorderWidth(3);
        } else {
            circleImageView.setBorderColor(-1);
            circleImageView.setFillColor(0);
            circleImageView.setBorderWidth(0);
        }
        return view;
    }

    public void setSelected(int i) {
        this.selectedPosition = i;
    }
}
