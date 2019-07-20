package com.example.jeffe.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashMain extends AppCompatActivity {

    private final long SPLASH_SCREEN_DELAY = 2000;
    private SharedPreferences pref;
    private final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        init();
    }

    private void init(){

        if(pref.getBoolean(Constants.IS_LOGGED,false)) {
            Intent intent = new Intent(this, ParkingsMapMarkers.class);
            startActivity(intent);
            finish();

        }else{

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashMain.this, LoginMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
            };

            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);

        }
    }
}
