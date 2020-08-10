package com.firebase.p037ui.common;

/* renamed from: com.firebase.ui.common.BaseChangeEventListener */
public interface BaseChangeEventListener<S, E> {
    void onChildChanged(ChangeEventType changeEventType, S s, int i, int i2);

    void onDataChanged();

    void onError(E e);
}
