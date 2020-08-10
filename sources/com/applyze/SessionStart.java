package com.applyze;

class SessionStart {
    String apiKey;
    String appKey;
    boolean onGoing = false;

    SessionStart(String str, String str2) {
        this.apiKey = str;
        this.appKey = str2;
    }

    SessionStart(String str, String str2, boolean z) {
        this.apiKey = str;
        this.appKey = str2;
        this.onGoing = z;
    }
}
