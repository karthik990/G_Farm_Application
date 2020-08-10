package com.afollestad.materialdialogs.color;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.commons.C0841R;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class ColorChooserDialog extends DialogFragment implements OnClickListener, OnLongClickListener {
    public static final String TAG_ACCENT = "[MD_COLOR_CHOOSER]";
    public static final String TAG_CUSTOM = "[MD_COLOR_CHOOSER]";
    public static final String TAG_PRIMARY = "[MD_COLOR_CHOOSER]";
    /* access modifiers changed from: private */
    public ColorCallback callback;
    /* access modifiers changed from: private */
    public int circleSize;
    private View colorChooserCustomFrame;
    /* access modifiers changed from: private */
    public int[][] colorsSub;
    /* access modifiers changed from: private */
    public int[] colorsTop;
    /* access modifiers changed from: private */
    public EditText customColorHex;
    /* access modifiers changed from: private */
    public View customColorIndicator;
    private OnSeekBarChangeListener customColorRgbListener;
    private TextWatcher customColorTextWatcher;
    /* access modifiers changed from: private */
    public SeekBar customSeekA;
    /* access modifiers changed from: private */
    public TextView customSeekAValue;
    /* access modifiers changed from: private */
    public SeekBar customSeekB;
    /* access modifiers changed from: private */
    public TextView customSeekBValue;
    /* access modifiers changed from: private */
    public SeekBar customSeekG;
    /* access modifiers changed from: private */
    public TextView customSeekGValue;
    /* access modifiers changed from: private */
    public SeekBar customSeekR;
    /* access modifiers changed from: private */
    public TextView customSeekRValue;
    private GridView grid;
    /* access modifiers changed from: private */
    public int selectedCustomColor;

    public static class Builder implements Serializable {
        boolean accentMode = false;
        boolean allowUserCustom = true;
        boolean allowUserCustomAlpha = true;
        int backBtn = C0841R.C0845string.md_back_label;
        int cancelBtn = C0841R.C0845string.md_cancel_label;
        int[][] colorsSub;
        int[] colorsTop;
        final transient AppCompatActivity context;
        int customBtn = C0841R.C0845string.md_custom_label;
        int doneBtn = C0841R.C0845string.md_done_label;
        boolean dynamicButtonColor = true;
        int preselectColor;
        int presetsBtn = C0841R.C0845string.md_presets_label;
        boolean setPreselectionColor = false;
        String tag;
        Theme theme;
        final int title;
        int titleSub;

        public <ActivityType extends AppCompatActivity & ColorCallback> Builder(ActivityType activitytype, int i) {
            this.context = activitytype;
            this.title = i;
        }

        public Builder titleSub(int i) {
            this.titleSub = i;
            return this;
        }

        public Builder tag(String str) {
            this.tag = str;
            return this;
        }

        public Builder theme(Theme theme2) {
            this.theme = theme2;
            return this;
        }

        public Builder preselect(int i) {
            this.preselectColor = i;
            this.setPreselectionColor = true;
            return this;
        }

        public Builder accentMode(boolean z) {
            this.accentMode = z;
            return this;
        }

        public Builder doneButton(int i) {
            this.doneBtn = i;
            return this;
        }

        public Builder backButton(int i) {
            this.backBtn = i;
            return this;
        }

        public Builder cancelButton(int i) {
            this.cancelBtn = i;
            return this;
        }

        public Builder customButton(int i) {
            this.customBtn = i;
            return this;
        }

        public Builder presetsButton(int i) {
            this.presetsBtn = i;
            return this;
        }

        public Builder dynamicButtonColor(boolean z) {
            this.dynamicButtonColor = z;
            return this;
        }

        public Builder customColors(int[] iArr, int[][] iArr2) {
            this.colorsTop = iArr;
            this.colorsSub = iArr2;
            return this;
        }

        public Builder customColors(int i, int[][] iArr) {
            this.colorsTop = DialogUtils.getColorArray(this.context, i);
            this.colorsSub = iArr;
            return this;
        }

        public Builder allowUserColorInput(boolean z) {
            this.allowUserCustom = z;
            return this;
        }

        public Builder allowUserColorInputAlpha(boolean z) {
            this.allowUserCustomAlpha = z;
            return this;
        }

        public ColorChooserDialog build() {
            ColorChooserDialog colorChooserDialog = new ColorChooserDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("builder", this);
            colorChooserDialog.setArguments(bundle);
            return colorChooserDialog;
        }

        public ColorChooserDialog show() {
            ColorChooserDialog build = build();
            build.show(this.context);
            return build;
        }
    }

    public interface ColorCallback {
        void onColorChooserDismissed(ColorChooserDialog colorChooserDialog);

        void onColorSelection(ColorChooserDialog colorChooserDialog, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorChooserTag {
    }

    private class ColorGridAdapter extends BaseAdapter {
        public long getItemId(int i) {
            return (long) i;
        }

        ColorGridAdapter() {
        }

        public int getCount() {
            if (ColorChooserDialog.this.isInSub()) {
                return ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()].length;
            }
            return ColorChooserDialog.this.colorsTop.length;
        }

        public Object getItem(int i) {
            if (ColorChooserDialog.this.isInSub()) {
                return Integer.valueOf(ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()][i]);
            }
            return Integer.valueOf(ColorChooserDialog.this.colorsTop[i]);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = new CircleView(ColorChooserDialog.this.getContext());
                view.setLayoutParams(new LayoutParams(ColorChooserDialog.this.circleSize, ColorChooserDialog.this.circleSize));
            }
            CircleView circleView = (CircleView) view;
            int i2 = ColorChooserDialog.this.isInSub() ? ColorChooserDialog.this.colorsSub[ColorChooserDialog.this.topIndex()][i] : ColorChooserDialog.this.colorsTop[i];
            circleView.setBackgroundColor(i2);
            if (ColorChooserDialog.this.isInSub()) {
                circleView.setSelected(ColorChooserDialog.this.subIndex() == i);
            } else {
                circleView.setSelected(ColorChooserDialog.this.topIndex() == i);
            }
            circleView.setTag(String.format("%d:%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
            circleView.setOnClickListener(ColorChooserDialog.this);
            circleView.setOnLongClickListener(ColorChooserDialog.this);
            return view;
        }
    }

    public static ColorChooserDialog findVisible(AppCompatActivity appCompatActivity, String str) {
        Fragment findFragmentByTag = appCompatActivity.getSupportFragmentManager().findFragmentByTag(str);
        if (findFragmentByTag == null || !(findFragmentByTag instanceof ColorChooserDialog)) {
            return null;
        }
        return (ColorChooserDialog) findFragmentByTag;
    }

    private void generateColors() {
        Builder builder = getBuilder();
        if (builder.colorsTop != null) {
            this.colorsTop = builder.colorsTop;
            this.colorsSub = builder.colorsSub;
            return;
        }
        if (builder.accentMode) {
            this.colorsTop = ColorPalette.ACCENT_COLORS;
            this.colorsSub = ColorPalette.ACCENT_COLORS_SUB;
        } else {
            this.colorsTop = ColorPalette.PRIMARY_COLORS;
            this.colorsSub = ColorPalette.PRIMARY_COLORS_SUB;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("top_index", topIndex());
        bundle.putBoolean("in_sub", isInSub());
        bundle.putInt("sub_index", subIndex());
        View view = this.colorChooserCustomFrame;
        bundle.putBoolean("in_custom", view != null && view.getVisibility() == 0);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ColorCallback) {
            this.callback = (ColorCallback) activity;
            return;
        }
        throw new IllegalStateException("ColorChooserDialog needs to be shown from an Activity implementing ColorCallback.");
    }

    /* access modifiers changed from: private */
    public boolean isInSub() {
        return getArguments().getBoolean("in_sub", false);
    }

    /* access modifiers changed from: private */
    public void isInSub(boolean z) {
        getArguments().putBoolean("in_sub", z);
    }

    /* access modifiers changed from: private */
    public int topIndex() {
        return getArguments().getInt("top_index", -1);
    }

    /* access modifiers changed from: private */
    public void topIndex(int i) {
        if (i > -1) {
            findSubIndexForColor(i, this.colorsTop[i]);
        }
        getArguments().putInt("top_index", i);
    }

    /* access modifiers changed from: private */
    public int subIndex() {
        if (this.colorsSub == null) {
            return -1;
        }
        return getArguments().getInt("sub_index", -1);
    }

    /* access modifiers changed from: private */
    public void subIndex(int i) {
        if (this.colorsSub != null) {
            getArguments().putInt("sub_index", i);
        }
    }

    public int getTitle() {
        int i;
        Builder builder = getBuilder();
        if (isInSub()) {
            i = builder.titleSub;
        } else {
            i = builder.title;
        }
        return i == 0 ? builder.title : i;
    }

    public String tag() {
        Builder builder = getBuilder();
        if (builder.tag != null) {
            return builder.tag;
        }
        return super.getTag();
    }

    public boolean isAccentMode() {
        return getBuilder().accentMode;
    }

    public void onClick(View view) {
        if (view.getTag() != null) {
            int parseInt = Integer.parseInt(((String) view.getTag()).split(":")[0]);
            MaterialDialog materialDialog = (MaterialDialog) getDialog();
            Builder builder = getBuilder();
            if (isInSub()) {
                subIndex(parseInt);
            } else {
                topIndex(parseInt);
                int[][] iArr = this.colorsSub;
                if (iArr != null && parseInt < iArr.length) {
                    materialDialog.setActionButton(DialogAction.NEGATIVE, builder.backBtn);
                    isInSub(true);
                }
            }
            if (builder.allowUserCustom) {
                this.selectedCustomColor = getSelectedColor();
            }
            invalidateDynamicButtonColors();
            invalidate();
        }
    }

    public boolean onLongClick(View view) {
        if (view.getTag() == null) {
            return false;
        }
        ((CircleView) view).showHint(Integer.parseInt(((String) view.getTag()).split(":")[1]));
        return true;
    }

    /* access modifiers changed from: private */
    public void invalidateDynamicButtonColors() {
        MaterialDialog materialDialog = (MaterialDialog) getDialog();
        if (materialDialog != null && getBuilder().dynamicButtonColor) {
            int selectedColor = getSelectedColor();
            if (Color.alpha(selectedColor) < 64 || (Color.red(selectedColor) > 247 && Color.green(selectedColor) > 247 && Color.blue(selectedColor) > 247)) {
                selectedColor = Color.parseColor("#DEDEDE");
            }
            if (getBuilder().dynamicButtonColor) {
                materialDialog.getActionButton(DialogAction.POSITIVE).setTextColor(selectedColor);
                materialDialog.getActionButton(DialogAction.NEGATIVE).setTextColor(selectedColor);
                materialDialog.getActionButton(DialogAction.NEUTRAL).setTextColor(selectedColor);
            }
            if (this.customSeekR != null) {
                if (this.customSeekA.getVisibility() == 0) {
                    MDTintHelper.setTint(this.customSeekA, selectedColor);
                }
                MDTintHelper.setTint(this.customSeekR, selectedColor);
                MDTintHelper.setTint(this.customSeekG, selectedColor);
                MDTintHelper.setTint(this.customSeekB, selectedColor);
            }
        }
    }

    /* access modifiers changed from: private */
    public int getSelectedColor() {
        View view = this.colorChooserCustomFrame;
        if (view != null && view.getVisibility() == 0) {
            return this.selectedCustomColor;
        }
        int i = 0;
        int i2 = subIndex() > -1 ? this.colorsSub[topIndex()][subIndex()] : topIndex() > -1 ? this.colorsTop[topIndex()] : 0;
        if (i2 == 0) {
            if (VERSION.SDK_INT >= 21) {
                i = DialogUtils.resolveColor(getActivity(), 16843829);
            }
            i2 = DialogUtils.resolveColor(getActivity(), C0841R.attr.colorAccent, i);
        }
        return i2;
    }

    private void findSubIndexForColor(int i, int i2) {
        int[][] iArr = this.colorsSub;
        if (iArr != null && iArr.length - 1 >= i) {
            int[] iArr2 = iArr[i];
            for (int i3 = 0; i3 < iArr2.length; i3++) {
                if (iArr2[i3] == i2) {
                    subIndex(i3);
                    return;
                }
            }
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        int i;
        int i2;
        if (getArguments() == null || !getArguments().containsKey("builder")) {
            throw new IllegalStateException("ColorChooserDialog should be created using its Builder interface.");
        }
        generateColors();
        if (bundle != null) {
            i = !bundle.getBoolean("in_custom", false);
            i2 = getSelectedColor();
        } else if (getBuilder().setPreselectionColor) {
            i2 = getBuilder().preselectColor;
            i = 0;
            if (i2 != 0) {
                int i3 = 0;
                while (true) {
                    int[] iArr = this.colorsTop;
                    if (i >= iArr.length) {
                        break;
                    } else if (iArr[i] == i2) {
                        topIndex(i);
                        if (getBuilder().accentMode) {
                            subIndex(2);
                        } else if (this.colorsSub != null) {
                            findSubIndexForColor(i, i2);
                        } else {
                            subIndex(5);
                        }
                        i3 = 1;
                    } else {
                        if (this.colorsSub != null) {
                            int i4 = 0;
                            while (true) {
                                int[][] iArr2 = this.colorsSub;
                                if (i4 >= iArr2[i].length) {
                                    break;
                                } else if (iArr2[i][i4] == i2) {
                                    topIndex(i);
                                    subIndex(i4);
                                    i3 = 1;
                                    break;
                                } else {
                                    i4++;
                                }
                            }
                            if (i3 != 0) {
                                break;
                            }
                        }
                        i++;
                    }
                }
                i = i3;
            }
        } else {
            i2 = ViewCompat.MEASURED_STATE_MASK;
            i = 1;
        }
        this.circleSize = getResources().getDimensionPixelSize(C0841R.dimen.md_colorchooser_circlesize);
        Builder builder = getBuilder();
        com.afollestad.materialdialogs.MaterialDialog.Builder showListener = new com.afollestad.materialdialogs.MaterialDialog.Builder(getActivity()).title(getTitle()).autoDismiss(false).customView(C0841R.layout.md_dialog_colorchooser, false).negativeText(builder.cancelBtn).positiveText(builder.doneBtn).neutralText(builder.allowUserCustom ? builder.customBtn : 0).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ColorCallback access$800 = ColorChooserDialog.this.callback;
                ColorChooserDialog colorChooserDialog = ColorChooserDialog.this;
                access$800.onColorSelection(colorChooserDialog, colorChooserDialog.getSelectedColor());
                ColorChooserDialog.this.dismiss();
            }
        }).onNegative(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                if (ColorChooserDialog.this.isInSub()) {
                    materialDialog.setActionButton(DialogAction.NEGATIVE, ColorChooserDialog.this.getBuilder().cancelBtn);
                    ColorChooserDialog.this.isInSub(false);
                    ColorChooserDialog.this.subIndex(-1);
                    ColorChooserDialog.this.invalidate();
                    return;
                }
                materialDialog.cancel();
            }
        }).onNeutral(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ColorChooserDialog.this.toggleCustom(materialDialog);
            }
        }).showListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                ColorChooserDialog.this.invalidateDynamicButtonColors();
            }
        });
        if (builder.theme != null) {
            showListener.theme(builder.theme);
        }
        MaterialDialog build = showListener.build();
        View customView = build.getCustomView();
        this.grid = (GridView) customView.findViewById(C0841R.C0844id.md_grid);
        if (builder.allowUserCustom) {
            this.selectedCustomColor = i2;
            this.colorChooserCustomFrame = customView.findViewById(C0841R.C0844id.md_colorChooserCustomFrame);
            this.customColorHex = (EditText) customView.findViewById(C0841R.C0844id.md_hexInput);
            this.customColorIndicator = customView.findViewById(C0841R.C0844id.md_colorIndicator);
            this.customSeekA = (SeekBar) customView.findViewById(C0841R.C0844id.md_colorA);
            this.customSeekAValue = (TextView) customView.findViewById(C0841R.C0844id.md_colorAValue);
            this.customSeekR = (SeekBar) customView.findViewById(C0841R.C0844id.md_colorR);
            this.customSeekRValue = (TextView) customView.findViewById(C0841R.C0844id.md_colorRValue);
            this.customSeekG = (SeekBar) customView.findViewById(C0841R.C0844id.md_colorG);
            this.customSeekGValue = (TextView) customView.findViewById(C0841R.C0844id.md_colorGValue);
            this.customSeekB = (SeekBar) customView.findViewById(C0841R.C0844id.md_colorB);
            this.customSeekBValue = (TextView) customView.findViewById(C0841R.C0844id.md_colorBValue);
            if (!builder.allowUserCustomAlpha) {
                customView.findViewById(C0841R.C0844id.md_colorALabel).setVisibility(8);
                this.customSeekA.setVisibility(8);
                this.customSeekAValue.setVisibility(8);
                this.customColorHex.setHint("2196F3");
                this.customColorHex.setFilters(new InputFilter[]{new LengthFilter(6)});
            } else {
                this.customColorHex.setHint("FF2196F3");
                this.customColorHex.setFilters(new InputFilter[]{new LengthFilter(8)});
            }
            if (i == 0) {
                toggleCustom(build);
            }
        }
        invalidate();
        return build;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        ColorCallback colorCallback = this.callback;
        if (colorCallback != null) {
            colorCallback.onColorChooserDismissed(this);
        }
    }

    /* access modifiers changed from: private */
    public void toggleCustom(MaterialDialog materialDialog) {
        if (materialDialog == null) {
            materialDialog = (MaterialDialog) getDialog();
        }
        if (this.grid.getVisibility() == 0) {
            materialDialog.setTitle(getBuilder().customBtn);
            materialDialog.setActionButton(DialogAction.NEUTRAL, getBuilder().presetsBtn);
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().cancelBtn);
            this.grid.setVisibility(4);
            this.colorChooserCustomFrame.setVisibility(0);
            this.customColorTextWatcher = new TextWatcher() {
                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    try {
                        ColorChooserDialog colorChooserDialog = ColorChooserDialog.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("#");
                        sb.append(charSequence.toString());
                        colorChooserDialog.selectedCustomColor = Color.parseColor(sb.toString());
                    } catch (IllegalArgumentException unused) {
                        ColorChooserDialog.this.selectedCustomColor = ViewCompat.MEASURED_STATE_MASK;
                    }
                    ColorChooserDialog.this.customColorIndicator.setBackgroundColor(ColorChooserDialog.this.selectedCustomColor);
                    if (ColorChooserDialog.this.customSeekA.getVisibility() == 0) {
                        int alpha = Color.alpha(ColorChooserDialog.this.selectedCustomColor);
                        ColorChooserDialog.this.customSeekA.setProgress(alpha);
                        ColorChooserDialog.this.customSeekAValue.setText(String.format(Locale.US, "%d", new Object[]{Integer.valueOf(alpha)}));
                    }
                    if (ColorChooserDialog.this.customSeekA.getVisibility() == 0) {
                        ColorChooserDialog.this.customSeekA.setProgress(Color.alpha(ColorChooserDialog.this.selectedCustomColor));
                    }
                    ColorChooserDialog.this.customSeekR.setProgress(Color.red(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.customSeekG.setProgress(Color.green(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.customSeekB.setProgress(Color.blue(ColorChooserDialog.this.selectedCustomColor));
                    ColorChooserDialog.this.isInSub(false);
                    ColorChooserDialog.this.topIndex(-1);
                    ColorChooserDialog.this.subIndex(-1);
                    ColorChooserDialog.this.invalidateDynamicButtonColors();
                }
            };
            this.customColorHex.addTextChangedListener(this.customColorTextWatcher);
            this.customColorRgbListener = new OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (z) {
                        if (ColorChooserDialog.this.getBuilder().allowUserCustomAlpha) {
                            int argb = Color.argb(ColorChooserDialog.this.customSeekA.getProgress(), ColorChooserDialog.this.customSeekR.getProgress(), ColorChooserDialog.this.customSeekG.getProgress(), ColorChooserDialog.this.customSeekB.getProgress());
                            ColorChooserDialog.this.customColorHex.setText(String.format("%08X", new Object[]{Integer.valueOf(argb)}));
                        } else {
                            int rgb = Color.rgb(ColorChooserDialog.this.customSeekR.getProgress(), ColorChooserDialog.this.customSeekG.getProgress(), ColorChooserDialog.this.customSeekB.getProgress());
                            ColorChooserDialog.this.customColorHex.setText(String.format("%06X", new Object[]{Integer.valueOf(rgb & ViewCompat.MEASURED_SIZE_MASK)}));
                        }
                    }
                    String str = "%d";
                    ColorChooserDialog.this.customSeekAValue.setText(String.format(str, new Object[]{Integer.valueOf(ColorChooserDialog.this.customSeekA.getProgress())}));
                    ColorChooserDialog.this.customSeekRValue.setText(String.format(str, new Object[]{Integer.valueOf(ColorChooserDialog.this.customSeekR.getProgress())}));
                    ColorChooserDialog.this.customSeekGValue.setText(String.format(str, new Object[]{Integer.valueOf(ColorChooserDialog.this.customSeekG.getProgress())}));
                    ColorChooserDialog.this.customSeekBValue.setText(String.format(str, new Object[]{Integer.valueOf(ColorChooserDialog.this.customSeekB.getProgress())}));
                }
            };
            this.customSeekR.setOnSeekBarChangeListener(this.customColorRgbListener);
            this.customSeekG.setOnSeekBarChangeListener(this.customColorRgbListener);
            this.customSeekB.setOnSeekBarChangeListener(this.customColorRgbListener);
            if (this.customSeekA.getVisibility() == 0) {
                this.customSeekA.setOnSeekBarChangeListener(this.customColorRgbListener);
                this.customColorHex.setText(String.format("%08X", new Object[]{Integer.valueOf(this.selectedCustomColor)}));
                return;
            }
            this.customColorHex.setText(String.format("%06X", new Object[]{Integer.valueOf(16777215 & this.selectedCustomColor)}));
            return;
        }
        materialDialog.setTitle(getBuilder().title);
        materialDialog.setActionButton(DialogAction.NEUTRAL, getBuilder().customBtn);
        if (isInSub()) {
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().backBtn);
        } else {
            materialDialog.setActionButton(DialogAction.NEGATIVE, getBuilder().cancelBtn);
        }
        this.grid.setVisibility(0);
        this.colorChooserCustomFrame.setVisibility(8);
        this.customColorHex.removeTextChangedListener(this.customColorTextWatcher);
        this.customColorTextWatcher = null;
        this.customSeekR.setOnSeekBarChangeListener(null);
        this.customSeekG.setOnSeekBarChangeListener(null);
        this.customSeekB.setOnSeekBarChangeListener(null);
        this.customColorRgbListener = null;
    }

    /* access modifiers changed from: private */
    public void invalidate() {
        if (this.grid.getAdapter() == null) {
            this.grid.setAdapter(new ColorGridAdapter());
            this.grid.setSelector(ResourcesCompat.getDrawable(getResources(), C0841R.C0843drawable.md_transparent, null));
        } else {
            ((BaseAdapter) this.grid.getAdapter()).notifyDataSetChanged();
        }
        if (getDialog() != null) {
            getDialog().setTitle(getTitle());
        }
    }

    /* access modifiers changed from: private */
    public Builder getBuilder() {
        if (getArguments() != null) {
            String str = "builder";
            if (getArguments().containsKey(str)) {
                return (Builder) getArguments().getSerializable(str);
            }
        }
        return null;
    }

    private void dismissIfNecessary(AppCompatActivity appCompatActivity, String str) {
        Fragment findFragmentByTag = appCompatActivity.getSupportFragmentManager().findFragmentByTag(str);
        if (findFragmentByTag != null) {
            ((DialogFragment) findFragmentByTag).dismiss();
            appCompatActivity.getSupportFragmentManager().beginTransaction().remove(findFragmentByTag).commit();
        }
    }

    public ColorChooserDialog show(AppCompatActivity appCompatActivity) {
        Builder builder = getBuilder();
        if (builder.colorsTop == null) {
            boolean z = builder.accentMode;
        }
        String str = "[MD_COLOR_CHOOSER]";
        dismissIfNecessary(appCompatActivity, str);
        show(appCompatActivity.getSupportFragmentManager(), str);
        return this;
    }
}
