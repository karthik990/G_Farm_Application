package com.mobiroller.models.user;

import com.mobiroller.util.validation.annotations.LocalizedName;
import com.mobiroller.util.validation.annotations.Required;
import java.io.Serializable;

public class DefaultAddressList implements Serializable {
    @LocalizedName(2131821433)
    @Required
    public UserBillingAddressModel billingAddress;
    @LocalizedName(2131821435)
    @Required
    public UserShippingAddressModel shippingAddress;
}
