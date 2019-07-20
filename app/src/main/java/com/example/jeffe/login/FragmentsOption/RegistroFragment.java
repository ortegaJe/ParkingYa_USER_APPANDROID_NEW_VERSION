package com.example.jeffe.login.FragmentsOption;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jeffe.login.LoginMain;
import com.example.jeffe.login.R;
import com.example.jeffe.login.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegistroFragment extends Fragment implements View.OnClickListener {

    DatePickerDialog datePickerDialog;
    Calendar c;

    TextView edad_a_registro;

    EditText nombre_a_registro, correo_a_registro,
            contraseña_a_registro,
            direccion_a_registro, tel_a_registro;

    Button btn_RegistUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_registro_fragment, container, false);

        nombre_a_registro=(EditText)v.findViewById(R.id.editText_RegNombre);
        correo_a_registro=(EditText)v.findViewById(R.id.editText_RegCorreo);
        contraseña_a_registro=(EditText)v.findViewById(R.id.editText_RegConstraseña);
        edad_a_registro=(TextView) v.findViewById(R.id.editText_RegEdad);
        direccion_a_registro=(EditText)v.findViewById(R.id.editText_RegDireccion);
        tel_a_registro=(EditText)v.findViewById(R.id.editText_RegTel);

        btn_RegistUser=v.findViewById(R.id.btn_RegistUser);
        btn_RegistUser.setOnClickListener((View.OnClickListener) this);

        //Accion calendario
        edad_a_registro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                c= Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                datePickerDialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        edad_a_registro.setText(mDay + "/" + (mMonth+1)+"/"+mYear);

                    }
                }, ano, mes, dia);
                datePickerDialog.show();

            }
        });

        return v;
    }

    private boolean validateName() {
        String nombreInput = nombre_a_registro.getText().toString().trim();

        if (nombreInput.isEmpty()) {
            nombre_a_registro.setError("Este campo no puede estar vacio.");
            return false;
        } else {
            nombre_a_registro.setError(null);
            return true;

        }

    }

    @Override
    public void onClick(View v) {

        final String name= nombre_a_registro.getText().toString();
        final String email= correo_a_registro.getText().toString();
        final String password= contraseña_a_registro.getText().toString();
        //final int age= Integer.parseInt(editText_RegistEdad.getText().toString());
        final String age= edad_a_registro.getText().toString();
        final String address= direccion_a_registro.getText().toString();
        final String phone= tel_a_registro.getText().toString();

        Response.Listener<String>respoListerner= new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse= new JSONObject(response);
                    boolean success= jsonresponse.getBoolean("success");

                    if(success){
                        Intent intent= new Intent(getActivity(), LoginMain.class);
                        RegistroFragment.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity().getApplicationContext());
                        builder.setMessage("Error al registrar")
                                .setNegativeButton("Reintentar",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if(!validateName()){
            return;
        }

        /*RegisterRequest registroRequest= new RegisterRequest(name, email, age, password, address, phone, identification,respoListerner);
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(registroRequest);*/

    }
}
