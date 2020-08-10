package com.google.firebase.abt.component;

import android.content.Context;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.HashMap;
import java.util.Map;

public class AbtComponent {
    private final AnalyticsConnector zzh;
    private final Map<String, FirebaseABTesting> zzk = new HashMap();
    private final Context zzl;

    protected AbtComponent(Context context, AnalyticsConnector analyticsConnector) {
        this.zzl = context;
        this.zzh = analyticsConnector;
    }

    public synchronized FirebaseABTesting get(String str) {
        if (!this.zzk.containsKey(str)) {
            this.zzk.put(str, new FirebaseABTesting(this.zzl, this.zzh, str));
        }
        return (FirebaseABTesting) this.zzk.get(str);
    }
}
