package com.nightonke.boommenu.BoomButtons;

public enum ButtonPlaceAlignmentEnum {
    Center(0),
    Top(1),
    Bottom(2),
    Left(3),
    Right(4),
    TL(5),
    TR(6),
    BL(7),
    BR(8),
    Unknown(-1);
    
    private final int value;

    private ButtonPlaceAlignmentEnum(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static ButtonPlaceAlignmentEnum getEnum(int i) {
        if (i < 0 || i >= values().length) {
            return Unknown;
        }
        return values()[i];
    }
}
