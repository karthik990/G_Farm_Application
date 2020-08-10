package com.startapp.android.publish.cache;

import com.startapp.common.p042c.C2362f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: StartAppSDK */
public class FailuresHandler implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean infiniteLastRetry = true;
    @C2362f(mo20786b = ArrayList.class, mo20787c = Integer.class)
    private List<Integer> intervals = Arrays.asList(new Integer[]{Integer.valueOf(10), Integer.valueOf(30), Integer.valueOf(60), Integer.valueOf(300)});

    public List<Integer> getIntervals() {
        return this.intervals;
    }

    public boolean isInfiniteLastRetry() {
        return this.infiniteLastRetry;
    }
}
