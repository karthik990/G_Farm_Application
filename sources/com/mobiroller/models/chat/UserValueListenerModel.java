package com.mobiroller.models.chat;

import com.google.firebase.database.ValueEventListener;

public class UserValueListenerModel {
    public ValueEventListener listener;
    public String uid;

    public UserValueListenerModel(ValueEventListener valueEventListener, String str) {
        this.listener = valueEventListener;
        this.uid = str;
    }
}
