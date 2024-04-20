package com.bagoshort.core;

import android.app.Application;

import com.bagoshort.core.utils.PreferenceUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtil.init(this);
        AppConfig.getInstance().init();
    }

}
