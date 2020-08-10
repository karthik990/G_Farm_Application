package com.mobiroller.serviceinterfaces;

import com.mobiroller.models.response.CommunityRoleResponse;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.models.user.AddBillingAddress;
import com.mobiroller.models.user.AddShippingAddress;
import com.mobiroller.models.user.CountryModel;
import com.mobiroller.models.user.DefaultAddressList;
import com.mobiroller.models.user.DefaultAddressModel;
import com.mobiroller.models.user.EditBillingAddress;
import com.mobiroller.models.user.EditShippingAddress;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApplyzeUserServiceInterface {
    @POST("addresses/{userId}")
    Call<DefaultAddressList> addAddress(@Path("userId") String str, @Body AddShippingAddress addShippingAddress);

    @POST("addresses/{userId}/billing")
    Call<UserBillingAddressModel> addBillingAddress(@Path("userId") String str, @Body AddBillingAddress addBillingAddress);

    @POST("addresses/{userId}/shipping")
    Call<UserShippingAddressModel> addShippingAddress(@Path("userId") String str, @Body AddShippingAddress addShippingAddress);

    @FormUrlEncoded
    @POST("users/changePassword")
    Call<UserLoginResponse> changePassword(@FieldMap Map<String, Object> map);

    @DELETE("addresses/{userId}/billing/{addressId}")
    Call<Void> deleteBillingAddress(@Path("userId") String str, @Path("addressId") String str2, @Header("api-key") String str3, @Header("app-key") String str4);

    @DELETE("addresses/{userId}/shipping/{addressId}")
    Call<Void> deleteShippingAddress(@Path("userId") String str, @Path("addressId") String str2, @Header("api-key") String str3, @Header("app-key") String str4);

    @PUT("addresses/{userId}/billing")
    Call<UserBillingAddressModel> editBillingAddress(@Path("userId") String str, @Body EditBillingAddress editBillingAddress);

    @PUT("addresses/{userId}/shipping")
    Call<UserShippingAddressModel> editShippingAddress(@Path("userId") String str, @Body EditShippingAddress editShippingAddress);

    @FormUrlEncoded
    @POST("users/forgotPassword")
    Call<Void> forgotPassword(@FieldMap Map<String, String> map);

    @GET("addresses/{userId}/billing")
    Call<List<UserBillingAddressModel>> getBillingAddresses(@Path("userId") String str, @Header("api-key") String str2, @Header("app-key") String str3);

    @GET("addresses/cities")
    Call<List<CountryModel>> getCities(@Query("stateId") String str);

    @GET("communityRoles")
    Call<List<CommunityRoleResponse>> getCommunityRolesList(@Header("api-key") String str, @Header("app-key") String str2);

    @GET("addresses/countries")
    Call<List<CountryModel>> getCountries();

    @GET("addresses/{userId}/defaults")
    Call<DefaultAddressList> getDefaultAddresses(@Path("userId") String str, @Header("api-key") String str2, @Header("app-key") String str3);

    @GET("addresses/{userId}/shipping")
    Call<List<UserShippingAddressModel>> getShippingAddresses(@Path("userId") String str, @Header("api-key") String str2, @Header("app-key") String str3);

    @GET("addresses/states")
    Call<List<CountryModel>> getStates(@Query("countryId") String str);

    @GET("profileElements")
    Call<List<UserProfileElement>> getUserProfileElements(@Header("api-key") String str, @Header("app-key") String str2);

    @FormUrlEncoded
    @POST("users/login")
    Call<UserLoginResponse> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("users/loginWithGoogle")
    Call<UserLoginResponse> loginWithGoogle(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("users")
    Call<UserLoginResponse> register(@FieldMap Map<String, String> map);

    @PATCH("addresses/{userId}/billing/{addressId}")
    Call<Void> setDefaultBillingAddress(@Path("userId") String str, @Path("addressId") String str2, @Body DefaultAddressModel defaultAddressModel);

    @PATCH("addresses/{userId}/shipping/{addressId}")
    Call<Void> setDefaultShippingAddress(@Path("userId") String str, @Path("addressId") String str2, @Body DefaultAddressModel defaultAddressModel);

    @PUT("users")
    @Multipart
    Call<UserLoginResponse> updateUser(@PartMap Map<String, RequestBody> map);

    @PUT("users")
    @Multipart
    Call<UserLoginResponse> updateUser(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("users/validateSession")
    Call<UserLoginResponse> validateSession(@FieldMap Map<String, String> map);
}
