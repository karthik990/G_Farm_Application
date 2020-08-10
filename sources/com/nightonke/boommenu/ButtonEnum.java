package com.nightonke.boommenu;

public enum ButtonEnum {
    SimpleCircle(0),
    TextInsideCircle(1),
    TextOutsideCircle(2),
    Ham(3),
    Unknown(-1);
    
    private final int value;

    private ButtonEnum(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static ButtonEnum getEnum(int i) {
        if (i < 0 || i > values().length) {
            return Unknown;
        }
        return values()[i];
    }
}
