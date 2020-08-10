package com.mobiroller.models.events;

import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import java.io.Serializable;

public class OrderAddressesEvent implements Serializable {
    public UserBillingAddressModel userBillingAddressModel;
    public UserShippingAddressModel userShippingAddressModel;

    public OrderAddressesEvent(UserShippingAddressModel userShippingAddressModel2, UserBillingAddressModel userBillingAddressModel2) {
        this.userBillingAddressModel = userBillingAddressModel2;
        this.userShippingAddressModel = userShippingAddressModel2;
    }
}
