package com.carles.carlesbasic.domain.interactor;

import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.Interactor;

import java.util.List;

import io.reactivex.Observable;

public class GetPoiListInteractor extends Interactor<List<Poi>, Void> {

    private final PoiRepository poiRepository;

    public GetPoiListInteractor(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @Override
    protected Observable<List<Poi>> createObservableUsecase(Void nothing) {
        return poiRepository.getPoiList();
    }

}
