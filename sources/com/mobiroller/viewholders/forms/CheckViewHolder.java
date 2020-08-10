package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.widget.CompoundButtonCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;

public class CheckViewHolder extends FormBaseViewHolder {
    @BindView(2131362433)
    Switch checkBox;
    public boolean isChecked = false;
    private TableItemsModel tableItemsModel;
    @BindView(2131362450)
    TextView title;
    private UserProfileElement userProfileItem;

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public boolean isValid() {
        return true;
    }

    public CheckViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CheckViewHolder.this.isChecked = z;
            }
        });
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.title.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.title.setTextColor(i);
        this.checkBox.setTextColor(i);
        int[] iArr = {-1, i, -1};
        CompoundButtonCompat.setButtonTintList(this.checkBox, new ColorStateList(new int[][]{new int[]{16842912}, new int[0]}, iArr));
        this.checkBox.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-16842910}, new int[]{16842912}, new int[0]}, iArr);
        if (VERSION.SDK_INT >= 23) {
            this.checkBox.setThumbTintList(colorStateList);
        }
        if (VERSION.SDK_INT >= 24) {
            this.checkBox.setTrackTintList(new ColorStateList(new int[][]{new int[]{-16842910}, new int[0]}, iArr));
            this.checkBox.setTrackTintMode(Mode.OVERLAY);
        }
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.userProfileItem = userProfileElement;
        this.title.setText(localizationHelper.getLocalizedTitle(userProfileElement.title));
        this.title.setTextColor(i);
        this.checkBox.setTextColor(i);
        int[] iArr = {-1, i, -1};
        CompoundButtonCompat.setButtonTintList(this.checkBox, new ColorStateList(new int[][]{new int[]{16842912}, new int[0]}, iArr));
        this.checkBox.setText(localizationHelper.getLocalizedTitle(userProfileElement.title));
        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-16842910}, new int[]{16842912}, new int[0]}, iArr);
        if (VERSION.SDK_INT >= 23) {
            this.checkBox.setThumbTintList(colorStateList);
        }
        if (VERSION.SDK_INT >= 24) {
            this.checkBox.setTrackTintList(new ColorStateList(new int[][]{new int[]{-16842910}, new int[0]}, iArr));
            this.checkBox.setTrackTintMode(Mode.OVERLAY);
        }
        if (userProfileElement.subType != null && userProfileElement.subType.equalsIgnoreCase("check")) {
            if (userProfileElement.value == null || (!userProfileElement.value.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) && !userProfileElement.value.equalsIgnoreCase("yes"))) {
                this.checkBox.setChecked(false);
            } else {
                this.checkBox.setChecked(true);
            }
        }
    }

    public String getValue() {
        return this.isChecked ? IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE : "0";
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public void clear() {
        this.isChecked = false;
        this.checkBox.setChecked(false);
    }

    public void setValue(String str) {
        if (str == null || (!str.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) && !str.equalsIgnoreCase("yes"))) {
            this.checkBox.setChecked(false);
        } else {
            this.checkBox.setChecked(true);
        }
    }
}
