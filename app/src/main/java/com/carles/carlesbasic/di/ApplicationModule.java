package com.carles.carlesbasic.di;

import com.carles.common.utils.RxBus;
import com.carles.common.utils.SharedPreferencesHelper;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    SharedPreferencesHelper provideSharedPreferencesHelper(Context context) {
        return new SharedPreferencesHelper(context);
    }

    @Provides
    @Singleton
    @Named("uiScheduler")
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named("processScheduler")
    Scheduler provideProcessScheduler() {
        return Schedulers.io();
    }
}
