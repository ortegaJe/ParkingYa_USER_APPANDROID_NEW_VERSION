package com.example.jeffe.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginMain extends AppCompatActivity {

    EditText txtInputEmail, txtInputContraseña;
    private SharedPreferences pref;
    private final String MyPREFERENCES = "MyPrefs";
    LinearLayout mainL;
    ProgressBar progress;
    TextView textView_RegisterLogin;
    Button btn_Enter;


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

        String email= txtInputEmail.getText().toString();
        String password= txtInputContraseña.getText().toString();
        if(!email.isEmpty()){
            if(!password.isEmpty()){
                AccessLogin(email,password);
            }else{
                Toast.makeText(this, "Debe llenar el campo password", Toast.LENGTH_SHORT).show();
            }
        }else{
            txtInputEmail.setError("Este campo no puede estar vacio.");
            Toast.makeText(this, "Debe llenar el campo email", Toast.LENGTH_SHORT).show();
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
                        AlertDialog.Builder builder= new AlertDialog.Builder(LoginMain.this);
                        builder.setMessage("Ups! credencial no registrada, por favor verifique su credencial.")
                                .setNegativeButton("Reintentar",null)
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


    public void register( View view) {
        Intent intentRegistro= new Intent(LoginMain.this, RegisterMain.class);
        LoginMain.this.startActivity(intentRegistro);
    }

}

