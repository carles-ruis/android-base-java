package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.presentation.BaseContract;

public interface PoiDetailContract {

    interface View extends BaseContract.View {
        void displayPoiDetail(Poi poi);
    }

    interface Presenter extends BaseContract.Presenter<PoiDetailContract.View> {
        void initialize(String id);
    }
}
