package com.carles.carlesbasic.data.datasource;

import com.carles.carlesbasic.data.entity.PoiMapper;
import com.carles.carlesbasic.data.entity.PoiRealmObject;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.utils.SharedPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import io.realm.Realm;
import io.realm.RealmQuery;

import static com.carles.carlesbasic.TestHelper.createPoi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Realm.class)
public class PoiLocalDatasourceTest {

    private PoiLocalDatasource datasource;
    @Mock
    private PoiMapper poiMapper;
    @Mock
    private Realm realm;
    @Mock
    private RealmQuery realmQuery;
    @Mock
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Before
    public void setup() {
        mockStatic(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(realm);
        when(realm.where(any())).thenReturn(realmQuery);

        datasource = new PoiLocalDatasource(poiMapper);
        Whitebox.setInternalState(datasource, "sharedPreferencesHelper", sharedPreferencesHelper);
    }

    @Test
    public void getPoiList_shouldReturnNoValues() {
        datasource.getPoiList().test().assertNoValues().assertComplete();
    }

    @Test
    public void getPoiDetail_shouldReturnNoValuesIfCacheExpired() throws Exception {
        PoiLocalDatasource spy = spy(datasource);
        PowerMockito.doReturn(true).when(spy, "isExpired", anyString(), anyString());
        spy.getPoiDetail("id").test().assertNoValues().assertComplete();
        verifyZeroInteractions(realm);
    }

    @Test
    public void getPoiDetail_shouldReturnNoValuesIfNoData() throws Exception {
        when(realmQuery.equalTo(anyString(), anyString())).thenReturn(realmQuery);
        when(realmQuery.findFirst()).thenReturn(null);

        PoiLocalDatasource spy = spy(datasource);
        PowerMockito.doReturn(false).when(spy, "isExpired", anyString(), anyString());
        spy.getPoiDetail("id").test().assertNoValues().assertComplete();
        verify(realm).where(eq(PoiRealmObject.class));
        verify(realm).close();
    }

    @Test
    public void getPoiDetail_shouldReturnStoredValues() throws Exception {
        PoiRealmObject realmObject = new PoiRealmObject();
        Poi poi = new Poi();
        when(realmQuery.equalTo(anyString(), anyString())).thenReturn(realmQuery);
        when(realmQuery.findFirst()).thenReturn(realmObject);
        when(poiMapper.realmObjectToModel(realmObject)).thenReturn(poi);

        PoiLocalDatasource spy = spy(datasource);
        PowerMockito.doReturn(false).when(spy, "isExpired", anyString(), eq("id"));
        spy.getPoiDetail("id").test().assertValue(poi).assertComplete();
        verify(realm).where(eq(PoiRealmObject.class));
        verify(realm).close();
    }

    @Test
    public void persist_shouldPersistToDatabase() {
        Poi poi = createPoi("id");
        PoiRealmObject realmObject = new PoiRealmObject();
        when(poiMapper.modelToRealmObject(poi)).thenReturn(realmObject);

        datasource.persist(poi);
        verify(realm).copyToRealmOrUpdate(realmObject);
        verify(realm).close();
        verify(sharedPreferencesHelper).setCacheExpirationTime(anyString(), eq("id"), anyLong());
    }

}
