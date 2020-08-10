package com.twitter.sdk.android.tweetui;

import android.content.res.Resources;
import androidx.collection.SparseArrayCompat;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

final class TweetDateUtils {
    static final SimpleDateFormat DATE_TIME_RFC822 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    static final long INVALID_DATE = -1;
    static final DateFormatter RELATIVE_DATE_FORMAT = new DateFormatter();

    static class DateFormatter {
        private Locale currentLocale;
        private final SparseArrayCompat<SimpleDateFormat> dateFormatArray = new SparseArrayCompat<>();

        DateFormatter() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized String formatLongDateString(Resources resources, Date date) {
            return getDateFormat(resources, C5234R.C5238string.tw__relative_date_format_long).format(date);
        }

        /* access modifiers changed from: 0000 */
        public synchronized String formatShortDateString(Resources resources, Date date) {
            return getDateFormat(resources, C5234R.C5238string.tw__relative_date_format_short).format(date);
        }

        private synchronized DateFormat getDateFormat(Resources resources, int i) {
            SimpleDateFormat simpleDateFormat;
            if (this.currentLocale == null || this.currentLocale != resources.getConfiguration().locale) {
                this.currentLocale = resources.getConfiguration().locale;
                this.dateFormatArray.clear();
            }
            simpleDateFormat = (SimpleDateFormat) this.dateFormatArray.get(i);
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat(resources.getString(i), Locale.getDefault());
                this.dateFormatArray.put(i, simpleDateFormat);
            }
            return simpleDateFormat;
        }
    }

    private TweetDateUtils() {
    }

    static long apiTimeToLong(String str) {
        long j = -1;
        if (str == null) {
            return -1;
        }
        try {
            j = DATE_TIME_RFC822.parse(str).getTime();
        } catch (ParseException unused) {
        }
        return j;
    }

    static boolean isValidTimestamp(String str) {
        return apiTimeToLong(str) != -1;
    }

    static String dotPrefix(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("• ");
        sb.append(str);
        return sb.toString();
    }

    static String getRelativeTimeString(Resources resources, long j, long j2) {
        long j3 = j - j2;
        if (j3 < 0) {
            return RELATIVE_DATE_FORMAT.formatLongDateString(resources, new Date(j2));
        }
        if (j3 < DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS) {
            int i = (int) (j3 / 1000);
            return resources.getQuantityString(C5234R.plurals.tw__time_secs, i, new Object[]{Integer.valueOf(i)});
        } else if (j3 < 3600000) {
            int i2 = (int) (j3 / DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
            return resources.getQuantityString(C5234R.plurals.tw__time_mins, i2, new Object[]{Integer.valueOf(i2)});
        } else if (j3 < 86400000) {
            int i3 = (int) (j3 / 3600000);
            return resources.getQuantityString(C5234R.plurals.tw__time_hours, i3, new Object[]{Integer.valueOf(i3)});
        } else {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(j);
            Calendar instance2 = Calendar.getInstance();
            instance2.setTimeInMillis(j2);
            Date date = new Date(j2);
            if (instance.get(1) == instance2.get(1)) {
                return RELATIVE_DATE_FORMAT.formatShortDateString(resources, date);
            }
            return RELATIVE_DATE_FORMAT.formatLongDateString(resources, date);
        }
    }
}
