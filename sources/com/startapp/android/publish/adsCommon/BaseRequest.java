package com.startapp.android.publish.adsCommon;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.startapp.android.publish.adsCommon.Utils.C4939c;
import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.adsCommon.Utils.C4944g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.Utils.NameValueSerializer;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.C5177c;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5139b;
import com.startapp.common.p092a.C5139b.C5143c;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5154f;
import com.startapp.common.p092a.C5156h;
import com.startapp.common.p092a.C5156h.C5158b;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lib.android.paypal.com.magnessdk.p100a.C5985b;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import p043io.netty.util.internal.StringUtil;

/* compiled from: StartAppSDK */
public abstract class BaseRequest implements NameValueSerializer {

    /* renamed from: OS */
    private static final String f3031OS = "android";
    private String androidId;
    private int appCode;
    private Boolean appOnForeground;
    private String appVersion;
    private String blat;
    private String blon;
    private String bssid;
    private String cellSignalLevel;
    private String cellTimingAdv;
    private String cid;
    private String clientSessionId;
    private float density;
    private String deviceIP;
    private String deviceVersion;
    private Map<String, String> frameworksMap = new TreeMap();
    private int height;
    private Set<String> inputLangs;
    private String installerPkg;
    private String isp;
    private String ispName;
    private String lac;
    private String locale;
    private List<Location> locations = null;
    private String manufacturer;
    private String model;
    private String netOper;
    private String networkOperName;
    private String networkType;

    /* renamed from: os */
    private String f3032os = "android";
    private String packageId;
    private Map<String, String> parameters = new HashMap();
    private String personalizedAdsServing;
    private String productId;
    private String publisherId;
    protected Integer retry;
    private Boolean roaming;
    private boolean root;
    private long sdkFlavor = new BigInteger(AdsConstants.f3025i, 2).longValue();
    private int sdkId = 3;
    private String sdkVersion = AdsConstants.f3024h;
    private String signalLevel;
    private boolean simulator;
    private String ssid;
    private String subProductId;
    private String subPublisherId;
    private Boolean unknownSourcesAllowed;
    private boolean usbDebug;
    private C5143c userAdvertisingId;
    private String wfScanRes;
    private int width;
    private String wifiRSSILevel;
    private String wifiSignalLevel;

    /* renamed from: com.startapp.android.publish.adsCommon.BaseRequest$a */
    /* compiled from: StartAppSDK */
    static class C4929a {

        /* renamed from: a */
        private ScanResult f3033a;

        public C4929a(ScanResult scanResult) {
            this.f3033a = scanResult;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            ScanResult scanResult = this.f3033a;
            if (scanResult != null) {
                sb.append(scanResult.SSID);
                sb.append(StringUtil.COMMA);
                sb.append(this.f3033a.BSSID);
                sb.append(StringUtil.COMMA);
                sb.append(WifiManager.calculateSignalLevel(this.f3033a.level, 5));
                sb.append(StringUtil.COMMA);
                sb.append(this.f3033a.level);
                sb.append(StringUtil.COMMA);
                long a = C5146c.m3743a(this.f3033a);
                if (a != 0) {
                    sb.append(a);
                }
                sb.append(StringUtil.COMMA);
                CharSequence b = C5146c.m3764b(this.f3033a);
                if (b != null) {
                    sb.append(b);
                }
            }
            return sb.toString();
        }
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    public String getPublisherId() {
        return this.publisherId;
    }

    public void setPublisherId(String str) {
        this.publisherId = str;
    }

    public long getSdkFlavor() {
        return this.sdkFlavor;
    }

    public void setSdkFlavor(long j) {
        this.sdkFlavor = j;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public void setPackageId(String str) {
        this.packageId = str;
    }

    public String getAndroidId() {
        return this.androidId;
    }

    private void setAndroidId(Context context) {
        String str = "com.google.android.gms";
        if (C5146c.m3761a(context, str, 0)) {
            try {
                if (Integer.parseInt(Integer.toString(context.getPackageManager().getPackageInfo(str, 0).versionCode).substring(0, 1)) >= 4) {
                    this.androidId = Secure.getString(context.getContentResolver(), C5985b.f4233f);
                }
            } catch (NameNotFoundException | Exception unused) {
            }
        }
    }

    public String getInstallerPkg() {
        return this.installerPkg;
    }

    public void setInstallerPkg(String str) {
        this.installerPkg = str;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public String getNetworkType() {
        return this.networkType;
    }

    public String getSignalLevel() {
        return this.signalLevel;
    }

    private void setNetworkType(Context context) {
        this.networkType = C5156h.m3812a(context);
    }

    private void setNetworkLevels(Context context) {
        String str = "e106";
        try {
            C5177c a = C5177c.m3887a();
            if (a != null) {
                this.cellSignalLevel = a.mo62899b();
                C5158b bVar = null;
                if (MetaData.getInstance().isWfScanEnabled()) {
                    bVar = C5156h.m3811a(context, this.networkType);
                }
                if (bVar == null) {
                    this.signalLevel = this.cellSignalLevel;
                } else if (bVar.mo62870a() == null) {
                    this.signalLevel = bVar.mo62874c();
                    this.wifiRSSILevel = bVar.mo62872b();
                    this.wifiSignalLevel = bVar.mo62874c();
                } else {
                    this.signalLevel = bVar.mo62870a();
                    this.wifiRSSILevel = bVar.mo62870a();
                    this.wifiSignalLevel = bVar.mo62870a();
                }
            } else {
                this.signalLevel = str;
                this.cellSignalLevel = str;
                this.wifiSignalLevel = str;
                this.wifiRSSILevel = str;
            }
        } catch (Exception unused) {
        }
    }

    public String getCellSignalLevel() {
        return this.cellSignalLevel;
    }

    public String getWifiSignalLevel() {
        return this.wifiSignalLevel;
    }

    public String getWifiRssiLevel() {
        return this.wifiRSSILevel;
    }

    public C5143c getUserAdvertisingId() {
        return this.userAdvertisingId;
    }

    public void setUserAdvertisingId(C5143c cVar) {
        this.userAdvertisingId = cVar;
    }

    public String getIsp() {
        return this.isp;
    }

    public void setIsp(String str) {
        this.isp = str;
    }

    public String getIspName() {
        return this.ispName;
    }

    public void setIspName(String str) {
        this.ispName = str;
    }

    public String getNetOper() {
        return this.netOper;
    }

    public void setNetOper(String str) {
        this.netOper = str;
    }

    public String getNetworkOperName() {
        return this.networkOperName;
    }

    public void setNetworkOperName(String str) {
        this.networkOperName = str;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String str) {
        this.cid = str;
    }

    public String getLac() {
        return this.lac;
    }

    public void setLac(String str) {
        this.lac = str;
    }

    public String getBlat() {
        return this.blat;
    }

    public void setBlat(String str) {
        this.blat = str;
    }

    public String getBlon() {
        return this.blon;
    }

    public void setBlon(String str) {
        this.blon = str;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getWfScanRes() {
        return this.wfScanRes;
    }

    public void setWfScanRes(String str) {
        this.wfScanRes = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setRetry(int i) {
        this.retry = null;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String str) {
        this.manufacturer = str;
    }

    public String getDeviceVersion() {
        return this.deviceVersion;
    }

    public void setDeviceVersion(String str) {
        this.deviceVersion = str;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public String getSubPublisherId() {
        return this.subPublisherId;
    }

    public void setSubPublisherId(String str) {
        this.subPublisherId = str;
    }

    public String getSubProductId() {
        return this.subProductId;
    }

    public void setSubProductId(String str) {
        this.subProductId = str;
    }

    public String getOs() {
        return this.f3032os;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(String str) {
        this.sdkVersion = str;
    }

    public String getSessionId() {
        String str = this.clientSessionId;
        return str == null ? "" : str;
    }

    public void setSessionId(String str) {
        this.clientSessionId = str;
    }

    public Boolean isRoaming() {
        return this.roaming;
    }

    public void setRoaming(Context context) {
        this.roaming = C5156h.m3818c(context);
    }

    public String getDeviceIP() {
        return this.deviceIP;
    }

    public void setDeviceIP(WifiInfo wifiInfo) {
        this.deviceIP = C5156h.m3815a(wifiInfo);
    }

    public Boolean isUnknownSourcesAllowed() {
        return this.unknownSourcesAllowed;
    }

    public void setUnknownSourcesAllowed(Boolean bool) {
        this.unknownSourcesAllowed = bool;
    }

    public float getDensity() {
        return this.density;
    }

    public void setDensity(float f) {
        this.density = f;
    }

    public Boolean isAppOnForeground() {
        return this.appOnForeground;
    }

    public void setAppOnForeground(Context context) {
        try {
            this.appOnForeground = Boolean.valueOf(C4946i.m2936e(context));
        } catch (Exception unused) {
            this.appOnForeground = null;
        }
    }

    public Set<String> getInputLangs() {
        return this.inputLangs;
    }

    public void setInputLangs(Set<String> set) {
        this.inputLangs = set;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public int getAppCode() {
        return this.appCode;
    }

    public void setAppCode(int i) {
        this.appCode = i;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> list) {
        this.locations = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BaseRequest [parameters=");
        sb.append(this.parameters);
        sb.append("]");
        return sb.toString();
    }

    public void fillApplicationDetails(Context context, AdPreferences adPreferences) {
        fillApplicationDetails(context, adPreferences, true);
    }

    public void fillApplicationDetails(Context context, AdPreferences adPreferences, boolean z) {
        setPublisherId(adPreferences.getPublisherId());
        setProductId(adPreferences.getProductId());
        this.frameworksMap = C5051k.m3341b(context, "sharedPrefsWrappers", this.frameworksMap);
        if (!AdsConstants.f3023g.booleanValue()) {
            setUserAdvertisingId(C5139b.m3719a().mo62840a(context));
            if (this.userAdvertisingId == null) {
                setAndroidId(context);
            }
        }
        setPackageId(context.getPackageName());
        setInstallerPkg(C4946i.m2934d(context));
        setManufacturer(Build.MANUFACTURER);
        setModel(Build.MODEL);
        setDeviceVersion(Integer.toString(VERSION.SDK_INT));
        setLocale(context.getResources().getConfiguration().locale.toString());
        setInputLangs(C5146c.m3766b(context));
        setWidth(context.getResources().getDisplayMetrics().widthPixels);
        setHeight(context.getResources().getDisplayMetrics().heightPixels);
        setDensity(context.getResources().getDisplayMetrics().density);
        setAppOnForeground(context);
        setSessionId(C4944g.m2886d().mo62033a());
        setUnknownSourcesAllowed(Boolean.valueOf(C5146c.m3759a(context)));
        setRoaming(context);
        setNetworkType(context);
        setNetworkLevels(context);
        setAppVersion(C5146c.m3773e(context));
        setAppCode(C5146c.m3772d(context));
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                fillSimDetails(telephonyManager);
                fillNetworkOperatorDetails(context, telephonyManager);
                fillCellLocationDetails(context, telephonyManager);
                setCellTimingAdv(context, telephonyManager);
            }
        } catch (Exception unused) {
        }
        fillWifiDetails(context, z);
        this.usbDebug = C5146c.m3775g(context);
        this.root = C5146c.m3776h(context);
        this.simulator = C5146c.m3777i(context);
        this.personalizedAdsServing = C5051k.m3339a(context, "USER_CONSENT_PERSONALIZED_ADS_SERVING", (String) null);
    }

    public C4941e getNameValueJson() {
        C4939c cVar = new C4939c();
        addParams(cVar);
        return cVar;
    }

    public C4941e getNameValueMap() {
        C4940d dVar = new C4940d();
        addParams(dVar);
        return dVar;
    }

    private void addParams(C4941e eVar) {
        eVar.mo62030a("publisherId", (Object) this.publisherId, false);
        eVar.mo62030a("productId", (Object) this.productId, true);
        eVar.mo62030a("os", (Object) this.f3032os, true);
        eVar.mo62030a("sdkVersion", (Object) this.sdkVersion, false);
        eVar.mo62030a("flavor", (Object) Long.valueOf(this.sdkFlavor), false);
        Map<String, String> map = this.frameworksMap;
        String str = "";
        if (map != null && !map.isEmpty()) {
            String str2 = str;
            for (String str3 : this.frameworksMap.keySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str3);
                sb.append(":");
                sb.append((String) this.frameworksMap.get(str3));
                sb.append(";");
                str2 = sb.toString();
            }
            eVar.mo62026a("frameworksData", (Object) str2.substring(0, str2.length() - 1), false, false);
        }
        eVar.mo62030a("packageId", (Object) this.packageId, false);
        eVar.mo62030a("installerPkg", (Object) this.installerPkg, false);
        C5143c cVar = this.userAdvertisingId;
        if (cVar != null) {
            eVar.mo62030a("userAdvertisingId", (Object) cVar.mo62849a(), false);
            if (this.userAdvertisingId.mo62853b()) {
                eVar.mo62030a("limat", (Object) Boolean.valueOf(this.userAdvertisingId.mo62853b()), false);
            }
            eVar.mo62030a("advertisingIdSource", (Object) this.userAdvertisingId.mo62854c(), false);
        } else {
            String str4 = this.androidId;
            if (str4 != null) {
                eVar.mo62030a("userId", (Object) str4, false);
            }
        }
        eVar.mo62030a("model", (Object) this.model, false);
        eVar.mo62030a("manufacturer", (Object) this.manufacturer, false);
        eVar.mo62030a("deviceVersion", (Object) this.deviceVersion, false);
        eVar.mo62030a("locale", (Object) this.locale, false);
        eVar.mo62031a("inputLangs", this.inputLangs, false);
        eVar.mo62030a("isp", (Object) this.isp, false);
        eVar.mo62030a("ispName", (Object) this.ispName, false);
        eVar.mo62030a("netOper", (Object) getNetOper(), false);
        eVar.mo62030a("networkOperName", (Object) getNetworkOperName(), false);
        eVar.mo62030a("cid", (Object) getCid(), false);
        eVar.mo62030a("lac", (Object) getLac(), false);
        eVar.mo62030a("blat", (Object) getBlat(), false);
        eVar.mo62030a("blon", (Object) getBlon(), false);
        eVar.mo62030a("ssid", (Object) getSsid(), false);
        eVar.mo62030a("bssid", (Object) getBssid(), false);
        eVar.mo62030a("wfScanRes", (Object) getWfScanRes(), false);
        eVar.mo62030a("subPublisherId", (Object) this.subPublisherId, false);
        eVar.mo62030a("subProductId", (Object) this.subProductId, false);
        eVar.mo62030a("retryCount", (Object) this.retry, false);
        eVar.mo62030a("roaming", (Object) isRoaming(), false);
        eVar.mo62030a("deviceIP", (Object) getDeviceIP(), false);
        eVar.mo62030a("grid", (Object) getNetworkType(), false);
        eVar.mo62030a("silev", (Object) getSignalLevel(), false);
        eVar.mo62030a("cellSignalLevel", (Object) getCellSignalLevel(), false);
        if (getWifiSignalLevel() != null) {
            eVar.mo62030a("wifiSignalLevel", (Object) getWifiSignalLevel(), false);
        }
        if (getWifiRssiLevel() != null) {
            eVar.mo62030a("wifiRssiLevel", (Object) getWifiRssiLevel(), false);
        }
        if (getCellTimingAdv() != null) {
            eVar.mo62030a("cellTimingAdv", (Object) getCellTimingAdv(), false);
        }
        eVar.mo62030a("outsource", (Object) isUnknownSourcesAllowed(), false);
        eVar.mo62030a(SettingsJsonConstants.ICON_WIDTH_KEY, (Object) String.valueOf(this.width), false);
        eVar.mo62030a(SettingsJsonConstants.ICON_HEIGHT_KEY, (Object) String.valueOf(this.height), false);
        eVar.mo62030a("density", (Object) String.valueOf(this.density), false);
        eVar.mo62030a("fgApp", (Object) isAppOnForeground(), false);
        eVar.mo62030a("sdkId", (Object) String.valueOf(this.sdkId), true);
        eVar.mo62030a("clientSessionId", (Object) this.clientSessionId, false);
        eVar.mo62030a("appVersion", (Object) this.appVersion, false);
        eVar.mo62030a("appCode", (Object) Integer.valueOf(this.appCode), false);
        eVar.mo62030a("timeSinceBoot", (Object) Long.valueOf(getTimeSinceBoot()), false);
        if (getLocations() != null && getLocations().size() > 0) {
            String a = C5154f.m3802a(getLocations());
            if (a != null && !a.equals(str)) {
                eVar.mo62030a("locations", (Object) C5138a.m3717c(a), false);
            }
        }
        eVar.mo62030a("udbg", (Object) Boolean.valueOf(this.usbDebug), false);
        eVar.mo62030a("root", (Object) Boolean.valueOf(this.root), false);
        eVar.mo62030a("smltr", (Object) Boolean.valueOf(this.simulator), false);
        eVar.mo62030a("pas", (Object) this.personalizedAdsServing, false);
    }

    public String getRequestString() {
        return getNameValueMap().toString();
    }

    private void fillSimDetails(TelephonyManager telephonyManager) {
        if (telephonyManager.getSimState() == 5) {
            setIsp(telephonyManager.getSimOperator());
            setIspName(telephonyManager.getSimOperatorName());
        }
    }

    private void fillNetworkOperatorDetails(Context context, TelephonyManager telephonyManager) {
        int phoneType = telephonyManager.getPhoneType();
        if (phoneType != 0 && phoneType != 2) {
            String networkOperator = telephonyManager.getNetworkOperator();
            if (networkOperator != null) {
                setNetOper(C5138a.m3717c(networkOperator));
            }
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            if (networkOperatorName != null) {
                setNetworkOperName(C5138a.m3717c(networkOperatorName));
            }
        }
    }

    private void fillCellLocationDetails(Context context, TelephonyManager telephonyManager) {
        if (C5146c.m3760a(context, "android.permission.ACCESS_FINE_LOCATION") || C5146c.m3760a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            CellLocation cellLocation = telephonyManager.getCellLocation();
            if (cellLocation == null) {
                return;
            }
            if (cellLocation instanceof GsmCellLocation) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                setCid(C5138a.m3717c(String.valueOf(gsmCellLocation.getCid())));
                setLac(C5138a.m3717c(String.valueOf(gsmCellLocation.getLac())));
            } else if (cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                setBlat(C5138a.m3717c(String.valueOf(cdmaCellLocation.getBaseStationLatitude())));
                setBlon(C5138a.m3717c(String.valueOf(cdmaCellLocation.getBaseStationLongitude())));
            }
        }
    }

    private String getCellTimingAdv() {
        return this.cellTimingAdv;
    }

    private void setCellTimingAdv(Context context, TelephonyManager telephonyManager) {
        this.cellTimingAdv = C5146c.m3765b(context, telephonyManager);
    }

    private static long getTimeSinceBoot() {
        return SystemClock.elapsedRealtime();
    }

    public void fillLocationDetails(AdPreferences adPreferences, Context context) {
        boolean z;
        this.locations = new ArrayList();
        if (adPreferences == null || adPreferences.getLatitude() == null || adPreferences.getLongitude() == null) {
            z = false;
        } else {
            Location location = new Location("loc");
            location.setLongitude(adPreferences.getLongitude().doubleValue());
            location.setLongitude(adPreferences.getLongitude().doubleValue());
            location.setProvider(MetaData.DEFAULT_LOCATION_SOURCE);
            this.locations.add(location);
            z = true;
        }
        C5059m.m3378a().mo62401h(context);
        List a = C5154f.m3803a(context, MetaData.getInstance().getLocationConfig().isFiEnabled(), MetaData.getInstance().getLocationConfig().isCoEnabled());
        if (a != null && a.size() > 0) {
            this.locations.addAll(a);
            z = true;
        }
        setUsingLocation(context, z);
    }

    public static void setUsingLocation(Context context, boolean z) {
        C5051k.m3342b(context, "shared_prefs_using_location", Boolean.valueOf(z));
    }

    private void fillWifiDetails(Context context, boolean z) {
        try {
            if (MetaData.getInstance().isWfScanEnabled()) {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (wifiManager != null && C5146c.m3760a(context, "android.permission.ACCESS_WIFI_STATE")) {
                    if (getNetworkType().equals("WIFI")) {
                        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        if (connectionInfo != null) {
                            setDeviceIP(connectionInfo);
                            String ssid2 = connectionInfo.getSSID();
                            String bssid2 = connectionInfo.getBSSID();
                            if (ssid2 != null) {
                                setSsid(C5138a.m3717c(ssid2));
                            }
                            if (bssid2 != null) {
                                setBssid(C5138a.m3717c(bssid2));
                            }
                        }
                    }
                    if (z) {
                        List a = C5146c.m3746a(context, wifiManager);
                        if (a != null && !a.equals(Collections.EMPTY_LIST)) {
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < Math.min(5, a.size()); i++) {
                                arrayList.add(new C4929a((ScanResult) a.get(i)));
                            }
                            setWfScanRes(C5138a.m3717c(TextUtils.join(";", arrayList)));
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
