package com.example.jeffe.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class PqrMain extends AppCompatActivity  {

    EditText text_pqr_user;
    TextView text_title;
    Button btn_send_pqr;
    Button btn_go_to_map;

    private NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pqr_main);

        text_title     = findViewById(R.id.pqr_title);
        text_pqr_user  = findViewById(R.id.pqr_text);
        btn_send_pqr   = findViewById(R.id.btn_pqr);
        btn_go_to_map  = findViewById(R.id.btn_go_map);

        btn_go_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoMap= new Intent(PqrMain.this,ParkingsMapMarkers.class);
                PqrMain.this.startActivity(intentGoMap);
                finish();
            }
        });

        btn_send_pqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_pqr_user = text_pqr_user.getText().toString();

                if (!txt_pqr_user.isEmpty()){
                    registerPqr(txt_pqr_user);
                }else{
                    Snackbar snackbar;
                    snackbar = Snackbar.make(v, "Escribenos tu inquietud por favor!", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorYellowForSnack));
                    TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorGreenForSnack));
                    snackbar.show();
                }
            }
        });

      }

    private void registerPqr(String pqr) {

        Response.Listener<String>respoListerner= new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse= new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    String  msg     = jsonresponse.getString("msg");

                    if(success){
                        Intent intent= new Intent(PqrMain.this,UserRegistredMain.class);
                        PqrMain.this.startActivity(intent);
                        Toasty.success(PqrMain.this, ""+msg, Toast.LENGTH_LONG, true).show();
                        finish();

                    }else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(PqrMain.this);
                        builder.setMessage(msg)
                                .setNegativeButton("Reintentar",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(helper, "Ha ocurrido un error al momento de generar la PQR", Toast.LENGTH_SHORT).show();
                }

            }
        };


        PqrRequest pqrRequest= new PqrRequest(pqr, respoListerner);
        RequestQueue queue= Volley.newRequestQueue(PqrMain.this);
        queue.add(pqrRequest);
    }

}



