package com.example.jeffe.login;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class http {
    String id;
    RequestParams params = new RequestParams();
    interfaceListener interfaceListener;
    String ipserver = "192.168.1.58:8080";


    public http(interfaceListener interfaceListener){
        this.interfaceListener = interfaceListener;
    }

    public void getPlaces(){
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);
        client.setConnectTimeout(10000);
        client.setTimeout(10000);
        client.post("http://"+ipserver+"/backendParkingya/getPlaces.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        interfaceListener.onPlacesListener(1, ParkingsMapMarkers.class, jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(1);
                interfaceListener.onPlacesListener(0, ParkingsMapMarkers.class, jsonArray);
            }
        });
    }

    public void decodepdf417(String code){
        id = null;
        params.put("code", code);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);
        client.setConnectTimeout(10000);
        client.setTimeout(10000);
        client.post("http://52.14.112.11/parkingya/decode.php",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int success = jsonArray.getJSONObject(0).getInt("success");
                        if (success == 1) {
                            id = jsonArray.getJSONObject(0).getString("identification");
                            if(id != null){
                                //String newid = id.replaceFirst("^0+(?!$)","");
                                interfaceListener.onPlacesListener(1, RegisterMain.class, jsonArray);
                            }else{
                                String status = "ERROR";
                                String Mess = "¡IDENTIFICACIÓN NO RECONOCIDA!";
                            }
                        }else{
                            String msg = jsonArray.getJSONObject(0).getString("msg");
                            String status = "ERROR";
                            String Mess = msg;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        String status = "ERROR";
                        String Mess = "¡OCURRIO UN ERROR EN LA CONSULTA, COMUNÍQUESE CON EL ADMINISTRADOR!";
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(1);
                interfaceListener.onPlacesListener(0, ParkingsMapMarkers.class, jsonArray);
            }
        });

    }
}
