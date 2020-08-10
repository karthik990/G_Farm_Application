package com.mobiroller.models.events;

public class LoginEvent {
    public String screenId;

    public LoginEvent(String str) {
        if (str != null) {
            this.screenId = str;
        }
    }

    public LoginEvent() {
    }
}
