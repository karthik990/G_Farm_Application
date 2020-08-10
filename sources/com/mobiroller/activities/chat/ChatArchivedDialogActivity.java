package com.mobiroller.activities.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.chat.ChatArchivedDialogAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.UnarchiveDialogEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatArchivedDialogActivity extends AveActivity {
    private ChatArchivedDialogAdapter adapter;
    @Inject
    BannerHelper bannerHelper;
    @BindView(2131362141)
    RecyclerView chatArchivedList;
    @BindView(2131362334)
    ImageView emptyImageView;
    @BindView(2131362335)
    TextView emptyTextView;
    @BindView(2131362145)
    LinearLayout emptyView;
    @BindView(2131361950)
    RelativeLayout mainLayout;
    @BindView(2131361951)
    LinearLayout overlayLayout;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private Snackbar snackbar;
    @BindView(2131363276)
    MobirollerToolbar toolbar;
    private ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_chat_archived_dialog);
        EventBus.getDefault().register(this);
        ButterKnife.bind((Activity) this);
        init();
        setDetails();
        setChatArchivedDialogList();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void init() {
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.toolbarHelper = new ToolbarHelper(this.sharedPrefHelper);
    }

    public void setDetails() {
        TextView textView = this.emptyTextView;
        boolean isColorDark = ColorUtil.isColorDark(Theme.primaryColor);
        int i = ViewCompat.MEASURED_STATE_MASK;
        textView.setTextColor(isColorDark ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK);
        ImageView imageView = this.emptyImageView;
        if (ColorUtil.isColorDark(Theme.primaryColor)) {
            i = Theme.primaryColor;
        }
        imageView.setColorFilter(i);
        this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.archived_chats_without_count));
        this.snackbar = Snackbar.make((View) this.mainLayout, (CharSequence) "", 0);
    }

    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = this.mainLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.overlayLayout);
        }
    }

    public void setChatArchivedDialogList() {
        Intent intent = getIntent();
        String str = ChatConstants.ARG_ARCHIVED_LIST_MODEL;
        if (intent.hasExtra(str)) {
            this.adapter = new ChatArchivedDialogAdapter((List) getIntent().getSerializableExtra(str), this);
            this.chatArchivedList.setAdapter(this.adapter);
            this.chatArchivedList.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Subscribe
    public void removeItem(UnarchiveDialogEvent unarchiveDialogEvent) {
        this.snackbar.setText((int) R.string.dialog_unarchived);
        this.snackbar.show();
        checkAdapterIsEmpty();
    }

    public void checkAdapterIsEmpty() {
        if (this.adapter.getItemCount() != 0) {
            this.emptyView.setVisibility(8);
            this.chatArchivedList.setVisibility(0);
            return;
        }
        this.emptyView.setVisibility(0);
        this.chatArchivedList.setVisibility(8);
    }
}
