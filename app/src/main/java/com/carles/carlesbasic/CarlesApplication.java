package com.carles.carlesbasic;

import com.carles.carlesbasic.di.ApplicationComponent;
import com.carles.carlesbasic.di.ApplicationModule;
import com.carles.carlesbasic.di.DaggerApplicationComponent;
import com.carles.carlesbasic.di.ServiceModule;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class CarlesApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initializeApplicationComponent();
        Realm.init(this);
    }

    protected ApplicationComponent initializeApplicationComponent() {
        return DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .serviceModule(new ServiceModule())
            .build();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((CarlesApplication)context.getApplicationContext()).applicationComponent;
    }
}
