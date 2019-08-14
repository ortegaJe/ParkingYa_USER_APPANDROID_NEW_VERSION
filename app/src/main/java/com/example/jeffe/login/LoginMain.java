package com.example.jeffe.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class LoginMain extends AppCompatActivity {

    EditText txtInputEmail, txtInputContraseña;
    private SharedPreferences pref;
    private final String MyPREFERENCES = "MyPrefs";
    LinearLayout mainL;
    ProgressBar progress;
    TextView textView_RegisterLogin;
    Button btn_Enter;

    int timer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        txtInputEmail      = findViewById(R.id.editText_LoginName);
        txtInputContraseña = findViewById(R.id.editText_LoginPass);
        mainL              = findViewById(R.id.mainlayout);
        progress           = findViewById(R.id.progress);
        pref               = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        textView_RegisterLogin = findViewById(R.id.textView_registerLogin);
        btn_Enter              = findViewById(R.id.btn_enter);
    }


    public void validate(View v){

        String email_input= txtInputEmail.getText().toString();
        String password_input= txtInputContraseña.getText().toString();

        if(!email_input.isEmpty()){
            if(!password_input.isEmpty()){
                AccessLogin(email_input,password_input);
            }else{
                Snackbar snackbar;
                snackbar = Snackbar.make(v, "Debe ingresar una contraseña!", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                snackbar.show();
            }
                 }else{
                     Snackbar snackbar;
                     snackbar = Snackbar.make(v, "Debe ingresar un email!", Snackbar.LENGTH_SHORT);
                     View snackBarView = snackbar.getView();
                     snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                     TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                     textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                     snackbar.show();
            }
    }


    private void AccessLogin(final String email, final String password){

        mainL.setAlpha((float) 0.0);
        progress.setVisibility(View.VISIBLE);
        Response.Listener<String> responseListener= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success= jsonResponse.getBoolean("success");
                    if(success){
                        String name    = jsonResponse.getString("name");
                        String age     = jsonResponse.getString("age");
                        String id      = jsonResponse.getString("id");
                        String address = jsonResponse.getString("address");
                        String phone   = jsonResponse.getString("phone");

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean(Constants.IS_LOGGED, true);
                        editor.putString(Constants.NAME,name);
                        editor.putString(Constants.EMAIL,email);
                        editor.putString(Constants.AGE,age);
                        editor.putString(Constants.ADDRESS,address);
                        editor.putString(Constants.PHONE,phone);
                        editor.putString(Constants.ID,id);
                        editor.apply();
                        editor.commit();
                        Intent intent= new Intent(LoginMain.this, ParkingsMapMarkers.class);
                        LoginMain.this.startActivity(intent);
                        finish();
                    }else{
                        final AlertDialog.Builder builder= new AlertDialog.Builder(LoginMain.this);
                        builder.setMessage("Ups! credencial no registrada, por favor verifique su credencial.")
                                .setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        Intent retryIntent = new Intent(LoginMain.this, LoginMain.class);
                                        startActivity(retryIntent);
                                        finish();
                                    }
                                })
                                .create().show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest= new LoginRequest(email, password, responseListener);
        RequestQueue queue= Volley.newRequestQueue(LoginMain.this);
        queue.add(loginRequest);

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        super.onDestroy();

        LoginMain.this.finish();
    }


    public void register( View view) {
        Intent intentRegistro= new Intent(LoginMain.this, RegisterMain.class);
        LoginMain.this.startActivity(intentRegistro);
    }

    @Override
    public void onBackPressed() {

        if (timer==0){
            //Toast.makeText(getApplicationContext(),"Presione nuevamente para salir de la aplicación", Toast.LENGTH_SHORT).show();
            Toasty.warning(getApplicationContext(),"Presione nuevamente para salir de la aplicación", Toast.LENGTH_SHORT).show();
            timer++;
        }else{
            super.onBackPressed();
        }

        new CountDownTimer(3000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                timer=0;
            }
        }.start();
    }

}

