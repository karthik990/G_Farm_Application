package com.mobiroller.viewholders.updateprofile;

import android.app.Activity;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.helpers.EditTextHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.viewholders.forms.FormBaseViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TextViewHolder extends FormBaseViewHolder {
    private Activity activity;
    private TableItemsModel tableItemsModel;
    @BindView(2131362447)
    MaterialEditText textArea;
    private UserProfileElement userProfileItem;

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public TextViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, Activity activity2, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.activity = activity2;
        this.textArea.setBaseColor(i);
        this.textArea.setPrimaryColor(i);
        this.textArea.setUnderlineColor(i);
        this.textArea.setFloatingLabelTextColor(i);
        this.textArea.setMetTextColor(i);
        this.textArea.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        EditTextHelper.setCursorColor(this.textArea, i);
        this.textArea.setHint(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.textArea.setFloatingLabelText(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        if (tableItemsModel2.getType().equalsIgnoreCase("phone")) {
            this.textArea.setInputType(2);
        }
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity2, int i) {
        this.userProfileItem = userProfileElement;
        this.activity = activity2;
        this.textArea.setBaseColor(i);
        this.textArea.setPrimaryColor(i);
        this.textArea.setUnderlineColor(i);
        this.textArea.setFloatingLabelTextColor(i);
        this.textArea.setMetTextColor(i);
        this.textArea.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        EditTextHelper.setCursorColor(this.textArea, i);
        this.textArea.setHint(localizationHelper.getLocalizedTitle(userProfileElement.title));
        this.textArea.setFloatingLabelText(localizationHelper.getLocalizedTitle(userProfileElement.title));
        if (userProfileElement.type.equalsIgnoreCase("userName")) {
            this.textArea.setEnabled(false);
        }
        if (userProfileElement.type.equalsIgnoreCase("facebook")) {
            this.textArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.facebook, 0);
        } else if (userProfileElement.type.equalsIgnoreCase("twitter")) {
            this.textArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.twitter_vector, 0);
        } else if (userProfileElement.type.equalsIgnoreCase("website")) {
            this.textArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_language_black_36dp, 0);
        } else if (userProfileElement.type.equalsIgnoreCase("phone")) {
            this.textArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_phone_black_36dp, 0);
        }
    }

    public String getValue() {
        return this.textArea.getText().toString().trim();
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public boolean isValid() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            if (!tableItemsModel2.getMandatory().equalsIgnoreCase("YES") || !this.textArea.getText().toString().isEmpty()) {
                return true;
            }
            this.textArea.setError(this.activity.getString(R.string.text_validation_message));
            this.textArea.setErrorColor(SupportMenu.CATEGORY_MASK);
            return false;
        } else if (this.userProfileItem.type.equalsIgnoreCase("userName") || !this.userProfileItem.mandotory || !this.textArea.getText().toString().isEmpty()) {
            return true;
        } else {
            this.textArea.setError(this.activity.getString(R.string.text_validation_message));
            this.textArea.setErrorColor(SupportMenu.CATEGORY_MASK);
            return false;
        }
    }

    public void clear() {
        this.textArea.setText("");
    }

    public void setValue(String str) {
        this.textArea.setText(str);
    }
}
