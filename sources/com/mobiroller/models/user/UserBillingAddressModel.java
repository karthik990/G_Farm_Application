package com.mobiroller.models.user;

import com.mobiroller.constants.Constants;
import com.mobiroller.models.ecommerce.MakeOrderAddress;
import com.mobiroller.util.validation.annotations.Required;

public class UserBillingAddressModel extends BaseAddressModel {
    public String companyName;
    public String identityNumber;
    public String taxNumber;
    public String taxOffice;
    @Required
    public String type;

    public MakeOrderAddress getOrderAddress() {
        MakeOrderAddress makeOrderAddress = new MakeOrderAddress();
        makeOrderAddress.city = this.city;
        makeOrderAddress.companyName = this.companyName;
        makeOrderAddress.state = this.state;
        makeOrderAddress.country = this.country;
        makeOrderAddress.description = this.addressLine;
        makeOrderAddress.f2176id = this.f2189id;
        makeOrderAddress.identityNumber = this.identityNumber;
        makeOrderAddress.taxOffice = this.taxOffice;
        makeOrderAddress.taxNumber = this.taxNumber;
        makeOrderAddress.zipCode = this.zipCode;
        makeOrderAddress.phoneNumber = this.contact.phoneNumber;
        makeOrderAddress.nameSurname = this.contact.nameSurname;
        return makeOrderAddress;
    }

    public static UserBillingAddressModel getFakeAddres() {
        UserBillingAddressModel userBillingAddressModel = new UserBillingAddressModel();
        userBillingAddressModel.city = "Antalya";
        userBillingAddressModel.companyName = "Mobiroller";
        String str = "Türkiye";
        userBillingAddressModel.state = str;
        userBillingAddressModel.country = str;
        userBillingAddressModel.description = "Adres";
        userBillingAddressModel.f2189id = TtmlNode.ATTR_ID;
        userBillingAddressModel.identityNumber = "1234567890";
        userBillingAddressModel.taxNumber = "12341234";
        userBillingAddressModel.zipCode = "07700";
        userBillingAddressModel.contact = AddressContactModel.getFakeContact();
        return userBillingAddressModel;
    }

    public String getListBillingDescriptionArea() {
        boolean equalsIgnoreCase = this.type.equalsIgnoreCase("Corporate");
        String str = " / ";
        String str2 = Constants.NEW_LINE;
        if (equalsIgnoreCase) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.contact.nameSurname);
            sb.append(str2);
            sb.append(this.addressLine);
            sb.append(str2);
            sb.append(this.city);
            sb.append(str);
            sb.append(this.state);
            sb.append(str);
            sb.append(this.country);
            sb.append(str2);
            sb.append(this.contact.phoneNumber);
            sb.append(str2);
            sb.append(this.taxOffice);
            sb.append(" - ");
            sb.append(this.taxNumber);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.contact.nameSurname);
        sb2.append(str2);
        sb2.append(this.addressLine);
        sb2.append(str2);
        sb2.append(this.city);
        sb2.append(str);
        sb2.append(this.state);
        sb2.append(str);
        sb2.append(this.country);
        sb2.append(str2);
        sb2.append(this.contact.phoneNumber);
        sb2.append(str2);
        sb2.append(this.identityNumber);
        return sb2.toString();
    }
}
