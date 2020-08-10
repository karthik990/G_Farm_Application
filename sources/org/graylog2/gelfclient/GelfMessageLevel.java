package org.graylog2.gelfclient;

public enum GelfMessageLevel {
    EMERGENCY(0),
    ALERT(1),
    CRITICAL(2),
    ERROR(3),
    WARNING(4),
    NOTICE(5),
    INFO(6),
    DEBUG(7);
    
    private final int numericLevel;

    private GelfMessageLevel(int i) {
        this.numericLevel = i;
    }

    public int getNumericLevel() {
        return this.numericLevel;
    }

    public static GelfMessageLevel fromNumericLevel(int i) {
        GelfMessageLevel[] values;
        for (GelfMessageLevel gelfMessageLevel : values()) {
            if (i == gelfMessageLevel.getNumericLevel()) {
                return gelfMessageLevel;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown GELF message level: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name());
        sb.append("(");
        sb.append(this.numericLevel);
        sb.append(")");
        return sb.toString();
    }
}
