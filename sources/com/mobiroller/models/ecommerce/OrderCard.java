package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderCard implements Serializable {
    @SerializedName("cardHolderName")
    public String cardHolderName;
    @SerializedName("cardNumber")
    public String cardNumber;
    @SerializedName("cvc")
    public String cvc;
    @SerializedName("expireMonth")
    public String expireMonth;
    @SerializedName("expireYear")
    public String expireYear;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderCard{cardHolderName='");
        sb.append(this.cardHolderName);
        sb.append('\'');
        sb.append(", cardNumber='");
        sb.append(this.cardNumber);
        sb.append('\'');
        sb.append(", expireMonth='");
        sb.append(this.expireMonth);
        sb.append('\'');
        sb.append(", expireYear='");
        sb.append(this.expireYear);
        sb.append('\'');
        sb.append(", cvc='");
        sb.append(this.cvc);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
