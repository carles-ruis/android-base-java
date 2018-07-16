package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.data.repository.PoiRepository;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BasePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

public class PoiDetailPresenter extends BasePresenter<PoiDetailContract.View> implements PoiDetailContract.Presenter {

    private final PoiRepository poiRepository;
    String id;

    @Inject
    public PoiDetailPresenter(PoiRepository poiRepository, @Named("uiScheduler") Scheduler uiScheduler, @Named("processScheduler") Scheduler processScheduler) {
        super(uiScheduler, processScheduler);
        this.poiRepository = poiRepository;
    }

    @Override
    public void initialize(String id) {
        this.id = id;
    }

    @Override
    public void onViewCreated(PoiDetailContract.View view) {
        super.onViewCreated(view);
        getPoiDetail();
    }

    private void getPoiDetail() {
        view.showProgress();
        addDisposable(poiRepository.getPoiDetail(id).subscribeOn(processScheduler).observeOn(uiScheduler)
            .subscribe(this::onGetPoiDetailSuccess, this::onGetPoiDetailError));
    }

    void onGetPoiDetailSuccess(Poi poi) {
        view.hideProgress();
        view.displayPoiDetail(poi);
    }

    void onGetPoiDetailError(Throwable e) {
        view.showError(errorMessageFactory.getErrorMessageId(e), () -> getPoiDetail());
    }
}