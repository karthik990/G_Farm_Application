package com.google.firebase.messaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Map.Entry;

public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Creator<RemoteMessage> CREATOR = new zzc();
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;
    Bundle zzds;
    private Map<String, String> zzdt;
    private Notification zzdu;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public static class Builder {
        private final Bundle zzds = new Bundle();
        private final Map<String, String> zzdt = new ArrayMap();

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                String str2 = "Invalid to: ";
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            this.zzds.putString("google.to", str);
        }

        public RemoteMessage build() {
            Bundle bundle = new Bundle();
            for (Entry entry : this.zzdt.entrySet()) {
                bundle.putString((String) entry.getKey(), (String) entry.getValue());
            }
            bundle.putAll(this.zzds);
            this.zzds.remove("from");
            return new RemoteMessage(bundle);
        }

        public Builder addData(String str, String str2) {
            this.zzdt.put(str, str2);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.zzdt.clear();
            this.zzdt.putAll(map);
            return this;
        }

        public Builder clearData() {
            this.zzdt.clear();
            return this;
        }

        public Builder setMessageId(String str) {
            this.zzds.putString("google.message_id", str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.zzds.putString("message_type", str);
            return this;
        }

        public Builder setTtl(int i) {
            this.zzds.putString("google.ttl", String.valueOf(i));
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.zzds.putString("collapse_key", str);
            return this;
        }
    }

    public static class Notification {
        private final String tag;
        private final String zzdv;
        private final String zzdw;
        private final String[] zzdx;
        private final String zzdy;
        private final String zzdz;
        private final String[] zzea;
        private final String zzeb;
        private final String zzec;
        private final String zzed;
        private final String zzee;
        private final Uri zzef;

        private Notification(Bundle bundle) {
            String str = "gcm.n.title";
            this.zzdv = zza.zza(bundle, str);
            this.zzdw = zza.zzb(bundle, str);
            this.zzdx = zze(bundle, str);
            String str2 = "gcm.n.body";
            this.zzdy = zza.zza(bundle, str2);
            this.zzdz = zza.zzb(bundle, str2);
            this.zzea = zze(bundle, str2);
            this.zzeb = zza.zza(bundle, "gcm.n.icon");
            this.zzec = zza.zzi(bundle);
            this.tag = zza.zza(bundle, "gcm.n.tag");
            this.zzed = zza.zza(bundle, "gcm.n.color");
            this.zzee = zza.zza(bundle, "gcm.n.click_action");
            this.zzef = zza.zzg(bundle);
        }

        private static String[] zze(Bundle bundle, String str) {
            Object[] zzc = zza.zzc(bundle, str);
            if (zzc == null) {
                return null;
            }
            String[] strArr = new String[zzc.length];
            for (int i = 0; i < zzc.length; i++) {
                strArr[i] = String.valueOf(zzc[i]);
            }
            return strArr;
        }

        public String getTitle() {
            return this.zzdv;
        }

        public String getTitleLocalizationKey() {
            return this.zzdw;
        }

        public String[] getTitleLocalizationArgs() {
            return this.zzdx;
        }

        public String getBody() {
            return this.zzdy;
        }

        public String getBodyLocalizationKey() {
            return this.zzdz;
        }

        public String[] getBodyLocalizationArgs() {
            return this.zzea;
        }

        public String getIcon() {
            return this.zzeb;
        }

        public String getSound() {
            return this.zzec;
        }

        public String getTag() {
            return this.tag;
        }

        public String getColor() {
            return this.zzed;
        }

        public String getClickAction() {
            return this.zzee;
        }

        public Uri getLink() {
            return this.zzef;
        }
    }

    public RemoteMessage(Bundle bundle) {
        this.zzds = bundle;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzds, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String getFrom() {
        return this.zzds.getString("from");
    }

    public final String getTo() {
        return this.zzds.getString("google.to");
    }

    public final Map<String, String> getData() {
        if (this.zzdt == null) {
            Bundle bundle = this.zzds;
            ArrayMap arrayMap = new ArrayMap();
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!str.startsWith("google.") && !str.startsWith("gcm.") && !str.equals("from") && !str.equals("message_type") && !str.equals("collapse_key")) {
                        arrayMap.put(str, str2);
                    }
                }
            }
            this.zzdt = arrayMap;
        }
        return this.zzdt;
    }

    public final String getCollapseKey() {
        return this.zzds.getString("collapse_key");
    }

    public final String getMessageId() {
        String string = this.zzds.getString("google.message_id");
        return string == null ? this.zzds.getString("message_id") : string;
    }

    public final String getMessageType() {
        return this.zzds.getString("message_type");
    }

    public final long getSentTime() {
        Object obj = this.zzds.get("google.sent_time");
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(obj);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
                sb.append("Invalid sent time: ");
                sb.append(valueOf);
                Log.w("FirebaseMessaging", sb.toString());
            }
        }
        return 0;
    }

    public final int getTtl() {
        Object obj = this.zzds.get("google.ttl");
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(obj);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13);
                sb.append("Invalid TTL: ");
                sb.append(valueOf);
                Log.w("FirebaseMessaging", sb.toString());
            }
        }
        return 0;
    }

    public final int getOriginalPriority() {
        String string = this.zzds.getString("google.original_priority");
        if (string == null) {
            string = this.zzds.getString("google.priority");
        }
        return zzm(string);
    }

    public final int getPriority() {
        String string = this.zzds.getString("google.delivered_priority");
        if (string == null) {
            if (IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(this.zzds.getString("google.priority_reduced"))) {
                return 2;
            }
            string = this.zzds.getString("google.priority");
        }
        return zzm(string);
    }

    private static int zzm(String str) {
        if ("high".equals(str)) {
            return 1;
        }
        return "normal".equals(str) ? 2 : 0;
    }

    public final Notification getNotification() {
        if (this.zzdu == null && zza.zzf(this.zzds)) {
            this.zzdu = new Notification(this.zzds);
        }
        return this.zzdu;
    }

    public final Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtras(this.zzds);
        return intent;
    }
}
