package com.example.meteo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import static com.example.meteo.R.id.textViewMyMeteo;
import static com.example.meteo.R.layout.activity_main;



public
class MainActivity extends AppCompatActivity{
    private
    EditText mEditText;
    private
    Button mButton;
    private
    TextView myMeteo;
    private
    String key = "62b2e4102b4c85a060579e4bfc86d71c";
    private
    String maVille = "Paris";

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( activity_main );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        myMeteo = findViewById ( R.id.textViewMyMeteo );
        mButton = findViewById ( R.id.button );
        mEditText = findViewById ( R.id.editText );


    }

    public void Validate( View view ){
        try {
            MyMeteo (mEditText.getText ().toString () );
        } catch ( JSONException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }


    public void MyMeteo( String city) throws IOException, JSONException {

        String myurl = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=62b2e4102b4c85a060579e4bfc86d71c";
        URL url = url = new URL ( myurl );
        HttpURLConnection urlConnection = urlConnection = (HttpURLConnection) url.openConnection ( );
        urlConnection.connect ( );

        try{
        InputStream inputStream = urlConnection.getInputStream ( );
        String result = InputStreamOperations.InputStreamToString ( inputStream );

        // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject ( result );
            JSONObject main  = jsonObject.getJSONObject("main");

            //String myCity = main.getString ( "name" );
            Integer temperature = main.getInt ( "temp" );
            temperature = temperature - 273;
            //
            String humidity = main.getString ( "humidity" );
            //Log.i ( "Main", "Main"+ temperature );
            myMeteo.setText ("Temperature : "+temperature.toString () + "°C");

    }
            catch ( Exception e ) {
                e.printStackTrace ( );
            }

        }
    }