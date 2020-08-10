package com.rometools.rome.feed.module;

import java.util.Date;

public interface SyModule extends Module {
    public static final String DAILY = "daily";
    public static final String HOURLY = "hourly";
    public static final String MONTHLY = "monthly";
    public static final String URI = "http://purl.org/rss/1.0/modules/syndication/";
    public static final String WEEKLY = "weekly";
    public static final String YEARLY = "yearly";

    Date getUpdateBase();

    int getUpdateFrequency();

    String getUpdatePeriod();

    void setUpdateBase(Date date);

    void setUpdateFrequency(int i);

    void setUpdatePeriod(String str);
}
