package com.carles.carlesbasic.poi.data.datasource;

import com.carles.carlesbasic.poi.data.entity.PoiListResponseDto;
import com.carles.carlesbasic.poi.data.entity.PoiRealmObject;
import com.carles.carlesbasic.poi.data.entity.PoiResponseDto;
import com.carles.carlesbasic.poi.model.Poi;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.carles.carlesbasic.poi.TestHelper.createEmptyPoiListResponseDto;
import static com.carles.carlesbasic.poi.TestHelper.createPoiListResponseDto;
import static com.carles.carlesbasic.poi.TestHelper.createPoiResponseDto;
import static junit.framework.Assert.assertEquals;

public class PoiMapperTest {

    private PoiMapper poiMapper = new PoiMapper();

    @Test
    public void dtoToModel_shouldTransformPoiListResponseDto() {
        assertEquals(Collections.emptyList(), poiMapper.dtoToModel((PoiListResponseDto) null));
        assertEquals(Collections.emptyList(), poiMapper.dtoToModel(createEmptyPoiListResponseDto()));

        PoiListResponseDto dto = createPoiListResponseDto();
        List<Poi> model = poiMapper.dtoToModel(dto);
        assertEquals(dto.list.size(), model.size());
        assertEquals(dto.list.get(0).id, model.get(0).id);
    }

    @Test
    public void dtoToModel_shouldTransformPoiResponseDto() {
        PoiResponseDto dto = createPoiResponseDto();
        Poi model = poiMapper.dtoToModel(dto);
        assertEquals(dto.id, model.id);
        assertEquals(dto.title, model.title);
        assertEquals(dto.transport, model.transport);
        assertEquals(dto.email, model.email);
        assertEquals(dto.phone, model.phone);

        dto.transport = "";
        assertEquals(null, poiMapper.dtoToModel(dto).transport);
        dto.email = "null";
        assertEquals(null, poiMapper.dtoToModel(dto).email);
        dto.phone = "undefined";
        assertEquals(null, poiMapper.dtoToModel(dto).phone);
    }

    @Test
    public void modelToRealmObject_shouldTransform() {
        Poi model = new Poi();
        model.id = String.valueOf(System.currentTimeMillis());
        PoiRealmObject realmObject = poiMapper.modelToRealmObject(model);
        assertEquals(model.id, realmObject.id);
    }

    @Test
    public void realmObjectToModel_shouldTransform() {
        PoiRealmObject realmObject = new PoiRealmObject();
        realmObject.id = String.valueOf(System.currentTimeMillis());
        Poi model = poiMapper.realmObjectToModel(realmObject);
        assertEquals(realmObject.id, model.id);
    }
}
