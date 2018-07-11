package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.interactor.GetPoiListInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.InteractorObserver;
import com.carles.common.presentation.BasePresenter;

import java.util.List;

public class PoiListPresenter extends BasePresenter<PoiListContract.View> implements PoiListContract.Presenter {

    private final GetPoiListInteractor getPoiListInteractor;

    public PoiListPresenter(GetPoiListInteractor getPoiListInteractor) {
        this.getPoiListInteractor = getPoiListInteractor;
    }

    @Override
    public void onViewCreated(PoiListContract.View view) {
        super.onViewCreated(view);
        getPoiList();
    }

    @Override
    public void onViewDestroyed() {
        getPoiListInteractor.dispose();
        super.onViewDestroyed();
    }

     public void getPoiList() {
        view.showProgress();
        getPoiListInteractor.execute(null, new InteractorObserver<List<Poi>>() {
            @Override
            public void onNext(List<Poi> poiList) {
                super.onNext(poiList);
                view.hideProgress();
                view.displayPoiList(poiList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showError(errorMessageFactory.getErrorMessageId(e), () -> getPoiList());
            }
        });
    }

    @Override
    public void onPoiClicked(Poi poi) {
        view.navigateToPoiDetail(poi.id);
    }

}
