package com.startapp.android.publish.adsCommon.Utils;

import java.util.Set;

/* compiled from: StartAppSDK */
public class NameValueObject {
    private String name;
    private String value;
    private Set<String> valueSet;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public Set<String> getValueSet() {
        return this.valueSet;
    }

    public void setValueSet(Set<String> set) {
        this.valueSet = set;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NameValueObject [name=");
        sb.append(this.name);
        sb.append(", value=");
        sb.append(this.value);
        sb.append(", valueSet=");
        sb.append(this.valueSet);
        sb.append("]");
        return sb.toString();
    }
}
