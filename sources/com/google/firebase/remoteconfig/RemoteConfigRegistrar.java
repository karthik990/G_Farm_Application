package com.google.firebase.remoteconfig;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.abt.component.AbtComponent;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.Arrays;
import java.util.List;

public class RemoteConfigRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(zzg.class).add(Dependency.required(Context.class)).add(Dependency.required(FirebaseApp.class)).add(Dependency.required(FirebaseInstanceId.class)).add(Dependency.required(AbtComponent.class)).add(Dependency.optional(AnalyticsConnector.class)).factory(zzk.zzjx).alwaysEager().build()});
    }
}
