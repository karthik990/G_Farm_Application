package com.rometools.rome.feed.module;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.utils.Dates;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SyModuleImpl extends ModuleImpl implements SyModule {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final Set<String> PERIODS = new HashSet();
    private static final long serialVersionUID = 1;
    private Date updateBase;
    private int updateFrequency;
    private String updatePeriod;

    static {
        PERIODS.add(SyModule.HOURLY);
        PERIODS.add(SyModule.DAILY);
        PERIODS.add(SyModule.WEEKLY);
        PERIODS.add(SyModule.MONTHLY);
        PERIODS.add(SyModule.YEARLY);
        HashMap hashMap = new HashMap();
        hashMap.put("updatePeriod", String.class);
        hashMap.put("updateFrequency", Integer.TYPE);
        hashMap.put("updateBase", Date.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyModule.class, hashMap, Collections.emptyMap());
    }

    public SyModuleImpl() {
        super(SyModule.class, SyModule.URI);
    }

    public String getUpdatePeriod() {
        return this.updatePeriod;
    }

    public void setUpdatePeriod(String str) {
        if (PERIODS.contains(str)) {
            this.updatePeriod = str;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid period [");
        sb.append(str);
        sb.append("]");
        throw new IllegalArgumentException(sb.toString());
    }

    public int getUpdateFrequency() {
        return this.updateFrequency;
    }

    public void setUpdateFrequency(int i) {
        this.updateFrequency = i;
    }

    public Date getUpdateBase() {
        return Dates.copy(this.updateBase);
    }

    public void setUpdateBase(Date date) {
        this.updateBase = Dates.copy(date);
    }

    public Class<? extends Module> getInterface() {
        return SyModule.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
