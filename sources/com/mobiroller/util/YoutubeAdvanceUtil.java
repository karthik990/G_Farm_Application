package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Builder;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;

public class YoutubeAdvanceUtil {
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/youtube.readonly", "https://www.googleapis.com/auth/youtube.force-ssl"};

    public static YouTube getService(SharedPrefHelper sharedPrefHelper, Activity activity) {
        HttpTransport newCompatibleTransport = AndroidHttp.newCompatibleTransport();
        JacksonFactory defaultInstance = JacksonFactory.getDefaultInstance();
        GoogleAccountCredential usingOAuth2 = GoogleAccountCredential.usingOAuth2(activity, Collections.singleton("https://www.googleapis.com/auth/youtube.readonly"));
        if (sharedPrefHelper.getGoogleSignInAccount() == null) {
            return new Builder(newCompatibleTransport, defaultInstance, new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                }
            }).setApplicationName(activity.getString(R.string.app_name)).build();
        }
        usingOAuth2.setSelectedAccount(sharedPrefHelper.getGoogleSignInAccount().getAccount());
        return new Builder(newCompatibleTransport, defaultInstance, usingOAuth2).setApplicationName(activity.getString(R.string.app_name)).build();
    }

    public static String getNumberWithExtension(Context context, BigInteger bigInteger) {
        if (BigInteger.valueOf(1000).compareTo(bigInteger) > 0) {
            return bigInteger.toString();
        }
        if (BigInteger.valueOf(1000000).compareTo(bigInteger) > 0) {
            return context.getString(R.string.youtube_count_thousand, new Object[]{String.valueOf(new BigDecimal(bigInteger).divide(new BigDecimal(1000), 1, RoundingMode.HALF_EVEN).floatValue())});
        }
        return context.getString(R.string.youtube_count_million, new Object[]{String.valueOf(new BigDecimal(bigInteger).divide(new BigDecimal(1000000), 1, RoundingMode.HALF_EVEN).floatValue())});
    }
}
