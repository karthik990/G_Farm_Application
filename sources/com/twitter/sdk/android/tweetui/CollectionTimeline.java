package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.core.models.TwitterCollection;
import com.twitter.sdk.android.core.models.TwitterCollection.TimelineItem;
import com.twitter.sdk.android.core.models.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import retrofit2.Call;

public class CollectionTimeline extends BaseTimeline implements Timeline<Tweet> {
    static final String COLLECTION_PREFIX = "custom-";
    final String collectionIdentifier;
    final Integer maxItemsPerRequest;
    final TwitterCore twitterCore;

    public static class Builder {
        private Long collectionId;
        private Integer maxItemsPerRequest;
        private final TwitterCore twitterCore;

        public Builder() {
            this.maxItemsPerRequest = Integer.valueOf(30);
            this.twitterCore = TwitterCore.getInstance();
        }

        Builder(TwitterCore twitterCore2) {
            this.maxItemsPerRequest = Integer.valueOf(30);
            this.twitterCore = twitterCore2;
        }

        /* renamed from: id */
        public Builder mo63298id(Long l) {
            this.collectionId = l;
            return this;
        }

        public Builder maxItemsPerRequest(Integer num) {
            this.maxItemsPerRequest = num;
            return this;
        }

        public CollectionTimeline build() {
            Long l = this.collectionId;
            if (l != null) {
                return new CollectionTimeline(this.twitterCore, l, this.maxItemsPerRequest);
            }
            throw new IllegalStateException("collection id must not be null");
        }
    }

    class CollectionCallback extends Callback<TwitterCollection> {

        /* renamed from: cb */
        final Callback<TimelineResult<Tweet>> f3668cb;

        CollectionCallback(Callback<TimelineResult<Tweet>> callback) {
            this.f3668cb = callback;
        }

        public void success(Result<TwitterCollection> result) {
            TimelineResult timelineResult;
            TimelineCursor timelineCursor = CollectionTimeline.getTimelineCursor((TwitterCollection) result.data);
            List orderedTweets = CollectionTimeline.getOrderedTweets((TwitterCollection) result.data);
            if (timelineCursor != null) {
                timelineResult = new TimelineResult(timelineCursor, orderedTweets);
            } else {
                timelineResult = new TimelineResult(null, Collections.emptyList());
            }
            Callback<TimelineResult<Tweet>> callback = this.f3668cb;
            if (callback != null) {
                callback.success(new Result(timelineResult, result.response));
            }
        }

        public void failure(TwitterException twitterException) {
            Callback<TimelineResult<Tweet>> callback = this.f3668cb;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }

    CollectionTimeline(TwitterCore twitterCore2, Long l, Integer num) {
        if (l == null) {
            this.collectionIdentifier = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(COLLECTION_PREFIX);
            sb.append(Long.toString(l.longValue()));
            this.collectionIdentifier = sb.toString();
        }
        this.twitterCore = twitterCore2;
        this.maxItemsPerRequest = num;
    }

    public void next(Long l, Callback<TimelineResult<Tweet>> callback) {
        createCollectionRequest(l, null).enqueue(new CollectionCallback(callback));
    }

    public void previous(Long l, Callback<TimelineResult<Tweet>> callback) {
        createCollectionRequest(null, l).enqueue(new CollectionCallback(callback));
    }

    /* access modifiers changed from: 0000 */
    public Call<TwitterCollection> createCollectionRequest(Long l, Long l2) {
        return this.twitterCore.getApiClient().getCollectionService().collection(this.collectionIdentifier, this.maxItemsPerRequest, l2, l);
    }

    static List<Tweet> getOrderedTweets(TwitterCollection twitterCollection) {
        if (twitterCollection == null || twitterCollection.contents == null || twitterCollection.contents.tweetMap == null || twitterCollection.contents.userMap == null || twitterCollection.contents.tweetMap.isEmpty() || twitterCollection.contents.userMap.isEmpty() || twitterCollection.metadata == null || twitterCollection.metadata.timelineItems == null || twitterCollection.metadata.position == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (TimelineItem timelineItem : twitterCollection.metadata.timelineItems) {
            arrayList.add(mapTweetToUsers((Tweet) twitterCollection.contents.tweetMap.get(timelineItem.tweetItem.f3664id), twitterCollection.contents.userMap));
        }
        return arrayList;
    }

    static Tweet mapTweetToUsers(Tweet tweet, Map<Long, User> map) {
        TweetBuilder user = new TweetBuilder().copy(tweet).setUser((User) map.get(Long.valueOf(tweet.user.f3665id)));
        if (tweet.quotedStatus != null) {
            user.setQuotedStatus(mapTweetToUsers(tweet.quotedStatus, map));
        }
        return user.build();
    }

    static TimelineCursor getTimelineCursor(TwitterCollection twitterCollection) {
        if (twitterCollection == null || twitterCollection.metadata == null || twitterCollection.metadata.position == null) {
            return null;
        }
        return new TimelineCursor(twitterCollection.metadata.position.minPosition, twitterCollection.metadata.position.maxPosition);
    }
}
