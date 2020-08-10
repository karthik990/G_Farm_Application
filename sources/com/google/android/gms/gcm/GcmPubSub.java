package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;
import java.util.regex.Pattern;

@Deprecated
public class GcmPubSub {
    private static GcmPubSub zzn;
    private static final Pattern zzp = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
    private InstanceID zzo;

    private GcmPubSub(Context context) {
        this.zzo = InstanceID.getInstance(context);
    }

    @Deprecated
    public static synchronized GcmPubSub getInstance(Context context) {
        GcmPubSub gcmPubSub;
        synchronized (GcmPubSub.class) {
            if (zzn == null) {
                GoogleCloudMessaging.zze(context);
                zzn = new GcmPubSub(context);
            }
            gcmPubSub = zzn;
        }
        return gcmPubSub;
    }

    @Deprecated
    public void subscribe(String str, String str2, Bundle bundle) throws IOException {
        if (str == null || str.isEmpty()) {
            String str3 = "Invalid appInstanceToken: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        } else if (str2 == null || !zzp.matcher(str2).matches()) {
            String str4 = "Invalid topic name: ";
            String valueOf2 = String.valueOf(str2);
            throw new IllegalArgumentException(valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4));
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("gcm.topic", str2);
            this.zzo.getToken(str, str2, bundle);
        }
    }

    @Deprecated
    public void unsubscribe(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        bundle.putString("gcm.topic", str2);
        this.zzo.zzd(str, str2, bundle);
    }
}
