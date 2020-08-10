package p043io.fabric.sdk.android.services.settings;

import org.json.JSONException;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.common.CurrentTimeProvider;

/* renamed from: io.fabric.sdk.android.services.settings.SettingsJsonTransform */
public interface SettingsJsonTransform {
    SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject) throws JSONException;

    JSONObject toJson(SettingsData settingsData) throws JSONException;
}
