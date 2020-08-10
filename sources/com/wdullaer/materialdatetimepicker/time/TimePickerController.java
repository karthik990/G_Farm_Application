package com.wdullaer.materialdatetimepicker.time;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;
import com.wdullaer.materialdatetimepicker.time.Timepoint.TYPE;

public interface TimePickerController {
    int getAccentColor();

    Version getVersion();

    boolean is24HourMode();

    boolean isAmDisabled();

    boolean isOutOfRange(Timepoint timepoint, int i);

    boolean isPmDisabled();

    boolean isThemeDark();

    Timepoint roundToNearest(Timepoint timepoint, TYPE type);

    void tryVibrate();
}
