package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.wdullaer.materialdatetimepicker.C5266R;

public class YearPickerView extends ListView implements OnItemClickListener, OnDateChangedListener {
    private static final String TAG = "YearPickerView";
    private YearAdapter mAdapter;
    private int mChildSize;
    /* access modifiers changed from: private */
    public final DatePickerController mController;
    /* access modifiers changed from: private */
    public TextViewWithCircularIndicator mSelectedView;
    private int mViewSize;

    private final class YearAdapter extends BaseAdapter {
        private final int mMaxYear;
        private final int mMinYear;

        public long getItemId(int i) {
            return (long) i;
        }

        YearAdapter(int i, int i2) {
            if (i <= i2) {
                this.mMinYear = i;
                this.mMaxYear = i2;
                return;
            }
            throw new IllegalArgumentException("minYear > maxYear");
        }

        public int getCount() {
            return (this.mMaxYear - this.mMinYear) + 1;
        }

        public Object getItem(int i) {
            return Integer.valueOf(this.mMinYear + i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            TextViewWithCircularIndicator textViewWithCircularIndicator;
            boolean z = false;
            if (view != null) {
                textViewWithCircularIndicator = (TextViewWithCircularIndicator) view;
            } else {
                textViewWithCircularIndicator = (TextViewWithCircularIndicator) LayoutInflater.from(viewGroup.getContext()).inflate(C5266R.layout.mdtp_year_label_text_view, viewGroup, false);
                textViewWithCircularIndicator.setAccentColor(YearPickerView.this.mController.getAccentColor(), YearPickerView.this.mController.isThemeDark());
            }
            int i2 = this.mMinYear + i;
            if (YearPickerView.this.mController.getSelectedDay().year == i2) {
                z = true;
            }
            textViewWithCircularIndicator.setText(String.valueOf(i2));
            textViewWithCircularIndicator.drawIndicator(z);
            textViewWithCircularIndicator.requestLayout();
            if (z) {
                YearPickerView.this.mSelectedView = textViewWithCircularIndicator;
            }
            return textViewWithCircularIndicator;
        }
    }

    public YearPickerView(Context context, DatePickerController datePickerController) {
        super(context);
        this.mController = datePickerController;
        this.mController.registerOnDateChangedListener(this);
        setLayoutParams(new LayoutParams(-1, -2));
        Resources resources = context.getResources();
        this.mViewSize = resources.getDimensionPixelOffset(C5266R.dimen.mdtp_date_picker_view_animator_height);
        this.mChildSize = resources.getDimensionPixelOffset(C5266R.dimen.mdtp_year_label_height);
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(this.mChildSize / 3);
        init();
        setOnItemClickListener(this);
        setSelector(new StateListDrawable());
        setDividerHeight(0);
        onDateChanged();
    }

    private void init() {
        this.mAdapter = new YearAdapter(this.mController.getMinYear(), this.mController.getMaxYear());
        setAdapter(this.mAdapter);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.mController.tryVibrate();
        TextViewWithCircularIndicator textViewWithCircularIndicator = (TextViewWithCircularIndicator) view;
        if (textViewWithCircularIndicator != null) {
            TextViewWithCircularIndicator textViewWithCircularIndicator2 = this.mSelectedView;
            if (textViewWithCircularIndicator != textViewWithCircularIndicator2) {
                if (textViewWithCircularIndicator2 != null) {
                    textViewWithCircularIndicator2.drawIndicator(false);
                    this.mSelectedView.requestLayout();
                }
                textViewWithCircularIndicator.drawIndicator(true);
                textViewWithCircularIndicator.requestLayout();
                this.mSelectedView = textViewWithCircularIndicator;
            }
            this.mController.onYearSelected(getYearFromTextView(textViewWithCircularIndicator));
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private static int getYearFromTextView(TextView textView) {
        return Integer.valueOf(textView.getText().toString()).intValue();
    }

    public void postSetSelectionCentered(int i) {
        postSetSelectionFromTop(i, (this.mViewSize / 2) - (this.mChildSize / 2));
    }

    public void postSetSelectionFromTop(final int i, final int i2) {
        post(new Runnable() {
            public void run() {
                YearPickerView.this.setSelectionFromTop(i, i2);
                YearPickerView.this.requestLayout();
            }
        });
    }

    public int getFirstPositionOffset() {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return childAt.getTop();
    }

    public void onDateChanged() {
        this.mAdapter.notifyDataSetChanged();
        postSetSelectionCentered(this.mController.getSelectedDay().year - this.mController.getMinYear());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4096) {
            accessibilityEvent.setFromIndex(0);
            accessibilityEvent.setToIndex(0);
        }
    }
}
