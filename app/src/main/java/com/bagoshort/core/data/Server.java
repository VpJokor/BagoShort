package com.bagoshort.core.data;

import java.util.List;

public class Server {
    //最新版本号
    public long currentVersion;
    //服务是否可用
    public boolean isRun;
    //Banner控制
    public List<BannerData> banners;
    public List<Long> recoms;
}
