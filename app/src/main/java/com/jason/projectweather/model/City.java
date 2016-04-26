package com.jason.projectweather.model;

/**
 * Created by Jason on 2016/4/26.
 */
public class City {
    private int id;
    private String strCityName;
    private String strCityCode;
    private int id_Province;


    public int getId_Province() {
        return id_Province;
    }

    public void setId_Province(int id_Province) {
        this.id_Province = id_Province;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrCityName() {
        return strCityName;
    }

    public void setStrCityName(String strCityName) {
        this.strCityName = strCityName;
    }

    public String getStrCityCode() {
        return strCityCode;
    }

    public void setStrCityCode(String strCityCode) {
        this.strCityCode = strCityCode;
    }
}
