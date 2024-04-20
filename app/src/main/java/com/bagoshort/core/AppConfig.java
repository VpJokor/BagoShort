package com.bagoshort.core;

public class AppConfig {
    private AppConfig() {}
    private static AppConfig _instance;

    public static AppConfig getInstance() {
        if (_instance == null) _instance = new AppConfig();
        return _instance;
    }

}
