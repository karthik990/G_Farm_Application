package com.google.firebase.auth;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

public class FirebaseAuthRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseAuth.class, InternalAuthProvider.class).add(Dependency.required(FirebaseApp.class)).factory(zzp.zzjd).alwaysEager().build(), LibraryVersionComponent.create("fire-auth", "17.0.0")});
    }
}
