package com.google.firebase.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzan;
import com.google.android.gms.measurement.internal.zzbw;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorHandle;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorListener;
import com.google.firebase.analytics.connector.AnalyticsConnector.ConditionalUserProperty;
import com.google.firebase.analytics.connector.internal.zza;
import com.google.firebase.analytics.connector.internal.zzc;
import com.google.firebase.analytics.connector.internal.zzd;
import com.google.firebase.analytics.connector.internal.zzf;
import com.google.firebase.events.Event;
import com.google.firebase.events.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AnalyticsConnectorImpl implements AnalyticsConnector {
    private static volatile AnalyticsConnector zzbsr;
    private final AppMeasurement zzbss;
    final Map<String, zza> zzbst = new ConcurrentHashMap();

    private AnalyticsConnectorImpl(AppMeasurement appMeasurement) {
        Preconditions.checkNotNull(appMeasurement);
        this.zzbss = appMeasurement;
    }

    public static AnalyticsConnector getInstance(FirebaseApp firebaseApp, Context context, Subscriber subscriber) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzbsr == null) {
            synchronized (AnalyticsConnectorImpl.class) {
                if (zzbsr == null) {
                    Bundle bundle = new Bundle(1);
                    if (firebaseApp.isDefaultApp()) {
                        subscriber.subscribe(DataCollectionDefaultChange.class, zza.zzbsu, zzb.zzbsv);
                        bundle.putBoolean("dataCollectionDefaultEnabled", firebaseApp.isDataCollectionDefaultEnabled());
                    }
                    zzbsr = new AnalyticsConnectorImpl(zzbw.zza(context, zzan.zzc(bundle)).zzkm());
                }
            }
        }
        return zzbsr;
    }

    public static AnalyticsConnector getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static AnalyticsConnector getInstance(FirebaseApp firebaseApp) {
        return (AnalyticsConnector) firebaseApp.get(AnalyticsConnector.class);
    }

    public void logEvent(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (zzc.zzft(str) && zzc.zza(str2, bundle) && zzc.zzb(str, str2, bundle)) {
            this.zzbss.logEventInternal(str, str2, bundle);
        }
    }

    public void setUserProperty(String str, String str2, Object obj) {
        if (zzc.zzft(str) && zzc.zzz(str, str2)) {
            this.zzbss.setUserPropertyInternal(str, str2, obj);
        }
    }

    public Map<String, Object> getUserProperties(boolean z) {
        return this.zzbss.getUserProperties(z);
    }

    public AnalyticsConnectorHandle registerAnalyticsConnectorListener(final String str, AnalyticsConnectorListener analyticsConnectorListener) {
        Preconditions.checkNotNull(analyticsConnectorListener);
        if (!zzc.zzft(str) || zzfs(str)) {
            return null;
        }
        AppMeasurement appMeasurement = this.zzbss;
        Object obj = AppMeasurement.FIAM_ORIGIN.equals(str) ? new zzd(appMeasurement, analyticsConnectorListener) : AppMeasurement.CRASH_ORIGIN.equals(str) ? new zzf(appMeasurement, analyticsConnectorListener) : null;
        if (obj == null) {
            return null;
        }
        this.zzbst.put(str, obj);
        return new AnalyticsConnectorHandle() {
            public void unregister() {
                if (AnalyticsConnectorImpl.this.zzfs(str)) {
                    AnalyticsConnectorListener zztv = ((zza) AnalyticsConnectorImpl.this.zzbst.get(str)).zztv();
                    if (zztv != null) {
                        zztv.onMessageTriggered(0, null);
                    }
                    AnalyticsConnectorImpl.this.zzbst.remove(str);
                }
            }

            public void registerEventNames(Set<String> set) {
                if (AnalyticsConnectorImpl.this.zzfs(str) && str.equals(AppMeasurement.FIAM_ORIGIN) && set != null && !set.isEmpty()) {
                    ((zza) AnalyticsConnectorImpl.this.zzbst.get(str)).registerEventNames(set);
                }
            }

            public void unregisterEventNames() {
                if (AnalyticsConnectorImpl.this.zzfs(str) && str.equals(AppMeasurement.FIAM_ORIGIN)) {
                    ((zza) AnalyticsConnectorImpl.this.zzbst.get(str)).unregisterEventNames();
                }
            }
        };
    }

    public void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        if (zzc.zza(conditionalUserProperty)) {
            this.zzbss.setConditionalUserProperty(zzc.zzb(conditionalUserProperty));
        }
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        if (str2 == null || zzc.zza(str2, bundle)) {
            this.zzbss.clearConditionalUserProperty(str, str2, bundle);
        }
    }

    public List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        for (AppMeasurement.ConditionalUserProperty zzd : this.zzbss.getConditionalUserProperties(str, str2)) {
            arrayList.add(zzc.zzd(zzd));
        }
        return arrayList;
    }

    public int getMaxUserProperties(String str) {
        return this.zzbss.getMaxUserProperties(str);
    }

    /* access modifiers changed from: private */
    public final boolean zzfs(String str) {
        return !str.isEmpty() && this.zzbst.containsKey(str) && this.zzbst.get(str) != null;
    }

    static final /* synthetic */ void zza(Event event) {
        boolean z = ((DataCollectionDefaultChange) event.getPayload()).enabled;
        synchronized (AnalyticsConnectorImpl.class) {
            ((AnalyticsConnectorImpl) zzbsr).zzbss.zzd(z);
        }
    }
}
