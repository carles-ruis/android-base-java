package com.carles.carlesbasic.data.datasource;

import com.carles.carlesbasic.data.entity.PoiMapper;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.data.datasource.BaseCloudDatasource;

import java.util.List;

import io.reactivex.Observable;

public class PoiCloudDatasource extends BaseCloudDatasource {

    private final PoiMapper poiMapper;
    private final PoiLocalDatasource poiLocalDatasource;
    PoiService poiService;

    public PoiCloudDatasource(PoiMapper poiMapper, PoiLocalDatasource poiLocalDatasource) {
        this.poiMapper = poiMapper;
        this.poiLocalDatasource = poiLocalDatasource;
        poiService = buildRetrofit().create(PoiService.class);
    }

    public Observable<List<Poi>> getPoiList() {
        return poiService.getPoiList().map(poiMapper::dtoToModel);
    }

    public Observable<Poi> getPoiDetail(String id) {
        return poiService.getPoi(id).map(poiMapper::dtoToModel).doOnNext(poi -> poiLocalDatasource.persist(poi));
    }

}
