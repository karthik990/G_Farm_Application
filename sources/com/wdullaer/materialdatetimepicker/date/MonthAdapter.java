package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.MonthView.OnDayClickListener;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class MonthAdapter extends Adapter<MonthViewHolder> implements OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    protected static int WEEK_7_OVERHANG_HEIGHT = 7;
    protected final DatePickerController mController;
    private CalendarDay mSelectedDay;

    public static class CalendarDay {
        private Calendar calendar;
        int day;
        TimeZone mTimeZone;
        int month;
        int year;

        public CalendarDay(TimeZone timeZone) {
            this.mTimeZone = timeZone;
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(long j, TimeZone timeZone) {
            this.mTimeZone = timeZone;
            setTime(j);
        }

        public CalendarDay(Calendar calendar2, TimeZone timeZone) {
            this.mTimeZone = timeZone;
            this.year = calendar2.get(1);
            this.month = calendar2.get(2);
            this.day = calendar2.get(5);
        }

        public CalendarDay(int i, int i2, int i3) {
            setDay(i, i2, i3);
        }

        public void set(CalendarDay calendarDay) {
            this.year = calendarDay.year;
            this.month = calendarDay.month;
            this.day = calendarDay.day;
        }

        public void setDay(int i, int i2, int i3) {
            this.year = i;
            this.month = i2;
            this.day = i3;
        }

        private void setTime(long j) {
            if (this.calendar == null) {
                this.calendar = Calendar.getInstance(this.mTimeZone);
            }
            this.calendar.setTimeInMillis(j);
            this.month = this.calendar.get(2);
            this.year = this.calendar.get(1);
            this.day = this.calendar.get(5);
        }

        public int getYear() {
            return this.year;
        }

        public int getMonth() {
            return this.month;
        }

        public int getDay() {
            return this.day;
        }
    }

    static class MonthViewHolder extends ViewHolder {
        public MonthViewHolder(MonthView monthView) {
            super(monthView);
        }

        /* access modifiers changed from: 0000 */
        public void bind(int i, DatePickerController datePickerController, CalendarDay calendarDay) {
            int i2 = (datePickerController.getStartDate().get(2) + i) % 12;
            int minYear = ((i + datePickerController.getStartDate().get(2)) / 12) + datePickerController.getMinYear();
            ((MonthView) this.itemView).setMonthParams(isSelectedDayInMonth(calendarDay, minYear, i2) ? calendarDay.day : -1, minYear, i2, datePickerController.getFirstDayOfWeek());
            this.itemView.invalidate();
        }

        private boolean isSelectedDayInMonth(CalendarDay calendarDay, int i, int i2) {
            return calendarDay.year == i && calendarDay.month == i2;
        }
    }

    public abstract MonthView createMonthView(Context context);

    public long getItemId(int i) {
        return (long) i;
    }

    public MonthAdapter(DatePickerController datePickerController) {
        this.mController = datePickerController;
        init();
        setSelectedDay(this.mController.getSelectedDay());
        setHasStableIds(true);
    }

    public void setSelectedDay(CalendarDay calendarDay) {
        this.mSelectedDay = calendarDay;
        notifyDataSetChanged();
    }

    public CalendarDay getSelectedDay() {
        return this.mSelectedDay;
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.mSelectedDay = new CalendarDay(System.currentTimeMillis(), this.mController.getTimeZone());
    }

    public MonthViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MonthView createMonthView = createMonthView(viewGroup.getContext());
        createMonthView.setLayoutParams(new LayoutParams(-1, -1));
        createMonthView.setClickable(true);
        createMonthView.setOnDayClickListener(this);
        return new MonthViewHolder(createMonthView);
    }

    public void onBindViewHolder(MonthViewHolder monthViewHolder, int i) {
        monthViewHolder.bind(i, this.mController, this.mSelectedDay);
    }

    public int getItemCount() {
        Calendar endDate = this.mController.getEndDate();
        Calendar startDate = this.mController.getStartDate();
        return (((endDate.get(1) * 12) + endDate.get(2)) - ((startDate.get(1) * 12) + startDate.get(2))) + 1;
    }

    public void onDayClick(MonthView monthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    /* access modifiers changed from: protected */
    public void onDayTapped(CalendarDay calendarDay) {
        this.mController.tryVibrate();
        this.mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }
}
