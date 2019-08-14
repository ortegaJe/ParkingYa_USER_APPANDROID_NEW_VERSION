package com.example.jeffe.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PqrRequest extends StringRequest {

    private static final String PQR_REQUEST="http://"+Constants.REQUEST_URL+"/backendParkingya/PqrDates.php";
    private Map<String,String> params;
    public PqrRequest(String pqr, Response.Listener<String>listerner){
        super(Method.POST, PQR_REQUEST,listerner,null);

        params= new HashMap<>();
        params.put("pqr",pqr);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
