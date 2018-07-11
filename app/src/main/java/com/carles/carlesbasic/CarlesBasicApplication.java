package com.carles.carlesbasic;

import com.carles.common.utils.SharedPreferencesHelper;

import android.app.Application;

import io.realm.Realm;

public class CarlesBasicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesHelper.initialize(this);
        Realm.init(this);
    }
}
