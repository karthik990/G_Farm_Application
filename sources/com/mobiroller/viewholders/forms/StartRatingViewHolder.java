package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;

public class StartRatingViewHolder extends FormBaseViewHolder {
    private Activity activity;
    private LocalizationHelper localizationHelper;
    @BindView(2131362444)
    RatingBar ratingBar;
    private TableItemsModel tableItemsModel;
    @BindView(2131362450)
    TextView title;

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper2, Activity activity2, int i) {
    }

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public void setValue(String str) {
    }

    public StartRatingViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper2, Activity activity2, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.localizationHelper = localizationHelper2;
        this.activity = activity2;
        this.title.setText(localizationHelper2.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.title.setTextColor(i);
        this.ratingBar.setNumStars(Integer.parseInt(tableItemsModel2.getRatingLevel()));
        this.ratingBar.setStepSize(1.0f);
        DrawableCompat.setTint(this.ratingBar.getProgressDrawable(), i);
    }

    public String getValue() {
        return String.valueOf(this.ratingBar.getRating());
    }

    public String getId() {
        return this.tableItemsModel.getId();
    }

    public boolean isValid() {
        if (!this.tableItemsModel.getMandatory().equalsIgnoreCase("yes") || this.ratingBar.getRating() != 0.0f) {
            this.title.setText(this.localizationHelper.getLocalizedTitle(this.tableItemsModel.getTitle()));
            return true;
        }
        TextView textView = this.title;
        StringBuilder sb = new StringBuilder();
        sb.append(this.localizationHelper.getLocalizedTitle(this.tableItemsModel.getTitle()));
        sb.append(" - <font color='red'>");
        sb.append(this.activity.getResources().getString(R.string.required_field));
        sb.append("</font>");
        textView.setText(Html.fromHtml(sb.toString()));
        return false;
    }

    public void clear() {
        this.title.setText(this.localizationHelper.getLocalizedTitle(this.tableItemsModel.getTitle()));
        this.ratingBar.setRating(0.0f);
    }
}
