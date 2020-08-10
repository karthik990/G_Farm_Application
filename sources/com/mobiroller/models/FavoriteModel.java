package com.mobiroller.models;

public class FavoriteModel {
    private int mContentType;
    private GalleryModel mGalleryModel;
    private boolean mIsScreen;
    private RssModel mRssModel;
    private int mScreenId;
    private String mScreenIdString;
    private String mScreenImage;
    private ScreenModel mScreenModel;
    private String mScreenTitle;
    private String mScreenType;
    private String mSubScreenType;

    public final class ContentTypes {
        public static final int TYPE_GALLERY = 2;
        public static final int TYPE_RSS = 1;

        public ContentTypes() {
        }
    }

    public FavoriteModel(ScreenModel screenModel, String str, String str2, String str3) {
        this.mScreenIdString = str3;
        this.mScreenModel = screenModel;
        this.mScreenType = str;
        this.mSubScreenType = str2;
        this.mScreenTitle = screenModel.getTitle();
        if (screenModel.getMainImageName() != null) {
            this.mScreenImage = screenModel.getMainImageName().getImageURL();
        }
        this.mIsScreen = true;
    }

    public String getScreenId() {
        String str = this.mScreenIdString;
        if (str != null) {
            return str;
        }
        return String.valueOf(this.mScreenId);
    }

    public FavoriteModel(RssModel rssModel) {
        this.mRssModel = rssModel;
        this.mContentType = 1;
    }

    public FavoriteModel(GalleryModel galleryModel) {
        this.mGalleryModel = galleryModel;
        this.mContentType = 2;
    }

    public String getmSubScreenType() {
        return this.mSubScreenType;
    }

    public void setmSubScreenType(String str) {
        this.mSubScreenType = str;
    }

    public boolean IsScreen() {
        return this.mIsScreen;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public ScreenModel getScreenModel() {
        return this.mScreenModel;
    }

    public RssModel getRssModel() {
        return this.mRssModel;
    }

    public GalleryModel getGalleryModel() {
        return this.mGalleryModel;
    }

    public String getScreenType() {
        return this.mScreenType;
    }

    public String getScreenTitle() {
        return this.mScreenTitle;
    }

    public String getScreenImage() {
        return this.mScreenImage;
    }
}
