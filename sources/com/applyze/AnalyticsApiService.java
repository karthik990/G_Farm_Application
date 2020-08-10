package com.applyze;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface AnalyticsApiService {
    @POST("app/analytic/session")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "Version-Number: 2", "Version-Name: 1.0.0-alpha.30"})
    Call<ResponseBody> sendSession(@Body Session session);
}
