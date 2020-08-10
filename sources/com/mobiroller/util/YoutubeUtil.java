package com.mobiroller.util;

import com.mobiroller.constants.YoutubeConstants;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.models.youtube.ItemSearch;
import com.mobiroller.models.youtube.YoutubeDetailModel;
import com.mobiroller.models.youtube.YoutubeSearchModel;
import com.mobiroller.serviceinterfaces.YoutubeServiceInterface;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.greenrobot.eventbus.EventBus;
import p043io.reactivex.Observer;
import p043io.reactivex.android.schedulers.AndroidSchedulers;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeUtil {
    /* access modifiers changed from: private */
    public String nextPageToken = "";
    private Retrofit retrofit;
    /* access modifiers changed from: private */
    public String screenId;
    private YoutubeServiceInterface youtubeService;

    public YoutubeUtil(String str) {
        this.screenId = str;
        this.retrofit = getRetrofit();
        this.youtubeService = getYoutubeService();
    }

    private Retrofit getRetrofit() {
        return new Builder().client(new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).addInterceptor(new HttpLoggingInterceptor().setLevel(Level.NONE)).build()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://www.googleapis.com/youtube/v3/").build();
    }

    private YoutubeServiceInterface getYoutubeService() {
        return (YoutubeServiceInterface) this.retrofit.create(YoutubeServiceInterface.class);
    }

    public void getVideoListId(String str) {
        if (this.nextPageToken != null) {
            String str2 = str;
            this.youtubeService.getYoutubeSearchData(YoutubeRequestParams.req_search_parts, YoutubeRequestParams.req_search_order, str2, "video", YoutubeConstants.YOUTUBE_KEY, YoutubeRequestParams.req_search_max_results, this.nextPageToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<YoutubeSearchModel>() {
                public void onComplete() {
                }

                public void onSubscribe(Disposable disposable) {
                }

                public void onError(Throwable th) {
                    EventBus.getDefault().post(new YoutubeDetailModel());
                }

                public void onNext(YoutubeSearchModel youtubeSearchModel) {
                    String str = "";
                    String str2 = str;
                    for (ItemSearch itemSearch : youtubeSearchModel.getItems()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(itemSearch.getId().getVideoId());
                        sb.append(",");
                        str2 = sb.toString();
                    }
                    if (YoutubeUtil.this.nextPageToken == null || youtubeSearchModel.getNextPageToken() == null || youtubeSearchModel.getNextPageToken().equalsIgnoreCase(str) || YoutubeUtil.this.nextPageToken.equalsIgnoreCase(youtubeSearchModel.getNextPageToken())) {
                        YoutubeUtil.this.nextPageToken = null;
                    } else {
                        YoutubeUtil.this.nextPageToken = youtubeSearchModel.getNextPageToken();
                    }
                    YoutubeUtil.this.getVideoListWithDetails(str2);
                }
            });
            return;
        }
        EventBus.getDefault().post(new YoutubeDetailModel());
    }

    public void getVideoListWithDetails(String str) {
        this.youtubeService.getYoutubeListDetails(YoutubeRequestParams.req_detail_parts, YoutubeConstants.YOUTUBE_KEY, str).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<YoutubeDetailModel>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onError(Throwable th) {
                EventBus.getDefault().post(new YoutubeDetailModel());
            }

            public void onNext(YoutubeDetailModel youtubeDetailModel) {
                youtubeDetailModel.setScreenId(YoutubeUtil.this.screenId);
                EventBus.getDefault().post(youtubeDetailModel);
            }
        });
    }
}
