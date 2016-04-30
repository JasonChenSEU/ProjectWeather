package com.jason.projectweather.activity;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.projectweather.R;
import com.jason.projectweather.db.WeatherDB;
import com.jason.projectweather.model.City;
import com.jason.projectweather.model.Country;
import com.jason.projectweather.model.Province;
import com.jason.projectweather.util.DataUtil;
import com.jason.projectweather.util.HttpCallBackListener;
import com.jason.projectweather.util.HttpUtil;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;

public class ActivityChooseData extends AppCompatActivity {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTRY = 2;

    private ProgressDialog progressDialog;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB db;
    private List<String> dataList = new ArrayList<>();


    private List<Province> provinceList;
    private List<City> cityList;
    private List<Country> countryList;

    private Province selectedProvince;
    private City selectedCity;

    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.datachoose);

        textView = (TextView) findViewById(R.id.title_text);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, dataList);
        listView.setAdapter(adapter);

        db = WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    queryCountries();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces() {
        provinceList = db.loadProvince();
        if(provinceList.size() > 0){
            dataList.clear();
            for (Province p:
                 provinceList) {
                dataList.add(p.getStrProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText("China");
            currentLevel = LEVEL_PROVINCE;
        }else
            queryFromServer(null, "province");
    }


    private void queryCities() {
        cityList = db.loadCity(selectedProvince.getId());
        if(cityList.size() > 0){
            dataList.clear();
            for(City city: cityList){
                dataList.add(city.getStrCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedProvince.getStrProvinceName());
            currentLevel = LEVEL_CITY;
        }else
            queryFromServer(selectedProvince.getStrProvinceCode(), "city");
    }

    private void queryCountries() {
        countryList = db.loadCountry(selectedCity.getId());
        if(countryList.size() > 0){
            dataList.clear();
            for(Country country: countryList){
                dataList.add(country.getStrCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedCity.getStrCityName());
            currentLevel = LEVEL_COUNTRY;
        }else
            queryFromServer(selectedCity.getStrCityCode(), "country");
    }

    private void queryFromServer(final String code, final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city"+ code + ".xml";
        }else{
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if("province".equals(type)){
                    result = DataUtil.handleProvincesResponse(db,response);
                }else if("city".equals(type)){
                    result = DataUtil.handleCitiesResponse(db,response,selectedProvince.getId());
                }else if("country".equals(type)){
                    result = DataUtil.handleCountryResponse(db,response,selectedCity.getId());
                }

                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("country".equals(type)){
                                queryCountries();
                            }
                        }
                    });
                }
            }

            public void onError(Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ActivityChooseData.this, "Error loading...", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void closeProgressDialog() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(ActivityChooseData.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(currentLevel == LEVEL_COUNTRY)
            queryCities();
        else if(currentLevel == LEVEL_CITY)
            queryProvinces();
        else
            finish();
    }
}

