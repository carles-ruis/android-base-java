package com.carles.carlesbasic.data.datasource;

import com.carles.carlesbasic.data.entity.PoiResponseDto;
import com.carles.carlesbasic.data.entity.PoiListResponseDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PoiService {

    @GET("points")
    Observable<PoiListResponseDto> getPoiList();

    @GET("points/{id}")
    Observable<PoiResponseDto> getPoi(@Path("id") String id);
}
