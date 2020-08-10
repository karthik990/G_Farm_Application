package com.paypal.android.sdk.onetouch.core.metadata;

import android.content.Context;
import java.util.HashMap;
import lib.android.paypal.com.magnessdk.MagnesSDK;
import lib.android.paypal.com.magnessdk.MagnesSettings.Builder;
import lib.android.paypal.com.magnessdk.MagnesSource;

public class MetadataIdProviderImpl implements MetadataIdProvider {
    public String getClientMetadataId(Context context, String str, String str2, HashMap<String, String> hashMap) {
        MagnesSDK instance = MagnesSDK.getInstance();
        instance.setUp(new Builder(context).setMagnesSource(MagnesSource.BRAINTREE).setAppGuid(str).build());
        return instance.collectAndSubmit(context, str2, hashMap).getPaypalClientMetaDataId();
    }
}
