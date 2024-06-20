package com.selfagent.core;

import android.app.Application;

import com.selfagent.core.utils.PreferenceUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtil.init(this);
        AppConfig.getInstance().init();
    }

}
