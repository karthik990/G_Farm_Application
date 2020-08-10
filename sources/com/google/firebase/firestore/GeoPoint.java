package com.google.firebase.firestore;

import com.google.firebase.firestore.util.Util;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class GeoPoint implements Comparable<GeoPoint> {
    private final double latitude;
    private final double longitude;

    public GeoPoint(double d, double d2) {
        if (Double.isNaN(d) || d < -90.0d || d > 90.0d) {
            throw new IllegalArgumentException("Latitude must be in the range of [-90, 90]");
        } else if (Double.isNaN(d2) || d2 < -180.0d || d2 > 180.0d) {
            throw new IllegalArgumentException("Longitude must be in the range of [-180, 180]");
        } else {
            this.latitude = d;
            this.longitude = d2;
        }
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int compareTo(GeoPoint geoPoint) {
        int compareDoubles = Util.compareDoubles(this.latitude, geoPoint.latitude);
        return compareDoubles == 0 ? Util.compareDoubles(this.longitude, geoPoint.longitude) : compareDoubles;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GeoPoint { latitude=");
        sb.append(this.latitude);
        sb.append(", longitude=");
        sb.append(this.longitude);
        sb.append(" }");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof GeoPoint)) {
            return false;
        }
        GeoPoint geoPoint = (GeoPoint) obj;
        if (this.latitude == geoPoint.latitude && this.longitude == geoPoint.longitude) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.latitude);
        int i = (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        long doubleToLongBits2 = Double.doubleToLongBits(this.longitude);
        return (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
    }
}
