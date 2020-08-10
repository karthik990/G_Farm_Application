package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.activities.AveAddNoteActivity;
import com.mobiroller.adapters.NoteAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NoteModel;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.NoteUtil;
import com.mobiroller.views.SimpleDividerItemDecoration;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnRowClickListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnRowLongClickListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnSwipeOptionsClickListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class aveNoteViewFragment extends BackHandledFragment {
    @BindView(2131362373)
    FloatingActionButton addNoteFAB;
    @BindView(2131362333)
    RelativeLayout emptyView;
    @BindView(2131362379)
    RelativeLayout fabLayout;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public boolean isActionMode = false;
    /* access modifiers changed from: private */
    public Menu menu;
    /* access modifiers changed from: private */
    public NoteAdapter noteAdapter;
    @BindView(2131362807)
    TextView noteEmptyText;
    @BindView(2131362808)
    FrameLayout noteFrameView;
    @BindView(2131362814)
    LinearLayout noteLinearLayout;
    @BindView(2131362815)
    RecyclerView noteListView;
    /* access modifiers changed from: private */
    public ArrayList<NoteModel> noteModelArrayList;
    @BindView(2131362813)
    RelativeLayout note_rel_layout;
    @BindView(2131362822)
    ImageView notificationEmptyImage;
    private RecyclerTouchListener onTouchListener;
    /* access modifiers changed from: private */
    public boolean showActionButton;
    private View view;

    public String getTagText() {
        return null;
    }

    public void onPrepareOptionsMenu(Menu menu2) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.layout_note, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, this.view);
        this.interstitialAdsUtil = new InterstitialAdsUtil((Activity) getActivity());
        this.noteModelArrayList = NoteUtil.getDb(getActivity(), this.screenId);
        loadUi(this.view);
        return this.view;
    }

    public void onResume() {
        super.onResume();
        if (this.note_rel_layout != null) {
            this.bannerHelper.addBannerAd(this.note_rel_layout, this.noteFrameView);
        }
        this.noteListView.addOnItemTouchListener(this.onTouchListener);
        loadData();
    }

    private void loadData() {
        this.noteModelArrayList = NoteUtil.getDb(getActivity(), this.screenId);
        if (this.noteModelArrayList == null) {
            this.noteModelArrayList = new ArrayList<>();
        }
        this.noteAdapter = new NoteAdapter(getActivity(), this.noteModelArrayList);
        this.noteListView.setAdapter(this.noteAdapter);
        checkIsEmpty();
    }

    /* access modifiers changed from: private */
    public void checkIsEmpty() {
        if (this.noteModelArrayList.size() == 0) {
            ((TextView) this.view.findViewById(R.id.note_empty_text)).setTextColor(getStatusBarColor());
            this.noteListView.setVisibility(8);
            this.emptyView.setVisibility(0);
            this.note_rel_layout.setBackgroundColor(getResources().getColor(R.color.empty_recycler_background_color));
            return;
        }
        ((TextView) this.view.findViewById(R.id.note_empty_text)).setTextColor(getStatusBarColor());
        this.noteListView.setVisibility(0);
        this.emptyView.setVisibility(8);
        this.note_rel_layout.setBackgroundColor(getResources().getColor(R.color.filled_recycler_background_color));
    }

    public void loadUi(View view2) {
        hideToolbar((Toolbar) view2.findViewById(R.id.toolbar_top));
        this.addNoteFAB.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
        this.addNoteFAB.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                aveNoteViewFragment.this.addNote();
            }
        });
        this.noteListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.noteListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), R.drawable.todo_line));
        this.onTouchListener = new RecyclerTouchListener(getActivity(), this.noteListView);
        RecyclerTouchListener recyclerTouchListener = this.onTouchListener;
        Integer valueOf = Integer.valueOf(R.id.backgroundView);
        recyclerTouchListener.setIndependentViews(valueOf).setSwipeOptionViews(valueOf).setClickable((OnRowClickListener) new OnRowClickListener() {
            public void onRowClicked(int i) {
                if (aveNoteViewFragment.this.isActionMode) {
                    aveNoteViewFragment.this.myToggleSelection(i);
                    return;
                }
                Intent putExtra = new Intent(aveNoteViewFragment.this.getActivity(), AveAddNoteActivity.class).putExtra(AveAddNoteActivity.NOTE_MODEL, (Serializable) aveNoteViewFragment.this.noteModelArrayList.get(i)).putExtra(AveAddNoteActivity.NOTE_MODEL_POSITION, i);
                if (((NoteModel) aveNoteViewFragment.this.noteModelArrayList.get(i)).getImagePaths().size() != 0) {
                    putExtra.putStringArrayListExtra(AveAddNoteActivity.NOTE_MODEL_IMAGES, ((NoteModel) aveNoteViewFragment.this.noteModelArrayList.get(i)).getImagePaths());
                }
                putExtra.putExtra(Constants.KEY_SCREEN_ID, aveNoteViewFragment.this.screenId);
                aveNoteViewFragment.this.interstitialAdsUtil.checkInterstitialAds(putExtra);
            }

            public void onIndependentViewClicked(int i, int i2) {
                if (aveNoteViewFragment.this.isActionMode) {
                    aveNoteViewFragment.this.myToggleSelection(i2);
                    return;
                }
                Intent putExtra = new Intent(aveNoteViewFragment.this.getActivity(), AveAddNoteActivity.class).putExtra(AveAddNoteActivity.NOTE_MODEL, (Serializable) aveNoteViewFragment.this.noteModelArrayList.get(i2)).putExtra(AveAddNoteActivity.NOTE_MODEL_POSITION, i2);
                if (((NoteModel) aveNoteViewFragment.this.noteModelArrayList.get(i2)).getImagePaths().size() != 0) {
                    putExtra.putStringArrayListExtra(AveAddNoteActivity.NOTE_MODEL_IMAGES, ((NoteModel) aveNoteViewFragment.this.noteModelArrayList.get(i2)).getImagePaths());
                }
                putExtra.putExtra(Constants.KEY_SCREEN_ID, aveNoteViewFragment.this.screenId);
                aveNoteViewFragment.this.interstitialAdsUtil.checkInterstitialAds(putExtra);
            }
        }).setLongClickable(true, new OnRowLongClickListener() {
            public void onRowLongClicked(int i) {
                if (!aveNoteViewFragment.this.isActionMode) {
                    aveNoteViewFragment.this.showActionButton = true;
                    aveNoteViewFragment.this.getActivity().invalidateOptionsMenu();
                }
                aveNoteViewFragment.this.myToggleSelection(i);
            }
        }).setSwipeable(R.id.foregroundView, R.id.backgroundView, new OnSwipeOptionsClickListener() {
            public void onSwipeOptionClicked(int i, final int i2) {
                if (i == R.id.backgroundView) {
                    new Builder(aveNoteViewFragment.this.getActivity()).content((int) R.string.are_you_sure_delete).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            aveNoteViewFragment.this.noteAdapter.removeItem(i2);
                            aveNoteViewFragment.this.checkIsEmpty();
                            NoteUtil.updateDb(aveNoteViewFragment.this.noteAdapter.getList(), aveNoteViewFragment.this.getActivity(), aveNoteViewFragment.this.screenId);
                            Toast.makeText(aveNoteViewFragment.this.getActivity(), aveNoteViewFragment.this.getResources().getString(R.string.note_removed), 1).show();
                        }
                    }).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void addNote() {
        this.interstitialAdsUtil.checkInterstitialAds(new Intent(getActivity(), AveAddNoteActivity.class).putExtra(Constants.KEY_SCREEN_ID, this.screenId));
    }

    public void onPause() {
        super.onPause();
        this.noteListView.removeOnItemTouchListener(this.onTouchListener);
    }

    /* access modifiers changed from: private */
    public void myToggleSelection(int i) {
        this.noteAdapter.toggleSelection(i);
        this.onTouchListener.setSwipeable(false);
        if (this.noteAdapter.getSelectedItemCount() == 0) {
            this.showActionButton = false;
            getActivity().invalidateOptionsMenu();
            this.isActionMode = false;
            this.onTouchListener.setSwipeable(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.delete_all_selected) {
            new Builder(getActivity()).content((int) R.string.delete_selected).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    List selectedItems = aveNoteViewFragment.this.noteAdapter.getSelectedItems();
                    int i = 0;
                    for (int size = selectedItems.size() - 1; size >= 0; size--) {
                        int intValue = ((Integer) selectedItems.get(size)).intValue();
                        NoteUtil.removeFromList(aveNoteViewFragment.this.getActivity(), (NoteModel) aveNoteViewFragment.this.noteAdapter.getList().get(intValue), aveNoteViewFragment.this.screenId);
                        aveNoteViewFragment.this.noteAdapter.removeItem(intValue);
                        i++;
                    }
                    Toast.makeText(aveNoteViewFragment.this.getActivity(), aveNoteViewFragment.this.getString(R.string.selected_notes_deleted), 0).show();
                    aveNoteViewFragment.this.showActionButton = false;
                    aveNoteViewFragment.this.getActivity().invalidateOptionsMenu();
                    if (aveNoteViewFragment.this.noteAdapter.getItemCount() == 0) {
                        aveNoteViewFragment.this.checkIsEmpty();
                    }
                    aveNoteViewFragment.this.noteAdapter.clearSelections();
                    aveNoteViewFragment.this.isActionMode = false;
                    aveNoteViewFragment.this.menu.clear();
                    if (i <= 0) {
                        return;
                    }
                    if (i > 1) {
                        Toast.makeText(aveNoteViewFragment.this.getActivity(), aveNoteViewFragment.this.getResources().getString(R.string.notes_removed), 1).show();
                    } else {
                        Toast.makeText(aveNoteViewFragment.this.getActivity(), aveNoteViewFragment.this.getResources().getString(R.string.note_removed), 1).show();
                    }
                }
            }).show();
        }
        super.onOptionsItemSelected(menuItem);
        return true;
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        if (this.showActionButton) {
            menuInflater.inflate(R.menu.notification_delete_menu, menu2);
            this.isActionMode = true;
            this.menu = menu2;
        }
        super.onCreateOptionsMenu(menu2, menuInflater);
    }

    public boolean onBackPressed() {
        if (!this.isActionMode) {
            return false;
        }
        this.showActionButton = false;
        getActivity().invalidateOptionsMenu();
        this.isActionMode = false;
        this.noteAdapter.clearSelections();
        this.onTouchListener.setSwipeable(true);
        return true;
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject((BaseModuleFragment) this);
        return this;
    }
}
