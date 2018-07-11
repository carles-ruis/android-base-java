package com.carles.carlesbasic.domain.interactor;

import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.Interactor;

import io.reactivex.Observable;

public class GetPoiDetailInteractor extends Interactor<Poi, GetPoiDetailInteractor.Params> {

    private final PoiRepository poiRepository;

    public GetPoiDetailInteractor(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @Override
    protected Observable<Poi> createObservableUsecase(Params params) {
        return poiRepository.getPoiDetail(params.id);
    }

    public static class Params {
        private final String id;

        private Params(String id) {
            this.id = id;
        }

        public static Params create(String id) {
            return new Params(id);
        }
    }
}
