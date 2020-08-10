package com.truenet.android;

import com.anjlab.android.iab.p020v3.Constants;
import p000a.p001a.p003b.p005b.C0032h;

/* compiled from: StartAppSDK */
public final class DeviceInfo {
    private final String advertisingId;
    private final String cellId;
    private final String deviceManufacturer;
    private final String deviceModel;
    private final String deviceType;
    private final String deviceVersion;
    private final String isp;
    private final String ispName;
    private final String latitude;
    private final String locale;
    private final String locationAreaCode;
    private final String longitude;
    private final String networkOperName;
    private final String networkType;
    private final String osId;
    private final String osVer;
    private final String packageName;
    private String publisherId;
    private final String sdkVersion;
    private final String signalLevel;
    private final String userAgent;

    public static /* synthetic */ DeviceInfo copy$default(DeviceInfo deviceInfo, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, int i, Object obj) {
        DeviceInfo deviceInfo2 = deviceInfo;
        int i2 = i;
        return deviceInfo.copy((i2 & 1) != 0 ? deviceInfo2.latitude : str, (i2 & 2) != 0 ? deviceInfo2.longitude : str2, (i2 & 4) != 0 ? deviceInfo2.userAgent : str3, (i2 & 8) != 0 ? deviceInfo2.locale : str4, (i2 & 16) != 0 ? deviceInfo2.advertisingId : str5, (i2 & 32) != 0 ? deviceInfo2.osId : str6, (i2 & 64) != 0 ? deviceInfo2.osVer : str7, (i2 & 128) != 0 ? deviceInfo2.deviceModel : str8, (i2 & 256) != 0 ? deviceInfo2.deviceManufacturer : str9, (i2 & 512) != 0 ? deviceInfo2.deviceVersion : str10, (i2 & 1024) != 0 ? deviceInfo2.packageName : str11, (i2 & 2048) != 0 ? deviceInfo2.networkOperName : str12, (i2 & 4096) != 0 ? deviceInfo2.isp : str13, (i2 & 8192) != 0 ? deviceInfo2.ispName : str14, (i2 & 16384) != 0 ? deviceInfo2.cellId : str15, (i2 & 32768) != 0 ? deviceInfo2.locationAreaCode : str16, (i2 & 65536) != 0 ? deviceInfo2.networkType : str17, (i2 & 131072) != 0 ? deviceInfo2.signalLevel : str18, (i2 & 262144) != 0 ? deviceInfo2.deviceType : str19, (i2 & 524288) != 0 ? deviceInfo2.sdkVersion : str20, (i2 & 1048576) != 0 ? deviceInfo2.publisherId : str21);
    }

    public final String component1() {
        return this.latitude;
    }

    public final String component10() {
        return this.deviceVersion;
    }

    public final String component11() {
        return this.packageName;
    }

    public final String component12() {
        return this.networkOperName;
    }

    public final String component13() {
        return this.isp;
    }

    public final String component14() {
        return this.ispName;
    }

    public final String component15() {
        return this.cellId;
    }

    public final String component16() {
        return this.locationAreaCode;
    }

    public final String component17() {
        return this.networkType;
    }

    public final String component18() {
        return this.signalLevel;
    }

    public final String component19() {
        return this.deviceType;
    }

    public final String component2() {
        return this.longitude;
    }

    public final String component20() {
        return this.sdkVersion;
    }

    public final String component21() {
        return this.publisherId;
    }

    public final String component3() {
        return this.userAgent;
    }

    public final String component4() {
        return this.locale;
    }

    public final String component5() {
        return this.advertisingId;
    }

    public final String component6() {
        return this.osId;
    }

    public final String component7() {
        return this.osVer;
    }

    public final String component8() {
        return this.deviceModel;
    }

    public final String component9() {
        return this.deviceManufacturer;
    }

    public final DeviceInfo copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21) {
        String str22 = str;
        String str23 = str2;
        String str24 = str3;
        String str25 = str4;
        String str26 = str5;
        String str27 = str6;
        String str28 = str7;
        String str29 = str8;
        String str30 = str9;
        String str31 = str10;
        String str32 = str11;
        String str33 = str12;
        String str34 = str13;
        String str35 = str14;
        String str36 = str15;
        String str37 = str16;
        String str38 = str17;
        String str39 = str18;
        String str40 = str19;
        String str41 = str20;
        String str42 = str21;
        String str43 = str22;
        C0032h.m44b(str22, "latitude");
        C0032h.m44b(str2, "longitude");
        C0032h.m44b(str3, "userAgent");
        C0032h.m44b(str4, "locale");
        C0032h.m44b(str5, "advertisingId");
        C0032h.m44b(str6, "osId");
        C0032h.m44b(str7, "osVer");
        C0032h.m44b(str8, "deviceModel");
        C0032h.m44b(str9, "deviceManufacturer");
        C0032h.m44b(str10, "deviceVersion");
        C0032h.m44b(str11, Constants.RESPONSE_PACKAGE_NAME);
        C0032h.m44b(str12, "networkOperName");
        C0032h.m44b(str13, "isp");
        C0032h.m44b(str14, "ispName");
        C0032h.m44b(str15, "cellId");
        C0032h.m44b(str16, "locationAreaCode");
        C0032h.m44b(str17, "networkType");
        C0032h.m44b(str18, "signalLevel");
        C0032h.m44b(str19, "deviceType");
        C0032h.m44b(str20, "sdkVersion");
        C0032h.m44b(str21, "publisherId");
        DeviceInfo deviceInfo = new DeviceInfo(str43, str23, str24, str25, str26, str27, str28, str29, str30, str31, str32, str33, str34, str35, str36, str37, str38, str39, str40, str41, str42);
        return deviceInfo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d8, code lost:
        if (p000a.p001a.p003b.p005b.C0032h.m43a((java.lang.Object) r2.publisherId, (java.lang.Object) r3.publisherId) != false) goto L_0x00dd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x00dd
            boolean r0 = r3 instanceof com.truenet.android.DeviceInfo
            if (r0 == 0) goto L_0x00db
            com.truenet.android.DeviceInfo r3 = (com.truenet.android.DeviceInfo) r3
            java.lang.String r0 = r2.latitude
            java.lang.String r1 = r3.latitude
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.longitude
            java.lang.String r1 = r3.longitude
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.userAgent
            java.lang.String r1 = r3.userAgent
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.locale
            java.lang.String r1 = r3.locale
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.advertisingId
            java.lang.String r1 = r3.advertisingId
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.osId
            java.lang.String r1 = r3.osId
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.osVer
            java.lang.String r1 = r3.osVer
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.deviceModel
            java.lang.String r1 = r3.deviceModel
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.deviceManufacturer
            java.lang.String r1 = r3.deviceManufacturer
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.deviceVersion
            java.lang.String r1 = r3.deviceVersion
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.packageName
            java.lang.String r1 = r3.packageName
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.networkOperName
            java.lang.String r1 = r3.networkOperName
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.isp
            java.lang.String r1 = r3.isp
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.ispName
            java.lang.String r1 = r3.ispName
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.cellId
            java.lang.String r1 = r3.cellId
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.locationAreaCode
            java.lang.String r1 = r3.locationAreaCode
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.networkType
            java.lang.String r1 = r3.networkType
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.signalLevel
            java.lang.String r1 = r3.signalLevel
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.deviceType
            java.lang.String r1 = r3.deviceType
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.sdkVersion
            java.lang.String r1 = r3.sdkVersion
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = r2.publisherId
            java.lang.String r3 = r3.publisherId
            boolean r3 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r3)
            if (r3 == 0) goto L_0x00db
            goto L_0x00dd
        L_0x00db:
            r3 = 0
            return r3
        L_0x00dd:
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.DeviceInfo.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        String str = this.latitude;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.longitude;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.userAgent;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.locale;
        int hashCode4 = (hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.advertisingId;
        int hashCode5 = (hashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31;
        String str6 = this.osId;
        int hashCode6 = (hashCode5 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.osVer;
        int hashCode7 = (hashCode6 + (str7 != null ? str7.hashCode() : 0)) * 31;
        String str8 = this.deviceModel;
        int hashCode8 = (hashCode7 + (str8 != null ? str8.hashCode() : 0)) * 31;
        String str9 = this.deviceManufacturer;
        int hashCode9 = (hashCode8 + (str9 != null ? str9.hashCode() : 0)) * 31;
        String str10 = this.deviceVersion;
        int hashCode10 = (hashCode9 + (str10 != null ? str10.hashCode() : 0)) * 31;
        String str11 = this.packageName;
        int hashCode11 = (hashCode10 + (str11 != null ? str11.hashCode() : 0)) * 31;
        String str12 = this.networkOperName;
        int hashCode12 = (hashCode11 + (str12 != null ? str12.hashCode() : 0)) * 31;
        String str13 = this.isp;
        int hashCode13 = (hashCode12 + (str13 != null ? str13.hashCode() : 0)) * 31;
        String str14 = this.ispName;
        int hashCode14 = (hashCode13 + (str14 != null ? str14.hashCode() : 0)) * 31;
        String str15 = this.cellId;
        int hashCode15 = (hashCode14 + (str15 != null ? str15.hashCode() : 0)) * 31;
        String str16 = this.locationAreaCode;
        int hashCode16 = (hashCode15 + (str16 != null ? str16.hashCode() : 0)) * 31;
        String str17 = this.networkType;
        int hashCode17 = (hashCode16 + (str17 != null ? str17.hashCode() : 0)) * 31;
        String str18 = this.signalLevel;
        int hashCode18 = (hashCode17 + (str18 != null ? str18.hashCode() : 0)) * 31;
        String str19 = this.deviceType;
        int hashCode19 = (hashCode18 + (str19 != null ? str19.hashCode() : 0)) * 31;
        String str20 = this.sdkVersion;
        int hashCode20 = (hashCode19 + (str20 != null ? str20.hashCode() : 0)) * 31;
        String str21 = this.publisherId;
        if (str21 != null) {
            i = str21.hashCode();
        }
        return hashCode20 + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeviceInfo(latitude=");
        sb.append(this.latitude);
        sb.append(", longitude=");
        sb.append(this.longitude);
        sb.append(", userAgent=");
        sb.append(this.userAgent);
        sb.append(", locale=");
        sb.append(this.locale);
        sb.append(", advertisingId=");
        sb.append(this.advertisingId);
        sb.append(", osId=");
        sb.append(this.osId);
        sb.append(", osVer=");
        sb.append(this.osVer);
        sb.append(", deviceModel=");
        sb.append(this.deviceModel);
        sb.append(", deviceManufacturer=");
        sb.append(this.deviceManufacturer);
        sb.append(", deviceVersion=");
        sb.append(this.deviceVersion);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", networkOperName=");
        sb.append(this.networkOperName);
        sb.append(", isp=");
        sb.append(this.isp);
        sb.append(", ispName=");
        sb.append(this.ispName);
        sb.append(", cellId=");
        sb.append(this.cellId);
        sb.append(", locationAreaCode=");
        sb.append(this.locationAreaCode);
        sb.append(", networkType=");
        sb.append(this.networkType);
        sb.append(", signalLevel=");
        sb.append(this.signalLevel);
        sb.append(", deviceType=");
        sb.append(this.deviceType);
        sb.append(", sdkVersion=");
        sb.append(this.sdkVersion);
        sb.append(", publisherId=");
        sb.append(this.publisherId);
        sb.append(")");
        return sb.toString();
    }

    public DeviceInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21) {
        String str22 = str;
        String str23 = str2;
        String str24 = str3;
        String str25 = str4;
        String str26 = str5;
        String str27 = str6;
        String str28 = str7;
        String str29 = str8;
        String str30 = str9;
        String str31 = str10;
        String str32 = str11;
        String str33 = str12;
        String str34 = str13;
        String str35 = str14;
        String str36 = str15;
        String str37 = str16;
        C0032h.m44b(str22, "latitude");
        C0032h.m44b(str23, "longitude");
        C0032h.m44b(str24, "userAgent");
        C0032h.m44b(str25, "locale");
        C0032h.m44b(str26, "advertisingId");
        C0032h.m44b(str27, "osId");
        C0032h.m44b(str28, "osVer");
        C0032h.m44b(str29, "deviceModel");
        C0032h.m44b(str30, "deviceManufacturer");
        C0032h.m44b(str31, "deviceVersion");
        C0032h.m44b(str32, Constants.RESPONSE_PACKAGE_NAME);
        C0032h.m44b(str33, "networkOperName");
        C0032h.m44b(str34, "isp");
        C0032h.m44b(str35, "ispName");
        C0032h.m44b(str36, "cellId");
        C0032h.m44b(str16, "locationAreaCode");
        C0032h.m44b(str17, "networkType");
        C0032h.m44b(str18, "signalLevel");
        C0032h.m44b(str19, "deviceType");
        C0032h.m44b(str20, "sdkVersion");
        C0032h.m44b(str21, "publisherId");
        String str38 = str16;
        this.latitude = str22;
        this.longitude = str23;
        this.userAgent = str24;
        this.locale = str25;
        this.advertisingId = str26;
        this.osId = str27;
        this.osVer = str28;
        this.deviceModel = str29;
        this.deviceManufacturer = str30;
        this.deviceVersion = str31;
        this.packageName = str32;
        this.networkOperName = str33;
        this.isp = str34;
        this.ispName = str35;
        this.cellId = str15;
        this.locationAreaCode = str38;
        String str39 = str18;
        this.networkType = str17;
        this.signalLevel = str39;
        String str40 = str20;
        this.deviceType = str19;
        this.sdkVersion = str40;
        this.publisherId = str21;
    }

    public final String getLatitude() {
        return this.latitude;
    }

    public final String getLocale() {
        return this.locale;
    }

    public final String getLongitude() {
        return this.longitude;
    }

    public final String getUserAgent() {
        return this.userAgent;
    }

    public final String getAdvertisingId() {
        return this.advertisingId;
    }

    public final String getOsId() {
        return this.osId;
    }

    public final String getOsVer() {
        return this.osVer;
    }

    public final String getDeviceManufacturer() {
        return this.deviceManufacturer;
    }

    public final String getDeviceModel() {
        return this.deviceModel;
    }

    public final String getDeviceVersion() {
        return this.deviceVersion;
    }

    public final String getIsp() {
        return this.isp;
    }

    public final String getNetworkOperName() {
        return this.networkOperName;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getCellId() {
        return this.cellId;
    }

    public final String getIspName() {
        return this.ispName;
    }

    public final String getLocationAreaCode() {
        return this.locationAreaCode;
    }

    public final String getNetworkType() {
        return this.networkType;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final String getPublisherId() {
        return this.publisherId;
    }

    public final String getSdkVersion() {
        return this.sdkVersion;
    }

    public final String getSignalLevel() {
        return this.signalLevel;
    }

    public final void setPublisherId(String str) {
        C0032h.m44b(str, "<set-?>");
        this.publisherId = str;
    }
}
