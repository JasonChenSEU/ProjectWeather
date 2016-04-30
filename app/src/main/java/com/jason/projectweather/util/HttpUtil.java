package com.jason.projectweather.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Jason on 2016/4/27.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    StringBuilder sb = new StringBuilder();
                    while(scanner.hasNext()){
                        sb.append(scanner.nextLine());
                    }
                    if(listener != null)
                        listener.onFinish(sb.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }
}

