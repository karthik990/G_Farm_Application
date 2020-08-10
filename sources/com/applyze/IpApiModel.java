package com.applyze;

import com.google.gson.Gson;

class IpApiModel {

    /* renamed from: as */
    private String f84as;
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private String lat;
    private String lon;
    private String metaVersion = "v1";

    /* renamed from: org reason: collision with root package name */
    private String f4758org;
    private String query;
    private String region;
    private String regionName;
    private String status;
    private String timezone;
    private String zip;

    IpApiModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.status = str;
        this.country = str2;
        this.countryCode = str3;
        this.region = str4;
        this.city = str5;
        this.lat = str6;
        this.lon = str7;
        this.isp = str8;
        this.f4758org = str9;
        this.query = str10;
        this.metaVersion = str11;
    }

    /* access modifiers changed from: 0000 */
    public String getStatus() {
        return this.status;
    }

    /* access modifiers changed from: 0000 */
    public void setStatus(String str) {
        this.status = str;
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
    public String getCountryCode() {
        return this.countryCode;
    }

    /* access modifiers changed from: 0000 */
    public void setCountryCode(String str) {
        this.countryCode = str;
    }

    /* access modifiers changed from: 0000 */
    public String getRegion() {
        return this.region;
    }

    /* access modifiers changed from: 0000 */
    public void setRegion(String str) {
        this.region = str;
    }

    /* access modifiers changed from: 0000 */
    public String getRegionName() {
        return this.regionName;
    }

    /* access modifiers changed from: 0000 */
    public void setRegionName(String str) {
        this.regionName = str;
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
    public String getZip() {
        return this.zip;
    }

    /* access modifiers changed from: 0000 */
    public void setZip(String str) {
        this.zip = str;
    }

    /* access modifiers changed from: 0000 */
    public String getLat() {
        return this.lat;
    }

    /* access modifiers changed from: 0000 */
    public void setLat(String str) {
        this.lat = str;
    }

    /* access modifiers changed from: 0000 */
    public String getLon() {
        return this.lon;
    }

    /* access modifiers changed from: 0000 */
    public void setLon(String str) {
        this.lon = str;
    }

    /* access modifiers changed from: 0000 */
    public String getTimezone() {
        return this.timezone;
    }

    /* access modifiers changed from: 0000 */
    public void setTimezone(String str) {
        this.timezone = str;
    }

    /* access modifiers changed from: 0000 */
    public String getIsp() {
        return this.isp;
    }

    /* access modifiers changed from: 0000 */
    public void setIsp(String str) {
        this.isp = str;
    }

    /* access modifiers changed from: 0000 */
    public String getOrg() {
        return this.f4758org;
    }

    /* access modifiers changed from: 0000 */
    public void setOrg(String str) {
        this.f4758org = str;
    }

    /* access modifiers changed from: 0000 */
    public String getAs() {
        return this.f84as;
    }

    /* access modifiers changed from: 0000 */
    public void setAs(String str) {
        this.f84as = str;
    }

    /* access modifiers changed from: 0000 */
    public String getQuery() {
        return this.query;
    }

    /* access modifiers changed from: 0000 */
    public void setQuery(String str) {
        this.query = str;
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
