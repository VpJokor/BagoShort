package com.bagoshort.core;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.getInstance().init();
    }

}
