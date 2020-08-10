package com.twitter.sdk.android.core.services.params;

public class Geocode {
    public final Distance distance;
    public final double latitude;
    public final double longitude;
    public final int radius;

    public enum Distance {
        MILES("mi"),
        KILOMETERS("km");
        
        public final String identifier;

        private Distance(String str) {
            this.identifier = str;
        }
    }

    public Geocode(double d, double d2, int i, Distance distance2) {
        this.latitude = d;
        this.longitude = d2;
        this.radius = i;
        this.distance = distance2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.latitude);
        String str = ",";
        sb.append(str);
        sb.append(this.longitude);
        sb.append(str);
        sb.append(this.radius);
        sb.append(this.distance.identifier);
        return sb.toString();
    }
}
