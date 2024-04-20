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
        initDatas();
    }

    //从服务端拉tab数据
    private void initTab(){

    }
    //从服务端拉取短剧数据
    private void initDatas(){

    }

}
