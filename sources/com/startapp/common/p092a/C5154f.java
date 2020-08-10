package com.startapp.common.p092a;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* renamed from: com.startapp.common.a.f */
/* compiled from: StartAppSDK */
public class C5154f {
    /* renamed from: a */
    public static List<Location> m3803a(Context context, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        for (String lastKnownLocation : m3804b(context, z, z2)) {
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService(Param.LOCATION);
                if (locationManager == null) {
                    return null;
                }
                Location lastKnownLocation2 = locationManager.getLastKnownLocation(lastKnownLocation);
                if (lastKnownLocation2 != null) {
                    arrayList.add(lastKnownLocation2);
                }
            } catch (Exception | IllegalArgumentException | SecurityException unused) {
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static String m3802a(List<Location> list) {
        StringBuilder sb = new StringBuilder();
        if (list == null || list.size() <= 0) {
            return sb.toString();
        }
        for (Location location : list) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(location.getLongitude());
            String str = ",";
            sb2.append(str);
            sb.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(location.getLatitude());
            sb3.append(str);
            sb.append(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(location.getAccuracy());
            sb4.append(str);
            sb.append(sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(location.getProvider());
            sb5.append(str);
            sb.append(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(location.getTime());
            sb6.append(";");
            sb.append(sb6.toString());
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /* renamed from: b */
    private static Queue<String> m3804b(Context context, boolean z, boolean z2) {
        LinkedList linkedList = new LinkedList();
        String str = "network";
        if (z && C5146c.m3760a(context, "android.permission.ACCESS_FINE_LOCATION")) {
            linkedList.add("gps");
            linkedList.add("passive");
            linkedList.add(str);
        } else if (z2 && C5146c.m3760a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            linkedList.add(str);
        }
        return linkedList;
    }
}
