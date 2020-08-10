package com.airbnb.lottie;

public class LottieImageAsset {
    private final String dirName;
    private final String fileName;
    private final int height;

    /* renamed from: id */
    private final String f75id;
    private final int width;

    public LottieImageAsset(int i, int i2, String str, String str2, String str3) {
        this.width = i;
        this.height = i2;
        this.f75id = str;
        this.fileName = str2;
        this.dirName = str3;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getId() {
        return this.f75id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDirName() {
        return this.dirName;
    }
}
