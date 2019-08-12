package com.example.jeffe.login;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import es.dmoral.toasty.Toasty;

public class RegisterMain extends AppCompatActivity implements  interfaceListener {


    ConnectionDetector cd;
    DatePickerDialog datePickerDialog;
    Calendar c;
    http http;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;

    EditText nombre_a_registro, correo_a_registro,
            contraseña_a_registro,direccion_a_registro,
            tel_a_registro, identifi_a_registro;

    TextView edad_a_registro;

    Button btn_RegistUser;
    private NotificationHelper helper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        //helper = new NotificationHelper(this);
        identifi_a_registro   = findViewById(R.id.editText_identific);
        nombre_a_registro     = findViewById(R.id.editText_RegNombre);
        correo_a_registro     = findViewById(R.id.editText_RegCorreo);
        contraseña_a_registro = findViewById(R.id.editText_RegConstraseña);
        edad_a_registro       = findViewById(R.id.editText_RegEdad);
        direccion_a_registro  = findViewById(R.id.editText_RegDireccion);
        tel_a_registro        = findViewById(R.id.editText_RegTel);
        btn_RegistUser        = findViewById(R.id.btn_RegistUser);


        edad_a_registro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                c= Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);
                datePickerDialog= new DatePickerDialog(RegisterMain.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        edad_a_registro.setText(mDay + "/" + (mMonth+1)+"/"+mYear);

                    }
                }, ano, mes, dia);
                datePickerDialog.show();

            }
        });

    }


    public void btnRegist(View v) {
        final String name     = nombre_a_registro.getText().toString();
        final String email    = correo_a_registro.getText().toString();
        final String password = contraseña_a_registro.getText().toString();
        final String age      = edad_a_registro.getText().toString();
        final String address  = direccion_a_registro.getText().toString();
        final String phone    = tel_a_registro.getText().toString();
        final String identifi = identifi_a_registro.getText().toString();

        if (!name.isEmpty()) {
            if(!email.isEmpty()){
                if(!password.isEmpty()){
                    if(!age.isEmpty()){
                        if(!address.isEmpty()){
                            if(!phone.isEmpty()){
                                if (!identifi.isEmpty()) {
                                    register(name, email, password, age, address, phone, identifi);
                                }else{
                                    Snackbar snackbar;
                                    snackbar = Snackbar.make(v, "Escriba su numero de identificación por favor!", Snackbar.LENGTH_SHORT);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                                    TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                                    snackbar.show();
                                }
                            }else{
                                Snackbar snackbar;
                                snackbar = Snackbar.make(v, "Escriba un telefono por favor!", Snackbar.LENGTH_SHORT);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                                TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                                snackbar.show();
                            }
                        }else{
                            Snackbar snackbar;
                            snackbar = Snackbar.make(v, "Escriba una dirección por favor!", Snackbar.LENGTH_SHORT);
                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                            snackbar.show();
                        }
                    }else{
                        Snackbar snackbar;
                        snackbar = Snackbar.make(v, "Ingrese una fecha de nacimiento por favor!", Snackbar.LENGTH_SHORT);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                        snackbar.show();
                    }
                }else{
                    Snackbar snackbar;
                    snackbar = Snackbar.make(v, "Escriba un contraseña por favor!", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                    TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                    snackbar.show();
                }
            }else{
                Snackbar snackbar;
                snackbar = Snackbar.make(v, "Escriba un email por favor!", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                snackbar.show();
            }
        } else {
            Snackbar snackbar;
            snackbar = Snackbar.make(v, "Escriba un nombre por favor!", Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
            snackbar.show();
        }
    }

    private void register(String name, String email, String password, String age, String address, String phone, String identifi){

        Response.Listener<String>respoListerner= new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse= new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    String  msg     = jsonresponse.getString("msg");

                    if(success){
                        Intent intent= new Intent(RegisterMain.this,LoginMain.class);
                        RegisterMain.this.startActivity(intent);
                        //notificationUsers();
                        Toasty.success(RegisterMain.this, ""+msg, Toast.LENGTH_LONG, true).show();

                    }else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(RegisterMain.this);
                        builder.setMessage(msg)
                                .setNegativeButton("Reintentar",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(helper, "Ha ocurrido un error en la consulta", Toast.LENGTH_SHORT).show();
                }

            }
        };


        RegisterRequest registroRequest= new RegisterRequest(name, email, age, password, address, phone, identifi,respoListerner);
        RequestQueue queue= Volley.newRequestQueue(RegisterMain.this);
        queue.add(registroRequest);
    }

    public void scan(View v){
        try {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.PDF_417);
            integrator.setPrompt("Scaneando...");
            integrator.setTimeout(20000);
            integrator.setOrientationLocked(true);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        }catch (Exception e){
            Toast.makeText(helper, "Error en el scanner", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        switch (requestCode) {
            case CUSTOMIZED_REQUEST_CODE: {
                Toast.makeText(this, "REQUEST_CODE = " + requestCode, Toast.LENGTH_LONG).show();
                break;
            }
            default:
                break;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        if(result.getContents() == null) {
            Toasty.error(this,"¡NO FUE POSIBLE ESCANEAR EL DOCUMENTO!");
        } else {
            String code = result.getContents();
            decodeString(code);
        }
    }

    private void decodeString(String code){

        cd = new ConnectionDetector(this);
        if(!cd.isNetworkAvailable()) {
            String status = "ERROR";
            String Mess = "¡VERIFIQUE LA CONEXION DE RED!";
            Toasty.error(this,Mess);
        }else{
            http = new http(RegisterMain.this);
            http.decodepdf417(code);

        }
    }

    /*@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void notificationUsers() {

        String title = "Bienvenido";
        String text = nombre_a_registro.getText().toString()+" a nuestra app pruebala ya!";

        Notification.Builder builder = helper.getRegisterUserChannelNotify(title,text)
                    .setSmallIcon(R.mipmap.ic_launcher);
        helper.getManager().notify(new Random().nextInt(),builder.build());
    }*/

    @Override
    public void onPlacesListener(int identifier, Class gointo, JSONArray jsonArray) {
        switch (identifier){
            case 1:
                try {
                    String name     = jsonArray.getJSONObject(0).getJSONArray("fullname").getString(2);
                    String lastname = jsonArray.getJSONObject(0).getJSONArray("fullname").getString(0);
                    String date = jsonArray.getJSONObject(0).getString("born");
                    String identifi = jsonArray.getJSONObject(0).getString("identification");
                    nombre_a_registro.setText(name+" "+lastname);
                    edad_a_registro.setText(date);
                    identifi_a_registro.setText(identifi);
                    correo_a_registro.setFocusable(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

                default:
                    break;
        }

    }
}
