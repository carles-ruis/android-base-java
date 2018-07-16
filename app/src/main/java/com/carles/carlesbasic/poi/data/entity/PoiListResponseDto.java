package com.carles.carlesbasic.poi.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoiListResponseDto {

    @SerializedName("list")
    public List<PoiResponseDto> list;
}
