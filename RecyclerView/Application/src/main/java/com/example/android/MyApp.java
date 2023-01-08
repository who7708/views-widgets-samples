package com.example.android;

import android.app.Application;

import com.example.android.utils.SnowflakeUtils;

/**
 * @author Chris
 * @version 1.0
 * @since 2023-01-08
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SnowflakeUtils.init(this);
    }
}
