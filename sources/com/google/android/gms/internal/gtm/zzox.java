package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.List;

public final class zzox {
    private final List<zzot> zzatu;
    private final List<zzot> zzatv;
    private final List<zzot> zzatw;
    private final List<zzot> zzatx;
    private final List<zzot> zzava;
    private final List<zzot> zzavb;
    private final List<String> zzavc;
    private final List<String> zzavd;
    private final List<String> zzave;
    private final List<String> zzavf;

    private zzox(List<zzot> list, List<zzot> list2, List<zzot> list3, List<zzot> list4, List<zzot> list5, List<zzot> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
        this.zzatu = Collections.unmodifiableList(list);
        this.zzatv = Collections.unmodifiableList(list2);
        this.zzatw = Collections.unmodifiableList(list3);
        this.zzatx = Collections.unmodifiableList(list4);
        this.zzava = Collections.unmodifiableList(list5);
        this.zzavb = Collections.unmodifiableList(list6);
        this.zzavc = Collections.unmodifiableList(list7);
        this.zzavd = Collections.unmodifiableList(list8);
        this.zzave = Collections.unmodifiableList(list9);
        this.zzavf = Collections.unmodifiableList(list10);
    }

    public final List<zzot> zzlw() {
        return this.zzatu;
    }

    public final List<zzot> zzlx() {
        return this.zzatv;
    }

    public final List<zzot> zzly() {
        return this.zzatw;
    }

    public final List<zzot> zzlz() {
        return this.zzatx;
    }

    public final List<zzot> zzmq() {
        return this.zzava;
    }

    public final List<zzot> zzmr() {
        return this.zzavb;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzatu);
        String valueOf2 = String.valueOf(this.zzatv);
        String valueOf3 = String.valueOf(this.zzatw);
        String valueOf4 = String.valueOf(this.zzatx);
        String valueOf5 = String.valueOf(this.zzava);
        String valueOf6 = String.valueOf(this.zzavb);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 102 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length());
        sb.append("Positive predicates: ");
        sb.append(valueOf);
        sb.append("  Negative predicates: ");
        sb.append(valueOf2);
        sb.append("  Add tags: ");
        sb.append(valueOf3);
        sb.append("  Remove tags: ");
        sb.append(valueOf4);
        sb.append("  Add macros: ");
        sb.append(valueOf5);
        sb.append("  Remove macros: ");
        sb.append(valueOf6);
        return sb.toString();
    }
}
