package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.exifinterface.media.ExifInterface;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter.CalendarDay;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public abstract class MonthView extends View {
    protected static int DAY_SELECTED_CIRCLE_SIZE = 0;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static final int DEFAULT_FOCUS_MONTH = -1;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_DAYS = 7;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static final int DEFAULT_SELECTED_DAY = -1;
    protected static final int DEFAULT_SHOW_WK_NUM = 0;
    protected static final int DEFAULT_WEEK_START = 1;
    protected static final int MAX_NUM_ROWS = 6;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE = 0;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE = 0;
    protected static int MONTH_HEADER_SIZE = 0;
    protected static int MONTH_LABEL_TEXT_SIZE = 0;
    private static final int SELECTED_CIRCLE_ALPHA = 255;
    protected static float mScale;
    private final Calendar mCalendar;
    protected DatePickerController mController;
    protected final Calendar mDayLabelCalendar;
    private int mDayOfWeekStart;
    private String mDayOfWeekTypeface;
    protected int mDayTextColor;
    protected int mDisabledDayTextColor;
    protected int mEdgePadding;
    protected int mFirstJulianDay;
    protected int mFirstMonth;
    protected boolean mHasToday;
    protected int mHighlightedDayTextColor;
    protected int mLastMonth;
    private boolean mLockAccessibilityDelegate;
    protected int mMonth;
    protected Paint mMonthDayLabelPaint;
    protected int mMonthDayTextColor;
    protected Paint mMonthNumPaint;
    protected int mMonthTitleColor;
    protected Paint mMonthTitlePaint;
    private String mMonthTitleTypeface;
    protected int mNumCells;
    protected int mNumDays;
    protected int mNumRows;
    protected OnDayClickListener mOnDayClickListener;
    protected int mRowHeight;
    protected Paint mSelectedCirclePaint;
    protected int mSelectedDay;
    protected int mSelectedDayTextColor;
    protected int mSelectedLeft;
    protected int mSelectedRight;
    private final StringBuilder mStringBuilder;
    protected int mToday;
    protected int mTodayNumberColor;
    private final MonthViewTouchHelper mTouchHelper;
    protected int mWeekStart;
    protected int mWidth;
    protected int mYear;
    private SimpleDateFormat weekDayLabelFormatter;

    protected class MonthViewTouchHelper extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar = Calendar.getInstance(MonthView.this.mController.getTimeZone());
        private final Rect mTempRect = new Rect();

        public MonthViewTouchHelper(View view) {
            super(view);
        }

        public void setFocusedVirtualView(int i) {
            getAccessibilityNodeProvider(MonthView.this).performAction(i, 64, null);
        }

        public void clearFocusedVirtualView() {
            int focusedVirtualView = getFocusedVirtualView();
            if (focusedVirtualView != Integer.MIN_VALUE) {
                getAccessibilityNodeProvider(MonthView.this).performAction(focusedVirtualView, 128, null);
            }
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float f, float f2) {
            int dayFromLocation = MonthView.this.getDayFromLocation(f, f2);
            if (dayFromLocation >= 0) {
                return dayFromLocation;
            }
            return Integer.MIN_VALUE;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(List<Integer> list) {
            for (int i = 1; i <= MonthView.this.mNumCells; i++) {
                list.add(Integer.valueOf(i));
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getItemDescription(i));
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            getItemBounds(i, this.mTempRect);
            accessibilityNodeInfoCompat.setContentDescription(getItemDescription(i));
            accessibilityNodeInfoCompat.setBoundsInParent(this.mTempRect);
            accessibilityNodeInfoCompat.addAction(16);
            if (i == MonthView.this.mSelectedDay) {
                accessibilityNodeInfoCompat.setSelected(true);
            }
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            MonthView.this.onDayClick(i);
            return true;
        }

        /* access modifiers changed from: protected */
        public void getItemBounds(int i, Rect rect) {
            int i2 = MonthView.this.mEdgePadding;
            int monthHeaderSize = MonthView.this.getMonthHeaderSize();
            int i3 = MonthView.this.mRowHeight;
            int i4 = (MonthView.this.mWidth - (MonthView.this.mEdgePadding * 2)) / MonthView.this.mNumDays;
            int findDayOffset = (i - 1) + MonthView.this.findDayOffset();
            int i5 = i2 + ((findDayOffset % MonthView.this.mNumDays) * i4);
            int i6 = monthHeaderSize + ((findDayOffset / MonthView.this.mNumDays) * i3);
            rect.set(i5, i6, i4 + i5, i3 + i6);
        }

        /* access modifiers changed from: protected */
        public CharSequence getItemDescription(int i) {
            this.mTempCalendar.set(MonthView.this.mYear, MonthView.this.mMonth, i);
            CharSequence format = DateFormat.format(DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
            if (i != MonthView.this.mSelectedDay) {
                return format;
            }
            return MonthView.this.getContext().getString(C5266R.C5270string.mdtp_item_is_selected, new Object[]{format});
        }
    }

    public interface OnDayClickListener {
        void onDayClick(MonthView monthView, CalendarDay calendarDay);
    }

    public abstract void drawMonthDay(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    public MonthView(Context context) {
        this(context, null, null);
    }

    public MonthView(Context context, AttributeSet attributeSet, DatePickerController datePickerController) {
        super(context, attributeSet);
        boolean z = false;
        this.mEdgePadding = 0;
        this.mFirstJulianDay = -1;
        this.mFirstMonth = -1;
        this.mLastMonth = -1;
        this.mRowHeight = DEFAULT_HEIGHT;
        this.mHasToday = false;
        this.mSelectedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mNumDays = 7;
        this.mNumCells = this.mNumDays;
        this.mSelectedLeft = -1;
        this.mSelectedRight = -1;
        this.mNumRows = 6;
        this.mDayOfWeekStart = 0;
        this.mController = datePickerController;
        Resources resources = context.getResources();
        this.mDayLabelCalendar = Calendar.getInstance(this.mController.getTimeZone());
        this.mCalendar = Calendar.getInstance(this.mController.getTimeZone());
        this.mDayOfWeekTypeface = resources.getString(C5266R.C5270string.mdtp_day_of_week_label_typeface);
        this.mMonthTitleTypeface = resources.getString(C5266R.C5270string.mdtp_sans_serif);
        DatePickerController datePickerController2 = this.mController;
        if (datePickerController2 != null && datePickerController2.isThemeDark()) {
            z = true;
        }
        if (z) {
            this.mDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_normal_dark_theme);
            this.mMonthDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_month_day_dark_theme);
            this.mDisabledDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_disabled_dark_theme);
            this.mHighlightedDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_highlighted_dark_theme);
        } else {
            this.mDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_normal);
            this.mMonthDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_month_day);
            this.mDisabledDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_disabled);
            this.mHighlightedDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_highlighted);
        }
        this.mSelectedDayTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_white);
        this.mTodayNumberColor = this.mController.getAccentColor();
        this.mMonthTitleColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_white);
        this.mStringBuilder = new StringBuilder(50);
        MINI_DAY_NUMBER_TEXT_SIZE = resources.getDimensionPixelSize(C5266R.dimen.mdtp_day_number_size);
        MONTH_LABEL_TEXT_SIZE = resources.getDimensionPixelSize(C5266R.dimen.mdtp_month_label_size);
        MONTH_DAY_LABEL_TEXT_SIZE = resources.getDimensionPixelSize(C5266R.dimen.mdtp_month_day_label_text_size);
        MONTH_HEADER_SIZE = resources.getDimensionPixelOffset(C5266R.dimen.mdtp_month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = resources.getDimensionPixelSize(C5266R.dimen.mdtp_day_number_select_circle_radius);
        this.mRowHeight = (resources.getDimensionPixelOffset(C5266R.dimen.mdtp_date_picker_view_animator_height) - getMonthHeaderSize()) / 6;
        this.mTouchHelper = getMonthViewTouchHelper();
        ViewCompat.setAccessibilityDelegate(this, this.mTouchHelper);
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mLockAccessibilityDelegate = true;
        initView();
    }

    public void setDatePickerController(DatePickerController datePickerController) {
        this.mController = datePickerController;
    }

    /* access modifiers changed from: protected */
    public MonthViewTouchHelper getMonthViewTouchHelper() {
        return new MonthViewTouchHelper(this);
    }

    public void setAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        if (!this.mLockAccessibilityDelegate) {
            super.setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            int dayFromLocation = getDayFromLocation(motionEvent.getX(), motionEvent.getY());
            if (dayFromLocation >= 0) {
                onDayClick(dayFromLocation);
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.mMonthTitlePaint = new Paint();
        this.mMonthTitlePaint.setFakeBoldText(true);
        this.mMonthTitlePaint.setAntiAlias(true);
        this.mMonthTitlePaint.setTextSize((float) MONTH_LABEL_TEXT_SIZE);
        this.mMonthTitlePaint.setTypeface(Typeface.create(this.mMonthTitleTypeface, 1));
        this.mMonthTitlePaint.setColor(this.mDayTextColor);
        this.mMonthTitlePaint.setTextAlign(Align.CENTER);
        this.mMonthTitlePaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint = new Paint();
        this.mSelectedCirclePaint.setFakeBoldText(true);
        this.mSelectedCirclePaint.setAntiAlias(true);
        this.mSelectedCirclePaint.setColor(this.mTodayNumberColor);
        this.mSelectedCirclePaint.setTextAlign(Align.CENTER);
        this.mSelectedCirclePaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint.setAlpha(255);
        this.mMonthDayLabelPaint = new Paint();
        this.mMonthDayLabelPaint.setAntiAlias(true);
        this.mMonthDayLabelPaint.setTextSize((float) MONTH_DAY_LABEL_TEXT_SIZE);
        this.mMonthDayLabelPaint.setColor(this.mMonthDayTextColor);
        this.mMonthTitlePaint.setTypeface(Typeface.create(this.mDayOfWeekTypeface, 1));
        this.mMonthDayLabelPaint.setStyle(Style.FILL);
        this.mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        this.mMonthDayLabelPaint.setFakeBoldText(true);
        this.mMonthNumPaint = new Paint();
        this.mMonthNumPaint.setAntiAlias(true);
        this.mMonthNumPaint.setTextSize((float) MINI_DAY_NUMBER_TEXT_SIZE);
        this.mMonthNumPaint.setStyle(Style.FILL);
        this.mMonthNumPaint.setTextAlign(Align.CENTER);
        this.mMonthNumPaint.setFakeBoldText(false);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    public void setMonthParams(int i, int i2, int i3, int i4) {
        if (i3 == -1 && i2 == -1) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
        this.mSelectedDay = i;
        this.mMonth = i3;
        this.mYear = i2;
        Calendar instance = Calendar.getInstance(this.mController.getTimeZone());
        int i5 = 0;
        this.mHasToday = false;
        this.mToday = -1;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (i4 != -1) {
            this.mWeekStart = i4;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        this.mNumCells = this.mCalendar.getActualMaximum(5);
        while (i5 < this.mNumCells) {
            i5++;
            if (sameDay(i5, instance)) {
                this.mHasToday = true;
                this.mToday = i5;
            }
        }
        this.mNumRows = calculateNumRows();
        this.mTouchHelper.invalidateRoot();
    }

    public void setSelectedDay(int i) {
        this.mSelectedDay = i;
    }

    private int calculateNumRows() {
        int findDayOffset = findDayOffset();
        int i = this.mNumCells;
        int i2 = findDayOffset + i;
        int i3 = this.mNumDays;
        return (i2 / i3) + ((findDayOffset + i) % i3 > 0 ? 1 : 0);
    }

    private boolean sameDay(int i, Calendar calendar) {
        if (this.mYear == calendar.get(1) && this.mMonth == calendar.get(2) && i == calendar.get(5)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), (this.mRowHeight * this.mNumRows) + getMonthHeaderSize() + 5);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mTouchHelper.invalidateRoot();
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getYear() {
        return this.mYear;
    }

    /* access modifiers changed from: protected */
    public int getMonthHeaderSize() {
        return MONTH_HEADER_SIZE;
    }

    private String getMonthAndYearString() {
        String str;
        Locale locale = Locale.getDefault();
        if (VERSION.SDK_INT < 18) {
            str = getContext().getResources().getString(C5266R.C5270string.mdtp_date_v1_monthyear);
        } else {
            str = DateFormat.getBestDateTimePattern(locale, "MMMM yyyy");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(this.mController.getTimeZone());
        simpleDateFormat.applyLocalizedPattern(str);
        this.mStringBuilder.setLength(0);
        return simpleDateFormat.format(this.mCalendar.getTime());
    }

    /* access modifiers changed from: protected */
    public void drawMonthTitle(Canvas canvas) {
        canvas.drawText(getMonthAndYearString(), (float) ((this.mWidth + (this.mEdgePadding * 2)) / 2), (float) ((getMonthHeaderSize() - MONTH_DAY_LABEL_TEXT_SIZE) / 2), this.mMonthTitlePaint);
    }

    /* access modifiers changed from: protected */
    public void drawMonthDayLabels(Canvas canvas) {
        int monthHeaderSize = getMonthHeaderSize() - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int i = (this.mWidth - (this.mEdgePadding * 2)) / (this.mNumDays * 2);
        int i2 = 0;
        while (true) {
            int i3 = this.mNumDays;
            if (i2 < i3) {
                int i4 = (((i2 * 2) + 1) * i) + this.mEdgePadding;
                this.mDayLabelCalendar.set(7, (this.mWeekStart + i2) % i3);
                canvas.drawText(getWeekDayLabel(this.mDayLabelCalendar), (float) i4, (float) monthHeaderSize, this.mMonthDayLabelPaint);
                i2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawMonthNums(Canvas canvas) {
        float f = ((float) (this.mWidth - (this.mEdgePadding * 2))) / (((float) this.mNumDays) * 2.0f);
        int monthHeaderSize = (((this.mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2) - DAY_SEPARATOR_WIDTH) + getMonthHeaderSize();
        int findDayOffset = findDayOffset();
        for (int i = 1; i <= this.mNumCells; i++) {
            int i2 = (int) ((((float) ((findDayOffset * 2) + 1)) * f) + ((float) this.mEdgePadding));
            int i3 = this.mRowHeight;
            float f2 = (float) i2;
            int i4 = monthHeaderSize - (((MINI_DAY_NUMBER_TEXT_SIZE + i3) / 2) - DAY_SEPARATOR_WIDTH);
            int i5 = i4 + i3;
            Canvas canvas2 = canvas;
            drawMonthDay(canvas2, this.mYear, this.mMonth, i, i2, monthHeaderSize, (int) (f2 - f), (int) (f2 + f), i4, i5);
            findDayOffset++;
            if (findDayOffset == this.mNumDays) {
                monthHeaderSize += this.mRowHeight;
                findDayOffset = 0;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int findDayOffset() {
        int i = this.mDayOfWeekStart;
        if (i < this.mWeekStart) {
            i += this.mNumDays;
        }
        return i - this.mWeekStart;
    }

    public int getDayFromLocation(float f, float f2) {
        int internalDayFromLocation = getInternalDayFromLocation(f, f2);
        if (internalDayFromLocation < 1 || internalDayFromLocation > this.mNumCells) {
            return -1;
        }
        return internalDayFromLocation;
    }

    /* access modifiers changed from: protected */
    public int getInternalDayFromLocation(float f, float f2) {
        int i = this.mEdgePadding;
        float f3 = (float) i;
        if (f < f3 || f > ((float) (this.mWidth - i))) {
            return -1;
        }
        return (((int) (((f - f3) * ((float) this.mNumDays)) / ((float) ((this.mWidth - i) - this.mEdgePadding)))) - findDayOffset()) + 1 + ((((int) (f2 - ((float) getMonthHeaderSize()))) / this.mRowHeight) * this.mNumDays);
    }

    /* access modifiers changed from: private */
    public void onDayClick(int i) {
        if (!this.mController.isOutOfRange(this.mYear, this.mMonth, i)) {
            OnDayClickListener onDayClickListener = this.mOnDayClickListener;
            if (onDayClickListener != null) {
                onDayClickListener.onDayClick(this, new CalendarDay(this.mYear, this.mMonth, i));
            }
            this.mTouchHelper.sendEventForVirtualView(i, 1);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isHighlighted(int i, int i2, int i3) {
        return this.mController.isHighlighted(i, i2, i3);
    }

    private String getWeekDayLabel(Calendar calendar) {
        Locale locale = Locale.getDefault();
        if (VERSION.SDK_INT < 18) {
            String format = new SimpleDateFormat(ExifInterface.LONGITUDE_EAST, locale).format(calendar.getTime());
            String substring = format.toUpperCase(locale).substring(0, 1);
            if (locale.equals(Locale.CHINA) || locale.equals(Locale.CHINESE) || locale.equals(Locale.SIMPLIFIED_CHINESE) || locale.equals(Locale.TRADITIONAL_CHINESE)) {
                int length = format.length();
                substring = format.substring(length - 1, length);
            }
            if (locale.getLanguage().equals("he") || locale.getLanguage().equals("iw")) {
                if (this.mDayLabelCalendar.get(7) != 7) {
                    int length2 = format.length();
                    substring = format.substring(length2 - 2, length2 - 1);
                } else {
                    substring = format.toUpperCase(locale).substring(0, 1);
                }
            }
            if (locale.getLanguage().equals("ca")) {
                substring = format.toLowerCase().substring(0, 2);
            }
            if (locale.getLanguage().equals("es") && calendar.get(7) == 4) {
                substring = "X";
            }
            return substring;
        }
        if (this.weekDayLabelFormatter == null) {
            this.weekDayLabelFormatter = new SimpleDateFormat("EEEEE", locale);
        }
        return this.weekDayLabelFormatter.format(calendar.getTime());
    }

    public CalendarDay getAccessibilityFocus() {
        int focusedVirtualView = this.mTouchHelper.getFocusedVirtualView();
        if (focusedVirtualView >= 0) {
            return new CalendarDay(this.mYear, this.mMonth, focusedVirtualView);
        }
        return null;
    }

    public void clearAccessibilityFocus() {
        this.mTouchHelper.clearFocusedVirtualView();
    }

    public boolean restoreAccessibilityFocus(CalendarDay calendarDay) {
        if (calendarDay.year != this.mYear || calendarDay.month != this.mMonth || calendarDay.day > this.mNumCells) {
            return false;
        }
        this.mTouchHelper.setFocusedVirtualView(calendarDay.day);
        return true;
    }
}
