package com.mobiroller.helpers;

import android.content.Context;
import android.os.Build.VERSION;
import com.mobiroller.constants.Constants;
import com.mobiroller.serviceinterfaces.MobirollerStatsInterface;
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

public class RxJavaRequestStatsHelper {
    private static final String META_VAL_OS_TYPE = "1";
    private static final String REQUEST_META_APP_VERSION = "app_version";
    private static final String REQUEST_META_LANGUAGE = "language";
    private static final String REQUEST_META_OS_TYPE = "os_type";
    private static final String REQUEST_META_OS_VERSION = "os_version";
    private static final int REQ_CONNECTION_TIMEOUT = 15;
    private static final int REQ_READ_TIMEOUT = 30;
    private static final int REQ_WRITE_TIMEOUT = 15;
    private MobirollerStatsInterface mobirollerStatsInterface;
    private SharedPrefHelper sharedPrefHelper;

    public RxJavaRequestStatsHelper(Context context, SharedPrefHelper sharedPrefHelper2) {
        this.sharedPrefHelper = sharedPrefHelper2;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        Builder readTimeout = new Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);
        if (VERSION.SDK_INT < 22) {
            readTimeout.connectionSpecs(Collections.singletonList(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).allEnabledCipherSuites().build()));
        }
        readTimeout.addInterceptor(httpLoggingInterceptor);
        readTimeout.addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().build());
            }
        });
        this.mobirollerStatsInterface = (MobirollerStatsInterface) new Retrofit.Builder().baseUrl(Constants.MobiRoller_BaseURL).client(readTimeout.build()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build().create(MobirollerStatsInterface.class);
    }

    public MobirollerStatsInterface getAPIService() {
        return this.mobirollerStatsInterface;
    }
}
