package com.jason.projectweather.model;

/**
 * Created by Jason on 2016/4/26.
 */
public class Province {
    private int id;
    private String strProvinceName;
    private String strProvinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrProvinceName() {
        return strProvinceName;
    }

    public void setStrProvinceName(String strProvinceName) {
        this.strProvinceName = strProvinceName;
    }

    public String getStrProvinceCode() {
        return strProvinceCode;
    }

    public void setStrProvinceCode(String strProvinceCode) {
        this.strProvinceCode = strProvinceCode;
    }
}
