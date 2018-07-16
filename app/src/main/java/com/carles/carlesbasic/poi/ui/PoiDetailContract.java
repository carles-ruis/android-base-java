package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BaseContract;

public interface PoiDetailContract {

    interface View extends BaseContract.View {
        void displayPoiDetail(Poi poi);
    }

    interface Presenter extends BaseContract.Presenter<PoiDetailContract.View> {
        void initialize(String id);
    }
}
