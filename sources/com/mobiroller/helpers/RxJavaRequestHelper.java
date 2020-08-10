package com.mobiroller.helpers;

import android.content.Context;
import android.os.Build.VERSION;
import com.google.gson.GsonBuilder;
import com.mobiroller.constants.Constants;
import com.mobiroller.serviceinterfaces.MobirollerServiceInterface;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRequestHelper {
    private static final String META_VAL_OS_TYPE = "1";
    private static final String REQUEST_META_APP_VERSION = "app_version";
    private static final String REQUEST_META_LANGUAGE = "language";
    private static final String REQUEST_META_OS_TYPE = "os_type";
    private static final String REQUEST_META_OS_VERSION = "os_version";
    private static final int REQ_CONNECTION_TIMEOUT = 10;
    private static final int REQ_READ_TIMEOUT = 10;
    private static final int REQ_WRITE_TIMEOUT = 10;
    private MobirollerServiceInterface mobirollerServiceInterface;
    private SharedPrefHelper sharedPrefHelper;

    public RxJavaRequestHelper(final Context context, final SharedPrefHelper sharedPrefHelper2) {
        this.sharedPrefHelper = sharedPrefHelper2;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        Builder readTimeout = new Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
        if (VERSION.SDK_INT < 22) {
            readTimeout.connectionSpecs(Collections.singletonList(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).allEnabledCipherSuites().build()));
        }
        readTimeout.addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(RxJavaRequestHelper.REQUEST_META_APP_VERSION, sharedPrefHelper2.getAppVersionName(context)).addHeader(RxJavaRequestHelper.REQUEST_META_OS_TYPE, "1").addHeader(RxJavaRequestHelper.REQUEST_META_OS_VERSION, VERSION.RELEASE).addHeader(RxJavaRequestHelper.REQUEST_META_LANGUAGE, LocaleHelper.getLocale().toUpperCase()).build());
            }
        });
        this.mobirollerServiceInterface = (MobirollerServiceInterface) new Retrofit.Builder().baseUrl(Constants.MobiRoller_BaseURL).client(readTimeout.build()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(MobirollerServiceInterface.class);
    }

    public MobirollerServiceInterface getAPIService() {
        return this.mobirollerServiceInterface;
    }
}
