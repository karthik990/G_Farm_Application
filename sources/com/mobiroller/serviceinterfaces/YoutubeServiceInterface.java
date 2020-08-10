package com.mobiroller.serviceinterfaces;

import com.mobiroller.models.youtube.YoutubeDetailModel;
import com.mobiroller.models.youtube.YoutubeSearchModel;
import p043io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeServiceInterface {
    @GET("videos?")
    Observable<YoutubeDetailModel> getYoutubeListDetails(@Query("part") String str, @Query("key") String str2, @Query("id") String str3);

    @GET("search?")
    Observable<YoutubeSearchModel> getYoutubeSearchData(@Query("part") String str, @Query("order") String str2, @Query("channelId") String str3, @Query("type") String str4, @Query("key") String str5, @Query("maxResults") String str6, @Query("pageToken") String str7);
}
