package com.mobiroller.enums;

import java.util.regex.Pattern;

public enum CardType {
    UNKNOWN,
    VISA("^4[0-9]{12}(?:[0-9]{3}){0,2}$"),
    MASTERCARD("^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$"),
    AMERICAN_EXPRESS("^3[47][0-9]{13}$"),
    DISCOVER("^6(?:011|[45][0-9]{2})[0-9]{12}$"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$"),
    CHINA_UNION_PAY("^62[0-9]{14,17}$");
    
    private Pattern pattern;

    private CardType(String str) {
        this.pattern = Pattern.compile(str);
    }

    public static CardType detect(String str) {
        CardType[] values;
        for (CardType cardType : values()) {
            Pattern pattern2 = cardType.pattern;
            if (pattern2 != null && pattern2.matcher(str).matches()) {
                return cardType;
            }
        }
        return UNKNOWN;
    }
}
