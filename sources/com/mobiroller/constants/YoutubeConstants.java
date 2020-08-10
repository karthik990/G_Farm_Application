package com.mobiroller.constants;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.mobi942763453128.R;

public class YoutubeConstants {
    public static final String INTENT_EXTRA_CHANNEL_ID = "channelId";
    public static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String YOUTUBE_KEY = MobiRollerApplication.app.getString(R.string.youtube_key);
    public static final String YOUTUBE_SCOPE = "https://www.googleapis.com/auth/youtube.readonly";
    public static final String YOUTUBE_SCOPE_SSL = "https://www.googleapis.com/auth/youtube.force-ssl";

    public interface YoutubeRequestParams {
        public static final String req_detail_parts = "snippet,contentDetails";
        public static final String req_search_max_results = "20";
        public static final String req_search_order = "date";
        public static final String req_search_parts = "snippet";
        public static final String req_search_type = "video";
    }
}
