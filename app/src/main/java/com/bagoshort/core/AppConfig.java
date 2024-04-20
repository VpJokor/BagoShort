package com.bagoshort.core;

public class AppConfig {
    private AppConfig() {}
    private static AppConfig _instance;

    public static AppConfig getInstance() {
        if (_instance == null) _instance = new AppConfig();
        return _instance;
    }

    public void init(){
        initTab();
        initBanner();
        initDatas();
    }

    //从服务端拉tab数据
    private void initTab(){

    }

    //从服务器获取banner
    private void initBanner(){

    }

    //从服务端拉取短剧数据
    //检查本地数据版本，从服务端拉新数据
    private void initDatas(){

    }

}
