package com.bagoshort.core.data;

public class BannerData {
    public String img;
    public String content;

    public int bannerType;
    public static final int BANNER_TYPE_VIDEO = 0;
    public static final int BANNER_TYPE_WEB = 1;

    public BannerData() {
    }

    public BannerData(String img, String content) {
        this.img = img;
        this.content = content;
    }
}
