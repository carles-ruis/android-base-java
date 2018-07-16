package com.carles.carlesbasic.poi.data.datasource;

import com.carles.carlesbasic.poi.data.entity.PoiListResponseDto;
import com.carles.carlesbasic.poi.data.entity.PoiResponseDto;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PoiService {

    @GET("points")
    Single<PoiListResponseDto> getPoiList();

    @GET("points/{id}")
    Single<PoiResponseDto> getPoi(@Path("id") String id);
}
