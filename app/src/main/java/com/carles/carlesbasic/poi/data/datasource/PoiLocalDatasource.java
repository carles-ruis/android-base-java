package com.carles.carlesbasic.poi.data.datasource;

import com.carles.carlesbasic.poi.data.entity.PoiRealmObject;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.data.datasource.BaseLocalDatasource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.realm.Realm;

public class PoiLocalDatasource extends BaseLocalDatasource {

    private final PoiMapper poiMapper;

    @Inject
    public PoiLocalDatasource(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    public Maybe<List<Poi>> getPoiList() {
        return Maybe.empty();
    }

    public Maybe<Poi> getPoiDetail(String id) {
        if (isExpired(PoiRealmObject.class.getName(), id)) {
            return Maybe.empty();
        }

        Realm realm = Realm.getDefaultInstance();
        PoiRealmObject poiRealmObject = realm.where(PoiRealmObject.class).equalTo(PoiRealmObject.ID, id).findFirst();
        Poi poi = poiRealmObject == null ? null : poiMapper.realmObjectToModel(poiRealmObject);
        realm.close();

        return poi == null ? Maybe.empty() : Maybe.just(poi);
    }

    public void persist(Poi poi) {
        PoiRealmObject realmObject = poiMapper.modelToRealmObject(poi);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
        realm.close();
        sharedPreferencesHelper
            .setCacheExpirationTime(PoiRealmObject.class.getName(), poi.id, calculateCacheExpirationTime());
    }

}
