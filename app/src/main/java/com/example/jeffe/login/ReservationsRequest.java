package com.example.jeffe.login;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationsRequest extends StringRequest {

    private static final String REGISTRO_RESERV_URL="http://"+Constants.REQUEST_URL+"/backendParkingya/ReservDates.php";
    private Map<String,String> params;
    public ReservationsRequest(String name_park, String license, Response.Listener<String>listerner){
        super(Request.Method.POST, REGISTRO_RESERV_URL,listerner,null);

        params= new HashMap<>();
        params.put("name_park",name_park);
        params.put("license",license);
        //params.put("hours",hours);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
