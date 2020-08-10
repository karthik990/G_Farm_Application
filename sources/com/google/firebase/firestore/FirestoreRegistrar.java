package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FirestoreRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirestoreMultiDbComponent.class).add(Dependency.required(FirebaseApp.class)).add(Dependency.required(Context.class)).add(Dependency.optional(InternalAuthProvider.class)).factory(FirestoreRegistrar$$Lambda$1.lambdaFactory$()).build(), LibraryVersionComponent.create("fire-fst", BuildConfig.VERSION_NAME)});
    }

    static /* synthetic */ FirestoreMultiDbComponent lambda$getComponents$0(ComponentContainer componentContainer) {
        return new FirestoreMultiDbComponent((Context) componentContainer.get(Context.class), (FirebaseApp) componentContainer.get(FirebaseApp.class), (InternalAuthProvider) componentContainer.get(InternalAuthProvider.class));
    }
}
