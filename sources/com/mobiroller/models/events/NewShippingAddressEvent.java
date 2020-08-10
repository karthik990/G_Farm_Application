package com.mobiroller.models.events;

import com.mobiroller.models.user.UserShippingAddressModel;
import java.io.Serializable;

public class NewShippingAddressEvent implements Serializable {
    public UserShippingAddressModel addressModel;

    public NewShippingAddressEvent(UserShippingAddressModel userShippingAddressModel) {
        this.addressModel = userShippingAddressModel;
    }
}
