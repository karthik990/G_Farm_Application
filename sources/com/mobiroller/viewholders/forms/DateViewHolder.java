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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DateViewHolder extends FormBaseViewHolder {
    Activity activity;
    @BindView(2131362434)
    MaterialEditText date;
    /* access modifiers changed from: private */
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private int lineCount;
    private TableItemsModel tableItemsModel;
    private UserProfileElement userProfileItem;

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public DateViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, final Activity activity2, int i) {
        this.activity = activity2;
        this.tableItemsModel = tableItemsModel2;
        this.date.setBaseColor(i);
        this.date.setPrimaryColor(i);
        this.date.setUnderlineColor(i);
        this.date.setFloatingLabelTextColor(i);
        this.date.setMetTextColor(i);
        this.date.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        EditTextHelper.setCursorColor(this.date, i);
        this.date.setContentDescription(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.date.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()), (float) (point.x - TextHelper.convertDpToPixels(64.0f, activity2)), paint);
        MaterialEditText materialEditText = this.date;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.date.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.date.setLines(this.lineCount);
        Calendar instance = Calendar.getInstance();
        this.date.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
        final DatePickerDialog newInstance = DatePickerDialog.newInstance(new OnDateSetListener() {
            public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {
                Calendar instance = Calendar.getInstance();
                instance.set(i, i2, i3);
                DateViewHolder.this.date.setText(DateViewHolder.this.dateFormatter.format(instance.getTime()));
                DateViewHolder.this.date.setLines(1);
            }
        }, instance.get(1), instance.get(2), instance.get(5));
        newInstance.setAccentColor(i);
        this.date.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                newInstance.show(activity2.getFragmentManager(), "datePicker");
            }
        });
        this.date.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    newInstance.show(activity2.getFragmentManager(), "datePicker");
                }
            }
        });
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, final Activity activity2, int i) {
        final DatePickerDialog datePickerDialog;
        this.activity = activity2;
        this.userProfileItem = userProfileElement;
        this.date.setBaseColor(i);
        this.date.setPrimaryColor(i);
        this.date.setUnderlineColor(i);
        this.date.setFloatingLabelTextColor(i);
        this.date.setMetTextColor(i);
        this.date.setMetHintTextColor(i);
        EditTextHelper.setCursorColor(this.date, i);
        this.date.setContentDescription(localizationHelper.getLocalizedTitle(userProfileElement.title));
        this.date.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_date_range_black_24dp, 0);
        Calendar instance = Calendar.getInstance();
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.date.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(userProfileElement.title), (float) (point.x - TextHelper.convertDpToPixels(64.0f, activity2)), paint);
        MaterialEditText materialEditText = this.date;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.date.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.date.setLines(this.lineCount);
        C44024 r6 = new OnDateSetListener() {
            public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {
                Calendar instance = Calendar.getInstance();
                instance.set(i, i2, i3);
                DateViewHolder.this.date.setText(DateViewHolder.this.dateFormatter.format(instance.getTime()));
                DateViewHolder.this.date.setLines(1);
            }
        };
        if (userProfileElement.type.equalsIgnoreCase("birthday")) {
            datePickerDialog = DatePickerDialog.newInstance(r6, instance.get(1) - 18, instance.get(2), instance.get(5));
            instance.add(2, -1);
            datePickerDialog.setMaxDate(instance);
        } else {
            datePickerDialog = DatePickerDialog.newInstance(r6, instance.get(1), instance.get(2), instance.get(5));
        }
        datePickerDialog.setAccentColor(i);
        this.date.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                datePickerDialog.show(activity2.getFragmentManager(), "datePicker");
            }
        });
        this.date.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    datePickerDialog.show(activity2.getFragmentManager(), "datePicker");
                }
            }
        });
    }

    public String getValue() {
        return this.date.getText().toString();
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
            if (!tableItemsModel2.getMandatory().equalsIgnoreCase("YES") || !this.date.getText().toString().equalsIgnoreCase(str)) {
                return true;
            }
            this.date.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.date.setError(this.activity.getString(R.string.select_a_date));
            return false;
        } else if (!this.userProfileItem.mandotory || !this.date.getText().toString().equalsIgnoreCase(str)) {
            return true;
        } else {
            this.date.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.date.setError(this.activity.getString(R.string.select_a_date));
            return false;
        }
    }

    public void clear() {
        this.date.setText("");
        this.date.setLines(this.lineCount);
    }

    public void setValue(String str) {
        this.date.setText(str);
        this.date.setLines(1);
    }
}
