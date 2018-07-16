package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BaseContract;

import java.util.List;

public interface PoiListContract {

    interface View extends BaseContract.View {
        void displayPoiList(List<Poi> poiList);

        void navigateToPoiDetail(String id);
    }

    interface Presenter extends BaseContract.Presenter<PoiListContract.View> {
         void onPoiClicked(Poi poi);
    }
}
