package com.selfagent.core.data;

public class BannerData {
    public String img;
    public String url;
    public long shortItemId;
    public int bannerType;
    public static final int BANNER_TYPE_VIDEO = 0;
    public static final int BANNER_TYPE_WEB = 1;

    public BannerData(String img, String url) {
        this.img = img;
        this.url = url;
        this.bannerType = BANNER_TYPE_WEB;
    }

    public BannerData(long shortItemId) {
        this.shortItemId = shortItemId;
        this.bannerType = BANNER_TYPE_VIDEO;
    }

}
