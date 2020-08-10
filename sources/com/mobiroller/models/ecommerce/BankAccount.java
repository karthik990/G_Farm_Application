package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class BankAccount implements Serializable {
    public String accountAddress;
    public String accountCode;
    public String accountName;
    public String accountNumber;
    public boolean isSelected;
    public String name;
    public String nameSurname;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        String str = " ";
        sb.append(str);
        sb.append(this.accountName);
        sb.append(str);
        sb.append(this.accountCode);
        sb.append(str);
        sb.append(this.accountNumber);
        return sb.toString();
    }
}
