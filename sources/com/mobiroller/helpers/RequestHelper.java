package com.mobiroller.helpers;

import android.content.Context;
import android.os.Build.VERSION;
import com.google.gson.GsonBuilder;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.serviceinterfaces.MobirollerServiceInterface;
import com.mobiroller.serviceinterfaces.MobirollerServicePreviewInterface;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {
    private static final String META_VAL_OS_TYPE = "1";
    private static final String REQUEST_HEADER_ACCEPT = "Accept";
    private static final String REQUEST_HEADER_APPLYZE_API_KEY = "Api-key";
    private static final String REQUEST_HEADER_LANGUAGE = "Accept-Language";
    private static final String REQUEST_META_APP_VERSION = "app_version";
    private static final String REQUEST_META_LANGUAGE = "language";
    private static final String REQUEST_META_OS_TYPE = "os_type";
    private static final String REQUEST_META_OS_VERSION = "os_version";
    private static final int REQ_CONNECTION_TIMEOUT = 10;
    private static final int REQ_READ_TIMEOUT = 10;
    private static final int REQ_WRITE_TIMEOUT = 10;

    public RequestHelper() {
    }

    @Deprecated
    public RequestHelper(Context context, SharedPrefHelper sharedPrefHelper) {
    }

    public <T> T getAPIService(Class<T> cls, String str) {
        return getRetrofitBuilder().baseUrl(str).build().create(cls);
    }

    public MobirollerServiceInterface getAPIService() {
        return (MobirollerServiceInterface) getAPIService(MobirollerServiceInterface.class, Constants.MobiRoller_BaseURL);
    }

    public ApplyzeUserServiceInterface getApplyzeUserAPIService() {
        return (ApplyzeUserServiceInterface) getAPIService(ApplyzeUserServiceInterface.class, Constants.Applyze_User_BaseURL);
    }

    public MobirollerServicePreviewInterface getPreviewAPIService() {
        return (MobirollerServicePreviewInterface) getAPIService(MobirollerServicePreviewInterface.class, Constants.MobiRoller_BaseURL);
    }

    private Builder getRetrofitBuilder() {
        OkHttpClient.Builder readTimeout = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
        if (VERSION.SDK_INT < 22) {
            readTimeout.connectionSpecs(Collections.singletonList(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).allEnabledCipherSuites().build()));
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        readTimeout.addInterceptor(httpLoggingInterceptor);
        String str = "TR";
        final String str2 = (LocaleHelper.getLocale().toUpperCase().equalsIgnoreCase(str) || LocaleHelper.getLocale().toUpperCase().contains(str)) ? "TR-tr" : "EN-us";
        readTimeout.addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                String str = "Accept-Language";
                return chain.proceed(chain.request().newBuilder().addHeader(RequestHelper.REQUEST_META_APP_VERSION, UtilManager.sharedPrefHelper().getAppVersionName()).addHeader(RequestHelper.REQUEST_META_OS_TYPE, "1").addHeader(RequestHelper.REQUEST_META_OS_VERSION, VERSION.RELEASE).addHeader(RequestHelper.REQUEST_META_LANGUAGE, LocaleHelper.getLocale().toUpperCase()).addHeader(str, str2).addHeader("Accept", "application/json").addHeader(RequestHelper.REQUEST_HEADER_APPLYZE_API_KEY, MobiRollerApplication.app.getString(R.string.applyze_api_key)).build());
            }
        });
        return new Builder().client(readTimeout.build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));
    }
}
