package com.mobiroller.models.events;

import com.mobiroller.models.user.UserShippingAddressModel;
import java.io.Serializable;

public class EditShippingAddressEvent implements Serializable {
    public UserShippingAddressModel addressModel;

    public EditShippingAddressEvent(UserShippingAddressModel userShippingAddressModel) {
        this.addressModel = userShippingAddressModel;
    }
}
