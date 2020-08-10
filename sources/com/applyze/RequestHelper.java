package com.applyze;

import android.os.Build.VERSION;
import android.util.Log;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

class RequestHelper {
    private static final String BASE_URL = "https://api.applyze.com/v1/";
    private static final int REQ_CONNECTION_TIMEOUT = 5;
    private static final int REQ_READ_TIMEOUT = 5;
    private static final int REQ_WRITE_TIMEOUT = 15;
    private static RequestHelper instance;
    private AnalyticsApiService analyticsApiService;

    static RequestHelper getInstance() {
        if (instance == null) {
            synchronized (ApplyzeAnalytics.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                }
            }
        }
        return instance;
    }

    private RequestHelper() {
        Builder readTimeout = new Builder().connectTimeout(5, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        readTimeout.addInterceptor(httpLoggingInterceptor);
        readTimeout.addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().build());
            }
        });
        this.analyticsApiService = (AnalyticsApiService) new Retrofit.Builder().baseUrl(BASE_URL).client(enableTls12OnPreLollipop(readTimeout).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).addConverterFactory(ScalarsConverterFactory.create()).build().create(AnalyticsApiService.class);
    }

    /* access modifiers changed from: 0000 */
    public AnalyticsApiService getAPIService() {
        return this.analyticsApiService;
    }

    private static Builder enableTls12OnPreLollipop(Builder builder) {
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 22) {
            try {
                SSLContext instance2 = SSLContext.getInstance("TLSv1.2");
                instance2.init(null, null, null);
                builder.sslSocketFactory(new Tls12SocketFactory(instance2.getSocketFactory()));
                ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build();
                ArrayList arrayList = new ArrayList();
                arrayList.add(build);
                arrayList.add(ConnectionSpec.COMPATIBLE_TLS);
                arrayList.add(ConnectionSpec.CLEARTEXT);
                builder.connectionSpecs(arrayList);
            } catch (Exception e) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", e);
            }
        }
        return builder;
    }
}
