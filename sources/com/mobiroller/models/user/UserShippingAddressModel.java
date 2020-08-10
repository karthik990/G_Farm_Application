package com.mobiroller.models.user;

import com.mobiroller.models.ecommerce.MakeOrderAddress;

public class UserShippingAddressModel extends BaseAddressModel {
    public MakeOrderAddress getOrderAddress() {
        MakeOrderAddress makeOrderAddress = new MakeOrderAddress();
        makeOrderAddress.city = this.city;
        makeOrderAddress.state = this.state;
        makeOrderAddress.country = this.country;
        makeOrderAddress.description = this.addressLine;
        makeOrderAddress.f2176id = this.f2189id;
        makeOrderAddress.zipCode = this.zipCode;
        makeOrderAddress.phoneNumber = this.contact.phoneNumber;
        makeOrderAddress.nameSurname = this.contact.nameSurname;
        return makeOrderAddress;
    }

    public static UserShippingAddressModel getFakeAddres() {
        UserShippingAddressModel userShippingAddressModel = new UserShippingAddressModel();
        userShippingAddressModel.city = "Antalya";
        String str = "Türkiye";
        userShippingAddressModel.state = str;
        userShippingAddressModel.country = str;
        userShippingAddressModel.description = "Adres";
        userShippingAddressModel.f2189id = TtmlNode.ATTR_ID;
        userShippingAddressModel.zipCode = "07700";
        userShippingAddressModel.contact = AddressContactModel.getFakeContact();
        return userShippingAddressModel;
    }
}
