package com.nightonke.boommenu.Animation;

public enum OrderEnum {
    DEFAULT(0),
    REVERSE(1),
    RANDOM(2),
    Unknown(-1);
    
    private final int value;

    private OrderEnum(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderEnum getEnum(int i) {
        if (i < 0 || i >= values().length) {
            return Unknown;
        }
        return values()[i];
    }
}
