package lib.android.paypal.com.magnessdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import lib.android.paypal.com.magnessdk.p100a.C5984a;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: lib.android.paypal.com.magnessdk.f */
final class C5994f extends C5990d {

    /* renamed from: aJ */
    private static final int f4356aJ = 255;

    /* renamed from: aK */
    private static final int f4357aK = 256;

    /* renamed from: aL */
    private static final int f4358aL = 16;

    /* renamed from: aM */
    private static final int f4359aM = 32;

    /* renamed from: aN */
    private static final int f4360aN = 32;

    /* renamed from: aO */
    private static final String f4361aO = "invalid_input";

    /* renamed from: aP */
    private static final String f4362aP = "SG1hY1NIQTI1Ng==";

    /* renamed from: aQ */
    private static final String f4363aQ = "RiskManagerCT";

    /* renamed from: aR */
    private static final String f4364aR = "00:00:00:00:00:00";

    /* renamed from: aS */
    private static C5994f f4365aS;

    /* renamed from: aT */
    private int f4366aT = -1;

    /* renamed from: aU */
    private int f4367aU = -1;

    /* renamed from: aV */
    private int f4368aV;

    /* renamed from: aW */
    private int f4369aW = -1;

    /* renamed from: aX */
    private int f4370aX = -1;

    /* renamed from: aY */
    private int f4371aY = -1;

    /* renamed from: aZ */
    private String f4372aZ;

    /* renamed from: bA */
    private long f4373bA;

    /* renamed from: bB */
    private long f4374bB = -1;

    /* renamed from: bC */
    private boolean f4375bC;

    /* renamed from: bD */
    private boolean f4376bD;

    /* renamed from: bE */
    private boolean f4377bE;

    /* renamed from: bF */
    private boolean f4378bF;

    /* renamed from: bG */
    private boolean f4379bG;

    /* renamed from: bH */
    private boolean f4380bH;

    /* renamed from: bI */
    private boolean f4381bI;

    /* renamed from: bJ */
    private boolean f4382bJ;

    /* renamed from: bK */
    private boolean f4383bK;

    /* renamed from: bL */
    private Map<String, String> f4384bL;

    /* renamed from: bM */
    private NetworkInfo f4385bM;

    /* renamed from: bN */
    private WifiInfo f4386bN;

    /* renamed from: bO */
    private GsmCellLocation f4387bO;

    /* renamed from: bP */
    private CdmaCellLocation f4388bP;

    /* renamed from: bQ */
    private TelephonyManager f4389bQ;

    /* renamed from: bR */
    private WifiManager f4390bR;

    /* renamed from: bS */
    private ConnectivityManager f4391bS;

    /* renamed from: bT */
    private LocationManager f4392bT;

    /* renamed from: bU */
    private Location f4393bU;

    /* renamed from: ba */
    private String f4394ba;

    /* renamed from: bb */
    private String f4395bb;

    /* renamed from: bc */
    private String f4396bc;

    /* renamed from: bd */
    private String f4397bd;

    /* renamed from: be */
    private String f4398be;

    /* renamed from: bf */
    private String f4399bf;

    /* renamed from: bg */
    private String f4400bg;

    /* renamed from: bh */
    private String f4401bh;

    /* renamed from: bi */
    private String f4402bi;

    /* renamed from: bj */
    private String f4403bj;

    /* renamed from: bk */
    private String f4404bk;

    /* renamed from: bl */
    private String f4405bl;

    /* renamed from: bm */
    private String f4406bm;

    /* renamed from: bn */
    private String f4407bn;

    /* renamed from: bo */
    private String f4408bo;

    /* renamed from: bp */
    private String f4409bp;

    /* renamed from: bq */
    private String f4410bq;

    /* renamed from: br */
    private String f4411br;

    /* renamed from: bs */
    private String f4412bs;

    /* renamed from: bt */
    private String f4413bt;

    /* renamed from: bu */
    private String f4414bu;

    /* renamed from: bv */
    private String f4415bv;

    /* renamed from: bw */
    private String f4416bw;

    /* renamed from: bx */
    private List<String> f4417bx;

    /* renamed from: by */
    private List<String> f4418by;

    /* renamed from: bz */
    private List<String> f4419bz;

    private C5994f() {
    }

    /* renamed from: a */
    private Location m4073a(LocationManager locationManager) {
        Location location = null;
        if (locationManager == null) {
            return null;
        }
        try {
            List providers = locationManager.getProviders(true);
            for (int size = providers.size() - 1; size >= 0; size--) {
                location = locationManager.getLastKnownLocation((String) providers.get(size));
                if (location != null) {
                    break;
                }
            }
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        return location;
    }

    /* renamed from: a */
    private String m4074a(String str) {
        if (str == null || str.isEmpty()) {
            str = "invalid input in dc method";
        }
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        instance.update(str.getBytes());
        byte[] digest = instance.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString().substring(0, 32);
    }

    /* renamed from: a */
    private String m4075a(String str, String str2, long j, String str3) {
        String str4;
        String str5;
        StringBuilder sb;
        if (!C5989c.m4038a((Object) str) || !C5989c.m4038a((Object) str2) || !C5989c.m4038a((Object) Long.valueOf(j))) {
            String str6 = "";
            if (C5989c.m4038a((Object) str)) {
                str = str6;
            }
            if (C5989c.m4038a((Object) str2)) {
                str2 = str6;
            }
            if (C5989c.m4038a((Object) Long.valueOf(j))) {
                sb = new StringBuilder();
                sb.append(str);
            } else {
                sb = new StringBuilder();
                sb.append(str);
                sb.append(j);
            }
            sb.append(str2);
            str4 = sb.toString();
        } else {
            str4 = f4361aO;
        }
        String a = C5989c.m4034a(f4362aP);
        if (C5989c.m4038a((Object) Long.valueOf(j))) {
            str5 = C5989c.m4034a(str3);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(C5989c.m4034a(str3));
            sb2.append(j);
            str5 = sb2.toString();
        }
        Mac instance = Mac.getInstance(a);
        instance.init(new SecretKeySpec(str5.getBytes(), a));
        byte[] doFinal = instance.doFinal(str4.getBytes());
        StringBuilder sb3 = new StringBuilder();
        for (byte b : doFinal) {
            sb3.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb3.toString().substring(0, 32);
    }

    /* renamed from: a */
    private ArrayList<String> m4076a(WifiManager wifiManager) {
        if (wifiManager == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        List scanResults = wifiManager.getScanResults();
        if (!(scanResults == null || scanResults.size() == 0)) {
            String bssid = wifiManager.getConnectionInfo().getBSSID();
            if (bssid != null && !bssid.equals(f4364aR)) {
                int i = -1;
                int i2 = Integer.MIN_VALUE;
                for (int i3 = 0; i3 < scanResults.size(); i3++) {
                    if (!bssid.equals(((ScanResult) scanResults.get(i3)).BSSID)) {
                        int i4 = ((ScanResult) scanResults.get(i3)).level;
                        if (i2 < i4) {
                            i = i3;
                            i2 = i4;
                        }
                    }
                }
                arrayList.add(bssid);
                if (i != -1) {
                    arrayList.add(((ScanResult) scanResults.get(i)).BSSID);
                }
                return arrayList;
            }
        }
        return null;
    }

    /* renamed from: a */
    private List<String> m4077a(boolean z) {
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses != null && inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (!(inetAddress instanceof Inet6Address) || z) {
                            arrayList.add(hostAddress);
                        }
                    }
                }
            }
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    /* renamed from: a */
    private JSONObject m4078a(Location location) {
        if (location != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("{\"lat\":");
                sb.append(location.getLatitude());
                sb.append(",\"lng\":");
                sb.append(location.getLongitude());
                sb.append(",\"acc\":");
                sb.append(location.getAccuracy());
                sb.append(",\"timestamp\":");
                sb.append(location.getTime());
                sb.append("}");
                return new JSONObject(sb.toString());
            } catch (Exception e) {
                C5988a.m4032a(getClass(), 3, (Throwable) e);
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [android.telephony.gsm.GsmCellLocation] */
    /* JADX WARNING: type inference failed for: r2v3, types: [android.telephony.gsm.GsmCellLocation] */
    /* JADX WARNING: type inference failed for: r2v4, types: [android.telephony.cdma.CdmaCellLocation] */
    /* JADX WARNING: type inference failed for: r2v6, types: [android.telephony.cdma.CdmaCellLocation] */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.telephony.cdma.CdmaCellLocation, android.telephony.gsm.GsmCellLocation]
      uses: [android.telephony.gsm.GsmCellLocation, android.telephony.cdma.CdmaCellLocation]
      mth insns count: 43
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m4079a(android.telephony.TelephonyManager r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r5.getPhoneType()
            if (r0 == 0) goto L_0x0067
            r1 = 1
            r2 = 0
            r3 = 3
            if (r0 == r1) goto L_0x0046
            r1 = 2
            if (r0 == r1) goto L_0x002e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "unknown ("
            r0.append(r1)
            int r5 = r5.getPhoneType()
            r0.append(r5)
            java.lang.String r5 = ")"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
        L_0x002b:
            r4.f4407bn = r5
            goto L_0x006a
        L_0x002e:
            java.lang.String r0 = "cdma"
            r4.f4407bn = r0
            boolean r0 = r4.f4377bE     // Catch:{ Exception -> 0x005e }
            if (r0 == 0) goto L_0x0043
            android.telephony.CellLocation r5 = r5.getCellLocation()     // Catch:{ Exception -> 0x005e }
            java.lang.Class<android.telephony.cdma.CdmaCellLocation> r0 = android.telephony.cdma.CdmaCellLocation.class
            java.lang.Object r5 = lib.android.paypal.com.magnessdk.C5989c.m4033a(r5, r0)     // Catch:{ Exception -> 0x005e }
            r2 = r5
            android.telephony.cdma.CdmaCellLocation r2 = (android.telephony.cdma.CdmaCellLocation) r2     // Catch:{ Exception -> 0x005e }
        L_0x0043:
            r4.f4388bP = r2     // Catch:{ Exception -> 0x005e }
            goto L_0x006a
        L_0x0046:
            java.lang.String r0 = "gsm"
            r4.f4407bn = r0
            boolean r0 = r4.f4377bE     // Catch:{ Exception -> 0x005e }
            if (r0 == 0) goto L_0x005b
            android.telephony.CellLocation r5 = r5.getCellLocation()     // Catch:{ Exception -> 0x005e }
            java.lang.Class<android.telephony.gsm.GsmCellLocation> r0 = android.telephony.gsm.GsmCellLocation.class
            java.lang.Object r5 = lib.android.paypal.com.magnessdk.C5989c.m4033a(r5, r0)     // Catch:{ Exception -> 0x005e }
            r2 = r5
            android.telephony.gsm.GsmCellLocation r2 = (android.telephony.gsm.GsmCellLocation) r2     // Catch:{ Exception -> 0x005e }
        L_0x005b:
            r4.f4387bO = r2     // Catch:{ Exception -> 0x005e }
            goto L_0x006a
        L_0x005e:
            r5 = move-exception
            java.lang.Class r0 = r4.getClass()
            lib.android.paypal.com.magnessdk.p046b.C5988a.m4032a(r0, r3, r5)
            goto L_0x006a
        L_0x0067:
            java.lang.String r5 = "none"
            goto L_0x002b
        L_0x006a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: lib.android.paypal.com.magnessdk.C5994f.m4079a(android.telephony.TelephonyManager):void");
    }

    /* renamed from: b */
    private String m4080b(Context context) {
        String str = f4363aQ;
        int i = context.getSharedPreferences(str, 0).getInt(str, 0);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("");
        return sb.toString();
    }

    /* renamed from: b */
    private String m4081b(TelephonyManager telephonyManager) {
        try {
            return telephonyManager.getSimOperatorName();
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return null;
        }
    }

    /* renamed from: b */
    static C5994f m4082b() {
        if (f4365aS == null) {
            f4365aS = new C5994f();
            C5988a.m4031a(C5994f.class, 0, "creating RiskBlobDynamicData instance");
        }
        return f4365aS;
    }

    /* renamed from: b */
    private void m4083b(JSONObject jSONObject) {
        Map<String, String> map = this.f4384bL;
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                try {
                    jSONObject.put((String) entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    C5988a.m4032a(getClass(), 3, (Throwable) e);
                }
            }
        }
    }

    /* renamed from: c */
    private String m4084c() {
        List a = m4077a(false);
        if (a == null) {
            return null;
        }
        return (String) a.get(0);
    }

    /* renamed from: c */
    private void m4085c(Context context) {
        String str = f4363aQ;
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        int i = sharedPreferences.getInt(str, 0);
        Editor edit = sharedPreferences.edit();
        int i2 = 1;
        if (i > 0 && i < Integer.MAX_VALUE) {
            i2 = 1 + i;
        }
        edit.putInt(str, i2);
        edit.apply();
    }

    /* renamed from: d */
    private String m4086d() {
        String property = System.getProperty("http.proxyHost");
        if (property != null) {
            String property2 = System.getProperty("http.proxyPort");
            if (property2 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("host=");
                sb.append(property);
                sb.append(",port=");
                sb.append(property2);
                return sb.toString();
            }
        }
        return null;
    }

    /* renamed from: e */
    private String m4087e() {
        try {
            Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (networkInterface.isUp()) {
                    if (networkInterface.getInterfaceAddresses().size() != 0) {
                        String name = networkInterface.getName();
                        if (name.startsWith("ppp") || name.startsWith("tun") || name.startsWith("tap")) {
                            return name;
                        }
                    }
                }
            }
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        return null;
    }

    /* renamed from: f */
    private String m4088f() {
        C5984a aVar = new C5984a();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/Android/data/com.ebay.lid/");
        aVar.mo72533a(sb.toString());
        String str = "fb.bin";
        try {
            if (this.f4380bH || this.f4379bG) {
                return aVar.mo72535b(str);
            }
            return null;
        } catch (FileNotFoundException unused) {
            if (!this.f4380bH) {
                return null;
            }
            String a = C5989c.m4035a(Boolean.TRUE.booleanValue());
            aVar.mo72534a(str, a.getBytes("UTF-8"));
            return a;
        } catch (IOException e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return null;
        }
    }

    /* renamed from: g */
    private String m4089g() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f4377bE ? 1 : 0);
        sb.append(this.f4378bF ? 1 : 0);
        sb.append(this.f4381bI ? 1 : 0);
        sb.append(this.f4382bJ ? 1 : 0);
        sb.append(this.f4379bG ? 1 : 0);
        sb.append(this.f4380bH ? 1 : 0);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72538a() {
        JSONObject jSONObject = new JSONObject();
        try {
            Integer num = null;
            jSONObject.put("base_station_id", this.f4366aT == -1 ? null : Integer.valueOf(this.f4366aT));
            jSONObject.put("bssid", this.f4372aZ);
            jSONObject.put("bssid_array", this.f4419bz == null ? null : new JSONArray(this.f4419bz));
            jSONObject.put("cell_id", this.f4367aU == -1 ? null : Integer.valueOf(this.f4367aU));
            jSONObject.put("conn_type", this.f4401bh);
            jSONObject.put("conf_version", this.f4412bs);
            jSONObject.put("device_id", this.f4402bi);
            jSONObject.put("dc_id", this.f4400bg);
            jSONObject.put("device_uptime", this.f4374bB == -1 ? null : Long.valueOf(this.f4374bB));
            jSONObject.put("ip_addrs", this.f4403bj);
            jSONObject.put("ip_addresses", this.f4417bx == null ? null : new JSONArray(this.f4417bx));
            jSONObject.put("known_apps", this.f4418by == null ? null : new JSONArray(this.f4418by));
            jSONObject.put("locale_country", this.f4405bl);
            jSONObject.put("locale_lang", this.f4406bm);
            jSONObject.put(Param.LOCATION, m4078a(this.f4393bU));
            jSONObject.put("location_area_code", this.f4371aY == -1 ? null : Integer.valueOf(this.f4371aY));
            jSONObject.put("phone_type", this.f4407bn);
            jSONObject.put("risk_comp_session_id", this.f4408bo);
            jSONObject.put("roaming", this.f4375bC);
            jSONObject.put("sim_operator_name", this.f4415bv);
            jSONObject.put("sim_serial_number", this.f4409bp);
            jSONObject.put("ssid", this.f4410bq);
            jSONObject.put("cdma_network_id", this.f4370aX == -1 ? null : Integer.valueOf(this.f4370aX));
            String str = "cdma_system_id";
            if (this.f4369aW != -1) {
                num = Integer.valueOf(this.f4369aW);
            }
            jSONObject.put(str, num);
            jSONObject.put("subscriber_id", this.f4411br);
            jSONObject.put(AppMeasurement.Param.TIMESTAMP, this.f4373bA);
            jSONObject.put("tz_name", this.f4404bk);
            jSONObject.put("ds", this.f4376bD);
            jSONObject.put("tz", this.f4368aV);
            jSONObject.put("network_operator", this.f4394ba);
            jSONObject.put("pairing_id", this.f4395bb);
            jSONObject.put("serial_number", this.f4396bc);
            jSONObject.put("VPN_setting", this.f4398be);
            jSONObject.put("proxy_setting", this.f4397bd);
            jSONObject.put("c", this.f4399bf);
            jSONObject.put("mg_id", this.f4413bt);
            jSONObject.put("linker_id", this.f4414bu);
            jSONObject.put("pl", this.f4416bw);
            m4083b(jSONObject);
            return jSONObject;
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return jSONObject;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72539a(Context context) {
        return mo72545a(context, null, null);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72545a(Context context, String str, HashMap<String, String> hashMap) {
        C5988a.m4031a(getClass(), 0, "collecting RiskBlobDynamicData");
        boolean z = true;
        if (!this.f4383bK) {
            this.f4389bQ = (TelephonyManager) context.getSystemService("phone");
            this.f4390bR = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            this.f4392bT = (LocationManager) context.getSystemService(Param.LOCATION);
            this.f4391bS = (ConnectivityManager) context.getSystemService("connectivity");
            this.f4383bK = true;
        }
        if (!mo72542a(context, "android.permission.ACCESS_COARSE_LOCATION") && !mo72542a(context, "android.permission.ACCESS_FINE_LOCATION")) {
            z = false;
        }
        this.f4377bE = z;
        this.f4379bG = mo72542a(context, "android.permission.READ_EXTERNAL_STORAGE");
        this.f4380bH = mo72542a(context, "android.permission.WRITE_EXTERNAL_STORAGE");
        this.f4378bF = mo72542a(context, "android.permission.READ_PHONE_STATE");
        this.f4382bJ = mo72542a(context, "android.permission.ACCESS_NETWORK_STATE");
        this.f4381bI = mo72542a(context, "android.permission.ACCESS_WIFI_STATE");
        this.f4384bL = hashMap;
        this.f4373bA = System.currentTimeMillis();
        this.f4412bs = MagnesSDK.getInstance().f4210a.mo72562a();
        this.f4395bb = str;
        if (this.f4395bb == null) {
            this.f4395bb = C5989c.m4035a(false);
        }
        m4079a(this.f4389bQ);
        WifiManager wifiManager = this.f4390bR;
        NetworkInfo networkInfo = null;
        if (wifiManager != null) {
            this.f4386bN = this.f4381bI ? wifiManager.getConnectionInfo() : null;
        }
        ConnectivityManager connectivityManager = this.f4391bS;
        if (connectivityManager != null) {
            if (this.f4382bJ) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            this.f4385bM = networkInfo;
        }
        mo72541a(82, context);
        mo72541a(81, context);
        mo72541a(16, context);
        mo72541a(21, context);
        mo72541a(75, context);
        mo72541a(23, context);
        mo72541a(27, context);
        mo72541a(28, context);
        mo72541a(25, context);
        mo72541a(56, context);
        mo72541a(72, context);
        mo72541a(42, context);
        mo72541a(43, context);
        mo72541a(45, context);
        mo72541a(53, context);
        mo72541a(80, context);
        mo72541a(71, context);
        mo72541a(4, context);
        mo72541a(57, context);
        mo72541a(58, context);
        mo72541a(6, context);
        mo72541a(30, context);
        mo72541a(29, context);
        mo72541a(13, context);
        mo72541a(68, context);
        mo72541a(49, context);
        mo72541a(84, context);
        mo72541a(5, context);
        mo72541a(48, context);
        mo72541a(11, context);
        mo72541a(85, context);
        mo72541a(46, context);
        mo72541a(79, context);
        mo72541a(87, context);
        return mo72538a();
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v5, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v8, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v9, types: [java.util.List<java.lang.String>] */
    /* JADX WARNING: type inference failed for: r3v10, types: [java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r3v12, types: [android.location.Location] */
    /* JADX WARNING: type inference failed for: r3v13, types: [android.location.Location] */
    /* JADX WARNING: type inference failed for: r3v14, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v15, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r3v22 */
    /* JADX WARNING: type inference failed for: r3v23 */
    /* JADX WARNING: type inference failed for: r3v24 */
    /* JADX WARNING: type inference failed for: r3v25 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.location.Location, java.lang.String, java.util.ArrayList]
      uses: [java.util.List<java.lang.String>, android.location.Location, java.lang.String]
      mth insns count: 317
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo72541a(int r9, android.content.Context r10) {
        /*
            r8 = this;
            r0 = 4
            r1 = 3
            r2 = -1
            if (r9 == r0) goto L_0x02a7
            r0 = 5
            r3 = 0
            if (r9 == r0) goto L_0x0299
            r0 = 6
            if (r9 == r0) goto L_0x028b
            r0 = 11
            if (r9 == r0) goto L_0x027d
            r0 = 13
            if (r9 == r0) goto L_0x026c
            r0 = 16
            if (r9 == r0) goto L_0x0265
            r0 = 21
            if (r9 == r0) goto L_0x025e
            r0 = 23
            if (r9 == r0) goto L_0x020a
            r0 = 25
            if (r9 == r0) goto L_0x0202
            r0 = 53
            r4 = 1
            if (r9 == r0) goto L_0x01e7
            r0 = 68
            if (r9 == r0) goto L_0x01ce
            r0 = 75
            if (r9 == r0) goto L_0x01c6
            r0 = 87
            if (r9 == r0) goto L_0x01be
            r0 = 42
            if (r9 == r0) goto L_0x01b6
            r0 = 43
            if (r9 == r0) goto L_0x01a9
            r0 = 45
            if (r9 == r0) goto L_0x019a
            r0 = 46
            if (r9 == r0) goto L_0x0188
            r0 = 48
            if (r9 == r0) goto L_0x0179
            r0 = 49
            if (r9 == r0) goto L_0x0167
            r0 = 71
            if (r9 == r0) goto L_0x015f
            r0 = 72
            if (r9 == r0) goto L_0x0157
            r0 = 84
            if (r9 == r0) goto L_0x0149
            r0 = 85
            if (r9 == r0) goto L_0x012c
            switch(r9) {
                case 27: goto L_0x0120;
                case 28: goto L_0x0114;
                case 29: goto L_0x0106;
                case 30: goto L_0x00f7;
                default: goto L_0x0060;
            }
        L_0x0060:
            switch(r9) {
                case 56: goto L_0x00e8;
                case 57: goto L_0x00d9;
                case 58: goto L_0x00ca;
                default: goto L_0x0063;
            }
        L_0x0063:
            switch(r9) {
                case 79: goto L_0x00b9;
                case 80: goto L_0x00a4;
                case 81: goto L_0x0085;
                case 82: goto L_0x0068;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x02bd
        L_0x0068:
            lib.android.paypal.com.magnessdk.MagnesSDK r9 = lib.android.paypal.com.magnessdk.MagnesSDK.getInstance()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.MagnesSettings r9 = r9.f4211b     // Catch:{ Exception -> 0x02b5 }
            int r9 = r9.getMagnesSource()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.MagnesSource r0 = lib.android.paypal.com.magnessdk.MagnesSource.PAYPAL     // Catch:{ Exception -> 0x02b5 }
            int r0 = r0.getVersion()     // Catch:{ Exception -> 0x02b5 }
            if (r9 != r0) goto L_0x02bd
            r8.m4085c(r10)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r8.m4080b(r10)     // Catch:{ Exception -> 0x02b5 }
            r8.f4399bf = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0085:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02b5 }
            r9.<init>()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.e r10 = lib.android.paypal.com.magnessdk.C5991e.m4046b()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r10 = r10.f4332aJ     // Catch:{ Exception -> 0x02b5 }
            r9.append(r10)     // Catch:{ Exception -> 0x02b5 }
            long r2 = r8.f4373bA     // Catch:{ Exception -> 0x02b5 }
            r9.append(r2)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r8.m4074a(r9)     // Catch:{ Exception -> 0x02b5 }
            r8.f4400bg = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00a4:
            java.util.TimeZone r9 = java.util.TimeZone.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.util.Date r10 = new java.util.Date     // Catch:{ Exception -> 0x02b5 }
            r10.<init>()     // Catch:{ Exception -> 0x02b5 }
            long r2 = r10.getTime()     // Catch:{ Exception -> 0x02b5 }
            int r9 = r9.getOffset(r2)     // Catch:{ Exception -> 0x02b5 }
            r8.f4368aV = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00b9:
            java.util.TimeZone r9 = java.util.TimeZone.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.util.Date r10 = new java.util.Date     // Catch:{ Exception -> 0x02b5 }
            r10.<init>()     // Catch:{ Exception -> 0x02b5 }
            boolean r9 = r9.inDaylightTime(r10)     // Catch:{ Exception -> 0x02b5 }
            r8.f4376bD = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00ca:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x00cf
            goto L_0x00d5
        L_0x00cf:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            int r2 = r9.getSystemId()     // Catch:{ Exception -> 0x02b5 }
        L_0x00d5:
            r8.f4369aW = r2     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00d9:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x00de
            goto L_0x00e4
        L_0x00de:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            int r2 = r9.getNetworkId()     // Catch:{ Exception -> 0x02b5 }
        L_0x00e4:
            r8.f4370aX = r2     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00e8:
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x00ed
            goto L_0x00f3
        L_0x00ed:
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r9.getNetworkOperator()     // Catch:{ Exception -> 0x02b5 }
        L_0x00f3:
            r8.f4394ba = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x00f7:
            android.telephony.gsm.GsmCellLocation r9 = r8.f4387bO     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x00fc
            goto L_0x0102
        L_0x00fc:
            android.telephony.gsm.GsmCellLocation r9 = r8.f4387bO     // Catch:{ Exception -> 0x02b5 }
            int r2 = r9.getLac()     // Catch:{ Exception -> 0x02b5 }
        L_0x0102:
            r8.f4371aY = r2     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0106:
            boolean r9 = r8.f4377bE     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x0110
            android.location.LocationManager r9 = r8.f4392bT     // Catch:{ Exception -> 0x02b5 }
            android.location.Location r3 = r8.m4073a(r9)     // Catch:{ Exception -> 0x02b5 }
        L_0x0110:
            r8.f4393bU = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0114:
            java.util.Locale r9 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getLanguage()     // Catch:{ Exception -> 0x02b5 }
            r8.f4406bm = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0120:
            java.util.Locale r9 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getCountry()     // Catch:{ Exception -> 0x02b5 }
            r8.f4405bl = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x012c:
            lib.android.paypal.com.magnessdk.e r9 = lib.android.paypal.com.magnessdk.C5991e.m4046b()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r9.f4332aJ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r4 = r8.f4395bb     // Catch:{ Exception -> 0x02b5 }
            long r5 = r8.f4373bA     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.MagnesSDK r9 = lib.android.paypal.com.magnessdk.MagnesSDK.getInstance()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.network.i r9 = r9.f4210a     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r7 = r9.mo72565c()     // Catch:{ Exception -> 0x02b5 }
            r2 = r8
            java.lang.String r9 = r2.m4075a(r3, r4, r5, r7)     // Catch:{ Exception -> 0x02b5 }
            r8.f4413bt = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0149:
            boolean r9 = r8.f4377bE     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x0153
            android.net.wifi.WifiManager r9 = r8.f4390bR     // Catch:{ Exception -> 0x02b5 }
            java.util.ArrayList r3 = r8.m4076a(r9)     // Catch:{ Exception -> 0x02b5 }
        L_0x0153:
            r8.f4419bz = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0157:
            java.lang.String r9 = r8.m4086d()     // Catch:{ Exception -> 0x02b5 }
            r8.f4397bd = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x015f:
            java.lang.String r9 = r8.m4087e()     // Catch:{ Exception -> 0x02b5 }
            r8.f4398be = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0167:
            boolean r9 = r8.f4378bF     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getSubscriberId()     // Catch:{ Exception -> 0x02b5 }
            r8.f4411br = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0179:
            android.net.wifi.WifiInfo r9 = r8.f4386bN     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x017e
            goto L_0x0184
        L_0x017e:
            android.net.wifi.WifiInfo r9 = r8.f4386bN     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r9.getSSID()     // Catch:{ Exception -> 0x02b5 }
        L_0x0184:
            r8.f4410bq = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0188:
            boolean r9 = r8.f4378bF     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getSimSerialNumber()     // Catch:{ Exception -> 0x02b5 }
            r8.f4409bp = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x019a:
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x019f
            goto L_0x01a5
        L_0x019f:
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r8.m4081b(r9)     // Catch:{ Exception -> 0x02b5 }
        L_0x01a5:
            r8.f4415bv = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01a9:
            android.telephony.ServiceState r9 = new android.telephony.ServiceState     // Catch:{ Exception -> 0x02b5 }
            r9.<init>()     // Catch:{ Exception -> 0x02b5 }
            boolean r9 = r9.getRoaming()     // Catch:{ Exception -> 0x02b5 }
            r8.f4375bC = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01b6:
            java.lang.String r9 = lib.android.paypal.com.magnessdk.C5989c.m4035a(r4)     // Catch:{ Exception -> 0x02b5 }
            r8.f4408bo = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01be:
            java.lang.String r9 = r8.m4089g()     // Catch:{ Exception -> 0x02b5 }
            r8.f4416bw = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01c6:
            java.util.List r9 = r8.m4077a(r4)     // Catch:{ Exception -> 0x02b5 }
            r8.f4417bx = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01ce:
            boolean r9 = r8.f4378bF     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            int r9 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x02b5 }
            r10 = 26
            if (r9 < r10) goto L_0x01e4
            java.lang.String r9 = android.os.Build.getSerial()     // Catch:{ Exception -> 0x02b5 }
        L_0x01e0:
            r8.f4396bc = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x01e4:
            java.lang.String r9 = android.os.Build.SERIAL     // Catch:{ Exception -> 0x02b5 }
            goto L_0x01e0
        L_0x01e7:
            java.util.TimeZone r9 = java.util.TimeZone.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.util.TimeZone r10 = java.util.TimeZone.getDefault()     // Catch:{ Exception -> 0x02b5 }
            java.util.Date r0 = new java.util.Date     // Catch:{ Exception -> 0x02b5 }
            r0.<init>()     // Catch:{ Exception -> 0x02b5 }
            boolean r10 = r10.inDaylightTime(r0)     // Catch:{ Exception -> 0x02b5 }
            java.util.Locale r0 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getDisplayName(r10, r4, r0)     // Catch:{ Exception -> 0x02b5 }
            r8.f4404bk = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0202:
            java.lang.String r9 = r8.m4088f()     // Catch:{ Exception -> 0x02b5 }
            r8.f4414bu = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x020a:
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Exception -> 0x02b5 }
            r9.<init>()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.MagnesSDK r0 = lib.android.paypal.com.magnessdk.MagnesSDK.getInstance()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.network.i r0 = r0.f4210a     // Catch:{ Exception -> 0x02b5 }
            if (r0 == 0) goto L_0x0254
            lib.android.paypal.com.magnessdk.MagnesSDK r0 = lib.android.paypal.com.magnessdk.MagnesSDK.getInstance()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.network.i r0 = r0.f4210a     // Catch:{ Exception -> 0x02b5 }
            java.util.List r0 = r0.mo72564b()     // Catch:{ Exception -> 0x02b5 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x024c }
        L_0x0225:
            boolean r2 = r0.hasNext()     // Catch:{ Exception -> 0x024c }
            if (r2 == 0) goto L_0x0254
            java.lang.Object r2 = r0.next()     // Catch:{ Exception -> 0x024c }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x024c }
            android.content.pm.PackageManager r4 = r10.getPackageManager()     // Catch:{ Exception -> 0x024c }
            android.content.Intent r5 = new android.content.Intent     // Catch:{ Exception -> 0x024c }
            r5.<init>()     // Catch:{ Exception -> 0x024c }
            android.content.ComponentName r6 = android.content.ComponentName.unflattenFromString(r2)     // Catch:{ Exception -> 0x024c }
            android.content.Intent r5 = r5.setComponent(r6)     // Catch:{ Exception -> 0x024c }
            boolean r4 = lib.android.paypal.com.magnessdk.C5989c.m4037a(r4, r5)     // Catch:{ Exception -> 0x024c }
            if (r4 == 0) goto L_0x0225
            r9.add(r2)     // Catch:{ Exception -> 0x024c }
            goto L_0x0225
        L_0x024c:
            r10 = move-exception
            java.lang.Class r0 = r8.getClass()     // Catch:{ Exception -> 0x02b5 }
            lib.android.paypal.com.magnessdk.p046b.C5988a.m4032a(r0, r1, r10)     // Catch:{ Exception -> 0x02b5 }
        L_0x0254:
            int r10 = r9.size()     // Catch:{ Exception -> 0x02b5 }
            if (r10 != 0) goto L_0x025b
            r9 = r3
        L_0x025b:
            r8.f4418by = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x025e:
            java.lang.String r9 = r8.m4084c()     // Catch:{ Exception -> 0x02b5 }
            r8.f4403bj = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0265:
            long r9 = android.os.SystemClock.uptimeMillis()     // Catch:{ Exception -> 0x02b5 }
            r8.f4374bB = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x026c:
            boolean r9 = r8.f4378bF     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            if (r9 == 0) goto L_0x02bd
            android.telephony.TelephonyManager r9 = r8.f4389bQ     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r9 = r9.getDeviceId()     // Catch:{ Exception -> 0x02b5 }
            r8.f4402bi = r9     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x027d:
            android.net.NetworkInfo r9 = r8.f4385bM     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x0282
            goto L_0x0288
        L_0x0282:
            android.net.NetworkInfo r9 = r8.f4385bM     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r9.getTypeName()     // Catch:{ Exception -> 0x02b5 }
        L_0x0288:
            r8.f4401bh = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x028b:
            android.telephony.gsm.GsmCellLocation r9 = r8.f4387bO     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x0290
            goto L_0x0296
        L_0x0290:
            android.telephony.gsm.GsmCellLocation r9 = r8.f4387bO     // Catch:{ Exception -> 0x02b5 }
            int r2 = r9.getCid()     // Catch:{ Exception -> 0x02b5 }
        L_0x0296:
            r8.f4367aU = r2     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x0299:
            android.net.wifi.WifiInfo r9 = r8.f4386bN     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x029e
            goto L_0x02a4
        L_0x029e:
            android.net.wifi.WifiInfo r9 = r8.f4386bN     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r9.getBSSID()     // Catch:{ Exception -> 0x02b5 }
        L_0x02a4:
            r8.f4372aZ = r3     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x02a7:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            if (r9 != 0) goto L_0x02ac
            goto L_0x02b2
        L_0x02ac:
            android.telephony.cdma.CdmaCellLocation r9 = r8.f4388bP     // Catch:{ Exception -> 0x02b5 }
            int r2 = r9.getBaseStationId()     // Catch:{ Exception -> 0x02b5 }
        L_0x02b2:
            r8.f4366aT = r2     // Catch:{ Exception -> 0x02b5 }
            goto L_0x02bd
        L_0x02b5:
            r9 = move-exception
            java.lang.Class r10 = r8.getClass()
            lib.android.paypal.com.magnessdk.p046b.C5988a.m4032a(r10, r1, r9)
        L_0x02bd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: lib.android.paypal.com.magnessdk.C5994f.mo72541a(int, android.content.Context):void");
    }
}
