package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import androidx.core.internal.view.SupportMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.EditTextHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.TextHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.util.ColorUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import java.util.Calendar;
import java.util.List;

public class TimeViewHolder extends FormBaseViewHolder {
    private Activity activity;
    private int lineCount;
    private TableItemsModel tableItemsModel;
    @BindView(2131362449)
    MaterialEditText time;
    private UserProfileElement userProfileItem;

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public TimeViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, final Activity activity2, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.activity = activity2;
        Calendar instance = Calendar.getInstance();
        this.time.setBaseColor(i);
        this.time.setPrimaryColor(i);
        this.time.setUnderlineColor(i);
        this.time.setFloatingLabelTextColor(i);
        this.time.setMetTextColor(i);
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.time.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()), (float) (point.x - TextHelper.convertDpToPixels(64.0f, activity2)), paint);
        MaterialEditText materialEditText = this.time;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.time.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.time.setLines(this.lineCount);
        EditTextHelper.setCursorColor(this.time, i);
        this.time.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        this.time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_av_timer_black_24dp, 0);
        final TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
            public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
                MaterialEditText materialEditText = TimeViewHolder.this.time;
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(":");
                sb.append(i2);
                materialEditText.setText(sb.toString());
                TimeViewHolder.this.time.setLines(1);
            }
        }, instance.get(11), instance.get(12), true);
        newInstance.setAccentColor(i);
        this.time.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                newInstance.show(activity2.getFragmentManager(), "timePicker");
            }
        });
        this.time.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    newInstance.show(activity2.getFragmentManager(), "timePicker");
                }
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, final Activity activity2, int i) {
        this.userProfileItem = userProfileElement;
        Calendar instance = Calendar.getInstance();
        this.activity = activity2;
        this.time.setBaseColor(i);
        this.time.setPrimaryColor(i);
        this.time.setUnderlineColor(i);
        this.time.setFloatingLabelTextColor(i);
        this.time.setMetTextColor(i);
        EditTextHelper.setCursorColor(this.time, i);
        this.time.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        this.time.setHint(localizationHelper.getLocalizedTitle(userProfileElement.title));
        this.time.setFloatingLabelText(localizationHelper.getLocalizedTitle(userProfileElement.title));
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.time.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(userProfileElement.title), (float) (point.x - TextHelper.convertDpToPixels(64.0f, activity2)), paint);
        MaterialEditText materialEditText = this.time;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.time.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.time.setLines(this.lineCount);
        this.time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_av_timer_black_24dp, 0);
        final TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
            public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
                MaterialEditText materialEditText = TimeViewHolder.this.time;
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(":");
                sb.append(i2);
                materialEditText.setText(sb.toString());
                TimeViewHolder.this.time.setLines(1);
            }
        }, instance.get(11), instance.get(12), true);
        newInstance.setAccentColor(i);
        this.time.setInputType(0);
        this.time.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                newInstance.show(activity2.getFragmentManager(), "timePicker");
            }
        });
        this.time.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    newInstance.show(activity2.getFragmentManager(), "timePicker");
                }
            }
        });
    }

    public String getValue() {
        return this.time.getText().toString();
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
        String str = "";
        if (tableItemsModel2 != null) {
            if (!tableItemsModel2.getMandatory().equalsIgnoreCase("YES") || !this.time.getText().toString().equalsIgnoreCase(str)) {
                return true;
            }
            this.time.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.time.setError(this.activity.getString(R.string.select_a_time));
            return false;
        } else if (!this.userProfileItem.mandotory || !this.time.getText().toString().equalsIgnoreCase(str)) {
            return true;
        } else {
            this.time.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.time.setError(this.activity.getString(R.string.select_a_time));
            return false;
        }
    }

    public void clear() {
        this.time.setText("");
        this.time.setLines(this.lineCount);
    }

    public void setValue(String str) {
        this.time.setText(str);
        this.time.setLines(1);
    }
}
