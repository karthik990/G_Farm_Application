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
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.braintreepayments.api.models.BinData;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.EditTextHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.TextHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.util.ColorUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.List;

public class SelectionViewHolder extends FormBaseViewHolder {
    private Activity activity;
    /* access modifiers changed from: private */
    public boolean isSelected = false;
    private int lineCount;
    private String selectedValue = "";
    @BindView(2131362445)
    MaterialEditText selection;
    private TableItemsModel tableItemsModel;
    private UserProfileElement userProfileItem;

    public byte[] getImage() {
        return null;
    }

    public boolean isImage() {
        return false;
    }

    public SelectionViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper, Activity activity2, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.activity = activity2;
        this.selection.setBaseColor(i);
        this.selection.setPrimaryColor(i);
        this.selection.setUnderlineColor(i);
        this.selection.setFloatingLabelTextColor(i);
        this.selection.setMetTextColor(i);
        this.selection.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        EditTextHelper.setCursorColor(this.selection, i);
        this.selection.setContentDescription(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()));
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.selection.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        float convertDpToPixels = (float) (point.x - TextHelper.convertDpToPixels(70.0f, activity2));
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(tableItemsModel2.getTitle()), convertDpToPixels, paint);
        MaterialEditText materialEditText = this.selection;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.selection.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.selection.setLines(this.lineCount);
        this.selection.setImeOptions(1073741824);
        this.selection.setCursorVisible(false);
        this.selection.setInputType(524288);
        this.selection.setInputType(131072);
        String[] split = localizationHelper.getLocalizedTitle(tableItemsModel2.getItems()).split(",");
        MaterialEditText materialEditText2 = this.selection;
        final Activity activity3 = activity2;
        final String[] strArr = split;
        final float f = convertDpToPixels;
        final Paint paint2 = paint;
        C44091 r1 = new OnClickListener() {
            public void onClick(View view) {
                new Builder(activity3).title(SelectionViewHolder.this.selection.getHint()).items((CharSequence[]) strArr).itemsCallback(new ListCallback() {
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        SelectionViewHolder.this.isSelected = true;
                        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(strArr[i], f, paint2);
                        SelectionViewHolder.this.selection.setLines(splitWordsIntoStringsThatFit.size());
                        SelectionViewHolder.this.selection.setText(TextUtils.join("\t", splitWordsIntoStringsThatFit));
                    }
                }).show();
            }
        };
        materialEditText2.setOnClickListener(r1);
        MaterialEditText materialEditText3 = this.selection;
        C44112 r12 = new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    new Builder(activity3).title(SelectionViewHolder.this.selection.getHint()).items((CharSequence[]) strArr).itemsCallback(new ListCallback() {
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            SelectionViewHolder.this.isSelected = true;
                            List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(strArr[i], f, paint2);
                            SelectionViewHolder.this.selection.setLines(splitWordsIntoStringsThatFit.size());
                            SelectionViewHolder.this.selection.setText(TextUtils.join("\t", splitWordsIntoStringsThatFit));
                        }
                    }).show();
                }
            }
        };
        materialEditText3.setOnFocusChangeListener(r12);
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper, Activity activity2, int i) {
        this.userProfileItem = userProfileElement;
        this.activity = activity2;
        this.selection.setBaseColor(i);
        this.selection.setPrimaryColor(i);
        this.selection.setUnderlineColor(i);
        this.selection.setFloatingLabelTextColor(i);
        this.selection.setMetTextColor(i);
        this.selection.setMetHintTextColor(ColorUtil.getLighterColor(i, 0.3f));
        EditTextHelper.setCursorColor(this.selection, i);
        this.selection.setContentDescription(localizationHelper.getLocalizedTitle(userProfileElement.title));
        Paint paint = new Paint();
        paint.setTextSize((float) TextHelper.convertSpToPixels(14.7f, activity2));
        paint.setTypeface(this.selection.getTypeface());
        Display defaultDisplay = activity2.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        float convertDpToPixels = (float) (point.x - TextHelper.convertDpToPixels(64.0f, activity2));
        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(localizationHelper.getLocalizedTitle(userProfileElement.title), convertDpToPixels, paint);
        MaterialEditText materialEditText = this.selection;
        String str = Constants.NEW_LINE;
        materialEditText.setHint(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.selection.setFloatingLabelText(TextUtils.join(str, splitWordsIntoStringsThatFit));
        this.selection.setImeOptions(1073741824);
        this.selection.setCursorVisible(false);
        this.selection.setInputType(524288);
        this.selection.setInputType(131072);
        this.lineCount = splitWordsIntoStringsThatFit.size();
        this.selection.setLines(this.lineCount);
        String[] split = localizationHelper.getLocalizedTitle(userProfileElement.selections).split(",");
        MaterialEditText materialEditText2 = this.selection;
        final Activity activity3 = activity2;
        final String[] strArr = split;
        final float f = convertDpToPixels;
        final Paint paint2 = paint;
        C44133 r1 = new OnClickListener() {
            public void onClick(View view) {
                new Builder(activity3).title(SelectionViewHolder.this.selection.getHint()).items((CharSequence[]) strArr).itemsCallback(new ListCallback() {
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        SelectionViewHolder.this.isSelected = true;
                        SelectionViewHolder.this.selection.setText(strArr[i]);
                        List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(strArr[i], f, paint2);
                        SelectionViewHolder.this.selection.setLines(splitWordsIntoStringsThatFit.size());
                        SelectionViewHolder.this.selection.setText(TextUtils.join(Constants.NEW_LINE, splitWordsIntoStringsThatFit));
                    }
                }).show();
            }
        };
        materialEditText2.setOnClickListener(r1);
        MaterialEditText materialEditText3 = this.selection;
        C44154 r12 = new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    new Builder(activity3).title(SelectionViewHolder.this.selection.getHint()).items((CharSequence[]) strArr).itemsCallback(new ListCallback() {
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            SelectionViewHolder.this.isSelected = true;
                            SelectionViewHolder.this.selection.setText(strArr[i]);
                            List splitWordsIntoStringsThatFit = TextHelper.splitWordsIntoStringsThatFit(strArr[i], f, paint2);
                            SelectionViewHolder.this.selection.setLines(splitWordsIntoStringsThatFit.size());
                            SelectionViewHolder.this.selection.setText(TextUtils.join(Constants.NEW_LINE, splitWordsIntoStringsThatFit));
                        }
                    }).show();
                }
            }
        };
        materialEditText3.setOnFocusChangeListener(r12);
    }

    public String getValue() {
        return this.selection.getText().toString();
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
            if (!tableItemsModel2.getMandatory().equalsIgnoreCase(BinData.YES) || this.isSelected) {
                return true;
            }
            this.selection.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.selection.setError(this.activity.getString(R.string.make_a_choice));
            return false;
        } else if (!this.userProfileItem.mandotory || this.isSelected) {
            return true;
        } else {
            this.selection.setErrorColor(SupportMenu.CATEGORY_MASK);
            this.selection.setError(this.activity.getString(R.string.make_a_choice));
            return false;
        }
        return true;
    }

    public void clear() {
        this.selection.setText("");
        this.selection.setLines(this.lineCount);
        this.isSelected = false;
    }

    public void setValue(String str) {
        this.selection.setText(str);
        this.isSelected = true;
    }
}
