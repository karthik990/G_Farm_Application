package com.mobiroller.helpers;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.mobiroller.mobi942763453128.R;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class TimeHelper {
    private static final int DAY_MILLIS = 86400000;
    private static final int HOUR_MILLIS = 3600000;
    private static final int MINUTE_MILLIS = 60000;
    private static final int SECOND_MILLIS = 1000;
    private static String regex2two = "(?<=[^\\d])(\\d)(?=[^\\d])";
    private static HashMap<String, String> regexMap = new HashMap<>();
    private static String two = "0$1";

    public static String getTimeAgo(long j, Context context) {
        if (j < 1000000000000L) {
            j *= 1000;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (j > currentTimeMillis || j <= 0) {
            return null;
        }
        long j2 = currentTimeMillis - j;
        if (j2 < DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS) {
            return context.getString(R.string.time_just_now);
        }
        if (j2 < 120000) {
            return context.getString(R.string.time_a_minute_ago);
        }
        String str = " ";
        if (j2 < 3000000) {
            StringBuilder sb = new StringBuilder();
            sb.append(j2 / DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
            sb.append(str);
            sb.append(context.getString(R.string.time_minute_ago));
            return sb.toString();
        } else if (j2 < 5400000) {
            return context.getString(R.string.time_an_hour_ago);
        } else {
            if (j2 < 86400000) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(j2 / 3600000);
                sb2.append(str);
                sb2.append(context.getString(R.string.time_hours_ago));
                return sb2.toString();
            } else if (j2 < 172800000) {
                return context.getString(R.string.time_yesterday);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(j2 / 86400000);
                sb3.append(str);
                sb3.append(context.getString(R.string.time_days_ago));
                return sb3.toString();
            }
        }
    }

    public static String getDuration(String str) {
        if (regexMap.size() == 0) {
            regexMap.put("PT(\\d\\d)S", "00:$1");
            regexMap.put("PT(\\d\\d)M", "$1:00");
            regexMap.put("PT(\\d\\d)H", "$1:00:00");
            regexMap.put("PT(\\d\\d)M(\\d\\d)S", "$1:$2");
            regexMap.put("PT(\\d\\d)H(\\d\\d)S", "$1:00:$2");
            regexMap.put("PT(\\d\\d)H(\\d\\d)M", "$1:$2:00");
            regexMap.put("PT(\\d\\d)H(\\d\\d)M(\\d\\d)S", "$1:$2:$3");
        }
        String replaceAll = str.replaceAll(regex2two, two);
        String regex = getRegex(replaceAll);
        if (regex == null) {
            return "";
        }
        return replaceAll.replaceAll(regex, (String) regexMap.get(regex));
    }

    private static String getRegex(String str) {
        for (String str2 : regexMap.keySet()) {
            if (Pattern.matches(str2, str)) {
                return str2;
            }
        }
        return null;
    }

    public static String getTimeAgo(Context context, long j) {
        long timeInMillis = (Calendar.getInstance().getTimeInMillis() / 1000) - (j / 1000);
        if (timeInMillis <= 60) {
            return context.getString(R.string.time_just_now);
        }
        int round = Math.round((float) (timeInMillis / 60));
        String str = " ";
        if (round > 60) {
            int round2 = Math.round((float) (timeInMillis / 3600));
            if (round2 > 24) {
                int round3 = Math.round((float) (timeInMillis / 86400));
                if (round3 > 7) {
                    int round4 = Math.round((float) (timeInMillis / 604800));
                    if (((double) round4) > 4.3d) {
                        int round5 = Math.round((float) (timeInMillis / 2600640));
                        if (round5 > 12) {
                            int round6 = Math.round((float) (timeInMillis / 31207680));
                            if (round6 == 1) {
                                return context.getString(R.string.time_a_year_ago);
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(round6);
                            sb.append(str);
                            sb.append(context.getString(R.string.time_years_ago));
                            return sb.toString();
                        } else if (round5 == 1) {
                            return context.getString(R.string.time_a_month_ago);
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(round5);
                            sb2.append(str);
                            sb2.append(context.getString(R.string.time_months_ago));
                            return sb2.toString();
                        }
                    } else if (round4 == 1) {
                        return context.getString(R.string.time_a_week_ago);
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(round4);
                        sb3.append(str);
                        sb3.append(context.getString(R.string.time_weeks_ago));
                        return sb3.toString();
                    }
                } else if (round3 == 1) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("1 ");
                    sb4.append(context.getString(R.string.time_day_ago));
                    return sb4.toString();
                } else {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(round3);
                    sb5.append(str);
                    sb5.append(context.getString(R.string.time_days_ago));
                    return sb5.toString();
                }
            } else if (round2 == 1) {
                return context.getString(R.string.time_an_hour_ago);
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(round2);
                sb6.append(str);
                sb6.append(context.getString(R.string.time_hours_ago));
                return sb6.toString();
            }
        } else if (round == 1) {
            return context.getString(R.string.time_a_minute_ago);
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(round);
            sb7.append(str);
            sb7.append(context.getString(R.string.time_minute_ago));
            return sb7.toString();
        }
    }
}
