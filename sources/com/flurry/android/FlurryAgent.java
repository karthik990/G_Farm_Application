package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.work.WorkRequest;
import com.flurry.android.Consent;
import com.flurry.android.FlurryAgentListener;
import com.flurry.android.FlurryPerformance;
import com.flurry.android.FlurryPrivacySession.Request;
import com.flurry.sdk.C1479a;
import com.flurry.sdk.C1479a.C148110;
import com.flurry.sdk.C1479a.C15125;
import com.flurry.sdk.C1479a.C15136;
import com.flurry.sdk.C1479a.C15147;
import com.flurry.sdk.C1479a.C15158;
import com.flurry.sdk.C1576b;
import com.flurry.sdk.C1598bi;
import com.flurry.sdk.C1685cy;
import com.flurry.sdk.C1689db;
import com.flurry.sdk.C1734dz;
import com.flurry.sdk.C1738eb;
import com.flurry.sdk.C1771fb;
import com.flurry.sdk.C1877ht;
import com.flurry.sdk.C1948n;
import com.flurry.sdk.C1956s.C1957a;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class FlurryAgent {
    public static String VERSION_STRING = "!SDK-VERSION-STRING!:com.flurry.android:analytics:12.3.0";

    /* renamed from: a */
    private static String f290a;

    public static class Builder {

        /* renamed from: a */
        private FlurryAgentListener f291a = null;

        /* renamed from: b */
        private boolean f292b = false;

        /* renamed from: c */
        private int f293c = 5;

        /* renamed from: d */
        private long f294d = WorkRequest.MIN_BACKOFF_MILLIS;

        /* renamed from: e */
        private boolean f295e = true;

        /* renamed from: f */
        private boolean f296f = true;

        /* renamed from: g */
        private boolean f297g = false;

        /* renamed from: h */
        private int f298h = FlurryPerformance.NONE;

        /* renamed from: i */
        private List<FlurryModule> f299i = new ArrayList();

        /* renamed from: j */
        private Consent f300j;

        /* renamed from: k */
        private boolean f301k = false;

        /* renamed from: l */
        private boolean f302l = false;

        @Deprecated
        public Builder withPulseEnabled(boolean z) {
            return this;
        }

        public Builder withListener(FlurryAgentListener flurryAgentListener) {
            this.f291a = flurryAgentListener;
            return this;
        }

        public Builder withLogEnabled(boolean z) {
            this.f292b = z;
            return this;
        }

        public Builder withLogLevel(int i) {
            this.f293c = i;
            return this;
        }

        public Builder withContinueSessionMillis(long j) {
            if (j >= DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                this.f294d = j;
            }
            return this;
        }

        public Builder withCaptureUncaughtExceptions(boolean z) {
            this.f295e = z;
            return this;
        }

        public Builder withIncludeBackgroundSessionsInMetrics(boolean z) {
            this.f296f = z;
            return this;
        }

        public Builder withSslPinningEnabled(boolean z) {
            this.f297g = z;
            return this;
        }

        public Builder withPerformanceMetrics(int i) {
            this.f298h = i;
            return this;
        }

        public Builder withModule(FlurryModule flurryModule) throws IllegalArgumentException {
            if (C1689db.m783a(flurryModule.getClass().getCanonicalName())) {
                this.f299i.add(flurryModule);
                return this;
            }
            StringBuilder sb = new StringBuilder("The Flurry module you have registered is invalid: ");
            sb.append(flurryModule.getClass().getCanonicalName());
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder withConsent(Consent consent) {
            this.f300j = consent;
            return this;
        }

        public Builder withDataSaleOptOut(boolean z) {
            this.f301k = z;
            return this;
        }

        public Builder withSessionForceStart(boolean z) {
            this.f302l = z;
            return this;
        }

        public void build(Context context, String str) {
            boolean z;
            Context context2 = context;
            if (FlurryAgent.m352b()) {
                if (!TextUtils.isEmpty(str)) {
                    C1576b.m503a(context);
                    C1598bi.m531a().f665b = str;
                    C1479a a = C1479a.m353a();
                    FlurryAgentListener flurryAgentListener = this.f291a;
                    boolean z2 = this.f292b;
                    int i = this.f293c;
                    long j = this.f294d;
                    boolean z3 = this.f295e;
                    boolean z4 = this.f296f;
                    boolean z5 = this.f297g;
                    int i2 = this.f298h;
                    List<FlurryModule> list = this.f299i;
                    Consent consent = this.f300j;
                    boolean z6 = this.f301k;
                    boolean z7 = this.f302l;
                    String str2 = "FlurryAgentImpl";
                    if (C1479a.f315b.get()) {
                        C1685cy.m768d(str2, "Invalid call to Init. Flurry is already initialized");
                        return;
                    }
                    boolean z8 = z7;
                    C1685cy.m768d(str2, "Initializing Flurry SDK");
                    if (C1479a.f315b.get()) {
                        C1685cy.m768d(str2, "Invalid call to register. Flurry is already initialized");
                    } else {
                        a.f317a = list;
                    }
                    C1771fb.m926a();
                    a.runAsync(new C1738eb(context2, list) {

                        /* renamed from: a */
                        final /* synthetic */ Context f318a;

                        /* renamed from: b */
                        final /* synthetic */ List f319b;

                        {
                            this.f318a = r2;
                            this.f319b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() throws Exception {
                            C1771fb a = C1771fb.m926a();
                            a.f1047d.mo16243a();
                            a.f1045b.f1059a.mo16478a();
                            C1935ju juVar = a.f1046c;
                            File[] listFiles = new File(C1776ff.m943b()).listFiles();
                            if (listFiles != null) {
                                for (int i = 0; i < listFiles.length; i++) {
                                    String str = "StreamingFileUtil";
                                    if (listFiles[i].isFile()) {
                                        StringBuilder sb = new StringBuilder("File ");
                                        sb.append(listFiles[i].getName());
                                        C1685cy.m754a(3, str, sb.toString());
                                    } else if (listFiles[i].isDirectory()) {
                                        StringBuilder sb2 = new StringBuilder("Directory ");
                                        sb2.append(listFiles[i].getName());
                                        C1685cy.m754a(3, str, sb2.toString());
                                    }
                                }
                            }
                            System.out.println();
                            StringBuilder sb3 = new StringBuilder("Number of files already pending: in startWatching ");
                            sb3.append(listFiles.length);
                            C1685cy.m754a(2, "VNodeFileProcessor", sb3.toString());
                            juVar.mo16523a(Arrays.asList(listFiles));
                            juVar.runAsync(new C1738eb(juVar, juVar) {

                                /* renamed from: a */
                                final /* synthetic */ C1934jt f1391a;

                                /* renamed from: b */
                                final /* synthetic */ C1935ju f1392b;

                                /* renamed from: a */
                                public final 
/*
Method generation error in method: com.flurry.sdk.ju.1.a():null, dex: classes.dex
                                java.lang.NullPointerException
                                	at jadx.core.codegen.ClassGen.useType(ClassGen.java:442)
                                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:109)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:311)
                                	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:661)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:595)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:773)
                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:713)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                                	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:661)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:595)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:773)
                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:713)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                                	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
                                	at jadx.core.codegen.ClassGen.addInnerClasses(ClassGen.java:237)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
                                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:76)
                                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                                	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                                	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                                	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                                	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                                
*/
                            });
                            C1755ej.m900a();
                            C1689db.m780a(this.f318a);
                            C1755ej.m902a(this.f319b);
                            C1755ej.m901a(this.f318a);
                        }
                    });
                    C1877ht a2 = C1877ht.m1121a();
                    C1948n a3 = C1948n.m1229a();
                    String str3 = str2;
                    if (a3 != null) {
                        z = z6;
                        a3.f1415a.subscribe(a2.f1266h);
                        a3.f1416b.subscribe(a2.f1267i);
                        a3.f1417c.subscribe(a2.f1264f);
                        a3.f1418d.subscribe(a2.f1265g);
                        a3.f1419e.subscribe(a2.f1270l);
                        a3.f1420f.subscribe(a2.f1262d);
                        a3.f1421g.subscribe(a2.f1263e);
                        a3.f1422h.subscribe(a2.f1269k);
                        a3.f1423i.subscribe(a2.f1260b);
                        a3.f1424j.subscribe(a2.f1268j);
                        a3.f1425k.subscribe(a2.f1261c);
                        a3.f1426l.subscribe(a2.f1271m);
                        a3.f1428n.subscribe(a2.f1272n);
                        a3.f1429o.subscribe(a2.f1273o);
                        a3.f1430p.subscribe(a2.f1274p);
                    } else {
                        z = z6;
                    }
                    C1598bi.m531a().mo16289c();
                    C1948n.m1229a().f1420f.f443b = z3;
                    if (consent != null) {
                        a.runAsync(new C1738eb(consent) {

                            /* renamed from: a */
                            final /* synthetic */ Consent f329a;

                            {
                                this.f329a = r2;
                            }

                            /* renamed from: a */
                            public final void mo16236a() throws Exception {
                                C1948n.m1229a().f1426l.mo16534a(this.f329a);
                            }
                        });
                    }
                    if (z2) {
                        C1685cy.m759b();
                    } else {
                        C1685cy.m752a();
                    }
                    C1685cy.m753a(i);
                    a.runAsync(new C1738eb(j, flurryAgentListener) {

                        /* renamed from: a */
                        final /* synthetic */ long f358a;

                        /* renamed from: b */
                        final /* synthetic */ FlurryAgentListener f359b;

                        {
                            this.f358a = r2;
                            this.f359b = r4;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1948n.m1229a().f1425k.f619e = this.f358a;
                            C1948n.m1229a().f1425k.mo16284a(this.f359b);
                        }
                    });
                    a.runAsync(new C1738eb(z4, z5) {

                        /* renamed from: a */
                        final /* synthetic */ boolean f390a;

                        /* renamed from: b */
                        final /* synthetic */ boolean f391b;

                        {
                            this.f390a = r2;
                            this.f391b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1519ac acVar = C1948n.m1229a().f1422h;
                            String b = C1598bi.m531a().mo16288b();
                            boolean z = this.f390a;
                            boolean z2 = this.f391b;
                            acVar.f444a = b;
                            acVar.f446d = z;
                            acVar.f447e = z2;
                            acVar.runAsync(new C1738eb() {
                                /* renamed from: a */
                                public final void mo16236a() throws Exception {
                                    C1519ac.m408d(C1519ac.this);
                                    C1519ac.m405a(C1519ac.this);
                                }
                            });
                            String property = System.getProperty("os.arch");
                            String str = "";
                            if (TextUtils.isEmpty(property)) {
                                property = str;
                            }
                            HashMap hashMap = new HashMap();
                            hashMap.put("device.model", Build.MODEL);
                            hashMap.put("build.brand", Build.BRAND);
                            hashMap.put("build.id", Build.ID);
                            hashMap.put("version.release", VERSION.RELEASE);
                            hashMap.put("build.device", Build.DEVICE);
                            hashMap.put("build.product", Build.PRODUCT);
                            C1599bj.m535a();
                            Context a = C1576b.m502a();
                            if (a != null) {
                                int identifier = a.getResources().getIdentifier("com.flurry.crash.map_id", "string", a.getPackageName());
                                if (identifier != 0) {
                                    str = a.getResources().getString(identifier);
                                }
                            }
                            hashMap.put("proguard.build.uuid", str);
                            hashMap.put("device.arch", property);
                            C1771fb.m926a().mo16467a(new C1896ij(new C1897ik(hashMap)));
                            C1883hz.m1128b();
                            C1898il.m1145b();
                            Map a2 = new C1635bu().mo16318a();
                            if (a2.size() > 0) {
                                C1771fb.m926a().mo16467a(new C1915jb(new C1916jc(a2)));
                            }
                            C1888ib.m1133a(C1948n.m1229a().f1417c.f540a);
                        }
                    });
                    a.runAsync(new C1738eb(i2, context2) {

                        /* renamed from: a */
                        final /* synthetic */ int f385a;

                        /* renamed from: b */
                        final /* synthetic */ Context f386b;

                        {
                            this.f385a = r2;
                            this.f386b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            if (this.f385a != FlurryPerformance.NONE) {
                                C1710dk.m827a().mo16432a(this.f386b, null);
                            }
                            if ((this.f385a & FlurryPerformance.COLD_START) == FlurryPerformance.COLD_START) {
                                C1703di a = C1703di.m811a();
                                a.f937a = true;
                                if (a.f938b) {
                                    a.mo16410b();
                                }
                            }
                            if ((this.f385a & FlurryPerformance.SCREEN_TIME) == FlurryPerformance.SCREEN_TIME) {
                                C1715dn.m835a().f969c = true;
                            }
                        }
                    });
                    a.runAsync(new C1738eb(z) {

                        /* renamed from: a */
                        final /* synthetic */ boolean f388a;

                        {
                            this.f388a = r2;
                        }

                        /* renamed from: a */
                        public final void mo16236a() throws Exception {
                            C1948n.m1229a().f1430p.mo16533a(this.f388a);
                        }
                    });
                    C1479a.f315b.set(true);
                    if (z8) {
                        C1685cy.m768d(str3, "Force start session");
                        a.mo16233a(context.getApplicationContext());
                    }
                    return;
                }
                throw new IllegalArgumentException("API key not specified");
            }
        }
    }

    public static class UserProperties {
        public static final String PROPERTY_CURRENCY_PREFERENCE = "Flurry.CurrencyPreference";
        public static final String PROPERTY_PURCHASER = "Flurry.Purchaser";
        public static final String PROPERTY_REGISTERED_USER = "Flurry.RegisteredUser";
        public static final String PROPERTY_SUBSCRIBER = "Flurry.Subscriber";

        public static void set(String str, String str2) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.set. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, str2) {

                        /* renamed from: a */
                        final /* synthetic */ String f349a;

                        /* renamed from: b */
                        final /* synthetic */ String f350b;

                        {
                            this.f349a = r2;
                            this.f350b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1175a(this.f349a, this.f350b);
                        }
                    });
                }
            }
        }

        public static void set(String str, List<String> list) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.set. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, list) {

                        /* renamed from: a */
                        final /* synthetic */ String f352a;

                        /* renamed from: b */
                        final /* synthetic */ List f353b;

                        {
                            this.f352a = r2;
                            this.f353b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1177a(this.f352a, this.f353b);
                        }
                    });
                }
            }
        }

        public static void add(String str, String str2) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.add. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, str2) {

                        /* renamed from: a */
                        final /* synthetic */ String f355a;

                        /* renamed from: b */
                        final /* synthetic */ String f356b;

                        {
                            this.f355a = r2;
                            this.f356b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1180b(this.f355a, this.f356b);
                        }
                    });
                }
            }
        }

        public static void add(String str, List<String> list) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.add. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, list) {

                        /* renamed from: a */
                        final /* synthetic */ String f361a;

                        /* renamed from: b */
                        final /* synthetic */ List f362b;

                        {
                            this.f361a = r2;
                            this.f362b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1181b(this.f361a, this.f362b);
                        }
                    });
                }
            }
        }

        public static void remove(String str, String str2) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.remove. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, str2) {

                        /* renamed from: a */
                        final /* synthetic */ String f364a;

                        /* renamed from: b */
                        final /* synthetic */ String f365b;

                        {
                            this.f364a = r2;
                            this.f365b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1182c(this.f364a, this.f365b);
                        }
                    });
                }
            }
        }

        public static void remove(String str, List<String> list) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.remove. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str, list) {

                        /* renamed from: a */
                        final /* synthetic */ String f367a;

                        /* renamed from: b */
                        final /* synthetic */ List f368b;

                        {
                            this.f367a = r2;
                            this.f368b = r3;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1183c(this.f367a, this.f368b);
                        }
                    });
                }
            }
        }

        public static void remove(String str) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.remove. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str) {

                        /* renamed from: a */
                        final /* synthetic */ String f370a;

                        {
                            this.f370a = r2;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1174a(this.f370a);
                        }
                    });
                }
            }
        }

        public static void flag(String str) {
            if (FlurryAgent.m352b()) {
                C1479a a = C1479a.m353a();
                if (!C1479a.f315b.get()) {
                    C1685cy.m768d("FlurryAgentImpl", "Invalid call to UserProperties.flag. Flurry is not initialized");
                } else {
                    a.runAsync(new C1738eb(str) {

                        /* renamed from: a */
                        final /* synthetic */ String f376a;

                        {
                            this.f376a = r2;
                        }

                        /* renamed from: a */
                        public final void mo16236a() {
                            C1923jj.m1179b(this.f376a);
                        }
                    });
                }
            }
        }
    }

    private FlurryAgent() {
    }

    public static int getAgentVersion() {
        C1479a.m353a();
        return C1479a.m354b();
    }

    public static String getReleaseVersion() {
        C1479a.m353a();
        return C1479a.m355c();
    }

    public static List<FlurryModule> getAddOnModules() {
        return C1479a.m353a().f317a;
    }

    public static void setVersionName(String str) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setVersionName. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(str) {

                    /* renamed from: a */
                    final /* synthetic */ String f393a;

                    {
                        this.f393a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1601bl.m537a().f669a = this.f393a;
                        C1883hz.m1128b();
                    }
                });
            }
        }
    }

    public static void setReportLocation(boolean z) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setReportLocation. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(z) {

                    /* renamed from: a */
                    final /* synthetic */ boolean f395a;

                    {
                        this.f395a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1558at atVar = C1948n.m1229a().f1415a;
                        atVar.f552a = this.f395a;
                        atVar.refresh();
                    }
                });
            }
        }
    }

    public static void addOrigin(String str, String str2) {
        C1479a.m353a().mo16235a(str, str2, null);
    }

    public static void addOrigin(String str, String str2, Map<String, String> map) {
        if (m352b()) {
            C1479a.m353a().mo16235a(str, str2, map);
        }
    }

    public static void setInstantAppName(String str) {
        C1479a a = C1479a.m353a();
        if (!C1479a.f315b.get()) {
            C1685cy.m768d("FlurryAgentImpl", "Invalid call to addOrigin. Flurry is not initialized");
        } else {
            a.runAsync(new C1738eb(str) {

                /* renamed from: a */
                final /* synthetic */ String f347a;

                {
                    this.f347a = r2;
                }

                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    C1546an anVar = C1948n.m1229a().f1427m;
                    anVar.f517a = this.f347a;
                    anVar.mo16264b();
                }
            });
        }
    }

    public static String getInstantAppName() {
        C1479a.m353a();
        return C1479a.m357e();
    }

    public static synchronized Consent getFlurryConsent() {
        Consent d;
        synchronized (FlurryAgent.class) {
            C1479a.m353a();
            d = C1479a.m356d();
        }
        return d;
    }

    public static synchronized boolean updateFlurryConsent(Consent consent) {
        synchronized (FlurryAgent.class) {
            if (!m352b()) {
                return false;
            }
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to updateFlurryConsent. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(consent) {

                    /* renamed from: a */
                    final /* synthetic */ Consent f345a;

                    {
                        this.f345a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() throws Exception {
                        C1948n.m1229a().f1426l.mo16534a(this.f345a);
                    }
                });
            }
            return true;
        }
    }

    public static void onStartSession(Context context) {
        if (m352b()) {
            C1479a.m353a().mo16233a(context);
        }
    }

    public static void onEndSession(Context context) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            String str = "FlurryAgentImpl";
            if (context instanceof Activity) {
                C1685cy.m756a(str, "Activity's session is controlled by Flurry SDK");
            } else if (!C1479a.f315b.get()) {
                C1685cy.m768d(str, "Invalid call to onStartSession. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb() {
                    /* renamed from: a */
                    public final void mo16236a() {
                        C1948n.m1229a().f1425k.mo16286b(C1587bd.FOREGROUND, false);
                    }
                });
            }
        }
    }

    public static boolean isSessionActive() {
        if (!m352b()) {
            return false;
        }
        C1479a.m353a();
        return C1479a.m358f();
    }

    public static String getSessionId() {
        if (!m352b()) {
            return null;
        }
        C1479a.m353a();
        return C1479a.m359g();
    }

    public static FlurryEventRecordStatus logEvent(String str) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (!m352b()) {
            return flurryEventRecordStatus;
        }
        return C1479a.m353a().mo16232a(str, Collections.emptyMap(), false, false, System.currentTimeMillis(), SystemClock.elapsedRealtime());
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (!m352b()) {
            return flurryEventRecordStatus;
        }
        String str2 = "FlurryAgent";
        if (str == null) {
            C1685cy.m762b(str2, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        }
        if (map == null) {
            C1685cy.m766c(str2, "String parameters passed to logEvent was null.");
        }
        return C1479a.m353a().mo16232a(str, map, false, false, System.currentTimeMillis(), SystemClock.elapsedRealtime());
    }

    public static FlurryEventRecordStatus logEvent(String str, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (!m352b()) {
            return flurryEventRecordStatus;
        }
        return C1479a.m353a().mo16232a(str, Collections.emptyMap(), z, true, System.currentTimeMillis(), SystemClock.elapsedRealtime());
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (!m352b()) {
            return flurryEventRecordStatus;
        }
        String str2 = "FlurryAgent";
        if (str == null) {
            C1685cy.m762b(str2, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        }
        if (map == null) {
            C1685cy.m766c(str2, "String parameters passed to logEvent was null.");
        }
        return C1479a.m353a().mo16232a(str, map, z, true, System.currentTimeMillis(), SystemClock.elapsedRealtime());
    }

    public static void logPayment(int i, Intent intent, Map<String, String> map) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            C15125 r1 = new Runnable(i, intent, hashMap, currentTimeMillis, elapsedRealtime) {

                /* renamed from: a */
                final /* synthetic */ int f402a;

                /* renamed from: b */
                final /* synthetic */ Intent f403b;

                /* renamed from: c */
                final /* synthetic */ Map f404c;

                /* renamed from: d */
                final /* synthetic */ long f405d;

                /* renamed from: e */
                final /* synthetic */ long f406e;

                {
                    this.f402a = r2;
                    this.f403b = r3;
                    this.f404c = r4;
                    this.f405d = r5;
                    this.f406e = r7;
                }

                public final void run() {
                    C1837gl.m1078a(this.f402a, this.f403b, this.f404c, this.f405d, this.f406e);
                }
            };
            a.runAsync(r1);
        }
    }

    public static FlurryEventRecordStatus logPayment(String str, String str2, int i, double d, String str3, String str4, Map<String, String> map) {
        Map<String, String> map2 = map;
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (!m352b()) {
            return flurryEventRecordStatus;
        }
        C1479a a = C1479a.m353a();
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        HashMap hashMap = new HashMap();
        if (map2 != null) {
            hashMap.putAll(map2);
        }
        C15136 r3 = new Runnable(str, str2, i, d, str3, str4, hashMap, currentTimeMillis, elapsedRealtime) {

            /* renamed from: a */
            final /* synthetic */ String f408a;

            /* renamed from: b */
            final /* synthetic */ String f409b;

            /* renamed from: c */
            final /* synthetic */ int f410c;

            /* renamed from: d */
            final /* synthetic */ double f411d;

            /* renamed from: e */
            final /* synthetic */ String f412e;

            /* renamed from: f */
            final /* synthetic */ String f413f;

            /* renamed from: g */
            final /* synthetic */ Map f414g;

            /* renamed from: h */
            final /* synthetic */ long f415h;

            /* renamed from: i */
            final /* synthetic */ long f416i;

            {
                this.f408a = r2;
                this.f409b = r3;
                this.f410c = r4;
                this.f411d = r5;
                this.f412e = r7;
                this.f413f = r8;
                this.f414g = r9;
                this.f415h = r10;
                this.f416i = r12;
            }

            public final void run() {
                C1837gl.m1074a(this.f408a, this.f409b, this.f410c, this.f411d, this.f412e, this.f413f, this.f414g, this.f415h, this.f416i);
            }
        };
        a.runAsync(r3);
        return FlurryEventRecordStatus.kFlurryEventRecorded;
    }

    public static void endTimedEvent(String str) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to endTimedEvent. Flurry is not initialized");
                return;
            }
            C15147 r1 = new C1738eb(str, System.currentTimeMillis(), SystemClock.elapsedRealtime()) {

                /* renamed from: a */
                final /* synthetic */ String f418a;

                /* renamed from: b */
                final /* synthetic */ long f419b;

                /* renamed from: c */
                final /* synthetic */ long f420c;

                {
                    this.f418a = r2;
                    this.f419b = r3;
                    this.f420c = r5;
                }

                /* renamed from: a */
                public final void mo16236a() {
                    C1837gl.m1075a(this.f418a, Collections.emptyMap(), true, false, this.f419b, this.f420c);
                }
            };
            a.runAsync(r1);
        }
    }

    public static void endTimedEvent(String str, Map<String, String> map) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to endTimedEvent. Flurry is not initialized");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            C15158 r1 = new C1738eb(str, hashMap, currentTimeMillis, elapsedRealtime) {

                /* renamed from: a */
                final /* synthetic */ String f422a;

                /* renamed from: b */
                final /* synthetic */ Map f423b;

                /* renamed from: c */
                final /* synthetic */ long f424c;

                /* renamed from: d */
                final /* synthetic */ long f425d;

                {
                    this.f422a = r2;
                    this.f423b = r3;
                    this.f424c = r4;
                    this.f425d = r6;
                }

                /* renamed from: a */
                public final void mo16236a() {
                    C1837gl.m1075a(this.f422a, this.f423b, true, false, this.f424c, this.f425d);
                }
            };
            a.runAsync(r1);
        }
    }

    public static void onError(String str, String str2, String str3) {
        StackTraceElement[] stackTraceElementArr;
        if (m352b()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace == null || stackTrace.length <= 4) {
                stackTraceElementArr = stackTrace;
            } else {
                StackTraceElement[] stackTraceElementArr2 = new StackTraceElement[(stackTrace.length - 4)];
                System.arraycopy(stackTrace, 4, stackTraceElementArr2, 0, stackTraceElementArr2.length);
                stackTraceElementArr = stackTraceElementArr2;
            }
            C1479a.m353a().mo16234a(str, str2, str3, null, stackTraceElementArr);
        }
    }

    public static void onError(String str, String str2, String str3, Map<String, String> map) {
        StackTraceElement[] stackTraceElementArr;
        if (m352b()) {
            String str4 = "FlurryAgent";
            if (TextUtils.isEmpty(str)) {
                C1685cy.m762b(str4, "String errorId passed to onError was empty.");
            } else if (TextUtils.isEmpty(str2)) {
                C1685cy.m762b(str4, "String message passed to onError was empty.");
            } else if (TextUtils.isEmpty(str3)) {
                C1685cy.m762b(str4, "String errorClass passed to onError was empty.");
            } else {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                if (stackTrace == null || stackTrace.length <= 4) {
                    stackTraceElementArr = stackTrace;
                } else {
                    StackTraceElement[] stackTraceElementArr2 = new StackTraceElement[(stackTrace.length - 4)];
                    System.arraycopy(stackTrace, 4, stackTraceElementArr2, 0, stackTraceElementArr2.length);
                    stackTraceElementArr = stackTraceElementArr2;
                }
                C1479a.m353a().mo16234a(str, str2, str3, map, stackTraceElementArr);
            }
        }
    }

    public static void onError(String str, String str2, Throwable th) {
        onError(str, str2, th, null);
    }

    public static void onError(String str, String str2, Throwable th, Map<String, String> map) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to onError. Flurry is not initialized");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            C148110 r1 = new C1738eb(str, currentTimeMillis, str2, th, hashMap) {

                /* renamed from: a */
                final /* synthetic */ String f321a;

                /* renamed from: b */
                final /* synthetic */ long f322b;

                /* renamed from: c */
                final /* synthetic */ String f323c;

                /* renamed from: d */
                final /* synthetic */ Throwable f324d;

                /* renamed from: e */
                final /* synthetic */ Map f325e;

                {
                    this.f321a = r2;
                    this.f322b = r3;
                    this.f323c = r5;
                    this.f324d = r6;
                    this.f325e = r7;
                }

                /* renamed from: a */
                public final void mo16236a() {
                    C1948n.m1229a().f1420f.mo16239a(this.f321a, this.f322b, this.f323c, this.f324d.getClass().getName(), this.f324d, C1962x.m1243a(), this.f325e);
                }
            };
            a.runAsync(r1);
        }
    }

    public static void logBreadcrumb(String str) {
        if (m352b()) {
            if (TextUtils.isEmpty(str)) {
                C1685cy.m762b("FlurryAgent", "Crash breadcrumb cannot be empty.");
                return;
            }
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to logBreadcrumb. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(str) {

                    /* renamed from: a */
                    final /* synthetic */ String f327a;

                    {
                        this.f327a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1518ab abVar = C1948n.m1229a().f1420f;
                        C1960v vVar = new C1960v(this.f327a, System.currentTimeMillis());
                        if (abVar.f442a != null) {
                            abVar.f442a.mo16537a(vVar);
                        }
                    }
                });
            }
        }
    }

    public static void setAge(int i) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setAge. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(i) {

                    /* renamed from: a */
                    final /* synthetic */ int f331a;

                    {
                        this.f331a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        int i = this.f331a;
                        if (i > 0 && i < 110) {
                            long time = new Date(new Date(System.currentTimeMillis() - (((long) this.f331a) * 31449600000L)).getYear(), 1, 1).getTime();
                            if (time <= 0) {
                                C1685cy.m768d("BirthdateFrame", "Birth date is invalid, do not send the frame.");
                            } else {
                                C1771fb.m926a().mo16467a(new C1826gd(new C1827ge(Long.valueOf(time))));
                            }
                        }
                    }
                });
            }
        }
    }

    public static void setGender(byte b) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setGender. Flurry is not initialized");
                return;
            }
            boolean z = true;
            if (!(b == 0 || b == 1 || b == -1)) {
                z = false;
            }
            if (z) {
                a.runAsync(new C1738eb(b) {

                    /* renamed from: a */
                    final /* synthetic */ byte f333a;

                    {
                        this.f333a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1771fb.m926a().mo16467a(new C1843gp(new C1844gq(this.f333a)));
                    }
                });
            }
        }
    }

    public static void setUserId(String str) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setUserId. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(str) {

                    /* renamed from: a */
                    final /* synthetic */ String f335a;

                    {
                        this.f335a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1519ac acVar = C1948n.m1229a().f1422h;
                        String str = this.f335a;
                        acVar.f445b = str;
                        C1771fb.m926a().mo16467a(new C1864hh(new C1865hi(str)));
                    }
                });
            }
        }
    }

    public static void setSessionOrigin(String str, String str2) {
        if (m352b()) {
            if (TextUtils.isEmpty(str)) {
                C1685cy.m762b("FlurryAgent", "String originName passed to setSessionOrigin was empty.");
                return;
            }
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setSessionOrigin. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(str, str2) {

                    /* renamed from: a */
                    final /* synthetic */ String f337a;

                    /* renamed from: b */
                    final /* synthetic */ String f338b;

                    {
                        this.f337a = r2;
                        this.f338b = r3;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1771fb.m926a().mo16467a(new C1853gz(new C1857ha(this.f337a, this.f338b)));
                    }
                });
            }
        }
    }

    public static void addSessionProperty(String str, String str2) {
        if (m352b()) {
            if (TextUtils.isEmpty(str)) {
                C1685cy.m762b("FlurryAgent", "Session property name was empty");
                return;
            }
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to addSessionProperty. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(str, str2) {

                    /* renamed from: a */
                    final /* synthetic */ String f340a;

                    /* renamed from: b */
                    final /* synthetic */ String f341b;

                    {
                        this.f340a = r2;
                        this.f341b = r3;
                    }

                    /* renamed from: a */
                    public final void mo16236a() {
                        C1771fb.m926a().mo16467a(new C1858hb(new C1859hc(this.f340a, this.f341b)));
                    }
                });
            }
        }
    }

    public static void openPrivacyDashboard(Request request) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            a.runAsync(new C1738eb(request) {

                /* renamed from: a */
                final /* synthetic */ Request f343a;

                {
                    this.f343a = r2;
                }

                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    C1744eg.m886a(this.f343a);
                }
            });
        }
    }

    public static void setDataSaleOptOut(boolean z) {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to setDataSaleOptOut. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb(z) {

                    /* renamed from: a */
                    final /* synthetic */ boolean f372a;

                    {
                        this.f372a = r2;
                    }

                    /* renamed from: a */
                    public final void mo16236a() throws Exception {
                        C1948n.m1229a().f1430p.mo16533a(this.f372a);
                    }
                });
            }
        }
    }

    public static void deleteData() {
        if (m352b()) {
            C1479a a = C1479a.m353a();
            if (!C1479a.f315b.get()) {
                C1685cy.m768d("FlurryAgentImpl", "Invalid call to deleteData. Flurry is not initialized");
            } else {
                a.runAsync(new C1738eb() {
                    /* renamed from: a */
                    public final void mo16236a() throws Exception {
                        C1948n.m1229a().f1430p.notifyObservers(new C1956s(C1957a.f1449c));
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static boolean m352b() {
        if (C1734dz.m872a(16)) {
            return true;
        }
        C1685cy.m762b("FlurryAgent", String.format(Locale.getDefault(), "Device SDK Version older than %d", new Object[]{Integer.valueOf(16)}));
        return false;
    }
}
