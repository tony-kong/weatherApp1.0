package com.example.tonykong.theweatherapp.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TonyKong on 12/26/2016.
 */

public class Reader {
    static String stream = "";

    public Reader(){
        stream = "";
    }

    public String getDataStream(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode() == 200){  //200 = OK code
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = in.readLine();
                while ((line!=null)){
                    stream = stream + line;
                    line = in.readLine();
                }
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return stream;
    }
}
