package com.carles.carlesbasic.data.datasource;

import com.carles.carlesbasic.data.entity.PoiMapper;
import com.carles.carlesbasic.data.entity.PoiRealmObject;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.data.datasource.BaseLocalDatasource;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;

public class PoiLocalDatasource extends BaseLocalDatasource {

    private final PoiMapper poiMapper;

    public PoiLocalDatasource(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    public Observable<List<Poi>> getPoiList() {
        return Observable.empty();
    }

    public Observable<Poi> getPoiDetail(String id) {
        return Observable.create(source -> {
            if (isExpired(PoiRealmObject.class.getName(), id)) {
                source.onComplete();
                return;
            }

            Realm realm = Realm.getDefaultInstance();
            PoiRealmObject poiRealmObject = realm.where(PoiRealmObject.class).equalTo(PoiRealmObject.ID, id)
                .findFirst();
            if (poiRealmObject != null) {
                Poi poi = poiMapper.realmObjectToModel(poiRealmObject);
                source.onNext(poi);
            }
            source.onComplete();
            realm.close();
        });
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
