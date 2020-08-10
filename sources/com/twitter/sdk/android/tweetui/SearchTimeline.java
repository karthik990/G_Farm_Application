package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.params.Geocode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;

public class SearchTimeline extends BaseTimeline implements Timeline<Tweet> {
    static final String FILTER_RETWEETS = " -filter:retweets";
    /* access modifiers changed from: private */
    public static final SimpleDateFormat QUERY_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    final Geocode geocode;
    final String languageCode;
    final Integer maxItemsPerRequest;
    final String query;
    final String resultType;
    final TwitterCore twitterCore;
    final String untilDate;

    public static class Builder {
        private Geocode geocode;
        private String lang;
        private Integer maxItemsPerRequest;
        private String query;
        private String resultType;
        private final TwitterCore twitterCore;
        private String untilDate;

        public Builder() {
            this.resultType = ResultType.FILTERED.type;
            this.maxItemsPerRequest = Integer.valueOf(30);
            this.twitterCore = TwitterCore.getInstance();
        }

        Builder(TwitterCore twitterCore2) {
            this.resultType = ResultType.FILTERED.type;
            this.maxItemsPerRequest = Integer.valueOf(30);
            this.twitterCore = twitterCore2;
        }

        public Builder query(String str) {
            this.query = str;
            return this;
        }

        public Builder geocode(Geocode geocode2) {
            this.geocode = geocode2;
            return this;
        }

        public Builder resultType(ResultType resultType2) {
            this.resultType = resultType2.type;
            return this;
        }

        public Builder languageCode(String str) {
            this.lang = str;
            return this;
        }

        public Builder maxItemsPerRequest(Integer num) {
            this.maxItemsPerRequest = num;
            return this;
        }

        public Builder untilDate(Date date) {
            this.untilDate = SearchTimeline.QUERY_DATE.format(date);
            return this;
        }

        public SearchTimeline build() {
            String str = this.query;
            if (str != null) {
                SearchTimeline searchTimeline = new SearchTimeline(this.twitterCore, str, this.geocode, this.resultType, this.lang, this.maxItemsPerRequest, this.untilDate);
                return searchTimeline;
            }
            throw new IllegalStateException("query must not be null");
        }
    }

    public enum ResultType {
        RECENT("recent"),
        POPULAR("popular"),
        MIXED("mixed"),
        FILTERED("filtered");
        
        final String type;

        private ResultType(String str) {
            this.type = str;
        }
    }

    class SearchCallback extends Callback<Search> {

        /* renamed from: cb */
        final Callback<TimelineResult<Tweet>> f3672cb;

        SearchCallback(Callback<TimelineResult<Tweet>> callback) {
            this.f3672cb = callback;
        }

        public void success(Result<Search> result) {
            List<Tweet> list = ((Search) result.data).tweets;
            TimelineResult timelineResult = new TimelineResult(new TimelineCursor(list), list);
            Callback<TimelineResult<Tweet>> callback = this.f3672cb;
            if (callback != null) {
                callback.success(new Result(timelineResult, result.response));
            }
        }

        public void failure(TwitterException twitterException) {
            Callback<TimelineResult<Tweet>> callback = this.f3672cb;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }

    SearchTimeline(TwitterCore twitterCore2, String str, Geocode geocode2, String str2, String str3, Integer num, String str4) {
        String str5;
        this.twitterCore = twitterCore2;
        this.languageCode = str3;
        this.maxItemsPerRequest = num;
        this.untilDate = str4;
        this.resultType = str2;
        if (str == null) {
            str5 = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(FILTER_RETWEETS);
            str5 = sb.toString();
        }
        this.query = str5;
        this.geocode = geocode2;
    }

    public void next(Long l, Callback<TimelineResult<Tweet>> callback) {
        createSearchRequest(l, null).enqueue(new SearchCallback(callback));
    }

    public void previous(Long l, Callback<TimelineResult<Tweet>> callback) {
        createSearchRequest(null, decrementMaxId(l)).enqueue(new SearchCallback(callback));
    }

    /* access modifiers changed from: 0000 */
    public Call<Search> createSearchRequest(Long l, Long l2) {
        return this.twitterCore.getApiClient().getSearchService().tweets(this.query, this.geocode, this.languageCode, null, this.resultType, this.maxItemsPerRequest, this.untilDate, l, l2, Boolean.valueOf(true));
    }
}
