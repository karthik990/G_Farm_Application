package com.google.android.gms.internal.ads;

import androidx.collection.SimpleArrayMap;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
public final class zzacp implements zzacd<zzos> {
    private final boolean zzcbk;

    public zzacp(boolean z) {
        this.zzcbk = z;
    }

    public final /* synthetic */ zzpb zza(zzabv zzabv, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
        SimpleArrayMap simpleArrayMap2 = new SimpleArrayMap();
        zzanz zzg = zzabv.zzg(jSONObject);
        zzanz zzc = zzabv.zzc(jSONObject, "video");
        JSONArray jSONArray = jSONObject.getJSONArray("custom_assets");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            String string = jSONObject2.getString("type");
            boolean equals = "string".equals(string);
            String str = PostalAddressParser.USER_ADDRESS_NAME_KEY;
            if (equals) {
                simpleArrayMap2.put(jSONObject2.getString(str), jSONObject2.getString("string_value"));
            } else if (TtmlNode.TAG_IMAGE.equals(string)) {
                simpleArrayMap.put(jSONObject2.getString(str), zzabv.zza(jSONObject2, "image_value", this.zzcbk));
            } else {
                String str2 = "Unknown custom asset type: ";
                String valueOf = String.valueOf(string);
                zzakb.zzdk(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        }
        zzaqw zzc2 = zzabv.zzc(zzc);
        String string2 = jSONObject.getString("custom_template_id");
        SimpleArrayMap simpleArrayMap3 = new SimpleArrayMap();
        for (int i2 = 0; i2 < simpleArrayMap.size(); i2++) {
            simpleArrayMap3.put(simpleArrayMap.keyAt(i2), ((Future) simpleArrayMap.valueAt(i2)).get());
        }
        zzos zzos = new zzos(string2, simpleArrayMap3, simpleArrayMap2, (zzoj) zzg.get(), zzc2 != null ? zzc2.zztm() : null, zzc2 != null ? zzc2.getView() : null);
        return zzos;
    }
}
