package com.mobiroller.models.user;

import com.mobiroller.constants.Constants;
import com.mobiroller.util.validation.annotations.LocalizedName;
import com.mobiroller.util.validation.annotations.Required;
import java.io.Serializable;

public class BaseAddressModel implements Serializable {
    @LocalizedName(2131821393)
    @Required
    public String addressLine;
    @LocalizedName(2131821400)
    @Required
    public String city;
    @Required
    public AddressContactModel contact;
    @LocalizedName(2131821398)
    @Required
    public String country;
    public String description;
    @Required

    /* renamed from: id */
    public String f2189id;
    public boolean isDefault;
    public boolean isSelected;
    @LocalizedName(2131821395)
    @Required
    public String state;
    @LocalizedName(2131821394)
    @Required
    public String title;
    @LocalizedName(2131821425)
    @Required
    public String zipCode;

    public String getDescriptionArea() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.city);
        String str = " / ";
        sb.append(str);
        sb.append(this.state);
        sb.append(str);
        sb.append(this.country);
        sb.append(Constants.NEW_LINE);
        sb.append("");
        sb.append(this.contact.nameSurname);
        sb.append(" - ");
        sb.append(this.contact.phoneNumber);
        return sb.toString();
    }

    public String getListDeliveryDescriptionArea() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.contact.nameSurname);
        String str = Constants.NEW_LINE;
        sb.append(str);
        sb.append(this.addressLine);
        sb.append(str);
        sb.append(this.city);
        String str2 = " / ";
        sb.append(str2);
        sb.append(this.state);
        sb.append(str2);
        sb.append(this.country);
        sb.append(str);
        sb.append(this.contact.phoneNumber);
        return sb.toString();
    }

    public String getSummaryDescriptionArea() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.addressLine);
        String str = " ";
        sb.append(str);
        sb.append(this.city);
        sb.append(" / ");
        sb.append(this.state);
        sb.append(str);
        sb.append(this.contact.nameSurname);
        sb.append(" - ");
        sb.append(this.contact.phoneNumber);
        return sb.toString();
    }

    public String getPopupAddressFirstLine() {
        return this.addressLine;
    }

    public String getPopupAddressSecondLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.city);
        String str = " / ";
        sb.append(str);
        sb.append(this.state);
        sb.append(str);
        sb.append(this.country);
        return sb.toString();
    }

    public String getPopupAddressThirdLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.contact.nameSurname);
        sb.append(" - ");
        sb.append(this.contact.phoneNumber);
        return sb.toString();
    }
}
