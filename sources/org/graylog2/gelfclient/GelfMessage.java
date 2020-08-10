package org.graylog2.gelfclient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GelfMessage {
    private final Map<String, Object> additionalFields;
    private String fullMessage;
    private final String host;
    private GelfMessageLevel level;
    private final String message;
    private double timestamp;
    private final GelfMessageVersion version;

    public GelfMessage(String str) {
        this(str, "localhost");
    }

    public GelfMessage(String str, String str2) {
        this(str, str2, GelfMessageVersion.V1_1);
    }

    public GelfMessage(String str, String str2, GelfMessageVersion gelfMessageVersion) {
        double currentTimeMillis = (double) System.currentTimeMillis();
        Double.isNaN(currentTimeMillis);
        this.timestamp = currentTimeMillis / 1000.0d;
        this.level = GelfMessageLevel.ALERT;
        this.additionalFields = new HashMap();
        this.message = str;
        this.host = str2;
        this.version = gelfMessageVersion;
    }

    public GelfMessageVersion getVersion() {
        return this.version;
    }

    public String getHost() {
        return this.host;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFullMessage() {
        return this.fullMessage;
    }

    public void setFullMessage(String str) {
        this.fullMessage = str;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(double d) {
        this.timestamp = d;
    }

    public GelfMessageLevel getLevel() {
        return this.level;
    }

    public void setLevel(GelfMessageLevel gelfMessageLevel) {
        this.level = gelfMessageLevel;
    }

    public Map<String, Object> getAdditionalFields() {
        return this.additionalFields;
    }

    public void addAdditionalField(String str, Object obj) {
        if (str != null) {
            this.additionalFields.put(str, obj);
        }
    }

    public void addAdditionalFields(Map<String, Object> map) {
        this.additionalFields.putAll(map);
    }

    public String toString() {
        return String.format("GelfMessage{version=\"%s\" timestamp=\"%.3f\" short_message=\"%s\", level=\"%s\"}", new Object[]{this.version, Double.valueOf(this.timestamp), this.message, this.level});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GelfMessage gelfMessage = (GelfMessage) obj;
        if (this.version != gelfMessage.version || !this.message.equals(gelfMessage.message) || !this.host.equals(gelfMessage.host) || this.level != gelfMessage.level || Double.compare(gelfMessage.timestamp, this.timestamp) != 0) {
            return false;
        }
        String str = this.fullMessage;
        String str2 = gelfMessage.fullMessage;
        return str == null ? str2 == null : str.equals(str2);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.version, this.host, this.message, this.fullMessage, this.level, Double.valueOf(this.timestamp)});
    }
}
