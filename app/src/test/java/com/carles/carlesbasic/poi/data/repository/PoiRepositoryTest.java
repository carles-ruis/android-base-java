package com.carles.carlesbasic.poi.data.repository;

import com.carles.carlesbasic.poi.data.datasource.PoiCloudDatasource;
import com.carles.carlesbasic.poi.data.datasource.PoiLocalDatasource;
import com.carles.carlesbasic.poi.model.Poi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

import static com.carles.carlesbasic.poi.TestHelper.createPoiList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PoiRepositoryTest {

    private PoiRepository poiRepository;
    @Mock
    private PoiLocalDatasource poiLocalDatasource;
    @Mock
    private PoiCloudDatasource poiCloudDatasource;

    @Before
    public void setup() {
        poiRepository = new PoiRepository(poiLocalDatasource, poiCloudDatasource);
    }

    @Test
    public void getPoiList_shouldGetFromLocalIfAvailable() {
        List<Poi> localList = createPoiList("local");
        List<Poi> cloudList = createPoiList("cloud");
        when(poiLocalDatasource.getPoiList()).thenReturn(Maybe.just(localList));
        when(poiCloudDatasource.getPoiList()).thenReturn(Single.just(cloudList));
        poiRepository.getPoiList().test().assertValue(localList);
    }

    @Test
    public void getPoiList_shouldGetFromCloudIfLocalNotAvailable() {
        List<Poi> cloudList = createPoiList("cloud");
        when(poiLocalDatasource.getPoiList()).thenReturn(Maybe.empty());
        when(poiCloudDatasource.getPoiList()).thenReturn(Single.just(cloudList));
        poiRepository.getPoiList().test().assertValue(cloudList);
    }

    @Test
    public void getPoiList_shouldHandleCloudError() {
        Throwable error = new Throwable();
        when(poiLocalDatasource.getPoiList()).thenReturn(Maybe.empty());
        when(poiCloudDatasource.getPoiList()).thenReturn(Single.error(error));
        poiRepository.getPoiList().test().assertError(error);
    }

    @Test
    public void getPoiDetail_shouldAccessDatasources() {
        when(poiLocalDatasource.getPoiDetail("id")).thenReturn(Maybe.empty());
        when(poiCloudDatasource.getPoiDetail("id")).thenReturn(Single.error(new Throwable()));
        poiRepository.getPoiDetail("id");
        verify(poiLocalDatasource).getPoiDetail(eq("id"));
        verify(poiCloudDatasource).getPoiDetail(eq("id"));
    }

}
