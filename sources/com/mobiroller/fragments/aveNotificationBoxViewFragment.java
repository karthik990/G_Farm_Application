package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.DynamicConstants;
import com.mobiroller.adapters.NotificationAdapter;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NotificationModel;
import com.mobiroller.util.NotificationUtil;
import com.mobiroller.views.SimpleDividerItemDecoration;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnRowClickListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnRowLongClickListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnSwipeOptionsClickListener;
import java.util.ArrayList;
import java.util.List;

public class aveNotificationBoxViewFragment extends BackHandledFragment {
    /* access modifiers changed from: private */
    public NotificationAdapter adapter;
    @BindView(2131362333)
    RelativeLayout emptyView;
    /* access modifiers changed from: private */
    public boolean isActionMode;
    /* access modifiers changed from: private */
    public Menu menu;
    @BindView(2131362819)
    RelativeLayout notificationBoxLayout;
    @BindView(2131362822)
    ImageView notificationEmptyImage;
    @BindView(2131362823)
    TextView notificationEmptyText;
    @BindView(2131362827)
    RecyclerView notificationList;
    /* access modifiers changed from: private */
    public ArrayList<NotificationModel> notificationModels;
    @BindView(2131362831)
    RelativeLayout notificationNotSupportedLayout;
    @BindView(2131362832)
    RelativeLayout notificationRelLayout;
    private RecyclerTouchListener onTouchListener;
    /* access modifiers changed from: private */
    public boolean showActionButton;
    Unbinder unbinder;
    View view;

    public String getTagText() {
        return null;
    }

    public void onPrepareOptionsMenu(Menu menu2) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.notifications_layout, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, this.view);
        hideToolbar((Toolbar) this.view.findViewById(R.id.toolbar_top));
        if (DynamicConstants.MobiRoller_Stage) {
            this.notificationNotSupportedLayout.setVisibility(0);
            this.notificationBoxLayout.setVisibility(8);
        } else {
            this.notificationModels = NotificationUtil.getDb(getActivity());
            if (this.notificationModels == null) {
                this.notificationModels = new ArrayList<>();
            }
            loadUi();
        }
        return this.view;
    }

    public void loadUi() {
        this.notificationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.notificationList.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), R.drawable.notification_divider));
        if (this.notificationModels.size() == 0) {
            setEmptyView();
            return;
        }
        this.adapter = new NotificationAdapter(this.notificationModels, getActivity());
        this.notificationList.setAdapter(this.adapter);
        this.onTouchListener = new RecyclerTouchListener(getActivity(), this.notificationList);
        this.onTouchListener.setIndependentViews(Integer.valueOf(R.id.backgroundView)).setClickable((OnRowClickListener) new OnRowClickListener() {
            public void onRowClicked(int i) {
                if (aveNotificationBoxViewFragment.this.isActionMode) {
                    aveNotificationBoxViewFragment.this.myToggleSelection(i);
                    return;
                }
                NotificationModel item = aveNotificationBoxViewFragment.this.adapter.getItem(i);
                NotificationUtil.showPopup(aveNotificationBoxViewFragment.this.getActivity(), item);
                aveNotificationBoxViewFragment.this.adapter.setRead(i);
                NotificationUtil.setRead(item, aveNotificationBoxViewFragment.this.getActivity());
            }

            public void onIndependentViewClicked(int i, int i2) {
                if (aveNotificationBoxViewFragment.this.isActionMode) {
                    aveNotificationBoxViewFragment.this.myToggleSelection(i2);
                    return;
                }
                NotificationModel item = aveNotificationBoxViewFragment.this.adapter.getItem(i2);
                NotificationUtil.showPopup(aveNotificationBoxViewFragment.this.getActivity(), item);
                aveNotificationBoxViewFragment.this.adapter.setRead(i2);
                NotificationUtil.setRead(item, aveNotificationBoxViewFragment.this.getActivity());
            }
        }).setLongClickable(true, new OnRowLongClickListener() {
            public void onRowLongClicked(int i) {
                if (!aveNotificationBoxViewFragment.this.isActionMode) {
                    aveNotificationBoxViewFragment.this.showActionButton = true;
                    aveNotificationBoxViewFragment.this.getActivity().invalidateOptionsMenu();
                }
                aveNotificationBoxViewFragment.this.myToggleSelection(i);
            }
        }).setSwipeOptionViews(Integer.valueOf(R.id.backgroundView)).setSwipeable(R.id.foregroundView, R.id.backgroundView, new OnSwipeOptionsClickListener() {
            public void onSwipeOptionClicked(int i, final int i2) {
                if (i == R.id.backgroundView) {
                    new Builder(aveNotificationBoxViewFragment.this.getActivity()).content((int) R.string.are_you_sure_delete).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            NotificationUtil.removeFromList((NotificationModel) aveNotificationBoxViewFragment.this.adapter.getAllData().get(i2), aveNotificationBoxViewFragment.this.getActivity());
                            Toast.makeText(aveNotificationBoxViewFragment.this.getActivity(), aveNotificationBoxViewFragment.this.getString(R.string.notification_deleted), 0).show();
                            aveNotificationBoxViewFragment.this.notificationModels.remove(i2);
                            aveNotificationBoxViewFragment.this.adapter.notifyItemRemoved(i2);
                            aveNotificationBoxViewFragment.this.adapter.notifyItemRangeChanged(i2, aveNotificationBoxViewFragment.this.adapter.getItemCount());
                            if (aveNotificationBoxViewFragment.this.adapter.getItemCount() == 0) {
                                aveNotificationBoxViewFragment.this.setEmptyView();
                            }
                        }
                    }).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setEmptyView() {
        ((TextView) this.view.findViewById(R.id.notification_empty_text)).setTextColor(getStatusBarColor());
        this.notificationList.setVisibility(8);
        this.emptyView.setVisibility(0);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        if (this.showActionButton) {
            menuInflater.inflate(R.menu.notification_delete_menu, menu2);
            this.isActionMode = true;
            this.menu = menu2;
        }
        super.onCreateOptionsMenu(menu2, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.delete_all_selected) {
            new Builder(getActivity()).content((int) R.string.delete_selected).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    List selectedItems = aveNotificationBoxViewFragment.this.adapter.getSelectedItems();
                    for (int size = selectedItems.size() - 1; size >= 0; size--) {
                        int intValue = ((Integer) selectedItems.get(size)).intValue();
                        NotificationUtil.removeFromList((NotificationModel) aveNotificationBoxViewFragment.this.adapter.getAllData().get(intValue), aveNotificationBoxViewFragment.this.getActivity());
                        aveNotificationBoxViewFragment.this.adapter.removeData(intValue);
                    }
                    Toast.makeText(aveNotificationBoxViewFragment.this.getActivity(), aveNotificationBoxViewFragment.this.getString(R.string.selected_deleted), 0).show();
                    aveNotificationBoxViewFragment.this.showActionButton = false;
                    aveNotificationBoxViewFragment.this.getActivity().invalidateOptionsMenu();
                    if (aveNotificationBoxViewFragment.this.adapter.getItemCount() == 0) {
                        aveNotificationBoxViewFragment.this.setEmptyView();
                    }
                    aveNotificationBoxViewFragment.this.adapter.clearSelections();
                    aveNotificationBoxViewFragment.this.isActionMode = false;
                    aveNotificationBoxViewFragment.this.menu.clear();
                }
            }).show();
        }
        super.onOptionsItemSelected(menuItem);
        return true;
    }

    /* access modifiers changed from: private */
    public void myToggleSelection(int i) {
        if (!this.isActionMode) {
            this.showActionButton = true;
            getActivity().invalidateOptionsMenu();
        }
        this.adapter.toggleSelection(i);
        this.onTouchListener.setSwipeable(false);
        if (this.adapter.getSelectedItemCount() == 0) {
            this.showActionButton = false;
            getActivity().invalidateOptionsMenu();
            this.isActionMode = false;
            this.onTouchListener.setSwipeable(true);
        }
    }

    public void onResume() {
        super.onResume();
        this.notificationList.addOnItemTouchListener(this.onTouchListener);
        if (this.notificationBoxLayout != null) {
            this.bannerHelper.addBannerAd(this.notificationBoxLayout, this.notificationRelLayout);
        }
    }

    public void onPause() {
        super.onPause();
        this.notificationList.removeOnItemTouchListener(this.onTouchListener);
    }

    public boolean onBackPressed() {
        if (!this.isActionMode) {
            return false;
        }
        this.showActionButton = false;
        getActivity().invalidateOptionsMenu();
        this.isActionMode = false;
        this.adapter.clearSelections();
        this.onTouchListener.setSwipeable(true);
        return true;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
