package com.carles.carlesbasic.data.entity;

import com.google.gson.annotations.SerializedName;

public class PoiResponseDto {

    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("address")
    public String address;
    @SerializedName("transport")
    public String transport;
    @SerializedName("email")
    public String email;
    @SerializedName("geocoordinates")
    public String geocoordinates;
    @SerializedName("description")
    public String description;
    @SerializedName("phone")
    public String phone;

}
