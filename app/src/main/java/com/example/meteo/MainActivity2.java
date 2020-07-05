package com.example.meteo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public
class MainActivity2 extends AppCompatActivity {

        /*
    Propriétés
 */
        //Text view
    private TextView myTextMeteoTemperature;
    private TextView myTextMeteoHumidity;
    private TextView myTextViewWind;
    private TextView myTextViewCiel;
        //Images
    private ImageView mImageViewTemp;
    private ImageView mImageViewWind;

    private String message;
    private int windSpeed;
    //60 par minutes
    private String key = "62b2e4102b4c85a060579e4bfc86d71c";

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );

        init ( );
        Intent intent = getIntent ( );
        message = intent.getStringExtra ( MainActivity.EXTRA_MESSAGE );
        try {
            MyMeteo ( message );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } catch ( JSONException e ) {
            e.printStackTrace ( );
        }
    }

    public
    void init () {
        //Temperature
        myTextMeteoTemperature = findViewById ( R.id.textViewTemperature );
        //Humidity
        myTextMeteoHumidity = findViewById ( R.id.textViewHumidity );
        //Ciel
        myTextViewCiel = findViewById ( R.id.textViewCouvertureCiel );
        //Vent
        myTextViewWind = findViewById ( R.id.textViewWind );
        //Images Temperature
        mImageViewTemp = findViewById ( R.id.imageViewTemp );
        //Image wind(vent)
        mImageViewWind = findViewById ( R.id.imageViewWind );
    }

    public
    void MyMeteo ( String city ) throws IOException, JSONException {

        String myurl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid="+key;
        URL url = new URL ( myurl );
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ( );
        urlConnection.connect ( );

        try {
            InputStream inputStream = urlConnection.getInputStream ( );
            String result = InputStreamOperations.InputStreamToString ( inputStream );
            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject ( result );
            JSONObject main = jsonObject.getJSONObject ( "main" );
            JSONArray weather = jsonObject.getJSONArray ( "weather" );
            JSONObject wind = jsonObject.getJSONObject ( "wind" );

        /*
            le tableau weather
         */
            String temp = "";
            for (int i = 0; i < weather.length ( ); i++) {
                JSONObject object = weather.getJSONObject ( i );
                temp = object.getString ( "description" );
            }

            int temperature = main.getInt ( "temp" );
            int humidity = main.getInt ( "humidity" );
            temperature = temperature - 273;

            myTextMeteoTemperature.setText ( "La temperature est de " + temperature + "° à "+city);
            myTextMeteoHumidity.setText ( "Humidity : " + humidity + "%" );


        /*
            Tableau couverture du ciel
         */
            if (temp.contains ( "overcast clouds" )) {
                myTextViewCiel.setText ( "Le ciel est Nuageux" );
                mImageViewTemp.setImageResource ( R.drawable.overcastclouds );
                Log.i ( "Temp", "Le temp est Nuageux" );
            } else if (temp.contains ( "clear sky" )) {
                myTextViewCiel.setText ( "Le ciel clair" );
                mImageViewTemp.setImageResource ( R.drawable.clearsky );
            } else if (temp.contains ( "few clouds" )) {
                myTextViewCiel.setText ( "Le ciel avec quelques nuages" );
                mImageViewTemp.setImageResource ( R.drawable.fewclouds );
            } else if (temp.contains ( "broken clouds" )) {
                myTextViewCiel.setText ( "Le ciel avec nuages brisés" );
                mImageViewTemp.setImageResource ( R.drawable.brokenclouds );
            }
            else if (temp.contains ( "light intensity shower rain" )) {
            myTextViewCiel.setText ( "pluie légère" );
            mImageViewTemp.setImageResource ( R.drawable.lightintensityshowerrain );
        }
            /*
            Wind speed
         */
            windSpeed = wind.getInt ( "speed" );
            myTextViewWind.setText ( "Vitesse du vent " + windSpeed + " " + "km/h" );

            if (windSpeed < 5 & windSpeed < 9) {
                mImageViewWind.setImageResource ( R.drawable.vent01 );
            } else if (windSpeed < 10 & windSpeed < 19) {
                mImageViewWind.setImageResource ( R.drawable.vent02 );
            } else if (windSpeed < 20 & windSpeed < 37) {
                mImageViewWind.setImageResource ( R.drawable.vent03 );
            } else if (windSpeed > 25 & windSpeed > 46) {
                mImageViewWind.setImageResource ( R.drawable.vent04 );
            }

        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }
}