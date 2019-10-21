package com.example.videoapplication.test;

import android.app.Application;

import com.example.videoapplication.BuildConfig;

import timber.log.Timber;

public class FFmpegExample extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
