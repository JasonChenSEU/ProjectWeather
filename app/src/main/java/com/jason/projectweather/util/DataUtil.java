package com.jason.projectweather.util;

import android.text.TextUtils;

import com.jason.projectweather.db.WeatherDB;
import com.jason.projectweather.model.City;
import com.jason.projectweather.model.Country;
import com.jason.projectweather.model.Province;

/**
 * Created by Jason on 2016/4/27.
 */
public class DataUtil {
    public synchronized static boolean handleProvincesResponse(WeatherDB db, String response){
        if(!TextUtils.isEmpty(response)){
            String[] provinces = response.split(",");
            for (String strProvince :
                    provinces) {
                String[] array = strProvince.split("\\|");
                if(array.length == 2){
                    Province p = new Province();
                    p.setStrProvinceCode(array[0]);
                    p.setStrProvinceName(array[1]);
                    db.saveProvince(p);
                }
            }
            return true;
        }
        return false;
    }


    public synchronized static boolean handleCitiesResponse(WeatherDB db, String response,int provinceID){
        if(!TextUtils.isEmpty(response)){
            String[] cities = response.split(",");
            for (String strCity :
                    cities) {
                String[] array = strCity.split("\\|");
                if(array.length == 2){
                    City p = new City();
                    p.setStrCityCode(array[0]);
                    p.setStrCityName(array[1]);
                    p.setId_Province(provinceID);
                    db.saveCity(p);
                }
            }
            return true;
        }
        return false;
    }

    public synchronized static boolean handleCountryResponse(WeatherDB db, String response,int cityID){
        if(!TextUtils.isEmpty(response)){
            String[] countries = response.split(",");
            for (String strCountry :
                    countries) {
                String[] array = strCountry.split("\\|");
                if(array.length == 2){
                    Country p = new Country();
                    p.setStrCountryCode(array[0]);
                    p.setStrCountryName(array[1]);
                    p.setCity_id(cityID);
                    db.saveCountry(p);
                }
            }
            return true;
        }
        return false;
    }
}
