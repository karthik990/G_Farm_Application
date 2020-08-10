package com.mobiroller.models.events;

import android.view.KeyEvent;

public class VolumeButtonEvent {
    int keyCode;
    KeyEvent keyEvent;

    public VolumeButtonEvent(KeyEvent keyEvent2, int i) {
        this.keyEvent = keyEvent2;
        this.keyCode = i;
    }

    public KeyEvent getKeyEvent() {
        return this.keyEvent;
    }

    public void setKeyEvent(KeyEvent keyEvent2) {
        this.keyEvent = keyEvent2;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int i) {
        this.keyCode = i;
    }
}
