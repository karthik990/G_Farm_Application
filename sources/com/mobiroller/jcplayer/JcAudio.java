package com.mobiroller.jcplayer;

import java.io.Serializable;

public class JcAudio implements Serializable {

    /* renamed from: id */
    private long f2166id;
    private Origin origin;
    private String path;
    private int position;
    private String title;

    public JcAudio(String str, String str2, Origin origin2) {
        this.f2166id = -1;
        this.position = -1;
        this.title = str;
        this.path = str2;
        this.origin = origin2;
    }

    public JcAudio(String str) {
        this.path = str;
    }

    public JcAudio(String str, String str2, long j, int i, Origin origin2) {
        this.f2166id = j;
        this.position = i;
        this.title = str;
        this.path = str2;
        this.origin = origin2;
    }

    public long getId() {
        return this.f2166id;
    }

    public void setId(long j) {
        this.f2166id = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public static JcAudio createFromURL(String str, String str2) {
        return new JcAudio(str, str2, Origin.URL);
    }
}
