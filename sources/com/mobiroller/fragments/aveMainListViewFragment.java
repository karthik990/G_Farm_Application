package com.mobiroller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.adapters.ContentRecyclerAdapter;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import java.util.ArrayList;
import java.util.HashMap;

public class aveMainListViewFragment extends BaseModuleFragment {
    public static final String KEY_IMAGE_URL = "img_url";
    public static final String KEY_SCREEN_ID = "screen_id";
    public static final String KEY_SCREEN_TYPE = "screen_type";
    public static final String KEY_TITLE = "title";
    @BindView(2131362194)
    RelativeLayout contentLayout;
    private ArrayList<HashMap<String, String>> contentList = new ArrayList<>();
    @BindView(2131362197)
    LinearLayout contentListLayout;
    @BindView(2131362199)
    RelativeLayout contentOverlay;
    @BindView(2131362193)
    ImageView imgView;
    @BindView(2131362195)
    RecyclerView list;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363093)
    ScrollView scrollView;
    @BindView(2131362200)
    TextView textView;
    /* access modifiers changed from: private */
    public ArrayList<TableItemsModel> validNavItems = new ArrayList<>();

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.content, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        loadUi();
        return inflate;
    }

    public void onResume() {
        super.onResume();
        if (this.contentLayout != null) {
            this.bannerHelper.addBannerAd(this.contentOverlay, this.contentLayout);
        }
    }

    private void loadUi() {
        if (this.screenModel == null) {
            this.screenModel = JSONStorage.getScreenModel(this.screenId);
        }
        if (this.screenModel != null) {
            try {
                ImageManager.loadBackgroundImageFromImageModel(this.contentOverlay, this.screenModel.getBackgroundImageName());
                setMainImageView();
                setContentText();
                setDataList();
                setContentList();
                setListComponent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0059, code lost:
        r5 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.mobiroller.models.ScreenModel checkTableItems(com.mobiroller.models.ScreenModel r10) {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.content.res.Resources r1 = r9.getResources()
            r2 = 2130903040(0x7f030000, float:1.7412887E38)
            java.lang.String[] r1 = r1.getStringArray(r2)
            java.util.ArrayList r2 = r10.getTableItems()
            r3 = 0
            r4 = 0
        L_0x0015:
            int r5 = r2.size()
            if (r4 >= r5) goto L_0x0066
            int r5 = r1.length
            r6 = 0
        L_0x001d:
            if (r6 >= r5) goto L_0x0059
            r7 = r1[r6]
            java.lang.Object r8 = r2.get(r4)
            com.mobiroller.models.TableItemsModel r8 = (com.mobiroller.models.TableItemsModel) r8
            java.lang.String r8 = r8.getScreenType()
            boolean r7 = r8.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x0056
            java.lang.Object r5 = r2.get(r4)
            com.mobiroller.models.TableItemsModel r5 = (com.mobiroller.models.TableItemsModel) r5
            java.lang.String r5 = r5.getScreenType()
            java.lang.String r6 = "aveHtmlView"
            boolean r5 = r5.equalsIgnoreCase(r6)
            if (r5 == 0) goto L_0x0054
            java.lang.Object r5 = r2.get(r4)
            com.mobiroller.models.TableItemsModel r5 = (com.mobiroller.models.TableItemsModel) r5
            java.lang.String r5 = r5.screenSubType
            java.lang.String r6 = "aveWeatherView"
            boolean r5 = r5.equalsIgnoreCase(r6)
            if (r5 == 0) goto L_0x0054
            goto L_0x0059
        L_0x0054:
            r5 = 1
            goto L_0x005a
        L_0x0056:
            int r6 = r6 + 1
            goto L_0x001d
        L_0x0059:
            r5 = 0
        L_0x005a:
            if (r5 == 0) goto L_0x0063
            java.lang.Object r5 = r2.get(r4)
            r0.add(r5)
        L_0x0063:
            int r4 = r4 + 1
            goto L_0x0015
        L_0x0066:
            r10.setTableItems(r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.aveMainListViewFragment.checkTableItems(com.mobiroller.models.ScreenModel):com.mobiroller.models.ScreenModel");
    }

    private ArrayList<TableItemsModel> getValidItems(ArrayList<TableItemsModel> arrayList) {
        String userRole = this.sharedPrefHelper.getUserRole();
        ArrayList<TableItemsModel> arrayList2 = new ArrayList<>();
        try {
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (!((TableItemsModel) arrayList.get(i)).isLoginActive()) {
                        arrayList2.add(arrayList.get(i));
                    } else if (this.sharedPrefHelper.getUserLoginStatus()) {
                        if (((TableItemsModel) arrayList.get(i)).getRoles().size() > 0) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= ((TableItemsModel) arrayList.get(i)).getRoles().size()) {
                                    break;
                                } else if (((String) ((TableItemsModel) arrayList.get(i)).getRoles().get(i2)).equalsIgnoreCase(userRole)) {
                                    arrayList2.add(arrayList.get(i));
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        } else {
                            arrayList2.add(arrayList.get(i));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList2;
    }

    private void setContentList() {
        for (int i = 0; i < this.validNavItems.size(); i++) {
            TableItemsModel tableItemsModel = (TableItemsModel) this.validNavItems.get(i);
            String localizedTitle = this.localizationHelper.getLocalizedTitle(tableItemsModel.getTitle());
            String str = null;
            if (tableItemsModel.getImageName() != null) {
                str = tableItemsModel.getImageName().getImageURL();
            }
            String screenType = tableItemsModel.getScreenType();
            String accountScreenID = tableItemsModel.getAccountScreenID();
            HashMap hashMap = new HashMap();
            hashMap.put("title", localizedTitle);
            hashMap.put("img_url", str);
            hashMap.put("screen_type", screenType);
            hashMap.put("screen_id", accountScreenID);
            this.contentList.add(hashMap);
        }
    }

    private void setListComponent() {
        ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(getActivity(), this.contentList, this.screenModel, this.screenHelper, this.sharedPrefHelper);
        this.list.setAdapter(contentRecyclerAdapter);
        this.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.list.setVisibility(0);
        ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                if (((TableItemsModel) aveMainListViewFragment.this.validNavItems.get(i)).getAccountScreenID() == null || Integer.valueOf(((TableItemsModel) aveMainListViewFragment.this.validNavItems.get(i)).getAccountScreenID()).intValue() == -1) {
                    view.setEnabled(false);
                } else {
                    ActivityHandler.startActivity((Context) aveMainListViewFragment.this.getActivity(), (TableItemsModel) aveMainListViewFragment.this.validNavItems.get(i));
                }
            }
        });
    }

    private void setMainImageView() {
        if (this.screenModel.getMainImageName() == null || this.screenModel.getMainImageName().getImageURL() == null) {
            this.imgView.setVisibility(8);
            return;
        }
        LayoutParams layoutParams = new LayoutParams(this.imgView.getLayoutParams());
        layoutParams.setMargins(this.imgView.getPaddingLeft(), 0, this.imgView.getPaddingRight(), 0);
        this.imgView.setLayoutParams(layoutParams);
        this.componentHelper.setMainImage(getActivity(), this.imgView, this.screenModel);
        this.imgView.setScaleType(ScaleType.FIT_XY);
        this.imgView.setVisibility(0);
    }

    private void setContentText() {
        this.textView.setMovementMethod(new ScrollingMovementMethod());
        if (this.screenModel.getContentText() == null || this.screenModel.getContentText().equalsIgnoreCase("")) {
            this.textView.setVisibility(8);
            return;
        }
        this.componentHelper.setMainTextView(getActivity(), this.textView, this.screenModel);
        this.textView.setVisibility(0);
    }

    private void setDataList() {
        ArrayList tableItems = checkTableItems(this.screenModel).getTableItems();
        if (tableItems.size() > 0) {
            this.validNavItems = getValidItems(tableItems);
            return;
        }
        LayoutParams layoutParams = (LayoutParams) this.scrollView.getLayoutParams();
        ScreenHelper screenHelper = this.screenHelper;
        layoutParams.setMargins(0, 0, 0, ScreenHelper.getHeightForDevice(5, getActivity()));
        this.scrollView.setLayoutParams(layoutParams);
    }
}
