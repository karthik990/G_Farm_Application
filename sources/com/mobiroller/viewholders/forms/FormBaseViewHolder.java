package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;

public abstract class FormBaseViewHolder extends ViewHolder {
    public abstract void bind(TableItemsModel tableItemsModel, LocalizationHelper localizationHelper, Activity activity, int i);

    public abstract void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity, int i);

    public abstract void clear();

    public abstract String getId();

    public abstract byte[] getImage();

    public abstract String getValue();

    public abstract boolean isImage();

    public abstract boolean isValid();

    public abstract void setValue(String str);

    public FormBaseViewHolder(View view) {
        super(view);
    }
}
