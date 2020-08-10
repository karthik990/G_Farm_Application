package com.applyze;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.util.Patterns;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lib.android.paypal.com.magnessdk.p100a.C5985b;

class Session implements Serializable {
    private String apiKey;
    private String appKey;
    private String appVersion;
    private String birthyear;
    private String carrier;
    private String city;
    private String connectionType;
    private String country;
    private Map<String, String> customData;
    private Date date;
    private String density;
    private String device;
    private String email;
    private transient Date endDate;
    private String fullName;
    private String gender;
    private boolean isOnGoing = true;
    private double latitude;
    private String locale;
    private double longitude;
    private IpApiModel metaData;
    private String osType;
    private String osVersion;
    private String phone;
    private String resolution;
    private List<ScreenDisplay> screenDisplays;
    boolean status = false;
    private String token;
    private long totalDuration;
    private String udid;
    private String userName;

    Session(Context context) {
        setAppVersion(ServiceUtil.getAppVersion(context));
        setDate(ServiceUtil.getDate());
        setDensity(ServiceUtil.getDensityName(context));
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(" - ");
        sb.append(Build.MODEL);
        setDevice(sb.toString());
        setOsType(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        setLocale(Locale.getDefault().getLanguage());
        setResolution(ServiceUtil.getDeviceResolution(context));
        setUdid(context);
        setConnectionType(context);
        setOsVersion();
        this.customData = new HashMap();
    }

    /* access modifiers changed from: 0000 */
    public Date getDate() {
        return this.date;
    }

    /* access modifiers changed from: 0000 */
    public void setDate(Date date2) {
        this.date = date2;
    }

    /* access modifiers changed from: 0000 */
    public Date getEndDate() {
        return this.endDate;
    }

    /* access modifiers changed from: 0000 */
    public void setEndDate(Date date2) {
        this.endDate = date2;
        setTotalDuration();
    }

    /* access modifiers changed from: 0000 */
    public String getAppKey() {
        return this.appKey;
    }

    /* access modifiers changed from: 0000 */
    public void setAppKey(String str) {
        this.appKey = str;
    }

    /* access modifiers changed from: 0000 */
    public String getApiKey() {
        return this.apiKey;
    }

    /* access modifiers changed from: 0000 */
    public void setApiKey(String str) {
        this.apiKey = str;
    }

    /* access modifiers changed from: 0000 */
    public String getToken() {
        return this.token;
    }

    /* access modifiers changed from: 0000 */
    public void setToken(String str) {
        this.token = str;
    }

    /* access modifiers changed from: 0000 */
    public String getUdid() {
        return this.udid;
    }

    /* access modifiers changed from: 0000 */
    public void setUdid(Context context) {
        if (ServiceUtil.checkRootMethod1() || ServiceUtil.checkRootMethod2() || ServiceUtil.checkRootMethod3()) {
            this.udid = ServiceUtil.generateUdid();
            return;
        }
        String string = Secure.getString(context.getContentResolver(), C5985b.f4233f);
        if (string != null) {
            this.udid = string;
        } else {
            this.udid = ServiceUtil.generateUdid();
        }
    }

    /* access modifiers changed from: 0000 */
    public String getAppVersion() {
        return this.appVersion;
    }

    /* access modifiers changed from: 0000 */
    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    /* access modifiers changed from: 0000 */
    public String getLocale() {
        return this.locale;
    }

    /* access modifiers changed from: 0000 */
    public void setLocale(String str) {
        this.locale = str;
    }

    /* access modifiers changed from: 0000 */
    public double getLatitude() {
        return this.latitude;
    }

    /* access modifiers changed from: 0000 */
    public void setLatitude(double d) {
        this.latitude = d;
    }

    /* access modifiers changed from: 0000 */
    public double getLongitude() {
        return this.longitude;
    }

    /* access modifiers changed from: 0000 */
    public void setLongitude(double d) {
        this.longitude = d;
    }

    /* access modifiers changed from: 0000 */
    public String getDevice() {
        return this.device;
    }

    /* access modifiers changed from: 0000 */
    public void setDevice(String str) {
        this.device = str;
    }

    /* access modifiers changed from: 0000 */
    public String getOsType() {
        return this.osType;
    }

    /* access modifiers changed from: 0000 */
    public void setOsType(String str) {
        this.osType = str;
    }

    /* access modifiers changed from: 0000 */
    public String getDensity() {
        return this.density;
    }

    /* access modifiers changed from: 0000 */
    public void setDensity(String str) {
        this.density = str;
    }

    /* access modifiers changed from: 0000 */
    public String getResolution() {
        return this.resolution;
    }

    /* access modifiers changed from: 0000 */
    public void setResolution(String str) {
        this.resolution = str;
    }

    /* access modifiers changed from: 0000 */
    public List<ScreenDisplay> getScreenDisplays() {
        if (this.screenDisplays == null) {
            this.screenDisplays = new ArrayList();
        }
        return this.screenDisplays;
    }

    /* access modifiers changed from: 0000 */
    public void setScreenDisplays(List<ScreenDisplay> list) {
        this.screenDisplays = list;
    }

    /* access modifiers changed from: 0000 */
    public int getTotalDuration() {
        return (int) this.totalDuration;
    }

    /* access modifiers changed from: 0000 */
    public void setTotalDuration() {
        this.totalDuration = (this.endDate.getTime() - this.date.getTime()) / 1000;
    }

    /* access modifiers changed from: 0000 */
    public void setTotalDuration(int i) {
        this.totalDuration = (long) i;
    }

    /* access modifiers changed from: 0000 */
    public String getEmail() {
        return this.email;
    }

    /* access modifiers changed from: 0000 */
    public void setEmail(String str) {
        if (str != null && !str.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            this.email = str;
        }
    }

    /* access modifiers changed from: 0000 */
    public String getUserName() {
        return this.userName;
    }

    /* access modifiers changed from: 0000 */
    public void setUserName(String str) {
        this.userName = str;
    }

    /* access modifiers changed from: 0000 */
    public String getFullName() {
        return this.fullName;
    }

    /* access modifiers changed from: 0000 */
    public void setFullName(String str) {
        this.fullName = str;
    }

    /* access modifiers changed from: 0000 */
    public String getOsVersion() {
        return this.osVersion;
    }

    /* access modifiers changed from: 0000 */
    public void setOsVersion() {
        this.osVersion = VERSION.RELEASE;
    }

    /* access modifiers changed from: 0000 */
    public String getCity() {
        return this.city;
    }

    /* access modifiers changed from: 0000 */
    public void setCity(String str) {
        this.city = str;
    }

    /* access modifiers changed from: 0000 */
    public String getCountry() {
        return this.country;
    }

    /* access modifiers changed from: 0000 */
    public void setCountry(String str) {
        this.country = str;
    }

    /* access modifiers changed from: 0000 */
    public String getCarrier() {
        return this.carrier;
    }

    /* access modifiers changed from: 0000 */
    public void setCarrier(String str) {
        this.carrier = str;
    }

    /* access modifiers changed from: 0000 */
    public String getGender() {
        return this.gender;
    }

    /* access modifiers changed from: 0000 */
    public void setGender(String str) {
        if (str == null) {
            return;
        }
        if (str.equalsIgnoreCase("m") || str.equalsIgnoreCase("f")) {
            this.gender = str;
        }
    }

    /* access modifiers changed from: 0000 */
    public String getPhone() {
        return this.phone;
    }

    /* access modifiers changed from: 0000 */
    public void setPhone(String str) {
        this.phone = str;
    }

    /* access modifiers changed from: 0000 */
    public String getBirthyear() {
        return this.birthyear;
    }

    /* access modifiers changed from: 0000 */
    public void setBirthyear(String str) {
        this.birthyear = str;
    }

    /* access modifiers changed from: 0000 */
    public IpApiModel getMetaData() {
        return this.metaData;
    }

    /* access modifiers changed from: 0000 */
    public void setMetaData(IpApiModel ipApiModel) {
        this.metaData = ipApiModel;
    }

    /* access modifiers changed from: 0000 */
    public void addCustomField(String str, String str2) {
        this.customData.put(str, str2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Session{date=");
        sb.append(this.date);
        sb.append(", endDate=");
        sb.append(this.endDate);
        sb.append(", appKey='");
        sb.append(this.appKey);
        sb.append('\'');
        sb.append(", apiKey='");
        sb.append(this.apiKey);
        sb.append('\'');
        sb.append(", token='");
        sb.append(this.token);
        sb.append('\'');
        sb.append(", udid='");
        sb.append(this.udid);
        sb.append('\'');
        sb.append(", appVersion='");
        sb.append(this.appVersion);
        sb.append('\'');
        sb.append(", locale='");
        sb.append(this.locale);
        sb.append('\'');
        sb.append(", latitude=");
        sb.append(this.latitude);
        sb.append(", longitude=");
        sb.append(this.longitude);
        sb.append(", device='");
        sb.append(this.device);
        sb.append('\'');
        sb.append(", osType='");
        sb.append(this.osType);
        sb.append('\'');
        sb.append(", density='");
        sb.append(this.density);
        sb.append('\'');
        sb.append(", resolution='");
        sb.append(this.resolution);
        sb.append('\'');
        sb.append(", totalDuration=");
        sb.append(this.totalDuration);
        sb.append(", email='");
        sb.append(this.email);
        sb.append('\'');
        sb.append(", userName='");
        sb.append(this.userName);
        sb.append('\'');
        sb.append(", fullName='");
        sb.append(this.fullName);
        sb.append('\'');
        sb.append(", osVersion='");
        sb.append(this.osVersion);
        sb.append('\'');
        sb.append(", city='");
        sb.append(this.city);
        sb.append('\'');
        sb.append(", country='");
        sb.append(this.country);
        sb.append('\'');
        sb.append(", carrier='");
        sb.append(this.carrier);
        sb.append('\'');
        sb.append(", gender='");
        sb.append(this.gender);
        sb.append('\'');
        sb.append(", phone='");
        sb.append(this.phone);
        sb.append('\'');
        sb.append(", birthyear='");
        sb.append(this.birthyear);
        sb.append('\'');
        sb.append(", connectionType='");
        sb.append(this.connectionType);
        sb.append('\'');
        sb.append(", metaData=");
        sb.append(this.metaData);
        sb.append(", screenDisplays=");
        sb.append(this.screenDisplays);
        sb.append(", customData=");
        sb.append(this.customData);
        sb.append(", isOnGoing=");
        sb.append(this.isOnGoing);
        sb.append(", status=");
        sb.append(this.status);
        sb.append('}');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String getConnectionType() {
        return this.connectionType;
    }

    /* access modifiers changed from: 0000 */
    public void setConnectionType(Context context) {
        this.connectionType = ServiceUtil.getConnectionType(context);
    }

    /* access modifiers changed from: 0000 */
    public boolean isOnGoing() {
        return this.isOnGoing;
    }

    /* access modifiers changed from: 0000 */
    public void setOnGoing(boolean z) {
        this.isOnGoing = z;
    }
}
