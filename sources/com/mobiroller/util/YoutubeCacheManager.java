package com.mobiroller.util;

public class YoutubeCacheManager {
    private static final long ONE_MINUTE_CACHE = 6000;
    private static TimedCache mTimedCache = new TimedCache(50, ONE_MINUTE_CACHE);

    public static String getLikeStatus(String str) {
        String str2 = (String) mTimedCache.getIfNotExpired(str, ONE_MINUTE_CACHE);
        return str2 != null ? str2 : "null";
    }

    public static void putLikeStatus(String str, String str2) {
        mTimedCache.put(str, str2);
    }
}
