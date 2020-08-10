package com.mobiroller.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InAppPurchaseModel implements Serializable {
    @SerializedName("alk")
    public String androidLicenseKey;
    @SerializedName("ia")
    public boolean isActive;
    @SerializedName("p")
    public List<InAppPurchaseProduct> products = new ArrayList();
    @SerializedName("updateDate")
    public String updateDate;
}
