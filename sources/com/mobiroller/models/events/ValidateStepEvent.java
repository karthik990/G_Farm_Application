package com.mobiroller.models.events;

import java.io.Serializable;

public class ValidateStepEvent implements Serializable {
    public int currentStep;
    public boolean isValid;

    public ValidateStepEvent(int i, boolean z) {
        this.currentStep = i;
        this.isValid = z;
    }
}
