package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.data.repository.PoiRepository;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

public class PoiListPresenter extends BasePresenter<PoiListContract.View> implements PoiListContract.Presenter {

    private final PoiRepository poiRepository;

    @Inject
    public PoiListPresenter(PoiRepository poiRepository, @Named("uiScheduler") Scheduler uiScheduler, @Named("processScheduler") Scheduler processScheduler) {
        super(uiScheduler, processScheduler);
        this.poiRepository = poiRepository;
    }

    @Override
    public void onViewCreated(PoiListContract.View view) {
        super.onViewCreated(view);
        getPoiList();
    }

     private void getPoiList() {
        view.showProgress();
        addDisposable(poiRepository.getPoiList().subscribeOn(processScheduler).observeOn(uiScheduler)
            .subscribe(this::onGetPoiListSuccess, this::onGetPoiListError));
     }

    void onGetPoiListSuccess(List<Poi> poiList) {
        view.hideProgress();
        view.displayPoiList(poiList);
    }

    void onGetPoiListError(Throwable e) {
        view.showError(errorMessageFactory.getErrorMessageId(e), () -> getPoiList());
    }

    @Override
    public void onPoiClicked(Poi poi) {
        view.navigateToPoiDetail(poi.id);
    }

}
