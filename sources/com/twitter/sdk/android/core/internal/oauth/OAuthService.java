package com.twitter.sdk.android.core.internal.oauth;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.internal.network.OkHttpClientHelper;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

abstract class OAuthService {
    private static final String CLIENT_NAME = "TwitterAndroidSDK";
    private final TwitterApi api;
    private final Retrofit retrofit = new Builder().baseUrl(getApi().getBaseHostUrl()).client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        public final Response intercept(Chain chain) {
            return OAuthService.this.lambda$new$0$OAuthService(chain);
        }
    }).certificatePinner(OkHttpClientHelper.getCertificatePinner()).build()).addConverterFactory(GsonConverterFactory.create()).build();
    private final TwitterCore twitterCore;
    private final String userAgent;

    OAuthService(TwitterCore twitterCore2, TwitterApi twitterApi) {
        this.twitterCore = twitterCore2;
        this.api = twitterApi;
        this.userAgent = TwitterApi.buildUserAgent(CLIENT_NAME, twitterCore2.getVersion());
    }

    public /* synthetic */ Response lambda$new$0$OAuthService(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder().header("User-Agent", getUserAgent()).build());
    }

    /* access modifiers changed from: protected */
    public TwitterCore getTwitterCore() {
        return this.twitterCore;
    }

    /* access modifiers changed from: protected */
    public TwitterApi getApi() {
        return this.api;
    }

    /* access modifiers changed from: protected */
    public String getUserAgent() {
        return this.userAgent;
    }

    /* access modifiers changed from: protected */
    public Retrofit getRetrofit() {
        return this.retrofit;
    }
}
