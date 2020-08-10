package com.google.firebase.database.connection;

import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RangeMerge {
    private final List<String> optExclusiveStart;
    private final List<String> optInclusiveEnd;
    private final Object snap;

    public RangeMerge(List<String> list, List<String> list2, Object obj) {
        this.optExclusiveStart = list;
        this.optInclusiveEnd = list2;
        this.snap = obj;
    }

    public List<String> getOptExclusiveStart() {
        return this.optExclusiveStart;
    }

    public List<String> getOptInclusiveEnd() {
        return this.optInclusiveEnd;
    }

    public Object getSnap() {
        return this.snap;
    }
}
