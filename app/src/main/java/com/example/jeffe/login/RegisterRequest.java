package com.example.jeffe.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTRO_REQUEST_URL="http://192.168.1.58:8080/backendParkingya/Register.php";
    private Map<String,String> params;
    public RegisterRequest(String name, String email, String age, String password, String address, String phone, String identificacion, Response.Listener<String>listerner){
        super(Method.POST, REGISTRO_REQUEST_URL,listerner,null);

        params= new HashMap<>();
        params.put("name",name);
        params.put("email",email);
        params.put("age",age);
        params.put("password",password);
        params.put("address",address);
        params.put("phone",phone);
        params.put("identification",identificacion);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
