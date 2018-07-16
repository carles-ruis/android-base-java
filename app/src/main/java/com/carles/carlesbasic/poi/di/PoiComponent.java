package com.carles.carlesbasic.poi.di;

import com.carles.carlesbasic.di.ActivityScope;
import com.carles.carlesbasic.di.ApplicationComponent;
import com.carles.carlesbasic.poi.ui.PoiDetailActivity;
import com.carles.carlesbasic.poi.ui.PoiListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = PoiModule.class)
public interface PoiComponent {
    void inject(PoiListActivity activity);

    void inject(PoiDetailActivity activity);
}
