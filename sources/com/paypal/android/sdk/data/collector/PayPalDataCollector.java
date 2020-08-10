package com.paypal.android.sdk.data.collector;

import android.content.Context;
import lib.android.paypal.com.magnessdk.Environment;
import lib.android.paypal.com.magnessdk.MagnesSDK;
import lib.android.paypal.com.magnessdk.MagnesSettings.Builder;
import lib.android.paypal.com.magnessdk.MagnesSource;

public class PayPalDataCollector {
    public static String getClientMetadataId(Context context) {
        return getClientMetadataId(context, new PayPalDataCollectorRequest().setApplicationGuid(InstallationIdentifier.getInstallationGUID(context)));
    }

    public static String getClientMetadataId(Context context, String str) {
        return getClientMetadataId(context, new PayPalDataCollectorRequest().setApplicationGuid(InstallationIdentifier.getInstallationGUID(context)).setClientMetadataId(str));
    }

    public static String getClientMetadataId(Context context, PayPalDataCollectorRequest payPalDataCollectorRequest) {
        if (context == null) {
            return "";
        }
        MagnesSDK instance = MagnesSDK.getInstance();
        instance.setUp(new Builder(context).setMagnesSource(MagnesSource.BRAINTREE).disableBeacon(payPalDataCollectorRequest.isDisableBeacon()).setMagnesEnvironment(Environment.LIVE).setAppGuid(payPalDataCollectorRequest.getApplicationGuid()).build());
        return instance.collectAndSubmit(context, payPalDataCollectorRequest.getClientMetadataId(), payPalDataCollectorRequest.getAdditionalData()).getPaypalClientMetaDataId();
    }
}
