package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

public class AddressViewHolder extends FormBaseViewHolder {
    private int actionBarColor;
    @BindView(2131362430)
    TextView address;
    @BindView(2131362431)
    ImageView addressIcon;
    @BindView(2131362432)
    LinearLayout addressMainLayout;
    private TableItemsModel tableItemsModel;
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

    public AddressViewHolder(View view, int i) {
        super(view);
        this.actionBarColor = i;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, final Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        DrawableCompat.setTint(this.addressIcon.getDrawable(), this.actionBarColor);
        this.address.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue()));
        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.google.co.in/maps?q=");
        sb.append(this.address.getText().toString());
        final String sb2 = sb.toString();
        this.addressMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(sb2));
                activity.startActivity(intent);
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.userProfileItem = userProfileElement;
    }

    public String getValue() {
        return this.address.getText().toString().trim();
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public void setValue(String str) {
        this.address.setText(str);
    }
}
