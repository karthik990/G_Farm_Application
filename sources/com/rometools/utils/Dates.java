package com.rometools.utils;

import java.util.Date;

public final class Dates {
    private Dates() {
    }

    public static Date copy(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }
}
