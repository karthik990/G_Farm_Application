package com.startapp.common.p042c;

import com.startapp.common.p092a.C5155g;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.startapp.common.c.a */
/* compiled from: StartAppSDK */
public class C5179a extends InputStream {

    /* renamed from: a */
    public static Map<String, Class> f3620a = new HashMap();

    /* renamed from: b */
    private InputStream f3621b = null;

    /* renamed from: c */
    private String f3622c;

    @Deprecated
    public final int read() {
        return 0;
    }

    public C5179a(String str) {
        this.f3622c = str;
    }

    public void close() {
        super.close();
        InputStream inputStream = this.f3621b;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /* renamed from: a */
    public final <T> T mo62902a(Class<T> cls, JSONObject jSONObject) {
        try {
            return m3904b(cls, jSONObject);
        } catch (C5182d e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error while trying to parse object ");
            sb.append(cls.toString());
            C5155g.m3808a("JSONInputStream", 6, sb.toString(), e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02c9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02d1, code lost:
        throw new com.startapp.common.p042c.C5182d("Unknown error occurred ", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x02d2, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02d3, code lost:
        r13 = r3;
        r17 = r4;
        r16 = r5;
        r18 = r6;
        r14 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02c9 A[ExcHandler: all (r0v9 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:67:0x010d] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0161 A[Catch:{ Exception -> 0x02d2, all -> 0x02c9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01a0 A[Catch:{ Exception -> 0x02d2, all -> 0x02c9 }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T m3904b(java.lang.Class<T> r20, org.json.JSONObject r21) {
        /*
            r19 = this;
            r8 = r19
            r0 = r20
            java.lang.String r9 = "JSONInputStream"
            java.lang.String r1 = "Problem instantiating object class "
            java.io.InputStream r2 = r8.f3621b
            if (r2 != 0) goto L_0x0019
            java.lang.String r2 = r8.f3622c
            if (r2 == 0) goto L_0x0011
            goto L_0x0019
        L_0x0011:
            com.startapp.common.c.d r0 = new com.startapp.common.c.d
            java.lang.String r1 = "Can't read object, the input is null."
            r0.<init>(r1)
            throw r0
        L_0x0019:
            java.lang.String r2 = r8.f3622c
            r10 = 0
            if (r2 != 0) goto L_0x0030
            java.io.InputStream r2 = r8.f3621b     // Catch:{ Exception -> 0x0027 }
            java.lang.String r2 = m3899a(r2, r10)     // Catch:{ Exception -> 0x0027 }
            r8.f3622c = r2     // Catch:{ Exception -> 0x0027 }
            goto L_0x0030
        L_0x0027:
            r0 = move-exception
            com.startapp.common.c.d r1 = new com.startapp.common.c.d
            java.lang.String r2 = "Can't read input stream."
            r1.<init>(r2, r0)
            throw r1
        L_0x0030:
            if (r21 != 0) goto L_0x0044
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x003b }
            java.lang.String r3 = r8.f3622c     // Catch:{ JSONException -> 0x003b }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x003b }
            r11 = r2
            goto L_0x0046
        L_0x003b:
            r0 = move-exception
            com.startapp.common.c.d r1 = new com.startapp.common.c.d
            java.lang.String r2 = "Can't deserialize the object. Failed to create JSON object."
            r1.<init>(r2, r0)
            throw r1
        L_0x0044:
            r11 = r21
        L_0x0046:
            java.lang.Class<com.startapp.common.c.e> r2 = com.startapp.common.p042c.C2361e.class
            java.lang.annotation.Annotation r2 = r0.getAnnotation(r2)     // Catch:{ Exception -> 0x030c }
            com.startapp.common.c.e r2 = (com.startapp.common.p042c.C2361e) r2     // Catch:{ Exception -> 0x030c }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x030c }
            r4 = 9
            java.lang.String r12 = "."
            r13 = 2
            r14 = 1
            r15 = 0
            if (r3 < r4) goto L_0x0079
            java.lang.Class<java.net.HttpCookie> r3 = java.net.HttpCookie.class
            boolean r3 = r0.equals(r3)     // Catch:{ Exception -> 0x030c }
            if (r3 == 0) goto L_0x0079
            java.lang.reflect.Constructor[] r1 = r20.getDeclaredConstructors()     // Catch:{ Exception -> 0x030c }
            r1 = r1[r15]     // Catch:{ Exception -> 0x030c }
            r1.setAccessible(r14)     // Catch:{ Exception -> 0x030c }
            java.lang.Object[] r3 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x030c }
            java.lang.String r4 = "name"
            r3[r15] = r4     // Catch:{ Exception -> 0x030c }
            java.lang.String r4 = "value"
            r3[r14] = r4     // Catch:{ Exception -> 0x030c }
            java.lang.Object r1 = r1.newInstance(r3)     // Catch:{ Exception -> 0x030c }
            goto L_0x00df
        L_0x0079:
            boolean r3 = r20.isPrimitive()     // Catch:{ Exception -> 0x030c }
            if (r3 == 0) goto L_0x0084
            java.lang.Object r0 = r20.newInstance()     // Catch:{ Exception -> 0x030c }
            return r0
        L_0x0084:
            java.lang.Class<com.startapp.common.c.e> r3 = com.startapp.common.p042c.C2361e.class
            java.lang.annotation.Annotation r3 = r0.getAnnotation(r3)     // Catch:{ Exception -> 0x030c }
            if (r3 == 0) goto L_0x00d0
            boolean r3 = r2.mo20784c()     // Catch:{ Exception -> 0x030c }
            if (r3 == 0) goto L_0x0093
            goto L_0x00d0
        L_0x0093:
            boolean r3 = r2.mo20784c()     // Catch:{ Exception -> 0x030c }
            if (r3 != 0) goto L_0x00ce
            java.lang.String r0 = r2.mo20782a()     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.String r0 = r11.getString(r0)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.String r2 = r2.mo20783b()     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            r3.<init>()     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            r3.append(r2)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            r3.append(r12)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            r3.append(r0)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.String r0 = r3.toString()     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            java.lang.Object r0 = r8.m3904b(r0, r11)     // Catch:{ ClassNotFoundException -> 0x00c7, JSONException -> 0x00c0 }
            return r0
        L_0x00c0:
            r0 = move-exception
            com.startapp.common.c.d r2 = new com.startapp.common.c.d     // Catch:{ Exception -> 0x030c }
            r2.<init>(r1, r0)     // Catch:{ Exception -> 0x030c }
            throw r2     // Catch:{ Exception -> 0x030c }
        L_0x00c7:
            r0 = move-exception
            com.startapp.common.c.d r2 = new com.startapp.common.c.d     // Catch:{ Exception -> 0x030c }
            r2.<init>(r1, r0)     // Catch:{ Exception -> 0x030c }
            throw r2     // Catch:{ Exception -> 0x030c }
        L_0x00ce:
            r7 = r10
            goto L_0x00e0
        L_0x00d0:
            java.lang.Class[] r1 = new java.lang.Class[r15]     // Catch:{ Exception -> 0x030c }
            java.lang.reflect.Constructor r1 = r0.getDeclaredConstructor(r1)     // Catch:{ Exception -> 0x030c }
            r1.setAccessible(r14)     // Catch:{ Exception -> 0x030c }
            java.lang.Object[] r3 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x030c }
            java.lang.Object r1 = r1.newInstance(r3)     // Catch:{ Exception -> 0x030c }
        L_0x00df:
            r7 = r1
        L_0x00e0:
            java.lang.reflect.Field[] r1 = r20.getDeclaredFields()
            if (r2 == 0) goto L_0x00f0
            boolean r2 = r2.mo20784c()
            if (r2 == 0) goto L_0x00f0
            java.lang.reflect.Field[] r1 = r8.m3903a(r0, r1)
        L_0x00f0:
            r6 = r1
            int r5 = r6.length
            r4 = 0
        L_0x00f3:
            if (r4 >= r5) goto L_0x030a
            r0 = r6[r4]
            int r1 = r0.getModifiers()
            boolean r2 = java.lang.reflect.Modifier.isStatic(r1)
            if (r2 != 0) goto L_0x02f3
            boolean r1 = java.lang.reflect.Modifier.isTransient(r1)
            if (r1 == 0) goto L_0x0109
            goto L_0x02f3
        L_0x0109:
            java.lang.String r3 = com.startapp.common.p042c.C5181c.m3912a(r0)
            boolean r1 = r11.has(r3)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r1 == 0) goto L_0x02a4
            r0.setAccessible(r14)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.annotation.Annotation[] r1 = r0.getDeclaredAnnotations()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            int r1 = r1.length     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r1 <= 0) goto L_0x014b
            java.lang.annotation.Annotation[] r1 = r0.getDeclaredAnnotations()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r1 = r1[r15]     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r2 = r1.annotationType()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class<com.startapp.common.c.f> r13 = com.startapp.common.p042c.C2362f.class
            boolean r2 = r2.equals(r13)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r2 == 0) goto L_0x014b
            com.startapp.common.c.f r1 = (com.startapp.common.p042c.C2362f) r1     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r2 = r1.mo20786b()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r13 = r1.mo20788d()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r16 = r1.mo20787c()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            boolean r17 = r1.mo20785a()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r1 = r1.mo20789e()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r20 = r16
            r16 = r1
            r1 = 1
            goto L_0x0155
        L_0x014b:
            r20 = r10
            r2 = r20
            r13 = r2
            r16 = r13
            r1 = 0
            r17 = 0
        L_0x0155:
            java.lang.Class r15 = r0.getType()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class<com.startapp.common.c.e> r14 = com.startapp.common.p042c.C2361e.class
            java.lang.annotation.Annotation r14 = r15.getAnnotation(r14)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r14 == 0) goto L_0x01a0
            java.lang.Class r1 = r0.getType()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class<com.startapp.common.c.e> r2 = com.startapp.common.p042c.C2361e.class
            java.lang.annotation.Annotation r1 = r1.getAnnotation(r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            com.startapp.common.c.e r1 = (com.startapp.common.p042c.C2361e) r1     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            org.json.JSONObject r2 = r11.getJSONObject(r3)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.String r13 = r1.mo20782a()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.String r2 = r2.getString(r13)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.String r1 = r1.mo20783b()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r13.<init>()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r13.append(r1)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r13.append(r12)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r13.append(r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.String r1 = r13.toString()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            org.json.JSONObject r2 = r11.getJSONObject(r3)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Object r1 = r8.m3904b(r1, r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r0.set(r7, r1)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            goto L_0x02f3
        L_0x01a0:
            if (r17 == 0) goto L_0x01b3
            java.lang.Class r1 = r0.getType()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            org.json.JSONObject r2 = r11.getJSONObject(r3)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.lang.Object r1 = r8.m3904b(r1, r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r0.set(r7, r1)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            goto L_0x02f3
        L_0x01b3:
            if (r1 == 0) goto L_0x023b
            java.lang.Class<java.util.Map> r1 = java.util.Map.class
            boolean r1 = r1.isAssignableFrom(r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r1 != 0) goto L_0x01c5
            java.lang.Class<java.util.Collection> r1 = java.util.Collection.class
            boolean r1 = r1.isAssignableFrom(r2)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r1 == 0) goto L_0x023b
        L_0x01c5:
            java.lang.Class<java.util.HashMap> r1 = java.util.HashMap.class
            boolean r1 = r2.equals(r1)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            if (r1 == 0) goto L_0x01f0
            org.json.JSONObject r14 = r11.getJSONObject(r3)     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            java.util.Iterator r15 = r14.keys()     // Catch:{ Exception -> 0x02d2, all -> 0x02c9 }
            r1 = r19
            r2 = r13
            r13 = r3
            r3 = r20
            r17 = r4
            r4 = r16
            r16 = r5
            r5 = r0
            r18 = r6
            r6 = r14
            r14 = r7
            r7 = r15
            java.util.Map r1 = r1.m3900a(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x01f0:
            r13 = r3
            r17 = r4
            r16 = r5
            r18 = r6
            r14 = r7
            java.lang.Class<java.util.ArrayList> r1 = java.util.ArrayList.class
            boolean r1 = r2.equals(r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x020f
            org.json.JSONArray r1 = r11.getJSONArray(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r3 = r20
            java.util.List r1 = r8.m3905b(r3, r0, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x020f:
            r3 = r20
            java.lang.Class<java.util.HashSet> r1 = java.util.HashSet.class
            boolean r1 = r2.equals(r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x0226
            org.json.JSONArray r1 = r11.getJSONArray(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.util.Set r1 = r8.m3902a(r3, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x0226:
            java.lang.Class<java.util.EnumSet> r1 = java.util.EnumSet.class
            boolean r1 = r2.equals(r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x02fa
            org.json.JSONArray r1 = r11.getJSONArray(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.util.Set r1 = r8.m3901a(r3, r0, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x023b:
            r13 = r3
            r17 = r4
            r16 = r5
            r18 = r6
            r14 = r7
            java.lang.Class r1 = r0.getType()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            boolean r1 = r1.isEnum()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x025c
            java.lang.Object r1 = r11.get(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.Enum r1 = r8.m3894a(r1, r2)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x025c:
            java.lang.Class r1 = r0.getType()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            boolean r1 = r1.isPrimitive()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x0277
            java.lang.Object r1 = r11.get(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.Class r2 = r0.getType()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.Object r1 = m3898a(r11, r0, r1, r2)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x0277:
            java.lang.Class r1 = r0.getType()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            boolean r1 = r1.isArray()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r1 == 0) goto L_0x028a
            java.lang.Object r1 = r8.m3896a(r11, r2, r0)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x028a:
            java.lang.Object r1 = r11.get(r13)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.Class r2 = r0.getType()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.Object r1 = m3895a(r1, r2)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            boolean r2 = r1.equals(r10)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r2 == 0) goto L_0x02a0
            r0.set(r14, r10)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x02a0:
            r0.set(r14, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x02a4:
            r13 = r3
            r17 = r4
            r16 = r5
            r18 = r6
            r14 = r7
            java.lang.Boolean r0 = com.startapp.common.Constants.m3707a()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            if (r0 == 0) goto L_0x02fa
            r0 = 4
            java.lang.String r1 = "Field [%s] doesn't exist. Keeping default value."
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            r2 = 0
            r3[r2] = r13     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            java.lang.String r1 = java.lang.String.format(r1, r3)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            com.startapp.common.p092a.C5155g.m3807a(r9, r0, r1)     // Catch:{ Exception -> 0x02c7, all -> 0x02c9 }
            goto L_0x02fa
        L_0x02c7:
            r0 = move-exception
            goto L_0x02db
        L_0x02c9:
            r0 = move-exception
            com.startapp.common.c.d r1 = new com.startapp.common.c.d
            java.lang.String r2 = "Unknown error occurred "
            r1.<init>(r2, r0)
            throw r1
        L_0x02d2:
            r0 = move-exception
            r13 = r3
            r17 = r4
            r16 = r5
            r18 = r6
            r14 = r7
        L_0x02db:
            r1 = 6
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r4 = 0
            r3[r4] = r13
            java.lang.String r0 = r0.toString()
            r5 = 1
            r3[r5] = r0
            java.lang.String r0 = "Failed to get field [%s] %s"
            java.lang.String r0 = java.lang.String.format(r0, r3)
            com.startapp.common.p092a.C5155g.m3807a(r9, r1, r0)
            goto L_0x02fd
        L_0x02f3:
            r17 = r4
            r16 = r5
            r18 = r6
            r14 = r7
        L_0x02fa:
            r2 = 2
            r4 = 0
            r5 = 1
        L_0x02fd:
            int r0 = r17 + 1
            r4 = r0
            r7 = r14
            r5 = r16
            r6 = r18
            r13 = 2
            r14 = 1
            r15 = 0
            goto L_0x00f3
        L_0x030a:
            r14 = r7
            return r14
        L_0x030c:
            r0 = move-exception
            com.startapp.common.c.d r1 = new com.startapp.common.c.d
            java.lang.String r2 = "Can't deserialize the object. Failed to instantiate object."
            r1.<init>(r2, r0)
            goto L_0x0316
        L_0x0315:
            throw r1
        L_0x0316:
            goto L_0x0315
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p042c.C5179a.m3904b(java.lang.Class, org.json.JSONObject):java.lang.Object");
    }

    /* renamed from: a */
    private <T> Field[] m3903a(Class<T> cls, Field[] fieldArr) {
        int length = fieldArr.length;
        Field[] declaredFields = cls.getSuperclass().getDeclaredFields();
        int length2 = declaredFields.length;
        Field[] fieldArr2 = new Field[(length + length2)];
        System.arraycopy(fieldArr, 0, fieldArr2, 0, length);
        System.arraycopy(declaredFields, 0, fieldArr2, length, length2);
        return fieldArr2;
    }

    /* renamed from: a */
    private Enum<?> m3894a(String str, Class cls) {
        return Enum.valueOf(cls, str);
    }

    /* renamed from: a */
    private Object m3897a(JSONObject jSONObject, Field field) {
        Object obj;
        JSONArray jSONArray = jSONObject.getJSONArray(C5181c.m3912a(field));
        int length = jSONArray.length();
        Class cls = (Class) f3620a.get(field.getType().getSimpleName());
        Object newInstance = Array.newInstance((Class) cls.getField("TYPE").get(null), length);
        for (int i = 0; i < length; i++) {
            String string = jSONArray.getString(i);
            Class<String> cls2 = String.class;
            if (cls.equals(Character.class)) {
                cls2 = Character.TYPE;
            }
            Constructor constructor = cls.getConstructor(new Class[]{cls2});
            if (cls.equals(Character.class)) {
                obj = constructor.newInstance(new Object[]{Character.valueOf(string.charAt(0))});
            } else {
                obj = constructor.newInstance(new Object[]{string});
            }
            Array.set(newInstance, i, obj);
        }
        return newInstance;
    }

    /* renamed from: a */
    private <T> Object m3896a(JSONObject jSONObject, Class<T> cls, Field field) {
        if (cls != null) {
            return m3906b(jSONObject, cls, field);
        }
        return m3897a(jSONObject, field);
    }

    /* renamed from: b */
    private <T> T[] m3906b(JSONObject jSONObject, Class<T> cls, Field field) {
        JSONArray jSONArray = jSONObject.getJSONArray(C5181c.m3912a(field));
        int length = jSONArray.length();
        Object newInstance = Array.newInstance(cls, length);
        for (int i = 0; i < length; i++) {
            Array.set(newInstance, i, m3904b(cls, jSONArray.getJSONObject(i)));
        }
        return (Object[]) newInstance;
    }

    /* renamed from: a */
    private <K, V> Map<K, V> m3900a(Class<K> cls, Class<V> cls2, Class cls3, Field field, JSONObject jSONObject, Iterator<K> it) {
        HashMap hashMap = new HashMap();
        while (it.hasNext()) {
            Object next = it.next();
            Object cast = cls.equals(Integer.class) ? cls.cast(Integer.valueOf(Integer.parseInt((String) next))) : next;
            if (cls.isEnum()) {
                cast = m3894a(cast.toString(), (Class) cls);
            }
            String str = (String) next;
            JSONObject optJSONObject = jSONObject.optJSONObject(str);
            if (optJSONObject == null) {
                JSONArray optJSONArray = jSONObject.optJSONArray(str);
                if (optJSONArray != null) {
                    hashMap.put(cast, m3902a(cls3, optJSONArray));
                } else if (cls2.isEnum()) {
                    hashMap.put(cast, m3894a((String) jSONObject.get(str), (Class) cls2));
                } else {
                    hashMap.put(cast, jSONObject.get(str));
                }
            } else {
                hashMap.put(cast, m3904b(cls2, optJSONObject));
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    private <V> Set<V> m3902a(Class<V> cls, JSONArray jSONArray) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject == null) {
                hashSet.add(jSONArray.get(i));
            } else {
                hashSet.add(m3904b(cls, optJSONObject));
            }
        }
        return hashSet;
    }

    /* renamed from: a */
    private <V> Set m3901a(Class<V> cls, Field field, JSONArray jSONArray) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < jSONArray.length(); i++) {
            hashSet.add(m3894a(jSONArray.getString(i), (Class) cls));
        }
        return hashSet;
    }

    /* renamed from: b */
    private <V> List<V> m3905b(Class<V> cls, Field field, JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject == null) {
                arrayList.add(jSONArray.get(i));
            } else {
                arrayList.add(m3904b(cls, optJSONObject));
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private static Object m3895a(Object obj, Class<?> cls) {
        if (obj.getClass().equals(cls)) {
            return obj;
        }
        if (!cls.equals(Integer.class)) {
            return (!cls.equals(Long.class) || !obj.getClass().equals(Integer.class)) ? obj : Long.valueOf(((Integer) obj).longValue());
        }
        if (obj.getClass().equals(Double.class)) {
            return Integer.valueOf(((Double) obj).intValue());
        }
        if (obj.getClass().equals(Long.class)) {
            return Integer.valueOf(((Long) obj).intValue());
        }
        return obj;
    }

    /* renamed from: a */
    private static Object m3898a(JSONObject jSONObject, Field field, Object obj, Class<?> cls) {
        if (obj.getClass().equals(cls)) {
            return obj;
        }
        if (obj.getClass().equals(String.class)) {
            if (cls.equals(Integer.TYPE)) {
                return Integer.valueOf(jSONObject.getInt(C5181c.m3912a(field)));
            }
            return obj;
        } else if (cls.equals(Integer.TYPE)) {
            return Integer.valueOf(((Number) obj).intValue());
        } else {
            if (cls.equals(Float.TYPE)) {
                return Float.valueOf(((Number) obj).floatValue());
            }
            if (cls.equals(Long.TYPE)) {
                return Long.valueOf(((Number) obj).longValue());
            }
            return cls.equals(Double.TYPE) ? Double.valueOf(((Number) obj).doubleValue()) : obj;
        }
    }

    /* renamed from: a */
    private static String m3899a(InputStream inputStream, String str) {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        String str2 = "JSONInputStream";
        if (str != null) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str));
            } catch (Exception e) {
                C5155g.m3807a(str2, 6, String.format("Can't create BufferedReader [%s]", new Object[]{e.toString()}));
                throw e;
            }
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sb.toString();
                }
                sb.append(readLine);
            } catch (IOException e2) {
                C5155g.m3807a(str2, 6, String.format("Can't Can't read input stream [%s]", new Object[]{e2.toString()}));
                throw e2;
            }
        }
    }

    static {
        f3620a.put("int[]", Integer.class);
        f3620a.put("long[]", Long.class);
        f3620a.put("double[]", Double.class);
        f3620a.put("float[]", Float.class);
        f3620a.put("bool[]", Boolean.class);
        f3620a.put("char[]", Character.class);
        f3620a.put("byte[]", Byte.class);
        f3620a.put("void[]", Void.class);
        f3620a.put("short[]", Short.class);
    }
}
