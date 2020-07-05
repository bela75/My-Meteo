package com.example.meteo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import javax.xml.transform.Result;

import static com.example.meteo.R.layout.activity_main;
import static com.example.meteo.R.layout.activity_main2;


public
class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.AMB.android.Activity.extra.MESSAGE";
    private EditText mEditText;
    private Button mButton;
    private String message;

    private Button mButton2;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( activity_main );
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ( ).permitAll ( ).build ( );
        StrictMode.setThreadPolicy ( policy );

        /*
            initialisation des proprietes
         */
        mEditText = findViewById ( R.id.editTextVille );
        mButton = findViewById ( R.id.btnRecherche );

        /*
            Recuperer la valeur du champ edittext dans la variable message
         */
        message = String.valueOf ( mEditText.getText ( ) );

        mButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                Log.i ( "btn","Click button" );
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                mEditText = findViewById ( R.id.editTextVille );
                String message = mEditText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        } );
    }


    /*
         Methode initialisation des proprietes
      */
    public
    void init () {
        mEditText = findViewById ( R.id.editTextVille );
        mButton = findViewById ( R.id.btnRecherche );
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, Result.class);
        mEditText = findViewById ( R.id.editTextVille );
        String message = mEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}