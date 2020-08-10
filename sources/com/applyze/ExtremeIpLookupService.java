package com.applyze;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

interface ExtremeIpLookupService {
    @GET("json")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    Call<ExtremeApiModel> getMetaDataFromIp();
}
