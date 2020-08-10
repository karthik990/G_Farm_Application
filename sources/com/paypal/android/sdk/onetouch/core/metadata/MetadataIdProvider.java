package com.paypal.android.sdk.onetouch.core.metadata;

import android.content.Context;
import java.util.HashMap;

public interface MetadataIdProvider {
    String getClientMetadataId(Context context, String str, String str2, HashMap<String, String> hashMap);
}
