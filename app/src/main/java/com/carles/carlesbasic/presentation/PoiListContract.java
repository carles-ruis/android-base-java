package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.presentation.BaseContract;

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
