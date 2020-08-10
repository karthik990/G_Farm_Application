package com.mobiroller.serviceinterfaces;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MobirollerStatsInterface {
    @FormUrlEncoded
    @POST("MobiRoller/LogStageLogin")
    Call<String> sendUserData(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("AveAnalytics/UserToken")
    Call<Void> serverRegister(@FieldMap HashMap<String, String> hashMap);
}
