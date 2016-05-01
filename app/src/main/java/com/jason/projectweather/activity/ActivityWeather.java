package com.jason.projectweather.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jason.projectweather.R;
import com.jason.projectweather.model.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ActivityWeather extends AppCompatActivity {

    ProgressBar pB;

    TextView tvCurrentCity;
    TextView tvDate;
    TextView tvCurWeather;
    TextView tvCurWind;
    TextView tvCurtemp;
    TextView tvCurPM;

    TextView tvDay1Week;
    TextView tvDay1Weather;
    TextView tvDay1Temp;

    TextView tvDay2Week;
    TextView tvDay2Weather;
    TextView tvDay2Temp;

    TextView tvDay3Week;
    TextView tvDay3Weather;
    TextView tvDay3Temp;

    ImageView imgCurWea;
    ImageView imgWea1;
    ImageView imgWea2;
    ImageView imgWea3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_activity_weather);

        setTextViews();

        pB = (ProgressBar)findViewById(R.id.progressBar);

        String strCurrentHandle = getIntent().getStringExtra("CountryName");

        if(strCurrentHandle != null){
            handleData(strCurrentHandle);
        }else
            handleData("北京");
    }

    void setTextViews() {
        tvCurrentCity = (TextView)findViewById(R.id.currentCity);
        tvDate = (TextView)findViewById(R.id.currentDate);
        tvCurWeather = (TextView)findViewById(R.id.currentWeather);
        tvCurtemp = (TextView)findViewById(R.id.temperature);
        tvCurWind = (TextView)findViewById(R.id.currentWind);
        tvCurPM = (TextView)findViewById(R.id.pm25);

        tvDay1Week = (TextView)findViewById(R.id.day1Date);
        tvDay1Weather = (TextView)findViewById(R.id.day1Weather);
        tvDay1Temp = (TextView)findViewById(R.id.day1Temp);

        tvDay2Week = (TextView)findViewById(R.id.day2Date);
        tvDay2Weather = (TextView)findViewById(R.id.day2Weather);
        tvDay2Temp = (TextView)findViewById(R.id.day2Temp);

        tvDay3Week = (TextView)findViewById(R.id.day3Date);
        tvDay3Weather = (TextView)findViewById(R.id.day3Weather);
        tvDay3Temp = (TextView)findViewById(R.id.day3Temp);

        imgCurWea = (ImageView)findViewById(R.id.pictureUrl);
        imgWea1 = (ImageView)findViewById(R.id.picutureUrlDay1);
        imgWea2 = (ImageView)findViewById(R.id.picutureUrlDay2);
        imgWea3 = (ImageView)findViewById(R.id.picutureUrlDay3);
    }

    public void handleData(String strCity) {
        pB.setVisibility(View.VISIBLE);
        String strUrl = null;
        try {
            String strLocation = URLEncoder.encode(strCity,"UTF-8");

            String strAk = "BIyDEEec7pLQBlMSGAfN2GlG";
            strUrl = String.format("http://api.map.baidu.com/telematics/v3/weather?location=%s&ak=%s",strLocation,strAk);
//	          strUrl = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&ak=BIyDEEec7pLQBlMSGAfN2GlG";

            new ProgressTask().execute(strUrl);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private class ProgressTask extends AsyncTask<String,Integer,WeatherInfo> {

        WeatherInfo info = new WeatherInfo();
        @Override
        protected WeatherInfo doInBackground(String... params) {

            StringBuffer buffer = new StringBuffer();

            try {

                //URL url = new URL(URLEncoder.encode(params[0].toString(),"gb2312").replaceAll("%3A", ":").replaceAll("%2F", "/"));

                URL url = new URL(params[0]);
                HttpURLConnection httpUrlCon = (HttpURLConnection) url.openConnection();

                httpUrlCon.setDoOutput(false);
                httpUrlCon.setDoInput(true);
                httpUrlCon.setUseCaches(false);

                httpUrlCon.setRequestMethod("GET");
                httpUrlCon.connect();

                InputStream inputStream = httpUrlCon.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                //InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

                try {
                    parser.setInput(inputStream ,"UTF-8");
                    int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT)
                    {
                        switch(eventType)
                        {
                            case XmlPullParser.START_TAG:
                                String strTagName = parser.getName();
                                if (strTagName.equalsIgnoreCase("currentCity"))
                                    info.setStrCurCity(parser.nextText());
                                if(strTagName.equalsIgnoreCase("date"))
                                {
                                    if(info.getStrCurDate() == null)
                                        info.setStrCurDate(parser.nextText());
                                    else if(info.getStrCurWeek() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String NewStr=strTemp.substring(strTemp.indexOf("：")+1, strTemp.lastIndexOf(")"));
                                        info.setStrCurWeek(NewStr.replace('℃','°'));
                                    }
                                    else if(info.getStrWeek1() == null)
                                        info.setStrWeek1(parser.nextText());
                                    else if(info.getStrWeek2() == null)
                                        info.setStrWeek2(parser.nextText());
                                    else if(info.getStrWeek3() == null)
                                        info.setStrWeek3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("Weather"))
                                {
                                    if(info.getStrCurWeather() == null)
                                        info.setStrCurWeather(parser.nextText());
                                    else if(info.getStrWeather1() == null)
                                        info.setStrWeather1(parser.nextText());
                                    else if(info.getStrWeather2() == null)
                                        info.setStrWeather2(parser.nextText());
                                    else if(info.getStrWeather3() == null)
                                        info.setStrWeather3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("temperature"))
                                {
                                    if(info.getStrCurTemp() == null)
                                        info.setStrCurTemp(parser.nextText());
                                    else if(info.getStrTemp1() == null)
                                        info.setStrTemp1(parser.nextText());
                                    else if(info.getStrTemp2() == null)
                                        info.setStrTemp2(parser.nextText());
                                    else if(info.getStrTemp3() == null)
                                        info.setStrTemp3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("wind"))
                                {
                                    if(info.getStrCurWind() == null)
                                        info.setStrCurWind(parser.nextText());
                                    else if(info.getStrWind1() == null)
                                        info.setStrWind1(parser.nextText());
                                    else if(info.getStrWind2() == null)
                                        info.setStrWind2(parser.nextText());
                                    else if(info.getStrWind3() == null)
                                        info.setStrWind3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("pm25"))
                                {
                                    if(info.getStrPM() == null) {
                                        String str = parser.nextText();
                                        if(!str.isEmpty())
                                            info.setStrCurPM("PM2.5: " + str);
                                        else
                                            info.setStrCurPM("PM2.5: Unknown");
                                    }
                                }
                                else if(strTagName.equalsIgnoreCase("dayPictureUrl"))
                                {
                                    if(info.getStrImgUrl() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl(strsub);
                                    }
                                    else if(info.getStrImgUrl1() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl1(strsub);
                                    }
                                    else if(info.getStrImgUrl2() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl2(strsub);
                                    }
                                    else if(info.getStrImgUrl3() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl3(strsub);
                                    }
                                }
//                                else if(strTagName.equalsIgnoreCase("title"))
//                                    strList.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("zs"))
//                                    strList.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("tipt"))
//                                    strList.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("des"))
//                                    strList.add(parser.nextText());
                                //if (strTagName.equalsIgnoreCase("weather_data"))
                                //info.setStrCurTemp(parser.nextText());
                        }
                        eventType = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                inputStream.close();
                httpUrlCon.disconnect();

            }catch (IOException e){
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPostExecute(WeatherInfo info) {
            super.onPostExecute(info);

            tvCurrentCity.setText(info.getStrCurCity());
            tvDate.setText(info.getStrCurDate());
            tvCurWeather.setText(info.getStrCurWeather());
            tvCurtemp.setText(info.getStrCurWeek());
            tvCurWind.setText(info.getStrCurWind());
            tvCurPM.setText(info.getStrPM());

            tvDay1Week.setText(info.getStrWeek1());
            tvDay1Weather.setText(info.getStrWeather1());
            tvDay1Temp.setText(info.getStrTemp1());

            tvDay2Week.setText(info.getStrWeek2());
            tvDay2Weather.setText(info.getStrWeather2());
            tvDay2Temp.setText(info.getStrTemp2());

            tvDay3Week.setText(info.getStrWeek3());
            tvDay3Weather.setText(info.getStrWeather3());
            tvDay3Temp.setText(info.getStrTemp3());

            String strUrl = "WeatherPic/"+info.getStrImgUrl();
            String strUrl1 = "WeatherPic/"+info.getStrImgUrl1();
            String strUrl2 = "WeatherPic/"+info.getStrImgUrl2();
            String strUrl3 = "WeatherPic/"+info.getStrImgUrl3();

            InputStream is;
            try {
                is = getAssets().open(strUrl);
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                imgCurWea.setImageBitmap(bitmap);

                is = getAssets().open(strUrl1);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea1.setImageBitmap(bitmap);

                is = getAssets().open(strUrl2);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea2.setImageBitmap(bitmap);

                is = getAssets().open(strUrl3);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea3.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


            pB.setVisibility(View.INVISIBLE);
        }
    }
}
