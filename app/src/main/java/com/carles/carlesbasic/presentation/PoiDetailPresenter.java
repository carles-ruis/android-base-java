package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.interactor.GetPoiDetailInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.InteractorObserver;
import com.carles.common.presentation.BasePresenter;

public class PoiDetailPresenter extends BasePresenter<PoiDetailContract.View> implements PoiDetailContract.Presenter {

    private final GetPoiDetailInteractor getPoiDetailInteractor;
    String id;

    public PoiDetailPresenter(GetPoiDetailInteractor getPoiDetailInteractor) {
        this.getPoiDetailInteractor = getPoiDetailInteractor;
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

    @Override
    public void onViewDestroyed() {
        getPoiDetailInteractor.dispose();
        super.onViewDestroyed();
    }

    public void getPoiDetail() {
        view.showProgress();
        getPoiDetailInteractor.execute(GetPoiDetailInteractor.Params.create(id),
            new InteractorObserver<Poi>() {
                @Override
                public void onNext(Poi poi) {
                    super.onNext(poi);
                    view.hideProgress();
                    view.displayPoiDetail(poi);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.showError(errorMessageFactory.getErrorMessageId(e), () -> getPoiDetail());
                }
            });
    }
}
