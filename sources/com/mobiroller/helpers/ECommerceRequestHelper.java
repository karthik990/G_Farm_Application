package com.mobiroller.helpers;

import android.os.Build.VERSION;
import android.util.Log;
import android.widget.Toast;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.ECommerceResponse;
import com.mobiroller.serviceinterfaces.ECommerceServiceInterface;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ECommerceRequestHelper {
    private static final String CLIENT_ECOMMERCE_VERSION = "1";
    private static final String META_VAL_OS_TYPE = "1";
    private static final String REQUEST_CLIENT_ECOMMERCE_VERSION = "ECommerce-Client-Version";
    private static final String REQUEST_HEADER_LANGUAGE = "Accept-Language";
    private static final String REQUEST_META_APP_VERSION = "app_version";
    private static final String REQUEST_META_LANGUAGE = "language";
    private static final String REQUEST_META_OS_TYPE = "os_type";
    private static final String REQUEST_META_OS_VERSION = "os_version";
    private static final int REQ_CONNECTION_TIMEOUT = 15;
    private static final int REQ_READ_TIMEOUT = 30;
    private static final int REQ_WRITE_TIMEOUT = 15;
    private ECommerceServiceInterface ecommerceServiceInterface;

    public interface ECommerceCallBack<T> {
        void done();

        void onFailure(ECommerceErrorResponse eCommerceErrorResponse);

        void onNetworkError(String str);

        void onSuccess(T t);
    }

    public ECommerceRequestHelper() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        Builder readTimeout = new Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);
        String str = "TR";
        final String str2 = (LocaleHelper.getLocale().toUpperCase().equalsIgnoreCase(str) || LocaleHelper.getLocale().toUpperCase().contains(str)) ? "TR-tr" : "EN-us";
        readTimeout.addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request.Builder addHeader = chain.request().newBuilder().addHeader("api-Key", MobiRollerApplication.app.getString(R.string.applyze_api_key)).addHeader("Content-Type", "application/json").addHeader(ECommerceRequestHelper.REQUEST_META_APP_VERSION, UtilManager.sharedPrefHelper().getAppVersionName());
                String str = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
                String str2 = "Accept-Language";
                Request.Builder addHeader2 = addHeader.addHeader(ECommerceRequestHelper.REQUEST_META_OS_TYPE, str).addHeader(ECommerceRequestHelper.REQUEST_META_OS_VERSION, VERSION.RELEASE).addHeader(ECommerceRequestHelper.REQUEST_META_LANGUAGE, LocaleHelper.getLocale().toUpperCase()).addHeader(str2, str2).addHeader(ECommerceRequestHelper.REQUEST_CLIENT_ECOMMERCE_VERSION, str);
                String str3 = "app-Key";
                if (DynamicConstants.MobiRoller_Stage) {
                    addHeader2.addHeader(str3, DynamicConstants.AppKey);
                } else {
                    addHeader2.addHeader(str3, MobiRollerApplication.app.getString(R.string.applyze_app_key));
                }
                return chain.proceed(addHeader2.build());
            }
        });
        this.ecommerceServiceInterface = (ECommerceServiceInterface) new Retrofit.Builder().baseUrl(Constants.Applyze_ECommerce_BaseURL).client(readTimeout.build()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(ECommerceServiceInterface.class);
    }

    public ECommerceServiceInterface getAPIService() {
        return this.ecommerceServiceInterface;
    }

    public <T> void enqueue(Call<ECommerceResponse<T>> call, final ECommerceCallBack<T> eCommerceCallBack) {
        call.enqueue(new Callback<ECommerceResponse<T>>() {
            public void onResponse(Call<ECommerceResponse<T>> call, retrofit2.Response<ECommerceResponse<T>> response) {
                String str = "onResponse";
                if (response.isSuccessful()) {
                    eCommerceCallBack.done();
                    Log.e(str, "onSuccess");
                    eCommerceCallBack.onSuccess(((ECommerceResponse) response.body()).data);
                    return;
                }
                Log.e(str, "failed");
                eCommerceCallBack.done();
                if (response.code() == 502 || response.errorBody() == null) {
                    Toast.makeText(MobiRollerApplication.app, MobiRollerApplication.app.getString(R.string.common_error), 0).show();
                    return;
                }
                ECommerceResponse eCommerceResponse = (ECommerceResponse) new Gson().fromJson(response.errorBody().charStream(), ECommerceResponse.class);
                if (eCommerceResponse != null) {
                    eCommerceCallBack.onFailure(eCommerceResponse);
                } else {
                    Toast.makeText(MobiRollerApplication.app, MobiRollerApplication.app.getString(R.string.common_error), 0).show();
                }
            }

            public void onFailure(Call<ECommerceResponse<T>> call, Throwable th) {
                StringBuilder sb = new StringBuilder();
                sb.append("onFailure ");
                sb.append(th.getLocalizedMessage());
                Log.e("onResponse", sb.toString());
                eCommerceCallBack.done();
                Toast.makeText(MobiRollerApplication.app, MobiRollerApplication.app.getString(R.string.please_check_your_internet_connection), 0).show();
                eCommerceCallBack.onNetworkError(MobiRollerApplication.app.getString(R.string.please_check_your_internet_connection));
            }
        });
    }
}
