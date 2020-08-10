package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderDetailModel extends Order {
    @SerializedName("bankAccount")
    public String bankAccount = "";
    @SerializedName("billingAddress")
    public MakeOrderAddress billingAddress = new MakeOrderAddress();
    @SerializedName("buyer")
    public Buyer buyer = new Buyer();
    @SerializedName("paymentAccount")
    public BankAccount paymentAccount;
    @SerializedName("shippingAddress")
    public MakeOrderAddress shippingAddress = new MakeOrderAddress();

    public class Buyer implements Serializable {
        @SerializedName("name")
        public String name;
        @SerializedName("surname")
        public String surname;

        public Buyer() {
            String str = "";
            this.name = str;
            this.surname = str;
        }

        public String getFullName() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.name);
            sb.append(this.surname);
            return sb.toString();
        }
    }
}
