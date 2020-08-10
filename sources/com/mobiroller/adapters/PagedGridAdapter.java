package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.helpers.FontSizeHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.Item;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.Page;
import com.mobiroller.models.PagedGridModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.PagedDragDropGrid;
import com.mobiroller.widget.PageIndicator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PagedGridAdapter extends PagedDragDropGridAdapter {
    private String backgroundImgUrl = null;
    /* access modifiers changed from: private */
    public Activity context;
    public int counter;
    private int fontSizeOrder;
    /* access modifiers changed from: private */
    public PagedDragDropGrid gridView;
    public JSONParser jParserNew;
    public LocalizationHelper localizationHelper;
    private PageIndicator mPager;
    /* access modifiers changed from: private */
    public PagedGridModel pagedGridModel;
    private List<Page> pages = new ArrayList();
    public ProgressViewHelper progressViewHelper;
    public ScreenHelper screenHelper;

    public int deleteDropZoneLocation() {
        return 2;
    }

    public boolean showRemoveDropZone() {
        return false;
    }

    public PagedGridAdapter(Activity activity, PagedDragDropGrid pagedDragDropGrid, PagedGridModel pagedGridModel2, PageIndicator pageIndicator, String str, ScreenHelper screenHelper2, ProgressViewHelper progressViewHelper2, JSONParser jSONParser) {
        int i;
        this.context = activity;
        this.fontSizeOrder = new FontSizeHelper(activity).getFontOrder();
        this.gridView = pagedDragDropGrid;
        this.mPager = pageIndicator;
        if (activity.getResources().getInteger(R.integer.locale_mirror_flip) == 180) {
            pagedDragDropGrid.setRotation(180.0f);
            pagedDragDropGrid.setRotationX(180.0f);
            this.mPager.setRotationY(180.0f);
        }
        this.pagedGridModel = pagedGridModel2;
        this.backgroundImgUrl = str;
        this.screenHelper = screenHelper2;
        this.localizationHelper = UtilManager.localizationHelper();
        this.progressViewHelper = progressViewHelper2;
        this.jParserNew = jSONParser;
        progressViewHelper2.dismiss();
        int size = pagedGridModel2.getNavItems().size();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < pagedGridModel2.getNavItems().size()) {
            NavigationItemModel navigationItemModel = (NavigationItemModel) pagedGridModel2.getNavItems().get(i2);
            i2++;
            arrayList.add(new Item((long) i2, this.localizationHelper.getLocalizedTitle(navigationItemModel.getTitle()), navigationItemModel.getIconImage() != null ? navigationItemModel.getIconImage().getImageURL() : "null"));
        }
        int i3 = 0;
        while (size > 0) {
            Page page = new Page();
            ArrayList arrayList2 = new ArrayList();
            if (pagedGridModel2.getElementPerPage() < size) {
                i = i3;
                for (int i4 = 0; i4 < pagedGridModel2.getElementPerPage(); i4++) {
                    arrayList2.add(arrayList.get(i));
                    i++;
                }
            } else {
                int i5 = i3;
                for (int i6 = 0; i6 < size; i6++) {
                    arrayList2.add(arrayList.get(i));
                    i5 = i + 1;
                }
            }
            i3 = i;
            page.setItems(arrayList2);
            this.pages.add(page);
            size -= pagedGridModel2.getElementPerPage();
        }
        this.mPager.setDotCount(pageCount());
    }

    public int pageCount() {
        return this.pages.size();
    }

    private List<Item> itemsInPage(int i) {
        if (this.pages.size() > i) {
            return ((Page) this.pages.get(i)).getItems();
        }
        return Collections.emptyList();
    }

    public View view(final int i, final int i2) {
        RelativeLayout relativeLayout = new RelativeLayout(this.context);
        ImageView imageView = new ImageView(this.context);
        Item item = getItem(i, i2);
        ImageManager.loadImageView(this.context, item.getImageUrl(), imageView);
        imageView.setPadding(0, Math.round((float) ((this.pagedGridModel.getStParam() * 10) / 100)), 0, 0);
        LayoutParams layoutParams = new LayoutParams(this.pagedGridModel.getStParam(), Math.round((float) ((this.pagedGridModel.getStParam() * 2) / 3)));
        layoutParams.addRule(10);
        relativeLayout.addView(imageView, layoutParams);
        TextView textView = new TextView(this.context);
        textView.setTag("text");
        textView.setText(item.getTitle());
        textView.setTextColor(this.pagedGridModel.getColor());
        int i3 = this.fontSizeOrder;
        if (i3 == 0) {
            textView.setTextSize(2, 12.0f);
        } else if (i3 == 1) {
            textView.setTextSize(2, 16.0f);
        } else if (i3 == 2) {
            textView.setTextSize(2, 20.0f);
        } else if (i3 == 3) {
            textView.setTextSize(2, 22.0f);
        }
        textView.setGravity(81);
        textView.setPadding(0, 0, 0, Math.round((float) (this.pagedGridModel.getStParam() / 10)));
        textView.setMaxWidth(this.pagedGridModel.getStParam() - 30);
        textView.setEllipsize(TruncateAt.END);
        textView.setSingleLine();
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(14);
        relativeLayout.setMinimumHeight(this.pagedGridModel.getStParam());
        relativeLayout.setMinimumWidth(this.pagedGridModel.getStParam());
        relativeLayout.setLayoutParams(new LayoutParams(this.pagedGridModel.getStParam(), this.pagedGridModel.getStParam()));
        String str = this.backgroundImgUrl;
        if (str != null) {
            ImageManager.loadBackgroundImage(str, relativeLayout);
        } else {
            relativeLayout.setBackgroundColor(0);
        }
        if (this.context.getResources().getInteger(R.integer.locale_mirror_flip) == 180) {
            relativeLayout.setRotationY(180.0f);
        }
        if (i % 2 == 0) {
            setViewBackground(relativeLayout);
            relativeLayout.setClickable(true);
            relativeLayout.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return PagedGridAdapter.this.gridView.onLongClick(view);
                }
            });
        }
        relativeLayout.addView(textView, layoutParams2);
        ImageView imageView2 = new ImageView(this.context);
        imageView2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView2.setBackgroundResource(R.drawable.list_selector);
        imageView2.setClickable(true);
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PagedGridAdapter pagedGridAdapter = PagedGridAdapter.this;
                pagedGridAdapter.counter = pagedGridAdapter.rowCount() * PagedGridAdapter.this.columnCount() * i;
                if (Integer.valueOf(((NavigationItemModel) PagedGridAdapter.this.pagedGridModel.getNavItems().get(i2 + PagedGridAdapter.this.counter)).getAccountScreenID()).intValue() != -1) {
                    PagedGridAdapter.this.context.startActivity(new Intent(PagedGridAdapter.this.context, ActivityHandler.class).putExtra(ActivityHandler.INTENT_EXTRA_NAVIGATION_MODEL, (Serializable) PagedGridAdapter.this.pagedGridModel.getNavItems().get(i2 + PagedGridAdapter.this.counter)));
                    return;
                }
                view.setEnabled(false);
            }
        });
        relativeLayout.addView(imageView2);
        return relativeLayout;
    }

    private void setViewBackground(RelativeLayout relativeLayout) {
        String str = this.backgroundImgUrl;
        if (str != null) {
            ImageManager.loadBackgroundImage(str, relativeLayout);
        }
    }

    private Item getItem(int i, int i2) {
        return (Item) itemsInPage(i).get(i2);
    }

    public int rowCount() {
        return this.pagedGridModel.getNumOfRows();
    }

    public int columnCount() {
        return this.pagedGridModel.getNumOfColumns();
    }

    public int itemCountInPage(int i) {
        return itemsInPage(i).size();
    }

    private Page getPage(int i) {
        this.mPager.setActiveDot(i);
        return (Page) this.pages.get(i);
    }

    public void swapItems(int i, int i2, int i3) {
        this.mPager.setActiveDot(i);
        getPage(i).swapItems(i2, i3);
    }

    public void moveItemToPreviousPage(int i, int i2) {
        if (i > 0) {
            this.mPager.setActiveDot(i - 1);
        }
    }

    public void moveItemToNextPage(int i, int i2) {
        int i3 = i + 1;
        if (i3 < pageCount()) {
            this.mPager.setActiveDot(i3);
        }
    }

    public void deleteItem(int i, int i2) {
        getPage(i).deleteItem(i2);
    }
}
