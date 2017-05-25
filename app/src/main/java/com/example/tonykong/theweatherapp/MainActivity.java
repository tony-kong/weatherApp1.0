package com.example.tonykong.theweatherapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tonykong.theweatherapp.Model.MainWeather;
import com.example.tonykong.theweatherapp.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.tonykong.theweatherapp.Utils.Reader;
import com.google.gson.stream.JsonReader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView cityText, tempText, descriptionText, maxMinText, humidityText, windText,pressureText, sunriseText, sunsetText, updateText;
    private ImageView weatherImage;

    MainWeather mainWeather = new MainWeather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            //use CityPreference if there is no saved instance
            new WeatherTask().execute(Utils.apiRequest(new CityPreference(this).getCity()));
        }

        //set default values
        cityText = (TextView)findViewById(R.id.cityText);
        tempText = (TextView)findViewById(R.id.tempText);
        descriptionText = (TextView)findViewById(R.id.descriptionText);
        maxMinText = (TextView)findViewById(R.id.maxMinText);
        humidityText = (TextView)findViewById(R.id.humidityText);
        windText = (TextView)findViewById((R.id.windText));
        pressureText = (TextView)findViewById(R.id.pressureText);
        sunriseText = (TextView)findViewById(R.id.sunriseText);
        sunsetText = (TextView)findViewById(R.id.sunsetText);
        updateText = (TextView)findViewById(R.id.updateText);
        weatherImage = (ImageView)findViewById(R.id.weatherImage);
        Button updateButton = (Button) findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WeatherTask().execute(Utils.apiRequest(new CityPreference(MainActivity.this).getCity()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handles action bar clicks
        int id = item.getItemId();
        if (id == R.id.change_cityID){
            showInputDialog();
        }
        return false;
    }

    private void showInputDialog() {
        //build Change City button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change City");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update weather data
                new WeatherTask().execute(Utils.apiRequest(input.getText().toString()));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private class WeatherTask extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please Wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            Reader read = new Reader();
            stream = read.getDataStream(params[0]);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("city not found") || s.equals("")){
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<MainWeather>(){}.getType();
            mainWeather = gson.fromJson(s,mType);
            pd.dismiss();

            cityText.setText(String.format("%s,%s",mainWeather.getName(),mainWeather.getSys().getCountry()));
            tempText.setText(String.format(Locale.CANADA,"%d °C",(int)mainWeather.getMain().getTemp()));
            descriptionText.setText(String.format(Locale.CANADA, "%s", mainWeather.getWeather().get(0).getCapitalDescription()));
            maxMinText.setText(String.format(Locale.CANADA,"%d°C/%d°C",(int)mainWeather.getMain().getTemp_max(),(int)mainWeather.getMain().getTemp_min()));
            humidityText.setText(String.format(Locale.CANADA,"Humidity: %d%%", mainWeather.getMain().getHumidity()));
            pressureText.setText(String.format(Locale.CANADA,"Pressure: %dMB", (int)mainWeather.getMain().getPressure()));
            sunriseText.setText(String.format("Sunrise: %s", Utils.unixTimeStampToDateTime(mainWeather.getSys().getSunrise())));
            windText.setText(String.format(Locale.CANADA,"Wind: %dKM/H %s", (int)mainWeather.getWind().getSpeed(), mainWeather.getWind().getDirection()));
            sunsetText.setText(String.format("Sunset: %s", Utils.unixTimeStampToDateTime(mainWeather.getSys().getSunset())));
            updateText.setText(String.format("Last Updated: %s", Utils.getDateNow()));
            setBackground(mainWeather.getWeather().get(0).getId(),mainWeather.getSys().getSunrise() * 1000, mainWeather.getSys().getSunset() * 1000);

            //Picasso.with(MainActivity.this)
            //       .load(Utils.getImage(mainWeather.getWeather().get(0).getIcon()))
            //       .into(weatherImage);
            new CityPreference(MainActivity.this).setCity(String.format("%s,%s",mainWeather.getName(),mainWeather.getSys().getCountry()));
        }

        private void setBackground(int fullId, double sunrise, double sunset){
            int id = fullId / 100;
            if(fullId == 800) {
                long currentTime = new Date().getTime();
                if(currentTime >= sunrise && currentTime < sunset) {
                    weatherImage.setImageResource(R.drawable.sunny);
                } else {
                    weatherImage.setImageResource(R.drawable.night);
                }
            } else {
                switch (id) {
                    case 2 : weatherImage.setImageResource(R.drawable.thunder);
                        break;
                    case 3 : weatherImage.setImageResource(R.drawable.drizzle);
                        break;
                    case 7 : weatherImage.setImageResource(R.drawable.foggy);
                        break;
                    case 8 : weatherImage.setImageResource(R.drawable.cloudy);
                        break;
                    case 6 : weatherImage.setImageResource(R.drawable.snowy);
                        break;
                    case 5 : weatherImage.setImageResource(R.drawable.rainy);
                        break;
                }
            }
        }
    }
}
