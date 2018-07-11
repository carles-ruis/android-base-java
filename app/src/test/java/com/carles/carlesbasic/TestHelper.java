package com.carles.carlesbasic;

import com.carles.carlesbasic.data.entity.PoiListResponseDto;
import com.carles.carlesbasic.data.entity.PoiResponseDto;
import com.carles.carlesbasic.domain.model.Poi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public static Poi createPoi(String id) {
        Poi poi = new Poi();
        poi.id = id;
        return poi;
    }

    public static List<Poi> createPoiList(String...ids) {
        List<Poi> poiList = new ArrayList<>();
        for (String id : ids) {
            poiList.add(createPoi(id));
        }
        return poiList;
    }

    public static PoiListResponseDto createEmptyPoiListResponseDto() {
        PoiListResponseDto dto = new PoiListResponseDto();
        dto.list = new ArrayList<>();
        return dto;
    }

    public static PoiListResponseDto createPoiListResponseDto() {
        PoiListResponseDto dto = new PoiListResponseDto();
        dto.list = Arrays.asList(createPoiResponseDto(), createPoiResponseDto());
        return dto;
    }

    public static PoiResponseDto createPoiResponseDto() {
        PoiResponseDto dto = new PoiResponseDto();
        dto.id = String.valueOf(System.currentTimeMillis());
        dto.title = "title";
        dto.transport = "transport";
        dto.email = "email";
        dto.phone = "phone";
        return dto;
    }

}
