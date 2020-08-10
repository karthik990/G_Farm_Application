package com.rengwuxian.materialedittext.validation;

import java.util.regex.Pattern;

public class RegexpValidator extends METValidator {
    private Pattern pattern;

    public RegexpValidator(String str, String str2) {
        super(str);
        this.pattern = Pattern.compile(str2);
    }

    public boolean isValid(CharSequence charSequence, boolean z) {
        return this.pattern.matcher(charSequence).matches();
    }
}
