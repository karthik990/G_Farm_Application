package lib.android.paypal.com.magnessdk;

import org.json.JSONObject;

public final class MagnesResult {
    private JSONObject deviceInfo;
    private String paypalclientmetadataid;

    public JSONObject getDeviceInfo() {
        return this.deviceInfo;
    }

    public String getPaypalClientMetaDataId() {
        return this.paypalclientmetadataid;
    }

    /* access modifiers changed from: 0000 */
    public MagnesResult setDeviceInfo(JSONObject jSONObject) {
        this.deviceInfo = jSONObject;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public MagnesResult setPaypalClientMetaDataId(String str) {
        this.paypalclientmetadataid = str;
        return this;
    }
}
