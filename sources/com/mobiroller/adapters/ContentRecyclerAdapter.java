package com.mobiroller.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.ScreenUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentRecyclerAdapter extends Adapter<ContentViewHolder> {
    private Activity activity;
    private LayoutParams avatarParams = null;
    private String contentListIItemBackgroundURL = null;
    private ArrayList<HashMap<String, String>> data;
    private int device_height = 0;
    private Typeface face = null;
    private int height;
    private boolean heightFlag = false;
    private NavigationModel navigationModel;
    private ScreenHelper screenHelper;
    public ScreenModel screenModel;
    private int textColor = 0;

    class ContentViewHolder extends ViewHolder {
        ImageView arrow;
        ImageView contentAvatar;
        TextView contentTitle;
        int position;
        View view;

        ContentViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.contentTitle = (TextView) view2.findViewById(R.id.content_list_title);
            this.contentAvatar = (ImageView) view2.findViewById(R.id.content_list_image);
            this.arrow = (ImageView) view2.findViewById(R.id.arrow_image);
        }
    }

    public ContentRecyclerAdapter(Activity activity2, ArrayList<HashMap<String, String>> arrayList, ScreenModel screenModel2, ScreenHelper screenHelper2, SharedPrefHelper sharedPrefHelper) {
        this.activity = activity2;
        this.data = arrayList;
        this.screenHelper = screenHelper2;
        this.device_height = ScreenUtil.getScreenHeight();
        this.screenModel = screenModel2;
        getUiPropertiesFromScreenModel();
    }

    public ContentRecyclerAdapter(Activity activity2, ArrayList<HashMap<String, String>> arrayList, NavigationModel navigationModel2, ScreenHelper screenHelper2) {
        this.activity = activity2;
        this.data = arrayList;
        this.screenHelper = screenHelper2;
        this.device_height = ScreenUtil.getScreenHeight();
        this.navigationModel = navigationModel2;
        getUiPropertiesFromNavigationModel();
    }

    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_list_item, viewGroup, false);
        ImageManager.loadBackgroundImage(this.contentListIItemBackgroundURL, inflate);
        return new ContentViewHolder(inflate);
    }

    public void onBindViewHolder(ContentViewHolder contentViewHolder, int i) {
        this.avatarParams = contentViewHolder.contentAvatar.getLayoutParams();
        if (this.heightFlag) {
            this.avatarParams.height = Math.round((float) ((this.device_height * 45) / Constants.MobiRoller_Preferences_StandardHeight)) - 5;
            this.avatarParams.width = Math.round((float) ((this.device_height * 45) / Constants.MobiRoller_Preferences_StandardHeight)) - 5;
        } else {
            LayoutParams layoutParams = this.avatarParams;
            int i2 = this.height;
            layoutParams.height = i2 - 5;
            layoutParams.width = i2 - 5;
        }
        contentViewHolder.view.setMinimumHeight(this.height);
        loadItemUi(contentViewHolder, i);
    }

    public int getItemCount() {
        return this.data.size();
    }

    private void loadItemUi(ContentViewHolder contentViewHolder, int i) {
        contentViewHolder.position = i;
        if (this.face != null) {
            contentViewHolder.contentTitle.setTypeface(this.face);
        }
        if (this.textColor != 0) {
            contentViewHolder.contentTitle.setTextColor(this.textColor);
        }
        HashMap hashMap = (HashMap) this.data.get(i);
        if (Integer.parseInt((String) hashMap.get("screen_id")) == -1) {
            contentViewHolder.arrow.setVisibility(8);
        } else {
            contentViewHolder.arrow.setVisibility(0);
        }
        String str = (String) hashMap.get("img_url");
        String str2 = "";
        if (str == null || str.equalsIgnoreCase(str2)) {
            contentViewHolder.contentAvatar.setVisibility(8);
        } else {
            contentViewHolder.contentAvatar.setLayoutParams(this.avatarParams);
            contentViewHolder.contentAvatar.setVisibility(0);
        }
        contentViewHolder.contentTitle.setText((CharSequence) hashMap.get("title"));
        contentViewHolder.contentTitle.setAutoLinkMask(1);
        if (str != null && !str.equalsIgnoreCase(str2)) {
            ImageManager.loadImageView(this.activity, str, contentViewHolder.contentAvatar);
        }
    }

    private void getUiPropertiesFromScreenModel() {
        if (this.screenModel.getTableCellBackground() != null) {
            this.contentListIItemBackgroundURL = this.screenModel.getTableCellBackground().getImageURL();
        }
        if (this.screenModel.getTableTextColor() != null) {
            ScreenHelper screenHelper2 = this.screenHelper;
            this.textColor = ScreenHelper.setColorUnselected(this.screenModel.getTableTextColor());
        }
        if (this.screenModel.getTableFontName() != null) {
            try {
                this.face = this.screenHelper.getFontFromAsset(this.screenModel.getTableFontName());
            } catch (Exception unused) {
            }
        }
        if (this.screenModel.getTableRowHeight() != 0) {
            if (UtilManager.sharedPrefHelper().getTabActive()) {
                this.height = Math.round((float) ((this.screenModel.getTableRowHeight() * (this.device_height - UtilManager.sharedPrefHelper().getTabHeight())) / Constants.MobiRoller_Preferences_StandardHeight));
            } else {
                this.height = Math.round((float) ((this.screenModel.getTableRowHeight() * this.device_height) / Constants.MobiRoller_Preferences_StandardHeight));
            }
            this.heightFlag = true;
        }
    }

    private void getUiPropertiesFromNavigationModel() {
        if (this.navigationModel.getTableCellBackground() != null) {
            this.contentListIItemBackgroundURL = this.navigationModel.getTableCellBackground().getImageURL();
        }
        if (this.navigationModel.getMenuTextColor() != null) {
            ScreenHelper screenHelper2 = this.screenHelper;
            this.textColor = ScreenHelper.setColorUnselected(this.navigationModel.getMenuTextColor());
        }
        this.height = Math.round((float) ((this.device_height * 45) / Constants.MobiRoller_Preferences_StandardHeight));
        this.heightFlag = false;
    }
}
