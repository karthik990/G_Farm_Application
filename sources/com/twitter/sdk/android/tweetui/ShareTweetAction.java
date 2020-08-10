package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Tweet;
import org.apache.http.protocol.HTTP;

class ShareTweetAction implements OnClickListener {
    final Tweet tweet;
    final TweetUi tweetUi;

    ShareTweetAction(Tweet tweet2, TweetUi tweetUi2) {
        this.tweet = tweet2;
        this.tweetUi = tweetUi2;
    }

    public void onClick(View view) {
        onClick(view.getContext(), view.getResources());
    }

    /* access modifiers changed from: 0000 */
    public void onClick(Context context, Resources resources) {
        Tweet tweet2 = this.tweet;
        if (tweet2 != null && tweet2.user != null) {
            launchShareIntent(Intent.createChooser(getShareIntent(getShareSubject(resources), getShareContent(resources)), resources.getString(C5234R.C5238string.tw__share_tweet)), context);
        }
    }

    /* access modifiers changed from: 0000 */
    public String getShareContent(Resources resources) {
        return resources.getString(C5234R.C5238string.tw__share_content_format, new Object[]{this.tweet.user.screenName, Long.toString(this.tweet.f3662id)});
    }

    /* access modifiers changed from: 0000 */
    public String getShareSubject(Resources resources) {
        return resources.getString(C5234R.C5238string.tw__share_subject_format, new Object[]{this.tweet.user.name, this.tweet.user.screenName});
    }

    /* access modifiers changed from: 0000 */
    public void launchShareIntent(Intent intent, Context context) {
        if (!IntentUtils.safeStartActivity(context, intent)) {
            Twitter.getLogger().mo20819e("TweetUi", "Activity cannot be found to handle share intent");
        }
    }

    /* access modifiers changed from: 0000 */
    public Intent getShareIntent(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.TEXT", str2);
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        return intent;
    }
}
