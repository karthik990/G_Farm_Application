package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.models.HashtagEntity;
import com.twitter.sdk.android.core.models.MentionEntity;
import com.twitter.sdk.android.core.models.SymbolEntity;
import com.twitter.sdk.android.core.models.UrlEntity;

class FormattedUrlEntity {
    final String displayUrl;
    int end;
    final String expandedUrl;
    int start;
    final String url;

    FormattedUrlEntity(int i, int i2, String str, String str2, String str3) {
        this.start = i;
        this.end = i2;
        this.displayUrl = str;
        this.url = str2;
        this.expandedUrl = str3;
    }

    static FormattedUrlEntity createFormattedUrlEntity(UrlEntity urlEntity) {
        FormattedUrlEntity formattedUrlEntity = new FormattedUrlEntity(urlEntity.getStart(), urlEntity.getEnd(), urlEntity.displayUrl, urlEntity.url, urlEntity.expandedUrl);
        return formattedUrlEntity;
    }

    static FormattedUrlEntity createFormattedUrlEntity(HashtagEntity hashtagEntity) {
        String hashtagPermalink = TweetUtils.getHashtagPermalink(hashtagEntity.text);
        int start2 = hashtagEntity.getStart();
        int end2 = hashtagEntity.getEnd();
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(hashtagEntity.text);
        FormattedUrlEntity formattedUrlEntity = new FormattedUrlEntity(start2, end2, sb.toString(), hashtagPermalink, hashtagPermalink);
        return formattedUrlEntity;
    }

    static FormattedUrlEntity createFormattedUrlEntity(MentionEntity mentionEntity) {
        String profilePermalink = TweetUtils.getProfilePermalink(mentionEntity.screenName);
        int start2 = mentionEntity.getStart();
        int end2 = mentionEntity.getEnd();
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(mentionEntity.screenName);
        FormattedUrlEntity formattedUrlEntity = new FormattedUrlEntity(start2, end2, sb.toString(), profilePermalink, profilePermalink);
        return formattedUrlEntity;
    }

    static FormattedUrlEntity createFormattedUrlEntity(SymbolEntity symbolEntity) {
        String symbolPermalink = TweetUtils.getSymbolPermalink(symbolEntity.text);
        int start2 = symbolEntity.getStart();
        int end2 = symbolEntity.getEnd();
        StringBuilder sb = new StringBuilder();
        sb.append("$");
        sb.append(symbolEntity.text);
        FormattedUrlEntity formattedUrlEntity = new FormattedUrlEntity(start2, end2, sb.toString(), symbolPermalink, symbolPermalink);
        return formattedUrlEntity;
    }
}
