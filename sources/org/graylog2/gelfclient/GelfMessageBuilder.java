package org.graylog2.gelfclient;

import java.util.HashMap;
import java.util.Map;

public class GelfMessageBuilder {
    private final Map<String, Object> fields;
    private String fullMessage;
    private final String host;
    private GelfMessageLevel level;
    private String message;
    private Double timestamp;
    private final GelfMessageVersion version;

    public GelfMessageBuilder(String str) {
        this(str, "localhost");
    }

    public GelfMessageBuilder(String str, String str2) {
        this(str, str2, GelfMessageVersion.V1_1);
    }

    public GelfMessageBuilder(String str, String str2, GelfMessageVersion gelfMessageVersion) {
        this.level = GelfMessageLevel.ALERT;
        this.fields = new HashMap();
        this.message = str;
        this.host = str2;
        this.version = gelfMessageVersion;
    }

    public GelfMessageBuilder message(String str) {
        this.message = str;
        return this;
    }

    public GelfMessageBuilder fullMessage(String str) {
        this.fullMessage = str;
        return this;
    }

    public GelfMessageBuilder timestamp(double d) {
        this.timestamp = Double.valueOf(d);
        return this;
    }

    public GelfMessageBuilder timestamp(long j) {
        double d = (double) j;
        Double.isNaN(d);
        this.timestamp = Double.valueOf(d / 1000.0d);
        return this;
    }

    public GelfMessageBuilder level(GelfMessageLevel gelfMessageLevel) {
        this.level = gelfMessageLevel;
        return this;
    }

    public GelfMessageBuilder additionalField(String str, Object obj) {
        this.fields.put(str, obj);
        return this;
    }

    public GelfMessageBuilder additionalFields(Map<String, Object> map) {
        this.fields.putAll(map);
        return this;
    }

    public GelfMessage build() {
        if (this.message != null) {
            String str = this.host;
            if (str == null || str.trim().isEmpty()) {
                throw new IllegalArgumentException("host must not be null or empty!");
            }
            GelfMessageVersion gelfMessageVersion = this.version;
            if (gelfMessageVersion != null) {
                GelfMessage gelfMessage = new GelfMessage(this.message, this.host, gelfMessageVersion);
                gelfMessage.setLevel(this.level);
                String str2 = this.fullMessage;
                if (str2 != null) {
                    gelfMessage.setFullMessage(str2);
                }
                Double d = this.timestamp;
                if (d != null) {
                    gelfMessage.setTimestamp(d.doubleValue());
                }
                gelfMessage.addAdditionalFields(this.fields);
                return gelfMessage;
            }
            throw new IllegalArgumentException("version must not be null!");
        }
        throw new IllegalArgumentException("message must not be null!");
    }
}
