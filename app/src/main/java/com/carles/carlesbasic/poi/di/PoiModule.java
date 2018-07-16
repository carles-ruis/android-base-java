package com.carles.carlesbasic.poi.di;

import com.carles.carlesbasic.di.ActivityScope;
import com.carles.carlesbasic.poi.ui.PoiDetailContract;
import com.carles.carlesbasic.poi.ui.PoiDetailPresenter;
import com.carles.carlesbasic.poi.ui.PoiListContract;
import com.carles.carlesbasic.poi.ui.PoiListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PoiModule {

    @Provides
    @ActivityScope
    PoiListContract.Presenter providePoiListPresenter(PoiListPresenter poiListPresenter) {
        return poiListPresenter;
    }

    @Provides
    @ActivityScope
    PoiDetailContract.Presenter providePoiDetailPresenter(PoiDetailPresenter poiDetailPresenter) {
        return poiDetailPresenter;
    }

}
