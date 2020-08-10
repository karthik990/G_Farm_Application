package com.startapp.android.p064b;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* renamed from: com.startapp.android.b.a */
/* compiled from: StartAppSDK */
public class C4729a {

    /* renamed from: a */
    private final Context f2456a;

    public C4729a(Context context) {
        this.f2456a = context;
    }

    /* renamed from: a */
    public boolean mo61133a() {
        return mo61138c() || mo61139d() || mo61134a(ChatConstants.ARG_USERS_ROLES_SUPER_USER) || mo61134a("busybox") || mo61141f() || mo61142g() || mo61136b() || mo61143h() || mo61140e();
    }

    /* renamed from: b */
    public boolean mo61136b() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    /* renamed from: c */
    public boolean mo61138c() {
        return mo61135a((String[]) null);
    }

    /* renamed from: a */
    public boolean mo61135a(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(C4730b.f2457a));
        if (strArr != null && strArr.length > 0) {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return m2154a((List<String>) arrayList);
    }

    /* renamed from: d */
    public boolean mo61139d() {
        return mo61137b(null);
    }

    /* renamed from: b */
    public boolean mo61137b(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(C4730b.f2458b));
        if (strArr != null && strArr.length > 0) {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return m2154a((List<String>) arrayList);
    }

    /* renamed from: e */
    public boolean mo61140e() {
        return mo61134a("magisk");
    }

    /* renamed from: a */
    public boolean mo61134a(String str) {
        String[] strArr;
        boolean z = false;
        for (String str2 : C4730b.f2460d) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            sb.toString();
            if (new File(str2, str).exists()) {
                z = true;
            }
        }
        return z;
    }

    /* renamed from: i */
    private String[] m2155i() {
        String[] strArr = new String[0];
        try {
            return new Scanner(Runtime.getRuntime().exec("getprop").getInputStream()).useDelimiter("\\A").next().split(Constants.NEW_LINE);
        } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
            return strArr;
        }
    }

    /* renamed from: j */
    private String[] m2156j() {
        String[] strArr = new String[0];
        try {
            return new Scanner(Runtime.getRuntime().exec("mount").getInputStream()).useDelimiter("\\A").next().split(Constants.NEW_LINE);
        } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
            return strArr;
        }
    }

    /* renamed from: a */
    private boolean m2154a(List<String> list) {
        PackageManager packageManager = this.f2456a.getPackageManager();
        boolean z = false;
        for (String packageInfo : list) {
            try {
                packageManager.getPackageInfo(packageInfo, 0);
                z = true;
            } catch (NameNotFoundException unused) {
            }
        }
        return z;
    }

    /* renamed from: f */
    public boolean mo61141f() {
        String[] i;
        HashMap hashMap = new HashMap();
        hashMap.put("ro.debuggable", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        hashMap.put("ro.secure", "0");
        boolean z = false;
        for (String str : m2155i()) {
            for (String str2 : hashMap.keySet()) {
                if (str.contains(str2)) {
                    String str3 = (String) hashMap.get(str2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(str3);
                    sb.append("]");
                    if (str.contains(sb.toString())) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    /* renamed from: g */
    public boolean mo61142g() {
        boolean z = false;
        for (String split : m2156j()) {
            String[] split2 = split.split(" ");
            if (split2.length >= 4) {
                String str = split2[1];
                String str2 = split2[3];
                boolean z2 = z;
                for (String equalsIgnoreCase : C4730b.f2461e) {
                    if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                        String[] split3 = str2.split(",");
                        int length = split3.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            } else if (split3[i].equalsIgnoreCase("rw")) {
                                z2 = true;
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
                z = z2;
            }
        }
        return z;
    }

    /* renamed from: h */
    public boolean mo61143h() {
        boolean z = false;
        Process process = null;
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"which", ChatConstants.ARG_USERS_ROLES_SUPER_USER});
            if (new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine() != null) {
                z = true;
            }
            if (exec != null) {
                exec.destroy();
            }
            return z;
        } catch (Throwable unused) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }
}
