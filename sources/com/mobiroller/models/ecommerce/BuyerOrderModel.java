package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mobiroller.helpers.UserHelper;
import java.io.Serializable;

public class BuyerOrderModel implements Serializable {
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;
    @SerializedName("surname")
    public String surname;

    public BuyerOrderModel() {
        String str = "";
        this.name = str;
        this.surname = str;
    }

    public void BuyerOrderModel() {
        this.email = UserHelper.getUserEmail();
        String str = " ";
        String[] split = UserHelper.getUserName().split(str);
        this.surname = split[split.length - 1];
        for (int i = 0; i < split.length - 1; i++) {
            if (i == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.name);
                sb.append(split[0]);
                this.name = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.name);
                sb2.append(str);
                sb2.append(split[i]);
                this.name = sb2.toString();
            }
        }
    }
}
