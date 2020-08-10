package com.mobiroller.serviceinterfaces;

import com.mobiroller.models.ecommerce.AddProductModel;
import com.mobiroller.models.ecommerce.CompleteOrder;
import com.mobiroller.models.ecommerce.ECommerceResponse;
import com.mobiroller.models.ecommerce.MakeOrder;
import com.mobiroller.models.ecommerce.Order;
import com.mobiroller.models.ecommerce.OrderDetailModel;
import com.mobiroller.models.ecommerce.OrderResponseInner;
import com.mobiroller.models.ecommerce.PaymentSettings;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.models.ecommerce.ShoppingCartResponse;
import com.mobiroller.models.ecommerce.UpdateCartItemQuantity;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ECommerceServiceInterface {
    @POST("v2/users/{userId}/shopping-cart/items")
    Call<ECommerceResponse> addProductToShoppingCart(@Path("userId") String str, @Body AddProductModel addProductModel);

    @DELETE("v2/users/{userId}/shopping-cart/items")
    Call<ECommerceResponse> clearShoppingCart(@Path("userId") String str);

    @POST("v2/orders/{orderId}/failure")
    Call<ECommerceResponse> failurePayment(@Path("orderId") String str);

    @GET("v2/orders/{path}")
    Call<ECommerceResponse<OrderDetailModel>> getOrder(@Path("path") String str);

    @GET("v2/orders?perPage=50&orderBy=OrderByDescending")
    Call<ECommerceResponse<List<Order>>> getOrderList(@Query("userId") String str);

    @GET("v2/paymentSettings")
    Call<ECommerceResponse<PaymentSettings>> getPaymentSettings();

    @GET("v2/products/{path}")
    Call<ECommerceResponse<ProductDetailModel>> getProduct(@Path("path") String str);

    @GET("v2/products")
    Call<ECommerceResponse<List<ProductDetailModel>>> getProducts(@QueryMap Map<String, String> map);

    @GET("v2/users/{userId}/shopping-cart")
    Call<ECommerceResponse<ShoppingCartResponse>> getShoppingCart(@Path("userId") String str);

    @GET("v2/users/{userId}/shopping-cart/count")
    Call<ECommerceResponse<Integer>> getShoppingCartCount(@Path("userId") String str);

    @POST("v2/orders/user/{userId}")
    Call<ECommerceResponse<OrderResponseInner>> makeOrder(@Path("userId") String str, @Body MakeOrder makeOrder);

    @DELETE("v2/users/{userId}/shopping-cart/items/{cartItemId}")
    Call<ECommerceResponse> removeItemFromShoppingCart(@Path("userId") String str, @Path("cartItemId") String str2);

    @POST("v2/orders/complete")
    Call<ECommerceResponse<OrderResponseInner>> tryAgain(@Body CompleteOrder completeOrder);

    @PUT("v2/users/{userId}/shopping-cart/items/{cartItemId}/quantity")
    Call<ECommerceResponse> updateItemQuantity(@Path("userId") String str, @Path("cartItemId") String str2, @Body UpdateCartItemQuantity updateCartItemQuantity);

    @GET("v2/users/{userId}/shopping-cart/validate")
    Call<ECommerceResponse<ShoppingCartResponse>> validateShoppingCart(@Path("userId") String str);
}
