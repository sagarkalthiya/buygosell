package com.trooople.godofhell.buygosell.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.util.Log;

import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.UserSessionManager;

import java.util.ArrayList;


public class Splesh_activity extends AppCompatActivity  {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    UserSessionManager session;

    ArrayList<String> permissions=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new UserSessionManager(getApplicationContext());


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splesh_activity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }



}
