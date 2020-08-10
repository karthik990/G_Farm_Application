package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class GeoPoint extends GenericJson {
    @Key
    private Double altitude;
    @Key
    private Double latitude;
    @Key
    private Double longitude;

    public Double getAltitude() {
        return this.altitude;
    }

    public GeoPoint setAltitude(Double d) {
        this.altitude = d;
        return this;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public GeoPoint setLatitude(Double d) {
        this.latitude = d;
        return this;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public GeoPoint setLongitude(Double d) {
        this.longitude = d;
        return this;
    }

    public GeoPoint set(String str, Object obj) {
        return (GeoPoint) super.set(str, obj);
    }

    public GeoPoint clone() {
        return (GeoPoint) super.clone();
    }
}
