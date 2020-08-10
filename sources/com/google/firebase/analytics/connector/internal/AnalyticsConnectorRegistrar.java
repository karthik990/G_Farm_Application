package com.google.firebase.analytics.connector.internal;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.events.Subscriber;
import java.util.Collections;
import java.util.List;

public class AnalyticsConnectorRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Collections.singletonList(Component.builder(AnalyticsConnector.class).add(Dependency.required(FirebaseApp.class)).add(Dependency.required(Context.class)).add(Dependency.required(Subscriber.class)).factory(zzb.zzbsx).eagerInDefaultApp().build());
    }
}
