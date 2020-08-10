package com.google.api.services.youtube;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.youtube.model.Activity;
import com.google.api.services.youtube.model.ActivityListResponse;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelBannerResource;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ChannelSection;
import com.google.api.services.youtube.model.ChannelSectionListResponse;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.GuideCategoryListResponse;
import com.google.api.services.youtube.model.I18nLanguageListResponse;
import com.google.api.services.youtube.model.I18nRegionListResponse;
import com.google.api.services.youtube.model.InvideoBranding;
import com.google.api.services.youtube.model.LiveBroadcast;
import com.google.api.services.youtube.model.LiveBroadcastListResponse;
import com.google.api.services.youtube.model.LiveChatBan;
import com.google.api.services.youtube.model.LiveChatMessage;
import com.google.api.services.youtube.model.LiveChatMessageListResponse;
import com.google.api.services.youtube.model.LiveChatModerator;
import com.google.api.services.youtube.model.LiveChatModeratorListResponse;
import com.google.api.services.youtube.model.LiveStream;
import com.google.api.services.youtube.model.LiveStreamListResponse;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SponsorListResponse;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import com.google.api.services.youtube.model.SuperChatEventListResponse;
import com.google.api.services.youtube.model.ThumbnailSetResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoAbuseReport;
import com.google.api.services.youtube.model.VideoAbuseReportReasonListResponse;
import com.google.api.services.youtube.model.VideoCategoryListResponse;
import com.google.api.services.youtube.model.VideoGetRatingResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

public class YouTube extends AbstractGoogleJsonClient {
    public static final String DEFAULT_BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String DEFAULT_BATCH_PATH = "batch/youtube/v3";
    public static final String DEFAULT_ROOT_URL = "https://www.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "youtube/v3/";

    public class Activities {

        public class Insert extends YouTubeRequest<Activity> {
            private static final String REST_PATH = "activities";
            @Key
            private String part;

            protected Insert(String str, Activity activity) {
                super(YouTube.this, "POST", REST_PATH, activity, Activity.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<ActivityListResponse> {
            private static final String REST_PATH = "activities";
            @Key
            private String channelId;
            @Key
            private Boolean home;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private DateTime publishedAfter;
            @Key
            private DateTime publishedBefore;
            @Key
            private String regionCode;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, ActivityListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public Boolean getHome() {
                return this.home;
            }

            public List setHome(Boolean bool) {
                this.home = bool;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public DateTime getPublishedAfter() {
                return this.publishedAfter;
            }

            public List setPublishedAfter(DateTime dateTime) {
                this.publishedAfter = dateTime;
                return this;
            }

            public DateTime getPublishedBefore() {
                return this.publishedBefore;
            }

            public List setPublishedBefore(DateTime dateTime) {
                this.publishedBefore = dateTime;
                return this;
            }

            public String getRegionCode() {
                return this.regionCode;
            }

            public List setRegionCode(String str) {
                this.regionCode = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Activities() {
        }

        public Insert insert(String str, Activity activity) throws IOException {
            Insert insert = new Insert(str, activity);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, jsonFactory, YouTube.DEFAULT_ROOT_URL, YouTube.DEFAULT_SERVICE_PATH, httpRequestInitializer, false);
            setBatchPath(YouTube.DEFAULT_BATCH_PATH);
        }

        public YouTube build() {
            return new YouTube(this);
        }

        public Builder setRootUrl(String str) {
            return (Builder) super.setRootUrl(str);
        }

        public Builder setServicePath(String str) {
            return (Builder) super.setServicePath(str);
        }

        public Builder setBatchPath(String str) {
            return (Builder) super.setBatchPath(str);
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        public Builder setApplicationName(String str) {
            return (Builder) super.setApplicationName(str);
        }

        public Builder setSuppressPatternChecks(boolean z) {
            return (Builder) super.setSuppressPatternChecks(z);
        }

        public Builder setSuppressRequiredParameterChecks(boolean z) {
            return (Builder) super.setSuppressRequiredParameterChecks(z);
        }

        public Builder setSuppressAllChecks(boolean z) {
            return (Builder) super.setSuppressAllChecks(z);
        }

        public Builder setYouTubeRequestInitializer(YouTubeRequestInitializer youTubeRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer((GoogleClientRequestInitializer) youTubeRequestInitializer);
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
    }

    public class Captions {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "captions";
            @Key

            /* renamed from: id */
            private String f1788id;
            @Key
            private String onBehalfOf;
            @Key
            private String onBehalfOfContentOwner;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1788id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1788id;
            }

            public Delete setId(String str) {
                this.f1788id = str;
                return this;
            }

            public String getOnBehalfOf() {
                return this.onBehalfOf;
            }

            public Delete setOnBehalfOf(String str) {
                this.onBehalfOf = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Download extends YouTubeRequest<Void> {
            private static final String REST_PATH = "captions/{id}";
            @Key

            /* renamed from: id */
            private String f1789id;
            @Key
            private String onBehalfOf;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String tfmt;
            @Key
            private String tlang;

            protected Download(String str) {
                super(YouTube.this, "GET", REST_PATH, null, Void.class);
                this.f1789id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
                initializeMediaDownload();
            }

            public void executeMediaAndDownloadTo(OutputStream outputStream) throws IOException {
                super.executeMediaAndDownloadTo(outputStream);
            }

            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Download setAlt(String str) {
                return (Download) super.setAlt(str);
            }

            public Download setFields(String str) {
                return (Download) super.setFields(str);
            }

            public Download setKey(String str) {
                return (Download) super.setKey(str);
            }

            public Download setOauthToken(String str) {
                return (Download) super.setOauthToken(str);
            }

            public Download setPrettyPrint(Boolean bool) {
                return (Download) super.setPrettyPrint(bool);
            }

            public Download setQuotaUser(String str) {
                return (Download) super.setQuotaUser(str);
            }

            public Download setUserIp(String str) {
                return (Download) super.setUserIp(str);
            }

            public String getId() {
                return this.f1789id;
            }

            public Download setId(String str) {
                this.f1789id = str;
                return this;
            }

            public String getOnBehalfOf() {
                return this.onBehalfOf;
            }

            public Download setOnBehalfOf(String str) {
                this.onBehalfOf = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Download setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getTfmt() {
                return this.tfmt;
            }

            public Download setTfmt(String str) {
                this.tfmt = str;
                return this;
            }

            public String getTlang() {
                return this.tlang;
            }

            public Download setTlang(String str) {
                this.tlang = str;
                return this;
            }

            public Download set(String str, Object obj) {
                return (Download) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<Caption> {
            private static final String REST_PATH = "captions";
            @Key
            private String onBehalfOf;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;
            @Key
            private Boolean sync;

            protected Insert(String str, Caption caption) {
                super(YouTube.this, "POST", REST_PATH, caption, Caption.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            protected Insert(String str, Caption caption, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "POST", sb.toString(), caption, Caption.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOf() {
                return this.onBehalfOf;
            }

            public Insert setOnBehalfOf(String str) {
                this.onBehalfOf = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Boolean getSync() {
                return this.sync;
            }

            public Insert setSync(Boolean bool) {
                this.sync = bool;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<CaptionListResponse> {
            private static final String REST_PATH = "captions";
            @Key

            /* renamed from: id */
            private String f1790id;
            @Key
            private String onBehalfOf;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;
            @Key
            private String videoId;

            protected List(String str, String str2) {
                super(YouTube.this, "GET", REST_PATH, null, CaptionListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                this.videoId = (String) Preconditions.checkNotNull(str2, "Required parameter videoId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public List setVideoId(String str) {
                this.videoId = str;
                return this;
            }

            public String getId() {
                return this.f1790id;
            }

            public List setId(String str) {
                this.f1790id = str;
                return this;
            }

            public String getOnBehalfOf() {
                return this.onBehalfOf;
            }

            public List setOnBehalfOf(String str) {
                this.onBehalfOf = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<Caption> {
            private static final String REST_PATH = "captions";
            @Key
            private String onBehalfOf;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;
            @Key
            private Boolean sync;

            protected Update(String str, Caption caption) {
                super(YouTube.this, "PUT", REST_PATH, caption, Caption.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                checkRequiredParameter(caption, Param.CONTENT);
                checkRequiredParameter(caption.getId(), "Caption.getId()");
            }

            protected Update(String str, Caption caption, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "PUT", sb.toString(), caption, Caption.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOf() {
                return this.onBehalfOf;
            }

            public Update setOnBehalfOf(String str) {
                this.onBehalfOf = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Boolean getSync() {
                return this.sync;
            }

            public Update setSync(Boolean bool) {
                this.sync = bool;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Captions() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Download download(String str) throws IOException {
            Download download = new Download(str);
            YouTube.this.initialize(download);
            return download;
        }

        public Insert insert(String str, Caption caption) throws IOException {
            Insert insert = new Insert(str, caption);
            YouTube.this.initialize(insert);
            return insert;
        }

        public Insert insert(String str, Caption caption, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Insert insert = new Insert(str, caption, abstractInputStreamContent);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str, String str2) throws IOException {
            List list = new List(str, str2);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, Caption caption) throws IOException {
            Update update = new Update(str, caption);
            YouTube.this.initialize(update);
            return update;
        }

        public Update update(String str, Caption caption, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Update update = new Update(str, caption, abstractInputStreamContent);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class ChannelBanners {

        public class Insert extends YouTubeRequest<ChannelBannerResource> {
            private static final String REST_PATH = "channelBanners/insert";
            @Key
            private String channelId;
            @Key
            private String onBehalfOfContentOwner;

            protected Insert(ChannelBannerResource channelBannerResource) {
                super(YouTube.this, "POST", REST_PATH, channelBannerResource, ChannelBannerResource.class);
            }

            protected Insert(ChannelBannerResource channelBannerResource, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "POST", sb.toString(), channelBannerResource, ChannelBannerResource.class);
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getChannelId() {
                return this.channelId;
            }

            public Insert setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public ChannelBanners() {
        }

        public Insert insert(ChannelBannerResource channelBannerResource) throws IOException {
            Insert insert = new Insert(channelBannerResource);
            YouTube.this.initialize(insert);
            return insert;
        }

        public Insert insert(ChannelBannerResource channelBannerResource, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Insert insert = new Insert(channelBannerResource, abstractInputStreamContent);
            YouTube.this.initialize(insert);
            return insert;
        }
    }

    public class ChannelSections {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "channelSections";
            @Key

            /* renamed from: id */
            private String f1791id;
            @Key
            private String onBehalfOfContentOwner;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1791id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1791id;
            }

            public Delete setId(String str) {
                this.f1791id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<ChannelSection> {
            private static final String REST_PATH = "channelSections";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Insert(String str, ChannelSection channelSection) {
                super(YouTube.this, "POST", REST_PATH, channelSection, ChannelSection.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Insert setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<ChannelSectionListResponse> {
            private static final String REST_PATH = "channelSections";
            @Key
            private String channelId;
            @Key

            /* renamed from: hl */
            private String f1792hl;
            @Key

            /* renamed from: id */
            private String f1793id;
            @Key
            private Boolean mine;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, ChannelSectionListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getHl() {
                return this.f1792hl;
            }

            public List setHl(String str) {
                this.f1792hl = str;
                return this;
            }

            public String getId() {
                return this.f1793id;
            }

            public List setId(String str) {
                this.f1793id = str;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<ChannelSection> {
            private static final String REST_PATH = "channelSections";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Update(String str, ChannelSection channelSection) {
                super(YouTube.this, "PUT", REST_PATH, channelSection, ChannelSection.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public ChannelSections() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, ChannelSection channelSection) throws IOException {
            Insert insert = new Insert(str, channelSection);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, ChannelSection channelSection) throws IOException {
            Update update = new Update(str, channelSection);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class Channels {

        public class List extends YouTubeRequest<ChannelListResponse> {
            private static final String REST_PATH = "channels";
            @Key
            private String categoryId;
            @Key
            private String forUsername;
            @Key

            /* renamed from: hl */
            private String f1794hl;
            @Key

            /* renamed from: id */
            private String f1795id;
            @Key
            private Boolean managedByMe;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private Boolean mySubscribers;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, ChannelListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getCategoryId() {
                return this.categoryId;
            }

            public List setCategoryId(String str) {
                this.categoryId = str;
                return this;
            }

            public String getForUsername() {
                return this.forUsername;
            }

            public List setForUsername(String str) {
                this.forUsername = str;
                return this;
            }

            public String getHl() {
                return this.f1794hl;
            }

            public List setHl(String str) {
                this.f1794hl = str;
                return this;
            }

            public String getId() {
                return this.f1795id;
            }

            public List setId(String str) {
                this.f1795id = str;
                return this;
            }

            public Boolean getManagedByMe() {
                return this.managedByMe;
            }

            public List setManagedByMe(Boolean bool) {
                this.managedByMe = bool;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public Boolean getMySubscribers() {
                return this.mySubscribers;
            }

            public List setMySubscribers(Boolean bool) {
                this.mySubscribers = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<Channel> {
            private static final String REST_PATH = "channels";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Update(String str, Channel channel) {
                super(YouTube.this, "PUT", REST_PATH, channel, Channel.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Channels() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, Channel channel) throws IOException {
            Update update = new Update(str, channel);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class CommentThreads {

        public class Insert extends YouTubeRequest<CommentThread> {
            private static final String REST_PATH = "commentThreads";
            @Key
            private String part;

            protected Insert(String str, CommentThread commentThread) {
                super(YouTube.this, "POST", REST_PATH, commentThread, CommentThread.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<CommentThreadListResponse> {
            private static final String REST_PATH = "commentThreads";
            @Key
            private String allThreadsRelatedToChannelId;
            @Key
            private String channelId;
            @Key

            /* renamed from: id */
            private String f1796id;
            @Key
            private Long maxResults;
            @Key
            private String moderationStatus;
            @Key
            private String order;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private String searchTerms;
            @Key
            private String textFormat;
            @Key
            private String videoId;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, CommentThreadListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getAllThreadsRelatedToChannelId() {
                return this.allThreadsRelatedToChannelId;
            }

            public List setAllThreadsRelatedToChannelId(String str) {
                this.allThreadsRelatedToChannelId = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getId() {
                return this.f1796id;
            }

            public List setId(String str) {
                this.f1796id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getModerationStatus() {
                return this.moderationStatus;
            }

            public List setModerationStatus(String str) {
                this.moderationStatus = str;
                return this;
            }

            public String getOrder() {
                return this.order;
            }

            public List setOrder(String str) {
                this.order = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getSearchTerms() {
                return this.searchTerms;
            }

            public List setSearchTerms(String str) {
                this.searchTerms = str;
                return this;
            }

            public String getTextFormat() {
                return this.textFormat;
            }

            public List setTextFormat(String str) {
                this.textFormat = str;
                return this;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public List setVideoId(String str) {
                this.videoId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<CommentThread> {
            private static final String REST_PATH = "commentThreads";
            @Key
            private String part;

            protected Update(String str, CommentThread commentThread) {
                super(YouTube.this, "PUT", REST_PATH, commentThread, CommentThread.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public CommentThreads() {
        }

        public Insert insert(String str, CommentThread commentThread) throws IOException {
            Insert insert = new Insert(str, commentThread);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, CommentThread commentThread) throws IOException {
            Update update = new Update(str, commentThread);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class Comments {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "comments";
            @Key

            /* renamed from: id */
            private String f1797id;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1797id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1797id;
            }

            public Delete setId(String str) {
                this.f1797id = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<Comment> {
            private static final String REST_PATH = "comments";
            @Key
            private String part;

            protected Insert(String str, Comment comment) {
                super(YouTube.this, "POST", REST_PATH, comment, Comment.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<CommentListResponse> {
            private static final String REST_PATH = "comments";
            @Key

            /* renamed from: id */
            private String f1798id;
            @Key
            private Long maxResults;
            @Key
            private String pageToken;
            @Key
            private String parentId;
            @Key
            private String part;
            @Key
            private String textFormat;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, CommentListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getId() {
                return this.f1798id;
            }

            public List setId(String str) {
                this.f1798id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getParentId() {
                return this.parentId;
            }

            public List setParentId(String str) {
                this.parentId = str;
                return this;
            }

            public String getTextFormat() {
                return this.textFormat;
            }

            public List setTextFormat(String str) {
                this.textFormat = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class MarkAsSpam extends YouTubeRequest<Void> {
            private static final String REST_PATH = "comments/markAsSpam";
            @Key

            /* renamed from: id */
            private String f1799id;

            protected MarkAsSpam(String str) {
                super(YouTube.this, "POST", REST_PATH, null, Void.class);
                this.f1799id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public MarkAsSpam setAlt(String str) {
                return (MarkAsSpam) super.setAlt(str);
            }

            public MarkAsSpam setFields(String str) {
                return (MarkAsSpam) super.setFields(str);
            }

            public MarkAsSpam setKey(String str) {
                return (MarkAsSpam) super.setKey(str);
            }

            public MarkAsSpam setOauthToken(String str) {
                return (MarkAsSpam) super.setOauthToken(str);
            }

            public MarkAsSpam setPrettyPrint(Boolean bool) {
                return (MarkAsSpam) super.setPrettyPrint(bool);
            }

            public MarkAsSpam setQuotaUser(String str) {
                return (MarkAsSpam) super.setQuotaUser(str);
            }

            public MarkAsSpam setUserIp(String str) {
                return (MarkAsSpam) super.setUserIp(str);
            }

            public String getId() {
                return this.f1799id;
            }

            public MarkAsSpam setId(String str) {
                this.f1799id = str;
                return this;
            }

            public MarkAsSpam set(String str, Object obj) {
                return (MarkAsSpam) super.set(str, obj);
            }
        }

        public class SetModerationStatus extends YouTubeRequest<Void> {
            private static final String REST_PATH = "comments/setModerationStatus";
            @Key
            private Boolean banAuthor;
            @Key

            /* renamed from: id */
            private String f1800id;
            @Key
            private String moderationStatus;

            protected SetModerationStatus(String str, String str2) {
                super(YouTube.this, "POST", REST_PATH, null, Void.class);
                this.f1800id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
                this.moderationStatus = (String) Preconditions.checkNotNull(str2, "Required parameter moderationStatus must be specified.");
            }

            public SetModerationStatus setAlt(String str) {
                return (SetModerationStatus) super.setAlt(str);
            }

            public SetModerationStatus setFields(String str) {
                return (SetModerationStatus) super.setFields(str);
            }

            public SetModerationStatus setKey(String str) {
                return (SetModerationStatus) super.setKey(str);
            }

            public SetModerationStatus setOauthToken(String str) {
                return (SetModerationStatus) super.setOauthToken(str);
            }

            public SetModerationStatus setPrettyPrint(Boolean bool) {
                return (SetModerationStatus) super.setPrettyPrint(bool);
            }

            public SetModerationStatus setQuotaUser(String str) {
                return (SetModerationStatus) super.setQuotaUser(str);
            }

            public SetModerationStatus setUserIp(String str) {
                return (SetModerationStatus) super.setUserIp(str);
            }

            public String getId() {
                return this.f1800id;
            }

            public SetModerationStatus setId(String str) {
                this.f1800id = str;
                return this;
            }

            public String getModerationStatus() {
                return this.moderationStatus;
            }

            public SetModerationStatus setModerationStatus(String str) {
                this.moderationStatus = str;
                return this;
            }

            public Boolean getBanAuthor() {
                return this.banAuthor;
            }

            public SetModerationStatus setBanAuthor(Boolean bool) {
                this.banAuthor = bool;
                return this;
            }

            public boolean isBanAuthor() {
                Boolean bool = this.banAuthor;
                if (bool == null || bool == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.banAuthor.booleanValue();
            }

            public SetModerationStatus set(String str, Object obj) {
                return (SetModerationStatus) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<Comment> {
            private static final String REST_PATH = "comments";
            @Key
            private String part;

            protected Update(String str, Comment comment) {
                super(YouTube.this, "PUT", REST_PATH, comment, Comment.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Comments() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, Comment comment) throws IOException {
            Insert insert = new Insert(str, comment);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public MarkAsSpam markAsSpam(String str) throws IOException {
            MarkAsSpam markAsSpam = new MarkAsSpam(str);
            YouTube.this.initialize(markAsSpam);
            return markAsSpam;
        }

        public SetModerationStatus setModerationStatus(String str, String str2) throws IOException {
            SetModerationStatus setModerationStatus = new SetModerationStatus(str, str2);
            YouTube.this.initialize(setModerationStatus);
            return setModerationStatus;
        }

        public Update update(String str, Comment comment) throws IOException {
            Update update = new Update(str, comment);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class GuideCategories {

        public class List extends YouTubeRequest<GuideCategoryListResponse> {
            private static final String REST_PATH = "guideCategories";
            @Key

            /* renamed from: hl */
            private String f1801hl;
            @Key

            /* renamed from: id */
            private String f1802id;
            @Key
            private String part;
            @Key
            private String regionCode;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, GuideCategoryListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1801hl;
            }

            public List setHl(String str) {
                this.f1801hl = str;
                return this;
            }

            public String getId() {
                return this.f1802id;
            }

            public List setId(String str) {
                this.f1802id = str;
                return this;
            }

            public String getRegionCode() {
                return this.regionCode;
            }

            public List setRegionCode(String str) {
                this.regionCode = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public GuideCategories() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class I18nLanguages {

        public class List extends YouTubeRequest<I18nLanguageListResponse> {
            private static final String REST_PATH = "i18nLanguages";
            @Key

            /* renamed from: hl */
            private String f1803hl;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, I18nLanguageListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1803hl;
            }

            public List setHl(String str) {
                this.f1803hl = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public I18nLanguages() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class I18nRegions {

        public class List extends YouTubeRequest<I18nRegionListResponse> {
            private static final String REST_PATH = "i18nRegions";
            @Key

            /* renamed from: hl */
            private String f1804hl;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, I18nRegionListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1804hl;
            }

            public List setHl(String str) {
                this.f1804hl = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public I18nRegions() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class LiveBroadcasts {

        public class Bind extends YouTubeRequest<LiveBroadcast> {
            private static final String REST_PATH = "liveBroadcasts/bind";
            @Key

            /* renamed from: id */
            private String f1805id;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;
            @Key
            private String streamId;

            protected Bind(String str, String str2) {
                super(YouTube.this, "POST", REST_PATH, null, LiveBroadcast.class);
                this.f1805id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
                this.part = (String) Preconditions.checkNotNull(str2, "Required parameter part must be specified.");
            }

            public Bind setAlt(String str) {
                return (Bind) super.setAlt(str);
            }

            public Bind setFields(String str) {
                return (Bind) super.setFields(str);
            }

            public Bind setKey(String str) {
                return (Bind) super.setKey(str);
            }

            public Bind setOauthToken(String str) {
                return (Bind) super.setOauthToken(str);
            }

            public Bind setPrettyPrint(Boolean bool) {
                return (Bind) super.setPrettyPrint(bool);
            }

            public Bind setQuotaUser(String str) {
                return (Bind) super.setQuotaUser(str);
            }

            public Bind setUserIp(String str) {
                return (Bind) super.setUserIp(str);
            }

            public String getId() {
                return this.f1805id;
            }

            public Bind setId(String str) {
                this.f1805id = str;
                return this;
            }

            public String getPart() {
                return this.part;
            }

            public Bind setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Bind setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Bind setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public String getStreamId() {
                return this.streamId;
            }

            public Bind setStreamId(String str) {
                this.streamId = str;
                return this;
            }

            public Bind set(String str, Object obj) {
                return (Bind) super.set(str, obj);
            }
        }

        public class Control extends YouTubeRequest<LiveBroadcast> {
            private static final String REST_PATH = "liveBroadcasts/control";
            @Key
            private Boolean displaySlate;
            @Key

            /* renamed from: id */
            private String f1806id;
            @Key
            private BigInteger offsetTimeMs;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;
            @Key
            private DateTime walltime;

            protected Control(String str, String str2) {
                super(YouTube.this, "POST", REST_PATH, null, LiveBroadcast.class);
                this.f1806id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
                this.part = (String) Preconditions.checkNotNull(str2, "Required parameter part must be specified.");
            }

            public Control setAlt(String str) {
                return (Control) super.setAlt(str);
            }

            public Control setFields(String str) {
                return (Control) super.setFields(str);
            }

            public Control setKey(String str) {
                return (Control) super.setKey(str);
            }

            public Control setOauthToken(String str) {
                return (Control) super.setOauthToken(str);
            }

            public Control setPrettyPrint(Boolean bool) {
                return (Control) super.setPrettyPrint(bool);
            }

            public Control setQuotaUser(String str) {
                return (Control) super.setQuotaUser(str);
            }

            public Control setUserIp(String str) {
                return (Control) super.setUserIp(str);
            }

            public String getId() {
                return this.f1806id;
            }

            public Control setId(String str) {
                this.f1806id = str;
                return this;
            }

            public String getPart() {
                return this.part;
            }

            public Control setPart(String str) {
                this.part = str;
                return this;
            }

            public Boolean getDisplaySlate() {
                return this.displaySlate;
            }

            public Control setDisplaySlate(Boolean bool) {
                this.displaySlate = bool;
                return this;
            }

            public BigInteger getOffsetTimeMs() {
                return this.offsetTimeMs;
            }

            public Control setOffsetTimeMs(BigInteger bigInteger) {
                this.offsetTimeMs = bigInteger;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Control setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Control setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public DateTime getWalltime() {
                return this.walltime;
            }

            public Control setWalltime(DateTime dateTime) {
                this.walltime = dateTime;
                return this;
            }

            public Control set(String str, Object obj) {
                return (Control) super.set(str, obj);
            }
        }

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "liveBroadcasts";
            @Key

            /* renamed from: id */
            private String f1807id;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1807id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1807id;
            }

            public Delete setId(String str) {
                this.f1807id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Delete setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<LiveBroadcast> {
            private static final String REST_PATH = "liveBroadcasts";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Insert(String str, LiveBroadcast liveBroadcast) {
                super(YouTube.this, "POST", REST_PATH, liveBroadcast, LiveBroadcast.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Insert setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<LiveBroadcastListResponse> {
            private static final String REST_PATH = "liveBroadcasts";
            @Key
            private String broadcastStatus;
            @Key
            private String broadcastType;
            @Key

            /* renamed from: id */
            private String f1808id;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, LiveBroadcastListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getBroadcastStatus() {
                return this.broadcastStatus;
            }

            public List setBroadcastStatus(String str) {
                this.broadcastStatus = str;
                return this;
            }

            public String getBroadcastType() {
                return this.broadcastType;
            }

            public List setBroadcastType(String str) {
                this.broadcastType = str;
                return this;
            }

            public String getId() {
                return this.f1808id;
            }

            public List setId(String str) {
                this.f1808id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public List setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Transition extends YouTubeRequest<LiveBroadcast> {
            private static final String REST_PATH = "liveBroadcasts/transition";
            @Key
            private String broadcastStatus;
            @Key

            /* renamed from: id */
            private String f1809id;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Transition(String str, String str2, String str3) {
                super(YouTube.this, "POST", REST_PATH, null, LiveBroadcast.class);
                this.broadcastStatus = (String) Preconditions.checkNotNull(str, "Required parameter broadcastStatus must be specified.");
                this.f1809id = (String) Preconditions.checkNotNull(str2, "Required parameter id must be specified.");
                this.part = (String) Preconditions.checkNotNull(str3, "Required parameter part must be specified.");
            }

            public Transition setAlt(String str) {
                return (Transition) super.setAlt(str);
            }

            public Transition setFields(String str) {
                return (Transition) super.setFields(str);
            }

            public Transition setKey(String str) {
                return (Transition) super.setKey(str);
            }

            public Transition setOauthToken(String str) {
                return (Transition) super.setOauthToken(str);
            }

            public Transition setPrettyPrint(Boolean bool) {
                return (Transition) super.setPrettyPrint(bool);
            }

            public Transition setQuotaUser(String str) {
                return (Transition) super.setQuotaUser(str);
            }

            public Transition setUserIp(String str) {
                return (Transition) super.setUserIp(str);
            }

            public String getBroadcastStatus() {
                return this.broadcastStatus;
            }

            public Transition setBroadcastStatus(String str) {
                this.broadcastStatus = str;
                return this;
            }

            public String getId() {
                return this.f1809id;
            }

            public Transition setId(String str) {
                this.f1809id = str;
                return this;
            }

            public String getPart() {
                return this.part;
            }

            public Transition setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Transition setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Transition setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Transition set(String str, Object obj) {
                return (Transition) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<LiveBroadcast> {
            private static final String REST_PATH = "liveBroadcasts";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Update(String str, LiveBroadcast liveBroadcast) {
                super(YouTube.this, "PUT", REST_PATH, liveBroadcast, LiveBroadcast.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                checkRequiredParameter(liveBroadcast, Param.CONTENT);
                checkRequiredParameter(liveBroadcast.getId(), "LiveBroadcast.getId()");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Update setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public LiveBroadcasts() {
        }

        public Bind bind(String str, String str2) throws IOException {
            Bind bind = new Bind(str, str2);
            YouTube.this.initialize(bind);
            return bind;
        }

        public Control control(String str, String str2) throws IOException {
            Control control = new Control(str, str2);
            YouTube.this.initialize(control);
            return control;
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, LiveBroadcast liveBroadcast) throws IOException {
            Insert insert = new Insert(str, liveBroadcast);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Transition transition(String str, String str2, String str3) throws IOException {
            Transition transition = new Transition(str, str2, str3);
            YouTube.this.initialize(transition);
            return transition;
        }

        public Update update(String str, LiveBroadcast liveBroadcast) throws IOException {
            Update update = new Update(str, liveBroadcast);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class LiveChatBans {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "liveChat/bans";
            @Key

            /* renamed from: id */
            private String f1810id;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1810id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1810id;
            }

            public Delete setId(String str) {
                this.f1810id = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<LiveChatBan> {
            private static final String REST_PATH = "liveChat/bans";
            @Key
            private String part;

            protected Insert(String str, LiveChatBan liveChatBan) {
                super(YouTube.this, "POST", REST_PATH, liveChatBan, LiveChatBan.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public LiveChatBans() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, LiveChatBan liveChatBan) throws IOException {
            Insert insert = new Insert(str, liveChatBan);
            YouTube.this.initialize(insert);
            return insert;
        }
    }

    public class LiveChatMessages {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "liveChat/messages";
            @Key

            /* renamed from: id */
            private String f1811id;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1811id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1811id;
            }

            public Delete setId(String str) {
                this.f1811id = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<LiveChatMessage> {
            private static final String REST_PATH = "liveChat/messages";
            @Key
            private String part;

            protected Insert(String str, LiveChatMessage liveChatMessage) {
                super(YouTube.this, "POST", REST_PATH, liveChatMessage, LiveChatMessage.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<LiveChatMessageListResponse> {
            private static final String REST_PATH = "liveChat/messages";
            @Key

            /* renamed from: hl */
            private String f1812hl;
            @Key
            private String liveChatId;
            @Key
            private Long maxResults;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private Long profileImageSize;

            protected List(String str, String str2) {
                super(YouTube.this, "GET", REST_PATH, null, LiveChatMessageListResponse.class);
                this.liveChatId = (String) Preconditions.checkNotNull(str, "Required parameter liveChatId must be specified.");
                this.part = (String) Preconditions.checkNotNull(str2, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getLiveChatId() {
                return this.liveChatId;
            }

            public List setLiveChatId(String str) {
                this.liveChatId = str;
                return this;
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1812hl;
            }

            public List setHl(String str) {
                this.f1812hl = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Long getProfileImageSize() {
                return this.profileImageSize;
            }

            public List setProfileImageSize(Long l) {
                this.profileImageSize = l;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public LiveChatMessages() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, LiveChatMessage liveChatMessage) throws IOException {
            Insert insert = new Insert(str, liveChatMessage);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str, String str2) throws IOException {
            List list = new List(str, str2);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class LiveChatModerators {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "liveChat/moderators";
            @Key

            /* renamed from: id */
            private String f1813id;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1813id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1813id;
            }

            public Delete setId(String str) {
                this.f1813id = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<LiveChatModerator> {
            private static final String REST_PATH = "liveChat/moderators";
            @Key
            private String part;

            protected Insert(String str, LiveChatModerator liveChatModerator) {
                super(YouTube.this, "POST", REST_PATH, liveChatModerator, LiveChatModerator.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<LiveChatModeratorListResponse> {
            private static final String REST_PATH = "liveChat/moderators";
            @Key
            private String liveChatId;
            @Key
            private Long maxResults;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str, String str2) {
                super(YouTube.this, "GET", REST_PATH, null, LiveChatModeratorListResponse.class);
                this.liveChatId = (String) Preconditions.checkNotNull(str, "Required parameter liveChatId must be specified.");
                this.part = (String) Preconditions.checkNotNull(str2, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getLiveChatId() {
                return this.liveChatId;
            }

            public List setLiveChatId(String str) {
                this.liveChatId = str;
                return this;
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public LiveChatModerators() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, LiveChatModerator liveChatModerator) throws IOException {
            Insert insert = new Insert(str, liveChatModerator);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str, String str2) throws IOException {
            List list = new List(str, str2);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class LiveStreams {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "liveStreams";
            @Key

            /* renamed from: id */
            private String f1814id;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1814id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1814id;
            }

            public Delete setId(String str) {
                this.f1814id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Delete setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<LiveStream> {
            private static final String REST_PATH = "liveStreams";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Insert(String str, LiveStream liveStream) {
                super(YouTube.this, "POST", REST_PATH, liveStream, LiveStream.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Insert setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<LiveStreamListResponse> {
            private static final String REST_PATH = "liveStreams";
            @Key

            /* renamed from: id */
            private String f1815id;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, LiveStreamListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getId() {
                return this.f1815id;
            }

            public List setId(String str) {
                this.f1815id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public List setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<LiveStream> {
            private static final String REST_PATH = "liveStreams";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Update(String str, LiveStream liveStream) {
                super(YouTube.this, "PUT", REST_PATH, liveStream, LiveStream.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                checkRequiredParameter(liveStream, Param.CONTENT);
                checkRequiredParameter(liveStream.getId(), "LiveStream.getId()");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Update setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public LiveStreams() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, LiveStream liveStream) throws IOException {
            Insert insert = new Insert(str, liveStream);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, LiveStream liveStream) throws IOException {
            Update update = new Update(str, liveStream);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class PlaylistItems {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "playlistItems";
            @Key

            /* renamed from: id */
            private String f1816id;
            @Key
            private String onBehalfOfContentOwner;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1816id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1816id;
            }

            public Delete setId(String str) {
                this.f1816id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<PlaylistItem> {
            private static final String REST_PATH = "playlistItems";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Insert(String str, PlaylistItem playlistItem) {
                super(YouTube.this, "POST", REST_PATH, playlistItem, PlaylistItem.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<PlaylistItemListResponse> {
            private static final String REST_PATH = "playlistItems";
            @Key

            /* renamed from: id */
            private String f1817id;
            @Key
            private Long maxResults;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private String playlistId;
            @Key
            private String videoId;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, PlaylistItemListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getId() {
                return this.f1817id;
            }

            public List setId(String str) {
                this.f1817id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getPlaylistId() {
                return this.playlistId;
            }

            public List setPlaylistId(String str) {
                this.playlistId = str;
                return this;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public List setVideoId(String str) {
                this.videoId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<PlaylistItem> {
            private static final String REST_PATH = "playlistItems";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Update(String str, PlaylistItem playlistItem) {
                super(YouTube.this, "PUT", REST_PATH, playlistItem, PlaylistItem.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public PlaylistItems() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, PlaylistItem playlistItem) throws IOException {
            Insert insert = new Insert(str, playlistItem);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, PlaylistItem playlistItem) throws IOException {
            Update update = new Update(str, playlistItem);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class Playlists {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "playlists";
            @Key

            /* renamed from: id */
            private String f1818id;
            @Key
            private String onBehalfOfContentOwner;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1818id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1818id;
            }

            public Delete setId(String str) {
                this.f1818id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<Playlist> {
            private static final String REST_PATH = "playlists";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;

            protected Insert(String str, Playlist playlist) {
                super(YouTube.this, "POST", REST_PATH, playlist, Playlist.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Insert setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<PlaylistListResponse> {
            private static final String REST_PATH = "playlists";
            @Key
            private String channelId;
            @Key

            /* renamed from: hl */
            private String f1819hl;
            @Key

            /* renamed from: id */
            private String f1820id;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, PlaylistListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getHl() {
                return this.f1819hl;
            }

            public List setHl(String str) {
                this.f1819hl = str;
                return this;
            }

            public String getId() {
                return this.f1820id;
            }

            public List setId(String str) {
                this.f1820id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public List setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<Playlist> {
            private static final String REST_PATH = "playlists";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Update(String str, Playlist playlist) {
                super(YouTube.this, "PUT", REST_PATH, playlist, Playlist.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Playlists() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, Playlist playlist) throws IOException {
            Insert insert = new Insert(str, playlist);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Update update(String str, Playlist playlist) throws IOException {
            Update update = new Update(str, playlist);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class Search {

        public class List extends YouTubeRequest<SearchListResponse> {
            private static final String REST_PATH = "search";
            @Key
            private String channelId;
            @Key
            private String channelType;
            @Key
            private String eventType;
            @Key
            private Boolean forContentOwner;
            @Key
            private Boolean forDeveloper;
            @Key
            private Boolean forMine;
            @Key
            private String location;
            @Key
            private String locationRadius;
            @Key
            private Long maxResults;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String order;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private DateTime publishedAfter;
            @Key
            private DateTime publishedBefore;
            @Key

            /* renamed from: q */
            private String f1821q;
            @Key
            private String regionCode;
            @Key
            private String relatedToVideoId;
            @Key
            private String relevanceLanguage;
            @Key
            private String safeSearch;
            @Key
            private String topicId;
            @Key
            private String type;
            @Key
            private String videoCaption;
            @Key
            private String videoCategoryId;
            @Key
            private String videoDefinition;
            @Key
            private String videoDimension;
            @Key
            private String videoDuration;
            @Key
            private String videoEmbeddable;
            @Key
            private String videoLicense;
            @Key
            private String videoSyndicated;
            @Key
            private String videoType;

            protected List(String str) {
                super(YouTube.this, "GET", "search", null, SearchListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getChannelType() {
                return this.channelType;
            }

            public List setChannelType(String str) {
                this.channelType = str;
                return this;
            }

            public String getEventType() {
                return this.eventType;
            }

            public List setEventType(String str) {
                this.eventType = str;
                return this;
            }

            public Boolean getForContentOwner() {
                return this.forContentOwner;
            }

            public List setForContentOwner(Boolean bool) {
                this.forContentOwner = bool;
                return this;
            }

            public Boolean getForDeveloper() {
                return this.forDeveloper;
            }

            public List setForDeveloper(Boolean bool) {
                this.forDeveloper = bool;
                return this;
            }

            public Boolean getForMine() {
                return this.forMine;
            }

            public List setForMine(Boolean bool) {
                this.forMine = bool;
                return this;
            }

            public String getLocation() {
                return this.location;
            }

            public List setLocation(String str) {
                this.location = str;
                return this;
            }

            public String getLocationRadius() {
                return this.locationRadius;
            }

            public List setLocationRadius(String str) {
                this.locationRadius = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOrder() {
                return this.order;
            }

            public List setOrder(String str) {
                this.order = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public DateTime getPublishedAfter() {
                return this.publishedAfter;
            }

            public List setPublishedAfter(DateTime dateTime) {
                this.publishedAfter = dateTime;
                return this;
            }

            public DateTime getPublishedBefore() {
                return this.publishedBefore;
            }

            public List setPublishedBefore(DateTime dateTime) {
                this.publishedBefore = dateTime;
                return this;
            }

            public String getQ() {
                return this.f1821q;
            }

            public List setQ(String str) {
                this.f1821q = str;
                return this;
            }

            public String getRegionCode() {
                return this.regionCode;
            }

            public List setRegionCode(String str) {
                this.regionCode = str;
                return this;
            }

            public String getRelatedToVideoId() {
                return this.relatedToVideoId;
            }

            public List setRelatedToVideoId(String str) {
                this.relatedToVideoId = str;
                return this;
            }

            public String getRelevanceLanguage() {
                return this.relevanceLanguage;
            }

            public List setRelevanceLanguage(String str) {
                this.relevanceLanguage = str;
                return this;
            }

            public String getSafeSearch() {
                return this.safeSearch;
            }

            public List setSafeSearch(String str) {
                this.safeSearch = str;
                return this;
            }

            public String getTopicId() {
                return this.topicId;
            }

            public List setTopicId(String str) {
                this.topicId = str;
                return this;
            }

            public String getType() {
                return this.type;
            }

            public List setType(String str) {
                this.type = str;
                return this;
            }

            public String getVideoCaption() {
                return this.videoCaption;
            }

            public List setVideoCaption(String str) {
                this.videoCaption = str;
                return this;
            }

            public String getVideoCategoryId() {
                return this.videoCategoryId;
            }

            public List setVideoCategoryId(String str) {
                this.videoCategoryId = str;
                return this;
            }

            public String getVideoDefinition() {
                return this.videoDefinition;
            }

            public List setVideoDefinition(String str) {
                this.videoDefinition = str;
                return this;
            }

            public String getVideoDimension() {
                return this.videoDimension;
            }

            public List setVideoDimension(String str) {
                this.videoDimension = str;
                return this;
            }

            public String getVideoDuration() {
                return this.videoDuration;
            }

            public List setVideoDuration(String str) {
                this.videoDuration = str;
                return this;
            }

            public String getVideoEmbeddable() {
                return this.videoEmbeddable;
            }

            public List setVideoEmbeddable(String str) {
                this.videoEmbeddable = str;
                return this;
            }

            public String getVideoLicense() {
                return this.videoLicense;
            }

            public List setVideoLicense(String str) {
                this.videoLicense = str;
                return this;
            }

            public String getVideoSyndicated() {
                return this.videoSyndicated;
            }

            public List setVideoSyndicated(String str) {
                this.videoSyndicated = str;
                return this;
            }

            public String getVideoType() {
                return this.videoType;
            }

            public List setVideoType(String str) {
                this.videoType = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Search() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class Sponsors {

        public class List extends YouTubeRequest<SponsorListResponse> {
            private static final String REST_PATH = "sponsors";
            @Key
            private String filter;
            @Key
            private Long maxResults;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, SponsorListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getFilter() {
                return this.filter;
            }

            public List setFilter(String str) {
                this.filter = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Sponsors() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class Subscriptions {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "subscriptions";
            @Key

            /* renamed from: id */
            private String f1822id;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", "subscriptions", null, Void.class);
                this.f1822id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1822id;
            }

            public Delete setId(String str) {
                this.f1822id = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<Subscription> {
            private static final String REST_PATH = "subscriptions";
            @Key
            private String part;

            protected Insert(String str, Subscription subscription) {
                super(YouTube.this, "POST", "subscriptions", subscription, Subscription.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<SubscriptionListResponse> {
            private static final String REST_PATH = "subscriptions";
            @Key
            private String channelId;
            @Key
            private String forChannelId;
            @Key

            /* renamed from: id */
            private String f1823id;
            @Key
            private Long maxResults;
            @Key
            private Boolean mine;
            @Key
            private Boolean myRecentSubscribers;
            @Key
            private Boolean mySubscribers;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String order;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", "subscriptions", null, SubscriptionListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public List setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getForChannelId() {
                return this.forChannelId;
            }

            public List setForChannelId(String str) {
                this.forChannelId = str;
                return this;
            }

            public String getId() {
                return this.f1823id;
            }

            public List setId(String str) {
                this.f1823id = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Boolean getMine() {
                return this.mine;
            }

            public List setMine(Boolean bool) {
                this.mine = bool;
                return this;
            }

            public Boolean getMyRecentSubscribers() {
                return this.myRecentSubscribers;
            }

            public List setMyRecentSubscribers(Boolean bool) {
                this.myRecentSubscribers = bool;
                return this;
            }

            public Boolean getMySubscribers() {
                return this.mySubscribers;
            }

            public List setMySubscribers(Boolean bool) {
                this.mySubscribers = bool;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public List setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public String getOrder() {
                return this.order;
            }

            public List setOrder(String str) {
                this.order = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Subscriptions() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public Insert insert(String str, Subscription subscription) throws IOException {
            Insert insert = new Insert(str, subscription);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class SuperChatEvents {

        public class List extends YouTubeRequest<SuperChatEventListResponse> {
            private static final String REST_PATH = "superChatEvents";
            @Key

            /* renamed from: hl */
            private String f1824hl;
            @Key
            private Long maxResults;
            @Key
            private String pageToken;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, SuperChatEventListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1824hl;
            }

            public List setHl(String str) {
                this.f1824hl = str;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public SuperChatEvents() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class Thumbnails {

        public class Set extends YouTubeRequest<ThumbnailSetResponse> {
            private static final String REST_PATH = "thumbnails/set";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String videoId;

            protected Set(String str) {
                super(YouTube.this, "POST", REST_PATH, null, ThumbnailSetResponse.class);
                this.videoId = (String) Preconditions.checkNotNull(str, "Required parameter videoId must be specified.");
            }

            protected Set(String str, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "POST", sb.toString(), null, ThumbnailSetResponse.class);
                this.videoId = (String) Preconditions.checkNotNull(str, "Required parameter videoId must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Set setAlt(String str) {
                return (Set) super.setAlt(str);
            }

            public Set setFields(String str) {
                return (Set) super.setFields(str);
            }

            public Set setKey(String str) {
                return (Set) super.setKey(str);
            }

            public Set setOauthToken(String str) {
                return (Set) super.setOauthToken(str);
            }

            public Set setPrettyPrint(Boolean bool) {
                return (Set) super.setPrettyPrint(bool);
            }

            public Set setQuotaUser(String str) {
                return (Set) super.setQuotaUser(str);
            }

            public Set setUserIp(String str) {
                return (Set) super.setUserIp(str);
            }

            public String getVideoId() {
                return this.videoId;
            }

            public Set setVideoId(String str) {
                this.videoId = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Set setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Set set(String str, Object obj) {
                return (Set) super.set(str, obj);
            }
        }

        public Thumbnails() {
        }

        public Set set(String str) throws IOException {
            Set set = new Set(str);
            YouTube.this.initialize(set);
            return set;
        }

        public Set set(String str, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Set set = new Set(str, abstractInputStreamContent);
            YouTube.this.initialize(set);
            return set;
        }
    }

    public class VideoAbuseReportReasons {

        public class List extends YouTubeRequest<VideoAbuseReportReasonListResponse> {
            private static final String REST_PATH = "videoAbuseReportReasons";
            @Key

            /* renamed from: hl */
            private String f1825hl;
            @Key
            private String part;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, VideoAbuseReportReasonListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1825hl;
            }

            public List setHl(String str) {
                this.f1825hl = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public VideoAbuseReportReasons() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class VideoCategories {

        public class List extends YouTubeRequest<VideoCategoryListResponse> {
            private static final String REST_PATH = "videoCategories";
            @Key

            /* renamed from: hl */
            private String f1826hl;
            @Key

            /* renamed from: id */
            private String f1827id;
            @Key
            private String part;
            @Key
            private String regionCode;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, VideoCategoryListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getHl() {
                return this.f1826hl;
            }

            public List setHl(String str) {
                this.f1826hl = str;
                return this;
            }

            public String getId() {
                return this.f1827id;
            }

            public List setId(String str) {
                this.f1827id = str;
                return this;
            }

            public String getRegionCode() {
                return this.regionCode;
            }

            public List setRegionCode(String str) {
                this.regionCode = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public VideoCategories() {
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }
    }

    public class Videos {

        public class Delete extends YouTubeRequest<Void> {
            private static final String REST_PATH = "videos";
            @Key

            /* renamed from: id */
            private String f1828id;
            @Key
            private String onBehalfOfContentOwner;

            protected Delete(String str) {
                super(YouTube.this, "DELETE", REST_PATH, null, Void.class);
                this.f1828id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getId() {
                return this.f1828id;
            }

            public Delete setId(String str) {
                this.f1828id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Delete setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class GetRating extends YouTubeRequest<VideoGetRatingResponse> {
            private static final String REST_PATH = "videos/getRating";
            @Key

            /* renamed from: id */
            private String f1829id;
            @Key
            private String onBehalfOfContentOwner;

            protected GetRating(String str) {
                super(YouTube.this, "GET", REST_PATH, null, VideoGetRatingResponse.class);
                this.f1829id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public GetRating setAlt(String str) {
                return (GetRating) super.setAlt(str);
            }

            public GetRating setFields(String str) {
                return (GetRating) super.setFields(str);
            }

            public GetRating setKey(String str) {
                return (GetRating) super.setKey(str);
            }

            public GetRating setOauthToken(String str) {
                return (GetRating) super.setOauthToken(str);
            }

            public GetRating setPrettyPrint(Boolean bool) {
                return (GetRating) super.setPrettyPrint(bool);
            }

            public GetRating setQuotaUser(String str) {
                return (GetRating) super.setQuotaUser(str);
            }

            public GetRating setUserIp(String str) {
                return (GetRating) super.setUserIp(str);
            }

            public String getId() {
                return this.f1829id;
            }

            public GetRating setId(String str) {
                this.f1829id = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public GetRating setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public GetRating set(String str, Object obj) {
                return (GetRating) super.set(str, obj);
            }
        }

        public class Insert extends YouTubeRequest<Video> {
            private static final String REST_PATH = "videos";
            @Key
            private Boolean autoLevels;
            @Key
            private Boolean notifySubscribers;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String onBehalfOfContentOwnerChannel;
            @Key
            private String part;
            @Key
            private Boolean stabilize;

            protected Insert(String str, Video video) {
                super(YouTube.this, "POST", REST_PATH, video, Video.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            protected Insert(String str, Video video, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "POST", sb.toString(), video, Video.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Insert setPart(String str) {
                this.part = str;
                return this;
            }

            public Boolean getAutoLevels() {
                return this.autoLevels;
            }

            public Insert setAutoLevels(Boolean bool) {
                this.autoLevels = bool;
                return this;
            }

            public Boolean getNotifySubscribers() {
                return this.notifySubscribers;
            }

            public Insert setNotifySubscribers(Boolean bool) {
                this.notifySubscribers = bool;
                return this;
            }

            public boolean isNotifySubscribers() {
                Boolean bool = this.notifySubscribers;
                if (bool == null || bool == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.notifySubscribers.booleanValue();
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Insert setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getOnBehalfOfContentOwnerChannel() {
                return this.onBehalfOfContentOwnerChannel;
            }

            public Insert setOnBehalfOfContentOwnerChannel(String str) {
                this.onBehalfOfContentOwnerChannel = str;
                return this;
            }

            public Boolean getStabilize() {
                return this.stabilize;
            }

            public Insert setStabilize(Boolean bool) {
                this.stabilize = bool;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends YouTubeRequest<VideoListResponse> {
            private static final String REST_PATH = "videos";
            @Key
            private String chart;
            @Key

            /* renamed from: hl */
            private String f1830hl;
            @Key

            /* renamed from: id */
            private String f1831id;
            @Key
            private String locale;
            @Key
            private Long maxHeight;
            @Key
            private Long maxResults;
            @Key
            private Long maxWidth;
            @Key
            private String myRating;
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String pageToken;
            @Key
            private String part;
            @Key
            private String regionCode;
            @Key
            private String videoCategoryId;

            protected List(String str) {
                super(YouTube.this, "GET", REST_PATH, null, VideoListResponse.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public List setPart(String str) {
                this.part = str;
                return this;
            }

            public String getChart() {
                return this.chart;
            }

            public List setChart(String str) {
                this.chart = str;
                return this;
            }

            public String getHl() {
                return this.f1830hl;
            }

            public List setHl(String str) {
                this.f1830hl = str;
                return this;
            }

            public String getId() {
                return this.f1831id;
            }

            public List setId(String str) {
                this.f1831id = str;
                return this;
            }

            public String getLocale() {
                return this.locale;
            }

            public List setLocale(String str) {
                this.locale = str;
                return this;
            }

            public Long getMaxHeight() {
                return this.maxHeight;
            }

            public List setMaxHeight(Long l) {
                this.maxHeight = l;
                return this;
            }

            public Long getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Long l) {
                this.maxResults = l;
                return this;
            }

            public Long getMaxWidth() {
                return this.maxWidth;
            }

            public List setMaxWidth(Long l) {
                this.maxWidth = l;
                return this;
            }

            public String getMyRating() {
                return this.myRating;
            }

            public List setMyRating(String str) {
                this.myRating = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public List setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getRegionCode() {
                return this.regionCode;
            }

            public List setRegionCode(String str) {
                this.regionCode = str;
                return this;
            }

            public String getVideoCategoryId() {
                return this.videoCategoryId;
            }

            public List setVideoCategoryId(String str) {
                this.videoCategoryId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Rate extends YouTubeRequest<Void> {
            private static final String REST_PATH = "videos/rate";
            @Key

            /* renamed from: id */
            private String f1832id;
            @Key
            private String rating;

            protected Rate(String str, String str2) {
                super(YouTube.this, "POST", REST_PATH, null, Void.class);
                this.f1832id = (String) Preconditions.checkNotNull(str, "Required parameter id must be specified.");
                this.rating = (String) Preconditions.checkNotNull(str2, "Required parameter rating must be specified.");
            }

            public Rate setAlt(String str) {
                return (Rate) super.setAlt(str);
            }

            public Rate setFields(String str) {
                return (Rate) super.setFields(str);
            }

            public Rate setKey(String str) {
                return (Rate) super.setKey(str);
            }

            public Rate setOauthToken(String str) {
                return (Rate) super.setOauthToken(str);
            }

            public Rate setPrettyPrint(Boolean bool) {
                return (Rate) super.setPrettyPrint(bool);
            }

            public Rate setQuotaUser(String str) {
                return (Rate) super.setQuotaUser(str);
            }

            public Rate setUserIp(String str) {
                return (Rate) super.setUserIp(str);
            }

            public String getId() {
                return this.f1832id;
            }

            public Rate setId(String str) {
                this.f1832id = str;
                return this;
            }

            public String getRating() {
                return this.rating;
            }

            public Rate setRating(String str) {
                this.rating = str;
                return this;
            }

            public Rate set(String str, Object obj) {
                return (Rate) super.set(str, obj);
            }
        }

        public class ReportAbuse extends YouTubeRequest<Void> {
            private static final String REST_PATH = "videos/reportAbuse";
            @Key
            private String onBehalfOfContentOwner;

            protected ReportAbuse(VideoAbuseReport videoAbuseReport) {
                super(YouTube.this, "POST", REST_PATH, videoAbuseReport, Void.class);
            }

            public ReportAbuse setAlt(String str) {
                return (ReportAbuse) super.setAlt(str);
            }

            public ReportAbuse setFields(String str) {
                return (ReportAbuse) super.setFields(str);
            }

            public ReportAbuse setKey(String str) {
                return (ReportAbuse) super.setKey(str);
            }

            public ReportAbuse setOauthToken(String str) {
                return (ReportAbuse) super.setOauthToken(str);
            }

            public ReportAbuse setPrettyPrint(Boolean bool) {
                return (ReportAbuse) super.setPrettyPrint(bool);
            }

            public ReportAbuse setQuotaUser(String str) {
                return (ReportAbuse) super.setQuotaUser(str);
            }

            public ReportAbuse setUserIp(String str) {
                return (ReportAbuse) super.setUserIp(str);
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public ReportAbuse setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public ReportAbuse set(String str, Object obj) {
                return (ReportAbuse) super.set(str, obj);
            }
        }

        public class Update extends YouTubeRequest<Video> {
            private static final String REST_PATH = "videos";
            @Key
            private String onBehalfOfContentOwner;
            @Key
            private String part;

            protected Update(String str, Video video) {
                super(YouTube.this, "PUT", REST_PATH, video, Video.class);
                this.part = (String) Preconditions.checkNotNull(str, "Required parameter part must be specified.");
                checkRequiredParameter(video, Param.CONTENT);
                checkRequiredParameter(video.getId(), "Video.getId()");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getPart() {
                return this.part;
            }

            public Update setPart(String str) {
                this.part = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Update setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Videos() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            YouTube.this.initialize(delete);
            return delete;
        }

        public GetRating getRating(String str) throws IOException {
            GetRating getRating = new GetRating(str);
            YouTube.this.initialize(getRating);
            return getRating;
        }

        public Insert insert(String str, Video video) throws IOException {
            Insert insert = new Insert(str, video);
            YouTube.this.initialize(insert);
            return insert;
        }

        public Insert insert(String str, Video video, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Insert insert = new Insert(str, video, abstractInputStreamContent);
            YouTube.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            YouTube.this.initialize(list);
            return list;
        }

        public Rate rate(String str, String str2) throws IOException {
            Rate rate = new Rate(str, str2);
            YouTube.this.initialize(rate);
            return rate;
        }

        public ReportAbuse reportAbuse(VideoAbuseReport videoAbuseReport) throws IOException {
            ReportAbuse reportAbuse = new ReportAbuse(videoAbuseReport);
            YouTube.this.initialize(reportAbuse);
            return reportAbuse;
        }

        public Update update(String str, Video video) throws IOException {
            Update update = new Update(str, video);
            YouTube.this.initialize(update);
            return update;
        }
    }

    public class Watermarks {

        public class Set extends YouTubeRequest<Void> {
            private static final String REST_PATH = "watermarks/set";
            @Key
            private String channelId;
            @Key
            private String onBehalfOfContentOwner;

            protected Set(String str, InvideoBranding invideoBranding) {
                super(YouTube.this, "POST", REST_PATH, invideoBranding, Void.class);
                this.channelId = (String) Preconditions.checkNotNull(str, "Required parameter channelId must be specified.");
            }

            protected Set(String str, InvideoBranding invideoBranding, AbstractInputStreamContent abstractInputStreamContent) {
                YouTube youTube = YouTube.this;
                StringBuilder sb = new StringBuilder();
                sb.append("/upload/");
                sb.append(YouTube.this.getServicePath());
                sb.append(REST_PATH);
                super(youTube, "POST", sb.toString(), invideoBranding, Void.class);
                this.channelId = (String) Preconditions.checkNotNull(str, "Required parameter channelId must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Set setAlt(String str) {
                return (Set) super.setAlt(str);
            }

            public Set setFields(String str) {
                return (Set) super.setFields(str);
            }

            public Set setKey(String str) {
                return (Set) super.setKey(str);
            }

            public Set setOauthToken(String str) {
                return (Set) super.setOauthToken(str);
            }

            public Set setPrettyPrint(Boolean bool) {
                return (Set) super.setPrettyPrint(bool);
            }

            public Set setQuotaUser(String str) {
                return (Set) super.setQuotaUser(str);
            }

            public Set setUserIp(String str) {
                return (Set) super.setUserIp(str);
            }

            public String getChannelId() {
                return this.channelId;
            }

            public Set setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Set setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Set set(String str, Object obj) {
                return (Set) super.set(str, obj);
            }
        }

        public class Unset extends YouTubeRequest<Void> {
            private static final String REST_PATH = "watermarks/unset";
            @Key
            private String channelId;
            @Key
            private String onBehalfOfContentOwner;

            protected Unset(String str) {
                super(YouTube.this, "POST", REST_PATH, null, Void.class);
                this.channelId = (String) Preconditions.checkNotNull(str, "Required parameter channelId must be specified.");
            }

            public Unset setAlt(String str) {
                return (Unset) super.setAlt(str);
            }

            public Unset setFields(String str) {
                return (Unset) super.setFields(str);
            }

            public Unset setKey(String str) {
                return (Unset) super.setKey(str);
            }

            public Unset setOauthToken(String str) {
                return (Unset) super.setOauthToken(str);
            }

            public Unset setPrettyPrint(Boolean bool) {
                return (Unset) super.setPrettyPrint(bool);
            }

            public Unset setQuotaUser(String str) {
                return (Unset) super.setQuotaUser(str);
            }

            public Unset setUserIp(String str) {
                return (Unset) super.setUserIp(str);
            }

            public String getChannelId() {
                return this.channelId;
            }

            public Unset setChannelId(String str) {
                this.channelId = str;
                return this;
            }

            public String getOnBehalfOfContentOwner() {
                return this.onBehalfOfContentOwner;
            }

            public Unset setOnBehalfOfContentOwner(String str) {
                this.onBehalfOfContentOwner = str;
                return this;
            }

            public Unset set(String str, Object obj) {
                return (Unset) super.set(str, obj);
            }
        }

        public Watermarks() {
        }

        public Set set(String str, InvideoBranding invideoBranding) throws IOException {
            Set set = new Set(str, invideoBranding);
            YouTube.this.initialize(set);
            return set;
        }

        public Set set(String str, InvideoBranding invideoBranding, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            Set set = new Set(str, invideoBranding, abstractInputStreamContent);
            YouTube.this.initialize(set);
            return set;
        }

        public Unset unset(String str) throws IOException {
            Unset unset = new Unset(str);
            YouTube.this.initialize(unset);
            return unset;
        }
    }

    static {
        Preconditions.checkState(GoogleUtils.MAJOR_VERSION.intValue() == 1 && GoogleUtils.MINOR_VERSION.intValue() >= 15, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.25.0 of the YouTube Data API library.", GoogleUtils.VERSION);
    }

    public YouTube(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }

    YouTube(Builder builder) {
        super(builder);
    }

    /* access modifiers changed from: protected */
    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
    }

    public Activities activities() {
        return new Activities();
    }

    public Captions captions() {
        return new Captions();
    }

    public ChannelBanners channelBanners() {
        return new ChannelBanners();
    }

    public ChannelSections channelSections() {
        return new ChannelSections();
    }

    public Channels channels() {
        return new Channels();
    }

    public CommentThreads commentThreads() {
        return new CommentThreads();
    }

    public Comments comments() {
        return new Comments();
    }

    public GuideCategories guideCategories() {
        return new GuideCategories();
    }

    public I18nLanguages i18nLanguages() {
        return new I18nLanguages();
    }

    public I18nRegions i18nRegions() {
        return new I18nRegions();
    }

    public LiveBroadcasts liveBroadcasts() {
        return new LiveBroadcasts();
    }

    public LiveChatBans liveChatBans() {
        return new LiveChatBans();
    }

    public LiveChatMessages liveChatMessages() {
        return new LiveChatMessages();
    }

    public LiveChatModerators liveChatModerators() {
        return new LiveChatModerators();
    }

    public LiveStreams liveStreams() {
        return new LiveStreams();
    }

    public PlaylistItems playlistItems() {
        return new PlaylistItems();
    }

    public Playlists playlists() {
        return new Playlists();
    }

    public Search search() {
        return new Search();
    }

    public Sponsors sponsors() {
        return new Sponsors();
    }

    public Subscriptions subscriptions() {
        return new Subscriptions();
    }

    public SuperChatEvents superChatEvents() {
        return new SuperChatEvents();
    }

    public Thumbnails thumbnails() {
        return new Thumbnails();
    }

    public VideoAbuseReportReasons videoAbuseReportReasons() {
        return new VideoAbuseReportReasons();
    }

    public VideoCategories videoCategories() {
        return new VideoCategories();
    }

    public Videos videos() {
        return new Videos();
    }

    public Watermarks watermarks() {
        return new Watermarks();
    }
}
