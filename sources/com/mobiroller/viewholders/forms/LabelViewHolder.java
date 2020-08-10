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

public class LabelViewHolder extends FormBaseViewHolder {
    private int actionBarColor;
    @BindView(2131362438)
    TextView label;
    @BindView(2131362439)
    ImageView labelIcon;
    @BindView(2131362440)
    LinearLayout labelMainLayout;
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

    public LabelViewHolder(View view, int i) {
        super(view);
        this.actionBarColor = i;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(final TableItemsModel tableItemsModel2, final LocalizationHelper localizationHelper, final Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        DrawableCompat.setTint(this.labelIcon.getDrawable(), this.actionBarColor);
        this.title.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.label.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue()));
        this.labelMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue())));
                activity.startActivity(intent);
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.userProfileItem = userProfileElement;
    }

    public String getValue() {
        return this.label.getText().toString();
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public void setValue(String str) {
        this.label.setText(str);
    }
}
