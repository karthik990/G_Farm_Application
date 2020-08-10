package com.startapp.android.publish.cache;

import java.io.Serializable;

/* renamed from: com.startapp.android.publish.cache.h */
/* compiled from: StartAppSDK */
public class C5091h implements Serializable {
    private static final long serialVersionUID = 1;
    private String filename;
    private long lastUseTimestamp;
    private String videoLocation;

    public C5091h(String str) {
        this.filename = str;
    }

    /* renamed from: a */
    public String mo62517a() {
        return this.filename;
    }

    /* renamed from: b */
    public String mo62520b() {
        return this.videoLocation;
    }

    /* renamed from: a */
    public void mo62519a(String str) {
        this.videoLocation = str;
    }

    /* renamed from: a */
    public void mo62518a(long j) {
        this.lastUseTimestamp = j;
    }

    public int hashCode() {
        int i;
        String str = this.filename;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        return 31 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C5091h hVar = (C5091h) obj;
        String str = this.filename;
        if (str == null) {
            if (hVar.filename != null) {
                return false;
            }
        } else if (!str.equals(hVar.filename)) {
            return false;
        }
        return true;
    }
}
