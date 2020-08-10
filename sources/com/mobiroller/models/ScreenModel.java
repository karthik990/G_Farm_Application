package com.mobiroller.models;

import androidx.fragment.app.Fragment;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ScreenModel implements Serializable {
    private String URL;
    private String about;
    private String accountName;
    private String apiDomain;
    private String appStoreLink;
    private Boolean assignLinksManually;
    private String aveAccountModule;
    private ImageModel backgroundImageName;
    private ImageModel catalogImageName;
    private int cellControlWidth;
    private int cellTitleWidth;
    private String city;
    private String contentFontName;
    private float contentFontSize;
    private String contentHeader;
    private String contentHtml;
    private String contentText;
    private ColorModel contentTextBackColor;
    private ColorModel contentTextColor;
    private int contentTextHeight;
    private String description;
    private String email;
    private String facebook;
    private ArrayList<FlagModel> flags;
    private transient Fragment fragment;
    private String googlePlayLink;
    private String googleplus;
    private ArrayList<GalleryModel> images;
    public Boolean isCacheEnabled;
    private boolean isDownloadable;
    private boolean isRssContent;
    private String lattitude;
    private int layoutType;
    private String linkedin;
    private String localizedRssLink;
    private String localizedURL;
    private String longitude;
    private int mainImageHeight;
    private ImageModel mainImageName;
    private String mapType;
    private String pageNo;
    private String phoneNumber;
    private String radioBroadcastLink;
    private ArrayList<String> requiredFields;
    private String rssLink;
    private String screenType;
    private String scriptPath;

    /* renamed from: sd */
    public String f1504sd;
    private Boolean showProgress;
    public Boolean showToolbar;
    private String showUserLocation;
    private String subTitle;
    private String submitAvailable;
    private ArrayList<CategoryModel> tableCategories;
    private ImageModel tableCellBackground;
    private ImageModel tableCellBackground1;
    private ImageModel tableCellBackground2;
    private String tableFontName;
    private int tableFontSize;
    private ArrayList<TableItemsModel> tableItems;
    private int tableRowHeight;
    private ColorModel tableTextColor;
    private String title;
    private String tvBroadcastLink;
    private String tweetCount;
    private String twitter;
    private String type;
    private String updateDate;
    private String userName;
    private int viewAreaInMeters;
    private String website;
    public String youtubeChannelID;
    private String youtubeChannelId;

    public boolean isHideToolbar() {
        Boolean bool = this.showToolbar;
        if (bool == null) {
            return false;
        }
        return !bool.booleanValue();
    }

    public void setHideToolbar(boolean z) {
        this.showToolbar = Boolean.valueOf(z);
    }

    public String getLocalizedURL() {
        return this.localizedURL;
    }

    public void setLocalizedURL(String str) {
        this.localizedURL = str;
    }

    public Boolean getShowProgress() {
        Boolean bool = this.showProgress;
        return bool == null ? Boolean.valueOf(true) : bool;
    }

    public void setShowProgress(Boolean bool) {
        this.showProgress = bool;
    }

    public int getLayoutType() {
        int i = this.layoutType;
        if (i == 0 || i > 6) {
            return 1;
        }
        return i;
    }

    public void setLayoutType(int i) {
        this.layoutType = i;
    }

    public ArrayList<String> getRequiredFields() {
        return this.requiredFields;
    }

    public void setRequiredFields(ArrayList<String> arrayList) {
        this.requiredFields = arrayList;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment2) {
        this.fragment = fragment2;
    }

    public Boolean isAssignLinksManually() {
        return this.assignLinksManually;
    }

    public void setAssignLinksManually(boolean z) {
        this.assignLinksManually = Boolean.valueOf(z);
    }

    public String getYoutubeChannelId() {
        return this.youtubeChannelId;
    }

    public void setYoutubeChannelId(String str) {
        this.youtubeChannelId = str;
    }

    public String getTableFontName() {
        return this.tableFontName;
    }

    public void setTableFontName(String str) {
        this.tableFontName = str;
    }

    public boolean isRssContent() {
        return this.isRssContent;
    }

    public void setRssContent(boolean z) {
        this.isRssContent = z;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String str) {
        this.subTitle = str;
    }

    public ImageModel getCatalogImageName() {
        return this.catalogImageName;
    }

    public void setCatalogImageName(ImageModel imageModel) {
        this.catalogImageName = imageModel;
    }

    public ArrayList<CategoryModel> getTableCategories() {
        return this.tableCategories;
    }

    public void setTableCategories(ArrayList<CategoryModel> arrayList) {
        this.tableCategories = arrayList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public ImageModel getBackgroundImageName() {
        return this.backgroundImageName;
    }

    public void setBackgroundImageName(ImageModel imageModel) {
        this.backgroundImageName = imageModel;
    }

    public String getURL() {
        return this.URL;
    }

    public void setURL(String str) {
        this.URL = str;
    }

    public ImageModel getMainImageName() {
        return this.mainImageName;
    }

    public void setMainImageName(ImageModel imageModel) {
        this.mainImageName = imageModel;
    }

    public int getMainImageHeight() {
        return this.mainImageHeight;
    }

    public void setMainImageHeight(int i) {
        this.mainImageHeight = i;
    }

    public ColorModel getContentTextColor() {
        return this.contentTextColor;
    }

    public void setContentTextColor(ColorModel colorModel) {
        this.contentTextColor = colorModel;
    }

    public float getContentFontSize() {
        return this.contentFontSize;
    }

    public void setContentFontSize(float f) {
        this.contentFontSize = f;
    }

    public String getContentFontName() {
        return this.contentFontName;
    }

    public void setContentFontName(String str) {
        this.contentFontName = str;
    }

    public int getContentTextHeight() {
        return this.contentTextHeight;
    }

    public void setContentTextHeight(int i) {
        this.contentTextHeight = i;
    }

    public ColorModel getContentTextBackColor() {
        return this.contentTextBackColor;
    }

    public void setContentTextBackColor(ColorModel colorModel) {
        this.contentTextBackColor = colorModel;
    }

    public ColorModel getTableTextColor() {
        return this.tableTextColor;
    }

    public void setTableTextColor(ColorModel colorModel) {
        this.tableTextColor = colorModel;
    }

    public int getTableFontSize() {
        return this.tableFontSize;
    }

    public void setTableFontSize(int i) {
        this.tableFontSize = i;
    }

    public int getTableRowHeight() {
        return this.tableRowHeight;
    }

    public void setTableRowHeight(int i) {
        this.tableRowHeight = i;
    }

    public ImageModel getTableCellBackground() {
        return this.tableCellBackground;
    }

    public void setTableCellBackground(ImageModel imageModel) {
        this.tableCellBackground = imageModel;
    }

    public ArrayList<TableItemsModel> getTableItems() {
        return this.tableItems;
    }

    public void setTableItems(ArrayList<TableItemsModel> arrayList) {
        this.tableItems = arrayList;
    }

    public String getContentText() {
        return this.contentText;
    }

    public void setContentText(String str) {
        this.contentText = str;
    }

    public String getContentHtml() {
        return this.contentHtml;
    }

    public void setContentHtml(String str) {
        this.contentHtml = str;
    }

    public String getAppStoreLink() {
        return this.appStoreLink;
    }

    public void setAppStoreLink(String str) {
        this.appStoreLink = str;
    }

    public String getGooglePlayLink() {
        return this.googlePlayLink;
    }

    public void setGooglePlayLink(String str) {
        this.googlePlayLink = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getRssLink() {
        if (getLocalizedRssLink() == null) {
            return this.rssLink;
        }
        String localizedTitle = UtilManager.localizationHelper().getLocalizedTitle(this.localizedRssLink);
        return (localizedTitle == null || localizedTitle.isEmpty()) ? this.rssLink : localizedTitle;
    }

    public void setRssLink(String str) {
        this.rssLink = str;
    }

    public boolean isDownloadable() {
        return this.isDownloadable;
    }

    public void setDownloadable(boolean z) {
        this.isDownloadable = z;
    }

    public ArrayList<GalleryModel> getImages() {
        return this.images;
    }

    public void setImages(ArrayList<GalleryModel> arrayList) {
        this.images = arrayList;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getScreenType() {
        return this.screenType;
    }

    public void setScreenType(String str) {
        this.screenType = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(String str) {
        this.pageNo = str;
    }

    public String getTweetCount() {
        return this.tweetCount;
    }

    public void setTweetCount(String str) {
        this.tweetCount = str;
    }

    public String getApiDomain() {
        return this.apiDomain;
    }

    public void setApiDomain(String str) {
        this.apiDomain = str;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String str) {
        this.accountName = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public String getTvBroadcastLink() {
        return this.tvBroadcastLink;
    }

    public void setTvBroadcastLink(String str) {
        this.tvBroadcastLink = str;
    }

    public String getRadioBroadcastLink() {
        return this.radioBroadcastLink;
    }

    public void setRadioBroadcastLink(String str) {
        this.radioBroadcastLink = str;
    }

    public String getAveAccountModule() {
        return this.aveAccountModule;
    }

    public void setAveAccountModule(String str) {
        this.aveAccountModule = str;
    }

    public int getCellTitleWidth() {
        return this.cellTitleWidth;
    }

    public void setCellTitleWidth(int i) {
        this.cellTitleWidth = i;
    }

    public int getCellControlWidth() {
        return this.cellControlWidth;
    }

    public void setCellControlWidth(int i) {
        this.cellControlWidth = i;
    }

    public String getSubmitAvailable() {
        return this.submitAvailable;
    }

    public void setSubmitAvailable(String str) {
        this.submitAvailable = str;
    }

    public String getMapType() {
        return this.mapType;
    }

    public void setMapType(String str) {
        this.mapType = str;
    }

    public String getShowUserLocation() {
        return this.showUserLocation;
    }

    public void setShowUserLocation(String str) {
        this.showUserLocation = str;
    }

    public String getLattitude() {
        return this.lattitude;
    }

    public void setLattitude(String str) {
        this.lattitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public int getViewAreaInMeters() {
        return this.viewAreaInMeters;
    }

    public void setViewAreaInMeters(int i) {
        this.viewAreaInMeters = i;
    }

    public ArrayList<FlagModel> getFlags() {
        return this.flags;
    }

    public void setFlags(ArrayList<FlagModel> arrayList) {
        this.flags = arrayList;
    }

    public String getScriptPath() {
        return this.scriptPath;
    }

    public void setScriptPath(String str) {
        this.scriptPath = str;
    }

    public ImageModel getTableCellBackground1() {
        return this.tableCellBackground1;
    }

    public void setTableCellBackground1(ImageModel imageModel) {
        this.tableCellBackground1 = imageModel;
    }

    public ImageModel getTableCellBackground2() {
        return this.tableCellBackground2;
    }

    public void setTableCellBackground2(ImageModel imageModel) {
        this.tableCellBackground2 = imageModel;
    }

    public String getContentHeader() {
        return this.contentHeader;
    }

    public void setContentHeader(String str) {
        this.contentHeader = str;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String str) {
        this.about = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public void setTwitter(String str) {
        this.twitter = str;
    }

    public String getFacebook() {
        return this.facebook;
    }

    public void setFacebook(String str) {
        this.facebook = str;
    }

    public String getLinkedin() {
        return this.linkedin;
    }

    public void setLinkedin(String str) {
        this.linkedin = str;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String str) {
        this.website = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getGoogleplus() {
        return this.googleplus;
    }

    public void setGoogleplus(String str) {
        this.googleplus = str;
    }

    public void localizeCategoryModels(LocalizationHelper localizationHelper) {
        ArrayList<CategoryModel> arrayList = this.tableCategories;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CategoryModel categoryModel = (CategoryModel) it.next();
                categoryModel.setCategoryTitle(localizationHelper.getLocalizedTitle(categoryModel.getCategoryTitle()));
                categoryModel.setCategorySubTitle(localizationHelper.getLocalizedTitle(categoryModel.getCategorySubTitle()));
            }
        }
    }

    public String getLocalizedRssLink() {
        return this.localizedRssLink;
    }

    public void setLocalizedRssLink(String str) {
        this.localizedRssLink = str;
    }
}
