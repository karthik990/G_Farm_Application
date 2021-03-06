package com.google.firebase.abt.component;

import android.content.Context;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import java.util.Arrays;
import java.util.List;

public class AbtRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(AbtComponent.class).add(Dependency.required(Context.class)).add(Dependency.optional(AnalyticsConnector.class)).factory(zza.zzm).build()});
    }
}
