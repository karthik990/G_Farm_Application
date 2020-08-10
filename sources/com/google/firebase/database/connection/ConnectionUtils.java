package com.google.firebase.database.connection;

import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ConnectionUtils {
    public static List<String> stringToPath(String str) {
        ArrayList arrayList = new ArrayList();
        String[] split = str.split("/", -1);
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                arrayList.add(split[i]);
            }
        }
        return arrayList;
    }

    public static String pathToString(List<String> list) {
        String str = "/";
        if (list.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str2 : list) {
            if (!z) {
                sb.append(str);
            }
            z = false;
            sb.append(str2);
        }
        return sb.toString();
    }

    public static Long longFromObject(Object obj) {
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        return null;
    }

    public static void hardAssert(boolean z) {
        hardAssert(z, "", new Object[0]);
    }

    public static void hardAssert(boolean z, String str, Object... objArr) {
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("hardAssert failed: ");
            sb.append(String.format(str, objArr));
            throw new AssertionError(sb.toString());
        }
    }
}
