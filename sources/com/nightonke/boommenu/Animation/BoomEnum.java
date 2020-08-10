package com.nightonke.boommenu.Animation;

public enum BoomEnum {
    LINE(0),
    PARABOLA_1(1),
    PARABOLA_2(2),
    PARABOLA_3(3),
    PARABOLA_4(4),
    HORIZONTAL_THROW_1(5),
    HORIZONTAL_THROW_2(6),
    RANDOM(7),
    Unknown(-1);
    
    private final int value;

    private BoomEnum(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static BoomEnum getEnum(int i) {
        if (i < 0 || i >= values().length) {
            return Unknown;
        }
        return values()[i];
    }
}
