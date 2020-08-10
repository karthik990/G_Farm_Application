package com.mobiroller.models.user;

import com.mobiroller.util.validation.annotations.LocalizedName;
import com.mobiroller.util.validation.annotations.Required;
import java.io.Serializable;

public class AddressContactModel implements Serializable {
    public String email;
    @LocalizedName(2131821423)
    @Required
    public String nameSurname;
    @LocalizedName(2131821424)
    @Required
    public String phoneNumber;

    public static AddressContactModel getFakeContact() {
        AddressContactModel addressContactModel = new AddressContactModel();
        addressContactModel.email = "test@test.com";
        addressContactModel.phoneNumber = "05392476787";
        addressContactModel.nameSurname = "John Doe";
        return addressContactModel;
    }
}
