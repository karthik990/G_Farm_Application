package com.wdullaer.materialdatetimepicker.date;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.C5272Utils;
import com.wdullaer.materialdatetimepicker.HapticFeedbackController;
import com.wdullaer.materialdatetimepicker.TypefaceHelper;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter.CalendarDay;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class DatePickerDialog extends DialogFragment implements OnClickListener, DatePickerController {
    private static final int ANIMATION_DELAY = 500;
    private static final int ANIMATION_DURATION = 300;
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", Locale.getDefault());
    private static final String KEY_ACCENT = "accent";
    private static final String KEY_AUTO_DISMISS = "auto_dismiss";
    private static final String KEY_CANCEL_COLOR = "cancel_color";
    private static final String KEY_CANCEL_RESID = "cancel_resid";
    private static final String KEY_CANCEL_STRING = "cancel_string";
    private static final String KEY_CURRENT_VIEW = "current_view";
    private static final String KEY_DATERANGELIMITER = "daterangelimiter";
    private static final String KEY_DEFAULT_VIEW = "default_view";
    private static final String KEY_DISMISS = "dismiss";
    private static final String KEY_HIGHLIGHTED_DAYS = "highlighted_days";
    private static final String KEY_LIST_POSITION = "list_position";
    private static final String KEY_LIST_POSITION_OFFSET = "list_position_offset";
    private static final String KEY_OK_COLOR = "ok_color";
    private static final String KEY_OK_RESID = "ok_resid";
    private static final String KEY_OK_STRING = "ok_string";
    private static final String KEY_SELECTED_DAY = "day";
    private static final String KEY_SELECTED_MONTH = "month";
    private static final String KEY_SELECTED_YEAR = "year";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_THEME_DARK_CHANGED = "theme_dark_changed";
    private static final String KEY_TIMEZONE = "timezone";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VERSION = "version";
    private static final String KEY_VIBRATE = "vibrate";
    private static final String KEY_WEEK_START = "week_start";
    private static final int MONTH_AND_DAY_VIEW = 0;
    private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM", Locale.getDefault());
    private static final int UNINITIALIZED = -1;
    private static SimpleDateFormat VERSION_2_FORMAT = null;
    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());
    private static final int YEAR_VIEW = 1;
    private HashSet<Calendar> highlightedDays = new HashSet<>();
    private int mAccentColor = -1;
    private AccessibleDateAnimator mAnimator;
    private boolean mAutoDismiss = false;
    private Calendar mCalendar = C5272Utils.trimToMidnight(Calendar.getInstance(getTimeZone()));
    private OnDateSetListener mCallBack;
    private int mCancelColor = -1;
    private int mCancelResid = C5266R.C5270string.mdtp_cancel;
    private String mCancelString;
    private int mCurrentView = -1;
    private TextView mDatePickerHeaderView;
    private DateRangeLimiter mDateRangeLimiter = this.mDefaultLimiter;
    private String mDayPickerDescription;
    private DayPickerView mDayPickerView;
    private DefaultDateRangeLimiter mDefaultLimiter = new DefaultDateRangeLimiter();
    private int mDefaultView = 0;
    private boolean mDelayAnimation = true;
    private boolean mDismissOnPause = false;
    private HapticFeedbackController mHapticFeedbackController;
    private HashSet<OnDateChangedListener> mListeners = new HashSet<>();
    private LinearLayout mMonthAndDayView;
    private int mOkColor = -1;
    private int mOkResid = C5266R.C5270string.mdtp_ok;
    private String mOkString;
    private OnCancelListener mOnCancelListener;
    private OnDismissListener mOnDismissListener;
    private String mSelectDay;
    private String mSelectYear;
    private TextView mSelectedDayTextView;
    private TextView mSelectedMonthTextView;
    private boolean mThemeDark = false;
    private boolean mThemeDarkChanged = false;
    private TimeZone mTimezone;
    private String mTitle;
    private Version mVersion;
    private boolean mVibrate = true;
    private int mWeekStart = this.mCalendar.getFirstDayOfWeek();
    private String mYearPickerDescription;
    private YearPickerView mYearPickerView;
    private TextView mYearView;

    protected interface OnDateChangedListener {
        void onDateChanged();
    }

    public interface OnDateSetListener {
        void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3);
    }

    public enum Version {
        VERSION_1,
        VERSION_2
    }

    public static DatePickerDialog newInstance(OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.initialize(onDateSetListener, i, i2, i3);
        return datePickerDialog;
    }

    public static DatePickerDialog newInstance(OnDateSetListener onDateSetListener) {
        Calendar instance = Calendar.getInstance();
        return newInstance(onDateSetListener, instance.get(1), instance.get(2), instance.get(5));
    }

    public void initialize(OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this.mCallBack = onDateSetListener;
        this.mCalendar.set(1, i);
        this.mCalendar.set(2, i2);
        this.mCalendar.set(5, i3);
        this.mVersion = VERSION.SDK_INT < 23 ? Version.VERSION_1 : Version.VERSION_2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(3);
        this.mCurrentView = -1;
        if (bundle != null) {
            this.mCalendar.set(1, bundle.getInt(KEY_SELECTED_YEAR));
            this.mCalendar.set(2, bundle.getInt(KEY_SELECTED_MONTH));
            this.mCalendar.set(5, bundle.getInt(KEY_SELECTED_DAY));
            this.mDefaultView = bundle.getInt(KEY_DEFAULT_VIEW);
        }
        if (VERSION.SDK_INT < 18) {
            VERSION_2_FORMAT = new SimpleDateFormat(activity.getResources().getString(C5266R.C5270string.mdtp_date_v2_daymonthyear), Locale.getDefault());
        } else {
            VERSION_2_FORMAT = new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "EEEMMMdd"), Locale.getDefault());
        }
        VERSION_2_FORMAT.setTimeZone(getTimeZone());
    }

    public void onSaveInstanceState(Bundle bundle) {
        int i;
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_SELECTED_YEAR, this.mCalendar.get(1));
        bundle.putInt(KEY_SELECTED_MONTH, this.mCalendar.get(2));
        bundle.putInt(KEY_SELECTED_DAY, this.mCalendar.get(5));
        bundle.putInt(KEY_WEEK_START, this.mWeekStart);
        bundle.putInt(KEY_CURRENT_VIEW, this.mCurrentView);
        int i2 = this.mCurrentView;
        if (i2 == 0) {
            i = this.mDayPickerView.getMostVisiblePosition();
        } else if (i2 == 1) {
            i = this.mYearPickerView.getFirstVisiblePosition();
            bundle.putInt(KEY_LIST_POSITION_OFFSET, this.mYearPickerView.getFirstPositionOffset());
        } else {
            i = -1;
        }
        bundle.putInt(KEY_LIST_POSITION, i);
        bundle.putSerializable(KEY_HIGHLIGHTED_DAYS, this.highlightedDays);
        bundle.putBoolean(KEY_THEME_DARK, this.mThemeDark);
        bundle.putBoolean(KEY_THEME_DARK_CHANGED, this.mThemeDarkChanged);
        bundle.putInt(KEY_ACCENT, this.mAccentColor);
        bundle.putBoolean(KEY_VIBRATE, this.mVibrate);
        bundle.putBoolean(KEY_DISMISS, this.mDismissOnPause);
        bundle.putBoolean(KEY_AUTO_DISMISS, this.mAutoDismiss);
        bundle.putInt(KEY_DEFAULT_VIEW, this.mDefaultView);
        bundle.putString("title", this.mTitle);
        bundle.putInt(KEY_OK_RESID, this.mOkResid);
        bundle.putString(KEY_OK_STRING, this.mOkString);
        bundle.putInt(KEY_OK_COLOR, this.mOkColor);
        bundle.putInt(KEY_CANCEL_RESID, this.mCancelResid);
        bundle.putString(KEY_CANCEL_STRING, this.mCancelString);
        bundle.putInt(KEY_CANCEL_COLOR, this.mCancelColor);
        bundle.putSerializable("version", this.mVersion);
        bundle.putSerializable(KEY_TIMEZONE, this.mTimezone);
        bundle.putParcelable(KEY_DATERANGELIMITER, this.mDateRangeLimiter);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2;
        int i3 = this.mDefaultView;
        if (bundle != null) {
            this.mWeekStart = bundle.getInt(KEY_WEEK_START);
            i3 = bundle.getInt(KEY_CURRENT_VIEW);
            i2 = bundle.getInt(KEY_LIST_POSITION);
            i = bundle.getInt(KEY_LIST_POSITION_OFFSET);
            this.highlightedDays = (HashSet) bundle.getSerializable(KEY_HIGHLIGHTED_DAYS);
            this.mThemeDark = bundle.getBoolean(KEY_THEME_DARK);
            this.mThemeDarkChanged = bundle.getBoolean(KEY_THEME_DARK_CHANGED);
            this.mAccentColor = bundle.getInt(KEY_ACCENT);
            this.mVibrate = bundle.getBoolean(KEY_VIBRATE);
            this.mDismissOnPause = bundle.getBoolean(KEY_DISMISS);
            this.mAutoDismiss = bundle.getBoolean(KEY_AUTO_DISMISS);
            this.mTitle = bundle.getString("title");
            this.mOkResid = bundle.getInt(KEY_OK_RESID);
            this.mOkString = bundle.getString(KEY_OK_STRING);
            this.mOkColor = bundle.getInt(KEY_OK_COLOR);
            this.mCancelResid = bundle.getInt(KEY_CANCEL_RESID);
            this.mCancelString = bundle.getString(KEY_CANCEL_STRING);
            this.mCancelColor = bundle.getInt(KEY_CANCEL_COLOR);
            this.mVersion = (Version) bundle.getSerializable("version");
            this.mTimezone = (TimeZone) bundle.getSerializable(KEY_TIMEZONE);
            this.mDateRangeLimiter = (DateRangeLimiter) bundle.getParcelable(KEY_DATERANGELIMITER);
            DateRangeLimiter dateRangeLimiter = this.mDateRangeLimiter;
            if (dateRangeLimiter instanceof DefaultDateRangeLimiter) {
                this.mDefaultLimiter = (DefaultDateRangeLimiter) dateRangeLimiter;
            } else {
                this.mDefaultLimiter = new DefaultDateRangeLimiter();
            }
        } else {
            i2 = -1;
            i = 0;
        }
        this.mDefaultLimiter.setController(this);
        View inflate = layoutInflater.inflate(this.mVersion == Version.VERSION_1 ? C5266R.layout.mdtp_date_picker_dialog : C5266R.layout.mdtp_date_picker_dialog_v2, viewGroup, false);
        this.mCalendar = this.mDateRangeLimiter.setToNearestDate(this.mCalendar);
        this.mDatePickerHeaderView = (TextView) inflate.findViewById(C5266R.C5269id.mdtp_date_picker_header);
        this.mMonthAndDayView = (LinearLayout) inflate.findViewById(C5266R.C5269id.mdtp_date_picker_month_and_day);
        this.mMonthAndDayView.setOnClickListener(this);
        this.mSelectedMonthTextView = (TextView) inflate.findViewById(C5266R.C5269id.mdtp_date_picker_month);
        this.mSelectedDayTextView = (TextView) inflate.findViewById(C5266R.C5269id.mdtp_date_picker_day);
        this.mYearView = (TextView) inflate.findViewById(C5266R.C5269id.mdtp_date_picker_year);
        this.mYearView.setOnClickListener(this);
        Activity activity = getActivity();
        this.mDayPickerView = new SimpleDayPickerView((Context) activity, (DatePickerController) this);
        this.mYearPickerView = new YearPickerView(activity, this);
        if (!this.mThemeDarkChanged) {
            this.mThemeDark = C5272Utils.isDarkTheme(activity, this.mThemeDark);
        }
        Resources resources = getResources();
        this.mDayPickerDescription = resources.getString(C5266R.C5270string.mdtp_day_picker_description);
        this.mSelectDay = resources.getString(C5266R.C5270string.mdtp_select_day);
        this.mYearPickerDescription = resources.getString(C5266R.C5270string.mdtp_year_picker_description);
        this.mSelectYear = resources.getString(C5266R.C5270string.mdtp_select_year);
        inflate.setBackgroundColor(ContextCompat.getColor(activity, this.mThemeDark ? C5266R.C5267color.mdtp_date_picker_view_animator_dark_theme : C5266R.C5267color.mdtp_date_picker_view_animator));
        this.mAnimator = (AccessibleDateAnimator) inflate.findViewById(C5266R.C5269id.mdtp_animator);
        this.mAnimator.addView(this.mDayPickerView);
        this.mAnimator.addView(this.mYearPickerView);
        this.mAnimator.setDateMillis(this.mCalendar.getTimeInMillis());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        this.mAnimator.setInAnimation(alphaAnimation);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation2.setDuration(300);
        this.mAnimator.setOutAnimation(alphaAnimation2);
        Button button = (Button) inflate.findViewById(C5266R.C5269id.mdtp_ok);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog.this.tryVibrate();
                DatePickerDialog.this.notifyOnDateListener();
                DatePickerDialog.this.dismiss();
            }
        });
        String str = "Roboto-Medium";
        button.setTypeface(TypefaceHelper.get(activity, str));
        String str2 = this.mOkString;
        if (str2 != null) {
            button.setText(str2);
        } else {
            button.setText(this.mOkResid);
        }
        Button button2 = (Button) inflate.findViewById(C5266R.C5269id.mdtp_cancel);
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog.this.tryVibrate();
                if (DatePickerDialog.this.getDialog() != null) {
                    DatePickerDialog.this.getDialog().cancel();
                }
            }
        });
        button2.setTypeface(TypefaceHelper.get(activity, str));
        String str3 = this.mCancelString;
        if (str3 != null) {
            button2.setText(str3);
        } else {
            button2.setText(this.mCancelResid);
        }
        button2.setVisibility(isCancelable() ? 0 : 8);
        if (this.mAccentColor == -1) {
            this.mAccentColor = C5272Utils.getAccentColorFromThemeIfAvailable(getActivity());
        }
        TextView textView = this.mDatePickerHeaderView;
        if (textView != null) {
            textView.setBackgroundColor(C5272Utils.darkenColor(this.mAccentColor));
        }
        inflate.findViewById(C5266R.C5269id.mdtp_day_picker_selected_date_layout).setBackgroundColor(this.mAccentColor);
        int i4 = this.mOkColor;
        if (i4 != -1) {
            button.setTextColor(i4);
        } else {
            button.setTextColor(this.mAccentColor);
        }
        int i5 = this.mCancelColor;
        if (i5 != -1) {
            button2.setTextColor(i5);
        } else {
            button2.setTextColor(this.mAccentColor);
        }
        if (getDialog() == null) {
            inflate.findViewById(C5266R.C5269id.mdtp_done_background).setVisibility(8);
        }
        updateDisplay(false);
        setCurrentView(i3);
        if (i2 != -1) {
            if (i3 == 0) {
                this.mDayPickerView.postSetSelection(i2);
            } else if (i3 == 1) {
                this.mYearPickerView.postSetSelectionFromTop(i2, i);
            }
        }
        this.mHapticFeedbackController = new HapticFeedbackController(activity);
        return inflate;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            viewGroup.addView(onCreateView(getActivity().getLayoutInflater(), viewGroup, null));
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        onCreateDialog.requestWindowFeature(1);
        return onCreateDialog;
    }

    public void onResume() {
        super.onResume();
        this.mHapticFeedbackController.start();
    }

    public void onPause() {
        super.onPause();
        this.mHapticFeedbackController.stop();
        if (this.mDismissOnPause) {
            dismiss();
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        OnCancelListener onCancelListener = this.mOnCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    private void setCurrentView(int i) {
        long timeInMillis = this.mCalendar.getTimeInMillis();
        String str = ": ";
        if (i == 0) {
            if (this.mVersion == Version.VERSION_1) {
                ObjectAnimator pulseAnimator = C5272Utils.getPulseAnimator(this.mMonthAndDayView, 0.9f, 1.05f);
                if (this.mDelayAnimation) {
                    pulseAnimator.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mDayPickerView.onDateChanged();
                if (this.mCurrentView != i) {
                    this.mMonthAndDayView.setSelected(true);
                    this.mYearView.setSelected(false);
                    this.mAnimator.setDisplayedChild(0);
                    this.mCurrentView = i;
                }
                pulseAnimator.start();
            } else {
                this.mDayPickerView.onDateChanged();
                if (this.mCurrentView != i) {
                    this.mMonthAndDayView.setSelected(true);
                    this.mYearView.setSelected(false);
                    this.mAnimator.setDisplayedChild(0);
                    this.mCurrentView = i;
                }
            }
            String formatDateTime = DateUtils.formatDateTime(getActivity(), timeInMillis, 16);
            AccessibleDateAnimator accessibleDateAnimator = this.mAnimator;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mDayPickerDescription);
            sb.append(str);
            sb.append(formatDateTime);
            accessibleDateAnimator.setContentDescription(sb.toString());
            C5272Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectDay);
        } else if (i == 1) {
            if (this.mVersion == Version.VERSION_1) {
                ObjectAnimator pulseAnimator2 = C5272Utils.getPulseAnimator(this.mYearView, 0.85f, 1.1f);
                if (this.mDelayAnimation) {
                    pulseAnimator2.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mYearPickerView.onDateChanged();
                if (this.mCurrentView != i) {
                    this.mMonthAndDayView.setSelected(false);
                    this.mYearView.setSelected(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = i;
                }
                pulseAnimator2.start();
            } else {
                this.mYearPickerView.onDateChanged();
                if (this.mCurrentView != i) {
                    this.mMonthAndDayView.setSelected(false);
                    this.mYearView.setSelected(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = i;
                }
            }
            String format = YEAR_FORMAT.format(Long.valueOf(timeInMillis));
            AccessibleDateAnimator accessibleDateAnimator2 = this.mAnimator;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mYearPickerDescription);
            sb2.append(str);
            sb2.append(format);
            accessibleDateAnimator2.setContentDescription(sb2.toString());
            C5272Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectYear);
        }
    }

    private void updateDisplay(boolean z) {
        this.mYearView.setText(YEAR_FORMAT.format(this.mCalendar.getTime()));
        if (this.mVersion == Version.VERSION_1) {
            TextView textView = this.mDatePickerHeaderView;
            if (textView != null) {
                String str = this.mTitle;
                if (str != null) {
                    textView.setText(str.toUpperCase(Locale.getDefault()));
                } else {
                    textView.setText(this.mCalendar.getDisplayName(7, 2, Locale.getDefault()).toUpperCase(Locale.getDefault()));
                }
            }
            this.mSelectedMonthTextView.setText(MONTH_FORMAT.format(this.mCalendar.getTime()));
            this.mSelectedDayTextView.setText(DAY_FORMAT.format(this.mCalendar.getTime()));
        }
        if (this.mVersion == Version.VERSION_2) {
            this.mSelectedDayTextView.setText(VERSION_2_FORMAT.format(this.mCalendar.getTime()));
            String str2 = this.mTitle;
            if (str2 != null) {
                this.mDatePickerHeaderView.setText(str2.toUpperCase(Locale.getDefault()));
            } else {
                this.mDatePickerHeaderView.setVisibility(8);
            }
        }
        long timeInMillis = this.mCalendar.getTimeInMillis();
        this.mAnimator.setDateMillis(timeInMillis);
        this.mMonthAndDayView.setContentDescription(DateUtils.formatDateTime(getActivity(), timeInMillis, 24));
        if (z) {
            C5272Utils.tryAccessibilityAnnounce(this.mAnimator, DateUtils.formatDateTime(getActivity(), timeInMillis, 20));
        }
    }

    public void vibrate(boolean z) {
        this.mVibrate = z;
    }

    public void dismissOnPause(boolean z) {
        this.mDismissOnPause = z;
    }

    public void autoDismiss(boolean z) {
        this.mAutoDismiss = z;
    }

    public void setThemeDark(boolean z) {
        this.mThemeDark = z;
        this.mThemeDarkChanged = true;
    }

    public boolean isThemeDark() {
        return this.mThemeDark;
    }

    public void setAccentColor(String str) {
        this.mAccentColor = Color.parseColor(str);
    }

    public void setAccentColor(int i) {
        this.mAccentColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public void setOkColor(String str) {
        this.mOkColor = Color.parseColor(str);
    }

    public void setOkColor(int i) {
        this.mOkColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public void setCancelColor(String str) {
        this.mCancelColor = Color.parseColor(str);
    }

    public void setCancelColor(int i) {
        this.mCancelColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public int getAccentColor() {
        return this.mAccentColor;
    }

    public void showYearPickerFirst(boolean z) {
        this.mDefaultView = z ? 1 : 0;
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Value must be between Calendar.SUNDAY and Calendar.SATURDAY");
        }
        this.mWeekStart = i;
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public void setYearRange(int i, int i2) {
        this.mDefaultLimiter.setYearRange(i, i2);
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public void setMinDate(Calendar calendar) {
        this.mDefaultLimiter.setMinDate(calendar);
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public Calendar getMinDate() {
        return this.mDefaultLimiter.getMinDate();
    }

    public void setMaxDate(Calendar calendar) {
        this.mDefaultLimiter.setMaxDate(calendar);
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public Calendar getMaxDate() {
        return this.mDefaultLimiter.getMaxDate();
    }

    public void setHighlightedDays(Calendar[] calendarArr) {
        for (Calendar trimToMidnight : calendarArr) {
            C5272Utils.trimToMidnight(trimToMidnight);
        }
        this.highlightedDays.addAll(Arrays.asList(calendarArr));
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public Calendar[] getHighlightedDays() {
        if (this.highlightedDays.isEmpty()) {
            return null;
        }
        Calendar[] calendarArr = (Calendar[]) this.highlightedDays.toArray(new Calendar[0]);
        Arrays.sort(calendarArr);
        return calendarArr;
    }

    public boolean isHighlighted(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(1, i);
        instance.set(2, i2);
        instance.set(5, i3);
        C5272Utils.trimToMidnight(instance);
        return this.highlightedDays.contains(instance);
    }

    public void setSelectableDays(Calendar[] calendarArr) {
        this.mDefaultLimiter.setSelectableDays(calendarArr);
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public Calendar[] getSelectableDays() {
        return this.mDefaultLimiter.getSelectableDays();
    }

    public void setDisabledDays(Calendar[] calendarArr) {
        this.mDefaultLimiter.setDisabledDays(calendarArr);
        DayPickerView dayPickerView = this.mDayPickerView;
        if (dayPickerView != null) {
            dayPickerView.onChange();
        }
    }

    public Calendar[] getDisabledDays() {
        return this.mDefaultLimiter.getDisabledDays();
    }

    public void setDateRangeLimiter(DateRangeLimiter dateRangeLimiter) {
        this.mDateRangeLimiter = dateRangeLimiter;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setOkText(String str) {
        this.mOkString = str;
    }

    public void setOkText(int i) {
        this.mOkString = null;
        this.mOkResid = i;
    }

    public void setCancelText(String str) {
        this.mCancelString = str;
    }

    public void setCancelText(int i) {
        this.mCancelString = null;
        this.mCancelResid = i;
    }

    public void setVersion(Version version) {
        this.mVersion = version;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimezone = timeZone;
        this.mCalendar.setTimeZone(timeZone);
        YEAR_FORMAT.setTimeZone(timeZone);
        MONTH_FORMAT.setTimeZone(timeZone);
        DAY_FORMAT.setTimeZone(timeZone);
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        this.mCallBack = onDateSetListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    private Calendar adjustDayInMonthIfNeeded(Calendar calendar) {
        int i = calendar.get(5);
        int actualMaximum = calendar.getActualMaximum(5);
        if (i > actualMaximum) {
            calendar.set(5, actualMaximum);
        }
        return this.mDateRangeLimiter.setToNearestDate(calendar);
    }

    public void onClick(View view) {
        tryVibrate();
        if (view.getId() == C5266R.C5269id.mdtp_date_picker_year) {
            setCurrentView(1);
        } else if (view.getId() == C5266R.C5269id.mdtp_date_picker_month_and_day) {
            setCurrentView(0);
        }
    }

    public void onYearSelected(int i) {
        this.mCalendar.set(1, i);
        this.mCalendar = adjustDayInMonthIfNeeded(this.mCalendar);
        updatePickers();
        setCurrentView(0);
        updateDisplay(true);
    }

    public void onDayOfMonthSelected(int i, int i2, int i3) {
        this.mCalendar.set(1, i);
        this.mCalendar.set(2, i2);
        this.mCalendar.set(5, i3);
        updatePickers();
        updateDisplay(true);
        if (this.mAutoDismiss) {
            notifyOnDateListener();
            dismiss();
        }
    }

    private void updatePickers() {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnDateChangedListener) it.next()).onDateChanged();
        }
    }

    public CalendarDay getSelectedDay() {
        return new CalendarDay(this.mCalendar, getTimeZone());
    }

    public Calendar getStartDate() {
        return this.mDateRangeLimiter.getStartDate();
    }

    public Calendar getEndDate() {
        return this.mDateRangeLimiter.getEndDate();
    }

    public int getMinYear() {
        return this.mDateRangeLimiter.getMinYear();
    }

    public int getMaxYear() {
        return this.mDateRangeLimiter.getMaxYear();
    }

    public boolean isOutOfRange(int i, int i2, int i3) {
        return this.mDateRangeLimiter.isOutOfRange(i, i2, i3);
    }

    public int getFirstDayOfWeek() {
        return this.mWeekStart;
    }

    public void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mListeners.add(onDateChangedListener);
    }

    public void unregisterOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mListeners.remove(onDateChangedListener);
    }

    public void tryVibrate() {
        if (this.mVibrate) {
            this.mHapticFeedbackController.tryVibrate();
        }
    }

    public TimeZone getTimeZone() {
        TimeZone timeZone = this.mTimezone;
        return timeZone == null ? TimeZone.getDefault() : timeZone;
    }

    public void notifyOnDateListener() {
        OnDateSetListener onDateSetListener = this.mCallBack;
        if (onDateSetListener != null) {
            onDateSetListener.onDateSet(this, this.mCalendar.get(1), this.mCalendar.get(2), this.mCalendar.get(5));
        }
    }
}
