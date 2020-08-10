package com.mobiroller.serviceinterfaces;

import com.mobiroller.models.AccountInfoModel;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.IntroModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.MobirollerBadgeModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.TwitterModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface MobirollerServiceInterface {
    @GET
    Call<MobirollerBadgeModel> getBadgeInfo(@Url String str);

    @GET("JSON/GetJSON")
    Call<InAppPurchaseModel> getInAppPurchaseProducts(@Query("accountScreenID") String str);

    @GET("JSON/GetJSON")
    Call<IntroModel> getIntroJSON(@Query("accountScreenID") String str);

    @GET("MobiRoller/GetIpAddress")
    Call<String> getIpAddress();

    @GET("JSON/GetJSON")
    Call<MainModel> getMainJSON(@Query("accountScreenID") String str);

    @GET("JSON/GetJSON")
    Call<NavigationModel> getNavigationJSON(@Query("accountScreenID") String str);

    @GET("JSON/GetJSON")
    Call<ScreenModel> getScreenJSON(@Query("accountScreenID") String str);

    @GET
    Call<String> getScript(@Url String str);

    @GET("JSON/GetAveTweetUserTimeline")
    Call<ArrayList<TwitterModel>> getTwitterModel(@Query("userName") String str, @Query("tweetCount") String str2, @Query("accountName") String str3);

    @POST("ProfilePost/GetOrphanAccountInfo")
    Call<AccountInfoModel> getUserLoginKey(@QueryMap Map<String, String> map);

    @GET("aveRateApp/SendFeedBack")
    Call<ResponseBody> sendFeedback(@Query("accountName") String str, @Query("message") String str2, @Query("rating") int i);

    @FormUrlEncoded
    @POST("FormPost/aveFormViewPost")
    Call<ResponseBody> sendForm(@FieldMap HashMap<String, String> hashMap);

    @POST("FormPost/aveFormViewPost")
    @Multipart
    Call<ResponseBody> sendFormWithImages(@PartMap HashMap<String, RequestBody> hashMap, @Part List<MultipartBody.Part> list);
}
