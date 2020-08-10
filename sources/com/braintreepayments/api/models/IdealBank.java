package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class IdealBank implements Parcelable {
    private static final String ASSETS_URL_PATH = "web/static/images/ideal_issuer-logo_";
    private static final String COUNTRY_CODE_KEY = "country_code";
    public static final Creator<IdealBank> CREATOR = new Creator<IdealBank>() {
        public IdealBank createFromParcel(Parcel parcel) {
            return new IdealBank(parcel);
        }

        public IdealBank[] newArray(int i) {
            return new IdealBank[i];
        }
    };
    private static final String DATA_KEY = "data";
    private static final String ID_KEY = "id";
    private static final String IMAGE_FILE_NAME_KEY = "image_file_name";
    private static final String ISSUERS_KEY = "issuers";
    private static final String NAME_KEY = "name";
    private String mCountryCode;
    private String mId;
    private String mImageUri;
    private String mName;

    public int describeContents() {
        return 0;
    }

    public static List<IdealBank> fromJson(Configuration configuration, String str) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            return arrayList;
        }
        JSONArray jSONArray = new JSONObject(str).getJSONArray("data");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String optString = jSONObject.optString("country_code");
            JSONArray jSONArray2 = jSONObject.getJSONArray(ISSUERS_KEY);
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                String str2 = "";
                String optString2 = Json.optString(jSONObject2, "id", str2);
                String optString3 = Json.optString(jSONObject2, "name", str2);
                String optString4 = Json.optString(jSONObject2, IMAGE_FILE_NAME_KEY, str2);
                StringBuilder sb = new StringBuilder();
                sb.append(configuration.getAssetsUrl());
                sb.append("/");
                sb.append(ASSETS_URL_PATH);
                sb.append(optString4);
                arrayList.add(new IdealBank(optString, optString2, optString3, sb.toString()));
            }
        }
        return arrayList;
    }

    private IdealBank(String str, String str2, String str3, String str4) {
        this.mCountryCode = str;
        this.mId = str2;
        this.mName = str3;
        this.mImageUri = str4;
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public String getImageUri() {
        return this.mImageUri;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mId);
        parcel.writeString(this.mName);
        parcel.writeString(this.mImageUri);
    }

    protected IdealBank(Parcel parcel) {
        this.mCountryCode = parcel.readString();
        this.mId = parcel.readString();
        this.mName = parcel.readString();
        this.mImageUri = parcel.readString();
    }
}
