package com.mobiroller.models.events;

import com.mobiroller.models.user.UserBillingAddressModel;
import java.io.Serializable;

public class EditBillingAddressEvent implements Serializable {
    public UserBillingAddressModel addressModel;

    public EditBillingAddressEvent(UserBillingAddressModel userBillingAddressModel) {
        this.addressModel = userBillingAddressModel;
    }
}
