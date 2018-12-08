package com.carles.carlesbasic.poi.data.repository;

import com.carles.carlesbasic.poi.data.datasource.PoiCloudDatasource;
import com.carles.carlesbasic.poi.data.datasource.PoiLocalDatasource;
import com.carles.carlesbasic.poi.model.Poi;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class PoiRepository {

    private final PoiLocalDatasource poiLocalDatasource;
    private final PoiCloudDatasource poiCloudDatasource;

    @Inject
    public PoiRepository(PoiLocalDatasource poiLocalDatasource, PoiCloudDatasource poiCloudDatasource) {
        this.poiLocalDatasource = poiLocalDatasource;
        this.poiCloudDatasource = poiCloudDatasource;
    }

    public Single<List<Poi>> getPoiList() {
        return Maybe.concat(poiLocalDatasource.getPoiList(), poiCloudDatasource.getPoiList().toMaybe()).firstOrError();
    }

    public Single<Poi> getPoiDetail(String id) {
        return Maybe.concat(poiLocalDatasource.getPoiDetail(id), poiCloudDatasource.getPoiDetail(id).toMaybe()).firstOrError();
    }
}
