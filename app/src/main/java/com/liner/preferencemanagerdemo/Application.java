package com.liner.preferencemanagerdemo;

import com.liner.preferencemanager.PreferenceManager;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
    }
}
