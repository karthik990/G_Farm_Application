package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;

public class PhoneViewHolder extends FormBaseViewHolder {
    private int actionBarColor;
    @BindView(2131362441)
    TextView phone;
    @BindView(2131362442)
    ImageView phoneIcon;
    @BindView(2131362443)
    LinearLayout phoneMainLayout;
    private TableItemsModel tableItemsModel;
    @BindView(2131362450)
    TextView title;
    private UserProfileElement userProfileItem;

    public void clear() {
    }

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public boolean isValid() {
        return true;
    }

    public PhoneViewHolder(View view, int i) {
        super(view);
        this.actionBarColor = i;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(final TableItemsModel tableItemsModel2, final LocalizationHelper localizationHelper, final Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.title.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.phone.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue()));
        DrawableCompat.setTint(this.phoneIcon.getDrawable(), this.actionBarColor);
        this.phoneMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("tel:");
                sb.append(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue()));
                String sb2 = sb.toString();
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse(sb2));
                activity.startActivity(intent);
            }
        });
    }

    public void bindEmergency(final TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, final Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        if (VERSION.SDK_INT >= 21) {
            if (i != -1) {
                this.phoneIcon.setImageTintList(ColorStateList.valueOf(i));
                this.title.setTextColor(i);
                this.phone.setTextColor(i);
            } else {
                this.phoneIcon.setImageTintList(ColorStateList.valueOf(this.actionBarColor));
            }
        }
        this.title.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.phone.setText(tableItemsModel2.getPhoneNumber());
        this.phoneMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("tel:");
                sb.append(tableItemsModel2.getPhoneNumber());
                String sb2 = sb.toString();
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse(sb2));
                activity.startActivity(intent);
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.userProfileItem = userProfileElement;
    }

    public String getValue() {
        return this.phone.getText().toString();
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public void setValue(String str) {
        this.phone.setText(str);
    }
}
