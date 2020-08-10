package com.startapp.android.publish.adsCommon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: StartAppSDK */
public abstract class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1;
    private String errorMessage = null;
    protected Map<String, String> parameters = new HashMap();
    private boolean validResponse = true;

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public boolean isValidResponse() {
        return this.validResponse;
    }

    public void setValidResponse(boolean z) {
        this.validResponse = z;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BaseResponse [parameters=");
        sb.append(this.parameters);
        sb.append(", validResponse=");
        sb.append(this.validResponse);
        sb.append(", errorMessage=");
        sb.append(this.errorMessage);
        sb.append("]");
        return sb.toString();
    }
}
