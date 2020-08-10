package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzan;
import com.google.android.gms.measurement.internal.zzbw;
import com.google.android.gms.measurement.internal.zzcu;
import com.google.android.gms.measurement.internal.zzcv;
import com.google.android.gms.measurement.internal.zzcw;
import com.google.android.gms.measurement.internal.zzcx;
import com.google.android.gms.measurement.internal.zzcy;
import com.google.android.gms.measurement.internal.zzdw;
import com.google.android.gms.measurement.internal.zzfu;
import java.util.List;
import java.util.Map;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@Deprecated
public class AppMeasurement {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    public static final String FIAM_ORIGIN = "fiam";
    private final zzbw zzada;

    public static class ConditionalUserProperty {
        public boolean mActive;
        public String mAppId;
        public long mCreationTimestamp;
        public String mExpiredEventName;
        public Bundle mExpiredEventParams;
        public String mName;
        public String mOrigin;
        public long mTimeToLive;
        public String mTimedOutEventName;
        public Bundle mTimedOutEventParams;
        public String mTriggerEventName;
        public long mTriggerTimeout;
        public String mTriggeredEventName;
        public Bundle mTriggeredEventParams;
        public long mTriggeredTimestamp;
        public Object mValue;

        public ConditionalUserProperty() {
        }

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
            Preconditions.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            Object obj = conditionalUserProperty.mValue;
            if (obj != null) {
                this.mValue = zzdw.zze(obj);
                if (this.mValue == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            Bundle bundle = conditionalUserProperty.mTimedOutEventParams;
            if (bundle != null) {
                this.mTimedOutEventParams = new Bundle(bundle);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            Bundle bundle2 = conditionalUserProperty.mTriggeredEventParams;
            if (bundle2 != null) {
                this.mTriggeredEventParams = new Bundle(bundle2);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            Bundle bundle3 = conditionalUserProperty.mExpiredEventParams;
            if (bundle3 != null) {
                this.mExpiredEventParams = new Bundle(bundle3);
            }
        }
    }

    public static final class Event extends zzcu {
        public static final String AD_REWARD = "_ar";
        public static final String APP_EXCEPTION = "_ae";

        private Event() {
        }
    }

    public interface EventInterceptor extends zzcx {
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    public interface OnEventListener extends zzcy {
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    public static final class Param extends zzcv {
        public static final String FATAL = "fatal";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        private Param() {
        }
    }

    public static final class UserProperty extends zzcw {
        public static final String FIREBASE_LAST_NOTIFICATION = "_ln";

        private UserProperty() {
        }
    }

    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        return zzbw.zza(context, (zzan) null).zzkm();
    }

    public final void logEvent(String str, Bundle bundle) {
        this.zzada.zzgj().zza(SettingsJsonConstants.APP_KEY, str, bundle, true);
    }

    public final void setUserProperty(String str, String str2) {
        this.zzada.zzgj().zzb(SettingsJsonConstants.APP_KEY, str, (Object) str2, false);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        this.zzada.zzgj().setMeasurementEnabled(z);
    }

    public final void zzd(boolean z) {
        this.zzada.zzgj().zzd(z);
    }

    @Deprecated
    public final void setMinimumSessionDuration(long j) {
        this.zzada.zzgj().setMinimumSessionDuration(j);
    }

    public final void setSessionTimeoutDuration(long j) {
        this.zzada.zzgj().setSessionTimeoutDuration(j);
    }

    public AppMeasurement(zzbw zzbw) {
        Preconditions.checkNotNull(zzbw);
        this.zzada = zzbw;
    }

    public void logEventInternal(String str, String str2, Bundle bundle) {
        this.zzada.zzgj().logEvent(str, str2, bundle);
    }

    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        this.zzada.zzgj().logEvent(str, str2, bundle, true, false, j);
    }

    public void setUserPropertyInternal(String str, String str2, Object obj) {
        Preconditions.checkNotEmpty(str);
        this.zzada.zzgj().zzb(str, str2, obj, true);
    }

    public Map<String, Object> getUserProperties(boolean z) {
        List<zzfu> zzk = this.zzada.zzgj().zzk(z);
        ArrayMap arrayMap = new ArrayMap(zzk.size());
        for (zzfu zzfu : zzk) {
            arrayMap.put(zzfu.name, zzfu.getValue());
        }
        return arrayMap;
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zzada.zzgj().zza((zzcx) eventInterceptor);
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzada.zzgj().zza((zzcy) onEventListener);
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzada.zzgj().zzb((zzcy) onEventListener);
    }

    public String getCurrentScreenName() {
        return this.zzada.zzgj().getCurrentScreenName();
    }

    public String getCurrentScreenClass() {
        return this.zzada.zzgj().getCurrentScreenClass();
    }

    public String getAppInstanceId() {
        return this.zzada.zzgj().zzgc();
    }

    public String getGmpAppId() {
        return this.zzada.zzgj().getGmpAppId();
    }

    public long generateEventId() {
        return this.zzada.zzgr().zzmj();
    }

    public void beginAdUnitExposure(String str) {
        this.zzada.zzgi().beginAdUnitExposure(str, this.zzada.zzbx().elapsedRealtime());
    }

    public void endAdUnitExposure(String str) {
        this.zzada.zzgi().endAdUnitExposure(str, this.zzada.zzbx().elapsedRealtime());
    }

    public void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        this.zzada.zzgj().setConditionalUserProperty(conditionalUserProperty);
    }

    /* access modifiers changed from: protected */
    public void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        this.zzada.zzgj().setConditionalUserPropertyAs(conditionalUserProperty);
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        this.zzada.zzgj().clearConditionalUserProperty(str, str2, bundle);
    }

    /* access modifiers changed from: protected */
    public void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        this.zzada.zzgj().clearConditionalUserPropertyAs(str, str2, str3, bundle);
    }

    /* access modifiers changed from: protected */
    public Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return this.zzada.zzgj().getUserProperties(str, str2, z);
    }

    /* access modifiers changed from: protected */
    public Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        return this.zzada.zzgj().getUserPropertiesAs(str, str2, str3, z);
    }

    public List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        return this.zzada.zzgj().getConditionalUserProperties(str, str2);
    }

    /* access modifiers changed from: protected */
    public List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        return this.zzada.zzgj().getConditionalUserPropertiesAs(str, str2, str3);
    }

    public int getMaxUserProperties(String str) {
        this.zzada.zzgj();
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    public Boolean getBoolean() {
        return this.zzada.zzgj().zzkx();
    }

    public String getString() {
        return this.zzada.zzgj().zzky();
    }

    public Long getLong() {
        return this.zzada.zzgj().zzkz();
    }

    public Integer getInteger() {
        return this.zzada.zzgj().zzla();
    }

    public Double getDouble() {
        return this.zzada.zzgj().zzlb();
    }
}
