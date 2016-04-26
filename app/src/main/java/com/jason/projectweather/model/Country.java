package com.jason.projectweather.model;

/**
 * Created by Jason on 2016/4/26.
 */
public class Country {
    private int id;
    private String strCountryName;
    private String strCountryCode;
    private int City_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrCountryName() {
        return strCountryName;
    }

    public void setStrCountryName(String strCountryName) {
        this.strCountryName = strCountryName;
    }

    public String getStrCountryCode() {
        return strCountryCode;
    }

    public void setStrCountryCode(String strCountryCode) {
        this.strCountryCode = strCountryCode;
    }

    public int getCity_id() {
        return City_id;
    }

    public void setCity_id(int city_id) {
        City_id = city_id;
    }
}
