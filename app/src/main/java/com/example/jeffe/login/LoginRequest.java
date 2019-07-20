package com.example.jeffe.login;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL="http://192.168.1.58:8080/backendParkingya/Login.php";
    private Map<String,String> params;
    public LoginRequest(String email, String password, Response.Listener<String>listerner){
        super(Request.Method.POST, LOGIN_REQUEST_URL,listerner,null);

        params= new HashMap<>();
        params.put("email",email);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
