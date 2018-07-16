package com.carles.carlesbasic.poi.data.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PoiRealmObject extends RealmObject {

    public static final String ID = "id";

    @PrimaryKey
    public String id;
    public String title;
    public String address;
    public String transport;
    public String description;
    public String email;
    public String phone;
    public String geocoordinates;

}
