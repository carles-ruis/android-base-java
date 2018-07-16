package com.carles.carlesbasic.di;

import com.carles.carlesbasic.poi.data.datasource.PoiService;
import com.carles.common.utils.RxBus;
import com.carles.common.utils.SharedPreferencesHelper;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {

    Context context();

    RxBus rxBus();

    SharedPreferencesHelper sharedPreferencesHelper();

    @Named("uiScheduler")
    Scheduler uiScheduler();

    @Named("processScheduler")
    Scheduler processScheduler();

    PoiService poiService();

}
