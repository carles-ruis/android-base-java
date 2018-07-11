package com.carles.carlesbasic.data.repository;

import com.carles.carlesbasic.data.datasource.PoiCloudDatasource;
import com.carles.carlesbasic.data.datasource.PoiLocalDatasource;
import com.carles.carlesbasic.domain.model.Poi;

import java.util.List;

import io.reactivex.Observable;

public class PoiRepository {

    private final PoiLocalDatasource poiLocalDatasource;
    private final PoiCloudDatasource poiCloudDatasource;

    public PoiRepository(PoiLocalDatasource poiLocalDatasource, PoiCloudDatasource poiCloudDatasource) {
        this.poiLocalDatasource = poiLocalDatasource;
        this.poiCloudDatasource = poiCloudDatasource;
    }

    public Observable<List<Poi>> getPoiList() {
        return Observable.concat(poiLocalDatasource.getPoiList(), poiCloudDatasource.getPoiList()).firstOrError()
            .toObservable();
    }

    public Observable<Poi> getPoiDetail(String id) {
        return Observable.concat(poiLocalDatasource.getPoiDetail(id), poiCloudDatasource.getPoiDetail(id))
            .firstOrError().toObservable();
    }
}
