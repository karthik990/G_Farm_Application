package com.mobiroller.models.events;

public class UserBlockEvent {
    public int from;
    public boolean isBlocked;
    public String uid;

    public UserBlockEvent(String str, boolean z, int i) {
        this.uid = str;
        this.from = i;
        this.isBlocked = z;
    }
}
