package com.mobiroller.models.events;

import com.mobiroller.models.user.UserBillingAddressModel;
import java.io.Serializable;

public class NewBillingAddressEvent implements Serializable {
    public UserBillingAddressModel addressModel;

    public NewBillingAddressEvent(UserBillingAddressModel userBillingAddressModel) {
        this.addressModel = userBillingAddressModel;
    }
}
