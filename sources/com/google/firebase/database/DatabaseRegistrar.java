package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseDatabaseComponent.class).add(Dependency.required(FirebaseApp.class)).add(Dependency.optional(InternalAuthProvider.class)).factory(DatabaseRegistrar$$Lambda$1.lambdaFactory$()).build(), LibraryVersionComponent.create("fire-rtdb", "17.0.0")});
    }

    static /* synthetic */ FirebaseDatabaseComponent lambda$getComponents$0(ComponentContainer componentContainer) {
        return new FirebaseDatabaseComponent((FirebaseApp) componentContainer.get(FirebaseApp.class), (InternalAuthProvider) componentContainer.get(InternalAuthProvider.class));
    }
}
