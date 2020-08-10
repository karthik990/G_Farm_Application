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
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;

public class EmailViewHolder extends FormBaseViewHolder {
    private int actionBarColor;
    @BindView(2131362435)
    TextView email;
    @BindView(2131362436)
    ImageView emailIcon;
    @BindView(2131362437)
    LinearLayout emailMainLayout;
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

    public EmailViewHolder(View view, int i) {
        super(view);
        this.actionBarColor = i;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, final Activity activity, int i) {
        this.tableItemsModel = tableItemsModel2;
        DrawableCompat.setTint(this.emailIcon.getDrawable(), this.actionBarColor);
        this.title.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.email.setText(localizationHelper.getLocalizedTitle(tableItemsModel2.getValue()));
        this.emailMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mailto:");
                sb.append(EmailViewHolder.this.email.getText().toString());
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(sb.toString()));
                Activity activity = activity;
                activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_email)));
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i) {
        this.userProfileItem = userProfileElement;
    }

    public String getValue() {
        return this.email.getText().toString().trim();
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public void setValue(String str) {
        this.email.setText(str);
    }
}
