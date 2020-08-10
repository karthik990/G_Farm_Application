package com.flurry.sdk;

import android.text.TextUtils;
import com.mobiroller.constants.Constants;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* renamed from: com.flurry.sdk.w */
public final class C1961w {

    /* renamed from: b */
    private static final char[] f1455b = {'F', 'C', 'B', 'M'};

    /* renamed from: c */
    private static final String f1456c = new String(f1455b);

    /* renamed from: d */
    private static final int f1457d;

    /* renamed from: e */
    private static final int f1458e;

    /* renamed from: f */
    private static final int f1459f;

    /* renamed from: g */
    private static final int f1460g;

    /* renamed from: a */
    public ByteBuffer f1461a;

    /* renamed from: h */
    private short f1462h;

    /* renamed from: i */
    private boolean f1463i;

    /* renamed from: b */
    public static int m1240b() {
        return 1;
    }

    static {
        char[] cArr = f1455b;
        f1457d = (cArr.length * 2) + 2 + 1 + 105984;
        int length = cArr.length * 2;
        f1458e = length;
        int i = length + 2;
        f1459f = i;
        f1460g = i + 1;
    }

    C1961w() {
        this.f1461a = ByteBuffer.allocateDirect(f1457d);
        this.f1461a.asCharBuffer().put(f1455b);
    }

    public C1961w(File file) {
        int i;
        boolean z = true;
        String format = String.format(Locale.getDefault(), "YCrashBreadcrumbs from %s", new Object[]{file.getAbsolutePath()});
        String str = "com.flurry.android.common.newProviders.errorCrashBreadcrumbsManager";
        C1685cy.m754a(6, str, format);
        this.f1461a = ByteBuffer.allocate(f1457d);
        if (file.length() != ((long) this.f1461a.capacity())) {
            C1685cy.m754a(6, str, String.format(Locale.getDefault(), "Crash breadcrumbs invalid file length %s != %s", new Object[]{Long.valueOf(file.length()), Integer.valueOf(this.f1461a.capacity())}));
            this.f1461a = null;
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            try {
                i = channel.read(this.f1461a);
            } catch (IOException unused) {
                C1685cy.m754a(6, str, "Issue reading breadcrumbs from file.");
                i = 0;
            }
            C1734dz.m871a((Closeable) channel);
            C1734dz.m871a((Closeable) fileInputStream);
            if (i != this.f1461a.capacity()) {
                C1685cy.m754a(6, str, String.format(Locale.getDefault(), "YCrashBreadcrumbs unexpected read size %s != %s", new Object[]{Integer.valueOf(i), Integer.valueOf(this.f1461a.capacity())}));
                this.f1461a = null;
                return;
            }
            this.f1461a.position(0);
            String obj = this.f1461a.asCharBuffer().limit(f1455b.length).toString();
            if (!obj.equals(f1456c)) {
                C1685cy.m754a(6, str, String.format(Locale.getDefault(), "YCrashBreadcrumbs invalid magic string: '%s'", new Object[]{obj}));
                this.f1461a = null;
                return;
            }
            this.f1462h = this.f1461a.getShort(f1458e);
            short s = this.f1462h;
            if (s < 0 || s >= 207) {
                C1685cy.m754a(6, str, String.format(Locale.getDefault(), "YCrashBreadcrumbs invalid index: '%s'", new Object[]{Short.valueOf(this.f1462h)}));
                this.f1461a = null;
                return;
            }
            if (this.f1461a.get(f1459f) != 1) {
                z = false;
            }
            this.f1463i = z;
        } catch (FileNotFoundException unused2) {
            C1685cy.m754a(6, str, "Issue reading breadcrumbs file.");
            this.f1461a = null;
        }
    }

    /* renamed from: a */
    private C1960v m1239a(int i) {
        this.f1461a.position(f1460g + (i * 512));
        return new C1960v(this.f1461a.asCharBuffer().limit(this.f1461a.getInt()).toString(), this.f1461a.getLong());
    }

    /* renamed from: a */
    public final List<C1960v> mo16536a() {
        ArrayList arrayList = new ArrayList();
        if (this.f1461a == null) {
            return arrayList;
        }
        if (this.f1463i) {
            for (int i = this.f1462h; i < 207; i++) {
                arrayList.add(m1239a(i));
            }
        }
        for (int i2 = 0; i2 < this.f1462h; i2++) {
            arrayList.add(m1239a(i2));
        }
        return arrayList;
    }

    /* renamed from: a */
    public final synchronized void mo16537a(C1960v vVar) {
        String str = vVar.f1453a;
        if (TextUtils.isEmpty(str)) {
            C1685cy.m754a(6, "com.flurry.android.common.newProviders.errorCrashBreadcrumbsManager", "Breadcrumb may not be null or empty.");
            return;
        }
        long j = vVar.f1454b;
        int min = Math.min(str.length(), 250);
        this.f1461a.position((this.f1462h * 512) + f1460g);
        this.f1461a.putLong(j);
        this.f1461a.putInt(min);
        this.f1461a.asCharBuffer().put(str, 0, min);
        byte b = 1;
        this.f1462h = (short) (this.f1462h + 1);
        if (this.f1462h >= 207) {
            this.f1462h = 0;
            this.f1463i = true;
        }
        this.f1461a.putShort(f1458e, this.f1462h);
        ByteBuffer byteBuffer = this.f1461a;
        int i = f1459f;
        if (!this.f1463i) {
            b = 0;
        }
        byteBuffer.put(i, b);
    }

    public final synchronized String toString() {
        StringBuilder sb;
        short s = this.f1461a == null ? 0 : this.f1463i ? 207 : this.f1462h;
        sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("Total number of breadcrumbs: ");
        sb2.append(s);
        sb2.append(Constants.NEW_LINE);
        sb.append(sb2.toString());
        for (C1960v vVar : mo16536a()) {
            sb.append(vVar.toString());
        }
        return sb.toString();
    }
}
