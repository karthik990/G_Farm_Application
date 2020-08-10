package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import com.wdullaer.materialdatetimepicker.C5272Utils;
import com.wdullaer.materialdatetimepicker.GravitySnapHelper;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter.CalendarDay;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class DayPickerView extends RecyclerView implements OnDateChangedListener {
    public static final int DAYS_PER_WEEK = 7;
    protected static final int SCROLL_HYST_WEEKS = 2;
    private static final String TAG = "MonthFragment";
    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());
    private LinearLayoutManager linearLayoutManager;
    protected MonthAdapter mAdapter;
    protected Context mContext;
    private DatePickerController mController;
    protected int mCurrentMonthDisplayed;
    protected int mDaysPerWeek = 7;
    protected int mFirstDayOfWeek;
    protected Handler mHandler;
    protected int mNumWeeks = 6;
    protected CharSequence mPrevMonthName;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    protected CalendarDay mSelectedDay;
    protected boolean mShowWeekNumber = false;
    protected CalendarDay mTempDay;

    public abstract MonthAdapter createMonthAdapter(DatePickerController datePickerController);

    public DayPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DayPickerView(Context context, DatePickerController datePickerController) {
        super(context);
        init(context);
        setController(datePickerController);
    }

    public void setController(DatePickerController datePickerController) {
        this.mController = datePickerController;
        this.mController.registerOnDateChangedListener(this);
        this.mSelectedDay = new CalendarDay(this.mController.getTimeZone());
        this.mTempDay = new CalendarDay(this.mController.getTimeZone());
        refreshAdapter();
        onDateChanged();
    }

    public void init(Context context) {
        this.linearLayoutManager = new LinearLayoutManager(context, 1, false);
        setLayoutManager(this.linearLayoutManager);
        this.mHandler = new Handler();
        setLayoutParams(new LayoutParams(-1, -1));
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        this.mContext = context;
        setUpRecyclerView();
    }

    public void setScrollOrientation(int i) {
        this.linearLayoutManager.setOrientation(i);
    }

    /* access modifiers changed from: protected */
    public void setUpRecyclerView() {
        setVerticalScrollBarEnabled(false);
        setFadingEdgeLength(0);
        new GravitySnapHelper(48).attachToRecyclerView(this);
    }

    public void onChange() {
        refreshAdapter();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        restoreAccessibilityFocus(findAccessibilityFocus());
    }

    /* access modifiers changed from: protected */
    public void refreshAdapter() {
        MonthAdapter monthAdapter = this.mAdapter;
        if (monthAdapter == null) {
            this.mAdapter = createMonthAdapter(this.mController);
        } else {
            monthAdapter.setSelectedDay(this.mSelectedDay);
        }
        setAdapter(this.mAdapter);
    }

    public boolean goTo(CalendarDay calendarDay, boolean z, boolean z2, boolean z3) {
        View childAt;
        String str;
        if (z2) {
            this.mSelectedDay.set(calendarDay);
        }
        this.mTempDay.set(calendarDay);
        int minYear = (((calendarDay.year - this.mController.getMinYear()) * 12) + calendarDay.month) - this.mController.getStartDate().get(2);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            childAt = getChildAt(i);
            str = TAG;
            if (childAt == null) {
                break;
            }
            int top = childAt.getTop();
            if (Log.isLoggable(str, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("child at ");
                sb.append(i2 - 1);
                sb.append(" has top ");
                sb.append(top);
                Log.d(str, sb.toString());
            }
            if (top >= 0) {
                break;
            }
            i = i2;
        }
        int childAdapterPosition = childAt != null ? getChildAdapterPosition(childAt) : 0;
        if (z2) {
            this.mAdapter.setSelectedDay(this.mSelectedDay);
        }
        if (Log.isLoggable(str, 3)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("GoTo position ");
            sb2.append(minYear);
            Log.d(str, sb2.toString());
        }
        if (minYear != childAdapterPosition || z3) {
            setMonthDisplayed(this.mTempDay);
            this.mPreviousScrollState = 1;
            if (z) {
                smoothScrollToPosition(minYear);
                return true;
            }
            postSetSelection(minYear);
        } else if (z2) {
            setMonthDisplayed(this.mSelectedDay);
        }
        return false;
    }

    public void postSetSelection(final int i) {
        clearFocus();
        post(new Runnable() {
            public void run() {
                ((LinearLayoutManager) DayPickerView.this.getLayoutManager()).scrollToPositionWithOffset(i, 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setMonthDisplayed(CalendarDay calendarDay) {
        this.mCurrentMonthDisplayed = calendarDay.month;
    }

    public int getMostVisiblePosition() {
        return getChildAdapterPosition(getMostVisibleMonth());
    }

    public MonthView getMostVisibleMonth() {
        boolean z = true;
        if (((LinearLayoutManager) getLayoutManager()).getOrientation() != 1) {
            z = false;
        }
        int height = z ? getHeight() : getWidth();
        MonthView monthView = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < height) {
            View childAt = getChildAt(i2);
            if (childAt == null) {
                break;
            }
            int bottom = z ? childAt.getBottom() : getRight();
            int min = Math.min(bottom, height) - Math.max(0, childAt.getTop());
            if (min > i3) {
                monthView = (MonthView) childAt;
                i3 = min;
            }
            i2++;
            i = bottom;
        }
        return monthView;
    }

    public void onDateChanged() {
        goTo(this.mController.getSelectedDay(), false, true, true);
    }

    private CalendarDay findAccessibilityFocus() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof MonthView) {
                MonthView monthView = (MonthView) childAt;
                CalendarDay accessibilityFocus = monthView.getAccessibilityFocus();
                if (accessibilityFocus != null) {
                    if (VERSION.SDK_INT == 17) {
                        monthView.clearAccessibilityFocus();
                    }
                    return accessibilityFocus;
                }
            }
        }
        return null;
    }

    private boolean restoreAccessibilityFocus(CalendarDay calendarDay) {
        if (calendarDay == null) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof MonthView) && ((MonthView) childAt).restoreAccessibilityFocus(calendarDay)) {
                return true;
            }
        }
        return false;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setItemCount(-1);
    }

    private static String getMonthAndYearString(CalendarDay calendarDay) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendarDay.year, calendarDay.month, calendarDay.day);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(instance.getDisplayName(2, 2, Locale.getDefault()));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(" ");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(YEAR_FORMAT.format(instance.getTime()));
        return sb5.toString();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (VERSION.SDK_INT >= 21) {
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD);
            return;
        }
        accessibilityNodeInfo.addAction(4096);
        accessibilityNodeInfo.addAction(8192);
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != 4096 && i != 8192) {
            return super.performAccessibilityAction(i, bundle);
        }
        int firstVisiblePosition = getFirstVisiblePosition() + this.mController.getStartDate().get(2);
        CalendarDay calendarDay = new CalendarDay((firstVisiblePosition / 12) + this.mController.getMinYear(), firstVisiblePosition % 12, 1);
        if (i == 4096) {
            calendarDay.month++;
            if (calendarDay.month == 12) {
                calendarDay.month = 0;
                calendarDay.year++;
            }
        } else if (i == 8192) {
            View childAt = getChildAt(0);
            if (childAt != null && childAt.getTop() >= -1) {
                calendarDay.month--;
                if (calendarDay.month == -1) {
                    calendarDay.month = 11;
                    calendarDay.year--;
                }
            }
        }
        C5272Utils.tryAccessibilityAnnounce(this, getMonthAndYearString(calendarDay));
        goTo(calendarDay, true, false, true);
        return true;
    }

    private int getFirstVisiblePosition() {
        return getChildAdapterPosition(getChildAt(0));
    }
}
