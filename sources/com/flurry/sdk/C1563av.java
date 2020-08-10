package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.telephony.CellSignalStrength;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.flurry.sdk.C1561au.C1562a;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/* renamed from: com.flurry.sdk.av */
public class C1563av extends C1942m<C1561au> {
    protected static long MINIMUM_REFRESH_INTERVAL = 3600000;

    /* renamed from: a */
    private boolean f575a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public boolean f576b;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f577d = false;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public String f578e = null;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public String f579f = null;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public String f580g = null;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public String f581h = null;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public String f582i = null;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public String f583j = null;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int f584k = -1;

    /* renamed from: l */
    private C1951q f585l;

    /* renamed from: m */
    private PhoneStateListener f586m;
    protected C1949o<C1955r> mAppStateListener = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            if (((C1955r) obj).f1445b == C1950p.FOREGROUND) {
                C1563av.this.refresh();
            }
        }
    };
    protected BroadcastReceiver mNetworkChangeReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            C1563av.this.refresh();
        }
    };

    /* access modifiers changed from: protected */
    public PhoneStateListener getPhoneStateListener() {
        if (this.f586m == null) {
            this.f586m = new PhoneStateListener() {

                /* renamed from: b */
                private long f590b;

                public final void onSignalStrengthsChanged(final SignalStrength signalStrength) {
                    super.onSignalStrengthsChanged(signalStrength);
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.f590b > C1563av.MINIMUM_REFRESH_INTERVAL) {
                        this.f590b = currentTimeMillis;
                        C1563av.this.runAsync(new C1738eb() {
                            /* renamed from: a */
                            public final void mo16236a() throws Exception {
                                C1563av.this.getCellularData(signalStrength);
                                C1563av.this.refresh();
                            }
                        });
                    }
                }
            };
        }
        return this.f586m;
    }

    public C1563av(C1951q qVar) {
        super("NetworkProvider");
        if (C1774fd.m932c()) {
            m482c();
            this.f585l = qVar;
            this.f585l.subscribe(this.mAppStateListener);
            return;
        }
        this.f576b = true;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static boolean m480b() {
        if (!C1774fd.m932c()) {
            return true;
        }
        NetworkInfo activeNetworkInfo = m483d().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void getCellularData(SignalStrength signalStrength) {
        String str;
        int a;
        TelephonyManager e = m485e();
        String networkOperatorName = e.getNetworkOperatorName();
        String networkOperator = e.getNetworkOperator();
        String simOperator = e.getSimOperator();
        String simOperatorName = e.getSimOperatorName();
        String str2 = "";
        if (VERSION.SDK_INT >= 28) {
            try {
                CharSequence simCarrierIdName = e.getSimCarrierIdName();
                if (simCarrierIdName != null) {
                    str2 = simCarrierIdName.toString();
                }
            } catch (NoSuchMethodError unused) {
            }
        }
        if (VERSION.SDK_INT < 29 || C1774fd.m933d()) {
            try {
                str = Integer.toString(e.getNetworkType());
            } catch (SecurityException unused2) {
            }
            a = m471a(signalStrength);
            if (TextUtils.equals(this.f578e, networkOperatorName) || !TextUtils.equals(this.f579f, networkOperator) || !TextUtils.equals(this.f580g, simOperator) || !TextUtils.equals(this.f581h, str2) || !TextUtils.equals(this.f582i, simOperatorName) || !TextUtils.equals(this.f583j, str) || this.f584k != a) {
                StringBuilder sb = new StringBuilder("Cellular Name: ");
                sb.append(networkOperatorName);
                sb.append(", Operator: ");
                sb.append(networkOperator);
                sb.append(", Sim Operator: ");
                sb.append(simOperator);
                sb.append(", Sim Id: ");
                sb.append(str2);
                sb.append(", Sim Name: ");
                sb.append(simOperatorName);
                sb.append(", Band: ");
                sb.append(str);
                sb.append(", Signal Strength: ");
                sb.append(a);
                C1685cy.m754a(3, "NetworkProvider", sb.toString());
                this.f577d = true;
                this.f578e = networkOperatorName;
                this.f579f = networkOperator;
                this.f580g = simOperator;
                this.f581h = str2;
                this.f582i = simOperatorName;
                this.f583j = str;
                this.f584k = a;
            }
            return;
        }
        str = "0";
        a = m471a(signalStrength);
        if (TextUtils.equals(this.f578e, networkOperatorName)) {
        }
        StringBuilder sb2 = new StringBuilder("Cellular Name: ");
        sb2.append(networkOperatorName);
        sb2.append(", Operator: ");
        sb2.append(networkOperator);
        sb2.append(", Sim Operator: ");
        sb2.append(simOperator);
        sb2.append(", Sim Id: ");
        sb2.append(str2);
        sb2.append(", Sim Name: ");
        sb2.append(simOperatorName);
        sb2.append(", Band: ");
        sb2.append(str);
        sb2.append(", Signal Strength: ");
        sb2.append(a);
        C1685cy.m754a(3, "NetworkProvider", sb2.toString());
        this.f577d = true;
        this.f578e = networkOperatorName;
        this.f579f = networkOperator;
        this.f580g = simOperator;
        this.f581h = str2;
        this.f582i = simOperatorName;
        this.f583j = str;
        this.f584k = a;
    }

    /* renamed from: a */
    private static int m471a(SignalStrength signalStrength) {
        int i;
        if (VERSION.SDK_INT >= 29) {
            try {
                for (CellSignalStrength dbm : signalStrength.getCellSignalStrengths()) {
                    int dbm2 = dbm.getDbm();
                    if (dbm2 != Integer.MAX_VALUE) {
                        return dbm2;
                    }
                }
            } catch (NoSuchMethodError unused) {
            }
        }
        char c = 0;
        try {
            return ((Integer) signalStrength.getClass().getMethod("getDbm", new Class[0]).invoke(signalStrength, new Object[0])).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
            if (signalStrength.isGsm()) {
                i = m472a(signalStrength, "getLteDbm", "rsrp", 9);
                if (i == Integer.MAX_VALUE) {
                    i = m472a(signalStrength, "getTdScdmaDbm", "mTdscdma", 14);
                    if (i <= -25 && i != Integer.MAX_VALUE) {
                        if (i >= -49) {
                            c = 4;
                        } else if (i >= -73) {
                            c = 3;
                        } else if (i >= -97) {
                            c = 2;
                        } else if (i >= -110) {
                            c = 1;
                        }
                    }
                    if (c == 0) {
                        i = m472a(signalStrength, "getWcdmaDbm", "mWcdma", 17);
                        if (i == Integer.MAX_VALUE) {
                            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                            int i2 = -1;
                            if ((gsmSignalStrength == 99 ? -1 : gsmSignalStrength) != -1) {
                                i2 = (gsmSignalStrength * 2) - 113;
                            }
                            i = i2;
                        }
                    }
                }
            } else {
                int cdmaDbm = signalStrength.getCdmaDbm();
                int evdoDbm = signalStrength.getEvdoDbm();
                i = (evdoDbm != -120 && (cdmaDbm == -120 || cdmaDbm >= evdoDbm)) ? evdoDbm : cdmaDbm;
            }
            return i;
        }
    }

    /* renamed from: a */
    private static int m472a(SignalStrength signalStrength, String str, String str2, int i) {
        int i2;
        try {
            i2 = ((Integer) signalStrength.getClass().getMethod(str, new Class[0]).invoke(signalStrength, new Object[0])).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            i2 = Integer.MAX_VALUE;
        }
        if (i2 == Integer.MAX_VALUE) {
            String signalStrength2 = signalStrength.toString();
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("=");
            int indexOf = signalStrength2.indexOf(sb.toString());
            if (indexOf != -1) {
                Scanner scanner = new Scanner(signalStrength2.substring(indexOf + str2.length() + 1));
                if (scanner.hasNextInt()) {
                    i2 = scanner.nextInt();
                    if (i2 == 99) {
                        i2 = Integer.MAX_VALUE;
                    }
                }
            }
        }
        if (i2 != Integer.MAX_VALUE) {
            return i2;
        }
        String[] split = signalStrength.toString().split(" ");
        if (split.length <= i) {
            return i2;
        }
        try {
            int parseInt = Integer.parseInt(split[i]);
            if (parseInt == 99) {
                return Integer.MAX_VALUE;
            }
            return parseInt;
        } catch (NumberFormatException unused2) {
            return i2;
        }
    }

    /* renamed from: c */
    private synchronized void m482c() {
        if (!this.f575a) {
            this.f576b = m480b();
            C1576b.m502a().registerReceiver(this.mNetworkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            m485e().listen(getPhoneStateListener(), 256);
            this.f575a = true;
        }
    }

    /* renamed from: d */
    private static ConnectivityManager m483d() {
        return (ConnectivityManager) C1576b.m502a().getSystemService("connectivity");
    }

    /* renamed from: e */
    private static TelephonyManager m485e() {
        return (TelephonyManager) C1576b.m502a().getSystemService("phone");
    }

    /* renamed from: f */
    private synchronized void m488f() {
        if (this.f575a) {
            C1576b.m502a().unregisterReceiver(this.mNetworkChangeReceiver);
            m485e().listen(getPhoneStateListener(), 0);
            this.f586m = null;
            this.f575a = false;
        }
    }

    public void destroy() {
        super.destroy();
        m488f();
        C1951q qVar = this.f585l;
        if (qVar != null) {
            qVar.unsubscribe(this.mAppStateListener);
        }
    }

    public boolean isNetworkEnabled() {
        return this.f576b;
    }

    public C1562a getNetworkType() {
        if (!C1774fd.m932c()) {
            return C1562a.NONE_OR_UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = m483d().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return C1562a.NONE_OR_UNKNOWN;
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            if (type == 1) {
                return C1562a.WIFI;
            }
            if (!(type == 2 || type == 3 || type == 4 || type == 5)) {
                if (type != 8) {
                    return C1562a.NETWORK_AVAILABLE;
                }
                return C1562a.NONE_OR_UNKNOWN;
            }
        }
        return C1562a.CELL;
    }

    public static String getCellularBand(int i) {
        switch (i) {
            case 1:
                return "2G - GPRS";
            case 2:
                return "2G - EDGE";
            case 3:
                return "3G - UMTS";
            case 4:
                return "2G - CDMA";
            case 5:
                return "3G - EVDO_0";
            case 6:
                return "3G - EVDO_A";
            case 7:
                return "2G - 1xRTT";
            case 8:
                return "3G - HSDPA";
            case 9:
                return "3G - HSUPA";
            case 10:
                return "3G - HSPA";
            case 11:
                return "2G - IDEN";
            case 12:
                return "3G - EVDO_B";
            case 13:
                return "4G - LTE";
            case 14:
                return "3G - EHRPD";
            case 15:
                return "3G - HSPAP";
            case 16:
                return "2G - GSM";
            case 17:
                return "3G - TD_SCDMA";
            case 18:
                return "4G - IWLAN";
            case 20:
                return "5G - NR";
            default:
                return "Unknown - ".concat(String.valueOf(i));
        }
    }

    public void subscribe(C1949o<C1561au> oVar) {
        super.subscribe(oVar);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1563av.this.f576b = C1563av.m480b();
                C1563av avVar = C1563av.this;
                C1561au auVar = new C1561au(avVar.getNetworkType(), C1563av.this.f576b, C1563av.this.f578e, C1563av.this.f579f, C1563av.this.f580g, C1563av.this.f581h, C1563av.this.f582i, C1563av.this.f583j, C1563av.this.f584k);
                avVar.notifyObservers(auVar);
            }
        });
    }

    public void refresh() {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                boolean a = C1563av.m480b();
                if (C1563av.this.f576b != a || C1563av.this.f577d) {
                    C1563av.this.f576b = a;
                    C1563av.this.f577d = false;
                    C1563av avVar = C1563av.this;
                    C1561au auVar = new C1561au(avVar.getNetworkType(), C1563av.this.f576b, C1563av.this.f578e, C1563av.this.f579f, C1563av.this.f580g, C1563av.this.f581h, C1563av.this.f582i, C1563av.this.f583j, C1563av.this.f584k);
                    avVar.notifyObservers(auVar);
                }
            }
        });
    }
}
