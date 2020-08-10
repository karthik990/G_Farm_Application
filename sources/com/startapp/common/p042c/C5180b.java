package com.startapp.common.p042c;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.startapp.common.c.b */
/* compiled from: StartAppSDK */
public class C5180b {
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0014 A[SYNTHETIC, Splitter:B:13:0x0014] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T m3908a(java.lang.String r2, java.lang.Class<T> r3) {
        /*
            r0 = 0
            com.startapp.common.c.a r1 = new com.startapp.common.c.a     // Catch:{ all -> 0x0011 }
            r1.<init>(r2)     // Catch:{ all -> 0x0011 }
            java.lang.Object r2 = r1.mo62902a(r3, r0)     // Catch:{ all -> 0x000e }
            r1.close()     // Catch:{ IOException -> 0x000d }
        L_0x000d:
            return r2
        L_0x000e:
            r2 = move-exception
            r0 = r1
            goto L_0x0012
        L_0x0011:
            r2 = move-exception
        L_0x0012:
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ IOException -> 0x0017 }
        L_0x0017:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p042c.C5180b.m3908a(java.lang.String, java.lang.Class):java.lang.Object");
    }

    /* renamed from: a */
    public static String m3909a(Object obj) {
        return m3911c(obj).toString();
    }

    /* renamed from: c */
    private static JSONObject m3911c(Object obj) {
        Class cls = obj.getClass();
        ArrayList<Field> arrayList = new ArrayList<>();
        while (cls != null && !Object.class.equals(cls)) {
            arrayList.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        JSONObject jSONObject = new JSONObject();
        for (Field field : arrayList) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                try {
                    field.setAccessible(true);
                    if (field.get(obj) != null) {
                        String a = C5181c.m3912a(field);
                        if (C5181c.m3917e(field)) {
                            jSONObject.put(a, m3911c(field.get(obj)));
                        } else if (C5181c.m3915c(field)) {
                            JSONArray jSONArray = new JSONArray();
                            for (Object b : (List) field.get(obj)) {
                                jSONArray.put(m3910b(b));
                            }
                            jSONObject.put(a, jSONArray);
                        } else if (C5181c.m3916d(field)) {
                            JSONArray jSONArray2 = new JSONArray();
                            for (Object b2 : (Set) field.get(obj)) {
                                jSONArray2.put(m3910b(b2));
                            }
                            jSONObject.put(a, jSONArray2);
                        } else if (C5181c.m3914b(field)) {
                            JSONObject jSONObject2 = new JSONObject();
                            for (Entry entry : ((Map) field.get(obj)).entrySet()) {
                                jSONObject2.put(entry.getKey().toString(), m3910b(entry.getValue()));
                            }
                            jSONObject.put(a, jSONObject2);
                        } else {
                            jSONObject.put(a, field.get(obj));
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException | JSONException unused) {
                }
            }
        }
        return jSONObject;
    }

    /* renamed from: b */
    public static Object m3910b(Object obj) {
        if (C5181c.m3913a(obj)) {
            return obj;
        }
        return m3911c(obj);
    }
}
