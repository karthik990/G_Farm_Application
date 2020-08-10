package com.mobiroller.serviceinterfaces;

import com.mobiroller.models.IntroModel;
import com.mobiroller.models.LoginModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MobirollerServicePreviewInterface {
    @POST("Account/ForgotPassword")
    Call<Boolean> forgotPassword(@Query("email") String str);

    @GET("JSON/GetJSONForPreview/")
    Call<IntroModel> getIntroJSON(@Query("accountScreenID") String str);

    @GET("JSON/GetJSONForPreview/")
    Call<MainModel> getMainJSON(@Query("accountScreenID") String str);

    @GET("JSON/GetJSONForPreview/")
    Call<NavigationModel> getNavigationJSON(@Query("accountScreenID") String str);

    @GET("JSON/GetJSONForPreview/")
    Call<ScreenModel> getScreenJSON(@Query("accountScreenID") String str);

    @GET("Account/PreviewLogIn")
    Call<LoginModel> loginByEmail(@Query("UserName") String str, @Query("Password") String str2);
}
