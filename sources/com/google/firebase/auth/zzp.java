package com.google.firebase.auth;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.zzk;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

final /* synthetic */ class zzp implements ComponentFactory {
    static final ComponentFactory zzjd = new zzp();

    private zzp() {
    }

    public final Object create(ComponentContainer componentContainer) {
        return new zzk((FirebaseApp) componentContainer.get(FirebaseApp.class));
    }
}
