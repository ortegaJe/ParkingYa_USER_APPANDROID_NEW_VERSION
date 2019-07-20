package com.example.jeffe.login;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class PopupReservation {

    Dialog alertDialog;
    interfaceListener interfaceListener;

    public PopupReservation(interfaceListener interfaceListener){
        this.interfaceListener = interfaceListener;
    }

    public  void alert_msg(final Context context, final String tvtitle, final String idparking, final String iduser, final String cost,Boolean cancelable) {
        alertDialog = new Dialog(context);
        alertDialog.setContentView(R.layout.reservation_popup);
        alertDialog.setCancelable(cancelable);

        final TextView title = alertDialog.findViewById(R.id.nombre_park_a_reservar);
        final TextView costTv = alertDialog.findViewById(R.id.costtv);
        final EditText placa = alertDialog.findViewById(R.id.placa_veh);
        final EditText hours = alertDialog.findViewById(R.id.hours);
        Button btn_register = alertDialog.findViewById(R.id.btn_reserv);

        title.setText(tvtitle);
        costTv.setText(cost);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plac_v = placa.getText().toString();
                String hour_par = hours.getText().toString();
                if(!plac_v.isEmpty()){
                    if (plac_v.length() > 5 && plac_v.length() < 7) {
                        if (!hour_par.isEmpty()) {
                            reserv(plac_v, hour_par, idparking, context, alertDialog, iduser);
                        } else {
                            Toasty.error(context,"DEBE INGRESAR UN TIEMPO VALIDO",Toast.LENGTH_SHORT,true);
                        }
                    }else{
                        Toasty.error(context,"LA PLACA INGRESADA NO ES VALIDA",Toast.LENGTH_SHORT,true);
                    }
                }else{
                    Toasty.error(context,"DEBE INGRESAR UNA PLACA VALIDA",Toast.LENGTH_SHORT,true);
                }
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public  void reserv(final String placa, final String hour, String idparki, final Context context, final Dialog alertDialog, String iduser) {
        RequestParams params = new RequestParams();

        final AsyncHttpClient client = new AsyncHttpClient();
        params.put("placa", placa);
        params.put("hora", hour);
        params.put("idparki", idparki);
        params.put("iduser", iduser);
        client.setMaxRetriesAndTimeout(0,10000);
        client.setConnectTimeout(10000);
        client.setTimeout(10000);

        client.post("http://192.168.1.58:8080/backendParkingya/ReservDates.php", params,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int success = jsonArray.getJSONObject(0).getInt("success");
                        alertDialog.dismiss();
                        switch (success){
                            case 1:
                            interfaceListener.onPlacesListener(2, ParkingsMapMarkers.class, jsonArray);
                            break;

                            case 2:
                            case 0:
                                interfaceListener.onPlacesListener(3, ParkingsMapMarkers.class, jsonArray);
                                break;

                            default:
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        String Mess = "¡OCURRIO UN ERROR EN LA CONSULTA, COMUNÍQUESE CON EL ADMINISTRADOR!";
                        Toasty.error(context,Mess,Toast.LENGTH_SHORT,true);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String Mess = "¡NO ES POSIBLE CONECTARSE CON EL SERVIDOR, COMUNIQUESE CON EL ADMINISTRADOR!";
                Toasty.error(context,Mess,Toast.LENGTH_SHORT,true);

            }
        });
    }

}
