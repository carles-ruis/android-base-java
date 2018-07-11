package com.carles.carlesbasic.data.entity;

import com.carles.carlesbasic.domain.model.Poi;

import java.util.ArrayList;
import java.util.List;

public class PoiMapper {

    public List<Poi> dtoToModel(PoiListResponseDto dto) {
        if (dto == null || dto.list == null || dto.list.isEmpty()) {
            return new ArrayList<>();
        }

        List<Poi> model = new ArrayList<>();
        for (PoiResponseDto dtoItem : dto.list) {
            model.add(dtoToModel(dtoItem));
        }
        return model;
    }

    public Poi dtoToModel(PoiResponseDto dto) {
        Poi model = new Poi();
        model.id = dto.id;
        model.title = dto.title;
        model.address = dto.address;
        model.transport = sanitize(dto.transport);
        model.email = sanitize(dto.email);
        model.geocoordinates = dto.geocoordinates;
        model.description = dto.description;
        model.phone = sanitize(dto.phone);
        return model;
    }

    private String sanitize(String source) {
        if (source == null || source.isEmpty() || "null".equals(source) || "undefined".equals(source)) {
            return null;
        } else {
            return source;
        }
    }

    public PoiRealmObject modelToRealmObject(Poi model) {
        PoiRealmObject ro = new PoiRealmObject();
        ro.id = model.id;
        ro.title = model.title;
        ro.address = model.address;
        ro.transport = model.transport;
        ro.email = model.email;
        ro.geocoordinates = model.geocoordinates;
        ro.description = model.description;
        ro.phone = model.phone;
        return ro;
    }

    public Poi realmObjectToModel(PoiRealmObject ro) {
        Poi model = new Poi();
        model.id = ro.id;
        model.title = ro.title;
        model.address = ro.address;
        model.transport = ro.transport;
        model.email = ro.email;
        model.geocoordinates = ro.geocoordinates;
        model.description = ro.description;
        model.phone = ro.phone;
        return model;
    }

}
