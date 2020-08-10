package com.mobiroller.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.RssModel;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private MobiRollerApplication app = MobiRollerApplication.app;
    private Activity mContext;
    ImageView rssAvatar;
    private List<Object> rssModelList;
    private String screenId;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ViewPagerAdapter(Activity activity, List<Object> list, String str) {
        this.mContext = activity;
        this.rssModelList = list;
        this.screenId = str;
    }

    public int getCount() {
        return this.rssModelList.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.rss_header_item, viewGroup, false);
        this.rssAvatar = (ImageView) inflate.findViewById(R.id.rss_list_image);
        final TextView textView = (TextView) inflate.findViewById(R.id.rss_list_title);
        RssModel rssModel = (RssModel) this.rssModelList.get(i);
        textView.setText(Html.fromHtml(rssModel.getTitle()));
        if (rssModel.getImage() == null || rssModel.getImage().equalsIgnoreCase("")) {
            Glide.with(this.mContext).load(Integer.valueOf(R.drawable.no_image)).into(this.rssAvatar);
        } else {
            Glide.with(this.mContext).load(rssModel.getImage()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).listener(new RequestListener<Drawable>() {
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    ViewPagerAdapter.this.rssAvatar.setImageResource(R.drawable.no_image);
                    return false;
                }
            }).into(this.rssAvatar);
        }
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = (int) (((float) textView.getHeight()) / ((float) textView.getLineHeight()));
                if (textView.getLineCount() != height) {
                    textView.setLines(height);
                }
            }
        });
        viewGroup.addView(inflate);
        StringBuilder sb = new StringBuilder();
        sb.append("viewpager");
        sb.append(i);
        inflate.setTag(sb.toString());
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public View getCurrentItemImageView() {
        return this.rssAvatar;
    }
}
