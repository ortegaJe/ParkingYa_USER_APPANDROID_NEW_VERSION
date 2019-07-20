package com.example.jeffe.login;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;


public class ConnectionDetector {

    private final Context context;

    public ConnectionDetector(Context context){
        this.context = context;
    }

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo= Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo !=null;
    }

    }


