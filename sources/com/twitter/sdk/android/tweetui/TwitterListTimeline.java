package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;
import retrofit2.Call;

public class TwitterListTimeline extends BaseTimeline implements Timeline<Tweet> {
    final Boolean includeRetweets;
    final Long listId;
    final Integer maxItemsPerRequest;
    final Long ownerId;
    final String ownerScreenName;
    final String slug;
    final TwitterCore twitterCore;

    public static class Builder {
        private Boolean includeRetweets;
        private Long listId;
        private Integer maxItemsPerRequest;
        private Long ownerId;
        private String ownerScreenName;
        private String slug;
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
        public Builder mo63431id(Long l) {
            this.listId = l;
            return this;
        }

        public Builder slugWithOwnerId(String str, Long l) {
            this.slug = str;
            this.ownerId = l;
            return this;
        }

        public Builder slugWithOwnerScreenName(String str, String str2) {
            this.slug = str;
            this.ownerScreenName = str2;
            return this;
        }

        public Builder maxItemsPerRequest(Integer num) {
            this.maxItemsPerRequest = num;
            return this;
        }

        public Builder includeRetweets(Boolean bool) {
            this.includeRetweets = bool;
            return this;
        }

        public TwitterListTimeline build() {
            boolean z = true;
            boolean z2 = this.listId == null;
            if (this.slug != null) {
                z = false;
            }
            if (!(z2 ^ z)) {
                throw new IllegalStateException("must specify either a list id or slug/owner pair");
            } else if (this.slug != null && this.ownerId == null && this.ownerScreenName == null) {
                throw new IllegalStateException("slug/owner pair must set owner via ownerId or ownerScreenName");
            } else {
                TwitterListTimeline twitterListTimeline = new TwitterListTimeline(this.twitterCore, this.listId, this.slug, this.ownerId, this.ownerScreenName, this.maxItemsPerRequest, this.includeRetweets);
                return twitterListTimeline;
            }
        }
    }

    TwitterListTimeline(TwitterCore twitterCore2, Long l, String str, Long l2, String str2, Integer num, Boolean bool) {
        this.twitterCore = twitterCore2;
        this.listId = l;
        this.slug = str;
        this.ownerId = l2;
        this.ownerScreenName = str2;
        this.maxItemsPerRequest = num;
        this.includeRetweets = bool;
    }

    public void next(Long l, Callback<TimelineResult<Tweet>> callback) {
        createListTimelineRequest(l, null).enqueue(new TweetsCallback(callback));
    }

    public void previous(Long l, Callback<TimelineResult<Tweet>> callback) {
        createListTimelineRequest(null, decrementMaxId(l)).enqueue(new TweetsCallback(callback));
    }

    /* access modifiers changed from: 0000 */
    public Call<List<Tweet>> createListTimelineRequest(Long l, Long l2) {
        return this.twitterCore.getApiClient().getListService().statuses(this.listId, this.slug, this.ownerScreenName, this.ownerId, l, l2, this.maxItemsPerRequest, Boolean.valueOf(true), this.includeRetweets);
    }
}
