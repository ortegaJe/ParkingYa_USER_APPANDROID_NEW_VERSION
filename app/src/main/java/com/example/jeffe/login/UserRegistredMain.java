package com.example.jeffe.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class UserRegistredMain extends AppCompatActivity {

    private SharedPreferences pref;
    private final String MyPREFERENCES = "MyPrefs";
    private RelativeLayout RelativeLayoutUser;
    private TextView t1_name, t2_email, t4_age, t5_address, t6_tel;

    PopupReservation popupReservationPqr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registered_main);

        RelativeLayoutUser= findViewById(R.id.RelativeLayoutUser);
        pref       = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        t1_name    = findViewById(R.id.textView_NombreRegis);
        t2_email   = findViewById(R.id.txtV_email_user);
        t4_age     = findViewById(R.id.textView_EdadRegis);
        t5_address = findViewById(R.id.textView_addressRegis);
        t6_tel     = findViewById(R.id.txtV_tel_reg_user);

        String name    = pref.getString(Constants.NAME,"");
        String email   = pref.getString(Constants.EMAIL,"");
        String age     = pref.getString(Constants.AGE,"");
        String address = pref.getString(Constants.ADDRESS,"");
        String phone   = pref.getString(Constants.PHONE,"");


        t1_name.setText(name);
        t2_email.setText(email);
        t4_age.setText(age);
        t5_address.setText(address);
        t6_tel.setText(phone);

        BottomNavigationView navigationActivity = findViewById(R.id.user_page_navigation);
        navigationActivity.setOnNavigationItemSelectedListener(OpenActivity);

        FloatingActionButton fab = findViewById(R.id.fab_add_pqr);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 Snackbar.make(view, "Realiza una pqr", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener OpenActivity=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            Snackbar snackbar;
                            snackbar= Snackbar.make(RelativeLayoutUser,"Cerrar sessión?",Snackbar.LENGTH_LONG);
                            View snackBarView = snackbar.getView();
                            snackbar.setAction("SI", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intentExit = new Intent(UserRegistredMain.this, LoginMain.class);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString(Constants.ID,"");
                                    editor.putString(Constants.EMAIL,"");
                                    editor.putString(Constants.NAME,"");
                                    editor.putString(Constants.PHONE,"");
                                    editor.putString(Constants.ADDRESS,"");
                                    editor.putString(Constants.AGE,"");
                                    editor.putBoolean(Constants.IS_LOGGED,false);
                                    editor.apply();
                                    startActivity(intentExit);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    finish();
                                }
                            })
                                    .show();
                            snackbar.setActionTextColor(Color.WHITE);
                            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);

                            break;
                        case R.id.nav_map:
                            Intent intentMap = new Intent(UserRegistredMain.this, ParkingsMapMarkers.class);
                            startActivity(intentMap);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                    }
                    return true;
                }
            };
    }
