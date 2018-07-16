package com.carles.carlesbasic.poi.data.datasource;

import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.data.datasource.BaseCloudDatasource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class PoiCloudDatasource extends BaseCloudDatasource {

    private final PoiMapper poiMapper;
    private final PoiLocalDatasource poiLocalDatasource;
    private final PoiService poiService;

    @Inject
    public PoiCloudDatasource(PoiMapper poiMapper, PoiLocalDatasource poiLocalDatasource, PoiService poiService) {
        this.poiMapper = poiMapper;
        this.poiLocalDatasource = poiLocalDatasource;
        this.poiService = poiService;
    }

    public Single<List<Poi>> getPoiList() {
        return poiService.getPoiList().map(poiMapper::dtoToModel);
    }

    public Single<Poi> getPoiDetail(String id) {
        return poiService.getPoi(id).map(poiMapper::dtoToModel).doOnSuccess(poi -> poiLocalDatasource.persist(poi));
    }

}
