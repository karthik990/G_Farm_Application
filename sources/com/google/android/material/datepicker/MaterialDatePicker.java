package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.C2556R;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialDatePicker<S> extends DialogFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
    static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    public static final int INPUT_MODE_CALENDAR = 0;
    private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
    public static final int INPUT_MODE_TEXT = 1;
    private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
    private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
    private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";
    static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
    private MaterialShapeDrawable background;
    private MaterialCalendar<S> calendar;
    private CalendarConstraints calendarConstraints;
    /* access modifiers changed from: private */
    public Button confirmButton;
    /* access modifiers changed from: private */
    public DateSelector<S> dateSelector;
    private boolean fullscreen;
    private TextView headerSelectionText;
    /* access modifiers changed from: private */
    public CheckableImageButton headerToggleButton;
    private int inputMode;
    private final LinkedHashSet<OnCancelListener> onCancelListeners = new LinkedHashSet<>();
    private final LinkedHashSet<OnDismissListener> onDismissListeners = new LinkedHashSet<>();
    /* access modifiers changed from: private */
    public final LinkedHashSet<OnClickListener> onNegativeButtonClickListeners = new LinkedHashSet<>();
    /* access modifiers changed from: private */
    public final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> onPositiveButtonClickListeners = new LinkedHashSet<>();
    private int overrideThemeResId;
    private PickerFragment<S> pickerFragment;
    private CharSequence titleText;
    private int titleTextResId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputMode {
    }

    public static final class Builder<S> {
        CalendarConstraints calendarConstraints;
        final DateSelector<S> dateSelector;
        int inputMode = 0;
        int overrideThemeResId = 0;
        S selection = null;
        CharSequence titleText = null;
        int titleTextResId = 0;

        private Builder(DateSelector<S> dateSelector2) {
            this.dateSelector = dateSelector2;
        }

        public static <S> Builder<S> customDatePicker(DateSelector<S> dateSelector2) {
            return new Builder<>(dateSelector2);
        }

        public static Builder<Long> datePicker() {
            return new Builder<>(new SingleDateSelector());
        }

        public static Builder<Pair<Long, Long>> dateRangePicker() {
            return new Builder<>(new RangeDateSelector());
        }

        public Builder<S> setSelection(S s) {
            this.selection = s;
            return this;
        }

        public Builder<S> setTheme(int i) {
            this.overrideThemeResId = i;
            return this;
        }

        public Builder<S> setCalendarConstraints(CalendarConstraints calendarConstraints2) {
            this.calendarConstraints = calendarConstraints2;
            return this;
        }

        public Builder<S> setTitleText(int i) {
            this.titleTextResId = i;
            this.titleText = null;
            return this;
        }

        public Builder<S> setTitleText(CharSequence charSequence) {
            this.titleText = charSequence;
            this.titleTextResId = 0;
            return this;
        }

        public Builder<S> setInputMode(int i) {
            this.inputMode = i;
            return this;
        }

        public MaterialDatePicker<S> build() {
            if (this.calendarConstraints == null) {
                this.calendarConstraints = new com.google.android.material.datepicker.CalendarConstraints.Builder().build();
            }
            if (this.titleTextResId == 0) {
                this.titleTextResId = this.dateSelector.getDefaultTitleResId();
            }
            S s = this.selection;
            if (s != null) {
                this.dateSelector.setSelection(s);
            }
            return MaterialDatePicker.newInstance(this);
        }
    }

    public static long todayInUtcMilliseconds() {
        return UtcDates.getTodayCalendar().getTimeInMillis();
    }

    public static long thisMonthInUtcMilliseconds() {
        return Month.today().timeInMillis;
    }

    public String getHeaderText() {
        return this.dateSelector.getSelectionDisplayString(getContext());
    }

    static <S> MaterialDatePicker<S> newInstance(Builder<S> builder) {
        MaterialDatePicker<S> materialDatePicker = new MaterialDatePicker<>();
        Bundle bundle = new Bundle();
        bundle.putInt(OVERRIDE_THEME_RES_ID, builder.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, builder.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, builder.calendarConstraints);
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, builder.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, builder.titleText);
        bundle.putInt(INPUT_MODE_KEY, builder.inputMode);
        materialDatePicker.setArguments(bundle);
        return materialDatePicker;
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(OVERRIDE_THEME_RES_ID, this.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, this.dateSelector);
        com.google.android.material.datepicker.CalendarConstraints.Builder builder = new com.google.android.material.datepicker.CalendarConstraints.Builder(this.calendarConstraints);
        if (this.calendar.getCurrentMonth() != null) {
            builder.setOpenAt(this.calendar.getCurrentMonth().timeInMillis);
        }
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, builder.build());
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, this.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, this.titleText);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.overrideThemeResId = bundle.getInt(OVERRIDE_THEME_RES_ID);
        this.dateSelector = (DateSelector) bundle.getParcelable(DATE_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.titleTextResId = bundle.getInt(TITLE_TEXT_RES_ID_KEY);
        this.titleText = bundle.getCharSequence(TITLE_TEXT_KEY);
        this.inputMode = bundle.getInt(INPUT_MODE_KEY);
    }

    private int getThemeResId(Context context) {
        int i = this.overrideThemeResId;
        if (i != 0) {
            return i;
        }
        return this.dateSelector.getDefaultThemeResId(context);
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId(requireContext()));
        Context context = dialog.getContext();
        this.fullscreen = isFullscreen(context);
        int resolveOrThrow = MaterialAttributes.resolveOrThrow(context, C2556R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
        this.background = new MaterialShapeDrawable(context, null, C2556R.attr.materialCalendarStyle, C2556R.C2562style.Widget_MaterialComponents_MaterialCalendar);
        this.background.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(resolveOrThrow));
        this.background.setElevation(ViewCompat.getElevation(dialog.getWindow().getDecorView()));
        return dialog;
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(this.fullscreen ? C2556R.layout.mtrl_picker_fullscreen : C2556R.layout.mtrl_picker_dialog, viewGroup);
        Context context = inflate.getContext();
        if (this.fullscreen) {
            inflate.findViewById(C2556R.C2559id.mtrl_calendar_frame).setLayoutParams(new LayoutParams(getPaddedPickerWidth(context), -2));
        } else {
            View findViewById = inflate.findViewById(C2556R.C2559id.mtrl_calendar_main_pane);
            View findViewById2 = inflate.findViewById(C2556R.C2559id.mtrl_calendar_frame);
            findViewById.setLayoutParams(new LayoutParams(getPaddedPickerWidth(context), -1));
            findViewById2.setMinimumHeight(getDialogPickerHeight(requireContext()));
        }
        this.headerSelectionText = (TextView) inflate.findViewById(C2556R.C2559id.mtrl_picker_header_selection_text);
        ViewCompat.setAccessibilityLiveRegion(this.headerSelectionText, 1);
        this.headerToggleButton = (CheckableImageButton) inflate.findViewById(C2556R.C2559id.mtrl_picker_header_toggle);
        TextView textView = (TextView) inflate.findViewById(C2556R.C2559id.mtrl_picker_title_text);
        CharSequence charSequence = this.titleText;
        if (charSequence != null) {
            textView.setText(charSequence);
        } else {
            textView.setText(this.titleTextResId);
        }
        initHeaderToggle(context);
        this.confirmButton = (Button) inflate.findViewById(C2556R.C2559id.confirm_button);
        if (this.dateSelector.isSelectionComplete()) {
            this.confirmButton.setEnabled(true);
        } else {
            this.confirmButton.setEnabled(false);
        }
        this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
        this.confirmButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.onPositiveButtonClickListeners.iterator();
                while (it.hasNext()) {
                    ((MaterialPickerOnPositiveButtonClickListener) it.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        Button button = (Button) inflate.findViewById(C2556R.C2559id.cancel_button);
        button.setTag(CANCEL_BUTTON_TAG);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.onNegativeButtonClickListeners.iterator();
                while (it.hasNext()) {
                    ((OnClickListener) it.next()).onClick(view);
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        return inflate;
    }

    public void onStart() {
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            InsetDrawable insetDrawable = new InsetDrawable(this.background, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(insetDrawable);
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        startPickerFragment();
    }

    public void onStop() {
        this.pickerFragment.clearOnSelectionChangedListeners();
        super.onStop();
    }

    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            ((OnCancelListener) it.next()).onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            ((OnDismissListener) it.next()).onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    public final S getSelection() {
        return this.dateSelector.getSelection();
    }

    /* access modifiers changed from: private */
    public void updateHeader() {
        String headerText = getHeaderText();
        this.headerSelectionText.setContentDescription(String.format(getString(C2556R.C2561string.mtrl_picker_announce_current_selection), new Object[]{headerText}));
        this.headerSelectionText.setText(headerText);
    }

    /* access modifiers changed from: private */
    public void startPickerFragment() {
        this.calendar = MaterialCalendar.newInstance(this.dateSelector, getThemeResId(requireContext()), this.calendarConstraints);
        this.pickerFragment = this.headerToggleButton.isChecked() ? MaterialTextInputPicker.newInstance(this.dateSelector, this.calendarConstraints) : this.calendar;
        updateHeader();
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(C2556R.C2559id.mtrl_calendar_frame, this.pickerFragment);
        beginTransaction.commitNow();
        this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener<S>() {
            public void onSelectionChanged(S s) {
                MaterialDatePicker.this.updateHeader();
                if (MaterialDatePicker.this.dateSelector.isSelectionComplete()) {
                    MaterialDatePicker.this.confirmButton.setEnabled(true);
                } else {
                    MaterialDatePicker.this.confirmButton.setEnabled(false);
                }
            }
        });
    }

    private void initHeaderToggle(Context context) {
        this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
        this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(context));
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, null);
        updateToggleContentDescription(this.headerToggleButton);
        this.headerToggleButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MaterialDatePicker.this.headerToggleButton.toggle();
                MaterialDatePicker materialDatePicker = MaterialDatePicker.this;
                materialDatePicker.updateToggleContentDescription(materialDatePicker.headerToggleButton);
                MaterialDatePicker.this.startPickerFragment();
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateToggleContentDescription(CheckableImageButton checkableImageButton) {
        String str;
        if (this.headerToggleButton.isChecked()) {
            str = checkableImageButton.getContext().getString(C2556R.C2561string.mtrl_picker_toggle_to_calendar_input_mode);
        } else {
            str = checkableImageButton.getContext().getString(C2556R.C2561string.mtrl_picker_toggle_to_text_input_mode);
        }
        this.headerToggleButton.setContentDescription(str);
    }

    private static Drawable createHeaderToggleDrawable(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842912}, AppCompatResources.getDrawable(context, C2556R.C2558drawable.material_ic_calendar_black_24dp));
        stateListDrawable.addState(new int[0], AppCompatResources.getDrawable(context, C2556R.C2558drawable.material_ic_edit_black_24dp));
        return stateListDrawable;
    }

    static boolean isFullscreen(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, C2556R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{16843277});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    private static int getDialogPickerHeight(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelSize = (MonthAdapter.MAXIMUM_WEEKS * resources.getDimensionPixelSize(C2556R.dimen.mtrl_calendar_day_height)) + ((MonthAdapter.MAXIMUM_WEEKS - 1) * resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_month_vertical_padding));
        return resources.getDimensionPixelSize(C2556R.dimen.mtrl_calendar_navigation_height) + resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_navigation_bottom_padding) + resources.getDimensionPixelSize(C2556R.dimen.mtrl_calendar_days_of_week_height) + dimensionPixelSize + resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_bottom_padding);
    }

    private static int getPaddedPickerWidth(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_content_padding);
        int i = Month.today().daysInWeek;
        return (dimensionPixelOffset * 2) + (resources.getDimensionPixelSize(C2556R.dimen.mtrl_calendar_day_width) * i) + ((i - 1) * resources.getDimensionPixelOffset(C2556R.dimen.mtrl_calendar_month_horizontal_padding));
    }

    public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.add(materialPickerOnPositiveButtonClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.remove(materialPickerOnPositiveButtonClickListener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.onPositiveButtonClickListeners.clear();
    }

    public boolean addOnNegativeButtonClickListener(OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.add(onClickListener);
    }

    public boolean removeOnNegativeButtonClickListener(OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.remove(onClickListener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.onNegativeButtonClickListeners.clear();
    }

    public boolean addOnCancelListener(OnCancelListener onCancelListener) {
        return this.onCancelListeners.add(onCancelListener);
    }

    public boolean removeOnCancelListener(OnCancelListener onCancelListener) {
        return this.onCancelListeners.remove(onCancelListener);
    }

    public void clearOnCancelListeners() {
        this.onCancelListeners.clear();
    }

    public boolean addOnDismissListener(OnDismissListener onDismissListener) {
        return this.onDismissListeners.add(onDismissListener);
    }

    public boolean removeOnDismissListener(OnDismissListener onDismissListener) {
        return this.onDismissListeners.remove(onDismissListener);
    }

    public void clearOnDismissListeners() {
        this.onDismissListeners.clear();
    }
}
