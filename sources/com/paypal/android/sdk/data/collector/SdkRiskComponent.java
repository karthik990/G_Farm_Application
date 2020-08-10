package com.paypal.android.sdk.data.collector;

import android.content.Context;

@Deprecated
public final class SdkRiskComponent {
    @Deprecated
    public static String getClientMetadataId(Context context, String str, String str2) {
        PayPalDataCollectorRequest applicationGuid = new PayPalDataCollectorRequest().setApplicationGuid(str);
        if (str2 != null) {
            applicationGuid.setClientMetadataId(str2);
        }
        return PayPalDataCollector.getClientMetadataId(context, applicationGuid);
    }
}
