package com.google.firebase.abt;

import android.content.Context;
import android.text.TextUtils;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.analytics.connector.AnalyticsConnector.ConditionalUserProperty;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FirebaseABTesting {
    private final AnalyticsConnector zzh;
    private final String zzi;
    private Integer zzj = null;

    public FirebaseABTesting(Context context, AnalyticsConnector analyticsConnector, String str) {
        this.zzh = analyticsConnector;
        this.zzi = str;
    }

    public void replaceAllExperiments(List<Map<String, String>> list) throws AbtException {
        String str;
        zzg();
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (Map zza : list) {
                arrayList.add(zza.zza(zza));
            }
            if (arrayList.isEmpty()) {
                removeAllExperiments();
                return;
            }
            HashSet hashSet = new HashSet();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                hashSet.add(((zza) obj).zza());
            }
            List<ConditionalUserProperty> zzh2 = zzh();
            HashSet hashSet2 = new HashSet();
            for (ConditionalUserProperty conditionalUserProperty : zzh2) {
                hashSet2.add(conditionalUserProperty.name);
            }
            ArrayList arrayList3 = new ArrayList();
            for (ConditionalUserProperty conditionalUserProperty2 : zzh2) {
                if (!hashSet.contains(conditionalUserProperty2.name)) {
                    arrayList3.add(conditionalUserProperty2);
                }
            }
            zza((Collection<ConditionalUserProperty>) arrayList3);
            ArrayList arrayList4 = new ArrayList();
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                zza zza2 = (zza) obj2;
                if (!hashSet2.contains(zza2.zza())) {
                    arrayList4.add(zza2);
                }
            }
            ArrayDeque arrayDeque = new ArrayDeque(zzh());
            if (this.zzj == null) {
                this.zzj = Integer.valueOf(this.zzh.getMaxUserProperties(this.zzi));
            }
            int intValue = this.zzj.intValue();
            ArrayList arrayList5 = arrayList4;
            int size3 = arrayList5.size();
            while (i < size3) {
                Object obj3 = arrayList5.get(i);
                i++;
                zza zza3 = (zza) obj3;
                while (arrayDeque.size() >= intValue) {
                    zza(((ConditionalUserProperty) arrayDeque.pollFirst()).name);
                }
                ConditionalUserProperty conditionalUserProperty3 = new ConditionalUserProperty();
                conditionalUserProperty3.origin = this.zzi;
                conditionalUserProperty3.creationTimestamp = zza3.zzd();
                conditionalUserProperty3.name = zza3.zza();
                conditionalUserProperty3.value = zza3.zzb();
                if (TextUtils.isEmpty(zza3.zzc())) {
                    str = null;
                } else {
                    str = zza3.zzc();
                }
                conditionalUserProperty3.triggerEventName = str;
                conditionalUserProperty3.triggerTimeout = zza3.zze();
                conditionalUserProperty3.timeToLive = zza3.zzf();
                this.zzh.setConditionalUserProperty(conditionalUserProperty3);
                arrayDeque.offer(conditionalUserProperty3);
            }
            return;
        }
        throw new IllegalArgumentException("The replacementExperiments list is null.");
    }

    public void removeAllExperiments() throws AbtException {
        zzg();
        zza((Collection<ConditionalUserProperty>) zzh());
    }

    private final void zza(Collection<ConditionalUserProperty> collection) {
        for (ConditionalUserProperty conditionalUserProperty : collection) {
            zza(conditionalUserProperty.name);
        }
    }

    private final void zzg() throws AbtException {
        if (this.zzh == null) {
            throw new AbtException("The Analytics SDK is not available. Please check that the Analytics SDK is included in your app dependencies.");
        }
    }

    private final void zza(String str) {
        this.zzh.clearConditionalUserProperty(str, null, null);
    }

    private final List<ConditionalUserProperty> zzh() {
        return this.zzh.getConditionalUserProperties(this.zzi, "");
    }
}
