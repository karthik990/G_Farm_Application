package org.graylog2.gelfclient;

public enum GelfMessageVersion {
    V1_1("1.1");
    
    private final String versionString;

    private GelfMessageVersion(String str) {
        this.versionString = str;
    }

    public String toString() {
        return this.versionString;
    }
}
