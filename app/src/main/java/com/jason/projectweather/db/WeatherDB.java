package com.jason.projectweather.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jason.projectweather.model.City;
import com.jason.projectweather.model.Country;
import com.jason.projectweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/4/26.
 */
public class WeatherDB {

    private final String DB_NAME = "WeatherDataBase";
    private final int VERSION = 1;

    public static WeatherDB weatherDB;
    private SQLiteDatabase database;

    private WeatherDB(Context context){
        WeatherDBTableHelper helper = new WeatherDBTableHelper(context,DB_NAME,null, VERSION);
        database = helper.getWritableDatabase();
    }

    public static synchronized WeatherDB getInstance(Context context){
        if(weatherDB != null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getStrProvinceName());
            values.put("province_code",province.getStrProvinceCode());
            database.insert("Province", null,values);
        }
    }

    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getStrCityName());
            values.put("city_code",city.getStrCityCode());
            values.put("province_id",city.getId_Province());
            database.insert("City", null, values);
        }
    }

    public void saveCountry(Country country){
        if(country != null){
            ContentValues values = new ContentValues();
            values.put("country_name",country.getStrCountryName());
            values.put("country_code",country.getStrCountryCode());
            values.put("city_id",country.getCity_id());
            database.insert("Province", null, values);
        }
    }

    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<>();
        Cursor cursor = database.query("Province",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setStrProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setStrProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            }while(cursor.moveToNext());
        }
        if(cursor != null)
            cursor.close();
        return list;
    }

    public List<City> loadCity(){
        List<City> list = new ArrayList<>();
        Cursor cursor = database.query("City",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setStrCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setStrCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setId_Province(cursor.getInt(cursor.getColumnIndex("province_id")));
            }while(cursor.moveToNext());
        }
        if(cursor != null)
            cursor.close();
        return list;
    }

    public List<Country> loadCountry(){
        List<Country> list = new ArrayList<>();
        Cursor cursor = database.query("Country",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setStrCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setStrCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
            }while(cursor.moveToNext());
        }
        if(cursor != null)
            cursor.close();
        return list;
    }

}
