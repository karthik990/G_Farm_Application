package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NavigationModel implements Serializable {
    private ImageModel backgroundImage;
    private String fontName;
    private boolean isAppStartLogin;
    private boolean isLoginActive;
    private boolean isRegistrationActive;
    private ImageModel itemBackgroundImage;
    private ColorModel menuBackgroundColor;
    private ColorModel menuTextColor;
    public int menuType;
    private ColorModel navBarTintColor;
    private ArrayList<NavigationItemModel> navigationItems;
    private int numberOfColumns;
    private int numberOfRows;
    public int subMenuType;
    private int type;
    private String updateDate;

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public ImageModel getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(ImageModel imageModel) {
        this.backgroundImage = imageModel;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String str) {
        this.fontName = str;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(int i) {
        this.numberOfRows = i;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public void setNumberOfColumns(int i) {
        this.numberOfColumns = i;
    }

    public ImageModel getItemBackgroundImage() {
        return this.itemBackgroundImage;
    }

    public void setItemBackgroundImage(ImageModel imageModel) {
        this.itemBackgroundImage = imageModel;
    }

    public ColorModel getNavBarTintColor() {
        return this.navBarTintColor;
    }

    public void setNavBarTintColor(ColorModel colorModel) {
        this.navBarTintColor = colorModel;
    }

    public ColorModel getMenuTextColor() {
        return this.menuTextColor;
    }

    public void setMenuTextColor(ColorModel colorModel) {
        this.menuTextColor = colorModel;
    }

    public ColorModel getMenuBackgroundColor() {
        return this.menuBackgroundColor;
    }

    public void setMenuBackgroundColor(ColorModel colorModel) {
        this.menuBackgroundColor = colorModel;
    }

    public boolean isLoginActive() {
        return this.isLoginActive;
    }

    public void setLoginActive(boolean z) {
        this.isLoginActive = z;
    }

    public boolean isRegistrationActive() {
        return this.isRegistrationActive;
    }

    public void setRegistrationActive(boolean z) {
        this.isRegistrationActive = z;
    }

    public boolean isAppStartLogin() {
        return this.isAppStartLogin;
    }

    public void setAppStartLogin(boolean z) {
        this.isAppStartLogin = z;
    }

    public ArrayList<NavigationItemModel> getNavigationItems() {
        return this.navigationItems;
    }

    public void setNavigationItems(ArrayList<NavigationItemModel> arrayList) {
        this.navigationItems = arrayList;
    }

    public ImageModel getBackgroundImageName() {
        return this.backgroundImage;
    }

    public ImageModel getTableCellBackground() {
        return this.itemBackgroundImage;
    }
}
