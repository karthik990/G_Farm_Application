package com.google.firebase.database;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.database.core.ServerValues;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ServerValue {
    public static final Map<String, String> TIMESTAMP = createServerValuePlaceholder(Param.TIMESTAMP);

    private static Map<String, String> createServerValuePlaceholder(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(ServerValues.NAME_SUBKEY_SERVERVALUE, str);
        return Collections.unmodifiableMap(hashMap);
    }
}
