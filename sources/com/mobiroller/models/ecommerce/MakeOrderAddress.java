package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mobiroller.constants.Constants;
import java.io.Serializable;

public class MakeOrderAddress implements Serializable {
    @SerializedName("city")
    public String city;
    @SerializedName("companyName")
    public String companyName;
    @SerializedName("country")
    public String country;
    @SerializedName("description")
    public String description;
    @SerializedName("id")

    /* renamed from: id */
    public String f2176id;
    @SerializedName("identityNumber")
    public String identityNumber;
    @SerializedName("nameSurname")
    public String nameSurname;
    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("state")
    public String state;
    @SerializedName("taxNumber")
    public String taxNumber;
    @SerializedName("taxOffice")
    public String taxOffice;
    @SerializedName("zipCode")
    public String zipCode;

    public String getTextArea() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.description);
        sb.append(" ");
        String sb2 = sb.toString();
        if (this.companyName != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(this.companyName);
            sb3.append(" - ");
            sb2 = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append(this.city);
        sb4.append("/");
        sb4.append(this.state);
        sb4.append(Constants.NEW_LINE);
        return sb4.toString();
    }

    public String getDescriptionArea() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.description);
        String str = Constants.NEW_LINE;
        sb.append(str);
        sb.append(this.city);
        String str2 = " / ";
        sb.append(str2);
        sb.append(this.state);
        sb.append(str2);
        sb.append(this.country);
        sb.append(str);
        sb.append("%s - ");
        sb.append(this.phoneNumber);
        return sb.toString();
    }

    public String getBillingDescriptionArea() {
        String str = this.taxOffice;
        String str2 = "%s - ";
        String str3 = " / ";
        String str4 = Constants.NEW_LINE;
        if (str == null || str.equalsIgnoreCase("")) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.description);
            sb.append(str4);
            sb.append(this.city);
            sb.append(str3);
            sb.append(this.state);
            sb.append(str3);
            sb.append(this.country);
            sb.append(str4);
            sb.append(str2);
            sb.append(this.phoneNumber);
            sb.append(str4);
            sb.append(this.identityNumber);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.description);
        sb2.append(str4);
        sb2.append(this.city);
        sb2.append(str3);
        sb2.append(this.state);
        sb2.append(str3);
        sb2.append(this.country);
        sb2.append(str4);
        sb2.append(str2);
        sb2.append(this.phoneNumber);
        sb2.append(str4);
        sb2.append(this.taxOffice);
        sb2.append(" - ");
        sb2.append(this.taxNumber);
        return sb2.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MakeOrderAddress{id='");
        sb.append(this.f2176id);
        sb.append('\'');
        sb.append(", city='");
        sb.append(this.city);
        sb.append('\'');
        sb.append(", country='");
        sb.append(this.country);
        sb.append('\'');
        sb.append(", state='");
        sb.append(this.state);
        sb.append('\'');
        sb.append(", description='");
        sb.append(this.description);
        sb.append('\'');
        sb.append(", zipCode='");
        sb.append(this.zipCode);
        sb.append('\'');
        sb.append(", identityNumber='");
        sb.append(this.identityNumber);
        sb.append('\'');
        sb.append(", companyName='");
        sb.append(this.companyName);
        sb.append('\'');
        sb.append(", taxNumber='");
        sb.append(this.taxNumber);
        sb.append('\'');
        sb.append(", phoneNumber='");
        sb.append(this.phoneNumber);
        sb.append('\'');
        sb.append(", taxOffice='");
        sb.append(this.taxOffice);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
