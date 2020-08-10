package com.applyze;

import com.google.gson.Gson;

class ExtremeApiModel {
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private String lat;
    private String lon;
    private String metaVersion = "v2";

    /* renamed from: org reason: collision with root package name */
    private String f4757org;
    private String query;
    private String region;
    private String status;

    ExtremeApiModel() {
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
    public String getCity() {
        return this.city;
    }

    /* access modifiers changed from: 0000 */
    public void setCity(String str) {
        this.city = str;
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
    public String getIsp() {
        return this.isp;
    }

    /* access modifiers changed from: 0000 */
    public void setIsp(String str) {
        this.isp = str;
    }

    /* access modifiers changed from: 0000 */
    public String getOrg() {
        return this.f4757org;
    }

    /* access modifiers changed from: 0000 */
    public void setOrg(String str) {
        this.f4757org = str;
    }

    /* access modifiers changed from: 0000 */
    public String getQuery() {
        return this.query;
    }

    /* access modifiers changed from: 0000 */
    public void setQuery(String str) {
        this.query = str;
    }

    /* access modifiers changed from: 0000 */
    public IpApiModel getIpApiModel() {
        IpApiModel ipApiModel = new IpApiModel(this.status, this.country, this.countryCode, this.region, this.city, this.lat, this.lon, this.isp, this.f4757org, this.query, this.metaVersion);
        return ipApiModel;
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
