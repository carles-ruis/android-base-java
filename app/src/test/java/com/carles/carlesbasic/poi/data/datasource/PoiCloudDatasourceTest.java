package com.carles.carlesbasic.poi.data.datasource;

import com.carles.carlesbasic.poi.data.entity.PoiListResponseDto;
import com.carles.carlesbasic.poi.data.entity.PoiResponseDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static com.carles.carlesbasic.poi.TestHelper.createPoiListResponseDto;
import static com.carles.carlesbasic.poi.TestHelper.createPoiResponseDto;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PoiCloudDatasourceTest {

    private PoiCloudDatasource datasource;
    @Mock
    private PoiLocalDatasource poiLocalDatasource;
    @Mock
    private PoiMapper poiMapper;
    @Mock
    private PoiService poiService;

    @Before
    public void setup() {
        datasource = new PoiCloudDatasource(poiMapper, poiLocalDatasource, poiService);
    }

    @Test
    public void getPoiList_shouldRequestApi() {
        PoiListResponseDto dto = createPoiListResponseDto();
        when(poiService.getPoiList()).thenReturn(Single.just(dto));
        datasource.getPoiList().test();
        verify(poiService).getPoiList();
        verify(poiMapper).dtoToModel(eq(dto));
    }

    @Test
    public void getPoiDetail_shouldRequestApi() {
        PoiResponseDto dto = createPoiResponseDto();
        when(poiService.getPoi("id")).thenReturn(Single.just(dto));
        datasource.getPoiDetail("id").test();
        verify(poiService).getPoi("id");
        verify(poiMapper).dtoToModel(eq(dto));
    }

}
